package TremolZFP;
    public enum OptionCSVformat {
        No("X"),
        Yes("C");

        private final String value;
        private OptionCSVformat(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
