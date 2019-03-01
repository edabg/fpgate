package TremolZFP;
    public enum OptionStornoReason {
        Goods_Claim_or_Goods_return("1"),
        Operator_error("0");

        private final String value;
        private OptionStornoReason(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
