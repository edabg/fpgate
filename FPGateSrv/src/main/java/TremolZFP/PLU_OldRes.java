package TremolZFP;
import java.util.Date;
public class PLU_OldRes {
   /**
    *5 symbols for article number format #####
    */
    public Double PLUNum;
    public Double getPLUNum() {
       return PLUNum;
    }
    protected void setPLUNum(Double value) {
       PLUNum = value;
    }

   /**
    *20 symbols for article name
    */
    public String PLUName;
    public String getPLUName() {
       return PLUName;
    }
    protected void setPLUName(String value) {
       PLUName = value;
    }

   /**
    *Up to 11 symbols for article price
    */
    public Double Price;
    public Double getPrice() {
       return Price;
    }
    protected void setPrice(Double value) {
       Price = value;
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
    *Up to 13 symbols for turnover by this article
    */
    public Double Turnover;
    public Double getTurnover() {
       return Turnover;
    }
    protected void setTurnover(Double value) {
       Turnover = value;
    }

   /**
    *Up to 13 symbols for sold quantity
    */
    public Double QuantitySold;
    public Double getQuantitySold() {
       return QuantitySold;
    }
    protected void setQuantitySold(Double value) {
       QuantitySold = value;
    }

   /**
    *Up to 5 symbols for the number of last Z Report
    */
    public Double LastZReportNumber;
    public Double getLastZReportNumber() {
       return LastZReportNumber;
    }
    protected void setLastZReportNumber(Double value) {
       LastZReportNumber = value;
    }

   /**
    *16 symbols for date and hour on last Z Report in format 
    * DD-MM-YYYY HH:MM
    */
    public Date LastZReportDate;
    public Date getLastZReportDate() {
       return LastZReportDate;
    }
    protected void setLastZReportDate(Date value) {
       LastZReportDate = value;
    }

   /**
    *BelongToDepNumber + 80h, 1 symbol for article department 
    *attachment, formed in the following manner: 
    *BelongToDepNumber[HEX] + 80h example: Dep01 = 81h, Dep02 
    *= 82h … Dep19 = 93h 
    *Department range from 1 to 127
    */
    public int BelongToDepNumber;
    public int getBelongToDepNumber() {
       return BelongToDepNumber;
    }
    protected void setBelongToDepNumber(int value) {
       BelongToDepNumber = value;
    }
}
