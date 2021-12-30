import db.*;
import javafx.scene.control.Alert;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddToDatabase {

    EntityManagerFactory em;

    public AddToDatabase(EntityManagerFactory em) {
        this.em = em;
    }

    /**Adds Customer to Database
     * @param tfAddCustomerFirstName First name of customer
     * @param tfAddCustomerLastName Last Name of customer
     * @param tfAddCustomerEmail Email of customer
     * @param tfAddCustomerStoreId StoreID of customer
     * @param tfAddCustomerActive If customer is Active
     * @param addressID AdressID of customer
     */
    public void addCustomer (TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName,
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
     * @param tfAddCustomerAddress Adress of Customer/Staff
     * @param tfAddCustomerAddress2 Adress2 of Customer/Staff
     * @param tfAddCustomerPostalCode Postal code  of Customer/Staff
     * @param tfAddCustomerDistrict District of Customer/Staff
     * @param tfAddCustomerPhone Phone-number  of Customer/Staff
     * @param cityID City ID of Customer/Staff
     * @return Return Adress ID
     */
    public int addAdressId(TextField tfAddCustomerAddress,TextField tfAddCustomerAddress2, TextField tfAddCustomerPostalCode,
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
     * @param tfStaffAddFirstName First name of staff
     * @param tfStaffAddLastName last Name of staff
     * @param tfStaffAddEmail Email of staff
     * @param tfStaffAddUserName Username of staff
     * @param tfStaffAddPassword Password of staff
     * @param tfStaffAddStoreID Store ID of staff //Make combobox?
     * @param tfStaffAddActive Is staff active
     * @param addressID Adress ID of staff
     */
    public void AddStaff(TextField tfStaffAddFirstName, TextField tfStaffAddLastName, TextField tfStaffAddEmail,
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
     * @param em EntityManagerFactory
     */
    public void addMovie(VBox box, EntityManagerFactory em) {
        EntityManager emAddMovie = em.createEntityManager();
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
                    sInfoField[i] = getCorrespondingId(cbString); // Gets the corresponding ID for whatever is chosen in combobox
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
     * @return Return ID for Language/Category
     */
    private String getCorrespondingId(String cbString) {
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

    /**Check if country exist in database, if not: add a new country
     * @param tfAddCountryName Country name
     * @return returnerar countryID
     */
    public int addCountryID(TextField tfAddCountryName){
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        short countryId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Country country = new Country();
            Instant instant = Instant.now();

            String Country = tfAddCountryName.getText();

            Query query = entityManager.createNativeQuery("SELECT country_id FROM country WHERE country = '" + Country + "'");

            //If list is empty add country
            if (query.getResultList().isEmpty()) {
                country.setCountry(Country);
                country.setLast_update(instant);
                entityManager.persist(country);
                Query query1 = entityManager.createNativeQuery("SELECT country_id FROM country WHERE country = '" + Country + "'");
                List<Short> chosenCountry1 = query1.getResultList();
                countryId = chosenCountry1.get(0);
            } else {
                List<Short> chosenCountry = query.getResultList();
                countryId = chosenCountry.get(0);
            }

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return countryId;
    }

    /** checks if city Idin Country exists, else add new city
     * @param tfAddCityName Name of city
     * @param countryID ID of country
     * @return Return cityID
     */
    public int addCityID(TextField tfAddCityName, int countryID){
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        short cityId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            City city = new City();

            Instant instant = Instant.now();

            String City = tfAddCityName.getText();

            Query query = entityManager.createNativeQuery("SELECT city_id FROM city WHERE country_id = '" + countryID + "' AND city = '"+City+"'");

            //Checks if City ID exist
            if (query.getResultList().isEmpty()) {
                city.setCity(City);
                city.setCountry_id(countryID);
                city.setLast_update(instant);
                entityManager.persist(city);
                Query query1 = entityManager.createNativeQuery("SELECT city_id FROM city WHERE country_id = '" + countryID + "' AND city = '"+City+"'");
                List<Short> chosenCountry1 = query1.getResultList();
                cityId = chosenCountry1.get(0);
            } else {
                List<Short> chosenCountry = query.getResultList();
                cityId = chosenCountry.get(0);
            }

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return cityId;
    }

    /**Deletes Customer or Staff
     * @param tfID ID of customer/Staff
     * //@param deleteFromTable Table to delete from (Same as staffOrCustomer)
     * //@param whereCustomerOrStaffID Either Customer_id or Staff_id (Same as customer_idOrstaff_id)
     */
    public void deleteCustomer(TextField tfID){
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Boolean payedAll = true;
            //Add errorCheck
            short deleteID = (short) Integer.parseInt(tfID.getText());

            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM customer WHERE customer_id = '"+deleteID+"'");
            List<Short> aid = queryAdressID.getResultList();
            short addressID = aid.get(0);

            Query queryRental = entityManager.createNativeQuery("SELECT return_date FROM rental WHERE customer_id = '" + deleteID + "'");
            List rentalDates = queryRental.getResultList();
            for (Object rental : rentalDates) {
                if (rental == null) {
                    payedAll = false;
                }
            }
            if (payedAll) {
                //delete payment so you can delete customer
                entityManager.createNativeQuery("DELETE payment FROM payment WHERE customer_id ='" + deleteID + "'").executeUpdate();
                //delete rental so you kan delete customer - To do, dont delete if they havent returned a movie
                entityManager.createNativeQuery("DELETE rental FROM rental WHERE customer_id = '" + deleteID + "'").executeUpdate();
                //Delete customer before address so you can it
                entityManager.createNativeQuery("DELETE customer FROM customer where customer_id = '" + deleteID + "'").executeUpdate();
                //delete address
                entityManager.createNativeQuery("DELETE address FROM address WHERE address_id = '" + addressID + "'").executeUpdate();
            } else {
                System.out.println("Kunden har inte lämnat tillbaka alla sina filmer. Det går därför ej att radera kund.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Kunden har inte lämnat tillbaka alla sina filmer. Det går därför ej att radera kund.");
                alert.show();
            }
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
    public void updateCustomer(EntityManagerFactory entityManagerFactory, TextField tfUpdateCustomerFirstName, TextField tfUpdateCustomerLastName,
                               TextField tfUpdateCustomerEmail, TextField tfUpdateCustomerStoreId,
                               TextField tfUpdateCustomerActive, TextField tfUpdateCustomerUpdateAddress,
                               TextField tfUpdateCustomerUpdateAddress2, TextField tfUpdateCustomerPostalCode,
                               TextField tfUpdateCustomerDistrict, TextField tfUpdateCustomerPhone, int cityID, TextField tfCustomerUpdateSearch){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(1, 2);
            Geometry geometry = null;
            geometry = geometryFactory.createPoint(coord);

            Customer customer = entityManager.find(Customer.class,Integer.parseInt(tfCustomerUpdateSearch.getText()));
            if (customer == null) {
                throw new EntityNotFoundException("Can't find customer for ID ");
            }

            int addressId = customer.getAddress_id();

            customer.setFirst_name(tfUpdateCustomerFirstName.getText());
            customer.setLast_name(tfUpdateCustomerLastName.getText());
            customer.setEmail(tfUpdateCustomerEmail.getText());
            customer.setActive(Integer.parseInt(tfUpdateCustomerActive.getText()));
            customer.setStore_id(Integer.parseInt(tfUpdateCustomerStoreId.getText()));
            customer.setLast_update(Instant.now());
            customer.setAddress(0);
            customer.setAddress_id(addressId);

            Address address = entityManager.find(Address.class, addressId);
            address.setAddress(tfUpdateCustomerUpdateAddress.getText());
            address.setAddress2(tfUpdateCustomerUpdateAddress2.getText());
            address.setDistrict(tfUpdateCustomerDistrict.getText());
            address.setPhone(tfUpdateCustomerPhone.getText());
            address.setPostal_code(tfUpdateCustomerPostalCode.getText());
            address.setCity_id(cityID);
            address.setLast_update(Instant.now());
            address.setLocation(geometry);



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
}
