package TremolZFP;
    public enum OptionTypeReceipt {
        Invoice_Credit_note_receipt_Postponed_Printing("7"),
        Invoice_Credit_note_receipt_printing_step_by_step("5"),
        Invoice_sales_receipt_Postponed_Printing("3"),
        Invoice_sales_receipt_printing_step_by_step("1"),
        Sales_receipt_Postponed_Printing("2"),
        Sales_receipt_printing_step_by_step("0"),
        Storno_receipt_Postponed_Printing("6"),
        Storno_receipt_printing_step_by_step("4");

        private final String value;
        private OptionTypeReceipt(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
