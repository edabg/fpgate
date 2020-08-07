package TremolZFP;
    public enum OptionBT {
        No("0"),
        Yes("1");

        private final String value;
        private OptionBT(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
