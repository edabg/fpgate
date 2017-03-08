package org.eda.fiscal.device;

import com.taliter.fiscal.device.FiscalDeviceIOException;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * ICLFiscalPrinter interface describes the basic operations of ICL protocol.
 */
public interface ICLFiscalPrinter {
        
    /**
     * Open a fiscal check.
     * @param operatorNum Operator's number.
     * @param password Operator's password.
     * @param invoice - if it's true invoice is issued.
     * @return Returns the number of fiscal and non fiscal checks as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String operatorNum, String password, boolean invoice) throws FiscalDeviceIOException;

    /**
     * Close the fiscal check.
     * @return Returns the number of fiscal and non fiscal checks as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws FiscalDeviceIOException;

    /**
     * Print a fiscal text. Fiscal check should be opened first. 
     * @param text The text that will be printed.
     * @throws FiscalDeviceIOException
     */
    public void cmdPrintFiscalText(String text) throws FiscalDeviceIOException;

    /**
     * Sale. A fiscal check should be opened first.
     * @param text Sale description text.
     * @param sellDescription Additional sale description text.
     * @param taxGroup One symbol for tax group (А, Б, В, Г, Д, Е, Ж, З).
     * @param price Price (to 8 digits).
     * @param quantity Quantity of sale (to 8 digits).
     * @param discount The value of discount/add (depends on the sign).
     * @param inPercent Determines whether the value of discount is in percent or netto.
     * @throws FiscalDeviceIOException
     */
    public void cmdSell(String text, String sellDescription, String taxGroup, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException;

    /**
     * Sale by department. A fiscal check should be opened first.
     * @param text Sale description text.
     * @param sellDescription Additional sale description text.
     * @param dept Department number (2 digits).
     * @param price - Price (to 8 digits).
     * @param quantity - Quantity of sale (to 8 digits).
     * @param discount The value of discount/add (depends on the sign).
     * @param inPercent Determines whether the value of discount is in percent or netto.
     * @throws FiscalDeviceIOException
     */
    public void cmdSellDept(String text, String sellDescription, String dept, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException;
    
    /**
     * Cancel the check.
     * @return Returns the number of all non fiscal and fiscal checks as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdCancelFiscalCheck() throws FiscalDeviceIOException;

    /**
     * Print a duplicate of the last fiscal check.
     * @throws FiscalDeviceIOException
     */
    public void cmdPrintCheckDuplicate() throws FiscalDeviceIOException;       

    /**
     * Opens non fiscal check.
     * @return Returns the number of fiscal and non fiscal checks as LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws FiscalDeviceIOException;

    /**
     * Close the non fiscal check.
     * @return Returns the number of fiscal and non fiscal checks as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws FiscalDeviceIOException;

    /**
     * On receipt of this command fiscal device prints a non fiscal text. Non fiscal check should be opened to be executed this command. 
     * @param text The text that will be printed.
     * @throws FiscalDeviceIOException
     */
    public void cmdPrintNonFiscalText(String text) throws FiscalDeviceIOException;

    /**
     * The subtotal of the fiscal check.
     * @param toPrint if it's true the subtotal is printed.
     * @param toDisplay if it's true the subtotal is displayed on the fiscal device display.
     * @param discountPercent [Optional] The discount/add in percent.
     * @return Returns the subtotal.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, double discount, boolean inPercent) throws FiscalDeviceIOException;

    /**
     * Prints the total of the fiscal check.
     * @param firstRowText Text of the first row.
     * @param secondRowText Text of the second row
     * @param paymentType [Optional] Payment type: "P" - in cash;
     * @param amount [Optional] Amount for payment.
     * @return Returns the result of command execution and payment information as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdTotal(String firstRowText, String secondRowText, String paymentType, double amount) throws FiscalDeviceIOException;

    /**
     * This command shows whether it is possible to correct registered sales, and information on the accumulated turnovers in the individual tax groups.
     * @return Returns received data as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws FiscalDeviceIOException;

    /**
     * On receipt of this command fiscal device returns information about the last daily report in the fiscal memory.
     * @return Returns received data as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws FiscalDeviceIOException;

    /**
     *  Prints diagnostic information of the fiscal device.
     * @throws FiscalDeviceIOException
     */
    public void cmdPrintDiagnosticInfo() throws FiscalDeviceIOException;

    /**
     *  Last number of the printed document.
     * @return Last document number.
     * @throws FiscalDeviceIOException
     */
    public int cmdLastDocNum()  throws FiscalDeviceIOException;

    /**
     *  Fiscal device status.
     * @return Returns the fiscal device status as a LinkedHashMap.
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdPrinterStatus() throws FiscalDeviceIOException;

    /**
     *  Diagnostic information.
     * @return Returns the diagnostic information of fiscal device as a LinkedHashMap
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws FiscalDeviceIOException;

    /**
     * Get the fiscal device's date and time.
     * @return Returns date and time of fiscal device.
     * @throws FiscalDeviceIOException
     */
    public Date cmdGetDateTime() throws FiscalDeviceIOException;

    /**
     * Sets date and time of fiscal device. The date can't be earlier than last entry in the fiscal memory. 
     * @param year The year part of the date
     * @param month The month part of the date
     * @param day The day part of the date
     * @param hour The hour part of the time
     * @param minute The minute part of the time
     * @param second The second part of the time
     * @throws FiscalDeviceIOException
     */
    public void cmdSetDateTime(int year, int month, int day, int hour, int minute, int second) throws FiscalDeviceIOException;

    /**
     * Prints daily report.
     * @param type The report type. Values: 0 or 1 - Z report; 2 or 3 - X report; 8 - Z report by departments; 9 - X report by departments
     * @param toReset Specifies whether to reset Z report or not.
     * @return
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdReportDaily(String type, boolean toReset) throws FiscalDeviceIOException;

    /**
     *  Prints a report by dates. Date format: DDMMYY
     * @param detailed if it's true the report is extended
     * @param startDate start date of report
     * @param endDate end date of report
     * @throws FiscalDeviceIOException
     */
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws FiscalDeviceIOException;

    /**
     *  Paper feed.
     * @param lines number of lines
     * @throws FiscalDeviceIOException
     */
    public void cmdPaperFeed(int lines) throws FiscalDeviceIOException;

    /**
     *  Sets operator's name. This command can't be executed if the sales by this operator are not reset.
     * @param clerkNum operator's number
     * @param password operator's password
     * @param name operator's new name
     * @throws FiscalDeviceIOException
     */
    public void cmdSetOperator(String clerkNum, String password, String name) throws FiscalDeviceIOException;

    /**
     *  Executes custom command.
     * @param cmd the command code
     * @param params the command parameters
     * @return Returns the response as a string.
     * @throws FiscalDeviceIOException
     */
    public String customCmd(int cmd, String params) throws FiscalDeviceIOException;

    /**
     *  Resets the sales by operator.
     * @param clerkNum operator's number
     * @param password operator's password
     * @throws FiscalDeviceIOException
     */
    public void  cmdResetByOperator(String clerkNum, String password) throws FiscalDeviceIOException;

    /**
     * Get the fiscal device constants.
     * @return Returns the fiscal device constants as a LinkedHashMap
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdGetConstants() throws FiscalDeviceIOException;
    
    /**
     * Register Cash in or Cash out
     * @param ioSum Cash amount depending of sign +In/-Out
     * @return
     * @throws FiscalDeviceIOException
     */
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws FiscalDeviceIOException;
}
