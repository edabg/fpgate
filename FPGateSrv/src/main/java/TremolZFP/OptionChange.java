package TremolZFP;
    public enum OptionChange {
        With_Change("0"),
        Without_Change("1");

        private final String value;
        private OptionChange(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
