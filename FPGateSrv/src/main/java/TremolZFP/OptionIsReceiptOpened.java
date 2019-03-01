package TremolZFP;
    public enum OptionIsReceiptOpened {
        No("0"),
        Yes("1");

        private final String value;
        private OptionIsReceiptOpened(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
