package TremolZFP;
    public enum OptionReportFormat {
        Brief_EJ("J8"),
        Detailed_EJ("J0");

        private final String value;
        private OptionReportFormat(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
