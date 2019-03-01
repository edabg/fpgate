package TremolZFP;
    public enum OptionSubtotal {
        No("0"),
        Yes("1");

        private final String value;
        private OptionSubtotal(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
