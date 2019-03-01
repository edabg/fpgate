package TremolZFP;
import java.util.Date;
public class StatusRes {
   /**
    *FM Read only
    */
    public boolean FM_Read_only;
    public boolean getFM_Read_only() {
       return FM_Read_only;
    }
    protected void setFM_Read_only(boolean value) {
       FM_Read_only = value;
    }

   /**
    *Power down in opened fiscal receipt
    */
    public boolean Power_down_in_opened_fiscal_receipt;
    public boolean getPower_down_in_opened_fiscal_receipt() {
       return Power_down_in_opened_fiscal_receipt;
    }
    protected void setPower_down_in_opened_fiscal_receipt(boolean value) {
       Power_down_in_opened_fiscal_receipt = value;
    }

   /**
    *Printer not ready - overheat
    */
    public boolean Printer_not_ready_overheat;
    public boolean getPrinter_not_ready_overheat() {
       return Printer_not_ready_overheat;
    }
    protected void setPrinter_not_ready_overheat(boolean value) {
       Printer_not_ready_overheat = value;
    }

   /**
    *DateTime not set
    */
    public boolean DateTime_not_set;
    public boolean getDateTime_not_set() {
       return DateTime_not_set;
    }
    protected void setDateTime_not_set(boolean value) {
       DateTime_not_set = value;
    }

   /**
    *DateTime wrong
    */
    public boolean DateTime_wrong;
    public boolean getDateTime_wrong() {
       return DateTime_wrong;
    }
    protected void setDateTime_wrong(boolean value) {
       DateTime_wrong = value;
    }

   /**
    *RAM reset
    */
    public boolean RAM_reset;
    public boolean getRAM_reset() {
       return RAM_reset;
    }
    protected void setRAM_reset(boolean value) {
       RAM_reset = value;
    }

   /**
    *Hardware clock error
    */
    public boolean Hardware_clock_error;
    public boolean getHardware_clock_error() {
       return Hardware_clock_error;
    }
    protected void setHardware_clock_error(boolean value) {
       Hardware_clock_error = value;
    }

   /**
    *Printer not ready - no paper
    */
    public boolean Printer_not_ready_no_paper;
    public boolean getPrinter_not_ready_no_paper() {
       return Printer_not_ready_no_paper;
    }
    protected void setPrinter_not_ready_no_paper(boolean value) {
       Printer_not_ready_no_paper = value;
    }

   /**
    *Reports registers Overflow
    */
    public boolean Reports_registers_Overflow;
    public boolean getReports_registers_Overflow() {
       return Reports_registers_Overflow;
    }
    protected void setReports_registers_Overflow(boolean value) {
       Reports_registers_Overflow = value;
    }

   /**
    *Customer report is not zeroed
    */
    public boolean Customer_report_is_not_zeroed;
    public boolean getCustomer_report_is_not_zeroed() {
       return Customer_report_is_not_zeroed;
    }
    protected void setCustomer_report_is_not_zeroed(boolean value) {
       Customer_report_is_not_zeroed = value;
    }

   /**
    *Daily report is not zeroed
    */
    public boolean Daily_report_is_not_zeroed;
    public boolean getDaily_report_is_not_zeroed() {
       return Daily_report_is_not_zeroed;
    }
    protected void setDaily_report_is_not_zeroed(boolean value) {
       Daily_report_is_not_zeroed = value;
    }

   /**
    *Article report is not zeroed
    */
    public boolean Article_report_is_not_zeroed;
    public boolean getArticle_report_is_not_zeroed() {
       return Article_report_is_not_zeroed;
    }
    protected void setArticle_report_is_not_zeroed(boolean value) {
       Article_report_is_not_zeroed = value;
    }

   /**
    *Operator report is not zeroed
    */
    public boolean Operator_report_is_not_zeroed;
    public boolean getOperator_report_is_not_zeroed() {
       return Operator_report_is_not_zeroed;
    }
    protected void setOperator_report_is_not_zeroed(boolean value) {
       Operator_report_is_not_zeroed = value;
    }

   /**
    *Duplicate printed
    */
    public boolean Duplicate_printed;
    public boolean getDuplicate_printed() {
       return Duplicate_printed;
    }
    protected void setDuplicate_printed(boolean value) {
       Duplicate_printed = value;
    }

   /**
    *Opened Non-fiscal Receipt
    */
    public boolean Opened_Non_fiscal_Receipt;
    public boolean getOpened_Non_fiscal_Receipt() {
       return Opened_Non_fiscal_Receipt;
    }
    protected void setOpened_Non_fiscal_Receipt(boolean value) {
       Opened_Non_fiscal_Receipt = value;
    }

   /**
    *Opened Fiscal Receipt
    */
    public boolean Opened_Fiscal_Receipt;
    public boolean getOpened_Fiscal_Receipt() {
       return Opened_Fiscal_Receipt;
    }
    protected void setOpened_Fiscal_Receipt(boolean value) {
       Opened_Fiscal_Receipt = value;
    }

   /**
    *Opened Fiscal Detailed Receipt
    */
    public boolean Opened_Fiscal_Detailed_Receipt;
    public boolean getOpened_Fiscal_Detailed_Receipt() {
       return Opened_Fiscal_Detailed_Receipt;
    }
    protected void setOpened_Fiscal_Detailed_Receipt(boolean value) {
       Opened_Fiscal_Detailed_Receipt = value;
    }

   /**
    *Opened Fiscal Receipt with VAT
    */
    public boolean Opened_Fiscal_Receipt_with_VAT;
    public boolean getOpened_Fiscal_Receipt_with_VAT() {
       return Opened_Fiscal_Receipt_with_VAT;
    }
    protected void setOpened_Fiscal_Receipt_with_VAT(boolean value) {
       Opened_Fiscal_Receipt_with_VAT = value;
    }

   /**
    *Opened Invoice Fiscal Receipt
    */
    public boolean Opened_Invoice_Fiscal_Receipt;
    public boolean getOpened_Invoice_Fiscal_Receipt() {
       return Opened_Invoice_Fiscal_Receipt;
    }
    protected void setOpened_Invoice_Fiscal_Receipt(boolean value) {
       Opened_Invoice_Fiscal_Receipt = value;
    }

   /**
    *SD card near full
    */
    public boolean SD_card_near_full;
    public boolean getSD_card_near_full() {
       return SD_card_near_full;
    }
    protected void setSD_card_near_full(boolean value) {
       SD_card_near_full = value;
    }

   /**
    *SD card full
    */
    public boolean SD_card_full;
    public boolean getSD_card_full() {
       return SD_card_full;
    }
    protected void setSD_card_full(boolean value) {
       SD_card_full = value;
    }

   /**
    *No FM module
    */
    public boolean No_FM_module;
    public boolean getNo_FM_module() {
       return No_FM_module;
    }
    protected void setNo_FM_module(boolean value) {
       No_FM_module = value;
    }

   /**
    *FM error
    */
    public boolean FM_error;
    public boolean getFM_error() {
       return FM_error;
    }
    protected void setFM_error(boolean value) {
       FM_error = value;
    }

   /**
    *FM full
    */
    public boolean FM_full;
    public boolean getFM_full() {
       return FM_full;
    }
    protected void setFM_full(boolean value) {
       FM_full = value;
    }

   /**
    *FM near full
    */
    public boolean FM_near_full;
    public boolean getFM_near_full() {
       return FM_near_full;
    }
    protected void setFM_near_full(boolean value) {
       FM_near_full = value;
    }

   /**
    *Decimal point (1=fract, 0=whole)
    */
    public boolean Decimal_point;
    public boolean getDecimal_point() {
       return Decimal_point;
    }
    protected void setDecimal_point(boolean value) {
       Decimal_point = value;
    }

   /**
    *FM fiscalized
    */
    public boolean FM_fiscalized;
    public boolean getFM_fiscalized() {
       return FM_fiscalized;
    }
    protected void setFM_fiscalized(boolean value) {
       FM_fiscalized = value;
    }

   /**
    *FM produced
    */
    public boolean FM_produced;
    public boolean getFM_produced() {
       return FM_produced;
    }
    protected void setFM_produced(boolean value) {
       FM_produced = value;
    }

   /**
    *Printer: automatic cutting
    */
    public boolean Printer_automatic_cutting;
    public boolean getPrinter_automatic_cutting() {
       return Printer_automatic_cutting;
    }
    protected void setPrinter_automatic_cutting(boolean value) {
       Printer_automatic_cutting = value;
    }

   /**
    *External display: transparent display
    */
    public boolean External_display_transparent_display;
    public boolean getExternal_display_transparent_display() {
       return External_display_transparent_display;
    }
    protected void setExternal_display_transparent_display(boolean value) {
       External_display_transparent_display = value;
    }

   /**
    *Speed is 9600
    */
    public boolean Speed_is_9600;
    public boolean getSpeed_is_9600() {
       return Speed_is_9600;
    }
    protected void setSpeed_is_9600(boolean value) {
       Speed_is_9600 = value;
    }

   /**
    *Drawer: automatic opening
    */
    public boolean Drawer_automatic_opening;
    public boolean getDrawer_automatic_opening() {
       return Drawer_automatic_opening;
    }
    protected void setDrawer_automatic_opening(boolean value) {
       Drawer_automatic_opening = value;
    }

   /**
    *Customer logo included in the receipt
    */
    public boolean Customer_logo_included_in_the_receipt;
    public boolean getCustomer_logo_included_in_the_receipt() {
       return Customer_logo_included_in_the_receipt;
    }
    protected void setCustomer_logo_included_in_the_receipt(boolean value) {
       Customer_logo_included_in_the_receipt = value;
    }

   /**
    *Wrong SIM card
    */
    public boolean Wrong_SIM_card;
    public boolean getWrong_SIM_card() {
       return Wrong_SIM_card;
    }
    protected void setWrong_SIM_card(boolean value) {
       Wrong_SIM_card = value;
    }

   /**
    *Blocking 3 days without mobile operator
    */
    public boolean Blocking_3_days_without_mobile_operator;
    public boolean getBlocking_3_days_without_mobile_operator() {
       return Blocking_3_days_without_mobile_operator;
    }
    protected void setBlocking_3_days_without_mobile_operator(boolean value) {
       Blocking_3_days_without_mobile_operator = value;
    }

   /**
    *No task from NRA
    */
    public boolean No_task_from_NRA;
    public boolean getNo_task_from_NRA() {
       return No_task_from_NRA;
    }
    protected void setNo_task_from_NRA(boolean value) {
       No_task_from_NRA = value;
    }

   /**
    *Wrong SD card
    */
    public boolean Wrong_SD_card;
    public boolean getWrong_SD_card() {
       return Wrong_SD_card;
    }
    protected void setWrong_SD_card(boolean value) {
       Wrong_SD_card = value;
    }

   /**
    *Deregistered
    */
    public boolean Deregistered;
    public boolean getDeregistered() {
       return Deregistered;
    }
    protected void setDeregistered(boolean value) {
       Deregistered = value;
    }

   /**
    *No SIM card
    */
    public boolean No_SIM_card;
    public boolean getNo_SIM_card() {
       return No_SIM_card;
    }
    protected void setNo_SIM_card(boolean value) {
       No_SIM_card = value;
    }

   /**
    *No GPRS Modem
    */
    public boolean No_GPRS_Modem;
    public boolean getNo_GPRS_Modem() {
       return No_GPRS_Modem;
    }
    protected void setNo_GPRS_Modem(boolean value) {
       No_GPRS_Modem = value;
    }

   /**
    *No mobile operator
    */
    public boolean No_mobile_operator;
    public boolean getNo_mobile_operator() {
       return No_mobile_operator;
    }
    protected void setNo_mobile_operator(boolean value) {
       No_mobile_operator = value;
    }

   /**
    *No GPRS service
    */
    public boolean No_GPRS_service;
    public boolean getNo_GPRS_service() {
       return No_GPRS_service;
    }
    protected void setNo_GPRS_service(boolean value) {
       No_GPRS_service = value;
    }

   /**
    *Near end of paper
    */
    public boolean Near_end_of_paper;
    public boolean getNear_end_of_paper() {
       return Near_end_of_paper;
    }
    protected void setNear_end_of_paper(boolean value) {
       Near_end_of_paper = value;
    }

   /**
    *Unsent data for 24 hours
    */
    public boolean Unsent_data_for_24_hours;
    public boolean getUnsent_data_for_24_hours() {
       return Unsent_data_for_24_hours;
    }
    protected void setUnsent_data_for_24_hours(boolean value) {
       Unsent_data_for_24_hours = value;
    }
}
