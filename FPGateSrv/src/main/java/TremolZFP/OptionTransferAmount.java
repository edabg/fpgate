package TremolZFP;
    public enum OptionTransferAmount {
        No("0"),
        Yes("1");

        private final String value;
        private OptionTransferAmount(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
