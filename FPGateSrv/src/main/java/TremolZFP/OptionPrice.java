package TremolZFP;
    public enum OptionPrice {
        Free_price_is_disable_valid_only_programmed_price("0"),
        Free_price_is_enable("1"),
        Limited_price("2");

        private final String value;
        private OptionPrice(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
