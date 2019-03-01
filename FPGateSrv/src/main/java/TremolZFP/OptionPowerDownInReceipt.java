package TremolZFP;
    public enum OptionPowerDownInReceipt {
        No("0"),
        Yes("1");

        private final String value;
        private OptionPowerDownInReceipt(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
