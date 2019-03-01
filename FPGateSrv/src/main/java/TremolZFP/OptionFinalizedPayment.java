package TremolZFP;
    public enum OptionFinalizedPayment {
        No("0"),
        Yes("1");

        private final String value;
        private OptionFinalizedPayment(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
