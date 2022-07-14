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
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.eda.protocol.DeviceTremolV1;
import org.eda.protocol.DeviceTremolV1.CustomerDataType;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "Tremol fiscal devices native protocol"
    , usable = true
    , experimental = true
)
public class FPCTremolNative extends FPCBase {

    protected DeviceTremolV1 FP;
    protected String lastCommand;
    protected int lastErrorCode;
    protected String lastErrorMsg;
    
    protected CheckInfo lastCurrentCheckInfo = null;
    protected Date dtAfterOpenFiscalCheck = null;
    protected static final Handler FDLogHandler;
    static {
        FDLogHandler = new LogHandlerFiscalDevice();
        AbstractFiscalDevice.getLogger().addHandler(FDLogHandler);
    }
    
    /**
     * FPCDatecsECRICL constructor.
     * @throws Exception Throws an exception
     */
    public FPCTremolNative() throws Exception {
        super();
        
        lastCommand = "";
        lastErrorCode = 0;
        lastErrorMsg = "";
        
        
    }

    // Catch logs from AbstractFiscalDevice
    private static class LogHandlerFiscalDevice extends Handler {
//        private ControlPanel panel;

        public LogHandlerFiscalDevice() {}

//        public LogHandler(ControlPanel panel) {
//            this.panel = panel;
//        }

        @Override
        public void publish(LogRecord lr) {
            LOGGER.log(lr);
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
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "1"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "PMCash", "Payment number for cash", "Payment number for cash", 0));
                     addProperty(new FPProperty(Integer.class, "PMCard", "Payment number for debit/credit card", "Payment number for debit/credit card", 7));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 30));
                     addProperty(new FPProperty(
                        String.class
                        , "Printing", "Postphoned or step by step", "Postphoned or step by step"
                        , "SBS"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("SBS", "Step By Step");
                                    put("PSP", "Postphoned");
                                    put("BUF", "Buffered");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(
                        String.class
                        , "ReceiptFormat", "Receipt format", "Receipt format"
                        , "BRIEF"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("BRIEF", "Brief");
                                    put("DETAIL", "Detailed");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(
                        String.class
                        , "ReceiptVAT", "Print VAT in receipts", "Print VAT in receipts"
                        , "YES"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("YES", "Yes");
                                    put("NO", "No");
                                }}
                        )
                     ));
                }}

        );
        LinkedHashMap<String, String> payCodeMapList = new LinkedHashMap() {{
            put("0", "0 - В БРОЙ");
            put("1", "1 - Плащане 1");
            put("2", "2 - Плащане 2");
            put("3", "3 - Плащане 3");
            put("4", "4 - Плащане 4");
            put("5", "5 - Плащане 5");
            put("6", "6 - Плащане 6");
            put("7", "7 - Плащане 7");
            put("8", "8 - Плащане 8");
            put("9", "9 - Плащане 9");
            put("10", "10 - Плащане 10");
        }};
        params.addGroups(
            new FPPropertyGroup("Начини на плащане", "Начини на плащане по спецификация на НАП") {{
                 addProperty(new FPProperty( // 0
                    String.class
                    , "NRASCASH", "0 НАП SCash", "НАП Спецификация SCash - В БРОЙ"
                    , "0"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 1
                    String.class
                    , "NRASCHECKS", "1 НАП SChecks", "НАП Спецификация SChecks - С чек"
                    , "1"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 2
                    String.class
                    , "NRAST", "2 НАП SТ", "НАП Спецификация SТ - Талони"
                    , "2"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 3
                    String.class
                    , "NRASOT", "3 НАП SOТ", "НАП Спецификация SOТ - Сума по външни талони"
                    , "3"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));

                 addProperty(new FPProperty( // 4
                    String.class
                    , "NRASP", "4 НАП SP", "НАП Спецификация SP - Сума по амбалаж"
                    , "4"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 5
                    String.class
                    , "NRASSELF", "5 НАП SSelf", "НАП Спецификация SSelf - Сума по вътрешно обслужване"
                    , "5"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 6
                    String.class
                    , "NRASDMG", "6 НАП SDmg", "НАП Спецификация SDmg - Сума по повреди"
                    , "6"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 7
                    String.class
                    , "NRASCARDS", "7 НАП SCards", "НАП Спецификация SCards - Сума по кредитни/дебитни карти"
                    , "7"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 8
                    String.class
                    , "NRASW", "8 НАП SW", "НАП Спецификация SW - Сума по банкови трансфери"
                    , "8"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 9
                    String.class
                    , "NRASR1", "9 НАП SR1", "НАП Спецификация SR1 - Плащане НЗОК"
                    , "9"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 10
                    String.class
                    , "NRASR2", "10 НАП SR2", "НАП Спецификация SR2 - Резерв"
                    , "10"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
            }}
        );
        return params;
    }

    @Override
    protected int getLineWidth() {
        int lw = FP.getLineWidthFiscalText();
        if (lw == 0)
            lw = getParamInt("LWIDTH");
        if (lw == 0)
            lw = 32;
       return lw; 
    }

    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
        this.FP = new DeviceTremolV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
        this.FP.open();
        this.FP.setOpCode(getParam("OperatorCode"));
        this.FP.setOpPasswd(getParam("OperatorPass"));
        // Print Mode
        String printModeAbbr = getParam("Printing");
        DeviceTremolV1.ReceiptPrintModeType printMode = DeviceTremolV1.ReceiptPrintModeType.STEP_BY_STEP;
        switch(printModeAbbr) {
            case "PSP" : printMode = DeviceTremolV1.ReceiptPrintModeType.POSTPHONED; break;
            case "BUF" : printMode = DeviceTremolV1.ReceiptPrintModeType.BUFFERED; break;
            case "SBS" : printMode = DeviceTremolV1.ReceiptPrintModeType.STEP_BY_STEP; break;
        }
        this.FP.setReceiptPrintMode(printMode);
        // Receipt format
        DeviceTremolV1.ReceiptPrintFormatType receiptFormat = DeviceTremolV1.ReceiptPrintFormatType.DETAILED;
        String receiptFormatAbbr = getParam("ReceiptFormat");
        if (receiptFormatAbbr.equals("BRIEF"))
            receiptFormat = DeviceTremolV1.ReceiptPrintFormatType.BRIEF;
        else
            receiptFormat = DeviceTremolV1.ReceiptPrintFormatType.DETAILED;
        this.FP.setReceiptPrintFormat(receiptFormat);
		this.FP.setPaymenNumForCASH(paymenTypeToPaymentNum(paymentTypes.CASH));
        // Print VAT
        FP.setReceiptPrintVAT(getParam("ReceiptVAT").equals("YES"));
        
    }
    
    @Override
    public void destroy() {
        try {
            if (FP != null)
                FP.close();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        FP = null;
    }

    @Override
    public void readStatus() {
        if (FP != null)
            FP.getStatusBytes();
    }

    
    @Override
    public DeviceInfo getDeviceInfo() throws FPException {
        DeviceInfo di = new DeviceInfo();
        di.Model = FP.getModelName();
        di.Firmware = FP.getFWRev();
        di.SerialNum = FP.getSerialNum();
        di.FMNum = FP.getFMNum();
        di.IsFiscalized = FP.isFiscalized();
        di.Errors = String.join("\t", FP.getErrors());
        di.Warnings = String.join("\t", FP.getWarnings());
        di.IsReady = 
            !FP.isOpenFiscalCheck()
            && !FP.isOpenNonFiscalCheck()
            && !FP.isOpenFiscalCheckRev()
            && !FP.haveErrors();
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
        '0' – Payment 0
        '1' – Payment 1
        '2' – Payment 2
        '3' – Payment 3
        Следващите са за новите устройства
        '4' – Payment 4
        '5' – Payment 5
        '6' – Payment 6
        '7' – Payment 7
        '8' – Payment 8
        '9' – Payment 9
        '10' – Payment 10
        '11' – Payment 11
        */        
        String pc = "0";
        switch (payType) {
            case CASH : pc = params.get("PMCache", "0"); break;
            case CREDIT : pc = "3"; break;
            case CARD : pc = params.get("PMCard", "7"); break;
            case CHECK : pc = "1"; break;
            case CUSTOM1 : pc = "2"; break;
            case CUSTOM2 : pc = "3"; break;

            case NRASCASH :  pc = params.get("NRASCASH", "0"); break;
            case NRASCHECKS :  pc = params.get("NRASCHECKS", "1"); break;
            case NRAST :  pc = params.get("NRAST", "2"); break;
            case NRASOT :  pc = params.get("NRASOT", "3"); break;
            case NRASP :  pc = params.get("NRASP", "4"); break;
            case NRASSELF :  pc = params.get("NRASSELF", "5"); break;
            case NRASDMG :  pc = params.get("NRASDMG", "6"); break;
            case NRASCARDS :  pc = params.get("NRASCARDS", "7"); break;
            case NRASW :  pc = params.get("NRASW", "8"); break;
            case NRASR1 :  pc = params.get("NRASR1", "9"); break;
            case NRASR2 :  pc = params.get("NRASR2", "10"); break;
        }
        return pc;
    }

    protected int paymenTypeToPaymentNum(paymentTypes payType) {
        /*		
        <Res Name="NamePayment0" Value="Лева      " Type="Text" />
        <Res Name="NamePayment1" Value="Чек       " Type="Text" />
        <Res Name="NamePayment2" Value="Талон     " Type="Text" />
        <Res Name="NamePayment3" Value="В.Талон   " Type="Text" />
        <Res Name="NamePayment4" Value="Амбалаж   " Type="Text" />
        <Res Name="NamePayment5" Value="Обслужване" Type="Text" />
        <Res Name="NamePayment6" Value="Повреди   " Type="Text" />
        <Res Name="NamePayment7" Value="Карта     " Type="Text" />
        <Res Name="NamePayment8" Value="Банка     " Type="Text" />
        <Res Name="NamePayment9" Value="Резерв 1  " Type="Text" />
        <Res Name="NamePayment10" Value="Резерв 2  " Type="Text" />
        <Res Name="NamePayment11" Value="Евро      " Type="Text" />
        <Res Name="ExchangeRate" Value="1.95583" Type="Decimal_with_format" Format="0000.00000" />
        */        
        int pc = 0;
        switch (payType) {
            case CASH : pc = params.getInt("PMCache", 0); break;
            case CREDIT : pc = 3; break;
            case CARD : pc = params.getInt("PMCard", 7); break;
            case CHECK : pc = 1; break;
            case CUSTOM1 : pc = 2; break;
            case CUSTOM2 : pc = 3; break;

            case NRASCASH :  pc = params.getInt("NRASCASH", 0); break;
            case NRASCHECKS :  pc = params.getInt("NRASCHECKS", 1); break;
            case NRAST :  pc = params.getInt("NRAST", 2); break;
            case NRASOT :  pc = params.getInt("NRASOT", 3); break;
            case NRASP :  pc = params.getInt("NRASP", 4); break;
            case NRASSELF :  pc = params.getInt("NRASSELF", 5); break;
            case NRASDMG :  pc = params.getInt("NRASDMG", 6); break;
            case NRASCARDS :  pc = params.getInt("NRASCARDS", 7); break;
            case NRASW :  pc = params.getInt("NRASW", 8); break;
            case NRASR1 :  pc = params.getInt("NRASR1", 9); break;
            case NRASR2 :  pc = params.getInt("NRASR2", 10); break;
        }
        return pc;
    }
	
    protected String revTypeChar(fiscalCheckRevType revType) {
        /*
        StornoReason
        1 symbol for reason of storno operation with value:
        - '0' – Operator error
        - '1' – Goods Claim or Goods return
        - '2' – Tax relief
        */
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
        /*
        * 'Z' for Z-report, 'X' for X-report
        * '' default, 'D' - by departments
        
        */
        String[] options = new String[]{"X",""};
        
        switch (drType) {
            case Z :
               options[0] = "Z";
               options[1] = "";
               break;
            case ZD :
               options[0] = "Z";
               options[1] = "D";
               break;
            case X :
               options[0] = "X";
               options[1] = "";
               break;
            case XD :
               options[0] = "X";
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
                    LOGGER.warning(ex.getMessage());
                }
                // if it is continue to be open try to pay rest amount and close
                if (FP.isOpenFiscalCheck() || FP.isOpenFiscalCheckRev()) {
                    try {
                        FP.cmdPrintFiscalText("АВАРИЙНО ЗАТВАРЯНЕ!");
                        FP.cmdTotal("Аварийно плащане", "", 0, "");
                    } catch (Exception ex) {
                        LOGGER.warning(ex.getMessage());
                    }
                    try {
                        FP.cmdCloseFiscalCheck();
                    } catch (Exception ex) {
                        LOGGER.warning(ex.getMessage());
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
    public void setInvoiceFiscalCheckOn(CustomerInfo custInfo) throws FPException {
        super.setInvoiceFiscalCheckOn(custInfo); 
        
        DeviceTremolV1.CustomerDataType customerData = new CustomerDataType();
        customerData.UIC = custInfo.UIC;
        switch (custInfo.UICType) {
            case EGN : customerData.UICType = DeviceTremolV1.UICTypeType.EGN; break;
            case FOREIGN : customerData.UICType = DeviceTremolV1.UICTypeType.FOREIGN; break;
            case NRA : customerData.UICType = DeviceTremolV1.UICTypeType.NRA; break;
            case BULSTAT : 
                customerData.UICType = DeviceTremolV1.UICTypeType.BULSTAT;
                break;
        }
        customerData.VATNumber = custInfo.VATNumber;
        customerData.address = custInfo.address;
        customerData.buyer = custInfo.buyer;
        customerData.recipient = custInfo.recipient;
        customerData.seller = custInfo.seller;
        FP.setCustomerData(customerData);
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
        try {
            FP.cmdCloseFiscalCheck();
        } catch (IOException ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, String discountPerc)  throws FPException {
        String[] lines = splitOnLines(text, getLineWidth());
        lastCommand = "sell";
        
        try {
            FP.cmdSell(
                lines[0]+((lines.length > 1)?"\n"+lines[1]:"")
                , this.taxGroupToChar(taxCode), price, quantity
                , ""
                , discountPerc
            );
        } catch (IOException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, String discountPerc) throws FPException {
        this.sell(text, taxCode, price, 0, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, String discountPerc)  throws FPException{
        String[] lines = splitOnLines(text, getLineWidth());

        lastCommand = "sellDept";
        
        try {
            FP.cmdSellDept(
                lines[0]+((lines.length > 1)?"\n"+lines[1]:"")
                , deptCode, price, quantity
                , ""
                , discountPerc
            );
        } catch (IOException ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public void printFiscalText(String text) throws FPException {
        lastCommand = "printFiscalText";
        String[] lines = splitOnLines(text, getLineWidth());
        
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
        String[] lines = splitOnLines(text, getLineWidth());
        
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
        String[] lines = splitOnLines(text, getLineWidth());
        
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
            String resFBType = response.containsKey("Type")?response.get("Type"):"";
            
            if (resFBType.equals("FISCAL")) {
                res.FCType = fiscalCheckType.FISCAL;
            } else if (resFBType.equals("FISCAL_REV")) {
                res.FCType = fiscalCheckType.FISCAL_REV;
            } else if (FP.isOpenNonFiscalCheck()) {
                res.FCType = fiscalCheckType.NON_FISCAL;
            }        
            res.IsInvoice = response.get("Inv").equals("1");
            if (res.IsInvoice)
                res.InvNum = response.get("InvNum");
            res.SellCount = parseDouble(response.get("SellCount"));
            res.SellAmount = parseDouble(response.get("SellAmount"));
            res.PayAmount = parseDouble(response.get("PayAmount"));
            try {
                String DocNum = FP.cmdLastDocNum();
//                if (res.IsOpen) {
//                    Integer iDocNum = Integer.parseInt(DocNum);
//                    iDocNum++;
//                    DocNum = String.format("%07d", iDocNum);
//                }
                res.DocNum = DocNum;
            } catch (Exception ex) {
                LOGGER.severe(ex.getMessage());
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
            LOGGER.info(e.getMessage());
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
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }    
            if (ci != null) {
                Date lastDocDate = null;
//                try {
//                    lastDocDate = FP.cmdLastFiscalCheckDate();
//                } catch (IOException ex) {
//                }
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
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
//            res.DocDate = DateUtils.parse(response.get("DocDate"), Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
            if (response.containsKey("TaxA")) {
                res.TaxA = parseDouble(response.get("TaxA"));
                res.TaxB = parseDouble(response.get("TaxB"));
                res.TaxC = parseDouble(response.get("TaxC"));
                res.TaxD = parseDouble(response.get("TaxD"));
                res.TaxE = parseDouble(response.get("TaxE"));
                res.TaxF = parseDouble(response.get("TaxF"));
                res.TaxG = parseDouble(response.get("TaxG"));
                res.TaxH = parseDouble(response.get("TaxH"));
            }    
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

    @Override
    public StrTable readPaymentMethods() throws FPException{
        StrTable res = new StrTable();
        lastCommand = "readPaymentMethods";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadPaymentMethods();
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable readDepartments() throws FPException{
        StrTable res = new StrTable();
        lastCommand = "readDepartments";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadDepartments();
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable readTaxGroups() throws FPException {
        StrTable res = new StrTable();
        lastCommand = "readTaxGroups";
        try {
            LinkedHashMap<String,String> res_ = FP.cmdReadTaxGroups();
            res.putAll(res_);
        } catch (IOException ex) {
            throw createException(ex);
        }
        return res;
    }

}
