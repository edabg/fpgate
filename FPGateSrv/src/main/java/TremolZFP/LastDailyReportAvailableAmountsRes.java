package TremolZFP;
public class LastDailyReportAvailableAmountsRes {
   /**
    *1 symbol with value: 
    * - '0' - Manual 
    * - '1' - Automatic 
    *ZReportNum 4 symbols for Z report number in format ####
    */
    public OptionZReportType OptionZReportType;
    public OptionZReportType getOptionZReportType() {
       return OptionZReportType;
    }
    protected void setOptionZReportType(OptionZReportType value) {
       OptionZReportType = value;
    }

   /**
    *4 symbols for Z report number in format ####
    */
    public Double ZreportNum;
    public Double getZreportNum() {
       return ZreportNum;
    }
    protected void setZreportNum(Double value) {
       ZreportNum = value;
    }

   /**
    *Up to 13 symbols for available amounts in cash payment
    */
    public Double CashAvailableAmount;
    public Double getCashAvailableAmount() {
       return CashAvailableAmount;
    }
    protected void setCashAvailableAmount(Double value) {
       CashAvailableAmount = value;
    }

   /**
    *Up to 13 symbols for available amounts in currency payment
    */
    public Double CurrencyAvailableAmount;
    public Double getCurrencyAvailableAmount() {
       return CurrencyAvailableAmount;
    }
    protected void setCurrencyAvailableAmount(Double value) {
       CurrencyAvailableAmount = value;
    }
}
