package TremolZFP;
    public enum OptionPaymentNum {
        Payment_10("10"),
        Payment_11("11"),
        Payment_9("9");

        private final String value;
        private OptionPaymentNum(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
