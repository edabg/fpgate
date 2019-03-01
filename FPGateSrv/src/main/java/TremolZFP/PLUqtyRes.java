package TremolZFP;
public class PLUqtyRes {
   /**
    *5 symbols for article number with leading zeroes in format #####
    */
    public Double PLUNum;
    public Double getPLUNum() {
       return PLUNum;
    }
    protected void setPLUNum(Double value) {
       PLUNum = value;
    }

   /**
    *Up to13 symbols for quantity in stock
    */
    public Double AvailableQuantity;
    public Double getAvailableQuantity() {
       return AvailableQuantity;
    }
    protected void setAvailableQuantity(Double value) {
       AvailableQuantity = value;
    }

   /**
    *1 symbol for Quantity flag with next value:  
    *- '0'- Availability of PLU stock is not monitored  
    *- '1'- Disable negative quantity  
    *- '2'- Enable negative quantity
    */
    public OptionQuantityType OptionQuantityType;
    public OptionQuantityType getOptionQuantityType() {
       return OptionQuantityType;
    }
    protected void setOptionQuantityType(OptionQuantityType value) {
       OptionQuantityType = value;
    }
}
