package TremolZFP;
    public enum OptionCodePayment {
        Bank("8"),
        Card("7"),
        Check("1"),
        Damage("6"),
        Packaging("4"),
        Programming_Name1("9"),
        Programming_Name2(":"),
        Service("5"),
        Talon("2"),
        V_Talon("3");

        private final String value;
        private OptionCodePayment(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
