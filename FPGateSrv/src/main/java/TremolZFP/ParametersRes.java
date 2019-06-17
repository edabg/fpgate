package TremolZFP;
public class ParametersRes {
   /**
    *(POS Number) 4 symbols for number of POS in format ####
    */
    public Double POSNum;
    public Double getPOSNum() {
       return POSNum;
    }
    protected void setPOSNum(Double value) {
       POSNum = value;
    }

   /**
    *(Print Logo) 1 symbol of value: 
    * - '1' - Yes 
    * - '0' - No
    */
    public OptionPrintLogo OptionPrintLogo;
    public OptionPrintLogo getOptionPrintLogo() {
       return OptionPrintLogo;
    }
    protected void setOptionPrintLogo(OptionPrintLogo value) {
       OptionPrintLogo = value;
    }

   /**
    *(Auto Open Drawer) 1 symbol of value: 
    * - '1' - Yes 
    * - '0' - No
    */
    public OptionAutoOpenDrawer OptionAutoOpenDrawer;
    public OptionAutoOpenDrawer getOptionAutoOpenDrawer() {
       return OptionAutoOpenDrawer;
    }
    protected void setOptionAutoOpenDrawer(OptionAutoOpenDrawer value) {
       OptionAutoOpenDrawer = value;
    }

   /**
    *(Auto Cut) 1 symbol of value: 
    * - '1' - Yes 
    * - '0' - No
    */
    public OptionAutoCut OptionAutoCut;
    public OptionAutoCut getOptionAutoCut() {
       return OptionAutoCut;
    }
    protected void setOptionAutoCut(OptionAutoCut value) {
       OptionAutoCut = value;
    }

   /**
    *(External Display Management) 1 symbol of value: 
    * - '1' - Manual 
    * - '0' - Auto
    */
    public OptionExternalDispManagement OptionExternalDispManagement;
    public OptionExternalDispManagement getOptionExternalDispManagement() {
       return OptionExternalDispManagement;
    }
    protected void setOptionExternalDispManagement(OptionExternalDispManagement value) {
       OptionExternalDispManagement = value;
    }

   /**
    *(Article Report) 1 symbol of value: 
    * - '1' - Detailed 
    * - '0' - Brief
    */
    public OptionArticleReportType OptionArticleReportType;
    public OptionArticleReportType getOptionArticleReportType() {
       return OptionArticleReportType;
    }
    protected void setOptionArticleReportType(OptionArticleReportType value) {
       OptionArticleReportType = value;
    }

   /**
    *(Enable Currency) 1 symbol of value: 
    * - '1' - Yes 
    * - '0' - No
    */
    public OptionEnableCurrency OptionEnableCurrency;
    public OptionEnableCurrency getOptionEnableCurrency() {
       return OptionEnableCurrency;
    }
    protected void setOptionEnableCurrency(OptionEnableCurrency value) {
       OptionEnableCurrency = value;
    }

   /**
    *(EJ Font) 1 symbol of value: 
    * - '1' - Low Font 
    * - '0' - Normal Font
    */
    public OptionEJFontType OptionEJFontType;
    public OptionEJFontType getOptionEJFontType() {
       return OptionEJFontType;
    }
    protected void setOptionEJFontType(OptionEJFontType value) {
       OptionEJFontType = value;
    }

   /**
    *(Work Operator Count) 1 symbol of value: 
    * - '1' - One 
    * - '0' - More
    */
    public OptionWorkOperatorCount OptionWorkOperatorCount;
    public OptionWorkOperatorCount getOptionWorkOperatorCount() {
       return OptionWorkOperatorCount;
    }
    protected void setOptionWorkOperatorCount(OptionWorkOperatorCount value) {
       OptionWorkOperatorCount = value;
    }
}
