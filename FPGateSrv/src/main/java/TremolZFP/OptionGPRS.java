package TremolZFP;
    public enum OptionGPRS {
        No("0"),
        Yes("1");

        private final String value;
        private OptionGPRS(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
