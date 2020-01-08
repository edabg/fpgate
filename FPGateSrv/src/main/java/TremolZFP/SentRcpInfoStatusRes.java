package TremolZFP;
import java.util.Date;
public class SentRcpInfoStatusRes {
   /**
    *Up to 6 symbols for the last sent receipt number to NRA server
    */
    public Double LastSentRcpNum;
    public Double getLastSentRcpNum() {
       return LastSentRcpNum;
    }
    protected void setLastSentRcpNum(Double value) {
       LastSentRcpNum = value;
    }

   /**
    *16 symbols for the date and time of the last sent receipt to NRA 
    *server in format DD-MM-YYYY HH:MM
    */
    public Date LastSentRcpDateTime;
    public Date getLastSentRcpDateTime() {
       return LastSentRcpDateTime;
    }
    protected void setLastSentRcpDateTime(Date value) {
       LastSentRcpDateTime = value;
    }

   /**
    *Up to 6 symbols for the first unsent receipt number to NRA server
    */
    public Double FirstUnsentRcpNum;
    public Double getFirstUnsentRcpNum() {
       return FirstUnsentRcpNum;
    }
    protected void setFirstUnsentRcpNum(Double value) {
       FirstUnsentRcpNum = value;
    }

   /**
    *16 symbols for the date and time of the first unsent receipt to
    */
    public Date FirstUnsentRcpDateTime;
    public Date getFirstUnsentRcpDateTime() {
       return FirstUnsentRcpDateTime;
    }
    protected void setFirstUnsentRcpDateTime(Date value) {
       FirstUnsentRcpDateTime = value;
    }

   /**
    *Up to 100 symbols for error message from NRA server, if exist
    */
    public String NRA_ErrorMessage;
    public String getNRA_ErrorMessage() {
       return NRA_ErrorMessage;
    }
    protected void setNRA_ErrorMessage(String value) {
       NRA_ErrorMessage = value;
    }
}
