package TremolZFP;
public class SerialAndFiscalNumsRes {
   /**
    *8 symbols for individual number of the fiscal device
    */
    public String SerialNumber;
    public String getSerialNumber() {
       return SerialNumber;
    }
    protected void setSerialNumber(String value) {
       SerialNumber = value;
    }

   /**
    *8 symbols for individual number of the fiscal memory
    */
    public String FMNumber;
    public String getFMNumber() {
       return FMNumber;
    }
    protected void setFMNumber(String value) {
       FMNumber = value;
    }
}
