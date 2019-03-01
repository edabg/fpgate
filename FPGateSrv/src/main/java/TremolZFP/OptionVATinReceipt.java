package TremolZFP;
    public enum OptionVATinReceipt {
        No("0"),
        Yes("1");

        private final String value;
        private OptionVATinReceipt(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
