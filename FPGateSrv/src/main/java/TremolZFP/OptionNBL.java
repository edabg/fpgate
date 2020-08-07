package TremolZFP;
    public enum OptionNBL {
        No("0"),
        Yes("1");

        private final String value;
        private OptionNBL(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
