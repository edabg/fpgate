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
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import static org.eda.protocol.AbstractFiscalDevice.LOGGER;

/**
 * This class supports Tremol fiscal devices 
 * Corresponding to specification protocol_description_BG_1910211454.pdf
 * @author Dimitar Angelov
 */
public class DeviceTremolV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;

    protected int CONST_TEXTLENGTH=32;
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
        LOGGER.fine(res);
        if (res.substring(0, 1).equals("I")) {
            String tl = res.substring(1, 3);
            try {
                CONST_TEXTLENGTH = Integer.parseInt(tl);
            } catch (Exception e) {
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
    
    @Override
    public boolean isOpenNonFiscalCheck() {
      int byteNum = 2;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x01) == 0x01);
    }

    @Override
    public boolean isOpenFiscalCheck() {
      int byteNum = 2;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x020) == 0x20);
    }

    @Override
    public boolean isOpenFiscalCheckRev() {
        return isOpenFiscalCheck() && fiscalCheckRevOpened;
    }

    @Override
    public boolean isFiscalized() {
      int byteNum = 3;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x20) == 0x20);
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
        if (!opCode.matches("^$\\d\\d?$"))
            throw new FDException("Невалиден код на оператор!");
        if (opPasswd.length() > 6)
            throw new FDException("Максималната дължина на паролата е 6 символа!");
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
            recipient = recipient.replaceAll(";", ",");
            String buyer = customerData.buyer;
            if (buyer.length() > 16) {
                warn("Дължината на 'купувач' е по-голяма от 16 сивола и ще бъде съкратена!");
                buyer = buyer.substring(0, 16);
            }    
            buyer = buyer.replaceAll(";", ",");
            String address = customerData.address;
            if (address.length() > 30) {
                warn("Дължината на 'адрес' е по-голяма от 30 сивола и ще бъде съкратена!");
                address = address.substring(0, 30);
            }    
            address = address.replaceAll(";", ",");
            String UICType = "";
            switch (customerData.UICType) {
                case EGN : UICType = "1"; break;
                case FOREIGN : UICType = "2"; break;
                case NRA : UICType = "3"; break;
                case BULSTAT : 
                default :    
                    UICType = "0"; break;
            }
            params = opCode+";"+opPasswd;
            params = params + ";0;0";
            params = params + ";" + printMode;
            params = params + ";" + recipient;
            params = params + ";" + buyer;
            params = params + ";" + customerData.VATNumber;
            params = params + ";" + customerData.UIC;
            params = params + ";" + UICType;
            if (UNS.length() > 0)
                params = params + "$" + UNS;
        } else {
            params = opCode+";"+opPasswd;
            params = params + ";" + (receiptPrintFormat.equals(ReceiptPrintFormatType.DETAILED)?"1":"0");
            params = params + ";" + (receiptPrintVAT?"1":"0");
            params = params + ";" + printMode;
            if (UNS.length() > 0)
                params = params + "$" + UNS;
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
        int maxLen = 46;
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+text+")");
            text = text.substring(0, maxLen);
        }
        cmdCustom(54, text);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        26h (38) ОТВАРЯНЕ НА СЛУЖЕБЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt,ErrCode
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното
        приключване на деня до момента /4 байта/.
        ErrCode Код на грешката при неуспешно изпълнена команда /1 байт/.
        ФП извършва следните действия:
         Отпечатва се HEADER.
         Отпечатва се ЕИК на продавача.
         Връща се отговор, съдържащ Allreceipt.
        Командата не може да се изпълни, ако:
         Фискалната памет не е форматирана.
         Има отворен фискален бон.
         Вече е отворен служебен бон.
         Часовникът не е сверен.        
        */        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String res = cmdCustom(38, "");
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("ErrCode", rData[1]);
            if (!rData[1].equals("0")) {
                err("Грешка при отваряне на служебен бон ("+res+")!");
                throw new FDException(mErrors.toString());
            }
        } else {
            if (res.length() > 0) {
                response.put("AllReceipt", res);
            } else {
                err("Грешка при отваряне на служебен бон ("+res+")!");
                throw new FDException(mErrors.toString());
            }    
        }
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text, int font) throws IOException {
        /*
        2Ah (42) ПЕЧАТАНЕ НА СВОБОДЕН ТЕКСТ В СЛУЖЕБЕН БОН
        Област за данни: Text
        Отговор: Няма данни
        Text Текст до 46 символа. Символите след 46-я се отрязват.
        В началото и края на реда се отпечатва символът '#'.
        Ако е вдигнат S1.1, значи в момента не е отворен служебен бон и текста не е отпечатан.        
        */        
        int maxLen = 46;
        String params = "";
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
            text = text.substring(0, maxLen);
        }
        params = text;
        cmdCustom(42, params);
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws IOException {
        cmdPrintNonFiscalText(text, 0);
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws IOException {
        /*
        27h (39) ЗАТВАРЯНЕ НА СЛУЖЕБЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента /4 байта/.
        ФП извършва следните действия:
         Отпечатва се FOOTER.
         Отпечатва се поредния номер, датата и часа на документа
         Отпечатва се с широк печат “ СЛУЖЕБЕН БОН”.
         Връща се отговор, съдържащ Allreceipt.
         Ако е вдигнат S1.1 командата не е изпълнена защото в момента не е отворен служебен бон.
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(39, "");
        if (res.length() > 0) {
            response.put("AllReceipt", res);
        } else {    
            long errCode = -1;
            err("Грешка при затваряне на служебен бон!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public void cmdPaperFeed(int lineCount) throws IOException {
        /*
        2Ch (44) ПРИДВИЖВАНЕ НА ХАРТИЯТА.
        Област за данни: Lines
        Отговор: Няма данни
        Lines Броят на редовете, с които да бъде придвижена хартията. 
        Трябва да бъде положително число не по-голямо от 99 /1 или 2 байта/. Ако параметър липсва, подразбира се 1 ред.
        */        
        cmdCustom(44, Integer.toString(Integer.max(1, Integer.min(lineCount, 99))));
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        31h (49) РЕГИСТРИРАНЕ (ПРОДАЖБА) НА СТОКА
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc\;Abs]
                     или
                         [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc\;Abs]
        Отговор: Няма данни
        L1 Текст до 30 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 30 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 60 включително. Продажбата се причислява към данъчната група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000 Дължина до 8 значещи цифри (не повече от 3 след десетичната точка).Произведението Price*Qwan се закръгля от принтера до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се до 2 десетични знака
        Допустим е само един от аргументите Perc или Abs.
        ФП извършва следните действия:
         Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на департамента.
         Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено количество, информацията за него също се отпечатва.
         Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване се установяват съответните битове от статус полето.
         Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на принтера. Стойностите за целия ден се отпечатват при дневния финансов отчет.
         Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива, се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
         Не е отворен фискален бон.
         Вече са направени максималния брой продажби за един бон (512).
         Командата (35h) е изпълнена успешно.
         Сумата по някоя от данъчните групи става отрицателна.
         Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
         КЛЕН е пълна.        
        
        */        
        int maxLen = 30;
        String params = "";
        String L1 = "";
        String L2 = "";
        String[] textLines = sellText.split("\n");
        if (textLines.length > 1) {
            if (textLines[0].length() > maxLen) 
                L1 = textLines[0].substring(0, maxLen);
            else
                L1 = textLines[0];
            if (textLines[1].length() > maxLen) 
                L2 = textLines[1].substring(0, maxLen);
            else
                L2 = textLines[1];
        } else {
            if (textLines[0].length() > maxLen) {
                L1 = textLines[0].substring(0, maxLen);
                L2 = textLines[0].substring(maxLen);
                if (L2.length() > maxLen) {
                    L2 = L2.substring(0, maxLen);
                }
            } else
                L1 = textLines[0];
        }
        params = L1;
        if (L2.length() > 0)
            params += "\n" + L2;
        if (taxGroup.length() > 1)
            taxGroup = taxGroup.substring(0,1);
        params += "\t" + taxGroup+ String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001)
            params += "*"+String.format(Locale.ROOT, "%.3f", quantity);

        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
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
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ";"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        }
        cmdCustom(49, params);
    }

    @Override
    public void cmdSellDept(String sellText, String dept, double price, double quantity, String unit, String discount) throws IOException {
        /*
        31h (49) РЕГИСТРИРАНЕ (ПРОДАЖБА) НА СТОКА
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc\;Abs]
                     или
                         [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc\;Abs]
        Отговор: Няма данни
        L1 Текст до 30 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 30 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 60 включително. Продажбата се причислява към данъчната група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000 Дължина до 8 значещи цифри (не повече от 3 след десетичната точка).Произведението Price*Qwan се закръгля от принтера до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се до 2 десетични знака
        Допустим е само един от аргументите Perc или Abs.
        ФП извършва следните действия:
         Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на департамента.
         Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено количество, информацията за него също се отпечатва.
         Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване се установяват съответните битове от статус полето.
         Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на принтера. Стойностите за целия ден се отпечатват при дневния финансов отчет.
         Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива, се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
         Не е отворен фискален бон.
         Вече са направени максималния брой продажби за един бон (512).
         Командата (35h) е изпълнена успешно.
         Сумата по някоя от данъчните групи става отрицателна.
         Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
         КЛЕН е пълна.
        */        
        int maxLen = 30;
        String params = "";
        String L1 = "";
        String L2 = "";
        String[] textLines = sellText.split("\n");
        if (textLines.length > 1) {
            if (textLines[0].length() > maxLen) 
                L1 = textLines[0].substring(0, maxLen);
            else
                L1 = textLines[0];
            if (textLines[1].length() > maxLen) 
                L2 = textLines[1].substring(0, maxLen);
            else
                L2 = textLines[1];
        } else {
            if (textLines[0].length() > maxLen) {
                L1 = textLines[0].substring(0, maxLen);
                L2 = textLines[0].substring(maxLen);
                if (L2.length() > maxLen) {
                    L2 = L2.substring(0, maxLen);
                }
            } else
                L1 = textLines[0];
        }
        params = L1;
        if (L2.length() > 0)
            params += "\n" + L2;
        if (dept.length() > 2)
            dept = dept.substring(0,2);
        params += "\t" + dept + "\t" + String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001)
            params += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
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
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ";"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        }
        cmdCustom(49, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, String discount) throws IOException {
       /*
        33h (51) МЕЖДИННА СУМА        
        Област за данни: <Print><Display>[,Perc|;Abs]
        Отговор: SubTotal,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH
        Print Един байт, който ако е ‘1’ стойността на под сумата ще се отпечата.
        Display Един байт, който ако е ‘1’ стойността на под сумата ще се покаже на дисплея.
        Perc Незадължителен параметър, който показва стойността в проценти на отстъпката или
        надбавката върху натрупаната до момента сума.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума (до 8 значещи цифри). Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        SubTotal Сумата до момента за текущия фискален бон /до 10 байта/
        TaxA Сумата по данъчна група А /до 10 байта/
        TaxB Сумата по данъчна група Б /до 10 байта/
        TaxC Сумата по данъчна група В /до 10 байта/
        TaxD Сумата по данъчна група Г /до 10 байта/
        TaxE Сумата по данъчна група Д /до 10 байта/
        TaxF Сумата по данъчна група Е /до 10 байта/
        TaxG Сумата по данъчна група Ж /до 10 байта/
        TaxH Сумата по данъчна група З /до 10 байта/
        Изчислява се сума на всички продажби регистрирани във фискалния бон до момента. 
        По желание сумата може да бъде отпечатана и/или показана на дисплея. 
        Към PC се връща изчислената сума и натрупаните до момента суми за всяка данъчна група. 
        Ако е посочена надбавка или отстъпка, тя се отпечатва на отделен ред и натрупаните суми по данъчни групи се коригират съответно.        
       */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = (toPrint?"1":"0")+(toDisplay?"1":"0");
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
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
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (dd != 0) {
                    params += ";"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        }
        String res = cmdCustom(51, params);
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            response.put("SubTotal", reformatCurrency(resLines[0], 100));
            response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
            response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
            response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
            response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
            response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
            response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
            response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
            response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
        } else {
            err("Грешка при междинна сума във фискален бон!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        /*
        35h (53) ИЗЧИСЛЯВАНЕ НА СБОР (ТОТАЛ)
        Област за данни: [<Line1>][<Lf><Line2>]<Tab>[[<PaidMode>]<[Sign]Amount>]
        Отговор: <PaidCode><Amount>
        Line1 Текст до 36 байта съдържащ първия ред
        Lf Един байт със съдържание 0Ah
        Line2 Текст до 36 байта съдържащ втория ред
        Tab Един байт със съдържание 09h
        PaidMode Незадължителен код, указващ начина на плащане. Може да има следните стойности:
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ – „С чек",;
        ‘C’ – Сима по Талони "Талони",
        ‘D’ - Сума по външни талони "външни талони"
        ‘I’ - Сума по амбалаж "амбалаж",
        ‘J’ - Сума по вътрешно обслужване "вътрешно обслужване",
        ‘K’ - Сума по повреди "повреди",
        ‘L’ - Сума по кредитни/дебитни карти "кредитни/дебитни карти",
        ‘M’ - Сума по банкови трансфери "банкови трансфери"
        ‘Q’ - Плащане НЗОК "НЗОК",
        ‘R’ - Резерв 2 "Резерв 2"
        В зависимост от кода сумите се натрупват в различни регистри и могат да бъдат
        получени в дневния отчет.
        Sign Един байт със стойност ‘+’, указващ знака на Amount (сумата, която се плаща).
        Amount Сумата, която се плаща /до 10 значещи цифри/.
        PaidCode Един байт - резултат от изпълнението на командата.
        ‘F’ Грешка.
        ‘E’ Изчислената под сума е отрицателна. Плащане не се извършва и Amount ще
        съдържа отрицателната под сума.
        ‘D’ Ако платената сума е по-малка от сумата на бона. Остатъкът за доплащане се
        връща в Amount.
        ‘R’ Ако платената сума е по-голяма от сбора на бележката. Ще се отпечата
        съобщение “РЕСТО” и рестото се връща в Amount.
        ‘I’ Сумата по някоя данъчна група е бил отрицателен и затова се е получила
        грешка. В Amount се връща текущата под сума.
        Amount До 9 цифри със знак. Зависи от PaidCode.
        Тази команда предизвиква изчисляването на сумите от фискалния бон, отпечатването на сумата със специален шрифт и показването й на дисплей. 
        Възможно е отпечатването на допълнителен текст. При успешно изпълнение на командата се генерира импулс за отваряне на чекмедже, 
        ако това е разрешено с подкоманда ‘X’ на команда 43. 
        Ако след символа <Tab> няма повече данни, то принтерът втоматично плаща цялата налична сума в брой.
        Командата няма да бъде изпълнена успешно, ако:
         Не е отворен фискален бон.
         Натрупаната сума е отрицателна.
         Ако някоя от натрупаните суми по данъчни групи е отрицателна.
        След успешното изпълнение на командата, фискалният принтер няма да изпълнява командите 49 и 51 в рамките на отворения бон,
        обаче може да изпълнява още команда 53.
        Забележка: Кодове на грешка ‘E’ и ‘I’ никога няма да се получат в българската версия на принтера, 
                   защото команди 49 и 52 (Регистриране на продажба) няма да допуснат отрицателни суми.        
        
        */        
        int maxLen = 36;
        String params = "";
        String L1 = "";
        String L2 = "";
        String[] textLines = totalText.split("\n");
        if (textLines.length > 1) {
            if (textLines[0].length() > maxLen) 
                L1 = textLines[0].substring(0, maxLen);
            else
                L1 = textLines[0];
            if (textLines[1].length() > maxLen) 
                L2 = textLines[1].substring(0, maxLen);
            else
                L2 = textLines[1];
        } else {
            if (textLines[0].length() > maxLen) {
                L1 = textLines[0].substring(0, maxLen);
                L2 = textLines[0].substring(maxLen);
                if (L2.length() > maxLen) {
                    L2 = L2.substring(0, maxLen);
                }
            } else
                L1 = textLines[0];
        }
        params = L1;
        if (L2.length() > 0)
            params += "\n" + L2;
        params += "\t";
        // Check method of payment
        if (paymentType.length() > 1)
            paymentType = paymentType.substring(0, 1);
        String[] validPaymentTypes = {"","P","N","C","D","I","J","K","L","M","Q","R"};
        if (!Arrays.asList(validPaymentTypes).contains(paymentType))
            throw new FDException(-1L, "Невалиден начин на плащане ("+paymentType+")!");
        if (paymentType.length() > 0)
            params += paymentType;
        if (abs(amount) >= 0.01)
            params += String.format(Locale.ROOT, "%.2f", amount);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(53, params);
        if (res.length() > 0) {
            // Payment in cache or other w/o pinpad connection
            String paidCode = res.substring(0,1);
            if (paidCode.equals("F")) {
                throw new FDException(-1L, "Грешка при обща сума и плащане!"+res);
            } else {
                String rAmount = reformatCurrency(res.substring(1), 100);
                double rAmountD = 0;
                try {
                    rAmountD = Double.parseDouble(rAmount);
                } catch (Exception ex) {
                }
                if (paidCode.equals("E")) {
                    // Изчислената междинна сума е отрицателна. Плащане не се извършва и Amount ще съдържа отрицателната междинна сума.
                    err("Изчислената междинна сума е отрицателна. Плащане не се извършва и Amount ще съдържа отрицателната междинна сума.");
                    response.put("PaidCode", "E");
                    response.put("Amount", rAmount);
                } else if (paidCode.equals("D")) {
                    // Ако платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.
                    if (Math.abs(rAmountD) >= 0.01)
                        warn("Платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.");
                    response.put("PaidCode", "D");
                    response.put("Amount", rAmount);
                } else if (paidCode.equals("R")) {
                    // Ако платената сума е по-голяма от сбора на бележката. Ще се отпечата съобщение “РЕСТО” и рестото се връща в Amount.
                    debug("Платената сума е по-голяма от сумата на бона. Рестото се връща в Amount.");
                    response.put("PaidCode", "R");
                    response.put("Amount", rAmount);
                } else if (paidCode.equals("I")) {
                    // Сумата по някоя данъчна група е бил отрицателен и затова се е получила грешка. В Amount се връща текущата междинна сума.
                    err("Сумата по някоя данъчна група е бил отрицателен и затова се е получила грешка. В Amount се връща текущата междинна сума.");
                    response.put("PaidCode", "I");
                    response.put("Amount", rAmount);
                } else {
                    // Неочакван отговор!
                    warn("Неочакван отговор ("+res+")!");
                    response.put("PaidCode", paidCode);
                    response.put("Amount", rAmount);
                }
            }
        } else {
//            long errCode = -1;
            // Ако е сторно бон не връща информация
            if (!isOpenFiscalCheckRev())
                warn("Липсва отговор при команда за обща сума и плащане на фискален бон!");
//            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        90h (144) ОТВАРЯНЕ НА ФИСКАЛЕН (КЛИЕНТСКИ) БОН по Н-18/2018
        Област за данни: <OperName>,<UNP>[,Type[ ,<FMIN>,<Reason>,<num>[,<time>[,<inv>]]]]
        Отговор: Allreceipt, FiscReceipt
        Type = I – разширен фискален бон (фактура); 
        Type = S – сторно/кредитно известие.
        варианти:
        * <OperName>,<UNP>
        * <OperName>,<UNP>,I
        * <OperName>,<UNP>,S,<FMIN>,<Reason>,<num>[,<time>[,<inv>]]
        Когато принтера е част от ЕСФП система прамаетър <UNP> не се подава.
        , където: * OperName – име на оператора * UNP - уникален номер на продажбата /EDXXXXXX-YYYY-NNNNNNN/ Когато се използва принтер закачен към ЕСФП на мястото на UNP се подава само номера на оператора YYYY * Type - Един символ със стойност:
        "I" - отваряне на разширен фискален бон (фактура). 
            Автоматично след заглавната част (HEADER-а) се отпечатва номера на фактурата, а след първата команда за плащане се разпечатват сумите по данъчни групи. 
            След плащането трябва да се отпечата информация за купувача с команда 57 (39h). 
        "S" - отваряне на сторно/кредитно известие. 
            * FMIN - номера на ФП /44ХХХХХХ/ на устройството, от което е издадена фактурата, по повод на която е съставен сторно документът. 
            * Reason – причината за сторно операцията. Един символ със стойност: 
                "O" - операторска грешка – само за бележки издадени със същата ФП. 
                "R" - връщане/рекламация 
                "T" - намаление на данъчната основа 
            * num - номер на фискалния бон, който се сторнира. 
            * time - дата и час на бележката, която се сторнира, във формат ГГГГ-MM-ДДTчч:мм:сс /2018-10-31T15:58:43/
            * inv - номер на разширеният фискален бон (фактурата) - в случай, че се сторнира фактура.
        Allreceipt - Броят на всички издадени бонове (фискални и служебни) от последното
        приключване на деня до момента. /4 байта/
        FiscReceipt - Броят на всички издадени фискални бонове от последното приключване на деня
        до момента. /4 байта/
        Командата няма да бъде изпълнена успешно, ако:
        - Има отворен фискален или служебен бон.
        - Фискалната памет е пълна.
        - Фискалната памет е повредена.
        - Липсва код или парола на оператор, или номер на касово място.
        - HEADER-а съдържа по-малко от 2 реда.
        - Не е зададен ЕИК.
        - Не е вярна операторската парола.        
        */        
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        fiscalCheckRevOpened = true;
        
        String params;
        if (UNS.length() == 0) {
            throw new FDException(-1L, "Липсва Уникален Номер на Продажбата!");
        } 
        String[] validRevTypes = {"O","R","T"};
        if (!Arrays.asList(validRevTypes).contains(RevType))
            throw new FDException("Невалиден тип сторно ("+RevType+")!");
        
        if (!RevDocNum.matches("^\\d{1,7}$")) 
            throw new FDException("Невалиден номер на документа, по който е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999!");

        if (!RevFMNum.matches("^\\d{8,8}$"))
            throw new FDException("Невалиден номер на фискалната памет '"+RevFMNum+"' трябва да е число записано с 8 знака!");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        //<OperName>,<UNP>,S,<FMIN>,<Reason>,<num>[,<time>[,<inv>]]
        params = "Оператор: "+opCode+","+UNS+",S"+","+RevFMNum+","+RevType+","+RevDocNum+","+dateFormat.format(RevDateTime);
        if (invoice) {
            if (!RevInvNum.matches("^\\d{1,10}$")) 
                throw new FDException("Невалиден номер на фактура, по която е сторно операцията '"+RevInvNum+"' трябва да е число 1 - 9999999999!");
            params = params + ","+RevInvNum;
        }
        
        String res = cmdCustom(144, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("StrReceipt", rData[1]);
            if (RevReason.length() > 0) {
                try {
                    cmdPrintFiscalText(RevReason);
                } catch (Exception ex) {
                    err("Грешка при печат на фискален текст с причнина за сторно ("+ex.getMessage()+")!");
                }
            }
        } else {    
            err("Грешка при отваряне на сторно фискален бон ("+res+")!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws IOException {
        /*
        46h (70) СЛУЖЕБЕН ВНОС И ИЗНОС НА ПАРИ
        Област за данни: [<Amount>]
        Отговор: ExitCode,CashSum,ServIn,ServOut
        Amount Сумата за регистриране (до 10 значещи цифри). В зависимост от знака на числото тя се интерпретира като внос или износ.
        ExitCode ‘P’ Заявката е изпълнена. Ако заявената сума е ненулева, принтерът отпечатва
        служебен бон за регистриране на операцията.
        ‘F’ Заявката е отказана. Това става, ако:
         Касовата наличност е по-малка от заявения служебен износ.
         Има отворен фискален или служебен бон.
        CashSum Касова наличност. Освен от тази команда сумата нараства и при всяко плащане в брой.
        ServIn Сумата от всички команди “Служебен внос”.
        ServOut Сумата от всички команди “Служебен износ”.
        Променя съдържанието на регистъра за касова наличност. 
        В зависимост от знака на посочената сума тя се натрупва в регистъра за служебен внос или износ. 
        Информацията не се записва във фискалната памет и е достъпна до момента на приключване на деня. 
        Разпечатва се при команда 69 (45h) и при предизвикване на дневен финансов отчет без нулиране от самия принтер. 
        При успешно изпълнение на командата с параметър се генерира импулс за отваряне на чекмедже, ако това е разрешено с подкоманда ‘X’ на команда 43.        
        */  
        String params = "";
        params = String.format(Locale.ROOT, "%.2f", ioSum);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(70, params);
        // P,0.00,0.00,0.00
        if (res.contains(",")) {
            String[] rData = res.split(",");
            if (rData[0].equals("P")) {
                // Заявката е изпълнена
                response.put("CashSum", reformatCurrency((rData.length > 1)?rData[1]:"0", 0));
                response.put("CashIn", reformatCurrency((rData.length > 2)?rData[2]:"0", 0));
                response.put("CashOut", reformatCurrency((rData.length > 3)?rData[3]:"0", 0));
            } else {
                err("Грешка при Служебен внос/износ ("+res+")!");
                throw new FDException(mErrors.toString());
            }
        } else {    
            err("Грешка при Служебен внос/износ ("+res+")!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public void cmdSetOperator(String opCode, String opPasswd, String name) throws IOException {
        /*
        66h (102) ЗАДАВАНЕ НА ИМЕ НА ОПЕРАТОР
        Област за данни: <OpCode>,<Pwd>,<OpName>
        Отговор: Няма данни
        OpCode Код на оператор. От 1 до 16.
        Pwd Парола (4 до 8 цифри).
        OpName Име на оператор (до 24 символа).
        Задава едно от шестнайсетте имена на оператори. Номерът и името на оператора се отпечатва в началото на всеки фискален (клиентски) бон. При три грешни пароли принтерът блокира и трябва да се изключи и включи за продължаване на работата.
        След инициализация или нулиране на оперативната памет и шестнайсетте имена на оператори са празни.        
        
        66h (102) ЗАДАВАНЕ НА ИМЕ НА ОПЕРАТОР
        Област за данни: <OpCode>,<Pwd>,<OpName>
        Отговор: Няма данни
        OpCode Код на оператор. От 1 до 30.
        Pwd Парола (1 до 8 цифри).
        OpName Име на оператор (до 10 символа).
        Задава едно от тридесетте имена на оператори. Номерът и името на оператора се отпечатва в началото на всеки фискален
        (клиентски) бон. След инициализация или нулиране на оперативната памет и имената на операторите са “ОПЕРАТОРХХ”. Където
        ХХ е номера на оператора.
        */
        int maxLen = 24;
        if (name.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(name.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+name+")");
            name = name.substring(0, maxLen);
        }
        String params = opCode+","+opPasswd+","+name;
        cmdCustom(102, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        Няма функция за прочитане на последния фискален запис (дневен отчет),
        затова ще се използва следната за да има нещо :-)
        41h (65) ИНФОРМАЦИЯ ЗА СУМИТЕ ПО ДАНЪЧНИ ГРУПИ ЗА ДЕНЯ
        Област за данни: [Option]
        Отговор: TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH
        Option Определя каква информация да се върне:
        ‘0’ - Общ оборот.
        ‘1’ – Натрупан ДДС.
        Ако параметърът липсва, подразбира се ‘0’.
        TaxX Сумите по всяка данъчна група ‘А’, ‘Б’, ‘В’, ... - 12 байта със знак.
        Връщат се сумите по данъчни групи от последното приключване на деня до момента на получаване на командата.        
        */
        warn("Липсва функция за прочитане на последния фискален запис! Ще се върнат натрупаните суми за деня!");
        String res = cmdCustom(65, "0");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.contains(",")) {
            String[] resLines = res.split(",");
            String DocNum = "0000000"; // Няма номер на Z отчет документ номер
            response.put("DocNum", DocNum);
            String resDate = "";
            response.put("DocDate", resDate); // Няма дата
            response.put("TaxA", reformatCurrency((resLines.length > 0)?resLines[0]:"0", 100));
            response.put("TaxB", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
            response.put("TaxC", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
            response.put("TaxD", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
            response.put("TaxE", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
            response.put("TaxF", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
            response.put("TaxG", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
            response.put("TaxH", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
        } else {
            err("Грешка при четене на информация за натрупаните суми за деня!");
            throw new FDException(mErrors.toString());
        }

        return response;
    }

    
    /**
     * Print daily report
     * @param option '0' for Z-report, '2' for X-report
     * @param subOption '' default, 'D' - by departments
     * @return Sums in report
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdReportDaily(String option, String subOption) throws IOException {
        /*
        45h (69) ДНЕВЕН ФИНАНСОВ ОТЧЕТ
        Област за данни: [<Option>[N]
        Отговор: Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        Option Незадължителен параметър, управляващ вида на генерирания отчет:
        ‘0’ Отпечатва се Z-отчет. Разпечатката завършва с надпис “ФИСКАЛЕН БОН”.
        ‘2’ Прави се дневен финансов отчет без нулиране (т. е. не се извършва запис във фискалната памет и нулиране на регистрите). 
            Разпечатката завършва с лого “СЛУЖЕБЕН БОН”.
        N Наличието на този символ в края на данните забранява изчистването на натрупаните
        данни по оператори при отчет с нулиране.
        Closure Номер на фискалния запис - 4 байта.
        FM_Total Сумата от всички продажби без ДДС - 12 байта със знак
        TotX Сумите по всяка от данъчните групи ‘А’, ‘Б’, ‘В’, … - 12 байта със знак.
        Дневен отчет без нулиране може да се предизвика и чрез задържането на бутон <FEED> при включване на принтера до третия звуков сигнал.
        
        75h (117) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ПЕЧАТ НА ДАННИ ПО ДЕПАРТАМЕНТИ
        Област за данни: [<Option>[N]
        Отговор: Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        Option Незадължителен параметър, управляващ вида на генерирания отчет:
        ‘0’ Отпечатва се Z-отчет. Разпечатката завършва с надпис “ФИСКАЛЕН
        БОН”.
        ‘2’ Прави се дневен финансов отчет без нулиране
        Командата е идентична с 69 (45h) с единствена разлика, че в началото на дневния отчет се отпечатват и департаментите, за които има продажби за деня.        
        */
        if (!option.equals("0") && !option.equals("2"))
            throw new FDException("Невалиден тип за дневен отчет! 0 => Z отчет, 2 => X отчет");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (subOption.equals("D")) { // By Departments
            String res = cmdCustom(117, option.equals("0")?"0":"2");
            if (res.length() > 0) {
                String[] resLines = res.split(",");
                response.put("Closure", resLines[0]);
                response.put("Total", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
                response.put("TaxA", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
                response.put("TaxB", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
                response.put("TaxC", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
                response.put("TaxD", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
                response.put("TaxE", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
                response.put("TaxF", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
                response.put("TaxG", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
                response.put("TaxH", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
            } else {
                long errCode = -1;
                err("Грешка при печат на дневен отчет!");
                throw new FDException(errCode, mErrors.toString());
            }
        } else {
            String res = cmdCustom(69, option);
            if (res.length() > 0) {
                String[] resLines = res.split(",");
                response.put("Closure", resLines[0]);
                response.put("Total", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
                response.put("TaxA", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
                response.put("TaxB", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
                response.put("TaxC", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
                response.put("TaxD", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
                response.put("TaxE", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
                response.put("TaxF", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
                response.put("TaxG", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
                response.put("TaxH", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
            } else {
                long errCode = -1;
                err("Грешка при печат на дневен отчет!");
                throw new FDException(errCode, mErrors.toString());
            }
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws IOException {
        /*
        5Ah (90) ЧЕТЕНЕ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: <Calc>
        Отговор: <Model of device>,<Type of device>,<EJ type> <FwRev><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        Calc Ако е ‘1’ се изчислява контролна сума на кодовата памет (фърмуера), в противен
        случай се връща ‘FFFF’. 1 байт.
        Model of device модел на устройството.
        Type of device тип на устройството.
        EJ type Тип на електронната контролна летна 2 байта.
        <Model of device>,<Type of device>,<EJ type> формират името на устойството
        FwRev Версията на програмното осигуряване. 13 байта.
        Sp Интервал. 1 байт.
        FwDate Датата на програмното осигуряване . 7 байта.
        Sp Интервал. 1 байт.
        FwTime Час на програмното осигуряване HH:MM. 5 байта.
        Chk Контролна сума на EPROM. 4 байта стринг в шестнайсетичен вид. Например, ако
        контролната сума е 214A, то тя ще се предаде 32h,31h,34h,41h.
        Sw Ключетата от Sw1 до Sw2. 2 байта стринг с ‘0’ или ‘1’
        Ser Индивидуален номер на устройството - 8 байта.
        FМ Номер на фискалния модул – 8 байта.        
        */
        // Request diagnostic info
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(90, "1");
        // A3,ONLINE_BG,KL5301.1811.0.3 15Nov18 12:40,FFFF,00,ED327823,44327823
        response.put("RawInfo", res);
        String[] commaParts = res.split(",");
        response.put("ModelName", commaParts[0]+((commaParts.length > 1)?"/"+commaParts[1]:""));
        response.put("FWRev", "");
        response.put("FWRevDT", "");
        response.put("Switches", "");
        response.put("SerialNum", "");
        response.put("FMNum", "");
        response.put("CheckSum", "");
        
        if (commaParts.length > 2) {
            String[] spaceParts = commaParts[2].split("\\s");
            response.put("FWRev", spaceParts[0]);
            if (spaceParts.length > 1)
                response.put("FWRevDT", spaceParts[1]);
            if (spaceParts.length > 2)
                response.put("FWRevDT", response.get("FWRevDT")+" "+spaceParts[2]);
        }
        if (commaParts.length > 2) {
            response.put("CheckSum", commaParts[3]);
        }
        if (commaParts.length > 4) 
            response.put("Switches", commaParts[4]);
        if (commaParts.length > 5) 
            response.put("SerialNum", commaParts[5]);
        if (commaParts.length > 6) 
            response.put("FMNum", commaParts[6]);
        return response;
    }


    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        47h (71) ПЕЧАТ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: Няма данни
        Отговор: Няма данни
        Тази команда отпечатва служебен бон с диагностична информация.
        Бонът съдържа следното:
         Датата и версията на програмното осигуряване.
         Контролната сума на фърмуера.
         Скоростта на предаване на серийния порт.
         Положението на конфигурационните ключета и името на страната.
         Аварийното време при отпадане на захранването.
         Номера, датата и часа на последното нулиране на RAM (ако има такова).
         Текущата температура на печатащата глава.
         Общия брой полета във фискалната памет и броя на свободните.
         Текущата дата и час.
        Командата няма да се изпълни при отворен бон и липса на хартия. 
        Може да се предизвика и чрез задържането на бутон <FEED> при включване на принтера до първия звуков сигнал.        
        */
        
        String res = cmdCustom(71, ""); // Full
    }

    @Override
    public String cmdLastDocNum() throws IOException {
        /*
        71h (113) ПОЛУЧАВАНЕ НОМЕРА НА ПОСЛЕДНИЯ ОТПЕЧАТАН ДОКУМЕНТ
        Област за данни: Няма данни
        Отговор: DocNum
        DocNum Номер на последния издаден документ (7 цифри).
        */
        String res = cmdCustom(113, ""); // Full
        return res;
    }

    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws IOException {
        /*
        4Ah (74) ПОЛУЧАВАНЕ НА СТАТУСИТЕ
        Област за данни: [Option]
        Отговор: <S0><S1><S2><S3><S4><S5>
        Option Един байт със следните значения:
        W: Първо чака да се отпечатат всички буфери на принтера.
        X: Не изчаква принтера.
        Sn Статус байт N.
        
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
        String res = cmdCustom(0x74, "X");
        byte[] bytes = res.getBytes();
        for (int i = 0; i < 7; i++) {
            byte b = 0;
            if (i < bytes.length)
                b = bytes[i];
            response.put("S"+Integer.toString(i), Integer.toBinaryString(b & 0xFF));
        }
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
        res = cmdCustom(0x66, "");
        //output: <ExternalDisplay[1]> <;> <StatPRN[4]> <;> <FlagServiceJumper[1]>
        String[] commaParts = res.split(";");
        response.put("ExternalDisplay", commaParts[0]);
        if (commaParts.length > 1) 
            response.put("StatPRN", commaParts[1]);
        if (commaParts.length > 2) 
            response.put("FlagServiceJumper", commaParts[2]);
        */
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
ERROR 1001 
        Usage: sklen from to [pattern [format]]
        pattern - [B][mask[submask]]    if B 'from' and 'to' are the numbers of the Z reports
            mask -XY X: 0,Z,F,S Y:Hex 
               X= 0-none, Z- Z reports, F and S - Z and Change reports
               Y= 0-none, 1-Fiscal 2-Comment 4-Copy 8-X reports
            submask:Hex= 0-none 1-Serv.IN 2-Serv. OUT 4-INVOICE 8-STORNO
        format= 0-teext 1-SD records 2-print 3-NRA xml 4-CSV
        
        
        6Dh (109) ПЕЧАТ НА ДУБЛИРАЩ БОН
        Област за данни: <Count>
        Отговор: Няма данни
        Count Брой дублиращи бонове (приема се само стойност 1!).
        Предизвиква отпечатването на копие на последния затворен фискален бон с продажби. 
        Копието се маркира като СЛУЖЕБЕН БОН и веднага след HEADER-а се отпечатва ред с удебелен шрифт “ДУБЛИКАТ”. 
        При повторен опит командата ще откаже да печати. Отпечатването на дублиращ бон е невъзможно и ако броят редове в бона е по-голям от 1000.        
        */
        //TODO: Check on fiscalized device
        cmdCustom(109, "1");
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
        Използва се Raw команда 
        KLEN 101 101 ще отпечати КЛЕН от номер до номер.
        */
        //TODO: Check on fiscalized device
        String command = "KLEN "+docNum+" "+docNum;
//        cmdCustomRaw(command);
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        /*
        5Eh (94) ОТПЕЧАТВАНЕ НА ФИСКАЛНАТА ПАМЕТ ПО ДАТА НА ФИСКАЛЕН ЗАПИС
        Област за данни: [<SHA1>][<SkipZ>]<Start>[,<End>]
        Отговор: Няма данни
        SHA1 Опционален аргумент – един байт със стойност '#'. Ако присъства, за всеки Z-отчет се
        отпечатва и контролната сума по алгоритъм SHA-1.
        SkipZ Опционален аргумент – един байт със стойност '*'. Ако присъства, за всеки Z-отчет се
        отпечатват само данъчните ставки, за които натрупаните суми за деня са ненулеви. Такъв отчет е нестандартен и може да се използва само за вътрешни справки на обекта!
        Start Началната дата на фискален запис. 6 байта (DDMMYY).
        End Крайна дата на фискален запис. 6 байта (DDMMYY).
        Тази команда отпечатва пълен отчет на фискалната памет за периода между две дати.
        Ако вторият параметър липсва, командата генерира месечен или годишен отчет. Синтаксисът в този случай е:
        Start Месец – 4 байта (MMYY) за месечен отчет.
        Start Година – 2 байта (YY) за годишен отчет.

        4Fh (79) НАТРУПАНИ СУМИ ОТ ФИСКАЛНАТА ПАМЕТ ЗА ДАДЕН ПЕРИОД
        Област за данни: <Start>[,<End>]
        Отговор: Няма данни
        Start Начална дата - 6 байта (DDMMYY)
        End Крайна дата - 6 байта (DDMMYY)
        Командата води до изчисляване и отпечатване на съкратен отчет на фискалната памет.
        Ако вторият параметър липсва, командата генерира месечен или годишен отчет. Синтаксисът в този случай е:
        Start Месец – 4 байта (MMYY) за месечен отчет.
        Start Година – 2 байта (YY) за годишен отчет.
        */
        //TODO: Check on fiscalized device
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        String params = dtf.format(startDate)+((endDate != null)?","+dtf.format(endDate):"");
        cmdCustom(detailed?94:79, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws IOException {
        /*
        67h (103) ИНФОРМАЦИЯ ЗА ТЕКУЩИЯ БОН
        Област за данни: Няма данни
        Отговор: CanVd,TaxA,TaxB,TaxC,TaxD, TaxE,TaxF,TaxG,TaxH,Inv,InvNum
        CanVd: Възможно ли е връщане (продажба с отрицателен знак) [0/1]
        TaxA: Натрупана сума по данъчна група А
        TaxB: Натрупана сума по данъчна група Б
        TaxC: Натрупана сума по данъчна група В
        TaxD: Натрупана сума по данъчна група Г
        TaxE: Натрупана сума по данъчна група Д
        TaxF: Натрупана сума по данъчна група Е
        TaxG: Натрупана сума по данъчна група Ж
        TaxH: Натрупана сума по данъчна група З
        Inv: Отворена ли е разширена клиентска бележка
        InvNmb: Номер на следващата фактура /10 цифри/.
        Дава информация за натрупаните суми по данъчни групи и дали е възможно връщане на регистрирани стоки.        
        
        4Ch (76) СТАТУС НА ФИСКАЛНАТА ТРАНЗАКЦИЯ
        Област за данни: [Option]
        Отговор: Open,Items,Amount[,Tender]
        Option = ‘T’. Ако този параметър е указан командата ще върне информацията относно
        текущото състояние на дължимата до момента сметка от клиента.
        Open Един байт, който е ‘1’ ако е отворен фискален или служебен бон (какъв точно може
        да се разбере по статус битовете), и ‘0’ ако няма отворен бон.
        Items Броят на продажбите регистрирани на текущия или на последния фискален бон. 4
        байта.
        Amount Сумата от последния фискален бон – 9 байта със знак.
        Tender Сумата платена на поредния или последен бон. 9 байта със знак.
        Тази команда дава възможност на приложението в PC да установи статуса, а ако е нужно и да
        възстанови и завърши фискална операция, прекъсната аварийно и ненавременно, например при изключване на ел. захранване.        
        
        */
        String res = cmdCustom(103, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            // Отговор: <CanVd>,<TaxA>,<TaxB>,<TaxC>,<TaxD>,<TaxE>,<TaxF>,<TaxG>,<TaxH>,<Inv>,<InvNum>
            response.put("CanVD", resLines[0]);
            response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
            response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
            response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
            response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
            response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
            response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
            response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
            response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
            response.put("Inv", (resLines.length > 9)?resLines[9]:"");
            response.put("InvNum", (resLines.length > 10)?resLines[10]:"");
//            response.put("Type", (resLines.length > 11)?resLines[11]:"");
//            response.put("Type", "");

            // Additional info about fiscal transaction
            setClearErrors(false);
            try {
                res = cmdCustom(76, "T");
                if (res.length() > 0) {
                    resLines = res.split(",");
                    boolean isOpen = resLines[0].equals("1");
                    response.put("IsOpen", isOpen?"1":"0");
                    if (isOpen && response.get("Inv").equals("1")) {
                        // correction of InvNum (decrement)
                        int invNum = Integer.parseInt(response.get("InvNum"))-1;
                        response.put("InvNum", String.format(Locale.ROOT, "%010d", invNum));
                    }
                    if (resLines.length > 1)
                        response.put("SellCount", (resLines[1].length() > 0)?Integer.toString(Integer.parseInt(resLines[1])):"0");
                    if (resLines.length > 2)
                        response.put("SellAmount", reformatCurrency(resLines[2],100));
                    if (resLines.length > 3)
                        response.put("PayAmount", reformatCurrency(resLines[3], 100));
                }
            } catch (Exception e) {
            }
            setClearErrors(true);
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
        err("Неподдържана команда! Четене на информация за КЛЕН.");
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        /*
        Използва се Raw команда 
        SKLEN startDate [endDate]
        Dates are in format yyyy-MM-dd'T'HH:mm:ss

        Usage: sklen from to [pattern [format]]
         pattern - [B][mask[submask]]    
           if B 'from' and 'to' are the numbers of the Z reports
           mask -XY X: 0,Z,F,S Y:Hex 
           X= 0-none, Z- Z reports, F and S - Z and Change reports
           Y= 0-none, 1-Fiscal 2-Comment 4-Copy 8-X reports
           submask:Hex= 0-none 1-Serv.IN 2-Serv. OUT 4-INVOICE 8-STORNO
         format= 0-teext 1-SD records 2-print 3-NRA xml 4-CSV ДО НОМЕР	000000<LF><LF>
        
        Examples:
        - only Z reports
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 Z0
        - only Z reports
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 Z0
        - only fiscal receipts
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 01 
        - only fiscal receipts as invoices
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 014 
        - only storno fiscal receipts 
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 018 
        - only fiscal receipts in xml format
        SKLEN 2019-12-01T00:00:00 2020-01-01T00:00:00 01 3
        - all documents with numbers between from and to
        SKLEN 0000001 0000002
        - all Z-reports with numbers between from and to
        SKLEN 0000001 0000002 B
        
        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String command = "SKLEN "+dtf.format(fromDate)+" "+dtf.format(toDate);
//        String EJ = cmdCustomRaw(command);
//        response.put("EJ", EJ);
        return response;
    }

     public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        /*
         See cmdReadE by dates
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String command = "SKLEN "+fromDoc+" "+toDoc;
//        String EJ = cmdCustomRaw(command);
//        response.put("EJ", EJ);
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
        56h (86) ПРОЧИТА ДАТА И ЧАС НА ПОСЛЕДЕН ФИСКАЛЕН БОН
        Област за данни: [T]
        Отговор: dd-MM-yyyy[ HH:mm:ss]
        T - връща и час
        Пример:
        30-12-2019 15:00:02        

        */
        DateFormat formatGet = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date docDate = null;
        String params = "T";
        String res = cmdCustom(86, params);
        try {
            docDate = formatGet.parse(res);
        } catch (Exception e) {
            err("Невалидна дата/час ["+res+"] "+e.getMessage());
        }
        return docDate;
    }
    
    
}
