package TremolZFP;
public class DetailedPrinterStatusRes {
   /**
    *1 symbol - connection with external display  
    * - 'Y' - Yes 
    * - 'N' - No
    */
    public OptionExternalDisplay OptionExternalDisplay;
    public OptionExternalDisplay getOptionExternalDisplay() {
       return OptionExternalDisplay;
    }
    protected void setOptionExternalDisplay(OptionExternalDisplay value) {
       OptionExternalDisplay = value;
    }

   /**
    *4 symbols for detailed status of printer (only for printers with ASB) 
    *N 
    *byte 
    *N bit status flag 
    *ST0 
    *0 Reserved 
    *1 Reserved 
    *2 Signal level for drawer 
    *3 Printer not ready 
    *4 Reserved 
    *5 Open cover 
    *6 Paper feed status 
    *7 Reserved 
    *   
    *ST1 
    *0 Reserved 
    *1 Reserved 
    *2 Reserved 
    *3 Cutter error 
    *4 Reserved 
    *5 Fatal error
    */
    public String StatPRN;
    public String getStatPRN() {
       return StatPRN;
    }
    protected void setStatPRN(String value) {
       StatPRN = value;
    }

   /**
    *1 symbol with value: 
    * - 'J' - Yes  
    * - ' ' - No
    */
    public byte FlagServiceJumper;
    public byte getFlagServiceJumper() {
       return FlagServiceJumper;
    }
    protected void setFlagServiceJumper(byte value) {
       FlagServiceJumper = value;
    }
}
