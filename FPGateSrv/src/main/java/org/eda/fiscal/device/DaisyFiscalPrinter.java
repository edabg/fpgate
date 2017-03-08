package org.eda.fiscal.device;

import com.taliter.fiscal.device.FiscalDeviceIOException;
import com.taliter.fiscal.device.icl.ICLFiscalPacket;
import com.taliter.fiscal.port.rxtx.RXTXFiscalPortSource;
import com.taliter.fiscal.port.serial.SerialFiscalPortSource;
import com.taliter.fiscal.util.LoggerFiscalDeviceEventHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;


/**
 * DaisyFiscalPrinter implements ICLFiscalPrinter interface.
 * This implementation provides the basic functionality of
 * Daisy fiscal devices.
 */
public class DaisyFiscalPrinter implements ICLFiscalPrinter{
    
    public final static String RXTX = "RXTX";
    public final static String SERIAL = "SERIAL";
    
    /** Daisy fiscal device object */
    private DaisyFiscalDevice device;
    private String responseStatusStr;
    protected String[] responseErrors;

    private LinkedHashMap<String, String[]> statusBytesDef;
    private LinkedHashMap<String, byte[]> errorStatusBits;
    
    
    private static int REQUEST_FIELD_PARAMS = 1;
    private static int RESPONSE_FIELD_DATA = 1;
    private static int RESPONSE_FIELD_STATUS = 2;
    
    /**
     * Creates an instance of DaisyFiscalDevice
     * @param port The port in which the fiscal device is connected to.
     * @param baudRate serial port baud rate
     * @param portSource Specifies which library to use. The expected values are the public RXTX or SERIAL constants.
     * @throws FiscalDeviceIOException
     * @throws Exception
     */
    public DaisyFiscalPrinter(String port, int baudRate, String portSource) throws FiscalDeviceIOException, Exception {
        
        initPrinterStatusMap();        
        
        DaisyFiscalDeviceSource deviceSource;
        if(portSource.equals(RXTX)) {
            RXTXFiscalPortSource rxtxSource = new RXTXFiscalPortSource(port);
            rxtxSource.setOpenTimeout(10000);
            deviceSource = new DaisyFiscalDeviceSource(rxtxSource);
        } else {
            deviceSource = new DaisyFiscalDeviceSource(new SerialFiscalPortSource(port));
        }
        
        //DaisyFiscalDeviceSource deviceSource = new DaisyFiscalDeviceSource(new RXTXFiscalPortSource(port));
        device = (DaisyFiscalDevice) deviceSource.getFiscalDevice();
        device.getFiscalPort().setBaudRate(baudRate);
        device.setEventHandler(new LoggerFiscalDeviceEventHandler(System.out));
	
        // Open the device         
        device.open();
    }

    /**
     * Creates an instance of DaisyFiscalDevice.
     * By default baudRate is 9600 bps
     * @param port The port in which the fiscal device is connected to.
     * @param portSource Specifies which library to use. The expected values are the public RXTX or SERIAL constants.
     * @throws FiscalDeviceIOException
     * @throws Exception
     */
    public DaisyFiscalPrinter(String port, String portSource) throws FiscalDeviceIOException, Exception {
        this(port, 9600, portSource);
    }

    /**
     * Initialize printer status bytes meaning.
     * Define which bits from status bytes are critical errors.
     */
    private void initPrinterStatusMap() {
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        
        statusBytesDef.put("S0", new String[]
        {
             "Синтактична грешка"
            ,"Невалиден код на команда"
            ,"Неустановени дата/час"
            ,"Не е свързан клиентски дисплей"
            ,"Неизправен механизъм"
            ,"Обща грешка"
            ,""
            ,""
        }
        );
        errorStatusBits.put("S0", new byte[] {
            0,1,4 //,5
        });
        statusBytesDef.put("S1", new String[]
        {
             "Препълване"
            ,"Непозволена команда в този контекст"
            ,"Аварийно зануляване на оперативната памет"
            ,"Прекъсната и рестартирана операция на печат/Слаба батерия"
            ,"Разрушено състояние на ОП"
            ,"Капакът на принтера е отворен"
            ,"Електронната контролна лента е отпечатана"
            ,""
        }
        );
        errorStatusBits.put("S1", new byte[] {
            1
        });
        statusBytesDef.put("S2", new String[]
        {
             "Край на хартията"
            ,"Останала е малко хартия"
            ,"Край на електронната контролна лента (95% запълнена)"
            ,"Отворен Фискален Бон"
            ,"Близък край на електронната контролна лента (90% запълнена)"
            ,"Отворен Нефискален Бон"
            ,"Електронната контролна лента не е празна"
            ,""
        }
        );
        errorStatusBits.put("S2", new byte[] {
            0
        });
        statusBytesDef.put("S3", new String[]
        {
             "SW1.1"
            ,"SW1.2"
            ,"SW1.3"
            ,"SW1.4"
            ,"SW1.5"
            ,"SW2.1"
            ,"SW2.2"
            ,""
        }
        );
        errorStatusBits.put("S3", new byte[] {
        });
        statusBytesDef.put("S4", new String[]
        {
             "Грешка при запис във фискалната памет"
            ,"Зададен е БУЛСТАТ"
            ,"Зададени са индивидуален номер на принтера и номер на фискалната памет"
            ,"Има място за по малко от 50 фискални затваряния"
            ,"Фискалната памет е пълна"
            ,"0 или 4"
            ,""
            ,""
        }
        );
        errorStatusBits.put("S4", new byte[] {
            0, 4, 5
        });
        statusBytesDef.put("S5", new String[]
        {
             "Фискалната памет в режим Read Only"
            ,"Фискалната памет e форматирана"
            ,"Последният запис във ФП не е успешен"
            ,"Устройството е фискализирано"
            ,"Зададени са данъчните ставки"
            ,"Номерът на фискалната памет е програмиран"
            ,""
            ,""
        }
        );
        errorStatusBits.put("S5", new byte[] {
            0
        });
    }

    /**
     * Returns hash map with status bytes definition
     * @return 
     */
    public HashMap<String, String[]> getStatusBytesDef() {
        return statusBytesDef;
    }
    
    /**
     * Convert list of status bytes as string in hexadecimal form in format '\xFF\xFF\xFF\xFF\xFF\xFF'
     * 
     * @param strBytes 
     *  list of status bytes as string in hexadecimal form in format '\xFF\xFF\xFF\xFF\xFF\xFF'
     * @return 
     *  Status bytes as binary string
     */
    protected LinkedHashMap<String, String> stringsToStatusBytes(String strHexBytes) {
        LinkedHashMap<String, String> statusBytes = new LinkedHashMap();
        if (strHexBytes.length() > 2 && strHexBytes.substring(0, 2).equals("\\x"))
            strHexBytes = strHexBytes.substring(2);
        String[] strBytes = strHexBytes.split("\\\\x");
        int i = 0;
        String sb;
        String ss;
        for(String statusByte : statusBytesDef.keySet()) {
            if (strBytes.length > i)
               sb = strBytes[i];
            else 
               sb = "00";
            ss = Integer.toBinaryString(Integer.parseInt(sb, 16) & 0xFF);
            statusBytes.put(statusByte, ss);
            i++;
        }
        return statusBytes;
    }
    
    public void checkErrorStatus() throws FiscalDeviceIOException {
        ArrayList<String> errors = new ArrayList<>();
        LinkedHashMap<String,String> responseStatusBytes =  stringsToStatusBytes(responseStatusStr);
        for(String sb : responseStatusBytes.keySet()) {
            String ss = new StringBuffer(responseStatusBytes.get(sb)).reverse().toString();
            if (errorStatusBits.containsKey(sb)) {
                byte[] errBits = errorStatusBits.get(sb);
                for(int i = 0; i < errBits.length; i++) {
                    if (i < errBits.length && ss.charAt(errBits[i]) == '1') {
                        errors.add(statusBytesDef.get(sb)[errBits[i]]);
                    }
                }
            }
        }
//        errors.add("Тестова грешка");
        responseErrors = errors.toArray(new String[errors.size()]);
        if (responseErrors.length > 0) 
            throw new FiscalDeviceIOException(errors.toString());
    }
    
    public LinkedHashMap<String,String> getStatusBytesAsText(LinkedHashMap<String, String> statusBytes) {

        LinkedHashMap<String,String> res = new LinkedHashMap<>();
        LinkedHashMap<String,String> resText = new LinkedHashMap<>();
        String ss;
        
        for(String s : statusBytes.keySet()) {
            try {
                ss = statusBytes.get(s);
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
                    resText.put("Msg_"+s+"_"+Integer.toString(i), statusBytesDef.get(s)[i]);
        }   
        
        res.putAll(resText);
        
        return res;
    }
    
    /**
     * Get the fiscal device status bytes of the last 
     * executed command and stores them in a string array. 
     * Each status byte is represented by eight bits.
     * @return A string array with the status bytes.
     */
    public LinkedHashMap<String, String> getResponseStatusBytes() {
        return stringsToStatusBytes(responseStatusStr);
    }

    /**
     * Close the connection with the fiscal device.
     * @throws Exception 
     */
    public void close() throws Exception {
        device.close();
    }
    
    protected ICLFiscalPacket createRequest(int CMD) {
        ICLFiscalPacket request = (ICLFiscalPacket) device.createFiscalPacket();
        request.setCommandCode(CMD);
        return request;
    }

    protected ICLFiscalPacket sendRequest(ICLFiscalPacket request) throws FiscalDeviceIOException {
        ICLFiscalPacket responsePacket;
        responseStatusStr = "";
        try {
            responsePacket = (ICLFiscalPacket) this.device.execute(request);
            responseStatusStr = responsePacket.toASCIIString(RESPONSE_FIELD_STATUS);
            checkErrorStatus();
//            responseStatusBytes =  stringsToStatusBytes(responseStatusStr);
        } catch (IOException ex) {
            throw new FiscalDeviceIOException(ex.getMessage());
        }
        return responsePacket;
    }
    
    @Override
    public void cmdPrintDiagnosticInfo() throws FiscalDeviceIOException {
        sendRequest(createRequest(DaisyFiscalDevice.CMD_PRINT_DIAGNOSTIC_INFO));
    }

    @Override
    public int cmdLastDocNum() throws FiscalDeviceIOException {
        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_LAST_DOC_NUM));
        return Integer.parseInt(responsePacket.getString(RESPONSE_FIELD_DATA));
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String operator, String password, boolean invoice) throws FiscalDeviceIOException{
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_OPEN_FISCAL_CHECK);
        String data = operator + "," + password + "," + "1";
        if(invoice) {
            data += "," + "I";
        }
        request.setString(REQUEST_FIELD_PARAMS, data);

        ICLFiscalPacket responsePacket = sendRequest(request);

        String rString = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        if(!rString.isEmpty()) {
            String[] rData = rString.split(",");

            response.put("AllReceipt", rData[0]);
            response.put("FiscReceipt", rData[1]);
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_CLOSE_FISCAL_CHECK));
        
        String rString = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        if(!rString.isEmpty()) {
            String[] rData = rString.split(",");

            response.put("AllReceipt", rData[0]);
            response.put("FiscReceipt", rData[1]);
        }
        
        return response;
    }

    @Override
    public void cmdPrintFiscalText(String text) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_PRINT_FISCAL_TEXT);
        request.setString(REQUEST_FIELD_PARAMS, text);
        sendRequest(request);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_OPEN_NONFISCAL_CHECK));
        
        response.put("CheckCount", responsePacket.getString(RESPONSE_FIELD_DATA));
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_CLOSE_NONFISCAL_CHECK));
        
        
        response.put("CheckCount", responsePacket.getString(RESPONSE_FIELD_DATA));
        
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_PRINT_NONFISCAL_TEXT);
        request.setString(REQUEST_FIELD_PARAMS, text);
        sendRequest(request);
    }

    @Override
    public void cmdSell(String sellText, String descriptionText, String taxGroup, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_SELL);
        
        String cr = "\n";
        String tab = "\t";

        String data = sellText;
        
        if(!descriptionText.isEmpty()) {
            data += cr + descriptionText;
        }
        
        if(taxGroup.isEmpty()) {
            taxGroup = "Р‘";
        }
        

        data += tab + taxGroup + String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001)
            data += "*"+String.format(Locale.ROOT, "%.3f", quantity);

        if (Math.abs(discount) >= 0.01) {
            if(inPercent) {
                data += "," + discount;
            } else {
                data += "$" + discount;
            }
        }
        request.setString(REQUEST_FIELD_PARAMS, data);

        sendRequest(request);
    }
    
    @Override
    public void cmdSellDept(String sellText, String descriptionText, String dept, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_SELL_DEPT);

        
        String data = ((price < 0)?"-":"") + dept + "@" + String.format(Locale.ROOT, "%.2f", Math.abs(price));
        if (Math.abs(quantity) >= 0.001)
            data += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        
        if (Math.abs(discount) >= 0.01) {
            if(inPercent) {
                data += "," + String.format(Locale.ROOT, "%.2f", discount);
            } else {
                data += "$" + String.format(Locale.ROOT, "%.2f", discount);
            }
        }
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        sendRequest(request);
    }

    
    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, double discount, boolean inPercent) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_SUBTOTAL);
        
        String data = "";
        
        data += toPrint ? "1" : "0";
        data += toDisplay ? "1" : "0";
        
        if (Math.abs(discount) >= 0.01) 
            data += "," + String.format(Locale.ROOT, "%.2f", discount);
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String[] rdata = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        response.put("SubTotal", rdata[0]);
        response.put("TaxA", rdata[1]);
        response.put("TaxB", rdata[2]);
        response.put("TaxC", rdata[3]);
        response.put("TaxD", rdata[4]);
        response.put("TaxE", rdata[5]);
        response.put("TaxF", rdata[6]);
        response.put("TaxG", rdata[7]);
        response.put("TaxH", rdata[8]);
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String firstRowText, String secondRowText, String paymentType, double amount) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_TOTAL);
        
        String data = firstRowText;
        
        if(!secondRowText.isEmpty()) {
            data += "\n" + secondRowText;
        }
        
        data += "\t" + paymentType + String.format(Locale.ROOT, "%.2f", amount);

        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);

        byte[] rdata = responsePacket.get(RESPONSE_FIELD_DATA);
        
        response.put("PaidCode", Integer.toHexString(rdata[0]));
        
        String am = "";
        for (int i = 1; i < rdata.length; i++) {
            am += (char) rdata[i];
        }
        
        response.put("PaidCode", String.valueOf((char) rdata[0]));
        response.put("Amount", am);
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_CURRENT_CHECK_INFO));
        
        String[] data = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        response.put("CanVoid", data[0]);
        response.put("TaxA", data[1]);
        response.put("TaxB", data[2]);
        response.put("TaxC", data[3]);
        response.put("TaxD", data[4]);
        response.put("TaxE", data[5]);
        response.put("TaxF", data[6]);
        response.put("TaxG", data[7]);
        response.put("TaxH", data[8]);
        response.put("InvoiceFlag", data[9]);
        response.put("InvoiceNo", data[10]);
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_LAST_FISCAL_RECORD);
        
        request.setString(REQUEST_FIELD_PARAMS, "N");

        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String responseString = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        if(!responseString.isEmpty()) {
            String[] data = responseString.split(",");
            if (data[0].substring(0, 1).equals("F")) {
                // error
            } else {
                if (data[0].substring(0, 1).equals("P"))    
                    response.put("Number", data[0].substring(1));
                else
                    response.put("Number", data[0]);
//                response.put("SpaceGr", data[1]);
                String[] TaxGr = {"TaxA","TaxB","TaxC","TaxD", "TaxE", "TaxF", "TaxG", "TaxH"};
                int i = 1;
                for(String tg : TaxGr) {
                    if (i < data.length)
                        response.put(tg, Double.toString(Double.parseDouble(data[i])/100));
                    else
                        response.put(tg, "");
                    i++;
                }
                if (i < data.length)
                    response.put("Date", data[i]);
                else
                    response.put("Date", "");
            }    
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> statusBytes = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_PRINTER_STATUS);
        
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String data = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);

        statusBytes = stringsToStatusBytes(data);
        
        return getStatusBytesAsText(statusBytes);
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_DIAGNOSTIC_INFO);
        
        request.setByte(REQUEST_FIELD_PARAMS, 1);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String[] data = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        String[] basicData = data[0].split(" ");
        
        response.put("FirmwareVersion", basicData[0]);
        response.put("FirmwareDate", basicData[1]);
        response.put("FirmwareTime", basicData[2]);
        response.put("CheckSum", data[1]);
        response.put("SW", data[2]);
        response.put("Country", data[3]);
        response.put("SerialNumber", data[4]);
        response.put("FiscalModuleNum", data[5]);
        
        return response;
    }

    @Override
    public Date cmdGetDateTime() throws FiscalDeviceIOException {
        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_DATETIME_INFO));
        
        return responsePacket.getDateAndTime(RESPONSE_FIELD_DATA);
    }

    @Override
    public void cmdSetDateTime(int year, int month, int day, int hour, int minute, int second) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_SET_DATETIME);
        
        request.setDateAndTime(REQUEST_FIELD_PARAMS, year, month, day, hour, minute, second);
        
        sendRequest(request);
    }
    
    @Override
    public void cmdPrintCheckDuplicate() throws FiscalDeviceIOException{
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_PRINT_CHECK_DUBLICATE);
        
        request.setString(REQUEST_FIELD_PARAMS, "1");
        
        sendRequest(request);
    }

    @Override
    public LinkedHashMap<String, String> cmdCancelFiscalCheck() throws FiscalDeviceIOException{
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_CANCEL_FISCAL_CHECK));
        
        if(responsePacket.get(1).length > 0) {
            String[] rData = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
            response.put("AllReceipt", rData[0]);
            response.put("FiscReceipt", rData[1]);
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReportDaily(String item, boolean toReset) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_REPORT_DAILY);
        
        if(item == null || item.isEmpty()) {
            item = "0";
        }
       
        String data = item;
        
        if(!toReset) 
            data += "N";
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String[] rData = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        response.put("Closure", rData[0]);
        response.put("Total", rData[1]);
        response.put("TaxA", rData[2]);
        response.put("TaxB", rData[3]);
        response.put("TaxC", rData[4]);
        response.put("TaxD", rData[5]);
        response.put("TaxE", rData[6]);
        response.put("TaxF", rData[7]);
        response.put("TaxG", rData[8]);
        response.put("TaxH", rData[9]);
        
        return response;
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws FiscalDeviceIOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        
        int cmd = detailed ? DaisyFiscalDevice.CMD_REPORT_BY_DATE : DaisyFiscalDevice.CMD_REPORT_BY_DATE_SHORT;
        ICLFiscalPacket request = createRequest(cmd);

        String data = dateFormat.format(startDate) + "," + dateFormat.format(endDate);
        
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        sendRequest(request);
    }

    @Override
    public void cmdPaperFeed(int lines) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_PAPER_FEED);
        
        request.setString(REQUEST_FIELD_PARAMS, Integer.toString(lines));
        
        sendRequest(request);
    }

    @Override
    public void cmdSetOperator(String clerkNum, String password, String name) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_SET_OPERATOR);

        String data = clerkNum + "," + password + "," + name;
 
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        sendRequest(request);
    }

    @Override
    public String customCmd(int cmd, String params) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(cmd);
        
        if(params != null && !params.isEmpty())
            request.setString(REQUEST_FIELD_PARAMS, params);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String responseString = responsePacket.getString(RESPONSE_FIELD_DATA);
//        byte[] rdata = responsePacket.get(RESPONSE_FIELD_DATA);
        
//        String responseString = "";
//        for (byte b : rdata) {
//            responseString += (char) b;
//        }
        
        return responseString;
    }
    
    @Override
    public void cmdResetByOperator(String clerkNumber, String password) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_RESET_BY_OPERATOR);
        
        String data = clerkNumber + "," + password;
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        sendRequest(request);
    }

    @Override
    public LinkedHashMap<String, String> cmdGetConstants() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DaisyFiscalDevice.CMD_GET_CONSTANTS));
        
        String[] rData = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        
        response.put("LogoWidth", rData[0]);
        response.put("LogoHeight", rData[1]);
        response.put("PaymentCount", rData[2]);
        response.put("TaxGroupCount", rData[3]);
/*      
        response.put("NotUsingInBG", rData[4]);
        response.put("NotUsingInBG", rData[5]);
*/
        response.put("FirstTaxGroup", rData[6]);
        response.put("InternalArithmetic", rData[7]);
        response.put("SymbolsRowCount", rData[8]);
        response.put("SymbolsCommentedRowCount", rData[9]);
        
        response.put("NameLength", rData[10]);
        response.put("FDIdLength", rData[11]);
        response.put("FMNumberLength", rData[12]);
        response.put("TaxNumberLength", rData[13]);
        response.put("BulstatLength", rData[14]);
        response.put("DepartmentsCount", rData[15]);
        response.put("ItemCount", rData[16]);

        response.put("StockFlag", rData[17]);
        response.put("BarcodeFlag", rData[18]);
        
        response.put("CommodityGroupCount", rData[19]);
        response.put("OperatorsCount", rData[20]);
        response.put("PaymentNameLength", rData[21]);
        response.put("FuelsCount", rData[22]);

        /*
        response.put("NotUsing", rData[23]);
        response.put("NotUsing", rData[24]);
        */
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> res = new LinkedHashMap();
        ICLFiscalPacket request = createRequest(DaisyFiscalDevice.CMD_CASH_INOUT);
        String data = ioSum.toString();
        request.setString(REQUEST_FIELD_PARAMS, data);
        ICLFiscalPacket responsePacket = sendRequest(request);
//        System.out.println("Response:"+responsePacket.toASCIIString(RESPONSE_FIELD_DATA));
        String[] rData = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        if (rData[0].equals("F"))
            throw new FiscalDeviceIOException("Заявката е отказана! Касовата начлиност е по-малка от заявената или има отворен фискален бон.");
        res.put("CashSum", String.format(Locale.ROOT, "%.2f", (rData.length > 1)?Double.parseDouble(rData[1])/100:0));
        res.put("CashIn", String.format(Locale.ROOT, "%.2f", (rData.length > 2)?Double.parseDouble(rData[2])/100:0));
        res.put("CashOut", String.format(Locale.ROOT, "%.2f", (rData.length > 3)?Double.parseDouble(rData[3])/100:0));
        return res;
    }
    
}
