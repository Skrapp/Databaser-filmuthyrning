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
}
