package TremolZFP;
public class DailyGeneralRegistersByOperatorRes {
   /**
    *Symbols from 1 to 20 corresponding to operator's number
    */
    public Double OperNum;
    public Double getOperNum() {
       return OperNum;
    }
    protected void setOperNum(Double value) {
       OperNum = value;
    }

   /**
    *Up to 5 symbols for number of customers
    */
    public Double CustomersNum;
    public Double getCustomersNum() {
       return CustomersNum;
    }
    protected void setCustomersNum(Double value) {
       CustomersNum = value;
    }

   /**
    *Up to 5 symbols for number of discounts
    */
    public Double DiscountsNum;
    public Double getDiscountsNum() {
       return DiscountsNum;
    }
    protected void setDiscountsNum(Double value) {
       DiscountsNum = value;
    }

   /**
    *Up to 13 symbols for accumulated amount of discounts
    */
    public Double DiscountsAmount;
    public Double getDiscountsAmount() {
       return DiscountsAmount;
    }
    protected void setDiscountsAmount(Double value) {
       DiscountsAmount = value;
    }

   /**
    *Up to 5 symbols for number ofadditions
    */
    public Double AdditionsNum;
    public Double getAdditionsNum() {
       return AdditionsNum;
    }
    protected void setAdditionsNum(Double value) {
       AdditionsNum = value;
    }

   /**
    *Up to 13 symbols for accumulated amount of additions
    */
    public Double AdditionsAmount;
    public Double getAdditionsAmount() {
       return AdditionsAmount;
    }
    protected void setAdditionsAmount(Double value) {
       AdditionsAmount = value;
    }

   /**
    *Up to 5 symbols for number of corrections
    */
    public Double CorrectionsNum;
    public Double getCorrectionsNum() {
       return CorrectionsNum;
    }
    protected void setCorrectionsNum(Double value) {
       CorrectionsNum = value;
    }

   /**
    *Up to 13 symbols for accumulated amount of corrections
    */
    public Double CorrectionsAmount;
    public Double getCorrectionsAmount() {
       return CorrectionsAmount;
    }
    protected void setCorrectionsAmount(Double value) {
       CorrectionsAmount = value;
    }
}
