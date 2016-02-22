/*
 * Copyright (C) 2015 nikolabintev@edabg.com
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
import com.taliter.fiscal.device.daisy.DaisyFiscalDevice;
import com.taliter.fiscal.device.daisy.DaisyFiscalPrinter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikolabintev@edabg.com
 */
public class FPCDaisyICL extends FPCBase {

    private DaisyFiscalPrinter FP;
    private String lastCommand;
    private int lastErrorCode;
    private String lastErrorMsg;
    
    private HashMap<String, String[]> printerStatus;
    
    /**
     * FPCDaisy constructor.
     * @throws Exception Throws an exception
     */
    public FPCDaisyICL() throws Exception {
        super();
        
        lastCommand = "";
        lastErrorCode = 0;
        lastErrorMsg = "";
    }
    
    public static String getClassDecription() {
        return "(BETA Version!) This class supports Daisy fiscal printers via raw ICL serial communication protocol!";
    }
    
    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(Integer.class, "COM", "Com port", "Com port number", 1));
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
                        , DaisyFiscalPrinter.RXTX
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put(DaisyFiscalPrinter.RXTX, "RXTX library");
                                    put(DaisyFiscalPrinter.SERIAL, "SERIAL library");
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
        this.FP = new DaisyFiscalPrinter("COM" + getParam("COM"), Integer.parseInt(getParam("BaudRate")), getParam("portSourceLib"));
    }
    
    @Override
    public void destroy() {
        try {
            FP.close();
        } catch (Exception ex) {
            Logger.getLogger(FPCDaisyICL.class.getName()).log(Level.SEVERE, null, ex);
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
        lastCommand = "cancelFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_CANCEL_FISCAL_CHECK);
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
        lastCommand = "printDiagnosticInfo - " + Integer.toString(DaisyFiscalDevice.CMD_PRINT_DIAGNOSTIC_INFO);

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
        lastCommand = "getLastPrintDoc - " + Integer.toString(DaisyFiscalDevice.CMD_LAST_DOC_NUM);
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
                        if (tSum > 0)
                            try {
                                FP.cmdSell("-", "", key, key, "+", Double.toString(-tSum), "", true);
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
                        FP.cmdTotal("Общо", "", paymenTypeChar(paymentTypes.CASH), Double.toString(tSum));
                    } catch (FiscalDeviceIOException ex) {
                        lastErrorCode = -1;
                        lastErrorMsg = ex.getMessage();
//                        throw createException();       
                    }
                }    
                // close fiscal check
                lastCommand = "closeFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_CLOSE_FISCAL_CHECK);
                try {
                    FP.cmdCloseFiscalCheck();
                } catch (FiscalDeviceIOException ex) {
                    lastErrorCode = -1;
                    lastErrorMsg = ex.getMessage();
                }
            } else {
                //close nonfiscal check
                lastCommand = "closeNonFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_CLOSE_NONFISCAL_CHECK);
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
        lastCommand = "cUSTOM_CMD("+CMD+")";
        String output = "";
        
        try {
            output = FP.customCmd(Integer.parseInt(CMD), params);
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
        
        lastCommand = "setOperator - " + Integer.toString(DaisyFiscalDevice.CMD_SET_OPERATOR);
        
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
        
        lastCommand = "openFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_OPEN_FISCAL_CHECK);
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

        lastCommand = "closeFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_CLOSE_FISCAL_CHECK);
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

        lastCommand = "sell - " + Integer.toString(DaisyFiscalDevice.CMD_SELL);
        
        try {
            FP.cmdSell(lines[0], lines.length > 1 ? lines[1] : "", this.taxGroupToChar(taxCode), "+", Double.toString(price), quantity > 0 ? Double.toString(quantity) : "1", Double.toString(discountPerc), true);
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

        lastCommand = "sellDept - " + Integer.toString(DaisyFiscalDevice.CMD_SELL_DEPT);
        
        try {
            FP.cmdSellDept("+", deptCode, Double.toString(price), quantity > 0 ? Double.toString(quantity) : "1", Double.toString(discountPerc), true);
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }
    
    @Override
    public void printFiscalText(String text) throws FPException {
        lastCommand = "printFiscalText - " + Integer.toString(DaisyFiscalDevice.CMD_PRINT_FISCAL_TEXT);
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
        lastCommand = "printNonFiscalText - " + Integer.toString(DaisyFiscalDevice.CMD_PRINT_NONFISCAL_TEXT);
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
        
        lastCommand = "subTotal - " + Integer.toString(DaisyFiscalDevice.CMD_SUBTOTAL);
        try {
            response = FP.cmdSubTotal(toPrint, toDisplay, Double.toString(discountPerc));
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
        
        lastCommand = "total - " + Integer.toString(DaisyFiscalDevice.CMD_TOTAL);
        
        try {
            response = FP.cmdTotal(lines[0], lines.length > 1 ? lines[1] : "", paymenTypeChar(payType), Double.toString(payAmount));
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
        
        //Current check info.
        lastCommand = "getCurrentCheckInfo - " + Integer.toString(DaisyFiscalDevice.CMD_CURRENT_CHECK_INFO);
        try {
            response = FP.cmdCurrentCheckInfo();
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        StrTable res = new StrTable();
        
        res.putAll(response);
        
        response.clear();
        
        //Last printed doc number.
        lastCommand = "lastPrintedDocNumber - " + Integer.toString(DaisyFiscalDevice.CMD_LAST_DOC_NUM);
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
        lastCommand = "lastFiscalRecord - " + Integer.toString(DaisyFiscalDevice.CMD_LAST_FISCAL_RECORD);
        try {
            response = FP.cmdLastFiscalRecord("N");
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
//            throw createException();       
        }
        
        res.put("LFRI_DocNum", response.containsKey("Number")?response.get("Number"):"");
        res.put("LFRI_DateTime_", response.containsKey("Date")?response.get("Date"):"");
        
//        response.clear();
        
        // "cMD_86_0_0"
        
        return res;
    }
    
    @Override
    public void openNonFiscalCheck() throws FPException {
        lastCommand = "openNonFiscalChek - " + Integer.toString(DaisyFiscalDevice.CMD_OPEN_NONFISCAL_CHECK);
        
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
        lastCommand = "closeNonFiscalCheck - " + Integer.toString(DaisyFiscalDevice.CMD_CLOSE_NONFISCAL_CHECK);
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
        
        lastCommand = "getPrinterStatus - " + Integer.toString(DaisyFiscalDevice.CMD_PRINTER_STATUS);
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
        lastCommand = "getDiagnosticInfo - " + Integer.toString(DaisyFiscalDevice.CMD_DIAGNOSTIC_INFO);
        
        try {
            response = FP.cmdDiagnosticInfo(false);
            
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
        lastCommand = "reportDaily - " + Integer.toString(DaisyFiscalDevice.CMD_REPORT_DAILY);
        
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

        lastCommand = "reportByDates - " + Integer.toString(DaisyFiscalDevice.CMD_REPORT_BY_DATE_SHORT);
        
        boolean detailed = false;
        
        if (reportType == datesReportType.DETAIL) {
            lastCommand = Integer.toString(DaisyFiscalDevice.CMD_REPORT_BY_DATE);
            detailed = true;
        }

        try {
            FP.cmdReportByDates(detailed, dateFormat.format(from), dateFormat.format(to));
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        lastCommand = "getDateTime - " + Integer.toString(DaisyFiscalDevice.CMD_DATETIME_INFO);
        
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
        lastCommand = "setDateTime - " + Integer.toString(DaisyFiscalDevice.CMD_SET_DATETIME);
        
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
        lastCommand = "paperFeed - " + Integer.toString(DaisyFiscalDevice.CMD_PAPER_FEED);
        
        try {
            FP.cmdPaperFeed(Integer.toString(lineCount));
        } catch (FiscalDeviceIOException ex) {
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "printLastCheckDuplicate - " + Integer.toString(DaisyFiscalDevice.CMD_PRINT_CHECK_DUBLICATE);
        
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
}
