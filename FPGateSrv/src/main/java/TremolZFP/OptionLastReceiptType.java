package TremolZFP;
    public enum OptionLastReceiptType {
        Invoice_Credit_note("5"),
        Invoice_sales_receipt("1"),
        Non_fiscal_receipt("2"),
        Sales_receipt_printing("0"),
        Storno_receipt("4");

        private final String value;
        private OptionLastReceiptType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
