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
package org.eda.fdevice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.reflections.Reflections;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCBase {

    protected static final Logger logger = Logger.getLogger(FPCBase.class.getName());

    public static Logger getLogger() {
        return logger;
    }
    
    protected final FPParams params;
    private static FPCBaseClassList FPCList = null;
    
    // Extended Fiscal Check (invoice) mode/info
    protected boolean invoiceMode = false;
    protected CustomerInfo customer = null;

    public static enum UICTypes {
        BULSTAT, EGN, FOREIGN, NRA
    }
    
    public static class FiscalCheckQRCodeInfo {
        public String QRCode = "";
        public String FMNum = "";
        public String DocNum = "";
        public Date DocDateTime = null;
        public Double Sum = 0d;
        protected boolean isNative = false;
        
        public FiscalCheckQRCodeInfo() {
            
        }

        public FiscalCheckQRCodeInfo(String QR) {
            QRCode = QR;
            String[] qr_parts = QR.split("\\*");
            // 50970007*000456*2019-03-05*14:39:33*0.00
            // FMNum*DocNum*DocDate*DocTime*Sum
            if (qr_parts.length > 3) {
                FMNum = qr_parts[0];
                DocNum = qr_parts[1];
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    DocDateTime = fmt.parse(qr_parts[2] + " " + qr_parts[3]);
                } catch (Exception ex) {
                    logger.severe(ex.getMessage());
                }
                try {
                    Sum = Double.parseDouble(qr_parts[4]);
                    isNative = true;
                } catch (Exception ex) {
                    logger.severe(ex.getMessage());
                }
            }
        }

        public FiscalCheckQRCodeInfo(String FMNum_, String DocNum_, Date DocDateTime_, Double Sum_) {
            FMNum = FMNum_;
            DocNum = DocNum_;
            DocDateTime = DocDateTime_;
            Sum = Sum_;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd*HH:mm:ss");
            QRCode = FMNum+"*"+DocNum+"*"+((DocDateTime != null)?fmt.format(DocDateTime):"0000-00-00 00:00:00")+"*"+String.format(Locale.ROOT, "%.2f", Sum);
        }
        
        public StrTable toStrTable() {
            return toStrTable("");
        }
        public StrTable toStrTable(String prefix) {
            StrTable res = new StrTable();
            res.put(prefix+"QR_FMNum", FMNum);
            res.put(prefix+"QR_DocNum", DocNum);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            res.put(prefix+"QR_DocDate", (DocDateTime != null)?fmt.format(DocDateTime):"");
            res.put(prefix+"QR_Sum", String.format(Locale.ROOT, "%.2f", Sum));
            res.put(prefix+"QR_Code", QRCode);
            res.put(prefix+"QR_IsNative", isNative?"1":"0");
            return res;
        }
    }
    
    public static class CustomerInfo {
        public String recipient = "";
        public String buyer = "";
        public String address = "";
        public String VATNumber = "";
        public String UIC = "";
        public UICTypes UICType = UICTypes.BULSTAT;
        public String seller = "";
        
        public void setUICtypeStr(String sUICType) {
            if (sUICType.equals("BULSTAT"))
                UICType = UICTypes.BULSTAT;
            else if (sUICType.equals("EGN"))
                UICType = UICTypes.EGN;
            else if (sUICType.equals("FOREIGN"))
                UICType = UICTypes.FOREIGN;
            else if (sUICType.equals("NRA"))
                UICType = UICTypes.NRA;
            else {
                logger.severe("Unknown UICType("+sUICType+"). Valid values: BULSTAT, EGN, FOREIGN, NRA. BULSTAT will be used!");
                UICType = UICTypes.BULSTAT;
            }    
        }
    }
    
    // Payment Types
    public static enum paymentTypes {
        CASH, CREDIT, CHECK, DEBIT_CARD
        , CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4
        , UNSPECIFIED
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
        paymentTypeAbbr.put("", FPCBase.paymentTypes.UNSPECIFIED);
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
    
    public static enum fiscalCheckRevType {
        OP_ERROR, RETURN_CLAIM, REDUCTION
    }

    public static final HashMap<String, fiscalCheckRevType> fiscalCheckRevTypeAbbr = new HashMap<>(); static {
        fiscalCheckRevTypeAbbr.put("ERR", fiscalCheckRevType.OP_ERROR);
        fiscalCheckRevTypeAbbr.put("RET", fiscalCheckRevType.RETURN_CLAIM);
        fiscalCheckRevTypeAbbr.put("RED", fiscalCheckRevType.REDUCTION);
    }

    public static class DeviceInfo {
        public String Model = "";        // Model
        public String SerialNum = "";    // Serial Number
        public String FMNum = "";        // Fiscal memory number
        public String Firmware = "";     // Firmware
        public Boolean IsFiscalized = false;//Fiscalization Status
        public Boolean IsReady = false;     // Ready for operations
        public String Errors = "";       // Error information
        public String Warnings = "";     // Warnings information
        
        public StrTable toStrTable() {
            return toStrTable("DEV_");
        }
        public StrTable toStrTable(String prefix) {
            StrTable res = new StrTable();
            res.put(prefix+"Model", Model);
            res.put(prefix+"SerialNum", SerialNum);
            res.put(prefix+"FMNum", FMNum);
            res.put(prefix+"Firmware", Firmware);
            res.put(prefix+"IsFiscalized", IsFiscalized?"1":"0");
            res.put(prefix+"IsReady", IsReady?"1":"0");
            res.put(prefix+"Errors", Errors);
            res.put(prefix+"Warnings", Warnings);
            return res;
        }
    }

    public static enum fiscalCheckType {
        UNKNOWN, FISCAL, FISCAL_REV, NON_FISCAL
    }

    public static final HashMap<fiscalCheckType, String> fiscalCheckTypeAbbr = new HashMap<>(); static {
        fiscalCheckTypeAbbr.put(fiscalCheckType.UNKNOWN, "UNKNOWN");
        fiscalCheckTypeAbbr.put(fiscalCheckType.FISCAL, "FISCAL");
        fiscalCheckTypeAbbr.put(fiscalCheckType.FISCAL_REV, "FISCAL_REV");
        fiscalCheckTypeAbbr.put(fiscalCheckType.NON_FISCAL, "NON_FISCAL");
    }

    
    public static class CheckInfo {
        public Double TaxA = 0d;
        public Double TaxB = 0d;
        public Double TaxC = 0d;
        public Double TaxD = 0d;
        public Double TaxE = 0d;
        public Double TaxF = 0d;
        public Double TaxG = 0d;
        public Double TaxH = 0d;
        public Integer SellCount = 0;
        public Double SellAmount = 0d;
        public Double PayAmount = 0d;
        public String DocNum = "";
        public String InvNum = "";
        public Boolean IsOpen = false;
        public Boolean IsInvoice = false;
        public fiscalCheckType FCType = fiscalCheckType.UNKNOWN;
        
        public StrTable toStrTable() {
            return toStrTable("");
        }
        protected String formatCurrency(Double value) {
            return String.format(Locale.ROOT, "%.2f", value);
        }
        public StrTable toStrTable(String prefix) {
            StrTable res = new StrTable();
            res.put(prefix+"TaxA", formatCurrency(TaxA));
            res.put(prefix+"TaxB", formatCurrency(TaxB));
            res.put(prefix+"TaxC", formatCurrency(TaxC));
            res.put(prefix+"TaxD", formatCurrency(TaxD));
            res.put(prefix+"TaxE", formatCurrency(TaxE));
            res.put(prefix+"TaxF", formatCurrency(TaxF));
            res.put(prefix+"TaxG", formatCurrency(TaxG));
            res.put(prefix+"TaxH", formatCurrency(TaxH));

            res.put(prefix+"SellCount", SellCount.toString());
            res.put(prefix+"SellAmount", formatCurrency(SellAmount));
            res.put(prefix+"PayAmount", formatCurrency(PayAmount));
            res.put(prefix+"DocNum", DocNum);
            res.put(prefix+"InvNum", InvNum);
            
            res.put(prefix+"IsOpen", IsOpen?"1":"0");
            res.put(prefix+"IsInvoice", IsInvoice?"1":"0");
            res.put(prefix+"FCType", fiscalCheckTypeAbbr.get(FCType));
            return res;
        }
    }

    public static class FiscalRecordInfo {
        public String DocNum = "";
        public Date DocDate = null;
        public Double TaxA = 0d;
        public Double TaxB = 0d;
        public Double TaxC = 0d;
        public Double TaxD = 0d;
        public Double TaxE = 0d;
        public Double TaxF = 0d;
        public Double TaxG = 0d;
        public Double TaxH = 0d;
        
        public Double RevTaxA = 0d;
        public Double RevTaxB = 0d;
        public Double RevTaxC = 0d;
        public Double RevTaxD = 0d;
        public Double RevTaxE = 0d;
        public Double RevTaxF = 0d;
        public Double RevTaxG = 0d;
        public Double RevTaxH = 0d;
        
        public StrTable toStrTable() {
            return toStrTable("");
        }
        protected String formatCurrency(Double value) {
            return String.format(Locale.ROOT, "%.2f", value);
        }
        public StrTable toStrTable(String prefix) {
            StrTable res = new StrTable();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            res.put(prefix+"DocNum", DocNum);
            res.put(prefix+"DocDate", (DocDate == null)?"":fmt.format(DocDate));
            res.put(prefix+"TaxA", formatCurrency(TaxA));
            res.put(prefix+"TaxB", formatCurrency(TaxB));
            res.put(prefix+"TaxC", formatCurrency(TaxC));
            res.put(prefix+"TaxD", formatCurrency(TaxD));
            res.put(prefix+"TaxE", formatCurrency(TaxE));
            res.put(prefix+"TaxF", formatCurrency(TaxF));
            res.put(prefix+"TaxG", formatCurrency(TaxG));
            res.put(prefix+"TaxH", formatCurrency(TaxH));
            res.put(prefix+"RevTaxA", formatCurrency(RevTaxA));
            res.put(prefix+"RevTaxB", formatCurrency(RevTaxB));
            res.put(prefix+"RevTaxC", formatCurrency(RevTaxC));
            res.put(prefix+"RevTaxD", formatCurrency(RevTaxD));
            res.put(prefix+"RevTaxE", formatCurrency(RevTaxE));
            res.put(prefix+"RevTaxF", formatCurrency(RevTaxF));
            res.put(prefix+"RevTaxG", formatCurrency(RevTaxG));
            res.put(prefix+"RevTaxH", formatCurrency(RevTaxH));
            return res;
        }
    }
    
    public FPCBase() throws Exception {
        this.params = getDefinedParams(this.getClass());
    }
    
    /**
     * 
     * @return List of Descendant classes of FPCBase
     */
    public static final FPCBaseClassList getDerivedClasses() {
        if (FPCList == null) {
            FPCList = new FPCBaseClassList();
            // Use tree map to be sorted
            TreeMap<String, Class<? extends FPCBase>> tmpList = new TreeMap<>();
            Reflections reflections = new Reflections(FPCBase.class.getPackage().getName());
            Set<Class<? extends FPCBase>> classes = reflections.getSubTypesOf(FPCBase.class);
            Iterator iter = classes.iterator();
            Class<? extends FPCBase> fpc;
            while (iter.hasNext()) {
                fpc = (Class<? extends FPCBase>)iter.next();
                FiscalDevice fda = fpc.getAnnotation(FiscalDevice.class);
                if ((fda != null) && fda.usable())
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
    
    protected String formatCurrency(Double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
    
    protected Double parseDouble(String d) {
        Double val = 0d;
        try {
            val = Double.parseDouble(d);
        } finally {
            
        }
        return val;
    }

    protected Integer parseInt(String i) {
        Integer val = 0;
        try {
            val = Integer.parseInt(i);
        } finally {
            
        }
        return val;
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

    public static fiscalCheckRevType fiscalCheckRevTypeAbbrToType(String abbr) {
        if (fiscalCheckRevTypeAbbr.containsKey(abbr))
            return fiscalCheckRevTypeAbbr.get(abbr);
        else
            return fiscalCheckRevType.OP_ERROR;
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
     */
    public void printDiagnosticInfo() throws FPException {
        
    }
    
    /**
     * Returns the number of last printed fiscal document.
     * 
     * @return Last Fiscal Document Number
     * @throws org.eda.fdevice.FPException
     */
    public String getLastPrintDoc() throws FPException {
        return "";
    }
    

    /**
     * Get Information about Last QR-Code
     * @return 
     */
    public FiscalCheckQRCodeInfo getLastFiscalCheckQRCode() {
        return new FiscalCheckQRCodeInfo();
    }
    /**
     * Returns information about device
     * @return
     * @throws FPException 
     */
    public DeviceInfo getDeviceInfo() throws FPException {
        return new DeviceInfo();
    }
    
    /**
     * Return information about document number
     * @param docNum - Document number. If not set, get last document
     * @return 
     */
    public StrTable getDocInfo(String docNum) throws FPException {
        return null;
    }
    
    /**
     * Cancels Current Fiscal check (if opened)
     * @throws org.eda.fdevice.FPException
     */
    public void cancelFiscalCheck() throws FPException {
    }
    

    /**
     * Set extended fiscal check mode on/off and sets customer info
     * @param custInfo - customer data
     */
    public void setInvoiceFiscalCheckOn(CustomerInfo custInfo) throws FPException {
        invoiceMode = true;
        customer = custInfo;
        if (customer.recipient.length() == 0)
            throw new FPException("Customer 'recpient' is missing!");
        if (customer.buyer.length() == 0)
            throw new FPException("Customer 'buyer' is missing!");
        if (customer.UIC.length() == 0)
            throw new FPException("Customer 'UIC' is missing!");
    }

    public void setInvoiceFiscalCheckOff() {
        invoiceMode = false;
        customer = null;
    }
    
    /**
     * Opens fiscal check in case of error throws exception
     * @throws org.eda.fdevice.FPException
     */
    public void openFiscalCheck() throws FPException {
    }

    /**
     * Opens fiscal check receipt with Unique Number Sell
     * @param UNS - Unique Number of Sell
     * @param invoice Invoice type fiscal receipt
     * @throws org.eda.fdevice.FPException
     */
    public void openFiscalCheck(String UNS) throws FPException {
        // TODO: Check UNS format can be implemented in base class
        throw new FPException("Not supported!");
    }
    
    /**
     * Opens revision/reverse (storno) fiscal check receipt
     * @param UNS - Unique Number of Sell for this Reverse Receipt
     * @param RevType - Type of reason
     * @param RevDocNum - Revision for Document Number
     * @param RevUNS - Revision Unique Number of Sell   
     * @param RevDateTime - Revision Document Date and Time
     * @param RevFMNum - Revision Fiscal Memory Number (Individual Number of Fiscal Memory)
     * @param RevReason - A reason for revision
     * @param RevInvNum - Related to Invoice number
     * @param RevInvDate - Related to Invoice Date
     */
    public void openFiscalCheckRev(String UNS, fiscalCheckRevType RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, Date RevInvDate) throws FPException {
        throw new FPException("Not supported!");
    }
    
    /**
     * Print Fiscal text.
     * Must be opened fiscal check first
     * @param text - text to be printed
     * @throws org.eda.fdevice.FPException
     */
    public void printFiscalText(String text) throws FPException {
        
    }

    /**
     * Print non Fiscal text.
     * Must be opened fiscal check first
     * @param text - text to pe printed
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
     * @throws org.eda.fdevice.FPException
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
    public CheckInfo getCurrentCheckInfo() throws FPException {
        return new CheckInfo();
    }
    
    /**
     * Return information about last fiscal record
     * @return
     * @throws FPException 
     */
    public FiscalRecordInfo getLastFiscalRecordInfo() throws FPException {
        return new FiscalRecordInfo();
    }
    
    /**
     * Sets operator
     * @param code      - operator code
     * @param passwd    - operator password
     * @param fullName  - operator full name
     * @throws org.eda.fdevice.FPException
     */
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        
    }
    
    /**
     * Prints duplicate of last fiscal check
     */
    public void printLastCheckDuplicate() throws FPException {
        
    }
    
    /**
     * Print duplicate of check by document number via EJ
     * @param docNum 
     */
    public void printCheckDuplicateByDocNum(String docNum) throws FPException {
        
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
     * @param fromDate 
     * @param toDate 
     * @return CRC, SHA1 controls of journal
     */
    public StrTable getJournal(Date fromDate, Date toDate) throws FPException {
        return null;
    }

    public StrTable getJournal(String fromDoc, String toDoc) throws FPException {
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
     * @throws org.eda.fdevice.FPException
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

