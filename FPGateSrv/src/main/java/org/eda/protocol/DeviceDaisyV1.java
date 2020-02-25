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
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * This class supports Daisy fiscal devices
 * Corresponding to specification Daisy-Communication-Protocol-v2.pdf
 * @author Dimitar Angelov
 */
public class DeviceDaisyV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;
    private boolean fiscalCheckRevOpened = false;
    
    private LinkedHashMap<String, Integer> consts = new LinkedHashMap();

    
    public DeviceDaisyV1(AbstractProtocol p) {
        super(p);
    }

    public DeviceDaisyV1(String portName, int baudRate, int readTimeout, int writeTimeout) {
        super(portName, baudRate, readTimeout, writeTimeout);
        serialPort = SerialPort.getCommPort(portName);
        if (serialPort.openPort()) {
            serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            // Always have to initialize Serial Port in nonblocking (forever) mode!
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, readTimeout, writeTimeout);
            serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            protocol = new ProtocolV10D(serialPort.getInputStream(), serialPort.getOutputStream(), ProtocolV10.EncodingType.CP_1251);
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

    /**
     * Read device constants/parameters
     */
    protected void readConstants() {
        /*
        128 (80h) ПОЛУЧАВАНЕ НА КОНСТАНТИ 
        Област за данни: Няма данни 
        Отговор: {P1},{ P2},{......},{P25} 
        P1 Хоризонтален размер на графичното лого в пиксели. 
        P2 Вертикален размер на графично лого в пиксели. 
        P3 Брой на видове плащания. 
        P4 Брой данъчни групи. 
        P5 Не се използва за България. 
        P6 Не се използва за България. 
        P7 Символ за първа данъчна група.
        P8 Размерност на вътрешна аритметика. 
        P9 Брой символи на ред. 
        P10 Брой символи на коментарен ред. 
        Р11 Дължина (Брой символи) на имена (оператори, артикули, департаменти). 
        Р12 Дължина (Брой символи) на инд. номер на ФУ. 
        Р13 Дължина (Брой символи) на номер на ФП. 
        Р14 Дължина (Брой символи) на данъчен номер - за България = 0. 
        Р15 Дължина (Брой символи) на ЕИК по БУЛСТАТ. 
        Р16 Брой департаменти. 
        Р17 Брой артикули. 
        Р18 Флаг за поле стокова наличност в описанието на PLU (0,1). 
        Р19 Флаг за поле баркод в описанието на PLU (0,1). 
        Р20 Брой на стокови групи. 
        Р21 Брой на оператори. 
        Р22 Дължина (Брой символи) на имена на плащания 
        Р23 Не се използва (за тази версия на фърмуера 0). 
        Р24 Не се използва (за тази версия на фърмуера 0). 
        Р25 Не се използва (за тази версия на фърмуера 0).        
        */
        consts.clear();
        try {
            String res = cmdCustom(128, "");
            if (res.length() > 0) {
                String[] params = res.split(",");
                for (int i = 1; i <= min(25,params.length); i++) {
                    int pval = 0;
                    try {
                        pval = Integer.parseInt(params[i-1]);
                    } catch (Exception e) {
                    }
                    consts.put("P"+Integer.toString(i),pval);
                }            
            }    
        } catch (IOException ex) {
            err("Грешка при четене на константите!");
        }
        if (consts.containsKey("P10") && (consts.get("P10") > 0))
            consts.put("COMMENT_LEN", consts.get("P10"));
        else
            consts.put("COMMENT_LEN", 28);
        if (consts.containsKey("P3"))
            consts.put("PAY_MAX_CNT", consts.get("P3"));
        if (consts.containsKey("P16"))
            consts.put("DEPT_MAX_CNT", consts.get("P16"));
    }

    @Override
    public int getLineWidthNonFiscalText() {
        return consts.get("COMMENT_LEN");
    }

    @Override
    public int getLineWidthFiscalText() {
        return consts.get("COMMENT_LEN");
    }
    
    
    @Override
    protected void readDeviceInfo() throws IOException {
        /*
        90 (5Ah) ДИАГНОСТИЧНА ИНФОРМАЦИЯ 
        Област за данни: {Calculate} 
        Отговор: {FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        Calculate Ако е “1” се изчислява контролната сума на EPROM (1 байт). 
        FirmwareRev Версия на програмното осигуряване (4 символа). Space Интервал 20h (1 байт). 
        FirmwareDate Датата на програмното осигуряване DDMМYY (6 байта). 
        Space Интервал 20h (1 байт). 
        FirmwareTime Час на програмното осигуряване HHMM (4 байта). 
        ChekSum Контролна сума на EPROM (4 байта низ в шестнайсетичен вид). 
        Sw Състояние на ключетата на ФУ(4 цифри). 
        Country Номер на страната (1 байт), за България = 6 
        SerNum Инд. номер на ФУ (#MACHNO_LEN# символа). 
        FМ Номер на фискалния модул (#FMNO_LEN# символа).        
        */
        
        // Request diagnostic info
        String res = cmdCustom(90, "");
        mDeviceInfo = res;
        // By documentation is this:
        // {FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        // Real 
        // {Model}{Space}{FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        // CompactM ONL01-4.01BG 29-10-2018 11:34,FFFF,00,6,DY408097,36543869
        String[] commaParts = res.split(",");
        if (commaParts.length > 0) {
            
            String[] spaceParts = commaParts[0].split("\\s");
            mModelName = spaceParts[0];
            if (spaceParts.length > 1)
                mFWRev = spaceParts[1];
            if (spaceParts.length > 2)
                mFWRevDT = spaceParts[2];
            if (spaceParts.length > 3)
                mFWRevDT = mFWRevDT + " " + spaceParts[3];
        }
        if (commaParts.length > 2) 
            mSwitches = commaParts[2];
        if (commaParts.length > 4) 
            mSerialNum = commaParts[4];
        if (commaParts.length > 5) 
            mFMNum = commaParts[5];
        readConstants();
    }

    @Override
    protected void initPrinterStatusMap() {
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();
        
        statusBytesDef.put("S0", new String[] // Общо предназначение
        {
             "Синтактична грешка"              // 0 Получените данни имат синктактична грешка.
            ,"Невалиден код на команда"        // 1 Кодът на получената команда е невалиден.
            ,"Неустановени дата/час"           // 2 Не е сверен часовника.
            ,"Не е свързан клиентски дисплей"  // 3 Не е свързан клиентски дисплей.
            ,"Грешка на печатащото устройство" // 4 Грешка на печатащото устройство
            ,"Обща грешка"                     // 5 OR на всички грешки с * от байтове 0, 1, 4 – обща грешка
            ,""                                // 6 Не се използва
            ,""                                // 7 Резервиран
        }
        );
        warningStatusBits.put("S0", new byte[] {
            2
        });
        errorStatusBits.put("S0", new byte[] {
            0,1,4,5
        });
        statusBytesDef.put("S1", new String[] // Общо предназначение
        {
             "Препълване"                                 // 0 Препълване на някои полета в сумите
            ,"Непозволена команда в този режим"           // 1 Неразрешена команда в текущия режим
            ,"Нулиран RAM"                                // 2 Нулиран RAM
            ,""                                           // 3 Не се използва
            ,""                                           // 4 Не се използва
            ,"Грешка в cutter"                            // 5 Грешка в cutter
            ,"Грешна парола"                              // 6 Грешна парола
            ,""                                           // 7 Резервиран
        }
        );
        warningStatusBits.put("S1", new byte[] {
            0,2
        });
        errorStatusBits.put("S1", new byte[] {
            1,5,6
        });
        statusBytesDef.put("S2", new String[] // Общо предназначение
        {
             "Край на хартията"                                            // 0 Няма хартия
            ,"Малко хартия"                                                // 1 Малко хартия
            ,"Край на КЛЕН"                                                // 2 Край на КЛЕН
            ,"Отворен Фискален Бон"                                        // 3 Отворен е фискален бон
            ,"Близък край на КЛЕН"                                         // 4 Близък край на КЛЕН
            ,"Отворен е Служебен Бон"                                      // 5 Отворен е служебен бон.
            ,"Разрешен печат на документ"                                  // 6 Разрешен печат на документ
            ,""                                                            // 7 Резервиран
        }
        );
        warningStatusBits.put("S2", new byte[] {
            1,4
        });
        errorStatusBits.put("S2", new byte[] {
            0,2
        });
        statusBytesDef.put("S3", new String[] // Номер грешка на ФУ
        {
             "ERR.0" // 0 - код на грешка бит 0 
            ,"ERR.1" // 1 - код на грешка бит 1 
            ,"ERR.1" // 2 - код на грешка бит 2 
            ,"ERR.1" // 3 - код на грешка бит 3 
            ,"ERR.1" // 4 - код на грешка бит 4 
            ,"ERR.1" // 5 - код на грешка бит 5 
            ,"ERR.1" // 6 - код на грешка бит 6 
            ,""      // 7 Резервиран
        }
        );
        warningStatusBits.put("S3", new byte[] {
        });
        errorStatusBits.put("S3", new byte[] {
        });
        statusBytesDef.put("S4", new String[] // За ФП
        {
             "Грешка при запис във фискалната памет"                   // 0 Грешка при запис във фискалната памет.
            ,"Проблем в данъчния терминал"                             // 1 Проблем в данъчния терминал
            ,"Грешен запис във ФП"                                     // 2 Грешен запис във ФП
            ,"Място за по–малко от 50 записа във ФП"                   // 3 Има място за по-малко от 50 записа във ФП.
            ,"Фискалната памет е пълна"                                // 4 ФП е пълна.
            ,"ФП Обща Грешка"                                          // 5 OR на всички грешки с * от байтове 4 и 5– обща грешка
            ,""                                                        // 6 Не се използува
            ,""                                                        // 7 Резервиран
        }
        );
        warningStatusBits.put("S4", new byte[] {
            1, 3
        });
        errorStatusBits.put("S4", new byte[] {
            0, 2, 4, 5
        });
        statusBytesDef.put("S5", new String[] // За ФП
        {
             "Препълнена ФП"                             // 0 Препълнена ФП
            ,""                                          // 1 Не се използва
            ,""                                          // 2 Не се използва
            ,"ФУ е във фискален режим"                   // 3 ФУ е във фискален режим.
            ,"Зададени са поне веднъж данъчните ставки"  // 4 Зададени са поне веднъж данъчните ставки.
            ,"Програмирани са инд.Nо на ФУ и No на ФП"   // 5 Програмирани са инд. номер на ФУ и номер на ФП
            ,"Готова ФП"                                 // 6 Готова ФП
            ,""                                          // 7 Резервиран
        }
        );
        warningStatusBits.put("S5", new byte[] {
        });
        errorStatusBits.put("S5", new byte[] {
            0
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
        48 (30h) НАЧАЛО НА ФИСКАЛЕН БОН 
        Област за данни: {ClerkNum},{Password},{UnicSaleNum}[{Tab}{Invoice}|{Refund}{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}] 
        Отговор: Allreceipt, FiscReceipt 
        ClerkNum Номер на оператор – 1 до #OPER_MAX_CNT#. 
        Password Парола на оператор – до 6 цифри 
        UnicSaleNum Задължителен параметър, указващ Уникален номер на продажбата 
          Формат: “Инд. номер на ФУ” (2 главни латински букви+6 цифри) “-“ “Код на оператора”четири главни латински букви или цифри “-“ “Пореден номер на продажбата” (минимум седем цифри) пример: DY000600-OP01-0000001). 
        Invoice Указва издаване на разширена клиентска бележка (фактура) = “I”. 
        Refund Указва издаване на сторно фискален бон = “R” 
        Reason Причина за издаването на сторно фискален бон 0 = ВРЪЩАНЕ / РЕКЛАМАЦИЯ 1 = ОПЕРАТОРСКА ГРЕШКА 2 = НАМАЛЯВАНЕ НА ДАН. ОСНОВА 
        DocLink Номер на фискалния бон, по повод на който се издава сторно 
        DocLinkDT Дата и час на издаване на фискалния бон, по повод на който се издава сторно, във формат {DD–MM–YY}{space}{HH:mm[:SS]} 
        Tab Един байт със стойност 09h (разделител). 
        FiskMem Номер на ФП на устройството, от което е издаден фискалния бон 
        Allreceipt Брой на всички издадени фискални и нефискални бонове от последното приключване на деня до момента (4 байта). 
        FiscReceipt Брой на всички фискални бонове от последното приключване на деня до момента (4 байта). 
        ФУ извършва следните действия:
        • Отпечатва HEADER и БУЛСТАТ;
        • Отпечатва номер и име на оператор;
        • Отпечатва номер на фактура и текста „ОРИГИНАЛ”, ако {Invoice}= “I”
        Командата не се изпълнява от ФУ,, ако:
        • Има отворен нефискален или фискален бон;
        • ФП е препълнена.
        • Препълнена ЕКЛ, ако ФУ работи с ЕКЛ
        • Препълнен или отсъстващ или некоректен КЛЕН
        • Няма номер или парола на оператор;
        • Грешна парола;
        • Установен е проблем в комуникацията с данъчния терминал
        • При опит за отваряне на фактура е изчерпан или незададен диапазонът на брояча на фактурите
        • Несверен часовник.
        • Отсъстващ или некоректно зададен УНП
        Внимание: Ако ФУ не е въведено в експлоатация, можете да отворите фискален бон, да извършите продажби и плащане, да го затворите, но в края на бона се изписва текста “НЕФИСКАЛЕН БОН“.
        */
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();

        fiscalCheckRevOpened = false;
        
        String params;
//        if (UNS.length() == 0) 
//            throw new FDException("Липсва УНП!");

        params = opCode+","+opPasswd+","+UNS+(invoice?"\tI":"");
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
        57 (39h) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА 
        Област за данни: IdentNo[<Tab>RegNo[<Tab>Seller[<Tab>Receiver[<Tab>Client[<Tab>Address]]]] 
        Отговор: Няма данни 
        IdentNo Идентификационен номер на клиента 
        Tab Табулатор(разделител) със стойност 09h 
        RegNo Незадължителен параметър, ЗДДС N# на клиента 
        Seller Незадължителен параметър, име на продавача
        Receiver Незадължителен параметър, име на получателя 
        Client Незадължителен параметър, име на купувача 
        Address Незадължителен параметър, адрес на клиента. 
        Допустимо е задаване на повече от един ред за печат. Отделните редове са разделени помежду си с табулатор(09h) 
        Внимание: Командата е допустима (и задължителна), само ако е отворен разширен фискален бон(фактура), и то след извършване на цялостно плащане. 
        В случай на отворен разширен фискален бон(фактура), дори и след извършване на плащането, ФУ отказва да изпълни команда 56(38h), 
        преди да е изпълнило тази команда. 
        Забележка: Подадените IdentNo, RegNo се "отрязва" отдясно, ако е по-дълъг от #STATNO_LEN#. 
        Подаденитe Seller,Receiver,Client,Address се "отрязват" отдясно, ако са по-дълги от #CHARS_PER_LINE#.        
        */
        if (UICType < 0 || UICType > 3)
            throw new FDException("Ivalid UIC type!");
        String params;
        params = UIC+"\t"+VATNum+"\t"+seller+"\t"+recipient+"\t"+buyer+"\t"+address;
        String res = cmdCustom(57, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseFiscalCheck() throws IOException {
        /*
        56 (38h) КРАЙ НА ФИСКАЛЕН БОН 
        Област за данни: Няма данни 
        Отговор: Allreceipt, FiscReceipt 
        Allreceipt Брой на всички издадени бонове от последното приключване на деня до момента. 
        FiscReceipt Брой на всички издадени фискални бонове от последното приключване на деня до момента. 
        Командата не се изпълнява, ако:
        • Не е отворен фискален бон;
        • Команда “Обща сума” (53) не е изпълнена;
        • Платената сума по команда “Обща сума” (53) е по–малка от общата сума на фискалния бон.
        • Ако е отворен разширен фискален бон(фактура) и не е изпълнена успешно команда 57(39h).
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
        130 (82h) АНУЛИРАНЕ НА БОН 
        Област за данни: Няма данни 
        Отговор: Allreceipt, FiscReceipt 
        Allreceipt Брой на всички издадени бонове от последното приключване на деня до момента. 
        FiscReceipt Брой на всички издадени фискални бонове от последното приключване на деня до момента. 
        При получаване на тази команда ФУ : 
        1. Извършва корекция на всички продажби във фискалния бон; 
        2. Изпълнява операция плащане в брой за 0.00 лв; 
        3. Затваря фискалния бон.        
        */
        cmdCustom(130, "");
    }

    @Override
    public void cmdPrintFiscalText(String text) throws IOException {
        /*
        54 (36h) ПЕЧАТ НА ФИСКАЛЕН ТЕКСТ 
        Област за данни: Text 
        Отговор: Няма данни 
        Text Текст за печат 
        Командата няма да се изпълни, ако не е отворен фискален бон 
        Забележка 1: Подаденият текст се "отрязва" отдясно, ако е по-дълъг от #COMMENT_LEN# 
        Забележка 2: Полето Text се интерпретира като коментарен ред и се отпечатва оградено със символа “#”        
        */        
        int maxLen = consts.get("COMMENT_LEN");
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
            text = text.substring(0, maxLen);
        }
        cmdCustom(54, text);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        38 (26h) НАЧАЛО НА НЕФИСКАЛЕН БОН 
        Област за данни: Няма данни 
        Отговор: Allreceipt 
        Allreceipt Броят на всички издадени бонове (нефискални и фискални) от последното приключване на деня до момента (6 байта). 
        ФУ извършва следните действия:
        • Отпечатва се HEADER;
        Командата не се изпълнява от ФУ ако:
        • Има отворен нефискален бон;
        • Има отворен фискален бон;
        • Несверен часовник.
        • Препълнен или отсъстващ или некоректен КЛЕН
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
        42 (2Ah) ПЕЧАТ НА НЕФИСКАЛЕН ТЕКСТ 
        Област за данни: Text 
        Отговор: Няма данни 
        Text Текст Командата не се изпълнява, ако не е отворен нефискален бон. 
        Забележка 1: Подаденият текст се "отрязва" отдясно, ако е по-дълъг от #COMMENT_LEN# 
        Забележка 2: Полето Text се интерпретира като коментарен ред и се отпечатва оградено отляво и отдясно със символа “#”
        */        
        int maxLen = consts.get("COMMENT_LEN");
        String params = "";
        if (text.length() > maxLen) {
            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
            text = text.substring(0, maxLen);
        }
        if (font > 0)
            warn("Не се поддържат шрифтове за печат на нефискален текст!");
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
        39 (27h) КРАЙ НА НЕФИСКАЛЕН БОН 
        Област за данни: Няма данни 
        Отговор: Allreceipt
        Allreceipt Брой на всички издадени бонове (фискални и нефискални) от последното приключване на деня до момента (6 байта). 
        ФУ извършва следните действия:
        • Разпечатва се FOOTER;
        • Разпечатва се поредния номер, дата и час на издаване на документа;
        • Ако ФУ е въведено в експлоатация, се отпечатва текста “СЛУЖЕБЕН БОН“, ако не -“НЕФИСКАЛЕН БОН”.
        Командата не се изпълнява от ФУ, ако:
        • Не е отворен нефискален бон;
        • Отворен е фискален бон.
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
        44 (2Ch) ПРИДВИЖВАНЕ НА ХАРТИЯТА 
        Област за данни: [Lines] 
        Отговор: Няма данни 
        Lines Броят на редовете, с които ще се придвижва хартията.
        */        
        cmdCustom(44, Integer.toString(Integer.max(1, Integer.min(lineCount, 99))));
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        49 (31h) ПРОДАЖБА 
        Област за данни: [{Text1}][{CR}{Text2}]{Tab}{TaxGr}{[Sign]Price} [*{QTY}][,Percent][$Netto] 
        Отговор: Няма данни 
        Text 1 Текст, описващ продажбата 
        CR Един байт със стойност 0Аh (разделител). 
        Text 2 Допълнителен текст, пописващ продажбата. 
        Tab Един байт със стойност 09h (разделител). 
        TaxGr Това е 1 символ, който показва данъчната група (кирилица главни букви А, Б, В, Г, Д, Е, Ж, З). 
        Sign Един байт със стойност ‘+’ или ‘–‘. 
          Ако Sign=’–’, тогава се изнорират полетата Percent и Netto 
          и се прави корекция на последната продажба в бележката със същата цена, количество и дан. група. 
        Price Единична цена : до 8 значещи цифри. 
        QTY Незадължителен параметър, до 8 значещи цифри (не повече от 3 след десетичната точка), който указва количеството на продажбата. По подразбиране е 1.000. 
        Percent Незадължителен параметър, указващ процентната отстъпка/надбавка (в зависимост от знака) върху текущата продажба. 
          Допустими стойности са от – 99.99 % до 99.99 %. Приемат се до 2 цифри след десетичната точка. 
        Netto Незадължителен параметър, указващ стойностната отстъпка/надбавка (в зависимост от знака) върху текущата продажба. 
        ФУ отпечатва името на продажбата с цената и кода на данъчната група (и количество ако е зададено). 
        При наличието на отстъпка/надбавка, тя се отпечатва на отделен ред. 
        Командата не се изпълнява, ако:
        • Не е отворен фискален бон;
        • Достигнат е максималния брой продажби за един бон;
        • Започнало е плащане в текущия фискален бон;
        • Препълнен или отсъстващ или некоректен КЛЕН
        • Едновременно са зададени полетата Percent и Netto
        • При изпълнение на операцията би се получило препълване по някой от отчетите;
        • Указаната данъчна група е забранена за продажба
        Забележка 1: Подадените текстове се "отрязват" отдясно, ако са по-дълги от #COMMENT_LEN# 
        Забележка 2: Ако едновременно са подадени полетата Text1 и Text2, полето Text1 се интерпретира като коментарен ред и се отпечатва оградено със символа “#”        
        */        
        int maxLen = consts.get("COMMENT_LEN");
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
                    params += "$"+String.format(Locale.ROOT, "%.2f", dd);
                }
            }
        }
        cmdCustom(49, params);
    }

    @Override
    public void cmdSellDept(String sellText, String dept, double price, double quantity, String unit, String discount) throws IOException {
        /*
        138 (8Ah) ПРОДАЖБА ПО ДЕПАРТАМЕНТ 
        Област за данни: {[Sign]Dept}{@Price}[*{QTY}][,Percent] [$Netto] 
        Отговор: Няма данни 
        Sign Един байт със стойност ‘+’ или ‘–‘.
           Ако Sign=’–’, тогава се изнорират полетата Percent и Netto 
           и се прави корекция на последната продажба в бележката със същата единична цена, количество и номер на департамент. 
        Dept Номер на департамент (до 2 цифри). 
        QTY виж описание на команда 49(31h). 
        Percent виж описание на команда 49(31h). 
        Price виж описание на команда 49(31h). 
        Netto виж описание на команда 49(31h). 
        ФУ отпечатва името, цената, количеството и данъчната група на департамента; 
        При наличието на отстъпка или надбавка, тя се отпечатва на отделен ред. 
        Командата не се изпълнява, ако:
        • виж описание на команда 49(31h).
        */        
        String params = "";
        if (sellText.length() > 0) {
            warn("Продажба по департамент не отпечатва текст и за това ще се отпечата като фискален текст!");
            String[] stLines = sellText.split("\n");
            cmdPrintFiscalText(stLines[0]);
            if (stLines.length > 1)
                cmdPrintFiscalText(stLines[1]);
        }    
        
        if (dept.length() > 2)
            dept = dept.substring(0,2);
        params = dept + "@" + String.format(Locale.ROOT, "%.2f", price);
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
        cmdCustom(138, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdSubTotal(boolean toPrint, boolean toDisplay, String discount) throws IOException {
       /*
        51 (33h) МЕЖДИННА СУМА 
        Област за данни: {Print}{Display}[,Percent] 
        Отговор: SubTotal,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8 
        Print Стойност 0 (не печата) или 1 (печата) новополучената междинна сума. 
        Display Стойност 0 или 1. 
        Указва на ФУ дали да покаже получената междинна сума на външния дисплей. 
        Percent Незадължителен параметър, показващ процентната отстъпка/надбавка (зависи от знака) върху междинната сума. 
        SubTotal Сумата до момента за текущия фискален бон – 10 символа. 
        Tax1 Сумата по данъчна група А – 10 байта. 
        Tax2 Сумата по данъчна група Б – 10 байта. 
        Tax3 Сумата по данъчна група В – 10 байта. 
        Tax4 Сумата по данъчна група Г – 10 байта. 
        Tax5 Сумата по данъчна група Д – 10 байта. 
        Tax6 Сумата по данъчна група Е – 10 байта. 
        Tax7 Сумата по данъчна група Ж – 10 байта. 
        Tax8 Сумата по данъчна група З – 10 байта.        
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
                err("Не се поддържат остъпки по абсолютна стойност!");
//                if (dd != 0) {
//                    params += ";"+String.format(Locale.ROOT, "%.2f", dd);
//                }
            }
        }
        String res = cmdCustom(51, params);
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            response.put("SubTotal", reformatCurrency(resLines[0], 100));
            response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 1));
            response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 1));
            response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 1));
            response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 1));
            response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 1));
            response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 1));
            response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 1));
            response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 1));
        } else {
            err("Грешка при междинна сума във фискален бон!");
//            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        /*
        53 (35h) ОБЩА СУМА (ТОТАЛ) 
        Област за данни: [{Text1}][{CR}{Text2}]{Tab}[[{Payment}]{Amount}] 
        Отговор: {PaidCode}{Amount} 
        Text1 Първи ред за печат – текст. 
        CR Един байт със стойност 0Ah (разделител). 
        Text2 Втори ред за печат – текст. 
        Tab Един байт със стойност 09h (разделител). 
        Payment Незадължителен параметър, показващ начина на плащане. 
            Ако не сте задали нито Payment, нито Amount, ФУ изпълнява плащане на цялата сума в бележката в брой. 
        Видове плащания: 
            “P” – "В БРОЙ"; 
            “N” – Плащане 1 
            “C” – Плащане 2 
            “D” или "U" – Плащане 3 
            “B” или "E" – Плащане 4
        Amount Сумата, която се плаща (до 8 значещи цифри). 
        PaidCode Един байт – резултатът от изпълнението на командата. 
            “F” – грешка. 
            “I” – ако сумата по някоя данъчна група е отрицателна. 
            “D” – ако от платената сума е по-малка или равна на общата сума на бона. 
            “R” – ако платената сума е по–голяма или равна на общата сумата 
            “E” – отрицателна междинна сума. Amount Сума ресто или остатък от плащането (зависи от PaidCode). 
        Командата не се изпълнява, ако:
        • Не е отворен фискален бон;
        Забележка 1: Подадените текстове се "отрязват" отдясно, ако са по-дълги от #COMMENT_LEN# 
        Забележка 2: Полетата Text1 и Text2 се интерпретират като коментарни редове и се отпечатват оградени със символа “#” 
        Забележка 3: Когато фискалния бон е от тип сторно, е разрешено само плащане „В БРОЙ” 
        Внимание: Когато командата се изпълни успешно, ФУ няма да разреши извършване на повече продажби в рамките на този бон. 
        Кодове на грешка “E” и “I” няма да се получат в България.
        */        
        int maxLen = consts.get("COMMENT_LEN");
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
        String[] validPaymentTypes = {"","P","N","C","D","B"};
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
                String rAmount = reformatCurrency(res.substring(1), 1);
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
                    // Ако платената сума е по-малка или равна на сумата на бона. Остатъкът за доплащане се връща в Amount.
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
                    // Сумата по някоя данъчна група е била отрицателна и затова се е получила грешка. В Amount се връща текущата междинна сума.
                    err("Сумата по някоя данъчна група е билa отрицателнa и затова се е получила грешка. В Amount се връща текущата междинна сума.");
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
            err("Липсва отговор при команда за обща сума и плащане на фискален бон!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        48 (30h) НАЧАЛО НА ФИСКАЛЕН БОН
        Област за данни: {ClerkNum},{Password},{UnicSaleNum}[{Tab}{Invoice |{Refund}{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}
        |{Credit}|{InvLivk},{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}]
        Отговор: Allreceipt, FiscReceipt
        ClerkNum Номер на оператор – 1 до #OPER_MAX_CNT#.
        Password Парола на оператор – до 6 цифри
        UnicSaleNum Задължителен параметър, указващ Уникален номер на продажбата
        Формат: “Инд. номер на ФУ” (2 главни латински букви+6 цифри)
        “-“
        “Код на оператора”четири главни латински букви или цифри
        “-“
        “Пореден номер на продажбата” (минимум седем цифри)
        пример: DY000600-OP01-0000001).
        Invoice Указва издаване на разширена клиентска бележка (фактура) = “I”.
        Refund Указва издаване на сторно фискален бон = “R”
        Reason Причина за издаването на сторно фискален бон
        0 = ВРЪЩАНЕ / РЕКЛАМАЦИЯ
        1 = ОПЕРАТОРСКА ГРЕШКА
        2 = НАМАЛЯВАНЕ НА ДАН. ОСНОВА
        DocLink Номер на фискалния бон, по повод на който се издава сторно
        DocLinkDT Дата и час на издаване на фискалния бон, по повод на който се издава сторно, във формат {DD–MM–YY}{space}{HH:mm[:SS]}
        Tab Един байт със стойност 09h (разделител).
        FiskMem Номер на ФП на устройството, от което е издаден фискалния бон
        Credit Указва издаване на кредитно известие = “C”
        InvLink Номер на данъчната фактура, основание за издаване на кредитното известие
        Allreceipt Брой на всички издадени фискални и нефискални бонове от последното приключване на деня до момента (4 байта).
        FiscReceipt Брой на всички фискални бонове от последното приключване на деня до момента (4 байта).
        ФУ извършва следните действия:
         Отпечатва HEADER и БУЛСТАТ;
         Отпечатва номер и име на оператор;
         Отпечатва номер на фактура и текста „ОРИГИНАЛ”, ако {Invoice}= “I”
        Командата не се изпълнява от ФУ,, ако:
         Има отворен нефискален или фискален бон;
         ФП е препълнена.
         Препълнена ЕКЛ, ако ФУ работи с ЕКЛ
         Препълнен или отсъстващ или некоректен КЛЕН
         Няма номер или парола на оператор;
         Грешна парола;
         Установен е проблем в комуникацията с данъчния терминал
         При опит за отваряне на фактура е изчерпан или незададен диапазонът на брояча на фактурите
         Несверен часовник.
         Отсъстващ или некоректно зададен УНП
        
        Внимание: Ако ФУ не е въведено в експлоатация, можете да отворите фискален бон, да извършите продажби и плащане, да го затворите, но в края на бона се изписва текста “НЕФИСКАЛЕН БОН“        
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
        
//        if (!RevDocNum.matches("^\\d{1,7}$")) 
//            throw new FDException("Невалиден номер на документа, по който е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999!");

//        if (!RevFMNum.matches("^\\d{8,8}$"))
//            throw new FDException("Невалиден номер на фискалната памет '"+RevFMNum+"' трябва да е число записано с 8 знака!");

        if (RevUNS.length() > 0)
            warn("Не се поддържа УНП, по която е сторното");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        
//      {ClerkNum},{Password},{UnicSaleNum}[{Tab}{Invoice |{Refund}{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}|{Credit}|{InvLivk},{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}]
        params = opCode+","+opPasswd+","+UNS+"\t";
        if (invoice) {
            if (RevInvNum.length() == 0)
                throw new FDException("Липсва номер на фактура!");
            int invNum = Integer.parseInt(RevInvNum);
            params = params +"C"+Integer.toString(invNum)+","+RevType+","+RevDocNum+","+dateFormat.format(RevDateTime)+"\t"+RevFMNum;
        } else
            params = params +"R"+RevType+","+RevDocNum+","+dateFormat.format(RevDateTime)+"\t"+RevFMNum;
        
        String res = cmdCustom(48, params);
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
        70 (46h) СЛУЖЕБНО ВЪВЕДЕНИ И ИЗВЕДЕНИ СУМИ 
        Област за данни: {Amount}[,{Text1}[{CR}{Text2}{TAB}]] 
        Отговор: Code,CashSum,ServInput,ServOutput 
        Amount Сума за регистриране (до 9 смвола). В зависимост от знака на числото се изпълнява служебно въвеждане или извеждане. 
        Text 1 Коментарен текст. 
        CR Един байт със стойност 0Аh (разделител). 
        Text 2 Коментарен текст. 
        TAB Един байт със стойност 09h (разделител). 
        Code “P” – заявката е изпълнена. 
        “F” – заявката е отказана. Това става, ако касовата наличност е по–малка от заявената сума или има отворен бон. 
        CashSum Касова наличност. 
        Освен от тази команда сумата се променя и при всяко плащане в брой. 
        ServInput Сумата от всички команди за деня “Служебно въвеждане”. 
        ServOutput Сумата от всички команди за деня “Служебно извеждане”. 
        Забележка 1: Подадените текстове се "отрязват" отдясно, ако са по-дълги от #COMMENT_LEN# 
        Забележка 2: Полетата Text1 и Text2 се интерпретират като коментарни редове и се отпечатват оградени със символа “#” 
        Внимание: Ако не зададете никакви данни или подадете Amount = 0, ФУ няма да отпечата документ, но ще отговори с гореописания отговор. 
        Така PC-то може да следи за касовата наличност, преди да издаде команда за отваряне на сторно фискален бон
        */  
        String params = "";
        if (abs(ioSum) >= 0.01) {
            params = String.format(Locale.ROOT, "%.2f", ioSum);
        } else
            params = "0";

        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(70, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            if (rData[0].equals("P")) {
                // Заявката е изпълнена
                response.put("CashSum", reformatCurrency((rData.length > 1)?rData[1]:"0", 1));
                response.put("CashIn", reformatCurrency((rData.length > 2)?rData[2]:"0", 1));
                response.put("CashOut", reformatCurrency((rData.length > 3)?rData[3]:"0", 1));
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
        102 (66h) ИМЕ НА ОПЕРАТОР 
        Област за данни: {ClerkNum},{Password},{Name} [<Tab> RepClaim[,RefError[,RefOther]]] 
        Отговор: Няма данни 
        ClerkNum Номер на оператора от 1 до #OPER_MAX_CNT#. 
        Password Парола Name 
        Име на оператор. "Отрязва" се отдясно, ако е по-дълго от #NAME_LEN# символа. 
        Tab Един байт със стойност 09h (разделител). 
        RefClaim Разрешение(=1)/Забрана(=0) за издаване на сторно при рекламация 
        RefError Разрешение(=1)/Забрана(=0) за издаване на сторно при оп. грешка 
        RefOther Разрешение(=1)/Забрана(=0) за издаване на сторно при нам. на дан. основа 
        Програмира се името на съответния оператор. 
        Номерът и името му се отпечатват в началото на всеки фискален бон. 
        Внимание: Ако операторът е извършвал операции (издал е поне един документ), 
        името му не може да се смени, докато не се нулират продажбите по този оператор.
        */
        String params = opCode+","+opPasswd+","+name;
        cmdCustom(102, params);
    }
    
    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        64 (40h) ПОСЛЕДЕН ФИСКАЛЕН ЗАПИС 
        Област за данни: [Type] 
        Отговор: Number,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8, StTax1,StTax2, StTax3,StTax4,StTax5,StTax6,StTax7,StTax8,Date 
        Type Незадължителен параметър, указващ типа на връщаните данни: “T”- суми с ДДС (общи) и “N”-суми без ДДС (нетни). По подразбиране е “N” 
        Number Номер на последния фискален запис. 
        Tax1 Обща/нетна сума от продажби по данъчна група с код А 
        Tax2 Обща /нетна сума от продажби по данъчна група с код Б 
        Tax3 Обща /нетна сума от продажби по данъчна група с код В 
        Tax4 Обща /нетна сума от продажби по данъчна група с код Г 
        Tax5 Обща /нетна сума от продажби по данъчна група с код Д 
        Tax6 Обща /нетна сума от продажби по данъчна група с код Е 
        Tax7 Обща /нетна сума от продажби по данъчна група с код Ж 
        Tax8 Обща /нетна сума от продажби по данъчна група с код З 
        StTax1 Обща/нетна сума от сторно операции по данъчна група с код А 
        StTax2 Обща /нетна сума от сторно операции по данъчна група с код Б 
        StTax3 Обща /нетна сума от сторно операции по данъчна група с код В 
        StTax4 Обща /нетна сума от сторно операции по данъчна група с код Г 
        StTax5 Обща /нетна сума от сторно операции по данъчна група с код Д 
        StTax6 Обща /нетна сума от сторно операции по данъчна група с код Е 
        StTax7 Обща /нетна сума от сторно операции по данъчна група с код Ж 
        StTax8 Обща /нетна сума от сторно операции по данъчна група с код З 
        Date Датата на последния фискален запис (6 символа DDMMYY). 
        При получаване на тази команда ФУ връща информация за последния записан дневен финансов отчет във ФП.
        */
        String res = cmdCustom(64, "T");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.contains(",")) {
            String[] resLines = res.split(",");
            response.put("DocNum", resLines[0]);
            response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
            response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
            response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
            response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
            response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
            response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
            response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
            response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));

            response.put("RevTaxA", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
            response.put("RevTaxB", reformatCurrency((resLines.length > 10)?resLines[10]:"0", 100));
            response.put("RevTaxC", reformatCurrency((resLines.length > 11)?resLines[11]:"0", 100));
            response.put("RevTaxD", reformatCurrency((resLines.length > 12)?resLines[12]:"0", 100));
            response.put("RevTaxE", reformatCurrency((resLines.length > 13)?resLines[13]:"0", 100));
            response.put("RevTaxF", reformatCurrency((resLines.length > 14)?resLines[14]:"0", 100));
            response.put("RevTaxG", reformatCurrency((resLines.length > 15)?resLines[15]:"0", 100));
            response.put("RevTaxH", reformatCurrency((resLines.length > 16)?resLines[16]:"0", 100));
            
            
            String resDate = "";
            if (resLines.length > 17) {
                String r = resLines[17];
                if (r.length() == 6) {
                    // DDMMYY
                    // 012345
                    resDate = "20"+r.substring(4, 6)+"-"+r.substring(2, 4)+"-"+r.substring(0, 2)+" 00:00:00";
                } else {
                    //??
                }
            }
            response.put("DocDate", resDate);
        } else {
            err("Грешка при четене на информация за последния фискален запис!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    /**
     * Print daily report
     * @param option 
     *  '0' or '1' for Z-report
     *  '2' or '3' for X-report
     * @param subOption '' default, 'D' - by departments
     * @return Sums in report
     * @throws IOException 
     */
    public LinkedHashMap<String, String> cmdReportDaily(String option, String subOption) throws IOException {
        /*
        69 (45h) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ИЛИ БЕЗ НУЛИРАНЕ 
        Област за данни: [[Item]Option] 
        Отговор: [Closure, Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8, StTax1,StTax2, StTax3,StTax4,StTax5,StTax6,StTax7,StTax8] 
        Item Незадължителен параметър, указващ вида на отчета. Ако не е въведено се приема = “0”. 
            = “0” или “1” – извършва се дневен отчет с нулиране (Z отчет). 
                Отчетът завършва с надпис “ФИСКАЛЕН БОН” или “НЕФИСКАЛЕН БОН” в зависимост дали ФУ е въведено в експлоатация. 
            = “2” или “3” – извършва се дневен отчет без нулиране (X отчет). 
                Отчетът завършва с отпечатване на текста “СЛУЖЕБЕН БОН” или “НЕФИСКАЛЕН БОН” в зависимост дали ФУ е въведено в експлоатация. 
            = “5” – при препълване на ЕКЛ ФУ разпечатва нейното съдържание. 
                Връща информация за броя на свободните линии в ЕКЛ. Ако ФУ не работи с ЕКЛ, връща грешка. 
            = “8” – извършва се периодичен Z отчет по департаменти. Не се връщат никакви данни. 
            = “9” – извършва се периодичен X отчет по департаменти. Не се връщат никакви данни. 
        Option Незадължителен параметър. Ако е подадена стойност "N", при изпълнение на дневен финансов Z отчет не се нулира информацията по оператори. 
        Closure Номер на фискалния запис (4 цифри). 
        Tax1 Обща сума от продажби по данъчна група с код А 
        Tax2 Обща сума от продажби по данъчна група с код Б 
        Tax3 Обща сума от продажби по данъчна група с код В 
        Tax4 Обща сума от продажби по данъчна група с код Г 
        Tax5 Обща сума от продажби по данъчна група с код Д 
        Tax6 Обща сума от продажби по данъчна група с код Е 
        Tax7 Обща сума от продажби по данъчна група с код Ж 
        Tax8 Обща сума от продажби по данъчна група с код З 
        StTax1 Обща сума от сторно операции по данъчна група с код А 
        StTax2 Обща сума от сторно операции по данъчна група с код Б 
        StTax3 Обща сума от сторно операции по данъчна група с код В 
        StTax4 Обща сума от сторно операции по данъчна група с код Г 
        StTax5 Обща сума от сторно операции по данъчна група с код Д 
        StTax6 Обща сума от сторно операции по данъчна група с код Е 
        StTax7 Обща сума от сторно операции по данъчна група с код Ж 
        StTax8 Обща сума от сторно операции по данъчна група с код З
        */
        String[] validOptions = {"0","1","2","5","8","9"};
        if (!Arrays.asList(validOptions).contains(option))
            throw new FDException("Невалиден тип дневен отчет ("+option+")!");
        if ((subOption.length() > 0) && !subOption.equals("N"))
            throw new FDException("Невалиденa опция дневен отчет ("+subOption+")!");

        String res = cmdCustom(69, option+subOption);
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            response.put("Closure", resLines[0]);
            response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 100));
            response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 100));
            response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 100));
            response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 100));
            response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 100));
            response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 100));
            response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 100));
            response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 100));
            response.put("StTaxA", reformatCurrency((resLines.length > 9)?resLines[9]:"0", 100));
            response.put("StTaxB", reformatCurrency((resLines.length > 10)?resLines[10]:"0", 100));
            response.put("StTaxC", reformatCurrency((resLines.length > 11)?resLines[11]:"0", 100));
            response.put("StTaxD", reformatCurrency((resLines.length > 12)?resLines[12]:"0", 100));
            response.put("StTaxE", reformatCurrency((resLines.length > 13)?resLines[13]:"0", 100));
            response.put("StTaxF", reformatCurrency((resLines.length > 14)?resLines[14]:"0", 100));
            response.put("StTaxG", reformatCurrency((resLines.length > 15)?resLines[15]:"0", 100));
            response.put("StTaxH", reformatCurrency((resLines.length > 16)?resLines[16]:"0", 100));
        } else {
            warn("Командата не връща птговор!");
//            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdDiagnosticInfo() throws IOException {
        /*
        90 (5Ah) ДИАГНОСТИЧНА ИНФОРМАЦИЯ 
        Област за данни: {Calculate} 
        Отговор: {FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        Calculate Ако е “1” се изчислява контролната сума на EPROM (1 байт). 
        FirmwareRev Версия на програмното осигуряване (4 символа). Space Интервал 20h (1 байт). 
        FirmwareDate Датата на програмното осигуряване DDMМYY (6 байта). 
        Space Интервал 20h (1 байт). 
        FirmwareTime Час на програмното осигуряване HHMM (4 байта). 
        ChekSum Контролна сума на EPROM (4 байта низ в шестнайсетичен вид). 
        Sw Състояние на ключетата на ФУ(4 цифри). 
        Country Номер на страната (1 байт), за България = 6 
        SerNum Инд. номер на ФУ (#MACHNO_LEN# символа). 
        FМ Номер на фискалния модул (#FMNO_LEN# символа).        
        
        194(C2h) ПРОВЕРКА И ЧЕТЕНЕ НА ДАННИ ОТ ТЕРМИНАЛА 
        Област за данни: {Item}[PIN] 
        Отговор: Data Item Един байт със стойност “T”, “W”, “I”, “S” или “C” 
        Data Зависи от Item 
        Item = “T” тест на комуникацията между ФУ и терминала 
        Отговор: {Error}[,Level,Attached,IMSI,ICC_ID,IMEI] 
            Error: Грешка при комуникация с модема: 0 = няма грешка, за повече подробности виж "Инструкция за експлоатация" 
            Level: Ниво на GSM сигнала в проценти        
            Attached: Един байт със стойност „1” при успешна регистрация на SIM картата в клетката на мобилния оператор. 
                      Ако този байт е със стойност „0”, при добър GSM сигнал, проверете платена ли е SIM картата. 
            IMSI: IMSI на SIM картата. 
            ICC_ID: сериен номер на SIM картата. 
            IMЕI: сериен номер на GSM модема. 
        Item = “W” тест на връзката между терминала и сървъра на НАП 
        Отговор: {Error}[,ErrString] 
        Error: Грешка при опита за свързване със сървъра на НАП. 
            0 = няма грешка, за повече подробности виж "Инструкция за експлоатация" 
        ErrString: Текстово поле (до 40 символа) с описание на грешката 
        Item = “I” Печат на информация от ФП за активната в този момент SIM карта 
        Отговор: {IMSI,MSISDN,OPID} IMSI: IMSI на SIM картата. 
        MSISDN: телефонен номер на SIM картата. 
        OPID: номер на мобилния оператор 0 – Mtel 1 – Globul 2 - Vivacom 
        Item = “S” Информация от ФП за активната в този момент SIM карта 
        Отговор: {IMSI,MSISDN,OPID} IMSI: IMSI на SIM картата. 
        MSISDN: телефонен номер на SIM картата. 
        OPID: номер на мобилния оператор 0 – Mtel 1 – Globul 2 - Vivacom 
        Item = “C” Сваляне на защитата на SIM картата 
        Област за данни: {C}{PIN} 
        Отговор: няма данни PIN PIN код на SIM картата Ако SIM картата е заключена с PIN код, модемът не може да работи коректно с нея. 
        Затова, при поява на грешка 107 (ЗАКЛ. SIM КАРТА), трябва да въведете PIN код, за да я отключите и да свалите защитата 
        Внимание: Внимавайте при въвеждането на PIN кода!!!! При 3 поредни грешни опита SIM картата е блокирана. Забележка: Успешното отключване на SIM картата (след коректно въведен PIN код) се проявява едва след като изключите и отново включите фискалното устроиство        
        */
        
        LinkedHashMap<String, String> response = new LinkedHashMap();
        // Request diagnostic info
        String res = cmdCustom(90, "1");
        response.put("RawInfo", res);
        response.put("ModelName", "");
        response.put("FWRev", "");
        response.put("FWRevDT", "");
        response.put("Switches", "");
        response.put("SerialNum", "");
        response.put("FMNum", "");
        response.put("CheckSum", "");
        // By documentation is this:
        // {FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        // Real 
        // {Model}{Space}{FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM} 
        // CompactM ONL01-4.01BG 29-10-2018 11:34,FFFF,00,6,DY408097,36543869
        String[] commaParts = res.split(",");
        if (commaParts.length > 0) {
            String[] spaceParts = commaParts[0].split("\\s");
            response.put("ModelName", spaceParts[0]);
            if (spaceParts.length > 1)
                response.put("FWRev", spaceParts[1]);
            if (spaceParts.length > 3)
                response.put("FWRevDT", spaceParts[2]+ " " + spaceParts[3]);
            else if (spaceParts.length > 2)
                response.put("FWRevDT", spaceParts[2]);
        }
        if (commaParts.length > 1) 
            response.put("CheckSum", commaParts[1]);
        if (commaParts.length > 2) 
            response.put("Switches", commaParts[2]);
        if (commaParts.length > 4) 
            response.put("SerialNum", commaParts[4]);
        if (commaParts.length > 5) 
            response.put("FMNum", commaParts[5]);
        
        // Request additional diagnostic information via cmd 194
        // Info about modem (GPRS)
        res = cmdCustom(194, "T");
        /*
        Отговор: {Error}[,Level,Attached,IMSI,ICC_ID,IMEI] 
            Error: Грешка при комуникация с модема: 0 = няма грешка, за повече подробности виж "Инструкция за експлоатация" 
            Level: Ниво на GSM сигнала в проценти        
            Attached: Един байт със стойност „1” при успешна регистрация на SIM картата в клетката на мобилния оператор. 
                      Ако този байт е със стойност „0”, при добър GSM сигнал, проверете платена ли е SIM картата. 
            IMSI: IMSI на SIM картата. 
            ICC_ID: сериен номер на SIM картата. 
            IMЕI: сериен номер на GSM модема. 
        */        
        response.put("RawInfoGPRS", res);
        if (res.length() > 0) {
            if (res.substring(0, 1).equals("0")) {
                commaParts = res.split(",");
                if (commaParts.length > 1)
                    response.put("GPRS_Signal", commaParts[1]);
                if (commaParts.length > 2)
                    response.put("GPRS_isReg", commaParts[2]);
                if (commaParts.length > 3)
                    response.put("GPRS_IMSI", commaParts[3]);
                if (commaParts.length > 4)
                    response.put("GPRS_ICC_ID", commaParts[4]);
                if (commaParts.length > 5)
                    response.put("GPRS_IMEI", commaParts[5]);
            } else {
                err("Грешка при изпълнение на команда за получаване на информация за данъчния терминал!");
            }
        } else
            err("Неочакван отговор при получаване на информация за данъчния терминал!");
        return response;
    }

    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        71 (47h) ПЕЧАТ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ 
        Област за данни: Няма данни 
        Отговор: Няма данни 
        Отпечатва се служебен бон с диагностична информация, който включва:
        • Датата и версията на програмното осигуряване;
        • Контролната сума на фърмуера;
        • Положението на конфигурационните ключета;
        • Друга информация
        */
        cmdCustom(71, ""); 
    }
    
    @Override
    public String cmdLastDocNum() throws IOException {
        /*
        113 (71h) ПОСЛЕДЕН НОМЕР НА ДОКУМЕНТ 
        Област за данни: Няма данни 
        Отговор: DocNumber DocNumber 
        Показва номера на последния издаден документ.        
        */
        String res = cmdCustom(113, ""); 
        return res;
    }

    @Override
    public LinkedHashMap<String, String> cmdPrinterStatus() throws IOException {
        /*
        74 (4Ah) СТАТУС НА ФУ 
        Област за данни: няма данни 
        Отговор: {S0}{S1}{S2}{S3}{S4}{S5} 
        Sn Статус байт N.
        */
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
        62 (3Eh) ИНФОРМАЦИЯ ЗА ДАТА И ЧАС 
        Област за данни: Няма данни. 
        Отговор: {DD–MM–YY}{Space}{HH:mm:SS} 
        ФУ връща информация за текущите дата и час.
        */
        String res = cmdCustom(62, "");
        Date dt;
        
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        try {
            res = res.replace(".", "-");
            dt = format.parse(res);
        } catch (ParseException ex) {
            throw new FDException("Грешка при четене на информация за дата/час ("+res+")!"+ex.getMessage());
        }
        return dt;
    }
    
    @Override
    public void cmdSetDateTime(Date dateTime) throws IOException {
        /*
        61 (3Dh) ВЪВЕЖДАНЕ НА ДАТА И ЧАС 
        Област за данни: {DD–MM–YY}{space}{HH:mm[:SS]} 
        Отговор: Няма данни 
        Забележка: Подаването на тази команда е задължително след събитие „НУЛИРАН RAM”. 
        Внимание: Не можете да въвеждате дата, която е по–ранна от датата на последния запис във ФП или на последния записан в КЛЕН-а документ.        
        */
        DateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        String params = format.format(dateTime);
        cmdCustom(61, params);
    }
    
    @Override
    public void cmdPrintCheckDuplicate() throws IOException {
        /*
        109 (6Dh) ПЕЧАТ ДУБЛИКАТ НА БОН 
        Област за данни: {Count} 
        Отговор: Няма данни Count Брой дубликати. 
        Този параметър трябва да е 1
        */
        cmdCustom(109, "1");
    }

    @Override
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException {
        /*
        195(C3h) СМЯНА НА КЛЕН И ОТЧЕТИ ОТ КЛЕН 
        Област за данни: {Item}[,Data] 
        Отговор: Code Item Един байт със стойност “I”, “S” или “R” 
        Data Зависи от Item 
        Item = “I” Смяна на КЛЕН 
        Област за данни: {I}[Reason] 
        Отговор: няма данни        
        Reason Незадължителен параметър. Ако причината за смяната на КЛЕН е повреда на предишния, въведете 1, ако не – не задавайте това поле 
        Item = “S” Печат на сумарни данни за КЛЕН 
        Област за данни: {S} 
        Отговор: няма данни 
        Item = “R” Печат/четене на данни от КЛЕН 
        Област за данни: {R}{PrnType},{Bgn},{End}[,Send] 
        Отговор: няма данни 
        PrnType Указва типа на отчета. Възможни стойности: 
            1 Печат на всички документи по номера на документите 
            2 Печат на всички документи по номера на Z отчети 
            3 Печат на всички документи по дати 
            11 Печат само на Z отчети по номера на документите 
            12 Печат само на Z отчети по номера 
            13 Печат само на Z отчети по дати 
            21 Печат на фискални бележки по номера на документите 
            22 Печат на фискални бележки по номера на Z отчети 
            23 Печат на фискални бележки по дати 
        Bgn Начало на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        End Край на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        Send Незадължителен параметър. Ако е зададен, ФУ не извършва печат, 
            а предава данните по RS-а ред по ред, като след всеки ред чака потвърждение от PC за приемането му.
            Потвърждаването от PC се състои от непакетирано еднобайтово съобщение с код 11h. 
            При липса на потвърждение, както и при изключване на захранването, ФУ преустановява пращането на данни към PC-то.        
        */
        //TODO: Check on fiscalized device
        String params = "R1,"+docNum+","+docNum;
        String res = cmdCustom(195, params);
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
        94 (5Eh) ОТЧЕТ ОТ ФП ПО ДАТА 
        Област за данни: {StartDate},{EndDate} 
        Отговор: Няма данни 
        StartDate Началната дата на фискален запис (6 символа DDMMYY). 
        EndDate Крайна дата на фискален запис (6 символа DDMMYY). 
        При изпълнение на тази команда ФУ отпечатва детайлен отчет на ФП за период от дата до дата.        
        
        79 (4Fh) СЪКРАТЕН ОТЧЕТ ОТ ФП ПО ДАТА 
        Област за данни: {StartDate},{EndDate} 
        Отговор: Няма данни 
        StartDate Начална дата с дължина 6 символа (DDMMYY) 
        EndDate Крайна дата с дължина 6 символа (DDMMYY) 
        При изпълнение на тази команда ФУ отпечатва съкратен отчет на ФП за период от дата до дата.
        */
        //TODO: Check on fiscalized device
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        if (endDate == null)
            endDate = new Date();
        String params = dtf.format(startDate)+","+dtf.format(endDate);
        cmdCustom(detailed?94:79, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdCurrentCheckInfo() throws IOException {
        /*
        103 (67h) ИНФОРМАЦИЯ ЗА БОНА 
        Област за данни: Няма данни 
        Отговор: CanVoid,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8, InvoiceFlag,InvoiceNo 
        CanVoid Показва възможно ли да се правят корекции [0/1]. 
        Tax1 Сума по данъчна група А. 
        Tax2 Сума по данъчна група Б. 
        Tax3 Сума по данъчна група В. 
        Tax4 Сума по данъчна група Г. 
        Tax5 Сума по данъчна група Д. 
        Tax6 Сума по данъчна група Е. 
        Tax7 Сума по данъчна група Ж. 
        Tax8 Сума по данъчна група З. 
        InvoiceFlag Флаг дали е отворен разширен фискален бон(фактура). 0 - не, 1 - да 
        InvoiceNo Ако InvoiceFlag = 1, номер на следващата фактура. (10 цифри) 
        Тази команда показва дали е възможно връщане (корекция) на регистрирани продажби и дава информация за натрупаните обороти по отделните данъчни групи.
        
        76 (4Ch) СТАТУС НА ФИСКАЛЕН БОН 
        Област за данни: [Option] 
        Отговор: Open,Items,Amount[,Tender,Remainder] 
        Option “T” – командата връща информация за последното извършено плащане. 
        Open “1” = отворен фискален или нефискален бон, “0” = няма отворен бон. 
        Items Брой на регистрираните продажби на текущия или последния фискален бон. 
        Amount Общата сума от последния фискален бон (10 цифри). 
        Tender Сумата от оследното плащане в текущия или последен бон (10 цифри). 
        Remainder Остатък за плащане в текущия или последен бон 
        Тази команда дава възможност на PC да установи статуса, а ако е нужно и да възстанови и завърши фискална операция, 
        прекъсната аварийно и ненавременно, например при изключване на ел. захранване.        
        */
        String res = cmdCustom(103, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            if (!resLines[0].equals("F")) {
                // Отговор: <CanVd>,<TaxA>,<TaxB>,<TaxC>,<TaxD>,<TaxE>,<TaxF>,<TaxG>,<TaxH>,<Inv>,<InvNum>,<Type>
                response.put("CanVD", resLines[0]);
                response.put("TaxA", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 1));
                response.put("TaxB", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 1));
                response.put("TaxC", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 1));
                response.put("TaxD", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 1));
                response.put("TaxE", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 1));
                response.put("TaxF", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 1));
                response.put("TaxG", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 1));
                response.put("TaxH", reformatCurrency((resLines.length > 8)?resLines[8]:"0", 1));
                response.put("Inv", (resLines.length > 9)?resLines[9]:"");
                response.put("InvNum", (resLines.length > 10)?resLines[10]:"");
                
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
                        Double sellAmount = 0d;
                        Double restAmount = 0d;
                        if (resLines.length > 2) {
                            sellAmount = stringToDouble(resLines[2]);
                            restAmount = sellAmount;
                            if (resLines.length > 4) 
                                restAmount = stringToDouble(resLines[4]);
                        }    
                        response.put("SellAmount", String.format(Locale.ROOT, "%.2f", sellAmount));
                        response.put("PayAmount", String.format(Locale.ROOT, "%.2f", Double.min(sellAmount - restAmount, sellAmount)));
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
        195(C3h) СМЯНА НА КЛЕН И ОТЧЕТИ ОТ КЛЕН 
        Област за данни: {Item}[,Data] 
        Отговор: Code Item Един байт със стойност “I”, “S” или “R” 
        Data Зависи от Item 
        Item = “I” Смяна на КЛЕН 
        Област за данни: {I}[Reason] 
        Отговор: няма данни        
        Reason Незадължителен параметър. Ако причината за смяната на КЛЕН е повреда на предишния, въведете 1, ако не – не задавайте това поле 
        Item = “S” Печат на сумарни данни за КЛЕН 
        Област за данни: {S} 
        Отговор: няма данни 
        Item = “R” Печат/четене на данни от КЛЕН 
        Област за данни: {R}{PrnType},{Bgn},{End}[,Send] 
        Отговор: няма данни 
        PrnType Указва типа на отчета. Възможни стойности: 
            1 Печат на всички документи по номера на документите 
            2 Печат на всички документи по номера на Z отчети 
            3 Печат на всички документи по дати 
            11 Печат само на Z отчети по номера на документите 
            12 Печат само на Z отчети по номера 
            13 Печат само на Z отчети по дати 
            21 Печат на фискални бележки по номера на документите 
            22 Печат на фискални бележки по номера на Z отчети 
            23 Печат на фискални бележки по дати 
        Bgn Начало на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        End Край на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        Send Незадължителен параметър. Ако е зададен, ФУ не извършва печат, 
            а предава данните по RS-а ред по ред, като след всеки ред чака потвърждение от PC за приемането му.
            Потвърждаването от PC се състои от непакетирано еднобайтово съобщение с код 11h. 
            При липса на потвърждение, както и при изключване на захранването, ФУ преустановява пращането на данни към PC-то.        
        
        
        Недокументирано се използва команда 69 с параметър 5
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(69, "5");
        response.put("EJInfoRaw", res);
        if (res.contains(",")) {
            String[] commaParts = res.split(",");
            response.put("EJ_LinesFree", commaParts[0]);
            if (commaParts.length > 1)
                response.put("EJ_LinesTotal", commaParts[1]);
        } else if (mErrors.size() > 0) {
           err("Грешка при четене на информация за КЛЕН!"); 
           throw new FDException(mErrors.toString());
        }
        return response;
    }
    
    public final String cmdCustomStream(int command, String data) throws IOException {
        mLastCmd = command;
        ProtocolV10D p = (ProtocolV10D) protocol;
        String result = p.customCommandStream(command, data);
        mStatusBytes = protocol.getStatusBytes();
        checkErrors(mClearErrors);
        return result;
    }

     public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        /*
        195(C3h) СМЯНА НА КЛЕН И ОТЧЕТИ ОТ КЛЕН 
        Област за данни: {Item}[,Data] 
        Отговор: Code Item Един байт със стойност “I”, “S” или “R” 
        Data Зависи от Item 
        Item = “I” Смяна на КЛЕН 
        Област за данни: {I}[Reason] 
        Отговор: няма данни        
        Reason Незадължителен параметър. Ако причината за смяната на КЛЕН е повреда на предишния, въведете 1, ако не – не задавайте това поле 
        Item = “S” Печат на сумарни данни за КЛЕН 
        Област за данни: {S} 
        Отговор: няма данни 
        Item = “R” Печат/четене на данни от КЛЕН 
        Област за данни: {R}{PrnType},{Bgn},{End}[,Send] 
        Отговор: няма данни 
        PrnType Указва типа на отчета. Възможни стойности: 
            1 Печат на всички документи по номера на документите 
            2 Печат на всички документи по номера на Z отчети 
            3 Печат на всички документи по дати 
            11 Печат само на Z отчети по номера на документите 
            12 Печат само на Z отчети по номера 
            13 Печат само на Z отчети по дати 
            21 Печат на фискални бележки по номера на документите 
            22 Печат на фискални бележки по номера на Z отчети 
            23 Печат на фискални бележки по дати 
        Bgn Начало на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        End Край на периода (номер документ, или номер Z отчет, или дата според PrnType) 
        Send Незадължителен параметър. Ако е зададен, ФУ не извършва печат, 
            а предава данните по RS-а ред по ред, като след всеки ред чака потвърждение от PC за приемането му.
            Потвърждаването от PC се състои от непакетирано еднобайтово съобщение с код 11h. 
            При липса на потвърждение, както и при изключване на захранването, ФУ преустановява пращането на данни към PC-то.        
        
        
        Недокументирано се използва команда 69 с параметър 5
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (toDoc.length() == 0)
            toDoc = fromDoc;
        String params = "R1,"+fromDoc+","+toDoc+",1";
        String res = cmdCustomStream(195, params);
        if (mErrors.size() > 0) {
            err("Грешка при четене на КЛЕН!");
            throw new FDException(mErrors.toString());
        }
        // T	N R Док. N#:0000036 13-02-2019 12:17
        response.put("EJ", res.replaceAll("T\\s[A-Z]\\s[A-Za-z]\\s", ""));
        return response;
     }
    
    @Override
    public LinkedHashMap<String, String> cmdReadEJ(Date fromDate, Date toDate) throws IOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String params = "R3,"+dateFormat.format(fromDate)+","+dateFormat.format(toDate)+",1";
        String res = cmdCustomStream(195, params);
        if (mErrors.size() > 0) {
            err("Грешка при четене на КЛЕН!");
            throw new FDException(mErrors.toString());
        }
        // T	N R Док. N#:0000036 13-02-2019 12:17
        response.put("EJ", res.replaceAll("T\\s[A-Z]\\s[A-Za-z]\\s", ""));
        return response;
    }

    @Override
    public String cmdLastFiscalCheckQRCode() throws IOException {
        /*
        116 (74h) Получаване на QR данни от последния документ
        Област за данни: Няма данни
        Отговор: {Result}[DocType,Data]
        Result Един байт със стойност “P”(Pass) или “F”(Fail)
        DocType Един байт със стойност “F”(Fiscal) или “S”(Service)
        Data - Ако документът не е съпроводен с печат на QR баркод, ФУ връща “XXXXX”
        - Ако документът е съпроводен с печат на QR баркод, ФУ връща:
        OpType,QRData
        OpType – 1 байт със стойности:
        “0” = Фискален бон за продажба
        “1” = Сторно фискален бон поради рекламация
        “2“ = Сторно фискален бон поради операторска грешка
        “3” = Сторно фискален бон поради нам. на данъчната основа
        QRData – данните на QR баркода        
        */
        String QRCode = "";
//        if (isFiscalized()) {
            String res = cmdCustom(116, "");
            if (res.substring(0, 1).equals("P")) {
               //PF,0,QR
               String[] commaParts = res.split(","); 
               if (commaParts.length > 2)
                   QRCode = commaParts[2];
//               if ((res.length() > 4) && res.substring(1, 2).equals("F")) {
//                   QRCode = res.substring(5);
//               }
            } else
                throw new FDException("Грешка при четене на информация за QRCode!");
//        }    
        return QRCode;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadPaymentMethods() throws IOException {
        /*
        Payment modes:
        “P” – Cash;
        “N” – payment 1
        “C” – payment 2
        “D” or “U” – payment 3
        “B” or “E” – payment 4
        
        151 (97h) SET PAYMENTS (CURRENCIES)
        Data field: {Item}{Data}
        Reply: {Data}
        Item Determines the type of the required operation. Acceptable values: “P” and “R”.
        “P” – programming name and currency rate of payment.
        Data field: {P}{Number},{Name}[{TAB}{Rate},TagNo]
        “R” –reading parameter value.
        Data field: {R}{Number}
        Reply {Number},{Name}{TAB}{Rate},{TagNo}
        Number Payment number (from 1 to #PAY_MAX_CNT#)
        Name Payment name. It is "cut" at the right side, if it is longer than #NAME_LEN# symbols.
        TAB Delimiter (ASCII 09)
        Rate Currency rate.
        TagNo Tag number for sending X and Z data to the tax authority server. 0 = <SCash>, 1 = < SChecks>, ......., 10 = <SR2> (Only for FD with tax terminal)
        
        */
        
        LinkedHashMap<String, String> response = new LinkedHashMap(); 
        int PAY_MAX_CNT = consts.containsKey("PAY_MAX_CNT")?consts.get("PAY_MAX_CNT"):4;
        String res;
        String[] parts;
        String name;
        String nratag;
        LinkedHashMap<String, Integer> pNumsCode = new LinkedHashMap(); 
        pNumsCode.put("P", 0);
        pNumsCode.put("N", 1);
        pNumsCode.put("C", 2);
        pNumsCode.put("D", 3);
        pNumsCode.put("U", 3);
        pNumsCode.put("B", 4);
        pNumsCode.put("E", 4);
        LinkedHashMap<Integer, String> pNums = new LinkedHashMap(); 
        pNums.put(0, "'Б БРОЙ' НАП #:0");
        for (int i = 1; i <= PAY_MAX_CNT; i++) {
            res = cmdCustom(151, "R"+Integer.toString(i));
            //{Number},{Name}{TAB}{Rate},{TagNo}
            parts = res.split(",");
            name = "Payment "+Integer.toString(i);
            nratag = "";
            if (parts.length > 1) 
                name = parts[1].replaceAll("\\t.*$", ""); 
            if (parts.length > 2) 
                nratag = parts[2]; 
            pNums.put(i, "'"+name+"' НАП #:"+nratag);
        }
        for(Integer i : pNums.keySet()) {
            response.put("P_"+i.toString(), pNums.get(i));
        }
        for(String pCode : pNumsCode.keySet()) {
            String pName = "N/A";
            if (pNums.containsKey(pNumsCode.get(pCode)))
                pName = pNums.get(pNumsCode.get(pCode));
            response.put("P_"+pCode, " '"+pCode+" "+pName);
        }    
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadDepartments() throws IOException {
        /*
        131 (83h) PROGRAM DEPARTMENTS
        Data field: {Item}[Data]
        Reply: Code[,Data]
        Code 1 symbol, indicating the result::
        “P” – valid command.
        “F” – invalid command.
        Item Specifies the type of required operation. Acceptable values: “P” and“R”.
        “P” – programming departments
        Data field: {P}{DeptNum},{Name}{CR}{TaxGroup},{Price}[,{MaxDigits}]
        DeptNum Number of department (from 1 to #DEPT_MAX_CNT#)
        Name Name of department. It is "cut" at the right side, if it is longer than #NAME_LEN#symbols.
        CR 1 byte with value 0Ah.
        TaxGroup Tax group. 1 byte of (А,Б,В,Г,Д,Е,Ж,З)
        Price Unit price of department (up to 8 digits). It is not used. For compatibility send 0.
        MaxDigit Maximum acceptable number of digits for free unit price entered. If there is no value (or value=0), it will be unpossible to sale by this department (command 138)
        Attention: Can‘t change the name or the tax group of the department, if there are sales on that department and the corresponfing Z reports are not issued. Check the error number (status byte 3) and print the necessary Z report.
        “R” – Reading data about department.
        Data field: {R}{DeptNum}
        Reply: {P},{DeptNum},{Name}{CR}{TaxGroup},{Price},{Amount},{Total},{PerAmount},{PerTotal},{MaxDigits}
        The information is anallogical to the command for programming departments.
        Amount Accumulated quantity at sale by this department in the daily report. Number with 3 decimal symbols.
        Total Accumulated amount from registrated sale by this department in the daily report.
        PerAmount Accumulated quantity at sale by this department in the periodic report. Number with 3 decimal symbols.
        PerTotal Accumulated amount from registrated sale by this department in the periodic report.
        If the indicated number of department is not found, FD returns Code = ‘F”.        
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
            TotInfo[] totals;

            public DeptInfo(String deptNum, String deptName, String taxGr) {
                this.deptNum = deptNum;
                this.deptName = deptName;
                this.taxGr = taxGr;
                this.totals = new TotInfo[4];
                for(int i = 0; i < 4; i++)
                    this.totals[i] = new TotInfo(0, 0);
                this.totals[0].totName = "Продажби";
                this.totals[1].totName = "Сторно от връщане/рекламация";
                this.totals[2].totName = "Сторно от операторска грешка";
                this.totals[3].totName = "Сторно от намаление на данъчната основа";
            }
            
        }
        
        LinkedHashMap<String, String> response = new LinkedHashMap(); 
        int MaxDeptCount = consts.containsKey("DEPT_MAX_CNT")?consts.get("DEPT_MAX_CNT"):10;
        DeptInfo[] Depts = new DeptInfo[MaxDeptCount];
        String res;
        for (int i = 0; i < MaxDeptCount; i++) {
            String deptNum = Integer.toString(i+1);
            res = cmdCustom(131, "R"+deptNum);
            // {P},{DeptNum},{Name}{CR}{TaxGroup},{Price},{Amount},{Total},{PerAmount},{PerTotal},{MaxDigits}
            // 0       1             2               3        4       5         6          7          8
            String[] parts = res.substring(1).split(",");
            if (parts[0].equals("P") && (parts.length > 1)) {
                Depts[i] = new DeptInfo(deptNum, "-", "-");
                String[] ng = parts[1].split("\r");
                Depts[i].deptName = ng[0];
                if (ng.length > 1)
                    Depts[i].taxGr = ng[1];
                if (parts.length > 6)
                    Depts[i].totals[0].totQty = stringToDouble(parts[6]);
                if (parts.length > 7)
                    Depts[i].totals[0].totSum = stringToDouble(parts[7]);
            } else {
//                err("Грешка при четене на информация за департамент #"+Integer.toString(i));
            }
/*            
            // Прочитане на информация за сторно сумите
            for (int j = 1; j <= 3; j++) {
                res = cmdCustom(??, deptNum+","+Integer.toString(j));
                if (res.startsWith("P")) {
                    String[] parts = res.substring(1).split(",");
                    if (parts.length > 3)
                       Depts[i].totals[j].totQty = stringToDouble(parts[3]); 
                    if (parts.length > 4)
                       Depts[i].totals[j].totSum = stringToDouble(parts[4]); 
                } else {
                    err("Грешка при четене на информация за департамент #"+Integer.toString(i)+" Сторно тип:"+Integer.toString(j));
                }
            }
*/
        }
        // Формиране на резултата
        for (int i = 0; i < MaxDeptCount; i++) {
            if (Depts[i] != null)
                response.put("D_"+Depts[i].deptNum, Depts[i].deptNum+" Група:"+Depts[i].taxGr+" Име:"+Depts[i].deptName);
        }
        // Формиране на резултата по натрупани суми
        for (int i = 0; i < MaxDeptCount; i++) {
            if (Depts[i] != null) {
                String sInfo = 
                       Depts[i].deptNum
                      + " " + Depts[i].totals[0].totName+"="
                      + Double.toString(Depts[i].totals[0].totQty)
                      + ":" + Double.toString(Depts[i].totals[0].totSum);
                response.put("DS_"+Depts[i].deptNum, sInfo);
            }    
        }
        return response; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap<String, String> cmdReadTaxGroups() throws IOException {
        /*
        97 (61h) CURRENT TAX RATES
        Data field: No data
        Reply: Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8
        Tax 1 Tax rate А
        Tax 2 Tax rate Б
        Tax 3 Tax rate В
        Tax 4 Tax rate Г
        Tax 5 Tax rate Д
        Tax 6 Tax rate Е
        Tax 7 Tax rate Ж
        Tax 8 Tax rate З
        Receiving information about the current tax rates.
        */
        LinkedHashMap<String, String> response = new LinkedHashMap(); 
        String res = cmdCustom(97, "");
        //00.00,20.00,20.00,09.00,00.00,00.00,00.00,00.00
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            response.put("TaxA", reformatCurrency((resLines.length > 0)?resLines[0]:"0", 1));
            response.put("TaxB", reformatCurrency((resLines.length > 1)?resLines[1]:"0", 1));
            response.put("TaxC", reformatCurrency((resLines.length > 2)?resLines[2]:"0", 1));
            response.put("TaxD", reformatCurrency((resLines.length > 3)?resLines[3]:"0", 1));
            response.put("TaxE", reformatCurrency((resLines.length > 4)?resLines[4]:"0", 1));
            response.put("TaxF", reformatCurrency((resLines.length > 5)?resLines[5]:"0", 1));
            response.put("TaxG", reformatCurrency((resLines.length > 6)?resLines[6]:"0", 1));
            response.put("TaxH", reformatCurrency((resLines.length > 7)?resLines[7]:"0", 1));
        } else {
            long errCode = -1;
            err("Грешка при получаване на информация за данъчните групи!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response; //To change body of generated methods, choose Tools | Templates.
    }
    
}
