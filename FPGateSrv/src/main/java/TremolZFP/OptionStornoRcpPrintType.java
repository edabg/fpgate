package TremolZFP;
    public enum OptionStornoRcpPrintType {
        Buffered_Printing("D"),
        Postponed_Printing("B"),
        Step_by_step_printing("@");

        private final String value;
        private OptionStornoRcpPrintType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
