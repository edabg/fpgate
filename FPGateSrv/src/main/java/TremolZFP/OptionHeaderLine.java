package TremolZFP;
    public enum OptionHeaderLine {
        Header_1("1"),
        Header_2("2"),
        Header_3("3"),
        Header_4("4"),
        Header_5("5"),
        Header_6("6"),
        Header_7("7");

        private final String value;
        private OptionHeaderLine(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
