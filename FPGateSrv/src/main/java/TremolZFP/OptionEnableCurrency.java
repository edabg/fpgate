package TremolZFP;
    public enum OptionEnableCurrency {
        No("0"),
        Yes("1");

        private final String value;
        private OptionEnableCurrency(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
