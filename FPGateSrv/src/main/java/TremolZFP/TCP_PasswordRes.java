package TremolZFP;
public class TCP_PasswordRes {
   /**
    *(Length) Up to 3 symbols for the password length
    */
    public Double PassLength;
    public Double getPassLength() {
       return PassLength;
    }
    protected void setPassLength(Double value) {
       PassLength = value;
    }

   /**
    *Up to 100 symbols for the TCP password
    */
    public String Password;
    public String getPassword() {
       return Password;
    }
    protected void setPassword(String value) {
       Password = value;
    }
}
