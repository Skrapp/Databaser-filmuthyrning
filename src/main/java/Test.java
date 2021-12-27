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

    }


    /**Ser ifall country finns, om inte: lägger till ett nytt country
     * @param tfAddCountryName Country name
     * @return returnerar countryID
     */
    public int addCountryID(TextField tfAddCountryName){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
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
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
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
     * @param getAddressIDTable Table where adress ID is, Staff/Customer
     * @param whereID Either Customer_id or Staff_id
     * @param deleteFromTable Table to delete from (Same as getAddressIDTable)
     * @param whereCustomerOrStaffID Either Customer_id or Staff_id (Same as whereID)
     */
        public void deleteCustomer(TextField tfID, String getAddressIDTable, String whereID, String deleteFromTable, String whereCustomerOrStaffID){
            //"SELECT address_id FROM '"+getAdressIDFrom+"'  <--- getAddressIDTable = customer eller staff. WHERE "whereID" --> customer_id eller staffid  = '"+deleteID+"'")  <--- deleteId = staff_id eller customer_id
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();

                //Add errorCheck
                short deleteID = (short) Integer.parseInt(tfID.getText());

                Query queryAdressID = entityManager.createNativeQuery("SELECT address_id FROM "+getAddressIDTable+" WHERE "+whereID+" = '"+deleteID+"'");
                List<Short> aid = queryAdressID.getResultList();
                short addressID = aid.get(0);

                System.out.println(addressID); //Debug

                entityManager.createNativeQuery("DELETE "+deleteFromTable+" FROM "+deleteFromTable+" where "+whereCustomerOrStaffID+" = '"+deleteID+"'").executeUpdate();

                entityManager.createNativeQuery("DELETE address FROM address WHERE address_id = '"+addressID+"'").executeUpdate();

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
