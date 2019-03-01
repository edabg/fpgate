package TremolZFP;
    public enum OptionNumber {
        Payment_1("1"),
        Payment_2("2"),
        Payment_3("3"),
        Payment_4("4");

        private final String value;
        private OptionNumber(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
