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
import java.time.Instant;
import java.util.List;
import javafx.scene.control.TextField;

public class AddToDatabase {

    public void addCustomer (EntityManagerFactory em, TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName, TextField tfAddCustomerEmail, TextField tfAddCustomerStoreId,
                             TextField tfAddCustomerActive, TextField tfAddCustomerAddress, TextField tfAddCustomerPostalCode, TextField tfAddCustomerDistrict,
                             TextField tfAddCustomerPhone, int cityID) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Test test = new Test();
            Customer customer = new Customer();
            City city = new City();
            Country country = new Country();
            Address address= new Address();
            Instant instant = Instant.now();
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(1, 2);
            Geometry geometry = null;
            geometry = geometryFactory.createPoint(coord);


            address.setAddress(tfAddCustomerAddress.getText());
            address.setAddress2("");
            address.setDistrict(tfAddCustomerDistrict.getText());
            address.setCity_id(cityID);
            address.setPostal_code(tfAddCustomerPostalCode.getText());
            address.setPhone(tfAddCustomerPhone.getText());
            address.setLocation(geometry);
            address.setLast_update(instant);

            entityManager.persist(address);

            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM address WHERE address = '" +tfAddCustomerAddress.getText() + "' AND district = '"+tfAddCustomerDistrict.getText()+"' AND city_id = '"+cityID+"' AND phone ='"+tfAddCustomerPhone.getText()+"' " +
                    "AND postal_code= '"+tfAddCustomerPostalCode.getText()+"'");
            List<Short> chosenID = queryAdressID.getResultList();
            short addressID = chosenID.get(0);

            System.out.println(addressID);

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
}
