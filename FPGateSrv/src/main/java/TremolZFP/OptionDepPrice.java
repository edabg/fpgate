package TremolZFP;
    public enum OptionDepPrice {
        Free_price_disabled("0"),
        Free_price_disabled_for_single_transaction("4"),
        Free_price_enabled("1"),
        Free_price_enabled_for_single_transaction("5"),
        Limited_price("2"),
        Limited_price_for_single_transaction("6");

        private final String value;
        private OptionDepPrice(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
