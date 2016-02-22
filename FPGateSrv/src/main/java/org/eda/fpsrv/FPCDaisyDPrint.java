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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.Thread.sleep;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Dimitar Angelov
 */
public class FPCDaisyDPrint extends FPCBase {

    private int lastErrorCode;
    private String lastCommand;
    private boolean commandFileOpened;
    private StringBuilder commandList;
    
    
    public FPCDaisyDPrint() throws Exception {
        super();
    }
    
    public static String getClassDecription() {
        return "(BETA Version!) This class supports Daisy Fiscal Printers and cash registers using DPrint utility!";
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = new FPParams();
        params.addGroups(
                new FPPropertyGroup("main", "Main Options") {{
                     addProperty(new FPProperty(String.class, "CmdFile", "Command File", "Command file name with absolute path", "C:\\DPrint\\DEFEXEC.txt"));
                     addProperty(new FPProperty(String.class, "AnsFile", "Answer File", "Answer file name with absolute path", "C:\\DPrint\\DEFANSW.txt"));
                     addProperty(new FPProperty(Integer.class, "CmdTimeout", "Command timeout", "Command timeout in secs", 60));
                     addProperty(new FPProperty(
                        String.class
                        , "FileEncoding", "Files encoding", "File Encoding"
                        , "CP1251"
                        , new FPPropertyRule<>(
                                null, null, true
                                , new LinkedHashMap() {{
                                    put("CP1251", "CP1251");
                                }}
                        )
                     ));
                     addProperty(new FPProperty(Integer.class, "LogicalNumber", "Logical Number", "Logical Number of device", 1));
                     addProperty(new FPProperty(String.class, "OperatorCode", "Operator Code", "Operator Code", "1"));
                     addProperty(new FPProperty(String.class, "OperatorPass", "Operator Password", "Operator Password", "0000"));
                     addProperty(new FPProperty(String.class, "TillNumber", "Till Number", "Till Number", "1"));
                     addProperty(new FPProperty(Integer.class, "LWIDTH", "Line width", "Line width in characters", 40));
                }}
        );
        return params;
    }

    @Override
    protected int getLineWidth() {
        int lw = getParamInt("LWIDTH");
        if (lw == 0) 
            lw = 40;
       return lw; 
    }

    @Override
    public void init() throws Exception, FPException {
    }
    
    @Override
    public void destroy() {
    }
    
    protected FPException createException() {
        return new FPException((long)lastErrorCode, "FP Error");
    }
    
    
    protected boolean beginCommandFile() {
        if (commandFileOpened) return false;
        commandList = new StringBuilder();
        commandFileOpened = true;
        return commandFileOpened;
    }

    public static final HashMap<String, String> resCodeList = new HashMap<>(); static {
        // Classic Answer type
        resCodeList.put("Ok", "No error");
        resCodeList.put("Sd", "Communication Error");
        resCodeList.put("Er", "Command failed, or bad parameters");
        // Error code answer type
        resCodeList.put("0", "No Error");
        resCodeList.put("1", "Communication Error");
        resCodeList.put("2", "Incorrect Command");
        resCodeList.put("3", "Failed to configure comm. port");
        resCodeList.put("4", "Unknown Fiscal Device");
        resCodeList.put("5", "Syntax Error");
        resCodeList.put("6", "Command File Read/Write Error");
        resCodeList.put("7", "Bad command file format");
        resCodeList.put("8", "Bad registration key");
        resCodeList.put("9", "Bad command parameters");
        resCodeList.put("10", "Reserved");
        resCodeList.put("11", "Command failed");
        resCodeList.put("12", "User break");
        resCodeList.put("13", "No Paper");
        resCodeList.put("20", "SHA1 error");
        resCodeList.put("21", "SHA1 file error");
    }
    
    protected void processAnswer(List<String> aLines) throws FPException {
        String line;
        String[] parts;
        String resCode;
        List<String> errList = new ArrayList<>();
        String errMsg;
        for (int i = 0; i < aLines.size(); i++) {
            line = aLines.get(i);
//            System.out.println(line.substring(0, 16));
            parts = line.substring(0, 16).split(",");
            if (parts.length == 5) {
                localLogger.debug("Cmd:"+parts[0]+" LN:"+parts[1]+" SN:"+parts[2]+" RLN: "+parts[3]+" RES:"+parts[4]); 
                resCode = parts[4];
                if (!resCode.equals("0") && !resCode.equals("Ok")) {
                    errMsg = "Error while executing command at line: "+Integer.toString(i+1)+" "+(resCodeList.containsKey(resCode)?resCodeList.get(resCode):"Unknown result code:"+resCode);
                    errList.add(errMsg);
                    localLogger.warning(errMsg);
                }
            } else {
                errMsg = "Malformed line result at: "+Integer.toString(i+1);
                errList.add(errMsg);
                localLogger.warning(errMsg);
            }   
        }
        if (errList.size() > 0) {
            throw new FPException((long)-1, errList.toString());
        }
    }
    
    protected void sendCommandFile() throws FPException {
        // TODO: Create command file and process waiting for answer
        if (commandFileOpened) {
            localLogger.debug("Sending commad file:");
            localLogger.debug(commandList.toString());
            int timeoutSecs = getParamInt("CmdTimeout");
            try {
                // Write commands to the comannd file
                File cmdFile = new File(getParam("CmdFile"));
                String charSet = getParam("FileEncoding").equals("CP1251")?"Windows-1251":"UTF-8";
                Files.write(cmdFile.toPath(), commandList.toString().getBytes(charSet));
                // Wait for command file to be deleted
                while(timeoutSecs > 0) {
                    if (!cmdFile.exists()) break;
                    timeoutSecs--;
                    sleep(1000); // 1s
                }
                if (cmdFile.exists()) throw new Exception("File "+cmdFile.getPath()+" was not deleted in given timeout!");
                // Read answer file
                File ansFile = new File(getParam("AnsFile"));
                List<String> aLines = Files.readAllLines(ansFile.toPath(), Charset.forName(charSet));
                // Get error codes from answer file
                processAnswer(aLines);
            } catch (FPException e) {
                throw e; // reraise exeption
            } catch (Exception e) {
                throw new FPException((long)-1, e.getMessage());
            }
            commandList = null;
            commandFileOpened = false;
        } else {
            throw new FPException((long)-1, "Commad file not opened!");
        }    
    }
    
    protected void addCommand(String Cmd, String[] Params) {
        if (!commandFileOpened) beginCommandFile();
        commandList
            .append(Cmd)
            .append(",1,______,_,___;")
            .append((Params != null)?String.join(";", Params)+";":"")
            .append("\n");
    }
    
    @Override
    public void cancelFiscalCheck() throws FPException {
        boolean doSend = beginCommandFile();
        addCommand("V", null);
        if (doSend) sendCommandFile();
    }
    
    @Override
    public String getLastPrintDoc() throws FPException {
        return "__NA__";
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

    protected String taxGroupToNum(taxGroup taxCode) {
        String res; 
        switch (taxCode) {
            case A : res = "1"; break;
            case B : res = "2"; break;
            case C : res = "3"; break;
            case D : res = "4"; break;
            case E : res = "5"; break;
            case F : res = "6"; break;
            case G : res = "7"; break;
            case H : res = "8"; break;
            default :
                res = "1";
        }
        return res;
    }
    
    protected String paymenTypeChar(paymentTypes payType) {
/*		'P'	- Плащане в брой (по подразбиране);
		'N'	- Плащане с кредит;
		'C'	- Плащане с чек;
		'D'	- Плащане с дебитна карта
		'I'	- Програмируем тип плащане 1
		'J'	- Програмируем тип плащане 2
		'K'	- Програмируем тип плащане 3
		'L'	- Програмируем тип плащане 4
*/        
        String pc = "P";
        switch (payType) {
            case CASH : pc = "P"; break;
            case CREDIT : pc = "N"; break;
            case CHECK : pc = "C"; break;
            case DEBIT_CARD : pc = "D"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;
            case CUSTOM3 : pc = "K"; break;
            case CUSTOM4 : pc = "L"; break;
        }
        return pc;
    }

    protected String paymenTypeNum(paymentTypes payType) {
/*		'0'	- Плащане в брой (по подразбиране);
		'1'	- Плащане 1
		'2'	- Плащане 2
		'3'	- Плащане 3
		'4'	- Междинна сума
*/        
        String pc = "0";
        switch (payType) {
            case CASH : pc = "0"; break;
            case CREDIT : pc = "1"; break;
            case CHECK : pc = "2"; break;
            case DEBIT_CARD : pc = "3"; break;
            case CUSTOM1 : pc = "0"; break;
            case CUSTOM2 : pc = "0"; break;
            case CUSTOM3 : pc = "0"; break;
            case CUSTOM4 : pc = "0"; break;
        }
        return pc;
    }
    
    @Override
    public void abnormalComplete() throws FPException {
        cancelFiscalCheck();
    }

    @Override
    public String customCmd(String CMD, String params) throws FPException{
        return "Execution of custom commands is not supported!";
    }
    
    @Override
    public void setOperator(String code, String passwd, String fullName) throws FPException {
        // Not supported command
    }
    
    @Override
    public void openFiscalCheck() throws FPException {
        beginCommandFile();
        // There is no explicit command to do 
        // By default first open 
        localLogger.debug("Dummy open of fiscal check.");
    }

    @Override
    public void closeFiscalCheck() throws FPException{
        sendCommandFile();    
    }

    @Override
    public void closeNonFiscalCheck() throws FPException {
        sendCommandFile();    
    }
    
    @Override
    public void sell(String text, taxGroup taxCode, double price, double quantity, double discountPerc)  throws FPException {
        addCommand("S", new String[]{
            text
            , Double.toString(price)
            , Double.toString(quantity)
            , "1" // Stand
            , "1" // item group
            , taxGroupToNum(taxCode)
            , "0"
            , "0"
        });
    }

    @Override
    public void sell(String text, taxGroup taxCode, double price, double discountPerc) throws FPException {
        this.sell(text, taxCode, price, 1, discountPerc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sellDept(String text, String deptCode, double price, double quantity, double discountPerc)  throws FPException{
        addCommand("S", new String[]{
            text
            , Double.toString(price)
            , Double.toString(quantity)
            , "1" // Stand
            , "1" // item group
            , deptCode
            , "0"
            , "0"
        });
    }

    @Override
    public void printFiscalText(String text) throws FPException {
        String[] lines = splitOnLines(text);
        addCommand("P", new String[]{
            (lines.length > 0)?lines[0]:""
            , (lines.length > 1)?lines[1]:""
            , (lines.length > 2)?lines[2]:""
            , (lines.length > 3)?lines[3]:""
            , (lines.length > 4)?lines[4]:""
        });
    }

    @Override
    public void printNonFiscalText(String text) throws FPException {
        // same as fiscal, but depends of context
        printFiscalText(text);
    }

    @Override
    public StrTable subTotal(boolean toPrint, boolean toDisplay, double discountPerc) throws FPException {
        addCommand("T", new String[]{
            "4"
            , "0"
            , ""
            , ""
            , ""
        });
        StrTable res = new StrTable();
        return res;
    }

    @Override
    public StrTable total(String text, paymentTypes payType, double payAmount) throws FPException {
        addCommand("T", new String[]{
            paymenTypeNum(payType)
            , Double.toString(payAmount)
            , ""
            , ""
            , ""
        });
        StrTable res = new StrTable();
        return res;
    }

    @Override
    public StrTable getCurrentCheckInfo() throws FPException {
        LinkedHashMap<taxGroup,String[]> tax = new LinkedHashMap<>();
        tax.put(taxGroup.A, new String[]{""});
        tax.put(taxGroup.B, new String[]{""});
        tax.put(taxGroup.C, new String[]{""});
        tax.put(taxGroup.D, new String[]{""});
        tax.put(taxGroup.E, new String[]{""});
        tax.put(taxGroup.F, new String[]{""});
        tax.put(taxGroup.G, new String[]{""});
        tax.put(taxGroup.H, new String[]{""});

        StrTable res = new StrTable();
        for(taxGroup taxCode : tax.keySet())
            res.put("Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        
        res.put("LastPrintDocNum", "NA");

        res.put("LFRI_DocNum", "NA");
        res.put("LFRI_DateTime_", "");

        for(taxGroup taxCode : tax.keySet())
            res.put("LFRI_Tax"+taxGroupToCharL(taxCode), tax.get(taxCode)[0]);
        return res;
    }

    @Override
    public StrTable getDiagnosticInfo() throws FPException {
        // TODO: Return Date Time and Serial Number
        StrTable res = new StrTable();
        return res;
    }

    @Override
    public StrTable reportDaily(dailyReportType reportType) throws FPException {
        if (reportType != dailyReportType.Z) 
            throw new FPException((long)-1, "Unsuported daily report type.");
        beginCommandFile();
        addCommand("Z", new String[]{});
        sendCommandFile();
        StrTable res = new StrTable();
        return res;
    }

    @Override
    public StrTable reportByDates(Date from, Date to, datesReportType reportType) throws FPException {
        throw new FPException((long)-1, "Unsuported report type.");
    }

    @Override
    public Date getDateTime() throws FPException {
        beginCommandFile();
        addCommand("X", new String[]{"DATETIME","R"});
        sendCommandFile();
        Date dateTime;
        dateTime = new Date();
        return dateTime;
    }

    @Override
    public Date setDateTime(Date dateTime) throws FPException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        beginCommandFile();
        addCommand("X", new String[]{"DATETIME","W", dateFormat.format(dateTime), timeFormat.format(dateTime)});
        sendCommandFile();
        return getDateTime();
    }

    @Override
    public void paperFeed(int lineCount) throws FPException {
        // NOT IMPLEMENTED
    }

    @Override
    public void printLastCheckDuplicate() throws FPException  {
        beginCommandFile();
        addCommand("D", new String[]{});
        sendCommandFile();
    }

    @Override
    public StrTable getLastFiscalRecordInfo() throws FPException{
        return getCurrentCheckInfo();
    }

    @Override
    public StrTable getJournalInfo() throws FPException {
        return new StrTable();
    }
    
    @Override
    public StrTable getJournal(boolean readAndErase) throws FPException {
        // First try to get journal info in case of error to skip temporary file creation
        StrTable res = getJournalInfo();
    	try {
            //create a temp file
            File temp = File.createTempFile("fp-journal", ".tmp"); 
            //create a temp file for hash
            File tempHash = File.createTempFile("fp-journal-hash", ".tmp"); 
            try {
                beginCommandFile();
                addCommand("J", new String[]{temp.getAbsolutePath(), tempHash.getAbsolutePath(), readAndErase?"1":""});
                sendCommandFile();
                byte[] fileContent = Files.readAllBytes(tempHash.toPath());
                res.put("SHA1", new String(fileContent));
                fileContent = Files.readAllBytes(temp.toPath());
                res.put("Content", Base64.getEncoder().encodeToString(fileContent));
            } finally {
                // remove temporary file
                temp.delete();
                tempHash.delete();
            }
    	} catch(IOException e){
            throw new FPException((long)1, e.getMessage());
    	}    
        return res;
    }
    
}
