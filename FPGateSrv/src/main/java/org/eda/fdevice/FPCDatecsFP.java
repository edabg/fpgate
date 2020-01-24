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
package org.eda.fdevice;

import java.util.LinkedHashMap;
import org.eda.protocol.DeviceDatecsFPV1;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "Datecs Fiscal devices via raw serial communication protocol v1! Supported devices: FP-800 / FP-2000 / FP-650 / SK1-21F / SK1-31F/ FMP-10 / FP-550"
)
public class FPCDatecsFP extends FPCGeneralV10{

    public FPCDatecsFP() throws Exception {
        super();
    }

    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
        this.FP = new DeviceDatecsFPV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
        this.FP.open();
    }

    @Override
    protected String paymenTypeChar(paymentTypes payType) {
        /*		
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
        */        
        String pc = "";
        switch (payType) {
            case CASH : pc = params.get("PMCache", "P"); break;
            case CREDIT : pc = "N"; break;
            case CARD :  pc = params.get("PMCard", "D"); break;
            case CHECK : pc = "C"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;
            case CUSTOM3 : pc = "K"; break;
            case CUSTOM4 : pc = "L"; break;

            case NRASCASH :  pc = params.get("NRASCASH", "P"); break;
            case NRASCHECKS :  pc = params.get("NRASCHECKS", "s"); break;
            case NRAST :  pc = params.get("NRAST", "m"); break;
            case NRASOT :  pc = params.get("NRASOT", "n"); break;
            case NRASP :  pc = params.get("NRASP", "o"); break;
            case NRASSELF :  pc = params.get("NRASSELF", "p"); break;
            case NRASDMG :  pc = params.get("NRASDMG", "q"); break;
            case NRASCARDS :  pc = params.get("NRASCARDS", "D"); break;
            case NRASW :  pc = params.get("NRASW", "r"); break;
            case NRASR1 :  pc = params.get("NRASR1", "I"); break;
            case NRASR2 :  pc = params.get("NRASR2", "J"); break;
        }
        return pc;
    }

    @Override
    protected String revTypeChar(fiscalCheckRevType revType) {
        String rt = "0";
        switch (revType) {
            case OP_ERROR :
                rt = "E";
                break;
            case RETURN_CLAIM :
                rt = "R";
                break;
            case REDUCTION :
                rt = "T";
                break;
        }
        return rt;
    }
    
    protected String[] dailyReportTypeToOptions(dailyReportType drType) {
        String[] options = new String[]{"2",""};
        
        switch (drType) {
            case Z :
               options[0] = "0";
               options[1] = "";
               break;
            case ZD :
               options[0] = "0";
               options[1] = "D";
               break;
            case X :
               options[0] = "2";
               options[1] = "";
               break;
            case XD :
               options[0] = "2";
               options[1] = "D";
               break;
        }
        return options;
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = FPCGeneralV10.getDefinedParams();
        params.getProperties().get("PMCash").setDefaultValue("P");
        params.getProperties().get("PMCash").setValue("P");
        params.getProperties().get("PMCard").setDefaultValue("D");
        params.getProperties().get("PMCard").setValue("D");
        params.getProperties().get("LWIDTH").setValue(46);
        params.getProperties().get("LWIDTHN").setValue(46);
        LinkedHashMap<String, String> payCodeMapList = new LinkedHashMap() {{
            put("P", "P - В БРОЙ");
            put("N", "N - Плащане с кредит");
            put("C", "C - Плащане чек");
            put("D", "D - Плащане с дебитна карта");
            put("I", "I - Програмируем тип плащане 1");
            put("J", "J - Програмируем тип плащане 2");
            put("K", "K - Програмируем тип плащане 3");
            put("L", "L - Програмируем тип плащане 4");
            put("i", "i - Програмируем тип плащане 1");
            put("j", "j - Програмируем тип плащане 2");
            put("k", "k - Програмируем тип плащане 3");
            put("l", "l - Програмируем тип плащане 4");
            put("m", "m - Талони");
            put("n", "n - Външни талони");
            put("o", "o - Амбалаж");
            put("p", "p - Вътрешно обслужване");
            put("q", "q - Повреди");
            put("r", "r - Банкови трансфери");
            put("s", "s - С чек");
        }};
        params.addGroups(
            new FPPropertyGroup("Начини на плащане", "Начини на плащане по спецификация на НАП") {{
                 addProperty(new FPProperty( // 0
                    String.class
                    , "NRASCASH", "0 НАП SCash", "НАП Спецификация SCash - В БРОЙ"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 1
                    String.class
                    , "NRASCHECKS", "1 НАП SChecks", "НАП Спецификация SChecks - С чек"
                    , "s"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 2
                    String.class
                    , "NRAST", "2 НАП SТ", "НАП Спецификация SТ - Талони"
                    , "m"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 3
                    String.class
                    , "NRASOT", "3 НАП SOТ", "НАП Спецификация SOТ - Сума по външни талони"
                    , "n"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));

                 addProperty(new FPProperty( // 4
                    String.class
                    , "NRASP", "4 НАП SP", "НАП Спецификация SP - Сума по амбалаж"
                    , "o"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 5
                    String.class
                    , "NRASSELF", "5 НАП SSelf", "НАП Спецификация SSelf - Сума по вътрешно обслужване"
                    , "p"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 6
                    String.class
                    , "NRASDMG", "6 НАП SDmg", "НАП Спецификация SDmg - Сума по повреди"
                    , "q"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 7
                    String.class
                    , "NRASCARDS", "7 НАП SCards", "НАП Спецификация SCards - Сума по кредитни/дебитни карти"
                    , "D"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 8
                    String.class
                    , "NRASW", "8 НАП SW", "НАП Спецификация SW - Сума по банкови трансфери"
                    , "r"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 9
                    String.class
                    , "NRASR1", "9 НАП SR1", "НАП Спецификация SR1 - Плащане НЗОК"
                    , "I"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 10
                    String.class
                    , "NRASR2", "10 НАП SR2", "НАП Спецификация SR2 - Резерв"
                    , "J"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
            }}
        );
        return params;
    }
    
}
