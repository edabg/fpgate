package TremolZFP;
    public enum OptionForbiddenVoid {
        allowed("0"),
        forbidden("1");

        private final String value;
        private OptionForbiddenVoid(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
