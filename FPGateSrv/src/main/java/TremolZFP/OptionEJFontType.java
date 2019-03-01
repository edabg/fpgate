package TremolZFP;
    public enum OptionEJFontType {
        Low_Font("1"),
        Normal_Font("0");

        private final String value;
        private OptionEJFontType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
