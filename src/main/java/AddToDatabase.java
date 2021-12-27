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

    public void addCustomer (EntityManagerFactory em, TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName, TextField tfAddCustomerEmail, TextField tfAddCustomerStoreId,
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
            customer.setAddress_id(addressID); //adressID
            customer.setStore_id(Integer.parseInt(tfAddCustomerStoreId.getText()));
            customer.setActive(Integer.parseInt(tfAddCustomerActive.getText()));
            customer.setEmail(tfAddCustomerEmail.getText());
            //customer.setCreate_date(Date.valueOf(String.valueOf(2021-03-02)));
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
    public int addAdressId(EntityManagerFactory em,
                           TextField tfAddCustomerAddress,TextField tfAddCustomerAddress2, TextField tfAddCustomerPostalCode, TextField tfAddCustomerDistrict, TextField tfAddCustomerPhone, int cityID){
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        short addressID = 1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Address address= new Address();
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

            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM address WHERE address = '" +tfAddCustomerAddress.getText() +
                    "' AND district = '"+tfAddCustomerDistrict.getText()+"' AND city_id = '"+cityID+"' AND phone ='"+tfAddCustomerPhone.getText()+"' " +
                    "AND postal_code= '"+tfAddCustomerPostalCode.getText()+"'");
            List<Short> chosenID = queryAdressID.getResultList();
             addressID = chosenID.get(0);

            System.out.println(addressID);


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
    public void AddStaff(EntityManagerFactory em, TextField tfStaffAddFirstName, TextField tfStaffAddLastName, TextField tfStaffAddEmail,
                         TextField tfStaffAddUserName, TextField tfStaffAddPassword,TextField tfStaffAddStoreID,TextField tfStaffAddActive, int addressID){
            EntityManager entityManager = em.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                Staff staff = new Staff();

                Instant instant = Instant.now();

                staff.setFirstName(tfStaffAddFirstName.getText());
                staff.setLastName(tfStaffAddLastName.getText());
                staff.setEmail(tfStaffAddEmail.getText());
                staff.setUsername(tfStaffAddUserName.getText());
                staff.setPassword(tfStaffAddPassword.getText());
                staff.setLastUpdate(instant);
                staff.setAdressID(addressID);
                staff.setStoreID(Integer.parseInt(tfStaffAddStoreID.getText()));
                staff.setActive(Integer.parseInt(tfStaffAddActive.getText()));
                //staff.setPicture();
                staff.setStoreId(0);
                staff.setAddressId(0);



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

    public void addMovie(VBox box, EntityManagerFactory entityManager) {
        EntityManager emAddMovie = entityManager.createEntityManager();
        EntityTransaction transaction = null;
        String[] sTextField = new String[box.getChildren().size()];
        try{
            transaction = emAddMovie.getTransaction();
            transaction.begin();
            for(int i = 0; i < box.getChildren().size() - 1; i++) {
                if (box.getChildren().get(i) instanceof TextField) {
                    sTextField[i] = ((TextField) box.getChildren().get(i)).getText().trim();
                    System.out.println(i + "tf     " + sTextField[i]);
                }
                if(box.getChildren().get(i) instanceof ComboBox) {
                    String cbString = (String) ((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem();
                    sTextField[i] = getCorrespondingId(cbString, entityManager); // Gets the corresponding ID for whatever is chosen in combobox
                    System.out.println(i + "cb     " + sTextField[i]);
                }
            }
            Film newFilm = new Film();
            //Objects for entity Language (new and original) created where corresponding language_id for whatever
            // language chosen is set to the object, object is then set to newFilm Film object.
            Language language = new Language();
            language.setId(Integer.valueOf(sTextField[13])); //sTextField[13] is language_id value of language combobox
            newFilm.setTitle(sTextField[1]);
            newFilm.setDescription(sTextField[6]);
            newFilm.setLanguage(language);
            newFilm.setLastUpdate(Instant.now()); // set last update to current time, obviously. Removed the textbox.
            newFilm.setRentalDuration(Integer.valueOf((int) Long.parseLong(sTextField[8])));
            newFilm.setRentalRate(BigDecimal.valueOf(Long.parseLong(sTextField[3])));
            newFilm.setReplacementCost(BigDecimal.valueOf(Long.parseLong(sTextField[21])));
            newFilm.setRating(sTextField[10]);
            newFilm.setOriginalLanguage(language);
            newFilm.setReleaseYear(2002);
            newFilm.setSpecialFeatures(sTextField[17]);
            emAddMovie.persist(newFilm);
            newFilm.setCategory(Integer.valueOf(sTextField[4]), emAddMovie, newFilm);
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


    private String getCorrespondingId(String cbString, EntityManagerFactory em) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        String idAsString = "";
        Query query;
        Query query2;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            query = entityManager.createNativeQuery("SELECT language_id from language WHERE name = '" + cbString + "';");
            query2 = entityManager.createNativeQuery("SELECT category_id from category WHERE name = '" + cbString + "';");
            try {
                if (!query2.getResultList().isEmpty()) idAsString = query2.getResultList().get(0).toString();
                if (!query.getResultList().isEmpty()) idAsString = query.getResultList().get(0).toString();
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
