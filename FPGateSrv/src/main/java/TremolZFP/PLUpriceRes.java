package TremolZFP;
public class PLUpriceRes {
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
    *Up to 10 symbols for article price
    */
    public Double Price;
    public Double getPrice() {
       return Price;
    }
    protected void setPrice(Double value) {
       Price = value;
    }

   /**
    *1 symbol for price flag with next value: 
    * - '0'- Free price is disable valid only programmed price 
    * - '1'- Free price is enable 
    * - '2'- Limited price
    */
    public OptionPrice OptionPrice;
    public OptionPrice getOptionPrice() {
       return OptionPrice;
    }
    protected void setOptionPrice(OptionPrice value) {
       OptionPrice = value;
    }
}
