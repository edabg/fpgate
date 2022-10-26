/*
 * Copyright (C) 2019 EDA Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eda.cb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.auth.TicketAuth;
import io.crossbar.autobahn.wamp.interfaces.IAuthenticator;
import io.crossbar.autobahn.wamp.interfaces.ITransport;
import io.crossbar.autobahn.wamp.types.CallResult;
import io.crossbar.autobahn.wamp.types.CloseDetails;
import io.crossbar.autobahn.wamp.types.ExitInfo;
import io.crossbar.autobahn.wamp.types.InvocationDetails;
import io.crossbar.autobahn.wamp.types.InvocationResult;
import io.crossbar.autobahn.wamp.types.RegisterOptions;
import io.crossbar.autobahn.wamp.types.Registration;
import io.crossbar.autobahn.wamp.types.SessionDetails;
import io.crossbar.autobahn.wamp.utils.Platform;
import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.ws.BindingProvider;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import static java.util.concurrent.CompletableFuture.runAsync;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.util.UrlEncoded;
import org.eda.coerp.soap.ErpFPGateAuthInfo;
import org.eda.coerp.soap.ErpSOAPBaseInfoRequestType;
import org.eda.coerp.soap.ErpSOAPBaseInfoResponseType;
import org.eda.coerp.soap.ErpSOAPFPGateDoneSessionRequestType;
import org.eda.coerp.soap.ErpSOAPFPGateDoneSessionResponseType;
import org.eda.coerp.soap.ErpSOAPFPGateGetCrossbarIOAuthInfoRequestType;
import org.eda.coerp.soap.ErpSOAPFPGateGetCrossbarIOAuthInfoResponseType;
import org.eda.coerp.soap.ErpSOAPServerLoginByPAPRequestType;
import org.eda.coerp.soap.ErpSOAPServerLogoutRequestType;
import org.eda.coerp.soap.Erpserver;
import org.eda.coerp.soap.ErpserverPortType;
import org.eda.fpsrv.PrintRequest;
import org.restlet.data.CharacterSet;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author Dimitar Angelov
 */
public class CBIOService {
    private static final Logger LOGGER = Logger.getLogger(CBIOService.class.getName());

    public static Logger getLogger() {
        return LOGGER;
    }
    
    public static enum ConnectionState {
//        NA, AUTH, AUTHFAILED, CONNECTING, CONNECTED, DISCONNECTED, JOINED, LEAVED
        NA ("NA", "N/A", "?") 
        , AUTH ("AUTH", "Идентидфициран", "i")
        , AUTHFAILED ("AUTHFAILED", "Отказан", "!")
        , CONNECTING ("CONNECTING", "Свързване", ">")
        , CONNECTED ("CONNECTED", "Свързан", "=")
        , DISCONNECTED ("DISCONNECTED", "Прекъснат", ".")
        , JOINED ("JOINED", "Присъединен", "o")
        , LEAVED ("LEAVED", "Напуснат", "x")
        ;
        
        private final String stateNameL;
        private final String stateName;
        private final String stateAbbr;
        
        private ConnectionState(String nameL, String name, String abbr) {
            stateNameL = nameL;
            stateName = name;
            stateAbbr = abbr;
        }
        
        public String abbr() {
            return this.stateAbbr;
        }
        public String toString() {
            return this.stateName;
        }        
    }

    private int mServiceNum = 0;

    public int getServiceNum() {
        return mServiceNum;
    }

    public void setServiceNum(int mServiceNum) {
        this.mServiceNum = mServiceNum;
    }
    
    // Colibri ERP SOAPService
    private String coWSDL;
    private String coPAPID;
    private String coUser;
    private String coPassword;
    private ErpserverPortType coPort = null;
    protected CoErpCipher coCipher;
    protected boolean coConnected;
    
    private String mFPGateID;

    protected String mMethodPrefix = "com.eda.fpgate";
    private ExecutorService mExecutor;
    // This is the central object to interact with Crossbar.io
    // a WAMP session runs over a transport, uses authenticators
    // and finally joins a realm.
    private Session mSession;
    private ITransport mTransport;
    private Client mClient;
    private String mURL;
    private String mRealm;
    private String mUser;
    private String mTicket;

    private int mConnectionExitCode;
    private ConnectionState mConnectionState;
    private ScheduledExecutorService connectionCheckScheduler;
    
    // CBIOService Singleton
    private final ArrayList<OnStateChangeListener> mOnStateChangeListeners = new ArrayList<>();
    
    // Create a trust manager that does not validate certificate chains like the default 
    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                {
                    //No need to implement.
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                {
                    //No need to implement.
                }
            }
    };    
    
    public class CoErpCipher {
        
        Cipher cipherDecrypt;
        Cipher cipherEncrypt;
        IvParameterSpec iv;
        SecretKeySpec skeySpec;

        public CoErpCipher(String cipherName, String key, String initVector) throws Exception {
            byte[] initVectorBytes;
            String initVectorStr;
            byte[] keyBytes;
            String keyStr;
            switch(cipherName) {
                // Enabling Unlimited Strength Jurisdiction Policy
                //
                case "AES-256-CBC" : //result is invalid padding block!
                case "AES-128-CBC" :
//                    Provider p = new BouncyCastleProvider();
//                    Security.addProvider(p);
                    int MakKeyLength  = Cipher.getMaxAllowedKeyLength("AES");
                    initVectorBytes = Base64.getDecoder().decode(initVector);
                    initVectorStr = DatatypeConverter.printHexBinary(initVectorBytes);
                    keyBytes = Base64.getDecoder().decode(key);
                    keyStr = DatatypeConverter.printHexBinary(keyBytes);
                    iv = new IvParameterSpec(initVectorBytes);
                    skeySpec = new SecretKeySpec(keyBytes, "AES");
                    cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                    cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", p);
                    
//                    cipherDecrypt = Cipher.getInstance("AES/CBC/NoPadding");
//                    cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                    cipherEncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipherEncrypt.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                    break;
                case "BF-CBC" :
                    initVectorBytes = Base64.getDecoder().decode(initVector);
                    initVectorStr = DatatypeConverter.printHexBinary(initVectorBytes);
                    keyBytes = Base64.getDecoder().decode(key);
                    keyStr = DatatypeConverter.printHexBinary(keyBytes);
                    iv = new IvParameterSpec(initVectorBytes);
                    skeySpec = new SecretKeySpec(keyBytes, "Blowfish");
                    cipherDecrypt = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
                    cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                    cipherEncrypt = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
                    cipherEncrypt.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                    break;
                case "DES-CBC" :
                    initVectorBytes = Base64.getDecoder().decode(initVector);
                    initVectorStr = DatatypeConverter.printHexBinary(initVectorBytes);
                    keyBytes = Base64.getDecoder().decode(key);
                    keyStr = DatatypeConverter.printHexBinary(keyBytes);
                    iv = new IvParameterSpec(initVectorBytes);
                    skeySpec = new SecretKeySpec(keyBytes, "DES");
                    cipherDecrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
                    cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                    cipherEncrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
                    cipherEncrypt.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                    break;
                default :
                    throw new Exception("Unsupported Cipher "+cipherName);
            }
        }
        
        public String decrypt(String dataEncrypted) throws IllegalBlockSizeException, BadPaddingException {
            byte[] dataEncryptedBytes = Base64.getDecoder().decode(dataEncrypted);
            String dataEncryptedStr = DatatypeConverter.printHexBinary(dataEncryptedBytes);
            byte[] original = cipherDecrypt.doFinal(dataEncryptedBytes);
            String originalStr = DatatypeConverter.printHexBinary(original);
//            return new String(original, Charset.forName("ASCII"));
            return new String(original, Charset.forName("UTF-8"));
        }

        public String encrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
            byte[] encryptedData = cipherEncrypt.doFinal(data);
            String encryptedStr = Base64.getEncoder().encodeToString(encryptedData);
            return encryptedStr;
        }
        
        public String encrypt(String data) throws IllegalBlockSizeException, BadPaddingException {
            return encrypt(data.getBytes(StandardCharsets.UTF_8));
//            byte[] encryptedData = cipherEncrypt.doFinal(data.getBytes(StandardCharsets.UTF_8));
//            String encryptedStr = Base64.getEncoder().encodeToString(encryptedData);
//            return encryptedStr;
        }
        
    } // CoErpCipher
    
    public interface OnStateChangeListener {
        void onStateChange(CBIOService service,  ConnectionState state);
    }
    

    public CBIOService() {
    }
    

    public void setCoERPInfo(String WSDL, String PAPID, String user, String password) {
        coWSDL = WSDL;
        coPAPID = PAPID;
        coUser = user;
        coPassword = password;
        coPort = null;
    }

    public void setCoERPInfo(String coERPURL, String user, String password) {
        try {
//            javax.ws.rs.core.UriBuilder urib = new javax.ws.rs.core.UriBuilder();
            HttpURI huri = new HttpURI(coERPURL);
            UrlEncoded ue = new UrlEncoded();
            huri.decodeQueryTo(ue);
            if (ue.containsKey("gid")) {
                mFPGateID = ue.getValues("gid").get(0);
                ue.remove("gid");
            }
            if (ue.containsKey("pid")) {
                coPAPID = ue.getValues("pid").get(0);
                ue.remove("pid");
            }
            huri.setQuery(ue.encode());
            coWSDL = huri.toString();
            coUser = user;
            coPassword = password;
            coPort = null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    
    public OnStateChangeListener addOnStateChangeListener(OnStateChangeListener listener) {
        if (!mOnStateChangeListeners.contains(listener)) {
            mOnStateChangeListeners.add(listener);
        }
        return listener;
    }
    
    public  void removeOnStateChangeListener(OnStateChangeListener listener) {
        if (mOnStateChangeListeners.contains(listener)){
            mOnStateChangeListeners.remove(listener);
        }
    }
    
    protected  void onStateChange() {
        LOGGER.fine("OnState change notify");
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (OnStateChangeListener listener: mOnStateChangeListeners) {
            futures.add(runAsync(() -> listener.onStateChange(this, getConnectionState())));
        }
        CompletableFuture d = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
//        d.thenRunAsync(() -> {
//            LOGGER.fine("All listeners was notified.");
//        });
        
    }
    
    public static CBIOService newService(String coURL, String user, String password, int mServiceNum, boolean start) {
        
        CBIOService service = new CBIOService();
        service.setServiceNum(mServiceNum);
        service.setCoERPInfo(coURL, user, password);
        if (start)
            service.start();
        return service;
    }

    public static CBIOService newService(String coURL, String user, String password) {
        return newService(coURL, user, password, 0, true);
    }
    
    public ConnectionState getConnectionState() {
        return (mConnectionState != null)?mConnectionState:ConnectionState.NA;
    }
    
    protected ErpserverPortType getCoPort() throws Exception {
        if (coPort == null) {
            // Sets debugging of soap request/responses
            // Addition you can run prameters to JVM -Djaxb.debug=true
/*            
            if (false) {
                System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
                System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
                System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
                System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
                System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");   
            }    
*/			
            // Adjust to accept all certtificates
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                    @Override
                    public boolean verify(String string, SSLSession ssls) {
                        return true;
                    }
                });                
            } catch (Exception e) {
                System.out.println(e);
            }        
            Erpserver coerp = new Erpserver(new URL(coWSDL));
            coPort = coerp.getErpserverPort();
            // Adjust HTTP Session Cookie support and if is needed other parameters
            Map requestCtx = ((BindingProvider) coPort).getRequestContext();
    //        requestCtx.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
            requestCtx.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);        
        }
        return coPort;
    }
    
    public void initCoERPSession() throws Exception {
        coConnected = false;
        LOGGER.info("Logging to Colibri ERP Server");
        ErpserverPortType port = getCoPort();
        ErpSOAPServerLoginByPAPRequestType loginRequest = new ErpSOAPServerLoginByPAPRequestType();
        loginRequest.setPAPID(coPAPID);
        loginRequest.setUsername(coUser);
        loginRequest.setPassword(coPassword);
        port.erpSOAPServerLoginByPAP(loginRequest); // In case of fail fault and exception is returned
//            System.out.println(loginResponse.getReturn().toString());
//            ErpSOAPServerCheckEncodingRequestType checkEncodingRequest = new ErpSOAPServerCheckEncodingRequestType();
//            checkEncodingRequest.setInput("Ала бала");
//            ErpSOAPServerCheckEncodingResponseType encResponse = port.erpSOAPServerCheckEncoding(checkEncodingRequest);
        try {
            ErpSOAPBaseInfoResponseType infoResponse = port.erpSOAPBaseInfo(new ErpSOAPBaseInfoRequestType());
            LOGGER.info("Logged to "+infoResponse.getReturn().getName()+"/"+infoResponse.getReturn().getBrand()+"/"+infoResponse.getReturn().getRevision());
        } catch (Exception E) {
            LOGGER.warning(E.getMessage());
        }
        // trying to initialize crossbario secure channel
        Exception localException = null;
        try {
//            System.out.println(infoResponse.getReturn().getName());            
            ErpSOAPFPGateGetCrossbarIOAuthInfoRequestType CrossbarIOAuthInfoRequest = new ErpSOAPFPGateGetCrossbarIOAuthInfoRequestType();
            CrossbarIOAuthInfoRequest.setFPGateID(mFPGateID);
            ErpSOAPFPGateGetCrossbarIOAuthInfoResponseType CrossbarIOAuthInfoResponse = port.erpSOAPFPGateGetCrossbarIOAuthInfo(CrossbarIOAuthInfoRequest);
            ErpFPGateAuthInfo FPGateInfo = CrossbarIOAuthInfoResponse.getReturn();
            mURL = FPGateInfo.getWSURI();
            mRealm = FPGateInfo.getRealm();
            mMethodPrefix = FPGateInfo.getProcPrefix();
            if (mMethodPrefix.endsWith(".")) 
                mMethodPrefix = mMethodPrefix.substring(0,mMethodPrefix.length()-1);
            String TestPattern = FPGateInfo.getTestPattern();
            String TestPatternEncrypted =FPGateInfo.getTestPatternEncrypted();
            String IV = ""; // Initialization vector
            String[] EParts = TestPatternEncrypted.split("\\|");
            if (EParts.length > 1) {
                TestPatternEncrypted = EParts[0];
                IV = EParts[1];
            } else {
                // There is no initialization vector
            }    
            coCipher = new CoErpCipher(FPGateInfo.getEncryptionMethod()
                    , FPGateInfo.getEncryptionKey()
                    , IV
            );
            // Test and verify decryption on test pattern
            TestPatternEncrypted = coCipher.decrypt(TestPatternEncrypted);
            if (TestPatternEncrypted.equals(TestPattern)) {
                LOGGER.info("Encryption handshake OK");
            } else {
                LOGGER.severe("Encryption hanshake FAILED!");
                throw new Exception("Encryption hanshake FAILED!");
            } 
            mUser = coPAPID+"/"+mFPGateID;
            mTicket = coCipher.encrypt(FPGateInfo.getSessionID());
            coConnected = true;
        } catch (Exception E) {
            LOGGER.severe(E.getMessage());
//            disconnect();
            localException = E;
        }        
        port.erpSOAPServerLogout(new ErpSOAPServerLogoutRequestType());
        if (localException != null)
            throw localException;
    }
    
    public void leaveCoERPSession() throws Exception {
        // TODO: set dtSessionDisconnected, SessionState = DISCONNECTED, idSession = NULL
        ErpserverPortType port = getCoPort();
        ErpSOAPFPGateDoneSessionRequestType doneRequest = new ErpSOAPFPGateDoneSessionRequestType();
        doneRequest.setFPGateID(mFPGateID);
        doneRequest.setPAPID(coPAPID);
        doneRequest.setTicket(mTicket);
        try {
            ErpSOAPFPGateDoneSessionResponseType response = port.erpSOAPFPGateDoneSession(doneRequest);
            if (response.getReturn().intValue() == 1)
                LOGGER.info("Logged out successfully.");
            else
                LOGGER.severe("Logout failed!");
        } catch (Exception E) {
            LOGGER.severe(E.getMessage());
        }
    }

    protected String getMethodFullName(String methodName) {
        return mMethodPrefix+"."+methodName;
    }
    
    public void stopCheckConnection() {
        if (connectionCheckScheduler != null) {
            connectionCheckScheduler.shutdownNow();
        }
        connectionCheckScheduler = null;
    }
    
    public void startCheckConnection() {
        // Initiate Colibri ERP Session maintain
        connectionCheckScheduler = Executors.newScheduledThreadPool(1);
        connectionCheckScheduler.scheduleAtFixedRate(
            new Runnable() {
                @Override
                public void run() {
                    checkConnection();
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }
            , 1
            , 1
            , TimeUnit.MINUTES
        );
    }
    
    public void start() {
        LOGGER.info("Starting CBIO Service");
        mExecutor = Executors.newSingleThreadExecutor();
        connect();
        startCheckConnection();
    }
    public void stop() {
        stopCheckConnection();
        disconnect();
        if (mExecutor != null) {
            mExecutor.shutdown();
            try {
                if (!mExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    mExecutor.shutdownNow();
                } 
            } catch (InterruptedException e) {
                mExecutor.shutdownNow();
            }       
        }
        setConnectionState(ConnectionState.NA);
        mExecutor = null;
    }
    
    
    public void checkConnection() {
        LOGGER.fine("Checking connection");
        if (mSession != null) {
            if (mSession.isConnected()) {
                LOGGER.fine("Session is connected.");
                // TODO: Additional check with message/call
                CompletableFuture<CallResult> callFuture = mSession.call("com.eda.coerp.fpgate.verify", coPAPID, mFPGateID, mTicket);
                try {
                    LOGGER.fine("Calling verify ...");
                    callFuture.thenAccept((verifyResult) -> {
                        if ((verifyResult.results != null) 
                             && (verifyResult.results.size() > 0)
                             && verifyResult.results.get(0).equals(1)
                        ) {
                            LOGGER.fine("Verify session OK.");
                            return;
                        }
                        LOGGER.warning("Verify of session failed! "+verifyResult.results.toString());
                        if (mConnectionState != ConnectionState.CONNECTING) {
                            disconnect();
                            connect();
                        }
                    });
//                    CallResult verifyResult = callFuture.get(10, TimeUnit.SECONDS);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                return;
            } else {
                LOGGER.warning("Session is not connected.");
            }
        } else
            LOGGER.warning("Session is not instantiated!");
        //TODO: Trying to reconnect!
        if (mConnectionState != ConnectionState.CONNECTING) {
            disconnect();
            connect();
        }
    }
    
    protected void setConnectionState(ConnectionState state) {
        LOGGER.finest("Setting connection state to: "+state.stateNameL);
        mConnectionState = state;
        onStateChange();
    }
    public void disconnect() {
        if (mSession != null) {
            if (mConnectionState == ConnectionState.JOINED) {
                mSession.leave(mMethodPrefix+".leave");
                // Ugly solution to give enough time to complete session and call listeners
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CBIOService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
            mSession.removeOnConnectListener(this::sessionOnConnect);
            mSession.removeOnJoinListener(this::sessionOnJoin);
            mSession.removeOnLeaveListener(this::sessionOnLeave);
            mSession.removeOnDisconnectListener(this::sessionOnDisconnect);
            try {
                if (mTransport != null) {
                    // schedule transport close 
                    // while fix to issue "ensure to close nio loop on netty"  https://github.com/crossbario/autobahn-java/pull/442
                    // is applied
                    ITransport thisTransport = mTransport;
                    Executors.newSingleThreadScheduledExecutor().schedule(
                        () -> {
                            LOGGER.fine("Closing old transport for session");
                            try {
                                thisTransport.close();
                            } catch (Exception ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                        , 2, TimeUnit.SECONDS
                    );
                }    
                mTransport = null;
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        mSession = null;
        mClient = null;
        setConnectionState(ConnectionState.DISCONNECTED);
        try {
            leaveCoERPSession();
        } catch (Exception ex) {
            LOGGER.severe("Colibri ERP Session leave failed! "+ex.getMessage());
            return;
        }
    }
    
    protected void sessionOnConnect(Session session) {
        if (session.equals(mSession)) {
            LOGGER.info("Session connected");
            setConnectionState(ConnectionState.CONNECTED);
        } else {
            LOGGER.warning("Session connected is not current!");
        }    
    }
    
    protected void sessionOnLeave(Session session, CloseDetails details) {
        if ((mSession == null) || session.equals(mSession)) {
            LOGGER.info("Session leaved");
            setConnectionState(ConnectionState.LEAVED);
        } else {
            LOGGER.warning("Session leaved is not current!");
        }    
    }
    
    protected void sessionOnJoin(Session session, SessionDetails details) {
        if (session.equals(mSession)) {
            LOGGER.info("Session joined");
            setConnectionState(ConnectionState.JOINED);
            RegisterOptions options = new RegisterOptions("exact", "single");
            String requestMethodName = getMethodFullName("request");
            CompletableFuture<Registration> regRequestMethod = session.register(requestMethodName, this::requestHandler, options);
            regRequestMethod.whenComplete((registration, throwable) -> {
                if (throwable == null) {    
                    LOGGER.info("procedure registered: "+requestMethodName);
                } else {
                    LOGGER.severe("Registration failed: "+requestMethodName+"/"+throwable.getMessage());
                }    
            });
            String disconnectMethodName = getMethodFullName("disconnect");
            CompletableFuture<Registration> regDisconnectMethod = session.register(disconnectMethodName, this::disconnectHandler, options);
            regDisconnectMethod.whenComplete((registration, throwable) -> {
                if (throwable == null) {
                    LOGGER.info("procedure registered: "+disconnectMethodName);
                } else {
                    LOGGER.severe("Registration failed: "+requestMethodName+"/"+throwable.getMessage());
                }
            });
        } else {
            LOGGER.warning("Session joined is not current!");
        }    
    }
    
    protected void sessionOnDisconnect(Session session, boolean wasClean) {
        if ((mSession == null) || session.equals(mSession)) {
            LOGGER.info("Session disconnected");
            setConnectionState(ConnectionState.DISCONNECTED);
//            try {
//                if (mTransport != null)
//                    mTransport.close();
//                mTransport = null;
//            } catch (Exception ex) {
//                Logger.getLogger(CBIOService.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            LOGGER.warning("Session disconnected is not current!");
        }    
    }
	
	protected  void sessionOnUserError(Session session, String message) {
        LOGGER.info("sessionOnUserError:"+message);
	}
    
    synchronized public void connect() {
        try {
            setConnectionState(ConnectionState.AUTH);
            initCoERPSession();
        } catch (Exception ex) {
            setConnectionState(ConnectionState.AUTHFAILED);
            LOGGER.severe("Colibri ERP Session failed! "+ex.getMessage());
            return;
        }
        setConnectionState(ConnectionState.CONNECTING);
        LOGGER.info(String.format("Conecting to url=%s, realm=%s", mURL, mRealm));
        // first, we create a session object (that may or may not be reused)
        mSession = new Session(mExecutor);
//        mSession.addOnConnectListener(this::onSessionConnect);
        // when the session joins a realm, run our code
        mSession.addOnReadyListener((aSession) -> { // ???
            LOGGER.info("Session ready");
//            setConnectionState(ConnectionState.CONNECTED);
        });
		mSession.addOnConnectListener(this::sessionOnConnect);
        mSession.addOnJoinListener(this::sessionOnJoin);
        mSession.addOnLeaveListener(this::sessionOnLeave);
        mSession.addOnDisconnectListener(this::sessionOnDisconnect);
        mSession.addOnUserErrorListener(this::sessionOnUserError);

        // finally, provide everything to a Client instance
//        Client client = new Client(mSession, url, realm, mExecutor);
/*
        IAuthenticator authenticator = new TicketAuth(user, ticket);
        realm = "realm-fpgate";
        Client client = new Client(mSession, url, realm, authenticator);
*/
//Logger.getLogger(Client.class.getName()).setLevel(Level.ALL);
//Logger.getLogger(Session.class.getName()).setLevel(Level.ALL);
//Logger.getLogger(NettyWebSocket.class.getName()).setLevel(Level.ALL);
//Logger.getLogger(NettyWebSocketClientHandler.class.getName()).setLevel(Level.ALL);

        mTransport = Platform.autoSelectTransport(mURL);
        mClient = new Client(mTransport, mExecutor);
		//client.getExtensionFactory().register("permessage-deflate",PerMessageDeflateExtension.class);
        IAuthenticator authenticator = new TicketAuth(mUser, mTicket);
        List<IAuthenticator> mAuthenticators = new ArrayList<>();
        mAuthenticators.add(authenticator);
        mClient.add(mSession, mRealm, mAuthenticators);
		
        CompletableFuture<ExitInfo> exitFuture = mClient.connect();
        exitFuture.thenAccept(exitInfo -> {
            mConnectionExitCode = exitInfo.code;
            LOGGER.info("Client connection exit with code: "+exitInfo.code+" "+getConnectionState().stateNameL);
            setConnectionState(ConnectionState.DISCONNECTED);
        });
        
        exitFuture.handle((ExitInfo res, Throwable ex) -> {
            LOGGER.info("Client connection handle");
            if(ex != null) {
                LOGGER.severe("Client Connection error: " + ex.getMessage());
                return new ExitInfo(false);
            }
            return res;
        });
        
    }


    private String RIAPRequestPrint(String request) {
        String result = "";
        ClientResource res = new ClientResource(LocalReference.createRiapReference(LocalReference.RIAP_COMPONENT, "/print/"));
        res.accept(MediaType.APPLICATION_JSON);
        res.accept(CharacterSet.UTF_8);
        PrintRequest prequest = new PrintRequest();
        ObjectMapper mapper = new ObjectMapper();
        try {
//            String jsonRequest = "{\"method\":\"PrinterStatus\",\"params\":{\"arguments\":[\"\"],\"printer\":{\"ID\":\"DP150KL\",\"Model\":\"\",\"Params\":{}}},\"id\":\"\"}";
//                                   "{\"method\":\"PrinterStatus\",\"params\":{\"arguments\":[\"\"],\"printer\":{\"ID\":\"DP150KL\",\"Model\":\"\",\"Params\":[]}},\"id\":\"\"}" 
            prequest = mapper.readValue(request, PrintRequest.class);
//            Representation repr = res.post(prequest, MediaType.APPLICATION_JSON);
            Representation repr = res.post(prequest);
            repr.setCharacterSet(CharacterSet.UTF_8);
//            PrintResponse response = res.type("application/json").post(PrintResponse.class, jsonRequest);
            
//            Representation repr = res.get(MediaType.ALL); 
            if (repr != null)
                result = repr.getText();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        res.release();
        return result;
    }
    
    private CompletableFuture<InvocationResult> requestHandler( // request method handler
            List<Object> args, Map<String, Object> kwargs, InvocationDetails details
    ) {
      String result = null;
      LOGGER.info("Request received");
      if ((args.size() == 1) && args.get(0).getClass().equals(String.class)) {
            String encryptedCmd = (String) args.get(0);
            String plainCmd = "";
            try {
                plainCmd = coCipher.decrypt(encryptedCmd);
                LOGGER.fine("Request data:"+plainCmd);
                result = RIAPRequestPrint(plainCmd);
                LOGGER.fine("Request result:"+result);
                result = coCipher.encrypt(result);
            } catch (Exception ex) {
                LOGGER.severe(ex.getMessage());
            }
//            if (plainCmd.equals("\"disconnect\""))
//                mSession.leave();
//            else
//                return CompletableFuture.completedFuture(new InvocationResult(plainCmd));
        } else {
            LOGGER.severe("Invalid request arguments!");
        }
        if (result != null) {
            return CompletableFuture.completedFuture(new InvocationResult(result));
        } else
            return null;
/*        
        List<Object> arr = new ArrayList<>();
        for (int i =0; i < args.size(); i++)
            arr.add(args.get(i));
        return CompletableFuture.completedFuture(new InvocationResult(arr));
*/
    }

    private CompletableFuture<InvocationResult> disconnectHandler( // diconnect 
        List<Object> args, Map<String, Object> kwargs, InvocationDetails details
    ) {
        // Expect 1 argument with encrypted command
        if ((args.size() == 1) && args.get(0).getClass().equals(String.class)) {
            String encryptedCmd = (String) args.get(0);
            String plainCmd = "";
            try {
                plainCmd = coCipher.decrypt(encryptedCmd);
            } catch (Exception ex) {
                LOGGER.severe(ex.getMessage());
            }
            if (plainCmd.equals("disconnect"))
                mSession.leave();
        }
        return null;
//        return CompletableFuture.completedFuture(new InvocationResult(arr));
    }
}
