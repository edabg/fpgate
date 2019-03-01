package TremolZFP;
    public enum OptionReportStorage {
        Printing("J1"),
        SD_card_storage("J4"),
        USB_storage("J2");

        private final String value;
        private OptionReportStorage(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
