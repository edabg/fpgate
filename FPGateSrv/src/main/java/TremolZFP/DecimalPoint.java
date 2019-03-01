package TremolZFP;
    public enum DecimalPoint {
        Fractions("2"),
        Whole_numbers("0");

        private final String value;
        private DecimalPoint(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
