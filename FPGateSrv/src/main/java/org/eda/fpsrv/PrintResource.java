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

import com.google.common.util.concurrent.Striped;
import java.io.IOException;
import static java.lang.System.currentTimeMillis;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.restlet.resource.ServerResource;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 *
 * @author Dimitar Angelov
 */
public class PrintResource extends ServerResource {

    private FPCBase FP;
    private PrintResponse response;
    private PrintRequest pRequest;
    private FPDatabase FPDB;
    private localExecutionLog execLog;

    public PrintResource() {
        super();
        execLog = this.new localExecutionLog();
    }
    
    private class localExecutionLog {
        public void log(Level level, String msg) {
            getLogger().log(level, msg);
            String threadId = Long.toString(Thread.currentThread().getId());
            String time = Long.toString(currentTimeMillis());
            msg = time+" : "+threadId+" : "+msg;
            if (response != null) {
                if (level.intValue() >= Level.WARNING.intValue()) {
                    response.getLog().add(msg);
                    response.getErrors().add(msg);
                } else if ((level.intValue() >= Level.CONFIG.intValue()) 
                        || (FPServer.application.getAppProperties().getProperty("DebugMode", "0") != "0"))
                    response.getLog().add(msg);
            }
        }
        
        public void msg(String msg) {
            log(Level.INFO, msg);
        }
        
        public void error(String msg) {
            log(Level.SEVERE, msg);
        }
        
        public void warning(String msg) {
            log(Level.WARNING, msg);
        }

        public void debug(String msg) {
            log(Level.FINER, msg);
        }
        
    }
    
    protected static Striped<Semaphore> printerSemaphores = Striped.semaphore(20,1);
    protected Semaphore printerSemaphore;
    protected String printerRefID = ""; 
    
    protected void acquirePrinterSemaphore(String printerRefID) throws Exception {
        releasePrinterSemaphore(); // if already taken
        execLog.msg("Requesting Printer by RefId:"+printerRefID);
        printerSemaphore  = printerSemaphores.get(printerRefID);
        if (!printerSemaphore.tryAcquire(10, TimeUnit.SECONDS)) {
            printerSemaphore = null;
            throw new FPException((long)-1, "Printer "+printerRefID+" is busy!");
        }
    }
    
    protected void releasePrinterSemaphore() {
        if (printerSemaphore != null) {
            printerSemaphore.release();
            printerSemaphore = null;
        }    
    }
    
    
    protected void initPrinter() throws FPException, Exception {
        FP = null;
        printerRefID = pRequest.getPrinter().getID();
        if (printerRefID.length() > 0) {
            acquirePrinterSemaphore(printerRefID);
            try {
                if (FPDB == null) FPDB = new FPDatabase();
                FPrinter fp_ = FPDB.getPrinterByRefId(printerRefID);
                if (fp_ == null)
                    throw new FPException((long)-1, "Can't find the printer!");
                if (fp_.getIsActive() == 0)
                    throw new FPException((long)-1, "Printer is not active!");
                execLog.msg("Printer found. Requesting Printer by Model:"+fp_.getModelID());
                FP = FPCBase.getFPCObject(fp_.getModelID());
                execLog.msg("Setting printer parameters:");
                FP.setParams(fp_.getProperties());
                execLog.msg("Initializing printer");
                FP.init();
            } catch (Exception E) {
                releasePrinterSemaphore();
                execLog.msg("Exception:"+E.getMessage());
                E.printStackTrace();
                throw new FPException((long)-1, E.getMessage());
            }
        } else {
            acquirePrinterSemaphore(pRequest.getPrinter().getModel());
            try {
                execLog.msg("Requesting Printer by Model:"+pRequest.getPrinter().getModel());
                FP = FPCBase.getFPCObject(pRequest.getPrinter().getModel());
                execLog.msg("Setting printer parameters:");
                for (HashMap.Entry<?, ?> pEntry : pRequest.getPrinter().getParams().entrySet()) {
                    String key = pEntry.getKey().toString();
                    String value = pEntry.getValue().toString();
                    execLog.msg(key+"="+value);
                }    
                FP.setParams(pRequest.getPrinter().getParams());
                execLog.msg("Initializing printer");
                FP.init();
            } catch (Exception E) {
                releasePrinterSemaphore();
                execLog.msg("Exception:"+E.getMessage());
                E.printStackTrace();
                throw new FPException((long)-1, E.getMessage());
            }
        }    
    }
    
    protected void donePrinter() {
        if (FP != null) 
            FP.destroy();
        FP = null;
        releasePrinterSemaphore();
        if (printerSemaphore != null) {
            printerSemaphore.release();
            printerSemaphore = null;
        }    
    }

    public void abnormalComplete() throws FPException{
        FP.abnormalComplete();
    }
    
    
    public void setOperatorName()  throws FPException {
        execLog.msg("Entering setOperatorName");
        String opCode = "DETAILED";
        String opPass = "";
        String opName = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("Code"))
            opCode = namedArgs.get("Code");
        if (namedArgs.containsKey("Passwd"))
            opPass = namedArgs.get("Passwd");
        if (namedArgs.containsKey("FullName"))
            opName = namedArgs.get("FullName");
        execLog.msg("setOperatorName("+opCode+","+((opPass.length()>0)?"*":"")+","+opName);
        FP.setOperator(opCode, opPass, opName);
    }
    
    /**
     * Prints Fiscal/Non fiscal check<br>
     * Fiscal check contents is as items in pRequest.Arguments.<br>
     * Each item is in following format:<br>
     * 
     *  CMD\t[Params]<br>
     * Valid commands are:<br>
     * SON\tOperatorName - Sets operator name<br>
     * PFT\tText - Print Fiscal Text<br>
     *  PFT\tText<br>
     * PNT\tText - Print Non Fiscal Text<br>
     *  PNT\tText<br>
     * PLF - Paper Line Feed<br>
     *  PLF\t[Lines]<br>
     * STG - Register sell by tax group<br>
     *  STG\tText(48)\TaxCode([...])\tPrice\tPercent[\tQty]<br>
     * SDP - Register sell by department<br>
     *  SDP\tText(48)\DeptCode([...])\tPrice\tPercent[\tQty]<br>
     * STL - Subtotal<br>
     *  STL[\tToPrint[\tToDisplay[\tPerc]]]<br>
     * TTL - Total<br>
     *  TTL\tText\tPaymentAbbr\\tAmount<br>
     * CMD - Custom command<br>
     *  CMD\tCCC\tParams<br>
     * Notes:<br>
     * For fiscal check, there are must be at least 1 sell Row <br>
     * and at the end must be total<br>
     * PFT and PNT supports some functions in following form<br>
     * PFT\t@func\tParam1\tParam2\tParam3<br>
     * Following is the list of supported functions:<br>
     * {@literal @}padl\tText[\tPaddingSymbol] - Align text to the left and pad to the whole width with padding symbol (by default is space)<br>
     * {@literal @}padr\tText[\tPaddingSymbol] - Align text to the right and pad to the whole width with padding symbol (by default is space)<br>
     * {@literal @}padc\tText[\tPaddingSymbol] - Align text to the center and pad to the whole width with padding symbol (by default is space)<br>
     * {@literal @}lval\tLabel\tValue - Align Label And Value. The Label is aligned to the left and Value to the right.<br>
     * @param fiscal fiscal or nonfiscal check
     * @throws org.eda.fpsrv.FPException
     */
    public void commonPrint(boolean fiscal) throws FPException, Exception {
        ArrayList<String[]> cmdList = new ArrayList<>();
        String[] cmdParams;
        String setOperatorName = "";
        for(String arg : pRequest.getArguments()) {
            if (arg.length() > 0) {
                cmdParams = arg.split("\\t");
                // Check for per open fiscal check command
                if ((cmdParams[0].length() > 1) && cmdParams[0].equals("SON"))
                    setOperatorName = cmdParams[1];
                else
                    cmdList.add(cmdParams);
            }    
        }
        if (setOperatorName.length() > 0) {
            execLog.msg("setOperatorName("+setOperatorName+")");
            try {
                FP.setOperator("", "", setOperatorName);
            } catch (FPException e) {
                execLog.error("Set Operator Name: FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
            }    
        }
        if (fiscal) {
            execLog.msg("Opening fiscal check");
            FP.openFiscalCheck();
        } else {
            execLog.msg("Opening non fiscal check");
            FP.openNonFiscalCheck();
        }    
        Integer lineNum = 0;
        for(String[] cmd : cmdList) {
            lineNum++;
            String cmdStr = "";
            for(int i = 0; i < cmd.length; i++)
                cmdStr += ((i > 0)?":":"")+cmd[i];
            try {
                execLog.msg("Processing command:"+cmdStr);
                switch (cmd[0]) {
                    case "PFT" : // PFT\tText - Print Fiscal Text
                        if (cmd.length > 1) {
                            String text = "";
                            for (int i = 1; i < cmd.length; i++)
                                text = text + ((i > 1)?"\t":"") + cmd[i];
                            FP.printFiscalText(text);
                        }    
                        break;
                    case "PNT" : // PNT\tText - Print Non Fiscal Text
                        if (cmd.length > 1) {
                            String text = "";
                            for (int i = 1; i < cmd.length; i++)
                                text = text + ((i > 1)?"\t":"") + cmd[i];
                            FP.printNonFiscalText(text);
                        }    
                        break;
                    case "PLF" : // PF\tLines - Paper feed 
                        FP.paperFeed((cmd.length > 1)?Integer.parseInt(cmd[1]):1);
                        break;
                    case "STG" : // STG\tText\TaxCode([...])\tPrice\tPercent[\tQty] - Register Sell by tax Group
                        if (cmd.length > 5) {
                            // sell with Quantity
                            FP.sell(
                                cmd[1]
                                , FP.taxAbbrToGroup(cmd[2])
                                , Double.parseDouble(cmd[3])
                                , Double.parseDouble(cmd[4])
                                , Double.parseDouble(cmd[5])
                            );
                        } else if (cmd.length == 5) {
                            // sell w/o Quantity
                            FP.sell(
                                cmd[1]
                                , FP.taxAbbrToGroup(cmd[2])
                                , Double.parseDouble(cmd[3])
                                , Double.parseDouble(cmd[4])
                            );
                        } else
                           execLog.error("line:"+lineNum+" cmd:"+cmd[0]+": Invalid number of arguments");
                        break;
                    case "SDP" : // STG\tText(48)\DeptCode([...])\tPrice\tPercent[\tQty] - Register Sell by department
                        if (cmd.length >=5)
                           FP.sellDept(cmd[1], cmd[2], Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4]), Double.parseDouble(cmd[5]));
                        else
                           FP.sellDept(cmd[1], cmd[2], Double.parseDouble(cmd[3]));
                        break;
                    case "STL" : // STL[\tToPrint[\tToDisplay[\tPerc]]] - Subtotal
                        FP.subTotal(
                            (cmd.length > 1)?(Byte.valueOf(cmd[1]) != 0):true
                            , (cmd.length > 2)?(Byte.valueOf(cmd[2]) != 0):true
                            , (cmd.length > 3)?Double.valueOf(cmd[3]):0
                        );
                        break;
                    case "TTL" : // TTL\tText\tPaymentAbbr\\tAmount - total
                        if (cmd.length > 2) {
                            FP.total(cmd[1], FP.payAbbrToType(cmd[2]), Double.parseDouble(cmd[3]));
                        } else
                           execLog.error("line:"+lineNum+" cmd:"+cmd[0]+": Invalid number of arguments");
                        break;
                    case "CMD" : // CMD\tCMD\tParams
                        String params = "";
                        for (int i = 2; i < cmd.length; i++)
                            params = params + ((i > 2)?"\t":"") + cmd[i];
                        FP.customCmd(cmd[1], params);
                        break;
                    default :
                        execLog.error("line:"+lineNum+" cmd:"+cmd[0]+": Unsupported command!");
                }
            } catch (FPException e) {
                response.setErrorCode(e.getErrorCode());
                execLog.error("line:"+lineNum+" cmd:"+cmd[0]+": FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
            } catch (Exception e) {
                execLog.error("line:"+lineNum+" cmd:"+cmd[0]+" Error:"+e.getMessage());
            }    
        } // for each command
        if (fiscal) {
            execLog.msg("Closing fiscal check");
            FP.printFiscalText("www.colibrierp.com");
            FP.closeFiscalCheck();
        } else {
            execLog.msg("Closing non fiscal check");
            FP.printNonFiscalText("www.colibrierp.com");
            FP.closeNonFiscalCheck();
        }
        response.getResultTable().put("CloseStatus", "1");
        response.getResultTable().putAll(FP.getCurrentCheckInfo());
    }

    public void printDuplicateCheck() throws FPException {
        execLog.msg("Entering printDuplicateCheck");
        FP.printLastCheckDuplicate();
    }

    public void lastFiscalRecordInfo() throws FPException {
        execLog.msg("Entering lastFiscalRecordInfo");
        StrTable res;
        execLog.msg("Calling daily getLastFiscalRecordInfo");
        res = FP.getLastFiscalRecordInfo();
        if (res == null) {
            execLog.msg("getLastFiscalRecordInfo report returns no result!");
        } else {
            execLog.msg("getLastFiscalRecordInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void printerStatus() throws FPException {
        execLog.msg("Entering printerStatus");
        StrTable res;
        execLog.msg("Calling daily getLastFiscalRecordInfo");
        res = FP.getPrinterStatus();
        if (res == null) {
            execLog.msg("getPrinterStatus report returns no result!");
        } else {
            execLog.msg("getPrinterStatus report returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void getDiagnosticInfo() throws FPException {
        execLog.msg("Entering getDiagnosticInfo");
        StrTable res;
        execLog.msg("Calling daily getDiagnosticInfo");
        res = FP.getDiagnosticInfo();
        if (res == null) {
            execLog.msg("getDiagnosticInfo report returns no result!");
        } else {
            execLog.msg("getDiagnosticInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void reportDaily() throws FPException {
        execLog.msg("Entering daily report");
        String reportTypeAbbr = "X";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("ReportType"))
            reportTypeAbbr = namedArgs.get("ReportType");
        execLog.msg("Report type abbreviation:"+reportTypeAbbr);
        StrTable res;
        execLog.msg("Calling daily report");
        res = FP.reportDaily(FPCBase.dailyReportTypeAbbrToType(reportTypeAbbr));
        if (res == null) {
            execLog.msg("Daily report returns no result!");
        } else {
            execLog.msg("Daily report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void reportByDates() throws ParseException, FPException {
        execLog.msg("Entering reportByDates");
        String reportTypeAbbr = "DETAILED";
        String fromDateStr = "";
        String toDateStr = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("ReportType"))
            reportTypeAbbr = namedArgs.get("ReportType");
        if (namedArgs.containsKey("FromDate"))
            fromDateStr = namedArgs.get("FromDate");
        if (namedArgs.containsKey("ToDate"))
            toDateStr = namedArgs.get("ToDate");

        execLog.msg("Report type abbreviation:"+reportTypeAbbr);
        execLog.msg("From Date :"+fromDateStr);
        execLog.msg("To Date :"+toDateStr);
        Date fromDate;
        Date toDate;
        
        if (fromDateStr.length() > 0)
            fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
        else
            fromDate = new Date();
        if (toDateStr.length() > 0)
            toDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
        else
            toDate = new Date();

        StrTable res;
        execLog.msg("Calling report by dates");
        res = FP.reportByDates(fromDate, toDate, FPCBase.datesReportTypeAbbrToType(reportTypeAbbr));
        if (res == null) {
            execLog.msg("Report by dates returns no result!");
        } else {
            execLog.msg("Report by dates returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void getDateTime() throws FPException {
        execLog.msg("Entering getDateTime");
        execLog.msg("Calling getDateTime");
        Date dateTime;
        dateTime = FP.getDateTime();
        response.getResultTable().put("DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime));
    }

    public void setDateTime() throws ParseException, FPException {
        execLog.msg("Request setDateTime");
        String dateTimeStr = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("DateTime"))
            dateTimeStr = namedArgs.get("DateTime");

        execLog.msg("Date time requested:"+dateTimeStr);
        Date dateTime;
        if (dateTimeStr.length() > 0)
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeStr);
        else
            dateTime = new Date();
        StrTable res;
        execLog.msg("Calling setDateTime");
        dateTime = FP.setDateTime(dateTime);
        response.getResultTable().put("DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime));
    }
    
    public void customCommand() throws FPException {
        execLog.msg("Request custom command");
        String command = "";
        String args = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("Cmd"))
            command = namedArgs.get("Cmd");
        if (namedArgs.containsKey("Args"))
            args = namedArgs.get("Args");

        execLog.msg("Command:"+command+" Arguments:"+args);
        String res = FP.customCmd(command, args);
        response.getResultTable().put("Result:", res);
    }

    
    public void getJournalInfo() throws FPException {
        execLog.msg("Entering getJournalInfo");
        StrTable res;
        execLog.msg("Calling daily getJournalInfo");
        res = FP.getJournalInfo();
        if (res == null) {
            execLog.msg("getJournalInfo report returns no result!");
        } else {
            execLog.msg("getJournalInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void getJournal() throws FPException {
        execLog.msg("Entering getJournal");
        StrTable res;
        String readAndEraseStr = "false";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("ReadAndErase"))
            readAndEraseStr = namedArgs.get("ReadAndErase");
        execLog.msg("Calling daily getJournal(readAndErase="+readAndEraseStr+")");
        res = FP.getJournal(Boolean.parseBoolean(readAndEraseStr));
        if (res == null) {
            execLog.msg("getJournal report returns no result!");
        } else {
            execLog.msg("getJournal report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void test() throws FPException {
        execLog.msg("Entering test");
        StrTable res;
        execLog.msg("Calling daily getDiagnosticInfo");
        res = FP.getDiagnosticInfo();
        if (res == null) {
            execLog.msg("getDiagnosticInfo report returns no result!");
        } else {
            execLog.msg("getDiagnosticInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
          
    public void cashInOut() throws ParseException, FPException {
        execLog.msg("Request cashInOut");
        String amountStr = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("Amount"))
            amountStr = namedArgs.get("Amount");

        execLog.msg("Cash In/Out requested:"+amountStr);
        if (amountStr.length() == 0)
            amountStr = "0";
        Double amount = Double.parseDouble(amountStr);
        execLog.msg("Calling cashInOut");
        StrTable res = FP.cashInOut(amount);
        if (res == null) {
            execLog.msg("cashInOut returns no result!");
        } else {
            execLog.msg("cashInOut returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    @Post("json:json")
    public PrintResponse processCommand(PrintRequest request) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        PrintRequest pReq = mapper.readValue(pRequest, PrintRequest.class);
        pRequest = request;
        response = new PrintResponse();
        response.setId(request.getId());
        try {
            execLog.msg("Request command:"+pRequest.getCommand());
            initPrinter();
            try {
                switch (pRequest.getCommand().toLowerCase()) {
                    case "abnormalcomplete" :
                        abnormalComplete();
                        break;
                    case "setoperatorname" :
                        setOperatorName();
                        break;
                    case "printfiscalcheck" :
                        commonPrint(true);
                        break;
                    case "printnonfiscalcheck" :
                        commonPrint(false);
                        break;
                    case "printduplicatecheck" :
                        printDuplicateCheck();
                        break;
                    case "lastfiscalrecordinfo" :
                        lastFiscalRecordInfo();
                        break;
                    case "reportdaily" :
                        reportDaily();
                        break;
                    case "reportbydates" :
                        reportByDates();
                        break;
                    case "getdatetime" :
                        getDateTime();
                        break;
                    case "setdatetime" :
                        setDateTime();
                        break;
                    case "cashinout" :
                        cashInOut();
                        break;
                    case "customcommand" :
                        customCommand();
                        break;
                    case "getjournalinfo" :
                        getJournalInfo();
                        break;
                    case "getjournal" :
                        getJournal();
                        break;
                    case "printerstatus" :
                        printerStatus();
                        break;
                    case "getdiagnosticinfo" :
                        getDiagnosticInfo();
                        break;
                    case "test" :
                        test();
                        break;
                    default :
                        throw new FPException((long)-1, "Unsupported command:"+pRequest.getCommand());
                        //response.getResultTable().put("ala", "bala");
                }
            } catch (FPException e) {
                response.setErrorCode(e.getErrorCode());
                execLog.error("FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
            } catch (Exception e) {
                execLog.error("Error:"+e.getMessage());
            } finally {
                donePrinter();
            }    
        } catch (FPException  e) {
            response.setErrorCode(e.getErrorCode());
            execLog.error("FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
        } catch (Exception  e) {
            execLog.error("Error:"+e.getMessage());
        }
        return response;
    }

    @Post("form:html")
    public String processFrom(Form f) {
        return "<h1>Ala bala"+f.toString()+"</h1>";
    }

    @Get("html") 
    public String display() {
        return "<h1>Printers are waiting ["+getLogger().getName()+"]...</h1>";
    }
    

}
