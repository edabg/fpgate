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
import org.eda.protocol.DeviceDatecsXV1;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "Datecs Fiscal devices via raw serial communication protocol v1X! Supported devices: FMP-350X, FMP-55X, FP-700X , FP-700XE, WP-500X, WP-50X, DP-25X, WP-25X, DP-150X, DP-05C"
    , usable = true
    , experimental = true
)
public class FPCDatecsX extends FPCGeneralV10 {

    public FPCDatecsX() throws Exception {
        super();
    }

    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
        this.FP = new DeviceDatecsXV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
        this.FP.open();
    }

    @Override
    protected boolean incrementDocNumWhenIsOpen() {
        return false;
    }
    
    @Override
    protected String paymenTypeChar(paymentTypes payType) {
        /*		
        PaidMode - Type of payment;
        • '0' - cash;
        • '1' - credit card;
        • '2' - debit card;
        • '3' - other pay#3
        • '4' - other pay#4
        • '5' - other pay#5
        */        
        String pc = "";
        switch (payType) {
            case CASH : pc = params.get("PMCache", "0"); break;
            case CREDIT : pc = "1"; break;
            case CARD :  pc = params.get("PMCard", "1"); break;
            case CHECK : pc = "2"; break;
            case CUSTOM1 : pc = "2"; break;
            case CUSTOM2 : pc = "2"; break;

            case NRASCASH :  pc = params.get("NRASCASH", "0"); break;
            case NRASCHECKS :  pc = params.get("NRASCHECKS", "1"); break;
            case NRAST :  pc = params.get("NRAST", "3"); break;
            case NRASOT :  pc = params.get("NRASOT", "3"); break;
            case NRASP :  pc = params.get("NRASP", "3"); break;
            case NRASSELF :  pc = params.get("NRASSELF", "4"); break;
            case NRASDMG :  pc = params.get("NRASDMG", "4"); break;
            case NRASCARDS :  pc = params.get("NRASCARDS", "1"); break;
            case NRASW :  pc = params.get("NRASW", "5"); break;
            case NRASR1 :  pc = params.get("NRASR1", "5"); break;
            case NRASR2 :  pc = params.get("NRASR2", "5"); break;
        }
        return pc;
    }

    @Override
    protected String revTypeChar(fiscalCheckRevType revType) {
        String rt = "0";
        switch (revType) {
            case OP_ERROR :
                rt = "0";
                break;
            case RETURN_CLAIM :
                rt = "1";
                break;
            case REDUCTION :
                rt = "2";
                break;
        }
        return rt;
    }
    
    @Override
    protected String[] dailyReportTypeToOptions(dailyReportType drType) {
        String[] options = new String[]{"2",""};
        
        switch (drType) {
            case Z :
               options[0] = "Z";
               options[1] = "";
               break;
            case ZD :
               options[0] = "Z";
               options[1] = "D";
               break;
            case X :
               options[0] = "X";
               options[1] = "";
               break;
            case XD :
               options[0] = "X";
               options[1] = "D";
               break;
        }
        return options;
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = FPCGeneralV10.getDefinedParams();
        /*
        PaidMode - Type of payment;
        • '0' - cash;
        • '1' - credit card;
        • '2' - debit card;
        • '3' - other pay#3
        • '4' - other pay#4
        • '5' - other pay#5
        */
        LinkedHashMap<String, String> payCodeMapList = new LinkedHashMap() {{
            put("0", "0 - В БРОЙ");
            put("1", "1 - Кредитна карта");
            put("2", "2 - Дебитна карта");
            put("3", "3 - Друг 3");
            put("4", "4 - Друг 4");
            put("5", "5 - Друг 5");
        }};
        
        params.getProperties().get("PMCash").setRule(new FPPropertyRule<>(null, null, true, payCodeMapList));
        params.getProperties().get("PMCash").setDefaultValue("0");
        params.getProperties().get("PMCash").setValue("0");
        
        params.getProperties().get("PMCard").setRule(new FPPropertyRule<>(null, null, true, payCodeMapList));
        params.getProperties().get("PMCard").setDefaultValue("1");
        params.getProperties().get("PMCard").setValue("1");
        params.addGroups(
            new FPPropertyGroup("Начини на плащане", "Начини на плащане по спецификация на НАП") {{
                 addProperty(new FPProperty( // 0
                    String.class
                    , "NRASCASH", "0 НАП SCash", "НАП Спецификация SCash - В БРОЙ"
                    , "0"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 1
                    String.class
                    , "NRASCHECKS", "1 НАП SChecks", "НАП Спецификация SChecks - С чек"
                    , "1"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 2
                    String.class
                    , "NRAST", "2 НАП SТ", "НАП Спецификация SТ - Талони"
                    , "3"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 3
                    String.class
                    , "NRASOT", "3 НАП SOТ", "НАП Спецификация SOТ - Сума по външни талони"
                    , "3"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 4
                    String.class
                    , "NRASP", "4 НАП SP", "НАП Спецификация SP - Сума по амбалаж"
                    , "3"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 5
                    String.class
                    , "NRASSELF", "5 НАП SSelf", "НАП Спецификация SSelf - Сума по вътрешно обслужване"
                    , "4"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 6
                    String.class
                    , "NRASDMG", "6 НАП SDmg", "НАП Спецификация SDmg - Сума по повреди"
                    , "4"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 7
                    String.class
                    , "NRASCARDS", "7 НАП SCards", "НАП Спецификация SCards - Сума по кредитни/дебитни карти"
                    , "1"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 8
                    String.class
                    , "NRASW", "8 НАП SW", "НАП Спецификация SW - Сума по банкови трансфери"
                    , "5"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 9
                    String.class
                    , "NRASR1", "9 НАП SR1", "НАП Спецификация SR1 - Плащане НЗОК"
                    , "5"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 10
                    String.class
                    , "NRASR2", "10 НАП SR2", "НАП Спецификация SR2 - Резерв"
                    , "5"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
            }}
        );
        
        return params;
    }

}
