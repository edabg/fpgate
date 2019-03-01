package TremolZFP;
    public enum OptionSingleTransaction {
        Active_Single_transaction_in_receipt("1"),
        Inactive_default_value("0");

        private final String value;
        private OptionSingleTransaction(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
