import javafx.scene.control.Hyperlink;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerSearchResults {

    EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    Integer id;
    String fullName;
    String email;
    Hyperlink linkEdit;
    Hyperlink linkDelete;
    AddToDatabase addToDatabase = new AddToDatabase(ENTITY_MANAGER_FACTORY);
    FXBuilder fxBuilder = new FXBuilder();
    Main main = new Main();

    CustomerSearchResults(Integer id, String fullName, String email, Hyperlink link) {
        this.id = new Integer(id);
        this.fullName = new String(fullName);
        this.email = new String(email);
        this.linkEdit = new Hyperlink("Redigera");
        this.linkEdit.setOnAction(e -> {
            fxBuilder.createEditCustomerPopup(id, ENTITY_MANAGER_FACTORY);
        });
        this.linkDelete = new Hyperlink("Ta bort");
        this.linkDelete.setOnAction(e -> {
            fxBuilder.deleteCustomerPopup(id, ENTITY_MANAGER_FACTORY);
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

    public Hyperlink getLinkEdit() {
        return linkEdit;
    }
    public Hyperlink getLinkDelete() {
        return linkDelete;
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

    public void setLinkEdit(Hyperlink linkEdit) {
        this.linkEdit = linkEdit;
    }
    public void setLinkDelete(Hyperlink linkDelete) {
        this.linkDelete = linkDelete;
    }
}

