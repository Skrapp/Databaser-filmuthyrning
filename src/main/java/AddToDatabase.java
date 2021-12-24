import db.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AddToDatabase {

    public void addCustomer (EntityManagerFactory em/*, TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName, TextField tfAddCustomerEmail, TextField tfAddCustomerStoreId,
                             TextField tfAddCustomerRegistered, TextField tfAddCustomerActive, TextField tfAddCustomerAddress, TextField tfAddCustomerPostalCode, TextField tfAddCustomerDistrict,
                             TextField tfAddCustomerPhone, TextField tfAddCustomerCity, TextField tfAddCustomerCountrys*/) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            test test = new test();
            Customer customer = new Customer();
            City city = new City();
            Country country = new Country();
            Address address= new Address();
            Instant instant = Instant.now();
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(1, 2);
            Geometry geometry = null;
            geometry = geometryFactory.createPoint(coord);


            address.setAddress("Hos Jesper 2");
            address.setAddress2("");
            address.setDistrict("Salabacke 2");
            address.setCity_id(16);
            address.setPostal_code("755972");
            address.setPhone("63712");
            address.setLocation(geometry);
            address.setLast_update(instant);

            entityManager.persist(address);

            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM address WHERE address = 'Hos Jesper 2' AND district = 'Salabacke 2' AND city_id = 16 AND phone ='63712'");
            List<Short> chosenID = queryAdressID.getResultList();
            short addressID = chosenID.get(0);

            System.out.println(addressID);

            customer.setFirst_name("Daniel3");
            customer.setLast_name("Säfström3");
            customer.setAddress_id(addressID); //adressID
            customer.setStore_id(1);
            customer.setActive(1);
            customer.setEmail("Daniel@hot");
            //customer.setAddress(0);
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
            newFilm.setLastUpdate(Instant.now()); // set last update to current time, obviously.
            newFilm.setRentalDuration(Integer.valueOf(sTextField[8]));
            newFilm.setRentalRate(BigDecimal.valueOf(Long.parseLong(sTextField[3])));
            newFilm.setReplacementCost(BigDecimal.valueOf(Long.parseLong(sTextField[21])));
            newFilm.setRating(sTextField[10]);
            newFilm.setOriginalLanguage(language);
            newFilm.setReleaseYear(2002);
            newFilm.setSpecialFeatures(sTextField[17]);
            emAddMovie.persist(newFilm);
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
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("SELECT language_id from language WHERE name = '" + cbString + "';");
            Query query2 = entityManager.createNativeQuery("SELECT category_id from category WHERE name = '" + cbString + "';");
            try {
                if (query.getSingleResult() != null) idAsString = query.getResultList().get(0).toString();
                if (query2.getSingleResult() != null) idAsString = query2.getResultList().get(0).toString();
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
