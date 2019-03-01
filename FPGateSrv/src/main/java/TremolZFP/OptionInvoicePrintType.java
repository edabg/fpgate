package TremolZFP;
    public enum OptionInvoicePrintType {
        Buffered_Printing("5"),
        Postponed_Printing("3"),
        Step_by_step_printing("1");

        private final String value;
        private OptionInvoicePrintType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
