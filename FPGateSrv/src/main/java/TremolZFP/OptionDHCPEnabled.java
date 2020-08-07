package TremolZFP;
    public enum OptionDHCPEnabled {
        Disabled("0"),
        Enabled("1");

        private final String value;
        private OptionDHCPEnabled(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
