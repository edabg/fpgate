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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.datecs.fpcom.CS_BGR_FP705_KL;
import org.datecs.fpcom.TTransportProtocol;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCFP705KL  extends FPCBase {

    private CS_BGR_FP705_KL FP;
    private int lastErrorCode;
    private String lastCommand;
    

    public FPCFP705KL() throws Exception {
        super();
    }

    public static String getClassDecription() {
        return "This class supports Datecs FP-700 KL fiscal printer!";
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(
                        String.class
                        , "ConnectionType", "Connection type", "Type of communication with device"
                        , "COM"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("COM", "COM Port");
                                    put("TCPIP", "TCP/IP (Not Supported yet!)");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 40));
                     addProperty(new FPProperty(Integer.class, "LW_FICAL_TEXT", "Line width of fiscal text", "Line width of fiscal text", 46));
                     addProperty(new FPProperty(Integer.class, "LW_NONFICAL_TEXT", "Line width of non-fiscal text", "Line width of non-fiscal text", 46));
                     addProperty(new FPProperty(Integer.class, "LW_SELL_TEXT", "Line width of text in sell item", "Line width of text in sell item", 72));
                }}

                , new FPPropertyGroup("com", "COM Port") {{
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
            lw = 40;
       return lw; 
    }

    @Override
    public void init() throws Exception, FPException {
        LibraryLoader.loadJacobLibrary();
        ComThread.InitMTA();
        this.FP = new CS_BGR_FP705_KL();
        if ("COM".equals(getParam("ConnectionType"))) {
            lastCommand = "set_CommunicationRS232Values";
            FP.set_CommunicationType(TTransportProtocol.ctc_RS232);
            FP.set_CommunicationRS232Values(getParamInt("COM"), getParamInt("BaudRate"), 5);
        } else {
            throw new FPException((long)-1, "Not supported connection type!");
//            lastCommand = "set_CommunicationRS232Values";
//            FP.set_CommunicationType(TTransportProtocol.ctc_RS232);
//            FP.set_CommunicationRS232Values(getParamInt("COM"), getParamInt("BaudRate"), 5);
        }
        FP.oPEN_CONNECTION();
    }

    @Override
    public void destroy() {
        if (FP != null )
            FP.cLOSE_CONNECTION();
        ComThread.Release();
        FP = null;
    }

    protected FPException createException() {
        if (FP != null )
            return new FPException((long)FP.getlast_ErrorCode(), FP.getlast_ErrorMessage());
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
        return FP.getlast_ErrorMessage();
    }

    @Override
    public void cancelFiscalCheck() throws FPException {
        lastCommand = "command_060";
        lastErrorCode = FP.command_060(0);
        checkForError();
    }
    
    @Override
    public void printDiagnosticInfo() throws FPException {
        lastCommand = "command_071";
        lastErrorCode = FP.command_071(0, 0);
        checkForError();
    }
    
    @Override
    public String getLastPrintDoc() throws FPException {
        int[] errorCode = new int[]{0};
        int[] isOpen = new int[]{0};
        int[] number = new int[]{0};
        int[] items = new int[]{0};
        String[] amount = new String[]{""};
        String[] payed = new String[]{""};
        lastCommand = "command_076";
        lastErrorCode = FP.command_076(errorCode, isOpen, number, items, amount, payed);
        checkForError();
        return Integer.toString(number[0]);
    }

    protected int taxGroupToInt(taxGroup taxCode) {
        int res; 
        switch (taxCode) {
            case A : res = 1; break;
            case B : res = 2; break;
            case C : res = 3; break;
            case D : res = 4; break;
            case E : res = 5; break;
            case F : res = 6; break;
            case G : res = 7; break;
            case H : res = 8; break;
            default :
                res = 1;
        }
        return res;
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

        protected int paymenTypeInt(paymentTypes payType) {
/*		
PaidMode – Type of payment;
 '0' – cash;
 '1' – credit card;
 '2' – debit card;
 '3' – other pay#3
 '4' – other pay#4
 '5' – other pay#5
 '6' – Foreign currency            
*/        
        int pt = 0;
        switch (payType) {
            case CASH : pt = 0; break;
            case CREDIT : pt = 1; break;
            case CHECK : pt = 1; break; // NA same as CREDIT!
            case DEBIT_CARD : pt = 2; break;
            case CUSTOM1 : pt = 3; break;
            case CUSTOM2 : pt = 4; break;
            case CUSTOM3 : pt = 5; break;
            case CUSTOM4 : pt = 6; break; // foreign
        }
        return pt;
    }

    @Override
    public void abnormalComplete() throws FPException {
        // First Try to Cancel Fiscal Receipt
        lastCommand = "command_060";
        FP.command_060(0);
        // close nonfiscal check
        String[] allReceipt = new String[]{""};
        lastCommand = "command_039";
        lastErrorCode = FP.command_039(0, 0);
    }
    
    @Override
    public String customCmd(String CMD, String params) throws FPException{
        String input = params.replace("\\t", "\t");
        String[] output = new String[1];
        output[0] = "";
        lastCommand = "command_Variant1("+CMD+")";
        lastErrorCode = FP.command_Variant1(Integer.parseInt(CMD), input, output);
        checkForError();
        return output[0];
    }

    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        if (code.length() == 0)
           code = getParam("OperatorCode");
        if (passwd.length() == 0)
           passwd = getParam("OperatorPass");
        lastCommand = "command_Variant1(255, ...)";
        // OperName<SEP>index<SEP>Value<SEP>
        String[] output = new String[]{""};
        Integer opIndex = Integer.parseInt(code)-1;
        opIndex = Math.max(opIndex, 0);
        lastErrorCode = FP.command_Variant1(255, "OperName\t"+opIndex+"\t"+fullName+"\t", output);
        checkForError();
//        throw new FPException((long)-1, "setOperator is not supported!");
    }

    @Override
    public void openFiscalCheck() throws FPException {
        lastCommand = "command_048";
        //command_048(int opCode, String opPwd, int tillNmb, String invoice, int errorCode, int slipNumber)
        lastErrorCode = FP.command_048(params.getInt("OperatorCode", 1), params.get("OperatorPass", "0000"), params.getInt("TillNumber",1), "", 0, 0);
        checkForError();
    }

    @Override
    public void closeFiscalCheck() throws FPException{
        lastCommand = "command_056";
        lastErrorCode = FP.command_056(0, 0);
        checkForError();
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        String[] lines = splitOnLines(text, getParamInt("LW_SELL_TEXT"));
        int[] errorCode = new int[]{0};
        int[] slipNumber = new int[]{0};
        // public int command_049(String pluName, int taxCd, String price, String quantity, int discountType, String discountValue, int department, int[] errorCode, int[] slipNumber)
        lastCommand = "command_049";
        lastErrorCode = FP.command_049(
            lines[0]
            , taxGroupToInt(taxCode)
            , String.format(Locale.ROOT, "%.2f", price)
            , ((quantity > 0)?String.format(Locale.ROOT, "%.3f", quantity):"")
            , (discountPerc != 0)?2:0
            , String.format(Locale.ROOT, "%.2f", discountPerc)
            , 0
            , errorCode
            , slipNumber
        );
//        lastCommand = "command_Variant1(49...)";
//        String[] output = new String[]{""};
        // {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
//        String args = 
//            lines[0]+"\t"
//            +Integer.toString(taxGroupToInt(taxCode))
//            +"\t"+String.format(Locale.ROOT, "%.2f", price)
//            +"\t"+((quantity > 0)?String.format(Locale.ROOT, "%.3f", quantity):"")
//            +"\t"+((discountPerc != 0)?"2":"0")
//            +"\t"+String.format(Locale.ROOT, "%.2f", discountPerc)  
//            +"\t"+"0"
//            +"\t"  
//        ;
//        lastErrorCode = FP.command_Variant1(49, args, output);
        checkForError();
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        String[] lines = splitOnLines(text, getParamInt("LW_SELL_TEXT"));
        lastCommand = "command_049";
        int[] errorCode = new int[]{0};
        int[] slipNumber = new int[]{0};
        // public int command_049(String pluName, int taxCd, String price, String quantity, int discountType, String discountValue, int department, int[] errorCode, int[] slipNumber)
        lastErrorCode = FP.command_049(
            lines[0]
            , taxGroupToInt(taxGroup.A)
            , String.format(Locale.ROOT, "%.2f", price)
            , (quantity > 0)?String.format(Locale.ROOT, "%.3f", quantity):""
            , (discountPerc != 0)?2:0
            , String.format(Locale.ROOT, "%.2f", discountPerc)
            , Integer.valueOf(deptCode)
            , errorCode
            , slipNumber
        );
        checkForError();
        
    }

    @Override
    public void printFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text, getParamInt("LW_FICAL_TEXT"));
        for (String line : lines) {
//            lastCommand = "command_054";
//            lastErrorCode = FP.command_054(line, 0); // limit to 40 chars!!!
            lastCommand = "command_Variant1(54...)";
            String[] output = new String[]{""};
            lastErrorCode = FP.command_Variant1(54, line+"\t", output);
            checkForError();
        }    
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text, getParamInt("LW_NONFICAL_TEXT"));
        for (String line : lines) {
//            lastCommand = "cMD_42_0_0";
//            lastErrorCode = FP.command_042(line, 0);
            lastCommand = "command_Variant1(42...)";
            String[] output = new String[]{""};
            lastErrorCode = FP.command_Variant1(42, line+"\t", output);
            checkForError();
        }    
    }

    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        int[] errorCode = new int[]{0};
        int[] slipNumber = new int[]{0};
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
        // int command_051(int print, int display, int discountType, String discountValue, int[] errorCode, int[] slipNumber, String[] subTotal, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH)
        lastCommand = "command_051";
        lastErrorCode = FP.command_051(
                1, 1
                , (discountPerc != 0)?2:0, String.format(Locale.ROOT, "%.2f", discountPerc)
                , errorCode, slipNumber
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
        int[] errorCode = new int[]{0};
        String[] status = new String[]{""};
        String[] amountOut = new String[]{""};
        lastCommand = "cMD_53_0_0";
        //int command_053_v001(int paidMode, String in_Amount, int[] errorCode, String[] status, String[] out_Amount) {
        lastErrorCode = FP.command_053_v001(paymenTypeInt(payType), String.format(Locale.ROOT, "%.2f", payAmount), errorCode, status, amountOut);
        checkForError();
        StrTable res = new StrTable();
        res.put("PaidCode", status[0]);
        res.put("AmountOut", amountOut[0]);
        return res;
    }

    @Override
    public StrTable getCurrentCheckInfo() throws FPException {
        int[] errorCode = new int[]{0};
        String[] canVoid = new String[]{""};
        int[] isInv = new int[]{0};
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
        // command_103(int[] errorCode, String[] sumVATA, String[] sumVATB, String[] sumVATC, String[] sumVATD, String[] sumVATE, String[] sumVATF, String[] sumVATG, String[] sumVATH, int[] inv, String[] invNmb)
        lastErrorCode = FP.command_103(
            errorCode
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
            , isInv, invDocNum
        );
        StrTable res = new StrTable();
        res.put("CanVoid", "0");
        res.put("IsInvoice", Integer.toString(isInv[0]));
        res.put("InvNumber", invDocNum[0]);
        for(taxGroup taxCode : tax.keySet())
            res.put("Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        
        res.put("LastPrintDocNum", getLastPrintDoc());

        for(taxGroup taxCode : tax.keySet())
            tax.get(taxCode)[0] = "";
        String[] errCode = new String[]{""};
        String[] repDate = new String[]{""};
        int[] nRep = new int[]{0};
        lastCommand = "command_064";
        // int command_064(int in_Type, int[] errorCode, int[] nRep, String[] sumA, String[] sumB, String[] sumC, String[] sumD, String[] sumE, String[] sumF, String[] sumG, String[] sumH, String[] date)
        lastErrorCode = FP.command_064(
            1, errorCode, nRep
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
            , repDate
        );
        res.put("LFRI_DocNum", Integer.toString(nRep[0]));
        res.put("LFRI_DateTime_", repDate[0]);

        lastCommand = "command_086";
        lastErrorCode = FP.command_086(errorCode, repDate);
        res.put("LFRI_DateTime", repDate[0]);
        
//        res.put("LFRI_Total", Double.toString(total[0]));
        for(taxGroup taxCode : tax.keySet())
            res.put("LFRI_Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        return res;
    }

    @Override
    public void openNonFiscalCheck() throws FPException {
        lastCommand = "command_038";
        lastErrorCode = FP.command_038(0, 0);
        checkForError();
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        lastCommand = "command_039";
        lastErrorCode = FP.command_039(0, 0);
        checkForError();
    }

    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String,Integer> sw = new LinkedHashMap<>();
        sw.put("S0", 0);
        sw.put("S1", 0);
        sw.put("S2", 0);
        sw.put("S3", 0);
        sw.put("S4", 0);
        sw.put("S5", 0);
        sw.put("S6", 0);
        sw.put("S7", 0);
        // Status bits interpretation
        HashMap<String, String[]> BM = new HashMap<>();
/*
Byte 0: General purpose
 0.0 = 1# Syntax error.
 0.1 = 1# Command code is invalid.
 0.2 = 0 Always 0.
 0.3 = 0 Always 0.
 0.4 = 1# Failure in printing mechanism.
 0.5 = 1 General error – this is OR of all errors marked with #.
 0.6 = 0 Cover is open.
 0.7 = 1 Always 1.
*/        
        BM.put("S0", new String[]
        {
             "Синтактична грешка"           // 0
            ,"Невалиден код на команда"     // 1
            ,""                             // 2
            ,""                             // 3
            ,"Неизправен механизъм"         // 4
            ,"Обща грешка"                  // 5
            ,"Капакът е отворен"            // 6
            ,""
        }
        );
/*
Byte 1: General purpose
 1.0 = 1# Overflow during command execution.
 1.1 = 1# Command is not permitted.
 1.2 = 0 Always 0.
 1.3 = 0 Always 0.
 1.4 = 0 Always 0.
 1.5 = 0 Always 0.
 1.6 = 0 Always 0.
 1.7 = 1 Always 1.
*/
        BM.put("S1", new String[]
        {
             "Препълване"
            ,"Непозволена команда в този контекст"
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
        }
        );
/*
Byte 2: General purpose
 2.0 = 1# End of paper.
 2.1 = 0 Near paper end.
 2.2 = 1 EJ is full.
 2.3 = 1 Fiscal receipt is open.
 2.4 = 1 EJ nearly full.
 2.5 = 1 Nonfiscal receipt is open.
 2.6 = 0 Always 0.
 2.7 = 1 Always 1.
*/        
        BM.put("S2", new String[]
        {
             "Край на хартията"
            ,"Останала е малко хартия"
            ,"Край на електронната контролна лента (95% запълнена)"
            ,"Отворен Фискален Бон"
            ,"Близък край на електронната контролна лента (90% запълнена)"
            ,"Отворен Нефискален Бон"
            ,""
            ,""
        }
        );
/*
Byte 3: Not used
 3.0 = 0 Always 0.
 3.1 = 0 Always 0.
 3.2 = 0 Always 0.
 3.3 = 0 Always 0.
 3.4 = 0 Always 0.
 3.5 = 0 Always 0.
 3.6 = 0 Always 0.
 3.7 = 1 Always 1.
*/        
        BM.put("S3", new String[]
        {
             ""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
        }
        );
/*
Byte 4: Fiscal memory
 4.0 = 1* Error while writing in FM.
 4.1 = 1 Tax number is set.
 4.2 = 1 Serial number and number of FM are set.
 4.3 = 1 There is space for less then 50 reports in Fiscal memory.
 4.4 = 1* Fiscal memory is full.
 4.5 = 1 OR of all errors marked with ‘*’ from Bytes 4 и 5.
 4.6 = 0 Always 0.
 4.7 = 1 Always 1.
*/        
        BM.put("S4", new String[]
        {
             "Грешка при запис във фискалната памет"
            ,"Зададен е Данъчен номер"
            ,"Зададени са индивидуален номер на принтера и номер на фискалната памет"
            ,"Има място за по малко от 50 фискални затваряния"
            ,"Фискалната памет е пълна"
            ,"0 или 4"
            ,""
            ,""
        }
        );
/*
Byte 5: Fiscal memory
 5.0 = 0 Always 0.
 5.1 = 1 FM is formated.
 5.2 = 0 Always 0.
 5.3 = 1 Device is fiscalized.
 5.4 = 1 VAT are set at least once.
 5.5 = 0 Always 0.
 5.6 = 0 Always 0.
 5.7 = 1 Always 1.
*/        
        BM.put("S5", new String[]
        {
             ""
            ,"Фискалната памет e форматирана"
            ,""
            ,"Устройството е фискализирано"
            ,"Зададени са данъчните ставки"
            ,""
            ,""
            ,""
        }
        );
/*
Byte 6: Not used
 6.0 = 0 Always 0.
 6.1 = 0 Always 0.
 6.2 = 0 Always 0.
 6.3 = 0 Always 0.
 6.4 = 0 Always 0.
 6.5 = 0 Always 0.
 6.6 = 0 Always 0.
 6.7 = 0 if Old Protocol is defined, else = 1.
*/        
        BM.put("S6", new String[]
        {
             ""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
        }
        );
/*
Byte 7: Not used
 7.0 = 0 Always 0.        
 7.1 = 0 Always 0.
 7.2 = 0 Always 0.
 7.3 = 0 Always 0.
 7.4 = 0 Always 0.
 7.5 = 0 Always 0.
 7.6 = 0 Always 0.
 7.7 = 0 if Old Protocol is defined, else = 1.
*/        
        BM.put("S7", new String[]
        {
             ""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
            ,""
        }
        );
        lastCommand = "command_074"; // W,X
        int[] errorCode = new int[]{0};
        String[] statusBytes = new String[]{""};
        lastErrorCode = FP.command_074(errorCode, statusBytes);
        byte[] sBytes  = statusBytes[0].getBytes();
        for(int i = 1; i < Math.min(sBytes.length, 8); i++)
            sw.put("S"+Integer.toString(i), (int)sBytes[i]);
        // TODO: Ckeck how status bytes are returned as String
//        lastErrorCode = FP.cMD_74_0_0("W", sw.get("S0"), sw.get("S1"), sw.get("S2"), sw.get("S3"), sw.get("S4"), sw.get("S5"));

        StrTable res = new StrTable();
        
        StrTable resText = new StrTable();
        String ss;
        for(String s : sw.keySet()) {
            try {
                ss = Integer.toBinaryString(sw.get(s) & 0xFF);
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
//        int[] Country = new int[]{0};
        int[] errorCode = new int[]{0};
        
        lastCommand = "command_090_v002";
        // int command_090_v002(int calculate, int[] errorCode, String[] devicename, String[] fwRev, String[] fwDate, String[] fwTime, String[] checksum, String[] sw, String[] serialNumber, String[] fMNumber)
        lastErrorCode = FP.command_090_v002(1, errorCode, printerName, Firmware, FirmwareDate, FirmwareTime, CRC, Switches, SerialNumber, FiscalMemoryNumber);
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
        int[] errorCode = new int[]{0};
        int[] nRep = new int[]{0};
        String[] closure = new String[]{""};
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});

        lastCommand = "command_069";
        // int command_069(String reportType, int[] errorCode, int[] nRep, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH)
        lastErrorCode = FP.command_069(
            (reportType == dailyReportType.Z)?"Z":"X", errorCode, nRep
            , tax.get(taxGroup.A), tax.get(taxGroup.B), tax.get(taxGroup.C), tax.get(taxGroup.D)
            , tax.get(taxGroup.E), tax.get(taxGroup.F), tax.get(taxGroup.G), tax.get(taxGroup.H)
        );
        checkForError();
        
        StrTable res = new StrTable();
        res.put("FiscalRecNumber", Integer.toString(nRep[0]));
        res.put("Total", Integer.toString(nRep[0]));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        if (reportType == datesReportType.DETAIL) {
            lastCommand = "cMD_94_0_0";
            // int command_094(int in_Type, String startDate, String endDate, int errorCode)
            lastErrorCode = FP.command_094(1, dateFormat.format(from), dateFormat.format(to), 0);
            checkForError();
        } else {
            lastCommand = "cMD_94_0_0";
            // int command_094(int in_Type, String startDate, String endDate, int errorCode)
            lastErrorCode = FP.command_094(0, dateFormat.format(from), dateFormat.format(to), 0);
            checkForError();
        }
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        String dd = "", mm = "", yy = "", hh = "", nn = "", ss = "";
        int[] errorCode = new int[]{0};
        String[] dateTimeStr = new String[]{""};
        lastCommand = "cMD_62_0_1";
        lastErrorCode = FP.command_062(errorCode, dateTimeStr);
        checkForError();
        // dd-MM-yy hh:mm:ss [DST]
        String[] dtParts = dateTimeStr[0].split("\\s+");
        if (dtParts.length > 0) {
            String[] dParts = dtParts[0].split("-");
            if (dParts.length >= 3) {
                dd = dParts[0];
                mm = dParts[1];
                yy = dParts[2];
            }
            if (dtParts.length > 1) {
                String[] tParts = dtParts[1].split(":");
                if (tParts.length >= 3) {
                    hh = tParts[0];
                    nn = tParts[1];
                    ss = tParts[2];
                }    
            }
        }
        if (yy.length()==2) yy = "20"+yy;
        Date dateTime;
        dateTime = new GregorianCalendar(
            Integer.parseInt(yy)
            , Integer.parseInt(mm)
            , Integer.parseInt(dd)
            , Integer.parseInt(hh)
            , Integer.parseInt(nn)
            , Integer.parseInt(ss)
        ).getTime();
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        lastCommand = "command_061";
        String dateTimeStr = dateTimeFormat.format(dateTime);
        lastErrorCode = FP.command_061(dateTimeStr, 0);
        checkForError();
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        lastCommand = "command_044";
        lastErrorCode = FP.command_044(lineCount, 0);
        checkForError();
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "command_109";
        lastErrorCode = FP.command_109(0);
        checkForError();
    }
    
    @Override
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return getCurrentCheckInfo();
    }

    @Override
    public StrTable getJournalInfo() throws FPException {
        throw new FPException((long)-1, "Not supported!");
    }

    @Override
    public StrTable getJournal(boolean readAndErase) throws FPException {
        throw new FPException((long)-1, "Not supported!");
    }

    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        int[] errorCode = new int[]{0};
        int cashType = (ioSum < 0)?1:0; // 0 - cash in, 1 - cash out
        String[] cashSum = new String[]{""};
        String[] cashIn = new String[]{""};
        String[] cashOut = new String[]{""};

        lastCommand = "command_070";
        // int command_070(int reportType, String amount, int[] errorCode, String[] cashSum, String[] cashIn, String[] cashOut)
        lastErrorCode = FP.command_070(cashType, String.format(Locale.ROOT, "%.2f", Math.abs(ioSum)), errorCode, cashSum, cashIn, cashOut);
        checkForError();
        
        StrTable res = new StrTable();
        res.put("CashSum", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashSum[0])));
        res.put("CashIn", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashIn[0])));
        res.put("CashOut", String.format(Locale.ROOT, "%.2f", Double.parseDouble(cashOut[0])));
        return res;
    }
    
    
}
