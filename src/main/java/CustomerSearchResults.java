public class CustomerSearchResults {
    Integer id;
    String fullName;
    String email;
    CustomerSearchResults(Integer id, String fullName, String email) {
        this.id = new Integer(id);
        this.fullName = new String(fullName);
        this.email = new String(email);
    }

    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

