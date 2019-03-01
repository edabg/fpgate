package TremolZFP;
    public enum OptionPrintVAT {
        No("0"),
        Yes("1");

        private final String value;
        private OptionPrintVAT(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
