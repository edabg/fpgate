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
import org.eda.protocol.DeviceDaisyV1;

/**
 *
 * @author Dimitar Angelov
 */
@FiscalDevice(
    description = "Daisy Fiscal devices via raw serial communication protocol v1! Tested devices: Daisy Compact M"
)
public class FPCDaisy extends FPCGeneralV10 {

    public FPCDaisy() throws Exception {
        super();
    }

    @Override
    public void init() throws Exception, FPException {
        lastCommand = "Init";
        this.FP = new DeviceDaisyV1(getParam("COM"), getParamInt("BaudRate"), getParamInt("PortReadTimeout"), getParamInt("PortWriteTimeout"));
        this.FP.open();
    }
    
    @Override
    protected String paymenTypeChar(paymentTypes payType) {
        /*		
        TODO: Да се провери, кое е плащането с дебитна карта
        “P” – "В БРОЙ"; 
        “N” – Плащане 1 
        “C” – Плащане 2 
        “D” или "U" – Плащане 3 
        “B” или "E" – Плащане 4
        */        
        String pc = "";
        switch (payType) {
            case CASH : pc = params.get("PMCache", "P"); break;
            case CREDIT : pc = "N"; break;
            case CARD :  pc = params.get("PMCard", "C"); break;
//            case CHECK : pc = "D"; break;
            case CUSTOM1 : pc = "D"; break;
            case CUSTOM2 : pc = "B"; break;
            
            case NRASCASH :  pc = params.get("NRASCASH", "P"); break;
            case NRASCHECKS :  pc = params.get("NRASCHECKS", "C"); break;
            case NRAST :  pc = params.get("NRAST", "C"); break;
            case NRASOT :  pc = params.get("NRASOT", "C"); break;
            case NRASP :  pc = params.get("NRASP", "C"); break;
            case NRASSELF :  pc = params.get("NRASSELF", "C"); break;
            case NRASDMG :  pc = params.get("NRASDMG", "C"); break;
            case NRASCARDS :  pc = params.get("NRASCARDS", "C"); break;
            case NRASW :  pc = params.get("NRASW", "C"); break;
            case NRASR1 :  pc = params.get("NRASR1", "C"); break;
            case NRASR2 :  pc = params.get("NRASR2", "C"); break;
        }
        return pc;
    }

    @Override
    protected String revTypeChar(fiscalCheckRevType revType) {
        String rt = "0";
        switch (revType) {
            case OP_ERROR :
                rt = "1";
                break;
            case RETURN_CLAIM :
                rt = "0";
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
               options[0] = "8";
               options[1] = "";
               break;
            case X :
               options[0] = "2";
               options[1] = "";
               break;
            case XD :
               options[0] = "9";
               options[1] = "";
               break;
        }
        return options;
    }

    public static FPParams getDefinedParams() throws Exception {
        FPParams params = FPCGeneralV10.getDefinedParams();
        params.getProperties().get("PMCash").setDefaultValue("P");
        params.getProperties().get("PMCard").setDefaultValue("C");
        LinkedHashMap<String, String> payCodeMapList = new LinkedHashMap() {{
            put("P", "0 P - В БРОЙ");
            put("N", "1 N - Плащане 1");
            put("C", "2 C - Плащане 2");
            put("D", "3 D - Плащане 3");
            put("U", "3 U - Плащане 3");
            put("B", "4 B - Плащане 4");
            put("E", "4 E - Плащане 4");
        }};
        params.addGroups(
            new FPPropertyGroup("Начини на плащане", "Начини на плащане по спецификация на НАП") {{
                 addProperty(new FPProperty( // 0
                    String.class
                    , "NRASCASH", "НАП SCash", "НАП Спецификация SCash - В БРОЙ"
                    , "P"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 1
                    String.class
                    , "NRASCHECKS", "НАП SChecks", "НАП Спецификация SChecks - С чек"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 2
                    String.class
                    , "NRAST", "НАП SТ", "НАП Спецификация SТ - Талони"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 3
                    String.class
                    , "NRASOT", "НАП SOТ", "НАП Спецификация SOТ - Сума по външни талони"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 4
                    String.class
                    , "NRASP", "НАП SP", "НАП Спецификация SP - Сума по амбалаж"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 5
                    String.class
                    , "NRASSELF", "НАП SSelf", "НАП Спецификация SSelf - Сума по вътрешно обслужване"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 6
                    String.class
                    , "NRASDMG", "НАП SDmg", "НАП Спецификация SDmg - Сума по повреди"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 7
                    String.class
                    , "NRASCARDS", "НАП SCards", "НАП Спецификация SCards - Сума по кредитни/дебитни карти"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 8
                    String.class
                    , "NRASW", "НАП SW", "НАП Спецификация SW - Сума по банкови трансфери"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 9
                    String.class
                    , "NRASR1", "НАП SR1", "НАП Спецификация SR1 - Плащане НЗОК"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
                 addProperty(new FPProperty( // 10
                    String.class
                    , "NRASR2", "НАП SR2", "НАП Спецификация SR2 - Резерв"
                    , "C"
                    , new FPPropertyRule<>(null, null, true, payCodeMapList)
                 ));
            }}
        );
        return params;
    }
    
}
