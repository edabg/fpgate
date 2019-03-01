package TremolZFP;
    public enum OptionPrintLogo {
        No("0"),
        Yes("1");

        private final String value;
        private OptionPrintLogo(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
