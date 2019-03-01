package TremolZFP;
import java.util.Date;
public class DepartmentAllRes {
   /**
    *2 symbols for department number in format: ##
    */
    public Double DepNum;
    public Double getDepNum() {
       return DepNum;
    }
    protected void setDepNum(Double value) {
       DepNum = value;
    }

   /**
    *20 symbols for department name
    */
    public String DepName;
    public String getDepName() {
       return DepName;
    }
    protected void setDepName(String value) {
       DepName = value;
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
    *Up to 10 symbols for department price
    */
    public Double Price;
    public Double getPrice() {
       return Price;
    }
    protected void setPrice(Double value) {
       Price = value;
    }

   /**
    *1 symbol for Department flags with next value:  
    *- '0' - Free price disabled  
    *- '1' - Free price enabled  
    *- '2' - Limited price  
    *- '4' - Free price disabled for single transaction  
    *- '5' - Free price enabled for single transaction  
    *- '6' - Limited price for single transaction
    */
    public OptionDepPrice OptionDepPrice;
    public OptionDepPrice getOptionDepPrice() {
       return OptionDepPrice;
    }
    protected void setOptionDepPrice(OptionDepPrice value) {
       OptionDepPrice = value;
    }

   /**
    *Up to 13 symbols for accumulated turnover of the article
    */
    public Double TurnoverAmount;
    public Double getTurnoverAmount() {
       return TurnoverAmount;
    }
    protected void setTurnoverAmount(Double value) {
       TurnoverAmount = value;
    }

   /**
    *Up to 13 symbols for sold quantity of the department
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
    *"DD-MM-YYYY HH:MM"
    */
    public Date LastZReportDate;
    public Date getLastZReportDate() {
       return LastZReportDate;
    }
    protected void setLastZReportDate(Date value) {
       LastZReportDate = value;
    }
}
