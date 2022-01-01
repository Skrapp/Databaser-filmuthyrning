import attributes.MovieInfo;
import attributes.MovieSearch;
import db.City;
import db.Country;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;

public class test {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");
    FXBuilder fxBuilder = new FXBuilder();
    Controller controller = new Controller(fxBuilder);
    public static void main(String[] args) {
        MovieInfo movieInfo = new MovieInfo("Hej", "Epic Adventure", "G", "Englisg", "English", "Comedy","Trailer, Deleted Scenes");
        movieInfo.setFilmId((short) 2);
        getObject(movieInfo);
    }

/*
    public static void getObject(Object obj) {
        Class cls = obj.getClass();
        for (Field field : obj.getClass().getDeclaredFields()) {
            //field.setAccessible(true); // if you want to modify private fields
            try {
                Method getString = cls.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));

                System.out.println(field.getName()
                        + " - " + field.getType()
                        + " - " + getString.invoke(obj));
            } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
*/

    public static void getObject(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true); // if you want to modify private fields
            try {
                System.out.println(field.getName()
                        + " - " + field.getType()
                        + " - " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    Fetch fetch = new Fetch(ENTITY_MANAGER_FACTORY,controller);
    MovieInfo movieInfo = new MovieInfo();

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
