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

/**
 * This class supports following fiscal devices FMP-350X, FMP-55X, FP-700X, FP-700XE, WP-500X, WP-50X, DP-25X, WP-25X, DP-150X, DP-05C
 * Corresponding to specification PM_FMP350X_FMP55X_FP700X-BUL_ FPprotocol_eng.pdf
 * @author Dimitar Angelov
 */
public class DeviceDatecsXV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;
    
    private boolean fiscalCheckRevOpened = false;
    
    protected final String SEP = "\t";

    private LinkedHashMap<String, Integer> consts = new LinkedHashMap();
    
    public class DepartmentInfo {
        String number;
        String name;
        String taxGr;

        public DepartmentInfo(String number, String name, String taxGr) {
            this.number = number;
            this.name = name;
            this.taxGr = taxGr;
        }
    }
    
    private HashMap<String, DepartmentInfo> usedDepartments = new HashMap<>();
    
    public DeviceDatecsXV1(AbstractProtocol p) {
        super(p);
    }

    public DeviceDatecsXV1(String portName, int baudRate, int readTimeout, int writeTimeout) {
        super(portName, baudRate, readTimeout, writeTimeout);
        serialPort = SerialPort.getCommPort(portName);
        if (serialPort.openPort()) {
            serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            // Always have to initialize Serial Port in nonblocking (forever) mode!
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, readTimeout, writeTimeout);
            serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            protocol = new ProtocolV10X(serialPort.getInputStream(), serialPort.getOutputStream(), ProtocolV10X.EncodingType.CP_1251);
        }
        // TODO: if protocol is null throw exception
    }


    @Override
    public void close() throws IOException {
        super.close(); 
        if ((serialPort != null) && serialPort.isOpen())
            serialPort.closePort();
        serialPort = null;
    }

    private void checkErrorCode(String sErrorCode, String context) throws FDExceptionDatecsX {
        checkErrorCode(sErrorCode, context, null);
    }
    
    private void checkErrorCode(String sErrorCode, String context, long[] ignoreErrors) throws FDExceptionDatecsX {
        long errorCode = 0;
        if (sErrorCode.length() > 0)
            errorCode = stringToLong(sErrorCode);
        checkErrorCode(errorCode, context, ignoreErrors);
    }

    private void checkErrorCode(long errorCode, String context) throws FDExceptionDatecsX {
        checkErrorCode(errorCode, context, null);
    }
    
    private void checkErrorCode(long errorCode, String context, long[] ignoreErrors) throws FDExceptionDatecsX {
        if (errorCode != 0) {
            if (ignoreErrors != null)
                for(int i = 0; i < ignoreErrors.length; i++)
                    if (errorCode == ignoreErrors[i]) 
                        return;
            throw new FDExceptionDatecsX(errorCode, context);
        }    
    }
    
    private String[] getResponse(String res, String context) throws FDException {
        return getResponse(res, context, null);
    }
    private String[] getResponse(String res, String context, long[] ignoreErrors) throws FDException {
        String[] rData = new String[0];
        if (res.contains(SEP)) {
            rData = res.split(SEP);
            checkErrorCode(rData[0], context);
        } else {
            throw new FDException("Неочакван отговор '"+res+"' при '"+context+"'!");
        }   
        return rData;
    }
    
    @Override
    protected void readDeviceInfo() throws IOException {
        /*
        Command: 90 (5Ah) Diagnostic information
        Parameters of the command:
        Syntax 1: {Param}<SEP>
        Optional parameters:
            - none - Diagnostic information without firmware checksum;
                Answer(1)
            - '1' - Diagnostic information with firmware checksum;
                Answer(1)
        Syntax 2: {Param}
        Optional parameters:
            - none - Diagnostic information without firmware checksum;
                Answer(2)
            - '1' -Diagnostic information with firmware checksum;
                Answer(2)
        Answer(1): {ErrorCode}<SEP>{Name}<SEP>{FwRev}<SEP>{FwDate}<SEP>
        {FwTime}<SEP>{Checksum}<SEP>{Sw}<SEP>{SerialNumber}<SEP>{FMNumber}<SEP>
            - ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
            - Name - Device name ( up to 32 symbols ).
            - FwRev - Firmware version. 6 symbols;
            - FwDate - Firmware date DDMMMYY. 7 symbols;
            - FwTime - Firmware time hhmm. 4 symbols.
            - Checksum - Firmware checksum. 4 symbols;
            - Sw - Switch from Sw1 to Sw8. 8 symbols (not used at this device, always 00000000);
            - SerialNumber - Serial Number ( Two letters and six digits: XX123456);
            - {FMNumber} –Fiscal memory number (8 digits)
        Answer(2): {Name},{FwRev}{Sp}{FwDate}{Sp}{FwTime},{Checksum},{Sw},
        {SerialNumber},{FMNumber}
            - Name - Device name ( up to 32 symbols ).
            - FwRev - Firmware version. 6 symbols;
            - Sp - Space. 1 symbol;
            - FwDate - Firmware date DDMMMYY. 7 symbols;
            - FwTime - Firmware time hhmm. 4 symbols.
            - Checksum - Firmware checksum. 4 symbols;
            - Sw - Switch from Sw1 to Sw8. 8 symbols;
            - SerialNumber - Serial Number ( Two letters and six digits: XX123456);
            - {FMNumber} –Fiscal memory number (8 digits)        
        */
        // Request diagnostic info
        String res = cmdCustom(90, SEP);
        mDeviceInfo = res;
        // Answer 1
        // {ErrorCode}<SEP>{Name}<SEP>{FwRev}<SEP>{FwDate}<SEP>{FwTime}<SEP>{Checksum}<SEP>{Sw}<SEP>{SerialNumber}<SEP>{FMNumber}<SEP>
        //       0            1          2           3            4             5            6            7                8
        try {
            String[] rData = getResponse(res, "");
            mModelName = (rData.length > 1)?rData[1]:"";
            mFWRev = (rData.length > 2)?rData[2]:"";
            mFWRevDT = ((rData.length > 3)?rData[3]:"")+((rData.length > 4)?" "+rData[4]:"");
            mSwitches = (rData.length > 6)?rData[6]:"";
            mSerialNum = (rData.length > 7)?rData[7]:"";
            mFMNum = (rData.length > 8)?rData[8]:"";
        } catch (Exception ex) {
            err(ex.getMessage());
        }
        readConstants();
    }

    /**
     * Read device constants/parameters
     */
    protected void readConstants() {
        /*
        Command: 255 (FFh) Programming
        Parameters of the command:
        {Name}<SEP>{Index}<SEP>{Value}<SEP>
        Mandatory parameters:
        • Name - Variable name;
        Optional parameters:
        • Index - Used for index if variable is array. For variable that is not array can be left blank. Default: 0;
        Note
        For example: Header[], Index 0 refer to line 1. Index 9 refer to line 10.
        • Value - If this parameter is blank ECR will return current value ( Answer(2) ). If the value is set, then
        ECR will program this value ( Answer(1) );
        Answer(1):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        Answer(2):
        {ErrorCode}<SEP>{VarValue}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • VarValue - Curent value of the variable;
        
        • Receipt parameters;
            • PrnQuality - Contrast of Printing ( from 0 to 20 );
            • PrintColumns - Number of printer columns:
                • for FP-700X = 42, 48 or 64 columns;
                • for FMP-350X = 42, 48 or 64 columns;
                • for FMP-55X = 32 columns;
                • for DP-25X, DP-150X, WP-500X, WP-50X, WP-25X = 42 columns;        
        */
        consts.clear();
        String res;
        try {
            res = cmdCustom(255, "PrintColumns"+SEP+SEP+SEP);
            String[] rData = getResponse(res, "Четене на параметър PrintColumns");
            int pcols = 0;
            if (rData.length > 0)
                pcols = stringToInt(rData[1]);
            if (pcols > 0)
                consts.put("PRINT_COLS", pcols);
        } catch (IOException ex) {
            err("Грешка при четене на параметри!");
        }
        if (!consts.containsKey("PRINT_COLS"))
            consts.put("PRINT_COLS", 32); // Възможно най-малкия размер
    }

    @Override
    public int getLineWidthNonFiscalText() {
        return consts.get("PRINT_COLS")-2;
    }

    @Override
    public int getLineWidthFiscalText() {
        return consts.get("PRINT_COLS")-2;
    }
    
    @Override
    protected void initPrinterStatusMap() {
        /*        
        Status bits
        The current status of the device is coded in field 8 bytes long which is sent within each message of the fiscal
        printer. Description of each byte in this field:
        Byte 0: General purpose
        - 0.7 = 1 Always 1.
        - 0.6 = 1 Cover is open.
        52
        Datecs FMP-350X, FMP-55X, FP-700X , FP-700XE Programmer’s Manual
        WP-500X, WP-50X, DP-25X, WP-25X, DP-150X, DP-05C
        - 0.5 = 1 General error - this is OR of all errors marked with #.
        - 0.4 = 1# Failure in printing mechanism.
        - 0.3 = 0 Always 0.
        - 0.2 = 1 The real time clock is not synchronized.
        - 0.1 = 1# Command code is invalid.
        - 0.0 = 1# Syntax error.
        Byte 1: General purpose
        - 1.7 = 1 Always 1.
        - 1.6 = 0 Always 0.
        - 1.5 = 0 Always 0.
        - 1.4 = 0 Always 0.
        - 1.3 = 0 Always 0.
        - 1.2 = 0 Always 0.
        - 1.1 = 1# Command is not permitted.
        - 1.0 = 1# Overflow during command execution.
        Byte 2: General purpose
        - 2.7 = 1 Always 1.
        - 2.6 = 0 Always 0.
        - 2.5 = 1 Nonfiscal receipt is open.
        - 2.4 = 1 EJ nearly full.
        - 2.3 = 1 Fiscal receipt is open.
        - 2.2 = 1 EJ is full.
        - 2.1 = 1 Near paper end.
        - 2.0 = 1# End of paper.
        Byte 3: Not used
        - 3.7 = 1 Always 1.
        - 3.6 = 0 Always 0.
        - 3.5 = 0 Always 0.
        - 3.4 = 0 Always 0.
        - 3.3 = 0 Always 0.
        - 3.2 = 0 Always 0.
        - 3.1 = 0 Always 0.
        - 3.0 = 0 Always 0.
        Byte 4: Fiscal memory
        - 4.7 = 1 Always 1.
        - 4.6 = 1 Fiscal memory is not found or damaged.
        - 4.5 = 1 OR of all errors marked with ‘*’ from Bytes 4 и 5.
        - 4.4 = 1* Fiscal memory is full.
        - 4.3 = 1 There is space for less then 60 reports in Fiscal memory.
        - 4.2 = 1 Serial number and number of FM are set.
        - 4.1 = 1 Tax number is set.
        - 4.0 = 1* Error when trying to access data stored in the FM.
        Byte 5: Fiscal memory
        - 5.7 = 1 Always 1.
        - 5.6 = 0 Always 0.
        - 5.5 = 0 Always 0.
        - 5.4 = 1 VAT are set at least once.
        - 5.3 = 1 Device is fiscalized.
        - 5.2 = 0 Always 0.
        - 5.1 = 1 FM is formatted.
        - 5.0 = 0 Always 0.
        Byte 6: Not used
        - 6.7 = 1 Always 1.
        - 6.6 = 0 Always 0.
        - 6.5 = 0 Always 0.
        - 6.4 = 0 Always 0.
        - 6.3 = 0 Always 0.
        - 6.2 = 0 Always 0.
        - 6.1 = 0 Always 0.
        - 6.0 = 0 Always 0.
        Byte 7: Not used
        - 7.7 = 1 Always 1.
        - 7.6 = 0 Always 0.
        - 7.5 = 0 Always 0.
        - 7.4 = 0 Always 0.
        - 7.3 = 0 Always 0.
        - 7.2 = 0 Always 0.
        - 7.1 = 0 Always 0.
        - 7.0 = 0 Always 0        
        */
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();
        
        statusBytesDef.put("S0", new String[] // General purpose
        {
             "Синтактична грешка"             // 0 #Syntax error.
            ,"Невалиден код на команда"       // 1 #Command code is invalid.
            ,"Неустановени дата/час"          // 2 The real time clock is not synchronized.
            ,""                               // 3 Always 0.
            ,"Грешка в печатащия механизъм"   // 4 #Failure in printing mechanism.
            ,"Обща грешка"                    // 5 General error - this is OR of all errors marked with #.
            ,"Капакът е отворен"              // 6 Cover is open.
            ,""                               // 7 Always 1.
        }
        );
        warningStatusBits.put("S0", new byte[] {
            2,6
        });
        errorStatusBits.put("S0", new byte[] {
            0,1,5
        });
        statusBytesDef.put("S1", new String[] // General purpose
        {
             "Препълване"                                 // 0 #Overflow during command execution.
            ,"Непозволена команда в този контекст"        // 1 #Command is not permitted.
            ,""                                           // 2 Always 0.
            ,""                                           // 3 Always 0.
            ,""                                           // 4 Always 0.
            ,""                                           // 5 Always 0.
            ,""                                           // 6 Always 0.
            ,""                                           // 7 Always 1.
        }
        );
        warningStatusBits.put("S1", new byte[] {
            // 
        });
        errorStatusBits.put("S1", new byte[] {
            0,1
        });
        statusBytesDef.put("S2", new String[] // General purpose
        {
             "Край на хартията"                                            // 0 #End of paper.
            ,"Близък край на хартията"                                     // 1 Near paper end.
            ,"КЛЕН е пълна"                                                // 2 EJ is full.
            ,"Отворен Фискален Бон"                                        // 3 Fiscal receipt is open.
            ,"Близък край на КЛЕН"                                         // 4 EJ nearly full.
            ,"Отворен е Служебен Бон"                                      // 5 Nonfiscal receipt is open.
            ,""                                                            // 6 Always 0.
            ,""                                                            // 7 Always 1.
        }
        );
        warningStatusBits.put("S2", new byte[] {
            1,4
        });
        errorStatusBits.put("S2", new byte[] {
            0,2
        });
        statusBytesDef.put("S3", new String[] // Not Used
        {
             "" // 0 Always 0.
            ,"" // 1 Always 0.
            ,"" // 2 Always 0.
            ,"" // 3 Always 0.
            ,"" // 4 Always 0.
            ,"" // 5 Always 0.
            ,"" // 6 Always 0.
            ,"" // 7 Always 1.
        }
        );
        warningStatusBits.put("S3", new byte[] {
        });
        errorStatusBits.put("S3", new byte[] {
        });
        statusBytesDef.put("S4", new String[] // Fiscal memory
        {
             "Грешка при достъп до фискалната памет"                   // 0 *Error when trying to access data stored in the FM.
            ,"Зададен е ЕИК"                                           // 1 Tax number is set.
            ,"Зададени са индивидуален номер на ФУ и номер на ФП."     // 2 Serial number and number of FM are set.
            ,"Има място за по-малко от 60 фискални затваряния"         // 3 There is space for less then 60 reports in Fiscal memory.
            ,"Фискалната памет е пълна"                                // 4 *Fiscal memory is full.
            ,"Грешка 0 или 4"                                          // 5 OR of all errors marked with ‘*’ from Bytes 4 и 5.
            ,"Фискалната памет липсва или е повредена"                 // 6 Fiscal memory is not found or damaged.
            ,""                                                        // 7 Always 1.
        }
        );
        warningStatusBits.put("S4", new byte[] {
            3
        });
        errorStatusBits.put("S4", new byte[] {
            0, 4, 5
        });
        statusBytesDef.put("S5", new String[] // Fiscal memory
        {
             ""                                          // 0 Always 0.
            ,"Фискалната памет e форматирана"            // 1 FM is formatted.
            ,""                                          // 2 Always 0.
            ,"ФУ е във фискален режим"                   // 3 Device is fiscalized.
            ,"Зададени са поне веднъж данъчните ставки"  // 4 VAT are set at least once.
            ,""                                          // 5 Always 0.
            ,""                                          // 6 Always 0.
            ,""                                          // 7 Always 1.
        }
        );
        warningStatusBits.put("S5", new byte[] {
        });
        errorStatusBits.put("S5", new byte[] {
        });

        statusBytesDef.put("S6", new String[] // No Used
        {
             "" // 0 Always 0.
            ,"" // 1 Always 0.
            ,"" // 2 Always 0.
            ,"" // 3 Always 0.
            ,"" // 4 Always 0.
            ,"" // 5 Always 0.
            ,"" // 6 Always 0.
            ,"" // 7 Always 1.
        }
        );
        warningStatusBits.put("S6", new byte[] {
        });
        errorStatusBits.put("S6", new byte[] {
        });
        statusBytesDef.put("S7", new String[] // No Used
        {
             "" // 0 Always 0.
            ,"" // 1 Always 0.
            ,"" // 2 Always 0.
            ,"" // 3 Always 0.
            ,"" // 4 Always 0.
            ,"" // 5 Always 0.
            ,"" // 6 Always 0.
            ,"" // 7 Always 1.
        }
        );
        warningStatusBits.put("S7", new byte[] {
        });
        errorStatusBits.put("S7", new byte[] {
        });
    }
    
    @Override
    public boolean isOpenNonFiscalCheck() {
      int byteNum = 2;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x20) == 0x20);
    }

    @Override
    public boolean isOpenFiscalCheck() {
      int byteNum = 2;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x08) == 0x08);
    }

    @Override
    public boolean isOpenFiscalCheckRev() {
        return isOpenFiscalCheck() && fiscalCheckRevOpened;
    }

    @Override
    public boolean isFiscalized() {
      int byteNum = 5;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x08) == 0x08);
    }
    
    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String opCode, String opPasswd, String wpNum, String UNS, boolean invoice) throws IOException {
        /*
        Command: 48 (30h) Open fiscal receipt
        Parameters of the command:
        Syntax 1:
        {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Invoice}<SEP>
        Syntax 2:
        {OpCode}<SEP>{OpPwd}<SEP>{NSale}<SEP>{TillNmb}<SEP>{Invoice}<SEP>
        Mandatory parameters:
            - OpCode - Operator number from 1...30;
            - OpPwd - Operator password, ascii string of digits. Length from 1...8;
        Note: WP-500X, WP-25X, WP-50X, DP-25X, DP-150X, DP-05C:, the default password for each operator is equal to the corresponding
        number (for example, for Operator1 the password is "1") . FMP-350X, FMP-55X, FP-700X: the default password for each operator is
        “0000”
            - NSale - Unique sale number (21 chars "LLDDDDDD-CCCC-DDDDDDD", L[A-Z], C[0-9A-Za-z],
        D[0-9] )
            - TillNmb - Number of point of sale from 1...99999;
            - Invoice - If this parameter has value 'I' it opens an invoice receipt. If left blank it opens fiscal receipt;
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
            - ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
            - SlipNumber - Current slip number (1...9999999);
        */
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        fiscalCheckRevOpened = false;
        String params;
        if (UNS.length() == 0) {
            // Without Unique Number of Sell
            // {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Invoice}<SEP>
            params = opCode+SEP+opPasswd+SEP+wpNum+SEP+(invoice?"I":"")+SEP;
        } else {
            // With Unique Number of Sell
            // {OpCode}<SEP>{OpPwd}<SEP>{NSale}<SEP>{TillNmb}<SEP>{Invoice}<SEP>
            params = opCode+SEP+opPasswd+SEP+UNS+SEP+wpNum+SEP+(invoice?"I":"")+SEP;
        }
        String res = cmdCustom(48, params);
        String[] rData = getResponse(res, "Отваряне на фискален бон");
        if (rData.length > 0)
            response.put("FiscReceipt", rData[1]);
        return response;
    }

    @Override
    public void cmdPrintCustomerData(String UIC, int UICType, String seller, String recipient, String buyer, String VATNum, String address)  throws IOException {
        /*
        Command: 57 (39h) Enter and print invoice data
        Parameters of the command:
        {Seller}<SEP>{Receiver}<SEP>{Buyer}<SEP>{Address1}<SEP>{Address2}<SEP>{TypeTAXN}<SEP>{TAXN}<SEP>{VATN}<SEP>
        Mandatory parameters:{TypeTAXN} ,{TAXN}, {VATN}
        • TypeTAXN - Type of client's tax number. 0-BULSTAT; 1-EGN; 2-LNCH; 3-service number
        • TAXN - Client's tax number. ascii string of digits 8...13 Optional parameters:
        • VATN - VAT number of the client. 10...14 symbols
        • Seller - Name of the seller; 36 symbols max; if left blank prints empty space for hand-writing
        • Receiver - Name of the receiver; 36 symbols max; if left blank prints empty space for hand-writing
        • Buyer - Name of the buyer; 36 symbols max; if left blank prints empty space for hand-writing
        • Address1 - First line of the address; 36 symbols max; if left blank prints empty space for hand-writing
        • Address2 - Second line of the address; 36 symbols max; if left blank prints empty space for handwriting
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;        
        */
        if (UICType < 0 || UICType > 3)
            throw new FDException("Ivalid UIC type!");
        String params;
        String addr1 = address;
        String addr2 = "";
        if (addr1.length() > 36) {
            addr2 = addr1.substring(36);
            addr1 = addr1.substring(0,36);
            if (addr2.length() > 36)
                addr2 = addr2.substring(0, 36);
        }
            
        
        // {Seller}<SEP>{Receiver}<SEP>{Buyer}<SEP>{Address1}<SEP>{Address2}<SEP>{TypeTAXN}<SEP>{TAXN}<SEP>{VATN}<SEP>
        params = seller+SEP+recipient+SEP+buyer+SEP+addr1+SEP+addr2+SEP+Integer.toString(UICType)+SEP+UIC+SEP+VATNum+SEP;
        String res = cmdCustom(57, params);
        getResponse(res, "Отпечатване на данни за фактурата");
    }

    
    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws IOException {
        /*
        Command: 56 (38h) Close fiscal receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(56, "");
        String[] rData = getResponse(res, "Затваряне на фискален бон");
        if (rData.length > 0)
            response.put("FiscReceipt", rData[1]);
        return response;
    }
    
    @Override
    public void cmdCancelFiscalCheck() throws IOException {
        /*
        Command: 60 (3Ch) Cancel fiscal receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;        
        */
        String res = cmdCustom(60, "");
        getResponse(res, "Отказ на фискален бон");
    }

    @Override
    public void cmdPrintFiscalText(String text) throws IOException {
        /*
        Command: 54 (36h) Printing of a free fiscal text
        Parameters of the command:
        {Text}<SEP>Bold<SEP>Italic<SEP>DoubleH<SEP>Underline<SEP>alignment<SEP>
        Mandatory parameters:
        • Text - text of 0...XX symbols, XX = PrintColumns-2;
        Optional parameters:
        • Bold - flag 0 or 1, 1 = print bold text; empty field = normal text;
        • Italic - flag 0 or 1, 1 = print italic text; empty field = normal text;
        • DoubleH - flag 0 or 1, 1 = print double height text; empty field = normal text;
        • Underline - flag 0 or 1, 1 = print underlined text; empty field = normal text;
        • alignment - 0, 1 or 2. 0=left alignment, 1=center, 2=right; empty field = left alignment;
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;        
        */        
        int maxLen = consts.get("PRINT_COLS")-2;
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+text+")");
            text = text.substring(0, maxLen);
        }
        String res = cmdCustom(54, text+SEP+SEP+SEP+SEP+SEP+SEP);
        getResponse(res, "Печат на фискален текст");
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        Command: 38 (26h) Opening a non-fiscal receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);
        */        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String res = cmdCustom(38, "");
        String[] rData = getResponse(res, "Отваряне на служебен бон");
        if (rData.length > 1)
            response.put("AllReceipt", rData[1]);
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text, int font) throws IOException {
        /*
        Command: 42 (2Ah) Printing of a free non-fiscal text
        Parameters of the command:
        {Text}<SEP>{Bold}<SEP>{Italic}<SEP>{Height}<SEP>{Underline}<SEP>{alignment}<SEP>
        Mandatory parameters:
        • Text - text of 0...XX symbols. XX depend of opened receipt type. XX = (PrintColumns-2);
        Optional parameters:
        • Bold - flag 0 or 1, 1 = print bold text; empty field = normal text;
        • Italic - flag 0 or 1, 1 = print italic text; empty field = normal text;
        • Height - 0, 1 or 2. 0=normal height, 1=double height, 2=half height; empty field = normal height text;
        • Underline - flag 0 or 1, 1 = print underlined text; empty field = normal text;
        • alignment - 0, 1 or 2. 0=left alignment, 1=center, 2=right; empty field = left alignment;
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        */        
        int maxLen = consts.get("PRINT_COLS")-2;
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
            text = text.substring(0, maxLen);
        }
        // {Text}<SEP>{Bold}<SEP>{Italic}<SEP>{Height}<SEP>{Underline}<SEP>{alignment}<SEP>
        String params = text+SEP+((font > 0)?"1":"0")+SEP+"0"+SEP+"0"+SEP+"0"+SEP+"0"+SEP;
        String res = cmdCustom(42, params);
        getResponse(res, "Печат на нефискален текст");
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws IOException {
        cmdPrintNonFiscalText(text, 0);
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws IOException {
        /*
        Command: 39 (27h) Closing a non-fiscal receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(39, "");
        String[] rData = getResponse(res, "Затваряне на служебен бон");
        if (rData.length > 1)
            response.put("AllReceipt", rData[1]);
        return response;
    }

    @Override
    public void cmdPaperFeed(int lineCount) throws IOException {
        /*
        Command: 44 (2Ch) Paper feed
        Parameters of the command:
        {Lines}<SEP>
        Optional parameters:
        • Lines - Number of lines to feed from 1 to 99. Default: 1;
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        */        
        String res = cmdCustom(44, Integer.toString(Integer.max(1, Integer.min(lineCount, 99)))+SEP);
        getResponse(res, "Придвижване на хартията");
    }
    
    protected String TaxCdToTaxGroup(String taxCd) throws IOException {
        String taxGroup = "";
        switch(taxCd) {
            case "1" :
                taxGroup = "A";
                break;
            case "2" :
                taxGroup = "B";
                break;
            case "3" :
                taxGroup = "C";
                break;
            case "4" :
                taxGroup = "D";
                break;
            case "5" :
                taxGroup = "E";
                break;
            case "6" :
                taxGroup = "F";
                break;
            case "7" :
                taxGroup = "G";
                break;
            case "8" :
                taxGroup = "H";
                break;
        }
        if (taxGroup.isEmpty())
            throw new IOException("Невалиден код на данъчна група ["+taxCd+"]");
        return taxGroup;
    }
    
    protected String TaxGroupToTaxCd(String taxGroup) throws IOException {
        String taxCd = "";
        switch(taxGroup) {
            case "A" :
            case "А" :
                taxCd = "1";
                break;
            case "B" :
            case "Б" :
                taxCd = "2";
                break;
            case "C" :
            case "В" :
                taxCd = "3";
                break;
            case "D" :
            case "Г" :
                taxCd = "4";
                break;
            case "E" :
            case "Д" :
                taxCd = "5";
                break;
            case "F" :
            case "Е" :
                taxCd = "6";
                break;
            case "G" :
            case "Ж" :
                taxCd = "7";
                break;
            case "H" :
            case "З" :
                taxCd = "8";
                break;
        }
        if (taxCd.isEmpty())
            throw new IOException("Невалиден код на данъчна група ["+taxGroup+"]");
        return taxCd;
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        Command: 49 (31h) Registration of sale
        Parameters of the command:
        Syntax 1:
        {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        Syntax 2:
        {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>{Unit}<SEP>
        Mandatory parameters: PluName, TaxCd, Price
        • PluName - Name of product, up to 72 characters not empty string;
        • TaxCd - Tax code;
        • '1' - vat group A;
        • '2' - vat group B;
        • '3' - vat group C;
        • '4' - vat group D;
        • '5' - vat group E;
        • '6' - vat group F;
        • '7' - vat group G;
        • '8' - vat group H;
        • Price - Product price, with sign '-' at void operations. Format: 2 decimals; up to *9999999.99
        • Department - Number of the department 0..99; If '0' - Without department;
        Optional parameters: Quantity, DiscountType, DiscountValue
        • Quantity - Quantity of the product ( default: 1.000 ); Format: 3 decimals; up to *999999.999
        • Unit - Unit name, up to 6 characters not empty string;
        !!! Max value of Price * Quantity is *9999999.99. !!!
        • DiscountType - type of discount.
        • '0' or empty - no discount;
        • '1' - surcharge by percentage;
        • '2' - discount by percentage;
        • '3' - surcharge by sum;
        • '4' - discount by sum; If DiscountType is non zero, DiscountValue have to contain value. The
        format must be a value with two decimals.
        • DiscountValue - value of discount.
        • a number from 0.01 to 9999999.99 for sum operations;
        • a number from 0.01 to 99.99 for percentage operations;
        Note
        If DiscountType is zero or empty, parameter DiscountValue must be empty.
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);        
        */        
        int maxLen = 72;
        String L1 = "";
        String L2 = "";
        String[] textLines = sellText.split("\n");
        if (textLines.length > 1) {
            L1 = textLines[0];
            // ограничаване на 2-та реда до максималната дължина
            if (L1.length() > maxLen)
                L1 = L1.substring(0, maxLen);
            L2 = textLines[1];
            if (L2.length() > maxLen)
                L2 = L2.substring(0, maxLen);
            // Ако общата дължина надхвърля максималната
            // приоритет се дава на втория ред, който се предполага, че е с количество и цена
            if ((L1.length() + L2.length()) >= maxLen) {
                L1 = L1.substring(0,L2.length());
            }
        } else {
            if (textLines[0].length() > maxLen) {
                L1 = textLines[0].substring(0, maxLen);
                L2 = "";
            } else
                L1 = textLines[0];
        }
        if (taxGroup.length() > 1)
            taxGroup = taxGroup.substring(0,1);
        String taxCd = TaxGroupToTaxCd(taxGroup);
        if (Math.abs(quantity) < 0.001)
            quantity = 1;
        String discountType = "0"; // no discount
        String dicountValue = "";
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    dicountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"1":"2";
                }
            } else {
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    dicountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"3":"4";
                }
            }
        }
        
        // {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        // {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>{Unit}<SEP>
        // За забрана на отпечатване на количество x цена, когате количеството е 1
        // Cmd=255
        // Args=HideSingleItemInfoOnSells\t\t1\t
        
        String strQty = ((abs(quantity) < 0.001) || (abs(quantity-1) < 0.001))?"":String.format(Locale.ROOT, "%.3f", quantity);
        String params = 
                L1+" "+L2
                +SEP+taxCd
                +SEP+String.format(Locale.ROOT, "%.2f", price)
                +SEP+strQty
                +SEP+discountType+SEP+dicountValue
                +SEP+"0"
                +((unit.length() > 0)?SEP+unit:"")
                +SEP
                ;
        String res = cmdCustom(49, params);
        getResponse(res, "Регистриране на продажба по група");
    }

    protected DepartmentInfo getDeptInfo(String dept) {
        /*
        Command: 88 (58h) Get department information
        Parameters of the command:
        {Department}<SEP>
        Optional parameters:
        • Department - Number of department (1...99); If Department is empty - department report;
        Answer:
        {ErrorCode}<SEP>{TaxGr}<SEP>{Price}<SEP>{TotSales}<SEP>{TotSum}<SEP>{STotSales}<SEP>{STotSum}<SEP>{Name}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • TaxGr - Tax group of department;
        • Price - Price of department;
        • TotSales - Number of sales for this department for day;
        • TotSum - Accumulated sum for this department for day;
        • STotSales - Number of storno operations for this department for day;
        • STotSum - Accumulated sum from storno operations for this department for day;
        • Name - Name of the department;        
        */
        if (!usedDepartments.containsKey(dept)) {
            usedDepartments.put(dept, null);
            try {
                String res = cmdCustom(88, dept+SEP);
                // {ErrorCode}<SEP>{TaxGr}<SEP>{Price}<SEP>{TotSales}<SEP>{TotSum}<SEP>{STotSales}<SEP>{STotSum}<SEP>{Name}<SEP>
                //      0             1           2            3             4              5              6           7
                String[] rData = getResponse(res, "Четене на информация за департамент");
                if (rData.length > 1) {
                    DepartmentInfo di = new DepartmentInfo(dept, "", TaxCdToTaxGroup(rData[1]));
                    if (rData.length > 7)
                        di.name = rData[7];
                    usedDepartments.put(dept, di);
                } else    
                    throw new Exception("Грешка при четене на информация за департамент "+dept+" errorCode="+rData[0]);
            } catch (Exception ex) {
                err(ex.getMessage());
            }
        }
        return usedDepartments.get(dept);
    }
    @Override
    public void cmdSellDept(String sellText, String dept, double price, double quantity, String unit, String discount) throws IOException {
        /*
        Command: 49 (31h) Registration of sale
        Parameters of the command:
        Syntax 1:
        {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        Syntax 2:
        {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>{Unit}<SEP>
        Mandatory parameters: PluName, TaxCd, Price
        • PluName - Name of product, up to 72 characters not empty string;
        • TaxCd - Tax code;
        • '1' - vat group A;
        • '2' - vat group B;
        • '3' - vat group C;
        • '4' - vat group D;
        • '5' - vat group E;
        • '6' - vat group F;
        • '7' - vat group G;
        • '8' - vat group H;
        • Price - Product price, with sign '-' at void operations. Format: 2 decimals; up to *9999999.99
        • Department - Number of the department 0..99; If '0' - Without department;
        Optional parameters: Quantity, DiscountType, DiscountValue
        • Quantity - Quantity of the product ( default: 1.000 ); Format: 3 decimals; up to *999999.999
        • Unit - Unit name, up to 6 characters not empty string;
        !!! Max value of Price * Quantity is *9999999.99. !!!
        • DiscountType - type of discount.
        • '0' or empty - no discount;
        • '1' - surcharge by percentage;
        • '2' - discount by percentage;
        • '3' - surcharge by sum;
        • '4' - discount by sum; If DiscountType is non zero, DiscountValue have to contain value. The
        format must be a value with two decimals.
        • DiscountValue - value of discount.
        • a number from 0.01 to 9999999.99 for sum operations;
        • a number from 0.01 to 99.99 for percentage operations;
        Note
        If DiscountType is zero or empty, parameter DiscountValue must be empty.
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);        
        */        
        int maxLen = 72;
        DepartmentInfo dInfo = getDeptInfo(dept);
        if (dInfo == null)
            throw new IOException("Липсва департамент ["+dept+"]!");
        String taxCd = TaxGroupToTaxCd(dInfo.taxGr);
        String L1 = "";
        String L2 = "";
        String[] textLines = sellText.split("\n");
        
        if (textLines.length > 1) {
            L1 = textLines[0];
            // ограничаване на 2-та реда до максималната дължина
            if (L1.length() > maxLen)
                L1 = L1.substring(0, maxLen);
            L2 = textLines[1];
            if (L2.length() > maxLen)
                L2 = L2.substring(0, maxLen);
            // Ако общата дължина надхвърля максималната
            // приоритет се дава на втория ред, който се предполага, че е с количество и цена
            if ((L1.length() + L2.length()) >= maxLen) {
                L1 = L1.substring(0,L2.length());
            }
        } else {
            if (textLines[0].length() > maxLen) {
                L1 = textLines[0].substring(0, maxLen);
                L2 = "";
            } else
                L1 = textLines[0];
        }
        if (Math.abs(quantity) < 0.001)
            quantity = 1;
        String discountType = "0"; // no discount
        String dicountValue = "";
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    dicountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"1":"2";
                }
            } else {
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    dicountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"3":"4";
                }
            }
        }
        
        // {PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        String strQty = ((abs(quantity) < 0.001) || (abs(quantity-1) < 0.001))?"":String.format(Locale.ROOT, "%.3f", quantity);
        String params = 
                L1+" "+L2
                +SEP+taxCd
                +SEP+String.format(Locale.ROOT, "%.2f", price)
                +SEP+strQty
                +SEP+discountType+SEP+dicountValue
                +SEP+dept
                +((unit.length() > 0)?SEP+unit:"")
                +SEP
                ;
        String res = cmdCustom(49, params);
        getResponse(res, "Регистриране на продажба по департамент");
    }

    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, String discount) throws IOException {
        /*
        Command: 51 (33h) Subtotal
        Parameters of the command:
        {Print}<SEP>{Display}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>
        Optional parameters:
        • Print - print out;
        • '0' - default, no print out;
        • '1' - the sum of the subtotal will be printed out;
        • Display - Show the subtotal on the client display. Default: 0;
        • '0' - No display;
        • '1' - The sum of the subtotal will appear on the display;
        Note: The option is not used on FMP-350X and FMP-55X;
        • DiscountType - type of discount.
        • '0' or empty - no discount;
        • '1' - surcharge by percentage;
        • '2' - discount by percentage;
        • '3' - surcharge by sum;
        • '4' - discount by sum; If {DiscountType} is non zero, {DiscountValue} have to contain value.
        The format must be a value with two decimals.
        • DiscountValue - value of discount.
        • a number from 0.01 to 21474836.47 for sum operations;
        • a number from 0.01 to 99.99 for percentage operations;
        Note
        If DiscountType is zero or empty, parameter DiscountValue must be empty.
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>{Subtotal}<SEP>{TaxA}<SEP>{TaxB}<SEP>{TaxC}<SEP>{TaxD}<SEP>{TaxE}<SEP>{TaxF}<SEP>{TaxG}<SEP>{TaxH}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);
        • Subtotal - Subtotal of the receipt ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        • TaxX - Recepts turnover by vat groups ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String discountType = "0"; // no discount
        String discountValue = "";
        if (discount.length() > 0) {
            if (discount.contains("%")) {
                // discount as percent
                discount = discount.replaceAll("%", "");
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    discountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"1":"2";
                }
            } else {
                // discount as absolute value
                double dd = 0;
                try { 
                    dd = Double.parseDouble(discount);
                } catch (Exception e) {
                }
                if (Math.abs(dd) >= 0.01) {
                    discountValue = String.format(Locale.ROOT, "%.2f", Math.abs(dd));
                    discountType = (dd > 0)?"3":"4";
                }
            }
        }
        //{Print}<SEP>{Display}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>
        String params = 
                (toPrint?"1":"0")
                +SEP+(toDisplay?"1":"0")
                +SEP+discountType
                +SEP+discountValue
                +SEP
                ;
        String res = cmdCustom(51, params);
        // {ErrorCode}<SEP>{SlipNumber}<SEP>{Subtotal}<SEP>{TaxA}<SEP>{TaxB}<SEP>{TaxC}<SEP>{TaxD}<SEP>{TaxE}<SEP>{TaxF}<SEP>{TaxG}<SEP>{TaxH}<SEP>
        //      0              1                 2           3          4          5           6         7           8          9         10
        String[] rData = getResponse(res, "Междинна сума");
        response.put("SubTotal", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("TaxA", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        response.put("TaxB", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
        response.put("TaxC", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
        response.put("TaxD", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
        response.put("TaxE", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
        response.put("TaxF", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
        response.put("TaxG", reformatCurrency((rData.length > 9)?rData[9]:"0", 1));
        response.put("TaxH", reformatCurrency((rData.length > 10)?rData[10]:"0", 1));
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        /*
        Command: 53 (35h) Payments and calculation of the total sum (TOTAL)
        Parameters of the command:
        Syntax 1:
        {PaidMode}<SEP>{Amount}<SEP>{Type}<SEP>
        Mandatory parameters:
        • PaidMode - Type of payment;
        • '0' - cash;
        • '1' - credit card;
        • '2' - debit card;
        • '3' - other pay#3
        • '4' - other pay#4
        • '5' - other pay#5
        • Amount - Amount to pay ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        Optional parameters (with PinPad connected):
        • Type - Type of card payment. Only for payment with debit card;
        • '1' - with money;
        • '12'- with points from loyal scheme;
        Syntax 2:
        {PaidMode}<SEP>{Amount}<SEP>{Change}<SEP>
        • PaidMode - Type of payment;
        • '6' - Foreign currency
        • Amount - Amount to pay ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        • Change - Type of change. Only if PaidMode = '6';
        • '0' - current currency;
        • '1' - foreign currency;
        Answer: 1
        {ErrorCode}<SEP>{Status}<SEP>{Amount}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • Status - Indicates an error;
        • 'D' - The command passed, return when the paid sum is less than the sum of the receipt. The
        residual sum due for payment is returned to Amount;
        • 'R' - The command passed, return when the paid sum is greater than the sum of the receipt. A
        message “CHANGE” will be printed out and the change will be returned to Amount;
        • Amount - The sum tendered ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        Answer 2 - for payment with pinpad when transaction may be successful in pinpad, but unsuccessful in fiscal
        device:
        {ErrorCode}<SEP>{Sum}<SEP>{CardNum}<SEP>
        • ErrorCode -111560;
        • Sum - Sum from last transaction in cents;
        • CardNum - Last digits from card number;
        Answer 3 - for payment with pinpad when error from pinpad occured:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code;        
        */        
        int maxLen = 42;
        // Check method of payment
        if (paymentType.length() > 1)
            paymentType = paymentType.substring(0, 1);
        if (paymentType.length() == 0)
            paymentType = "0";
        String[] validPaymentTypes = {"0","1","2","3","4","5"};
        if (!Arrays.asList(validPaymentTypes).contains(paymentType))
            throw new FDException(-1L, "Невалиден начин на плащане ("+paymentType+")!");
        if (pinpadOpt.length() > 0) {
            if (!pinpadOpt.equals("1") && !pinpadOpt.equals("12")) 
                throw new FDException(-1L, "Невалидна пинпад опция("+pinpadOpt+")!");
        }    
        // {PaidMode}<SEP>{Amount}<SEP>{Type}<SEP>
        // amount = 0 плаща цялото задължение/остатък
        String params = 
                paymentType
                +SEP+String.format(Locale.ROOT, "%.2f", amount)
                +SEP+pinpadOpt
                +SEP
                ;
        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(53, params);
        //{ErrorCode}<SEP>{Status}<SEP>{Amount}<SEP>
        String[] rData = getResponse(res, "Обща сума и плащане");
        String paidStatus = (rData.length > 1)?rData[1]:"";
        String rAmount = reformatCurrency((rData.length > 2)?rData[2]:"0", 1);
        double rAmountD = 0;
        try {
            rAmountD = Double.parseDouble(rAmount);
        } catch (Exception ex) {
        }
        if (paidStatus.equals("D")) {
            // Ако платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.
            if (Math.abs(rAmountD) >= 0.01)
                warn("Платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.");
            response.put("PaidCode", "D");
            response.put("Amount", rAmount);
        } else if (paidStatus.equals("R")) {
            // Ако платената сума е по-голяма от сбора на бележката. Ще се отпечата съобщение “РЕСТО” и рестото се връща в Amount.
            debug("Платената сума е по-голяма от сумата на бона. Рестото се връща в Amount.");
            response.put("PaidCode", "R");
            response.put("Amount", rAmount);
        } else {
            // Неочакван отговор!
            warn("Неочакван отговор ("+res+")!");
            response.put("PaidCode", paidStatus);
            response.put("Amount", rAmount);
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        Command: 43 (2Bh) Opening of storno documents
        Parameters of the command:
        {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Storno}<SEP>{DocNum}<SEP>{DateTime}<SEP>{FMNumber}<SEP>{Invoice}<SEP>{ToInvoice}<SEP>{Reason}<SEP>{NSale}<SEP>
        Mandatory parameters:
        • OpCode - Operator number from 1...30;
        • OpPwd - Operator password, ascii string of digits. Length from 1...8;
        Note: WP-500X, WP-25X, WP-50X, DP-25X, DP-150X, DP-05C: the default password for each operator is equal to the corresponding
        number (for example, for Operator1 the password is "1") . FMP-350X, FMP-55X, FP-700X: the default password for each operator is
        “0000”
        • TillNmb - Number of point of sale from 1...99999;
        • Storno - Reason for storno.
        If Storno has value '0' it opens storno receipt. Reason "operator error";
        If Storno has value '1' it opens storno receipt. Reason "refund";
        If Storno has value '2' it opens storno receipt. Reason "tax base reduction";
        • DocNum - Number of the original document ( global 1...9999999 );
        • FMNumber - Fiscal memory number of the device the issued the original document;
        • DateTime - Date and time of the original document( format "DD-MM-YY hh:mm:ss DST" );
        Optional parameters:
        • Invoice - If this parameter has value 'I' it opens an invoice storno/refund receipt.
        • ToInvoice - If Invoice is 'I' - Number of the invoice that this receipt is referred to; If Invoice is blank
        this parameter has to be blank too;
        • Reason - If Invoice is 'I' - Reason for invoice storno/refund. If Invoice is blank this parameter has to be
        blank too;
        • NSale - Unique sale number (21 chars "LLDDDDDD-CCCC-DDDDDDD", L[A-Z], C[0-9A-Za-z],
        D[0-9] ) The parameter is not required only if the original document is printed by the cashier and not by
        the PC program.
        Answer:
        {ErrorCode}<SEP>{SlipNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SlipNumber - Current slip number (1...9999999);        
        */        
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        fiscalCheckRevOpened = true;
        
        String params;
        if (UNS.length() == 0) {
            throw new FDException(-1L, "Липсва Уникален Номер на Продажбата!");
        } 
        String[] validRevTypes = {"0","1","2"};
        if (!Arrays.asList(validRevTypes).contains(RevType))
            throw new FDException("Невалиден тип сторно ("+RevType+")!");
        
        if (!RevDocNum.matches("^\\d{1,7}$")) 
            throw new FDException("Невалиден номер на документа, по който е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999!");

        if (!RevFMNum.matches("^\\d{8,8}$"))
            throw new FDException("Невалиден номер на фискалната памет '"+RevFMNum+"' трябва да е число записано с 8 знака!");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        // {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Storno}<SEP>{DocNum}<SEP>{DateTime}<SEP>{FMNumber}<SEP>{Invoice}<SEP>{ToInvoice}<SEP>{Reason}<SEP>{NSale}<SEP>
        params = 
                opCode
                +SEP+opPasswd
                +SEP+wpNum
                +SEP+RevType
                +SEP+RevDocNum
                +SEP+dateFormat.format(RevDateTime)
                +SEP+RevFMNum
                +SEP+(invoice?"I":"")+SEP+(invoice?RevInvNum:"")
                +SEP+RevReason
                +SEP+RevUNS
                +SEP;
        String res = cmdCustom(43, params);
        // {ErrorCode}<SEP>{SlipNumber}<SEP>
        String[] rData = getResponse(res, "Отваряне на сторно фискален бон");
        if (rData.length > 1)
            response.put("StrReceipt", rData[1]);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws IOException {
        /*
        Command: 70 (46h) Cash in and Cash out operations
        Parameters of the command:
        {Type}<SEP>{Amount}<SEP>
        Mandatory parameters:
        • Type - type of operation;
        • '0' - cash in;
        • '1' - cash out;
        • '2' - cash in - (foreign currency);
        • '3' - cash out - (foreign currency); Optional parameters:
        • Amount - the sum ( 0.00...9999999.99 or 0...999999999 depending dec point position ); If Amount=0,
        the only Answer is returned, and receipt does not print.
        Answer:
        {ErrorCode}<SEP>{CashSum}<SEP>{CashIn}<SEP>{CashOut}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • CashSum - cash in safe sum ( 0.00...9999999.99 or 0...999999999 depending dec point position );
        • CashIn - total sum of cash in operations ( 0.00...9999999.99 or 0...999999999 depending dec point
        position );
        • CashOut - total sum of cash out operations ( 0.00...9999999.99 or 0...999999999 depending dec point
        position );
        */  
        // {Type}<SEP>{Amount}<SEP>
        String params = 
                ((ioSum < 0)?"1":"0")
                +SEP+String.format(Locale.ROOT, "%.2f", Math.abs(ioSum))
                +SEP;
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(70, params);
        // {ErrorCode}<SEP>{CashSum}<SEP>{CashIn}<SEP>{CashOut}<SEP>
        String[] rData = getResponse(res, "Служебен внос/износ");
        // Заявката е изпълнена
        response.put("CashSum", reformatCurrency((rData.length > 1)?rData[1]:"0", 1));
        response.put("CashIn", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("CashOut", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        return response;
    }

    @Override
    public void cmdSetOperator(String opCode, String opPasswd, String name) throws IOException {
        /*
        Command: 255 (FFh) Programming
        Parameters of the command:
        {Name}<SEP>{Index}<SEP>{Value}<SEP>
        • Name - Variable name;
        • Operators;
        • OperName - Name of operator. Text up to 32 symbols. Number of operator is determined
        by "Index";
        • OperPasw - Password of operator. Text up to 8 symbols. ( Require Service jumper )
        Number of operator is determined by "Index";
        Note: WP-500X, WP-50X, WP-25X, DP-25X, DP-150X, DP-05C: the default password for each operator is
        equal to the corresponding number (for example, for Operator1 the password is "1") . FMP-350X, FMP-55X,
        FP-700X: the default password for each operator is “0000”        
        ...
        Answer(1):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        */
        String params = 
                "OperName"
                +SEP+opCode
                +SEP+name
                +SEP;
        String res = cmdCustom(255, params);
        getResponse(res, "Задаване име на оператор");
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        Command: 64 (40h) Information on the last fiscal entry
        Parameters of the command:
        {Type}<SEP>
        • Type - Type of returned data. Default: 0;
        • 0 - Turnover on TAX group;
        • 1 - Amount on TAX group;
        • 2 - Storno turnover on TAX group;
        • 3 - Storno amount on TAX group;
        Answer:
        {ErrorCode}<SEP>{nRep}<SEP>{SumA}<SEP>{SumB}<SEP>{SumC}<SEP>{SumD}<SEP>{SumE}<SEP>{SumF}<SEP>{SumG}<SEP>{SumH}<SEP>{Date}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • nRep - Number of report 1...3650;
        • SumX - Depend on Type. X is the letter of TAX group ( 0.00...9999999.99 or 0...999999999 depending
        dec point position );
        • Date - Date of fiscal record in format DD-MM-YY;        
        */
        String res = cmdCustom(64, "0"+SEP);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // {ErrorCode}<SEP>{nRep}<SEP>{SumA}<SEP>{SumB}<SEP>{SumC}<SEP>{SumD}<SEP>{SumE}<SEP>{SumF}<SEP>{SumG}<SEP>{SumH}<SEP>{Date}<SEP>
        //       0           1          2           3          4          5          6          7          8          9         10
        String[] rData = getResponse(res, "Информация за последния фискален запис");
        String DocNum = "0000000";
        DocNum = (rData.length > 1)?rData[1]:"";
        response.put("DocNum", DocNum);
        response.put("TaxA", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("TaxB", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        response.put("TaxC", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
        response.put("TaxD", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
        response.put("TaxE", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
        response.put("TaxF", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
        response.put("TaxG", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
        response.put("TaxH", reformatCurrency((rData.length > 9)?rData[9]:"0", 1));
        String resDate = "";
        if (rData.length > 10) {
            String r = rData[10];
            if (r.length() == 8) {
                // DD-MM-YY
                // 01234567
                resDate = "20"+r.substring(6, 8)+"-"+r.substring(3, 5)+"-"+r.substring(0, 2);
            } else {
                //??
            }
        }
        response.put("DocDate", resDate);
        // Сторно операции
        res = cmdCustom(64, "2"+SEP);
        rData = getResponse(res, "Информация за последния фискален запис за сторно операции");
        response.put("RevTaxA", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("RevTaxB", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        response.put("RevTaxC", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
        response.put("RevTaxD", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
        response.put("RevTaxE", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
        response.put("RevTaxF", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
        response.put("RevTaxG", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
        response.put("RevTaxH", reformatCurrency((rData.length > 9)?rData[9]:"0", 1));
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
        Command: 69 (45h) Reports
        Parameters of the command:
        {ReportType}<SEP>
        Mandatory parameters:
        • ReportType - Report type;
        • 'X' - X report; Answer(1)
        • 'Z' - Z report; Answer(1)
        • 'D' - Departments report; Answer(2)
        • 'G' - Item groups report; Answer(2)
        Answer:
        {ErrorCode}<SEP>{nRep}<SEP>{TotA}<SEP>{TotB}<SEP>{TotC}<SEP>{TotD}<SEP>{TotE}<SEP>{TotF}<SEP>{TotG}<SEP>{TotH}
        <SEP>{StorA}<SEP>{StorB}<SEP>{StorC}<SEP>{StorD}<SEP>{StorE}<SEP>{StorF}<SEP>{StorG}<SEP>{StorH}<SEP>
        
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • nRep - Number of Z-report (1...3650);
        • TotX - Total sum accumulated by TAX group X - sell operations ( 0.00...9999999.99 or 0...999999999
        depending dec point position );
        • StorX - Total sum accumulated by TAX group X - storno operations ( 0.00...9999999.99 or
        0...999999999 depending dec point position );
        Answer(2):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        
        */
        if (!option.equals("X") && !option.equals("Z"))
            throw new FDException("Невалиден тип за дневен отчет! Z => Z отчет, X => X отчет");
        if (!subOption.equals("") && !subOption.equals("D"))
            throw new FDException("Невалиден подтип за дневен отчет! '' => обобщен, D => по департаменти");
        if (subOption.equals("D")) {
            String res = cmdCustom(69, "D"+SEP);
            if (res.contains(SEP)) {
                String[] rData = res.split(SEP);
                checkErrorCode(rData[0], "Отчет по департаменти.");
            } else 
                throw new FDException("Грешка при печат на дневен отчет по департаменти!");
        }

        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(69, option+SEP);
        String[] rData = getResponse(res, "Дневен отчет");
        response.put("RepNum", (rData.length > 1)?rData[1]:"");
        response.put("TaxA", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("TaxB", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        response.put("TaxC", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
        response.put("TaxD", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
        response.put("TaxE", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
        response.put("TaxF", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
        response.put("TaxG", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
        response.put("TaxH", reformatCurrency((rData.length > 9)?rData[9]:"0", 1));

        response.put("RevTaxA", reformatCurrency((rData.length > 10)?rData[10]:"0", 1));
        response.put("RevTaxB", reformatCurrency((rData.length > 11)?rData[11]:"0", 1));
        response.put("RevTaxC", reformatCurrency((rData.length > 12)?rData[12]:"0", 1));
        response.put("RevTaxD", reformatCurrency((rData.length > 13)?rData[13]:"0", 1));
        response.put("RevTaxE", reformatCurrency((rData.length > 14)?rData[14]:"0", 1));
        response.put("RevTaxF", reformatCurrency((rData.length > 15)?rData[15]:"0", 1));
        response.put("RevTaxG", reformatCurrency((rData.length > 16)?rData[16]:"0", 1));
        response.put("RevTaxH", reformatCurrency((rData.length > 17)?rData[17]:"0", 1));
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws IOException {
        /*
        Command: 90 (5Ah) Diagnostic information
        Parameters of the command:
        Syntax 1: {Param}<SEP>
        Optional parameters:
        • none - Diagnostic information without firmware checksum;
        Answer(1)
        • '1' - Diagnostic information with firmware checksum;
        Answer(1)
        Syntax 2: {Param}
        Optional parameters:
        • none - Diagnostic information without firmware checksum;
        Answer(2)
        • '1' -Diagnostic information with firmware checksum; Answer(2)
        Answer(1): {ErrorCode}<SEP>{Name}<SEP>{FwRev}<SEP>{FwDate}<SEP>{FwTime}<SEP>{Checksum}<SEP>{Sw}<SEP>{SerialNumber}<SEP>{FMNumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • Name - Device name ( up to 32 symbols ).
        • FwRev - Firmware version. 6 symbols;
        • FwDate - Firmware date DDMMMYY. 7 symbols;
        • FwTime - Firmware time hhmm. 4 symbols.
        • Checksum - Firmware checksum. 4 symbols;
        • Sw - Switch from Sw1 to Sw8. 8 symbols (not used at this device, always 00000000);
        • SerialNumber - Serial Number ( Two letters and six digits: XX123456);
        • {FMNumber} –Fiscal memory number (8 digits)
        Answer(2): {Name},{FwRev}{Sp}{FwDate}{Sp}{FwTime},{Checksum},{Sw},{SerialNumber},{FMNumber}
        • Name - Device name ( up to 32 symbols ).
        • FwRev - Firmware version. 6 symbols;
        • Sp - Space. 1 symbol;
        • FwDate - Firmware date DDMMMYY. 7 symbols;
        • FwTime - Firmware time hhmm. 4 symbols.
        • Checksum - Firmware checksum. 4 symbols;
        • Sw - Switch from Sw1 to Sw8. 8 symbols;
        • SerialNumber - Serial Number ( Two letters and six digits: XX123456);
        • {FMNumber} –Fiscal memory number (8 digits)        
        
        Command: 71 (47h) General information, modem test
        Parameters of the command:
        {InfoType}<SEP>
        Optional parameters:
        • InfoType - Type of the information printed. Default: 0;
        • '0' - General diagnostic information about the device;
        • '1' - test of the modem;
        • '2' - general information about the connection with NRA server;
        Answer(2)
        • '3' - print information about the connection with NRA server;
        • '4' - test of the LAN interface if present;
        • '6' - test of the SD card performance;
        • '9' - test of the Ble module ( if present );
        • '10' - test of the modem without PPP connection;
        • '11' - Send all unsent documents (command execution is accepted only once in every 5 minutes);
        Answer(1):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        Answer(2):
        {ErrorCode}<SEP>{LastDate}<SEP>{NextDate}<SEP>{Zrep}<SEP>{ZErrZnum}<SEP>{ZErrCnt}
        <SEP>{ZErrNum}<SEP>{SellErrnDoc}<SEP>{SellErrCnt}<SEP>{SellErrStatus}
        <SEP>SellNumber<SEP>SellDate<SEP>LastErr<SEP>RemMinutes<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • LastDate - Last connection to the server;
        • NextDate - Next connection to the server;
        • Zrep - Last send Z report;
        • ZErrZnum - Number of Z report with error;
        • ZErrCnt - Sum of all errors for Z reports;
        • ZErrNum - Error number from the server;
        • SellErrnDoc - Number of sell document with error;
        • SellErrCnt - Sum of all errors for sell documents;
        • SellErrStatus - Error number from the server;
        • SellNumber - Last received document number from the server;
        • SellDate - The date and time of last received document from the server;
        • LastErr- Last error from the server;
        • RemMinutes- Remaining minutes until next GetDeviceInfo request;        
        */
        // Request diagnostic info
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(90, "1"+SEP);
        // {ErrorCode}<SEP>{Name}<SEP>{FwRev}<SEP>{FwDate}<SEP>{FwTime}<SEP>{Checksum}<SEP>{Sw}<SEP>{SerialNumber}<SEP>{FMNumber}<SEP>
        //      0            1           2           3            4             5            6            7                8
        response.put("RawInfo", res);
        try {
            String[] rData = getResponse(res, "Диагностична информация");
            response.put("ModelName", (rData.length > 1)?rData[1]:"");
            response.put("FWRev", (rData.length > 2)?rData[2]:"");
            response.put("FWRevDT", (rData.length > 3)?rData[3]:""+((rData.length > 4)?" "+rData[4]:""));
            response.put("CheckSum", (rData.length > 5)?rData[5]:"");
            response.put("Switches", (rData.length > 6)?rData[6]:"");
            response.put("SerialNum", (rData.length > 7)?rData[7]:"");
            response.put("FMNum", (rData.length > 8)?rData[8]:"");
        } catch(Exception ex) {
            err(ex.getMessage());
        }

        // Request additional diagnostic information via cmd 71
        // Info about NAP terminal
        res = cmdCustom(71, "2"+SEP);
        /*
        {ErrorCode}<SEP>{LastDate}<SEP>{NextDate}<SEP>{Zrep}<SEP>{ZErrZnum}<SEP>{ZErrCnt}
        <SEP>{ZErrNum}<SEP>{SellErrnDoc}<SEP>{SellErrCnt}<SEP>{SellErrStatus}
        <SEP>SellNumber<SEP>SellDate<SEP>LastErr<SEP>RemMinutes<SEP>
        */        
        response.put("RawInfoNAPTerminal", res);
        try {
            String[] rData = getResponse(res, "Диагностична информация NAP терминал");
            response.put("LastDate", (rData.length > 1)?rData[1]:"");
            response.put("NextDate", (rData.length > 2)?rData[2]:"");
            response.put("ZRep", (rData.length > 3)?rData[3]:"");
            response.put("ZErrZnum", (rData.length > 4)?rData[4]:"");
            response.put("ZErrCnt", (rData.length > 5)?rData[5]:"");
            response.put("ZErrNum", (rData.length > 6)?rData[6]:"");
            response.put("SellErrnDoc", (rData.length > 7)?rData[7]:"");
            response.put("SellErrCnt", (rData.length > 8)?rData[8]:"");
            response.put("SellErrStatus", (rData.length > 9)?rData[9]:"");
            response.put("SellNumber", (rData.length > 10)?rData[10]:"");
            response.put("SellDate", (rData.length > 11)?rData[11]:"");
            response.put("LastErr", (rData.length > 12)?rData[12]:"");
            response.put("RemMinutes", (rData.length > 13)?rData[13]:"");
        } catch(Exception ex) {
            err(ex.getMessage());
        }
        return response;
    }


    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        Command: 71 (47h) General information, modem test
        Parameters of the command:
        {InfoType}<SEP>
        Optional parameters:
        • InfoType - Type of the information printed. Default: 0;
        • '0' - General diagnostic information about the device;
        • '1' - test of the modem;
        • '2' - general information about the connection with NRA server;
        Answer(2)
        • '3' - print information about the connection with NRA server;
        • '4' - test of the LAN interface if present;
        • '6' - test of the SD card performance;
        • '9' - test of the Ble module ( if present );
        • '10' - test of the modem without PPP connection;
        • '11' - Send all unsent documents (command execution is accepted only once in every 5 minutes);
        Answer(1):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        Answer(2):
        {ErrorCode}<SEP>{LastDate}<SEP>{NextDate}<SEP>{Zrep}<SEP>{ZErrZnum}<SEP>{ZErrCnt}
        <SEP>{ZErrNum}<SEP>{SellErrnDoc}<SEP>{SellErrCnt}<SEP>{SellErrStatus}
        <SEP>SellNumber<SEP>SellDate<SEP>LastErr<SEP>RemMinutes<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • LastDate - Last connection to the server;
        • NextDate - Next connection to the server;
        • Zrep - Last send Z report;
        • ZErrZnum - Number of Z report with error;
        • ZErrCnt - Sum of all errors for Z reports;
        • ZErrNum - Error number from the server;
        • SellErrnDoc - Number of sell document with error;
        • SellErrCnt - Sum of all errors for sell documents;
        • SellErrStatus - Error number from the server;
        • SellNumber - Last received document number from the server;
        • SellDate - The date and time of last received document from the server;
        • LastErr- Last error from the server;
        • RemMinutes- Remaining minutes until next GetDeviceInfo request;        
        */
        String res;
        try {
            res = cmdCustom(71, "0"+SEP); // General diagnostic information about the device
            getResponse(res, "Печат на основна диагностична информация");
        } catch(Exception ex) {
            err(ex.getMessage());
        }
        try {
            res = cmdCustom(71, "3"+SEP); // print information about the connection with NRA server;
            getResponse(res, "Диагностична информация на връзка с НАП");
        } catch(Exception ex) {
            err(ex.getMessage());
        }
    }

    @Override
    public String cmdLastDocNum() throws IOException {
        /*
        Command: 74 (4Ah) Reading the Status
        Parameters of the command:
        {Option}<SEP>
        Optional parameters:
        • Option - Type of information to return;
        • '0' - current receipt status; Answer(2)
        • '1' - Fiscal QRcode string - the contents of the QR code printed in the last fiscal document;
        Answer(3)
        Answer(1):
        {ErrorCode}<SEP>{StatusBytes}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • StatusBytes - Status Bytes ( See the description of the status bytes ).
        Answer(2):
        {ErrorCode}<SEP>{PrintBufferStatus}<SEP>{ReceiptStatus}<SEP>{Number}<SEP>{QRamount}<SEP>{QRnumber}<SEP>{QRdatetime}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • PrintBufferStatus - 0- empty buffer, no lines pending, 1-buffer is not empty;
        • ReceiptStatus - Status of the current receipt.
        • 0 - Receipt is closed;
        • 1 - Normal receipt is open;
        • 2 - Storno receipt is open. Reason "mistake by operator";
        • 3 - Storno receipt is open. Reason "refund";
        • 4 - Storno receipt is open. Reason "tax base reduction";
        • 5 - standart non-fiscal receipt is open;
        • Number - The number of the current or the last receipt (1...9999999);
        • QRamount - Fiscal QRcode - the amount of the last fiscal receipt;
        • QRnumber - Fiscal QRcode - the slip number of the last fiscal receipt (1...9999999);
        • QRdatetime - Fiscal QRcode - the date and time of the last fiscal receipt;
        Answer(3):
        {ErrorCode}<SEP>{QRCodeString}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • QRCodeString - the Fiscal QRcode string;        
        */
        String res = cmdCustom(74, "0"+SEP); // current receipt status; Answer(2)
        // {ErrorCode}<SEP>{PrintBufferStatus}<SEP>{ReceiptStatus}<SEP>{Number}<SEP>{QRamount}<SEP>{QRnumber}<SEP>{QRdatetime}<SEP>
        //       0                  1                    2                3             4              5               6
        String docNum = "";
        String[] rData = getResponse(res, "Диагностична информация");
        docNum = (rData.length > 3)?rData[3]:"";
        return docNum;
    }

    @Override
    public String cmdLastFiscalCheckQRCode() throws IOException {
        /*
        Command: 74 (4Ah) Reading the Status
        Parameters of the command:
        {Option}<SEP>
        Optional parameters:
        • Option - Type of information to return;
        • '0' - current receipt status; Answer(2)
        • '1' - Fiscal QRcode string - the contents of the QR code printed in the last fiscal document;
        Answer(3)
        Answer(1):
        {ErrorCode}<SEP>{StatusBytes}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • StatusBytes - Status Bytes ( See the description of the status bytes ).
        Answer(2):
        {ErrorCode}<SEP>{PrintBufferStatus}<SEP>{ReceiptStatus}<SEP>{Number}<SEP>{QRamount}<SEP>{Q
        Rnumber}<SEP>{QRdatetime}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • PrintBufferStatus - 0- empty buffer, no lines pending, 1-buffer is not empty;
        • ReceiptStatus - Status of the current receipt.
        • 0 - Receipt is closed;
        • 1 - Normal receipt is open;
        • 2 - Storno receipt is open. Reason "mistake by operator";
        • 3 - Storno receipt is open. Reason "refund";
        • 4 - Storno receipt is open. Reason "tax base reduction";
        • 5 - standart non-fiscal receipt is open;
        • Number - The number of the current or the last receipt (1...9999999);
        • QRamount - Fiscal QRcode - the amount of the last fiscal receipt;
        • QRnumber - Fiscal QRcode - the slip number of the last fiscal receipt (1...9999999);
        • QRdatetime - Fiscal QRcode - the date and time of the last fiscal receipt;
        Answer(3):
        {ErrorCode}<SEP>{QRCodeString}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • QRCodeString - the Fiscal QRcode string;        
        */
        String res = cmdCustom(74, "1"+SEP); // Fiscal QRcode string; Answer(3)
        // {ErrorCode}<SEP>{QRCodeString}<SEP>
        String[] rData = getResponse(res, "QR код на последния отпечатан документ!");
        return (rData.length > 1)?rData[1]:"";
    }

    
    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws IOException {
        /*
        Command: 74 (4Ah) Reading the Status
        Parameters of the command:
        {Option}<SEP>
        Answer(1):
        {ErrorCode}<SEP>{StatusBytes}<SEP>
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(74, "");
        String[] rData = getResponse(res, "Статус байтове");
        if (rData.length > 1) {
            byte[] bytes = rData[1].getBytes();
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                response.put("S"+Integer.toString(i), Integer.toBinaryString(b & 0xFF));
            }
        }    
        response.putAll(getStatusBytesAsText());
        /*
        byte[] bytes = res.getBytes();
        for (int i = 0; i < 6; i++) {
            byte b = 0;
            if (i < bytes.length)
                b = bytes[i];
            response.put("S"+Integer.toString(i), Integer.toBinaryString(b & 0xFF));
        }
        response.putAll(getStatusBytesAsText());
        */

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
        
        // Прочитане на параметъра за отпечатване на количество x цена при количество = 1.000
        // Cmd=255
        // Args=HideSingleItemInfoOnSells\t\t1\t
        try {
            res = cmdCustom(255, "HideSingleItemInfoOnSells"+SEP+SEP+SEP);
            rData = getResponse(res, "Проверка на стойността на HideSingleItemInfoOnSells");
            response.put("HideSingleItemInfoOnSells", (rData.length > 1)?rData[1]:"");
            if (response.get("HideSingleItemInfoOnSells").equals("0"))
                response.put("HideSingleItemInfoOnSells_HINT", "255,HideSingleItemInfoOnSells\\t\\t1\\t");
        } catch (Exception ex) {
            warn(ex.getMessage());
        }
        return response;
    }

    @Override
    public Date cmdGetDateTime() throws IOException {
        /*
        Command: 62 (3Eh) Read date and time
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{DateTime}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • DateTime - Date and time in format: "DD-MM-YY hh:mm:ss DST";
        • DD - Day;
        • MM - Month;
        • YY - Year;
        • hh - Hour;
        • mm - Minute;
        • ss - Second;
        • DST - Text "DST" if exist time is Summer time;
        */

        Date dt = null;
        String res = cmdCustom(62, "");
        String[] rData = getResponse(res, "Четене на дата час");
        if (rData.length > 1) {
            DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            try {
                dt = format.parse(rData[1].replace("\\s+DST\\s*", ""));
            } catch (ParseException ex) {
                throw new FDException("Грешка при четене на информация за дата/час ("+rData[1]+")!"+ex.getMessage());
            }
        } else {
            throw new FDException("Грешка при четене на дата/час!");
        }
        return dt;
    }
    
    @Override
    public void cmdSetDateTime(Date dateTime) throws IOException {
        /*
        Command: 61 (3Dh) Set date and time
        Parameters of the command:
        {DateTime}<SEP>
        Mandatory parameters:
        • DateTime - Date and time in format: "DD-MM-YY hh:mm:ss DST";
        • DD - Day;
        • MM - Month;
        • YY - Year;
        • hh - Hour;
        • mm - Minute;
        18
        Datecs FMP-350X, FMP-55X, FP-700X , FP-700XE Programmer’s Manual
        WP-500X, WP-50X, DP-25X, WP-25X, DP-150X, DP-05C
        • ss - Second;
        • DST - Text "DST" if exist time is Summer time;
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        */
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        String params = format.format(dateTime)+SEP;
        String res = cmdCustom(61, params);
        getResponse(res, "Установяване на дата/час");
    }

    @Override
    public void cmdPrintCheckDuplicate() throws IOException {
        /*
        Command: 109 (6Dh) Print duplicate copy of receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>
        */
        //TODO: Check on fiscalized device
        String res = cmdCustom(109, "");
        getResponse(res, "Отпечатване на дубликат");
    }

    @Override
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException {
        /*
        Command: 125 (7Dh) Information from EJ
        Parameters of the command:
        Syntax 1:
        {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
        Syntax 2 ( read CSV data):
        {Option}<SEP>{FirstDoc}<SEP>{LastDoc}<SEP>
        Syntax 3 ( read CSV data):
        {Option}<SEP>
        Syntax1: Mandatory parameters:
        • Option - Type of information;
        • '0' - Set document to read; Answer(1)
        • '1' - Read one line as text. Must be called multiple times to read the whole document; Answer(2)
        • '2' - Read as data. Must be called multiple times to read the whole document; Answer(3)
        • '3' - Print document; Answer(4)
        Syntax1: Optional parameters:
        • DocNum - Number of document (1...9999999). Needed for Option = 0.
        • RecType - Document type. Needed for Option = 0.
        • '0' - all types;
        • '1' - fiscal receipts;
        • '2' - daily Z reports;
        • '3' - invoice receipts;
        • '4' - nonfiscal receipts;
        • '5' - paidout receipts;
        • '6' - fiscal receipts - storno;
        • '7' - invoice receipts - storno;
        • '8' - cancelled receipts ( all voided );
        • '9' - daily X reports;
        • '10' - fiscal receipts, invoice receipts, fiscal receipts - storno and invoice receipts - storno;
        Syntax2: Mandatory parameters:
        • Option - Type of information;
        • '9' - Set document to read; Answer(1)
        • FirstDoc - First document in the period (1...99999999). Number received in response to command 124;
        • LastDoc - Last document in the period. (1...99999999). Number received in response to command 124;
        Syntax3: Mandatory parameters:
        • Option - Type of information;
        • '8' - Read as data. Must be called multiple times to read the whole document; Answer(5)
        Answer(1):
        {ErrorCode}<SEP>{DocNumber}<SEP>{RecNumber}<SEP>{Date}<SEP>{Type}<SEP>{Znumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • DocNumber - Number of document ( global 1...9999999 );
        • RecNumber - Number of document ( depending "Type" );
        • Date - Date of document, see DateTime format described at the beginning of the document;
        • Type - Type of document;
        • '0' - all types;
        • '1' - fiscal receipts;
        • '2' - daily Z reports;
        • '3' - invoice receipts;
        • '4' - non fiscal receipts;
        • '5' - paidout receipts;
        • '6' - fiscal receipts - storno;
        • '7' - invoice receipts - storno;
        • '8' - cancelled receipts ( all voided );
        • '9' - daily X reports;
        • Znumber- number of Z report (1...3650);
        Answer(2):
        {ErrorCode}<SEP>{TextData}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • TextData - Document text (up to 64 chars);
        Answer(3):
        {ErrorCode}<SEP>{Data}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • Data - Document data, structured information in base64 format. Detailed information in other document;
        Answer(4):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        Answer(5):
        {ErrorCode}<SEP>{CSV_Col_1}<SEP> ... {CSV_Col_14}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • CSV_Col_1 - идентификационен номер на ФУ;
        • CSV_Col_2 - вид на ФБ - ФБ, Разширен ФБ, Сторно ФБ или Разширен сторно ФБ;
        • CSV_Col_3 - номер на ФБ;
        • CSV_Col_4 - уникален номер на продажба (УНП) - в случай, че ФУ е от типа "Фискален принтер" или работи в такъв режим;
        • CSV_Col_5 - стока/услуга - наименование;
        • CSV_Col_6 - стока/услуга - единична цена;
        • CSV_Col_7 - стока/услуга - количество;
        • CSV_Col_8 - стока/услуга - стойност;
        • CSV_Col_9 - обща сума на т ФБ/Сторно ФБ или Разширен ФБ/Разширен сторно ФБ;
        • CSV_Col_10 - номер на фактура/кредитно известие - в случай че записът е за Разширен ФБ или съответно - за Разширен сторно ФБ;
        • CSV_Col_11 - ЕИК на получател - в случай че записът е за разширен ФБ или Разширен сторно ФБ;
        • CSV_Col_12 - номер на сторниран ФБ - в случай че записът се отнася за Сторно ФБ или Разширен сторно ФБ;
        • CSV_Col_13 - номер на сторнирана фактура - в случай че записът се отнася за Разширен сторно ФБ;
        • CSV_Col_14 - причина за издаване - в случай че записът се отнася за Сторно ФБ или Разширен сторно ФБ.        
        */
        // {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
        String params = 
                "3"
                +SEP+docNum
                +SEP+"0"
                +SEP;
        String res = cmdCustom(125, params);
        getResponse(res, "Печат на дубликат от КЛЕН");
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        /*
        Command: 94 (5Еh) Fiscal memory report by date
        Parameters of the command:
        {Type}<SEP>{Start}<SEP>{End}<SEP>
        Mandatory parameters:
        • Type - 0 - short; 1 - detailed;
        Optional parameters:
        • Start - Start date. Default: Date of fiscalization ( format DD-MM-YY );
        • End - End date. Default: Current date ( format DD-MM-YY );
        Answer:
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;        
        */
        //TODO: Check on fiscalized device
        DateFormat dtf = new SimpleDateFormat("dd-MM-yy");
        String params = 
                (detailed?"1":"0")
                +SEP+dtf.format(startDate)
                +SEP+((endDate != null)?dtf.format(endDate):"")
                +SEP;
        String res = cmdCustom(94, params);
        getResponse(res, "Отчет на фискалната памет за период");
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws IOException {
        /*
        Command: 103 (67h) Information for the current receipt
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{SumVATA}<SEP>{SumVATB}<SEP>{SumVATC}<SEP>{SumVATD}<SEP>{SumVATE}
        <SEP>{SumVATF}<SEP>{SumVATG}<SEP>{SumVATH}<SEP>{Inv}<SEP>{InvNum}<SEP>fStorno<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • SumVATx - The current accumulated sum on VATx ( 0.00...9999999.99 or 0...999999999 depending
        dec point position );
        • Inv - '1' if it is expanded receipt; '0' if it is simplified receipt;
        • InvNmb - Number of the next invoice (up to 10 digits)
        • fStorno - '1' if a storno receipt is open; '0' if it is normal receipt;
        
        Command: 76 (4Ch) Status of the fiscal transaction
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{IsOpen}<SEP>{Number}<SEP>{Items}<SEP>{Amount}<SEP>{Payed}<SEP>
         ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
         IsOpen
         0 - Receipt is closed;
         1 - Normal receipt is open;
         2 - Storno receipt is open. Reason "mistake by operator";
         3 - Storno receipt is open. Reason "refund";
         4 - Storno receipt is open. Reason "tax base reduction";
         5 - standard non-fiscal receipt is open;
         Number - The number of the current or the last receipt (1...9999999);
         Items - number of sales registered on the current or the last fiscal receipt (0...9999999);
         Amount - The sum from the current or the last fiscal receipt ( 0.00...9999999.99 or 0...999999999
        depending dec point position );
         Payed - The sum payed for the current or the last receipt ( 0.00...9999999.99 or 0...999999999
        depending dec point position );
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        response.put("TaxA", "0");
        response.put("TaxB", "0");
        response.put("TaxC", "0");
        response.put("TaxD", "0");
        response.put("TaxE", "0");
        response.put("TaxF", "0");
        response.put("TaxG", "0");
        response.put("TaxH", "0");
        response.put("Inv", "");
        response.put("InvNum", "");
        response.put("fStorno", "");
        response.put("IsOpen", "");
        response.put("Number", "");
        response.put("SellCount", "");
        response.put("SellAmount", "0");
        response.put("PayAmount", "0");
        boolean isOpen = false;
        boolean paymentInitiated = false;
        try {
          String res = cmdCustom(76, "");
          // {ErrorCode}<SEP>{IsOpen}<SEP>{Number}<SEP>{Items}<SEP>{Amount}<SEP>{Payed}<SEP>
          //      0              1            2           3           4            5 
          String[] rData = getResponse(res, "Статус на фискалната транзакция");
          response.put("IsOpen", (rData.length > 1)?rData[1]:"");
          response.put("Number", (rData.length > 2)?rData[2]:"");
          response.put("SellCount", (rData.length > 3)?rData[3]:"");
          response.put("SellAmount", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
          response.put("PayAmount", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
          isOpen = response.get("IsOpen").equals("1");
          paymentInitiated = Double.parseDouble(response.get("PayAmount")) > 0;
        } catch (Exception ex) {
            err(ex.getMessage());
        }
        if (isOpen && !paymentInitiated) {
            try {
                // Може да се изпълни само ако няма отворен бон и не е инициирано плащане
                String res = cmdCustom(103, "");
                // {ErrorCode}<SEP>{SumVATA}<SEP>{SumVATB}<SEP>{SumVATC}<SEP>{SumVATD}<SEP>{SumVATE}<SEP>{SumVATF}<SEP>{SumVATG}<SEP>{SumVATH}<SEP>{Inv}<SEP>{InvNum}<SEP>fStorno<SEP>
                //      0              1              2            3             4             5             6             7             8           9          10           11           
                String[] rData = getResponse(res, "Информация за текущ ФБ");
                response.put("TaxA", reformatCurrency((rData.length > 1)?rData[1]:"0", 1));
                response.put("TaxB", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
                response.put("TaxC", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
                response.put("TaxD", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
                response.put("TaxE", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
                response.put("TaxF", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
                response.put("TaxG", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
                response.put("TaxH", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
                response.put("Inv", (rData.length > 9)?rData[9]:"");
                response.put("InvNum", (rData.length > 10)?rData[10]:"");
                response.put("fStorno", (rData.length > 11)?rData[11]:"");
            } catch (Exception ex) {
                err(ex.getMessage());
            }
        }    
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdGetEJInfo() throws IOException {
        /*
        */
        throw new FDException("Неподдържана команда!");
    }

    @Override
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        /*
        Command: 124 (7Ch) Search receipt number by period
        Parameters of the command:
        {StartDate}<SEP>{EndDate}<SEP>{DocType}<SEP>
        Optional parameters:
        • StartDate - Start date and time for searching ( format "DD-MM-YY hh:mm:ss DST" ). Default: Date
        and time of first document;
        • EndDate - End date and time for searching ( format "DD-MM-YY hh:mm:ss DST" ). Default: Date and
        time of last document;
        Note
        See DateTime format described at the beginning of the document;
        • DocType - Type of document;
        • '0' - all types;
        • '1' - fiscal receipts;
        • '2' - daily Z reports;
        • '3' - invoice receipts;
        • '4' - non fiscal receipts;
        • '5' - paidout receipts;
        • '6' - fiscal receipts - storno;
        • '7' - invoice receipts - storno;
        • '8' - cancelled receipts ( all voided );
        • '9' - daily X reports;
        • '10' - fiscal receipts, invoice receipts, fiscal receipts - storno and invoice receipts - storno;
        Answer:
        {ErrorCode}<SEP>{StartDate}<SEP>{EndDate}<SEP>{FirstDoc}<SEP>{LastDoc}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • StartDate - Start date for searching, see DateTime format described at the beginning of the document;
        • EndDate - End date for searching, see DateTime format described at the beginning of the document;
        • FirstDoc - First document in the period. For DocType = '2' (1...3650), else (1...99999999);
        • LastDoc - Last document in the period. For DocType = '2' (1...3650), else (1...99999999);        
        */
        //TODO: Check on fiscalized device
        DateFormat dtf = new SimpleDateFormat("dd-MM-yy");
        // {StartDate}<SEP>{EndDate}<SEP>{DocType}<SEP>
        String params = 
            dtf.format(fromDate)+" 00:00:00"
            +SEP+dtf.format(toDate)+" 23:59:59"
            +SEP+"0"
            +SEP;
        // {ErrorCode}<SEP>{StartDate}<SEP>{EndDate}<SEP>{FirstDoc}<SEP>{LastDoc}<SEP>
        //      0               1              2             3             4
        String res = cmdCustom(124, params);
        String[] rData = getResponse(res, "Търсене на документ за период");
        return cmdReadEJ((rData.length > 3)?rData[3]:"", (rData.length > 4)?rData[4]:"");
    }

     public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        /*
        Command: 125 (7Dh) Information from EJ
        Parameters of the command:
        Syntax 1:
        {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
        Syntax 2 ( read CSV data):
        {Option}<SEP>{FirstDoc}<SEP>{LastDoc}<SEP>
        Syntax 3 ( read CSV data):
        {Option}<SEP>
        Syntax1: Mandatory parameters:
        • Option - Type of information;
        • '0' - Set document to read; Answer(1)
        • '1' - Read one line as text. Must be called multiple times to read the whole document; Answer(2)
        • '2' - Read as data. Must be called multiple times to read the whole document; Answer(3)
        • '3' - Print document; Answer(4)
        Syntax1: Optional parameters:
        • DocNum - Number of document (1...9999999). Needed for Option = 0.
        • RecType - Document type. Needed for Option = 0.
        • '0' - all types;
        • '1' - fiscal receipts;
        • '2' - daily Z reports;
        • '3' - invoice receipts;
        • '4' - nonfiscal receipts;
        • '5' - paidout receipts;
        • '6' - fiscal receipts - storno;
        • '7' - invoice receipts - storno;
        • '8' - cancelled receipts ( all voided );
        • '9' - daily X reports;
        • '10' - fiscal receipts, invoice receipts, fiscal receipts - storno and invoice receipts - storno;
        Syntax2: Mandatory parameters:
        • Option - Type of information;
        • '9' - Set document to read; Answer(1)
        • FirstDoc - First document in the period (1...99999999). Number received in response to command 124;
        • LastDoc - Last document in the period. (1...99999999). Number received in response to command 124;
        Syntax3: Mandatory parameters:
        • Option - Type of information;
        • '8' - Read as data. Must be called multiple times to read the whole document; Answer(5)
        Answer(1):
        {ErrorCode}<SEP>{DocNumber}<SEP>{RecNumber}<SEP>{Date}<SEP>{Type}<SEP>{Znumber}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • DocNumber - Number of document ( global 1...9999999 );
        • RecNumber - Number of document ( depending "Type" );
        • Date - Date of document, see DateTime format described at the beginning of the document;
        • Type - Type of document;
        • '0' - all types;
        • '1' - fiscal receipts;
        • '2' - daily Z reports;
        • '3' - invoice receipts;
        • '4' - non fiscal receipts;
        • '5' - paidout receipts;
        • '6' - fiscal receipts - storno;
        • '7' - invoice receipts - storno;
        • '8' - cancelled receipts ( all voided );
        • '9' - daily X reports;
        • Znumber- number of Z report (1...3650);
        Answer(2):
        {ErrorCode}<SEP>{TextData}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • TextData - Document text (up to 64 chars);
        Answer(3):
        {ErrorCode}<SEP>{Data}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • Data - Document data, structured information in base64 format. Detailed information in other document;
        Answer(4):
        {ErrorCode}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        Answer(5):
        {ErrorCode}<SEP>{CSV_Col_1}<SEP> ... {CSV_Col_14}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • CSV_Col_1 - идентификационен номер на ФУ;
        • CSV_Col_2 - вид на ФБ - ФБ, Разширен ФБ, Сторно ФБ или Разширен сторно ФБ;
        • CSV_Col_3 - номер на ФБ;
        • CSV_Col_4 - уникален номер на продажба (УНП) - в случай, че ФУ е от типа "Фискален принтер" или работи в такъв режим;
        • CSV_Col_5 - стока/услуга - наименование;
        • CSV_Col_6 - стока/услуга - единична цена;
        • CSV_Col_7 - стока/услуга - количество;
        • CSV_Col_8 - стока/услуга - стойност;
        • CSV_Col_9 - обща сума на т ФБ/Сторно ФБ или Разширен ФБ/Разширен сторно ФБ;
        • CSV_Col_10 - номер на фактура/кредитно известие - в случай че записът е за Разширен ФБ или съответно - за Разширен сторно ФБ;
        • CSV_Col_11 - ЕИК на получател - в случай че записът е за разширен ФБ или Разширен сторно ФБ;
        • CSV_Col_12 - номер на сторниран ФБ - в случай че записът се отнася за Сторно ФБ или Разширен сторно ФБ;
        • CSV_Col_13 - номер на сторнирана фактура - в случай че записът се отнася за Разширен сторно ФБ;
        • CSV_Col_14 - причина за издаване - в случай че записът се отнася за Сторно ФБ или Разширен сторно ФБ.        
         
         ErrorCode:
         -105000 ERR_EJ_NO_RECORDS EJ error: No records in EJ
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String EJ = "";
        Integer fromN = 0;
        Integer toN = 0;
        try {
            fromN = Integer.parseInt(fromDoc);
            if (toDoc.length() > 0) {
                toN = Integer.parseInt(toDoc);
            } else {
                toN = fromN;
            }    
        } catch (Exception e) {
            throw new IOException("Невалиден начален или краен номер номер на документ!"+e.getMessage());
        }
        if (fromN <= 0) 
            throw new IOException("Невалиден начален номер номер на документ!");
        toN = Integer.max(fromN, toN);
        String[] rData;
        for(int docN = fromN; docN <= toN; docN++) {
            // {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
            String params = "0"+SEP+docN+SEP+"0"+SEP; // Set first
            String res = "";
            try {
                res = cmdCustom(125, params);
                // {ErrorCode}<SEP>{DocNumber}<SEP>{RecNumber}<SEP>{Date}<SEP>{Type}<SEP>{Znumber}<SEP>
                //      0              1                2            3          4            5
                rData = getResponse(res, "Избор на документ за четене от КЛЕН", new long[]{-105000L});
            } catch (Exception e) {
                LOGGER.fine(e.getMessage());
                continue;
            }
            int maxLines = 2000; // for infinity protections
            while ((rData.length > 0) && rData[0].equals("0") && (maxLines > 0)) {
                params = "1"+SEP+docN+SEP+"0"+SEP; // Read line
                try {
                    res = cmdCustom(125, params);
                    // {ErrorCode}<SEP>{TextData}<SEP>
                    rData = getResponse(res, "Четене не ред от документ в КЛЕН", new long[]{-105000L});
                } catch (Exception e) {
                    LOGGER.fine(e.getMessage());
                    break;
                }
                if ((rData.length > 1) && rData[0].equals("0")) {
                    EJ = EJ + rData[1]+"\n";
                }
                maxLines--;
            }
        }
        response.put("EJ", EJ);
        return response;
     }
   
    @Override
    public LinkedHashMap<String, String> cmdReadDocInfo(String docNum) throws IOException {
        /*
        Command: 125 (7Dh) Information from EJ
        ...
        */
        return cmdReadEJ(docNum, docNum);
    }

    @Override
    public LinkedHashMap<String, String> cmdShortDocInfo(String docNum) throws IOException {
        throw new FDException("Не се поддържа!");
    }

    @Override
    public Date cmdLastFiscalCheckDate() throws IOException {
        /*
        7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        ...
        Отговор 1: <ErrCode>,<DocNumber>,<Date>,<Type>,<Znumber>
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        */
        String    docNum = cmdLastDocNum();
        Date docDate = null;
        // {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
        String params = "0"+SEP+docNum+SEP+"0"+SEP; // Set first
        String res = cmdCustom(125, params);
        // {ErrorCode}<SEP>{DocNumber}<SEP>{RecNumber}<SEP>{Date}<SEP>{Type}<SEP>{Znumber}<SEP>
        //      0              1                2            3          4            5
        String[] rData = getResponse(res, "Избор на документ за четене от КЛЕН");
        if (rData.length > 3) {
            DateFormat formatGet = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            try {
                docDate = formatGet.parse(rData[3].replace("\\s+DST\\s*",""));
            } catch (Exception e) {}
        }
        return docDate;
    }
 
    @Override
    public LinkedHashMap<String, String> cmdReadPaymentMethods() throws IOException {
        /*
        Пример от DP-25X
        P_0='0' - 'В БРОЙ' Разрешен:0 НАП #:0
        P_1='1' - 'КРЕДИТ' Разрешен:0 НАП #:7
        P_2='2' - 'ДЕБ.КАРТА' Разрешен:0 НАП #:7
        P_3='3' - 'ЧЕК' Разрешен:0 НАП #:1
        P_4='4' - 'ВАУЧЕР' Разрешен:0 НАП #:3
        P_5='5' - 'КУПОН' Разрешен:0 НАП #:2        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap(); // 32 31 3B 31 3B
        // Определяне на начина на плащане по номенклатура на НАП
        String prefix = new String(new byte[] {(byte)0x84});
        //new String(new byte[] { (byte)0x84, (byte)0x32, (byte)0x31, (byte)0x3B, (byte)0x31, (byte)0x3B });
        String params; 
        String res;
        String[] rData;
        String paymentCode;
        String paymentName;
        String paymentNRA;
        String paymentEnabled;
        for(int i = 0; i < 6; i++) {
            paymentCode = Integer.toString(i);
            paymentName = "";
            paymentNRA = "";
            paymentEnabled = "";
            try {
                params = "PayName"+SEP+paymentCode+SEP+SEP;
                res = cmdCustom(255, params);
                rData = getResponse(res, "Начин на плащане "+paymentCode);
                paymentName = rData[1];
            } catch (Exception ex) {
                err(ex.getMessage());
            }
            try {
                params = "PYxx_Server"+SEP+paymentCode+SEP+SEP;
                res = cmdCustom(255, params);
                rData = getResponse(res, "Начин на плащане НАП "+paymentCode);
                paymentNRA = rData[1];
            } catch (Exception ex) {
                err(ex.getMessage());
            }
            try {
                params = "Payment_forbidden"+SEP+paymentCode+SEP+SEP;
                res = cmdCustom(255, params);
                rData = getResponse(res, "Начин на плащане разр./забр. "+paymentCode);
                paymentEnabled = rData[1];
            } catch (Exception ex) {
                err(ex.getMessage());
            }
            response.put("P_"+paymentCode, "'"+paymentCode+"' - '"+paymentName+"' Разрешен:"+paymentEnabled+" НАП #:"+paymentNRA);
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadDepartments() throws IOException {
        /*
        Command: 88 (58h) Get department information
        Parameters of the command:
        {Department}<SEP>
        Optional parameters:
        • Department - Number of department (1...99); If Department is empty - department report;
        Answer:
        {ErrorCode}<SEP>{TaxGr}<SEP>{Price}<SEP>{TotSales}<SEP>{TotSum}<SEP>{STotSales}<SEP>{STotSum}<SEP>{Name}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • TaxGr - Tax group of department;
        • Price - Price of department;
        • TotSales - Number of sales for this department for day;
        • TotSum - Accumulated sum for this department for day;
        • STotSales - Number of storno operations for this department for day;
        • STotSum - Accumulated sum from storno operations for this department for day;
        • Name - Name of the department;        
        */
        class TotInfo {
            double totQty = 0;
            double totSum = 0;
            String totName = "";
            public TotInfo(double TotSales, double TotSum) {
                this.totQty = TotSales;
                this.totSum = TotSum;
            }
        }
        class DeptInfo {
            String deptNum;
            String deptName;
            String taxGr;
            double price;
            TotInfo[] totals;

            public DeptInfo(String deptNum, String deptName, String taxGr) {
                this.deptNum = deptNum;
                this.deptName = deptName;
                this.taxGr = taxGr;
                this.price = 0;
                this.totals = new TotInfo[2];
                for(int i = 0; i < this.totals.length; i++)
                    this.totals[i] = new TotInfo(0, 0);
                this.totals[0].totName = "Продажби";
                this.totals[1].totName = "Сторно";
            }
            
        }
        
        LinkedHashMap<String, String> response = new LinkedHashMap(); // 32 31 3B 31 3B
        int DeptCount = 99;
        DeptInfo[] Depts = new DeptInfo[DeptCount];
        String res;
        String[] rData;
        for (int i = 0; i < DeptCount; i++) {
            String deptNum = Integer.toString(i+1);
            Depts[i] = null;
            try {
                res = cmdCustom(88, deptNum+SEP);
                // {ErrorCode}<SEP>{TaxGr}<SEP>{Price}<SEP>{TotSales}<SEP>{TotSum}<SEP>{STotSales}<SEP>{STotSum}<SEP>{Name}<SEP>
                //       0            1           2            3             4              5              6            7
                rData = getResponse(res, "Прочитане на департамент "+deptNum);
                Depts[i] = new DeptInfo(deptNum, "-", "-");
                Depts[i].taxGr = (rData.length > 1)?TaxCdToTaxGroup(rData[1]):"";
                if (rData.length > 2)
                   Depts[i].price = stringToDouble(rData[2]); 
                if (rData.length > 3)
                   Depts[i].totals[0].totQty = stringToDouble(rData[3]); 
                if (rData.length > 4)
                   Depts[i].totals[0].totSum = stringToDouble(rData[4]); 
                if (rData.length > 5)
                   Depts[i].totals[1].totQty = stringToDouble(rData[5]); 
                if (rData.length > 6)
                   Depts[i].totals[1].totSum = stringToDouble(rData[6]); 
                if (rData.length > 7)
                   Depts[i].deptName = rData[7].replace("\n", "\\n");
            } catch(Exception ex) {
                
            }
        }
        // Формиране на резултата
        for (int i = 0; i < DeptCount; i++) {
            if (Depts[i] != null) {
                response.put("D_"+Depts[i].deptNum, 
                        Depts[i].deptNum+" Група:"+Depts[i].taxGr+" Име:"+Depts[i].deptName
                        +" Цена:"+String.format(Locale.ROOT, "%.2f", Depts[i].price)
                );
            }    
        }
        // Формиране на резултата по натрупани суми
        for (int i = 0; i < DeptCount; i++) {
            if (Depts[i] != null) {
                String sInfo = Depts[i].deptNum;
                for(int j = 0; j < Depts[i].totals.length; j++)
                    sInfo = sInfo 
                      + " "
                      + Depts[i].totals[j].totName+"="
                      + Double.toString(Depts[i].totals[j].totQty)
                      + ":" + Double.toString(Depts[i].totals[j].totSum);
                response.put("DS_"+Depts[i].deptNum, sInfo);
            }
        }
        return response; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap<String, String> cmdReadTaxGroups() throws IOException {
        /*
        Command: 50 (32h) Return the active VAT rates
        Parameters of the command:
        none
        Answer:
        {ErrorCode}<SEP>{nZreport}<SEP>{TaxA}<SEP>{TaxB}<SEP>{TaxC}<SEP>{TaxD}<SEP>{TaxE}
        <SEP>{TaxF}<SEP>{TaxG}<SEP>{TaxH}<SEP>{EntDate}<SEP>
        • ErrorCode - Indicates an error code. If command passed, ErrorCode is 0;
        • nZreport - Number of first Z report;
        • TaxX - Value of Tax group X (0.00...99.99 taxable,100.00=disabled);
        • EntDate - Date of entry ( format DD-MM-YY );        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap(); 
        String res = cmdCustom(50, "");
        String[] rData = getResponse(res, "Данъчни групи");
        // {ErrorCode}<SEP>{nZreport}<SEP>{TaxA}<SEP>{TaxB}<SEP>{TaxC}<SEP>{TaxD}<SEP>{TaxE}<SEP>{TaxF}<SEP>{TaxG}<SEP>{TaxH}<SEP>{EntDate}<SEP>
        //      0              1             2          3          4          5          6          7          8          9          10
        response.put("TaxA", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
        response.put("TaxB", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
        response.put("TaxC", reformatCurrency((rData.length > 4)?rData[4]:"0", 1));
        response.put("TaxD", reformatCurrency((rData.length > 5)?rData[5]:"0", 1));
        response.put("TaxE", reformatCurrency((rData.length > 6)?rData[6]:"0", 1));
        response.put("TaxF", reformatCurrency((rData.length > 7)?rData[7]:"0", 1));
        response.put("TaxG", reformatCurrency((rData.length > 8)?rData[8]:"0", 1));
        response.put("TaxH", reformatCurrency((rData.length > 9)?rData[9]:"0", 1));
        return response; //To change body of generated methods, choose Tools | Templates.
    }

    
}
