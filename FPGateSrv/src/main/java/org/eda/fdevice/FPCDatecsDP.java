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

import java.util.Date;
import static org.eda.fdevice.FPCBase.logger;
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
            case CASH : pc = "P"; break;
            case CREDIT : pc = "N"; break;
            case DEBIT_CARD : pc = "C"; break;
            case CHECK : pc = "D"; break;
            case CUSTOM1 : pc = "I"; break;
            case CUSTOM2 : pc = "J"; break;
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
    protected String dailyReportTypeToChar(dailyReportType drType) {
        if (dailyReportType.Z == drType)
            return "0";
        else
            return "2";
    }

    @Override
    public Date getLastFiscalCheckDate() {
        
        Date res = null;
        try {
            res = FP.cmdLastFiscalCheckDate();
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        return res;
    }
    
}
