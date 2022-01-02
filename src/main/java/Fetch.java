import attributes.CustomerInfo;
import attributes.CustomerSearch;
import attributes.MovieInfo;
import attributes.MovieSearch;

import db.*;

import javafx.scene.control.*;

import javafx.stage.Stage;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Fetch {

    private Controller controller;
    private ErrorCheck ec = new ErrorCheck("yyyy-mm-dd");
    private EntityManagerFactory em;

    public Fetch(EntityManagerFactory em,Controller controller) {
        this.em = em;
        this.controller = controller;
    }


    public List<Object> getSimpleList (String column, String table) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<Object> list = new ArrayList();
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT "+ column +" FROM "+ table +" GROUP BY " + column + ";");
            list = query.getResultList();

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            controller.callError("Något gick fel i laddningen av datan");
            e.printStackTrace();
        }finally {
            entityManager.close();
            return list;
        }
    }

    /** Main search function. Call sub searchfunctions
     * @param movieSearch MovieSearch object containing all search parameters
     * @return return a list of the result
     */
    public List<FilmSearchResults> searchMovies(MovieSearch movieSearch){
        String searchCriteria = createSearchCriteriaMovie(movieSearch);
        return searchFromDatabaseFilm(searchCriteria);
    }

    /** Main search function. Call sub searchfunctions
     * @param customerSearch CustomerSearch object containing all search parameters
     * @return return a list of the result
     */
    public List<CustomerSearchResults> searchCustomers(CustomerSearch customerSearch){
        String searchCriteria = createSearchCriteriaCustomer(customerSearch);
        return searchFromDatabaseCustomer(searchCriteria);
    }

    /** New searchFunction for movies. Do not handel any JavaFX stuff.
     * @param searchCriteria The WHERE command
     * @return Returns a list with the titles of movies
     */
    public List<FilmSearchResults> searchFromDatabaseFilm(String searchCriteria){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<FilmSearchResults> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT film.film_id" +
                            " FROM film" +
                            " LEFT JOIN language l ON film.language_id = l.language_id" +
                            " LEFT JOIN language ol ON film.original_language_id = ol.language_id" +
                            " LEFT JOIN film_actor fa ON film.film_id = fa.film_id" +
                            " LEFT JOIN actor a ON fa.actor_id = a.actor_id" +
                            " LEFT JOIN film_category fc ON film.film_id = fc.film_id" +
                            " LEFT JOIN category c ON fc.category_id = c.category_id" +
                            " LEFT JOIN inventory i ON film.film_id = i.film_id" +
                            " LEFT JOIN rental r ON i.inventory_id = r.inventory_id" +
                            searchCriteria +
                            " GROUP BY film.film_id" +
                            " ORDER BY film.title;"
            );

            List<Short> list = query.getResultList();

            for (Short id : list) {
                Film film = entityManager.find(Film.class, Integer.valueOf(id));
                FilmSearchResults srFilm = new FilmSearchResults(film.getId(), film.getTitle(), film.getDescription());
                result.add(srFilm);
            }
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    public List<CustomerSearchResults> searchFromDatabaseCustomer(String searchCriteria){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<CustomerSearchResults> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT customer.customer_id" +
                            " FROM customer" +
                            " LEFT JOIN address a ON customer.address_id = a.address_id"+
                            " LEFT JOIN city ci ON ci.city_id = a.city_id" +
                            searchCriteria +
                            " GROUP BY customer.customer_id" +
                            " ORDER BY customer.first_name;"
            );

            List<Short> list = query.getResultList();

            for(Short id : list){
                    Customer customer = entityManager.find(Customer.class, Integer.valueOf(id));
                    CustomerSearchResults srCustomer = new CustomerSearchResults(Integer.valueOf(id), customer.getFirstName() + " " + customer.getLastName(), customer.getEmail(), em);
                    result.add(srCustomer);
            }
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    /** New createSearchCriteria for movies. Gets the Where query for MySQL
     * @param movieSearch Object containig all searchdata
     * @return Returns a WHERE Query for MySQL
     */
    public String createSearchCriteriaMovie(MovieSearch movieSearch) {

        String sSearchCriteria;

        sSearchCriteria = exactSearchInt(movieSearch.getsFilmId(),"film.film_id");
        sSearchCriteria += exactSearchInt(movieSearch.getsReleaseYear(),"film.release_year");

        sSearchCriteria += containsSearch(movieSearch.getsTitle(),"title");
        sSearchCriteria += containsSearch(movieSearch.getsDescription(), "film.description");
        sSearchCriteria += containsSearch(movieSearch.getsAFirstName(),"a.first_name");
        sSearchCriteria += containsSearch(movieSearch.getsRating(),"film.rating");
        sSearchCriteria += containsSearch(movieSearch.getsOriginalLanguage(),"ol.name");
        sSearchCriteria += containsSearch(movieSearch.getsLanguage(),"l.name");
        sSearchCriteria += containsSearch(movieSearch.getsCategory(),"c.name");

        sSearchCriteria += dateSearch(movieSearch.getsLastUpdate(),"film.last_update");

        sSearchCriteria += maxSearch(movieSearch.getsLengthMax(),"film.length");
        sSearchCriteria += minSearch(movieSearch.getsReplacementCostMin(),"film.replacement_cost");
        sSearchCriteria += maxSearch(movieSearch.getsReplacementCostMax(),"film.replacement_cost");
        sSearchCriteria += minSearch(movieSearch.getsRentalDurationMin(),"film.rental_duration");
        sSearchCriteria += maxSearch(movieSearch.getsRentalDurationMax(),"film.rental_duration");
        sSearchCriteria += minSearch(movieSearch.getsRentalRateMin(),"film.rental_rate");
        sSearchCriteria += maxSearch(movieSearch.getsRentalRateMax(),"film.rental_rate");
        sSearchCriteria += minSearch(movieSearch.getsLengthMin(),"film.length");

        sSearchCriteria += checkBoxSearch(movieSearch.getHasSFDeletedScenes(),"film.special_features","Deleted Scenes");
        sSearchCriteria += checkBoxSearch(movieSearch.getHasSFTrailer(),"film.special_features","Trailers");
        sSearchCriteria += checkBoxSearch(movieSearch.getHasSFCommentaries(),"film.special_features","Commentaries");
        sSearchCriteria += checkBoxSearch(movieSearch.getHasSFBTS(),"film.special_features","Behind The Scenes");

        sSearchCriteria += inStoreSearch(movieSearch.getInStore());

        System.out.println(sSearchCriteria);  //Debug

        sSearchCriteria = changeFirstAnd(sSearchCriteria);

        return sSearchCriteria;
    }

    /** New createSearchCriteria for customer. Gets the Where query for MySQL
     * @param customerSearch Object containing all searchData
     * @return Returns a WHERE Query for MySQL
     */
    public String createSearchCriteriaCustomer(CustomerSearch customerSearch) {

        String sSearchCriteria;

        sSearchCriteria = exactSearchInt(customerSearch.getsId(), "customer.customer_id");
        sSearchCriteria += exactSearchInt(customerSearch.getsStoreId(), "customer.store_id");
        sSearchCriteria += containsSearch(customerSearch.getsFirstName(), "customer.first_name");
        sSearchCriteria += containsSearch(customerSearch.getsEmail(), "customer.email");
        sSearchCriteria += containsSearch(customerSearch.getsCity(), "ci.city");
        sSearchCriteria += containsSearch(customerSearch.getsAddress(), "a.address");
        sSearchCriteria += containsSearch(customerSearch.getsPhone(), "a.phone");
        sSearchCriteria += dateSearch(customerSearch.getsRegistered(), "customer.create_date");
        sSearchCriteria += dateSearch(customerSearch.getsUpdate(), "customer.last_update");

        sSearchCriteria += checkBoxSearch(customerSearch.getIsActive(), "customer.active", "1");

        System.out.println(sSearchCriteria); //Debug

        sSearchCriteria = changeFirstAnd(sSearchCriteria);

        return sSearchCriteria;
    }

    /**Get data from customer
     * @param selectedId What customer to get data from
     * @return return an array of all data (different datatypes)
     */
    public Object [] findBaseDataForCustomer(int selectedId){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        Object[] result = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(

                    "SELECT c.first_name, c.last_name, c.customer_id,c.store_id, c.active, c.email, a.phone, " +
                            "ci.city, a.address, a.address2, c.create_date, c.last_update" +
                            " FROM customer c" +
                            " LEFT JOIN address a ON c.address_id = a.address_id" +
                            " LEFT JOIN city ci ON ci.city_id = a.city_id" +
                            " WHERE c.customer_id = " + selectedId +
                            " GROUP BY c.customer_id;"
            );

            List <Object[]> list = query.getResultList();

            result = list.get(0);

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            controller.callError("Kunde inte hitta informationen om kunden.");
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    /** Get simple data from movie
     * @param selectedId What movie to get data from
     * @return return an array of all data (different datatypes)
     */
    public Object [] findBaseDataForFilm(int selectedId){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        Object[] result = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(

            "SELECT film.title,film.film_id, film.description,film.rating,c.name c_name,ol.name ol_name,l.name l_name," +
                    "film.special_features,film.rental_rate, film.replacement_cost, film.rental_duration, film.length, " +
                    " film.release_year,film.last_update" +
                    " FROM film" +
                    " LEFT JOIN language l ON film.language_id = l.language_id" +
                    " LEFT JOIN language ol ON film.original_language_id = ol.language_id" +
                    " LEFT JOIN film_actor fa ON film.film_id = fa.film_id" +
                    " LEFT JOIN actor a ON fa.actor_id = a.actor_id" +
                    " LEFT JOIN film_category fc ON film.film_id = fc.film_id" +
                    " LEFT JOIN category c ON fc.category_id = c.category_id" +
                    " LEFT JOIN inventory i ON film.film_id = i.film_id" +
                    " LEFT JOIN rental r ON i.inventory_id = r.inventory_id" +
                    " WHERE film.film_id = " + selectedId +
                    " GROUP BY film.film_id;"
            );

            List <Object[]> list = query.getResultList();

            result = list.get(0);

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            controller.callError("Kunde inte hitta informationen om filmen.");
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    /**Sets all the info oa a specified customer in a CustomerInfo object
     * @param selectedId What customer to get data from
     * @return object containing all data
     */
    public CustomerInfo setCustomerInfo(int selectedId) {
        CustomerInfo customerInfo = new CustomerInfo();

        Object[] infoArray = findBaseDataForCustomer(selectedId);

        int i = 0;
        //Add all data from infoArray to customerInfo
        for (Field field : customerInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true); // to access private fields
            try {
                //Attributes with data from two columns in mySQL
                if (field.getName().equals("fullName") || field.getName().equals("address")) {
                    field.set(customerInfo,infoArray[i++].toString().concat(" ").concat(infoArray[i++].toString()));
                    continue;
                }
                field.set(customerInfo,infoArray[i++]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                controller.callError("Kunde inte hämta information från customerInfo");
            }
        }
        return customerInfo;
    }

    /**Sets all info of a movie in a MovieInfo object
     * @param selectedId What film id to get data from
     * @return Object containing all info of movie
     */
    public MovieInfo setMovieInfo(int selectedId) {
        MovieInfo movieInfo = new MovieInfo();

        Object[] infoArray = findBaseDataForFilm(selectedId);

        movieInfo.setActorList(getMovieActorInfo(selectedId));
        movieInfo.setInventoryList(getMovieInventoryInfo(selectedId));
        movieInfo.setStoreIdList(getMovieStoreIDInfo(selectedId));

        int i = 0;
        //Add all data from infoArray to movieInfo
        for (Field field : movieInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true); // to access private fields
            try {
                //List attributes gets data from separate functions
                if (field.getType().isInstance(new ArrayList<>())) {
                    continue;
                }
                field.set(movieInfo,infoArray[i++]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                controller.callError("Kunde inte hämta information från movieInfo");
            }
        }

        return movieInfo;
    }

    /**Get all the inventory ID:s where movie is
     * @param selectedId What movie to get data from
     * @return List of inventory ID:s
     */
    private List<Integer> getMovieInventoryInfo(int selectedId) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<Integer> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
        "SELECT i.inventory_id" +
                " FROM film" +
                " LEFT JOIN inventory i ON film.film_id = i.film_id" +
                " WHERE film.film_id = " + selectedId +
                " GROUP BY i.inventory_id" +
                " ORDER BY i.inventory_id;"
            );

            result = query.getResultList();

            transaction.commit();

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    /** Gets all the store ID:S where movie is registered
     * @param selectedId What movie to get data from
     * @return List of store ID:s
     */
    private List<Short> getMovieStoreIDInfo(int selectedId) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<Short> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT i.store_id" +
                            " FROM film" +
                            " LEFT JOIN inventory i ON film.film_id = i.film_id" +
                            " WHERE film.film_id = " + selectedId +
                            " GROUP BY i.store_id;"
            );

            result = query.getResultList();

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }


    /** Gets all actors (full name) listed in movie
     * @param selectedId What movie to get data from
     * @return list of actor names
     */
    private List<String> getMovieActorInfo(int selectedId){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<String> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT a.first_name, a.last_name" +
                            " FROM film" +
                            " LEFT JOIN film_actor fa ON film.film_id = fa.film_id" +
                            " LEFT JOIN actor a ON fa.actor_id = a.actor_id" +
                            " WHERE film.film_id = " + selectedId +
                            " GROUP BY a.actor_id;"
            );

            List <Object[]> list = query.getResultList();


            for (Object[] o : list) {
                result.add(o[0].toString() + " " + o[1].toString());
            }

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
            return result;
        }
    }

    /**Checks if film is in any store
     * @param movieId ID of movie
     * @param join String of what other tables should be joined
     * @return
     */
    public boolean isInStore(int movieId, String join){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        Boolean isInStore = false;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT film.film_id"+
                        " FROM film"+
                        join +
                        " WHERE film.film_id = " + movieId +
                        " AND (r.return_date IS NOT NULL OR r.rental_date IS NULL) AND i.inventory_id IS NOT NULL" +
                        " GROUP BY film.film_id" +
                        " ORDER BY film.title;"
            );

            List<String>list = query.getResultList();//.get(i) Gör till forloop och få all them data

            System.out.println(list.size());
            if (list.size() >= 1)
                isInStore = true;

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return isInStore;
    }

    /** for int
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String exactSearchInt(String sSearchCriteria, String column) {
        if (!sSearchCriteria.equals("")) { //If not empty
            if (ec.isInteger(sSearchCriteria))
                return " AND ".concat(column).concat(" = ").concat(sSearchCriteria);
            else {
                controller.callError("Skriv ett  heltal i " + column);
                System.out.println("Skriv ett heltal i " + column);
                return "ERROR";
            }
        }
        return"";
    }

    /**for date
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String dateSearch(String sSearchCriteria, String column) {
        if (!sSearchCriteria.equals("")) {
            if (ec.isDate(sSearchCriteria))
                return " AND ".concat(column).concat(" like '%").concat(sSearchCriteria).concat("%'");
            else {
                controller.callError("Skriv ett  datum med formatet yyyy-mm-dd i " + column);
                System.out.println("Skriv ett datum i " + column);
                return "ERROR";
            }
        }
        return "";
    }

    /**for minimum doubles
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String minSearch(String sSearchCriteria, String column) {
        if (!sSearchCriteria.equals("")) {
            if (ec.isDouble(sSearchCriteria)) {
                return " AND ".concat(column).concat(" >= ").concat(sSearchCriteria);
            } else {
                controller.callError("Skriv ett  decimaltal i " + column);
                System.out.println("Skriv ett decimaltal i " + column);
                return "ERROR";
            }
        }
        return "";
    }

    /**for maximum doubles
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String maxSearch(String sSearchCriteria, String column) {
        if (!sSearchCriteria.equals("")) {
            if (ec.isDouble(sSearchCriteria)) {
                return " AND ".concat(column).concat(" <= ").concat(sSearchCriteria);
            } else {
                controller.callError("Skriv ett  decimaltal i " + column);
                System.out.println("Skriv ett decimaltal i " + column);
                return "ERROR";
            }
        }
        return "";
    }

    /** for not exact searches
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String containsSearch(String sSearchCriteria, String column) {
        if(!sSearchCriteria.equals(""))
            return " AND ".concat(column).concat(" like '%").concat(sSearchCriteria).concat("%'");
        return "";
    }

    /**If movie is in store
     * @param inStore
     * @return
     */
    private String inStoreSearch(Boolean inStore) {
    if (inStore)
        return " AND (r.return_date IS NOT NULL OR r.rental_date IS NULL) AND i.inventory_id IS NOT NULL";

        return "";
    }

    /** for booleans
     * @param isSelected is the checkbox selected
     * @param contains Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String checkBoxSearch(Boolean isSelected,String column, String contains) {
        if (isSelected){
            return " AND ".concat(column).concat(" like '%").concat(contains).concat("%'");
        }

        return "";
    }

    /**Changes the first AND to WHERE in a String
     * @param s original string
     * @return new string
     */
    private String changeFirstAnd(String s) {
        //saves new string where first AND is replaced with WHERE
        System.out.println(s);
            s = s.replaceFirst("AND", "WHERE");
        System.out.println(s);
        return s;
    }

    public void login(TextField tfUsername, TextField tfPassword, Stage primaryStage, Stage loginStage) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        try{
            Query queryPassword = entityManager.createNativeQuery(
                    "SELECT password FROM staff WHERE username='"+username+"'");

            if (queryPassword.getResultList().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fel användarnamn eller lösenord");
                alert.show();
            } else {
                List<String> listPass = queryPassword.getResultList();
                String p = listPass.get(0);

                if (p == null) {
                    p = "";
                }

                if (password.equals(p)) {
                    tfPassword.clear();
                    tfUsername.clear();
                    primaryStage.show();
                    loginStage.close();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Fel användarnamn eller lösenord");
                    alert.show();
                }
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public ErrorCheck getEc() {
        return ec;
    }

    public void setEc(ErrorCheck ec) {
        this.ec = ec;
    }

    public EntityManagerFactory getEm() {
        return em;
    }

    public void setEm(EntityManagerFactory em) {
        this.em = em;
    }
}
