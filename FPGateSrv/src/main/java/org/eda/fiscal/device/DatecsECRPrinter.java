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
 * This class implements Serial Protocol Communication to Datecs
 * Electronic cash registers.
 * Currently tested with Datecs MP55
 * The low level serial protocol is the same as for all Datecs Fiscal printers.
 * There is difference in implementation of different printers.
 * 
 * @author Dimitar Angelov
 * 
 */
public class DatecsECRPrinter  implements ICLFiscalPrinter {
    public final static String RXTX = "RXTX";
    public final static String SERIAL = "SERIAL";
    
    /** Datecs fiscal device object */
    private DatecsECRFiscalDevice device;
    private String responseStatusStr;
    protected String[] responseErrors;

    private LinkedHashMap<String, String[]> statusBytesDef;
    private LinkedHashMap<String, byte[]> errorStatusBits;
    
    
    private static int REQUEST_FIELD_PARAMS = 1;
    private static int RESPONSE_FIELD_DATA = 1;
    private static int RESPONSE_FIELD_STATUS = 2;
    
    /**
     * Creates an instance of DatecsECRPrinter
     * @param port The port in which the fiscal device is connected to.
     * @param baudRate serial port baud rate
     * @param portSource Specifies which library to use. The expected values are the public RXTX or SERIAL constants.
     * @throws FiscalDeviceIOException
     * @throws Exception
     */
    public DatecsECRPrinter(String port, int baudRate, String portSource) throws FiscalDeviceIOException, Exception {
        
        initPrinterStatusMap();        
        
        DatecsECRFiscalDeviceSource deviceSource;
        if(portSource.equals(RXTX)) {
            deviceSource = new DatecsECRFiscalDeviceSource(new RXTXFiscalPortSource(port));
        } else {
            deviceSource = new DatecsECRFiscalDeviceSource(new SerialFiscalPortSource(port));
        }
        
        device = (DatecsECRFiscalDevice) deviceSource.getFiscalDevice();
        device.getFiscalPort().setBaudRate(baudRate);
        device.setEventHandler(new LoggerFiscalDeviceEventHandler(System.out));
	
        // Open the device         
        device.open();
    }

    /**
     * Creates an instance of DatecsECRPrinter.
     * By default baudRate is 9600 bps
     * @param port The port in which the fiscal device is connected to.
     * @param portSource Specifies which library to use. The expected values are the public RXTX or SERIAL constants.
     * @throws FiscalDeviceIOException
     * @throws Exception
     */
    public DatecsECRPrinter(String port, String portSource) throws FiscalDeviceIOException, Exception {
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
            ,""
            ,"Обща грешка"
            ,""
            ,""
        }
        );
        errorStatusBits.put("S0", new byte[] {
            0,1,5
        });
        statusBytesDef.put("S1", new String[]
        {
             "Препълване"
            ,"Непозволена команда в този контекст"
            ,"Аварийно зануляване на оперативната памет"
            ,""
            ,""
            ,""
            ,"Вграденият данъчен терминал не отговаря"
            ,""
        }
        );
        errorStatusBits.put("S1", new byte[] {
            1
        });
        statusBytesDef.put("S2", new String[]
        {
             "Край на хартията"
            ,""
            ,"Край на електронната контролна лента (95% запълнена)"
            ,"Отворен Фискален Бон"
            ,"Близък край на електронната контролна лента (90% запълнена)"
            ,"Отворен е Служебен Бон"
            ,""
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
            ,"Грешка 0 или 4"
            ,""
            ,""
        }
        );
        errorStatusBits.put("S4", new byte[] {
            0, 4, 5
        });
        statusBytesDef.put("S5", new String[]
        {
             ""
            ,"Фискалната памет e форматирана"
            ,""
            ,"ФУ е във фискален режим"
            ,"Зададени са поне веднъж данъчните ставки"
            ,""
            ,""
            ,""
        }
        );
        errorStatusBits.put("S5", new byte[] {
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
        sendRequest(createRequest(DatecsECRFiscalDevice.CMD_PRINT_DIAGNOSTIC_INFO));
    }

    @Override
    public int cmdLastDocNum() throws FiscalDeviceIOException {
        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_LAST_DOC_NUM));
        return Integer.parseInt(responsePacket.getString(RESPONSE_FIELD_DATA));
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheck(String operator, String password, boolean invoice) throws FiscalDeviceIOException{
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_OPEN_FISCAL_CHECK);
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
            response.put("FiscReceipt", (rData.length > 1)?rData[1]:"");
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_CLOSE_FISCAL_CHECK));
        
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
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_PRINT_FISCAL_TEXT);
        request.setString(REQUEST_FIELD_PARAMS, text);
        sendRequest(request);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_OPEN_NONFISCAL_CHECK));

        String rString = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        if(!rString.isEmpty()) {
            String[] rData = rString.split(",");

            response.put("AllReceipt", rData[0]);
            response.put("ErrCode", (rData.length > 1)?rData[1]:"");
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_CLOSE_NONFISCAL_CHECK));
        
        
        response.put("AllReceipt", responsePacket.getString(RESPONSE_FIELD_DATA));
        
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_PRINT_NONFISCAL_TEXT);
        request.setString(REQUEST_FIELD_PARAMS, text);
        sendRequest(request);
    }

    @Override
    public void cmdSell(String sellText, String descriptionText, String taxGroup, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException {
        // [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        String lf = "\n";
        String tab = "\t";
        if(taxGroup.isEmpty()) 
            taxGroup = "Р‘";

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_SELL);
        
        String data = sellText;
        if(!descriptionText.isEmpty()) 
            data += lf + descriptionText;
        
        data += tab + taxGroup + String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001)
            data += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        
        if (Math.abs(discount) >= 0.01) {
            if(inPercent) {
                data += "," + String.format(Locale.ROOT, "%.2f", discount);
            } else {
                data += ";" + String.format(Locale.ROOT, "%.2f", discount);
            }
        }    
        request.setString(REQUEST_FIELD_PARAMS, data);

        sendRequest(request);
    }
    
    @Override
    public void cmdSellDept(String sellText, String descriptionText, String dept, double price, double quantity, double discount, boolean inPercent) throws FiscalDeviceIOException {
        // [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]        
        String lf = "\n";
        String tab = "\t";

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_SELL);

        String data = sellText;
        if(!descriptionText.isEmpty()) 
            data += lf + descriptionText;
        
        data += tab + dept + tab + String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001)
            data += "*"+String.format(Locale.ROOT, "%.3f", quantity);
        
        if (Math.abs(discount) >= 0.01) {
            if(inPercent) {
                data += "," + String.format(Locale.ROOT, "%.2f", discount);
            } else {
                data += ";" + String.format(Locale.ROOT, "%.2f", discount);
            }
        }    
        request.setString(REQUEST_FIELD_PARAMS, data);

        sendRequest(request);
    }

    
    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, double discount, boolean inPercent) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_SUBTOTAL);
        
        String data = "";
        
        data += toPrint ? "1" : "0";
        data += toDisplay ? "1" : "0";
        
        if (Math.abs(discount) >= 0.01) {
            if(inPercent) {
                data += "," + String.format(Locale.ROOT, "%.2f", discount);
            } else {
                data += ";" + String.format(Locale.ROOT, "%.2f", discount);
            }
        }    

        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String[] rdata = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        response.put("SubTotal", rdata[0]);
        response.put("TaxA", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 1)?rdata[1]:"0")/100));
        response.put("TaxB", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 2)?rdata[2]:"0")/100));
        response.put("TaxC", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 3)?rdata[3]:"0")/100));
        response.put("TaxD", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 4)?rdata[4]:"0")/100));
        response.put("TaxE", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 5)?rdata[5]:"0")/100));
        response.put("TaxF", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 6)?rdata[6]:"0")/100));
        response.put("TaxG", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 7)?rdata[7]:"0")/100));
        response.put("TaxH", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 8)?rdata[8]:"0")/100));
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String firstRowText, String secondRowText, String paymentType, double amount) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_TOTAL);
        
        String data = firstRowText;
        
        if(!secondRowText.isEmpty()) {
            data += "\n" + secondRowText;
        }
        
        data += "\t" + paymentType;
        if (amount > 0)
            data += String.format(Locale.ROOT, "%.2f", amount);

        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);

        String rdata = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        response.put("PaidCode", rdata.substring(0,1));
        response.put("Amount", String.format(Locale.ROOT, "%.2f", (rdata.length() > 1)?Double.parseDouble(rdata.substring(1))/100:0));
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_CURRENT_CHECK_INFO));
        
        String[] rdata = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        
        response.put("CanVoid", rdata[0]);
        response.put("TaxA", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 1)?rdata[1]:"0")/100));
        response.put("TaxB", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 2)?rdata[2]:"0")/100));
        response.put("TaxC", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 3)?rdata[3]:"0")/100));
        response.put("TaxD", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 4)?rdata[4]:"0")/100));
        response.put("TaxE", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 5)?rdata[5]:"0")/100));
        response.put("TaxF", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 6)?rdata[6]:"0")/100));
        response.put("TaxG", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 7)?rdata[7]:"0")/100));
        response.put("TaxH", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 8)?rdata[8]:"0")/100));
        response.put("InvoiceFlag", (rdata.length > 9)?rdata[9]:"0");
        response.put("InvoiceNo", (rdata.length > 10)?rdata[10]:"0");
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_LAST_FISCAL_RECORD);
        // By documentation
        //ErrCode[,N,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Date]
        // Real
        //ErrCode[N,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Date]

        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String responseString = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);
        
        if(!responseString.isEmpty()) {
            String[] rdata = responseString.split(",");
            if (rdata[0].substring(0,1).equals("F")) {
                // error
                throw new FiscalDeviceIOException("Грешка: Не се чете последния запис!");
            } else {
                response.put("Number", rdata[0].substring(1));
                response.put("TaxA", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 1)?rdata[1]:"0")/100));
                response.put("TaxB", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 2)?rdata[2]:"0")/100));
                response.put("TaxC", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 3)?rdata[3]:"0")/100));
                response.put("TaxD", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 4)?rdata[4]:"0")/100));
                response.put("TaxE", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 5)?rdata[5]:"0")/100));
                response.put("TaxF", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 6)?rdata[6]:"0")/100));
                response.put("TaxG", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 7)?rdata[7]:"0")/100));
                response.put("TaxH", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rdata.length > 8)?rdata[8]:"0")/100));
                response.put("Date", (rdata.length > 9)?rdata[9]:"");
            }    
        }
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> statusBytes = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_PRINTER_STATUS);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String data = responsePacket.toASCIIString(RESPONSE_FIELD_DATA);

        statusBytes = stringsToStatusBytes(data);
        
        return getStatusBytesAsText(statusBytes);
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_DIAGNOSTIC_INFO);
        
        request.setString(REQUEST_FIELD_PARAMS, "1");
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        String responseStr = responsePacket.getString(RESPONSE_FIELD_DATA);
//      <Name><FwRev><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,< FM >,< Ser >   
//      <Name>,<FwRev><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,< Ser >,< FM >
//      MP55PL,267621 12Apr11 0411,7627,00000000,DT316515,02333021

        String[] rdata = responseStr.split(",");
        response.put("Name", rdata[0]);
        if (rdata.length > 1) {
            String[] fwData = rdata[1].split(" ");
            response.put("FirmwareVersion", fwData[0]);
            response.put("FirmwareDate", (fwData.length > 1)?fwData[1]:"");
            response.put("FirmwareTime", (fwData.length > 2)?fwData[2]:"");
            response.put("CheckSum", (rdata.length > 2)?rdata[2]:"");
            response.put("SW", (rdata.length > 3)?rdata[3]:"");
            response.put("SerialNumber", (rdata.length > 4)?rdata[4]:"");
            response.put("FiscalModuleNum", (rdata.length > 5)?rdata[5]:"");
        }
        return response;
    }

    @Override
    public Date cmdGetDateTime() throws FiscalDeviceIOException {
        ICLFiscalPacket responsePacket = sendRequest(createRequest(DatecsECRFiscalDevice.CMD_GET_DATETIME));
        return responsePacket.getDateAndTime(RESPONSE_FIELD_DATA);
    }

    @Override
    public void cmdSetDateTime(int year, int month, int day, int hour, int minute, int second) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_SET_DATETIME);
        
        request.setDateAndTime(REQUEST_FIELD_PARAMS, year, month, day, hour, minute, second);
        
        sendRequest(request);
    }
    
    @Override
    public void cmdPrintCheckDuplicate() throws FiscalDeviceIOException{
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_PRINT_CHECK_DUBLICATE);
        request.setString(REQUEST_FIELD_PARAMS, "1");
        sendRequest(request);
    }

    @Override
    public LinkedHashMap<String, String> cmdCancelFiscalCheck() throws FiscalDeviceIOException{
        LinkedHashMap<String, String> response = new LinkedHashMap();

        sendRequest(createRequest(DatecsECRFiscalDevice.CMD_CANCEL_FISCAL_CHECK));
        
        response.put("AllReceipt", "");
        response.put("FiscReceipt", "");
        
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReportDaily(String item, boolean toReset) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();

        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_REPORT_DAILY);
        
        if(item == null || item.isEmpty()) {
            item = "0";
        }
       
        String data = item;
        
        if(!toReset) 
            data += "N";
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        ICLFiscalPacket responsePacket = sendRequest(request);
        
        String[] rData = responsePacket.toASCIIString(RESPONSE_FIELD_DATA).split(",");
        // Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        response.put("Closure", rData[0]);
        response.put("Total", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 1)?rData[1]:"0")/100));
        response.put("TaxA", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 2)?rData[2]:"0")/100));
        response.put("TaxB", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 3)?rData[3]:"0")/100));
        response.put("TaxC", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 4)?rData[4]:"0")/100));
        response.put("TaxD", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 5)?rData[5]:"0")/100));
        response.put("TaxE", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 6)?rData[6]:"0")/100));
        response.put("TaxF", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 7)?rData[7]:"0")/100));
        response.put("TaxG", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 8)?rData[8]:"0")/100));
        response.put("TaxH", String.format(Locale.ROOT, "%.2f", Double.parseDouble((rData.length > 9)?rData[9]:"0")/100));
        
        return response;
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws FiscalDeviceIOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        
        int cmd = detailed ? DatecsECRFiscalDevice.CMD_REPORT_BY_DATE : DatecsECRFiscalDevice.CMD_REPORT_BY_DATE_SHORT;
        ICLFiscalPacket request = createRequest(cmd);

        String data = dateFormat.format(startDate) + "," + dateFormat.format(endDate);
        
        request.setString(REQUEST_FIELD_PARAMS, data);
        
        sendRequest(request);
    }

    @Override
    public void cmdPaperFeed(int lines) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_PAPER_FEED);
        
        request.setString(REQUEST_FIELD_PARAMS, Integer.toString(lines));
        
        sendRequest(request);
    }

    @Override
    public void cmdSetOperator(String clerkNum, String password, String name) throws FiscalDeviceIOException {
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_SET_OPERATOR);

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
        throw new FiscalDeviceIOException("Not supported command!");
    }

    @Override
    public LinkedHashMap<String, String> cmdGetConstants() throws FiscalDeviceIOException {
        throw new FiscalDeviceIOException("Not supported command!");
    }

    @Override
    public LinkedHashMap<String, String> cmdCashInOut(Double ioSum) throws FiscalDeviceIOException {
        LinkedHashMap<String, String> res = new LinkedHashMap();
        ICLFiscalPacket request = createRequest(DatecsECRFiscalDevice.CMD_CASH_INOUT);
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
