package TremolZFP;
    public enum OptionType {
        Defined_from_the_device("2"),
        Over_subtotal("1"),
        Over_transaction_sum("0");

        private final String value;
        private OptionType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
