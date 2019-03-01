package TremolZFP;
    public enum OptionFiscalRcpPrintType {
        Buffered_printing("4"),
        Postponed_printing("2"),
        Step_by_step_printing("0");

        private final String value;
        private OptionFiscalRcpPrintType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
