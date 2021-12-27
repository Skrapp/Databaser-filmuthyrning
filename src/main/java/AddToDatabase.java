import db.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

public class AddToDatabase {

    /**Adds Customer to Database
     * @param em EntityManagerFactory
     * @param tfAddCustomerFirstName First name of customer
     * @param tfAddCustomerLastName Last Name of customer
     * @param tfAddCustomerEmail Email of customer
     * @param tfAddCustomerStoreId StoreID of customer
     * @param tfAddCustomerActive If customer is Active
     * @param addressID AdressID of customer
     */
    public void addCustomer (EntityManagerFactory em, TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName,
                             TextField tfAddCustomerEmail, TextField tfAddCustomerStoreId,
                             TextField tfAddCustomerActive, int addressID) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customer = new Customer();

            Instant instant = Instant.now();

            customer.setFirst_name(tfAddCustomerFirstName.getText());
            customer.setLast_name(tfAddCustomerLastName.getText());
            customer.setAddress_id(addressID);
            customer.setStore_id(Integer.parseInt(tfAddCustomerStoreId.getText()));
            customer.setActive(Integer.parseInt(tfAddCustomerActive.getText()));
            customer.setEmail(tfAddCustomerEmail.getText());
            customer.setLast_update(instant);

            entityManager.persist(customer);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    /**
     * @param em EntityManagerFactory
     * @param tfAddCustomerAddress Adress of Customer/Staff
     * @param tfAddCustomerAddress2 Adress2 of Customer/Staff
     * @param tfAddCustomerPostalCode Postal code  of Customer/Staff
     * @param tfAddCustomerDistrict District of Customer/Staff
     * @param tfAddCustomerPhone Phone-number  of Customer/Staff
     * @param cityID City ID of Customer/Staff
     * @return Return Adress ID
     */
    public int addAdressId(EntityManagerFactory em,
                           TextField tfAddCustomerAddress,TextField tfAddCustomerAddress2, TextField tfAddCustomerPostalCode,
                           TextField tfAddCustomerDistrict, TextField tfAddCustomerPhone, int cityID){
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        short addressID = 1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Address address= new Address();

            //Geometry Factory (Coordinates have a constant value)
            Instant instant = Instant.now();
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(1, 2);
            Geometry geometry = null;
            geometry = geometryFactory.createPoint(coord);

            address.setAddress(tfAddCustomerAddress.getText());
            address.setAddress2(tfAddCustomerAddress2.getText());
            address.setDistrict(tfAddCustomerDistrict.getText());
            address.setCity_id(cityID);
            address.setPostal_code(tfAddCustomerPostalCode.getText());
            address.setPhone(tfAddCustomerPhone.getText());
            address.setLocation(geometry);
            address.setLast_update(instant);

            entityManager.persist(address);

            //Checks for adress ID
            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM address WHERE address = '" +tfAddCustomerAddress.getText() +
                    "' AND district = '"+tfAddCustomerDistrict.getText()+"' AND city_id = '"+cityID+"' AND phone ='"+tfAddCustomerPhone.getText()+"' " +
                    "AND postal_code= '"+tfAddCustomerPostalCode.getText()+"'");
            List<Short> chosenID = queryAdressID.getResultList();
            addressID = chosenID.get(0);

            System.out.println(addressID); //Debug

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return addressID;
    }

    /** Adds Staff
     * @param em EntityManagerFactory
     * @param tfStaffAddFirstName First name of staff
     * @param tfStaffAddLastName last Name of staff
     * @param tfStaffAddEmail Email of staff
     * @param tfStaffAddUserName Username of staff
     * @param tfStaffAddPassword Password of staff
     * @param tfStaffAddStoreID Store ID of staff //Make combobox?
     * @param tfStaffAddActive Is staff active
     * @param addressID Adress ID of staff
     */
    public void AddStaff(EntityManagerFactory em, TextField tfStaffAddFirstName, TextField tfStaffAddLastName, TextField tfStaffAddEmail,
                         TextField tfStaffAddUserName, TextField tfStaffAddPassword,TextField tfStaffAddStoreID,TextField tfStaffAddActive, int addressID){
            EntityManager entityManager = em.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                Staff staff = new Staff();

                //Create date + time
                Instant instant = Instant.now();

                staff.setFirstName(tfStaffAddFirstName.getText());
                staff.setLastName(tfStaffAddLastName.getText());
                staff.setEmail(tfStaffAddEmail.getText());
                staff.setUsername(tfStaffAddUserName.getText());
                staff.setPassword(tfStaffAddPassword.getText());
                staff.setLastUpdate(instant);
                staff.setAdressID(addressID);
                staff.setStoreID(Integer.parseInt(tfStaffAddStoreID.getText())); //Error Check
                staff.setActive(Integer.parseInt(tfStaffAddActive.getText())); //Error Check

                entityManager.persist(staff);
                transaction.commit();
            }catch (Exception e){
                if(transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
            }finally {
                entityManager.close();
            }

    }

    /** Adds a movie to Database
     * @param box VBOX containing information to be added
     * @param entityManager EntityManagerFactory
     */
    public void addMovie(VBox box, EntityManagerFactory entityManager) {
        EntityManager emAddMovie = entityManager.createEntityManager();
        EntityTransaction transaction = null;
        String[] sInfoField = new String[box.getChildren().size()];
        try{
            transaction = emAddMovie.getTransaction();
            transaction.begin();

            for(int i = 0; i < box.getChildren().size() - 1; i++) {
                //Checks if child is textbox
                if (box.getChildren().get(i) instanceof TextField) {
                    sInfoField[i] = ((TextField) box.getChildren().get(i)).getText().trim();
                    System.out.println(i + "tf     " + sInfoField[i]); //Debug
                }
                //Checks if child is combobox
                else if (box.getChildren().get(i) instanceof ComboBox) {
                    String cbString = (String) ((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem();
                    sInfoField[i] = getCorrespondingId(cbString, entityManager); // Gets the corresponding ID for whatever is chosen in combobox
                    System.out.println(i + "cb     " + sInfoField[i]); //Debug
                }
            }
            Film newFilm = new Film();
            //Objects for entity Language (new and original) created where corresponding language_id for whatever
            // language chosen is set to the object, object is then set to newFilm Film object.
            Language language = new Language();
            language.setId(Integer.valueOf(sInfoField[13])); //sTextField[13] is language_id value of language combobox
            newFilm.setTitle(sInfoField[1]);
            newFilm.setDescription(sInfoField[6]);
            newFilm.setLanguage(language);
            newFilm.setLastUpdate(Instant.now()); // set last update to current time, obviously. Removed the textbox.
            newFilm.setRentalDuration(Integer.valueOf((int) Long.parseLong(sInfoField[8])));
            newFilm.setRentalRate(BigDecimal.valueOf(Long.parseLong(sInfoField[3])));
            newFilm.setReplacementCost(BigDecimal.valueOf(Long.parseLong(sInfoField[21])));
            newFilm.setRating(sInfoField[10]);
            newFilm.setOriginalLanguage(language);
            newFilm.setReleaseYear(2002); //Ska lägga till textbox
            newFilm.setSpecialFeatures(sInfoField[17]);

            emAddMovie.persist(newFilm);
            newFilm.setCategory(Integer.valueOf(sInfoField[4]), emAddMovie, newFilm);
            emAddMovie.flush();

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            emAddMovie.close();
        }
    }


    /** Get ID of category or Language
     * @param cbString Data from Combobox
     * @param em EntityManagerFactory
     * @return Return ID for Language/Category
     */
    private String getCorrespondingId(String cbString, EntityManagerFactory em) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        String idAsString = "";
        Query queryLanguage;
        Query queryCategory;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            queryLanguage = entityManager.createNativeQuery("SELECT language_id from language WHERE name = '" + cbString + "';");
            queryCategory = entityManager.createNativeQuery("SELECT category_id from category WHERE name = '" + cbString + "';");

            //Remove try?
            try {
                //As integer?
                //If there is result in query -> first result is the requested ID
                if (!queryCategory.getResultList().isEmpty()) idAsString = queryCategory.getResultList().get(0).toString();
                if (!queryLanguage.getResultList().isEmpty()) idAsString = queryLanguage.getResultList().get(0).toString();
                transaction.commit();
            }catch (NoResultException ee) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return idAsString;
    }
}
