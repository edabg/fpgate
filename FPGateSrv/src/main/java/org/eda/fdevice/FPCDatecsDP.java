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

import java.io.IOException;
import java.util.LinkedHashMap;
import org.eda.protocol.DeviceDatecsDPV1;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "Datecs Fiscal devices via raw serial communication protocol v1! Supported devices: DP-05, DP-25, DP-35, WP-50, DP-150"
)
public class FPCDatecsDP extends FPCGeneralV10 {

    public FPCDatecsDP() throws Exception {
        super();
    }

    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
        this.FP = new DeviceDatecsDPV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
        this.FP.open();
    }
    
    @Override
    protected String paymenTypeChar(paymentTypes payType) {
        /*		
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ - Плащане с кредит;
        ‘C’ - Плащане с дебитна карта;
        ‘D’ - Плащане с НЗОК;
        ‘I’ - Плащане с ваучер;
        ‘J’ - Плащане с купон;
        */        
        String pc = "";
        switch (payType) {
            case CASH : pc = params.get("PMCache", "P"); break;
            case CREDIT : pc = "N"; break;
            case CARD :  pc = params.get("PMCard", "C"); break;
            case CHECK : pc = "D"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;

            case NRASCASH :  pc = params.get("NRASCASH", "P"); break;
            case NRASCHECKS :  pc = params.get("NRASCHECKS", "P"); break;
            case NRAST :  pc = params.get("NRAST", "I"); break;
            case NRASOT :  pc = params.get("NRASOT", "J"); break;
            case NRASP :  pc = params.get("NRASP", "P"); break;
            case NRASSELF :  pc = params.get("NRASSELF", "P"); break;
            case NRASDMG :  pc = params.get("NRASDMG", "P"); break;
            case NRASCARDS :  pc = params.get("NRASCARDS", "C"); break;
            case NRASW :  pc = params.get("NRASW", "P"); break;
            case NRASR1 :  pc = params.get("NRASR1", "D"); break;
            case NRASR2 :  pc = params.get("NRASR2", "P"); break;
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
        params.getProperties().get("PMCard").setDefaultValue("C");
        params.getProperties().get("PMCard").setValue("C");
        /*
        Незадължителен код, указващ начина на плащане. Може да има следните стойности:
        ‘P’ - Плащане в брой (по подразбиране);
        ‘N’ - Плащане с кредит;
        ‘C’ - Плащане с дебитна карта;
        ‘D’ - Плащане с НЗОК;
        ‘I’ - Плащане с ваучер;
        ‘J’ - Плащане с купон;        
        */
        LinkedHashMap<String, String> payCodeMapList = new LinkedHashMap() {{
            put("P", "P - В БРОЙ");
            put("N", "N - Плащане с кредит");
            put("C", "C - Плащане с дебитна карта");
            put("D", "D - Плащане с НЗОК");
            put("I", "I - Плащане с ваучер");
            put("J", "J - Плащане с купон");
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
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 2
                    String.class
                    , "NRAST", "2 НАП SТ", "НАП Спецификация SТ - Талони"
                    , "I"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 3
                    String.class
                    , "NRASOT", "3 НАП SOТ", "НАП Спецификация SOТ - Сума по външни талони"
                    , "J"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 4
                    String.class
                    , "NRASP", "4 НАП SP", "НАП Спецификация SP - Сума по амбалаж"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 5
                    String.class
                    , "NRASSELF", "5 НАП SSelf", "НАП Спецификация SSelf - Сума по вътрешно обслужване"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 6
                    String.class
                    , "NRASDMG", "6 НАП SDmg", "НАП Спецификация SDmg - Сума по повреди"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 7
                    String.class
                    , "NRASCARDS", "7 НАП SCards", "НАП Спецификация SCards - Сума по кредитни/дебитни карти"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 8
                    String.class
                    , "NRASW", "8 НАП SW", "НАП Спецификация SW - Сума по банкови трансфери"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 9
                    String.class
                    , "NRASR1", "9 НАП SR1", "НАП Спецификация SR1 - Плащане НЗОК"
                    , "D"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 10
                    String.class
                    , "NRASR2", "10 НАП SR2", "НАП Спецификация SR2 - Резерв"
                    , "D"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
            }}
        );
        
        return params;
    }

}
