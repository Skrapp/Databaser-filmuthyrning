import db.Address;
import db.City;
import db.Country;
import db.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class AddToDatabase {

    public void addCustomer (EntityManagerFactory em/*, TextField tfAddCustomerFirstName, TextField tfAddCustomerLastName, TextField tfAddCustomerEmail, TextField tfAddCustomerStoreId,
                             TextField tfAddCustomerRegistered, TextField tfAddCustomerActive, TextField tfAddCustomerAddress, TextField tfAddCustomerPostalCode, TextField tfAddCustomerDistrict,
                             TextField tfAddCustomerPhone, TextField tfAddCustomerCity, TextField tfAddCustomerCountrys*/) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Customer customer = new Customer();
            City city = new City();
            Country country = new Country();
            Address address= new Address();
            Instant instant = Instant.now();

            customer.setFirst_name("Daniel2");
            customer.setLast_name("Säfström2");
            customer.setAddress_id(15);
            customer.setStore_id(1);
            customer.setActive(1);
            customer.setEmail("Daniel@hot");
            customer.setAddress(0);
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
