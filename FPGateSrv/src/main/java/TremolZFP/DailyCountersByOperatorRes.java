package TremolZFP;
import java.util.Date;
public class DailyCountersByOperatorRes {
   /**
    *Symbols from 1 to 20 corresponding to operator's number
    */
    public Double OperNum;
    public Double getOperNum() {
       return OperNum;
    }
    protected void setOperNum(Double value) {
       OperNum = value;
    }

   /**
    *Up to 5 symbols for number of the work operators
    */
    public Double WorkOperatorsCounter;
    public Double getWorkOperatorsCounter() {
       return WorkOperatorsCounter;
    }
    protected void setWorkOperatorsCounter(Double value) {
       WorkOperatorsCounter = value;
    }

   /**
    *16 symbols for date and time of the last operator's report in 
    *format DD-MM-YYYY HH:MM
    */
    public Date LastOperatorReportDateTime;
    public Date getLastOperatorReportDateTime() {
       return LastOperatorReportDateTime;
    }
    protected void setLastOperatorReportDateTime(Date value) {
       LastOperatorReportDateTime = value;
    }
}
