package TremolZFP;
    public enum OptionUICType {
        Bulstat("0"),
        EGN("1"),
        Foreigner_Number("2"),
        NRA_Official_Number("3");

        private final String value;
        private OptionUICType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
