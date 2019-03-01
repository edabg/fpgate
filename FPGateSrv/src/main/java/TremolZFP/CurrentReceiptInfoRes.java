package TremolZFP;
public class CurrentReceiptInfoRes {
   /**
    *1 symbol with value: 
    * - '0' - No 
    * - '1' - Yes
    */
    public OptionIsReceiptOpened OptionIsReceiptOpened;
    public OptionIsReceiptOpened getOptionIsReceiptOpened() {
       return OptionIsReceiptOpened;
    }
    protected void setOptionIsReceiptOpened(OptionIsReceiptOpened value) {
       OptionIsReceiptOpened = value;
    }

   /**
    *3 symbols for number of sales in format ###
    */
    public Double SalesNumber;
    public Double getSalesNumber() {
       return SalesNumber;
    }
    protected void setSalesNumber(Double value) {
       SalesNumber = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group А
    */
    public Double SubtotalAmountVAT0;
    public Double getSubtotalAmountVAT0() {
       return SubtotalAmountVAT0;
    }
    protected void setSubtotalAmountVAT0(Double value) {
       SubtotalAmountVAT0 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group Б
    */
    public Double SubtotalAmountVAT1;
    public Double getSubtotalAmountVAT1() {
       return SubtotalAmountVAT1;
    }
    protected void setSubtotalAmountVAT1(Double value) {
       SubtotalAmountVAT1 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group В
    */
    public Double SubtotalAmountVAT2;
    public Double getSubtotalAmountVAT2() {
       return SubtotalAmountVAT2;
    }
    protected void setSubtotalAmountVAT2(Double value) {
       SubtotalAmountVAT2 = value;
    }

   /**
    *1 symbol with value: 
    *- '0' - allowed 
    *- '1' - forbidden
    */
    public OptionForbiddenVoid OptionForbiddenVoid;
    public OptionForbiddenVoid getOptionForbiddenVoid() {
       return OptionForbiddenVoid;
    }
    protected void setOptionForbiddenVoid(OptionForbiddenVoid value) {
       OptionForbiddenVoid = value;
    }

   /**
    *1 symbol with value: 
    *- '0' - No 
    *- '1' - Yes
    */
    public OptionVATinReceipt OptionVATinReceipt;
    public OptionVATinReceipt getOptionVATinReceipt() {
       return OptionVATinReceipt;
    }
    protected void setOptionVATinReceipt(OptionVATinReceipt value) {
       OptionVATinReceipt = value;
    }

   /**
    *(Format) 1 symbol with value: 
    * - '1' - Detailed 
    * - '0' - Brief
    */
    public OptionReceiptFormat OptionReceiptFormat;
    public OptionReceiptFormat getOptionReceiptFormat() {
       return OptionReceiptFormat;
    }
    protected void setOptionReceiptFormat(OptionReceiptFormat value) {
       OptionReceiptFormat = value;
    }

   /**
    *1 symbol with value: 
    *- '0' - No 
    *- '1' - Yes
    */
    public OptionInitiatedPayment OptionInitiatedPayment;
    public OptionInitiatedPayment getOptionInitiatedPayment() {
       return OptionInitiatedPayment;
    }
    protected void setOptionInitiatedPayment(OptionInitiatedPayment value) {
       OptionInitiatedPayment = value;
    }

   /**
    *1 symbol with value: 
    *- '0' - No 
    *- '1' - Yes
    */
    public OptionFinalizedPayment OptionFinalizedPayment;
    public OptionFinalizedPayment getOptionFinalizedPayment() {
       return OptionFinalizedPayment;
    }
    protected void setOptionFinalizedPayment(OptionFinalizedPayment value) {
       OptionFinalizedPayment = value;
    }

   /**
    *1 symbol with value: 
    *- '0' - No 
    *- '1' - Yes
    */
    public OptionPowerDownInReceipt OptionPowerDownInReceipt;
    public OptionPowerDownInReceipt getOptionPowerDownInReceipt() {
       return OptionPowerDownInReceipt;
    }
    protected void setOptionPowerDownInReceipt(OptionPowerDownInReceipt value) {
       OptionPowerDownInReceipt = value;
    }

   /**
    *(Receipt and Printing type) 1 symbol with value: 
    * - '0' - Sales receipt printing step by step 
    * - '2' - Sales receipt Postponed Printing 
    * - '4' - Storno receipt printing step by step 
    * - '6' - Storno receipt Postponed Printing 
    * - '1' - Invoice sales receipt printing step by step 
    * - '3' - Invoice sales receipt Postponed Printing 
    * - '5' - Invoice Credit note receipt printing step by step 
    * - '7' - Invoice Credit note receipt Postponed Printing
    */
    public OptionTypeReceipt OptionTypeReceipt;
    public OptionTypeReceipt getOptionTypeReceipt() {
       return OptionTypeReceipt;
    }
    protected void setOptionTypeReceipt(OptionTypeReceipt value) {
       OptionTypeReceipt = value;
    }

   /**
    *Up to 13 symbols the amount of the due change in the stated payment type
    */
    public Double ChangeAmount;
    public Double getChangeAmount() {
       return ChangeAmount;
    }
    protected void setChangeAmount(Double value) {
       ChangeAmount = value;
    }

   /**
    *1 symbol with value: 
    * - '0' - Change In Cash 
    * - '1' - Same As The payment 
    * - '2' - Change In Currency
    */
    public OptionChangeType OptionChangeType;
    public OptionChangeType getOptionChangeType() {
       return OptionChangeType;
    }
    protected void setOptionChangeType(OptionChangeType value) {
       OptionChangeType = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group Г
    */
    public Double SubtotalAmountVAT3;
    public Double getSubtotalAmountVAT3() {
       return SubtotalAmountVAT3;
    }
    protected void setSubtotalAmountVAT3(Double value) {
       SubtotalAmountVAT3 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group Д
    */
    public Double SubtotalAmountVAT4;
    public Double getSubtotalAmountVAT4() {
       return SubtotalAmountVAT4;
    }
    protected void setSubtotalAmountVAT4(Double value) {
       SubtotalAmountVAT4 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group Е
    */
    public Double SubtotalAmountVAT5;
    public Double getSubtotalAmountVAT5() {
       return SubtotalAmountVAT5;
    }
    protected void setSubtotalAmountVAT5(Double value) {
       SubtotalAmountVAT5 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group Ж
    */
    public Double SubtotalAmountVAT6;
    public Double getSubtotalAmountVAT6() {
       return SubtotalAmountVAT6;
    }
    protected void setSubtotalAmountVAT6(Double value) {
       SubtotalAmountVAT6 = value;
    }

   /**
    *Up to 13 symbols for subtotal by VAT group З
    */
    public Double SubtotalAmountVAT7;
    public Double getSubtotalAmountVAT7() {
       return SubtotalAmountVAT7;
    }
    protected void setSubtotalAmountVAT7(Double value) {
       SubtotalAmountVAT7 = value;
    }

   /**
    *6 symbols for fiscal receipt number in format ######
    */
    public Double CurrentReceiptNumber;
    public Double getCurrentReceiptNumber() {
       return CurrentReceiptNumber;
    }
    protected void setCurrentReceiptNumber(Double value) {
       CurrentReceiptNumber = value;
    }
}
