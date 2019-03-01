package TremolZFP;
    public enum OptionExternalDisplay {
        No("N"),
        Yes("Y");

        private final String value;
        private OptionExternalDisplay(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
