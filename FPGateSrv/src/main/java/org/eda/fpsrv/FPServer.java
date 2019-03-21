/*
 * Copyright (C) 2015 EDA Ltd.
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
package org.eda.fpsrv;

import TremolZFP.FPcore;
import org.eda.fdevice.FUser;
import org.eda.fdevice.FPDatabase;
import org.eda.fdevice.FPCBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import org.eda.protocol.AbstractFiscalDevice;
import org.eda.protocol.AbstractProtocol;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.service.CorsService;
import org.restlet.ext.crypto.DigestUtils;
import org.restlet.resource.Directory;
import org.restlet.security.SecretVerifier;
import static org.restlet.security.Verifier.RESULT_INVALID;
import static org.restlet.security.Verifier.RESULT_VALID;
import org.restlet.util.Series;
/**
 *
 * @author Dimitar Angelov
 */
public class FPServer extends Application {

    private static Logger loggerProtocolPackage;
    private static Logger loggerProtocol;
    private static Logger loggerProtocolDevice;
    private static Logger loggerTremolFPCore;
    private static Logger loggerFPCBase;

    public static FPServer application;
    public static Component mainComponent;
    public static Server httpServer;
    public static Server httpsServer;
    public ChallengeAuthenticator adminGuard;
    public ChallengeAuthenticator userGuard;
    
    private String version;
    
    protected AppUpdateChecker.VersionInformation VersionInformation;

    /**
     * Get the value of version
     *
     * @return the value of version
     */
    public String getVersion() {
        return version;
    }

    private String buildNumber;

    /**
     * Get the value of buildNumber
     *
     * @return the value of buildNumber
     */
    public String getBuildNumber() {
        return buildNumber;
    }

    public AppUpdateChecker.VersionInformation getVersionInformation() {
        return VersionInformation;
    }
    
    private Properties appProperties;

    /**
     * Get the value of appProperties
     *
     * @return the value of appProperties
     */
    public Properties getAppProperties() {
        return appProperties;
    }
    
    private String appPath;

    /**
     * Get the value of appPath
     *
     * @return the value of appPath
     */
    public String getAppPath() {
        return appPath;
    }
    
    private String appBasePath;

    /**
     * Get the value of appBasePath
     *
     * @return the value of appBasePath
     */
    public String getAppBasePath() {
        return appBasePath;
    }

    
    /**
     * Sets the java library path to the specified path
     * http://fahdshariff.blogspot.bg/2011/08/changing-java-library-path-at-runtime.html
     * 
     * @param path the new library path
     * @throws Exception
     */
    protected static void setLibraryPath(String path) throws Exception {
        System.setProperty("java.library.path", path);

        //set sys_paths to null
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }

    /**
    * Adds the specified path to the java library path
     * http://fahdshariff.blogspot.bg/2011/08/changing-java-library-path-at-runtime.html
    *
    * @param pathToAdd the path to add
    * @throws Exception
    */
    protected static void addLibraryPath(String pathToAdd) throws Exception{
        final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);

        //get array of paths
        final String[] paths = (String[])usrPathsField.get(null);

        //check if the path to add is already present
        for(String path : paths) {
            if(path.equals(pathToAdd)) {
                return;
            }
        }

        //add the new path
        final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
        newPaths[newPaths.length-1] = pathToAdd;
        usrPathsField.set(null, newPaths);
    }    

    /**
     * Detects whether this is a 32-bit JVM.
     * 
     * @return {@code true} if this is a 32-bit JVM.
     */
    protected static boolean shouldLoad32Bit() {
            // This guesses whether we are running 32 or 64 bit Java.
            // This works for Sun and IBM JVMs version 5.0 or later.
            // May need to be adjusted for non-Sun JVMs.

            String bits = System.getProperty("sun.arch.data.model", "?");
            if (bits.equals("32"))
                    return true;
            else if (bits.equals("64"))
                    return false;

            // this works for jRocket
            String arch = System.getProperty("java.vm.name", "?");
            if (arch.toLowerCase().indexOf("64-bit") >= 0)
                    return false;

            return true;
    }
    
    private void initProperties() {
        // Read Version Number
        try {
            version = "N/A";
            buildNumber = "N/A";
            Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try {
                  Manifest manifest = new Manifest(resources.nextElement().openStream());
                  // check that this is your manifest and do what you need or get the next one
                  Attributes attr = manifest.getMainAttributes();
                  String implementationTitle = attr.getValue("Implementation-Title");
                  // 1553075604161 : 25 : FPError Code:-1 Message:getDateTime:Грешка при четене на информация за дата/час (20.03.19 11:51:38)!Unparseable date: "20.03.19 11:51:38"
                  if ((implementationTitle != null) && implementationTitle.equals("FPGateSrv")) {
                    System.out.println(attr.toString());
                    version = attr.getValue("Implementation-Version");
                    if (version == null) version = "N/A";
                    buildNumber = attr.getValue("Implementation-Build-TimeStamp");
                    if (buildNumber == null) buildNumber = "N/A";
                  } 
                } catch (IOException E) {
                  // handle
//                  JOptionPane.showMessageDialog(this, E.getMessage());  
                }
            }        
        } catch (IOException E) {
            E.printStackTrace();
        }
        
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("ServerPort", "8182");
        defaultProperties.setProperty("ServerPortSSL", "8183");
        defaultProperties.setProperty("AdminUser", "fpgadmin");
        defaultProperties.setProperty("AdminPass", DigestUtils.toMd5("Pr1nt"));
        defaultProperties.setProperty("DisableAnonymous", "0");
        defaultProperties.setProperty("UseSSL", "1");
        defaultProperties.setProperty("StartMinimized", "0");
        defaultProperties.setProperty("CheckVersionAtStartup", "1");
        defaultProperties.setProperty("SSLKeyFile", "ssl/fpgate.jks");
        defaultProperties.setProperty("SSLKeyFileType", "JKS"); // jks
        defaultProperties.setProperty("SSLKeyStorePassword", "FPGate"); // 
        defaultProperties.setProperty("SSLKeyPassword", "FPGate"); // 

        defaultProperties.setProperty("LLProtocol", Level.WARNING.getName()); // 
        defaultProperties.setProperty("LLDevice", Level.WARNING.getName()); // 
        defaultProperties.setProperty("LLFPCBase", Level.WARNING.getName()); // 
        
        this.appProperties = new Properties(defaultProperties);
        try {
            File jarPath=new File(FPServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            String propertiesPath=jarPath.getParentFile().getAbsolutePath();
            appPath=jarPath.getParent();
            appBasePath=jarPath.getParentFile().getParent().replace("%20", " ").replace("\\", "/");
            getLogger().log(Level.INFO, "Base Path:{0}", appBasePath);
//            System.out.println(" propertiesPath-"+appBasePath);
            String pfile = appBasePath+"/FPGateSrv.properties";
            if (new File(pfile).exists())
                this.appProperties.load(new FileInputStream(pfile));
            // Set library paths
            addLibraryPath(appBasePath+"/lib");
            if (shouldLoad32Bit()) 
                addLibraryPath(appBasePath+"/lib/x86");
            else
                addLibraryPath(appBasePath+"/lib/x64");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        String libPathProperty = System.getProperty("java.library.path");
//        System.out.println(libPathProperty);  
//        System.out.println(File.pathSeparator);
//        System.out.println("Server Port:"+this.appProperties.getProperty("ServerPort"));
    }
    
    public void updateProperties() {
        try {
            File jarPath=new File(FPServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String pfile = appBasePath+"/FPGateSrv.properties";
            this.appProperties.store(new FileOutputStream(pfile), "EDA FPGate Properties");
            userGuard.setOptional(getAppProperties().getProperty("DisableAnonymous", "1").equals("1")?false:true);
            adjustLogLevels();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public String getDocumentRoot() {
        return getAppBasePath()+"/public_html";
    }
    
    public URI getAdminURL() throws URISyntaxException {
        String serverAddr = getServerAddress();
        if (serverAddr.length() == 0) serverAddr = "localhost";
        String serverPort;
        String scheme;
        if (getUseSSL()) {
            serverPort = Integer.toString(getServerPortSSL());
            if (serverPort.length() == 0) serverPort = "8183";
            scheme =  "https://";
        } else {
            serverPort = Integer.toString(getServerPort());
            if (serverPort.length() == 0) serverPort = "8182";
            scheme =  "http://";
        }
        return new URI(scheme+serverAddr+":"+serverPort+"/admin/");
    }
    public FPServer() {
        setName("Fiscal Printer Gateway");
        setDescription("Fiscal Printer Gateway Application");
        setOwner("EDA Ltd.");
        setAuthor("EDA Software Development Team");
        initProperties();
    }
    
    protected void checkAppVersion(boolean showDetail) {
        VersionInformation = AppUpdateChecker.CheckForNewVersion(version, buildNumber);
        if ((VersionInformation.state == AppUpdateChecker.VersionState.NEW_VERSION) || (VersionInformation.state == AppUpdateChecker.VersionState.NEW_BUILD)){
            AppVersionDlg appv = new AppVersionDlg(VersionInformation);
            appv.setVisible(true);
        } else if (showDetail) {
            JOptionPane.showMessageDialog(null, "There is no new version available.", "Version Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    protected int getServerPort() {
        int port = 8182;
        try {
            port = Integer.parseInt(appProperties.getProperty("ServerPort", Integer.toString(port)));
        } catch (Exception E) {
            E.printStackTrace();
        }
        return port;
    }

    protected int getServerPortSSL() {
        int port = 8183;
        try {
            port = Integer.parseInt(appProperties.getProperty("ServerPortSSL", Integer.toString(port)));
        } catch (Exception E) {
            E.printStackTrace();
        }
        return port;
    }

    protected String getServerAddress() {
        String address = "";
        try {
            address = appProperties.getProperty("ServerAddr", address);
        } catch (Exception E) {
            E.printStackTrace();
        }
        return address;
    }
    
    protected boolean getUseSSL() {
        return appProperties.getProperty("UseSSL", "0").equals("1");
    }
    
    /**
     * Run as a standalone component.
     * 
     * @param args
     *            The optional arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Create an application
        application = new FPServer();

        mainComponent = new Component();
//        System.out.println(mainComponent.getLogService().getLoggerName());
//        mainComponent.getLogService().setLoggerName("com.noelios.web.WebComponent.www");

//        httpServer = new Server(Protocol.HTTP, application.getServerPort());
//        mainComponent.getServers().add(httpServer);
        
        // Enable CORS
        CorsService corsService = new CorsService();         
        corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
        corsService.setAllowedCredentials(true);        
        application.getServices().add(corsService);
//        Engine.getInstance().getRegisteredConverters().add(0, new JacksonConverter());        
//        Engine.getInstance().getRegisteredConverters().remove(0);
        List<ConverterHelper> chl = Engine.getInstance().getRegisteredConverters();
        // Attach the application to the component and start it
        mainComponent.getDefaultHost().attachDefault(application);
        application.initLogs();
        // Create a component
        ControlPanel cp = new ControlPanel();
        cp.setVisible(true);
        if (application.appProperties.getProperty("StartMinimized", "0").equals("1"))
            cp.setState(ControlPanel.ICONIFIED);
        
//        mainComponent.start();
        application.startServer();
        cp.notifyChange();
        // 
        if (application.appProperties.getProperty("CheckVersionAtStartup", "0").equals("1"))
            application.checkAppVersion(false);
    }

    protected void initLogs() {
        try {
            FileHandler logfile = new FileHandler(application.getAppBasePath()+"/logs/"+application.getName()+"-access-%u-%g.log", 5*1024*1024, 10, true){{
//                        setFormatter(new org.restlet.engine.log.AccessLogFormatter());
                        setFormatter(new SimpleFormatter());
                    }};
            // Resltlet framework logger
            mainComponent.getLogger().addHandler(logfile);

            // The handler is over whole package
            loggerProtocolPackage = Logger.getLogger(AbstractFiscalDevice.class.getPackage().getName());
            loggerProtocolPackage.addHandler(logfile);
            // Log levels for protocol package are separated for protocol and device
            loggerProtocol = Logger.getLogger(AbstractProtocol.class.getName());
            loggerProtocolDevice = Logger.getLogger(AbstractFiscalDevice.class.getName());

            // Tremol SDK
            loggerTremolFPCore = Logger.getLogger(FPcore.class.getName());
            loggerTremolFPCore.addHandler(logfile);
            
            // FPCBase 
            loggerFPCBase =  Logger.getLogger(FPCBase.class.getName());
            loggerFPCBase.addHandler(logfile);
            
            adjustLogLevels();
            
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(FPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
//            Logger.getLogger(FPServer.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void adjustLogLevels() {
        loggerProtocol.setLevel(Level.parse(this.appProperties.getProperty("LLProtocol")));
        loggerProtocolDevice.setLevel(Level.parse(this.appProperties.getProperty("LLDevice")));
        loggerTremolFPCore.setLevel(Level.parse(this.appProperties.getProperty("LLDevice")));
        loggerFPCBase.setLevel(Level.parse(this.appProperties.getProperty("LLFPCBase")));
    }
    
    public static void addLogHandler(Handler h) {
        mainComponent.getLogger().addHandler(h);
        loggerProtocolPackage.addHandler(h);
        loggerTremolFPCore.addHandler(h);
        loggerFPCBase.addHandler(h);
    }
    
    public void addSSL() {
        if (!appProperties.getProperty("UseSSL", "0").equals("1")) return;
        String keyFileName = appBasePath+"/"+appProperties.getProperty("SSLKeyFile", "ssl/fpgate.jks");
        File keyFile = new File(keyFileName);
        if (keyFile.exists()) {
            String address = application.getServerAddress(); 
            if (address.length() > 0)
                httpsServer = new Server(Protocol.HTTPS, address, application.getServerPortSSL());
            else
                httpsServer = new Server(Protocol.HTTPS, application.getServerPortSSL());
            mainComponent.getServers().add(httpsServer);
            Series<Parameter> parameters = httpsServer.getContext().getParameters();
            parameters.add("sslContextFactory", "org.restlet.engine.ssl.DefaultSslContextFactory");
            parameters.add("keyStorePath", keyFileName);
            parameters.add("keyStorePassword", appProperties.getProperty("SSLKeyStorePassword", "FPGate"));
            parameters.add("keyPassword", appProperties.getProperty("SSLKeyPassword", "FPGate"));
            parameters.add("keyStoreType", appProperties.getProperty("SSLKeyFileType", "JKS"));
        } else
            getLogger().log(Level.WARNING, "SSL Key file:"+keyFileName+" doesn' exist. SSL is turned off.");
    }
    
    public void startServer() {
        try {
            if (mainComponent.isStarted()) stopServer();
            
//            httpServer = new Server(Protocol.HTTP, application.getServerPort());
            String address = application.getServerAddress(); 
            if (address.length() > 0)
                httpServer = new Server(Protocol.HTTP, address, application.getServerPort());
            else
                httpServer = new Server(Protocol.HTTP, application.getServerPort());
            mainComponent.getServers().add(httpServer);
            addSSL();
            mainComponent.getClients().add(Protocol.FILE);
            mainComponent.start();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
    
    public void stopServer() {
        try {
            mainComponent.stop();
            mainComponent.getServers().clear();
            mainComponent.getClients().clear();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @Override
    public Restlet createInboundRoot() {
        getMetadataService().setDefaultCharacterSet(CharacterSet.UTF_8);
        CharacterSet cs = getMetadataService().getDefaultCharacterSet();
        // Create a router
        Router router = new Router(getContext());

        // Attach the resources to the router
        adminGuard = getAdminGuard();
        adminGuard.setNext(AdminResource.class);
        router.attach("/admin/{ares}/{idr}", adminGuard, Template.MODE_STARTS_WITH);
        router.attach("/admin/{ares}", adminGuard, Template.MODE_STARTS_WITH);
        router.attach("/admin", adminGuard, Template.MODE_STARTS_WITH);
//        Directory directory = new Directory(getContext(),getDocumentRoot());
//        directory.setIndexName("index.html");
        router.attach("/js", new Directory(getContext(),"file:///"+getDocumentRoot()+"/js"));
        router.attach("/css", new Directory(getContext(),"file:///"+getDocumentRoot()+"/css"));

        userGuard = getUserGuard();
        userGuard.setNext(PrintResource.class);
        router.attach("/print/", userGuard);
        router.attach("/", RootResource.class);
        
        
        // Return the root router
        return router;
    }

/*    
    public class AdminVerifier extends LocalVerifier {

        DigestAuthenticator guard = new DigestAuthenticator(getContext(), "FP Gate Admin", "Iechah4I");
        guard.setWrappedVerifier(new AdminVerifier());
        guard.setWrappedAlgorithm(Digest.ALGORITHM_HTTP_DIGEST);
        
        @Override
        public char[] getLocalSecret(String identifier) {
            return DigestUtils.toHttpDigest(identifier, "qwepoi".toCharArray(), "FP Gate Admin").toCharArray();
        }
        
    }    
*/    

    private FPDatabase fpdb;
    
    private FPDatabase getDatabase() throws SQLException {
        if (fpdb == null)
            fpdb = new FPDatabase();
        return  fpdb;
    }
    
    public class AdminVerifier extends SecretVerifier {

        @Override
        public int verify(String identifier, char[] secret) {
            Properties prop = FPServer.application.getAppProperties();
            String pass;
            if (identifier.equals(prop.getProperty("AdminUser"))) {
                pass = prop.getProperty("AdminPass");
            } else {
                try {
                    FPDatabase db = getDatabase();
                    FUser user = db.getUserByName(identifier);
                    if (user != null && user.getValidUser() > 0 && user.getRole() > 0) {
                        pass = user.getUserPass();
                    } else
                        return RESULT_INVALID;
                } catch (SQLException sQLException) {
                    return RESULT_INVALID;
                }
            }    

            String secPass = DigestUtils.toMd5(String.copyValueOf(secret));
            
            if (secPass.equals(pass))
                return RESULT_VALID;
            else
                return RESULT_INVALID;
        }
        
    }
    
    private ChallengeAuthenticator getAdminGuard() {
        // Create a simple password verifier
//        MapVerifier verifier = new MapVerifier();
//        verifier.getLocalSecrets().put("mitko", "qwepoi".toCharArray());

        // Create a guard
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(),
                ChallengeScheme.HTTP_BASIC, "FP Admin");
        guard.setVerifier(new AdminVerifier());
        return guard;
    }

    public class UserVerifier extends SecretVerifier {

        @Override
        public int verify(String identifier, char[] secret) {
            Properties prop = FPServer.application.getAppProperties();
            String pass;
            if (identifier.equals(prop.getProperty("AdminUser"))) {
                pass = prop.getProperty("AdminPass");
            } else if (identifier.equals("mitko")) {
                pass = DigestUtils.toMd5("qwepoi");
            } else {
                try {
                    FPDatabase db = getDatabase();
                    FUser user = db.getUserByName(identifier);
                    if (user != null && user.getValidUser() > 0) {
                        pass = user.getUserPass();
                    } else
                        return RESULT_INVALID;
                } catch (SQLException sQLException) {
                    return RESULT_INVALID;
                }
            }    

            String secPass = DigestUtils.toMd5(String.copyValueOf(secret));
            
            if (secPass.equals(pass))
                return RESULT_VALID;
            else
                return RESULT_INVALID;
        }
        
    }
    
    private ChallengeAuthenticator getUserGuard() {
        // Create a guard
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(),
                ChallengeScheme.HTTP_BASIC, "FP User");
        guard.setVerifier(new UserVerifier());
        guard.setOptional(getAppProperties().getProperty("DisableAnonymous", "1").equals("1")?false:true);
        return guard;
    }
    
    
}