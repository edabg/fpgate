package TremolZFP;
    public enum OptionWiFi {
        No("0"),
        Yes("1");

        private final String value;
        private OptionWiFi(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
