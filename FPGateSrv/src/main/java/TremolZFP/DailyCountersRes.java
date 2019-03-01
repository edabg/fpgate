package TremolZFP;
import java.util.Date;
public class DailyCountersRes {
   /**
    *Up to 5 symbols for number of the last report from reset
    */
    public Double LastReportNumFromReset;
    public Double getLastReportNumFromReset() {
       return LastReportNumFromReset;
    }
    protected void setLastReportNumFromReset(Double value) {
       LastReportNumFromReset = value;
    }

   /**
    *Up to 5 symbols for number of the last FM report
    */
    public Double LastFMBlockNum;
    public Double getLastFMBlockNum() {
       return LastFMBlockNum;
    }
    protected void setLastFMBlockNum(Double value) {
       LastFMBlockNum = value;
    }

   /**
    *Up to 5 symbols for number of EJ
    */
    public Double EJNum;
    public Double getEJNum() {
       return EJNum;
    }
    protected void setEJNum(Double value) {
       EJNum = value;
    }

   /**
    *16 symbols for date and time of the last block storage in FM in 
    *format "DD-MM-YYYY HH:MM"
    */
    public Date DateTime;
    public Date getDateTime() {
       return DateTime;
    }
    protected void setDateTime(Date value) {
       DateTime = value;
    }
}
