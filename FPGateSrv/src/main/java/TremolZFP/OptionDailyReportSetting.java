package TremolZFP;
    public enum OptionDailyReportSetting {
        Automatic_Z_report_without_printing("1"),
        Z_report_with_printing("0");

        private final String value;
        private OptionDailyReportSetting(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
