package TremolZFP;
    public enum OptionInitiatedPayment {
        No("0"),
        Yes("1");

        private final String value;
        private OptionInitiatedPayment(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
