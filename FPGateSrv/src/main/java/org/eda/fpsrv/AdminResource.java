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


import org.eda.fdevice.StrTable;
import org.eda.fdevice.FPPropertyRule;
import org.eda.fdevice.FPPropertyGroup;
import org.eda.fdevice.FUser;
import org.eda.fdevice.FPrinter;
import org.eda.fdevice.FPProperty;
import org.eda.fdevice.FPDatabase;
import org.eda.fdevice.FPCBase;
import org.eda.fdevice.FPCBaseClassList;
import org.eda.fdevice.FPParams;
import java.util.List;
import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Post;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import org.restlet.ext.crypto.DigestUtils;
import freemarker.template.*;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eda.fdevice.FPPrinterPool;
import org.restlet.data.Status;

/**
 *
 * @author Dimitar Angelov
 */
public class AdminResource extends ServerResource {
    private FPDatabase db;
    
    protected HashMap responseData = new HashMap();
    
    protected class ServerSettings {
        public String IPAddr = ""; // "ServerAddr", ""
        public int UseHTTP = 1; //"UseHTTP", "1"
        public int HTTPPort = 8182; // "ServerPort", "8182"
        public int UseHTTPS = 1; //"UseSSL", "1"
        public int HTTPSPort = 8183; // "ServerPortSSL", "8183"
        public String AdminUser = ""; // "AdminUser", "fpgadmin"
        public String AdminPass = ""; // "AdminPass", DigestUtils.toMd5("Pr1nt")
        public String AdminPass2 = ""; // "AdminPass", DigestUtils.toMd5("Pr1nt")
        public int DisableAnonymous = 0; // "DisableAnonymous", "0"
        public int PoolEnabled = 1; // "PoolEnabled", "1"
        public int PoolDeadtime = 5; // "PoolDeadtime", "30"); // 5min
        public int ZFPLabServerAutoStart = 0; // "ZFPLabServerAutoStart", "0"

        public int CoAutoStart = 0; // "CoAutoStart", "0"
        public String CoURL = ""; // "CoURL", ""
        public String CoUser = ""; // "CoUser", ""
        public String CoPass = ""; // CoPass", ""
        public String CoURL_1 = ""; // "CoURL_1", ""
        public String CoUser_1 = ""; // "CoUser_1", ""
        public String CoPass_1 = ""; // CoPass_1", ""
        public String CoURL_2 = ""; // "CoURL_2", ""
        public String CoUser_2 = ""; // "CoUser_2", ""
        public String CoPass_2 = ""; // CoPass_2", ""
        public String CoURL_3 = ""; // "CoURL_3", ""
        public String CoUser_3 = ""; // "CoUser_3", ""
        public String CoPass_3 = ""; // CoPass_3", ""
        
        public String LLProtocol = ""; // "LLProtocol", Level.WARNING.getName()
        public String LLDevice = ""; // "LLDevice", Level.WARNING.getName()
        public String LLFPCBase = ""; // "LLFPCBase", Level.WARNING.getName()
        public String LLCBIOService = ""; // "LLCBIOService", Level.WARNING.getName()
        
        public ArrayList<String> LogLevelsList;
        
        public ServerSettings() {
            // Read properties from application
            Properties prop = FPServer.application.getAppProperties();
            IPAddr = prop.getProperty("ServerAddr", "");
            UseHTTP = prop.getProperty("UseHTTP", "1").equals("1")?1:0;
            try {
                HTTPPort = Integer.parseInt(prop.getProperty("ServerPort", "8182"));
            } catch(Exception ex) {
            }
            UseHTTPS = prop.getProperty("UseSSL", "1").equals("1")?1:0;
            try {
                HTTPSPort = Integer.parseInt(prop.getProperty("ServerPortSSL", "8183"));
            } catch(Exception ex) {
            }
            AdminUser = prop.getProperty("AdminUser");
            DisableAnonymous = prop.getProperty("DisableAnonymous", "0").equals("1")?1:0;
            PoolEnabled = prop.getProperty("PoolEnabled", "1").equals("1")?1:0;
            try {
                PoolDeadtime = Integer.parseInt(prop.getProperty("PoolDeadtime", "30"));
            } catch (Exception ex) {
            }    
            ZFPLabServerAutoStart = prop.getProperty("ZFPLabServerAutoStart", "0").equals("1")?1:0;
            CoAutoStart = prop.getProperty("CoAutoStart", "0").equals("1")?1:0;
            CoURL = prop.getProperty("CoURL", "");
            CoUser = prop.getProperty("CoUser", "");
            CoURL_1 = prop.getProperty("CoURL_1", "");
            CoUser_1 = prop.getProperty("CoUser_1", "");
            CoURL_2 = prop.getProperty("CoURL_2", "");
            CoUser_2 = prop.getProperty("CoUser_2", "");
            CoURL_3 = prop.getProperty("CoURL_3", "");
            CoUser_3 = prop.getProperty("CoUser_3", "");
            
            LLProtocol = prop.getProperty("LLProtocol", Level.WARNING.getName());
            LLDevice = prop.getProperty("LLDevice", Level.WARNING.getName());
            LLFPCBase = prop.getProperty("LLFPCBase", Level.WARNING.getName());
            LLCBIOService = prop.getProperty("LLCBIOService", Level.WARNING.getName());
            
            LogLevelsList = new ArrayList<String>(){
                {
                    add(Level.OFF.getName());
                    add(Level.SEVERE.getName());
                    add(Level.WARNING.getName());
                    add(Level.INFO.getName());
                    add(Level.CONFIG.getName());
                    add(Level.FINE.getName());
                    add(Level.FINEST.getName());
                    add(Level.ALL.getName());
                }
            };
            
        }
        
        public void assign(ServerSettings ss) {
            
        }
        
        public void update(boolean save) {
            Properties prop = FPServer.application.getAppProperties();
            prop.setProperty("ServerAddr", IPAddr);
            prop.setProperty("UseHTTP", (UseHTTP==1)?"1":"0");
            prop.setProperty("ServerPort", Integer.toString(HTTPPort));
            prop.setProperty("UseSSL", (UseHTTPS==1)?"1":"0");
            prop.setProperty("ServerPortSSL", Integer.toString(HTTPSPort));
            prop.setProperty("AdminUser", AdminUser);
            if (!AdminPass.isEmpty() && AdminPass.equals(AdminPass2)) {
                prop.setProperty("AdminPass", DigestUtils.toMd5(AdminPass));
            }    
            prop.setProperty("DisableAnonymous", (DisableAnonymous == 1)?"1":"0");
            prop.setProperty("LLDevice", LLDevice);
            prop.setProperty("LLProtocol", LLProtocol);
            prop.setProperty("LLFPCBase", LLFPCBase);
            prop.setProperty("LLCBIOService", LLCBIOService);
            prop.setProperty("PoolEnabled", (PoolEnabled == 1)?"1":"0");
            prop.setProperty("ZFPLabServerAutoStart", (ZFPLabServerAutoStart == 1)?"1":"0");
            prop.setProperty("PoolDeadtime", Integer.toString(PoolDeadtime));

            prop.setProperty("CoAutoStart", (CoAutoStart == 1)?"1":"0");
            // Colibri ERP 1
            prop.setProperty("CoURL", CoURL);
            prop.setProperty("CoUser", CoUser);
            if (CoPass.length() > 0) {
                // TODO: Encrypt
                prop.setProperty("CoPass", Base64.getEncoder().encodeToString(CoPass.getBytes()));
            }
            // Colibri ERP 2
            prop.setProperty("CoURL_1", CoURL_1);
            prop.setProperty("CoUser_1", CoUser_1);
            if (CoPass_1.length() > 0) {
                // TODO: Encrypt
                prop.setProperty("CoPass_1", Base64.getEncoder().encodeToString(CoPass_1.getBytes()));
            }
            // Colibri ERP 3
            prop.setProperty("CoURL_2", CoURL_2);
            prop.setProperty("CoUser_2", CoUser_2);
            if (CoPass_2.length() > 0) {
                // TODO: Encrypt
                prop.setProperty("CoPass_2", Base64.getEncoder().encodeToString(CoPass_2.getBytes()));
            }
            // Colibri ERP 4
            prop.setProperty("CoURL_3", CoURL_3);
            prop.setProperty("CoUser_3", CoUser_3);
            if (CoPass_3.length() > 0) {
                // TODO: Encrypt
                prop.setProperty("CoPass_3", Base64.getEncoder().encodeToString(CoPass_3.getBytes()));
            }
            if (save) {
                FPServer.application.updateProperties();
                responseMessage("Server settings was saved.");
            } else {
                responseMessage("Server settings was updated and will be valid to next restart.");
            }
        }
    }

    public AdminResource() {
        
        initResponseData();

        try {
            db = new FPDatabase();
        } catch (SQLException ex) {
            responseError(ex.getMessage());
        }
        
    }
    
    private void initResponseData() {
        responseData.put("AppTitle", "FPGate Admin");
        responseData.put("AppVersion", FPServer.application.getVersion());
        responseData.put("AppBuild", FPServer.application.getBuildNumber());
        responseData.put("ContextTitle", "");
        responseData.put("URL_HOME", "/admin/");
        responseData.put("URL_PRINTERS", "/admin/printers/");
        responseData.put("URL_USERS", "/admin/users/");
        responseData.put("URL_STATUS", "/admin/status/");
        responseData.put("URL_PRINTERTEST", "/admin/printer-test/");
        responseData.put("URL_PRINT", "/print/");
        responseData.put("URL_SETTINGS", "/admin/settings/");
        responseData.put("errors", new ArrayList<String>());
        responseData.put("warnings", new ArrayList<String>());
        responseData.put("messages", new ArrayList<String>());
        
        responseData.put("ServerUptime", FPServer.application.getServerUptime());
        responseData.put("PrinterPoolEnabled", FPPrinterPool.isPoolEnabled()?1:0);
        responseData.put("PrinterPoolSize", Integer.toString(FPPrinterPool.getPoolSize()));
        responseData.put("isCBIOServiceStarted", FPServer.application.isCBIOServiceStarted()?1:0);
        
        int[] CBIOServiveStarted = new int[FPServer.application.getCBIO_SERVICE_COUNT()];
        String[] CBIOServiveState = new String[FPServer.application.getCBIO_SERVICE_COUNT()];
        for (int i = 0; i < CBIOServiveStarted.length; i++) {
            CBIOServiveStarted[i] = FPServer.application.isCBIOServiceStarted(i)?1:0;
            CBIOServiveState[i] = FPServer.application.getCBIOServiceStatus(i).toString();
        }    
        responseData.put("CBIOServiceStarted", CBIOServiveStarted);
        responseData.put("CBIOServiveState", CBIOServiveState);
        responseData.put("ZFPLabServerStarted", ZFPLabServer.isStarted()?1:0);
    }
    
    protected void responseError(String msg) {
        if (msg != null && msg.length() > 0)
            ((ArrayList<String>)responseData.get("errors")).add(msg);
    }
    protected void responseWarning(String msg) {
        if (msg != null && msg.length() > 0)
            ((ArrayList<String>)responseData.get("warnings")).add(msg);
    }
    protected void responseMessage(String msg) {
        if (msg != null && msg.length() > 0)
            ((ArrayList<String>)responseData.get("messages")).add(msg);
    }

    protected Template getTemplate(String tplName) {
        Template tpl = null;
        try {
//            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            Configuration cfg = new Configuration();
            String ftplPath = FPServer.application.getDocumentRoot()+"/ftl";
            cfg.setDirectoryForTemplateLoading(new File(ftplPath));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            tpl = cfg.getTemplate(tplName+".ftl");
        } catch (Exception ex) {
            responseError(ex.getMessage());
        }    
        return tpl;
    }

    protected String processTemplate(String tplName) {
        Template tpl = getTemplate(tplName);
        Writer out = new StringWriter();
        if (tpl != null) {
            try {
                responseData.put("AppTitleFull", 
                    responseData.get("AppTitle")
                    +((((String)responseData.get("ContextTitle")).length() > 0)?" - ":"")
                    +responseData.get("ContextTitle")
                );
                tpl.process(responseData, out);
            } catch (TemplateException ex) {
                getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
                getLogger().log(Level.SEVERE, ex.getMessage(), ex);
            } catch (IOException ex) {
                getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
                getLogger().log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            getResponse().setStatus(Status.SERVER_ERROR_NOT_IMPLEMENTED, "Can't find template "+tplName);
        }
        return out.toString();
    }
    

    @Get("html")
    public String handleGet() throws IOException {
        String res = "";
        String ares = "";
        String act = "";
        String id = "";
        try {
            ares = getRequestAttributes().getOrDefault("ares", "").toString();
            act = getRequestAttributes().getOrDefault("idr", "").toString();
            id = getQueryValue("id");
            if (id == null) id = "";
        } catch (Exception e) {
            responseError(e.getMessage());
        }
        String response = "";
        switch (ares) {
            case "printers":
                switch (act) {
                    case "":
                        response = displayPrinters();
                        break;
                    case "delete":
                        deletePrinter(id);
                        getResponse().redirectSeeOther("/admin/printers/");
                        break;
                    case "new":
                    case "edit":
                        response = editPrinter(id);
                        break;
                }
                break;
            case "users":
                switch (act) {
                    case "":
                        response = displayUsers();
                        break;
                    case "delete":
                        deleteUser(id);
                        getResponse().redirectSeeOther("/admin/users/");
                        break;
                    case "new":
                    case "edit":
                        response = editUser(id);
                        break;
                }
                break;
            case "status":
                response = displayStatus();
                break;
            case "printer-test":
                response = displayPrinterTestPage(id);
                break;
            case "settings":
                switch (act) {
                    case "":
                        response = displayServerSettings();
                        break;
                    case "reset-defaults":
                        // reset to defaults 
                        FPServer.application.resetProperties(false);
                        response = displayServerSettings();
                        break;
                    case "restart":
                        // response = updateServerSettings(id);
                        break;
                    default :
                        response = displayServerSettings();
                }
                break;
            default:
                response = displayMain();
        }
        
        return response;
    }

    /**
     *
     * @param postedForm
     * 
     */
    @Post("form:html")
    public String handlePost(org.restlet.data.Form postedForm) {   
        String res = "";
        String ares = "";
        String act = "";
        String id = "";
        try {
			Map<String, Object> attrs = getRequestAttributes();
			if (attrs.containsKey("ares")) {
				ares = getRequestAttributes().get("ares").toString();
			}	
			if (attrs.containsKey("idr")) {
	            act = getRequestAttributes().get("idr").toString();
			}	
            id = getQueryValue("id");
        } catch (Exception e) {
            responseError(e.getMessage());
        }
        String response = "";
        switch (ares) {
            case "printers":
                FPrinter printer;
                printer = getPrinter(postedForm);
                act = postedForm.getFirstValue("act", "");
                if (act.equals("update")) {
                    try {
                        if (printer.getIdPrinter() > 0) {
                            // update
                            db.updatePrinter(printer);
                        } else {
                            // add
                            db.addPrinter(printer);
                        }
                        this.getResponse().redirectSeeOther("/admin/printers/edit?id="+Integer.toString(printer.getIdPrinter()));
                    } catch (SQLException ex) {
                        responseError(ex.getMessage());
                        response = displayPrinter(printer);
                        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    }
                } else if (act.equals("cancel")) {
                    this.getResponse().redirectSeeOther("/admin/printers/edit?id="+Integer.toString(printer.getIdPrinter()));
                } else {
                    response = displayPrinter(printer);
                }
                break;
            case "users":
                FUser user;
                user = getUser(postedForm);
                act = postedForm.getFirstValue("act");
                if (act.equals("update")) {
                    try {
                        user.setUserPass(null);
                        String userPass = postedForm.getFirstValue("UserPass");
                        String userPass2 = postedForm.getFirstValue("UserPass2");
                        if (userPass.length() > 0) {
                            if (userPass.equals(userPass2)) {
                                user.setUserPass(DigestUtils.toMd5(userPass));
                            } else {
                                throw new Exception("Passwords doesn't match!");
                            }
                        }
                        if (user.getIdUser() > 0) {
                            // update
                            db.updateUser(user);
                        } else {
                            // add
                            db.addUser(user);
                        }
                        this.getResponse().redirectSeeOther("/admin/users/edit?id="+Integer.toString(user.getIdUser()));
                    } catch (SQLException ex) {
                        responseError(ex.getMessage());
                        response = displayUser(user);
                        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    } catch (Exception ex) {
                        responseError(ex.getMessage());
                        response = displayUser(user);
                        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    }
                } else if (act.equals("cancel")) {
                    this.getResponse().redirectSeeOther("/admin/users/edit?id="+Integer.toString(user.getIdUser()));
                } else {
                    response = displayUser(user);
                }
                break;
            case "printer-test":
                response = displayPrinterTestPage(postedForm.getFirstValue("idPrinter", ""));
                break;
            case "settings":
                act = postedForm.getFirstValue("act", "");
                switch (act) {
                    case "":
                        response = displayServerSettings();
                        break;
                    case "reset":
                        // reset to defaults 
                        response = displayServerSettings();
                        break;
                    case "update":
                        response = updateServerSettings(postedForm, false);
                        break;
                    case "update-save":
                        response = updateServerSettings(postedForm, true);
                        break;
                    case "clear-pool" :
                        FPPrinterPool.clear();
                        responseMessage("Printer pool was cleared.");
                        response = displayServerSettings();
                        break;
                    case "start-coerp" :
                        FPServer.application.startCBIOService();
                        responseMessage("Colibri ERP link service was started.");
                        response = displayServerSettings();
                        break;
                    case "stop-coerp" :
                        FPServer.application.stopCBIOService();
                        responseMessage("Colibri ERP link service was stopped.");
                        response = displayServerSettings();
                        break;
                    case "start-zfplabserver" :
                        try {
                            ZFPLabServer.start();
                            responseMessage("ZFPLabServer was started.");
                        } catch (Exception ex) {
                            responseError(ex.getMessage());
                        }
                        response = displayServerSettings();
                        break;

                    case "stop-zfplabserver" :
                        try {
                            ZFPLabServer.stop();
                            responseMessage("ZFPLabServer was sopped.");
                        } catch (Exception ex) {
                            responseError(ex.getMessage());
                        }
                        response = displayServerSettings();
                        break;
                    case "restart":
                        // response = updateServerSettings(id);
                        response = displayServerSettings();
                        break;
                    default :    
                        response = displayServerSettings();
                }
                break;
            default:
                response = displayMain();
        }
        // in case of empty response 
        if (response.length() == 0) {
            if (getResponse().getStatus() != Status.REDIRECTION_SEE_OTHER)
                response = displayMain();
        }
        return response;
    }
    
    public String displayStatus() {
        responseData.put("ContextTitle", "Status");
        return processTemplate("status");
    }

    public String displayPrinterTestPage(String idPrinter) {
        try {
            responseData.put("ContextTitle", "Printer Test");
            responseData.put("idPrinter", idPrinter);
            List<FPrinter> printers = new ArrayList();
            printers = db.getPrinters();
            
            responseData.put("printers", new LinkedHashMap());
            for (final FPrinter printer : printers) 
                if (printer.getIsActive() > 0) {
                    ((LinkedHashMap)responseData.get("printers")).put(
                       Integer.toString(printer.getIdPrinter())     
                       ,new LinkedHashMap<String, String>(){{
                           put("idPrinter", Integer.toString(printer.getIdPrinter()));
                           put("RefID", printer.getRefId());
                           put("Name", printer.getName());
                           put("ModelID", printer.getModelID());
                           put("Description", printer.getDescription());
                           put("Location", printer.getLocation());
                           put("IsActive", Integer.toString(printer.getIsActive()));
                       }}
                    );
                }
        } catch (Exception ex) {
            responseError(ex.getMessage());
            getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }    
        return processTemplate("printer-test");
    }
    
    
    public String displayMain() {
        try {
            //        responseError("Test error!");
//        responseWarning("Test warning!");
//        responseMessage("Test message!");
            responseData.put("PrinterCountAll", db.getPrinterCountAll());
            responseData.put("PrinterCountActive", db.getPrinterCountActive());
            responseData.put("UserCountAll", db.getUserCountAll());
            responseData.put("UserCountValid", db.getUserCountValid());
        } catch (SQLException ex) {
            responseError(ex.getMessage());
            getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return processTemplate("index");
    }
    
    public String displayPrinters() {
        try {
            responseData.put("ContextTitle", "Printers");
            List<FPrinter> printers = new ArrayList();
            printers = db.getPrinters();
            
            responseData.put("printers", new ArrayList());
            int rowCounter = 1;
            for (final FPrinter printer : printers) {
                ((ArrayList)responseData.get("printers")).add(
                   new LinkedHashMap<String, String>(){{
                       put("idPrinter", Integer.toString(printer.getIdPrinter()));
                       put("RefID", printer.getRefId());
                       put("Name", printer.getName());
                       put("ModelID", printer.getModelID());
                       put("Description", printer.getDescription());
                       put("Location", printer.getLocation());
                       put("IsActive", Integer.toString(printer.getIsActive()));
                   }}
                );
                rowCounter++;
            }
        } catch (Exception ex) {
            responseError(ex.getMessage());
            getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }    
        return processTemplate("printers");
    }
    
    public FPrinter getPrinter(org.restlet.data.Form printerForm) {
        FPrinter printer = null;
        String idPrinter = printerForm.getFirstValue("idPrinter");
        if (idPrinter != null && Integer.parseInt(idPrinter) > 0) {
            // update
            try {
                printer = db.getPrinter(Integer.parseInt(idPrinter));
                if (printer != null) {
                    String pval;
                    if ((pval = printerForm.getFirstValue("RefID")) != null)
                        printer.setRefId(pval);
                    if ((pval = printerForm.getFirstValue("Name")) != null)
                        printer.setName(pval);
                    if ((pval = printerForm.getFirstValue("ModelID")) != null)
                        printer.setModelID(pval);
                    if ((pval = printerForm.getFirstValue("Description")) != null)
                        printer.setDescription(pval);
                    if ((pval = printerForm.getFirstValue("Location")) != null)
                        printer.setLocation(pval);
                    pval = printerForm.getFirstValue("IsActive");
                    if (pval == null) pval = "0";
                    if (pval.equals("0") || pval.equals("1"))
                        printer.setIsActive(Integer.parseInt(pval));
                    // update Properties
                    StrTable propList = printer.getPropertyList();
                    for(String pid : propList.keySet()) 
                        if ((pval = printerForm.getFirstValue("Property["+pid+"]")) != null)
                            propList.put(pid, pval);
                    printer.setPropertyList(propList);
                }    
//                this.getResponse().redirectSeeOther("/admin/printers/");
            } catch (SQLException ex) {
                responseError(ex.getMessage());
            }
        } else {
            // add
            printer = new FPrinter();
            String pval;
            if ((pval = printerForm.getFirstValue("RefID")) != null)
                printer.setRefId(pval);
            if ((pval = printerForm.getFirstValue("Name")) != null)
                printer.setName(pval);
            if ((pval = printerForm.getFirstValue("ModelID")) != null)
                printer.setModelID(pval);
            if ((pval = printerForm.getFirstValue("Description")) != null)
                printer.setDescription(pval);
            if ((pval = printerForm.getFirstValue("Location")) != null)
                printer.setLocation(pval);
            if ((pval = printerForm.getFirstValue("IsActive")) != null)
                printer.setIsActive(Integer.parseInt(pval));
            // update Properties
            StrTable propList = printer.getPropertyList();
            for(String pid : propList.keySet()) 
                if ((pval = printerForm.getFirstValue("Property["+pid+"]")) != null)
                    propList.put(pid, pval);
            printer.setPropertyList(propList);
        }
       return printer; 
    }
    
    public void editPrinter(org.restlet.data.Form printerForm) {
        displayPrinter(getPrinter(printerForm));
    }
    
    public String editPrinter(String idPrinter) {
        FPrinter printer = null;
        if (idPrinter.length() == 0) idPrinter = "0";
        try {
            int idP = Integer.parseInt(idPrinter);
            if (idP > 0)
                printer = db.getPrinter(idP);
        } catch (SQLException ex) {
            responseError(ex.getMessage());
        }
        if (printer == null)
            printer = new FPrinter();
        return displayPrinter(printer);
    }

    
    public String displayPrinter(final FPrinter printer) {
        try {
            responseData.put("ContextTitle", (printer.getIdPrinter() > 0)?"Edit printer":"New Printer");
            responseData.put("printer", new HashMap(){{
                put("idPrinter", Integer.toString(printer.getIdPrinter()));
                put("RefID", printer.getRefId());
                put("ModelID", printer.getModelID());
                put("ModelDescription", printer.getModelDesctiption());
                put("Name", printer.getName());
                put("Description", printer.getDescription());
                put("Location", printer.getLocation());
                put("IsActive", Integer.toString(printer.getIsActive()));
            }});
            // List of printer classes
            FPCBaseClassList FPModels = FPCBase.getDerivedClasses();
            responseData.put("FPCList", FPModels.keySet());
            responseData.put("ParamGroups", new ArrayList(){{
                try {
                    FPParams printerParams = printer.getParams();
                    for (final FPPropertyGroup propGroup : printerParams.getGroups().values()) {
                        add(new HashMap(){{
                            put("ID", propGroup.getID());
                            put("Name", propGroup.getName());
                            put("Properties", new ArrayList(){{
                                for(final FPProperty prop : propGroup.getProperties().values()) {
                                    add(new HashMap(){{
                                        put("ID", prop.getID());
                                        put("Name", prop.getName());
                                        put("Description", prop.getDescription());
                                        put("DefaultValue", prop.getDefaultValue().toString());
                                        put("Value", prop.getValue().toString());
                                        put("ClassName", prop.getPropertyClass().getCanonicalName());
                                        put("IsNumber", prop.isNumberClass()?"1":"0");
                                        FPPropertyRule rule = prop.getRule();
                                        put("RuleApply", (rule != null)?"1":"0");
                                        if (rule != null) {
                                            put("RuleMinDefined", (rule.getMin() != null)?"1":"0");
                                            put("RuleMin", rule.getMin());
                                            put("RuleMaxDefined", (rule.getMax() != null)?"1":"0");
                                            put("RuleMax", rule.getMax());
                                            put("RuleStrict", rule.isStrict()?"1":"0");
                                            put("RuleListDefined", (rule.getList() != null)?"1":"0");
                                            put("RuleList", rule.getStringList());
                                        }
                                    }});    
                                }
                            }});
                        }});
                    }
                } catch (Exception ex) {
                    responseError(ex.getMessage());
                    getLogger().log(Level.SEVERE, ex.getMessage(), ex);
                }
            }});
        } catch (Exception ex) {
            responseError(ex.getMessage());
            getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }    
        return processTemplate("printer-edit");
    }
    
    
    
    public void deletePrinter(String idPrinter) {
        if (idPrinter != null) {
            try {
                db.deletePrinter(Integer.parseInt(idPrinter));
            } catch (SQLException ex) {
                responseError(ex.getMessage());
            }
        }    
    }


    public String displayUsers() {
        try {
            responseData.put("ContextTitle", "Users");
            List<FUser> users = new ArrayList();
            users = db.getUsers();
            
            responseData.put("users", new ArrayList());
            int rowCounter = 1;
            for (final FUser user : users) {
                ((ArrayList)responseData.get("users")).add(
                   new HashMap(){{
                       put("idUser", Integer.toString(user.getIdUser()));
                       put("UserName", user.getUserName());
                       put("FullName", user.getFullName());
                       put("Role", Integer.toString(user.getRole()));
                       put("ValidUser", Integer.toString(user.getValidUser()));
                   }}
                );
                rowCounter++;
            }
        } catch (Exception ex) {
            responseError(ex.getMessage());
        }    
        return processTemplate("users");
    }
    
    public FUser getUser(org.restlet.data.Form userForm) {
        FUser user = null;
        String idUser = userForm.getFirstValue("idUser");
        if (idUser != null && Integer.parseInt(idUser) > 0) {
            // update
            try {
                user = db.getUser(Integer.parseInt(idUser));
                if (user != null) {
                    String pval;
                    if ((pval = userForm.getFirstValue("UserName")) != null)
                        user.setUserName(pval);
                    if ((pval = userForm.getFirstValue("FullName")) != null)
                        user.setFullName(pval);
//                    if ((pval = userForm.getFirstValue("UserPass")) != null)
//                        user.setUserPass(pval);
                    pval = userForm.getFirstValue("ValidUser");
                    if (pval == null) pval = "0";
                    if (pval.equals("0") || pval.equals("1"))
                        user.setValidUser(Integer.parseInt(pval));
                    pval = userForm.getFirstValue("Role");
                    if (pval == null) pval = "0";
                    if (pval.equals("0") || pval.equals("1"))
                        user.setRole(Integer.parseInt(pval));
                }    
//                this.getResponse().redirectSeeOther("/admin/printers/");
            } catch (SQLException ex) {
                responseError(ex.getMessage());
            }
        } else {
            // add
            user = new FUser();
            String pval;
            if ((pval = userForm.getFirstValue("UserName")) != null)
                user.setUserName(pval);
            if ((pval = userForm.getFirstValue("FullName")) != null)
                user.setFullName(pval);
//                    if ((pval = userForm.getFirstValue("UserPass")) != null)
//                        user.setUserPass(pval);
            pval = userForm.getFirstValue("ValidUser");
            if (pval == null) pval = "0";
            if (pval.equals("0") || pval.equals("1"))
                user.setValidUser(Integer.parseInt(pval));
            pval = userForm.getFirstValue("Role");
            if (pval == null) pval = "0";
            if (pval.equals("0") || pval.equals("1"))
                user.setRole(Integer.parseInt(pval));
        }
       return user; 
    }

    public void deleteUser(String idUser) {
        if (idUser != null) {
            try {
                db.deleteUser(Integer.parseInt(idUser));
            } catch (SQLException ex) {
              responseError(ex.getMessage());
            }
        } else
            responseError("Missing idUser!");
    }

    
    public String editUser(org.restlet.data.Form userForm) {
        return displayUser(getUser(userForm));
    }
    
    public String editUser(String idUser) {
        FUser user = null;
        if (idUser.length() == 0) idUser = "0";
        try {
            int idU = Integer.parseInt(idUser);
            if (idU > 0)
                user = db.getUser(idU);
        } catch (SQLException ex) {
              responseError(ex.getMessage());
        }
        if (user == null)
            user = new FUser();
        return displayUser(user);
    }

    public String displayUser(final FUser user) {
        try {
            responseData.put("ContextTitle", (user.getIdUser() > 0)?"Edit user":"New user");
            responseData.put("user", new HashMap(){{
                put("idUser", Integer.toString(user.getIdUser()));
                put("UserName", user.getUserName());
                put("FullName", user.getFullName());
                put("ValidUser", Integer.toString(user.getValidUser()));
                put("Role", Integer.toString(user.getRole()));
            }});
        } catch (Exception ex) {
            responseError(ex.getMessage());
        }    
        return processTemplate("user-edit");
    } // displayUser

    protected String displayServerSettings() {
        return displayServerSettings(new ServerSettings());
    }
    
    protected String displayServerSettings(ServerSettings ss) {
        try {
            responseData.put("ContextTitle", "Settings");
            responseData.put("ss", new HashMap(){{
                put("IPAddr", ss.IPAddr);
                put("UseHTTP", ss.UseHTTP);
                put("HTTPPort", Integer.toString(ss.HTTPPort));
                put("UseHTTPS", ss.UseHTTPS);
                put("HTTPSPort", Integer.toString(ss.HTTPSPort));
                put("AdminUser", ss.AdminUser);
                put("DisableAnonymous", ss.DisableAnonymous);
                put("PoolEnabled", ss.PoolEnabled);
                put("PoolDeadtime", ss.PoolDeadtime);
                put("ZFPLabServerAutoStart", ss.ZFPLabServerAutoStart);
                put("CoAutoStart", ss.CoAutoStart);
                put("CoURL", ss.CoURL);
                put("CoUser", ss.CoUser);
                put("CoURL_1", ss.CoURL_1);
                put("CoUser_1", ss.CoUser_1);
                put("CoURL_2", ss.CoURL_2);
                put("CoUser_2", ss.CoUser_2);
                put("CoURL_3", ss.CoURL_1);
                put("CoUser_3", ss.CoUser_1);
                put("LLProtocol", ss.LLProtocol);
                put("LLDevice", ss.LLDevice);
                put("LLFPCBase", ss.LLFPCBase);
                put("LLCBIOService", ss.LLCBIOService);
                put("LogLevelsList", ss.LogLevelsList);
            }});
        } catch (Exception ex) {
            responseError(ex.getMessage());
            getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }    
        return processTemplate("settings");
    }
    
    
    protected List<String> getListOfLocalIPS() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if (inetAddress instanceof Inet4Address)
                        list.add(inetAddress.getHostAddress());
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(AdminResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    protected String updateServerSettings(org.restlet.data.Form setForm, boolean save) {
        ServerSettings ss = new ServerSettings();
        do {
            try {
                ss.IPAddr = setForm.getFirstValue("ss.IPAddr", ss.IPAddr);
                if (!ss.IPAddr.isEmpty()) {
                    List<String> ipList = getListOfLocalIPS();
                    if (!ipList.contains(ss.IPAddr)) {
                        throw new Exception("Ivalid IP address. Valid interface adressess are: "+ipList.toString());
                    }
                }
                ss.UseHTTP = setForm.getFirstValue("ss.UseHTTP", "0").equals("1")?1:0;
                try {
                    ss.HTTPPort = Integer.parseInt(setForm.getFirstValue("ss.HTTPPort", Integer.toString(ss.HTTPPort)));
                } catch (Exception ex) {
                    throw new Exception("Ivalid integer value for HTTP Port! "+ex.getMessage());
                }
                ss.UseHTTPS = setForm.getFirstValue("ss.UseHTTPS", "0").equals("1")?1:0;
                try {
                    ss.HTTPSPort = Integer.parseInt(setForm.getFirstValue("ss.HTTPSPort", Integer.toString(ss.HTTPSPort)));
                } catch (Exception ex) {
                    throw new Exception("Ivalid integer value for HTTPS Port! "+ex.getMessage());
                }
                ss.AdminUser = setForm.getFirstValue("ss.AdminUser", ss.AdminUser);
                ss.AdminPass = setForm.getFirstValue("ss.AdminPass", "");
                ss.AdminPass2 = setForm.getFirstValue("ss.AdminPass2", "");
                if (!ss.AdminPass.isEmpty() && !ss.AdminPass2.equals(ss.AdminPass)) {
                    throw new Exception("Admin passwords doesn't match!");
                }
                ss.PoolEnabled = setForm.getFirstValue("ss.PoolEnabled", (ss.PoolEnabled == 1)?"1":"0").equals("1")?1:0;
                try {
                    ss.PoolDeadtime = Integer.parseInt(setForm.getFirstValue("ss.PoolDeadtime", Integer.toString(ss.PoolDeadtime)));
                } catch (Exception ex) {
                    throw new Exception("Ivalid integer value for Pool Deadtime! "+ex.getMessage());
                }
                ss.CoAutoStart = setForm.getFirstValue("ss.CoAutoStart", "0").equals("1")?1:0;
                ss.CoURL = setForm.getFirstValue("ss.CoURL", ss.CoURL);
                ss.CoUser = setForm.getFirstValue("ss.CoUser", ss.CoUser);
                ss.CoPass = setForm.getFirstValue("ss.CoPass", ss.CoPass);
                ss.CoURL_1 = setForm.getFirstValue("ss.CoURL_1", ss.CoURL_1);
                ss.CoUser_1 = setForm.getFirstValue("ss.CoUser_1", ss.CoUser_1);
                ss.CoPass_1 = setForm.getFirstValue("ss.CoPass_1", ss.CoPass_1);
                ss.CoURL_2 = setForm.getFirstValue("ss.CoURL_2", ss.CoURL_2);
                ss.CoUser_2 = setForm.getFirstValue("ss.CoUser_2", ss.CoUser_2);
                ss.CoPass_2 = setForm.getFirstValue("ss.CoPass_2", ss.CoPass_2);
                ss.CoURL_3 = setForm.getFirstValue("ss.CoURL_3", ss.CoURL_3);
                ss.CoUser_3 = setForm.getFirstValue("ss.CoUser_3", ss.CoUser_3);
                ss.CoPass_3 = setForm.getFirstValue("ss.CoPass_3", ss.CoPass_3);
                ss.ZFPLabServerAutoStart = setForm.getFirstValue("ss.ZFPLabServerAutoStart", "0").equals("1")?1:0;
                
                ss.LLFPCBase = setForm.getFirstValue("ss.LLFPCBase", ss.LLFPCBase);
                if (!ss.LogLevelsList.contains(ss.LLFPCBase))
                    throw new Exception("Ivalid log level for LLFPCBase!"+ss.LLFPCBase);
                ss.LLProtocol = setForm.getFirstValue("ss.LLProtocol", ss.LLProtocol);
                if (!ss.LogLevelsList.contains(ss.LLProtocol))
                    throw new Exception("Ivalid log level for LLProtocol!"+ss.LLProtocol);
                ss.LLDevice = setForm.getFirstValue("ss.LLDevice", ss.LLDevice);
                if (!ss.LogLevelsList.contains(ss.LLDevice))
                    throw new Exception("Ivalid log level for LLDevice!"+ss.LLDevice);
                ss.LLCBIOService = setForm.getFirstValue("ss.LLCBIOService", ss.LLCBIOService);
                if (!ss.LogLevelsList.contains(ss.LLCBIOService))
                    throw new Exception("Ivalid log level for LLCBIOService!"+ss.LLCBIOService);
                ss.update(save);
            } catch (Exception ex) {
                responseError(ex.getMessage());
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                break;
            }
            // TODO: Actual save serrings
        } while (false);
        return displayServerSettings(ss);
    }
    
}
