package TremolZFP;
    public enum OptionVATClass {
        Forbidden("*"),
        VAT_Class_0("А"),
        VAT_Class_1("Б"),
        VAT_Class_2("В"),
        VAT_Class_3("Г"),
        VAT_Class_4("Д"),
        VAT_Class_5("Е"),
        VAT_Class_6("Ж"),
        VAT_Class_7("З");

        private final String value;
        private OptionVATClass(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
