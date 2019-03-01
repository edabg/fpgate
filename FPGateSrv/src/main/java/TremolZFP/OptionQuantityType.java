package TremolZFP;
    public enum OptionQuantityType {
        Availability_of_PLU_stock_is_not_monitored("0"),
        Disable_negative_quantity("1"),
        Enable_negative_quantity("2");

        private final String value;
        private OptionQuantityType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
