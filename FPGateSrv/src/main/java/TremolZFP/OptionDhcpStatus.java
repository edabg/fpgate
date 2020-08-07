package TremolZFP;
    public enum OptionDhcpStatus {
        Disabled("0"),
        Enabled("1");

        private final String value;
        private OptionDhcpStatus(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
