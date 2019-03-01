package TremolZFP;
    public enum OptionReceiptFormat {
        Brief("0"),
        Detailed("1");

        private final String value;
        private OptionReceiptFormat(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
