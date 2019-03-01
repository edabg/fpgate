package TremolZFP;
    public enum OptionStorageReport {
        To_PC("j0"),
        To_SD_card("j4"),
        To_USB_Flash_Drive("j2");

        private final String value;
        private OptionStorageReport(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
