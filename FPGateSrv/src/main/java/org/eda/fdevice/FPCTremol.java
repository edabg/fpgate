/*
 * Copyright (C) 2019 EDA Ltd.
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

import TremolZFP.CurrentOrLastReceiptPaymentAmountsRes;
import TremolZFP.CurrentReceiptInfoRes;
import TremolZFP.DailyAvailableAmountsRes;
import TremolZFP.DailyAvailableAmounts_OldRes;
import TremolZFP.DepartmentRes;
import TremolZFP.InvoiceRangeRes;
import TremolZFP.LastDailyReportInfoRes;
import TremolZFP.OptionChange;
import TremolZFP.OptionChangeType;
import TremolZFP.OptionDisplay;
import TremolZFP.OptionFiscalRcpPrintType;
import TremolZFP.OptionInvoiceCreditNotePrintType;
import TremolZFP.OptionInvoicePrintType;
import TremolZFP.OptionIsReceiptOpened;
import TremolZFP.OptionNonFiscalPrintType;
import TremolZFP.OptionPayType;
import TremolZFP.OptionPaymentType;
import TremolZFP.OptionPrintAvailability;
import TremolZFP.OptionPrintVAT;
import TremolZFP.OptionPrinting;
import TremolZFP.OptionReceiptFormat;
import TremolZFP.OptionReportFormat;
import TremolZFP.OptionStornoRcpPrintType;
import TremolZFP.OptionStornoReason;
import TremolZFP.OptionUICType;
import TremolZFP.OptionVATClass;
import TremolZFP.OptionZeroing;
import TremolZFP.PaymentsRes;
import TremolZFP.Payments_OldRes;
import TremolZFP.RegistrationInfoRes;
import TremolZFP.SerialAndFiscalNumsRes;
import TremolZFP.StatusRes;
import TremolZFP.VATratesRes;
import TremolZFP.VersionRes;
import static java.lang.Math.abs;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eda.protocol.FDException;
import static org.eda.fdevice.FPCBase.LOGGER;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(description = "Tremol fiscal devices via ZFPLabServer")
public class FPCTremol extends FPCBase {

    protected String lastCommand;
    protected int lastErrorCode;
    protected String lastErrorMsg;
    protected CheckInfo lastCurrentCheckInfo = null;
    protected Date dtAfterOpenFiscalCheck = null;
    protected DeviceInfo devInfo;
    
    protected int CONST_TEXTLENGTH=32;
    protected int CONST_DEPTNUM=32;
    protected int CONST_PAYNUM=10;
    protected boolean isOldDevice = false;

    HashMap<String, String> statusDefs = new HashMap<>();
    List<String> statusDefsError = new ArrayList<>();
    List<String> statusDefsWarning = new ArrayList<>();
    
    // Errors and warnings collector
    protected List<String> mErrors = new ArrayList<>();
    protected List<String> mWarnings = new ArrayList<>();
    
    protected TremolZFP.FP fp = null;

    public FPCTremol() throws Exception {
        super();
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(String.class, "ServerURL", "ZFP Lab Server URL", "ZFP Lab Server URL", "http://localhost:4444/"));
                     addProperty(new FPProperty(
                        String.class
                        , "LinkType", "Type of link to device", "Type of link to device"
                        , "Serial"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("TCP", "TCP");
                                    put("Serial", "Serial");
                                }}
                        )
                     ));
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
                     addProperty(new FPProperty(String.class, "IPAddr", "IP Address", "IP Address of fiscal device", ""));
                     addProperty(new FPProperty(Integer.class, "IPPort", "IP Port", "IP port of fiscal device", 8000));
                     addProperty(new FPProperty(String.class, "IPPassword", "IP Access password", "IP access password", "1234"));
                     
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "1"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "PMCash", "Payment number for cash", "Payment number for cash", 0));
                     addProperty(new FPProperty(Integer.class, "PMCard", "Payment number for debit/credit card", "Payment number for debit/credit card", 7));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 32));
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
    public void init() throws Exception, FPException {
        fp = new TremolZFP.FP();
        LOGGER.log(Level.INFO, "ZFPLabServer version defs:"+Integer.toString(fp.getVersionDef()));
        fp.setServerAddress(getParam("ServerURL"));
        if (getParam("LinkType").equals("TCP")) {
            fp.ServerSetDeviceTcpSettings(getParam("IPAddr"), getParamInt("IPPort"), getParam("IPPassword"));
        } else {
            fp.ServerSetDeviceSerialPortSettings(getParam("COM"), getParamInt("BaudRate"));
        }
//        this.FP = new DeviceDatecsDPV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
//        this.FP.open();
        readParameters();
    }
    
    @Override
    public void destroy() {
        fp = null;
    }

    protected void clearErrors() {
        mErrors.clear();
        mWarnings.clear();
    }
    
    protected void err(String msg) {
        mErrors.add(msg);
        LOGGER.severe("Error (cmd:"+((lastCommand != null)?lastCommand:"n/a")+"): "+msg);
    }

    protected void warn(String msg) {
        mWarnings.add(msg);
        LOGGER.warning("Warning (cmd:"+((lastCommand != null)?lastCommand:"n/a")+"): "+msg);
    }
    
    protected void debug(String msg) {
        LOGGER.fine("Debug (cmd:"+((lastCommand != null)?lastCommand:"n/a")+"): "+msg);
    }
    
    protected void readParameters() {
        try {
            devInfo = getDeviceInfo();
            try {
                isOldDevice = devInfo.Model.matches("^.+V2$");
                LOGGER.info(isOldDevice?"Old Device V2 detected":"New device model detected");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
            
            CONST_TEXTLENGTH = getParamInt("LWIDTH");
            CONST_PAYNUM = 11;
            CONST_DEPTNUM = 40;
            /*
            Information
            <1Dh><Info> where: Info – character 49h - 'I'
            <'I'><Number of printable characters per line[2]>
            <;> <PLU number[5]>
            <;> <Departments number[2]>
            <;> <Operators number[2]>
            <;> <VAT classs number[1]>
            <;> <header lines number[2]>
            <;> <payments number[2]>
            <;> <logo number[2]>
            <;> <reserve=’00’[2]>
            <;> <receipt transaction number[3]>
            <;> <customers base info [4]>
            <0Ah>
            */
            fp.RawWrite(new byte[] {0x1D, 0x49});
            byte[] res = fp.RawRead(0d, "\n");
            String textres = new String(res);
            //System.out.println(textres);
            if (textres.startsWith("I")) {
                textres = textres.substring(1);
                String[] parts = textres.split(";");
                try {
                    CONST_TEXTLENGTH = Integer.parseInt(parts[0].trim());
                } catch (Exception e) {
                    LOGGER.warning("Невалидна стойност за максимална дължина на текста!"+e.getMessage());
                }
                try {
                    if (parts.length > 2)
                        CONST_DEPTNUM = Integer.parseInt(parts[2].trim());
                } catch (Exception e) {
                    LOGGER.warning("Невалидна стойност за брой департаменти!"+e.getMessage());
                }
                try {
                    if (parts.length > 6)
                        CONST_PAYNUM = Integer.parseInt(parts[6].trim());
                } catch (Exception e) {
                    LOGGER.warning("Невалидна стойност за брой плащания!"+e.getMessage());
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    protected String fixCharsetBug(String str) {
        return str;
//        if (!Charset.defaultCharset().name().equalsIgnoreCase("UTF-8")) {
//            return new String(str.getBytes(Charset.defaultCharset()), Charset.forName("UTF-8"));
//        } else
//            return str;
    }
    
    @Override
    public DeviceInfo getDeviceInfo() throws FPException {
        DeviceInfo di = new DeviceInfo();
        try {
//            Payments_OldRes po = fp.ReadPayments_Old();
            VersionRes ver = fp.ReadVersion();
            di.Model = ver.Model;
            di.Firmware = fixCharsetBug(ver.Version);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            SerialAndFiscalNumsRes si = fp.ReadSerialAndFiscalNums();
            di.SerialNum = si.SerialNumber;
            di.FMNum = si.FMNumber;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        try {
            StatusRes status = fp.ReadStatus();
            di.IsFiscalized = status.FM_fiscalized;
            // TODO: Check for errors
            checkStatus(status);
//            err("Тестова грешка 1");
//            err("Тестова грешка 2");
//            warn("Тестовo предупреждение 1");
//            warn("Тестовo предупреждение 2");
            di.Errors = String.join("\t", mErrors);
            di.Warnings = String.join("\t", mWarnings);
            di.IsReady = 
                !status.Opened_Fiscal_Receipt
                && !status.Opened_Non_fiscal_Receipt
                && (mErrors.size() == 0)
            ;
        } catch (Exception ex) {
            throw createException(ex);
        }
        
        return di;
    }
    
    protected OptionVATClass taxGroupToVATClass(taxGroup taxCode) {
        OptionVATClass res; 
        switch (taxCode) {
            case A : res = OptionVATClass.VAT_Class_0; break;
            case B : res = OptionVATClass.VAT_Class_1; break;
            case C : res = OptionVATClass.VAT_Class_2; break;
            case D : res = OptionVATClass.VAT_Class_3; break;
            case E : res = OptionVATClass.VAT_Class_4; break;
            case F : res = OptionVATClass.VAT_Class_5; break;
            case G : res = OptionVATClass.VAT_Class_6; break;
            case H : res = OptionVATClass.VAT_Class_7; break;
            default :
                res = OptionVATClass.VAT_Class_0;
        }
        return res;
    }

    protected taxGroup VATClassToTaxGroup(OptionVATClass VATClass) {
        taxGroup res; 
        switch (VATClass) {
            case VAT_Class_0 : res = taxGroup.A; break;
            case VAT_Class_1 : res = taxGroup.B; break;
            case VAT_Class_2 : res = taxGroup.C; break;
            case VAT_Class_3 : res = taxGroup.D; break;
            case VAT_Class_4 : res = taxGroup.E; break;
            case VAT_Class_5 : res = taxGroup.F; break;
            case VAT_Class_6 : res = taxGroup.G; break;
            case VAT_Class_7 : res = taxGroup.H; break;
            default :
                res = taxGroup.A; break;
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
    
    protected String VATClassToCharL(OptionVATClass VATClass) {
        return taxGroupToCharL(VATClassToTaxGroup(VATClass));
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
    
    protected OptionPaymentType paymentNumToOptionPaymentType(int payNum, OptionPaymentType defaultPaymentType) {
        OptionPaymentType paymentType = defaultPaymentType;
        switch (payNum) {
            case 0 : paymentType = OptionPaymentType.Payment_0;break;
            case 1 : paymentType = OptionPaymentType.Payment_1;break;
            case 2 : paymentType = OptionPaymentType.Payment_2;break;
            case 3 : paymentType = OptionPaymentType.Payment_3;break;
            case 4 : paymentType = OptionPaymentType.Payment_4;break;
            case 5 : paymentType = OptionPaymentType.Payment_5;break;
            case 6 : paymentType = OptionPaymentType.Payment_6;break;
            case 7 : paymentType = OptionPaymentType.Payment_7;break;
        }
        return paymentType;
    }
    protected OptionPaymentType paymenTypeToOptionPaymentType(paymentTypes payType) {
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
        
        Old models
        <Res Name="NamePayment0" Value="Лева      " Type="Text" />
        <Res Name="NamePayment1" Value="Карта     " Type="Text" />
        <Res Name="NamePayment2" Value="Чек     " Type="Text" />
        <Res Name="NamePayment3" Value="Талон     " Type="Text" />
        <Res Name="NamePayment4" Value="Евро      " Type="Text" />
        <Res Name="ExchangeRate" Value="1.95583" Type="Decimal_with_format" Format="0000.00000" />
        ...
        
        */        
        OptionPaymentType pc = OptionPaymentType.Payment_0;
        switch (payType) {
            case CASH : 
                pc = paymentNumToOptionPaymentType(params.getInt("PMCache", 0), OptionPaymentType.Payment_0);
                break;
            case CREDIT : pc = OptionPaymentType.Payment_3; break;
            case CARD : 
                pc = paymentNumToOptionPaymentType(params.getInt("PMCard", 7), OptionPaymentType.Payment_7);
                break;
            case CHECK : pc = OptionPaymentType.Payment_1; break;
            case CUSTOM1 : pc = OptionPaymentType.Payment_2; break;
            case CUSTOM2 : pc = OptionPaymentType.Payment_3; break;
            
            case NRASCASH : pc = paymentNumToOptionPaymentType(params.getInt("NRASCASH", 0), OptionPaymentType.Payment_0); break;
            case NRASCHECKS :  pc = paymentNumToOptionPaymentType(params.getInt("NRASCHECKS", 1), OptionPaymentType.Payment_1); break;
            case NRAST :  pc = paymentNumToOptionPaymentType(params.getInt("NRAST", 2), OptionPaymentType.Payment_2); break;
            case NRASOT :  pc = paymentNumToOptionPaymentType(params.getInt("NRASOT", 3), OptionPaymentType.Payment_3); break;
            case NRASP :  pc = paymentNumToOptionPaymentType(params.getInt("NRASP", 4), OptionPaymentType.Payment_4); break;
            case NRASSELF :  pc = paymentNumToOptionPaymentType(params.getInt("NRASSELF", 5), OptionPaymentType.Payment_5); break;
            case NRASDMG :  pc = paymentNumToOptionPaymentType(params.getInt("NRASDMG", 6), OptionPaymentType.Payment_6); break;
            case NRASCARDS :  pc = paymentNumToOptionPaymentType(params.getInt("NRASCARDS", 7), OptionPaymentType.Payment_7); break;
            case NRASW :  pc = paymentNumToOptionPaymentType(params.getInt("NRASW", 8), OptionPaymentType.Payment_8); break;
            case NRASR1 :  pc = paymentNumToOptionPaymentType(params.getInt("NRASR1", 9), OptionPaymentType.Payment_9); break;
            case NRASR2 :  pc = paymentNumToOptionPaymentType(params.getInt("NRASR2", 10), OptionPaymentType.Payment_10); break;
        }
        return pc;
    }

    protected OptionStornoReason revTypeToStornoReason(fiscalCheckRevType revType) {
        OptionStornoReason rt = OptionStornoReason.Operator_error;
        switch (revType) {
            case OP_ERROR :
                rt = OptionStornoReason.Operator_error;
                break;
            case RETURN_CLAIM :
                rt = OptionStornoReason.Goods_Claim_or_Goods_return;
                break;
            case REDUCTION :
                rt = OptionStornoReason.Tax_relief;
                break;
        }
        return rt;
    }
    
    protected FPException createException() {
        return new FPException((long)lastErrorCode, lastCommand+":"+getLastErrorMessage());
    }

    protected FPException createException(int errorCode, String errorMsg) {
        lastErrorCode = errorCode;
        lastErrorMsg = errorMsg;
        return createException();
    }

    protected FPException createException(Exception ex) {
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
    
    protected void initStatusMap() {
/*
S0=10001000
S1=10000000
S2=10000000
S3=11101010
S4=10000110
S5=10011010
Msg_S0_3=Не е свързан клиентски дисплей
Msg_S3_1=SW1.2
Msg_S3_3=SW1.4
Msg_S3_5=SW1.6
Msg_S3_6=SW1.7
Msg_S3_7=SW1.8
Msg_S4_1=Зададен е ЕИК
Msg_S4_2=Зададени са индивидуален номер на ФУ и номер на ФП.
Msg_S5_1=Фискалната памет e форматирана
Msg_S5_3=ФУ е във фискален режим
Msg_S5_4=Зададени са поне веднъж данъчните ставки
DeviceInfo=FP-2000,1.00BG 23NOV18 1000,FFFF,0101011100000000,DT278392,02278392
Model=FP-2000
Switches=0101011100000000
FWRev=1.00BG
FWDT=23NOV18 1000

- FM_Read_only - FM Read only
- Power_down_in_opened_fiscal_receipt - Power down in opened fiscal receipt
- Printer_not_ready_overheat - Printer not ready - overheat
- DateTime_not_set - DateTime not set
- DateTime_wrong - DateTime wrong
- RAM_reset - RAM reset
- Hardware_clock_error - Hardware clock error
- Printer_not_ready_no_paper - Printer not ready - no paper
- Reports_registers_Overflow - Reports registers Overflow
- Customer_report_is_not_zeroed - Customer report is not zeroed
- Daily_report_is_not_zeroed - Daily report is not zeroed
- Article_report_is_not_zeroed - Article report is not zeroed
- Operator_report_is_not_zeroed - Operator report is not zeroed
- Duplicate_printed - Duplicate printed
- Opened_Non_fiscal_Receipt - Opened Non-fiscal Receipt
- Opened_Fiscal_Receipt - Opened Fiscal Receipt
- Opened_Fiscal_Detailed_Receipt - Opened Fiscal Detailed Receipt
- Opened_Fiscal_Receipt_with_VAT - Opened Fiscal Receipt with VAT
- Opened_Invoice_Fiscal_Receipt - Opened Invoice Fiscal Receipt
- SD_card_near_full - SD card near full
- SD_card_full - SD card full
- No_FM_module - No FM module
- FM_error - FM error
- FM_full - FM full
- FM_near_full - FM near full
- Decimal_point - Decimal point (1=fract, 0=whole)
- FM_fiscalized - FM fiscalized
- FM_produced - FM produced
- Printer_automatic_cutting - Printer: automatic cutting
- External_display_transparent_display - External display: transparent display
- Speed_is_9600 - Speed is 9600
- Drawer_automatic_opening - Drawer: automatic opening
- Customer_logo_included_in_the_receipt - Customer logo included in the receipt
- Wrong_SIM_card - Wrong SIM card
- Blocking_3_days_without_mobile_operator - Blocking 3 days without mobile operator
- No_task_from_NRA - No task from NRA
- Wrong_SD_card - Wrong SD card
- Deregistered - Deregistered
- No_SIM_card - No SIM card
- No_GPRS_Modem - No GPRS Modem
- No_mobile_operator - No mobile operator
- No_GPRS_service - No GPRS service
- Near_end_of_paper - Near end of paper
- Unsent_data_for_24_hours - Unsent data for 24 hours

        
        */   
        statusDefs.clear();
        statusDefs.put("FM_Read_only","FM Read only");
        statusDefs.put("Power_down_in_opened_fiscal_receipt","Power down in opened fiscal receipt");
        statusDefs.put("Printer_not_ready_overheat","Printer not ready - overheat");
        statusDefs.put("DateTime_not_set","DateTime not set");
        statusDefs.put("DateTime_wrong","DateTime wrong");
        statusDefs.put("RAM_reset","RAM reset");
        statusDefs.put("Hardware_clock_error","Hardware clock error");
        statusDefs.put("Printer_not_ready_no_paper","Printer not ready - no paper");
        statusDefs.put("Reports_registers_Overflow","Reports registers Overflow");
        statusDefs.put("Customer_report_is_not_zeroed","Customer report is not zeroed");
        statusDefs.put("Daily_report_is_not_zeroed","Daily report is not zeroed");
        statusDefs.put("Article_report_is_not_zeroed","Article report is not zeroed");
        statusDefs.put("Operator_report_is_not_zeroed","Operator report is not zeroed");
        statusDefs.put("Duplicate_printed","Duplicate printed");
        statusDefs.put("Opened_Non_fiscal_Receipt","Opened Non-fiscal Receipt");
        statusDefs.put("Opened_Fiscal_Receipt","Opened Fiscal Receipt");
        statusDefs.put("Opened_Fiscal_Detailed_Receipt","Opened Fiscal Detailed Receipt");
        statusDefs.put("Opened_Fiscal_Receipt_with_VAT","Opened Fiscal Receipt with VAT");
        statusDefs.put("Opened_Invoice_Fiscal_Receipt","Opened Invoice Fiscal Receipt");
        statusDefs.put("SD_card_near_full","SD card near full");
        statusDefs.put("SD_card_full","SD card full");
        statusDefs.put("No_FM_module","No FM module");
        statusDefs.put("FM_error","FM error");
        statusDefs.put("FM_full","FM full");
        statusDefs.put("FM_near_full","FM near full");
        statusDefs.put("Decimal_point","Decimal point (1=fract, 0=whole)");
        statusDefs.put("FM_fiscalized","FM fiscalized");
        statusDefs.put("FM_produced","FM produced");
        statusDefs.put("Printer_automatic_cutting","Printer: automatic cutting");
        statusDefs.put("External_display_transparent_display","External display: transparent display");
        statusDefs.put("Speed_is_9600","Speed is 9600");
        statusDefs.put("Drawer_automatic_opening","Drawer: automatic opening");
        statusDefs.put("Customer_logo_included_in_the_receipt","Customer logo included in the receipt");
        statusDefs.put("Wrong_SIM_card","Wrong SIM card");
        statusDefs.put("Blocking_3_days_without_mobile_operator","Blocking 3 days without mobile operator");
        statusDefs.put("No_task_from_NRA","No task from NRA");
        statusDefs.put("Wrong_SD_card","Wrong SD card");
        statusDefs.put("Deregistered","Deregistered");
        statusDefs.put("No_SIM_card","No SIM card");
        statusDefs.put("No_GPRS_Modem","No GPRS Modem");
        statusDefs.put("No_mobile_operator","No mobile operator");
        statusDefs.put("No_GPRS_service","No GPRS service");
        statusDefs.put("Near_end_of_paper","Near end of paper");
        statusDefs.put("Unsent_data_for_24_hours","Unsent data for 24 hours");
        
        statusDefsError.clear();
        statusDefsError.add("FM_Read_only");
        statusDefsError.add("Printer_not_ready_overheat");
        statusDefsError.add("DateTime_not_set");
        statusDefsError.add("DateTime_wrong");
        statusDefsError.add("Hardware_clock_error");
        statusDefsError.add("Printer_not_ready_no_paper");
        statusDefsError.add("Reports_registers_Overflow");
        statusDefsError.add("No_FM_module");
        statusDefsError.add("FM_error");
        statusDefsError.add("FM_full");
        statusDefsError.add("Wrong_SIM_card");
        statusDefsError.add("Deregistered");
        statusDefsError.add("No_SIM_card");
        statusDefsError.add("No_GPRS_Modem");
        
        statusDefsWarning.clear();
        statusDefsWarning.add("SD_card_near_full");
        statusDefsWarning.add("FM_near_full");
        statusDefsWarning.add("Blocking_3_days_without_mobile_operator");
        statusDefsWarning.add("No_task_from_NRA");
        statusDefsWarning.add("Near_end_of_paper");
        statusDefsWarning.add("Unsent_data_for_24_hours");
    }

    protected StrTable checkStatus(StatusRes status) throws FPException {
        StrTable res = new StrTable();
        try {
            clearErrors();
            int i = 0;
            for (String key : statusDefs.keySet()) {
                Field fld = status.getClass().getField(key);
                if ((fld != null) && fld.getType().getName().equals("boolean")) {
                    if (fld.getBoolean(status)) {
                        res.put("MSG_"+Integer.toString(++i), statusDefs.get(key));
                        if (statusDefsError.contains(key))
                            err(statusDefs.get(key));
                        if (statusDefsWarning.contains(key))
                            warn(statusDefs.get(key));
                    }
                }    
            }    
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    } 
    
    @Override
    public StrTable getPrinterStatus() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        StrTable res = new StrTable();
        lastCommand = "getPrinterStatus";

        try {
            VersionRes ver = fp.ReadVersion();
            res.put("Model", ver.Model);
            res.put("FWRev", fixCharsetBug(ver.Version));
            res.put("CertNum", ver.CertificateNum);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            SerialAndFiscalNumsRes si = fp.ReadSerialAndFiscalNums();
            res.put("SerialNum", si.SerialNumber);
            res.put("FMNum", si.FMNumber);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        try {
            StatusRes status = fp.ReadStatus();
            res.put("IsFiscalized", status.FM_fiscalized?"1":"0");
            res.put("IsOpenFiscalCheck", status.Opened_Fiscal_Receipt?"1":"0");
            res.put("IsOpenFiscalCheckRev", "");
            res.put("IsOpenNonFiscalCheck", status.Opened_Non_fiscal_Receipt?"1":"0");
            res.putAll(checkStatus(status));
        } catch (Exception ex) {
            throw createException(ex);
        }
        
        try {
            RegistrationInfoRes ri = fp.ReadRegistrationInfo();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            res.put("NRARegistrationNumber",ri.NRARegistrationNumber);
            res.put("NRARegistrationNumber",dateFormat.format(ri.NRARegistrationDate));
            res.put("UIC",ri.UIC);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        res.put("LastDocNum", getLastPrintDoc());
        return res;
    }

    @Override
    public StrTable getDiagnosticInfo() throws FPException {
        return getPrinterStatus();
    }

    @Override
    public String customCmd(String CMD, String params) throws FPException {
        String res = "NOT IMPLEMENTED";
        return res;
    }

    
    @Override
    public void cancelFiscalCheck() throws FPException {
        lastCommand = "cancelFiscalCheck";
        try {
            fp.CancelReceipt();
        } catch (Exception ex) {
            throw createException();       
        }
    }

    @Override
    public void abnormalComplete() throws FPException {
        // First Try to Cancel Fiscal Receipt
        lastCommand = "abnormalComplete";
        try {
            fp.CancelReceipt();
        } catch (Exception ex) {
//            throw createException();       
        }
        // try to Close non-fiscal check
        try {
            fp.CloseNonFiscalReceipt();
        } catch (Exception ex) {
//            throw createException(ex);
        }
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
            fp.ProgOperator(Double.parseDouble(code), fullName, passwd);
        } catch (Exception ex) {
            throw createException(ex);
        }
        
    }

    @Override
    public void openFiscalCheck() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheck";
        try {
            
            if (invoiceMode) {
                OptionInvoicePrintType printType = OptionInvoicePrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionInvoicePrintType.Postponed_Printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionInvoicePrintType.Buffered_Printing;
                OptionUICType uicType = OptionUICType.Bulstat;
                switch (customer.UICType) {
                    case BULSTAT : uicType = OptionUICType.Bulstat; break;
                    case EGN : uicType = OptionUICType.EGN; break;
                    case FOREIGN : uicType = OptionUICType.Foreigner_Number; break;
                    case NRA : uicType = OptionUICType.NRA_Official_Number; break;
                }
                fp.OpenInvoiceWithFreeCustomerData(
                    getParamDouble("OperatorCode"),  getParam("OperatorPass")
                    , printType
                    , customer.recipient, customer.buyer, customer.VATNumber
                    , customer.UIC, customer.address, uicType, ""
                );
            } else {
                OptionFiscalRcpPrintType printType = OptionFiscalRcpPrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionFiscalRcpPrintType.Postponed_printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionFiscalRcpPrintType.Buffered_printing;
                fp.OpenReceipt(
                    getParamDouble("OperatorCode"), getParam("OperatorPass")
                    , getParam("ReceiptFormat").equals("DETAIL")?OptionReceiptFormat.Detailed:OptionReceiptFormat.Brief
                    , getParam("ReceiptVAT").equals("NO")?OptionPrintVAT.No:OptionPrintVAT.Yes
                    , printType
                    , ""
                );
            }
            try {
                dtAfterOpenFiscalCheck = getDateTime();
            } catch (Exception e) {
            }
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void openFiscalCheck(String UNS) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        lastCommand = "openFiscalCheckUNS";
        try {
            if (invoiceMode) {
                // TODO: Add paramteres for invoice!
                OptionInvoicePrintType printType = OptionInvoicePrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionInvoicePrintType.Postponed_Printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionInvoicePrintType.Buffered_Printing;
                
                OptionUICType uicType = OptionUICType.Bulstat;
                switch (customer.UICType) {
                    case BULSTAT : uicType = OptionUICType.Bulstat; break;
                    case EGN : uicType = OptionUICType.EGN; break;
                    case FOREIGN : uicType = OptionUICType.Foreigner_Number; break;
                    case NRA : uicType = OptionUICType.NRA_Official_Number; break;
                }
                fp.OpenInvoiceWithFreeCustomerData(
                    getParamDouble("OperatorCode"),  getParam("OperatorPass")
                    , printType
                    , customer.recipient, customer.buyer, customer.VATNumber
                    , customer.UIC, customer.address, uicType, UNS
                );
            } else {
                OptionFiscalRcpPrintType printType = OptionFiscalRcpPrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionFiscalRcpPrintType.Postponed_printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionFiscalRcpPrintType.Buffered_printing;
                fp.OpenReceipt(
                    getParamDouble("OperatorCode"), getParam("OperatorPass")
                    , getParam("ReceiptFormat").equals("DETAIL")?OptionReceiptFormat.Detailed:OptionReceiptFormat.Brief
                    , getParam("ReceiptVAT").equals("NO")?OptionPrintVAT.No:OptionPrintVAT.Yes
                    , printType
                    , UNS
                );
            }
            try {
                dtAfterOpenFiscalCheck = getDateTime();
            } catch (Exception e) {
            }
        } catch (Exception ex) {
            throw createException(ex);       
        }
    }

    @Override
    public void openFiscalCheckRev(String UNS, fiscalCheckRevType RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, Date RevInvDate) throws FPException {
        lastCommand = "openFiscalCheckRev";
        try {
            if (invoiceMode) {
                OptionInvoiceCreditNotePrintType printType = OptionInvoiceCreditNotePrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionInvoiceCreditNotePrintType.Postponed_Printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionInvoiceCreditNotePrintType.Buffered_Printing;
                fp.OpenCreditNoteWithFreeCustomerData(
                        getParamDouble("OperatorCode"), getParam("OperatorPass")
                        , printType
                        , customer.recipient, customer.buyer, customer.VATNumber, customer.UIC, customer.address, OptionUICType.Bulstat
                        , revTypeToStornoReason(RevType), RevInvNum, RevInvDate, Double.parseDouble(RevDocNum), RevFMNum, RevUNS);
                if (RevReason.length() > 0)
                    printFiscalText(RevReason);
                
            } else {
                OptionStornoRcpPrintType printType = OptionStornoRcpPrintType.Step_by_step_printing;
                if (getParam("Printing").equals("PSP"))
                    printType = OptionStornoRcpPrintType.Postponed_Printing;
                else if(getParam("Printing").equals("BUF"))
                    printType = OptionStornoRcpPrintType.Buffered_Printing;
                fp.OpenStornoReceipt(getParamDouble("OperatorCode"), getParam("OperatorPass")
                    , getParam("ReceiptFormat").equals("DETAIL")?OptionReceiptFormat.Detailed:OptionReceiptFormat.Brief
                    , getParam("ReceiptVAT").equals("NO")?OptionPrintVAT.No:OptionPrintVAT.Yes
                    , printType
                    , revTypeToStornoReason(RevType)
                    , Double.parseDouble(RevDocNum), RevDateTime
                    , RevFMNum, RevUNS
                );
                if (RevReason.length() > 0)
                    printFiscalText(RevReason);
/*
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    //             Do("OpenStornoReceipt", "OperNum", operNum, "OperPass", operPass, "OptionReceiptFormat", optionReceiptFormat, "OptionPrintVAT", optionPrintVAT, "OptionStornoRcpPrintType", optionStornoRcpPrintType, "OptionStornoReason", optionStornoReason, "RelatedToRcpNum", relatedToRcpNum, "RelatedToRcpDateTime", relatedToRcpDateTime, "FMNum", fMNum, "RelatedToURN", relatedToURN);
                fp.Do("OpenStornoReceipt"
                    , "OperNum", getParamDouble("OperatorCode")
                    , "OperPass", getParam("OperatorPass")
                    , "OptionReceiptFormat", getParam("ReceiptFormat").equals("DETAIL")?OptionReceiptFormat.Detailed:OptionReceiptFormat.Brief
                    , "OptionPrintVAT", getParam("ReceiptVAT").equals("NO")?OptionPrintVAT.No:OptionPrintVAT.Yes
                    , "OptionStornoRcpPrintType", printType
                    , "OptionStornoReason", revTypeToStornoReason(RevType)
                    , "RelatedToRcpNum", Double.parseDouble(RevDocNum)
                    , "RelatedToRcpDateTime", fmt.format(RevDateTime)
                    , "FMNum", RevFMNum
                    , "RelatedToURN", RevUNS
                );
*/
            }
        } catch (Exception ex) {
            throw createException(ex);       
        }
    }

    
    @Override
    public void closeFiscalCheck() throws FPException {
        lastCommand = "closeFiscalCheck";
        try {
            fp.CloseReceipt();
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, String discountPerc) throws FPException {
        String[] lines = splitOnLines(text.replace("|", "/")); // | - is used as line separator in next command
        lastCommand = "sell";
        
        try {
            fp.SellPLUwithSpecifiedVAT(lines[0]+((lines.length > 1)?"|"+lines[1]:"")
                , taxGroupToVATClass(taxCode)
                , price
                , quantity
                , discountPerc.contains("%")?parseDouble(discountPerc.replaceAll("%", "")):0d
                , discountPerc.contains("%")?0d:parseDouble(discountPerc)
            );
        } catch (Exception ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, String discountPerc) throws FPException {
        sell(text, taxCode, price, 1d, discountPerc);
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, String discountPerc) throws FPException {
        String[] lines = splitOnLines(text.replace("|", "/")); // | - is used as line separator in next command
        lastCommand = "sell";
        
        try {
            Integer DeptNum = Integer.parseInt(deptCode);
            fp.SellPLUfromDep_(
                lines[0]+((lines.length > 1)?"|"+lines[1]:"")
                , DeptNum
                , price
                , quantity
                , discountPerc.contains("%")?parseDouble(discountPerc.replaceAll("%", "")):0d
                , discountPerc.contains("%")?0d:parseDouble(discountPerc)
            );
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void sellDept(String text, String deptCode, double price) throws FPException {
        sellDept(text, deptCode, price, 1, "0");
    }

    @Override
    public void printFiscalText(String text) throws FPException {
        lastCommand = "printFiscalText";
        String[] lines = splitOnLines(text, CONST_TEXTLENGTH-2);
        
        for (String line : lines) {
            try {
                fp.PrintText(line);
            } catch (Exception ex) {
                throw createException(ex);
            }
        }    
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        lastCommand = "printNonFiscalText";
        String[] lines = splitOnLines(text, CONST_TEXTLENGTH-2);
        
        for (String line : lines) {
            try {
                fp.PrintText(line);
            } catch (Exception ex) {
                throw createException(ex);
            }
        }    
    }
    
    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        StrTable res = new StrTable();
        
        lastCommand = "subTotal";
        try {
            Double amount = fp.Subtotal(toPrint?OptionPrinting.Yes:OptionPrinting.No, toDisplay?OptionDisplay.Yes:OptionDisplay.No, 0d, discountPerc);
            res.put("Amount", String.format(Locale.ROOT, "%.2f", amount));
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        lastCommand = "total";
        try {
            if (abs(payAmount) >= 0.01) 
                fp.Payment(paymenTypeToOptionPaymentType(payType), OptionChange.Without_Change, payAmount, OptionChangeType.Same_As_The_payment);
            else
                fp.PayExactSum(paymenTypeToOptionPaymentType(payType));
        } catch (Exception ex) {
            throw createException(ex);
        }

        StrTable res = new StrTable();
        
        res.putAll(response);
        
        return res;
    }

    @Override
    public CheckInfo getCurrentCheckInfo() throws FPException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        CheckInfo res = new CheckInfo();
        //Current check info.
        lastCommand = "getCurrentCheckInfo";
        try {
            CurrentReceiptInfoRes ri = fp.ReadCurrentReceiptInfo();
            res.TaxA = ri.SubtotalAmountVAT0;
            res.TaxB = ri.SubtotalAmountVAT1;
            res.TaxC = ri.SubtotalAmountVAT2;
            res.TaxD = ri.SubtotalAmountVAT3;
            res.TaxE = ri.SubtotalAmountVAT4;
            res.TaxF = ri.SubtotalAmountVAT5;
            res.TaxG = ri.SubtotalAmountVAT6;
            res.TaxH = ri.SubtotalAmountVAT7;
            // CurrentReceiptNumber for Nonfiscal check is DocNum+1???
            res.DocNum = String.format("%07d", ri.CurrentReceiptNumber.intValue());
//            res.DocNum = String.format("%07d", fp.ReadLastReceiptNum().intValue());
            res.IsOpen = (ri.OptionIsReceiptOpened == OptionIsReceiptOpened.Yes);
            res.SellCount = ri.SalesNumber;
            Double totalAmount = 
                    ri.SubtotalAmountVAT0+ri.SubtotalAmountVAT1+ri.SubtotalAmountVAT2+ri.SubtotalAmountVAT3
                    +ri.SubtotalAmountVAT4+ri.SubtotalAmountVAT5+ri.SubtotalAmountVAT6+ri.SubtotalAmountVAT7;
            res.SellAmount = totalAmount;
            
            res.FCType = fiscalCheckType.UNKNOWN;
            res.IsInvoice = false;
            if (res.IsOpen) {
                switch(ri.OptionTypeReceipt) {
                    case Sales_receipt_Postponed_Printing : // Фискален бон продажба.
                    case Sales_receipt_printing_step_by_step : // Фискален бон продажба.
                        res.FCType = fiscalCheckType.FISCAL;
                        break;
                    case Invoice_sales_receipt_Postponed_Printing : // Фактура.
                    case Invoice_sales_receipt_printing_step_by_step :
                        res.FCType = fiscalCheckType.FISCAL;
                        res.IsInvoice = true;
                        break;
                    case Storno_receipt_Postponed_Printing : // Сторно фискален бон.
                    case Storno_receipt_printing_step_by_step :
                        res.FCType = fiscalCheckType.FISCAL_REV;
                        break;
                    case Invoice_Credit_note_receipt_Postponed_Printing : // Сторно фактура (кр. известие).
                    case Invoice_Credit_note_receipt_printing_step_by_step :
                        res.FCType = fiscalCheckType.FISCAL_REV;
                        res.IsInvoice = true;
                        break;
                }
            }
            Double payAmount = 0d;
            try {
                CurrentOrLastReceiptPaymentAmountsRes pay = fp.ReadCurrentOrLastReceiptPaymentAmounts();
                payAmount = 
                    pay.Payment0Amount+pay.Payment1Amount+pay.Payment2Amount+pay.Payment3Amount
                    +pay.Payment4Amount+pay.Payment5Amount+pay.Payment6Amount+pay.Payment7Amount
                    +pay.Payment8Amount+pay.Payment9Amount+pay.Payment10Amount+pay.Payment11Amount;
            } catch (Exception e) {
                LOGGER.severe("ReadCurrentOrLastReceiptPaymentAmounts:"+e.getMessage());
            }
            res.PayAmount = payAmount;
            if (res.IsInvoice) {
                InvoiceRangeRes ir = fp.ReadInvoiceRange();
                int invNum = ir.StartNum.intValue()-1;
                res.InvNum = String.format(Locale.ROOT, "%010d", invNum);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        lastCurrentCheckInfo = res;
        return res;
    }

    @Override
    public FiscalRecordInfo getLastFiscalRecordInfo() throws FPException {
        FiscalRecordInfo res = new FiscalRecordInfo();
        //Current check info.
        lastCommand = "getLastFiscalRecordInfo";
        try {
            LastDailyReportInfoRes dri = fp.ReadLastDailyReportInfo();
            res.DocNum = Integer.toString(dri.LastZDailyReportNum.intValue());
            res.DocDate = dri.LastZDailyReportDate;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            lastErrorCode = -1;
            lastErrorMsg = ex.getMessage();
            throw createException();       
        }
        
        return res;
    }
    
    
    @Override
    public void printDiagnosticInfo() throws FPException {
        lastCommand = "printDiagnosticInfo";
        try {
            fp.PrintDiagnostics();
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public String getLastPrintDoc() throws FPException {
        lastCommand = "getLastPrintDoc";
        String lastDocNum = "";
        try {
            lastDocNum = String.format("%07d", fp.ReadLastReceiptNum().intValue());
        } catch (Exception ex) {
            throw createException(ex);
        }
        return lastDocNum;
    }

    @Override
    public FiscalCheckQRCodeInfo getLastFiscalCheckQRCode() {
        FiscalCheckQRCodeInfo res = null;
        try {
            String qr;
            qr = fp.ReadLastReceiptQRcodeData();
            res = new FiscalCheckQRCodeInfo(qr);
        } catch (Exception ex) {
            // Alternate method to get QRCode
            if (!isOldDevice)
                LOGGER.severe(ex.getMessage());
            LOGGER.warning("Using alternate method to build QRCode.");
            // Alternate method to create QRCode
            CheckInfo ci = lastCurrentCheckInfo;
            if (ci == null) {
                try {
                    ci = getCurrentCheckInfo();
                } catch (FPException exx) {
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }    
            if (ci != null) {
                Date lastDocDate = null;
//                try {
//                    lastDocDate = FP.cmdLastFiscalCheckDate();
//                } catch (IOException exx) {
//                }
                res = new FiscalCheckQRCodeInfo(devInfo.FMNum, ci.DocNum, (lastDocDate != null)?lastDocDate:dtAfterOpenFiscalCheck, ci.SellAmount);
            }    
        }
        if (res == null)
            res = new FiscalCheckQRCodeInfo();
        return res;
    }
    
    @Override
    public void openNonFiscalCheck() throws FPException {
        lastCommand = "openNonFiscalChek";
        try {
            OptionNonFiscalPrintType printType = OptionNonFiscalPrintType.Step_by_step_printing;
            if (getParam("Printing").equals("PSP"))
                printType = OptionNonFiscalPrintType.Postponed_Printing;
//            else if(getParam("Printing").equals("BUF"))
//                printType = OptionNonFiscalPrintType.Buffered_printing;
            fp.OpenNonFiscalReceipt(getParamDouble("OperatorCode"), getParam("OperatorPass"), printType);
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        lastCommand = "closeNonFiscalCheck";
        try {
            fp.CloseNonFiscalReceipt();
        } catch (Exception ex) {
            throw createException(ex);
        }
    }
    
    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        lastCommand = "reportDaily";
        
        try {
            switch(reportType) {
                case ZD : 
                    fp.PrintDepartmentReport(OptionZeroing.Zeroing); 
                    break;
                case X : 
                    fp.PrintDailyReport(OptionZeroing.Without_zeroing); 
                    break;
                case XD : 
                    fp.PrintDepartmentReport(OptionZeroing.Without_zeroing); 
                    break;
                case Z : 
                default :
                    fp.PrintDailyReport(OptionZeroing.Zeroing); 
                    break;
            }
        } catch (Exception ex) {
            throw createException(ex);
        }
        
        StrTable res = new StrTable();
        // TODO: get Report Sums
        return res;
    }
    
    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "reportByDates";
        
        try {
            if (reportType == datesReportType.DETAIL) {
                fp.PrintDetailedFMPaymentsReportByDate(from, to);
            } else {
                fp.PrintBriefFMPaymentsReportByDate(from, to);
            }    
        } catch (Exception ex) {
            throw createException(ex);
        }
        
        return res;
    }

    @Override
    public Date getDateTime() throws FPException {
        lastCommand = "getDateTime";
        
        Date dateTime = null;
        try {
            dateTime = fp.ReadDateTime();
        } catch (Exception ex) {
            throw createException(ex);
        }
        
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        lastCommand = "setDateTime";

        try {
            fp.SetDateTime(dateTime);
        } catch (Exception ex) {
            throw createException(ex);
        }
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        lastCommand = "paperFeed";
        
        try {
            for (int i = 0; i < lineCount; i++)
                fp.PaperFeed();
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        lastCommand = "printLastCheckDuplicate";
        
        try {
            fp.PrintLastReceiptDuplicate();
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public void printCheckDuplicateByDocNum(String docNum)  throws FPException  {
        lastCommand = "printCheckDuplicateByDocNum";
        
        try {
            fp.PrintEJByRcpNumCustom((byte)0x7F, (byte)0x5F, Double.parseDouble(docNum), Double.parseDouble(docNum));
        } catch (Exception ex) {
            throw createException(ex);
        }
    }

    @Override
    public StrTable cashInOut(Double ioSum) throws FPException {
        lastCommand = "cashInOut";
        StrTable res = new StrTable();
        try {
/*            
            Class cl = DailyAvailableAmountsRes.class;
            Object inst = cl.newInstance();
            Field f = cl.getDeclaredField("AmountPayment0");
            String strval = "11";
            double dval = Double.parseDouble(strval);
//            f.setDouble(inst, dval);
            f.set(inst, Double.valueOf(dval));
*/            
            if (abs(ioSum) >= 0.01)
                fp.ReceivedOnAccount_PaidOut(getParamDouble("OperatorCode"), getParam("OperatorPass"), OptionPayType.Cash, ioSum, OptionPrintAvailability.Yes, "");
//                fp.ReceivedOnAccount_PaidOut(getParamDouble("OperatorCode"), getParam("OperatorPass"), ioSum, OptionPrintAvailability.Yes, "");
                
            Locale.setDefault(Locale.ROOT);
            Double CashSum = 0d;
            if (isOldDevice) {
                DailyAvailableAmounts_OldRes da = fp.ReadDailyAvailableAmounts_Old();
                // number of payment for cache
                int pn = paymenTypeToPaymentNum(paymentTypes.CASH);
                switch (pn) {
                    case 0 : CashSum = da.AmountPayment0; break;
                    case 1 : CashSum = da.AmountPayment1; break;
                    case 2 : CashSum = da.AmountPayment2; break;
                    case 3 : CashSum = da.AmountPayment3; break;
                    case 4 : CashSum = da.AmountPayment4; break;
                }
            } else {
                DailyAvailableAmountsRes da = fp.ReadDailyAvailableAmounts();
                // number of payment for cache
                int pn = paymenTypeToPaymentNum(paymentTypes.CASH);
                switch (pn) {
                    case 0 : CashSum = da.AmountPayment0; break;
                    case 1 : CashSum = da.AmountPayment1; break;
                    case 2 : CashSum = da.AmountPayment2; break;
                    case 3 : CashSum = da.AmountPayment3; break;
                    case 4 : CashSum = da.AmountPayment4; break;
                    case 5 : CashSum = da.AmountPayment5; break;
                    case 6 : CashSum = da.AmountPayment6; break;
                    case 7 : CashSum = da.AmountPayment7; break;
                    case 8 : CashSum = da.AmountPayment8; break;
                    case 9 : CashSum = da.AmountPayment9; break;
                    case 10 : CashSum = da.AmountPayment10; break;
                    case 11 : CashSum = da.AmountPayment11; break;
                }
            }    
            res.put("CashSum", formatCurrency(CashSum));
            res.put("CashIn", "0");
            res.put("CashOut", "0");
//            LinkedHashMap<String,String> res_ = FP.cmdCashInOut(ioSum);
//            res.putAll(res_);
            // TODO: Read CashSum/CashIn/CashOut
        } catch (Exception ex) {
            ex.printStackTrace();
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournalInfo() throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournalInfo";
        try {
//            fp.ReadEJ(OptionReportFormat.Brief_EJ);
//            res.putAll(res_);
            // TODO: Get Journal Information
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournal(Date fromDate, Date toDate) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournal";
        try {
            // TODO: Change with this after fix Date serialization
            // fp.ReadEJByDate(OptionReportFormat.Detailed_EJ, fromDate, toDate);
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            fp.Do("ReadEJByDate", "OptionReportFormat", OptionReportFormat.Detailed_EJ, "StartRepFromDate", fmt.format(fromDate), "EndRepFromDate", fmt.format(toDate));

            /**
             * After that, call RawRead(0,"@") 
             * WHERE first parameter with value - 0 - how many bytes to read, zero (0) for all available bytes 
             * AND second parameter with value - @ - to read until marker "@" for end. 
             * OR call RawRead(200,"") for first 200 bytes to read
             */
            byte[] resBytes = fp.RawRead(0.0, "@");
            Charset charset = Charset.forName("Windows-1251");
            String resStr = new String(resBytes, charset);
            Pattern p = Pattern.compile("^\u0002.+?[|]", Pattern.MULTILINE);
            Matcher m = p.matcher(resStr);
            resStr = m.replaceAll("");
            // Replace suffix
            p = Pattern.compile("..$", Pattern.MULTILINE);
            m = p.matcher(resStr);
            resStr = m.replaceAll("");
            res.put("EJ", resStr);
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getJournal(String fromDoc, String toDoc) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getJournal";
        try {
            fp.ReadEJByReceiptNum(OptionReportFormat.Detailed_EJ, Double.parseDouble(fromDoc), Double.parseDouble(toDoc));
            /**
             * After that, call RawRead(0,"@") 
             * WHERE first parameter with value - 0 - how many bytes to read, zero (0) for all available bytes 
             * AND second parameter with value - @ - to read until marker "@" for end. 
             * OR call RawRead(200,"") for first 200 bytes to read
             */
            byte[] resBytes = fp.RawRead(0.0, "@");
            Charset charset = Charset.forName("Windows-1251");
            String resStr = new String(resBytes, charset);
            // Replace prefix
//            resStr = resStr.replaceAll("\u0002.+?[|]", "");
            Pattern p = Pattern.compile("^\u0002.+?[|]", Pattern.MULTILINE);
            Matcher m = p.matcher(resStr);
            resStr = m.replaceAll("");
            // Replace suffix
            p = Pattern.compile("..$", Pattern.MULTILINE);
            m = p.matcher(resStr);
            resStr = m.replaceAll("");
            res.put("EJ", resStr);
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable getDocInfo(String docNum) throws FPException {
        StrTable res = new StrTable();
        lastCommand = "getDocInfo";
        try {
//            LinkedHashMap<String,String> res_ = FP.cmdReadDocInfo(docNum);
//            res.putAll(res_);
            //TODO: To implement if possible
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable readPaymentMethods() throws FPException{
        StrTable res = new StrTable();
        lastCommand = "readPaymentMethods";
        try {
            if (isOldDevice) {
                Payments_OldRes payRes = fp.ReadPayments_Old();
                res.put("P_0", "'0' '"+payRes.getNamePaym0()+"' НАП #:0");
                res.put("P_1", "'1' '"+payRes.getNamePaym1()+"' НАП #:"+payRes.getCodePaym1());
                res.put("P_2", "'2' '"+payRes.getNamePaym2()+"' НАП #:"+payRes.getCodePaym2());
                res.put("P_3", "'3' '"+payRes.getNamePaym3()+"' НАП #:"+payRes.getCodePaym3());
                res.put("P_4", "'4' '"+payRes.getNamePaym4()+"' НАП #:0");
            } else {
                PaymentsRes payRes = fp.ReadPayments();
                res.put("P_0", "'0' '"+payRes.getNamePayment0()+"' НАП #:0");
                res.put("P_1", "'1' '"+payRes.getNamePayment1()+"' НАП #:1");
                res.put("P_2", "'2' '"+payRes.getNamePayment2()+"' НАП #:2");
                res.put("P_3", "'3' '"+payRes.getNamePayment3()+"' НАП #:3");
                res.put("P_4", "'4' '"+payRes.getNamePayment4()+"' НАП #:4");
                res.put("P_5", "'5' '"+payRes.getNamePayment5()+"' НАП #:5");
                res.put("P_6", "'6' '"+payRes.getNamePayment6()+"' НАП #:6");
                res.put("P_7", "'7' '"+payRes.getNamePayment7()+"' НАП #:7");
                res.put("P_8", "'8' '"+payRes.getNamePayment8()+"' НАП #:8");
                res.put("P_9", "'9' '"+payRes.getNamePayment9()+"' НАП #:9");
                res.put("P_10", "'10' '"+payRes.getNamePayment10()+"' НАП #:10");
                res.put("P_11", "'11' '"+payRes.getNamePayment11()+"' НАП #:11");
            }
        } catch (Exception ex) {
            throw createException(ex);
        }
        return res;
    }

    @Override
    public StrTable readDepartments() throws FPException{
        StrTable res = new StrTable();
        lastCommand = "readDepartments";
        for (int i = 0; i < CONST_DEPTNUM;  i++ ) {
            DepartmentRes dept;
            try {
                dept = fp.ReadDepartment((double)i);
                res.put(
                    "D_"+Integer.toString(i)
                   , 
                     Integer.toString(i)
                     +" Група:"+dept.getOptionVATClass().toString()
                     +" Име:"+dept.getDepName()
                     +" Продажби:"+Double.toString(dept.SoldQuantity)+"/"+Double.toString(dept.Turnover)
                );
            } catch (Exception ex) {
                LOGGER.warning(ex.getMessage());
            }
        }
        return res;
    }

    @Override
    public StrTable readTaxGroups() throws FPException {
        StrTable res = new StrTable();
        lastCommand = "readTaxGroups";
        try {
            VATratesRes vr = fp.ReadVATrates();
            res.put("TaxA", Double.toString(vr.VATrate0));
            res.put("TaxB", Double.toString(vr.VATrate1));
            res.put("TaxC", Double.toString(vr.VATrate2));
            res.put("TaxD", Double.toString(vr.VATrate3));
            res.put("TaxE", Double.toString(vr.VATrate4));
            res.put("TaxF", Double.toString(vr.VATrate5));
            res.put("TaxG", Double.toString(vr.VATrate6));
            res.put("TaxH", Double.toString(vr.VATrate7));
        } catch (Exception ex) {
            LOGGER.warning(ex.getMessage());
        }
        return res;
    }

    
}
