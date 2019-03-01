package TremolZFP;
    public enum OptionAutoCut {
        No("0"),
        Yes("1");

        private final String value;
        private OptionAutoCut(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
