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

import java.io.IOException;
import static java.lang.System.currentTimeMillis;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import org.reflections.Reflections;
import org.restlet.Application;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCBase {

    protected final FPParams params;
    private static FPCBaseClassList FPCList = null;
    
    // Payment Types
    public static enum paymentTypes {
        CASH, CREDIT, CHECK, DEBIT_CARD
        , CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4
    }
    
    public static final HashMap<String, paymentTypes> paymentTypeAbbr = new HashMap<>(); static {
        paymentTypeAbbr.put("CASH", FPCBase.paymentTypes.CASH);
        paymentTypeAbbr.put("CREDIT", FPCBase.paymentTypes.CREDIT);
        paymentTypeAbbr.put("CHECK", FPCBase.paymentTypes.CHECK);
        paymentTypeAbbr.put("DEBIT_CARD", FPCBase.paymentTypes.DEBIT_CARD);
        paymentTypeAbbr.put("CUSTOM1", FPCBase.paymentTypes.CUSTOM1);
        paymentTypeAbbr.put("CUSTOM2", FPCBase.paymentTypes.CUSTOM2);
        paymentTypeAbbr.put("CUSTOM3", FPCBase.paymentTypes.CUSTOM3);
        paymentTypeAbbr.put("CUSTOM4", FPCBase.paymentTypes.CUSTOM4);
    }
    
    public static enum taxGroup {
        A, B, C, D, E, F, G, H
    }
    
    public static final HashMap<String, taxGroup> taxGroupAbbr = new HashMap<>(); static {
        taxGroupAbbr.put("A", FPCBase.taxGroup.A);
        taxGroupAbbr.put("B", FPCBase.taxGroup.B);
        taxGroupAbbr.put("C", FPCBase.taxGroup.C);
        taxGroupAbbr.put("D", FPCBase.taxGroup.D);
        taxGroupAbbr.put("E", FPCBase.taxGroup.E);
        taxGroupAbbr.put("F", FPCBase.taxGroup.F);
        taxGroupAbbr.put("G", FPCBase.taxGroup.G);
        taxGroupAbbr.put("H", FPCBase.taxGroup.H);
    }
    
    public static enum dailyReportType {
        X, Z
    }

    public static final HashMap<String, dailyReportType> dailyReportTypeAbbr = new HashMap<>(); static {
        dailyReportTypeAbbr.put("X", FPCBase.dailyReportType.X);
        dailyReportTypeAbbr.put("Z", FPCBase.dailyReportType.Z);
    }

    public static enum datesReportType {
        SHORT, DETAIL
    }
    
    public static final HashMap<String, datesReportType> datesReportTypeAbbr = new HashMap<>(); static {
        datesReportTypeAbbr.put("SHORT", FPCBase.datesReportType.SHORT);
        datesReportTypeAbbr.put("S", FPCBase.datesReportType.SHORT);
        datesReportTypeAbbr.put("DETAIL", FPCBase.datesReportType.DETAIL);
        datesReportTypeAbbr.put("D", FPCBase.datesReportType.DETAIL);
    }
    
    public FPCBase() throws Exception {
        this.params = getDefinedParams(this.getClass());
    }
    
    protected static class localLogger {
        public static void log(Level level, String msg) {
            Application.getCurrent().getLogger().log(level, msg);
        }
        
        public static void msg(String msg) {
            log(Level.INFO, msg);
        }
        
        public static  void error(String msg) {
            log(Level.SEVERE, msg);
        }
        
        public static  void warning(String msg) {
            log(Level.WARNING, msg);
        }

        public static  void debug(String msg) {
            log(Level.FINER, msg);
        }
    }
    
    /**
     * 
     * @return List of Descendant classes of FPCBase
     */
    public static FPCBaseClassList getDerivedClasses() {
        if (FPCList == null) {
            FPCList = new FPCBaseClassList();
            // Use tree map to be sorted
            TreeMap<String, Class<? extends FPCBase>> tmpList = new TreeMap<>();
            Reflections reflections = new Reflections("org.eda.fpsrv");
            Set<Class<? extends FPCBase>> classes = reflections.getSubTypesOf(FPCBase.class);
            Iterator iter = classes.iterator();
            Class<? extends FPCBase> fpc;
            while (iter.hasNext()) {
              fpc = (Class<? extends FPCBase>)iter.next();
              tmpList.put(fpc.getSimpleName(), fpc);
            }
            FPCList.putAll(tmpList);
        }
        return FPCList;
    }
    

    public static String getClassDecription() {
        return "Abstact Class for device independent Fiscal Printer!";
    }

    public static final String getClassDecription(Class<? extends FPCBase> clazz) throws Exception {
        Method m;
        m = clazz.getMethod("getClassDecription");
        return ((String)m.invoke(null));
    }

    public static final String getClassDecription(String FPCClassName) throws Exception {
        if (!FPCBase.getDerivedClasses().containsKey(FPCClassName))
            return FPCBase.getClassDecription();
//           throw new FPException((long)1, "Unsupported Fiscal Printer Class:"+FPCClassName);
        else
            return FPCBase.getClassDecription(FPCBase.getDerivedClasses().get(FPCClassName));
    }
    
    public static FPParams getDefinedParams() throws Exception {
        return new FPParams();
    }

    public static final FPParams getDefinedParams(Class<? extends FPCBase> clazz) throws Exception {
        Method m;
        m = clazz.getMethod("getDefinedParams");
        return ((FPParams)m.invoke(null));
    }

    public static final FPParams getDefinedParams(String FPCClassName) throws Exception {
        if (!FPCBase.getDerivedClasses().containsKey(FPCClassName))
            return FPCBase.getDefinedParams();
//           throw new FPException((long)1, "Unsupported Fiscal Printer Class:"+FPCClassName);
        else
            return FPCBase.getDefinedParams(FPCBase.getDerivedClasses().get(FPCClassName));
    }
    
    public static final FPCBase getFPCObject(Class<? extends FPCBase> clazz) throws Exception {
        return clazz.newInstance();
    }

    public static final FPCBase getFPCObject(String FPCClassName) throws Exception {
        if (!FPCBase.getDerivedClasses().containsKey(FPCClassName))
           throw new FPException((long)1, "Unsupported Fiscal Printer Class:"+FPCClassName);
        return FPCBase.getFPCObject(FPCBase.getDerivedClasses().get(FPCClassName));
    }

    public void setParams(String jsonString) throws IOException {
        params.setFromJSONString(jsonString);
    }
    public void setParams(HashMap<String, String> params_) throws IOException {
        params.setFromList(params_);
    }
    
    public <T> void setParam(String param, T value) {
        params.setValue(param, value);
    }
    
    public String getParam(String param) {
        return params.get(param);
    }
    
    public Integer getParamInt(String param) {
        return params.getInt(param);
    }
    
    public Double getParamDouble(String param) {
        return params.getDouble(param);
    }

    protected int getLineWidth() {
       return 0; 
    }
    
    
    public static class PrintedStringUtils  {
        public static enum PaddingType {
            LEFT, RIGHT, CENTER
        }

        public static ArrayList<String> splitInChnunks(String str, int chunkSize) {
            ArrayList<String> res = new ArrayList<>();
            if (chunkSize < 1) chunkSize = str.length();
            int length = str.length();
            for(int i = 0; i < length; i+=chunkSize) 
                res.add(str.substring(i, Math.min(length, i+chunkSize)));
            return res;
        }
        
        public static String rep(String sym, int repCount) {
            return new String(new char[repCount]).replace("\0", sym);
        }

        public static ArrayList<String> pad(String str, int width, PaddingType padType, String padSym) {
            if (padSym.length() > 1) padSym = padSym.substring(0,0);
            else if (padSym.length() == 0) padSym = " ";
            ArrayList<String> slist = splitInChnunks(str, width);
            if (slist.size() == 0) slist.add("");
            String lastLine = slist.get(slist.size()-1);
            int padCount = (width - lastLine.length());
            if (padCount > 0) {
                switch (padType) {
                    case LEFT :
                        lastLine = lastLine + rep(padSym, padCount);
                        break;
                    case RIGHT :
                        lastLine = rep(padSym, padCount)+lastLine;
                        break;
                    case CENTER :
                        int lpad = (width-lastLine.length())/2;
                        int rpad = (width-lastLine.length())-lpad;
                        if (lpad > 0)
                            lastLine = lastLine + rep(padSym, lpad);
                        if (rpad > 0)
                            lastLine = rep(padSym, rpad)+lastLine;
                        break;
                }
            }
            slist.set(slist.size()-1, lastLine);
            return slist;
        }

        public static ArrayList<String> pad(String str, int width, PaddingType padType) {
            return pad(str, width, padType, " ");
        }

        public static ArrayList<String> pad(String str, int width) {
            return pad(str, width, PaddingType.LEFT);
        }
        
        public static ArrayList<String> labelValue(String label, String value, int width) {
            int minLabelWidth = 5;
            int valueWidth = Math.min(value.length()+1, width);
            int labelWidth = width-valueWidth;
            ArrayList<String> res = new ArrayList<>();
            if (labelWidth < minLabelWidth) {
                // print firsl label left aliagned next value right aligned
                res.addAll(splitInChnunks(label, width));
                res.addAll(pad(value, width, PaddingType.RIGHT));
            } else {
                ArrayList<String> llist = pad(label, labelWidth, PaddingType.LEFT, " ");
                ArrayList<String> vlist = pad(value, valueWidth, PaddingType.RIGHT, " ");
                int lCount = llist.size();
                // Add empty lines before value
                while (vlist.size() < lCount)
                    vlist.add(0, rep(" ", valueWidth));
                int vCount = vlist.size();
                int i = 0;
                for(; i < lCount; i++)
                    res.add(llist.get(i)+vlist.get(i));
                for(; i < vCount; i++)
                    res.add(rep(" ", labelWidth)+vlist.get(i));
            }
            return res;
        }
    }
    
    /**
     * 
     * @param str
     * @return Split 'str' on lines by LineWidth each
     */
    protected String[] splitOnLines(String str, int width) {
        String[] textParts;
        textParts = str.split("\\t");
        ArrayList<String> res = null;
        switch (textParts[0]) {
            case "@padl" :
            case "@padr" :
            case "@padc" :
                PrintedStringUtils.PaddingType padType = PrintedStringUtils.PaddingType.LEFT;
                switch (textParts[0]) {
                    case "@padl" :
                        padType = PrintedStringUtils.PaddingType.LEFT;
                        break;
                    case "@padr" :
                        padType = PrintedStringUtils.PaddingType.RIGHT;
                        break;
                    case "@padc" :
                        padType = PrintedStringUtils.PaddingType.CENTER;
                        break;
                }
                if (textParts.length > 1) {
                    if (textParts.length > 2) 
                        res = PrintedStringUtils.pad(textParts[1], width, padType, textParts[2]);
                    else    
                        res = PrintedStringUtils.pad(textParts[1], width, padType);
                }
                break;
            case "@lval" :
                if (textParts.length > 1) {
                    if (textParts.length > 2) 
                        res = PrintedStringUtils.labelValue(textParts[1], textParts[2], width);
                    else    
                        res = PrintedStringUtils.labelValue(textParts[1], "", width);
                }
                break;
            default :
                res = PrintedStringUtils.splitInChnunks(str, width);
        }
        return res.toArray(new String[res.size()]);
    }
    
    protected String[] splitOnLines(String str) {
        return splitOnLines(str, getLineWidth());
    }

    public static taxGroup taxAbbrToGroup(String abbr) {
        if (taxGroupAbbr.containsKey(abbr))
            return taxGroupAbbr.get(abbr);
        else
            return taxGroup.A;
    }
    
    public static paymentTypes payAbbrToType(String abbr) {
        if (paymentTypeAbbr.containsKey(abbr))
            return paymentTypeAbbr.get(abbr);
        else
            return paymentTypes.CASH;
    }
    
    public static dailyReportType dailyReportTypeAbbrToType(String abbr) {
        if (dailyReportTypeAbbr.containsKey(abbr))
            return dailyReportTypeAbbr.get(abbr);
        else
            return dailyReportType.X;
    }
    
    public static datesReportType datesReportTypeAbbrToType(String abbr) {
        if (datesReportTypeAbbr.containsKey(abbr))
            return datesReportTypeAbbr.get(abbr);
        else
            return datesReportType.DETAIL;
    }
    
    /**
     * Get Error code from Last executed command
     * 
     * @return Error code from last command
     */
    public Long getLastErrorCode() {
        return (long)0;
    }
    
    /**
     * Get Error message from Last executed command
     * 
     * @return Error message from last command
     */
    public String getLastErrorMessage() {
        return "";
    }
    
    /**
     * init fiscal device depending on params.
     * Must to override for particular model.
     * @throws java.lang.Exception
     * @throws org.eda.fpsrv.FPException
     */
    public void init() throws Exception, FPException {
        
    }
    
    /**
     * Destroys initialized printer
     */
    public void destroy() {
        
    }
    
    /**
     * Returns device diagnostic info
     * 
     * @return Diagnostic info as String HashMap
     */
    public StrTable getDiagnosticInfo() throws FPException{
        return null;
    }
    
    /**
     * Prints diagnostic information
     * @throws org.eda.fpsrv.FPException
     */
    public void printDiagnosticInfo() throws FPException {
        
    }
    
    /**
     * Returns the number of last printed fiscal document.
     * 
     * @return Last Fiscal Document Number
     * @throws org.eda.fpsrv.FPException
     */
    public String getLastPrintDoc() throws FPException {
        
        return "";
    }

    /**
     * Returns last fiscal record information
     * 
     * @return Last fiscal record info as String HashMap
     * @throws org.eda.fpsrv.FPException
     */
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return null;
    }
    
    /**
     * Cancels Current Fiscal check (if opened)
     * @throws org.eda.fpsrv.FPException
     */
    public void cancelFiscalCheck() throws FPException {
    }
    
    /**
     * Opens fiscal check in case of error throws exception
     * @throws org.eda.fpsrv.FPException
     */
    public void openFiscalCheck() throws FPException {
        
    }
    
    /**
     * Print Fiscal text.
     * Must be opened fiscal check first
     * @param text - text to pe printed
     * @throws org.eda.fpsrv.FPException
     */
    public void printFiscalText(String text) throws FPException {
        
    }

    /**
     * Print non Fiscal text.
     * Must be opened fiscal check first
     * @param text - text to pe printed
     * @throws org.eda.fpsrv.FPException
     */
    public void printNonFiscalText(String text) throws FPException {
        
    }
    
    /**
     * Perform linefeed
     * @param lineCount - count of lines
     */
    public void paperFeed(int lineCount) throws FPException {
        
    }

    
    /**
     * Register sell by Tax Code with quantity
     * 
     * @param text      - text to print
     * @param taxCode   - valid tax code
     * @param price     - price
     * @param quantity  - quantity
     * @param discountPerc - discount percent
     * @throws org.eda.fpsrv.FPException
     */
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        
    }

    /**
     * Register sell by Tax Code with quantity
     * 
     * @param text      - text to print
     * @param taxCode   - valid tax code
     * @param price     - price
     * @param discountPerc - discount percent
     * @throws org.eda.fpsrv.FPException
     */
    public void sell(String text, taxGroup taxCode, double price, double discountPerc)  throws FPException {
        
    }
    
    /**
     * Register sell by department
     * 
     * @param text      - text to print
     * @param deptCode  - department code
     * @param price     - price
     * @param quantity  - quantity
     * @param discountPerc - discount in percent
     * @throws org.eda.fpsrv.FPException
     */
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        
    }

    public void sellDept(String text, String deptCode, double price) throws FPException {
        this.sellDept(text, deptCode, price, 1, 0);
    }
    
    /**
     * Prints subtotal and returns Subtotal values by tax groups
     * @param toPrint - to print subtotal
     * @param toDisplay - show on display 
     * @param discountPerc - print discount percent  
     * @return Subtotal values by Tax groups
     * @throws org.eda.fpsrv.FPException
     */
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        return null;
    }
    
    public StrTable subTotal() throws FPException {
        return this.subTotal(true, true, 0);
    }

    /**
     * Execute final total and register payment
     * @param text      - text to print
     * @param payType   - payment type cash/credit card
     * @param payAmount - Amount of payment
     * @return Information about current fiscal check
     * @throws org.eda.fpsrv.FPException
     */
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        return null;
    }

    
    /**
     * Close fiscal check
     */
    public void closeFiscalCheck() throws FPException{
        
    }

    /**
     * Opens non fiscal check in case of error throws exception
     * @throws org.eda.fpsrv.FPException
     */
    public void openNonFiscalCheck() throws FPException {
        
    }

    /**
     * Close non fiscal check
     */
    public void closeNonFiscalCheck() throws FPException{
        
    }
    
    /**
     * Force closing or cancel opened fiscal check if needed 
     * to return printer in ready state
     */
    public void abnormalComplete() throws FPException {
        
    }
    
    /**
     * Retrieve information about current fiscal check
     * @return Table with current fiscal check information
     */
    public StrTable getCurrentCheckInfo() throws FPException {
        return null;
    }
    
    /**
     * Sets operator
     * @param code      - operator code
     * @param passwd    - operator password
     * @param fullName  - operator full name
     * @throws org.eda.fpsrv.FPException
     */
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        
    }
    
    /**
     * Prints duplicate of last fiscal check
     */
    public void printLastCheckDuplicate() throws FPException {
        
    }
    
    /**
     * Prints daily report and return totals
     * @param reportType - X or Z report
     * @return Report Totals by groups
     */
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        return null;
    }

    /**
     * Prints fiscal memory report by dates
     * @param from          - from date
     * @param to            - to date
     * @param reportType    - report type
     * @return Report totals
     */
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException  {
       return null; 
    }
    
    /**
     * Retrieve journal in file
     * @param readAndErase  - Erase journal after successful read
     * @return CRC, SHA1 controls of journal
     */
    public StrTable getJournal(boolean readAndErase) throws FPException {
        return null;
    }

    public StrTable getJournalInfo() throws FPException{
        return null;
    }

    /**
     * Get printer status fields
     * @return Status fields
     */
    public StrTable getPrinterStatus() throws FPException {
        return null;
    }
    
    /**
     * Execute custom printer command
     * @param CMD       - command
     * @param params    - parameters of command
     * @return Result of command
     * @throws org.eda.fpsrv.FPException
     */
    public String customCmd(String CMD, String params) throws FPException{
        return "";
    }

    /**
     * Gets fiscal printer time
     * @return
     * @throws FPException 
     */
    public Date getDateTime() throws FPException {
        return new Date();
    }
    
    /**
     * Sets  fiscal printer time and return it.
     * @param dateTime
     * @return
     * @throws FPException 
     */
    public Date setDateTime(java.util.Date dateTime) throws FPException {
        return new Date();
    }
    
    /**
     * Register Cash In/Out
     * @param ioSum positive number makes Cash In, negative Cash Out
     *              Zero value return Accumulated Cash In/Out
     * @return
     * @throws FPException
     */
    public StrTable cashInOut(Double ioSum) throws FPException {
        return null;
    }
}

