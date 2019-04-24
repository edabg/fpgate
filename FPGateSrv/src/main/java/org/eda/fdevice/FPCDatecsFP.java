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
        params.getProperties().get("PMCard").setDefaultValue("D");
        params.getProperties().get("LWIDTH").setValue(46);
        params.getProperties().get("LWIDTHN").setValue(46);
        return params;
    }
    
}
