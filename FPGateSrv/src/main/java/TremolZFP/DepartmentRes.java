package TremolZFP;
import java.util.Date;
public class DepartmentRes {
   /**
    *3 symbols for department number in format ###
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
    *Up to 13 symbols for accumulated turnover of the article
    */
    public Double Turnover;
    public Double getTurnover() {
       return Turnover;
    }
    protected void setTurnover(Double value) {
       Turnover = value;
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
