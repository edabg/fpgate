package TremolZFP;
    public enum OptionWorkOperatorCount {
        More("0"),
        One("1");

        private final String value;
        private OptionWorkOperatorCount(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
