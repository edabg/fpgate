package TremolZFP;
    public enum OptionSign {
        Correction("-"),
        Sale("+");

        private final String value;
        private OptionSign(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
