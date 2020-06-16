/*
 * Copyright (C) 2020 EDA Ltd.
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
package org.eda.protocol;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.eda.protocol.AbstractFiscalDevice.LOGGER;

/**
 * This class supports Tremol fiscal devices 
 * Corresponding to specification protocol_description_BG_1910211454.pdf
 * @author Dimitar Angelov
 */
public class DeviceTremolV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;

    protected int CONST_TEXTLENGTH=32;
    protected int CONST_DEPTNUM=32;
    protected int CONST_PAYNUM=10;
    protected boolean isOldDevice = false; // Old device upgraded to new regulations
    
    private boolean fiscalCheckRevOpened = false;
    
    public static enum ReceiptPrintFormatType {
        BRIEF, DETAILED
    }

    public static enum ReceiptPrintModeType {
        STEP_BY_STEP, POSTPHONED, BUFFERED
    }

    public static enum StornoReasonType {
        ERROR, RETURN, REDUCE
    }
    
    /**
     * Receipt Print Format
     */
    protected ReceiptPrintFormatType receiptPrintFormat;

    /**
     * Receipt Print Mode
     */
    protected ReceiptPrintModeType receiptPrintMode;

    /**
     * Receipt Print VAT
     */
    protected boolean receiptPrintVAT;

    
    public static enum UICTypeType {
        BULSTAT, EGN, FOREIGN, NRA
    }
    
    public static class CustomerDataType {
        public String recipient = "";
        public String buyer = "";
        public String address = "";
        public String VATNumber = "";
        public String UIC = "";
        public UICTypeType UICType = UICTypeType.BULSTAT;
        public String seller = "";
    }
    protected CustomerDataType customerData = new CustomerDataType();

    protected String opCode = "";
    protected String opPasswd = "";
    
    public DeviceTremolV1(AbstractProtocol p) {
        super(p);
        this.receiptPrintVAT = false;
        this.receiptPrintMode = ReceiptPrintModeType.STEP_BY_STEP;
        this.receiptPrintFormat = ReceiptPrintFormatType.BRIEF;
    }

    public DeviceTremolV1(String portName, int baudRate, int readTimeout, int writeTimeout) {
        super(portName, baudRate, readTimeout, writeTimeout);
        this.receiptPrintVAT = false;
        this.receiptPrintMode = ReceiptPrintModeType.STEP_BY_STEP;
        this.receiptPrintFormat = ReceiptPrintFormatType.BRIEF;
        serialPort = SerialPort.getCommPort(portName);
        if (serialPort.openPort()) {
            serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            // Always have to initialize Serial Port in nonblocking (forever) mode!
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, readTimeout, writeTimeout);
            serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            protocol = new ProtocolTremolV10(serialPort.getInputStream(), serialPort.getOutputStream(), ProtocolTremolV10.EncodingType.CP_1251);
        }
        // TODO: if protocol is null throw exception
    }

    public ReceiptPrintFormatType getReceiptPrintFormat() {
        return receiptPrintFormat;
    }

    public void setReceiptPrintFormat(ReceiptPrintFormatType receiptPrintFormat) {
        this.receiptPrintFormat = receiptPrintFormat;
    }

    public ReceiptPrintModeType getReceiptPrintMode() {
        return receiptPrintMode;
    }

    public void setReceiptPrintMode(ReceiptPrintModeType receiptPrintMode) {
        this.receiptPrintMode = receiptPrintMode;
    }

    public boolean isReceiptPrintVAT() {
        return receiptPrintVAT;
    }

    public void setReceiptPrintVAT(boolean receiptPrintVAT) {
        this.receiptPrintVAT = receiptPrintVAT;
    }
    
    public CustomerDataType getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerDataType customerData) {
        this.customerData = customerData;
    }

    @Override
    public int getLineWidthNonFiscalText() {
        return CONST_TEXTLENGTH-2;
    }

    @Override
    public int getLineWidthFiscalText() {
        return CONST_TEXTLENGTH-2;
    }

    
    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpPasswd() {
        return opPasswd;
    }

    public void setOpPasswd(String opPasswd) {
        this.opPasswd = opPasswd;
    }

    
    @Override
    public void close() throws IOException {
        super.close(); 
        if ((serialPort != null) && serialPort.isOpen())
            serialPort.closePort();
        serialPort = null;
    }
/*
    @Override
    public int getLineWidthNonFiscalText() {
        return 46;
    }

    @Override
    public int getLineWidthFiscalText() {
        return 46;
    }
*/
    
    @Override
    protected void readDeviceInfo() throws IOException {
        /*
        2.2.2. Command: 21h / ! – Version
        input: n. a.
        output: <DeviceType[1..2]> <;> <CertificateNum[6]> <;> <CertificateDateTime “DD-MM-YYYY HH:MM”> <;> <Model[50]> <;> <Version[20]>
        FPR operation: Provides information about the device type, Certificate number, Certificate date and time and Device model.
        Input data : n. a.
        Output data :
        DeviceType
        1 or 2 symbols for type of fiscal device:
        - '1' – ECR
        - '11' – ECR for online store
        - '2' – FPr
        - '21' – FPr for online store
        - '3' – Fuel
        - '31' – Fuel system
        - '5' – for FUVAS device
        CertificateNum
        6 symbols for Certification Number of device model
        CertificateDateTime
        16 symbols for Certificate Date and time parameter in format: DD-MM-YYYY HH:MM
        Model
        Up to 50 symbols for Model name
        Version
        Up to 20 symbols for Version name and Check sum
        zfpdef: ReadVersion()
        
        2.5.1. Command: 60h / ' – Read FD numbers
        input: n. a.
        output: <SerialNumber[8]> <;> <FMNumber[8]>
        FPR operation: Provides information about the manufacturing number of the fiscal device and FM number.
        Input data : n. a.
        Output data :
        SerialNumber
        8 symbols for individual number of the fiscal device
        FMNumber
        8 symbols for individual number of the fiscal memory
        zfpdef: ReadSerialAndFiscalNums()
        
        2.2.1. Command: 20h / SP - Status
        input: n. a.
        output: <StatusBytes[7]>
        FPR operation: Provides detailed 7-byte information about the current status of the fiscal printer.
        Input data : n. a.
        Output data :
        N byte  N bit   status flag
        ST0     0       FM Read only
                1       Power down in opened fiscal receipt
                2       Printer not ready - overheat        
                3       DateTime not set
                4       DateTime wrong
                5       RAM reset
                6       Hardware clock error
                7       1
        ST1     0       Printer not ready - no paper
                1       Reports registers Overflow
                2       Customer report is not zeroed
                3       Daily report is not zeroed
                4       Article report is not zeroed
                5       Operator report is not zeroed
                6       Duplicate printed
                7       1
        ST2     0       Opened Non-fiscal Receipt   
                1       Opened Fiscal Receipt
                2       Opened Fiscal Detailed Receipt
                3       Opened Fiscal Receipt with VAT
                4       Opened Invoice Fiscal Receipt
                5       SD card near full
                6       SD card full
                7       1
        ST3     0       No FM module
                1       FM error
                2       FM full
                3       FM near full
                4       Decimal point (1=fract, 0=whole)
                5       FM fiscalized
                6       FM produced
                7       1
        ST4     0       Printer: automatic cutting
                1       External display: transparent display
                2       Speed is 9600
                3       reserve
                4       Drawer: automatic opening
                5       Customer logo included in the receipt
                6       reserve
                7       1
        ST5     0       Wrong SIM card
                1       Blocking 3 days without mobile operator
                2       No task from NRA
                3       reserved
                4       reserved
                5       Wrong SD card
                6       Deregistered
                7       1
        ST6     0       No SIM card
                1       No GPRS Modem
                2       No mobile operator
                3       No GPRS service
                4       Near end of paper
                5       Unsent data for 24 hours
                6       reserved
                7       1
        zfpdef:ReadStatus()        
        
        */
        // Get Status Bytes
        getStatusBytes();
        // ReadVersion
        String res = cmdCustom(0x21, "");
        //<DeviceType[1..2]> <;> <CertificateNum[6]> <;> <CertificateDateTime “DD-MM-YYYY HH:MM”> <;> <Model[50]> <;> <Version[20]>
        // 1;823;19-04-2019 08:00;TREMOL S25; Ver.1.01 TRA25 C.S.D26C;
        mDeviceInfo = res;
        // TODO: Place here real response
        String[] commaParts = res.split(";");
        if (commaParts.length > 3) 
            mModelName = commaParts[3];
        
        if (commaParts.length > 4) {
            mFWRev = commaParts[4];
        }
        try {
            isOldDevice = mModelName.matches("^.+V2$");
            LOGGER.info(isOldDevice?"Old Device V2 detected":"New device model detected");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        mFWRevDT = "";
        mSwitches = "";
        //ReadSerialAndFiscalNums
        res = cmdCustom(0x60, "");
        // <SerialNumber[8]> <;> <FMNumber[8]>
        commaParts = res.split(";");
        mSerialNum = commaParts[0];
        if (commaParts.length > 1) 
            mFMNum = commaParts[1];
        //Read Maximum text length in recipes
        res = cmdAuxilary(new byte[] {(byte)0x1D, (byte)0x49}); // cmdCustom(0x1D, "I");
        CONST_TEXTLENGTH = 0;
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
        LOGGER.fine(res);
        if (res.startsWith("I")) {
            res = res.substring(1);
            String[] parts = res.split(";");
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
//        res = cmdCustom(0x20, "");
//        // TODO: Read status bytes
    }

    @Override
    protected void initPrinterStatusMap() {
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();

        statusBytesDef.put("S0", new String[] // 
        {
             "ФП е в режим само за четене"       // 0 FM Read only
            ,"Изкл. захравнане при отворен ФБ"   // 1 Power down in opened fiscal receipt
            ,"Принтерът не е готов - Прегряване" // 2 Printer not ready - overheat
            ,"Неустановени дата и час"           // 3 DateTime not set
            ,"Грешни дата и час"                 // 4 DateTime wrong
            ,"Нулиране на RAM"                   // 5 RAM reset
            ,"Грешка в часовника"                // 6 Hardware clock error
            ,""                                  // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S0", new byte[] {
            1,3,4,5
        });
        errorStatusBits.put("S0", new byte[] {
            2,6
        });
        statusBytesDef.put("S1", new String[] // 
        {
             "Принтерът не е готов - липсва хартия"       // 0 Printer not ready - no paper
            ,"Препълване на регистрите за отчети"         // 1 Reports registers Overflow
            ,"Клиенският отчет не е нулиран"              // 2 Customer report is not zeroed
            ,"Дневният отчет не е нулиран"                // 3 Daily report is not zeroed
            ,"Отчетът по артикули не е нулиран"           // 4 Article report is not zeroed
            ,"Отчетът по оператори не е нулиран"          // 5 Operator report is not zeroed
            ,"Отпечатън е дубликат"                       // 6 Duplicate printed
            ,""                                           // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S1", new byte[] {
            //2,3,4,5,6
        });
        errorStatusBits.put("S1", new byte[] {
            0,1
        });

        statusBytesDef.put("S2", new String[] // 
        {
             "Отворен е нефискален бон"                                    // 0 Opened Non-fiscal Receipt
            ,"Отворен е фискален бон"                                      // 1 Opened Fiscal Receipt
            ,"Отворен е детайлен фискален бон"                             // 2 Opened Fiscal Detailed Receipt
            ,"Отворен Фискален бон с ДДС"                                  // 3 Opened Fiscal Receipt with VAT
            ,"Отворена е фискална фактура"                                 // 4 Opened Invoice Fiscal Receipt
            ,"SD картата е почти пълна"                                    // 5 SD card near full
            ,"SD картата е пълна"                                          // 6 SD card full
            ,""                                                            // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S2", new byte[] {
            5
        });
        errorStatusBits.put("S2", new byte[] {
            6
        });
        statusBytesDef.put("S3", new String[] //
        {
             "Липсва модул с фискална памет"    // 0 No FM module
            ,"Грешка във фискалната памет"      // 1 FM error
            ,"Фискалната памет е пълна"         // 2 FM full
            ,"Фискалната памет е почти пълна"   // 3 FM near full
            ,"Десетична точка (дроб)"           // 4 Decimal point (1=fract, 0=whole)
            ,"Фискалната памет е фискализирана" // 5 FM fiscalized
            ,"Фискалната памет е подготвена"    // 6 FM produced
            ,""                                 // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S3", new byte[] {
            3
        });
        errorStatusBits.put("S3", new byte[] {
            0,1,2
        });
        statusBytesDef.put("S4", new String[] // За ФП
        {
             "Автоматично отрязване на хартията"                   // 0 Printer: automatic cutting
            ,"Външен дисплей: прозрачен"                           // 1 External display: transparent display
            ,"Скоростта е 9600"                                    // 2 Speed is 9600
            ,""                                                    // 3 reserve
            ,"Автоматично отваряне на касата"                      // 4 Drawer: automatic opening
            ,"Отпечатване на клиентско лого във ФБ"                // 5 Customer logo included in the receipt
            ,""                                                    // 6 reserve
            ,""                                                    // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S4", new byte[] {
        });
        errorStatusBits.put("S4", new byte[] {
        });
        
        statusBytesDef.put("S5", new String[] // 
        {
             "Грешна SIM карта"                        // 0 Wrong SIM card
            ,"Блокиране 3 дена без мобилен оператор"   // 1 Blocking 3 days without mobile operator
            ,"Няма задача от НАП"                      // 2 No task from NRA
            ,""                                        // 3 reserved
            ,""                                        // 4 reserved
            ,"Грешна SD карта"                         // 5 Wrong SD card
            ,"Дерегистриран"                           // 6 Deregistered
            ,""                                        // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S5", new byte[] {
            
        });
        errorStatusBits.put("S5", new byte[] {
            0,1,5
        });

        statusBytesDef.put("S6", new String[] // 
        {
             "Липсва SIM карта"                            // 0 No SIM card
            ,"Липсва GPRS модем"                           // 1 No GPRS Modem
            ,"Липсва мобилен оператор"                     // 2 No mobile operator
            ,"Липсва GPRS услуга"                          // 3 No GPRS service
            ,"Близък край на хартията"                     // 4 Near end of paper
            ,"Има неизпратени данни за последните 24 часа" // 5 Unsent data for 24 hours
            ,""                                            // 6 reserved
            ,""                                           // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S6", new byte[] {
            4,5
        });
        errorStatusBits.put("S6", new byte[] {
            0,1,2,3
        });
    }

    // Device is Old upgraded Model 'KL' suffix in Model name
    public boolean isIsOldDevice() {
        return isOldDevice;
    }
    
    
    @Override
    public boolean isOpenNonFiscalCheck() {
      int byteNum = 2;
      byte b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((byte)(b & 0x01) == (byte)0x01);
    }

    @Override
    public boolean isOpenFiscalCheck() {
      int byteNum = 2;
      byte b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((byte)(b & (byte)0x1E) != (byte)0x00);
    }

    @Override
    public boolean isOpenFiscalCheckRev() {
        return isOpenFiscalCheck() && fiscalCheckRevOpened;
    }

    @Override
    public boolean isFiscalized() {
      int byteNum = 3;
      byte b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((byte)(b & (byte)0x20) != (byte)0x00);
    }

    @Override
    public String cmdCustom(int command, String data) throws IOException {
        mLastCmd = command;
        String result = protocol.customCommand(command, data);
//        mStatusBytes = protocol.getStatusBytes();
//        checkErrors(mClearErrors);
        return result;
    }

    @Override
    public byte[] getStatusBytes() {
        mStatusBytes = protocol.getStatusBytes();
        return mStatusBytes;
    }

    @Override
    public LinkedHashMap<String, String> getStatusBytesAsText() {
        mStatusBytes = protocol.getStatusBytes();
        return super.getStatusBytesAsText(); 
    }
    
    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String opCode, String opPasswd, String wpNum, String UNS, boolean invoice) throws IOException {
        /*
        2.6.3. Command: 30h / 0 –Open Fiscal sales receipt
        input:<OperNum[1..2]> <;> <OperPass[6]> <;> <ReceiptFormat[1]> <;> <PrintVAT[1]> <;> <FiscalRcpPrintType[1]> {<’$’> <UniqueReceiptNumber[24]>}
        output: ACK
        FPR operation: Opens a fiscal receipt assigned to the specified operator number and operator password, parameters for receipt format, print VAT, printing type and unique receipt number.
        Input data :
        OperNum
        (Operator Number) Symbols from 1 to 20 corresponding to operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        ReceiptFormat
        (Format) 1 symbol with value:
        - '1' - Detailed
        - '0' - Brief
        PrintVAT
        (VAT included in the receipt) 1 symbol with value:
        - '1' – Yes
        - '0' – No
        FiscalRcpPrintType
        (Printing type) 1 symbol with value:
        - '0' – Step by step printing
        - '2' – Postponed printing
        - '4' – Buffered printing
        UniqueReceiptNumber
        Up to 24 symbols for unique receipt number.
        NRA format: XXXХХХХХ-ZZZZ-YYYYYYY where:
        * ХХХХХХXX – 8 symbols [A-Z, a-z, 0-9] for individual device number,
        * ZZZZ – 4 symbols [A-Z, a-z, 0-9] for code of the operator,
        * YYYYYYY – 7 symbols [0-9] for next number of the receipt
        Output data: n. a.
        zfpdef: OpenReceipt(OperNum, OperPass, RcpFormat, PrintVAT, FiscalRcpPrintType, UniqueReceiptNumber)
        
        2.6.6. Command: 30h / 0 – Open Fiscal Invoice receipt with free customer data
        input: <OperNum[1..2]> <;> <OperPass[6]> <;> <reserved['0']> <;> <reserved['0']> <;> <InvoicePrintType[1]> <;> <Recipient[26]> <;> <Buyer[16]> <;> <VATNumber[13]> <;> <UIC[13]> <;> <Address[30]> <;> <UICType[1]> { <’$’> <UniqueReceiptNumber[24]>}
        output: ACK
        FPR operation: Opens a fiscal invoice receipt assigned to the specified operator number and operator password with free info for customer data. The Invoice receipt can be issued only if the invoice range (start and end numbers) is set.
        Input data :
        OperNum
        (Operator Number) Symbol from 1 to 20 corresponding to operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        reserved
        1 symbol with value '0'
        reserved
        1 symbol with value '0'
        InvoicePrintType
        (Printing type) 1 symbol with value:
        - '1' – Step by step printing
        - '3' – Postponed Printing
        - '5' – Buffered Printing
        Recipient
        26 symbols for Invoice recipient
        Buyer
        16 symbols for Invoice buyer
        VATNumber
        13 symbols for customer Fiscal number
        UIC
        13 symbols for customer Unique Identification Code
        Address
        30 symbols for Address
        UICType
        1 symbol for type of Unique Identification Code:
        - '0' - Bulstat
        - '1' - EGN
        - '2' – Foreigner Number
        - '3' – NRA Official Number
        UniqueReceiptNumber
        Up to 24 symbols for unique receipt number.
        NRA format: XXXХХХХХ-ZZZZ-YYYYYYY where:
        * ХХХХХХXX – 8 symbols [A-Z, a-z, 0-9] for individual device number,
        * ZZZZ – 4 symbols [A-Z, a-z, 0-9] for code of the operator,
        * YYYYYYY – 7 symbols [0-9] for next number of the receipt
        Output data: n. a.
        Note:
        If Postponed printing is enabled after opened fiscal receipt, all the next commands will be executed but won't be printed immediately. The data for whole receipt is stored to be printed up to AS sent information for receipt closure. If up to 5 sec timeout no command ECR closing the receipt
        zfpdef: OpenInvoiceWithFreeCustomerData(OperNum, OperPass, InvoicePrintType, Recipient, Buyer, UIC, IDNumber, Address, UICType, UniqueReceiptNumber)        
        */
        // TODO: Check parameters
        //<OperNum[1..2]> <;> <OperPass[6]> <;> <ReceiptFormat[1]> <;> <PrintVAT[1]> <;> <FiscalRcpPrintType[1]> {<’$’> <UniqueReceiptNumber[24]>}
        if (!opCode.matches("^\\d\\d?$"))
            throw new FDException("Невалиден код на оператор!");
        if (opPasswd.length() > 6)
            throw new FDException("Максималната дължина на паролата е 6 символа!");
        if (opPasswd.length() < 6)
            opPasswd = String.format("%-6s", opPasswd);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String printMode = "";
        switch (receiptPrintMode) {
            case BUFFERED :
                printMode = invoice?"5":"4";
                break;
            case POSTPHONED :
                printMode = invoice?"3":"2";
            case STEP_BY_STEP :
            default :
                printMode = invoice?"1":"0";
        }
        String params;
        if (invoice) {
            String recipient = customerData.recipient;
            if (recipient.length() > 26) {
                warn("Дължината на 'получател' е по-голяма от 26 сивола и ще бъде съкратена!");
                recipient = recipient.substring(0, 26);
            }
            recipient = String.format("%-26s", recipient);
//            recipient = recipient.replaceAll(";", ",");
            String buyer = customerData.buyer;
            if (buyer.length() > 16) {
                warn("Дължината на 'купувач' е по-голяма от 16 сивола и ще бъде съкратена!");
                buyer = buyer.substring(0, 16);
            }    
            buyer = String.format("%-16s", buyer);
//            buyer = buyer.replaceAll(";", ",");
            String address = customerData.address;
            if (address.length() > 30) {
                warn("Дължината на 'адрес' е по-голяма от 30 сивола и ще бъде съкратена!");
                address = address.substring(0, 30);
            }    
            address = String.format("%-30s", address);
//            address = address.replaceAll(";", ",");
            String VATNumber = customerData.VATNumber;
            if (VATNumber.length() > 13) {
                warn("Дължината на 'Данъчен номер' е по-голяма от 13 сивола и ще бъде съкратена!");
                VATNumber = VATNumber.substring(0, 13);
            }    
            VATNumber = String.format("%-13s", VATNumber);
            String UIC = customerData.UIC;
            if (UIC.length() > 13) {
                warn("Дължината на 'Булстат' е по-голяма от 13 сивола и ще бъде съкратена!");
                UIC = UIC.substring(0, 13);
            }    
            UIC = String.format("%-13s", UIC);
            
            String UICType = "";
            switch (customerData.UICType) {
                case EGN : UICType = "1"; break;
                case FOREIGN : UICType = "2"; break;
                case NRA : UICType = "3"; break;
                case BULSTAT : 
                default :    
                    UICType = "0"; break;
            }
            params = opCode+";"+opPasswd;       // <OperNum[1..2]> <;> <OperPass[6]>
            params = params + ";0;0";           // <;> <reserved['0']> <;> <reserved['0']>
            params = params + ";" + printMode;  // <;> <InvoicePrintType[1]>
            params = params + ";" + recipient;  // <;> <Recipient[26]>
            params = params + ";" + buyer;      // <;> <Buyer[16]>
            params = params + ";" + VATNumber;  // <;> <VATNumber[13]>
            params = params + ";" + UIC;        // <;> <UIC[13]>
            params = params + ";" + address;    // <;> <Address[30]>
            params = params + ";" + UICType;    // <;> <UICType[1]>
            if (UNS.length() > 0)
                params = params + "$" + UNS;    // {<’$’> <UniqueReceiptNumber[24]>}
        } else {
            params = opCode+";"+opPasswd;   // <OperNum[1..2]> <;> <OperPass[6]>
            params = params + ";" + (receiptPrintFormat.equals(ReceiptPrintFormatType.DETAILED)?"1":"0"); // <;> <ReceiptFormat[1]>
            params = params + ";" + (receiptPrintVAT?"1":"0");  // <;> <PrintVAT[1]>
            params = params + ";" + printMode;                  // <;> <FiscalRcpPrintType[1]>
            if (UNS.length() > 0)
                params = params + "$" + UNS;                    // {<’$’> <UniqueReceiptNumber[24]>}
        }
        
        fiscalCheckRevOpened = false;

        String res = cmdCustom(0x30, params);
        // Няма отговор. Връща ACK и състоянието е в статус байтовете, които се проверяват в cmdCustom и се инициира грешка (exception)
        
        return response;
    }

    @Override
    public void cmdPrintCustomerData(String UIC, int UICType, String seller, String recipient, String buyer, String VATNum, String address)  throws IOException {
        // Не е необходимо, тъй като се задават данните при отваряне на фискален бон
    }

    
    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws IOException {
        /*
        2.6.23. Command: 38h / 8 – Close Fiscal receipt
        input: n. a.
        output: ACK
        FPR operation: Close the fiscal receipt (Fiscal receipt, Invoice receipt, Storno receipt, Credit Note or Non-fical receipt). When the payment is finished.
        Input data : n. a.
        Output data : n. a.
        zfpdef: CloseReceipt()
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(0x38, "");
        // Няма отговор. Връща ACK и състоянието е в статус байтовете, които се проверяват в cmdCustom и се инициира грешка (exception)
        return response;
    }
    
    @Override
    public void cmdCancelFiscalCheck() throws IOException {
        /*
        2.6.24. Command: 39h / 9 – Cancel fiscal receipt
        input: n. a.
        output: ACK
        FPR operation: Available only if receipt is not closed. Void all sales in the receipt and close the fiscal receipt (Fiscal receipt, Invoice receipt, Storno receipt or Credit Note). If payment is started, then finish payment and close the receipt.
        Input data : n. a.
        Output data : n. a.
        zfpdef: CancelReceipt()
        */
        cmdCustom(0x39, "");
    }

    @Override
    public void cmdPrintFiscalText(String text) throws IOException {
        /*
        2.6.22. Command: 37h / 7 – Free text printing
        input: <Text[TextLength-2]>
        output: ACK
        FPR operation: Print a free text. The command can be executed only if receipt is opened (Fiscal receipt, Invoice receipt, Storno receipt, Credit Note or Non-fical receipt). In the beginning and in the end of line symbol ‘#’ is printed.
        Input data :
        Text
        TextLength-2 symbols
        Output data: n. a.
        zfpdef: PrintText(Text)
        */        
        int maxLen = CONST_TEXTLENGTH-2;
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+text+")");
            text = text.substring(0, maxLen);
        }
        cmdCustom(0x37, text);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        2.6.1. Command: 2Eh / . – Open Non-fiscal receipt
        input: <OperNum[1..2]> <;> <OperPass[6]> {<;> <Reserved['0']> <;> <NonFiscalPrintType[1]>}
        output: ACK
        FPR operation: Opens a non-fiscal receipt assigned to the specified operator number, operator password and print type.
        Input data :
        OperNum
        (Operator Number) Symbols from 1 to 20 corresponding to operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        Reserved
        1 symbol with value ‘0’
        NonFiscalPrintType
        (Printing type) 1 symbol with value:
        - '0' – Step by step printing
        - '1' – Postponed Printing
        Output data: n. a.
        zfpdef: OpenNonFiscalReceipt(OperNum, OperPass, NonFiscalPrintType)
        */        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String printMode = "";
        switch (receiptPrintMode) {
            case BUFFERED :
                printMode = "0";
                break;
            case POSTPHONED :
                printMode = "1";
            case STEP_BY_STEP :
            default :
                printMode = "0";
        }
        String params = opCode+";"+opPasswd+";0;"+printMode;
        String res = cmdCustom(0x2E, params);
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text, int font) throws IOException {
        cmdPrintFiscalText(text);
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws IOException {
        cmdPrintFiscalText(text);
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws IOException {
        /*
        2.6.2. Command: 2Fh / / – Close Non-fiscal receipt
        input: n. a.
        output: ACK
        FPR operation: Closes the non-fiscal receipt.
        Input data : n. a.
        Output data : n. a.
        zfpdef: CloseNonFiscalReceipt()
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(0x2F, "");
        return response;
    }

    @Override
    public void cmdPaperFeed(int lineCount) throws IOException {
        /*
        2.2.11. Command: 2Bh / + – Paper feeding
        input: n. a.
        output: ACK
        FPR operation: Feeds one line of paper.
        Input data : n. a.
        Output data : n. a.
        zfpdef: PaperFeed()
        */        
        lineCount = Integer.max(1, Integer.min(lineCount, 99));
        for (int i = 0; i < lineCount; i++); 
            cmdCustom(0x2B, "");
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        2.6.12. Command: 31h / 1 – Sell/Correction of article belonging to VAT class definition
        input: <NamePLU[36]> <;> <OptionVATClass[1]> <;> <Price[1..10]> {<'*'> <Quantity[1..10]>} {<','> <DiscAddP[1..7]>} {<':'> <DiscAddV[1..8]>}
        output: ACK
        FPR operation: Register the sell (for correction use minus sign in the price field) of article with specified name, price, quantity, VAT class and/or discount/addition on the transaction.
        Input data :
        NamePLU
        (PLU Name) 36 symbols for article's name. 34 symbols are printed on paper. Symbol 0x7C ‘|’ is new line separator.
        OptionVATClass
        1 character for VAT class:
        - 'А' – VAT Class 0
        - 'Б' – VAT Class 1
        - 'В' – VAT Class 2
        - 'Г' – VAT Class 3
        - 'Д' – VAT Class 4
        - 'Е' – VAT Class 5
        - 'Ж' – VAT Class 6
        - 'З' – VAT Class 7
        - ‘*’ - Forbidden
        Price
        Up to 10 symbols for article's price. Use minus sign '-' for correction
        '*'
        1 symbol '*' indicating the presence of quantity field
        Quantity
        Up to 10 symbols for quantity
        ','
        1 symbol ',' indicating the presence of discount/addition field
        DiscAddP
        (Discount/Addition %) Up to 7 symbols for percentage of discount/addition. Use minus sign '-' for discount
        ':'
        1 symbol ',' indicating the presence of value discount/addition field
        DiscAddV
        (Discount/Addition Value) Up to 8 symbols for value of discount/addition. Use minus sign '-' for discount
        Output data : n. a.
        Notes:
        The quantity fields are not obligatory. If no value is stated for them the FD executed the command for a default quantity of 1.000
        The discount/addition fields are not obligatory. The discount/addition must be in percents and is determined from the presence or absence of the '-' symbol.
        zfpdef: SellPLUwithSpecifiedVAT(NamePLU, OptionVATClass, Price, Quantity, DiscAddP, DiscAddV)
        */        
        int maxLen = 34;
        String params = "";
        // NamePLU
        String namePLU = "";
        String[] textLines = sellText.replace("|"," ").split("\n");
        if ((textLines.length > 1) && (textLines[1].length() > 0)) {
            if ((textLines[0].length()+textLines[1].length()) < maxLen) {
                namePLU = textLines[0]+"|"+textLines[1];
            } else {
                // Скъсява се дългия ред, за да може да се вземе целия кратък
                if (textLines[0].length() > textLines[1].length()) {
                    // Първият ред е по-дълъг
                    int len1 = maxLen - textLines[1].length();
                    if (len1 > 0)
                        namePLU = textLines[0].substring(0, len1)+"|"+textLines[1];
                    else
                        namePLU = textLines[1];
                } else {
                    // Вторият ред е по-дълъг
                    int len2 = maxLen - textLines[0].length();
                    if (len2 > 0)
                        namePLU = textLines[0] + "|" + textLines[1].substring(0, len2);
                    else
                        namePLU = textLines[0];
                }
            }
        } else {
            namePLU = textLines[0];
        }
        if (namePLU.length() > 36)
            namePLU = namePLU.substring(0, 36);
        namePLU = String.format("%-36s", namePLU);
        params = namePLU;
        // OptionVATClass
        if (taxGroup.length() > 1)
            taxGroup = taxGroup.substring(0,1);
        params += ";" + taxGroup;
        // Price
        params += ";" + String.format(Locale.ROOT, "%.2f", price);
        // Quantity
        if (Math.abs(quantity) < 0.001) quantity = 1d;
        params += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        // DiscAddP or DiscAddV
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent DiscAddP
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ","+String.format(Locale.ROOT, "%.2f", dd)+":0.0";
                }
            } else {
                // discount as absolute value DiscAddV
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ",0.0:"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        } else 
            params += ",0.0:0.0";
        cmdCustom(0x31, params);
    }

    @Override
    public void cmdSellDept(String sellText, String dept, double price, double quantity, String unit, String discount) throws IOException {
        /*
        2.6.13. Command: 31h / 1 – Sell/Correction of article belonging to department
        input: <NamePLU[36]> <;> <reserved[' ']> <;> <Price[1..10]> {<'*'> <Quantity[1..10]>} {<','> <DiscAddP[1..7]>} {<':'> <DiscAddV[1..8]>} {<'!'> <DepNum[1..2]>}
        output: ACK
        FPR operation: Register the sell (for correction use minus sign in the price field) of article belonging to department with specified name, price, quantity and/or discount/addition on the transaction. The VAT of article got from department to which article belongs.
        Input data :
        NamePLU
        (PLU Name) 36 symbols for article's name. 34 symbols are printed on paper. Symbol 0x7C ‘|’ is new line separator.
        reserved
        1 symbol with value "space"
        Price
        Up to 10 symbols for article's price. Use minus sign '-' for correction
        '*'
        1 symbol '*' indicating the presence of quantity field
        Quantity
        Up to 10 symbols for quantity
        ','
        1 symbol ',' indicating the presence of discount/addition field
        DiscAddP
        (Discount/Addition %) Up to 7 symbols for percentage of discount/addition. Use minus sign '-' for discount
        ':'
        1 symbol ',' indicating the presence of value discount/addition field
        DiscAddV
        (Discount/Addition Value) Up to 8 symbols for value of discount/addition. Use minus sign '-' for discount
        '!'
        1 symbol '!' indicating the presence of departament
        DepNum
        DepNum + 80h (Department Number) 1 symbol for article department attachment, formed in the following manner; example: Dep01=81h, Dep02=82h … Dep19=93h
        Output data : n. a.
        Notes:
        The quantity fields are not obligatory. If no value is stated for them the FD executed the command for a default quantity of 1.000
        The discount/addition fields are not obligatory. The discount/addition must be in percents and is determined from the presence or absence of the '-' symbol.
        zfpdef: SellPLUfromDep(NamePLU, Price, Quantity, DiscAddP, DiscAddV, DepNum)
        */        
        int maxLen = 34;
        String params = "";
        // NamePLU
        String namePLU = "";
        String[] textLines = sellText.replace("|"," ").split("\n");
        if ((textLines.length > 1) && (textLines[1].length() > 0)) {
            if ((textLines[0].length()+textLines[1].length()) < maxLen) {
                namePLU = textLines[0]+"|"+textLines[1];
            } else {
                // Скъсява се дългия ред, за да може да се вземе целия кратък
                if (textLines[0].length() > textLines[1].length()) {
                    // Първият ред е по-дълъг
                    int len1 = maxLen - textLines[1].length();
                    if (len1 > 0)
                        namePLU = textLines[0].substring(0, len1)+"|"+textLines[1];
                    else
                        namePLU = textLines[1];
                } else {
                    // Вторият ред е по-дълъг
                    int len2 = maxLen - textLines[0].length();
                    if (len2 > 0)
                        namePLU = textLines[0] + "|" + textLines[1].substring(0, len2);
                    else
                        namePLU = textLines[0];
                }
            }
        } else {
            namePLU = textLines[0];
        }
        if (namePLU.length() > 36)
            namePLU = namePLU.substring(0, 36);
        namePLU = String.format("%-36s", namePLU);
        params = namePLU;
        // ;reserved['']
        params += "; ";
        // ;Price
        params += ";" + String.format(Locale.ROOT, "%.2f", price);
        // {<'*'> <Quantity[1..10]>}
        if (Math.abs(quantity) < 0.001) quantity = 1d;
        params += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        // {<','> <DiscAddP[1..7]>} {<':'> <DiscAddV[1..8]>}
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent DiscAddP
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ","+String.format(Locale.ROOT, "%.2f", dd)+":0.0";
                }
            } else {
                // discount as absolute value DiscAddV
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ",0.0:"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        } else 
            params += ",0.0:0.0";
        // {<'!'> <DepNum[1..2]>}
        if (dept.length() > 2)
            dept = dept.substring(0,2);
        params += "!" + dept;
        
        cmdCustom(0x31, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, String discount) throws IOException {
       /*
        2.6.16. Command: 33h / 3 – Subtotal
        input: <OptionPrinting[1]> <;> <OptionDisplay[1]> {<':'> <DiscAddV[1..8]>} {<','> <DiscAddP[1..7]>}
        output:<SubtotalValue[1..10]>
        FPR operation: Calculate the subtotal amount with printing and display visualization options. Provide information about values of the calculated amounts. If a percent or value discount/addition has been specified the subtotal and the discount/addition value will be printed regardless the parameter for printing.
        Input data :
        OptionPrinting
        1 symbol with value:
        - '1' – Yes
        - '0' – No
        OptionDisplay
        1 symbol with value:
        - '1' – Yes
        - '0' – No
        ':'
        1 symbol ':' indicating the presence of value discount/addition field
        DiscAddV
        (Discount/Addition Value) Up to 8 symbols for the value of the discount/addition. Use minus sign '-' for discount
        ','
        1 symbol ',' indicating the presence of percent discount/addition field
        DiscAddP
        (Discount/Addition %) Up to 7 symbols for the percentage value of the discount/addition. Use minus sign '-' for discount
        Output data:
        SubtotalValue
        Up to 10 symbols for the value of the subtotal amount
        Notes:
        When the discount/addition is a percentage the amount is distributed proportionally over the turnover items and is automatically transferred to the turnovers of the corresponding VAT groups.
        A value discount/addition may be specified only if all sales are of articles (items) belonging to one and the same VAT group.
        zfpdef: Subtotal(OptionPrinting, OptionDisplay, DiscAddP, DiscAddV)
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = (toPrint?"1":"0")+";"+(toDisplay?"1":"0");
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent DiscAddP
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ","+String.format(Locale.ROOT, "%.2f", dd);
                }
            } else {
                // discount as absolute value DiscAddV
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ":"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        }
        String res = cmdCustom(0x33, params);
        if (res.length() > 0) {
            response.put("SubTotal", reformatCurrency(res, 1));
        } else {
            err("Грешка при междинна сума във фискален бон!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        /*
        2.6.19. Command: 35h / 5 – Payment
        input: <PaymentType[1..2]> <;> <OptionChange[1]> <;> <Amount[1..10]> { <;> <OptionChangeType[1]> }
        output: ACK
        FPR operation: Register the payment in the receipt with specified type of payment with amount received.
        Input data :
        PaymentType
        (Payment Types)1 symbol for payment type:
        - '0' – Payment 0
        - '1' – Payment 1
        - '2' – Payment 2
        - '3' – Payment 3
        - '4' – Payment 4
        - '5' – Payment 5
        - '6' – Payment 6
        - '7' – Payment 7
        - '8' – Payment 8
        - '9' – Payment 9
        - '10' – Payment 10
        - '11' – Payment 11
        OptionChange
        (Change) Default value is 0, 1 symbol with value:
        - '0 – With Change
        - '1' – Without Change
        Amount
        Up to 10 characters for received amount
        ‘*’
        1 symbol with value ‘*’
        OptionChangeType
        1 symbols with value:
        - '0' – Change In Cash
        - '1' – Same As The payment
        - '2' – Change In Currency
        Output data: n. a.
        Notes:
        By executing this command the FD enters the payment mode. No further sales and/or corrections are allowed.
        If the amount received is equal to or greater than the grand total amount (the amount due) the FD quits the procedure and calculates the change in the specified type of payment except in the cases when OptionChange is not 1 – in such cases the operator is liable for the stated amount.
        The receipt can be finalized only when the last payment transfer is sufficient to cover the whole amount due (the grand total amount), i.e. the payment procedure has been finalized.
        zfpdef: Payment(PaymentType, OptionChange, Amount, OptionChangeType)
        
        2.6.20. Command: 35h / 5 – Pay exact sum
        input: <PaymentType[1..2]> <;> <Option[‘0’]> <;> <Amount['”']>
        output: ACK
        FPR operation: Register the payment in the receipt with specified type of payment and exact amount received.
        Input data :
        PaymentType
        (Payment Types)1 symbol for payment type:
        - '0' – Payment 0
        - '1' – Payment 1
        - '2' – Payment 2
        - '3' – Payment 3
        - '4' – Payment 4
        - '5' – Payment 5
        - '6' – Payment 6
        - '7' – Payment 7
        - '8' – Payment 8
        - '9' – Payment 9
        - '10' – Payment 10
        - '11' – Payment 11
        Option
        1 symbol with value 0
        Amount
        1 symbol ' ” ', quotation mark, for pay with exact sum
        Output data: n.a.
        zfpdef: PayExactSum(PaymentType)        
        */        
        String params = "";

        // Check method of payment
        if (paymentType.length() > 1)
            paymentType = paymentType.substring(0, 1);
        String[] validPaymentTypes = {"0","1","2","3","4","5","6","7","8","9","10","11"};
        if (!Arrays.asList(validPaymentTypes).contains(paymentType))
            throw new FDException(-1L, "Невалиден начин на плащане ("+paymentType+")!");
        // PaymentType
        params = paymentType;
        // <;> <OptionChange[1]>
        params += ";0";
        // <;> <Amount[1..10]> or <;> <Amount['"']>
        if (abs(amount) >= 0.01)
            params += ";"+String.format(Locale.ROOT, "%.2f", amount);
        else
            params += ";\"";
        LinkedHashMap<String, String> response = new LinkedHashMap();
        cmdCustom(0x35, params);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        2.6.5. Command: 30h / 0 –Open Fiscal storno receipt
        input:<OperNum[1..2]> <;> <OperPass[6]> <;> <ReceiptFormat[1]> <;> <PrintVAT[1]> <;> <StornoRcpPrintType[1]> <;> <StornoReason[1]> <;> <RelatedToRcpNum[1..6]> <;> <RelatedToRcpDateTime ”DD-MM-YY HH:MM:SS”> <;> <FMNum[8]> {<;> <RelatedToURN[24]>}
        output: ACK
        FPR operation: Open a fiscal storno receipt assigned to the specified operator number and operator password, parameters for receipt format, print VAT, printing type and parameters for the related storno receipt.
        Input data :
        OperNum
        (Operator Number) Symbols from 1 to 20 corresponding to operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        ReceiptFormat
        (Format) 1 symbol with value:
        - '1' - Detailed
        - '0' - Brief
        PrintVAT
        (VAT included in the receipt) 1 symbol with value:
        - '1' – Yes
        - '0' – No
        StornoRcpPrintType
        (Printing type) 1 symbol with value:
        - '@' – Step by step printing
        - 'B' – Postponed Printing
        - 'D' – Buffered Printing
        StornoReason
        1 symbol for reason of storno operation with value:
        - '0' – Operator error
        - '1' – Goods Claim or Goods return
        - '2' – Tax relief
        RelatedToRcpNum
        (Receipt number) Up to 6 symbols for issued receipt number
        RelatedToRcpDateTime
        (Receipt Date and Time) 17 symbols for Date and Time of the issued receipt in format DD-MM-YY HH:MM:SS
        FMNum
        8 symbols for number of the Fiscal Memory
        RelatedToURN
        Up to 24 symbols for the issed receipt unique receipt number.
        NRA format: XXXХХХХХ-ZZZZ-YYYYYYY where:
        * ХХХХХХXX – 8 symbols [A-Z, a-z, 0-9] for individual device number,
        * ZZZZ – 4 symbols [A-Z, a-z, 0-9] for code of the operator,
        * YYYYYYY – 7 symbols [0-9] for next number of the receipt
        Output data: n. a.
        Note:
        If Postponed printing is enabled after opened fiscal receipt, all the next commands will be executed but won't be printed immediately. The data for whole receipt is stored to be printed up to AS sent information for receipt closure. If up to 5 sec timeout no command ECR closing the receipt
        zfpdef: OpenStornoReceipt(OperNum, OperPass, ReceiptFormat, PrintVAT, StornoRcpPrintType, StornoReason, RelatedToRcpNum, RelatedToRcpDateTime, FMNum, RelatedToURN)

        2.6.8. Command: 30h / 0 – Open Fiscal Invoice Credit Note receipt with free customer data
        input: <OperNum[1..2]> <;> <OperPass[6]> <;> <reserved['0']> <;> <reserved['0']> <;> <InvoiceCreditNotePrintType[1]> <;> <Recipient[26]> <;> <Buyer[16]> <;> <VATNumber[13]> <;> <UIC[13]> <;> <Address[30]> <;> <UICType[1]> <;> <StornoReason[1]> <;> <RelatedToInvoiceNum[10]> <;> <RelatedToInvoiceDateTime”DD-MM-YY HH:MM:SS”> <;> <RelatedToRcpNum[1..6]> <;> <FMNum[8]> { <;> <RelatedToURN[24]> }
        output: ACK
        FPR operation: Opens a fiscal invoice credit note receipt assigned to the specified operator number and operator password with free info for customer data. The Invoice receipt can be issued only if the invoice range (start and end numbers) is set.
        Input data :
        OperNum
        (Operator Number) Symbol from 1 to 20 corresponding to operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        reserved
        1 symbol with value '0'
        reserved
        1 symbol with value '0'
        InvoiceCreditNotePrintType
        (Printing type) 1 symbol with value:
        - 'A' – Step by step printing
        - 'C' – Postponed Printing
        - 'E' – Buffered Printing
        Recipient
        26 symbols for Invoice recipient
        Buyer
        16 symbols for Invoice buyer
        VATNumber
        13 symbols for customer Fiscal number
        UIC
        13 symbols for customer Unique Identification Code
        Address
        30 symbols for Address
        UICType
        1 symbol for type of Unique Identification Code:
        - '0' - Bulstat
        - '1' - EGN
        - '2' – Foreigner Number
        - '3' – NRA Official Number
        StornoReason
        1 symbol for reason of storno operation with value:
        - '0' – Operator error
        - '1' – Goods Claim or Goods return
        - '2' – Tax relief
        RelatedToInvoiceNum
        10 symbols for issued invoice number
        RelatedToInvoiceDateTime
        17 symbols for issued invoice date and time in format DD-MM-YY HH:MM:SS
        RelatedToRcpNum
        (Receipt number) Up to 6 symbols for issued receipt number
        FMNum
        8 symbols for number of the Fiscal Memory
        RelatedToURN
        Up to 24 symbols for the issed invoice receipt unique receipt number.
        NRA format: XXXХХХХХ-ZZZZ-YYYYYYY where:
        * ХХХХХХXX – 8 symbols [A-Z, a-z, 0-9] for individual device number,
        * ZZZZ – 4 symbols [A-Z, a-z, 0-9] for code of the operator,
        * YYYYYYY – 7 symbols [0-9] for next number of the receipt
        Output data: n. a.
        Note:
        If Postponed printing is enabled after opened fiscal receipt, all the next commands will be executed but won't be printed immediately. The data for whole receipt is stored to be printed up to AS sent information for receipt closure. If up to 5 sec timeout no command ECR closing the receipt
        zfpdef: OpenCreditNoteWithFreeCustomerData(OperNum, OperPass, InvoiceCreditNotePrintType, Recipient , Buyer, UIC, IDNumber, Address, UICType, RelatedToInvoiceNum, RelatedToInvoiceDateTime, RelatedToRcpNum, FMNum, RelatedToURN)
        */        
        
        // TODO: Check parameters
        //<OperNum[1..2]> <;> <OperPass[6]> <;> <ReceiptFormat[1]> <;> <PrintVAT[1]> <;> <FiscalRcpPrintType[1]> {<’$’> <UniqueReceiptNumber[24]>}
        if (!opCode.matches("^\\d\\d?$"))
            throw new FDException("Невалиден код на оператор!");
        if (opPasswd.length() > 6)
            throw new FDException("Максималната дължина на паролата е 6 символа!");
        if (opPasswd.length() < 6)
            opPasswd = String.format("%-6s", opPasswd);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String printMode = "";
        switch (receiptPrintMode) {
            case BUFFERED :
                printMode = invoice?"E":"D";
                break;
            case POSTPHONED :
                printMode = invoice?"C":"B";
            case STEP_BY_STEP :
            default :
                printMode = invoice?"A":"@";
        }
        String params;
        if (invoice) {
            String recipient = customerData.recipient;
            if (recipient.length() > 26) {
                warn("Дължината на 'получател' е по-голяма от 26 сивола и ще бъде съкратена!");
                recipient = recipient.substring(0, 26);
            }
            recipient = String.format("%-26s", recipient);
//            recipient = recipient.replaceAll(";", ",");
            String buyer = customerData.buyer;
            if (buyer.length() > 16) {
                warn("Дължината на 'купувач' е по-голяма от 16 сивола и ще бъде съкратена!");
                buyer = buyer.substring(0, 16);
            }    
            buyer = String.format("%-16s", buyer);
//            buyer = buyer.replaceAll(";", ",");
            String address = customerData.address;
            if (address.length() > 30) {
                warn("Дължината на 'адрес' е по-голяма от 30 сивола и ще бъде съкратена!");
                address = address.substring(0, 30);
            }    
            address = String.format("%-30s", address);
//            address = address.replaceAll(";", ",");
            String VATNumber = customerData.VATNumber;
            if (VATNumber.length() > 13) {
                warn("Дължината на 'Данъчен номер' е по-голяма от 13 сивола и ще бъде съкратена!");
                VATNumber = VATNumber.substring(0, 13);
            }    
            VATNumber = String.format("%-13s", VATNumber);
            String UIC = customerData.UIC;
            if (UIC.length() > 13) {
                warn("Дължината на 'Булстат' е по-голяма от 13 сивола и ще бъде съкратена!");
                UIC = UIC.substring(0, 13);
            }    
            UIC = String.format("%-13s", UIC);
            
            String UICType = "";
            switch (customerData.UICType) {
                case EGN : UICType = "1"; break;
                case FOREIGN : UICType = "2"; break;
                case NRA : UICType = "3"; break;
                case BULSTAT : 
                default :    
                    UICType = "0"; break;
            }
            if (RevInvNum.length() > 10)
                RevInvNum = RevInvNum.substring(0, 10);
            RevInvNum = String.format("%-10s", RevInvNum);
            if (RevDocNum.length() > 6)
                RevDocNum = RevDocNum.substring(0, 6);
            if (RevFMNum.length() > 8)
                RevFMNum = RevFMNum.substring(0, 8);
            RevFMNum = String.format("%-8s", RevFMNum);
            
            params = opCode+";"+opPasswd;       // <OperNum[1..2]> <;> <OperPass[6]>
            params = params + ";0;0";           // <;> <reserved['0']> <;> <reserved['0']>
            params = params + ";" + printMode;  // <;> <InvoicePrintType[1]>
            params = params + ";" + recipient;  // <;> <Recipient[26]>
            params = params + ";" + buyer;      // <;> <Buyer[16]>
            params = params + ";" + VATNumber;  // <;> <VATNumber[13]>
            params = params + ";" + UIC;        // <;> <UIC[13]>
            params = params + ";" + address;    // <;> <Address[30]>
            params = params + ";" + UICType;    // <;> <UICType[1]>

            params = params + ";" + RevType;    // <;> <StornoReason[1]>
            params = params + ";" + RevInvNum;  // <;> <RelatedToInvoiceNum[10]>
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy HH:mm:ss");
            params = params + ";" + dateFormat.format(RevDateTime); // <;> <RelatedToInvoiceDateTime”DD-MM-YY HH:MM:SS”>
            params = params + ";" + RevDocNum;  // <;> <RelatedToRcpNum[1..6]>
            params = params + ";" + RevFMNum;// <;> <FMNum[8]>
            if (RevUNS.length() > 0)
                params = params + ";" + RevUNS; // {<;> <RelatedToURN[24]>}
        } else {
            String[] validRevTypes = {"0","1","2"};
            if (!Arrays.asList(validRevTypes).contains(RevType))
                throw new FDException(-1L, "Невалиден тип сторно ("+RevType+")!");
            if (!RevDocNum.matches("^\\d{1,7}$")) 
                throw new FDException("Невалиден номер на документа, по който е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999!");

            if (!RevFMNum.matches("^\\d{8,8}$"))
                throw new FDException("Невалиден номер на фискалната памет '"+RevFMNum+"' трябва да е число записано с 8 знака!");

            
            params = opCode+";"+opPasswd;   // <OperNum[1..2]> <;> <OperPass[6]>
            params = params + ";" + (receiptPrintFormat.equals(ReceiptPrintFormatType.DETAILED)?"1":"0");   // <;> <ReceiptFormat[1]>
            params = params + ";" + (receiptPrintVAT?"1":"0");  // <;> <PrintVAT[1]>
            params = params + ";" + printMode;                  // <;> <StornoRcpPrintType[1]>
            params = params + ";" + RevType;    // <;> <StornoReason[1]>
            params = params + ";" + RevDocNum;  // <;> <RelatedToRcpNum[1..6]>
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy HH:mm:ss");
            params = params + ";" + dateFormat.format(RevDateTime); // <;> <RelatedToRcpDateTime ”DD-MM-YY HH:MM:SS”>
            params = params + ";" + RevFMNum;// <;> <FMNum[8]>
            if (RevUNS.length() > 0)
                params = params + ";" + RevUNS; // {<;> <RelatedToURN[24]>}
        }
        
        fiscalCheckRevOpened = true;

        String res = cmdCustom(0x30, params);
        // Няма отговор. Връща ACK и състоянието е в статус байтовете, които се проверяват в cmdCustom и се инициира грешка (exception)
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws IOException {
        /*
        2.6.26. Command: 3Bh / ; – Non-fiscal RA and PO amounts
        input:<OperNum[1..2]> <;> <OperPass[6]> <;> <reserved['0']> <;> <Amount[1..10]> {<’$’> <PrintAvailability[1]> } {<;> <Text[TextLength-2]>}
        output: ACK
        FPR operation: Registers cash received on account or paid out.
        Input data :
        OperNum
        (Operator Number) Symbols from 1 to 20 corresponding to the operator's number
        OperPass
        (Operator Password) 6 symbols for operator's password
        reserved
        1 symbol reserved with value '0'
        Amount
        Up to 10 symbols for the amount lodged. Use minus sign for withdrawn
        ‘$’
        1 symbol with value ‘$’
        PrintAvailability
        1 symbol with value: - ‘0’ – No
        - ‘1’ - Yes
        Text
        TextLength-2 symbols. In the beginning and in the end of line symbol ‘#’ is printed.
        Output data: n. a.
        zfpdef: ReceivedOnAccount_PaidOut(OperNum, OperPass, Amount, PrintAvailability, Text)        
        
        2.7.2. Command: 6Eh / n – Read registers, Option ‘0’ (on hand)
        input: <'0'>
        output: <'0'> <;> <AmountPayment0[1..13]> <;> <AmountPayment1[1..13]> <;> <AmountPayment2[1..13]> <;> <AmountPayment3[1..13]> <;> <AmountPayment4[1..13]> <;> <AmountPayment5[1..13]> <;> <AmountPayment6[1..13]> <;> <AmountPayment7[1..13]> <;> <AmountPayment8[1..13]> <;> <AmountPayment9[1..13]> <;> <AmountPayment10[1..13]> <;> <AmountPayment11[1..13]>
        FPR operation: Provides information about the amounts on hand by type of payment.
        Input data :
        '0'
        1 symbol obligatory '0'
        Output data:
        '0'
        1 symbol obligatory '0'
        AmountPayment0
        Up to 13 symbols for the accumulated amount by payment type 0
        AmountPayment1
        Up to 13 symbols for the accumulated amount by payment type 1
        AmountPayment2
        Up to 13 symbols for the accumulated amount by payment type 2
        AmountPayment3
        Up to 13 symbols for the accumulated amount by payment type 3
        AmountPayment4
        Up to 13 symbols for the accumulated amount by payment type 4
        AmountPayment5
        Up to 13 symbols for the accumulated amount by payment type 5
        AmountPayment6
        Up to 13 symbols for the accumulated amount by payment type 6
        AmountPayment7
        Up to 13 symbols for the accumulated amount by payment type 7
        AmountPayment8
        Up to 13 symbols for the accumulated amount by payment type 8
        AmountPayment9
        Up to 13 symbols for the accumulated amount by payment type 9
        AmountPayment10
        Up to 13 symbols for the accumulated amount by payment type 10
        AmountPayment11
        Up to 13 symbols for the accumulated amount by payment type 11
        zfpdef: ReadDailyAvailableAmounts()
        2.7.3. Command: 6Eh / n – Read registers, Option ‘0’, KL (on hand)
        input: <'0'>
        output: <'0'> <;> <AmountPayment0[1..13]> <;> <AmountPayment1[1..13]> <;> <AmountPayment2[1..13]> <;> <AmountPayment3[1..13]> <;> <AmountPayment4[1..13]>
        FPR operation: Provides information about the amounts on hand by type of payment. Command works for KL version 2 devices.
        Input data :
        '0'
        1 symbol obligatory '0'
        Output data:
        '0'
        1 symbol obligatory '0'
        AmountPayment0
        Up to 13 symbols for the accumulated amount by payment type 0
        AmountPayment1
        Up to 13 symbols for the accumulated amount by payment type 1
        AmountPayment2
        Up to 13 symbols for the accumulated amount by payment type 2
        AmountPayment3
        Up to 13 symbols for the accumulated amount by payment type 3
        AmountPayment4
        Up to 13 symbols for the accumulated amount by payment type 4
        zfpdef: ReadDailyAvailableAmounts_Old()        
        */  
        if (!opCode.matches("^\\d\\d?$"))
            throw new FDException("Невалиден код на оператор!");
        if (opPasswd.length() > 6)
            throw new FDException("Максималната дължина на паролата е 6 символа!");
        String params = opCode+";"+opPasswd; // <OperNum[1..2]> <;> <OperPass[6]>
        params += ";0"; // <;> <reserved['0']>
        params += ";"+String.format(Locale.ROOT, "%.2f", ioSum); // <;> <Amount[1..10]>
        params += "$1"; // {<’$’> <PrintAvailability[1]> }
        // {<;> <Text[TextLength-2]>}
        cmdCustom(0x3B, params);
        // ReadDailyAvailableAmounts
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // 
        params = "0";
        String res = cmdCustom(0x6E, params);
        // <'0'> <;> <AmountPayment0[1..13]> <;> <AmountPayment1[1..13]> ...
        if (res.startsWith("0;")) {
            // Сумиране на наличностите по видове плащания
            String[] rData = res.split(";");
            double CashSum = 0;
            for(int i = 1; i < rData.length; i++) {
                try {
                    CashSum += Double.parseDouble(rData[i]);
                } catch (Exception e) {
                    warn("Невалидна сума ("+rData[i]+")."+e.getMessage());
                }
            }
            // Заявката е изпълнена
            response.put("CashSum", Double.toString(CashSum));
            response.put("CashIn", "0");
            response.put("CashOut", "0");
        } else {    
            err("Грешка при прочитане на информация за наличността ("+res+")!");
//            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public void cmdSetOperator(String opCode, String opPasswd, String name) throws IOException {
        /*
        2.4.10. Command: 4Ah / J – Program operator’s name and password
        input: <Number[1..2]> <;> <Name[20]> <;> <Password[6]>
        output: ACK
        FPR operation: Programs the operator's name and password.
        Input data :
        Number
        Symbols from '1' to '20' corresponding to operator's number
        Name
        20 symbols for operator's name
        Password
        6 symbols for operator's password
        Output data : n. a.
        zfpdef: ProgOperator(Number, Name, Password)        
        */
        int maxLen = 20;
        if (name.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(name.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+name+")");
            name = name.substring(0, maxLen);
        }
        String params = opCode+";"+name+";"+opPasswd;
        cmdCustom(0x4A, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        2.7.36. Command: 73h / s– Read last daily report info
        input: n. a.
        output:<LastZDailyReportDate “DD-MM-YYYY”> <;> <LastZDailyReportNum[1..4]> <;> <LastRAMResetNum[1..4]> <;> <TotalReceiptCounter[6]> <;> <DateTimeLastFiscRec “DD-MM-YYYY HH:MM”> <;> <EJNum[2]> <;> <LastReceiptType[1]>
        FPR operation: Read date and number of last Z-report and last RAM reset event.
        Input data : n. a.
        Output data :
        LastZDailyReportDate
        10 symbols for last Z-report date in DD-MM-YYYY format
        LastZDailyReportNum
        Up to 4 symbols for the number of the last daily report
        LastRAMResetNum
        Up to 4 symbols for the number of the last RAM reset
        TotalReceiptCounter
        6 symbols for the total number of receipts in format ######
        DateTimeLastFiscRec
        Date Time parameter in format: DD-MM-YYYY HH:MM
        EJNum
        Up to 2 symbols for number of EJ
        LastReceiptType
        (Receipt and Printing type) 1 symbol with value:
        - ‘0’ - Sales receipt printing
        - ‘2’ – Non fiscal receipt
        - ‘4’ - Storno receipt
        - ‘1’ – Invoice sales receipt
        - ‘5’ – Invoice Credit note
        zfpdef: ReadLastDailyReportInfo()
        */
        String res = cmdCustom(0x73, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // <LastZDailyReportDate “DD-MM-YYYY”> <;> <LastZDailyReportNum[1..4]> <;> <LastRAMResetNum[1..4]> <;> <TotalReceiptCounter[6]> <;> <DateTimeLastFiscRec “DD-MM-YYYY HH:MM”> <;> <EJNum[2]> <;> <LastReceiptType[1]>
        if (res.contains(";")) {
            String[] resLines = res.split(";");
            String DocDate = resLines[0];
            String DocNum = "0000000"; // Няма номер на Z отчет документ номер
            if (resLines.length > 1)
                DocNum = resLines[1];
            response.put("DocNum", DocNum);
            response.put("DocDate", DocDate); 
            if (resLines.length > 2)
                response.put("LastRAMResetNum", resLines[2]);
            if (resLines.length > 3)
                response.put("TotalReceiptCounter", resLines[3]);
            if (resLines.length > 4)
                response.put("DateTimeLastFiscRec", resLines[4]);
            if (resLines.length > 5)
                response.put("EJNum", resLines[5]);
            if (resLines.length > 6)
                response.put("LastReceiptType", resLines[6]);
        } else {
            err("Грешка при четене на информация за натрупаните суми за деня!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    
    /**
     * Print daily report
     * @param option 'Z' for Z-report, 'X' for X-report
     * @param subOption '' default, 'D' - by departments
     * @return Sums in report
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdReportDaily(String option, String subOption) throws IOException {
        /*
        2.8.12. Command: 7Ch / | – Print daily fiscal report X or Z
        input: <OptionZeroing[1]>
        output: ACK
        FPR operation: Depending on the parameter prints:
        − daily fiscal report with zeroing and fiscal memory record, preceded by Electronic Journal report print ('Z');
        − daily fiscal report without zeroing ('X');
        Input data:
        OptionZeroing
        1 character with following values:
        - 'Z' – Zeroing
        - 'X' – Without zeroing
        Output data: n.a.
        zfpdef: PrintDailyReport(OptionZeroing)
        
        2.8.1. Command: 76h / v – Print department report
        input: <OptionZeroing[1]>
        output: ACK
        FPR operation: Print a department report with or without zeroing ('Z' or 'X').
        Input data :
        OptionZeroing
        (Parameter Zero) 1 symbol with value:
        - 'Z' – Zeroing
        - 'X' – Without zeroing
        Output data : n. a.
        zfpdef: PrintDepartmentReport(OptionZeroing)        
        */
        if (!option.equals("Z") && !option.equals("X"))
            throw new FDException("Невалиден тип за дневен отчет! 'Z' => Z отчет, 'X' => X отчет");
        if (!subOption.equals("") && !subOption.equals("D"))
            throw new FDException("Невалиден подтип за дневен отчет! '' => обобщен, 'D' => по департаменти");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (subOption.equals("D")) { // By Departments
            cmdCustom(0x76, option);
        } else {
            cmdCustom(0x7C, option);
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws IOException {
        return cmdPrinterStatus();
    }


    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        2.2.3. Command: 22h / ” – Diagnostics
        input: n. a.
        output: ACK
        FPR operation: Prints out a diagnostic receipt.
        Input data : n. a.
        Output data : n. a.
        zfpdef:PrintDiagnostics()
        */
        
        String res = cmdCustom(0x22, ""); 
    }

    @Override
    public String cmdLastDocNum() throws IOException {
        /*
        2.7.28. Command: 71h / q – Read total receipt number
        input: n. a.
        output:<TotalReceiptCounter[6]>
        FPR operation: Read the total counter of last issued receipt.
        Input data : n. a.
        Output data :
        TotalReceiptCounter
        6 symbols for the total receipt counter in format ###### up to current last issued receipt by FD
        zfpdef: ReadLastReceiptNum()
        */
        return cmdCustom(0x71, ""); 
    }

    @Override
    public String cmdLastFiscalCheckQRCode() throws IOException {
        /*
        2.7.31. Command: 72h / r – Read information about last receipt QR barcode data, Option B
        input: <'B'>
        output: <QRcodeData[60]>
        FPR operation: Provides information about the QR code data in last issued receipt.
        Input data :
        'B'
        1 symbol 'B' for option
        Output data :
        QRcodeData
        Up to 60 symbols for last issued receipt QR code data separated by symbol ‘*’ in format: FM Number*Receipt Number*Receipt Date*Receipt Hour*Receipt Amount
        zfpdef:ReadLastReceiptQRcodeData()        
        */
        return cmdCustom(0x72, "B"); 
    }

    
    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws IOException {
        /*
        2.5.8. Command: 66h / f – Read detailed printer status
        input: n. a.
        output: <ExternalDisplay[1]> <;> <StatPRN[4]> <;> <FlagServiceJumper[1]>
        FPR operation: Provides additional status information
        Input data : n. a.
        Output data :
        ExternalDisplay
        1 symbol – connection with external display
        - 'Y' - Yes
        - 'N' - No
        StatPRN
        4 symbols for detailed status of printer (only for printers with ASB)
        N byte  N bit   status flag
        ST0     0       Reserved
                1       Reserved
                2       Signal level for drawer
                3       Printer not ready
                4       Reserved
                5       Open cover  
                6       Paper feed status
                7       Reserved
        ST1     0       Reserved    
                1       Reserved
                2       Reserved
                3       Cutter error
                4       Reserved
                5       Fatal error
                6       Overheat
                7       Reserved
        ST2     0       JNP (journal paper near end)
                1       RNP (receipt paper near end)
                2       JPE (journal paper end)
                3       RPE (receipt paper end)
                4       Reserved
                5       Reserved
                6       Reserved
                7       Reserved
        ST3     0       Print data buffer data exists        
                1       Reserved
                2       Reserved
                3       Reserved
                4       Reserved
                5       Reserved
                6       Reserved
                7       Reserved        
        FlagServiceJumper
        1 symbol with value:
        - 'J' - Yes
        - ' ' – No        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        response.putAll(getStatusBytesAsText());
        response.put("DeviceInfo", mDeviceInfo);
        response.put("Model", mModelName);
        response.put("SerialNum", mSerialNum);
        response.put("FMNum", mFMNum);
        response.put("Switches", mSwitches);
        response.put("FWRev", mFWRev);
        response.put("FWDT", mFWRevDT);
        response.put("IsFiscalized", isFiscalized()?"1":"0");
        response.put("IsOpenFiscalCheck", isOpenFiscalCheck()?"1":"0");
        response.put("IsOpenFiscalCheckRev", isOpenFiscalCheckRev()?"1":"0");
        response.put("IsOpenNonFiscalCheck", isOpenNonFiscalCheck()?"1":"0");
        // Read detailed printer status към момента връща невалиден отговор
        /*
        2.5.2. Command: 61h / a – Read registration information
        input: n. a.
        output: <UIC[13]> <;> <UICType[1]><;> <NRARegistrationNumber[6]><;> <NRARegistrationDate “DD-MM-YYYY HH:MM” >
        FPR operation: Provides information about the programmed VAT number, type of VAT number, register number in NRA and Date of registration in NRA.
        Input data : n. a.
        Output data :
        UIC
        13 symbols for Unique Identification Code
        UICType
        1 symbol for type of Unique Identification Code:
        - '0' - Bulstat
        - '1' - EGN
        - '2' – Foreigner Number
        - '3' – NRA Official Number
        NRARegistrationNumber
        Register number on the Fiscal device from NRA
        NRARegistrationDate
        Date of registration in NRA
        zfpdef: ReadRegistrationInfo(        
        */
        try {
            String res = cmdCustom(0x61, "");
            //output: <UIC[13]> <;> <UICType[1]><;> <NRARegistrationNumber[6]><;> <NRARegistrationDate “DD-MM-YYYY HH:MM” >
            String[] commaParts = res.split(";");
            response.put("UIC", commaParts[0]);
            if (commaParts.length > 1) 
                response.put("UICType", commaParts[1]);
            if (commaParts.length > 2) 
                response.put("NRARegistrationNumber", commaParts[2]);
            if (commaParts.length > 3) 
                response.put("NRARegistrationDate", commaParts[3]);
        } catch (Exception ex) {
            LOGGER.warning(ex.getMessage());
        }
        return response;
    }

    @Override
    public Date cmdGetDateTime() throws IOException {
        /*
        2.5.11. Command: 68h / h – Read date and time
        input: n. a.
        output: <DateTime “DD-MM-YYYY HH:MM”>
        FPR operation: Provides information about the current date and time.
        Input data : n. a.
        Output data :
            DateTime  Date Time parameter in format: DD-MM-YYYY HH:MM
        zfpdef: ReadDateTime()
        */
        String res = cmdCustom(0x68, "");
        Date dt;
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            dt = format.parse(res);
        } catch (ParseException ex) {
            throw new FDException("Грешка при четене на информация за дата/час ("+res+")!"+ex.getMessage());
        }
        return dt;
    }
    
    @Override
    public void cmdSetDateTime(Date dateTime) throws IOException {
        /*
        2.4.5. Command: 48h / H – Program date and time
        input: <DateTime “DD-MM-YY HH:MM:SS”>
        output: ACK
        FPR operation: Sets the date and time and prints out the current values.
        Input data :
        DateTime Date Time parameter in format: DD-MM-YY HH:MM:SS
        Output data : n. a.
        zfpdef: SetDateTime(DateTime)
        */
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        String params = format.format(dateTime);
        cmdCustom(0x48, params);
    }

    @Override
    public void cmdPrintCheckDuplicate() throws IOException {
        /*
        2.6.25. Command: 3Ah / : – Print a copy of the last document
        input: n. a.
        output: ACK
        FPR operation: Print a copy of the last receipt issued. When FD parameter for duplicates is enabled.
        Input data : n. a.
        Output data : n. a.
        zfpdef: PrintLastReceiptDuplicate()
        */
        cmdCustom(0x3A, "");
    }

    public final String cmdAuxilary(byte[] data) throws IOException {
        mLastCmd = -1;
        ProtocolTremolV10 p = (ProtocolTremolV10) protocol;
        String result = p.auxCommand(data);
//        mStatusBytes = protocol.getStatusBytes();
//        checkErrors(mClearErrors);
        return result;
    }

    /*    
    public final String cmdCustomRaw(String command) throws IOException {
        mLastCmd = -1;
        ProtocolV10E p = (ProtocolV10E) protocol;
        String result = p.customRawCommand(command);
//        mStatusBytes = protocol.getStatusBytes();
//        checkErrors(mClearErrors);
        return result;
    }
  */  
    @Override
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException {
        /*
        2.8.16. Command: 7Ch / | – Print Electronic Journal report from receipt number to receipt number with selected documents content
        input: <’j1’> <;> <’X’> <;> <FlagsReceipts [1]> <;> <FlagsReports [1]> <;> <'N'> <;> <StartRcpNum[6]> <;> <EndRcpNum[6]>
        output: ACK
        FPR operation: Print Electronic Journal Report from receipt number to receipt number and selected documents content. FlagsReceipts is a char with bits representing the receipt types. FlagsReports is a char with bits representing the report type.
        Input data:
        ‘j1’
        2 symbols with value ‘j1’
        ‘X’
        1 symbol with value ‘X’
        FlagsReceipts
        (Receipts included in EJ) 1 symbol for Receipts included in EJ:
        Flags.7=0
        Flags.6=1
        Flags.5=1 Yes, Flags.5=0 No (Include PO)
        Flags.4=1 Yes, Flags.4=0 No (Include RA)
        Flags.3=1 Yes, Flags.3=0 No (Include Credit Note)
        Flags.2=1 Yes, Flags.2=0 No (Include Storno Rcp)
        Flags.1=1 Yes, Flags.1=0 No (Include Invoice)
        Flags.0=1 Yes, Flags.0=0 No (Include Fiscal Rcp)
        ‘;’
        1 symbol with value ‘;’
        FlagsReports
        (Reports included in EJ) 1 symbol for Reports included in EJ:
        Flags.7=0
        Flags.6=1
        Flags.5=0
        Flags.4=1 Yes, Flags.4=0 No (Include FM reports)
        Flags.3=1 Yes, Flags.3=0 No (Include Other reports)
        Flags.2=1 Yes, Flags.2=0 No (Include Daily X)
        Flags.1=1 Yes, Flags.1=0 No (Include Daily Z)
        Flags.0=1 Yes, Flags.0=0 No (Include Duplicates)
        'N'
        1 symbol 'N'
        StartRcpNum
        6 symbols for initial receipt number included in report in format ######.
        EndRcpNum
        6 symbols for final receipt number included in report in format ######.
        Output data: n. a.
        */
        //TODO: Check on fiscalized device
//        cmdCustom(0x7C, params);
        ProtocolTremolV10 p = (ProtocolTremolV10) protocol;
        if (docNum.length() > 6)
            docNum = docNum.substring(0, 6);
        docNum = String.format("%6s", docNum).replace(" ", "0");
        byte[] paramPrefix = new byte[] {(byte)'j', (byte)'1', (byte)';', (byte)'X', (byte)';', (byte)0x7F, (byte)';', (byte)0x5F};
        byte[] paramSuffix = (";N;"+docNum+";"+docNum).getBytes();
        byte[] paramData = new byte[paramPrefix.length+paramSuffix.length];
        System.arraycopy(paramPrefix, 0, paramData, 0, paramPrefix.length);
        System.arraycopy(paramSuffix, 0, paramData, paramPrefix.length, paramSuffix.length);
        p.customCommandB(0x7C, paramData);
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        /*
        2.8.8. Command: 7Ah / z – Print detailed FM report by date
        input: <StartDate “DDMMYY”> <;> <EndDate “DDMMYY”>
        output: ACK
        FPR operation: Prints a detailed FM report by initial and end date.
        Input data :
        StartDate
        6 symbols for initial date in the DDMMYY format
        EndDate
        6 symbols for final date in the DDMMYY format
        Output data: n. a.
        zfpdef: PrintDetailedFMReportByDate(StartDate, EndDate)

        2.8.10. Command: 7Bh / { – Print brief FM report by date
        input: <StartDate “DDMMYY”> <;> <EndDate “DDMMYY”>
        output: ACK
        FPR operation: Print a brief FM report by initial and end date.
        Input data :
        StartDate
        6 symbols for initial date in the DDMMYY format
        EndDate
        6 symbols for final date in the DDMMYY format
        Output data: n. a.
        zfpdef: PrintBriefFMReportByDate(StartDate, EndDate)
        */
        //TODO: Check on fiscalized device
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        if (endDate == null)
            endDate = startDate;
        String params = dtf.format(startDate)+";"+dtf.format(endDate);
        cmdCustom(detailed?0x7A:0x7B, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws IOException {
        /*
        2.7.29. Command: 72h / r – Read information about current opened receipt
        input: n. a.
        output:<IsReceiptOpened[1]> <;> <SalesNumber[3]> <;> <SubtotalAmountVAT0[1..13]> <;> <SubtotalAmountVAT1[1..13]> <;> <SubtotalAmountVAT2[1..13]> <;> <ForbiddenVoid[1]> <;> <VATinReceipt[1]> <;> <ReceiptFormat[1]> <;> <InitiatedPayment[1]> <;> <FinalizedPayment[1]> <;> <PowerDownInReceipt[1]> <;> <TypeReceipt[1]> <;> <ChangeAmount[1..13]> <;> <OptionChangeType[1]> <;> <SubtotalAmountVAT3[1..13]> <;> <SubtotalAmountVAT4[1..13]> <;> <SubtotalAmountVAT5[1..13]> <;> <SubtotalAmountVAT6[1..13]> <;> <SubtotalAmountVAT7[1..13]> <;> <CurrentReceiptNumber[6]>
        FPR operation: Read the current status of the receipt.
        Input data : n. a.
        Output data :
        IsReceiptOpened
        1 symbol with value:
        - '0' - No
        - '1' - Yes
        SalesNumber
        3 symbols for number of sales in format ###
        SubtotalAmountVAT0
        Up to 13 symbols for subtotal by VAT group А
        SubtotalAmountVAT1
        Up to 13 symbols for subtotal by VAT group Б
        SubtotalAmountVAT2
        Up to 13 symbols for subtotal by VAT group В
        ForbiddenVoid
        1 symbol with value:
        - '0' – allowed
        - '1' - forbidden
        VATinReceipt
        1 symbol with value:
        - '0' – No
        - '1' - Yes
        ReceiptFormat
        (Format) 1 symbol with value:
        - '1' - Detailed
        - '0' - Brief
        InitiatedPayment
        1 symbol with value:
        - '0' – No
        - '1' – Yes
        FinalizedPayment
        1 symbol with value:
        - '0' - No
        - '1' - Yes
        PowerDownInReceipt
        1 symbol with value:
        - '0' - No
        - '1' - Yes
        TypeReceipt
        (Receipt and Printing type) 1 symbol with value:
        - ‘0’ - Sales receipt printing step by step
        - ‘2’ - Sales receipt Postponed Printing
        - ‘4’ - Storno receipt printing step by step
        - ‘6’ - Storno receipt Postponed Printing
        - ‘1’ – Invoice sales receipt printing step by step
        - ‘3’ – Invoice sales receipt Postponed Printing
        - ‘5’ – Invoice Credit note receipt printing step by step
        - ‘7’ – Invoice Credit note receipt Postponed Printing
        ChangeAmount
        Up to 13 symbols the amount of the due change in the stated payment type
        OptionChangeType
        1 symbol with value:
        - '0' - Change In Cash
        - '1' - Same As The payment
        - '2' – Change In Currency
        SubtotalAmountVAT3
        Up to 13 symbols for subtotal by VAT group Г
        SubtotalAmountVAT4
        Up to 13 symbols for subtotal by VAT group Д
        SubtotalAmountVAT5
        Up to 13 symbols for subtotal by VAT group Е
        SubtotalAmountVAT6
        Up to 13 symbols for subtotal by VAT group Ж
        SubtotalAmountVAT7
        Up to 13 symbols for subtotal by VAT group З
        CurrentReceiptNumber
        6 symbols for fiscal receipt number in format ######
        zfpdef: ReadCurrentReceiptInfo()        

        2.7.30. Command: 72h / r – Read information about current/last receipt payments amounts, Option P
        input: <'P'>
        output: <'P'> <;> <IsReceiptOpened[1]> <;> <Payment0Amount[1..13]> <;> <Payment1Amount[1..13]> <;> <Payment2Amount[1..13]> <;> <Payment3Amount[1..13]> <;> <Payment4Amount[1..13]> <;> <Payment5Amount[1..13]> <;> <Payment6Amount[1..13]> <;> <Payment7Amount[1..13]> <;> <Payment8Amount[1..13]> <;> <Payment9Amount[1..13]> <;> <Payment10Amount[1..13]> <;> <Payment11Amount[1..13]>
        FPR operation: Provides information about the payments in current receipt. This command is valid after receipt closing also.
        Input data :
        'P'
        1 symbol 'P' for option
        Output data :
        'P'
        1 symbol 'P' for option
        IsReceiptOpened
        1 symbol with value:
        - '0' - No
        - '1' - Yes
        Payment0Amount
        Up to 13 symbols for type 0 payment amount
        Payment1Amount
        Up to 13 symbols for type 1 payment amount
        Payment2Amount
        Up to 13 symbols for type 2 payment amount
        Payment3Amount
        Up to 13 symbols for type 3 payment amount
        Payment4Amount
        Up to 13 symbols for type 4 payment amount
        Payment5Amount
        Up to 13 symbols for type 5 payment amount
        Payment6Amount
        Up to 13 symbols for type 6 payment amount
        Payment7Amount
        Up to 13 symbols for type 7 payment amount
        Communication Protocol 74
        Payment8Amount
        Up to 13 symbols for type 8 payment amount
        Payment9Amount
        Up to 13 symbols for type 9 payment amount
        Payment10Amount
        Up to 13 symbols for type 10 payment amount
        Payment11Amount
        Up to 13 symbols for type 11 payment amount
        zfpdef:ReadCurrentOrLastReceiptPaymentAmounts()        
        
        2.7.27. Command: 70h / p – Read invoice number range
        input: n. a.
        output:<StartNum[10]> <;> <EndNum[10]>
        FPR operation: Provide information about invoice start and end numbers range.
        Input data : n. a.
        Output data :
        StartNum
        10 symbols for start No with leading zeroes in format ##########
        EndNum
        10 symbols for end No with leading zeroes in format ##########
        zfpdef: ReadInvoiceRange()        
        */
        String res = cmdCustom(0x72, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // output:
        //        0                       1
        // <IsReceiptOpened[1]> <;> <SalesNumber[3]> 
        //             2                                 3                               4
        // <;> <SubtotalAmountVAT0[1..13]> <;> <SubtotalAmountVAT1[1..13]> <;> <SubtotalAmountVAT2[1..13]> 
        //             5                    6                      7
        // <;> <ForbiddenVoid[1]> <;> <VATinReceipt[1]> <;> <ReceiptFormat[1]> 
        //             8                         9                         10
        // <;> <InitiatedPayment[1]> <;> <FinalizedPayment[1]> <;> <PowerDownInReceipt[1]> 
        //            11                    12                       13
        // <;> <TypeReceipt[1]> <;> <ChangeAmount[1..13]> <;> <OptionChangeType[1]> 
        //            14                                15                              16
        // <;> <SubtotalAmountVAT3[1..13]> <;> <SubtotalAmountVAT4[1..13]> <;> <SubtotalAmountVAT5[1..13]> 
        //            17                                18                              19
        // <;> <SubtotalAmountVAT6[1..13]> <;> <SubtotalAmountVAT7[1..13]> <;> <CurrentReceiptNumber[6]>
        if (res.contains(";")) {
            String[] resLines = res.split(";");
            // Отговор: <CanVd>,<TaxA>,<TaxB>,<TaxC>,<TaxD>,<TaxE>,<TaxF>,<TaxG>,<TaxH>,<Inv>,<InvNum>
            response.put("IsOpen", resLines[0]);
            response.put("CanVD", (resLines.length > 5)?resLines[5]:"");
            response.put("TaxA", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 1));
            response.put("TaxB", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 1));
            response.put("TaxC", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 1));
            response.put("TaxD", reformatCurrency((resLines.length > 14)?resLines[14]:"0", 1));
            response.put("TaxE", reformatCurrency((resLines.length > 15)?resLines[15]:"0", 1));
            response.put("TaxF", reformatCurrency((resLines.length > 16)?resLines[16]:"0", 1));
            response.put("TaxG", reformatCurrency((resLines.length > 17)?resLines[17]:"0", 1));
            response.put("TaxH", reformatCurrency((resLines.length > 18)?resLines[18]:"0", 1));
            response.put("SellCount", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 1));
            double sellAmount = 0;
            for (int i = 2; i < Math.min(resLines.length, 5); i++) {
                try {
                   sellAmount += Double.parseDouble(resLines[i]);
                } catch (Exception e) {
                    warn("Невалидна сума ("+resLines[i]+")."+e.getMessage());
                }
            }
            for (int i = 14; i < Math.min(resLines.length, 19); i++) {
                try {
                   sellAmount += Double.parseDouble(resLines[i]);
                } catch (Exception e) {
                    warn("Невалидна сума ("+resLines[i]+")."+e.getMessage());
                }
            }
            response.put("SellAmount", Double.toString(sellAmount));
//            response.put("Type", (resLines.length > 11)?resLines[11]:"");
//            response.put("Type", "");
            String TypeReceipt = ((resLines.length > 11)?resLines[11]:"0");
            response.put("Inv", "");
            response.put("InvNum", "");
            response.put("Type", "");
            /*
            TypeReceipt
            (Receipt and Printing type) 1 symbol with value:
            - ‘0’ - Sales receipt printing step by step
            - ‘2’ - Sales receipt Postponed Printing
            - ‘4’ - Storno receipt printing step by step
            - ‘6’ - Storno receipt Postponed Printing
            - ‘1’ – Invoice sales receipt printing step by step
            - ‘3’ – Invoice sales receipt Postponed Printing
            - ‘5’ – Invoice Credit note receipt printing step by step
            - ‘7’ – Invoice Credit note receipt Postponed Printing
            */            
            switch(TypeReceipt) {
                case "0" : // Фискален бон продажба.
                case "2" : // Фискален бон продажба.
                    response.put("Type", "FISCAL");
                    break;
                case "1" : // Фактура.
                case "3" :
                    response.put("Type", "FISCAL");
                    response.put("Inv", "1");
                    break;
                case "4" : // Сторно фискален бон.
                case "6" :
                    response.put("Type", "FISCAL_REV");
                    break;
                case "5" : // Сторно фактура (кр. известие).
                case "7" :
                    response.put("Type", "FISCAL_REV");
                    response.put("Inv", "1");
                    break;
            }
            response.put("InitiatedPayment", (resLines.length > 8)?resLines[8]:"");
            response.put("FinalizedPayment", (resLines.length > 9)?resLines[9]:"");
            response.put("PowerDownInReceipt", (resLines.length > 10)?resLines[10]:"");
            

            // Additional info about payments
            res = cmdCustom(0x72, "P");
            if (res.contains(";")) {
                resLines = res.split(";");
                double payAmount = 0;
                for (int i = 2; i < resLines.length; i++) {
                    try {
                       payAmount += Double.parseDouble(resLines[i]);
                    } catch (Exception e) {
                        warn("Невалидна сума ("+resLines[i]+")."+e.getMessage());
                    }
                }
                response.put("PayAmount", Double.toString(payAmount));
            }    
            
            if (response.get("Inv").equals("1")) {
                res = cmdCustom(0x70, "");
                // output:<StartNum[10]> <;> <EndNum[10]>
                if (res.contains(";")) {
                    resLines = res.split(";");
                    try {
                        int InvNum = Integer.parseInt(resLines[0]) - 1; 
                        response.put("InvNum", Integer.toString(InvNum));
                    } catch (Exception e) {
                        warn("Невалиден номер на фактура ("+resLines[0]+")."+e.getMessage());
                    }
                }
            }
        } else {
            err("Неочакван отговор при Четене на информация за текущия ФБ!");
//            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdGetEJInfo() throws IOException {
        /*
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // TODO:
        err("Неподдържана команда! Четене на информация за КЛЕН.");
        return response;
    }

    public final String cmdCustomRawRead(int command, String params, int byteCount, byte stopByte) throws IOException {
        mLastCmd = command;
        ProtocolTremolV10 p = (ProtocolTremolV10) protocol;
        p.customCommand(command, params);
        String result = p.rawRead(byteCount, stopByte);
//        mStatusBytes = protocol.getStatusBytes();
//        checkErrors(mClearErrors);
        return result;
    }
    
    @Override
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        /*
        2.9.1. Command: 7Ch / | – Read Electronic Journal report from date do date
        input: <ReportFormat[2]> <;> <'D'> <;> <StartRepFromDate “DDMMYY”> <;> <EndRepFromDate “DDMMYY”>
        output: ACK+
        FPR operation: Read Electronic Journal Report by initial to end date.
        Input data:
        ReportFormat
        (EJ Report format) 1 character with value
        - ‘J0’ – Detailed EJ
        - ‘J8’ – Brief EJ
        'D'
        1 symbol 'D'
        StartRepFromDate
        6 symbols for initial date in the DDMMYY format
        EndRepFromDate
        6 symbols for final date in the DDMMYY format
        Output data: n. a.
        zfpdef: ReadEJByDate(ReportFormat, StartRepFromDate, EndRepFromDate)
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        if (toDate == null)
            toDate = fromDate;
        String params = "J0;D;"+dtf.format(fromDate)+";"+dtf.format(toDate);
        String resStr = cmdCustomRawRead(0x7C, params, 0, (byte)'@');
//            Charset charset = Charset.forName("Windows-1251");
//            String resStr = new String(resBytes, charset);
        Pattern p = Pattern.compile("^\u0002.+?[|]", Pattern.MULTILINE);
        Matcher m = p.matcher(resStr);
        resStr = m.replaceAll("");
        // Replace suffix
        p = Pattern.compile("..$", Pattern.MULTILINE);
        m = p.matcher(resStr);
        resStr = m.replaceAll("");
        response.put("EJ", resStr);
        return response;
    }

     public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        /*
        2.9.3. Command: 7Ch / | – Read Electronic Journal report from receipt number to receipt number
        input: <ReportFormat[2]> <;> <'N'> <;> <StartRcpNum[6]> <;> <EndRcpNum[6]>
        output: ACK+
        FPR operation: Read Electronic Journal Report from receipt number to receipt number.
        Input data:
        ReportFormat
        (EJ Report format) 1 character with value
        - ‘J0’ – Detailed EJ
        - ‘J8’ – Brief EJ
        'N'
        1 symbol 'N'
        StartRcpNum
        6 symbols for initial receipt number included in report in format ######
        EndRcpNum
        6 symbols for final receipt number included in report in format ######
        Output data: n. a.
        zfpdef: ReadEJByReceiptNum(ReportFormat, StartNo, EndNo)
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (toDoc.length() == 0)
            toDoc = fromDoc;
        if (fromDoc.length() > 6)
            fromDoc = fromDoc.substring(0, 6);
        fromDoc = String.format("%6s", fromDoc).replace(" ", "0");
        if (toDoc.length() > 6)
            toDoc = toDoc.substring(0, 6);
        toDoc = String.format("%6s", toDoc).replace(" ", "0");
        String params = "J0;N;"+fromDoc+";"+toDoc;
        String resStr = cmdCustomRawRead(0x7C, params, 0, (byte)'@');
//            Charset charset = Charset.forName("Windows-1251");
//            String resStr = new String(resBytes, charset);
        Pattern p = Pattern.compile("^\u0002.+?[|]", Pattern.MULTILINE);
        Matcher m = p.matcher(resStr);
        resStr = m.replaceAll("");
        // Replace suffix
        p = Pattern.compile("..$", Pattern.MULTILINE);
        m = p.matcher(resStr);
        resStr = m.replaceAll("");
        response.put("EJ", resStr);
        return response;
     }
   
    @Override
    public LinkedHashMap<String, String> cmdReadDocInfo(String docNum) throws IOException {
        /*
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        err("Неподдържана команда! Грешка при четене на информация за КЛЕН!"); 
        response.put("EJ", "");
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdShortDocInfo(String docNum) throws IOException {
        /*
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        err("Неподдържана команда! Грешка при четене на информация за КЛЕН!"); 
        return response;
    }

    @Override
    public Date cmdLastFiscalCheckDate() throws IOException {
        /*
        TODO: from getQRCode
        */
        err("Неподдържана команда (cmdLastFiscalCheckDate)!"); 
        return null;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadPaymentMethods() throws IOException {
        /*
        2.5.5. Command: 64h / d – Read payment types
        input: n. a.
        output: <NamePayment0[10]> <;> <NamePayment1[10]> <;> <NamePayment2[10]> <;> <NamePayment3[10]> <;> <NamePayment4[10]> <;> <NamePayment5[10]> <;> <NamePayment6[10]> <;> <NamePayment7[10]> <;> <NamePayment8[10]> <;> <NamePayment9[10]> <;> <NamePayment10[10]> <;> <NamePayment11[10]> <;> <ExchangeRate[1..10]>
        FPR operation: Provides information about all programmed types of payment, currency name and currency exchange rate.
        Input data : n. a.
        Output data :
        NamePayment0
        10 symbols for payment name type 0
        NamePayment1
        10 symbols for payment name type 1
        NamePayment2
        10 symbols for payment name type 2
        NamePayment3
        10 symbols for payment name type 3
        NamePayment4
        10 symbols for payment name type 4
        NamePayment5
        10 symbols for payment name type 5
        NamePayment6
        10 symbols for payment name type 6
        NamePayment7
        10 symbols for payment name type 7
        NamePayment8
        10 symbols for payment name type 8
        NamePayment9
        10 symbols for payment name type 9
        NamePayment10
        10 symbols for payment name type 10
        NamePayment11
        10 symbols for payment name type 11
        ExchangeRate
        Up to 10 symbols for exchange rate of payment type 11 in format: ####.#####
        zfpdef: ReadPayments()
        
        2.5.6. Command: 64h / d – Read payment types, KL
        input: n. a.
        output: <NamePaym0[10]> <;> <NamePaym1[10]> <;> <NamePaym2[10]> <;> <NamePaym3[10]> <;> <NamePaym4[10]><;><ExRate[1..10]> <;> <CodePaym0[1]><;> <CodePaym1[1]><;> <CodePaym2[1]><;> <CodePaym3[1]> <;> <CodePaym4[1]>
        FPR operation: Provides information about all programmed types of payment. Command works for KL version 2 devices.
        Input data : n. a.
        Output data :
        NamePaym0
        10 symbols for payment name type 0
        NamePaym1
        10 symbols for payment name type 1
        NamePaym2
        10 symbols for payment name type 2
        NamePaym3
        10 symbols for payment name type 3
        NamePaym4
        10 symbols for payment name type 4
        ExRate
        Up to10 symbols for exchange rate of payment type 4 in format: ####.#####
        CodePaym0
        1 symbol for code of payment 0 = 0xFF (currency in cash)
        CodePaym1
        1 symbol for code of payment 1 (default value is '7')
        CodePaym2
        1 symbol for code of payment 2 (default value is '1')
        CodePaym3
        1 symbol for code of payment 3 (default value is '2')
        CodePaym4
        1 symbol for code of payment 4 = 0xFF (currency in cash)
        zfpdef: ReadPayments_Old()        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(0x64, "");
        if (res.contains(";")) {
            String[] parts = res.split(";");
            if (isOldDevice) {
                response.put("P_0", "'0' '"+parts[0]+"' НАП #:0");
                response.put("P_1", "'1' '"+((parts.length > 1)?parts[1]:"")+"' НАП #:"+((parts.length > 6)?parts[6]:"_"));
                response.put("P_2", "'2' '"+((parts.length > 2)?parts[2]:"")+"' НАП #:"+((parts.length > 7)?parts[7]:"_"));
                response.put("P_3", "'3' '"+((parts.length > 3)?parts[3]:"")+"' НАП #:"+((parts.length > 8)?parts[8]:"_"));
                response.put("P_4", "'4' '"+((parts.length > 4)?parts[4]:"")+"' НАП #:"+((parts.length > 9)?parts[9]:"_"));
            } else {
                response.put("P_0", "'0' '"+parts[0]+"' НАП #:0");
                response.put("P_1", "'1' '"+((parts.length > 1)?parts[1]:"")+"' НАП #:1");
                response.put("P_2", "'2' '"+((parts.length > 2)?parts[2]:"")+"' НАП #:2");
                response.put("P_3", "'3' '"+((parts.length > 3)?parts[3]:"")+"' НАП #:3");
                response.put("P_4", "'4' '"+((parts.length > 4)?parts[4]:"")+"' НАП #:4");
                response.put("P_5", "'5' '"+((parts.length > 5)?parts[5]:"")+"' НАП #:5");
                response.put("P_6", "'6' '"+((parts.length > 6)?parts[6]:"")+"' НАП #:6");
                response.put("P_7", "'7' '"+((parts.length > 7)?parts[7]:"")+"' НАП #:7");
                response.put("P_8", "'8' '"+((parts.length > 8)?parts[8]:"")+"' НАП #:8");
                response.put("P_9", "'9' '"+((parts.length > 9)?parts[9]:"")+"' НАП #:9");
                response.put("P_10", "'10' '"+((parts.length > 10)?parts[10]:"")+"' НАП #:10");
                response.put("P_11", "'11' '"+((parts.length > 11)?parts[11]:"")+"' НАП #:11");
            }
        }    
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadDepartments() throws IOException {
        /*
        2.5.9. Command: 67h / g – Read department registers
        input: <DepNum[2]>
        output: <DepNum[2]> <;> <DepName[20]> <;> <OptionVATClass[1]> <;> <Turnover[1..13]> <;> <SoldQuantity[1..13]> <;> <LastZReportNumber[1..5]> <;> <LastZReportDate “DD-MM-YYYY HH:MM”>
        FPR operation: Provides information for the programmed data, the turnover from the stated department number
        Input data :
        DepNum
        (Department Number) 2 symbols for department number in format: ##
        Output data :
        DepNum
        2 symbols for department number in format: ##
        DepName
        20 symbols for department name
        OptionVATClass
        1 character for VAT class:
        - 'А' – VAT Class 0
        - 'Б' – VAT Class 1
        - 'В' – VAT Class 2
        - 'Г' – VAT Class 3
        - 'Д' – VAT Class 4
        - 'Е' – VAT Class 5
        - 'Ж' – VAT Class 6
        - 'З' – VAT Class 7
        - ‘*’ - Forbidden
        Turnover
        Up to 13 symbols for accumulated turnover of the article
        SoldQuantity
        Up to 13 symbols for sold quantity of the department
        LastZReportNumber
        Up to 5 symbols for the number of last Z Report
        LastZReportDate
        16 symbols for date and hour on last Z Report in format “DD-MM-YYYY HH:MM”
        zfpdef: ReadDepartment(DepNum)        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        for (int i = 0; i < CONST_DEPTNUM;  i++ ) {
            try {
                String res = cmdCustom(0x67, String.format("%02d", i));
                if (res.contains(";")) {
                    String[] parts = res.split(";");
                    response.put(
                        "D_"+Integer.toString(i)
                       , 
                         parts[0]
                         +" Група:"+((parts.length > 2)?parts[2]:"")
                         +" Име:"+((parts.length > 1)?parts[1]:"")
                         +" Продажби:"+((parts.length > 4)?parts[4]:"")+"/"+((parts.length > 3)?parts[3]:"")
                    );
                }
            } catch (Exception Ex) {
                
            }
        }
        return response;
    }
    
    @Override
    public LinkedHashMap<String, String> cmdReadTaxGroups() throws IOException {
        /*
        2.5.3. Command: 62h / b – Read VAT rates
        input: n. a.
        output: <VATrate0[7]> <;> <VATrate1[7]> <;> <VATrate2[7]> <;> <VATrate3[7]> <;> <VATrate4[7]> <;> <VATrate5[7]> <;> <VATrate6[7]> <;> <VATrate7[7]>
        FPR operation: Provides information about the current VAT rates which are the last values stored into the FM.
        Input data : n. a.
        Output data :
        VATrate0
        Value of VAT rate А from 7 symbols in format ##.##%
        VATrate1
        Value of VAT rate Б from 7 symbols in format ##.##%
        VATrate2
        Value of VAT rate В from 7 symbols in format ##.##%
        VATrate3
        Value of VAT rate Г from 7 symbols in format ##.##%
        VATrate4
        Value of VAT rate Д from 7 symbols in format ##.##%
        VATrate5
        Value of VAT rate Е from 7 symbols in format ##.##%
        VATrate6
        Value of VAT rate Ж from 7 symbols in format ##.##%
        VATrate7
        Value of VAT rate З from 7 symbols in format ##.##%
        zfpdef: ReadVATrates()
        */
        LinkedHashMap<String, String> response = new LinkedHashMap(); 
        String res = cmdCustom(0x62, "");
        //00.00%;20.00%;20.00%;09.00%;-1.00%;-1.00%;-1.00%;-1.00%;
        if (res.length() > 0) {
            res = res.replace("%", "");
            String[] resLines = res.split(";");
            response.put("TaxA", reformatCurrency((resLines.length > 0)?resLines[0]:"0", 1));
            response.put("TaxB", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 1));
            response.put("TaxC", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 1));
            response.put("TaxD", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 1));
            response.put("TaxE", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 1));
            response.put("TaxF", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 1));
            response.put("TaxG", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 1));
            response.put("TaxH", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 1));
        } else {
            long errCode = -1;
            err("Грешка при получаване на информация за данъчните групи!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
