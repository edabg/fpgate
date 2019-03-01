package TremolZFP;
    public enum OptionNonFiscalPrintType {
        Postponed_Printing("1"),
        Step_by_step_printing("0");

        private final String value;
        private OptionNonFiscalPrintType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
