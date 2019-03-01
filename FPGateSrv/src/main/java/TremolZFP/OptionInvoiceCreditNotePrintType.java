package TremolZFP;
    public enum OptionInvoiceCreditNotePrintType {
        Buffered_Printing("E"),
        Postponed_Printing("C"),
        Step_by_step_printing("A");

        private final String value;
        private OptionInvoiceCreditNotePrintType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
