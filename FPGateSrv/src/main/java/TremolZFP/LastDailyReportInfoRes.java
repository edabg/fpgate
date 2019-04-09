package TremolZFP;
import java.util.Date;
public class LastDailyReportInfoRes {
   /**
    *10 symbols for last Z-report date in DD-MM-YYYY format
    */
    public Date LastZDailyReportDate;
    public Date getLastZDailyReportDate() {
       return LastZDailyReportDate;
    }
    protected void setLastZDailyReportDate(Date value) {
       LastZDailyReportDate = value;
    }

   /**
    *Up to 4 symbols for the number of the last daily report
    */
    public Double LastZDailyReportNum;
    public Double getLastZDailyReportNum() {
       return LastZDailyReportNum;
    }
    protected void setLastZDailyReportNum(Double value) {
       LastZDailyReportNum = value;
    }

   /**
    *Up to 4 symbols for the number of the last RAM reset
    */
    public Double LastRAMResetNum;
    public Double getLastRAMResetNum() {
       return LastRAMResetNum;
    }
    protected void setLastRAMResetNum(Double value) {
       LastRAMResetNum = value;
    }

   /**
    *6 symbols for the total number of receipts in format ######
    */
    public Double TotalReceiptCounter;
    public Double getTotalReceiptCounter() {
       return TotalReceiptCounter;
    }
    protected void setTotalReceiptCounter(Double value) {
       TotalReceiptCounter = value;
    }

   /**
    *Date Time parameter in format: DD-MM-YYYY HH:MM
    */
    public Date DateTimeLastFiscRec;
    public Date getDateTimeLastFiscRec() {
       return DateTimeLastFiscRec;
    }
    protected void setDateTimeLastFiscRec(Date value) {
       DateTimeLastFiscRec = value;
    }

   /**
    *Up to 2 symbols for number of EJ
    */
    public String EJNum;
    public String getEJNum() {
       return EJNum;
    }
    protected void setEJNum(String value) {
       EJNum = value;
    }

   /**
    *(Receipt and Printing type) 1 symbol with value: 
    * - '0' - Sales receipt printing step by step 
    * - '2' - Sales receipt Postponed Printing 
    * - '4' - Storno receipt printing step by step 
    * - '6' - Storno receipt Postponed Printing 
    * - '1' - Invoice sales receipt printing step by step 
    * - '3' - Invoice sales receipt Postponed Printing 
    * - '5' - Invoice Credit note receipt printing step by step 
    * - '7' - Invoice Credit note receipt Postponed Printing
    */
    public OptionTypeReceipt OptionTypeReceipt;
    public OptionTypeReceipt getOptionTypeReceipt() {
       return OptionTypeReceipt;
    }
    protected void setOptionTypeReceipt(OptionTypeReceipt value) {
       OptionTypeReceipt = value;
    }
}
