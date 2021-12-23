import db.Address;
import db.City;
import db.Country;
import db.Customer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.awt.*;
import java.sql.Date;
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



            Point point = new Point(2, 2);

            address.setAddress("Skogen");
            address.setAddress2("Skogsvägen");
            address.setDistrict("Knutby");
            address.setCity_id(12);
            address.setPostal_code("76437");
            address.setPhone("122303032");
            address.setLocation(geometry);
            address.setLast_update(instant);

            entityManager.persist(address);

           /* Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM address WHERE address = Skogen AND district = Knutby AND city_id = 12 AND phone =122303032");
            List<Integer> chosenArtist = queryAdressID.getResultList();
            int addressID = chosenArtist.get(0);*/

            /*customer.setFirst_name("Daniel3");
            customer.setLast_name("Säfström3");
            customer.setAddress_id(22); //adressID
            customer.setStore_id(1);
            customer.setActive(1);
            customer.setEmail("Daniel@hot");
            //customer.setAddress(0);
            //customer.setCreate_date(Date.valueOf(String.valueOf(2021-03-02)));
            customer.setLast_update(instant);



            entityManager.persist(customer);*/
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
