package TremolZFP;
    public enum OptionArticleReportType {
        Brief("0"),
        Detailed("1");

        private final String value;
        private OptionArticleReportType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
