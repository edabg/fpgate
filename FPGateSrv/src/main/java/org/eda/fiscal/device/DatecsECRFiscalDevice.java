/*
 * Copyright (C) 2016 EDA Ltd.
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
package org.eda.fiscal.device;

import com.taliter.fiscal.device.icl.ICLFiscalDevice;
import com.taliter.fiscal.port.FiscalPort;

/**
 *
 * @author Dimitar Angelov
 */
public class DatecsECRFiscalDevice  extends ICLFiscalDevice {
    /** Opens fiscal check. (0x30) */
    public static final int CMD_OPEN_FISCAL_CHECK            = 0x30;

    /** Closes the fiscal check. (0x48) */
    public static final int CMD_CLOSE_FISCAL_CHECK           = 0x38;

    /** Prints fiscal text. (0x36) */
    public static final int CMD_PRINT_FISCAL_TEXT            = 0x36;

    /** Register Sell. (0x31) */
    public static final int CMD_SELL                         = 0x31;

    /** Cancel the fiscal check. (0x3C) */
    public static final int CMD_CANCEL_FISCAL_CHECK          = 0x3C;
        
    /** Print duplicate check. (0x6d) */
    public static final int CMD_PRINT_CHECK_DUBLICATE        = 0x6d;
        
    /** Opens non-fiscal check. (0x26) */
    public static final int CMD_OPEN_NONFISCAL_CHECK         = 0x26;

    /** Closes the non-fiscal check. (0x27) */
    public static final int CMD_CLOSE_NONFISCAL_CHECK        = 0x27;
        
    /** Prints non-fiscal text. (0x2a) */
    public static final int CMD_PRINT_NONFISCAL_TEXT         = 0x2a;
        
    /** Subtotal. (0x33) */
    public static final int CMD_SUBTOTAL                     = 0x33;
        
    /** Total. (0x35) */
    public static final int CMD_TOTAL                        = 0x35;
        
    /** Information about the current check. (0x67) */
    public static final int CMD_CURRENT_CHECK_INFO           = 0x67;
        
    /** Information about the last saved daily report in the fiscal device. (0x40) */
    public static final int CMD_LAST_FISCAL_RECORD           = 0x40;

    /** Prints the diagnostic information about the fiscal device. (0x47) */
    public static final int CMD_PRINT_DIAGNOSTIC_INFO        = 0x47;
        
    /** The serial number of last printed document. (0x71) */
    public static final int CMD_LAST_DOC_NUM                 = 0x71;
        
    /** Fiscal device status. (0x4a) */
    public static final int CMD_PRINTER_STATUS               = 0x4a;

    /** Diagnostic information about the fiscal device. (0x5a) */
    public static final int CMD_DIAGNOSTIC_INFO              = 0x5a;
        
    /** Fiscal device's date and time. (0x3e) */
    public static final int CMD_GET_DATETIME                = 0x3e;

    /** Sets the date and time of fiscal device. (0x3d) */
    public static final int CMD_SET_DATETIME                 = 0x3d;
        
    /** Prints daily report. (0x45) */
    public static final int CMD_REPORT_DAILY                 = 0x45;
        
    /** Prints report by start and end date. (0x5e) */
    public static final int CMD_REPORT_BY_DATE               = 0x5e;
        
    /** Prints short report by start and end date. (0x4f) */
    public static final int CMD_REPORT_BY_DATE_SHORT         = 0x4f;
        
    /** Moves the paper. (0x2c) */
    public static final int CMD_PAPER_FEED                   = 0x2c;

    /** Sets operator's name. (0x66) */
    public static final int CMD_SET_OPERATOR                 = 0x66;

    /** Cash In/Cash Out operation (0x46) */
    public static final int CMD_CASH_INOUT                   = 0x46;

    /**
     * Creates an instance of DaisyFiscalDevice.
     * @param port The port in which the fiscal device is connected.
     * @param timeout The time to execute the request. In milliseconds.
     * @param maxTries The number of tries to execute the request.
     * @param encoding Fiscal device's encoding.
     */
    public DatecsECRFiscalDevice(FiscalPort port, int timeout, int maxTries, String encoding) {
        super(port, timeout, maxTries, encoding);
        
        setCMDPrinterStatus(CMD_PRINTER_STATUS);
    }
    
}
