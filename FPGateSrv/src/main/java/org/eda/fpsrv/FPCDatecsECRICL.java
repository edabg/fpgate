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
package org.eda.fpsrv;

import com.taliter.fiscal.device.FiscalDeviceIOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eda.fiscal.device.DatecsECRPrinter;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCDatecsECRICL extends FPCBase {

    private DatecsECRPrinter FP;
    private String lastCommand;
    private int lastErrorCode;
    private String lastErrorMsg;
    
    private HashMap<String, String[]> printerStatus;
    
    /**
     * FPCDatecsECRICL constructor.
     * @throws Exception Throws an exception
     */
    public FPCDatecsECRICL() throws Exception {
        super();
        
        lastCommand = "";
        lastErrorCode = 0;
        lastErrorMsg = "";
    }
    
    public static String getClassDecription() {
        return "(BETA Version!) This class supports Datecs ECR via raw ICL serial communication protocol!";
    }
    
    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(String.class, "COM", "Com port", "Com port number", "COM1"));
                     addProperty(new FPProperty(
                        Integer.class
                        , "BaudRate", "Baud rate", "Com port baud rate"
                        , 9600
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
                     
                     addProperty(new FPProperty(
                        String.class
                        , "portSourceLib", "Port source library", "Port source library"
                        , DatecsECRPrinter.RXTX
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put(DatecsECRPrinter.RXTX, "RXTX library");
                                    put(DatecsECRPrinter.SERIAL, "SERIAL library");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 38));
                }}

                , new FPPropertyGroup("net", "Network Options") {{
                     addProperty(new FPProperty(String.class, "IPAddr", "IP Address", "IP Address", ""));
                     addProperty(new FPProperty(Integer.class, "IPPort", "IP Port", "IP Port", 0));
                }}     
        );
        return params;
    }

    @Override
    protected int getLineWidth() {
        int lw = getParamInt("LWIDTH");
        if (lw == 0) 
            lw = 38;
       return lw; 
    }

    @Override
    public void init() throws Exception, FPException {
        
        lastCommand = "Init";
        this.FP = new DatecsECRPrinter(getParam("COM"), Integer.parseInt(getParam("BaudRate")), getParam("portSourceLib"));
    }
    
    @Override
    public void destroy() {
        try {
            FP.close();
        } catch (Exception ex) {
            Logger.getLogger(FPCDatecsECRICL.class.getName()).log(Level.SEVERE, null, ex);
        }
        FP = null;
    }

    
    /**
     * Initialize printer status hash map.
     * @return 
     */
    protected FPException createException() {
        if (FP != null ) {
            return new FPException((long)lastErrorCode, getLastErrorMessage());
        } else {
            return new FPException((long)-1, "The FP object is not initialized!");
        }
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
        } catch (FiscalDeviceIOException ex) {
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
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public String getLastPrintDoc() throws FPException {
        lastCommand = "getLastPrintDoc";
        String lastDocNum = "";
        try {
            lastDocNum = Integer.toString(FP.cmdLastDocNum());
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }

        return lastDocNum;
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
/*		'P'	- Плащане в брой (по подразбиране);
		'N'	- Плащане с кредит;
		'C'	- Плащане с чек;
		'D'	- Плащане с дебитна карта
		'I'	- Програмируем тип плащане 1
		'J'	- Програмируем тип плащане 2
		'K'	- Програмируем тип плащане 3
		'L'	- Програмируем тип плащане 4
*/        
        String pc = "P";
        switch (payType) {
            case CASH : pc = "P"; break;
            case CREDIT : pc = "N"; break;
            case CHECK : pc = "C"; break;
            case DEBIT_CARD : pc = "D"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;
            case CUSTOM3 : pc = "K"; break;
            case CUSTOM4 : pc = "L"; break;
        }
        return pc;
    }
    
    @Override
    public void abnormalComplete() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        // First Try to Cancel Fiscal Receipt
        try {
            FP.cmdCancelFiscalCheck();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        try {
            response = FP.cmdCurrentCheckInfo();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
        }
        
        if(lastErrorCode == 0) {
            if(!response.isEmpty() && !response.get("CanVoid").equals("0")) {

                double tSum; 
                for(String key : response.keySet()) {
                    if(!key.equals("CanVoid") && !key.equals("InvoiceFlag") && !key.equals("InvoiceNo")) {
                        tSum = Double.parseDouble(response.get(key).toString());
                        try {
                            FP.cmdSell("Cancel", "", key, -tSum, 0, 0, true);
                        } catch (FiscalDeviceIOException ex) {
                            lastErrorCode = -1;
                            lastErrorMsg = ex.getMessage();
//                            throw createException();       
                        }
                        
                    }
                }
                
                response.clear();
                
                try {
                    // try to pay rests
                    response = FP.cmdCurrentCheckInfo();
                } catch (FiscalDeviceIOException ex) {
                    lastErrorCode = -1;
                    lastErrorMsg = ex.getMessage();
//                    throw createException();       
                }
                
                
                tSum = 0;
                for(String key : response.keySet()) {
                    if(!key.equals("CanVoid") && !key.equals("InvoiceFlag") && !key.equals("InvoiceNo")) {
                        tSum += Double.parseDouble(response.get(key).toString());
                    }
                }
                              
                // try to pay in cache
                if (tSum > 0) {
                    try {
                        FP.cmdTotal("Общо", "", paymenTypeChar(paymentTypes.CASH), tSum);
                    } catch (FiscalDeviceIOException ex) {
                        lastErrorCode = -1;
                        lastErrorMsg = ex.getMessage();
//                        throw createException();       
                    }
                }    
                // close fiscal check
                lastCommand = "closeFiscalCheck";
                try {
                    FP.cmdCloseFiscalCheck();
                } catch (FiscalDeviceIOException ex) {
                    lastErrorCode = -1;
                    lastErrorMsg = ex.getMessage();
                }
            } else {
                //close nonfiscal check
                lastCommand = "closeNonFiscalCheck";
                try {
                    FP.cmdCloseNonFiscalCheck();
                } catch (FiscalDeviceIOException ex) {
                    lastErrorCode = -1;
                    lastErrorMsg = ex.getMessage();
                }
            }
        } else {
        }
    }
    
    @Override
    public String customCmd(String CMD, String params) throws FPException {
        String input = params.replace("\\t", "\t");
        lastCommand = "cUSTOM_CMD("+CMD+")";
        String output = "";
        
        try {
            output = FP.customCmd(Integer.parseInt(CMD), input);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        return output;
    }

    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        if (code.length() == 0)
           code = getParam("OperatorCode");
        if (passwd.length() == 0)
           passwd = getParam("OperatorPass");
        
        lastCommand = "setOperator";
        
        try {
            FP.cmdSetOperator(code, passwd, fullName);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
    }

    
    @Override
    public void openFiscalCheck() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheck";
        try {
            response = FP.cmdOpenFiscalCheck(params.get("OperatorCode", "1"), params.get("OperatorPass", "0000"), false);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
    }

    @Override
    public void closeFiscalCheck() throws FPException{
        LinkedHashMap<String, String> response = new LinkedHashMap();

        lastCommand = "closeFiscalCheck";
        try {
            response = FP.cmdCloseFiscalCheck();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        String[] lines = splitOnLines(text);

        lastCommand = "sell";
        
        try {
            FP.cmdSell(lines[0], lines.length > 1 ? lines[1] : "", this.taxGroupToChar(taxCode), price, quantity, discountPerc, true);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        String[] lines = splitOnLines(text);

        lastCommand = "sellDept";
        
        try {
            FP.cmdSellDept(lines[0], lines.length > 1 ? lines[1] : "", deptCode, price, quantity, discountPerc, true);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public void printFiscalText(String text) throws FPException {
        lastCommand = "printFiscalText";
        String[] lines = splitOnLines(text);
        
        for (String line : lines) {
            try {
                FP.cmdPrintFiscalText(line);
            } catch (FiscalDeviceIOException ex) {
                lastErrorCode = -1;
                lastErrorMsg = ex.getMessage();
                throw createException();       
            }
        }    
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        lastCommand = "printNonFiscalText";
        String[] lines = splitOnLines(text);
        
        for (String line : lines) {
            try {
                FP.cmdPrintNonFiscalText(line);
            } catch (FiscalDeviceIOException ex) {
                lastErrorCode = -1;
                lastErrorMsg = ex.getMessage();
                throw createException();       
            }
        }    
    }
    
    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "subTotal";
        try {
            response = FP.cmdSubTotal(toPrint, toDisplay, discountPerc, true);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        StrTable res = new StrTable();

        res.putAll(response);
        
        return res;
    }

    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String[] lines = splitOnLines(text);
        
        lastCommand = "total";
        
        try {
            response = FP.cmdTotal(lines[0], lines.length > 1 ? lines[1] : "", paymenTypeChar(payType), payAmount);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }

        StrTable res = new StrTable();
        
        res.putAll(response);
        
        return res;
    }

    
    @Override
    public StrTable getCurrentCheckInfo() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        StrTable res = new StrTable();
        //Current check info.
        lastCommand = "getCurrentCheckInfo";
        try {
            response = FP.cmdCurrentCheckInfo();
            res.putAll(response);
            String[] TaxGr = {"TaxA","TaxB","TaxC","TaxD", "TaxE", "TaxF", "TaxG", "TaxH"};
            for(String tg : TaxGr) 
                res.put(tg, response.containsKey(tg)?response.get(tg):"");
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        //Last printed doc number.
        lastCommand = "lastPrintedDocNumber";
        int lastDoc = 0;
        try {
            lastDoc = FP.cmdLastDocNum();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        
        res.put("LastPrintDocNum", Integer.toString(lastDoc));
        
        //Last fiscal record.
        lastCommand = "lastFiscalRecord";
        response.clear();
        try {
            response = FP.cmdLastFiscalRecord();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        res.put("LFRI_DocNum", response.containsKey("Number")?response.get("Number"):"");
        res.put("LFRI_DateTime_", response.containsKey("Date")?response.get("Date"):"");
        if (res.get("LFRI_DateTime_").length() == 6) {
            // ddmmyy
            String dt = res.get("LFRI_DateTime_");
            res.put("LFRI_DateTime", dt.substring(0,2)+"-"+dt.substring(2,4)+"-20"+dt.substring(4));
        } else
            res.put("LFRI_DateTime", res.get("LFRI_DateTime_"));
        String[] TaxGr = {"TaxA","TaxB","TaxC","TaxD", "TaxE", "TaxF", "TaxG", "TaxH"};
        for(String tg : TaxGr) 
            res.put("LFRI_"+tg, response.containsKey(tg)?response.get(tg):"");
//        response.clear();
        
        // "cMD_86_0_0"
        
        return res;
    }
    
    @Override
    public void openNonFiscalCheck() throws FPException {
        lastCommand = "openNonFiscalChek";
        
        try {
             FP.cmdOpenNonFiscalCheck();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        lastCommand = "closeNonFiscalCheck";
        try {
            FP.cmdCloseNonFiscalCheck();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        StrTable res = new StrTable();
        
        lastCommand = "getPrinterStatus";
        try {
            response = FP.cmdPrinterStatus();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
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
            
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        StrTable res = new StrTable();
        res.putAll(response);
        return res;
    }

    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String item = reportType == dailyReportType.Z ? "0" : "2";
        lastCommand = "reportDaily";
        
        try {
            response = FP.cmdReportDaily(item, true);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        StrTable res = new StrTable();

        res.putAll(response);
        
        return res;
    }
    
    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        StrTable res = new StrTable();

        lastCommand = "reportByDates";
        
        boolean detailed = false;
        
        if (reportType == datesReportType.DETAIL) {
            detailed = true;
        }

        try {
            FP.cmdReportByDates(detailed, from, to);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        lastCommand = "getDateTime";
        
        Date dateTime = null;
        try {
            dateTime = FP.cmdGetDateTime();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        lastCommand = "setDateTime";
        
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        
        try {
            FP.cmdSetDateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        lastCommand = "paperFeed";
        
        try {
            FP.cmdPaperFeed(lineCount);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "printLastCheckDuplicate";
        
        try {
            FP.cmdPrintCheckDuplicate();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return getCurrentCheckInfo();
    }

    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        lastCommand = "cashInOut";
        StrTable res = new StrTable();
        try {
            LinkedHashMap<String,String> res_ = FP.cmdCashInOut(ioSum);
            res.putAll(res_);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        return res;
    }

    
}
