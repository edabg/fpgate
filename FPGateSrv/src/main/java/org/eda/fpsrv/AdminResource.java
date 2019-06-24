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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.Status;

/**
 *
 * @author Dimitar Angelov
 */
public class AdminResource extends ServerResource {
    private FPDatabase db;
    
    protected HashMap responseData = new HashMap();

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
        responseData.put("ContextTitle", "");
        responseData.put("URL_HOME", "/admin/");
        responseData.put("URL_PRINTERS", "/admin/printers/");
        responseData.put("URL_USERS", "/admin/users/");
        responseData.put("URL_STATUS", "/admin/status/");
        responseData.put("URL_PRINTERTEST", "/admin/printer-test/");
        responseData.put("URL_PRINT", "/print/");
        responseData.put("errors", new ArrayList<String>());
        responseData.put("warnings", new ArrayList<String>());
        responseData.put("messages", new ArrayList<String>());
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
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
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
            ares = getRequestAttributes().get("ares").toString();
            act = getRequestAttributes().get("idr").toString();
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
    
}
