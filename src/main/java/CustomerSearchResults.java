import javafx.scene.control.Hyperlink;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerSearchResults {

    EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    Integer id;
    String fullName;
    String email;
    Hyperlink link;
    AddToDatabase addToDatabase = new AddToDatabase(ENTITY_MANAGER_FACTORY);
    FXBuilder fxBuilder = new FXBuilder();
    Main main = new Main();

    CustomerSearchResults(Integer id, String fullName, String email, Hyperlink link) {
        this.id = new Integer(id);
        this.fullName = new String(fullName);
        this.email = new String(email);
        this.link = new Hyperlink("Edit");
        this.link.setOnAction(e -> {
            fxBuilder.createEditCustomerPopup(id, ENTITY_MANAGER_FACTORY);
        });
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

    public Hyperlink getLink() {
        return link;
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

    public void setLink(Hyperlink link) {
        this.link = link;
    }
}

