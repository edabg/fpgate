package TremolZFP;
    public enum OptionZeroing {
        Without_zeroing("X"),
        Zeroing("Z");

        private final String value;
        private OptionZeroing(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
