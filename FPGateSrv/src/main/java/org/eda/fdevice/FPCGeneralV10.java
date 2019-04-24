/*
 * Copyright (C) 2016 EDA Ltd.
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
package org.eda.fdevice;

import java.io.IOException;
import static java.lang.Math.abs;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import org.eda.protocol.AbstractFiscalDevice;
import org.eda.protocol.FDException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.restlet.engine.util.DateUtils;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "General implementation class for AbstractFiscalDevice"
    , usable = false
)
public class FPCGeneralV10 extends FPCBase {

    protected AbstractFiscalDevice FP;
    protected String lastCommand;
    protected int lastErrorCode;
    protected String lastErrorMsg;
    
    protected CheckInfo lastCurrentCheckInfo = null;
    protected Date dtAfterOpenFiscalCheck = null;
    
    static {
        AbstractFiscalDevice.getLogger().addHandler(new LogHandler());
    }
    
    /**
     * FPCDatecsECRICL constructor.
     * @throws Exception Throws an exception
     */
    public FPCGeneralV10() throws Exception {
        super();
        
        lastCommand = "";
        lastErrorCode = 0;
        lastErrorMsg = "";
        
        
    }

    // Catch logs from AbstractFiscalDevice
    private static class LogHandler extends Handler {
//        private ControlPanel panel;

        public LogHandler() {}

//        public LogHandler(ControlPanel panel) {
//            this.panel = panel;
//        }

        @Override
        public void publish(LogRecord lr) {
            if ((lr.getLevel() == Level.WARNING) || (lr.getLevel() == Level.SEVERE)) {
                logger.log(lr);
            }
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
    
    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(String.class, "COM", "Com port", "Com port number", "COM1"));
                     addProperty(new FPProperty(
                        Integer.class
                        , "BaudRate", "Baud rate", "Com port baud rate"
                        , 115200
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put(4800, "4800");
                                    put(9600, "9600");
                                    put(19200, "19200");
                                    put(38400, "38400");
                                    put(57600, "57600");
                                    put(115200, "115200");
                                    put(230400, "230400");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(Integer.class, "PortReadTimeout", "Port Read timeout (ms)", "Port Read timeout (ms)", 500));
                     addProperty(new FPProperty(Integer.class, "PortWriteTimeout", "Port Write timeout (ms)", "Port Write timeout (ms)", 500));
                     
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(String.class, "PMCash", "Payment code for cash", "Payment code for cash", "P"));
                     addProperty(new FPProperty(String.class, "PMCard", "Payment code for debit/credit card", "Payment code for debit/credit card", "C"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width Fiscal", "Line width for fiscal text in characters", 36));
                     addProperty(new FPProperty(Integer.class, "LWIDTHN", "Line width Nonfiscal", "Line width for nonfiscal text in characters", 40));
                }}

        );
        return params;
    }

    @Override
    protected int getLineWidth() {
        int lw = getParamInt("LWIDTH");
        if (lw == 0) 
            lw = 36;
       return lw; 
    }

    protected int getLineWidthFiscalText() {
        int lw = FP.getLineWidthFiscalText();
        if (lw <= 0)
            lw = getParamInt("LWIDTH");
        if (lw <= 0) 
            lw = 36;
        return lw;
    }

    protected int getLineWidthNonFiscalText() {
        int lw = FP.getLineWidthNonFiscalText();
        if (lw <= 0)
            lw = getParamInt("LWIDTHN");
        if (lw <= 0) 
            lw = 36;
        return lw;
    }
    
    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
//        this.FP = new DeviceDatecsDPV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
//        this.FP.open();
    }
    
    @Override
    public void destroy() {
        try {
            if (FP != null)
                FP.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        FP = null;
    }

    @Override
    public DeviceInfo getDeviceInfo() throws FPException {
        DeviceInfo di = new DeviceInfo();
        di.Model = FP.getModelName();
        di.Firmware = FP.getFWRev();
        di.SerialNum = FP.getSerialNum();
        di.FMNum = FP.getFMNum();
        di.IsFiscalized = FP.isFiscalized();
        di.IsReady = 
            !FP.isOpenFiscalCheck()
            && !FP.isOpenNonFiscalCheck()
            && !FP.isOpenFiscalCheckRev();
        return di;
    }
    
    protected String taxGroupToChar(taxGroup taxCode) {
        String res; 
        switch (taxCode) {
            case A : res = "А"; break;
            case B : res = "Б"; break;
            case C : res = "В"; break;
            case D : res = "Г"; break;
            case E : res = "Д"; break;
            case F : res = "Е"; break;
            case G : res = "Ж"; break;
            case H : res = "З"; break;
            default :
                res = "А";
        }
        return res;
    }
    
    protected String taxGroupToCharL(taxGroup taxCode) {
        String res; 
        switch (taxCode) {
            case A : res = "A"; break;
            case B : res = "B"; break;
            case C : res = "C"; break;
            case D : res = "D"; break;
            case E : res = "E"; break;
            case F : res = "F"; break;
            case G : res = "G"; break;
            case H : res = "H"; break;
            default :
                res = "A";
        }
        return res;
    }
    
    protected String paymenTypeChar(paymentTypes payType) {
        /*		
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ - Плащане с кредит;
        ‘C’ - Плащане с дебитна карта;
        ‘D’ - Плащане с НЗОК;
        ‘I’ - Плащане с ваучер;
        ‘J’ - Плащане с купон;
        */        
        String pc = "P";
        switch (payType) {
            case CASH : pc = "P"; break;
            case CREDIT : pc = "N"; break;
            case CARD : pc = "C"; break;
            case CHECK : pc = "D"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;
        }
        return pc;
    }

    protected String revTypeChar(fiscalCheckRevType revType) {
        String rt = "0";
        switch (revType) {
            case OP_ERROR :
                rt = "0";
                break;
            case RETURN_CLAIM :
                rt = "1";
                break;
            case REDUCTION :
                rt = "2";
                break;
        }
        return rt;
    }
    
    protected String[] dailyReportTypeToOptions(dailyReportType drType) {
        String[] options = new String[]{"2",""};
        
        switch (drType) {
            case Z :
               options[0] = "0";
               options[1] = "";
               break;
            case ZD :
               options[0] = "0";
               options[1] = "D";
               break;
            case X :
               options[0] = "2";
               options[1] = "";
               break;
            case XD :
               options[0] = "2";
               options[1] = "D";
               break;
        }
        return options;
    }
    
    protected FPException createException() {
        return new FPException((long)lastErrorCode, lastCommand+":"+getLastErrorMessage());
    }

    protected FPException createException(int errorCode, String errorMsg) {
        lastErrorCode = errorCode;
        lastErrorMsg = errorMsg;
        return createException();
    }

    protected FPException createException(IOException ex) {
        return createException((ex instanceof FDException)?Math.toIntExact(((FDException)ex).getErrorCode()):-1, ex.getMessage());
    }

    @Override
    public Long getLastErrorCode() {
        return (long)lastErrorCode;
    }

    @Override
    public String getLastErrorMessage() {
        return this.lastErrorMsg;
    }

    
    @Override
    public void cancelFiscalCheck() throws FPException {
        lastCommand = "cancelFiscalCheck";
        try {
            FP.cmdCancelFiscalCheck();
        } catch (IOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public void printDiagnosticInfo() throws FPException {
        lastCommand = "printDiagnosticInfo";

        try {
            FP.cmdPrintDiagnosticInfo();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public String getLastPrintDoc() throws FPException {
        lastCommand = "getLastPrintDoc";
        String lastDocNum = "";
        try {
            lastDocNum = FP.cmdLastDocNum();
        } catch (IOException ex) {
            throw createException(ex);
        }
        return lastDocNum;
    }

    @Override
    public void abnormalComplete() throws FPException {
        // First Try to Cancel Fiscal Receipt
            lastCommand = "abnormalComplete";
        try {
            if (FP.isOpenNonFiscalCheck()) {
                // Cance non-fiscal check
                FP.cmdCloseNonFiscalCheck();
            } else if (FP.isOpenFiscalCheck() || FP.isOpenFiscalCheckRev()) {
                // opened fiscal check
                // first try to cancel
                try {
                    FP.cmdCancelFiscalCheck();
                } catch (Exception ex) {
                    logger.warning(ex.getMessage());
                }
                // if it is continue to be open try to pay rest amount and close
                if (FP.isOpenFiscalCheck() || FP.isOpenFiscalCheckRev()) {
                    try {
                        FP.cmdPrintFiscalText("АВАРИЙНО ЗАТВАРЯНЕ!");
                        FP.cmdTotal("Аварийно плащане", "", 0, "");
                    } catch (Exception ex) {
                        logger.warning(ex.getMessage());
                    }
                    try {
                        FP.cmdCloseFiscalCheck();
                    } catch (Exception ex) {
                        logger.warning(ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
//            throw createException(ex);
        }
    }
    
    @Override
    public String customCmd(String CMD, String params) throws FPException {
        String input = params.replace("\\t", "\t");
        lastCommand = "customCmd("+CMD+", '"+input.replace("\n", "\\n").replace("\t", "\\t")+"')";
        String output = "";
        
        try {
            output = FP.cmdCustom(Integer.parseInt(CMD), input);
            if (FP.getErrors().size() > 0)
                throw new FPException(FP.getErrors().toString());
        } catch (IOException ex) {
            throw createException(ex);
        }
        
        return output;
    }

    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        if (code.length() == 0)
           code = getParam("OperatorCode");
        else
           setParam("OperatorCode", code);
        if (passwd.length() == 0)
           passwd = getParam("OperatorPass");
        else
           setParam("OperatorPass", passwd);
        
        lastCommand = "setOperator";
        
        try {
            FP.cmdSetOperator(code, passwd, fullName);
        } catch (IOException ex) {
            throw createException(ex);
        }
        
    }

    
    @Override
    public void openFiscalCheck() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheck";
        try {
            response = FP.cmdOpenFiscalCheck(params.get("OperatorCode", "1"), params.get("OperatorPass", "1"), params.get("TillNumber"), "",  invoiceMode);
            try {
                dtAfterOpenFiscalCheck = FP.cmdGetDateTime();
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void openFiscalCheck(String UNS) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheckUNS";
        try {
            response = FP.cmdOpenFiscalCheck(params.get("OperatorCode", "1"), params.get("OperatorPass", "1"), params.get("TillNumber", "1"), UNS,  invoiceMode);
            try {
                dtAfterOpenFiscalCheck = FP.cmdGetDateTime();
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public void openFiscalCheckRev(String UNS, fiscalCheckRevType RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, Date RevInvDate) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheck";
        try {
            response = FP.cmdOpenFiscalCheckRev(
                params.get("OperatorCode", "1"),  params.get("OperatorPass", "1"),  params.get("TillNumber", "1")
                , UNS, revTypeChar(RevType), RevDocNum, RevUNS, RevDateTime, RevFMNum, RevReason, RevInvNum, invoiceMode);
            try {
                dtAfterOpenFiscalCheck = FP.cmdGetDateTime();
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            throw createException(ex);
        }
    }


    

    @Override
    public void closeFiscalCheck() throws FPException{
        lastCommand = "closeFiscalCheck";
        if (invoiceMode) {
            int uicType = 0; // Bulstat
            if (customer.UICType.equals(FPCBase.UICTypes.EGN))
                uicType = 1;
            else if (customer.UICType.equals(FPCBase.UICTypes.FOREIGN))
                uicType = 2;
            else if (customer.UICType.equals(FPCBase.UICTypes.NRA))
                uicType = 3;
            try {
               FP.cmdPrintCustomerData(customer.UIC, uicType, customer.seller, customer.recipient, customer.buyer, customer.VATNumber, customer.address);
            } catch (IOException ex) {
                throw createException(ex);
            }
        }
        try {
            FP.cmdCloseFiscalCheck();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        String[] lines = splitOnLines(text, getLineWidthFiscalText());
        lastCommand = "sell";
        
        try {
            FP.cmdSell(
                lines[0]+((lines.length > 1)?"\n"+lines[1]:"")
                , this.taxGroupToChar(taxCode), price, quantity
                , ""
                , (abs(discountPerc) >= 0.01)?Double.toString(discountPerc)+"%":""
            );
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        String[] lines = splitOnLines(text, getLineWidthFiscalText());

        lastCommand = "sellDept";
        
        try {
            FP.cmdSellDept(
                lines[0]+((lines.length > 1)?"\n"+lines[1]:"")
                , deptCode, price, quantity
                , ""
                , (abs(discountPerc) >= 0.01)?Double.toString(discountPerc)+"%":""
            );
        } catch (IOException ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public void printFiscalText(String text) throws FPException {
        lastCommand = "printFiscalText";
        String[] lines = splitOnLines(text, getLineWidthFiscalText());
        
        for (String line : lines) {
            try {
                FP.cmdPrintFiscalText(line);
            } catch (IOException ex) {
                throw createException(ex);
            }
        }    
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        lastCommand = "printNonFiscalText";
        String[] lines = splitOnLines(text, getLineWidthNonFiscalText());
        
        for (String line : lines) {
            try {
                FP.cmdPrintNonFiscalText(line);
            } catch (IOException ex) {
                throw createException(ex);
            }
        }    
    }
    
    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "subTotal";
        try {
            response = FP.cmdSubTotal(toPrint, toDisplay, (abs(discountPerc) >= 0.01)?Double.toString(discountPerc)+"%":"");
        } catch (IOException ex) {
            throw createException(ex);
        }
        StrTable res = new StrTable();

        res.putAll(response);
        
        return res;
    }

    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String[] lines = splitOnLines(text, getLineWidthFiscalText());
        
        lastCommand = "total";
        
        try {
            String strLines = String.join("\n", lines);
            response = FP.cmdTotal(strLines, paymenTypeChar(payType), payAmount, "");
        } catch (IOException ex) {
            throw createException(ex);
        }

        StrTable res = new StrTable();
        
        res.putAll(response);
        
        return res;
    }

    
    @Override
    public CheckInfo getCurrentCheckInfo() throws FPException {
        LinkedHashMap<String, String> response;
        
        CheckInfo res = new CheckInfo();
        //Current check info.
        lastCommand = "getCurrentCheckInfo";
        try {
            response = FP.cmdCurrentCheckInfo();
            res.TaxA = parseDouble(response.get("TaxA"));
            res.TaxB = parseDouble(response.get("TaxB"));
            res.TaxC = parseDouble(response.get("TaxC"));
            res.TaxD = parseDouble(response.get("TaxD"));
            res.TaxE = parseDouble(response.get("TaxE"));
            res.TaxF = parseDouble(response.get("TaxF"));
            res.TaxG = parseDouble(response.get("TaxG"));
            res.TaxH = parseDouble(response.get("TaxH"));
            res.IsOpen = response.get("IsOpen").equals("1");
            res.FCType = fiscalCheckType.UNKNOWN;
            res.IsInvoice = false;
            if (res.IsOpen) {
                if (FP.isOpenFiscalCheck())
                    res.FCType = fiscalCheckType.FISCAL;
                if (FP.isOpenFiscalCheckRev())
                    res.FCType = fiscalCheckType.FISCAL_REV;
                if (FP.isOpenNonFiscalCheck())
                    res.FCType = fiscalCheckType.NON_FISCAL;
                res.IsInvoice = response.get("Inv").equals("1");
            }    
            if (res.IsInvoice)
                res.InvNum = response.get("InvNum");
            res.SellCount = parseInt(response.get("SellCount"));
            res.SellAmount = parseDouble(response.get("SellAmount"));
            res.PayAmount = parseDouble(response.get("PayAmount"));
            try {
                String DocNum = FP.cmdLastDocNum();
                if (res.IsOpen) {
                    Integer iDocNum = Integer.parseInt(DocNum);
                    iDocNum++;
                    DocNum = String.format("%07d", iDocNum);
                }
                res.DocNum = DocNum;
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
            }
        } catch (IOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        lastCurrentCheckInfo = res;
        return res;
    }

    @Override
    public FiscalCheckQRCodeInfo getLastFiscalCheckQRCode() {
        String QR = "";
        try {
            QR = FP.cmdLastFiscalCheckQRCode();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        FiscalCheckQRCodeInfo QRInfo = null;
        if (QR.length() > 0)
            QRInfo = new FiscalCheckQRCodeInfo(QR);
        else {
            // Alternate method to create QRCode
            CheckInfo ci = lastCurrentCheckInfo;
            if (ci == null) {
                try {
                    ci = getCurrentCheckInfo();
                } catch (FPException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }    
            if (ci != null) {
                Date lastDocDate = null;
                try {
                    lastDocDate = FP.cmdLastFiscalCheckDate();
                } catch (IOException ex) {
                }
                QRInfo = new FiscalCheckQRCodeInfo(FP.getFMNum(), ci.DocNum, (lastDocDate != null)?lastDocDate:dtAfterOpenFiscalCheck, ci.SellAmount);
            }    
            
        }
        if (QRInfo == null)
            QRInfo = new FiscalCheckQRCodeInfo();
        return QRInfo;
    }

    
    @Override
    public FiscalRecordInfo getLastFiscalRecordInfo() throws FPException {
        LinkedHashMap<String, String> response;
        
        FiscalRecordInfo res = new FiscalRecordInfo();
                
        //Current check info.
        lastCommand = "getLastFiscalRecordInfo";
        try {
            response = FP.cmdLastFiscalRecord();
            res.DocNum = response.get("DocNum");
            String docDate = response.get("DocDate");
            SimpleDateFormat fmt;
            if (docDate.length() > 10) {
                fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else {
                fmt = new SimpleDateFormat("yyyy-MM-dd");
            }
            try {
                res.DocDate = fmt.parse(docDate);
            } catch (ParseException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
//            res.DocDate = DateUtils.parse(response.get("DocDate"), Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
            res.TaxA = parseDouble(response.get("TaxA"));
            res.TaxB = parseDouble(response.get("TaxB"));
            res.TaxC = parseDouble(response.get("TaxC"));
            res.TaxD = parseDouble(response.get("TaxD"));
            res.TaxE = parseDouble(response.get("TaxE"));
            res.TaxF = parseDouble(response.get("TaxF"));
            res.TaxG = parseDouble(response.get("TaxG"));
            res.TaxH = parseDouble(response.get("TaxH"));
            if (response.containsKey("RevTaxA")) {
                res.RevTaxA = parseDouble(response.get("RevTaxA"));
                res.RevTaxB = parseDouble(response.get("RevTaxB"));
                res.RevTaxC = parseDouble(response.get("RevTaxC"));
                res.RevTaxD = parseDouble(response.get("RevTaxD"));
                res.RevTaxE = parseDouble(response.get("RevTaxE"));
                res.RevTaxF = parseDouble(response.get("RevTaxF"));
                res.RevTaxG = parseDouble(response.get("RevTaxG"));
                res.RevTaxH = parseDouble(response.get("RevTaxH"));
            }    
        } catch (IOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        return res;
    }
    
    
    @Override
    public void openNonFiscalCheck() throws FPException {
        lastCommand = "openNonFiscalChek";
        
        try {
             FP.cmdOpenNonFiscalCheck();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        lastCommand = "closeNonFiscalCheck";
        try {
            FP.cmdCloseNonFiscalCheck();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        StrTable res = new StrTable();
        
        lastCommand = "getPrinterStatus";
        try {
            response = FP.cmdPrinterStatus();
        } catch (IOException ex) {
            throw createException(ex);
        }

        res.putAll(response);
        
        return res;
    }

    @Override
    public StrTable getDiagnosticInfo() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        lastCommand = "getDiagnosticInfo";
        
        try {
            response = FP.cmdDiagnosticInfo();
        } catch (IOException ex) {
            throw createException(ex);
        }
        
        StrTable res = new StrTable();
        res.putAll(response);
        return res;
    }

    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String[] options = dailyReportTypeToOptions(reportType);
        lastCommand = "reportDaily";
        
        try {
            response = FP.cmdReportDaily(options[0], options[1]);
        } catch (IOException ex) {
            throw createException(ex);
        }
        
        StrTable res = new StrTable();

        res.putAll(response);
        
        return res;
    }
    
    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        StrTable res = new StrTable();

        lastCommand = "reportByDates";
        
        boolean detailed = false;
        
        if (reportType == datesReportType.DETAIL) {
            detailed = true;
        }

        try {
            FP.cmdReportByDates(detailed, from, to);
        } catch (IOException ex) {
            throw createException(ex);
        }
        
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        lastCommand = "getDateTime";
        
        Date dateTime = null;
        try {
            dateTime = FP.cmdGetDateTime();
        } catch (IOException ex) {
            throw createException(ex);
        }
        
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        lastCommand = "setDateTime";

        try {
            FP.cmdSetDateTime(dateTime);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        lastCommand = "paperFeed";
        
        try {
            FP.cmdPaperFeed(lineCount);
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "printLastCheckDuplicate";
        
        try {
            FP.cmdPrintCheckDuplicate();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void printCheckDuplicateByDocNum(String docNum)  throws FPException  {
        lastCommand = "printCheckDuplicateByDocNum";
        
        try {
            FP.cmdPrintCheckDuplicateEJ(docNum);
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    
    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        lastCommand = "cashInOut";
        StrTable res = new StrTable();
        try {
            LinkedHashMap<String,String> res_ = FP.cmdCashInOut(ioSum);
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournalInfo() throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournalInfo";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdGetEJInfo();
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournal(Date fromDate, Date toDate) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournal";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadEJ(fromDate, toDate);
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournal(String fromDoc, String toDoc) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournal";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadEJ(fromDoc, toDoc);
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getDocInfo(String docNum) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getDocInfo";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadDocInfo(docNum);
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    
    
    
}
