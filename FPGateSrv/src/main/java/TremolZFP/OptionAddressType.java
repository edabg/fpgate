package TremolZFP;
    public enum OptionAddressType {
        DNS_address("5"),
        Gateway_address("4"),
        IP_address("2"),
        Subnet_Mask("3");

        private final String value;
        private OptionAddressType(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
