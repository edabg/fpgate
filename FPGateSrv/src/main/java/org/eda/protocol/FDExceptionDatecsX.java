/*
 * Copyright (C) 2020 EDA Ltd.
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

import java.util.HashMap;

/**
 *
 * @author Dimitar Angelov
 */
public class FDExceptionDatecsX extends FDException {
    
    protected static class ErrorInfo {
        long mCode;
        String mStrCode;
        String mMessage;
        String mLocalizedMessage;
        
        public ErrorInfo(long code, String strCode, String message, String localizedMessage) {
            mCode = code;
            mStrCode = strCode;
            mMessage = message;
            mLocalizedMessage = localizedMessage;
        }

        public ErrorInfo(long code, String strCode, String message) {
            this(code, strCode, message, message);
        }
    }
    
    protected static final HashMap<Long, ErrorInfo> errorList;
    
    static {
        errorList = new HashMap<>();
        errorList.put(-100001L, new ErrorInfo(-100001L, "ERR_IO", "General error in fiscal device: In - out error( cannot read or write )"));
        errorList.put(-100002L, new ErrorInfo(-100002L, "ERR_CHECKSUM", "General error in fiscal device: Wrong checksum"));
        errorList.put(-100003L, new ErrorInfo(-100003L, "ERR_END_OF_DATA", "General error in fiscal device: No more data"));
        errorList.put(-100004L, new ErrorInfo(-100004L, "ERR_NOTFOUND", "General error in fiscal device: The element is not found"));
        errorList.put(-100005L, new ErrorInfo(-100005L, "ERR_NO_RECORDS", "General error in fiscal device: There are no records found"));
        errorList.put(-100006L, new ErrorInfo(-100006L, "ERR_ABORTED", "General error in fiscal device: The operation is aborted"));
        errorList.put(-100007L, new ErrorInfo(-100007L, "ERR_WRONG_MODE", "Wrong mode( standart, training...) is selected."));
        errorList.put(-100008L, new ErrorInfo(-100008L, "ERR_NOT_READY", "General error in fiscal device: Device is not ready"));
        errorList.put(-100009L, new ErrorInfo(-100009L, "ERR_NOTHING_TO_PRINT", "General error in fiscal device: Nothing to print"));
        errorList.put(-100100L, new ErrorInfo(-100100L, "ERR_FM_BUSY", "Fiscal memory error: Fiscal memory is busy"));
        errorList.put(-100101L, new ErrorInfo(-100101L, "ERR_FM_FAILURE", "Fiscal memory error: Fiscal memory failure. Could not read or write"));
        errorList.put(-100102L, new ErrorInfo(-100102L, "ERR_FM_WRITE_PROTECTED", "Fiscal memory error: Forbidden write in fiscal memory"));
        errorList.put(-100103L, new ErrorInfo(-100103L, "ERR_FM_WRONG_ADDRESS", "Fiscal memory error: Wrong address in fiscal memory"));
        errorList.put(-100104L, new ErrorInfo(-100104L, "ERR_FM_WRONG_SIZE", "Fiscal memory error: Wrong size in fiscal memory"));
        errorList.put(-100105L, new ErrorInfo(-100105L, "ERR_FM_NOT_CONNECTED", "Fiscal memory error: Fiscal memory is not connected"));
        errorList.put(-100106L, new ErrorInfo(-100106L, "ERR_FM_WRONG_CHECK_SUM", "Fiscal memory error: Wrong checksum in fiscal memory( invalid data )"));
        errorList.put(-100107L, new ErrorInfo(-100107L, "ERR_FM_BLOCK_IS_EMPTY", "Fiscal memory error: Empty block in fiscal memory"));
        errorList.put(-100108L, new ErrorInfo(-100108L, "ERR_FM_MAX_NUMBER", "Fiscal memory error: Maximum number of block in fiscal memory"));
        errorList.put(-100109L, new ErrorInfo(-100109L, "ERR_FM_WRONG_RANGE", "Fiscal memory error: Wrong range in fiscal memory"));
        errorList.put(-100110L, new ErrorInfo(-100110L, "ERR_FM_EMPTY_RANGE", "Fiscal memory error: Empty range in fiscal memory"));
        errorList.put(-100111L, new ErrorInfo(-100111L, "ERR_FM_NEW_MODULE", "Fiscal memory error: New module in fiscal memory"));
        errorList.put(-100112L, new ErrorInfo(-100112L, "ERR_FM_NOT_EMPTY", "Fiscal memory error: Fiscal memory is not empty"));
        errorList.put(-100113L, new ErrorInfo(-100113L, "ERR_FM_NOT_EQUAL", "Fiscal memory error: Fiscal memory is not equal"));
        errorList.put(-100114L, new ErrorInfo(-100114L, "ERR_FM_FULL", "Fiscal memory error: Fiscal memory is full"));
        errorList.put(-100115L, new ErrorInfo(-100115L, "ERR_FM_NEED_UPDATE", "Fiscal memory error: Fiscal memory needs update"));
        errorList.put(-100116L, new ErrorInfo(-100116L, "ERR_FM_BLOCKED", "Fiscal memory error: Fiscal memory is blocked"));
        errorList.put(-100400L, new ErrorInfo(-100400L, "ERR_LTP_VCCERR", "Line thermal printer mechanism error: Power supply error (3,3 V)"));
        errorList.put(-100401L, new ErrorInfo(-100401L, "ERR_LTP_SVPERR", "Line thermal printer mechanism error: Power supply error (24V or 8V)"));
        errorList.put(-100402L, new ErrorInfo(-100402L, "ERR_LTP_STHERR", "Line thermal printer mechanism error: Head overheating"));
        errorList.put(-100403L, new ErrorInfo(-100403L, "ERR_LTP_PESENS", "Line thermal printer mechanism error: Paper end"));
        errorList.put(-100404L, new ErrorInfo(-100404L, "ERR_LTP_HDSENS", "Line thermal printer mechanism error: Cover is open"));
        errorList.put(-100405L, new ErrorInfo(-100405L, "ERR_LTP_NESENS", "Line thermal printer mechanism error: Near paper end"));
        errorList.put(-100406L, new ErrorInfo(-100406L, "ERR_LTP_MKSENS", "Line thermal printer mechanism error: Mark sensor - not used"));
        errorList.put(-100407L, new ErrorInfo(-100407L, "ERR_LTP_CUTERR", "Line thermal printer mechanism error: Cutter error"));
        errorList.put(-100408L, new ErrorInfo(-100408L, "ERR_LTP_PR_ERR", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100409L, new ErrorInfo(-100409L, "ERR_LTP_PR_BUSY", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100410L, new ErrorInfo(-100410L, "ERR_LTP_BZLPDEC", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100411L, new ErrorInfo(-100411L, "ERR_LTP_BZLCLMP", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100412L, new ErrorInfo(-100412L, "ERR_LTP_CHARGE_MODE", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100413L, new ErrorInfo(-100413L, "ERR_LTP_INZERR_MODE", "Line thermal printer mechanism error: Not used"));
        errorList.put(-100414L, new ErrorInfo(-100414L, "ERR_LTP_MOTOR_OVERRUN", "Printer on time is overrun."));
        errorList.put(-100500L, new ErrorInfo(-100500L, "ERR_PROGRAM_SELF_CHECK_ERROR", "System error: Memory structure error"));
        errorList.put(-100501L, new ErrorInfo(-100501L, "ERR_SRAM_ERROR", "System error: Error in RAM"));
        errorList.put(-100502L, new ErrorInfo(-100502L, "ERR_FLASH_ERROR", "System error: Flash memory error"));
        errorList.put(-100503L, new ErrorInfo(-100503L, "ERR_SDCARD_ERROR", "System error: SD card error"));
        errorList.put(-100504L, new ErrorInfo(-100504L, "ERR_INVALID_MSG_FILE", "System error: Invalid message file"));
        errorList.put(-100505L, new ErrorInfo(-100505L, "ERR_FM_ERROR", "System error: Fiscal memory error( could not write or read )"));
        errorList.put(-100506L, new ErrorInfo(-100506L, "ERR_NO_RAM_BATTERY", "System error: No RAM battery"));
        errorList.put(-100507L, new ErrorInfo(-100507L, "ERR_SAM_ERROR", "System error: SAM module error"));
        errorList.put(-100508L, new ErrorInfo(-100508L, "ERR_RTC_ERROR", "System error: Real time clock error"));
        errorList.put(-100509L, new ErrorInfo(-100509L, "ERR_PROGRAM_EXRAM_CHECK_ERROR", "System error: Memory error"));
        errorList.put(-100510L, new ErrorInfo(-100510L, "ERR_SDCARD_WRONG_SIZE", "System error: The size of SD card is wrong."));
        errorList.put(-100511L, new ErrorInfo(-100511L, "ERR_TPM_ERROR", "System error: TPM module error"));
        errorList.put(-101000L, new ErrorInfo(-101000L, "ERR_NO_HEAP_MEMORY", "Common logical error: No heap memory( cannot allocate memory for operation )"));
        errorList.put(-101001L, new ErrorInfo(-101001L, "ERR_FILE_MANIPULATE", "Common logical error: File manipulate error"));
        errorList.put(-101003L, new ErrorInfo(-101003L, "ERR_REJECTED", "Common logical error: Operation is rejected"));
        errorList.put(-101004L, new ErrorInfo(-101004L, "ERR_BAD_INPUT", "Common logical error: Bad input. Some of the data or parameters are incorrect"));
        errorList.put(-101005L, new ErrorInfo(-101005L, "ERR_IAP", "Common logical error: In Application Programming error"));
        errorList.put(-101006L, new ErrorInfo(-101006L, "ERR_NOT_POSSIBLE", "Common logical error: The execution of the operation is not possible"));
        errorList.put(-101007L, new ErrorInfo(-101007L, "ERR_TMOUT", "Common logical error: Timeout. The time for waiting execution is out"));
        errorList.put(-101007L, new ErrorInfo(-101007L, "ERR_TIMEOUT", "Common logical error: Timeout. The time for waiting execution is out"));
        errorList.put(-101008L, new ErrorInfo(-101008L, "ERR_INVALID_TIME", "Common logical error: Invalid time"));
        errorList.put(-101009L, new ErrorInfo(-101009L, "ERR_CANCELLED", "Common logical error: The operation is cancelled"));
        errorList.put(-101010L, new ErrorInfo(-101010L, "ERR_INVALID_FORMAT", "Common logical error: Invalid format"));
        errorList.put(-101011L, new ErrorInfo(-101011L, "ERR_INVALID_DATA", "Common logical error: Invalid data"));
        errorList.put(-101012L, new ErrorInfo(-101012L, "ERR_PARSE_ERROR", "Common logical error: Data parsing error"));
        errorList.put(-101013L, new ErrorInfo(-101013L, "ERR_HARDWARE_CONFIGURATION", "Common logical error: Hardware configuration error"));
        errorList.put(-101014L, new ErrorInfo(-101014L, "ERR_ACCESS_DENIED", "ERR_ACCESS_DENIED"));
        errorList.put(-101015L, new ErrorInfo(-101015L, "ERR_BAD_DATA_LENGTH", "Wrong data length"));
        errorList.put(-101016L, new ErrorInfo(-101016L, "ERR_VERIFY_Z", "Error during verification of Z reports"));
        errorList.put(-101017L, new ErrorInfo(-101017L, "ERR_NO_PERMISSION", "Common logical error: No permission"));
        errorList.put(-101500L, new ErrorInfo(-101500L, "ERR_NO_UPDATE", "Update error: No update. The device is up to date"));
        errorList.put(-101501L, new ErrorInfo(-101501L, "ERR_UPDATE_IN_PROGRESS", "Update error: Update is already in progress"));
        errorList.put(-102000L, new ErrorInfo(-102000L, "ERR_LOW_BATTERY", "Battery error: Low battery"));
        errorList.put(-102001L, new ErrorInfo(-102001L, "ERR_LOW_BATTERY_WARNING", "Battery error: Low battery warning"));
        errorList.put(-102002L, new ErrorInfo(-102002L, "ERR_OPER_WRONG_PASSWORD", "Operator error: Wrong operator password"));
        errorList.put(-102003L, new ErrorInfo(-102003L, "ERR_IDNUMBER_IS_EMPTY", "ECR error: ID number is empty"));
        errorList.put(-102004L, new ErrorInfo(-102004L, "ERR_NOT_FOUND_BLUETOOTH", "Bluetooth error: Bluetooth is not found"));
        errorList.put(-102005L, new ErrorInfo(-102005L, "ERR_DISPLAY_DISCONNECTED", "Display error: Display is not connected"));
        errorList.put(-102006L, new ErrorInfo(-102006L, "ERR_PRINTER_DISCONNECTED", "Printer error: Printer is not connected                                                                                                       "));
        errorList.put(-102007L, new ErrorInfo(-102007L, "ERR_SD_NOT_PRESENT", "SD card error: SD card not present"));
        errorList.put(-102008L, new ErrorInfo(-102008L, "ERR_SD2_NOT_PRESENT", "SD card error: SD2 card not present"));
        errorList.put(-102009L, new ErrorInfo(-102009L, "ERR_VAT_RATES_ARE_EMPTY", "ECR error: VAT rates is not set."));
        errorList.put(-102010L, new ErrorInfo(-102010L, "ERR_HEADER_IS_EMPTY", "ECR error: Header lines are empty."));
        errorList.put(-102011L, new ErrorInfo(-102011L, "ERR_ZDDS_NUM_IS_EMPTY", "User is registered by VAT, but number of the user is not entered."));
        errorList.put(-102012L, new ErrorInfo(-102012L, "ERR_FMNUMBER_IS_EMPTY", "ECR error: FM number is empty"));
        errorList.put(-102013L, new ErrorInfo(-102013L, "ERR_SERVICEMAN_NAME_IS_EMPTY", "ECR error: Serviceman name is empty"));
        errorList.put(-102014L, new ErrorInfo(-102014L, "ERR_SERVICEMAN_ID_IS_EMPTY", "ECR error: Serviceman ID is empty"));
        errorList.put(-102015L, new ErrorInfo(-102015L, "ERR_TAXOFFICE_ID_IS_EMPTY", "ECR error: Tax office ID is empty!"));
        errorList.put(-102016L, new ErrorInfo(-102016L, "ERR_WRONG_FORMAT", "ECR error: Wrong format"));
        errorList.put(-102017L, new ErrorInfo(-102017L, "ERR_TAXNUMBER_IS_EMPTY", "ECR error: TAX number is empty"));
        errorList.put(-102018L, new ErrorInfo(-102018L, "ERR_WRONG_IDNUMBER", "ECR error: ID number is wrong"));
        errorList.put(-102019L, new ErrorInfo(-102019L, "ERR_DATETIME_EARLIER_THAN_PREV_Z", "ECR error: Date and time are earlier than date and time of previous Z report."));
        errorList.put(-102020L, new ErrorInfo(-102020L, "ERR_NEED_SOFTWARE_PASSWORD", "ECR error: The software password is not entered"));
        errorList.put(-103000L, new ErrorInfo(-103000L, "ERR_PLUDB_NOT_FOUND", "PLU database error: PLU database is not found"));
        errorList.put(-103001L, new ErrorInfo(-103001L, "ERR_PLUDB_PLUCODE_EXISTS", "PLU database error: PLU code already exists"));
        errorList.put(-103002L, new ErrorInfo(-103002L, "ERR_PLUDB_BARCODE_EXISTS", "PLU database error: Barcode already exists"));
        errorList.put(-103003L, new ErrorInfo(-103003L, "ERR_PLUDB_FULL", "PLU database error: PLU database is full"));
        errorList.put(-103004L, new ErrorInfo(-103004L, "ERR_P_HAVE_TURNOVER", "PLU database error: PLU has turnover"));
        errorList.put(-103005L, new ErrorInfo(-103005L, "ERR_PLUDB_NAME_EXISTS", "PLU database error: In the PLU base has an article with same name."));
        errorList.put(-103006L, new ErrorInfo(-103006L, "ERR_PLUDB_NAMES_NOT_UNIQUE", "PLU database error: PLU name is not unique."));
        errorList.put(-103007L, new ErrorInfo(-103007L, "ERR_PLUDB_FORMAT_INCOMPATIBLE", "PLU database error: Database format is not compatible."));
        errorList.put(-103008L, new ErrorInfo(-103008L, "ERR_PLUDB_CAN_NOT_OPEN", "Can't open the PLU database file"));
        errorList.put(-104000L, new ErrorInfo(-104000L, "ERR_NEED_Z_REPORT", "Service operation error: Z report is needed for this operation"));
        errorList.put(-104001L, new ErrorInfo(-104001L, "ERR_NEED_SERVICE_JUMPER", "Service operation error: Service jumper is needed for this operation"));
        errorList.put(-104002L, new ErrorInfo(-104002L, "ERR_NEED_SERVICE_PASSWORD", "Service operation error: Service password is needed for this operation"));
        errorList.put(-104003L, new ErrorInfo(-104003L, "ERR_FORBIDEN", "Service operation error: The operation is forbidden"));
        errorList.put(-104004L, new ErrorInfo(-104004L, "ERR_NEED_SERVICE_INTERVENTION", "Service operation error: Service intervention is needed"));
        errorList.put(-104005L, new ErrorInfo(-104005L, "ERR_NEED_ALL_CLEARING_REPORTS", "Service operation error: All clearing report is needed."));
        errorList.put(-104006L, new ErrorInfo(-104006L, "ERR_Z_REPORT_CLOSED", "Service operation error: Z report closed."));
        errorList.put(-104007L, new ErrorInfo(-104007L, "ERR_NEED_MONTH_REPORT", "Service operation error: Montly report needed."));
        errorList.put(-104008L, new ErrorInfo(-104008L, "ERR_NEED_YEAR_REPORT", "Service operation error: Year report needed."));
        errorList.put(-104009L, new ErrorInfo(-104009L, "ERR_NEED_BACKUP", "Service operation error: Backup needed."));
        errorList.put(-104010L, new ErrorInfo(-104010L, "ERR_NEED_ALL_PAIDOUT", "ERR_NEED_ALL_PAIDOUT"));
        errorList.put(-104011L, new ErrorInfo(-104011L, "ERR_NEED_OPERATOR_Z_REPORT", "Clearing report for operator is needed."));
        errorList.put(-104012L, new ErrorInfo(-104012L, "ERR_NEED_GROUP_Z_REPORT", "Clearing report for item group is needed."));
        errorList.put(-104013L, new ErrorInfo(-104013L, "ERR_NEED_VAT_CHANGES", "VAT changes is needed."));
        errorList.put(-105000L, new ErrorInfo(-105000L, "ERR_EJ_NO_RECORDS", "EJ error: No records in EJ"));
        errorList.put(-105001L, new ErrorInfo(-105001L, "ERR_CANNOT_ADD_TO_EJ", "EJ error: Cannot add to EJ"));
        errorList.put(-105002L, new ErrorInfo(-105002L, "ERR_EJ_WRONG_MAC_RECORD", "EJ error: SAM module signature error"));
        errorList.put(-105003L, new ErrorInfo(-105003L, "ERR_EJ_IMMPOSSIBLE_TO_CHK_MAC_RECORD", "EJ error: Signature key version is changed ->impossible check"));
        errorList.put(-105004L, new ErrorInfo(-105004L, "ERR_EJ_BAD_RECORDS", "EJ error: Bad record in EJ"));
        errorList.put(-105005L, new ErrorInfo(-105005L, "ERR_EJ_CAN_NOT_GENERATE_MAC", "EJ error: Generate signature error( cannoct generate signature )"));
        errorList.put(-105006L, new ErrorInfo(-105006L, "ERR_EJ_WRONG_TYPE_TO_SIGN", "EJ error: Wrong type of document to sign"));
        errorList.put(-105007L, new ErrorInfo(-105007L, "ERR_EJ_ALREADY_SIGNED", "EJ error: Document is already signed"));
        errorList.put(-105008L, new ErrorInfo(-105008L, "ERR_EJ_NOT_FROM_THIS_DEVICE", "EJ error: EJ is not from this device"));
        errorList.put(-105009L, new ErrorInfo(-105009L, "ERR_EJ_NEAR_FULL", "EJ error: EJ is almost full"));
        errorList.put(-105010L, new ErrorInfo(-105010L, "ERR_EJ_FULL", "EJ error: EJ is full"));
        errorList.put(-105011L, new ErrorInfo(-105011L, "ERR_EJ_WRONG_FORMAT", "EJ error: Wrong format of EJ"));
        errorList.put(-105012L, new ErrorInfo(-105012L, "ERR_EJ_NOT_READY", "The electronic journal is not ready."));
        errorList.put(-105013L, new ErrorInfo(-105013L, "ERR_EJ_NEED_NEW", "Error in EJ structure. Create new one."));
        errorList.put(-106000L, new ErrorInfo(-106000L, "ERR_R_FIRM_NOTEXIST", "Client database error: Firm does not exist"));
        errorList.put(-106001L, new ErrorInfo(-106001L, "ERR_FIRMDB_FIRMCODE_EXISTS", "Client database error: Firmcode already exists"));
        errorList.put(-106002L, new ErrorInfo(-106002L, "ERR_FIRMDB_EIK_EXISTS", "Client database error: EIK already exists"));
        errorList.put(-106003L, new ErrorInfo(-106003L, "ERR_FIRMDB_FULL", "Client database error: Firm database is full"));
        errorList.put(-106004L, new ErrorInfo(-106004L, "ERR_FIRMDB_NOT_FOUND", "Client database error: Firm database is not found"));
        errorList.put(-107001L, new ErrorInfo(-107001L, "ERR_INVALID_CERTIFICATE", "Invalid certificate."));
        errorList.put(-107002L, new ErrorInfo(-107002L, "ERR_VALID_CERT_EXISTS", "Certificate exist."));
        errorList.put(-107003L, new ErrorInfo(-107003L, "ERR_CERT_UNPACKING_FAILED", "Certificate unpack failed."));
        errorList.put(-107004L, new ErrorInfo(-107004L, "ERR_CERT_WRONG_PASSWORD", "Wrong certificate password."));
        errorList.put(-107005L, new ErrorInfo(-107005L, "ERR_CERT_FILE_WRITE", "File write error."));
        errorList.put(-107006L, new ErrorInfo(-107006L, "ERR_CERT_FILE_READ", "File read error."));
        errorList.put(-107007L, new ErrorInfo(-107007L, "ERR_CERT_NOT_FOUND", "Certificate not found"));
        errorList.put(-108000L, new ErrorInfo(-108000L, "ERR_R_DISC_NOTEXIST", "Discount card database error: Discount card does not exist"));
        errorList.put(-108001L, new ErrorInfo(-108001L, "ERR_DISCDB_DISCCODE_EXISTS", "Discount card database error: Discount card already exists"));
        errorList.put(-108002L, new ErrorInfo(-108002L, "ERR_DISCDB_BARCODE_EXISTS", "Discount card database error: Barcode already exists"));
        errorList.put(-108003L, new ErrorInfo(-108003L, "ERR_DISCDB_FULL", "Discount card database error: Discount card database is full"));
        errorList.put(-108004L, new ErrorInfo(-108004L, "ERR_DISCDB_NOT_FOUND", "Discount card database error: Discount card not found"));
        errorList.put(-109981L, new ErrorInfo(-109981L, "ERR_SMARTCARD_NOT_PRESENT", "Smartcard error: No card in the holder."));
        errorList.put(-109982L, new ErrorInfo(-109982L, "ERR_SMARTCARD_CONFIG", "Smartcard error: Configuration failed"));
        errorList.put(-109983L, new ErrorInfo(-109983L, "ERR_SMARTCARD_COMMUNICATION", "Smartcard error: SmartCard communication error."));
        errorList.put(-109984L, new ErrorInfo(-109984L, "ERR_SMARTCARD_CARD_FAULT", "Smartcard error: Supply voltage drop, a VCC overcurrent detection or overheating."));
        errorList.put(-109985L, new ErrorInfo(-109985L, "ERR_SMARTCARD_MODULE_ERROR", "Smartcard error: Unexpected response from the applet."));
        errorList.put(-109986L, new ErrorInfo(-109986L, "ERR_SMARTCARD_WRONG_ID", "The ID of the smart card does not match the ID stored in the fiscal memory."));
        errorList.put(-110100L, new ErrorInfo(-110100L, "ERR_DEVICE_COMM_ERROR", "Device error: Communication error"));
        errorList.put(-110101L, new ErrorInfo(-110101L, "ERR_DEVICE_WRONG_STRUCT", "Device error: Wrong struct format"));
        errorList.put(-110102L, new ErrorInfo(-110102L, "ERR_DEVICE_STFLAG_ACTIVE", "Device error: ST flag is active"));
        errorList.put(-110103L, new ErrorInfo(-110103L, "ERR_DEVICE_INVALID_DATA", "Device error: Invalid data"));
        errorList.put(-110104L, new ErrorInfo(-110104L, "ERR_DEVICE_NOT_FISCALIZED", "Device error: Device is not fiscalized"));
        errorList.put(-110105L, new ErrorInfo(-110105L, "ERR_DEVICE_ALREADY_FISCALIZED", "Device error: Device is already fiscalized"));
        errorList.put(-110106L, new ErrorInfo(-110106L, "ERR_DEVICE_IN_SERVICE_MODE", "Device error: Device is in service mode"));
        errorList.put(-110107L, new ErrorInfo(-110107L, "ERR_DEVICE_PASSED_SERVICE_DATE", "Device error: Service date is passed"));
        errorList.put(-110108L, new ErrorInfo(-110108L, "ERR_DEVICE_DAY_IS_OPEN", "Device error: Day( shift ) is open"));
        errorList.put(-110109L, new ErrorInfo(-110109L, "ERR_DEVICE_DAY_IS_CLOSED", "Device error: Day( shift ) is closed"));
        errorList.put(-110110L, new ErrorInfo(-110110L, "ERR_DEVICE_WRONG_NUMBERS", "Device error: Z-report number and shift number are not equal"));
        errorList.put(-110111L, new ErrorInfo(-110111L, "ERR_DEVICE_ADMIN_ONLY", "Device error: Only admin has permition"));
        errorList.put(-110112L, new ErrorInfo(-110112L, "ERR_DEVICE_UNFISCALIZED", "Device error: Fiscal memory is closed"));
        errorList.put(-110200L, new ErrorInfo(-110200L, "ERR_NAP_OPEN_SESSION", "NAP server error: Error open session"));
        errorList.put(-110201L, new ErrorInfo(-110201L, "ERR_NAP_PREPARE_DATA", "NAP server error: Error preparing data for server"));
        errorList.put(-110202L, new ErrorInfo(-110202L, "ERR_NAP_SEND_DATA", "NAP server error: There is unsent data"));
        errorList.put(-110203L, new ErrorInfo(-110203L, "ERR_NAP_RECV_DATA", "NAP server error: Receiving data error"));
        errorList.put(-110204L, new ErrorInfo(-110204L, "ERR_NAP_EMPTY_DATA", "NAP server error: Empty data"));
        errorList.put(-110205L, new ErrorInfo(-110205L, "ERR_NAP_NEGATIVE_ANSWER", "NAP server error: Server negative answer"));
        errorList.put(-110206L, new ErrorInfo(-110206L, "ERR_NAP_WRONG_ANSWER_FORMAT", "NAP server error: Wrong answer format"));
        errorList.put(-110207L, new ErrorInfo(-110207L, "ERR_NAP_HOSTDI_ZERRO", "NAP server error: Server HOSTDI is zerro"));
        errorList.put(-110208L, new ErrorInfo(-110208L, "ERR_NAP_EXCEPTION", "NAP server error: Server exception"));
        errorList.put(-110209L, new ErrorInfo(-110209L, "ERR_NAP_NOTPERSONALIZED", "NAP server error: Not registered on server"));
        errorList.put(-110209L, new ErrorInfo(-110209L, "ERR_NAP_NOTREGISTERED", "NAP server error: Not registered on server"));
        errorList.put(-110210L, new ErrorInfo(-110210L, "ERR_NAP_BLOCKED_72H", "NAP server error: Communication with NAP server is blocked"));
        errorList.put(-110211L, new ErrorInfo(-110211L, "ERR_NAP_BLOCKED_NO_MODEM_LAN", "NAP server error: Modem error"));
        errorList.put(-110212L, new ErrorInfo(-110212L, "ERR_NAP_BUSY", "NAP server error: NAP is busy"));
        errorList.put(-110213L, new ErrorInfo(-110213L, "ERR_NAP_REGISTERED", "NAP server error: Already registered"));
        errorList.put(-110214L, new ErrorInfo(-110214L, "ERR_NAP_WRONG_PSTYPE", "NAP server error: Wrong PS type"));
        errorList.put(-110215L, new ErrorInfo(-110215L, "ERR_NAP_DEREG_ON_SERVER", "NAP server error: Deregistered in NAP"));
        errorList.put(-110216L, new ErrorInfo(-110216L, "ERR_NAP_WRONG_IMSI", "NAP server error: Wrong IMSI number"));
        errorList.put(-110217L, new ErrorInfo(-110217L, "ERR_NAP_BLOCKED_MAX_ZERRORS", "NAP server error: Device is blocked( maximum Zreports )"));
        errorList.put(-110218L, new ErrorInfo(-110218L, "ERR_NAP_WRONG_FDTYPE", "NAP server error: Wrong FD( Fiscal device ) type"));
        errorList.put(-110219L, new ErrorInfo(-110219L, "ERR_NAP_BLOCKED_BY_SERVER", "NAP server error: The ECR is blocked by server"));
        errorList.put(-110220L, new ErrorInfo(-110220L, "ERR_NAP_BLOCKED_ERROR_FROM_SERVER", "NAP server error: The ECR is blocked - server error"));
        errorList.put(-110221L, new ErrorInfo(-110221L, "ERR_NAP_NO_SERVER_ADDRESS", "NAP server error: No server address"));
        errorList.put(-110222L, new ErrorInfo(-110222L, "ERR_NAP_NO_REGISTRATIONS_POSSIBLE", "NAP server error: Max. registrations reached."));
        errorList.put(-110223L, new ErrorInfo(-110223L, "ERR_NAP_INVALID_OPERATOR_INN", "Invalid INN of the cashier"));
        errorList.put(-110224L, new ErrorInfo(-110224L, "ERR_NAP_INVALID_SERVER_INN", "Invalid INN of the server"));
        errorList.put(-110225L, new ErrorInfo(-110225L, "ERR_NAP_BLOCKED_MAX_SELLERRORS", "NAP server error: Device is blocked( unsent sales documents )"));
        errorList.put(-110226L, new ErrorInfo(-110226L, "ERR_NAP_BLOCKED_24H", "NAP server error: Communication with NAP server is blocked. More than 24 hours from last sent receipt."));
        errorList.put(-110300L, new ErrorInfo(-110300L, "ERR_WORK_INVALID_FILE", "Working error: Invalid file"));
        errorList.put(-110301L, new ErrorInfo(-110301L, "ERR_WORK_INVALID_PARAM", "Working error: Invalid parameters"));
        errorList.put(-110500L, new ErrorInfo(-110500L, "ERR_MODEM_CTRL", "Modem error: error in communication between device and modem"));
        errorList.put(-110501L, new ErrorInfo(-110501L, "ERR_MODEM_NO_SIM", "Modem error: No SIM card"));
        errorList.put(-110502L, new ErrorInfo(-110502L, "ERR_MODEM_PIN", "Modem error: Wrong PIN of SIM"));
        errorList.put(-110503L, new ErrorInfo(-110503L, "ERR_MODEM_ATTACH", "Modem error: Cannot register to mobile network"));
        errorList.put(-110504L, new ErrorInfo(-110504L, "ERR_MODEM_PPP", "Modem error: No PPP connection( cannot connect )"));
        errorList.put(-110505L, new ErrorInfo(-110505L, "ERR_MODEM_CONFIG", "Modem error: Wrong modem configuration( for example - no programmed apn )"));
        errorList.put(-110506L, new ErrorInfo(-110506L, "ERR_MODEM_WAIT_INIT", "Modem error: Modem initializing"));
        errorList.put(-110507L, new ErrorInfo(-110507L, "ERR_MODEM_NOTREADY", "Modem error: Modem is not ready"));
        errorList.put(-110508L, new ErrorInfo(-110508L, "ERR_MODEM_REMOVE_SIM", "Modem error: Remove SIM card"));
        errorList.put(-110509L, new ErrorInfo(-110509L, "ERR_MODEM_CELL_FOUND", "Modem error: Modem found a cell"));
        errorList.put(-110510L, new ErrorInfo(-110510L, "ERR_MODEM_CELL_NOTFOUND", "Modem error: Modem does not find a cell"));
        errorList.put(-110511L, new ErrorInfo(-110511L, "ERR_MODEM_LOT_DAYS_FAIL", "Modem error: Failed lot days"));
        errorList.put(-110601L, new ErrorInfo(-110601L, "ERR_MODEM_CONNECT_AP", "Modem error: Device is not connected to AP( access point )"));
        errorList.put(-110700L, new ErrorInfo(-110700L, "ERR_NET_DNS_RESOLVE", "Network error: Cannot resolve address"));
        errorList.put(-110701L, new ErrorInfo(-110701L, "ERR_NET_SOCKET", "Network error: Cannot open socket for communication with server"));
        errorList.put(-110702L, new ErrorInfo(-110702L, "ERR_NET_CONNECTION", "Network error: Connection error( cannot connect to a server )"));
        errorList.put(-110703L, new ErrorInfo(-110703L, "ERR_NET_CONFIG", "Network error: Config error( for example: no server address )"));
        errorList.put(-110704L, new ErrorInfo(-110704L, "ERR_NET_SOCKET_CONNECTED", "Network error: Connection socket is already opened"));
        errorList.put(-110705L, new ErrorInfo(-110705L, "ERR_NET_SSL_ERROR", "Network error: SSL communication error( something went wrong in cryptographic protocol )"));
        errorList.put(-110706L, new ErrorInfo(-110706L, "ERR_NET_HTTP_ERROR", "Network error: HTTP communication error( something went wrong in http protocol )"));
        errorList.put(-110800L, new ErrorInfo(-110800L, "ERR_DT_OK", "Tax terminal error: No error"));
        errorList.put(-110801L, new ErrorInfo(-110801L, "ERR_DT_UNKNOWN_ID", "Tax terminal error: Unknown ID"));
        errorList.put(-110802L, new ErrorInfo(-110802L, "ERR_DT_INVALID_TOKEN", "Tax terminal error: Invalid token( key from the server )"));
        errorList.put(-110803L, new ErrorInfo(-110803L, "ERR_DT_PROTOCOL_ERROR", "Tax terminal error: Protocol error"));
        errorList.put(-110804L, new ErrorInfo(-110804L, "ERR_DT_UNKNOWN_COMMAND", "Tax terminal error: The command is unknown"));
        errorList.put(-110805L, new ErrorInfo(-110805L, "ERR_DT_UNSUPPORTED_COMMAND", "Tax terminal error: The command is not supported"));
        errorList.put(-110806L, new ErrorInfo(-110806L, "ERR_DT_INVALID_CONFIGURATION", "Tax terminal error: Invalid configuration"));
        errorList.put(-110807L, new ErrorInfo(-110807L, "ERR_DT_SSL_IS_NOT_ALLOWED", "Tax terminal error: SSL is not allowed"));
        errorList.put(-110808L, new ErrorInfo(-110808L, "ERR_DT_INVALID_REQUEST_NUMBER", "Tax terminal error: Invalid request number"));
        errorList.put(-110809L, new ErrorInfo(-110809L, "ERR_DT_INVALID_RETRY_REQUEST", "Tax terminal error: Invalid retry request"));
        errorList.put(-110810L, new ErrorInfo(-110810L, "ERR_DT_CANT_CANCEL_TICKET", "Tax terminal error: Cannot cancel ticket"));
        errorList.put(-110811L, new ErrorInfo(-110811L, "ERR_DT_OPEN_SHIFT_TIMEOUT_EXPIRED", "Tax terminal error: More than 24 hours from shift opening"));
        errorList.put(-110812L, new ErrorInfo(-110812L, "ERR_DT_INVALID_LOGIN_PASSWORD", "Tax terminal error: Invalid login name or password"));
        errorList.put(-110813L, new ErrorInfo(-110813L, "ERR_DT_INCORRECT_REQUEST_DATA", "Tax terminal error: Incorrect request data"));
        errorList.put(-110814L, new ErrorInfo(-110814L, "ERR_DT_NOT_ENOUGH_CASH", "Tax terminal error: Not enough cash"));
        errorList.put(-110815L, new ErrorInfo(-110815L, "ERR_DT_BLOCKED", "Tax terminal error: Blocked from server"));
        errorList.put(-110854L, new ErrorInfo(-110854L, "ERR_DT_SERVICE_TEMPORARILY_UNAVAILABLE", "Tax terminal error: Service temporarily unavailable"));
        errorList.put(-110855L, new ErrorInfo(-110855L, "ERR_DT_UNKNOWN_ERROR", "Tax terminal error: Unknown error"));
        errorList.put(-111000L, new ErrorInfo(-111000L, "ERR_R_CLEAR", "Registration mode error: Common error, followed by deliting all data for the command"));
        errorList.put(-111001L, new ErrorInfo(-111001L, "ERR_R_NOCLEAR", "Registration mode error: Common error, followed by partly deliting data for the command"));
        errorList.put(-111002L, new ErrorInfo(-111002L, "ERR_R_SYNTAX", "Registration mode error: Syntax error. Check the parameters of the command"));
        errorList.put(-111003L, new ErrorInfo(-111003L, "ERR_R_NPOSSIBLE", "Registration mode error: Cannot do operation"));
        errorList.put(-111004L, new ErrorInfo(-111004L, "ERR_R_PLU_NOTEXIST", "Registration mode error: PLU code was not found"));
        errorList.put(-111005L, new ErrorInfo(-111005L, "ERR_R_PLU_VAT_DISABLE", "Registration mode error: Forbidden VAT"));
        errorList.put(-111006L, new ErrorInfo(-111006L, "ERR_R_PLU_QTY_PRC", "Registration mode error: Overflow in multiplication of"));
        errorList.put(-111007L, new ErrorInfo(-111007L, "ERR_R_PLU_NO_PRC", "Registration mode error: PLU has no price"));
        errorList.put(-111008L, new ErrorInfo(-111008L, "ERR_R_PLU_GRP_RANGE", "Registration mode error: Group is not in range"));
        errorList.put(-111009L, new ErrorInfo(-111009L, "ERR_R_PLU_DEP_RANGE", "Registration mode error: Department is not in range"));
        errorList.put(-111010L, new ErrorInfo(-111010L, "ERR_R_BAR_NOTEXIST", "Registration mode error: BAR code does not exist"));
        errorList.put(-111011L, new ErrorInfo(-111011L, "ERR_R_OVF_TOTAL", "Registration mode error: Overflow of the PLU turnover"));
        errorList.put(-111012L, new ErrorInfo(-111012L, "ERR_R_OVF_QTY", "Registration mode error: Overflow of the PLU quantity"));
        errorList.put(-111013L, new ErrorInfo(-111013L, "ERR_R_ECR_OVR", "Registration mode error: ECR daily registers overflow"));
        errorList.put(-111014L, new ErrorInfo(-111014L, "ERR_R_BILL_TL_OVR", "Registration mode error: Bill total register overflow"));
        errorList.put(-111015L, new ErrorInfo(-111015L, "ERR_R_OPEN_BON", "Registration mode error: Receipt is opened"));
        errorList.put(-111016L, new ErrorInfo(-111016L, "ERR_R_CLOSED_BON", "Registration mode error: Receipt is closed"));
        errorList.put(-111017L, new ErrorInfo(-111017L, "ERR_R_PAY_NOCASH", "Registration mode error: No cash in ECR"));
        errorList.put(-111018L, new ErrorInfo(-111018L, "ERR_R_PAY_STARTED", "Registration mode error: Payment is initiated"));
        errorList.put(-111019L, new ErrorInfo(-111019L, "ERR_R_OVF_TRZ_BUFF", "Registration mode error: Maximum number of sales in receipt"));
        errorList.put(-111020L, new ErrorInfo(-111020L, "ERR_R_NO_TRANSACTIONS", "Registration mode error: No transactions"));
        errorList.put(-111021L, new ErrorInfo(-111021L, "ERR_R_NEGATIVE_SUMVAT", "Registration mode error: Possible negative turnover"));
        errorList.put(-111022L, new ErrorInfo(-111022L, "ERR_R_PYFOREIGN_HAVERESTO", "Registration mode error: Foreign payment has change"));
        errorList.put(-111023L, new ErrorInfo(-111023L, "ERR_R_TRZ_NOT_EXIST", "Registration mode error: Transaction is not found in the receipt"));
        errorList.put(-111024L, new ErrorInfo(-111024L, "ERR_R_END_OF_24_HOUR_PERIOD", "Registration mode error: End of 24 hour blocking"));
        errorList.put(-111025L, new ErrorInfo(-111025L, "ERR_R_NO_VALID_INVOICE", "Registration mode error: Invalid invoice range"));
        errorList.put(-111026L, new ErrorInfo(-111026L, "ERR_R_POS_TERM_CANCELED", "Registration mode error: Operation is cancelled by operator"));
        errorList.put(-111027L, new ErrorInfo(-111027L, "ERR_R_POS_TERM_APPROVED", "Registration mode error: Operation approved by POS"));
        errorList.put(-111028L, new ErrorInfo(-111028L, "ERR_R_POS_TERM_NOT_APPROVED", "Registration mode error: Operation is not approved by POS"));
        errorList.put(-111029L, new ErrorInfo(-111029L, "ERR_R_POS_TERM_CONN_ERR", "Registration mode error: POS terminal communication error"));
        errorList.put(-111030L, new ErrorInfo(-111030L, "ERR_R_PLU_QTY_PRC_TOO_LOW", "Registration mode error: Multiplication of quantity and price is 0"));
        errorList.put(-111031L, new ErrorInfo(-111031L, "ERR_R_VALUE_TOO_BIG", "Registration mode error: Value is too big"));
        errorList.put(-111032L, new ErrorInfo(-111032L, "ERR_R_VALUE_BAD", "Registration mode error: Value is bad"));
        errorList.put(-111033L, new ErrorInfo(-111033L, "ERR_R_PRICE_TOO_BIG", "Registration mode error: Price is too big"));
        errorList.put(-111034L, new ErrorInfo(-111034L, "ERR_R_PRICE_BAD", "Registration mode error: Price is bad"));
        errorList.put(-111035L, new ErrorInfo(-111035L, "ERR_R_ALL_VOID_SELECTED", "Registration mode error: Operation all void is selected to be executed"));
        errorList.put(-111036L, new ErrorInfo(-111036L, "ERR_R_ONLY_ALL_VOID_IS_POSSIBLE", "Registration mode error: Only all void operation is permitted"));
        errorList.put(-111040L, new ErrorInfo(-111040L, "ERR_R_REST_NOFREESPC_SELLS", "Registration mode error: Restaurant: There is no free space for other purchases"));
        errorList.put(-111041L, new ErrorInfo(-111041L, "ERR_R_REST_NOFREESPCFORNEWACNT", "Registration mode error: Restaurant: There is no free space for new acount"));
        errorList.put(-111042L, new ErrorInfo(-111042L, "ERR_R_REST_ACCOUNT_IS_OPENED", "Registration mode error: Restaurant: Account is already opened"));
        errorList.put(-111043L, new ErrorInfo(-111043L, "ERR_R_REST_WRONG_INDEX", "Registration mode error: Restaurant: Wrong index"));
        errorList.put(-111044L, new ErrorInfo(-111044L, "ERR_R_REST_ACNT_IS_NOTFOUND", "Registration mode error: Restaurant: Account is not found"));
        errorList.put(-111045L, new ErrorInfo(-111045L, "ERR_R_REST_NOT_PERMITTED", "Registration mode error: Restaurant: Not permitted( only for admins )"));
        errorList.put(-111046L, new ErrorInfo(-111046L, "ERR_R_OPEN_NONFISCALBON", "Registration mode error: non-fiscal receipt is opened"));
        errorList.put(-111047L, new ErrorInfo(-111047L, "ERR_R_OPEN_FISCALBON", "Registration mode error: fiscal receipt is opened"));
        errorList.put(-111048L, new ErrorInfo(-111048L, "ERR_R_BUYERS_TIN_IS_ENTERED", "Registration mode error: Buyers TIN is already entered"));
        errorList.put(-111049L, new ErrorInfo(-111049L, "ERR_R_BUYERS_TIN_IS_NOT_ENTERED", "Registration mode error: Buyers TIN is not entered"));
        errorList.put(-111050L, new ErrorInfo(-111050L, "ERR_R_PAY_NOT_STARTED", "Registration mode error: Payment is not initiated"));
        errorList.put(-111051L, new ErrorInfo(-111051L, "ERR_R_BON_TYPE_MISMATCH", "Registration mode error: Reeipt type mismatch"));
        errorList.put(-111052L, new ErrorInfo(-111052L, "ERR_R_REACH_BON_TL_LIMIT", "Registration mode error: Receipt total limit is reached"));
        errorList.put(-111053L, new ErrorInfo(-111053L, "ERR_R_CASH_NO_MULT_MIN_COIN", "Registration mode error: Sum cannot be divided by the minimum coin"));
        errorList.put(-111054L, new ErrorInfo(-111054L, "ERR_R_PAY_BIG_AMOUNT", "Registration mode error: Sum must be <= payment amount"));
        errorList.put(-111055L, new ErrorInfo(-111055L, "ERR_R_PAY_VOUCHER_NEED_INPUT_SUM", "Registration mode error: Sum of voucher must be entered when paying with voucher"));
        errorList.put(-111056L, new ErrorInfo(-111056L, "ERR_R_PAY_VOUCHER_NEED_SURCHARGE", "Registration mode error: Value surcharge of the difference between voucher sum and total must be done when paying with voucher and sum > total"));
        errorList.put(-111057L, new ErrorInfo(-111057L, "ERR_R_PAY_FOREIGN_DISABLED", "Registration mode error: Payment with foreign currency is disabled"));
        errorList.put(-111058L, new ErrorInfo(-111058L, "ERR_R_PAY_FOREIGN_IMPOSSIBLE", "Registration mode error: Payment with foreign currency is impossible"));
        errorList.put(-111059L, new ErrorInfo(-111059L, "ERR_R_PAY_FOREIGN_SMALL_AMOUNT", "Registration mode error: Sum must be bigger or equal to payment amount"));
        errorList.put(-111060L, new ErrorInfo(-111060L, "ERR_R_SAFE_OPEN_DISABLED", "Registration mode error: Safe opening is disabled"));
        errorList.put(-111061L, new ErrorInfo(-111061L, "ERR_R_PAY_FORBIDDEN", "Registration mode error: Forbidden payment"));
        errorList.put(-111062L, new ErrorInfo(-111062L, "ERR_R_PERC_KEY_FORBIDDEN", "Registration mode error: Forbidden key for surcharge/discount"));
        errorList.put(-111063L, new ErrorInfo(-111063L, "ERR_R_AMOUNT_BIGGER_BILLAMOUNT", "Registration mode error: Entered sum is bigger thanreceipt sum"));
        errorList.put(-111064L, new ErrorInfo(-111064L, "ERR_R_AMOUNT_SMALLER_BILLAMOUNT", "Registration mode error: Entered sum is smaller thanreceipt sum"));
        errorList.put(-111065L, new ErrorInfo(-111065L, "ERR_R_ZERO_BILLAMOUNT", "Registration mode error: Fiscal printer: Sum of receipt is 0. Operation 'void' is needed"));
        errorList.put(-111066L, new ErrorInfo(-111066L, "ERR_R_ALL_VOID_EXECUTED", "Registration mode error: Fiscal printer: Operation 'void' is executed. Close receipt is needed"));
        errorList.put(-111067L, new ErrorInfo(-111067L, "ERR_R_OPEN_STORNOBON", "Registration mode error: Storno receipt is opened"));
        errorList.put(-111068L, new ErrorInfo(-111068L, "ERR_R_PAY_ZERO_AMOUNT", "Registration mode error: Sum is not entered"));
        errorList.put(-111069L, new ErrorInfo(-111069L, "ERR_R_PLU_PRICETYPE_RANGE", "Registration mode error: Price type is invalid"));
        errorList.put(-111070L, new ErrorInfo(-111070L, "ERR_R_PLU_PRICETYPE_LINKED", "Registration mode error: Linked surcharge is forbidden"));
        errorList.put(-111071L, new ErrorInfo(-111071L, "ERR_R_PLU_PRICETYPE_NEGATIVE", "Registration mode error: Negative price is forbidden"));
        errorList.put(-111072L, new ErrorInfo(-111072L, "ERR_R_MORE_THAN_ONE_VAT", "Registration mode error: More than 1 VAT in one receipt is not allowed"));
        errorList.put(-111073L, new ErrorInfo(-111073L, "ERR_R_PINPAD", "Registration mode error: Pinpad error"));
        errorList.put(-111074L, new ErrorInfo(-111074L, "ERR_R_WRONG_BUYERS_DATA", "Registration mode error: Buyer data is wrong"));
        errorList.put(-111075L, new ErrorInfo(-111075L, "ERR_R_VAT_SYSTEM_DISABLE", "Registration mode error: Vat system disable."));
        errorList.put(-111076L, new ErrorInfo(-111076L, "ERR_R_OPER_NOT_LOGGED_IN", "Operator not logged in."));
        errorList.put(-111077L, new ErrorInfo(-111077L, "ERR_R_WRONG_DATE_FM", "The receipt date is early on last date in fiscal memory."));
        errorList.put(-111078L, new ErrorInfo(-111078L, "ERR_R_CORR_DATA_NOT_ENTERED", "Correction receipt data is not entered!"));
        errorList.put(-111079L, new ErrorInfo(-111079L, "ERR_R_FRACTIONAL_QTY", "Fractional quantity!"));
        errorList.put(-111080L, new ErrorInfo(-111080L, "ERR_R_OUT_OF_STOCK", "Registration mode error: Registration mode error: Out of stock"));
        errorList.put(-111081L, new ErrorInfo(-111081L, "ERR_R_STL_NEEDED", "Registration mode error: Must pushing of the STL before TL."));
        errorList.put(-111082L, new ErrorInfo(-111082L, "ERR_R_PACK_NOTEXIST", "Package does not exist"));
        errorList.put(-111083L, new ErrorInfo(-111083L, "ERR_R_PLU_UNIT_NOTEXIST", "Measuring unit not found"));
        errorList.put(-111084L, new ErrorInfo(-111084L, "ERR_R_PLU_CATEGORY_NOTEXIST", "Category not found in the data base"));
        errorList.put(-111085L, new ErrorInfo(-111085L, "ERR_R_DEP_WRONG_NAME", "Invalid department name"));
        errorList.put(-111086L, new ErrorInfo(-111086L, "ERR_R_BANK_TERM_NOT_CONFIGURED", "Bank terminal not configured"));
        errorList.put(-111087L, new ErrorInfo(-111087L, "ERR_R_SIGN_PAY_INCORECT", "Disallowed 'РїСЂРёР·РЅР°Рє СЂР°СЃС‡РµС‚Р°' (Russia)"));
        errorList.put(-111088L, new ErrorInfo(-111088L, "ERR_R_SIGN_INCORRECT", "Forbidden РїСЂРёР·РЅР°Рє С‚РѕРІР°СЂР°"));
        errorList.put(-111089L, new ErrorInfo(-111089L, "ERR_R_PLU_OVER_MAX_PRC", "Entered price is bigger than the programmed"));
        errorList.put(-111090L, new ErrorInfo(-111090L, "ERR_R_PLU_FIX_PRC", "Fix PLU's price"));
        errorList.put(-111091L, new ErrorInfo(-111091L, "ERR_R_SIGN_AGENT_INCORECT", "Incorect sign agent."));
        errorList.put(-111092L, new ErrorInfo(-111092L, "ERR_R_PAY_VOUCHER_RESTO", "Voucher payment cannot have change"));
        errorList.put(-111093L, new ErrorInfo(-111093L, "ERR_R_PAY_ADVANCE_BIG", "Sum for advance payment is bigger than the sum of article"));
        errorList.put(-111094L, new ErrorInfo(-111094L, "ERR_R_PAY_STORNO_RESTO", "Payment in storno can not have change"));
        errorList.put(-111095L, new ErrorInfo(-111095L, "ERR_R_NOT_EXCISE_PLU_WITH_EXCISE_STAMP", "Invalid parameter - PLU is not defined as excise PLU"));
        errorList.put(-111096L, new ErrorInfo(-111096L, "ERR_R_EXCISE_PLU_WITHOUT_EXCISE_STAMP", "Excise stamp of an excise PLU is not entered"));
        errorList.put(-111097L, new ErrorInfo(-111097L, "ERR_R_EXCISE_PLU_FORBIDDEN", "SALE FORBIDDEN (excise stamp is not valid)"));
        errorList.put(-111500L, new ErrorInfo(-111500L, "ERR_PINPAD_NONE", "Pinpad error: No error from pinpad"));
        errorList.put(-111501L, new ErrorInfo(-111501L, "ERR_PINPAD_GENERAL", "Pinpad error: General unicreditbulbank icon error"));
        errorList.put(-111502L, new ErrorInfo(-111502L, "ERR_PINPAD_INVALID_COMMAND", "Pinpad error: Not valid command or sub command code"));
        errorList.put(-111503L, new ErrorInfo(-111503L, "ERR_PINPAD_INVALID_PARAM", "Pinpad error: Invalid parameter"));
        errorList.put(-111504L, new ErrorInfo(-111504L, "ERR_PINPAD_INVALID_ADDRESS", "Pinpad error: The address is outside limits"));
        errorList.put(-111505L, new ErrorInfo(-111505L, "ERR_PINPAD_INVALID_VALUE", "Pinpad error: The value is outside limits"));
        errorList.put(-111506L, new ErrorInfo(-111506L, "ERR_PINPAD_INVALID_LENGTH", "Pinpad error: The length is outside limits"));
        errorList.put(-111507L, new ErrorInfo(-111507L, "ERR_PINPAD_NOT_PERMIT", "Pinpad error: The action is not permited in current state"));
        errorList.put(-111508L, new ErrorInfo(-111508L, "ERR_PINPAD_NO_DATA", "Pinpad error: There is no data to be returned"));
        errorList.put(-111509L, new ErrorInfo(-111509L, "ERR_PINPAD_TIMEOUT", "Pinpad error: Timeout occurs"));
        errorList.put(-111510L, new ErrorInfo(-111510L, "ERR_PINPAD_INVALID_KEY_NUMBER", "Pinpad error: Invalid key number"));
        errorList.put(-111511L, new ErrorInfo(-111511L, "ERR_PINPAD_INVALID_KEY_ATTRIBUTES", "Pinpad error: Invalid key attributes(usage)"));
        errorList.put(-111512L, new ErrorInfo(-111512L, "ERR_PINPAD_INVALID_DEVICE", "Pinpad error: Calling of non-existing device"));
        errorList.put(-111513L, new ErrorInfo(-111513L, "ERR_PINPAD_NOT_SUPPORT", "Pinpad error: (Not used in this FW version)"));
        errorList.put(-111514L, new ErrorInfo(-111514L, "ERR_PINPAD_PIN_LIMIT", "Pinpad error: Pin entering limit exceed"));
        errorList.put(-111515L, new ErrorInfo(-111515L, "ERR_PINPAD_FLASH", "Pinpad error: General error in flash commands"));
        errorList.put(-111516L, new ErrorInfo(-111516L, "ERR_PINPAD_HARDWARE", "Pinpad error: General hardware unicreditbulbank error"));
        errorList.put(-111517L, new ErrorInfo(-111517L, "ERR_PINPAD_INVALID_CRC", "Pinpad error: Invalid code check (Not used in this FW version)"));
        errorList.put(-111518L, new ErrorInfo(-111518L, "ERR_PINPAD_CANCEL", "Pinpad error: The button 'CANCEL' is pressed"));
        errorList.put(-111519L, new ErrorInfo(-111519L, "ERR_PINPAD_INVALID_SIGNATURE", "Pinpad error: Invalid signature"));
        errorList.put(-111520L, new ErrorInfo(-111520L, "ERR_PINPAD_INVALID_HEADER", "Pinpad error: Invalid data in header"));
        errorList.put(-111521L, new ErrorInfo(-111521L, "ERR_PINPAD_INVALID_PASSWORD", "Pinpad error: Incorrect password"));
        errorList.put(-111522L, new ErrorInfo(-111522L, "ERR_PINPAD_INVALID_KEY_FORMAT", "Pinpad error: Invalid key format"));
        errorList.put(-111523L, new ErrorInfo(-111523L, "ERR_PINPAD_SCR", "Pinpad error: General unicreditbulbank error in smart card reader"));
        errorList.put(-111524L, new ErrorInfo(-111524L, "ERR_PINPAD_HAL", "Pinpad error: Error code returned from HAL functions"));
        errorList.put(-111525L, new ErrorInfo(-111525L, "ERR_PINPAD_INVALID_KEY", "Pinpad error: Invalid key (may not be present)"));
        errorList.put(-111526L, new ErrorInfo(-111526L, "ERR_PINPAD_NO_PIN_DATA", "Pinpad error: The PIN length is less than 4 or bigger than 12"));
        errorList.put(-111527L, new ErrorInfo(-111527L, "ERR_PINPAD_INVALID_REMINDER", "Pinpad error: Issuer or ICC key invalid remainder length"));
        errorList.put(-111528L, new ErrorInfo(-111528L, "ERR_PINPAD_NOT_INIT", "Pinpad error: Not initialized (Not used in this FW version)"));
        errorList.put(-111529L, new ErrorInfo(-111529L, "ERR_PINPAD_LIMIT", "Pinpad error: Limit is reached (Not used in this FW version)"));
        errorList.put(-111530L, new ErrorInfo(-111530L, "ERR_PINPAD_INVALID_SEQUENCE", "Pinpad error: Invalid sequence (Not used in this FW version)"));
        errorList.put(-111531L, new ErrorInfo(-111531L, "ERR_PINPAD_NO_PERMITION", "Pinpad error: The action is not permitted"));
        errorList.put(-111532L, new ErrorInfo(-111532L, "ERR_PINPAD_NO_TMK", "Pinpad error: TMK is not loaded. The action cannot be executed"));
        errorList.put(-111533L, new ErrorInfo(-111533L, "ERR_PINPAD_INVALID_KEK", "Pinpad error: Wrong key format"));
        errorList.put(-111534L, new ErrorInfo(-111534L, "ERR_PINPAD_DUPLICATE_KEY", "Pinpad error: Duplicated key"));
        errorList.put(-111535L, new ErrorInfo(-111535L, "ERR_PINPAD_KEYBOARD", "Pinpad error: General keyboard error"));
        errorList.put(-111536L, new ErrorInfo(-111536L, "ERR_PINPAD_KEYBOARD_NOT_CALIBRATED", "Pinpad error: The keyboard is no calibrated."));
        errorList.put(-111537L, new ErrorInfo(-111537L, "ERR_PINPAD_KEYBOARD_FAILED", "Pinpad error: Keyboard bug detected."));
        errorList.put(-111538L, new ErrorInfo(-111538L, "ERR_PINPAD_DEVICE_BUSY", "Pinpad error: The device is busy, try again"));
        errorList.put(-111539L, new ErrorInfo(-111539L, "ERR_PINPAD_TAMPERED", "Pinpad error: Device is tampered"));
        errorList.put(-111540L, new ErrorInfo(-111540L, "ERR_PINPAD_EMSR", "Pinpad error: Error in encrypted head"));
        errorList.put(-111541L, new ErrorInfo(-111541L, "ERR_PINPAD_ACCEPT", "Pinpad error: The button 'OK' is pressed"));
        errorList.put(-111542L, new ErrorInfo(-111542L, "ERR_PINPAD_INVALID_PAN", "Pinpad error: Wrong PAN"));
        errorList.put(-111543L, new ErrorInfo(-111543L, "ERR_PINPAD_NOT_ENOUGH_MEMORY", "Pinpad error: Out of memory"));
        errorList.put(-111544L, new ErrorInfo(-111544L, "ERR_PINPAD_EMV", "Pinpad error: EMV error"));
        errorList.put(-111545L, new ErrorInfo(-111545L, "ERR_PINPAD_CRYPTOGRAPHY", "Pinpad error: Cryptographic error"));
        errorList.put(-111546L, new ErrorInfo(-111546L, "ERR_PINPAD_COMMUNICATION", "Pinpad error: Communication error"));
        errorList.put(-111547L, new ErrorInfo(-111547L, "ERR_PINPAD_INVALID_VERSION", "Pinpad error: Invalid firmware version"));
        errorList.put(-111548L, new ErrorInfo(-111548L, "ERR_PINPAD_NOPAPER", "Pinpad error: Printer is out of paper"));
        errorList.put(-111549L, new ErrorInfo(-111549L, "ERR_PINPAD_OVERHEATED", "Pinpad error: Printer is overheated"));
        errorList.put(-111550L, new ErrorInfo(-111550L, "ERR_PINPAD_NOT_CONNECTED", "Pinpad error: Device is not connected"));
        errorList.put(-111551L, new ErrorInfo(-111551L, "ERR_PINPAD_USE_CHIP", "Pinpad error: Use the chip reader"));
        errorList.put(-111552L, new ErrorInfo(-111552L, "ERR_PINPAD_END_DAY", "Pinpad error: End the day first"));
        errorList.put(-111554L, new ErrorInfo(-111554L, "ERR_PINPAD_BOR_ERR", "Pinpad error: Error from Borica"));
        errorList.put(-111555L, new ErrorInfo(-111555L, "ERR_PINPAD_NO_CONN", "Pinpad error: No connection with pinpad"));
        errorList.put(-111556L, new ErrorInfo(-111556L, "ERR_PINPAD_ECR", "Pinpad error: Success in pinpad, unsuccess in ECR"));
        errorList.put(-111557L, new ErrorInfo(-111557L, "ERR_PINPAD_NOT_CONF", "Pinpad error: Not configured connection between fiscal device and PinPad"));
        errorList.put(-111558L, new ErrorInfo(-111558L, "ERR_PINPAD_SAME_TRANS", "Pinpad error: The last transactions are equals or connection is interrupted - try again."));
        errorList.put(-111559L, new ErrorInfo(-111559L, "ERR_PINPAD_RECEIPT", "Pinpad error: Payment type: debit/credit card via PinPad. In the fiscal receipt is allowed only one payment with such type."));
        errorList.put(-111560L, new ErrorInfo(-111560L, "ERR_PINPAD_FP_TRANS", "Pinpad error: Unknown result of the transaction between fiscal device and PinPad"));
        errorList.put(-111561L, new ErrorInfo(-111561L, "ERR_PINPAD_NOT_CONF_TYPE", "Pinpad error: Pinpad type not configured"));
        errorList.put(-111700L, new ErrorInfo(-111700L, "ERR_PINPAD_INV_AMOUNT", "Pinpad error: Invalid ammount."));
        errorList.put(-111701L, new ErrorInfo(-111701L, "ERR_PINPAD_TRN_NOT_FOUND", "Pinpad error: Transaction not found."));
        errorList.put(-111702L, new ErrorInfo(-111702L, "ERR_PINPAD_FILE_EMPTY", "Pinpad error: The file is empty."));
        errorList.put(-111703L, new ErrorInfo(-111703L, "ERR_PINPAD_MAX_CASHBACK", "Entered cashback is bigger than cashback limit."));
        errorList.put(-111800L, new ErrorInfo(-111800L, "ERR_SCALE_NOT_RESPOND", "ERR_SCALE_NOT_RESPOND"));
        errorList.put(-111801L, new ErrorInfo(-111801L, "ERR_SCALE_NOT_CALCULATED", "ERR_SCALE_NOT_CALCULATED"));
        errorList.put(-111802L, new ErrorInfo(-111802L, "ERR_SCALE_WRONG_RESPONSE", "ERR_SCALE_WRONG_RESPONSE"));
        errorList.put(-111803L, new ErrorInfo(-111803L, "ERR_SCALE_ZERO_WEIGHT", "ERR_SCALE_ZERO_WEIGHT"));
        errorList.put(-111804L, new ErrorInfo(-111804L, "ERR_SCALE_NEGATIVE_WEIGHT", "ERR_SCALE_NEGATIVE_WEIGHT"));
        errorList.put(-111805L, new ErrorInfo(-111805L, "ERR_SCALE_T_WRONG_INTF", "ERR_SCALE_T_WRONG_INTF"));
        errorList.put(-111806L, new ErrorInfo(-111806L, "ERR_SCALE_T_CONNECT", "ERR_SCALE_T_CONNECT"));
        errorList.put(-111807L, new ErrorInfo(-111807L, "ERR_SCALE_SEND", "ERR_SCALE_SEND"));
        errorList.put(-111808L, new ErrorInfo(-111808L, "ERR_SCALE_RECEIVE", "ERR_SCALE_RECEIVE"));
        errorList.put(-111809L, new ErrorInfo(-111809L, "ERR_SCALE_FILE_GENERATE", "ERR_SCALE_FILE_GENERATE"));
        errorList.put(-111810L, new ErrorInfo(-111810L, "ERR_SCALE_NOT_CONFIG", "ERR_SCALE_NOT_CONFIG"));
        errorList.put(-111900L, new ErrorInfo(-111900L, "ERR_NTP_NO_COMM", "Communication error wtih NTP server: Cannot make communication"));
        errorList.put(-111901L, new ErrorInfo(-111901L, "ERR_NTP_EARLIER_DATETIME", "Communication error wtih NTP server: The date and time is earlier than the last saved in the fiscal memory"));
        errorList.put(-111902L, new ErrorInfo(-111902L, "ERR_NTP_WRONG_IP", "Communication error wtih NTP server: Wrong IP address"));
        errorList.put(-112000L, new ErrorInfo(-112000L, "ERR_FP_INVALID_COMMAND", "Fiscal printer error: Fiscal printer invalid command"));
        errorList.put(-112001L, new ErrorInfo(-112001L, "ERR_FP_INVALID_SYNTAX", "Fiscal printer error: Fiscal printer command invalid syntax"));
        errorList.put(-112002L, new ErrorInfo(-112002L, "ERR_FP_COMMAND_NOT_PERMITTED", "Fiscal printer error: Command is not permitted"));
        errorList.put(-112003L, new ErrorInfo(-112003L, "ERR_FP_OVERFLOW", "Fiscal printer error: Register overflow"));
        errorList.put(-112004L, new ErrorInfo(-112004L, "ERR_FP_WRONG_DATE_TIME", "Fiscal printer error: Wrong date/time"));
        errorList.put(-112005L, new ErrorInfo(-112005L, "ERR_FP_NEEDED_MODE_PC", "Fiscal printer error: PC mode is needed"));
        errorList.put(-112006L, new ErrorInfo(-112006L, "ERR_FP_NO_PAPER", "Fiscal printer error: No paper"));
        errorList.put(-112007L, new ErrorInfo(-112007L, "ERR_FP_COVER_IS_OPEN", "Fiscal printer error: Cover is open"));
        errorList.put(-112008L, new ErrorInfo(-112008L, "ERR_FP_PRINTER_FAILURE", "Fiscal printer error: Printing mechanism error"));
        errorList.put(-112100L, new ErrorInfo(-112100L, "_ERR_FP_SYNTAX_PARAM_BEGIN", "_ERR_FP_SYNTAX_PARAM_BEGIN"));
        errorList.put(-112101L, new ErrorInfo(-112101L, "ERR_FP_SYNTAX_PARAM_1", "Invalid syntax of parameter 1."));
        errorList.put(-112102L, new ErrorInfo(-112102L, "ERR_FP_SYNTAX_PARAM_2", "Invalid syntax of parameter 2."));
        errorList.put(-112103L, new ErrorInfo(-112103L, "ERR_FP_SYNTAX_PARAM_3", "Invalid syntax of parameter 3."));
        errorList.put(-112104L, new ErrorInfo(-112104L, "ERR_FP_SYNTAX_PARAM_4", "Invalid syntax of parameter 4."));
        errorList.put(-112105L, new ErrorInfo(-112105L, "ERR_FP_SYNTAX_PARAM_5", "Invalid syntax of parameter 5."));
        errorList.put(-112106L, new ErrorInfo(-112106L, "ERR_FP_SYNTAX_PARAM_6", "Invalid syntax of parameter 6."));
        errorList.put(-112107L, new ErrorInfo(-112107L, "ERR_FP_SYNTAX_PARAM_7", "Invalid syntax of parameter 7."));
        errorList.put(-112108L, new ErrorInfo(-112108L, "ERR_FP_SYNTAX_PARAM_8", "Invalid syntax of parameter 8."));
        errorList.put(-112109L, new ErrorInfo(-112109L, "ERR_FP_SYNTAX_PARAM_9", "Invalid syntax of parameter 9."));
        errorList.put(-112110L, new ErrorInfo(-112110L, "ERR_FP_SYNTAX_PARAM_10", "Invalid syntax of parameter 10."));
        errorList.put(-112111L, new ErrorInfo(-112111L, "ERR_FP_SYNTAX_PARAM_11", "Invalid syntax of parameter 11."));
        errorList.put(-112112L, new ErrorInfo(-112112L, "ERR_FP_SYNTAX_PARAM_12", "Invalid syntax of parameter 12."));
        errorList.put(-112113L, new ErrorInfo(-112113L, "ERR_FP_SYNTAX_PARAM_13", "Invalid syntax of parameter 13."));
        errorList.put(-112114L, new ErrorInfo(-112114L, "ERR_FP_SYNTAX_PARAM_14", "Invalid syntax of parameter 14."));
        errorList.put(-112115L, new ErrorInfo(-112115L, "ERR_FP_SYNTAX_PARAM_15", "Invalid syntax of parameter 15."));
        errorList.put(-112116L, new ErrorInfo(-112116L, "ERR_FP_SYNTAX_PARAM_16", "Invalid syntax of parameter 16."));
        errorList.put(-112199L, new ErrorInfo(-112199L, "_ERR_FP_SYNTAX_PARAM_END", "_ERR_FP_SYNTAX_PARAM_END"));
        errorList.put(-112200L, new ErrorInfo(-112200L, "_ERR_FP_BAD_PARAM_BEGIN", "_ERR_FP_BAD_PARAM_BEGIN"));
        errorList.put(-112201L, new ErrorInfo(-112201L, "ERR_FP_BAD_PARAM_1", "Bad value of parameter 1."));
        errorList.put(-112202L, new ErrorInfo(-112202L, "ERR_FP_BAD_PARAM_2", "Bad value of parameter 2."));
        errorList.put(-112203L, new ErrorInfo(-112203L, "ERR_FP_BAD_PARAM_3", "Bad value of parameter 3."));
        errorList.put(-112204L, new ErrorInfo(-112204L, "ERR_FP_BAD_PARAM_4", "Bad value of parameter 4."));
        errorList.put(-112205L, new ErrorInfo(-112205L, "ERR_FP_BAD_PARAM_5", "Bad value of parameter 5."));
        errorList.put(-112206L, new ErrorInfo(-112206L, "ERR_FP_BAD_PARAM_6", "Bad value of parameter 6."));
        errorList.put(-112207L, new ErrorInfo(-112207L, "ERR_FP_BAD_PARAM_7", "Bad value of parameter 7."));
        errorList.put(-112208L, new ErrorInfo(-112208L, "ERR_FP_BAD_PARAM_8", "Bad value of parameter 8."));
        errorList.put(-112209L, new ErrorInfo(-112209L, "ERR_FP_BAD_PARAM_9", "Bad value of parameter 9."));
        errorList.put(-112210L, new ErrorInfo(-112210L, "ERR_FP_BAD_PARAM_10", "Bad value of parameter 10."));
        errorList.put(-112211L, new ErrorInfo(-112211L, "ERR_FP_BAD_PARAM_11", "Bad value of parameter 11."));
        errorList.put(-112212L, new ErrorInfo(-112212L, "ERR_FP_BAD_PARAM_12", "Bad value of parameter 12."));
        errorList.put(-112213L, new ErrorInfo(-112213L, "ERR_FP_BAD_PARAM_13", "Bad value of parameter 13."));
        errorList.put(-112214L, new ErrorInfo(-112214L, "ERR_FP_BAD_PARAM_14", "Bad value of parameter 14."));
        errorList.put(-112215L, new ErrorInfo(-112215L, "ERR_FP_BAD_PARAM_15", "Bad value of parameter 15."));
        errorList.put(-112216L, new ErrorInfo(-112216L, "ERR_FP_BAD_PARAM_16", "Bad value of parameter 16."));
        errorList.put(-112299L, new ErrorInfo(-112299L, "_ERR_FP_BAD_PARAM_END", "_ERR_FP_BAD_PARAM_END"));
        errorList.put(-112900L, new ErrorInfo(-112900L, "_ERR_RANGE_EM_BEGIN", "_ERR_RANGE_EM_BEGIN"));
        errorList.put(-112999L, new ErrorInfo(-112999L, "_ERR_RANGE_EM_END", "_ERR_RANGE_EM_END"));
        errorList.put(-113000L, new ErrorInfo(-113000L, "ERR_FLASH_WRONG_ID", "Flash memory error: Reading ID error"));
        errorList.put(-113001L, new ErrorInfo(-113001L, "ERR_FLASH_WRONG_SIZE", "Flash memory error: Sector size error"));
        errorList.put(-114000L, new ErrorInfo(-114000L, "ERR_POS_TERM_CHAN_CLOSED", "POS- terminal error: Communication channel is closed"));
        errorList.put(-118000L, new ErrorInfo(-118000L, "ERR_ECRSRV_NO_SOCKET_OPENED", "ECR server error: The connection socket is not open"));
        errorList.put(-118001L, new ErrorInfo(-118001L, "ERR_ECRSRV_SET_IS_NOT_TAKEN", "ECR server error: The set for this command is not opened"));
        errorList.put(-118002L, new ErrorInfo(-118002L, "ERR_ECRSRV_WRONG_PARAM", "ECR server error: Wrong parameter"));
        errorList.put(-118003L, new ErrorInfo(-118003L, "ERR_ECRSRV_NOT_SEND", "ECR server error: Socket send error. Could not send data to server"));
        errorList.put(-118004L, new ErrorInfo(-118004L, "ERR_ECRSRV_RECV_TMOUT", "ECR server error: Receiving timeout. No data is receivec on time"));
        errorList.put(-118005L, new ErrorInfo(-118005L, "ERR_ECRSRV_SOCKET_CLOSED", "ECR server error: Socket is closed"));
        errorList.put(-118006L, new ErrorInfo(-118006L, "ERR_ECRSRV_UNKNOWN", "ECR server error: Unknown state"));
        errorList.put(-118007L, new ErrorInfo(-118007L, "ERR_ECRSRV_FORBIDEN", "ECR server error: Forbidden operation"));
        errorList.put(-120000L, new ErrorInfo(-120000L, "ERR_PGM_NAME_NOT_UNIQUE", "Programming: Name is not unique!"));
        errorList.put(-120001L, new ErrorInfo(-120001L, "ERR_PGM_OPER_PASS_NOT_UNIQUE", "Programming: Operator password is not unique!"));
        errorList.put(-120002L, new ErrorInfo(-120002L, "ERR_PGM_DATETIME_OUT_OF_RANGE_MIN", "Programming: Date and time is under the range."));
        errorList.put(-120003L, new ErrorInfo(-120003L, "ERR_PGM_DATETIME_OUT_OF_RANGE_MAX", "Programming: Date and time is under the range."));
        errorList.put(-121000L, new ErrorInfo(-121000L, "ERR_SCANNER_GENERAL", "Barcode scanner reading error!"));
        errorList.put(-121001L, new ErrorInfo(-121001L, "ERR_COURIER_EIK_INVALID", "Invalid EIK/EGN number!"));
        errorList.put(-170000L, new ErrorInfo(-170000L, "ERR_USB_HOST_INIT", "USB error: Host init error"));
        errorList.put(-170001L, new ErrorInfo(-170001L, "ERR_USB_NO_DEVICE", "USB error: No device"));
        errorList.put(-170002L, new ErrorInfo(-170002L, "ERR_USB_NO_FILESYSTEM", "USB error: No filesystem"));
        errorList.put(-170003L, new ErrorInfo(-170003L, "ERR_USB_FILE_OPEN", "USB error: File open error"));
        errorList.put(-170004L, new ErrorInfo(-170004L, "ERR_USB_FILE_COPY", "USB error: File copy error"));
        errorList.put(-170005L, new ErrorInfo(-170005L, "ERR_FILE_UNPACK", "USB error: File unpack error"));
        errorList.put(-171000L, new ErrorInfo(-171000L, "ERR_RENTAL_NOT_FOUND", "Rental database: Not found"));
        errorList.put(-171001L, new ErrorInfo(-171001L, "ERR_RENTAL_FULL", "Rental database: Full"));
        errorList.put(-171002L, new ErrorInfo(-171002L, "ERR_RENTAL_POSITION_OCCUPIED", "Rental database: Position is occupied"));
        errorList.put(-171003L, new ErrorInfo(-171003L, "ERR_RENTAL_POSITION_FREE", "Rental database: Position is free"));
        errorList.put(-171004L, new ErrorInfo(-171004L, "ERR_RENTAL_SUBSCRIPTION_ACTIVE", "Rental database: Subscription is active"));
    }
            
    public FDExceptionDatecsX(String message) {
        super(message);
    }

    public FDExceptionDatecsX(long ErrorCode) {
        super(ErrorCode, "");
    }
    
    public FDExceptionDatecsX(long ErrorCode, String message) {
        super(ErrorCode, message);
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage(); 
        if (errorList.containsKey(ErrorCode)){
            msg = msg + ((msg.length() > 0)?" ":"") + errorList.get(ErrorCode).mMessage+" ("+errorList.get(ErrorCode).mStrCode+")";
        } else if (msg.length() == 0) {
            msg = "Unknown error code ("+Long.toString(ErrorCode)+")";
        }
        return msg;
    }

    @Override
    public String getLocalizedMessage() {
        String msg = super.getLocalizedMessage(); 
        if (errorList.containsKey(ErrorCode)){
            msg = msg + ((msg.length() > 0)?" ":"") + errorList.get(ErrorCode).mLocalizedMessage+" ("+errorList.get(ErrorCode).mStrCode+")";
        } else if (msg.length() == 0) {
            msg = "Unknown error code ("+Long.toString(ErrorCode)+")";
        }
        return msg;
    }

    
}
