package TremolZFP;
public class PLUbarcodeRes {
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
    *13 symbols for article barcode
    */
    public String Barcode;
    public String getBarcode() {
       return Barcode;
    }
    protected void setBarcode(String value) {
       Barcode = value;
    }
}
