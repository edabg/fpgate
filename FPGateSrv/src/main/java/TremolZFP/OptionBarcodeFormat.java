package TremolZFP;
    public enum OptionBarcodeFormat {
        NNNNcWWWWW("0"),
        NNNNNWWWWW("1");

        private final String value;
        private OptionBarcodeFormat(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
