package TremolZFP;
public class TCP_AddressesRes {
   /**
    *(Address) 1 symbol with value: 
    * - '2' - IP address 
    * - '3' - Subnet Mask 
    * - '4' - Gateway address 
    * - '5' - DNS address
    */
    public OptionAddressType OptionAddressType;
    public OptionAddressType getOptionAddressType() {
       return OptionAddressType;
    }
    protected void setOptionAddressType(OptionAddressType value) {
       OptionAddressType = value;
    }

   /**
    *15 symbols for the device's addresses
    */
    public String DeviceAddress;
    public String getDeviceAddress() {
       return DeviceAddress;
    }
    protected void setDeviceAddress(String value) {
       DeviceAddress = value;
    }
}
