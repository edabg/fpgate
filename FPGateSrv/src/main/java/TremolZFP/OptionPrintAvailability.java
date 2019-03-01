package TremolZFP;
    public enum OptionPrintAvailability {
        No("0"),
        Yes("1");

        private final String value;
        private OptionPrintAvailability(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
