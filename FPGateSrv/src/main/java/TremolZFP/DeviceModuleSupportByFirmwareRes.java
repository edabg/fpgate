package TremolZFP;
public class DeviceModuleSupportByFirmwareRes {
   /**
    *1 symbol for LAN suppor 
    *- '0' - No 
    * - '1' - Yes
    */
    public OptionLAN OptionLAN;
    public OptionLAN getOptionLAN() {
       return OptionLAN;
    }
    protected void setOptionLAN(OptionLAN value) {
       OptionLAN = value;
    }

   /**
    *1 symbol for WiFi support 
    *- '0' - No 
    * - '1' - Yes
    */
    public OptionWiFi OptionWiFi;
    public OptionWiFi getOptionWiFi() {
       return OptionWiFi;
    }
    protected void setOptionWiFi(OptionWiFi value) {
       OptionWiFi = value;
    }

   /**
    *1 symbol for GPRS support 
    *- '0' - No 
    * - '1' - Yes 
    *BT (Bluetooth) 1 symbol for Bluetooth support 
    *- '0' - No 
    * - '1' - Yes
    */
    public OptionGPRS OptionGPRS;
    public OptionGPRS getOptionGPRS() {
       return OptionGPRS;
    }
    protected void setOptionGPRS(OptionGPRS value) {
       OptionGPRS = value;
    }

   /**
    *(Bluetooth) 1 symbol for Bluetooth support 
    *- '0' - No 
    * - '1' - Yes
    */
    public OptionBT OptionBT;
    public OptionBT getOptionBT() {
       return OptionBT;
    }
    protected void setOptionBT(OptionBT value) {
       OptionBT = value;
    }
}
