import db.Address;
import db.City;
import db.Country;
import db.Customer;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

public class test {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    public static void main(String[] args) {
        int countryID = addCountryID();
        System.out.println(countryID);
        int cityID = addCityID();
        System.out.println(cityID);
    }

    public static int addCountryID(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        short countryId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Country country = new Country();
            Instant instant = Instant.now();

            String texCountry = "Norge";

                Query query = entityManager.createNativeQuery("SELECT country_id FROM country WHERE country = '" + texCountry + "'");

                if (query.getResultList().isEmpty()) {
                    country.setCountry(texCountry);
                    country.setLast_update(instant);
                    entityManager.persist(country);
                    Query query1 = entityManager.createNativeQuery("SELECT country_id FROM country WHERE country = '" + texCountry + "'");
                    List<Short> chosenCountry1 = query1.getResultList();
                    countryId = chosenCountry1.get(0);
                } else {
                    List<Short> chosenCountry = query.getResultList();
                    countryId = chosenCountry.get(0);
                }

                System.out.println(countryId);


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

    public static int addCityID(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        short cityId = -1;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            City city = new City();

            Instant instant = Instant.now();

            short texcountryId = 100;
            String texCity = "Oslo";

            Query query = entityManager.createNativeQuery("SELECT city_id FROM city WHERE country_id = '" + texcountryId + "' AND city = '"+texCity+"'");

            if (query.getResultList().isEmpty()) {
                city.setCity(texCity);
                city.setCountry_id(texcountryId);
                city.setLast_update(instant);
                entityManager.persist(city);
                Query query1 = entityManager.createNativeQuery("SELECT city_id FROM city WHERE country_id = '" + texcountryId + "' AND city = '"+texCity+"'");
                List<Short> chosenCountry1 = query1.getResultList();
                cityId = chosenCountry1.get(0);
            } else {
                List<Short> chosenCountry = query.getResultList();
                cityId = chosenCountry.get(0);
            }

            System.out.println(cityId);


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


}
