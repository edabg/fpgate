package TremolZFP;
    public enum OptionLAN {
        No("0"),
        Yes("1");

        private final String value;
        private OptionLAN(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
