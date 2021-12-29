package attributes;

public class CustomerSearch {
    String sFirstName;
    String sId;
    String sEmail;
    String sCity;
    String sAddress;
    String sPhone;
    String sRegistered;
    String sUpdate;

    String sStoreId;

    Boolean isActive;

    public CustomerSearch() {
    }

    public String getsFirstName() {
        return sFirstName;
    }

    public void setsFirstName(String sFirstName) {
        this.sFirstName = sFirstName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsRegistered() {
        return sRegistered;
    }

    public void setsRegistered(String sRegistered) {
        this.sRegistered = sRegistered;
    }

    public String getsUpdate() {
        return sUpdate;
    }

    public void setsUpdate(String sUpdate) {
        this.sUpdate = sUpdate;
    }

    public String getsStoreId() {
        return sStoreId;
    }

    public void setsStoreId(String sStoreId) {
        this.sStoreId = sStoreId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
