package TremolZFP;
public class OperatorNamePasswordRes {
   /**
    *Symbol from 1 to 20 corresponding to the number of operator
    */
    public Double Number;
    public Double getNumber() {
       return Number;
    }
    protected void setNumber(Double value) {
       Number = value;
    }

   /**
    *20 symbols for operator's name
    */
    public String Name;
    public String getName() {
       return Name;
    }
    protected void setName(String value) {
       Name = value;
    }

   /**
    *6 symbols for operator's password
    */
    public String Password;
    public String getPassword() {
       return Password;
    }
    protected void setPassword(String value) {
       Password = value;
    }
}
