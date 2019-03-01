package TremolZFP;
    public enum OptionPrinting {
        No("0"),
        Yes("1");

        private final String value;
        private OptionPrinting(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
