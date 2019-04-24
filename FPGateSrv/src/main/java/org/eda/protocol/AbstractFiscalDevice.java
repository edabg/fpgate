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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eda.fdevice.FPCBase;

/**
 * Abstract Fiscal Device
 * @author Dimitar Angelov
 */
public abstract class AbstractFiscalDevice {

    protected static final Logger logger = Logger.getLogger(AbstractFiscalDevice.class.getName());

    protected AbstractProtocol protocol;
    
    protected String mDeviceInfo;  // Device info in raw format returned from device
    protected String mModelName;   // Device/Model name parsed from mDeviceInfo
    protected String mSerialNum;   // Device Serian Number
    protected String mFMNum;       // Device Fiscal Memory Serial Number
    protected String mFWRev;       // Firmware Revision
    protected String mFWRevDT;     // Firmware Revision Date/Time
    protected String mSwitches;    // Switches configuration
    protected byte[] mStatusBytes; // Status bytes
    protected int    mLastCmd;     // Code of last executed command
    
    protected boolean mClearErrors = true; // Clear error before execution of cmdCustom

    /**
     * Status Bytes Definition
     */
    protected LinkedHashMap<String, String[]> statusBytesDef;
    
    /**
     * Describes which bits in status bytes indicate warnings
     */
    protected LinkedHashMap<String, byte[]> warningStatusBits;
    
    /**
     * Describes which bits in status bytes indicate for errors
     */
    protected LinkedHashMap<String, byte[]> errorStatusBits;

    // Errors and warnings collector
    protected List<String> mErrors = new ArrayList<>();;
    protected List<String> mWarnings = new ArrayList<>();;

    /**
     * Create device to user externally created protocol
     * @param p Fiscal DeviceTransport Protocol
     */
    public AbstractFiscalDevice(AbstractProtocol p) {
        protocol = p;
        initPrinterStatusMap();
    }
    
    public AbstractFiscalDevice(String portName, int baudRate, int readTimeout, int writeTimeout) {
        protocol = null;
        initPrinterStatusMap();
    }

    public static Logger getLogger() {
        return logger;
    }
    
    public void open() throws IOException {
        if (protocol == null)
            throw new IOException("Protocol is not initialized!");
        if (!protocol.isOpen()) {
            try {
                protocol.open();
                readDeviceInfo();
            } catch (Exception ex) {
                this.close();
                throw ex;
            }
        }
    }
    
    public void close() throws IOException {
        if (protocol != null)
            protocol.close();
        protocol = null;
    }
    
    /**
     * This method have to read information from device and set device info properties.
     * ModelName, SerialNum, FMNum, FWRev, FWRevDT and Switches configuration
     * @throws java.io.IOException
     */
    protected abstract void readDeviceInfo() throws IOException ;

    
    /**
     * Number of characters for fiscal text
     * @return 
     */
    public int getLineWidthFiscalText() {
        return 0;
    }
    
    /**
     * Number of characters for non-fiscal text
     * @return 
     */
    public int getLineWidthNonFiscalText() {
        return 0;
    }
    
    protected double stringToDouble(String str) {
        return stringToDouble(str, 1);
    }
    protected double stringToDouble(String str, int div) {
        double res = 0;
        try {
            res = Double.parseDouble(str);
            if (div > 0)
                res = res / div;
        } catch (Exception e) {
        }
        return res;
    }
    
    protected String reformatCurrency(String value, int div) {
        double dval = stringToDouble(value, div);
        return String.format(Locale.ROOT, "%.2f",dval);
    }
    
    
    public final String cmdCustom(int command, String data) throws IOException {
        mLastCmd = command;
        String result = protocol.customCommand(command, data);
        mStatusBytes = protocol.getStatusBytes();
        checkErrors(mClearErrors);
        return result;
    }

    /**
     * Return value of property clear errors on every command
     * @return 
     */
    public boolean getClearErrors() {
        return mClearErrors;
    }

    /**
     * Set value of property clear errors on every command
     * @param clearErrors 
     */
    public void setClearErrors(boolean clearErrors) {
        this.mClearErrors = clearErrors;
    }

    
    /**
     *  Fiscal Device Info in Raw format as returned from it
     * @return 
     */
    public final String getDeviceInfo() {
        return mDeviceInfo;
    }
    
    /**
     * Fiscal Device Model Name
     * @return 
     */
    public final String getModelName() {
        return mModelName;
    }
    
    /**
     * Fiscal Device Serial Number
     * @return 
     */
    public final String getSerialNum() {
        return mSerialNum;
    }
    
    /**
     * Fiscal Memory Number of the device
     * @return 
     */
    public final String getFMNum() {
        return mFMNum;
    }
    
    /**
     * Firmware Revision of Fiscal Device
     * @return 
     */
    public final String getFWRev() {
        return mFWRev;
    }

    /**
     * Firmware Revision Date/Time of Fiscal Device
     * @return 
     */
    public final String getFWRevDT() {
        return mFWRevDT;
    }
    
    /**
     * Get Device Configuration Switches
     * @return 
     */
    public final String getSwitches() {
        return mSwitches;
    }
    
    /**
     * Device Status Bytes After Last Command
     * @return 
     */
    public final byte[] getStatusBytes() {
        return mStatusBytes;
    }
    
    /**
     * Initialize printer status bytes meaning.
     * Define which bits from status bytes are critical errors.
     */
    protected void initPrinterStatusMap() {
        statusBytesDef = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
    }
    
    /**
     * Returns hash map with status bytes definition
     * @return 
     */
    public HashMap<String, String[]> getStatusBytesDef() {
        return statusBytesDef;
    }
    
    public LinkedHashMap<String,String> getStatusBytesAsText() {

        LinkedHashMap<String,String> res = new LinkedHashMap<>();
        int byteNum = 0;
        for(String s : statusBytesDef.keySet()) {
            int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
            String[] bitDefs = statusBytesDef.get(s);
            for (int i = 0; i < min(bitDefs.length, 8); i++) {
                if (bitDefs[i].length() > 0) {
                    if (((b & 0xFF) & (0x01 << i)) != 0) {
                        res.put("Msg_"+s+"_"+Integer.toString(i), bitDefs[i]);
                        
                    }
                }
            }
            byteNum++;
        };   
        return res;
    }
    
    protected void clearErrors() {
        mErrors.clear();
        mWarnings.clear();
    }
    
    
    protected void err(String msg) {
        mErrors.add(msg);
        logger.severe("Error (cmd:"+Integer.toString(mLastCmd)+"): "+msg);
    }

    protected void warn(String msg) {
        mWarnings.add(msg);
        logger.warning("Warning (cmd:"+Integer.toString(mLastCmd)+"): "+msg);
    }
    
    protected void debug(String msg) {
        logger.fine("Debug (cmd:"+Integer.toString(mLastCmd)+"): "+msg);
    }
    
    /**
     * Check for errors and warnings in status bytes
     * @param clear Clears previous messages
     */
    protected void checkErrors(boolean clear) {
        if (clear) clearErrors();
        int byteNum = 0;
        for(String s : statusBytesDef.keySet()) {
            int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
            byte[] ebits = errorStatusBits.get(s);
            for(int bi = 0; bi < ebits.length; bi++) {
                if (((b & 0xFF) & (0x01 << ebits[bi])) != 0) {
                    String errMsg = statusBytesDef.get(s)[ebits[bi]];
                    err(errMsg);
                }
            }
            byte[] wbits = warningStatusBits.get(s);
            for(int bi = 0; bi < wbits.length; bi++) {
                if (((b & 0xFF) & (0x01 << wbits[bi])) != 0) {
                    String warnMsg = statusBytesDef.get(s)[wbits[bi]];
                    warn(warnMsg);
                }
            }
            byteNum++;
        }
    }
    
    /**
     * Check for errors and warnings without clear previous ones
     */
    protected void checkErrors() {
        checkErrors(false);
    }

    /**
     * There are errors after last executed command
     * @return 
     */
    public boolean haveErrors() {
        return !mErrors.isEmpty();
    }
        
    /**
     * Return errors after last executed command
     * @return 
     */
    public List<String> getErrors() {
        return mErrors;
    }
    
    /**
     * There are warnings after last executed command
     * @return 
     */
    public boolean haveWarnings() {
        return !mErrors.isEmpty();
    }
        
    /**
     * Return warnings after last executed command
     * @return 
     */
    public List<String> getWarnings() {
        return mWarnings;
    }

    // Follow Application related commands

    /**
     * Open fiscal check receipt

     * @param opCode Operator code 1..30
     * @param opPasswd Operator password from 1 to 8 digits
     * @param wpNum Number of work place 1..99999
     * @param UNS Unique Number of Sell
     * @param invoice Open extended fiscal check (invoice)
     * @return <AllReceipt>,<FiscalReceipt>
     * @throws FDException in case of commend error
     */
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String opCode, String opPasswd, String wpNum, String UNS, boolean invoice) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * 
     * @param opCode Operator code 1..30
     * @param opPasswd Operator password from 1 to 8 digits
     * @param wpNum Number of work place 1..99999
     * @param UNS Unique Number of Sell
     * @param RevType Type of Revision/Storno. Depends of device.For example: 0 - Operator error, 1 - Return/Claim, 2 - Reduce of amount
     * @param RevDocNum Document Number subject of revision
     * @param RevUNS UNS of document subject of revision
     * @param RevDateTime Date/Time of document subject of revision
     * @param RevFMNum Fiscal Memory Number in which is registered document subject of revision
     * @param RevReason Reason for revision
     * @param RevInvNum Invoice number of document subject of revision if it was invoice.
     * @param invoice Invoice Credit note
     * @return <AllReceipt>,<StrReceipt>
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Get information about last fiscal record
     * The device have to be fiscalized for successful result!
     * @return Information about last fiscal record number. Tax group sums.
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Prints customer data in invoice fiscal check
     * @param UIC - UIC of buyer
     * @param UICType - UIC Type 0 - Bulstat, 1 - EGN, 2- LN4, 3 - Srv. Number
     * @param seller - The name of seller
     * @param recipient - Recipient name
     * @param buyer - Buyer company name
     * @param VATNum - VAT Number
     * @param address - buyer address
     */
    public void cmdPrintCustomerData(String UIC, int UICType, String seller, String recipient, String buyer, String VATNum, String address)  throws IOException  {
        
    }
    
    /**
     * Close fiscal check
     * @return <AllReceipt>,<FiscalReceipt>
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Cancel opened fiscal check
     */
    public void cmdCancelFiscalCheck() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Print fiscal text. 
     * @param text Text to print. Maximum length of text depends from Fiscal device
     * @throws FDException 
     */
    public void cmdPrintFiscalText(String text) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Open non fiscal check
     * @return
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Close non fiscal check
     * @return
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Print non fiscal text
     * @param text Text to print. Maximum length of text depends from Fiscal device
     * @param font Font size by number as 1,2,3 increasing size 
     * @throws IOException 
     */
    public void cmdPrintNonFiscalText(String text, int font) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Print non fiscal text with default font
     * @param text Text to print. Maximum length of text depends from Fiscal device
     * @throws IOException 
     */
    public void cmdPrintNonFiscalText(String text) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Perform linefeed
     * @param lineCount - count of lines
     * @throws java.io.IOException
     */
    public void cmdPaperFeed(int lineCount) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Register sell by tax group.
     * @param sellText Text to print. Can contain \n for second line
     * @param taxGroup Letter for taxGroup. Depends of configuration of the device
     * @param price Price for one unit
     * @param quantity Quantity. If , 0 assume 1.
     * @param unit Optional unit of quantity
     * @param discount Discount as percent or absolute value. Example: for abs value 20, for percent 20%
     * @throws IOException 
     */
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Register Sell by department
     * @param sellText Text to print. Can contain \n for second line
     * @param dept Department code. Depends of Fiscal Device configuration.
     * @param price Price for one unit. 
     * @param quantity Quantity
     * @param unit Optional unit of quantity
     * @param toDisplay Display on sell on display if attached
     * @throws IOException
     */
    public void cmdSellDept(String sellText, String dept, double price, double quantity, String unit, String discount) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Calculate subtotal in fiscal check
     * @param toPrint Print subtotal
     * @param toDisplay Display subtotal
     * @param discount Discount as percent or absolute value. Example: for abs value 20, for percent 20%
     * @return Return String List beginning with SubTotal value and values by tax groups - TaxA, TaxB ... 
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, String discount) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Create total for fiscal check and register payment.
     * @param totalText Text to print on total line
     * @param paymentType Type of payment. Depends of fiscal device configuration. 
     *        By default:
     *        P - In cache
     *        N - in credit
     *        C - Pay with debit card
     *        D - Pay with NZOK
     *        I - Voucher
     *        J - Coupon
     * @param amount Paid amount
     * @param pinpadOpt `Pinpad` payment option if supported by device. Valid in some combinations with paymentType. Usually with payment with debit card
     * @return PaidCode and Rest Amount
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Cash In or Out
     * @param ioSum The Amount In/Out. If amount is positive there is Cach In, in case of negative Cash Out. If is zero get current Amount in cash.
     * @return <CashSum><CashIn><CashOut>
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Set operator Name
     * @param opCode Operator Code
     * @param opPasswd Operator Password
     * @param name Operator Name
     * @throws IOException 
     */
    public void cmdSetOperator(String opCode, String opPasswd, String name) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Start daily report
     * @param option The type of report Z/X depends of device.
     * @param subOption Sub option. For example print summary by departments.
     * @return Sums in report
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdReportDaily(String option, String subOption) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Get Device Diagnostic Information. 
     * @return Model, Serial Number, Fiscal Memory number, Firmware information ...
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Print diagnostic information.
     * @throws IOException 
     */
    public void cmdPrintDiagnosticInfo() throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     *  Read number of last (printed) document
     * @return The number of last printed document
     * @throws IOException 
     */
    public String cmdLastDocNum() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Return last fiscal check date
     * @return
     * @throws IOException 
     */
    public Date cmdLastFiscalCheckDate() throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Return short information about document.
     * DocNum, DateTime, DocType, ZNumber
     * @param docNum - if is empty get for last printer document
     * @return
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdShortDocInfo(String docNum) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Read information about last (printed) document
     * @return The number of last printed document
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdReadDocInfo(String docNum) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Return printer status information.
     * @return ??? Depends of fiscal device.
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdPrinterStatus() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Return current date/time of fiscal device
     * @return
     * @throws IOException 
     */
    public Date cmdGetDateTime() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Sets current date/time of fiscal device
     * @param dateTime
     * @throws IOException 
     */
    public void cmdSetDateTime(Date dateTime) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Print duplicate of last fiscal check
     * @throws IOException 
     */
    public void cmdPrintCheckDuplicate() throws IOException{
        throw new FDException("Unsupported command");
    }

    /**
     * Print duplicate fiscal check from EJ
     * @throws IOException 
     */
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException{
        throw new FDException("Unsupported command");
    }
    
    /**
     * Prints FM report by dates
     * @param detailed detailed of short type
     * @param startDate Start date
     * @param endDate End date. Can be null to be unspecified end.
     * @throws IOException 
     */
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Request current fiscal check info
     * @return
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws IOException {
        throw new FDException("Unsupported command");
    }

    /**
     * Get EJ Information
     * @return
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdGetEJInfo() throws IOException {
        throw new FDException("Unsupported command");
    }

    
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        throw new FDException("Unsupported command");
    }

    public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        throw new FDException("Unsupported command");
    }
    
    /**
     * Return QRCode of last fiscal Check
     * @return
     * @throws IOException 
     */
    public String cmdLastFiscalCheckQRCode() throws IOException {
        throw new FDException("Unsupported command (cmdLastFiscalCheckQRCode)");
    }
    
    /**
     * Return information of fiscal status of the device
     * @return 
     */
    public boolean isFiscalized() {
        return false;
    }
    
    /**
     * Return true if is open fiscal check
     * @return 
     */
    public boolean isOpenFiscalCheck() {
        return false;
    }

    /**
     * Return true if is open fiscal check rev/storno
     * @return 
     */
    public boolean isOpenFiscalCheckRev() {
        return false;
    }
    
    /**
     * Return true if is open non fiscal check
     * @return 
     */
    public boolean isOpenNonFiscalCheck() {
        return false;
    }
    
}    
