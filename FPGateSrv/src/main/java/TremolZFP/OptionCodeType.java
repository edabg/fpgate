package TremolZFP;
    public enum OptionCodeType {
        CODABAR("6"),
        CODE_128("I"),
        CODE_39("4"),
        CODE_93("H"),
        EAN_13("2"),
        EAN_8("3"),
        ITF("5"),
        UPC_A("0"),
        UPC_E("1");

        private final String value;
        private OptionCodeType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
