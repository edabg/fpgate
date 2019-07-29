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
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * This class supports following fiscal devices Datecs DP-05, DP-25, DP-35, WP-50, DP-150
 * Corresponding to specification PM_XXXXXX_-BUL-FPprotocol_v2_04.pdf
 * @author Dimitar Angelov
 */
public class DeviceDatecsDPV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;
    
    private boolean fiscalCheckRevOpened = false;
    
    public DeviceDatecsDPV1(AbstractProtocol p) {
        super(p);
    }

    public DeviceDatecsDPV1(String portName, int baudRate, int readTimeout, int writeTimeout) {
        super(portName, baudRate, readTimeout, writeTimeout);
        serialPort = SerialPort.getCommPort(portName);
        if (serialPort.openPort()) {
            serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            // Always have to initialize Serial Port in nonblocking (forever) mode!
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, readTimeout, writeTimeout);
            serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            protocol = new ProtocolV10(serialPort.getInputStream(), serialPort.getOutputStream(), ProtocolV10.EncodingType.CP_1251);
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

    @Override
    protected void readDeviceInfo() throws IOException {
        // Request diagnostic info
        String res = cmdCustom(90, "");
        mDeviceInfo = res;
        // By documentation is this:
        // <Name><FwRev><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,< FM >,< Ser >
        // <FwRev> is 4 symbols/bytes
        // But real test with DP150KL is
        // "DP-150,265403 08Nov18 1050,FFFF,00000000,DT735762,02735762"
        // <Name>,<FwRev><sp><FwDate><sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        // From FP-2000KL is 
        // "FP-2000,1.00BG 23NOV18 1000,FFFF,01010111,DT278392,02278392"
        // <Name>,<FwRev><sp><FwDate><sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        String[] commaParts = res.split(",");
        mModelName = commaParts[0];
        if (commaParts.length > 1) {
            String[] spaceParts = commaParts[1].split("\\s");
            mFWRev = spaceParts[0];
            if (spaceParts.length > 1)
                mFWRevDT = spaceParts[1];
            if (spaceParts.length > 2)
                mFWRevDT = mFWRevDT + " " + spaceParts[2];
        }
        if (commaParts.length > 3) 
            mSwitches = commaParts[3];
        if (commaParts.length > 4) 
            mSerialNum = commaParts[4];
        if (commaParts.length > 5) 
            mFMNum = commaParts[5];
    }

    @Override
    protected void initPrinterStatusMap() {
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();
        
        statusBytesDef.put("S0", new String[] // Общо предназначение
        {
             "Синтактична грешка"             // 0 Получените данни имат синктактична грешка.
            ,"Невалиден код на команда"       // 1 Кодът на получената команда е невалиден.
            ,"Неустановени дата/час"          // 2 Не е сверен часовника.
            ,"Не е свързан клиентски дисплей" // 3 Не е свързан клиентски дисплей.
            ,""                               // 4 Резервиран – винаги е 0.
            ,"Обща грешка"                    // 5 Обща грешка - това е OR на 0 и 1.
            ,""                               // 6 Резервиран – винаги е 0.
            ,""                               // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S0", new byte[] {
            2
        });
        errorStatusBits.put("S0", new byte[] {
            0,1,5
        });
        statusBytesDef.put("S1", new String[] // Общо предназначение
        {
             "Препълване"                                 // 0 При изпълнение на командата се е получило препълване на някои полета от сумите. Статус 1.1 също ще се установи и командата няма да предизвика промяна на данните в ФУ.
            ,"Непозволена команда в този контекст"        // 1 Изпълнението на командата не е позволено в текущия фискален режим.
            ,""                                           // 2 Резервиран – винаги е 0.
            ,""                                           // 3 Резервиран – винаги е 0.
            ,""                                           // 4 Резервиран – винаги е 0.
            ,"Има неизпратени документи за повече време"  // 5 Има неизпратени документи за повече от настроеното време за предупреждение
            ,"Вграденият данъчен терминал не отговаря"    // 6 Вграденият данъчен терминал не отговаря.
            ,""                                           // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S1", new byte[] {
            6 // ,5 
        });
        errorStatusBits.put("S1", new byte[] {
            1
        });
        statusBytesDef.put("S2", new String[] // Общо предназначение
        {
             "Край на хартията"                                            // 0 Свършила е хартията. Ако се вдигне този флаг по време на команда, свързана с печат, то командата е отхвърлена и не е променила състоянието на ФУ.
            ,""                                                            // 1 Резервиран – винаги е 0.
            ,"Край на КЛЕН (по-малко от 1 MB от КЛЕН свободни)"            // 2 Край на КЛЕН (по-малко от 1 MB от КЛЕН свободни)
            ,"Отворен Фискален Бон"                                        // 3 Отворен е фискален бон.
            ,"Близък край на КЛЕН (по-малко от 10 MB от КЛЕН свободни)"    // 4 Близък край на КЛЕН (по-малко от 10 MB от КЛЕН свободни).
            ,"Отворен е Служебен Бон"                                      // 5 Отворен е служебен бон.
            ,""                                                            // 6 Не се използува.
            ,""                                                            // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S2", new byte[] {
            4
        });
        errorStatusBits.put("S2", new byte[] {
            0,2
        });
        statusBytesDef.put("S3", new String[] // За състояние на конфигурационните ключета
        {
             "SW1.1" // 0 Резервиран – винаги е 0.
            ,"SW1.2" // 1 Резервиран – винаги е 0.
            ,"SW1.3" // 2 Резервиран – винаги е 0.
            ,"SW1.4" // 3 Резервиран – винаги е 0.
            ,"SW1.5" // 4 Резервиран – винаги е 0.
            ,"SW1.6" // 5 Резервиран – винаги е 0.
            ,"SW1.7" // 6 Резервиран – винаги е 0.
            ,"SW1.8" // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S3", new byte[] {
        });
        errorStatusBits.put("S3", new byte[] {
        });
        statusBytesDef.put("S4", new String[] // За ФП
        {
             "Грешка при запис във фискалната памет"                   // 0 Грешка при запис във фискалната памет.
            ,"Зададен е ЕИК"                                           // 1 Зададен е ЕИК
            ,"Зададени са индивидуален номер на ФУ и номер на ФП."     // 2 Зададени са индивидуален номер на ФУ и номер на ФП.
            ,"Има място за по малко от 50 фискални затваряния"         // 3 Има място за по-малко от 50 записа във ФП.
            ,"Фискалната памет е пълна"                                // 4 ФП е пълна.
            ,"Грешка 0 или 4"                                          // 5 OR на грешки 0 и 4
            ,""                                                        // 6 Не се използува
            ,""                                                        // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S4", new byte[] {
            3
        });
        errorStatusBits.put("S4", new byte[] {
            0, 4, 5
        });
        statusBytesDef.put("S5", new String[] // За ФП
        {
             ""                                          // 0 Резервиран – винаги е 0.
            ,"Фискалната памет e форматирана"            // 1 ФП е форматирана. 
            ,""                                          // 2 Резервиран – винаги е 0.
            ,"ФУ е във фискален режим"                   // 3 ФУ е във фискален режим.
            ,"Зададени са поне веднъж данъчните ставки"  // 4 Зададени са поне веднъж данъчните ставки.
            ,""                                          // 5 Резервиран – винаги е 0.
            ,""                                          // 6 Резервиран – винаги е 0.
            ,""                                          // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S5", new byte[] {
        });
        errorStatusBits.put("S5", new byte[] {
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
        30h (48) ОТВАРЯНЕ НА ФИСКАЛЕН (КЛИЕНТСКИ) БОН
        Област за данни: <OpCode>,<OpPwd>,<NSale>,<TillNmb>[,<Invoice>]
        или
        <OpCode>,<OpPwd>,<TillNmb>[,<Invoice>]
        Отговор: <ErrCode>
        или
        <AllReceipt>,<FiscalReceipt>
        OpCode Номер на оператор /1 до 30/
        OpPwd Операторска парола /1 до 8 цифри/
        NSale Уникален номер на продажба УНП (21 символа CCCCCCCC-CCCC-DDDDDDD формат [0-9A-Zaz]{
        8}-[0-9A-Za-z]{4}-[0-9]{7} ). Параметърът е задължителен в първия синтаксис на командата.
        TillNmb Номер на касово място /цяло число от 1 до 99999/
        Invoice Един символ със стойност “I”. Наличието му предизвиква отпечатването на разширена клиентска
        бележка (фактура). Автоматично след HEADER-а се отпечатва номера на фактурата, а след първата
        команда за плащане разпечатка на сумите по данъчни групи. След плащането трябва да се отпечати
        информация за купувача с команда 57 (39h).
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до
        момента. /4 байта/
        FiscReceipt Броят на всички издадени фискални бонове от последното приключване на деня до момента. /4 байта/
        */
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        fiscalCheckRevOpened = false;
        String params;
        if (UNS.length() == 0) {
            // Without Unique Number of Sell
            params = opCode+","+opPasswd+","+wpNum+(invoice?",I":"");
        } else {
            // With Unique Number of Sell
            // <OpCode>,<OpPwd>,<NSale>,<TillNmb>[,<Invoice>]
            params = opCode+","+opPasswd+","+UNS+","+wpNum+(invoice?",I":"");
        }
        String res = cmdCustom(48, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("FiscReceipt", rData[1]);
        } else {    
            long errCode = -1;
            err("Грешка при отваряне на фискален бон ("+res+")!");
            try {
                errCode = Long.parseLong(res);
            } catch (Exception ex) {
            }
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public void cmdPrintCustomerData(String UIC, int UICType, String seller, String recipient, String buyer, String VATNum, String address)  throws IOException {
        /*
        39h (57) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА
        Област за данни: <EIK><Tab><EIKType>[<Tab><Seller>[<Tab><Receiver>[<Tab><Client>[<Tab><TaxNo>
        [<Tab><Address>]]]]]
        Отговор: <ErrCode>
        Задължителни параметри:
        EIK ЕИК номер на купувача. Между 9 и 14 символа
        Tab Табулация (09H). Разделител между параметрите
        EIKType ЕИК тип. 0 - Булстат, 1 - ЕГН, 2 - ЛНЧ, 3 - Сл. номер
        o 0 - Булстат.
        o 1 - ЕГН.
        o 2 - ЛНЧ.
        o 3 - Сл. номер.
        Опционални параметри:
        • Tab - Табулация (09H). Разделител между параметрите.
        • Seller - Име на продавача. До 36 символа.
        • Receiver - Име на получателя. До 36 символа.
        • Client - Име на купувача. До 36 символа.
        • Seller - Име на продавача. До 36 символа.
        • TaxNo - ЗДДС - номер на купувача. Между 10 и 14 символа.
        • Address - Адрес на купувача. До два реда текст разделени с LF.
        Отговор на командата:
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        При празен или незададен параметър се оставя празно място за попълване на ръка. Трябва да се изпълни непосредствено след
        цялостно плащане на натрупаната за бона сума. След това вече е разрешено затварянето на бона.        
        */
        if (UICType < 0 || UICType > 3)
            throw new FDException("Ivalid UIC type!");
        String params;
        params = UIC+"\t"+Integer.toString(UICType)+"\t"+seller+"\t"+recipient+"\t"+buyer+"\t"+VATNum+"\t"+address;
        String res = cmdCustom(57, params);
        if (!res.equals("P")) {
            long errCode = -1;
            err("Грешка при печат на данни за клиента!");
            try {
                errCode = Long.parseLong(res);
            } catch (Exception ex) {
            }
            throw new FDException(errCode, mErrors.toString());
        }
    }

    
    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws IOException {
        /*
        38h (56) ЗАТВАРЯНЕ (ПРИКЛЮЧВАНЕ) НА ФИСКАЛЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt, FiscReceipt
        Allreceipt Всички издадени бележки от последното приключване на деня до момента.
        FiscReceipt Всички издадени фискални бележки от последното приключване на деня до момента.
        Натрупаните суми от фискалния бон се прибавят към дневните суми в регистрите на оперативната памет.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Команда 53 (35h) не е изпълнена успешно.
        • Платената сума по команда 53 е по-малка от общата сума на фискалния бон.        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(56, "");
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("FiscReceipt", rData[1]);
        } else {    
            long errCode = -1;
            err("Грешка при затваряне на фискален бон ("+res+")!");
            try {
                errCode = Long.parseLong(res);
            } catch (Exception ex) {
            }
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }
    
    @Override
    public void cmdCancelFiscalCheck() throws IOException {
        /*
        3Ch (60) АНУЛИРАНЕ (ПРЕКРАТЯВАНЕ) НА ФИСКАЛЕН БОН
        Област за данни: Няма данни
        Отговор: Няма данни
        Командата е допустима само в отворен фискален бон, и то преди изпълнението на команда 53 (Total). Предизвиква
        отказването на всички натрупани в бона суми. Отпечатва се с двойна ширина “=АНУЛИРАН=” и „НЕ СЕ ДЪЛЖИ ПЛАЩАНЕ”
        и бонът завършва с надпис “ФИСКАЛЕН БОН”.        
        */
        cmdCustom(60, "");
    }

    @Override
    public void cmdPrintFiscalText(String text) throws IOException {
        /*
        36h (54) ПЕЧАТАНЕ НА ФИСКАЛЕН СВОБОДЕН ТЕКСТ
        Област за данни: Text
        Отговор: Няма данни
        Text Текст до 36 байта
        В началото и края на реда се отпечатва символът '#'. Необходимо е да е отворен фискален бон. В противен случай не се
        отпечатва текста. Ако текстът е по-дълъг от 36 символа, то буквите след 36-ят се изрязват.        
        */        
        int maxLen = 36;
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!("+text+")");
            text = text.substring(0, maxLen);
        }
        cmdCustom(54, text);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        26H (38) ОТВАРЯНЕ НА СЛУЖЕБЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента /4
        байта/.
        ФУ извършва следните действия:
        • Отпечатва се HEADER.
        • Връща се отговор, съдържащ Allreceipt.
        Командата не може да се изпълни, ако:
        • Има отворен фискален бон.
        • Вече е отворен служебен бон.        
        */        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String res = cmdCustom(38, "");
        if (res.length() > 0) {
            response.put("AllReceipt", res);
        } else {    
            long errCode = -1;
            err("Грешка при отваряне на нефискален бон ("+res+")!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public void cmdPrintNonFiscalText(String text, int font) throws IOException {
        /*
        2Ah (42) ПЕЧАТАНЕ НА СВОБОДЕН ТЕКСТ В СЛУЖЕБЕН БОН
        Област за данни: <Height><tab>[<Data>]
        или
        [<Data>]
        Отговор: Няма данни
        • Height
        '1' - Печат с нормална височина.
        '2' - Печат с 2x височина.
        '3' - Печат с 3x височина.
        • tab – Разделител-един байт със стойност 0x09.
        Опционални параметри:
        • Data - Текст до 40 символа.
        В началото и края на реда се отпечатва символът '#'.        
        */        
        int maxLen = 40;
        String params = "";
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
            text = text.substring(0, maxLen);
        }
        if ((font >= 1) && (font <= 3))
            params = Integer.toString(font)+"\t"+text;
        else 
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
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента /4
        байта/.
        ФУ извършва следните действия:
        • Отпечатва се FOOTER.
        • Отпечатва се поредния номер, датата и часа на документа
        • Отпечатва се “СЛУЖЕБЕН БОН”.
        • Връща се отговор, съдържащ Allreceipt.
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(39, "");
        if (res.length() > 0) {
            response.put("AllReceipt", res);
        } else {    
            long errCode = -1;
            err("Грешка при затваряне на нефискален бон!");
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
        Lines Броят на редовете, с които да бъде придвижена хартията. Трябва да бъде положително число не по-голямо от 99
        /1 или 2 байта/. Ако параметър липсва, подразбира се 1 ред.        
        */        
        cmdCustom(44, Integer.toString(Integer.max(1, Integer.min(lineCount, 99))));
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        31h (49) РЕГИСТРИРАНЕ (ПРОДАЖБА) НА СТОКА
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        или
        [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        Отговор: Няма данни
        L1 Текст до 22 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 22 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от
        параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 9 включително. Продажбата се причислява към данъчната
        група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000. Дължина до 8
        значещи цифри (не повече от 3 след десетичната точка). Произведението Price*Qwan се закръгля от ФУ
        до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от
        знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се
        до 2 десетични знака.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от
        знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        ФУ извършва следните действия:
        • Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на
        департамента.
        • Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено
        количество, информацията за него също се отпечатва.
        • Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване
        се установяват съответните битове от статус полето.
        • Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на ФУ.
        Стойностите за целия ден се отпечатват при дневния финансов отчет.
        • Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива,
        се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Вече са направени максималния брой продажби за един бон (120).
        • Командата (35h) е изпълнена успешно.
        • Сумата по някоя от данъчните групи става отрицателна.
        • Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
        • КЛЕН е пълна.
        
        
        */        
        int maxLen = 22;
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
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        или
        [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        Отговор: Няма данни
        L1 Текст до 22 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 22 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от
        параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 9 включително. Продажбата се причислява към данъчната
        група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000. Дължина до 8
        значещи цифри (не повече от 3 след десетичната точка). Произведението Price*Qwan се закръгля от ФУ
        до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от
        знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се
        до 2 десетични знака.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от
        знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        ФУ извършва следните действия:
        • Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на
        департамента.
        • Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено
        количество, информацията за него също се отпечатва.
        • Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване
        се установяват съответните битове от статус полето.
        • Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на ФУ.
        Стойностите за целия ден се отпечатват при дневния финансов отчет.
        • Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива,
        се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Вече са направени максималния брой продажби за един бон (120).
        • Командата (35h) е изпълнена успешно.
        • Сумата по някоя от данъчните групи става отрицателна.
        • Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
        • КЛЕН е пълна.
        */        
        int maxLen = 42;
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
       33h (51)	МЕЖДИННА СУМА
       Област за данни:	<Print><Display>[,Perc|;Abs]
       Отговор:	SubTotal,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH
       Print	Един байт, който ако е ‘1’ стойността на под сумата ще се отпечата.
       Display	Един байт, който ако е ‘1’ стойността на под сумата ще се покаже на дисплея.
       Perc	Незадължителен параметър, който показва стойността в проценти на отстъпката или надбавката върху натрупаната до момента сума.
       Abs	Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума (до 8 значещи цифри). 
               Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.

       Допустим е само един от аргументите Perc или Abs.

       SubTotal	Сумата до момента за текущия фискален бон /до 10 байта/
       TaxA	Сумата по данъчна група А /до 10 байта/
       TaxB	Сумата по данъчна група Б /до 10 байта/
       TaxC	Сумата по данъчна група В /до 10 байта/
       TaxD	Сумата по данъчна група Г /до 10 байта/
       TaxE	Сумата по данъчна група Д /до 10 байта/
       TaxF	Сумата по данъчна група Е /до 10 байта/
       TaxG	Сумата по данъчна група Ж /до 10 байта/
       TaxH	Сумата по данъчна група З /до 10 байта/
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
        Област за данни: [<Line1>][<Lf><Line2>]<Tab>[[<PaidMode>]<[Sign]Amount>][*<Type>]
        Отговор: <PaidCode><Amount>
        Line1 Текст до 42 байта съдържащ първия ред
        Lf Един байт със съдържание 0Ah
        Line2 Текст до 42 байта съдържащ втория ред
        Tab Един байт със съдържание 09h
        PaidMode Незадължителен код, указващ начина на плащане. Може да има следните стойности:
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ - Плащане с кредит;
        ‘C’ - Плащане с дебитна карта;
        ‘D’ - Плащане с НЗОК;
        ‘I’ - Плащане с ваучер;
        ‘J’ - Плащане с купон;
        В зависимост от кода сумите се натрупват в различни регистри и могат да бъдат получени в дневния отчет.
        Sign Един байт със стойност ‘+’, указващ знака на Amount (сумата, която се плаща).
        Amount Сумата, която се плаща /до 10 значещи цифри/.
        Type Тип на плащане с пинпад( само за плащане с карта, PaidMode = 'C')
        ‘1’ - Плащане с пари;
        ‘12’ - Плащане с точки по схема лоялен клиент( само за Борика );
        PaidCode Един байт - резултат от изпълнението на командата.
        ‘F’ - Грешка.
        ‘E’ - Изчислената междинна сума е отрицателна. Плащане не се извършва и Amount ще съдържа
        отрицателната междинна сума.
        ‘D’ - Ако платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.
        ‘R’ - Ако платената сума е по-голяма от сбора на бележката. Ще се отпечати съобщение “РЕСТО” и
        рестото се връща в Amount.
        ‘I’ - Сумата по някоя данъчна група е бил отрицателен и затова се е получила грешка. В Amount се връща
        текущата междинна сума.
        Amount До 9 цифри със знак. Зависи от PaidCode.
        Ако ФУ работи с пинпад, при плащане с PaidMode = 'C' отговора има следния вид:
        “F,PP,<TransError>”- Грешка от пинпада. Грешката се съдържа в <TransError>
        “F,PP,'-111555”- Няма връзка с пинпад.
        “F,PP,-111558” -Прекъсната връзка с пинпада. Транзакциите са еднакви. Опитайте пак.
        “F,PP,-111560,<Sum>,<CardNum>”- Вероятно транзакцията е минала успешно в пинпада, но в касата не е.
        Връщат се сумата в стотинки и последните 4 цифри от номера на картата за справка на
        транзакциите. При тази грешка да се извика команда 55, с опция 13 и избор между печат (1)
        или анулиране (2) на транзакцията.
        <TransError> Грешка, върната от пинпада.
        <Sum> Сума на транзакцията в стотинки.
        <CardNum> Последните 4 цифри от номера на картата, с която е направена транзакцията.
        Тази команда предизвиква изчисляването на сумите от фискалния бон, отпечатването на сумата със специален шрифт и
        показването й на дисплей. При успешно изпълнение на командата се генерира импулс за отваряне на чекмедже, ако това е
        разрешено с подкоманда ‘X’ на команда 43. Ако след символа <Tab> няма повече данни, то ФУ автоматично плаща цялата налична
        сума в брой.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Натрупаната сума е отрицателна.
        • Ако някоя от натрупаните суми по данъчни групи е отрицателна.
        */        
        int maxLen = 42;
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
        String[] validPaymentTypes = {"","P","N","C","D","I","J"};
        if (!Arrays.asList(validPaymentTypes).contains(paymentType))
            throw new FDException(-1L, "Невалиден начин на плащане ("+paymentType+")!");
        if (paymentType.length() > 0)
            params += paymentType;
        if (abs(amount) >= 0.01)
            params += String.format(Locale.ROOT, "%.2f", amount);
        if (pinpadOpt.length() > 0) {
            if (paymentType.equals("C")) {
                if (!pinpadOpt.equals("1") && !pinpadOpt.equals("12")) 
                    throw new FDException(-1L, "Невалидна пинпад опция("+pinpadOpt+")!");
                params += "*"+pinpadOpt;
            } else
                throw new FDException(-1L, "Пинпад опция е възможна само при плащане с карта (paymentType=C)!");
        }    
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(53, params);
        if (res.length() > 0) {
            if (!res.contains(",")) {
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
                // Result id fo payment with card vi pinpad connection
                warn("Пинпад плащане, не се парсира!");
                response.put("PaidCode", "PINPAD");
                response.put("PaidResponse", res);
                response.put("Amount", Double.toString(0));
            }
        } else {
            long errCode = -1;
            err("Липсва отговор при команда за обща сума и плащане на фискален бон!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        2Eh (46) ОТВАРЯНЕ НА СТОРНО ФИСКАЛЕН (КЛИЕНТСКИ) БОН
        Област за данни: <OpCode>,<OpPwd>,<NSale>,<TillNmb>,<DocType>,<DocNumber>,<DocDateTime>,
        <FMNumber>[,<Invoice>,<InvNumber>,<Reason>]
        Отговор: <ErrCode>
        или
        <AllReceipt>,<StrReceipt>
        Задължителни параметри:
        • OpCode - Номер на оператор (число 1 - 30).
        • OpPwd - Парола на оператора (до 8 символа).
        • NSale - Уникален номер на продажба (21 символа CCCCCCCC-CCCC-DDDDDDD формат [0-9A-Za-z]{8}-[0-
        9A-Za-z]{4}-[0-9]{7} ), ако се сторнира по бон за който има издаден УНП. Ако за бона няма издаден УНП, това
        поле е празно.
        • TillNmb - Номер на касово място (число 1 - 99999).
        • DocType - Причина за сторно документа (число 0 - 1).
        o 0 - операторска грешка.
        o 1 - връщане/замяна.
        o 2 - намаление на данъчната основа.
        • DocNumber - Номер на документа по който е сторното (число 1 - 9999999).
        • DocDateTime - Дата и час на документа по който е сторното формат (ДДММГГччмм или ДДММГГччммсс).
        o Пример: 30 Март 2018 16:13 - "3003181613" или "300318161300".
        • FMNumber - Номер на ФП от документа по който е сторното (число записано с 8 знака).
        Опционални параметри:
        • Invoice - Сторно по фактура един символ със стойност - 'I'.
        • InvNumber - Номер на фактура по която е сторното (число 1 - 9999999999).
        • Reason - Причина за издаване. До 42 символа.
        • ErrCode - Един байт код на грешка.
        o 'F' - Грешка.
        • AllReceipt - Броят на всички издадени бонове (фискални и нефискални) от последното приключване на деня до
        момента.
        • StrReceipt - Броят на всички сторно фискални бонове от последното приключване на деня до момента.        
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
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
        
        params = opCode+","+opPasswd+","+UNS+","+wpNum+","+RevType+","+RevDocNum+","+dateFormat.format(RevDateTime)+","+RevFMNum;
        boolean reasonPrinted = false;
        if (invoice) {
            if (!RevInvNum.matches("^\\d{1,10}$")) 
                throw new FDException("Невалиден номер на фактура, по която е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999999!");
            if (RevReason.length() > 42)
                RevReason = RevReason.substring(0, 42);
            params = params + ",I,"+RevInvNum+","+RevReason;
            reasonPrinted = true;
        }
        
        String res = cmdCustom(46, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("StrReceipt", rData[1]);
            if (!reasonPrinted && (RevReason.length() > 0)) {
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
        Amount Сумата за регистриране (до 10 значещи цифри). В зависимост от знака на числото тя се интерпретира като
        внос или износ.
        ExitCode ‘P’ Заявката е изпълнена. Ако заявената сума е ненулева, ФУ отпечатва служебен бон за регистриране на
        операцията.
        ‘F’ Заявката е отказана. Това става, ако:
        • Касовата наличност е по-малка от заявения служебен износ.
        • Има отворен фискален или служебен бон.
        CashSum Касова наличност. Освен от тази команда сумата нараства и при всяко плащане в брой.
        ServIn Сумата от всички команди “Служебен внос”.
        ServOut Сумата от всички команди “Служебен износ”.
        Променя съдържанието на регистъра за касова наличност. В зависимост от знака на посочената сума тя се натрупва в
        регистъра за служебен внос или износ. Информацията не се записва във ФП и е достъпна до момента на приключване на деня.
        Разпечатва се при команда 69 (45h) и при предизвикване на дневен отчет без нулиране от ФУ. При успешно изпълнение на
        командата с параметър се генерира импулс за отваряне на чекмедже, ако това е разрешено с подкоманда ‘X’ на команда 43.
        */  
        String params = "";
        if (abs(ioSum) >= 0.01) {
            params = String.format(Locale.ROOT, "%.2f", ioSum);
        }

        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(70, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            if (rData[0].equals("P")) {
                // Заявката е изпълнена
                response.put("CashSum", reformatCurrency((rData.length > 1)?rData[1]:"0", 100));
                response.put("CashIn", reformatCurrency((rData.length > 2)?rData[2]:"0", 100));
                response.put("CashOut", reformatCurrency((rData.length > 3)?rData[3]:"0", 100));
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
        OpCode Код на оператор. От 1 до 30.
        Pwd Парола (1 до 8 цифри).
        OpName Име на оператор (до 10 символа).
        Задава едно от тридесетте имена на оператори. Номерът и името на оператора се отпечатва в началото на всеки фискален
        (клиентски) бон. След инициализация или нулиране на оперативната памет и имената на операторите са “ОПЕРАТОРХХ”. Където
        ХХ е номера на оператора.
        */
        String params = opCode+","+opPasswd+","+name;
        cmdCustom(102, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        40h (64) ИНФОРМАЦИЯ ЗА ПОСЛЕДНИЯ ФИСКАЛЕН ЗАПИС
        Област за данни: [<Type>]
        Отговор: ErrCode[,N,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Date]
        Опционални параметри:
        • Type - Тип на исканите данни за последния фискален запис.
        o Без параметър - Суми по данъчни групи от продажби.
        o '0' - Суми по данъчни групи от продажби.
        o '1' - Суми по данъчни групи от сторно операции.
        ErrCode Код на грешка:
        ‘P’ Командата е успешна. Следват данни.
        ‘F’ Не се чете последния запис.
        N Това е номера на последния фискален запис - 4 байта.
        TaxX Сума оборот по данъчна група X, в зависимост от входния параметър. 12 байта със знак.
        Date Датата на фискалния запис - 6 байта /DDMMYY/.
        Командата води до предаване на информацията за последния запис във ФП към компютъра.        
        */
        String res = cmdCustom(64, "0");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.contains(",")) {
            String[] resLines = res.split(",");
            String errCode = resLines[0].substring(0,1);
            String DocNum = "0000000";
            if (errCode.equals("P")) {
                DocNum = resLines[1];
                response.put("DocNum", DocNum);
                response.put("TaxA", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
                response.put("TaxB", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
                response.put("TaxC", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
                response.put("TaxD", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
                response.put("TaxE", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
                response.put("TaxF", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
                response.put("TaxG", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
                response.put("TaxH", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
                String resDate = "";
                if (resLines.length > 10) {
                    String r = resLines[10];
                    if (r.length() == 6) {
                        // DDMMYY
                        // 012345
                        resDate = "20"+r.substring(4, 6)+"-"+r.substring(2, 4)+"-"+r.substring(0, 2);
                    } else {
                        //??
                    }
                }
                response.put("DocDate", resDate);
            } else {
                err("Грешка при четене на информация за последния фискален запис ("+res+")!");
                throw new FDException(mErrors.toString());
            }    
        } else {
            err("Грешка при четене на информация за последния фискален запис!");
            throw new FDException(mErrors.toString());
        }
        // Сторно операции
        res = cmdCustom(64, "1");
        if (res.contains(",")) {
            String[] resLines = res.split(",");
            String errCode = resLines[0].substring(0,1);
//            String DocNum = "0000000";
            if (errCode.equals("P")) {
  //              DocNum = resLines[1];
//                response.put("DocNum", DocNum);
                response.put("RevTaxA", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
                response.put("RevTaxB", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
                response.put("RevTaxC", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
                response.put("RevTaxD", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
                response.put("RevTaxE", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
                response.put("RevTaxF", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
                response.put("RevTaxG", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
                response.put("RevTaxH", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
            } else {
                err("Грешка при четене на информация за последния фискален запис ("+res+")!");
                throw new FDException(mErrors.toString());
            }    
        } else {
            err("Грешка при четене на информация за последния фискален запис!");
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
        Област за данни: [<Option>]
        Отговор: Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        Option Незадължителен параметър, управляващ вида на генерирания отчет:
        ‘0’ Отпечатва се Z-отчет. Разпечатката завършва с надпис “ФИСКАЛЕН БОН”.
        ‘2’ Прави се дневен финансов отчет без нулиране (т. е. не се извършва запис във ФП и нулиране на
        регистрите). Разпечатката завършва с текст “СЛУЖЕБЕН БОН”.
        Closure Номер на фискалния запис - 4 байта.
        FM_Total Сумата от всички продажби - 12 байта със знак
        TotX Сумите по всяка от данъчните групи ‘А’, ‘Б’, ‘В’, … - 12 байта със знак.        
        
        75h (117) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ПЕЧАТ НА ДАННИ ПО ДЕПАРТАМЕНТИ
        Област за данни: [<Option>]
        Отговор: Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        Преди дневния отчет се отпечатва и отчет по департаменти, за които има продажби за деня.
        Команди 69, 108, 117 и 118 с опция ‘0’ (дневен финансов отчет с нулиране) нулират и натрупаните данни по департаменти.        
        */
        if (!option.equals("0") && !option.equals("2"))
            throw new FDException("Невалиден тип за дневен отчет! 0 => Z отчет, 2 => X отчет");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (subOption.equals("D")) { // By Departments
            String res = cmdCustom(117, option.equals("0")?"0":"");
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
        // Request diagnostic info
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(90, "1");
        response.put("RawInfo", res);
        // By documentation is this:
        // <Name><FwRev><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,< FM >,< Ser >
        // <FwRev> is 4 symbols/bytes
        // But real test with DP150KL is
        // "DP-150,265403 08Nov18 1050,FFFF,00000000,DT735762,02735762"
        // <Name>,<FwRev><sp><FwDate><sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
//{ErrorCode}<SEP>{Name}<SEP>{FwRev}<SEP>{FwDate}<SEP>{FwTime}<SEP>{Checksum}<SEP>{Sw}<SEP>{SerialNumber}<SEP>{FMNumber}<SEP>        
        // From FP-2000KL is 
        // "FP-2000,1.00BG 23NOV18 1000,FFFF,01010111,DT278392,02278392"
        // <Name>,<FwRev><sp><FwDate><sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        String[] commaParts = res.split(",");
        response.put("ModelName", commaParts[0]);
        response.put("FWRev", "");
        response.put("FWRevDT", "");
        response.put("Switches", "");
        response.put("SerialNum", "");
        response.put("FMNum", "");
        response.put("CheckSum", "");
        if (commaParts.length > 1) {
            String[] spaceParts = commaParts[1].split("\\s");
            response.put("FWRev", spaceParts[0]);
            if (spaceParts.length > 2)
                response.put("FWRevDT", spaceParts[1] + " " + spaceParts[2]);
            else if (spaceParts.length > 1)
                response.put("FWRevDT", spaceParts[1]);
        }
        if (commaParts.length > 2) 
            response.put("CheckSum", commaParts[2]);
        if (commaParts.length > 3) 
            response.put("Switches", commaParts[3]);
        if (commaParts.length > 4) 
            response.put("SerialNum", commaParts[4]);
        if (commaParts.length > 5) 
            response.put("FMNum", commaParts[5]);
        // Request additional diagnostic information via cmd 71
        // Info about NAP terminal
        res = cmdCustom(71, "6");
        /*
        Отговор (2): ErrCode[,<Synhronized>,<Мinutes>,<NapSellXD>,<NapSellChN>,<SellForSend>,
        <SellErrDocNumber>,<SellErrCnt>,<SellErrCode>,<ZChN>,<ZForSend>,<ZErrDocNumber>,
        <ZErrCnt>,<ZErrCode>,<NapLastSentDate>,<NapLastErr>]
        */        
        response.put("RawInfoNAPTerminal", res);
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("P")) {
                commaParts = res.split(",");
                if (commaParts.length > 1)
                    response.put("NT_Synhronized", commaParts[1]);
                if (commaParts.length > 2)
                    response.put("NT_Мinutes", commaParts[2]);
                if (commaParts.length > 3)
                    response.put("NT_NapSellXD", commaParts[3]);
                if (commaParts.length > 4)
                    response.put("NT_NapSellChN", commaParts[4]);
                if (commaParts.length > 5)
                    response.put("NT_SellForSend", commaParts[5]);
                if (commaParts.length > 6)
                    response.put("NT_SellErrDocNumber", commaParts[6]);
                if (commaParts.length > 7)
                    response.put("NT_SellErrCnt", commaParts[7]);
                if (commaParts.length > 8)
                    response.put("NT_SellErrCode", commaParts[8]);
                if (commaParts.length > 9)
                    response.put("NT_ZChN", commaParts[9]);
                if (commaParts.length > 10)
                    response.put("NT_ZForSend", commaParts[10]);
                if (commaParts.length > 11)
                    response.put("NT_ZErrDocNumber", commaParts[11]);
                if (commaParts.length > 12)
                    response.put("NT_ZErrCnt", commaParts[12]);
                if (commaParts.length > 13)
                    response.put("NT_ZErrCode", commaParts[13]);
                if (commaParts.length > 14)
                    response.put("NT_NapLastSentDate", commaParts[14]);
                if (commaParts.length > 15)
                    response.put("NT_NapLastErr", commaParts[15]);
            } else {
                err("Грешка при изпълнение на команда за получаване на информация за данъчния терминал!");
            }
        } else
            err("Неочакван отговор при получаване на информация за данъчния терминал!");
        // Request additional diagnostic information via cmd 71 
        // Info about modem (GPRS)
        res = cmdCustom(71, "7");
        /*
        Отговор (3): ErrCode[,<SIM>,<IMSI>,<isReg>,<Operator>,<Signal>]
        */        
        response.put("RawInfoGPRS", res);
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("P")) {
                commaParts = res.split(",");
                if (commaParts.length > 1)
                    response.put("GPRS_SIM", commaParts[1]);
                if (commaParts.length > 2)
                    response.put("GPRS_IMSI", commaParts[2]);
                if (commaParts.length > 3)
                    response.put("GPRS_isReg", commaParts[3]);
                if (commaParts.length > 4)
                    response.put("GPRS_Operator", commaParts[4]);
                if (commaParts.length > 5)
                    response.put("GPRS_Signal", commaParts[5]);
            } else {
                err("Грешка при изпълнение на команда за получаване на информация за GPRS!");
            }
        } else
            err("Неочакван отговор при получаване на информация за GPRS!");
        // Request additional diagnostic information via cmd 71 
        // Info about Flash Memory
        res = cmdCustom(71, "8");
        /*
        Отговор (4): ErrCode,<DeviceID>,<VolumeSize>,<ValidBlocks>,<BlockSize>
        */        
        response.put("RawInfoFlashMem", res);
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("P")) {
                commaParts = res.split(",");
                if (commaParts.length > 1)
                    response.put("FM_DeviceID", commaParts[1]);
                if (commaParts.length > 2)
                    response.put("FM_VolumeSize", commaParts[2]);
                if (commaParts.length > 3)
                    response.put("FM_ValidBlocks", commaParts[3]);
                if (commaParts.length > 4)
                    response.put("FM_BlockSize", commaParts[4]);
            } else {
                err("Грешка при изпълнение на команда за получаване на информация за Flash Memory!");
            }
        } else
            err("Неочакван отговор при получаване на информация за Flash Memory!");
        return response;
    }


    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        47h (71) ПОЛУЧАВАНЕ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: [<Type>]
        Отговор (1): [ErrCode]
        Отговор (2): ErrCode[,<Synhronized>,<Мinutes>,<NapSellXD>,<NapSellChN>,<SellForSend>,
        <SellErrDocNumber>,<SellErrCnt>,<SellErrCode>,<ZChN>,<ZForSend>,<ZErrDocNumber>,
        <ZErrCnt>,<ZErrCode>,<NapLastSentDate>,<NapLastErr>]
        Отговор (3): ErrCode[,<SIM>,<IMSI>,<isReg>,<Operator>,<Signal>]
        Отговор (4): ErrCode,<DeviceID>,<VolumeSize>,<ValidBlocks>,<BlockSize>
        Опционални параметри:
        • Type
        o Без параметър - Печат на диагностична информация.
        o '0' - Печат на диагностична информация. Отпечатвания бон съдържа: датата и версията на програмното
        осигуряване, контролната сума на фирмуера и текущата дата и час Отговор (1)
        o '1' - Печат на тест - GPRS. Отговор (1)
        o '5' - Печат на информация за данъчния терминал. Отговор (1)
        o '6' - Получаване на информация за данъчния терминал. Отговор (2)
        o '7' - Получаване на информация за модема. Отговор (3)
        o '8' - Информация за флеш паметта. Отговор (4)
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • <Synhronized> - Състояние на данъчния терминал (ДТ). Един байт със стойност '1' или '0'.
        o '1' - ДТ е синхронизиран с НАП.
        o '0' - ДТ е в процес на синхронизация.
        • < Мinutes>=""> - Минути до следващ опит за синхронизация число (0 - 120). При синхронизиран ДТ (Synhronized
        = 1), винаги е 0.
        • <NapSellXD> - Дата и час на последен потвърден от НАП документ за продажба. Стринг във формат "ДД-ММ-
        ГГ чч:мм:сс".
        • <NapSellChN> - Номер на последния документ за продажба потвърден от НАП.
        • <SellForSend> - Брой документи за изпращане ( бройката включва всички документи издадени след последния
        документ за продажба потвърден от НАП, които все още не са обработени от ДТ ).
        • <SellErrDocNumber> - Номер на документ за продажба, при изпращане на който, сървърът на НАП е отговорил с
        грешка.
        • <SellErrCnt> - Брой на опитите за изпращане, на документа за продажба с номер SellErrDocNumber, завършили с
        една и съща грешка от НАП сървъра.
        • <SellErrCode> - Код на грешката от НАП сървъра за документ с номер SellErrDocNumber.
        • <ZChN> - Номер на последно изпратен в НАП дневен Z отчет.
        • <ZForSend> - Брой на чакащите за изпращане дневни Z отчети.
        • <ZErrDocNumber>- Номер на дневен Z отчет, при изпращане на който, сървърът на НАП е отговорил с грешка.
        • <ZErrCnt> - Брой на опитите за изпращане, на дневен отчет с номер ZErrDocNumber, завършили с една и съща
        грешка от НАП сървъра.
        • <ZErrCode> - Код на грешката от НАП сървъра за документ с номер ZErrDocNumber.
        • <NapLastSentDate> - Дата и час на последната връзка с НАП.
        • <NapLastErr> - Статус на последната връзка с НАП (цяло число със знак). 0 - връзката е била успешна.
        • <SIM> - Състояние на SIM картата число (0-1). 1 - готова за работа.
        • <IMSI> - IMSI номер на SIM картата ascii string. Попълва се след като, модемът е инициализирал успешно SIM
        картата.
        • <isReg> - Състояние на регистрацията число (0-1). 1 - модемът е регистриран в мобилната мрежа.
        • <Operator> - Име на мобилен оператор ascii string.
        • <Signal> - Ниво на сигнала в проценти число(1-100).
        • <DeviceID> - ID на чипа.
        • <VolumeSize> - Размер на паметта.
        • <ValidBlocks> - Брой блокове.
        • <BlockSize> - Размер на един блок        
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
        W: Първо чака да се отпечатат всички буфери на ФУ.
        X: Не изчаква ФУ.
            Sn Статус байт N.        
        */
LOGGER.fine("Test");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(74, "X");
        byte[] bytes = res.getBytes();
        for (int i = 0; i < 6; i++) {
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
        
        return response;
    }

    @Override
    public Date cmdGetDateTime() throws IOException {
        /*
        3Eh (62) ПРОЧИТАНЕ НА ДАТАТА И ЧАСА
        Област за данни: Няма данни.
        Отговор: <DD-MM-YY><Space><HH:MM:SS>        
        */
        String res = cmdCustom(62, "");
        Date dt;
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
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
        3Dh (61) УСТАНОВЯВАНЕ НА ДАТАТА И ЧАСА
        Област за данни: <DD-MM-YY><space><HH:MM[:SS]>
        Отговор: Няма данни
        Не може да се установява дата, по-ранна от датата на последния запис във ФП. Предвидено е да се работи до 2099 година
        включително. След RESET на паметта командата трябва задължително да се изпълни, за да с продължи нормалната работа на ФУ.
        При изпълнение на командата се записва блок във ФП за извършеното нулиране.
        Не е възможно задаване на дата и час по-ранна от последния отпечатан документ, записан в КЛЕН. Това е с цел коректно
        търсене на документи от КЛЕН по дата и час за печат или изтегляне по серийния порт като текст.        
        */
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        String params = format.format(dateTime);
        cmdCustom(61, params);
    }

    @Override
    public void cmdPrintCheckDuplicate() throws IOException {
        /*
        6Dh (109) ПЕЧАТ НА ДУБЛИРАЩ БОН
        Област за данни: <Count>
        Отговор: Няма данни
        Count Брой дублиращи бонове (приема се само стойност 1!).
        Предизвиква отпечатването на копие на последния затворен фискален бон с продажби. Копието се маркира като
        СЛУЖЕБЕН БОН и веднага след HEADER-а се отпечатва ред с удебелен шрифт “ДУБЛИКАТ”. При повторен опит командата
        ще откаже да печати.        
        */
        //TODO: Check on fiscalized device
        cmdCustom(109, "1");
    }

    @Override
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException {
        /*
        7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        Област за данни: <Option>[,<DocNum>,<RecType>[,<ToNumber>]]
        Отговор: в зависимост от параметрите
        Задължителни параметри:
        • Option - Вид на исканата информация
        o '0' - Подготовка на документа за четене. Отговор (1)
        o '1' - Четене на следващ текстов ред от документа. Отговор (2)
        o '2' - Четене на следващ ред със структурирана информация от документа. Отговор (3)
        o '3' - Отпечатване на документа в текстов вид на хартия (копие на докумнта). Отговор (1)
        o '4' - Четене на следващ ред текст/структурирани данни. Отговор (4)
        o '5' - Четене на данни от фискални бонове по прил.34. Отговор (5)
        • DocNum - Номер на документ за четене.
        • RecType - Вид на документа.
        o '0' - Всички.
        o '1' - Фискални бонове (всички фискални бонове, продажба, сторно, фактура, сторно фактура-кредитно
        известие).
        o '2' - Дневен Z отчет.
        o '3' - Сл. въведени/изведени.
        o '4' - Дневен X отчет.
        o '5' - Служебни бонове (всички без изброените в опция 1).
        o '6' - Фактура.
        o '7' - Сторно фискални бонове.
        o '8' - Сторно фактура (кр. известие).
        Опционални параметри:
        • ToNumber - Номер на бон до който да се търси, ако исканият бон е от вид различен от указания.
        Отговор 1: <ErrCode>,<DocNumber>,<Date>,<Type>,<Znumber>
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • DocNumber - Номер на документа.
        • Date - Дата и час на документа.
        • Type - Тип на документа.
        o '1' - Фискален бон продажба.
        o '2' - Дневен Z отчет.
        o '3' - Сл. въведени.
        o '4' - Сл. изведени.
        o '5' - Дневен X отчет.
        o '6' - Служебен бон.
        o '7' - Фактура.
        o '8' - Сторно фискален бон.
        o '9' - Сторно фактура (кр. известие).
        • Znumber - Номер на Z отчета.
        Отговор 2: <ErrCode>[,<Text>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Text - Един текстов ред от документа (42 ascii символа).
        Отговор 3: <ErrCode>[,<Data>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Data - Един ред двоични данни от документа, кодирани в base64 (128 символа).
        Отговор 4: <ErrCode>[,<Type>,<Data>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Type - Тип на данните в отговора (полето Data).
        o '1' - Текстов ред (Data - 42 ascii символа).
        o '2' - Ред данни (Data - base64 128 символа).
        • Data - Един ред двоични данни от документа, кодирани в base64 (128 символа), или един текстов ред от
        документа (42 ascii символа), в зависимост от полето Type.
        Отговор 5: в зависимост от типа изпълнявана операция в бона
        - Отваряне на фискален бон : <ErrCode>[,3,<bonType>,<nBon>,<nSale>,<decPoint>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • bonType - Тип на фискалния бон.
        o '1' - Фискален бон продажба.
        o '7' - Фактура.
        o '8' - Сторно фискален бон.
        o '9' - Сторно фактура (кр. известие).
        • nSale - УНП на фискалния бон. Полето съдържа 21 символа за УНП ( XXXXXXXX-XXXX-XXXXXXX), ако
        бонът е издаден от софтуер за управление на продажби. Иначе полето е празно.
        • decPoint - Позиция на десетичната точка за суми и цени в бона. Число със стойност 2 или 0 ( 0 за случай на
        работа с цели числа).
        - Описание на продажба в бона : <ErrCode>[,4,<Price>,<Qty>,<Suma>,<Vat>,<Name>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Price - Цена (цяло число 0..99999999), позицияta е в зависимост от използваната десетична позиция в бона.
        • Qty - Количество (цяло число 0..999999999), десетичната позиция за количество е винаги 3!
        • Suma - Сума на продажбата (0..99999999999), десетичната позиция е в зависимост от използваната десетична
        позиция в бона.
        • Vat - Данъчна група на продажбата число (1..8).
        • Name - Име на продажбата (стоката/услугата).
        - Описание на отстъпка/надбавка: <ErrCode>[,5,<Suma>,<Name>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Suma - Сума на отстъпката/надбавката (цяло число със знак -99999999 .. 999999999), десетичната позиция е в
        зависимост от използваната десетична позиция в бона.
        • Name - Име на отстъпката/надбавката.
        - Обща сума на бона (тотал на бона): <ErrCode>[,6,<Suma>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Suma - - Обща сума на бона (число от 0..99999999999), десетичната позиция е в зависимост от използваната
        десетична позиция в бона.
        - Описание на данни за сторно бон: <ErrCode>[,7,<strDocNumber>,<strType>,<strToInvoice>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • strDocNumber - Номер на документ, по който се прави сторното (число от 1..9999999).
        • strType - Тип на сторното:
        o '0' - Операторска грешка.
        o '1' - Връщане/рекламация.
        o '2' - Намаление на данъчната основа.
        • strToInvoice - Номер на фактура по която се прави сторното (число от 1 .. 9999999999), само за случай на сторно
        по фактура. В противен случай полето е празно.
        - Описание на данни за фактура: <ErrCode>[,8,<invNumber>,<EIK>,<EIKType>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • invNumber - Номер на фактура (1..9999999999).
        • EIK - ЕИК на получателя (стринг до 14 символа)
        • EIKType - Тип на ЕИК.
        o '0' - Булстат
        o '1' - ЕГН
        o '2' - ЛНЧ
        o '3' - Сл. номер        
        */
        //TODO: Check on fiscalized device
        String params = "3,"+docNum+",0";
        String res = cmdCustom(125, params);
        if (res.length() > 0) {
            // Отговор 1: <ErrCode>,<DocNumber>,<Date>,<Type>,<Znumber>
            String[] resLines = res.split(",");
            if (resLines[0].equals("P")) {
                //
            } else
                err("Грешка при печат на дублиращ ФБ от КЛЕН!");
        } else {
            err("Неочакван отговор при печат на дублиращ ФБ от КЛЕН!");
        }
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        /*
        5Eh (94) ПЪЛЕН ОТЧЕТ НА ФП ПО ДАТА
        Област за данни: <Start>[,<End>]
        Отговор: Няма данни
        Start Началната дата на фискален запис. 6 байта (DDMMYY).
        End Крайна дата на фискален запис. 6 байта (DDMMYY).
        Тази команда отпечатва пълен отчет на ФП за периода между две дати.
        Ако вторият параметър липсва, командата генерира месечен или годишен отчет. Синтаксисът в този случай е:
        Start Месец – 4 байта (MMYY) за месечен отчет.
        Start Година – 2 байта (YY) за годишен отчет.        
        
        4Fh (79) СЪКРАТЕН ОТЧЕТ НА ФП ПО ДАТА
        Област за данни: <Start>[,<End>]
        Отговор: Няма данни
        Start Начална дата - 6 байта (DDMMYY)
        End Крайна дата - 6 байта (DDMMYY)
        Командата води до изчисляване и отпечатване на съкратен отчет на ФП.
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
        Отговор: ErrCode[,<CanVd>,<TaxA>,<TaxB>,<TaxC>,<TaxD>,<TaxE>,<TaxF>,
        <TaxG>,<TaxH>,<Inv>,<InvNum>,<Type>]
        Отговор на командата:
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно. Следват данни.
        o 'F' - Грешка при четене на последния запис.
        • CanVd - Възможно ли е връщане (продажба с отрицателен знак).
        • TaxA: Натрупана сума по данъчна група А
        • TaxB: Натрупана сума по данъчна група Б
        • TaxC: Натрупана сума по данъчна група В
        • TaxD: Натрупана сума по данъчна група Г
        • TaxE: Натрупана сума по данъчна група Д
        • TaxF: Натрупана сума по данъчна група Е
        • TaxG: Натрупана сума по данъчна група Ж
        • TaxH: Натрупана сума по данъчна група З
        • Inv - Отворена ли е фактура?
        o '0' - Не.
        o '1' - Да.
        • InvNum - Номер на следващата фактура (число 10 позиции).
        • Type - Вид на бона.
        o '0' - Фискален.
        o '1' - Сторно операторска грешка.
        o '2' - Сторно връщане/рекламация.
        o '3' - Сторно намаление на дан. основа
        Дава информация за натрупаните суми по данъчни групи и дали е възможно връщане на регистрирани стоки.     
        
        4Ch (76) СТАТУС НА ФИСКАЛНАТА ТРАНЗАКЦИЯ
        Област за данни: [Option]
        Отговор: Open,Items,Amount[,Tender]
        Option = ‘T’. Ако този параметър е указан, командата ще върне информацията относно текущото състояние на
        дължимата до момента сметка от клиента.
        Open Един байт, който е ‘1’ ако е отворен фискален или служебен бон (какъв точно може да се разбере по статус
        битовете), и ‘0’ ако няма отворен бон.
        Items Броят на продажбите регистрирани на текущия или на последния фискален бон. 4 байта.
        Amount Сумата от последния фискален бон – 9 байта със знак.
        Tender Сумата платена на поредния или последен бон. 9 байта със знак.
        Тази команда дава възможност на приложението в PC да установи статуса, а ако е нужно и да възстанови и завърши фискална
        операция, прекъсната аварийно и ненавременно, например при изключване на ел. захранване.
        
        */
        String res = cmdCustom(103, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            if (!resLines[0].equals("F")) {
                // Отговор: <CanVd>,<TaxA>,<TaxB>,<TaxC>,<TaxD>,<TaxE>,<TaxF>,<TaxG>,<TaxH>,<Inv>,<InvNum>,<Type>
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
                response.put("Type", (resLines.length > 11)?resLines[11]:"");

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
                err("Грешка при Четене на информация за текущия ФБ!");
//                throw new FDException(mErrors.toString());
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
        7Ch (124) ЧЕТЕНЕ НА КЛЕН
        Област за данни: <Type>[,<InpData>]
        Отговор: в зависимост от параметрите
        Параметри:
        • Type - Вид на исканата информация.
        o 'D' - Търсене в КЛЕН по дата и час. Отговор (1).
        o 'Z' - Търсене в КЛЕН по номер на Z отчет. Отговор (1).
        o 'I' - Информация за КЛЕН. Отговор (2).
        • InpData - Данни за командата в зависимост от полето Type:
        o Type D: <DocType>,<BegDate>,<EndDate>
        o Type Z: <DocType>,<BegZ>,<EndZ>
        o Type I: Няма входни данни.
        Описание на полетата в InpData:
        o DocType - Вид натърсените документи.
         '0' - Всички.
         '1' - Фискални бонове (всички фискални бонове, продажба, сторно, фактура, сторно фактура (кр.
        известие), дневен финансов отчет (Z отчет)).
         '2' - Дневен Z отчет.
         '3' - Сл. въведени/изведени.
         '4' - Дневен X отчет.
         '5' - Служебни бонове (всички без изброените в опция 1).
         '6' - Фактура.
         '7' - Сторно фискални бонове.
         '8' - Сторно фактура (кр. известие).
        o BegDate - Начална дата (дата и час) за търсене на докумнети. 6/12 цифри (формат DDMMYY или
        DDMMYYhhmmss).
        o BegDate - Крайна дата (дата и час) за търсене на докумнети. 6/12 цифри (формат DDMMYY или
        DDMMYYhhmmss).
        o BegZ - Номер на Z отчет за начало на обхвата за търсене. Число (1..1825).
        o EndZ - Номер на Z отчет за край на обхвата за търсене. Число (1..1825).
        Отговор (1): <ErrCode>,<FirstDoc>,<LastDoc>
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • FirstDoc - Номер на първия намерен документ (число 0 - 9999999).
        • LastDoc - Номер на последния намерен документ( число 0 - 9999999);
        Отговор (2): <ErrCode>[,<isValid>,<isCurrent>,<IDnumber>,<Number>,<DateTime>,<Serial>,<fromZ>,<toZ>,
        <fromDoc>,<toDoc>,<Size>,<Used>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • isValid - Флаг SD картата съдържа валиден КЛЕН.
        o '1' - На картата има валиден КЛЕН.
        o '0' - На картата не се разпознава валиден КЛЕН.
        • isCurrent - Флаг КЛЕН на SD картата е текущият КЛЕН на ФУ.
        o '1' - КЛЕН е текущ за устройството.
        o '0' - КЛЕН е "Стар" или от друго устройство.
        • IDnumber - Идентификационен номер на устройството, от който е КЛЕН-а. Стринг 8 символа (формат
        DTXXXXXX, 2 букви + 6 цифри).
        • Number - Пореден номер на КЛЕН за устройството. Число (0..100).
        • DateTime - Дата и час на активация на КЛЕН. Стринг 19 символа формат "DD.MM.YYYY hh:mm:ss".
        • Serial - Сериен номер на SD картата. 8 символа asciihex (пример AC536E00).
        • fromZ - Първи Z отчет записан на този КЛЕН. Число (1..1825).
        • toZ - Последен Z отчет записан на този КЛЕН. Число (1..1825).
        • fromDoc - Първи документ записан в КЛЕН.
        • toDoc - Последен документ записан в КЛЕН.
        • Size - Размер на КЛЕН(SD картата) в MBytes. Число до 4000.
        • Used - Използвани байтове в КЛЕН(SD картата) в MBytes. Число дo 4000.        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(124, "I");
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            if (resLines[0].equals("P")) {
                response.put("IsValid", (resLines.length > 1)?resLines[1]:"");
                response.put("IsCurrent", (resLines.length > 2)?resLines[2]:"");
                response.put("IDnumber", (resLines.length > 3)?resLines[3]:"");
                response.put("Number", (resLines.length > 4)?resLines[4]:"");
                response.put("DateTime", (resLines.length > 5)?resLines[5]:"");
                response.put("Serial", (resLines.length > 6)?resLines[6]:"");
                response.put("FromZ", (resLines.length > 7)?resLines[7]:"");
                response.put("ToZ", (resLines.length > 8)?resLines[8]:"");
                response.put("FromDoc", (resLines.length > 9)?resLines[9]:"");
                response.put("ToDoc", (resLines.length > 10)?resLines[10]:"");
                response.put("Size", (resLines.length > 11)?resLines[11]:"");
                response.put("Used", (resLines.length > 12)?resLines[12]:"");
            } else {
                err("Грешка при Четене на информация за КЛЕН!");
                throw new FDException(mErrors.toString());
            }
        } else {
            err("Неочакван отговор при Четене на информация за КЛЕН!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        /*
        77h (119) РАБОТА С КЛЕН
        Област за данни: <Type>[,<InpData>]
        Отговор: Зависи от входните данни
        Type Клас на командата за работа с КЛЕН. Един байт с допустима стойност:
        'N' Четене на следващ текстов ред от КЛЕН.
        'R' Четене на данни от КЛЕН.
        Клас команди 'R': Четене на данни от КЛЕН.
        <Flg><DT1>,<DT2> Връща първия ред от документ с дата и час DT1 и задава край на търсенето при документ с
        дата и час DT2 (включително). Има филтриране на документите, които са избрани, в
        зависимост от аргумента Flg. Следващите редове се теглят с изпращане на команда клас 'N'.
        Формат на входните аргументи:
        Flg Стринг от символи, задаващи тип на документ, който да се избере за четене. Възможни
        символи в стринга:
        'A' Всички видове документи.
        DT1 Начални дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се
        подразбира „000000“, т.е 00:00:00.
        DT2 Крайни дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се
        подразбира „235959“, т.е 23:59:59.
        Връщат един от отговорите, посочени по-долу:
        P,Text Има пореден текстов ред в КЛЕН, съдържанието му е в Text. Прочетеният текст е винаги в кодова
        таблица 1251, независимо дали от ключетата е избран режим DOS-овска кодова таблица. Кодовата
        таблица е важна за правилното пресмятане на SHA-1 контролната сума.
        F Няма повече данни в КЛЕН.
        Начинът на работа е следния:
        Подава се една от предходните команди. Ако отговорът е 'F', то няма такива данни в КЛЕН. В противен случай се
        изпраща команда клас 'N', докато се върне отговор 'F' (няма повече данни). Така може да се изтегли ред по ред желаната
        част от КЛЕН като текст.
        Q<Addr>,<Bytes> Директно четене на данни от КЛЕН в „суров“ вид. Командата връща 2*Bytes символа,
        представящи Bytes байта от КЛЕН, започвайки от адрес Addr. Addr се задава шестнайсетично,
        а Bytes – десетично. Данните се връщат в шестнайсетичен вид.
        Клас команди 'N': Няма допълнителни данни след този символ. Използува се в комбинация с някои от
        командите от клас 'R'. Служи за прочитане на следващ текстови ред от КЛЕН. Възможните отговори са същите както
        при началната команда клас 'R' и са описани по-горе.
        ВНИМАНИЕ! Изпращането на тази команда без предхождаща от клас 'R', задаваща обхвата
        на справката, може да доведе до четенето на безсмислени данни!        
        */
        //TODO: Check on fiscalized device
        LinkedHashMap<String, String> response = new LinkedHashMap();
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        String params = "R,A"+dtf.format(fromDate)+","+dtf.format(toDate);
        String EJ = "";
        String res = cmdCustom(119, params);
        if (mErrors.size() > 0) {
            err("Грешка при четене на КЛЕН!");
            throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && res.substring(0, 1).matches("^P|[*]$")) {
            EJ = EJ + res.substring(2)+"\n";
            res = cmdCustom(119, "N"); // read next row
        }
        response.put("EJ", EJ);
        return response;
    }

     public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        /*
        7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        Област за данни: <Option>[,<DocNum>,<RecType>[,<ToNumber>]]
        Отговор: в зависимост от параметрите
        Задължителни параметри:
        • Option - Вид на исканата информация
        o '0' - Подготовка на документа за четене. Отговор (1)
        o '1' - Четене на следващ текстов ред от документа. Отговор (2)
        o '2' - Четене на следващ ред със структурирана информация от документа. Отговор (3)
        o '3' - Отпечатване на документа в текстов вид на хартия (копие на докумнта). Отговор (1)
        o '4' - Четене на следващ ред текст/структурирани данни. Отговор (4)
        o '5' - Четене на данни от фискални бонове по прил.34. Отговор (5)
        • DocNum - Номер на документ за четене.
        • RecType - Вид на документа.
        o '0' - Всички.
        o '1' - Фискални бонове (всички фискални бонове, продажба, сторно, фактура, сторно фактура-кредитно
        известие).
        o '2' - Дневен Z отчет.
        o '3' - Сл. въведени/изведени.
        o '4' - Дневен X отчет.
        o '5' - Служебни бонове (всички без изброените в опция 1).
        o '6' - Фактура.
        o '7' - Сторно фискални бонове.
        o '8' - Сторно фактура (кр. известие).
        Опционални параметри:
        • ToNumber - Номер на бон до който да се търси, ако исканият бон е от вид различен от указания.
        Отговор 1: <ErrCode>,<DocNumber>,<Date>,<Type>,<Znumber>
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • DocNumber - Номер на документа.
        • Date - Дата и час на документа.
        • Type - Тип на документа.
        o '1' - Фискален бон продажба.
        o '2' - Дневен Z отчет.
        o '3' - Сл. въведени.
        o '4' - Сл. изведени.
        o '5' - Дневен X отчет.
        o '6' - Служебен бон.
        o '7' - Фактура.
        o '8' - Сторно фискален бон.
        o '9' - Сторно фактура (кр. известие).
        • Znumber - Номер на Z отчета.
        Отговор 2: <ErrCode>[,<Text>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Text - Един текстов ред от документа (42 ascii символа).
        Отговор 3: <ErrCode>[,<Data>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Data - Един ред двоични данни от документа, кодирани в base64 (128 символа).
        Отговор 4: <ErrCode>[,<Type>,<Data>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Type - Тип на данните в отговора (полето Data).
        o '1' - Текстов ред (Data - 42 ascii символа).
        o '2' - Ред данни (Data - base64 128 символа).
        • Data - Един ред двоични данни от документа, кодирани в base64 (128 символа), или един текстов ред от
        документа (42 ascii символа), в зависимост от полето Type.
        Отговор 5: в зависимост от типа изпълнявана операция в бона
        - Отваряне на фискален бон : <ErrCode>[,3,<bonType>,<nBon>,<nSale>,<decPoint>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • bonType - Тип на фискалния бон.
        o '1' - Фискален бон продажба.
        o '7' - Фактура.
        o '8' - Сторно фискален бон.
        o '9' - Сторно фактура (кр. известие).
        • nSale - УНП на фискалния бон. Полето съдържа 21 символа за УНП ( XXXXXXXX-XXXX-XXXXXXX), ако
        бонът е издаден от софтуер за управление на продажби. Иначе полето е празно.
        • decPoint - Позиция на десетичната точка за суми и цени в бона. Число със стойност 2 или 0 ( 0 за случай на
        работа с цели числа).
        - Описание на продажба в бона : <ErrCode>[,4,<Price>,<Qty>,<Suma>,<Vat>,<Name>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Price - Цена (цяло число 0..99999999), позицияta е в зависимост от използваната десетична позиция в бона.
        • Qty - Количество (цяло число 0..999999999), десетичната позиция за количество е винаги 3!
        • Suma - Сума на продажбата (0..99999999999), десетичната позиция е в зависимост от използваната десетична
        позиция в бона.
        • Vat - Данъчна група на продажбата число (1..8).
        • Name - Име на продажбата (стоката/услугата).
        - Описание на отстъпка/надбавка: <ErrCode>[,5,<Suma>,<Name>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Suma - Сума на отстъпката/надбавката (цяло число със знак -99999999 .. 999999999), десетичната позиция е в
        зависимост от използваната десетична позиция в бона.
        • Name - Име на отстъпката/надбавката.
        - Обща сума на бона (тотал на бона): <ErrCode>[,6,<Suma>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • Suma - - Обща сума на бона (число от 0..99999999999), десетичната позиция е в зависимост от използваната
        десетична позиция в бона.
        - Описание на данни за сторно бон: <ErrCode>[,7,<strDocNumber>,<strType>,<strToInvoice>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • strDocNumber - Номер на документ, по който се прави сторното (число от 1..9999999).
        • strType - Тип на сторното:
        o '0' - Операторска грешка.
        o '1' - Връщане/рекламация.
        o '2' - Намаление на данъчната основа.
        • strToInvoice - Номер на фактура по която се прави сторното (число от 1 .. 9999999999), само за случай на сторно
        по фактура. В противен случай полето е празно.
        - Описание на данни за фактура: <ErrCode>[,8,<invNumber>,<EIK>,<EIKType>]
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        • invNumber - Номер на фактура (1..9999999999).
        • EIK - ЕИК на получателя (стринг до 14 символа)
        • EIKType - Тип на ЕИК.
        o '0' - Булстат
        o '1' - ЕГН
        o '2' - ЛНЧ
        o '3' - Сл. номер         
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = "0,"+fromDoc+",0"+((toDoc.length() > 0)?","+toDoc:"");
        String EJ = "";
        String res = cmdCustom(125, params);
        if (mErrors.size() > 0) {
            err("Грешка при четене на КЛЕН!");
            throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && res.substring(0, 1).matches("^P|[*]$")) {
            EJ = EJ + res.substring(2)+"\n";
            res = cmdCustom(125, "1"); // read next row
        }
        response.put("EJ", EJ);
        return response;
     }
   
    @Override
    public LinkedHashMap<String, String> cmdReadDocInfo(String docNum) throws IOException {
        /*
        7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        ...
        */
        if (docNum.length() == 0)
            docNum = cmdLastDocNum();
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = "0,"+docNum+",0";
        String EJ = "";
        String res = cmdCustom(125, params);
        if (mErrors.size() > 0) {
           err("Грешка при четене на информация за КЛЕН!"); 
           throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && !res.substring(0, 1).matches("^F[,]?")) {
            res = res.replaceAll("^P[,]", "");
            EJ = EJ + res + "\n";
            res = cmdCustom(125, "5"); // read next row
        }
        response.put("EJ", EJ);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdShortDocInfo(String docNum) throws IOException {
        /*
        7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        ...
        Отговор 1: <ErrCode>,<DocNumber>,<Date>,<Type>,<Znumber>
        • ErrCode - Един байт код на грешка.
        o 'P' - Командата е изпълнена успешно.
        o 'F' - Грешка.
        */
        if (docNum.length() == 0)
            docNum = cmdLastDocNum();
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = "0,"+docNum+",0";
        String res = cmdCustom(125, params);
        // P,6,14-02-19 16:14:37,8,1
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("P")) {
                String[] parts = res.substring(2).split(",");
                response.put("DocNum", parts[0]);
                response.put("DocDate", "");
                if (parts.length > 1) {
                    DateFormat formatGet = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                    DateFormat formatSet = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    try {
                        response.put("DocDate", formatSet.format(formatGet.parse(parts[1])));
                    } catch (Exception e) {}
                }
                String docType = "UNKNOWN";
                if (parts.length > 2) {
                    switch(parts[2]) {
                        case "1" : // Фискален бон продажба.
                            docType = "FB";
                            break;
                        case "2" : // Дневен Z отчет.
                            docType = "DZR";
                            break;
                        case "3" : // Сл. въведени.
                            docType = "CIN"; 
                            break; 
                        case "4" : // Сл. изведени.
                            docType = "COUT"; 
                            break;
                        case "5" : // Дневен X отчет.
                            docType = "DXR";
                            break;
                        case "6" : // Служебен бон.
                            docType = "NFB";
                            break;
                        case "7" : // Фактура.
                            docType = "INV";
                            break;
                        case "8" : // Сторно фискален бон.
                            docType = "RFB";
                            break;
                        case "9" : // Сторно фактура (кр. известие).
                            docType = "RINV";
                            break;
                    }
                }    
                response.put("DocType", docType);
                response.put("ZNum", (parts.length > 3)?parts[3]:"");
            } else {
               err("Грешка при четене на информация за документ от КЛЕН!"); 
            }
        } else {
           err("Грешка при четене на информация за документ от КЛЕН!"); 
//           throw new FDException(mErrors.toString());
        }
        return response;
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
        String params = "0,"+docNum+",0";
        String res = cmdCustom(125, params);
        // P,6,14-02-19 16:14:37,8,1
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("P")) {
                String[] parts = res.substring(2).split(",");
                if (parts.length > 1) {
                    DateFormat formatGet = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                    DateFormat formatSet = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    try {
                        docDate = formatGet.parse(parts[1]);
                    } catch (Exception e) {}
                }
            } else {
               err("Грешка при четене на информация за документ от КЛЕН!"); 
            }
        } else {
           err("Грешка при четене на информация за документ от КЛЕН!"); 
//           throw new FDException(mErrors.toString());
        }
        return docDate;
    }
    
    
}
