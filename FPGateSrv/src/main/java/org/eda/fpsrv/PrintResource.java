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

import com.fazecast.jSerialComm.SerialPort;
import java.io.File;
import java.io.FilenameFilter;
import org.eda.fdevice.StrTable;
import org.eda.fdevice.FPException;
import org.eda.fdevice.FPCBase;
import java.io.IOException;
import static java.lang.System.currentTimeMillis;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.restlet.resource.ServerResource;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.eda.fdevice.FPDatabase;
import org.eda.fdevice.FPPrinterPool;
import org.eda.fdevice.FPrinter;
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
    private localExecutionLog execLog;
    private FPCLogHandler fpcLogHandler = null;

    public PrintResource() {
        super();
        execLog = new localExecutionLog();
    }
    
    // Catch logs from AbstractFiscalDevice
    private class FPCLogHandler extends Handler {
//        private ControlPanel panel;
        private localExecutionLog execLog;
        
        public FPCLogHandler() {
        }

        public FPCLogHandler(localExecutionLog execLog_) {
            this.execLog = execLog_;
        }

        @Override
        public void publish(LogRecord lr) {
            if (this.execLog != null)
                this.execLog.log(lr.getLevel(), lr.getLoggerName()+":"+lr.getMessage(), false); // add only to exec log
        }

        @Override
        public void flush() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void close() throws SecurityException {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    private class localExecutionLog {
        public void log(Level level, String msg) {
            log(level, msg, true);
        }
        public void log(Level level, String msg, boolean logToComponentLogger) {
            String threadId = Long.toString(Thread.currentThread().getId());
            if (logToComponentLogger)
                getLogger().log(level, msg);
            String time = Long.toString(currentTimeMillis());
            msg = time+" : "+threadId+" : "+msg;
            if (response != null) {
                response.getLog().add(msg);
                if (level.intValue() >= Level.WARNING.intValue()) {
//                    response.getLog().add(msg);
                    response.getErrors().add(msg);
                } else if ((level.intValue() >= Level.CONFIG.intValue()) 
                        || (FPServer.application.getAppProperties().getProperty("DebugMode", "0") != "0")) {
//                    response.getLog().add(msg);
                }    
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

    protected void attachExecLogHandler() {
        fpcLogHandler = new FPCLogHandler(execLog);
        FPCBase.getLogger().addHandler(fpcLogHandler);
    }
    
    protected void detachExecLogHandler() {
        if (fpcLogHandler != null) {
            FPCBase.getLogger().removeHandler(fpcLogHandler);
        }    
        fpcLogHandler = null;
    }
    protected void initPrinter() throws FPException, Exception {
        if (pRequest.getPrinter() != null) {
            FP = FPPrinterPool.requestPrinter(pRequest.getPrinter().getID(), pRequest.getPrinter().getModel(), pRequest.getPrinter().getParams());
            if (FP == null) {
                throw new FPException("Can't initialize printer!");
            }
            FP.lock();
            FP.readStatus();
        }    
    }
    
    protected void donePrinter() {
        if (FP != null)
            FP.unLock();
        FPPrinterPool.releasePrinter(FP);
        FP = null;
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
    
    protected String splitSellText2(String text) {
        String[] textLines = text.split("[~][~]", 2);
        if (textLines.length > 1) {
            return textLines[0]+"\n"+textLines[1];
        } else
            return text;
    }
    
    protected String adjustDiscount(String discount) {
        // Discoutn values:
        // [+|-]99.99[%] - Отстъпка/надбавка като процент
        // v[+|-]99.99   - Отстъпка като стойност
        String aDiscount = discount;
        if (!aDiscount.contains("%")) {
            if (aDiscount.startsWith("v")) {
                aDiscount = aDiscount.substring(1);
            } else
                aDiscount = aDiscount+"%";
        }
        return aDiscount;
    }
    
    /**
     * Prints Fiscal/Non fiscal check<br>
     * Fiscal check contents is as items in pRequest.Arguments.<br>
     * Each item is in following format:<br>
     * 
     *  CMD\t[Params]<br>
     * Valid commands are:<br>
     * SON\tOperatorName - Sets operator name<br>
     * <br>
     * UNS\tDDDDDDDD-OOOO-####### - Unique Number of Sell<br>
     * <br>
     * REV - Revision/Storno check<br>
     * RTA\tRET|ERR|RED - Revision/Storno type<br>
     * RDN\t####### - Doc Number subject of revision/storno<br>
     * RDT\tYYYY-MM-DD HH:mm:ss - Doc Date/time subject of revision/storno<br>
     * RIN\t########## - Credit note to InvoiceNum 
     * RUS\tDDDDDDDD-OOOO-####### - Unique Number of sell subject of revision/storno<br>
     * RFM\t######## - Fiscal Memory number subject of revision/storno document<br>
     * RRR\tText - Optional revision/storno text<br>
     * <br>
     * INV - extended/invoice fiscal check
     * CRCP\tRecipient - Recipient name
     * CBUY\tBuyer - Buyer company name
     * CADR\tAddrsss - Buyer address 
     * CUIC\tUIC - Unified Identification Code
     * CUIT\tUICType - UIC Type code. 'BULSTAT', 'EGN', 'FOREIGN', 'NRA'
     * CVAT\tVATNum - VAT Number
     * CSEL\tSeller - Seller name (Not supported on all devices!)
     * <br>
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
     * FTR - Print Text footer<br>
     *  FTR\tText<br>
     * FDN - Fiscal Device Serial Number<br>
     *  FDN\tSerialNum<br>
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
        String setOperatorCode = "";
        String setOperatorPass = "";
        String setOperatorName = "";
        boolean isInvoice = false;
        FPCBase.CustomerInfo custInfo = new FPCBase.CustomerInfo();
        String UNS = ""; // Unique Number of Sell (only in fiscal)
        // Data for reverse/storno Fiscal receipt
        boolean isRevision = false;
        FPCBase.fiscalCheckRevType RevType = FPCBase.fiscalCheckRevType.OP_ERROR; // Reason for Reverse/Storno By default Error
        String RevDocNum = "";
        String RevUNS = "";
        Date RevDateTime = null;
        String RevFMNum = "";
        String RevReason = "";
        String RevInvoiceNum = "";
        String FDSerialNum = "";
        Date RevInvoiceDate = null;
        ArrayList<String[]> footer = new ArrayList<>();
        for(String arg : pRequest.getArguments()) {
            if (arg.length() == 0) continue;
            cmdParams = arg.split("\\t");
            // Check for per open fiscal check command
            if ((cmdParams[0].length() > 1)) {
                if (cmdParams[0].equals("FDN")) {
                    if (cmdParams.length > 1)
                        FDSerialNum = cmdParams[1];
                } else if (cmdParams[0].equals("SON")) {
                    setOperatorName = cmdParams[1];
                } else if (cmdParams[0].equals("SOC")) {
                    setOperatorCode = cmdParams[1];
                } else if (cmdParams[0].equals("SOP")) {
                    setOperatorPass = cmdParams[1];
                } else if (cmdParams[0].equals("UNS")) {
                    UNS = cmdParams[1]; 
                } else if (cmdParams[0].equals("REV")) { // Is Reverse/Strono receipt
                    isRevision = true; 
                } else if (cmdParams[0].equals("RTA")) { // Reverse/Storno type abbr
                    if (cmdParams.length > 1)
                        RevType = FPCBase.fiscalCheckRevTypeAbbrToType(cmdParams[1]); 
                    else
                        throw new FPException("Не е посочен тип на сторно операцията!");
                } else if (cmdParams[0].equals("RDN")) { // Reverse/Storno Doc Num
                    if (cmdParams.length > 1)
                        RevDocNum = cmdParams[1]; 
                    else
                        throw new FPException("Не е посочен номер на документа за сторниране!");
                } else if (cmdParams[0].equals("RUS")) { // Reverse/Storno UNS
                    if (cmdParams.length > 1)
                        RevUNS = cmdParams[1]; 
                    else
                        throw new FPException("Не е посочен УНП на документа за сторниране!");
                } else if (cmdParams[0].equals("RDT")) { // Reverse/Storno date/time
                    if (cmdParams.length > 1) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        RevDateTime = format.parse(cmdParams[1]);
                    } else    
                        throw new FPException("Не е посочена дата/час на документа за сторниране!");
                } else if (cmdParams[0].equals("RFM")) { // Reverse/Storno Fiscal Memory Num
                    if (cmdParams.length > 1)
                        RevFMNum = cmdParams[1];
                    else
                        throw new FPException("Не е посочен номер на фискалната памет на документа за сторниране!");
                } else if (cmdParams[0].equals("RRR")) { // Reverse/Storno Reson text
                    if (cmdParams.length > 1)
                        RevReason = cmdParams[1];
                } else if (cmdParams[0].equals("RIN")) { // Reverse/Storno Invoice num
                    if (cmdParams.length > 1)
                        RevInvoiceNum = cmdParams[1];
                } else if (cmdParams[0].equals("RID")) { // Reverse/Storno Invoice date/time
                    if (cmdParams.length > 1) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        RevInvoiceDate = format.parse(cmdParams[1]);
                    } else     
                        throw new FPException("Не е посочен номер на фактура за сторниране!");
                } else if (cmdParams[0].equals("INV")) {
                    isInvoice = true; 
                } else if (cmdParams[0].equals("CRCP")) { // Customer Recipient
                    custInfo.recipient = cmdParams[1];
                } else if (cmdParams[0].equals("CBUY")) { // Customer Buyer
                    custInfo.buyer = cmdParams[1];
                } else if (cmdParams[0].equals("CADR")) { // Customer Address
                    custInfo.address = cmdParams[1];
                } else if (cmdParams[0].equals("CUIC")) { // Customer UIC
                    custInfo.UIC = cmdParams[1];
                } else if (cmdParams[0].equals("CUIT")) { // Customer UIC Type
                    custInfo.setUICtypeStr(cmdParams[1]);
                } else if (cmdParams[0].equals("CVAT")) { // VAT Number
                    custInfo.VATNumber = cmdParams[1];
                } else if (cmdParams[0].equals("CSEL")) { // Customer Info Seller
                    custInfo.seller = cmdParams[1];
                } else if (cmdParams[0].equals("FTR")) { // Custom footer
                    if (cmdParams.length > 1)
                        footer.add(Arrays.copyOfRange(cmdParams, 1, cmdParams.length));
                    else
                        footer.add(new String[0]);
                } else {
                   cmdList.add(cmdParams);
                }
            } else
               cmdList.add(cmdParams);
        }
        if (FDSerialNum.length() > 0) {
            // Check if SerialNumber match
            if (!FP.getDeviceInfo().SerialNum.equals(FDSerialNum)) {
                throw new FPException("Серийният номер на ФУ е различен от заявения!");
            }
        }
        if (!setOperatorCode.isEmpty()) {
            FP.setParam("OperatorCode", setOperatorCode);
            FP.setParam("OperatorPass", setOperatorPass);
        }    
        if (setOperatorName.length() > 0) {
            execLog.msg("setOperator("+setOperatorCode+",*,"+setOperatorName+")");
            try {
                FP.setOperator(setOperatorCode, setOperatorPass, setOperatorName);
            } catch (FPException e) {
                execLog.error("Set Operator: FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
            }    
        }

        Date dtBeforeOpen = null;
        Date dtAfterOpen = null;
        Date dtBeforeClose = null;
        Date dtAfterClose = null;
        
        try {dtBeforeOpen = FP.getDateTime();} finally{};
        if (fiscal) {
            if (isInvoice) {
                FP.setInvoiceFiscalCheckOn(custInfo);
            } else
                FP.setInvoiceFiscalCheckOff();
            if (!isRevision) {
                execLog.msg("Opening fiscal check UNS:"+UNS+" Invoice:"+(isInvoice?"Yes":"No"));
                if (UNS.length() > 0) 
                    FP.openFiscalCheck(UNS);
                else 
                    FP.openFiscalCheck();
            } else {
                execLog.msg("Opening Reverse/Storno fiscal check");
                FP.openFiscalCheckRev(UNS, RevType, RevDocNum, RevUNS, RevDateTime, RevFMNum, RevReason, RevInvoiceNum, RevInvoiceDate);
            }    
        } else {
            execLog.msg("Opening non fiscal check");
            FP.openNonFiscalCheck();
        }    
        try {dtAfterOpen = FP.getDateTime();} finally{};
        Integer lineNum = 0;
		String commandError = "";
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
                                splitSellText2(cmd[1])          // text
                                , FP.taxAbbrToGroup(cmd[2])     // taxCode
                                , Double.parseDouble(cmd[3])    // price
                                , Double.parseDouble(cmd[5])    // quantity
                                , adjustDiscount(cmd[4])        // discountPerc
                            );
                        } else if (cmd.length == 5) {
                            // sell w/o Quantity
                            FP.sell(
                                splitSellText2(cmd[1])          // text
                                , FP.taxAbbrToGroup(cmd[2])     // taxCode
                                , Double.parseDouble(cmd[3])    // price
                                , adjustDiscount(cmd[4])        // discountPerc
                            );
                        } else
                           execLog.error("line:"+lineNum+" cmd:"+cmd[0]+": Invalid number of arguments");
                        break;
                    case "SDP" : // STG\tText(48)\DeptCode([...])\tPrice\tPercent[\tQty] - Register Sell by department
                        if (cmd.length > 4) {
                           FP.sellDept(
                                splitSellText2(cmd[1])                              // text
                                , cmd[2]                                            // deptCode
                                , Double.parseDouble(cmd[3])                        // price
                                , Double.parseDouble((cmd.length > 5)?cmd[5]:"1")   // quantity
                                , adjustDiscount(cmd[4])                            // discountPerc
                           );
                        } else
                           FP.sellDept(
                                splitSellText2(cmd[1])          // text
                                , cmd[2]                        // deptCode
                                , Double.parseDouble(cmd[3])    // price
                           );
                        break;
                    case "STL" : // STL[\tToPrint[\tToDisplay[\tPerc]]] - Subtotal
                        FP.subTotal(
                            (cmd.length > 1)?(Byte.valueOf(cmd[1]) != 0):true
                            , (cmd.length > 2)?(Byte.valueOf(cmd[2]) != 0):true
                            , (cmd.length > 3)?Double.valueOf(cmd[3]):0
                        );
                        break;
                    case "TTL" : // TTL\tText\tPaymentAbbr\\tAmount - total
                        FP.total((cmd.length > 1)?cmd[1]:"", FP.payAbbrToType((cmd.length> 2)?cmd[2]:""), Double.parseDouble((cmd.length > 3)?cmd[3]:"0"));
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
				commandError = cmdStr;
				break;
            } catch (Exception e) {
                execLog.error("line:"+lineNum+" cmd:"+cmd[0]+" Error:"+e.getMessage());
            }    
        } // for each command
        try {dtBeforeClose = FP.getDateTime();} finally{};
        try {response.getResultTable().putAll(FP.getCurrentCheckInfo().toStrTable());} finally{};
        if (footer.isEmpty())
            footer.add(new String[]{"@padc", "www.colibrierp.com"});
        if (fiscal) {
			if (commandError.isEmpty()) {
				execLog.msg("Closing fiscal check");
				for(String[] footline : footer) {
					if (footline.length > 0) {
						String text = "";
						for (int i = 0; i < footline.length; i++)
							text = text + ((i > 0)?"\t":"") + footline[i];
						FP.printFiscalText(text);
					}    
				}    
				FP.closeFiscalCheck();
				try {response.getResultTable().putAll(FP.getLastFiscalCheckQRCode().toStrTable());} finally{};
			} else {
				execLog.error("There is error while printing fiscal check! Error in command: "+commandError+".");
				try {
					execLog.msg("Trying to cance fiscal check.");
					FP.cancelFiscalCheck();
					execLog.msg("Fiscal check canceled.");
				} catch (Exception ex)	{
					execLog.error("Can't cancel fiscal check. "+ex.getMessage());
					execLog.error("Please try manually to cancel fiscal check.");
				}
			}
        } else {
            execLog.msg("Closing non fiscal check");
            for(String[] footline : footer) {
                if (footline.length > 0) {
                    String text = "";
                    for (int i = 0; i < footline.length; i++)
                        text = text + ((i > 0)?"\t":"") + footline[i];
                    FP.printNonFiscalText(text);
                }    
            }    
            FP.closeNonFiscalCheck();
        }
		execLog.msg("Print process finished.");
        try {dtAfterClose = FP.getDateTime();} finally{};
        response.getResultTable().put("CloseStatus", "1");
        DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        response.getResultTable().put("dtBeforeOpen", (dtBeforeOpen != null)?dtFormat.format(dtBeforeOpen):"");
        response.getResultTable().put("dtAfterOpen", (dtAfterOpen != null)?dtFormat.format(dtAfterOpen):"");
        response.getResultTable().put("dtBeforeClose", (dtBeforeClose != null)?dtFormat.format(dtBeforeClose):"");
        response.getResultTable().put("dtAfterClose", (dtAfterClose != null)?dtFormat.format(dtAfterClose):"");
        response.getResultTable().put("LastPrintDocNum", FP.getLastPrintDoc());
        response.getResultTable().putAll(FP.getDeviceInfo().toStrTable());
    }

    public void getVersion() {
        response.getResultTable().put("version", FPServer.application.getVersion());
        response.getResultTable().put("build", FPServer.application.getBuildNumber());
	    Properties prop = FPServer.application.getAppProperties();
		Set<Object> PKeys = prop.keySet();
		for (Object key: PKeys) {
			if (!key.toString().contains("Pass")) {
				response.getResultTable().put("Property "+key.toString(), prop.getProperty(key.toString()));
			}
		}
    }
    
    public void printDuplicateCheck() throws FPException {
        execLog.msg("Entering printDuplicateCheck");
        FP.printLastCheckDuplicate();
    }

    protected void checkDeviceSerialNum(boolean strict) throws FPException {
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("FDSerialNum")) {
            String FDSerialNum = namedArgs.get("FDSerialNum");
            if (FDSerialNum.length() > 0) {
                // Check if SerialNumber match
                if (!FP.getDeviceInfo().SerialNum.equals(FDSerialNum)) {
                    if (strict)
                        throw new FPException("Серийният номер на ФУ е различен от заявения!");
                    else
                        execLog.warning("Серийният номер на ФУ е различен от заявения!");
                }
            }
        }
    }

    protected void checkDeviceSerialNum() throws FPException {
        checkDeviceSerialNum(true);
    }
    
    public void printDuplicateCheckByDocNum() throws FPException {
        execLog.msg("Entering printDuplicateCheckByDocNum");
        checkDeviceSerialNum();
        String docNum = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("DocNum"))
            docNum = namedArgs.get("DocNum");
        execLog.msg("Print duplicate check of document number:"+docNum);
        FP.printCheckDuplicateByDocNum(docNum);
    }
    
    public void currentCheckInfo() throws FPException {
        execLog.msg("Entering currentCheckInfo");
        checkDeviceSerialNum(false);
        StrTable res;
        res = FP.getCurrentCheckInfo().toStrTable();
        if (res == null) {
            execLog.msg("getCurrentCheckInfo report returns no result!");
        } else {
            execLog.msg("getCurrentCheckInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
        FPCBase.FiscalCheckQRCodeInfo qri = FP.getLastFiscalCheckQRCode();
        response.getResultTable().putAll(qri.toStrTable());
    }

    public void lastFiscalRecordInfo() throws FPException {
        execLog.msg("Entering lastFiscalRecordInfo");
        checkDeviceSerialNum(false);
        StrTable res;
        res = FP.getLastFiscalRecordInfo().toStrTable();
        if (res == null) {
            execLog.msg("lastFiscalRecordInfo report returns no result!");
        } else {
            execLog.msg("lastFiscalRecordInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void printerStatus() throws FPException {
        execLog.msg("Entering printerStatus");
        checkDeviceSerialNum(false);
        StrTable res;
        res = FP.getPrinterStatus();
        if (res == null) {
            execLog.msg("getPrinterStatus report returns no result!");
        } else {
            execLog.msg("getPrinterStatus report returns result!");
            response.getResultTable().putAll(res);
            response.getResultTable().put("FPGate_Version", FPServer.application.getVersion());
            response.getResultTable().put("FPGate_Build", FPServer.application.getBuildNumber());
            // Add DeviceInfo
            response.getResultTable().putAll(FP.getDeviceInfo().toStrTable("DI_"));
            // Add information for current date/time
            try {
                Date dtFP = FP.getDateTime();
                response.getResultTable().put("DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtFP));
                Date dtNow = new Date();
                long diffInSecs = (dtNow.getTime()-dtFP.getTime())/1000;
                response.getResultTable().put("DateTimeDiffSecs", Long.toString(diffInSecs));
            } catch (Exception ex) {
                execLog.error(ex.getMessage());
            }    
        }    
// dump loggers
//Enumeration<String> logNames = LogManager.getLogManager().getLoggerNames();
//while (logNames.hasMoreElements()){
//    System.out.println(logNames.nextElement());
//}
/*
        FPCBase.getLogger().info(Thread.currentThread().getId()+": Sleeping ...");
        try {
            Thread.currentThread().sleep(5*1000);
        } catch (InterruptedException ex) {
            FPCBase.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }
        FPCBase.getLogger().info(Thread.currentThread().getId()+": Wakeup ...");
*/
    }

    public void getDiagnosticInfo() throws FPException {
        execLog.msg("Entering getDiagnosticInfo");
        checkDeviceSerialNum(false);
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
        checkDeviceSerialNum();
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
        checkDeviceSerialNum();
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
        checkDeviceSerialNum(false);
        execLog.msg("Calling getDateTime");
        Date dateTime;
        dateTime = FP.getDateTime();
        response.getResultTable().put("DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime));
    }

    public void setDateTime() throws ParseException, FPException {
        execLog.msg("Request setDateTime");
        checkDeviceSerialNum();
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
        checkDeviceSerialNum();
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
        checkDeviceSerialNum(false);
        StrTable res;
        execLog.msg("Calling daily getJournalInfo()");
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
        checkDeviceSerialNum(false);
        StrTable res = null;
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("FromDoc")) {
            String fromDoc = namedArgs.get("FromDoc");
            String toDoc = fromDoc;
            if (namedArgs.containsKey("ToDoc"))
                toDoc = namedArgs.get("ToDoc");
            execLog.msg("Calling getJournal("+fromDoc+","+toDoc+")");
            res = FP.getJournal(fromDoc, toDoc);
        } else { 
            // read journal by dates
            String fromDateStr = "";
            String toDateStr = "";
            if (namedArgs.containsKey("FromDate"))
                fromDateStr = namedArgs.get("FromDate");
            if (namedArgs.containsKey("ToDate"))
                toDateStr = namedArgs.get("ToDate");

            execLog.msg("From Date :"+fromDateStr);
            execLog.msg("To Date :"+toDateStr);
            Date fromDate = new Date();
            Date toDate = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (fromDateStr.length() > 0)
                try {
                    fromDate = dtFormat.parse(fromDateStr);
                } catch (ParseException ex) {
                    execLog.error(ex.getMessage());
                }
            if (toDateStr.length() > 0)
                try {
                    toDate = dtFormat.parse(toDateStr);
                } catch (ParseException ex) {
                    execLog.error(ex.getMessage());
                }
            execLog.msg("Calling getJournal("+dtFormat.format(fromDate)+","+dtFormat.format(toDate)+")");
            res = FP.getJournal(fromDate, toDate);
        }    
        if (res == null) {
            execLog.msg("getJournal report returns no result!");
        } else {
            execLog.msg("getJournal report returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void getDocInfo() throws FPException {
        execLog.msg("Entering getDocInfo");
        checkDeviceSerialNum(false);
        StrTable res = null;
        StrTable namedArgs = pRequest.getNamedArguments();
        String docNum = "";
        if (namedArgs.containsKey("DocNum")) 
            docNum = namedArgs.get("DocNum");
        execLog.msg("Calling getDocInfo("+docNum+")");
        res = FP.getDocInfo(docNum);
        if (res == null) {
            execLog.msg("getDocInfo report returns no result!");
        } else {
            execLog.msg("getDocInfo report returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void test() throws FPException {
        execLog.msg("Entering test");
        checkDeviceSerialNum(false);
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
        checkDeviceSerialNum();
        String amountStr = "";
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("Amount"))
            amountStr = namedArgs.get("Amount");
        if (namedArgs.containsKey("OpCode"))
            FP.setParam("OperatorCode", namedArgs.get("OpCode"));
        if (namedArgs.containsKey("OpPass"))
            FP.setParam("OperatorPass", namedArgs.get("OpPass"));
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

    public void readPaymentMethods() throws FPException {
        execLog.msg("Request readPaymentMethods");
        checkDeviceSerialNum(false);
        StrTable res = FP.readPaymentMethods();
        if (res == null) {
            execLog.msg("readPaymentMethods returns no result!");
        } else {
            execLog.msg("readPaymentMethods returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void readDepartments()throws FPException {
        execLog.msg("Request readDepartments");
        checkDeviceSerialNum(false);
        StrTable res = FP.readDepartments();
        if (res == null) {
            execLog.msg("readDepartments returns no result!");
        } else {
            execLog.msg("readDepartments returns result!");
            response.getResultTable().putAll(res);
        }    
    }

    public void readTaxGroups()throws FPException {
        execLog.msg("Request readTaxGroups");
        checkDeviceSerialNum(false);
        StrTable res = FP.readTaxGroups();
        if (res == null) {
            execLog.msg("readTaxGroups returns no result!");
        } else {
            execLog.msg("readTaxGroups returns result!");
            response.getResultTable().putAll(res);
        }    
    }
    
    public void getCommPorts() throws ParseException, FPException {
        execLog.msg("Request getComPortList");
        StrTable res = new StrTable();
        SerialPort[] ports = SerialPort.getCommPorts();
        for (int i = 0; i < ports.length; i++) {
            StringJoiner jstr = new StringJoiner(",");
            jstr.add(ports[i].getDescriptivePortName());
            jstr.add(Integer.toString(ports[i].getBaudRate()));
            jstr.add(Integer.toString(ports[i].getNumDataBits()));
            jstr.add(Integer.toString(ports[i].getParity()));
            jstr.add(Integer.toString(ports[i].getNumStopBits()));
            jstr.add(ports[i].isOpen()?"OPEN":"CLOSE");
            res.put(ports[i].getSystemPortName(), jstr.toString());
        }    
        response.getResultTable().putAll(res);
    }
    
    protected File[] getLogFiles() {
        File directory = new File(FPServer.application.getLogDir());
        File [] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".log");
            }
        });
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        return files;
    }
    
    protected String readLogFile(String FileName) {
        String fileContent = "";
        try {
            FileName = FPServer.application.getLogDir()+"/"+FileName;
            fileContent = new String(Files.readAllBytes(Paths.get(FileName)));
        } catch (Exception e) {
            execLog.error(e.getMessage());
        }
        return fileContent;
    }

    protected void getLogFilesList() {
        execLog.msg("Request getLogFilesList");
        StrTable res = new StrTable();
        File[] files = getLogFiles();
        for (int i = 0; i < files.length; i++)
            res.put(Integer.toString(i), files[i].getName());
        response.getResultTable().putAll(res);
    }
    
    protected void getDefinedPrinters() {
        execLog.msg("Request getDefinedPrinters");
        try {
            FPDatabase db = new FPDatabase();
            List<FPrinter> printers = db.getPrinters();
            StrTable res = new StrTable();
            for (FPrinter fp : printers) {
                res.put(fp.getRefId(), fp.getModelID()+"/"+fp.getName());
//                fp.getParams().getList();
            }
            response.getResultTable().putAll(res);
        } catch (SQLException ex) {
            execLog.error(ex.getMessage());
        }
    }
    
    protected void getLogFileContent() {
        execLog.msg("Request getLogFileContent");
        StrTable res = new StrTable();
        StrTable namedArgs = pRequest.getNamedArguments();
        String FileName = "";
        if (namedArgs.containsKey("FileName")) {
            FileName = namedArgs.get("FileName");
        }
        if (FileName.length() == 0) {
            // Select last log file
            File[] files = getLogFiles();
            if (files.length > 0)
                FileName = files[files.length-1].getName();
        }    
        if (FileName.length() > 0) {
            // get relative file name
            FileName = Paths.get(FileName).getFileName().toString();
            res.put(FileName, Base64.getEncoder().encodeToString(readLogFile(FileName).getBytes(Charset.forName("UTF-8"))));
        }
        response.getResultTable().putAll(res);
    }
    
	protected void flushPrinterPool() {
        execLog.msg("Request flushPrinterPool");
        FPPrinterPool.clear();
        execLog.msg("Printer pool was flushed.");
        response.getResultTable().put("Result", "OK");
    }

	protected void updateSettings() {
        execLog.msg("Request updateSettings");
        Properties prop = FPServer.application.getAppProperties();
		boolean SaveSettings = false;
        StrTable namedArgs = pRequest.getNamedArguments();
        if (namedArgs.containsKey("SaveSettings")) {
            SaveSettings = namedArgs.get("SaveSettings").equals("1");
        }
		String[] LoggerNameList = {"LLDevice", "LLProtocol", "LLFPCBase", "LLCBIOService"};
		for(String LoggerName : LoggerNameList) {
			if (namedArgs.containsKey(LoggerName)) {
				try {
			        execLog.msg(LoggerName+"="+namedArgs.get(LoggerName));
					prop.setProperty(LoggerName, Level.parse(namedArgs.get(LoggerName)).getName());
				} catch(Exception ex) {
					execLog.error(ex.getMessage());
				}
			}
		}
		String[] BoolParamsList = {"PoolEnabled", "ZFPLabServerAutoStart"};
		for(String ParamName : BoolParamsList) {
			if (namedArgs.containsKey(ParamName)) {
				String value = namedArgs.get(ParamName);
				execLog.msg(ParamName+"="+value);
				if (value.equals("1") || value.equals("0")) {
					prop.setProperty(ParamName,value);
				} else {
					execLog.error(ParamName+" Ivalid value "+value+"!");
				}
			}
		}
		if (namedArgs.containsKey("PoolDeadtime")) {
			Integer value = namedArgs.getInt("PoolDeadtime");
	        execLog.msg("PoolDeadtime="+value);
			prop.setProperty("PoolDeadtime",value.toString());
		}
        execLog.msg("SaveSettings="+(SaveSettings?"1":"0"));
		FPServer.application.updateProperties(SaveSettings);
        response.getResultTable().put("Result", "OK");
	}
	
    @Post("json:json")
    public PrintResponse processCommand(PrintRequest request) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        PrintRequest pReq = mapper.readValue(pRequest, PrintRequest.class);
		Instant processCommandStart = Instant.now();
        pRequest = request;
        response = new PrintResponse();
        response.setId(request.getId());
        attachExecLogHandler();
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
                    case "printduplicatecheckbydocnum" :
                        printDuplicateCheckByDocNum();
                        break;
                    case "currentcheckinfo" :
                        currentCheckInfo();
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
                    case "getdocinfo" :
                        getDocInfo();
                        break;
                    case "printerstatus" :
                        printerStatus();
                        break;
                    case "getdiagnosticinfo" :
                        getDiagnosticInfo();
                        break;
                    case "readpaymentmethods" :
                        readPaymentMethods();
                        break;
                    case "readdepartments" :
                        readDepartments();
                        break;
                    case "readtaxgroups" :
                        readTaxGroups();
                        break;
                    case "getversion" :
                        getVersion();
                        break;
                    case "getcomports" :
                        getCommPorts();
                        break;
                    case "getlogfileslist" :
                        getLogFilesList();
                        break;
                    case "getlogfile" :
                        getLogFileContent();
                        break;
                    case "getdefinedprinters" :
                        getDefinedPrinters();
                        break;
                    case "flushprinterpool" :
                        flushPrinterPool();
                        break;
                    case "updatesettings" :
                        updateSettings();
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
                e.printStackTrace();
            } catch (Exception e) {
                execLog.error("Error:"+e.getMessage());
                execLog.error("Error:"+Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            } finally {
                donePrinter();
            }    
        } catch (FPException  e) {
            response.setErrorCode(e.getErrorCode());
            execLog.error("FPError Code:"+e.getErrorCode()+" Message:"+e.getMessage());
        } catch (Exception  e) {
            execLog.error("Error:"+e.getMessage());
        }
		Instant processCommandEnd = Instant.now();
		double timeElapsed = Duration.between(processCommandStart, processCommandEnd).toMillis()/(double)1000;
		execLog.msg("Command processed in "+Double.toString(timeElapsed)+" s");
        detachExecLogHandler();
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
