package TremolZFP;
public class HeaderRes {
   /**
    *(Line Number) 1 symbol with value: 
    * - '1' - Header 1 
    * - '2' - Header 2 
    * - '3' - Header 3 
    * - '4' - Header 4 
    * - '5' - Header 5 
    * - '6' - Header 6 
    * - '7' - Header 7
    */
    public OptionHeaderLine OptionHeaderLine;
    public OptionHeaderLine getOptionHeaderLine() {
       return OptionHeaderLine;
    }
    protected void setOptionHeaderLine(OptionHeaderLine value) {
       OptionHeaderLine = value;
    }

   /**
    *TextLength symbols for header lines
    */
    public String HeaderText;
    public String getHeaderText() {
       return HeaderText;
    }
    protected void setHeaderText(String value) {
       HeaderText = value;
    }
}
