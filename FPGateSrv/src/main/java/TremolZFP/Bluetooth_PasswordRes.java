package TremolZFP;
public class Bluetooth_PasswordRes {
   /**
    *(Length) Up to 3 symbols for the BT password length
    */
    public Double PassLength;
    public Double getPassLength() {
       return PassLength;
    }
    protected void setPassLength(Double value) {
       PassLength = value;
    }

   /**
    *Up to 100 symbols for the BT password
    */
    public String Password;
    public String getPassword() {
       return Password;
    }
    protected void setPassword(String value) {
       Password = value;
    }
}
