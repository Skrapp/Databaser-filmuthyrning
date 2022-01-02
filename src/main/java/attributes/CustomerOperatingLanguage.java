package attributes;

public class CustomerOperatingLanguage implements OperatingLanguage{
    private String fullName,
             customerId,
             storeId,
             isActive,
             email,
             phone,
             city,
             address,
             registered,
             update;

    public CustomerOperatingLanguage() {
    }

    public CustomerOperatingLanguage(String fullName, String customerId, String storeId, String isActive, String email, String phone, String city, String address, String registered, String update) {
        this.fullName = fullName;
        this.customerId = customerId;
        this.storeId = storeId;
        this.isActive = isActive;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.registered = registered;
        this.update = update;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
