package TremolZFP;
import java.util.Date;
public class PLUallDataRes {
   /**
    *5 symbols for article number with leading zeroes in format: #####
    */
    public Double PLUNum;
    public Double getPLUNum() {
       return PLUNum;
    }
    protected void setPLUNum(Double value) {
       PLUNum = value;
    }

   /**
    *34 symbols for article name, new line=0x7C.
    */
    public String PLUName;
    public String getPLUName() {
       return PLUName;
    }
    protected void setPLUName(String value) {
       PLUName = value;
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
    *1 symbol for flags = 0x80 + FlagSinglTr + FlagQTY + OptionPrice 
    *Where  
    *OptionPrice: 
    *0x00 - for free price is disable valid only programmed price 
    *0x01 - for free price is enable 
    *0x02 - for limited price 
    *FlagQTY: 
    *0x00 - for availability of PLU stock is not monitored 
    *0x04 - for disable negative quantity 
    *0x08 - for enable negative quantity 
    *FlagSingleTr: 
    *0x00 - no single transaction 
    *0x10 - single transaction is active
    */
    public byte FlagsPricePLU;
    public byte getFlagsPricePLU() {
       return FlagsPricePLU;
    }
    protected void setFlagsPricePLU(byte value) {
       FlagsPricePLU = value;
    }

   /**
    *1 character for VAT class: 
    * - 'А' - VAT Class 0 
    * - 'Б' - VAT Class 1 
    * - 'В' - VAT Class 2 
    * - 'Г' - VAT Class 3 
    * - 'Д' - VAT Class 4 
    * - 'Е' - VAT Class 5 
    * - 'Ж' - VAT Class 6 
    * - 'З' - VAT Class 7 
    * - '*' - Forbidden
    */
    public OptionVATClass OptionVATClass;
    public OptionVATClass getOptionVATClass() {
       return OptionVATClass;
    }
    protected void setOptionVATClass(OptionVATClass value) {
       OptionVATClass = value;
    }

   /**
    *BelongToDepNumber + 80h, 1 symbol for PLU department 
    *attachment = 0x80 … 0x93
    */
    public int BelongToDepNumber;
    public int getBelongToDepNumber() {
       return BelongToDepNumber;
    }
    protected void setBelongToDepNumber(int value) {
       BelongToDepNumber = value;
    }

   /**
    *Up to 13 symbols for PLU accumulated turnover
    */
    public Double TurnoverAmount;
    public Double getTurnoverAmount() {
       return TurnoverAmount;
    }
    protected void setTurnoverAmount(Double value) {
       TurnoverAmount = value;
    }

   /**
    *Up to 13 symbols for Sales quantity of the article
    */
    public Double SoldQuantity;
    public Double getSoldQuantity() {
       return SoldQuantity;
    }
    protected void setSoldQuantity(Double value) {
       SoldQuantity = value;
    }

   /**
    *Up to 13 symbols for accumulated storno amount
    */
    public Double StornoAmount;
    public Double getStornoAmount() {
       return StornoAmount;
    }
    protected void setStornoAmount(Double value) {
       StornoAmount = value;
    }

   /**
    *Up to 13 symbols for accumulated storno quantiy
    */
    public Double StornoQuantity;
    public Double getStornoQuantity() {
       return StornoQuantity;
    }
    protected void setStornoQuantity(Double value) {
       StornoQuantity = value;
    }

   /**
    *Up to 5 symbols for the number of the last article report with zeroing
    */
    public Double LastZReportNumber;
    public Double getLastZReportNumber() {
       return LastZReportNumber;
    }
    protected void setLastZReportNumber(Double value) {
       LastZReportNumber = value;
    }

   /**
    *16 symbols for the date and time of the last article report with zeroing 
    *in format DD-MM-YYYY HH:MM
    */
    public Date LastZReportDate;
    public Date getLastZReportDate() {
       return LastZReportDate;
    }
    protected void setLastZReportDate(Date value) {
       LastZReportDate = value;
    }

   /**
    *(Available Quantity) Up to 11 symbols for quantity in stock
    */
    public Double AvailableQuantity;
    public Double getAvailableQuantity() {
       return AvailableQuantity;
    }
    protected void setAvailableQuantity(Double value) {
       AvailableQuantity = value;
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
