package TremolZFP;
public class CustomerDataRes {
   /**
    *(Customer Number) 4 symbols for customer number in format ####
    */
    public Double CustomerNum;
    public Double getCustomerNum() {
       return CustomerNum;
    }
    protected void setCustomerNum(Double value) {
       CustomerNum = value;
    }

   /**
    *(Company name) 26 symbols for customer name
    */
    public String CustomerCompanyName;
    public String getCustomerCompanyName() {
       return CustomerCompanyName;
    }
    protected void setCustomerCompanyName(String value) {
       CustomerCompanyName = value;
    }

   /**
    *(Buyer Name) 16 symbols for Buyer name
    */
    public String CustomerFullName;
    public String getCustomerFullName() {
       return CustomerFullName;
    }
    protected void setCustomerFullName(String value) {
       CustomerFullName = value;
    }

   /**
    *13 symbols for VAT number on customer
    */
    public String VATNumber;
    public String getVATNumber() {
       return VATNumber;
    }
    protected void setVATNumber(String value) {
       VATNumber = value;
    }

   /**
    *13 symbols for customer Unique Identification Code
    */
    public String UIC;
    public String getUIC() {
       return UIC;
    }
    protected void setUIC(String value) {
       UIC = value;
    }

   /**
    *30 symbols for address on customer
    */
    public String Address;
    public String getAddress() {
       return Address;
    }
    protected void setAddress(String value) {
       Address = value;
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
}
