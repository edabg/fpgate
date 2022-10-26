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
 * This class supports following fiscal devices Datecs FP-800 / FP-2000 / FP-650 / SK1-21F / SK1-31F/ FMP-10 / FP-550
 * Corresponding to specification PM_FISCAL_PRINTERS-BUL-v1_00BG.pdf
 * @author Dimitar Angelov
 */
public class DeviceDatecsFPV1 extends AbstractFiscalDevice {

    private SerialPort serialPort;
    
    public DeviceDatecsFPV1(AbstractProtocol p) {
        super(p);
    }

    public DeviceDatecsFPV1(String portName, int baudRate, int readTimeout, int writeTimeout) {
        super(portName, baudRate, readTimeout, writeTimeout);
		debug("Opening "+portName+" "+Integer.toString(baudRate)+" 8N1 RWTO:"+Integer.toString(readTimeout)+"/"+Integer.toString(writeTimeout));
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
        /*
        5Ah (90) ЧЕТЕНЕ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: [*]<Calc>
        Отговор: <Name>,<FwRev><Country><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        Програмен интерфейс на фискален принтер Версия 1.00BG
        DATECS FP-800 / FP-2000 / FP-650 / SK1-21F / SK1-31F/ FMP-10 / FP-550
        _
        Стр.
        30
        * Незадължителен параметър, позволяващ да се прочетат всичките 16 конфигурационни ключета.
        Calc Ако е ‘1’ се изчислява контролна сума на кодовата памет (фирмуера), в противен случай се връща ‘FFFF’. 1 байт.
        Name Име на фискалното устройство (в случая "FP-800 / FP-2000 / FP-650 / SK1-21F / SK1-31F/ FMP-10 / FP-550").
        FwRev Версията на програмното осигуряване. 4 байта.
        Country Дву-буквено означение на държавата (в случая “BG”).
        Sp Интервал. 1 байт.
        FwDate Датата на програмното осигуряване DDMmmYY. 7 байта.
        Sp Интервал. 1 байт.
        FwTime Час на програмното осигуряване HHMM. 4 байта.
        Chk Контролна сума на EPROM. 4 байта стринг в шестнайсетичен вид. Например, ако контролната сума е 214Ah, то тя ще се предаде 32h,31h,34h,41h.
        Sw Ключетата от Sw1 до Sw8. 8 байта стринг с ‘0’ или ‘1’
        Ser Индивидуален номер на устройството - 8 байта.
        FМ Номер на фискалния модул – 8 байта.        
        */
        // Request diagnostic info
        String res = cmdCustom(90, "*");
        mDeviceInfo = res;
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
        /*
        Текущото състояние на устройството е кодирано в поле с дължина 6 байта, което се предава в рамката на всяко съобщение от фискалния принтер. Следва описание на всеки байт от това поле:
        Байт 0: Общо предназначение
        0.7 Резервиран – винаги е 1.
        0.6 Отворен е капакът на принтера.
        0.5 Обща грешка - това е OR на всички грешки, маркирани с ‘#’.
        0.4 # Механизмът на печатащото устройство има неизправност.
        0.3 Не е свързан клиентски дисплей.
        0.2 Часовникът не е установен.
        0.1 # Кодът на получената команда е невалиден.
        0.0 # Получените данни имат синктактична грешка.
        Байт 1: Общо предназначение
        1.7 Резервиран – винаги е 1.
        1.6 Вграденият данъчен терминал не отговаря.
        1.5 Отворен е служебен бон за печат на завъртян на 90 градуса текст.
        1.4 Отворен сторно бон.
        1.3 # Слаба батерия (Часовникът за реално време е в състояние RESET).
        1.2 # Извършено е зануляване на оперативната памет.
        1.1 # Изпълнението на командата не е позволено в текущия фискален режим.
        1.0 При изпълнение на командата се е получило препълване на някои полета от сумите. Статус 1.1 също ще се установи и командата няма да предизвика промяна на данните в принтера.
        Байт 2: Общо предназначение
        2.7 Резервиран – винаги е 1.
        2.6 Много близък край на КЛЕН (допускат се само определени бонове).
        2.5 Отворен е служебен бон.
        2.4 Близък край на КЛЕН (по-малко от 10 MB от КЛЕН свободни).
        2.3 Отворен е фискален бон.
        2.2 Край на КЛЕН (по-малко от 1 MB от КЛЕН свободни).
        2.1 Останала е малко хартия.
        2.0 # Свършила е хартията. Ако се вдигне този флаг по време на команда, свързана с печат, то командата е отхвърлена и не е променила състоянието на принтера.
        Байт 3: За състояние на конфигурационните ключета
        3.7 Резервиран – винаги е 1.
        3.6 Състояние на Sw7.
        3.5 Състояние на Sw6.
        3.4 Състояние на Sw5.
        3.3 Състояние на Sw4.
        3.2 Състояние на Sw3.
        3.1 Състояние на Sw2.
        3.0 Състояние на Sw1.
        Байт 4: За фискалната памет
        4.7 Резервиран – винаги е 1.
        4.6 Печатащата глава е прегряла.
        4.5 OR на всички грешки, маркирани с ‘*’ от байтове 4 и 5.
        4.4 * Фискалната памет е пълна.
        4.3 Има място за по-малко от 50 записа във ФП.
        4.2 Зададени са индивидуален номер на принтера и номер на фискалната памет.
        4.1 Зададен е ЕИК по БУЛСТАТ.
        4.0 * Има грешка при запис във фискалната памет.
        Байт 5: За фискалната памет
        5.7 Резервиран – винаги е 1.
        5.6 Не се използува.
        5.5 Грешка при четене от фискалната памет.
        5.4 Зададени са поне веднъж данъчните ставки.
        5.3 Принтерът е във фискален режим.
        5.2 * Последният запис във фискалната памет не е успешен.
        5.1 Фискалната памет е форматирана.
        5.0 * Фискалната памет е установена в режим READONLY (заключена).        
        */
        statusBytesDef = new LinkedHashMap<>();
        errorStatusBits = new LinkedHashMap<>();
        warningStatusBits = new LinkedHashMap<>();
        
        statusBytesDef.put("S0", new String[] // Общо предназначение
        {
             "Синтактична грешка"             // 0 Получените данни имат синктактична грешка.
            ,"Невалиден код на команда"       // 1 Кодът на получената команда е невалиден.
            ,"Неустановени дата/час"          // 2 Не е сверен часовника.
            ,"Не е свързан клиентски дисплей" // 3 Не е свързан клиентски дисплей.
            ,"Неизправен печатен механизъм"   // 4 Механизмът на печатащото устройство има неизправност.
            ,"Обща грешка"                    // 5 Обща грешка - това е OR на 0,1 и 4.
            ,"Отворен е капакът на принтера"  // 6 Отворен е капакът на принтера
            ,""                               // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S0", new byte[] {
            2,6
        });
        errorStatusBits.put("S0", new byte[] {
            0,1,4,5
        });
        statusBytesDef.put("S1", new String[] // Общо предназначение
        {
             "Препълване"                                  // 0 При изпълнение на командата се е получило препълване на някои полета от сумите. Статус 1.1 също ще се установи и командата няма да предизвика промяна на данните в ФУ.
            ,"Непозволена команда в този режим"            // 1 Изпълнението на командата не е позволено в текущия фискален режим.
            ,"Извършено е зануляване на оперативната памет"// 2 Извършено е зануляване на оперативната памет.
            ,"Слаба батерия"                               // 3 Слаба батерия (Часовникът за реално време е в състояние RESET).
            ,"Отворен сторно бон"                          // 4 Отворен сторно бон
            ,"Отворен е служебен бон за печат завъртян"    // 5 Отворен е служебен бон за печат на завъртян на 90 градуса текст.
            ,"Вграденият данъчен терминал не отговаря"     // 6 Вграденият данъчен терминал не отговаря.
            ,""                                            // 7 Резервиран – винаги е 1.
        }
        );
        warningStatusBits.put("S1", new byte[] {
            0, 2, 3, 6
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
            ,""                                                            // 6 Не се използва.
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
             "Фискална памет в режим READONLY"           // 0 Фискалната памет е установена в режим READONLY (заключена)
            ,"Фискалната памет e форматирана"            // 1 ФП е форматирана. 
            ,"Неуспешен последен запис във ФП"           // 2 Последният запис във фискалната памет не е успешен.
            ,"ФУ е във фискален режим"                   // 3 ФУ е във фискален режим.
            ,"Зададени са поне веднъж данъчните ставки"  // 4 Зададени са поне веднъж данъчните ставки.
            ,"Грешка при четене от фискалната памет"     // 5 Грешка при четене от фискалната памет.
            ,""                                          // 6 Не се използва
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
      int byteNum = 1;
      int b = (byteNum < mStatusBytes.length)?mStatusBytes[byteNum]:0x00;
      return ((b & 0x10) == 0x10);
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
        Област за данни: Няма или <OpNum>,<Password>,<TillNum>[,<Invoice>][,<UNP>]
        Отговор: Allreceipt, FiscReceipt
        OpCode Номер на оператор /1 до 16/
        OpPwd Операторска парола /4 до 8 цифри/
        TillNmb Номер на касово място /цяло число от 1 до 99999/
        UNP Уникален номер на продажбата (формат: сериен номер на ФУ“-“четири цифри или латински букви“-“пореден номер на продажбата (седем цифри с водещи нули)“ пример: DT000600-OP01-0001000).
        При първо отваряне на бон за продажба УНП трябва да бъде зададен поне веднъж, ако след това се пропуска параметъра ФУ ще инкрементва с единица номера на продажбата автоматично.
        Invoice Един символ със стойност “I”. Наличието му предизвиква отпечатването на разширена клиентска бележка (фактура). Автоматично след HEADER-а се отпечатва номера на фактурата, а след първата команда за плащане разпечатка на сумите по данъчни групи. След плащането трябва да се отпечата информация за купувача с команда 57 (39h).
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента. /4 байта/
        FiscReceipt Броят на всички издадени фискални бонове от последното приключване на деня до момента. /4 байта/.
        Ако командата е подадена без аргументи, се връща пореден номер на продажбата от УНП на последния издаден фискален/сторно бон.
        */
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (!opCode.matches("^[1-9]{1}[0-6]?$"))
            throw new FDException("Код на оператор трябва да е между 1-16!");
        if (!opPasswd.matches("^\\d{4,8}$"))
            throw new FDException("Паролата трябва да е от 4 до 8 цифри!");
        if ((UNS.length() > 0) && !UNS.matches("^[0-9A-Za-z]{8}-[0-9A-Za-z]{4}-[0-9]{7}$"))
            throw new FDException("УНП не съотвества на формата SSSSSSSS-OOOO-#######!");
        String params;
        params = opCode+","+opPasswd+","+wpNum+(invoice?",I":"")+((UNS.length() > 0)?","+UNS:"");
        String res = cmdCustom(48, params);
//        String res2 = cmdCustom(32, "");
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
    public void cmdPrintCustomerData(String UIC, int UICType, String seller, String recipient, String buyer, String VATNum, String address) throws IOException {
        /*
        39h (57) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА
        Област за данни: [# | * | ^]<Bulstat>[<Tab><Seller>[<Tab><Receiver>[<Tab><Client>
        [<Tab><TaxNo>[<Tab><Address>]]]]]
        Отговор: Няма данни
        Bulstat ЕИК номер на купувача. Между 9 и 14 символа. Ако преди него стои символа ‘#’, данните се считат за ЕГН или символа ‘*’ - личен номер или символа ‘^’ - служебен номер.
        Tab Табулация (09H). Разделител между параметрите.
        Seller Име на продавача. До 26 символа.
        Receiver Име на получателя. До 26 символа.
        Client Име на купувача. До 26 символа.
        TaxNo ЗДДС номер на купувача. Между 10 и 14 символа.
        Address Адрес на купувача. До два реда текст, разделени с LF (0AH), първи ред максимално от 28 символа, а втори- максимално от 34 символа.
        С изключение на първия всички останали параметри не са задължителни. Ако трябва да се зададе някой параметър, всички преди него трябва да са зададени. При празен или незададен параметър се оставя празно място за попълване на ръка.
        Командата е допустима само във разширен фискален бон (фактура) за унифицирано оформяне на боновете. Трябва да се изпълни непосредствено след цялостно плащане на натрупаната за бона сума. След това вече е разрешено затварянето на бона        
        */
        if (UICType < 0 || UICType > 3)
            throw new FDException("Ivalid UIC type!");
        String uType = "";
        if (UICType == 1)
            uType = "#";
        else if (UICType == 2)
            uType = "*";
        else if (UICType == 3)
            uType = "^";
        String params;
        params = uType+UIC+"\t"+seller+"\t"+recipient+"\t"+buyer+"\t"+VATNum+"\t"+address;
        String res = cmdCustom(57, params);
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
        3Ch (60) ОТКАЗВАНЕ (ПРЕКРАТЯВАНЕ) НА ФИСКАЛЕН БОН
        Област за данни: Няма данни
        Отговор: Няма данни
        Командата е допустима само в отворен фисакален бон, и то преди изпълнението на команда 53 (Total). Предизвиква отказването на всички натрупани в бона суми. Отпечатва се с двойна ширина “=АНУЛИРАНО=” и бонът завършва с надпис “ФИСКАЛЕН БОН”.        
        */
        cmdCustom(60, "");
    }

    @Override
    public void cmdPrintFiscalText(String text) throws IOException {
        /*
        36h (54) ПЕЧАТАНЕ НА ФИСКАЛЕН СВОБОДЕН ТЕКСТ
        Област за данни: [<Tab><Font>[<Flags>],]<Text>
        Отговор: Няма данни
        Text Свободен текст за печат. В началото и края на реда се отпечатва символът '#'. 
        Текстът може да бъде с произволна дължина, но се оставят само толкова символа, колкото се побират на реда (без вдигане на грешка при изрязването).
        Командата допуска опционално посочване на шрифт и атрибути за печат на реда:
        Font Цяло число от 0 до 3:
        0 32 точки (4 mm) височина, по-високи букви
        1 32 точки (4 mm) височина, нормални букви
        2 24 точки (3 mm) височина
        3 16 точки (2 mm) височина
        Flags От една до 3 букви: 'B', 'H' или 'I'. Всяка може да се появи най-много веднъж. Задават съответно:
        B Bold (Удебелено)
        H High (Двойна височина)
        I Italic (Наклонено)
        Необходимо е да е отворен фискален бон. В противен случай не се отпечатва текста и се вдига S1.1.        
        */        
//        int maxLen = 36;
//        if (text.length() > maxLen) {
//            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
//            text = text.substring(0, maxLen);
//        }
        cmdCustom(54, text);
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenNonFiscalCheck() throws IOException {
        /*
        26h (38) ОТВАРЯНЕ НА СЛУЖЕБЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента /4 байта/.
        ФП извършва следните действия:
        • Отпечатва се HEADER.
        • Отпечатва се ЕИК на продавача.
        • Връща се отговор, съдържащ Allreceipt.
        Командата не може да се изпълни, ако:
        • Фискалната памет не е форматирана.
        • Има отворен фискален бон.
        • Вече е отворен служебен бон.
        • Часовникът не е сверен.
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
        Област за данни: [<Tab><Font>[<Flags>],]<Text>
        Отговор: Няма данни
        Text Свободен текст за печат. В началото и края на реда се отпечатва символът '#'. Текстът може да бъде с произволна дължина, но се оставят само толкова символа, колкото се побират на реда (без вдигане на грешка при изрязването).
        Командата допуска опционално посочване на шрифт и атрибути за печат на реда:
        Font Цяло число от 0 до 3:
        0 32 точки (4 mm) височина, по-високи букви
        1 32 точки (4 mm) височина, нормални букви
        2 24 точки (3 mm) височина
        3 16 точки (2 mm) височина
        Flags От една до 3 букви: 'B', 'H' или 'I'. Всяка може да се появи най-много веднъж. Задават съответно:
        B Bold (Удебелено)
        H High (Двойна височина)
        I Italic (Наклонено)        
        */        
//        int maxLen = 40; //???
        String params = "";
//        if (text.length() > maxLen) {
//            warn("Текст с дължина "+Integer.toString(text.length())+" повече от "+Integer.toString(maxLen)+" символа e съкратен!");
//            text = text.substring(0, maxLen);
//        }
        if ((font >= 1) && (font <= 3))
            params = "\t"+Integer.toString(font)+","+text;
        else 
            params = text;
        cmdCustom(42, params);
    }

    @Override
    public void cmdPrintNonFiscalText(String text) throws IOException {
        cmdPrintNonFiscalText(text, 1);
    }

    @Override
    public LinkedHashMap<String, String> cmdCloseNonFiscalCheck() throws IOException {
        /*
        27h (39) ЗАТВАРЯНЕ НА СЛУЖЕБЕН БОН
        Област за данни: Няма данни
        Отговор: Allreceipt
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента /4 байта/.
        ФП извършва следните действия:
        • Отпечатва се FOOTER.
        • Отпечатва се поредния номер, датата и часа на документа
        • Отпечатва се с широк печат “СЛУЖЕБЕН БОН”.
        • Връща се отговор, съдържащ Allreceipt.
        Ако е вдигнат S1.1 командата не е изпълнена защото в момента не е отворен служебен бон.
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
        Lines Броят на редовете, с които да бъде придвижена хартията. Трябва да бъде положително число не по-голямо от 99 /1 или 2 байта/. Ако параметър липсва, подразбира се 1 ред.        
        */        
        cmdCustom(44, Integer.toString(Integer.max(1, Integer.min(lineCount, 99))));
    }

    @Override
    public void cmdSell(String sellText, String taxGroup, double price, double quantity, String unit, String discount) throws IOException {
        /*
        31h (49) РЕГИСТРИРАНЕ (ПРОДАЖБА) НА СТОКА
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>[#UN]][,Perc|;Abs]
        или
        [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>[#UN]] [,Perc|;Abs]
        Отговор: Няма данни
        L1 Текст до 42 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 42 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 60 включително. Продажбата се причислява към данъчната група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000. Дължина до 8 значещи цифри (не повече от 3 след десетичната точка). Произведението Price*Qwan се закръгля от принтера до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        UN Име на мерна единица. Опционален текст за мерна единица на количеството до 8 символа, например “kg”.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се до 2 десетични знака.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        ФП извършва следните действия:
        • Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на департамента.
        • Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено количество, информацията за него също се отпечатва.
        • Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване се установяват съответните битове от статус полето.
        • Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на принтера. Стойностите за целия ден се отпечатват при дневния финансов отчет.
        • Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива, се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Вече са направени максималния брой продажби за един бон (512).
        • Командата (35h) е изпълнена успешно.
        • Сумата по някоя от данъчните групи става отрицателна.
        • Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
        • КЛЕН е пълна.
        • Принтерът е установил различна от регистрираната SIM карта в модема.        
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
        if (taxGroup.length() > 1)
            taxGroup = taxGroup.substring(0,1);
        params += "\t" + taxGroup+ String.format(Locale.ROOT, "%.2f", price);
        if (Math.abs(quantity) >= 0.001) {
            params += "*"+String.format(Locale.ROOT, "%.3f", quantity);
            if (unit.length() > 0) {
                if (unit.length() > 8)
                    unit = unit.substring(0,8);
                params += "#"+unit;
            }    
        }    

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
        Област за данни: [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>[#UN]][,Perc|;Abs]
        или
        [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>[#UN]] [,Perc|;Abs]
        Отговор: Няма данни
        L1 Текст до 42 байта съдържащ ред, описващ продажбата
        Lf Един байт със съдържание 0Ah.
        L2 Текст до 42 байта съдържащ втори ред, описващ продажбата
        Tab Един байт със съдържание 09h.
        TaxCd Един байт съдържащ буквата показваща видът на данъка (‘А’, ‘Б’, ‘В’, ...). Има ограничение зависещо от параметъра Enabled_Taxes, който се установява при задаването на данъчните ставки в команда 83.
        Dept Номер на департамент. Цяло число от 1 до 60 включително. Продажбата се причислява към данъчната група, с която е асоцииран департаментът при програмирането му.
        Sign Един байт със стойност ‘-‘.
        Price Това е единичната цена и е до 8 значещи цифри.
        Qwan Незадължителен параметър, задаващ количеството на стоката. По подразбиране е 1.000. Дължина до 8 значещи цифри (не повече от 3 след десетичната точка). Произведението Price*Qwan се закръгля от принтера до зададения брой десетични знаци и също не трябва да надхвърля 8 значещи цифри.
        UN Име на мерна единица. Опционален текст за мерна единица на количеството до 8 символа, например “kg”.
        Perc Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) в проценти върху текущата продажба. Допустими стойности са от -99.00 % до 99.00 %. Приемат се до 2 десетични знака.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума. Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        ФП извършва следните действия:
        • Ако продажбата е по департамент и е разрешено с команда 43, подкоманда ‘N’, отпечатва се името на департамента.
        • Текстът описващ продажбата се отпечатва заедно с цената и кода на данъчната група. Ако има зададено количество, информацията за него също се отпечатва.
        • Цената на стоката се прибавя към натрупаните суми в регистрите в оперативната памет. В случай на препълване се установяват съответните битове от статус полето.
        • Ако има отстъпка или надбавка, тя се отпечатва на отделен ред и се добавя в предвидени за това регистри на принтера. Стойностите за целия ден се отпечатват при дневния финансов отчет.
        • Ако е указан департамент, натрупаната стойност се прибавя към него. Надбавките и отстъпките, ако има такива, се отчитат.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Вече са направени максималния брой продажби за един бон (512).
        • Командата (35h) е изпълнена успешно.
        • Сумата по някоя от данъчните групи става отрицателна.
        • Сумата от надбавки или отстъпки в рамките на бона става отрицателна.
        • КЛЕН е пълна.
        • Принтерът е установил различна от регистрираната SIM карта в модема.        
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
        if (Math.abs(quantity) >= 0.001) {
            params += "*"+String.format(Locale.ROOT, "%.3f", quantity);
            if (unit.length() > 0) {
                if (unit.length() > 8)
                    unit = unit.substring(0,8);
                params += "#"+unit;
            }    
        }
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
        33h (51) МЕЖДИННА СУМА
        Област за данни: <Print><Display>[,Perc|;Abs]
        Отговор: SubTotal,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH
        Print Един байт, който ако е ‘1’ стойността на под сумата ще се отпечата.
        Display Един байт, който ако е ‘1’ стойността на под сумата ще се покаже на дисплея.
        Perc Незадължителен параметър, който показва стойността в проценти на отстъпката или надбавката върху натрупаната до момента сума.
        Abs Това е незадължителен параметър, показващ стойността на надбавката или отстъпката (в зависимост от знака) като сума (до 8 значещи цифри). Не е допустима отстъпка със стойност по-голяма от стойността на продажбата.
        Допустим е само един от аргументите Perc или Abs.
        SubTotal Сумата до момента за текущия фискален бон /до 10 байта/
        TaxA Сумата по данъчна група А /до 10 байта/
        TaxB Сумата по данъчна група Б /до 10 байта/
        TaxC Сумата по данъчна група В /до 10 байта/
        TaxD Сумата по данъчна група Г /до 10 байта/
        TaxE Сумата по данъчна група Д /до 10 байта/
        TaxF Сумата по данъчна група Е /до 10 байта/
        TaxG Сумата по данъчна група Ж /до 10 байта/
        TaxH Сумата по данъчна група З /до 10 байта/
        Изчислява се сума на всички продажби регистрирани във фискалния бон до момента. По желание сумата може да бъде отпечатана и/или показана на дисплея. Към PC се връща изчислената сума и натрупаните до момента суми за всяка данъчна група. Ако е посочена надбавка или отстъпка, тя се отпечатва на отделен ред и натрупаните суми по данъчни групи се коригират съответно.        
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
            long errCode = -1;
            err("Грешка при междинна сума във фискален бон!");
            throw new FDException(errCode, mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdTotal(String totalText, String paymentType, double amount, String pinpadOpt) throws IOException {
        /*
        35h (53) ИЗЧИСЛЯВАНЕ НА СБОР (ТОТАЛ)
        Област за данни: [<Line1>][<Lf><Line2>]<Tab>[[<PaidMode>]<[Sign]Amount>]
        Отговор: <PaidCode><Amount>
        Line1 Текст до 36 байта съдържащ първия ред
        Lf Един байт със съдържание 0Ah
        Line2 Текст до 36 байта съдържащ втория ред
        Tab Един байт със съдържание 09h
        PaidMode Незадължителен код, указващ начина на плащане. Може да има следните стойности:
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ - Плащане с кредит;
        ‘C’ - Плащане с чек;
        ‘D’ - Плащане с дебитна карта
        ‘I’ - Програмируем тип плащане 1
        ‘J’ - Програмируем тип плащане 2
        ‘K’ - Програмируем тип плащане 3
        ‘L’ - Програмируем тип плащане 4
        ‘i’ - Програмируем тип плащане 1
        ‘j’ - Програмируем тип плащане 2
        ‘k’ - Програмируем тип плащане 3
        ‘l’ - Програмируем тип плащане 4
        ‘m’ - Талони
        ‘n’ - Външни талони
        ‘o’ - Амбалаж
        ‘p’ - Вътрешно обслужване
        ‘q’ - Повреди
        ‘r’ - Банкови трансфери
        ‘s’ - С чек
        В зависимост от кода сумите се натрупват в различни регистри и могат да бъдат получени в дневния отчет.
        Sign Един байт със стойност ‘+’, указващ знака на Amount (сумата, която се плаща).
        Amount Сумата, която се плаща /до 10 значещи цифри/.
        PaidCode Един байт - резултат от изпълнението на командата.
        ‘F’ Грешка.
        ‘E’ Изчислената под сума е отрицателна. Плащане не се извършва и Amount ще съдържа отрицателната под сума.
        ‘D’ Ако платената сума е по-малка от сумата на бона. Остатъкът за доплащане се връща в Amount.
        ‘R’ Ако платената сума е по-голяма от сбора на бележката. Ще се отпечата съобщение “РЕСТО” и рестото се връща в Amount.
        ‘I’ Сумата по някоя данъчна група е бил отрицателен и затова се е получила грешка. В Amount се връща текущата под сума.
        Amount До 9 цифри със знак. Зависи от PaidCode.
        Тази команда предизвиква изчисляването на сумите от фискалния бон, отпечатването на сумата със специален шрифт и показването й на дисплей. Възможно е отпечатването на допълнителен текст. При успешно изпълнение на командата се генерира импулс за отваряне на чекмедже, ако това е разрешено с подкоманда ‘X’ на команда 43. Ако след символа <Tab> няма повече данни, то принтерът автоматично плаща цялата налична сума в брой.
        Командата няма да бъде изпълнена успешно, ако:
        • Не е отворен фискален бон.
        • Натрупаната сума е отрицателна.
        • Ако някоя от натрупаните суми по данъчни групи е отрицателна.
        След успешното изпълнение на командата, фискалният принтер няма да изпълнява командите 49 и 51 в рамките на отворения бон, обаче може да изпълнява още команда 53.
        Забележка: Кодове на грешка ‘E’ и ‘I’ никога няма да се получат в българската версия на принтера, защото команди 49 и 52 (Регистриране на продажба) няма да допуснат отрицателни суми.
        */        
        int maxLen = 36;
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
        String[] validPaymentTypes = {"","P","N","C","D","I","J","K","L","i","j","k","l","m","n","o","p","q","r","s"};
        if (!Arrays.asList(validPaymentTypes).contains(paymentType))
            throw new FDException(-1L, "Невалиден начин на плащане ("+paymentType+")!");
        if (paymentType.length() > 0) 
            params += paymentType;
        if (abs(amount) >= 0.001)
            params += String.format(Locale.ROOT, "%.2f", amount);

        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(53, params);
        if (res.length() > 0) {
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
            err("Липсва отговор при команда за обща сума и плащане на фискален бон!");
            throw new FDException(mErrors.toString());
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdOpenFiscalCheckRev(String opCode, String opPasswd, String wpNum, String UNS, String RevType, String RevDocNum, String RevUNS, Date RevDateTime, String RevFMNum, String RevReason, String RevInvNum, boolean invoice) throws IOException {
        /*
        2E (46) ИЗДАВАНЕ НА СТОРНО БОН
        Област за данни: <OpNum>,<Password>,<TillNum>[,<Invoice><InvNum>][,<UNP>],<StType><DocNo>[,<StUNP>,<StDT>,<StFMIN>][#<StornoReason>]
        Отговор: Allreceipt, StornoReceipt
        OpCode Номер на оператор /1 до 16/
        OpPwd Операторска парола /4 до 8 цифри/
        TillNmb Номер на касово място /цяло число от 1 до 99999/
        UNP Уникален номер на продажбата (формат: сериен номер на ФУ“-“четири цифри или латински букви“-“пореден номер на продажбата (седем цифри с водещи нули)“ пример: DT000600-OP01-0001000).
        При първо отваряне на бон за продажба УНП трябва да бъде зададен поне веднъж, ако след това се пропуска параметъра ФУ ще инкрементва с единица номера на продажбата автоматично.
        InvNum Номер на фактурата която се сторнира.
        Invoice Един символ със стойност “I”. Наличието му предизвиква отпечатването на сторно разширена клиентска бележка (кредитно известие). Автоматично след HEADER-а се отпечатва номера на фактурата, а след първата команда за плащане разпечатка на сумите по данъчни групи. След плащането трябва да се отпечата информация за купувача с команда 57 (39h).
        StType Причина за сторниране:
        E – операторска грешка
        R – връщане/рекламация
        T – намаление на данъчната основа
        DocNo Номер на документа (глобален), който се сторнира.
        StUNP УНП на документа, който се сторнира. Задава се цялото УНП задължително.
        StDT Дата и час на сторнирания документ. Формат „DDMMYYhhmmss“.
        StFMIN Номер на фискалната памет на ФУ от което е издаден бона, който се сторнира.
        StornoReason Причина за сторниране (до 30 символа).
        Allreceipt Броят на всички издадени бонове (фискални и служебни) от последното приключване на деня до момента. /4 байта/.
        StornoReceipt Броят на всички издадени фискални СТОРНО бонове от последното приключване на деня до момента. /4 байта/.
        Ако последния аргумент е StornoReceipt и няма следващи аргументи, ФУ търси издадения бон в контролната си лента. Ако не го намери, командата приключва неуспешно. Ако го намери, данните се попълват автоматично и се издава сторно бон с всички присъстващи в оригиналния фискален бон данни.
        Ако се подадат StUNP, StDT, StFMIN командата ще бъде приета само като отваряне на сторно бон в който трябва да се направят продажби, плащания и затваряне (по аналогия с команда 48).
        
        */        
        // TODO: Check parameters
        LinkedHashMap<String, String> response = new LinkedHashMap();
        
        String params;
        if (UNS.length() == 0) {
            throw new FDException(-1L, "Липсва Уникален Номер на Продажбата!");
        } 
        String[] validRevTypes = {"E","R","T"};
        if (!Arrays.asList(validRevTypes).contains(RevType))
            throw new FDException("Невалиден тип сторно ("+RevType+")!");
        
        if (!RevDocNum.matches("^\\d{1,7}$")) 
            throw new FDException("Невалиден номер на документа, по който е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999!");

        if (!RevFMNum.matches("^\\d{8,8}$"))
            throw new FDException("Невалиден номер на фискалната памет '"+RevFMNum+"' трябва да е число записано с 8 знака!");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        
//        params = opCode+","+opPasswd+","+UNS+","+wpNum+","+RevType+","+RevDocNum+","+dateFormat.format(RevDateTime)+","+RevFMNum;
        params = opCode+","+opPasswd+","+wpNum;
        if (invoice) {
            if (!RevInvNum.matches("^\\d{1,10}$")) 
                throw new FDException("Невалиден номер на фактура, по която е сторно операцията '"+RevDocNum+"' трябва да е число 1 - 9999999999!");
            params = params + ",I"+RevInvNum;
        }
        params += "," + UNS + "," + RevType + RevDocNum;
        
        if (RevUNS.length() > 0) {
            params += "," + RevUNS +"," + dateFormat.format(RevDateTime) + "," + RevFMNum;
        }
        if (RevReason.length() > 0) {
            if (RevReason.length() > 30)
                RevReason = RevReason.substring(0, 30);
            params += "#"+RevReason;
        }

        
        String res = cmdCustom(46, params);
        if (res.contains(",")) {
            String[] rData = res.split(",");
            response.put("AllReceipt", rData[0]);
            response.put("StrReceipt", rData[1]);
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
        Amount Сумата за регистриране (до 10 значещи цифри). В зависимост от знака на числото тя се интерпретира като внос или износ.
        ExitCode ‘P’ Заявката е изпълнена. Ако заявената сума е ненулева, принтерът отпечатва служебен бон за регистриране на операцията.
        ‘F’ Заявката е отказана. Това става, ако:
        • Касовата наличност е по-малка от заявения служебен износ.
        • Има отворен фискален или служебен бон.
        CashSum Касова наличност. Освен от тази команда сумата нараства и при всяко плащане в брой.
        ServIn Сумата от всички команди “Служебен внос”.
        ServOut Сумата от всички команди “Служебен износ”.
        Променя съдържанието на регистъра за касова наличност. В зависимост от знака на посочената сума тя се натрупва в регистъра за служебен внос или износ. Информацията не се записва във фискалната памет и е достъпна до момента на приключване на деня. Разпечатва се при команда 69 (45h) и при предизвикване на дневен финансов отчет без нулиране от самия принтер. При успешно изпълнение на командата с параметър се генерира импулс за отваряне на чекмедже, ако това е разрешено с подкоманда ‘X’ на команда 43.
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
        OpCode Код на оператор. От 1 до 16.
        Pwd Парола (4 до 8 цифри).
        OpName Име на оператор (до 24 символа).
        Задава едно от шестнайсетте имена на оператори. Номерът и името на оператора се отпечатва в началото на всеки фискален (клиентски) бон. При три грешни пароли принтерът блокира и трябва да се изключи и включи за продължаване на работата.
        След инициализация или нулиране на оперативната памет и шестнайсетте имена на оператори са празни.
        */
        String params = opCode+","+opPasswd+","+name;
        cmdCustom(102, params);
    }

    @Override
    public LinkedHashMap<String, String> cmdLastFiscalRecord() throws IOException {
        /*
        40h (64) ИНФОРМАЦИЯ ЗА ПОСЛЕДНИЯ ФИСКАЛЕН ЗАПИС
        Област за данни: [[<LongDT>]<Option>]
        Отговор: ErrCode[,N,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Date]
        LongDT Един опционален байт със стойност '*'. При наличието му се връща и часът на дневния отчет.
        Option Един опционален байт със стойност '0' или '1'. Ако липсва, се приема, че е '0'. При '0' се връща оборотът по данъчни групи, при '1' – натрупаните данъци.
        ErrCode Код на грешка:
        ‘P’ Командата е успешна. Следват данни.
        ‘F’ Командата е неуспешна.
        N Това е номера на последния фискален запис - 4 байта.
        TaxX Сумите по всяка данъчна група ‘А’, ‘Б’, ‘В’, … - 12 байта със знак.
        Date Датата на фискалния запис - 6 байта /DDMMYY/.
        Командата води до предаване на информацията от последния запис във фискалната памет към компютъра.        
        */
        String res = cmdCustom(64, "*0");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.contains(",")) {
            String[] resLines = res.split(",");
            String errCode = resLines[0].substring(0,1);
            if (errCode.equals("P")) {
                response.put("DocNum", resLines[1]);
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
                        resDate = "20" + r.substring(4, 6)+"-"+r.substring(2, 4)+"-"+r.substring(0, 2)+" 00:00:00";
                    } else if (r.length() == 12) {
                        // DDMMYYHHmmss
                        // 012345678901
                        resDate = 
                                "20" + r.substring(4, 6)+"-"+r.substring(2, 4)+"-"+r.substring(0, 2)
                                +" " + r.substring(6, 8) + ":" + r.substring(8, 10)+ ":" + r.substring(10, 12);
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
        Област за данни: [<Option>[N]
        Отговор: Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
        Option Незадължителен параметър, управляващ вида на генерирания отчет:
        ‘0’ Отпечатва се Z-отчет. Разпечатката завършва с надпис “ФИСКАЛЕН БОН”.
        ‘2’ Прави се дневен финансов отчет без нулиране (т. е. не се извършва запис във фискалната памет и нулиране на регистрите). Разпечатката завършва с лого “СЛУЖЕБЕН БОН”.
        N Наличието на този символ в края на данните забранява изчистването на натрупаните данни по оператори при отчет с нулиране.
        Closure Номер на фискалния запис - 4 байта.
        FM_Total Сумата от всички продажби без ДДС - 12 байта със знак
        TotX Сумите по всяка от данъчните групи ‘А’, ‘Б’, ‘В’, … - 12 байта със знак.
        Дневен отчет без нулиране може да се предизвика и чрез задържането на бутон <FEED> при включване на принтера до втория звуков сигнал.
        
        75h (117) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ПЕЧАТ НА ДАННИ ПО ДЕПАРТАМЕНТИ
       Област за данни:	[<Option>[N]]
       Отговор:	Closure,FM_Total,TotA,TotB,TotC,TotD,TotE,TotF,TotG,TotH
       Командата е идентична с 69 (45h) от предишната версия с единствена разлика, че в началото на дневния отчет се отпечатват и департаментите, за които има продажби за деня. 
       Команда 69 е оставена непроменена. Команди 69, 108, 117 и 118 с опция ‘0’ (дневен финансов отчет с нулиране) нулират и натрупаните данни по департаменти.
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
        /*        
        5Ah (90) ЧЕТЕНЕ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: [*]<Calc>
        Отговор: <Name>,<FwRev><Country><Sp><FwDate><Sp><FwTime>,<Chk>,<Sw>,<Ser>,<FM>
        * Незадължителен параметър, позволяващ да се прочетат всичките 16 конфигурационни ключета.
        Calc Ако е ‘1’ се изчислява контролна сума на кодовата памет (фирмуера), в противен случай се връща ‘FFFF’. 1 байт.
        Name Име на фискалното устройство (в случая "FP-800 / FP-2000 / FP-650 / SK1-21F / SK1-31F/ FMP-10 / FP-550").
        FwRev Версията на програмното осигуряване. 4 байта.
        Country Дву-буквено означение на държавата (в случая “BG”).
        Sp Интервал. 1 байт.
        FwDate Датата на програмното осигуряване DDMmmYY. 7 байта.
        Sp Интервал. 1 байт.
        FwTime Час на програмното осигуряване HHMM. 4 байта.
        Chk Контролна сума на EPROM. 4 байта стринг в шестнайсетичен вид. Например, ако контролната сума е 214Ah, то тя ще се предаде 32h,31h,34h,41h.
        Sw Ключетата от Sw1 до Sw8. 8 байта стринг с ‘0’ или ‘1’
        Ser Индивидуален номер на устройството - 8 байта.
        FМ Номер на фискалния модул – 8 байта.        
        
        4Ah (74) ПОЛУЧАВАНЕ НА СТАТУСА НА ПРИНТЕРА
        Област за данни: [Option]
        Отговор: <S0><S1><S2><S3><S4><S5>|<NLines>|HdwInfo|InfoReciepts
        Option Един байт със следните значения:
        W Първо чака да се отпечата всичко от буфера за печат на принтера.
        X Не изчаква принтера, а отговаря веднага.
        L Връща брой оставащи редове за печат.
        P Връща хардуерна информация за принтера.
        R Връща данни за изпратените към сървъра на НАП клиентски документи.
        Sn Статус байт N.
        Nlines Брой неотпечатани редове в буфера за печат. Стойност 0 означава, че няма чакащи за печат данни.
        HdwInfo Връща статистика за работата на принтера от производството му до момента. Данните са във формат:
        P<Len>,<Docs>,<Cuts>,<PwOns>,<MdRst>
        Len Дължина на отпечатаната хартия в милиметри.
        Docs Брой отпечатани документи.
        Cuts Брой отрязвания на хартията.
        PwOns Брой включвания на принтера.
        MdRst Брой рестартирания на модема. Не се поддържа в момента.
        InfoReciepts Връща данни за изпратените и не изпратените клиентски бонове
        <Lastprintdoc>,(Nlastsentdoc, Dtlastsentdoc, Minfromlastsuccesssent),(Nfirstnotsentdoc, Dtfirstnotsentdoc, Minfromfirstnotsuccesssent)        
        Lastprintdoc Номер на последния изпечатан документ.
        Nlastsentdoc Номер на последния успешно изпратен документ.
        Dtlastsentdoc Дата и час на последния успешно изпратен документ.
        Minfromlastsuccesssent Минути от последния успешно изпратен документ.
        Nfirstnotsentdoc Номер на първия не изпратен документ.
        Dtfirstnotsentdoc Дата и час на първия не изпратен документ.
        Minfromfirstnotsuccesssent Минути от първия не изпратен документ.
        При липса на данни датите са 01-01-2000 00:00:00, а минутите са 0.
        Ако минутите на Minfromfirstnotsuccesssent са повече от 1440 принтера се блокира.        
        */
        // Request diagnostic info
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(90, "*1");
        response.put("RawInfo", res);
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

        // Read Hardware Info
        res = cmdCustom(74, "P");
        // <Len>,<Docs>,<Cuts>,<PwOns>,<MdRst>
        if ((res.length() > 0)) {
            commaParts = res.split(",");
            response.put("TotalPaperLen", commaParts[0]);
            if (commaParts.length > 1)
                response.put("TotalDocPrinted", commaParts[1]);
            if (commaParts.length > 2)
                response.put("TotalCuts", commaParts[2]);
            if (commaParts.length > 3)
                response.put("TotalPowerOns", commaParts[3]);
            if (commaParts.length > 4)
                response.put("TotalModemRestarts", commaParts[4]);
        }
        // Read Nap Info
        res = cmdCustom(74, "R");
        // <Lastprintdoc>,(Nlastsentdoc, Dtlastsentdoc, Minfromlastsuccesssent),(Nfirstnotsentdoc, Dtfirstnotsentdoc, Minfromfirstnotsuccesssent)
        // 27,(17,05-02-2019 16:53:12,33),(0,01-01-2000 00:00:00,0)
        response.put("RawInfoNAPl", res);
        res = res.replace("(", "").replace(")", "");
        if (res.length() > 0) {
            commaParts = res.split(",");
            response.put("NT_LastPrintDoc", commaParts[0]);
            if (commaParts.length > 1)
                response.put("NT_LastSendDocNum", commaParts[1]);
            if (commaParts.length > 2)
                response.put("NT_LastSendDocDate", commaParts[2]);
            if (commaParts.length > 3)
                response.put("NT_MinsFromLastSent", commaParts[3]);
            if (commaParts.length > 4)
                response.put("NT_FirstNotSentDocNum", commaParts[4]);
            if (commaParts.length > 5)
                response.put("NT_FirstNotSentDocDate", commaParts[5]);
            if (commaParts.length > 6)
                response.put("NT_MinsFromFirstNotSent", commaParts[6]);
        }
        return response;
    }


    @Override
    public void cmdPrintDiagnosticInfo() throws IOException {
        /*
        47h (71) ПЕЧАТ НА ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        Област за данни: Няма данни
        Отговор: Няма данни
        Тази команда отпечатва служебен бон с диагностична информация.
        Бонът съдържа следното:
        • Датата и версията на програмното осигуряване.
        • Контролната сума на фирмуера.
        • Скоростта на предаване на серийния порт.
        • Положението на конфигурационните ключета и името на страната.
        • Аварийното време при отпадане на захранването.
        • Номера, датата и часа на последното нулиране на RAM (ако има такова).
        • Текущата температура на печатащата глава.
        • Общия брой полета във фискалната памет и броя на свободните.
        • Текущата дата и час.
        Командата няма да се изпълни при отворен бон и липса на хартия. Може да се предизвика и чрез задържането на бутон <FEED> при включване на принтера до първия звуков сигнал.        
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
        4Ah (74) ПОЛУЧАВАНЕ НА СТАТУСА НА ПРИНТЕРА
        Област за данни: [Option]
        Отговор: <S0><S1><S2><S3><S4><S5>|<NLines>|HdwInfo|InfoReciepts
        Option Един байт със следните значения:
        W Първо чака да се отпечата всичко от буфера за печат на принтера.
        X Не изчаква принтера, а отговаря веднага.
        L Връща брой оставащи редове за печат.
        P Връща хардуерна информация за принтера.
        R Връща данни за изпратените към сървъра на НАП клиентски документи.
        Sn Статус байт N.
        Nlines Брой неотпечатани редове в буфера за печат. Стойност 0 означава, че няма чакащи за печат данни.
        HdwInfo Връща статистика за работата на принтера от производството му до момента. Данните са във формат:
        P<Len>,<Docs>,<Cuts>,<PwOns>,<MdRst>
        Len Дължина на отпечатаната хартия в милиметри.
        Docs Брой отпечатани документи.
        Cuts Брой отрязвания на хартията.
        PwOns Брой включвания на принтера.
        MdRst Брой рестартирания на модема. Не се поддържа в момента.
        InfoReciepts Връща данни за изпратените и не изпратените клиентски бонове
        <Lastprintdoc>,(Nlastsentdoc, Dtlastsentdoc, Minfromlastsuccesssent),(Nfirstnotsentdoc, Dtfirstnotsentdoc, Minfromfirstnotsuccesssent)        
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
        Не може да се установява дата, по-ранна от датата на последния запис във фискалната памет. Предвидено е да се работи до 2099 година включително. След RESET на паметта командата трябва задължително да се изпълни, за да се продължи нормалната работа, при което се извършва RESET запис във фискалната памет.
        Не е възможно задаване на дата и час по-ранна от последния отпечатан документ, записан в КЛЕН. Това е с цел коректно търсене на документи от КЛЕН по дата и час за печат или изтегляне по серийния порт като текст.
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
        Предизвиква отпечатването на копие на последния затворен фискален бон с продажби. Копието се маркира като СЛУЖЕБЕН БОН и веднага след HEADER-а се отпечатва ред с удебелен шрифт “ДУБЛИКАТ”. При повторен опит командата ще откаже да печати. Отпечатването на дублиращ бон е невъзможно и ако броят редове в бона е по-голям от 1000.
        */
        //TODO: Check on fiscalized device
        cmdCustom(109, "1");
    }

    @Override
    public void cmdPrintCheckDuplicateEJ(String docNum) throws IOException {
        /*
        77h (119) РАБОТА С КЛЕН
        Област за данни: <Type>[,<InpData>]
        Отговор: Зависи от входните данни
        Type Клас на командата за работа с КЛЕН. Един байт с допустима стойност:
        'C' Проверка валидността на КЛЕН или част от нея.
        'I' Информация за КЛЕН.
        'N' Четене на следващ текстов ред от КЛЕН.
        'R' Четене на данни от КЛЕН.
        'P' Печат на данни от КЛЕН.
                ....

        Клас команди 'P': Печат на данни от КЛЕН.
        [<Fnt>][#<Flg>,]<D1>[,<D2>] Отпечатват се документите от номер D1 до номер D2 включително. Ако е пропуснат втория аргумент, избран е само един документ - D1.
        [<Fnt>][#<Flg>,]*<Cl>[,<D1>[,<D2>]] Отпечатват се документите от номер D1 за Z-отчет Cl до номер D2 включително за същия Z-отчет. Броячът на документи е за Z-отчета, т. е. Командата „*5,1,3“ ще отпечати първите три документа от Z-отчет номер 5. Ако е пропуснат D2, то е избран само един документ – D1. Ако са пропуснати D1 и D2, избрани са всички документи за Z-отчета.
        [<Fnt>]<Flg>,<DT1>,<DT2> Печатат се документите с дата и час от DT1 до DT2 включително. Има филтриране на документите, които са избрани, в зависимост от аргумента Flg. Формат на входните аргументи:
        Fnt Незадължителен параметър, с който можем да предизвикаме печата на документите от КЛЕН с шрифт с определена височина, ако принтерът го допуска. Един байт с допустима стойност:
        '>' Печат с нормален размер на шрифта.
        '<' Печат с ½ височина на шрифта..
        Flg Символ, задаващ тип на документ, който да се избере за четене:
        'A' Всички видове документи.
        'F' Фискални (клиентски) бонове.
        'V' Сторно (клиентски) бонове.
        'C' Анулирани (клиентски) бонове.
        'N' Служебни бонове.
        'I' Бонове от служебно въвеждане.
        'О' Бонове от служебно извеждане.
        'R' Служебни бонове със завъртян на 90 градуса печат.
        'S' Бонове от сервизни операции.
        'P' Отчети (само информация за дата/час и номер на бона)
        'X' X-отчети.
        'Z' Z-отчети.
        DT1 Начални дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се подразбира „000000“, т.е 00:00:00.
        DT2 Крайни дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се подразбира „235959“, т.е 23:59:59.
                ...        
        Трите предходни подкоманди (по документ, Z-отчет и дата), връщат в резултат броя отпечатани документи като цяло число.
        */
        //TODO: Check on fiscalized device
        String params = "P,#A,"+docNum;
        String res = cmdCustom(119, params);
        if (res.length() > 0) {
            // Отговор 1: Брой отпечатани документ
            if(res.equals("0"))
                warn("Не са отпечатани данни от КЛЕН!");
        } else {
            err("Неочакван отговор при печат на дублиращ ФБ от КЛЕН!");
        }
    }

    @Override
    public void cmdReportByDates(boolean detailed, Date startDate, Date endDate) throws IOException {
        /*
        5Eh (94) ПЪЛЕН ОТЧЕТ НА ФИСКАЛНАТА ПАМЕТ ПО ДАТА НА ФИСКАЛЕН ЗАПИС
        Област за данни: [<SHA1>][<SkipZ>]<Start>[,<End>]
        Отговор: Няма данни
        SHA1 Опционален аргумент – един байт със стойност '#'. Ако присъства, за всеки Z-отчет се отпечатва и контролната сума по алгоритъм SHA-1.
        SkipZ Опционален аргумент – един байт със стойност '*'. Ако присъства, за всеки Z-отчет се отпечатват само данъчните ставки, за които натрупаните суми за деня са ненулеви. Такъв отчет е нестандартен и може дасе използува само за вътрешни справки на обекта!
        Start Началната дата на фискален запис. 6 байта (DDMMYY).
        End Крайна дата на фискален запис. 6 байта (DDMMYY).
        Тази команда отпечатва пълен отчет на фискалната памет за периода между две дати.
        Ако вторият параметър липсва, командата генерира месечен или годишен отчет. Синтаксисът в този случай е:
        Start Месец – 4 байта (MMYY) за месечен отчет.
        Start Година – 2 байта (YY) за годишен отчет.
        
        4Fh (79) СЪКРАТЕН ОТЧЕТ НА ФИСКАЛНАТА ПАМЕТ ОТ ДАТА ДО ДАТА
        Област за данни: <Start>[,<End>]
        Отговор: Няма данни
        Start Начална дата - 6 байта (DDMMYY)
        End Крайна дата - 6 байта (DDMMYY)
        Командата води до изчисляване и отпечатване на съкратен отчет на фискалната памет.
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
        Отговор: CanVd,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Inv,InvNum
        CanVd: Възможно ли е връщане (продажба с отрицателен знак) [0/1]
        TaxA: Натрупана сума по данъчна група А
        TaxB: Натрупана сума по данъчна група Б
        TaxC: Натрупана сума по данъчна група В
        TaxD: Натрупана сума по данъчна група Г
        TaxE: Натрупана сума по данъчна група Д
        TaxF: Натрупана сума по данъчна група Е
        TaxG: Натрупана сума по данъчна група Ж
        TaxH: Натрупана сума по данъчна група З
        Inv: Отворена ли е разширена клиентска бележка.
        InvNmb: Номер на следващата фактура /10 цифри/.
        Дава информация за натрупаните суми по данъчни групи и дали е възможно връщане на регистрирани стоки.        
        
        4Ch (76) СТАТУС НА ФИСКАЛНАТА ТРАНЗАКЦИЯ
        Област за данни: [Option]
        Отговор: Open,Items,Amount[,Tender]
        Option = ‘T’. Ако този параметър е указан командата ще върне информацията относно текущото състояние на дължимата до момента сметка от клиента.
        Open Един байт, който е ‘1’ ако е отворен фискален или служебен бон (какъв точно може да се разбере по статус битовете), и ‘0’ ако няма отворен бон.
        Items Броят на продажбите регистрирани на текущия или на последния фискален бон. 4 байта.
        Amount Сумата от последния фискален бон – 9 байта със знак.
        Tender Сумата платена на поредния или последен бон. 9 байта със знак.
        Тази команда дава възможност на приложението в PC да установи статуса, а ако е нужно и да възстанови и завърши фискална операция, прекъсната аварийно и ненавременно, например при изключване на ел. захранване.        
        */
        String res = cmdCustom(103, "");
        LinkedHashMap<String, String> response = new LinkedHashMap();
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            if (!resLines[0].equals("F")) {
                // Отговор: CanVd,TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH,Inv,InvNum
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
                
                // Additional info about fiscal transaction
                setClearErrors(false);
                try {
                    res = cmdCustom(76, "T");
                    if (res.length() > 0) {
                        resLines = res.split(",");
                        response.put("IsOpen", resLines[0]);
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
        77h (119) РАБОТА С КЛЕН
        Област за данни: <Type>[,<InpData>]
        Отговор: Зависи от входните данни
        Type Клас на командата за работа с КЛЕН. Един байт с допустима стойност:
        'C' Проверка валидността на КЛЕН или част от нея.
        'I' Информация за КЛЕН.
        'N' Четене на следващ текстов ред от КЛЕН.
        'R' Четене на данни от КЛЕН.
        'P' Печат на данни от КЛЕН.
                ...
        Клас команди 'I': Информация за КЛЕН.
        [X] След символа 'I' може да няма нищо или да има буквата 'X'. Връща се следната информнация:
        P,Tot,Used,C1,C2,D1,D2
        Tot Общ размер на контролната лента в байтове.
        Used Използуван размер на контролната лента в байтове.
        C1 Първи номер на Z-отчет в КЛЕН.
        C2 Последен номер на Z-отчет в КЛЕН.
        D1 Първи номер на документ в КЛЕН.
        D2 Последен номер на документ в КЛЕН.        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String res = cmdCustom(119, "I");
        if (res.length() > 0) {
            String[] resLines = res.split(",");
            if (resLines[0].equals("P")) {
                response.put("Size", (resLines.length > 1)?resLines[1]:"");
                response.put("Used", (resLines.length > 2)?resLines[2]:"");
                response.put("FromZ", (resLines.length > 3)?resLines[3]:"");
                response.put("ToZ", (resLines.length > 4)?resLines[4]:"");
                response.put("FromDoc", (resLines.length > 5)?resLines[5]:"");
                response.put("ToDoc", (resLines.length > 6)?resLines[6]:"");
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
        'C' Проверка валидността на КЛЕН или част от нея.
        'I' Информация за КЛЕН.
        'N' Четене на следващ текстов ред от КЛЕН.
        'R' Четене на данни от КЛЕН.
        'P' Печат на данни от КЛЕН.
        ...
        Клас команди 'R': Четене на данни от КЛЕН.
        [#<Flg>,]<D1>[,<D2>] Връща първия ред от документ номер D1 и задава край на търсенето при документ D2 (включително). Следващите редове се теглят с изпращане на команда клас 'N'. Ако е пропуснат втория аргумент, избран е само един документ - D1.
        [#<Flg>,]*<Cl>[,<D1>[,<D2>]] Връща първия ред от документ номер D1 за Z-отчет Cl и задава край на търсенето при документ D2 (включително) за същия Z-отчет. Следващите редове се теглят с изпращане на команда клас 'N'. Броячът на документи е за Z-отчета, т. е. Командата „*5,1,3“ ще избере първите три документа от Z-отчет номер 5.Ако е пропуснат D2, то е избран само един документ – D1. Ако са пропуснати D1 и D2, избрани са всички документи за Z-отчета.
        <Flg>,<DT1>,<DT2> Връща първия ред от документ с дата и час DT1 и задава край на търсенето при документ с дата и час DT2 (включително). Има филтриране на документите, които са избрани, в зависимост от аргумента Flg. Следващите редове се теглят с изпращане на команда клас 'N'. Формат на входните аргументи:
        Flg Символ, задаващ тип на документ, който да се избере за четене:
        'A' Всички видове документи.
        'F' Фискални (клиентски) бонове.
        'V' Сторно (клиентски) бонове.
        'C' Анулирани (клиентски) бонове.
        'N' Служебни бонове.
        'I' Бонове от служебно въвеждане.
        'О' Бонове от служебно извеждане.
        'R' Служебни бонове със завъртян на 90 градуса печат.
        'S' Бонове от сервизни операции.
        'P' Отчети (само информация за дата/час и номер на бона)
        'X' X-отчети.
        'Z' Z-отчети.
        DT1 Начални дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се подразбира „000000“, т.е 00:00:00.
        DT2 Крайни дата и час на справката във формат DDMMYY[hhmmss]. Ако се изпусне часа, то се подразбира „235959“, т.е 23:59:59.        
        */
        LinkedHashMap<String, String> response = new LinkedHashMap();
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        String params = "R,A,"+dtf.format(fromDate)+","+dtf.format(toDate);
        String EJ = "";
        String res = cmdCustom(119, params);
        if (mErrors.size() > 0) {
           err("Грешка при четене на информация за КЛЕН!"); 
           throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && res.substring(0, 1).matches("^P|[*]$")) {
            EJ = EJ + res.substring(2) + "\n";
            res = cmdCustom(119, "N"); // read next row
        }
        response.put("EJ", EJ);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadEJ(String fromDoc, String toDoc) throws IOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = "R,#A,"+fromDoc+","+toDoc;
        String EJ = "";
        String res = cmdCustom(119, params);
        if (mErrors.size() > 0) {
           err("Грешка при четене на информация за КЛЕН!"); 
           throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && res.substring(0, 1).matches("^P|[*]$")) {
            EJ = EJ + res.substring(2) + "\n";
            res = cmdCustom(119, "N"); // read next row
        }
        response.put("EJ", EJ);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadDocInfo(String docNum) throws IOException {
        /*
        77h (119) РАБОТА С КЛЕН
        Област за данни: <Type>[,<InpData>]
        Отговор: Зависи от входните данни
        Type Клас на командата за работа с КЛЕН. Един байт с допустима стойност:
        'C' Проверка валидността на КЛЕН или част от нея.
        'I' Информация за КЛЕН.
        'N' Четене на следващ текстов ред от КЛЕН.
        'R' Четене на данни от КЛЕН.
        'P' Печат на данни от КЛЕН.
        Клас команди ‘W’ - връщане на структурирана информация
        W,<FirstDoc>[,<LastDoc>] Намиране на документ <FirstDoc> и връщане на първия текстов ред структурирана информация.
        или
        W,D<StartDateTime>,<EndDateTime> Намиране на първия документ между StartDateTime и EndDateTime и връщане на първия текстов ред структурирана информация. Датата и часът са във формат DDMMYY[hhmmss]. При пропускане на часа началният час е 00:00:00, а крайният 23:59:59.
        w Връщане на следващ ред структурирана информация
        FirstDoc Номер на първия документ за справката.
        EndDoc Номер на последния документ за справката. Ако липсва, е равен на първия.
        Върната структурирана информация може да има следния вид:
        U,<UNP>,<OperNum>,<TillNum>,<RecType>,<DocNum>
        V,<DecRec>,<Decimals>,<TaxRate1>, ... ,<TaxRate8>
        R,<TaxGrPos>,<TaxRate>,<SinglePrice>,<Quantity>,<Discount_MarkUp>,<Price>,<ArticleName>
        M,<Discount_MarkUp>,[<Percent>],<Subtotal>
        T,<DiscCnt>,<DiscSum>,<MarkUpCnt>,<MarkUpSum>,<VoidCnt>,<VoidSum>,<SalesCnt>,<Total>,<TotGr1>, ... ,<TotGr8>
        P,<CashPayd>,<CheqPayd>,<CardPayd>,<CredPayd>,<MorePayd1>, ... ,<MorePayd11>
        S,<StornoType>,<StornedDocNo>,<StornedDT>,<StornedFMIN>,<StornedUNP>,<StrornedInvoice>
        I,<Invoice>,<PIN>,<PINtype>
        D,<StartDT>,<EndDT>
        Z,<ZNo>,<DocNum>,<FiscNum>,<DateTime>,<Total>,<StornoSum>,<CashSum>
        *,
        F
        където:
        Справка "U": Структурирана информация за бона. Еднократно в бона.
        UNP Уникален номер на продажба. 21 символа с формат XXXXXXXX-YYYY-NNNNNNN.
        OperNum Номер на оператор. От 1 до 16.
        TillNum Номер на касово място. До 5 цифри.
        RecType Вид на бона: 0 - фискален; 1 - фактура; 2 - сторно; 3 - кредитно известие; 4 - анулиран.
        DocNum Глобален номер на документа.
        Справка "V": Структурирана информация за десетични знаци и данъчни ставки. Еднократно в бона.
        DecRec Номер на записа с данъчни ставики във ФП. Брои се от 1.
        Decimals Десетични знаци. 0 или 2.
        TaxRateX Десетична ставка X в проценти.
        Справка "R": Структурирана информация за продажба или корекция. Може да я има многократно в бона.
        TaxGrPos Данъчна група (от 1 до 8).
        TaxRate Данъчна ставка в проценти.
        SinglePrice Единична цена. За корекция е отрицателна.
        Quantity Количество. За корекция е отрицателно.
        Discount_MarkUp Отстъпка/надбавка (в зависимост от знака). За корекция е с обърнат знак.
        Price Цена.
        ArticleName Име на продадената стока. Ако е от два реда, разделителят е <TAB>.
        Справка "M": Структурирана информация групова отстъпка/надбавка. Може да я има многократно в бона.
        Discount_MarkUp Отстъпка/надбавка (в зависимост от знака).
        Percent Процент на отстъпката/надбавката. Полето може да е празно, ако е в сума.
        Subtotal Междинна сума след операцията.
        Справка "T": Структурирана информация за натрупаните суми в бона. Еднократно в бона.
        DiscCnt Брой отстъпки.
        DiscSum Сума от отстъпки.
        MarkUpCnt Брой надбавки.
        MarkUpSum Сума от надбавки.
        VoidCnt Брой от корекции.
        VoidSum Сума от корекции.
        SalesCnt Брой продажби.
        Total Обща сума за бона.
        TotGrX Сума по данъчна група.        
        Справка "P": Структурирана информация за платените суми. Еднократно в бона.
        CashPayd Платено в брой.
        CheqPayd Платено с чек.
        CardPayd Платено с карта.
        CredPayd Платено с кредитна карта.
        MorePaydX Допълнително плащане (по реда, зададен от принтера).
        Справка "S": Структурирана информация за сторниран бон. Липсва във фискалните бонове. Еднократно в бона.
        StornoType Тип на сторното: 0 - операторска грешка; 1 - връщане/рекламация; 2 - намаление дан. основа.
        StornedDocNo Номер на сторнирания документ.
        StornedDT Дата и час на сторнирания документ във формат DD-MM-YYYY hh:mm:ss.
        StornedFMIN Номер на фискалната памет за сторнирания документ.
        StornedUNP Уникален номер на продажба на сторнирания документ.
        StrornedInvoice Номер на фактура. Ако не е кредитно известие, съдържа 0.
        Справка "I": Структурирана информация за фактура. Еднократно в боновете тип фактура.
        Invoice Номер на фактура.
        PIN ПИН.
        PINtype Вид на ПИН: 0 - БУЛСТАТ; 1 - ЕГН; 2 - Личен номер; 3 - Служебен номер.
        Справка тип "D": Дата и час на започване и на завършване на бона.
        StartDT Начална дата и час във формат DD-MM-YYYY hh:mm:ss.
        Датата отпечатана на бона.
        EndDT Крайна дата и час във формат DD-MM-YYYY hh:mm:ss.
        Справка тип "Z": Дневен отчет.
        ZNo Номер на дневния отчет.
        DocNum Номер на документ.
        FiscNum Номер на последен фискален документ. Може да е 0, ако няма издавани.
        DateTime Дата и час във формат DD-MM-YYYY hh:mm:ss.
        Total Обща сума от продажби за деня.
        StornoSum Обща сума от сторно бонове.
        CashSum Касова наличност.
        Маркер "*": Край на бон.
        Маркер "F": Няма повече данни.
        */
        if (docNum.length() == 0)
            docNum = cmdLastDocNum();
        LinkedHashMap<String, String> response = new LinkedHashMap();
        String params = "W,"+docNum;
        String EJ = "";
        String res = cmdCustom(119, params);
        if (mErrors.size() > 0) {
           err("Грешка при четене на информация за КЛЕН!"); 
           throw new FDException(mErrors.toString());
        }
        while ((res.length() > 0) && !res.substring(0, 1).matches("^F|[*]$")) {
            EJ = EJ + res + "\n";
            res = cmdCustom(119, "w"); // read next row
        }
        response.put("EJ", EJ);
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdShortDocInfo(String docNum) throws IOException {
        LinkedHashMap<String, String> response = new LinkedHashMap();
        return response;
    }
    
    @Override
    public LinkedHashMap<String, String> cmdReadPaymentMethods() throws IOException {
        /*
        `P’	- Плащане в брой (по подразбиране);
	‘N’	- Плащане с кредит;
	‘C’	- Плащане с чек;
	‘D’	- Плащане с дебитна карта
	‘I’	- Програмируем тип плащане 1
	‘J’	- Програмируем тип плащане 2
	‘K’	- Програмируем тип плащане 3
	‘L’	- Програмируем тип плащане 4
        ‘i’	- Програмируем тип плащане 1
	‘j’	- Програмируем тип плащане 2
	‘k’	- Програмируем тип плащане 3
	‘l’	- Програмируем тип плащане 4
        ‘m’	- Талони
	‘n’	- Външни талони
	‘o’	- Амбалаж
	‘p’	- Вътрешно обслужване
        ‘q’	- Повреди
	‘r’	- Банкови трансфери
	‘s’	- С чек

    Видове плащания в паметта на фискалното устройство
    ##  Код НАП Стойност по подразбиране    Промяна Команда четене  Команда запис
    --------------------------------------------------------------------------        
    0   P   0   В БРОЙ                      Не      Не е възможна   Не е възможна
    1   N   7   ПЛАЩАНЕ С КРЕДИТ            Не      Не е възможна   Не е възможна
    2   C   1   ПЛАЩАНЕ С ЧЕК               Не      Не е възможна   Не е възможна
    3   D   7   ПЛАЩАНЕ С ДЕБИТНА КАРТА     Не      Не е възможна   Не е възможна
    4   I,i 9   ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 1      Да      85,i            85,i,ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 1
    5   j,j 9   ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 2      Да      85,j            85,j,ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 2
    6   K,k 9   ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 3      Да      85,k            85,k,ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 3
    7   L,l 9/10ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 4      Да      85,l            85,l,ДОПЪЛНИТЕЛНО ПЛАЩАНЕ 4
    8   m   2   ТАЛОНИ                      Да      85,m            85,m,ТАЛОНИ
    9   n   3   ВЪНШНИ ТАЛОНИ               Да      85,n            85,n,ВЪНШНИ ТАЛОНИ
    10  o   4   АМБАЛАЖ                     Да      85,o            85,o,АМБАЛАЖ
    11  p   5   ВЪТРЕШНО ОБСЛУЖВАНЕ         Да      85,p            85,p,ВЪТРЕШНО ОБСЛУЖВАНЕ
    12  q   6   ПОВРЕДИ                     Да      85,q            85,q,ПОВРЕДИ        
    13  r   7   БАНКОВИ ТРАНСФЕРИ           Да      85,r            85,r,БАНКОВИ ТРАНСФЕРИ
    14  s   1   С ЧЕК                       Да      85,s            85,s,С ЧЕК
*/        
//    InputString = InputString + String.format("%c%c%c", new Object[] { Integer.valueOf(132), Integer.valueOf(48), Integer.valueOf(59) });
        LinkedHashMap<String, String> response = new LinkedHashMap(); // 
        String res;
        LinkedHashMap<String, String> pList = new LinkedHashMap(); // 
        pList.put("P", "'P' - 'В БРОЙ' НАП #:0");
        pList.put("N", "'N' - 'ПЛАЩАНЕ С КРЕДИТ' НАП #:7");
        pList.put("C", "'C' - 'ПЛАЩАНЕ С ЧЕК' НАП #:1");
        pList.put("D", "'D' - 'ПЛАЩАНЕ С ДЕБИТНА КАРТА' НАП #:7");
        String[] pCodes = new String[]        {"I","J","K","L",   "i","j","k","l",   "m","o","p","q","r","s"};
        String[] paymentNRAMap = new String[] {"9","9","9","9,10","9","9","9","9,10","2","3","5","6","r","1"};
        for(int i = 0; i < pCodes.length; i++) {
            res = cmdCustom(85, pCodes[i]);
            LOGGER.fine(res);
            pList.put(pCodes[i], "'"+pCodes[i]+"' - '"+res+"' НАП #:"+paymentNRAMap[i]);
        }
        int i = 0;
        for (String pCode : pList.keySet()) {
            response.put("P_"+Integer.toString(i++), pList.get(pCode));
        }
        for (String pCode : pList.keySet()) {
            response.put("P_"+pCode, pList.get(pCode));
        }
        return response;
    }

    @Override
    public LinkedHashMap<String, String> cmdReadDepartments() throws IOException {
        /*
        58H (88)	ПОЛУЧАВАНЕ ДАННИ ЗА НАТРУПАНИТЕ СУМИ ЗА ДЕПАРТАМЕНТ
        Област за данни:	<Dept>
        Отговор:	ExitCode[TaxGr,RecSales,RecSum,TotSales,TotSum,Line1<LF>Line2]
        Dept	Номер на департамент. Цяло число от 1 до 1200. При стойност 0 на департамента се връщат данните за продабите, извършени без посочване на департамент. В този случай липсва данъчната група.
        ExitCode	Един байт с възможни стойности:
        ‘P’	Департаментът е програмиран. Следват описаните по-долу данни за него.
        ‘F’	Департаментът не е програмиран. Няма данни за него.
        TaxGr	Данъчна група на департамента.
        RecSales	Брой продажби за департамента в бона.
        RecSum	Натрупана сума за текущия или последния фискален бон за съответния департамент. Плаващо число с два десетични знака.
        TotSales	Брой продажби за департамента за деня.
        TotSum	Натрупана сума за деня за съответния департамент. Плаващо число с два десетични знака.
        Line1	Име или поясняващ текст за департамента.
        Line2	Име или поясняващ текст за департамента – втори ред.
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
        int MaxDeptCount = 1200;
        DeptInfo[] Depts = new DeptInfo[MaxDeptCount];
        String res;
        for (int i = 0; i < MaxDeptCount; i++) {
            String deptNum = Integer.toString(i+1);
            res = cmdCustom(88, deptNum);
            if (res.startsWith("P")) {
                Depts[i] = new DeptInfo(deptNum, "-", "-");
                String[] parts = res.substring(1).split(",");
                Depts[i].taxGr = parts[0];
                if (parts.length > 3)
                   Depts[i].totals[0].totQty = stringToDouble(parts[3]); 
                if (parts.length > 4)
                   Depts[i].totals[0].totSum = stringToDouble(parts[4]); 
                if (parts.length > 5)
                   Depts[i].deptName = parts[5].replace("\n", "\\n");
            } else {
                // Депатаментът не е програмиран
                // err("Грешка при четене на информация за департамент #"+Integer.toString(i));
            }
/*            
            // Прочитане на информация за сторно сумите
            for (int j = 1; j <= 3; j++) {
                res = cmdCustom(88, deptNum+","+Integer.toString(j));
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
        61h (97) 	ПРОЧИТАНЕ НА УСТАНОВЕНИТЕ ДАНЪЧНИ СТАВКИ
       Област за данни:	Няма данни
       Отговор:	TaxA,TaxB,TaxC,TaxD,TaxE,TaxF,TaxG,TaxH
       TaxA	Данъчна ставка А 
       TaxB	Данъчна ставка Б	
       TaxC	Данъчна ставка В
       TaxD	Данъчна ставка Г
       TaxE	Данъчна ставка Д 
       TaxF	Данъчна ставка Е	
       TaxG	Данъчна ставка Ж
       TaxH	Данъчна ставка З

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
