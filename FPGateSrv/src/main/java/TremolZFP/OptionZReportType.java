package TremolZFP;
    public enum OptionZReportType {
        Automatic("1"),
        Manual("0");

        private final String value;
        private OptionZReportType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
