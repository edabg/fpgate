package TremolZFP;
    public enum OptionBTstatus {
        Disabled("0"),
        Enabled("1");

        private final String value;
        private OptionBTstatus(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
