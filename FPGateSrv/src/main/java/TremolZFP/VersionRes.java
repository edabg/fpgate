package TremolZFP;
import java.util.Date;
public class VersionRes {
   /**
    *1 or 2 symbols for type of fiscal device: 
    *- '1' - ECR 
    *- '11' - ECR for online store 
    *- '2' - FPr 
    *- '21' - FPr for online store 
    *- '3' - Fuel 
    *- '31' - Fuel system 
    *- '5' - for FUVAS device
    */
    public OptionDeviceType OptionDeviceType;
    public OptionDeviceType getOptionDeviceType() {
       return OptionDeviceType;
    }
    protected void setOptionDeviceType(OptionDeviceType value) {
       OptionDeviceType = value;
    }

   /**
    *6 symbols for Certification Number of device model
    */
    public String CertificateNum;
    public String getCertificateNum() {
       return CertificateNum;
    }
    protected void setCertificateNum(String value) {
       CertificateNum = value;
    }

   /**
    *16 symbols for Certificate Date and time parameter  
    *in format: DD-MM-YYYY HH:MM
    */
    public Date CertificateDateTime;
    public Date getCertificateDateTime() {
       return CertificateDateTime;
    }
    protected void setCertificateDateTime(Date value) {
       CertificateDateTime = value;
    }

   /**
    *Up to 50 symbols for Model name
    */
    public String Model;
    public String getModel() {
       return Model;
    }
    protected void setModel(String value) {
       Model = value;
    }

   /**
    *Up to 20 symbols for Version name and Check sum
    */
    public String Version;
    public String getVersion() {
       return Version;
    }
    protected void setVersion(String value) {
       Version = value;
    }
}
