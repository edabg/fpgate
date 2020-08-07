package TremolZFP;
    public enum OptionDailyReport {
        Generate_automatic_Z_report("0"),
        Print_automatic_Z_report("1");

        private final String value;
        private OptionDailyReport(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
