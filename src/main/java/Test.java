import db.Address;
import db.City;
import db.Country;
import db.Customer;
import javafx.scene.control.TextField;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

public class Test {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    public static void main(String[] args) {
        Main main = new Main();
        deleteCustomer();
    }


    public int addCountryID(TextField tfAddCustomerCountrys){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        short countryId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Country country = new Country();
            Instant instant = Instant.now();

            String Country = tfAddCustomerCountrys.getText();

                Query query = entityManager.createNativeQuery("SELECT country_id FROM country WHERE country = '" + Country + "'");

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

    public int addCityID(TextField tfAddCustomerCity, int countryID){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        short cityId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            City city = new City();

            Instant instant = Instant.now();

            String City = tfAddCustomerCity.getText();

            Query query = entityManager.createNativeQuery("SELECT city_id FROM city WHERE country_id = '" + countryID + "' AND city = '"+City+"'");

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


    public static void deleteCustomer(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            short customerID = 603;

            Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM customer WHERE customer_id = '"+customerID+"'");
            List<Short> aid = queryAdressID.getResultList();
            short addressID = aid.get(0);

            System.out.println(addressID);

            entityManager.createNativeQuery("DELETE FROM customer where customer_id = '"+customerID+"'").executeUpdate();

            //Query queryDeleteAdress = entityManager.createNativeQuery("DELETE from address WHERE address_id = '"+queryAdressID.getResultList()+"'").executeUpdate;

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
