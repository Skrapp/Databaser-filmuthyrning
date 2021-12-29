import db.Address;
import db.City;
import db.Country;
import db.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Date;
import java.time.Instant;

public class FXBuilder {
    public void createPopUp(Pane box){
        Stage stage = new Stage();
        Popup p = new Popup();
            box.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(box);
        Scene scene2 = new Scene(borderPane);
            p.show(stage);
            stage.setScene(scene2);
            stage.show();
            p.setOnCloseRequest(e -> stage.close());
    }

    public void clearFields(Pane box) {
        for (int i = 0; i < box.getChildren().size(); i++) {
        if (box.getChildren().get(i) instanceof Pane)
        {
            clearFields((Pane) box.getChildren().get(i));
        }
        //See if object is a textField
        if(box.getChildren().get(i) instanceof TextField){
            ((TextField) box.getChildren().get(i)).clear();
        }
        //See if object is combobox
        if(box.getChildren().get(i) instanceof ComboBox){
            ((ComboBox) box.getChildren().get(i)).getSelectionModel().clearSelection();
        }
        //See if object is checkbox
        if(box.getChildren().get(i) instanceof CheckBox){
            ((CheckBox) box.getChildren().get(i)).setSelected(false);

            }
        }
    }

    //Errorpopup
    public void createErrorPopup(String errorMessage){
        Text tError = new Text(errorMessage);

        Button bClose = new Button("Stäng");
        bClose.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        VBox vBoxError = new VBox(tError,bClose);
        vBoxError.setAlignment(Pos.CENTER);
        vBoxError.setPrefWidth(300);
        vBoxError.setPrefHeight(50);
        vBoxError.setSpacing(10);

        createPopUp(vBoxError);
    }

    public void createInformationPopup(TableView tableView){
        VBox vBoxInfo = new VBox(tableView);
        vBoxInfo.setAlignment(Pos.CENTER);
        vBoxInfo.setSpacing(10);

        createPopUp(vBoxInfo);
    }

    public void createEditCustomerPopup(Integer id, EntityManagerFactory em) {
        // id is customerId.
        Main main = new Main();
        Label lAddCustomerHeader = new Label("Redigera kund");
        lAddCustomerHeader.setFont(new Font(40));
        Label lAddCustomerFirstName = new Label("Förnamn");
        Label lAddCustomerLastName = new Label("Efternamn");
        Label lAddCustomerEmail = new Label("Kundmail");
        Label lAddCustomerStoreId = new Label("ButikID");
        Label lAddCustomerRegistered = new Label("Registrerad");
        Label lAddCustomerActive = new Label("Aktiv");

        Label lAddCustomerAddress = new Label("Adress");
        Label lAddCustomerPostalCode = new Label("Postkod");
        Label lAddCustomerDistrict = new Label("Distrikt");
        Label lAddCustomerPhone = new Label("Telefonnummer");
        Label lAddCustomerCity = new Label("Stad");
        Label lAddCustomerCountry = new Label("Land");
        Label lAddCustomerAdress2 = new Label("Adress");
        TextField tfAddCustomerFirstName = new TextField();
        TextField tfAddCustomerLastName = new TextField();
        TextField tfAddCustomerEmail = new TextField();
        TextField tfAddCustomerStoreId = new TextField();
        TextField tfAddCustomerRegistered = new TextField();
        TextField tfAddCustomerActive = new TextField();

        TextField tfAddCustomerAddress = new TextField();
        TextField tfAddCustomerAddress2 = new TextField();
        TextField tfAddCustomerPostalCode = new TextField();
        TextField tfAddCustomerDistrict = new TextField();
        TextField tfAddCustomerPhone = new TextField();
        TextField tfAddCustomerCity = new TextField();
        TextField tfAddCustomerCountrys = new TextField();

        Button bAddCustomer = new Button("Spara");
        Button bCustomerAddCancel = new Button("Avbryt");

        Stage stage = new Stage();
        Popup p = new Popup();
        VBox box = new VBox();

        box.getChildren().addAll(lAddCustomerHeader,lAddCustomerFirstName, tfAddCustomerFirstName, lAddCustomerLastName,
                tfAddCustomerLastName, lAddCustomerEmail, tfAddCustomerEmail, lAddCustomerStoreId,
                tfAddCustomerStoreId, lAddCustomerActive,
                tfAddCustomerActive, lAddCustomerAddress, tfAddCustomerAddress,lAddCustomerAdress2, tfAddCustomerAddress2, lAddCustomerPostalCode,
                tfAddCustomerPostalCode, lAddCustomerDistrict, tfAddCustomerDistrict, lAddCustomerPhone,
                tfAddCustomerPhone, lAddCustomerCity, tfAddCustomerCity, lAddCustomerCountry,
                tfAddCustomerCountrys, bAddCustomer, bCustomerAddCancel);

        bCustomerAddCancel.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, id);
            String firstName = customer.getFirst_name();
            String lastName = customer.getLast_name();
            String email = customer.getEmail();
            int storeId = customer.getStore_id();
            int active = customer.getActive();
            Address address = entityManager.find(Address.class, customer.getAddress_id());
            String address1 = address.getAddress();
            String address2 = address.getAddress2();
            String postalCode = address.getPostal_code();
            String district = address.getDistrict();
            String phone = address.getPhone();
            Date registered = customer.getCreate_date();
            City cityem = entityManager.find(City.class, address.getCity_id());
            String city = cityem.getCity();
            Country countryem = entityManager.find(Country.class, cityem.getCountry_id());
            String country = countryem.getCountry();

            tfAddCustomerFirstName.setText(firstName);
            tfAddCustomerLastName.setText(lastName);
            tfAddCustomerEmail.setText(email);
            tfAddCustomerStoreId.setText(String.valueOf(storeId));
            tfAddCustomerRegistered.setText(String.valueOf(registered));
            tfAddCustomerActive.setText(String.valueOf(active));

            tfAddCustomerAddress.setText(address1);
            tfAddCustomerAddress2.setText(address2);
            tfAddCustomerPostalCode.setText(postalCode);
            tfAddCustomerDistrict.setText(district);
            tfAddCustomerPhone.setText(phone);
            tfAddCustomerCity.setText(city);
            tfAddCustomerCountrys.setText(country);
            entityManager.persist(customer);
            entityManager.persist(address);
            entityManager.persist(cityem);
            entityManager.persist(countryem);
            entityManager.flush();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        bAddCustomer.setOnAction(e -> {
            EntityManager entityManagerAdd = em.createEntityManager();
            EntityTransaction transactionAdd = null;
            try{
                transactionAdd = entityManagerAdd.getTransaction();
                transactionAdd.begin();
                Customer customer = entityManagerAdd.find(Customer.class, id);
                Address address = entityManagerAdd.find(Address.class, customer.getAddress_id());
                City city = entityManagerAdd.find(City.class, address.getCity_id());
                Country country = entityManagerAdd.find(Country.class, city.getCountry_id());
                customer.setFirst_name(tfAddCustomerFirstName.getText());
                customer.setLast_name(tfAddCustomerLastName.getText());
                customer.setEmail(tfAddCustomerEmail.getText());
                customer.setStore_id(Integer.valueOf(tfAddCustomerStoreId.getText()));
                customer.setActive(Integer.valueOf(tfAddCustomerActive.getText()));
                address.setAddress(tfAddCustomerAddress.getText());
                address.setAddress2(tfAddCustomerAddress2.getText());
                address.setLast_update(Instant.now());
                customer.setLast_update(Instant.now());
                city.setLast_update(Instant.now());
                country.setLast_update(Instant.now());
                address.setPostal_code(tfAddCustomerPostalCode.getText());
                address.setDistrict(tfAddCustomerDistrict.getText());
                address.setPhone(tfAddCustomerPhone.getText());
                city.setCity(tfAddCustomerCity.getText());
                country.setCountry(tfAddCustomerCountrys.getText());
                entityManagerAdd.persist(customer);
                entityManagerAdd.persist(address);
                entityManagerAdd.persist(city);
                entityManagerAdd.persist(country);
                entityManagerAdd.flush();
                transactionAdd.commit();
            }catch (Exception ee){
                if(transactionAdd != null){
                    transactionAdd.rollback();
                }
                ee.printStackTrace();
            }finally {
                entityManagerAdd.close();
            }
        });
        box.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(box);
        Scene scene2 = new Scene(borderPane);
        p.show(stage);
        stage.setScene(scene2);
        stage.show();
        p.setOnCloseRequest(e -> stage.close());
    }

    public void createEditMoviePopup(Integer id) {
        // id is customerId.
        Main main = new Main();
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(40));
        Label lMovieAddTitle = new Label("Titel");
        Label lMovieAddDescription = new Label("Beskrivning");
        Label lMovieAddActors = new Label("Skådespelare");
        Label lMovieAddOriginalLanguage = new Label("Originalspråk");
        Label lMovieAddLength = new Label("Längd");
        Label lMovieAddRating = new Label("Betyg");
        Label lMovieAddRentalCost = new Label("Hyreskostnad");
        Label lMovieAddReplacementCost = new Label("Ersättningskostnad");
        Label lMovieAddInStore = new Label("Tillgänglighet");
        Label lMovieAddSpecialFeatures = new Label("Extramaterial");
        Label lMovieAddRentalDuration = new Label("Hyrestid");
        Label lMovieAddLastUpdate = new Label("Senast uppdaterad");
        TextField tfAddTitle = new TextField();
        TextField tfMovieAddTitle = new TextField();
        TextField tfMovieAddDescription = new TextField();
        TextField tfMovieAddActors = new TextField();
        TextField tfMovieAddOriginalLanguage = new TextField();
        TextField tfMovieAddLength = new TextField();
        TextField tfMovieAddRating = new TextField();
        TextField tfMovieAddRentalCost = new TextField();
        TextField tfMovieAddReplacementCost = new TextField();
        TextField tfMovieAddInStore = new TextField();
        TextField tfMovieAddSpecialFeatures = new TextField();
        TextField tfMovieAddRentalDuration = new TextField();
        TextField tfMovieAddLastUpdate = new TextField();
        ComboBox cbMovieAddCategory = new ComboBox(main.olCategory);
        cbMovieAddCategory.setPromptText("Kategori");
        ComboBox cbMovieAddLanguages = new ComboBox(main.olLanguages);
        cbMovieAddLanguages.setPromptText("Språk");
        Button bCreateMovie = new Button("Lägg till");

        Stage stage = new Stage();
        Popup p = new Popup();
        VBox box = new VBox();

        box.getChildren().addAll(lMovieHeader, lMovieAddTitle, tfMovieAddTitle, lMovieAddRentalCost,
                tfMovieAddRentalCost, cbMovieAddCategory,lMovieAddDescription, tfMovieAddDescription,
                lMovieAddLength, tfMovieAddLength, lMovieAddRating,tfMovieAddRating, lMovieAddOriginalLanguage,
                tfMovieAddOriginalLanguage, cbMovieAddLanguages,lMovieAddActors, tfMovieAddActors,
                lMovieAddSpecialFeatures, tfMovieAddSpecialFeatures, lMovieAddRentalDuration, tfMovieAddRentalDuration,
                lMovieAddReplacementCost, tfMovieAddReplacementCost, lMovieAddInStore, tfMovieAddInStore,
                lMovieAddLastUpdate, tfMovieAddLastUpdate, bCreateMovie);

        box.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(box);
        Scene scene2 = new Scene(borderPane);
        p.show(stage);
        stage.setScene(scene2);
        stage.show();
        p.setOnCloseRequest(e -> stage.close());
    }
}
