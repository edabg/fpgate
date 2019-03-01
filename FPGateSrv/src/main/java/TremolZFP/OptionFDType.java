package TremolZFP;
    public enum OptionFDType {
        ECR_for_online_store_type_11("2"),
        FPr_for_Fuel_type_3("0"),
        FPr_for_online_store_type_21("3"),
        Main_FPr_for_Fuel_system_type_31("1"),
        reset_default_type("*");

        private final String value;
        private OptionFDType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
