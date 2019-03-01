package TremolZFP;
    public enum OptionDeviceType {
        ECR("1"),
        ECR_for_online_store("11"),
        for_FUVAS_device("5"),
        FPr("2"),
        FPr_for_online_store("21"),
        Fuel("3"),
        Fuel_system("31");

        private final String value;
        private OptionDeviceType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
