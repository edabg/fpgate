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

import com.jacob.com.ComThread;
import com.jacob.com.LibraryLoader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.datecs.fpcom.CSFP3530;


/**
 *
 * @author Dimitar Angelov
 */
public class FPCFP3530 extends FPCBase{

    private CSFP3530 FP;
    
    public FPCFP3530() throws Exception {
        super();
    }

    public static String getClassDecription() {
        return "This class supports following Datecs Fiscal Printers: FP3530*,FP550*,FP55*,FP1000*,FP300*,FP60*!";
    }
    
    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(Integer.class, "COM", "Com port", "Com port number", 1));
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
                     addProperty(new FPProperty(Integer.class, "StopBits", "Stop bits", "Stop bits", 0));
                     addProperty(new FPProperty(Integer.class, "Parity", "Parity bit", "Parity bit", 0));
                     addProperty(new FPProperty(Integer.class, "ByteSize", "Byte size", "Byte size", 8));
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 0));
                     addProperty(new FPProperty(
                             Integer.class
                             , "FirmwareType", "Firmware Type", "FirmwareType"
                             , -1
                             , new FPPropertyRule<>(
                                     null, null, true
                                     , new LinkedHashMap() {{
                                         put(-1, "Default");
                                         put(0, "Bulgarian");
                                         put(1, "English");
                                         put(2, "Serbian");
                                         put(3, "Kenyan");
                                     }}
                             )
                     ));
                     addProperty(new FPProperty(
                             Integer.class
                             , "PrinterModel", "Printer Model", "Printer Model"
                             , -1
                             , new FPPropertyRule<>(
                                     null, null, true
                                     , new LinkedHashMap() {{
                                         put(-1, "Default");
                                         put(0, "FP3530,FP550 FVer. 2.00");
                                         put(1, "FP55/PDA");
                                         put(2, "FP1000 FVer 1.00");
                                         put(3, "FP550  FVer 2.50");
                                         put(4, "FP300  FVer 1.10");
                                         put(5, "FP300  FVer 1.50");
                                         put(6, "FP550/3530-05 FVer 4.10");
                                         put(7, "FP300/1000-02 FVer 2.00"); // Daisy ProfiPrint
                                         put(8, "FP60   FVer 1.00");
                                         put(9, "FP1000 KL");
                                         put(10, "FP60 KL");
                                         put(11, "FP550 KL");
                                     }}
                             )
                     ));
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
            switch(getParamInt("PrinterModel")) {
                case 1  : // FP3530,FP550 FVer. 2.00
                case 2  : // FP55/PDA
                case 4  : // FP550  FVer 2.50
                case 5  : // FP300  FVer 1.10
                case 6  : // FP300  FVer 1.50
                case 7  : // FP550/3530-05 FVer 4.10
                case 8  : // FP300/1000-02 FVer 2.00
                case 9  : // FP60   FVer 1.00
                case 11 : // FP60 KL
                case 12 : // FP550 KL
                    lw = 38;
                    break;
                case 3   : // FP1000 FVer 1.00
                case 10  : // FP1000 KL
                    lw = 46;
                    break;
                default : 
                    lw = 38;
            }
       return lw; 
    }
    
    @Override
    public void init() throws Exception, FPException {
        LibraryLoader.loadJacobLibrary();
        ComThread.InitMTA();
        this.FP = new CSFP3530();
        if (getParamInt("PrinterModel") <= 0) {
            FP.init(
                    getParamInt("COM").byteValue()
                    , getParamInt("BaudRate")
                    , getParamInt("StopBits").byteValue()
                    , getParamInt("Parity").byteValue()
                    , getParamInt("ByteSize").byteValue()
            );
        } else {
            FP.iNIT_Ex1(
                    getParamInt("COM").byteValue()
                    , getParamInt("BaudRate")
                    , getParamInt("StopBits").byteValue()
                    , getParamInt("Parity").byteValue()
                    , getParamInt("ByteSize").byteValue()
                    , getParamInt("FirmwareType")
                    , getParamInt("PrinterModel")
            );
        }
    }

    @Override
    public void destroy() {
        ComThread.Release();
        FP = null;
    }
    
    protected FPException createException() {
        if (FP != null )
            return new FPException((long)FP.getLastErrorCode(), FP.getLastErrorMessage());
       else
            return new FPException((long)-1, "Unknown error!");
    }
    
    protected void checkForError() throws FPException {
        if ((FP != null) && (getLastErrorCode() != 0))
            throw createException();
    }

    @Override
    public Long getLastErrorCode() {
        return (long)FP.getLastErrorCode();
    }

    @Override
    public String getLastErrorMessage() {
        return FP.getLastErrorMessage();
    }

    
    @Override
    public void cancelFiscalCheck() throws FPException {
        if (!FP.cancel_FReceipt())
           throw createException();
    }

    @Override
    public void printDiagnosticInfo() throws FPException {
        if (!FP.printDiagnostic()) 
            throw createException();
    }

    
    @Override
    public String getLastPrintDoc() throws FPException {
        int[] docNum = new int[1];
        String LastDocNum = "";
        if (FP.getLastPrintDoc(docNum)) {
            LastDocNum = Integer.toString(docNum[0]);
        } else
           throw createException();
        return LastDocNum;
    }

    @Override
    public void abnormalComplete() throws FPException {
        // First Try to Cancel Fiscal Receipt
        FP.cancel_FReceipt();
        int[] open = new int[1], items = new int[1];
        double[] amount = new double[1], tender = new double[1];
        if (FP.checkFiscalCloseStatus(true, open, items, amount, tender)) {
            if (open[0] != 0) { // Fiscal check is open
                // Check to see can add corrections/reversal
                boolean[] canVoid = new boolean[1], inv = new boolean[1];
                int[] invNum = new int[1];
                LinkedHashMap<taxGroup,double[]> tax = new LinkedHashMap<>();
                tax.put(taxGroup.A, new double[1]);
                tax.put(taxGroup.B, new double[1]);
                tax.put(taxGroup.C, new double[1]);
                tax.put(taxGroup.D, new double[1]);
                if (FP.getCurrentCheckInfo(canVoid, inv
                    , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
                    , invNum) &&  canVoid[0]) {
                    // Reverse sums by tax groups
                    for(taxGroup tg : tax.keySet()) {
                        if (tax.get(tg)[0] > 0)
                            FP.sell("-", "", (byte)taxGroupToChar(tg), tax.get(tg)[0], 0);
                    }
                }
                // Get Current Paid status, what is rest after reversal
                // Try to pay in credit
                if (FP.checkFiscalCloseStatus(true, open, items, amount, tender) && (open[0] != 0) && (amount[0] > tender[0])) 
                    FP.total("Общо", "", (byte)paymenTypeChar(paymentTypes.CREDIT), amount[0]-tender[0]);
                // Try to pay in cache
                if (FP.checkFiscalCloseStatus(true, open, items, amount, tender) && (open[0] != 0) && (amount[0] > tender[0])) 
                    FP.total("Общо", "", (byte)paymenTypeChar(paymentTypes.CASH), amount[0]-tender[0]);
                if (FP.checkFiscalCloseStatus(true, open, items, amount, tender) && (open[0] != 0)) 
                    FP.total("Общо", "", (byte)paymenTypeChar(paymentTypes.CASH), 0);
                // Try to close
                if (!FP.closeFiscalCheck())
                    throw createException();
            } else {
                // close nonfiscal check
                if (!FP.closeNonFiscalCheck(items))
                    throw createException();
            } 
        } else
            checkForError();
    }

    @Override
    public String customCmd(String CMD, String params) throws FPException{
        String[] input = new String[1], output = new String[1];
        input[0] = params;
        output[0] = "";
        if (!FP.custom_CMD_1(Integer.parseInt(CMD), input, output))
            throw createException();
        return output[0];
    }

    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        if (code.length() == 0)
           code = getParam("OperatorCode");
        if (passwd.length() == 0)
           passwd = getParam("OperatorPass");
        int iCode = Integer.parseInt(code);
        if (!FP.setOperatorName(iCode, passwd, fullName)) 
            throw createException();
    }

    
    @Override
    public void openFiscalCheck() throws FPException {
        if (!FP.openFiscalCheck(params.getInt("OperatorCode", 1), params.get("OperatorPass", "0000"), (byte)params.getInt("TillNumber", 1).byteValue())) 
            throw createException();
    }

    
    @Override
    public void closeFiscalCheck() throws FPException{
        if (!FP.closeFiscalCheck())
            throw createException();
    }

    protected byte taxGroupToChar(taxGroup taxCode) {
        byte res; 
        switch (taxCode) {
            case A : res = (byte)0x41; break;
            case B : res = (byte)0x42; break;
            case C : res = (byte)0x43; break;
            case D : res = (byte)0x44; break;
            case E : res = (byte)0x45; break;
            case F : res = (byte)0x46; break;
            case G : res = (byte)0x47; break;
            case H : res = (byte)0x48; break;
            default :
                res = 'A';
        }
        return res;
    }
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        String[] lines = splitOnLines(text);
        if (quantity > 0) {
            if (!FP.sellQ(lines[0], (lines.length > 1)?lines[1]:"", (byte)taxGroupToChar(taxCode), price, quantity, discountPerc))
                throw createException();
        } else 
            if (!FP.sell(lines[0], (lines.length > 1)?lines[1]:"", (byte)taxGroupToChar(taxCode), price, discountPerc))
                throw createException();
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        String[] lines = splitOnLines(text);
        if (!FP.sellQ_B(lines[0], (lines.length > 1)?lines[1]:"", (byte)deptCode.charAt(0), price, quantity, discountPerc))
            throw createException();
    }

    @Override
    public void printFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text);
        for (String line : lines)
            if (!FP.printFiscalText(line)) 
                throw createException();
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text);
        for (String line : lines)
            if (!FP.printNonFiscalText(line)) 
                throw createException();
    }
    
    
    
    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        boolean[] canVoid = new boolean[1], inv = new boolean[1];
        double[] SubTotal = new double[1];
        LinkedHashMap<Character,double[]> tax = new LinkedHashMap<>();
        tax.put('A', new double[1]);
        tax.put('B', new double[1]);
        tax.put('C', new double[1]);
        tax.put('D', new double[1]);
        tax.put('E', new double[1]);
        tax.put('F', new double[1]);
        tax.put('G', new double[1]);
        tax.put('H', new double[1]);
        tax.put('I', new double[1]);
        if (!FP.subTotal_8(
                toPrint, toDisplay
                , discountPerc
                , SubTotal
                , tax.get('A'), tax.get('B'), tax.get('C'), tax.get('D')
                , tax.get('E'), tax.get('F'), tax.get('G'), tax.get('H')
            )) throw createException();
        StrTable res = new StrTable();
        res.put("SubTotal", Double.toString(SubTotal[0]));
        for(char taxCode : tax.keySet())
            res.put("Tax"+taxCode, Double.toString(tax.get(taxCode)[0]));
        return res;
    }
    
    protected char paymenTypeChar(paymentTypes payType) {
/*		'P'	- Плащане в брой (по подразбиране);
		'N'	- Плащане с кредит;
		'C'	- Плащане с чек;
		'D'	- Плащане с дебитна карта
		'I'	- Програмируем тип плащане 1
		'J'	- Програмируем тип плащане 2
		'K'	- Програмируем тип плащане 3
		'L'	- Програмируем тип плащане 4
*/        
        char pc = 'P';
        switch (payType) {
            case CASH : pc = 'P'; break;
            case CREDIT : pc = 'N'; break;
            case CHECK : pc = 'C'; break;
            case DEBIT_CARD : pc = 'D'; break;
            case CUSTOM1 : pc = 'I'; break;
            case CUSTOM2 : pc = 'J'; break;
            case CUSTOM3 : pc = 'K'; break;
            case CUSTOM4 : pc = 'L'; break;
        }
        return pc;
    }
    
    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        String[] lines = splitOnLines(text);
        if (!FP.total(lines[0], (lines.length > 1)?lines[1]:"", (byte)paymenTypeChar(payType), payAmount))
            throw createException();
        return new StrTable();
    }

    @Override
    public StrTable getCurrentCheckInfo() throws FPException {
        boolean[] canVoid = new boolean[1], isInvoice = new boolean[1];
        int[] invNum = new int[1];
        LinkedHashMap<Character,double[]> tax = new LinkedHashMap<>();
        tax.put('A', new double[1]);
        tax.put('B', new double[1]);
        tax.put('C', new double[1]);
        tax.put('D', new double[1]);
        tax.put('E', new double[1]);
        tax.put('F', new double[1]);
        tax.put('G', new double[1]);
        tax.put('H', new double[1]);
//        tax.put('I', new double[1]);
        FP.getCurrentCheckInfo(canVoid, isInvoice, tax.get('A'), tax.get('B'), tax.get('C'), tax.get('D'), invNum);
//        if (!FP.getCurrentCheckInfo(canVoid, isInvoice, tax.get('A'), tax.get('B'), tax.get('C'), tax.get('D'), invNum))
//            throw createException();
        StrTable res = new StrTable();
        res.put("CanVoid", Boolean.toString(canVoid[0]));
        res.put("IsInvoice", Boolean.toString(isInvoice[0]));
        res.put("InvNumber", Integer.toString(invNum[0]));
        for(char taxCode : tax.keySet())
            res.put("Tax"+taxCode, Double.toString(tax.get(taxCode)[0]));
        int[] docNum = new int[1];
        FP.getLastPrintDoc(docNum);
        res.put("LastPrintDocNum", Integer.toString(docNum[0]));
        double[] total = new double[1];
        Date[] dateTime = new Date[1];
        dateTime[0] = new Date();
        FP.getLastFiscalRecordInfo(docNum, total, tax.get('A'), tax.get('B'), tax.get('C'), tax.get('D'), dateTime);
        res.put("LFRI_DocNum", Integer.toString(docNum[0]));
        res.put("LFRI_DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime[0]));
        res.put("LFRI_Total", Double.toString(total[0]));
        res.put("LFRI_TaxA", Double.toString(tax.get('A')[0]));
        res.put("LFRI_TaxB", Double.toString(tax.get('B')[0]));
        res.put("LFRI_TaxC", Double.toString(tax.get('C')[0]));
        res.put("LFRI_TaxD", Double.toString(tax.get('D')[0]));
        return res;
    }

    @Override
    public void openNonFiscalCheck() throws FPException {
        int[] allReceipt = new int[1];
        byte[] errorCode = new byte[1];
        if (!FP.openNonFiscalCheck(allReceipt, errorCode)) 
            throw createException();
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        int[] allReceipt = new int[1];
        if (!FP.closeNonFiscalCheck(allReceipt)) 
            throw createException();
    }
    
    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String,byte[]> sw = new LinkedHashMap<>();
        sw.put("S0", new byte[1]);
        sw.put("S1", new byte[1]);
        sw.put("S2", new byte[1]);
        sw.put("S3", new byte[1]);
        sw.put("S4", new byte[1]);
        sw.put("S5", new byte[1]);
        // Status bits interpretation
        HashMap<String, String[]> BM = new HashMap<>();
        BM.put("S0", new String[]
        {
             "Синтактична грешка"
            ,"Невалиден код на команда"
            ,"Неустановени дата/час"
            ,"Не е свързан клиентски дисплей"
            ,"Неизправен механизъм"
            ,"Обща грешка"
            ,""
            ,""
        }
        );
        BM.put("S1", new String[]
        {
             "Препълване"
            ,"Непозволена команда в този контекст"
            ,"Аварийно зануляване на оперативната памет"
            ,"Прекъсната и рестартирана операция на печат/Слаба батерия"
            ,"Разрушено състояние на ОП"
            ,"Капакът на принтера е отворен"
            ,"Електронната контролна лента е отпечатана"
            ,""
        }
        );
        BM.put("S2", new String[]
        {
             "Край на хартията"
            ,"Останала е малко хартия"
            ,"Край на електронната контролна лента (95% запълнена)"
            ,"Отворен Фискален Бон"
            ,"Близък край на електронната контролна лента (90% запълнена)"
            ,"Отворен Нефискален Бон"
            ,"Електронната контролна лента не е празна"
            ,""
        }
        );
        BM.put("S3", new String[]
        {
             "SW1.1"
            ,"SW1.2"
            ,"SW1.3"
            ,"SW1.4"
            ,"SW1.5"
            ,"SW2.1"
            ,"SW2.2"
            ,""
        }
        );
        BM.put("S4", new String[]
        {
             "Грешка при запис във фискалната памет"
            ,"Зададен е БУЛСТАТ"
            ,"Зададени са индивидуален номер на принтера и номер на фискалната памет"
            ,"Има място за по малко от 50 фискални затваряния"
            ,"Фискалната памет е пълна"
            ,"0 или 4"
            ,""
            ,""
        }
        );
        BM.put("S5", new String[]
        {
             "Фискалната памет в режим Read Only"
            ,"Фискалната памет e форматирана"
            ,"Последният запис във ФП не е успешен"
            ,"Устройството е фискализирано"
            ,"Зададени са данъчните ставки"
            ,"Номерът на фискалната памет е програмиран"
            ,""
            ,""
        }
        );
        if (!FP.getPrinterStatus(true, sw.get("S0"), sw.get("S1"), sw.get("S2"), sw.get("S3"), sw.get("S4"), sw.get("S5")))
            throw createException();
        StrTable res = new StrTable();
        StrTable resText = new StrTable();
        String ss;
        for(String s : sw.keySet()) {
            ss = Integer.toBinaryString(sw.get(s)[0] & 0xFF);
            if (ss.length() > 8)
                ss = ss.substring(ss.length()-8);
            else if (ss.length() < 8)
                ss = "00000000".substring(0, 8 - ss.length())+ss;
            res.put(s, ss);
            for(int i = 0; i < 8; i++)
                if (ss.charAt(7-i) == '1')
                    resText.put("Msg_"+s+"_"+Integer.toString(i), BM.get(s)[i]);
        }   
        res.putAll(resText);
        return res;
    }

    @Override
    public StrTable getDiagnosticInfo() throws FPException {
        String[] Firmware = new String[]{""}, CRC = new String[]{""}
                , DateTime = new String[]{""}, SerialNumber = new String[]{""}
                , FiscalMemoryNumber = new String[]{""}, Switches = new String[]{""};
        int[] Country = new int[1];
        if (!FP.getDiagnosticInfo(true, Firmware, CRC, Country, DateTime, SerialNumber, FiscalMemoryNumber, Switches))
            throw createException();
        StrTable res = new StrTable();
        res.put("SerialNumber", SerialNumber[0]);
        res.put("FiscalMemoryNumber", FiscalMemoryNumber[0]);
        res.put("Firmware", Firmware[0]);
        res.put("DateTime", DateTime[0]);
        res.put("Country", Integer.toString(Country[0]));
        res.put("CRC", CRC[0]);
        res.put("Switches", Switches[0]);
        return res;
    }

    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
/*        
        VARIANT_BOOL DAILY_REPORT_8(
                        [in] long ReportKind, 
                        [in] VARIANT_BOOL ClearOpInfo, 
                        [in, out] long* Closure, 
                        [in, out] double* FM_Total, 
                        [in, out] double* TaxA, 
                        [in, out] double* TaxB, 
                        [in, out] double* TaxC, 
                        [in, out] double* TaxD, 
                        [in, out] double* TaxE, 
                        [in, out] double* TaxF, 
                        [in, out] double* TaxG, 
                        [in, out] double* TaxH);
*/
        int[] closure = new int[1];
        double[] fM_Total = new double[1];
        LinkedHashMap<Character,double[]> tax = new LinkedHashMap<>();
        tax.put('A', new double[1]);
        tax.put('B', new double[1]);
        tax.put('C', new double[1]);
        tax.put('D', new double[1]);
        tax.put('E', new double[1]);
        tax.put('F', new double[1]);
        tax.put('G', new double[1]);
        tax.put('H', new double[1]);
        
        if (!FP.dAILY_REPORT_8(
            (reportType == dailyReportType.Z)?0:2
            , true
            , closure
            , fM_Total
            , tax.get('A'), tax.get('B'), tax.get('C'), tax.get('D')
            , tax.get('E'), tax.get('F'), tax.get('G'), tax.get('H')
            ))
            throw createException();
        StrTable res = new StrTable();
        res.put("FiscalRecNumber", Integer.toString(closure[0]));
        res.put("Total", Double.toString(fM_Total[0]));
        for(char taxCode : tax.keySet()) 
            res.put("Tax"+taxCode, Double.toString(tax.get(taxCode)[0]));
        try {
            res.putAll(getPrinterStatus());
        } catch (Exception e) {
        }
        return res;
    }

    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        StrTable res = new StrTable();
        if (!FP.pRINT_FMBYDATES(from, to, reportType == datesReportType.DETAIL))
            throw createException();
        return res;
    }


    
    @Override
    public Date getDateTime() throws FPException {
        Date[] dateTime = new Date[1];
        dateTime[0] = new Date();
        if (!FP.getDateTime(dateTime))
            throw createException();
        return dateTime[0];
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        if (!FP.setDateTime(dateTime))
            throw createException();
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        if (!FP.paperFeed(lineCount))
            throw createException();
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        if (!FP.printDoubleCheck(1))
            throw createException();
    }

    @Override
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return getCurrentCheckInfo();
    }

    @Override
    public StrTable getJournalInfo() throws FPException {

        int[] joZNumFrom = new int[1];
        int[] joZNumTo = new int[1];
        int[] joDocNumFrom = new int[1];
        int[] joDocNumTo = new int[1];
        int[] joFreeBytes = new int[1];
        int[] joTotalBytes = new int[1];
        
        if (!FP.gET_JOURNAL_INFO(joZNumFrom, joZNumTo, joDocNumFrom, joDocNumTo, joFreeBytes, joTotalBytes))
            throw createException();
        StrTable res = new StrTable();
        res.put("ZNumFrom", Integer.toString(joZNumFrom[0]));
        res.put("ZNumTo", Integer.toString(joZNumTo[0]));
        res.put("DocNumFrom", Integer.toString(joDocNumFrom[0]));
        res.put("DocNumTo", Integer.toString(joDocNumTo[0]));
        res.put("FreeBytes", Integer.toString(joFreeBytes[0]));
        res.put("TotalBytes", Integer.toString(joTotalBytes[0]));
        return res;
    }

    @Override
    public StrTable getJournal(boolean readAndErase) throws FPException {
        // First try to get journal info in case of error to skip temporary file creation
        StrTable res = getJournalInfo();
    	try {
            //create a temp file
            File temp = File.createTempFile("fp-journal", ".tmp"); 
//    		System.out.println("Temp file : " + temp.getAbsolutePath());
            //Get tempropary file path
            String absolutePath = temp.getAbsolutePath();
            String[] CRC32 = new String[]{""};
            String[] SHA1 = new String[]{""};
//            String tempFilePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
//            System.out.println("Temp file path : " + tempFilePath);
            boolean fpres;
            try {
                fpres = FP.gET_JOURNAL(readAndErase, absolutePath, CRC32, SHA1);
                if (fpres) {
                    byte[] fileContent = Files.readAllBytes(temp.toPath());
                    res.put("Content", Base64.getEncoder().encodeToString(fileContent));
                    res.put("CRC32", CRC32[0]);
                    res.put("SHA1", SHA1[0]);
                }
            } finally {
                // remove temporary file
                temp.delete();
            }
            if (!fpres)
                throw createException();
    	} catch(IOException e){
            throw new FPException((long)1, e.getMessage());
    	}    
        return res;
    }

    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        double[] cashSum = new double[]{0};
        double[] cashIn = new double[]{0};
        double[] cashOut = new double[]{0};

        // boolean setServiceMoney(double amount, double[] cashSum, double[] servIn, double[] servOut)
        if (!FP.setServiceMoney(ioSum, cashSum, cashIn, cashOut))
            throw createException();
        
        StrTable res = new StrTable();
        res.put("CashSum", String.format(Locale.ROOT, "%.2f", cashSum[0]));
        res.put("CashIn", String.format(Locale.ROOT, "%.2f", cashIn[0]));
        res.put("CashOut", String.format(Locale.ROOT, "%.2f", cashOut[0]));
        return res;
    }
    
}
