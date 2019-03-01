package TremolZFP;
import java.util.Date;
public class RegistrationInfoRes {
   /**
    *13 symbols for Unique Identification Code
    */
    public String UIC;
    public String getUIC() {
       return UIC;
    }
    protected void setUIC(String value) {
       UIC = value;
    }

   /**
    *1 symbol for type of Unique Identification Code: 
    * - '0' - Bulstat 
    * - '1' - EGN 
    * - '2' - Foreigner Number 
    * - '3' - NRA Official Number
    */
    public OptionUICType OptionUICType;
    public OptionUICType getOptionUICType() {
       return OptionUICType;
    }
    protected void setOptionUICType(OptionUICType value) {
       OptionUICType = value;
    }

   /**
    *Register number on the Fiscal device from NRA
    */
    public String NRARegistrationNumber;
    public String getNRARegistrationNumber() {
       return NRARegistrationNumber;
    }
    protected void setNRARegistrationNumber(String value) {
       NRARegistrationNumber = value;
    }

   /**
    *Date of registration in NRA
    */
    public Date NRARegistrationDate;
    public Date getNRARegistrationDate() {
       return NRARegistrationDate;
    }
    protected void setNRARegistrationDate(Date value) {
       NRARegistrationDate = value;
    }
}
