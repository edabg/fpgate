package TremolZFP;
    public enum OptionTCPAutoStart {
        No("0"),
        Yes("1");

        private final String value;
        private OptionTCPAutoStart(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
