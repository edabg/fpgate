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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.datecs.fpcom.CS_BGR_FP700_KL;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCFP700KL  extends FPCBase {

    private CS_BGR_FP700_KL FP;
    private int lastErrorCode;
    private String lastCommand;
    

    public FPCFP700KL() throws Exception {
        super();
    }

    public static String getClassDecription() {
        return "This class supports Datecs FP-700 KL fiscal printer!";
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
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 48));
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
            lw = 48;
       return lw; 
    }

    @Override
    public void init() throws Exception, FPException {
        LibraryLoader.loadJacobLibrary();
        ComThread.InitMTA();
        this.FP = new CS_BGR_FP700_KL();
        lastCommand = "iNIT_Ex1";
        FP.iNIT_Ex1(getParamInt("COM").byteValue(), getParamInt("BaudRate"));
    }

    @Override
    public void destroy() {
        ComThread.Release();
        FP = null;
    }

    protected FPException createException() {
        if (FP != null )
            return new FPException((long)lastErrorCode, FP.gET_LASTERROR(0));
       else
            return new FPException((long)-1, "Unknown error!");
    }
    
    protected void checkForError() throws FPException {
        if ((FP != null) && (lastErrorCode != 0))
            throw createException();
    }

    @Override
    public Long getLastErrorCode() {
        return (long)lastErrorCode;
    }

    @Override
    public String getLastErrorMessage() {
        return FP.gET_LASTERROR(0);
    }

    @Override
    public void cancelFiscalCheck() throws FPException {
        lastCommand = "cMD_60_0_0";
        lastErrorCode = FP.cMD_60_0_0();
        checkForError();
    }
    
    @Override
    public void printDiagnosticInfo() throws FPException {
        lastCommand = "cMD_71_0_0";
        lastErrorCode = FP.cMD_71_0_0();
        checkForError();
    }
    
    @Override
    public String getLastPrintDoc() throws FPException {
        String[] lastDocNum = new String[1];
        lastDocNum[0] = "";
        lastCommand = "cMD_113_0_0";
        lastErrorCode = FP.cMD_113_0_0(lastDocNum);
        checkForError();
        return lastDocNum[0];
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
        // First Try to Cancel Fiscal Receipt
        FP.cMD_60_0_0();
        String[] canVoid = new String[]{""};
        String[] isInv = new String[]{""};
        String[] invDocNum = new String[]{""};
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});
        
//	public int cMD_103_0_0(String[] canVd, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH, String[] inv, String[] invNmb) 
        lastErrorCode = FP.cMD_103_0_0(
            canVoid
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
            , isInv, invDocNum
        );
        if (lastErrorCode == 0) {
            if (canVoid[0].length() != 0 && !canVoid[0].equals("0")) {
                // try to void sums by tax groups
                double tSum; 
                for(taxGroup tg : tax.keySet()) {
                    tSum = Double.parseDouble(tax.get(tg)[0]);
                    if (tSum > 0)
                        lastErrorCode = FP.cMD_49_6_1("-", taxGroupToChar(tg), Double.toString(-tSum));
                }
               // try to pay rests
                lastErrorCode = FP.cMD_103_0_0(
                    canVoid
                    , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
                    , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
                    , isInv, invDocNum
                );
                tSum = 0;
                for(taxGroup tg : tax.keySet()) 
                    tSum += Double.parseDouble(tax.get(tg)[0]);
                // try to pay in cache
                if (tSum > 0) {
                    String[] paidCode = new String[]{""};
                    String[] amountOut = new String[]{""};
                    lastErrorCode = FP.cMD_53_0_0("Общо", "", paymenTypeChar(paymentTypes.CASH), Double.toString(tSum), paidCode, amountOut);
                }    
                // close fiscal check
                String[] allReceipt = new String[]{""};
                String[] fiscReceipt = new String[]{""};
                lastCommand = "cMD_56_0_0";
                lastErrorCode = FP.cMD_56_0_0(allReceipt, fiscReceipt);
                checkForError();
            } else {
                // close nonfiscal check
                String[] allReceipt = new String[]{""};
                lastCommand = "cMD_39_0_0";
                lastErrorCode = FP.cMD_39_0_0(allReceipt);
                checkForError();
            } 
        } else
            checkForError();
    }
    
    @Override
    public String customCmd(String CMD, String params) throws FPException{
        String input = params;
        String[] output = new String[1];
        output[0] = "";
        lastCommand = "cUSTOM_CMD("+CMD+")";
        if (!FP.cUSTOM_CMD(Integer.parseInt(CMD), input, output)) 
            throw createException();
        return output[0];
    }

    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        if (code.length() == 0)
           code = getParam("OperatorCode");
        if (passwd.length() == 0)
           passwd = getParam("OperatorPass");
        lastCommand = "CMD_102_0_0";
        lastErrorCode = FP.cMD_102_0_0(code, passwd, fullName);
        checkForError();
    }

    @Override
    public void openFiscalCheck() throws FPException {
        String[] allReceipt = new String[]{""};
        String[] fiscReceipt = new String[]{""};
        lastCommand = "cMD_48_0_0";
        lastErrorCode = FP.cMD_48_0_0(params.get("OperatorCode", "1"), params.get("OperatorPass", "0000"), params.get("TillNumber","1"), allReceipt, fiscReceipt);
        checkForError();
    }

    @Override
    public void closeFiscalCheck() throws FPException{
        String[] allReceipt = new String[]{""};
        String[] fiscReceipt = new String[]{""};
        lastCommand = "cMD_56_0_0";
        lastErrorCode = FP.cMD_56_0_0(allReceipt, fiscReceipt);
        checkForError();
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        String[] lines = splitOnLines(text);
        lastCommand = "cMD_49_0_0";
        lastErrorCode = FP.cMD_49_0_0(
                lines[0], (lines.length > 1)?lines[1]:""
                , taxGroupToChar(taxCode)
                , Double.toString(price)
                , (quantity > 0)?Double.toString(quantity):"1"
                , Double.toString(discountPerc)
        );
        checkForError();
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        String[] lines = splitOnLines(text);
        lastCommand = "cMD_49_3_0";
        lastErrorCode = FP.cMD_49_3_0(
                lines[0], (lines.length > 1)?lines[1]:""
                , deptCode
                , Double.toString(price)
                , (quantity > 0)?Double.toString(quantity):"1"
                , Double.toString(discountPerc)
        );
        checkForError();
    }

    @Override
    public void printFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text);
        for (String line : lines) {
            lastCommand = "cMD_54_0_0";
            lastErrorCode = FP.cMD_54_0_0(line);
            checkForError();
        }    
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text);
        for (String line : lines) {
            lastCommand = "cMD_42_0_0";
            lastErrorCode = FP.cMD_42_0_0(line);
            checkForError();
        }    
    }

    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        String[] SubTotal = new String[]{""};
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});
        
        lastCommand = "cMD_51_0_0";
        lastErrorCode = FP.cMD_51_0_0(
            toPrint?"1":"0", toDisplay?"1":"0"
            , SubTotal
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
        );
        checkForError();
        StrTable res = new StrTable();
        res.put("SubTotal", SubTotal[0]);
        for(taxGroup taxCode : tax.keySet())
            res.put("Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        return res;
    }

    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        String[] lines = splitOnLines(text);
        String[] paidCode = new String[]{""};
        String[] amountOut = new String[]{""};
        lastCommand = "cMD_53_0_0";
//	public int cMD_53_0_0(String line1, String line2, String paidMode, String amount_In, String[] paidCode, String[] amount_Out) {
        lastErrorCode = FP.cMD_53_0_0(
            lines[0], (lines.length > 1)?lines[1]:""
            , paymenTypeChar(payType)
            , Double.toString(payAmount)
            , paidCode, amountOut
        );
        checkForError();
        StrTable res = new StrTable();
        res.put("PaidCode", paidCode[0]);
        res.put("AmountOut", amountOut[0]);
        return res;
    }

    @Override
    public StrTable getCurrentCheckInfo() throws FPException {
        String[] canVoid = new String[]{""};
        String[] isInv = new String[]{""};
        String[] invDocNum = new String[]{""};
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});
        
        lastCommand = "cMD_103_0_0";
        lastErrorCode = FP.cMD_103_0_0(
            canVoid
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
            , isInv, invDocNum
        );
        StrTable res = new StrTable();
        res.put("CanVoid", canVoid[0]);
        res.put("IsInvoice", isInv[0]);
        res.put("InvNumber", invDocNum[0]);
        for(taxGroup taxCode : tax.keySet())
            res.put("Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        
        String[] docNum = new String[]{""};
        lastCommand = "cMD_113_0_0";
        lastErrorCode = FP.cMD_113_0_0(docNum);
        res.put("LastPrintDocNum", docNum[0]);

        for(taxGroup taxCode : tax.keySet())
            tax.get(taxCode)[0] = "";
        String[] errCode = new String[]{""};
        String[] closureDate = new String[]{""};
        lastCommand = "cMD_64_0_0";
        lastErrorCode = FP.cMD_64_0_0(
            errCode, docNum
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
            , closureDate
        );
        res.put("LFRI_DocNum", docNum[0]);
        res.put("LFRI_DateTime_", closureDate[0]);

        lastCommand = "cMD_86_0_0";
        lastErrorCode = FP.cMD_86_0_0(closureDate);
        res.put("LFRI_DateTime", closureDate[0]);
        
//        res.put("LFRI_Total", Double.toString(total[0]));
        for(taxGroup taxCode : tax.keySet())
            res.put("LFRI_Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        return res;
    }

    @Override
    public void openNonFiscalCheck() throws FPException {
        String[] allReceipt = new String[]{""};
        String[] errorCode = new String[]{""};
        lastCommand = "cMD_38_0_0";
        lastErrorCode = FP.cMD_38_0_0(allReceipt, errorCode);
        checkForError();
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        String[] allReceipt = new String[]{""};
        lastCommand = "cMD_39_0_0";
        lastErrorCode = FP.cMD_39_0_0(allReceipt);
        checkForError();
    }

    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String,String[]> sw = new LinkedHashMap<>();
        sw.put("S0", new String[]{""});
        sw.put("S1", new String[]{""});
        sw.put("S2", new String[]{""});
        sw.put("S3", new String[]{""});
        sw.put("S4", new String[]{""});
        sw.put("S5", new String[]{""});
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
        lastCommand = "cMD_74_0_0"; // W,X
        lastErrorCode = FP.cMD_74_0_0("W", sw.get("S0"), sw.get("S1"), sw.get("S2"), sw.get("S3"), sw.get("S4"), sw.get("S5"));

        StrTable res = new StrTable();
        StrTable resText = new StrTable();
        String ss;
        for(String s : sw.keySet()) {
            try {
                ss = Integer.toBinaryString(Integer.parseInt(sw.get(s)[0]) & 0xFF);
            } catch (Exception E) {
                ss = "";
            }
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
        String[] 
            printerName = new String[]{""}
            , Firmware = new String[]{""}, CRC = new String[]{""}
            , FirmwareDate = new String[]{""}, FirmwareTime = new String[]{""}
            , SerialNumber = new String[]{""}
            , FiscalMemoryNumber = new String[]{""}, Switches = new String[]{""};
        int[] Country = new int[1];
        
        
        lastCommand = "cMD_90_0_0";
        lastErrorCode = FP.cMD_90_0_0(
            printerName, Firmware, FirmwareDate, FirmwareTime
            , CRC, Switches, SerialNumber, FiscalMemoryNumber
        );
        checkForError();
        StrTable res = new StrTable();
        res.put("SerialNumber", SerialNumber[0]);
        res.put("FiscalMemoryNumber", FiscalMemoryNumber[0]);
        res.put("Firmware", Firmware[0]);
        res.put("FirmwareDate", FirmwareDate[0]);
        res.put("FirmwareTime", FirmwareTime[0]);
        res.put("CRC", CRC[0]);
        res.put("Switches", Switches[0]);
        return res;
    }

    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        String[] closure = new String[]{""};
        String[] fM_Total = new String[]{""};
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});

	// public int cMD_69_0_0(
        // String option
        // , String n, String[] closure, String[] fM_Total
        //, String[] totA, String[] totB, String[] totC, String[] totD
        //, String[] totE, String[] totF, String[] totG, String[] totH) {
        
        lastCommand = "cMD_69_0_0";
        lastErrorCode = FP.cMD_69_0_0(
            (reportType == dailyReportType.Z)?"0":"2", ""
            , closure, fM_Total
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
        );
        checkForError();
        
        StrTable res = new StrTable();
        res.put("FiscalRecNumber", closure[0]);
        res.put("Total", fM_Total[0]);
        for(taxGroup taxCode : tax.keySet()) 
            res.put("Tax"+taxCode, tax.get(taxCode)[0]);
        try {
            res.putAll(getPrinterStatus());
        } catch (Exception e) {
        }
        return res;
    }
    
    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        StrTable res = new StrTable();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        if (reportType == datesReportType.DETAIL) {
            lastCommand = "cMD_94_0_0";
            lastErrorCode = FP.cMD_94_0_0(dateFormat.format(from), dateFormat.format(to));
            checkForError();
        } else {
            lastCommand = "cMD_79_0_0";
            lastErrorCode = FP.cMD_79_0_0(dateFormat.format(from), dateFormat.format(to));
            checkForError();
        }
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        String[] dd = new String[]{""};
        String[] mm = new String[]{""};
        String[] yy = new String[]{""};
        String[] hh = new String[]{""};
        String[] nn = new String[]{""};
        String[] ss = new String[]{""};
        lastCommand = "cMD_62_0_1";
        lastErrorCode = FP.cMD_62_0_1(dd, mm, yy, hh, nn, ss);
        checkForError();
        if (yy[0].length()==2) yy[0] = "20"+yy[0];
        Date dateTime;
        dateTime = new GregorianCalendar(
            Integer.parseInt(yy[0])
            , Integer.parseInt(mm[0])-1
            , Integer.parseInt(dd[0])
            , Integer.parseInt(hh[0])
            , Integer.parseInt(nn[0])
            , Integer.parseInt(ss[0])
        ).getTime();
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        lastCommand = "cMD_61_0_0";
        String dateStr = dateFormat.format(dateTime);
        String timeStr = timeFormat.format(dateTime);
        lastErrorCode = FP.cMD_61_0_0(dateStr, timeStr);
        checkForError();
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        lastCommand = "cMD_44_0_0";
        lastErrorCode = FP.cMD_44_0_0(Integer.toString(lineCount));
        checkForError();
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "cMD_109_0_0";
        lastErrorCode = FP.cMD_109_0_0("1");
        checkForError();
    }
    
    @Override
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return getCurrentCheckInfo();
    }

    @Override
    public StrTable getJournalInfo() throws FPException {
        String[] fResult = new String[]{""};
        String[] joTotalBytes = new String[]{""};
        String[] joUsedBytes = new String[]{""};
        String[] joZNumFrom = new String[]{""};
        String[] joZNumTo = new String[]{""};
        String[] joDocNumFrom = new String[]{""};
        String[] joDocNumTo = new String[]{""};
        lastCommand = "cMD_119_1_0";
        lastErrorCode = FP.cMD_119_1_0(
            fResult
            , joTotalBytes, joUsedBytes
            , joZNumFrom, joZNumTo
            , joDocNumFrom, joDocNumTo
        );
        checkForError();
        StrTable res = new StrTable();
        res.put("FResult", fResult[0]);
        res.put("ZNumFrom", joZNumFrom[0]);
        res.put("ZNumTo", joZNumTo[0]);
        res.put("DocNumFrom", joDocNumFrom[0]);
        res.put("DocNumTo", joDocNumTo[0]);
        res.put("TotalBytes", joTotalBytes[0]);
        res.put("UsedBytes", joUsedBytes[0]);
        return res;
    }

    @Override
    public StrTable getJournal(boolean readAndErase) throws FPException {
        // First try to get journal info in case of error to skip temporary file creation
        StrTable res = getJournalInfo();
    	try {
            //create a temp file
            File temp = File.createTempFile("fp-journal", ".tmp"); 
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp)));
//    		System.out.println("Temp file : " + temp.getAbsolutePath());
            //Get tempropary file path
            String absolutePath = temp.getAbsolutePath();
//	public int cMD_119_2_2(String cl, String f_RESULT, String tHE_TEXT) {
            String[] fResult = new String[]{""};
            String[] text = new String[]{""};
            try {
                lastCommand = "cMD_119_2_2";
                lastErrorCode = FP.cMD_119_2_2(res.get("ZNumTo"), fResult, text);
                if (lastErrorCode == 0) {
                    while(fResult[0].equals("P") || fResult[0].equals("*")) {
                        file.append(text[0]+"\n");
                        fResult[0] = ""; text[0] = "";
                        lastCommand = "cMD_119_3_0";
                        lastErrorCode = FP.cMD_119_3_0(fResult, text);
                    }
                    file.append(text[0]);
                    file.close();
                }
                // Извличане на SHA1 на последния Z-отчет
                String[] SHA1 = new String[]{""};
                String[] fDocs = new String[]{""};
                String[] DT = new String[]{""};
                lastCommand = "cMD_119_0_1";
                lastErrorCode = FP.cMD_119_0_1(res.get("ZNumTo"), fResult, fDocs, DT, SHA1);
                res.put("ZZNum", fDocs[0]);
                res.put("ZDateTime", DT[0]);
                res.put("SHA1", SHA1[0]);
                byte[] fileContent = Files.readAllBytes(temp.toPath());
                res.put("Content", Base64.getEncoder().encodeToString(fileContent));
                if (readAndErase) {
                    // print Journal
                    lastCommand = "cMD_119_4_4";
                    lastErrorCode = FP.cMD_119_4_4(res.get("ZNumTo"), fResult);
                    res.put("PrintedCount", fResult[0]);
                }
            } finally {
                // remove temporary file
                temp.delete();
            }
            checkForError();
    	} catch(IOException e){
            throw new FPException((long)1, e.getMessage());
    	}    
        return res;
    }

    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        String[] exitCode = new String[]{""};
        String[] cashSum = new String[]{""};
        String[] cashIn = new String[]{""};
        String[] cashOut = new String[]{""};

        lastCommand = "cMD_70_0_0";
        // int cMD_70_0_0(String amount, String[] exitCode, String[] cashSum, String[] servIn, String[] servOut)
        lastErrorCode = FP.cMD_70_0_0(String.format(Locale.ROOT, "%.2f", ioSum), exitCode, cashSum, cashIn, cashOut);
        checkForError();
        StrTable res = new StrTable();
        res.put("CashSum", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashSum[0])/100));
        res.put("CashIn", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashIn[0])/100));
        res.put("CashOut", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashOut[0])/100));
        return res;
    }
    
}
