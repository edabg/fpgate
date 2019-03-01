package TremolZFP;
    public enum OptionChangeType {
        Change_In_Cash("0"),
        Change_In_Currency("2"),
        Same_As_The_payment("1");

        private final String value;
        private OptionChangeType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
