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
        return params;
    }
    
}
