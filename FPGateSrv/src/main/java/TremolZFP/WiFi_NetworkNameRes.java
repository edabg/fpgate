package TremolZFP;
public class WiFi_NetworkNameRes {
   /**
    *(Length) Up to 3 symbols for the WiFi name length
    */
    public Double WiFiNameLength;
    public Double getWiFiNameLength() {
       return WiFiNameLength;
    }
    protected void setWiFiNameLength(Double value) {
       WiFiNameLength = value;
    }

   /**
    *(Name) Up to 100 symbols for the device's WiFi network name
    */
    public String WiFiNetworkName;
    public String getWiFiNetworkName() {
       return WiFiNetworkName;
    }
    protected void setWiFiNetworkName(String value) {
       WiFiNetworkName = value;
    }
}
