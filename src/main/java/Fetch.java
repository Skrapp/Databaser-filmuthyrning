import attributes.MovieSearch;
import javafx.collections.ObservableList;

import javafx.scene.control.*;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Fetch {

    Controller controller = new Controller();
    ErrorCheck ec = new ErrorCheck("yyyy-mm-dd");
    EntityManagerFactory em;

    public Fetch(EntityManagerFactory em) {
        this.em = em;
    }

    public void addToComboList (ObservableList ol, String column, String table) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT "+ column +" FROM "+ table +" GROUP BY " + column + ";");
            List<Object>list = query.getResultList();
            for (Object p : list){
                ol.add(p);
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
    }

    /** Main search function. Call sub searchfunctions
     * @param movieSearch MovieSearch object containing all search parameters
     * @return returna a list of the result
     */
    public List<String> searchMovies(MovieSearch movieSearch){
        String searchCriteria = createSearchCriteriaMovie(movieSearch);
        return searchFromDatabase(searchCriteria);
    }

    /** New searchFunction for movies. Do not handel any JavaFX stuff.
     * @param searchCriteria The WHERE command
     * @return Returns a list with the titles of movies
     */
    public List<String> searchFromDatabase(String searchCriteria){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        List<String> result = new ArrayList<>();

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT film.title" +
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

            result = query.getResultList();

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return result;
    }

    //Ska tas bort, kund ska decouplas på samam sätt som film
    /**
     * Search in table for written search criteria
     * @param box Pane (VBox or HBox) containing searchable data
     * @param ol where data is written out
     * @param column what column in table to show in search results
     * @param table what table in database to use
     * @param join String of what other tables should be joined
     */
    public void searchFromDatabase(Pane box, ObservableList ol, String column, String table, String join){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT " + column +
                    " FROM " + table +
                    join +
                    createSearchCriteria(box, "") +
                    " GROUP BY " + table + "." + table + "_id" +
                    " ORDER BY " + table + "." + column +";"
            );

            List<String>list = query.getResultList();
            ol.clear();
            for(String s : list){
                ol.add(s);
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
    }

    //On going
    //Funktion för varje datadel (film base data, actor, inventory, inStore)
    public void findBaseDataForFilm(String sSelectedTitle, String table, String join){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT film.*" +
                    " FROM " + table +
                    join +
                    " WHERE film.title = " + sSelectedTitle +
                    " GROUP BY " + table + "." + table + "_id;"
            );

            List<Object[]>list = query.getResultList();
            for(Object[] o : list){
                //Add to ObservableList
                System.out.println(o[1]);//Test
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
    }

    //To add: if more than a single film_id is found, user should be able to choose what ID to look at
    public int findFilmId (String sTitle){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        int filmID = -1;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query queryFilmID = entityManager.createNativeQuery("SELECT film_id FROM film " +
                            "WHERE title = '" + sTitle + "';");
            List<Short> chosenID = queryFilmID.getResultList();
            filmID = chosenID.get(0);

            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        System.out.println(filmID);
        return filmID;
    }

    /**Checks if film is in any store
     * @param movieTitle Title of movie //Change to ID
     * @param join String of what other tables should be joined
     * @return
     */
    public boolean isInStore(String movieTitle, String join){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        Boolean isInStore = false;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "SELECT title"+
                        " FROM film"+
                        join +
                        " WHERE title = '" + movieTitle +
                        "' AND (r.return_date IS NOT NULL OR r.rental_date IS NULL)" +
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

    //Ska tas bort, kunder ska också decouplas
    /**Searches all data in children to a Pane and returns a string with searchCriteria formatted for mysql
     * @param box Pane (parent to VBox and HBox) containing the searchable information
     * @param sSearchCriteria string to add search criteria
     * @return search criteria
     */
    public String createSearchCriteria (Pane box, String sSearchCriteria) {
        for (int i = 0; i < box.getChildren().size(); i++) {
            //See if object is a pane
            if (box.getChildren().get(i) instanceof Pane)
            {
                sSearchCriteria = createSearchCriteria((Pane) box.getChildren().get(i), sSearchCriteria);
                //If there is an error do not continue
                if(sSearchCriteria.contains("ERROR")){
                    return sSearchCriteria;
                }
            }

            //See if object is a textField
            else if(box.getChildren().get(i) instanceof TextField){
                String sTextField = ((TextField) box.getChildren().get(i)).getText().trim();

                if(!sTextField.equals("")) {
                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";

                    //exact search and not surrounded by '' (int)
                    if (box.getChildren().get(i).getId().contains("id") ||
                        box.getChildren().get(i).getId().contains("year") ){

                        try {
                            int parsed = Integer.parseInt(sTextField);
                        } catch (NumberFormatException nfe) {
                            //Should call for an Error popup method
                            System.out.println("Error Meddelande: Fel inmatning i " + box.getChildren().get(i).getId());
                            return "ERROR";
                        }
                            sSearchCriteria += box.getChildren().get(i).getId().concat(" = ")
                                .concat(sTextField);
                    }
                    //Date search
                    else if(box.getChildren().get(i).getId().contains("create_date") ||
                            box.getChildren().get(i).getId().contains("last_update")){
                        if(ec.isDate(sTextField))
                        sSearchCriteria += box.getChildren().get(i).getId().concat(" like '%")
                                .concat(sTextField).concat("%'");
                        else{
                            //Should call for an Error popup method
                            System.out.println("Error Meddelande: Fel inmatning i " + box.getChildren().get(i).getId());
                            return "ERROR";
                        }
                    }
                    //Max search
                    else if(box.getChildren().get(i).getId().contains("max_value")){
                        if(ec.isDouble(sTextField))
                        sSearchCriteria += box.getChildren().get(i).getId().split(",")[0].concat(" <= ")
                                .concat(sTextField);
                        else{
                            //Should call for an Error popup method
                            System.out.println("Error Meddelande: Fel inmatning i " + box.getChildren().get(i).getId());
                            return "ERROR";
                        }
                    }
                    //Min search
                    else if(box.getChildren().get(i).getId().contains("min_value")){
                        if(ec.isDouble(sTextField))
                        sSearchCriteria += box.getChildren().get(i).getId().split(",")[0].concat(" >= ")
                                .concat(sTextField);
                        else{
                            //Should call for an Error popup method
                            System.out.println("Error Meddelande: Fel inmatning i " + box.getChildren().get(i).getId());
                            return "ERROR";
                        }
                    }
                    //Search with "like '% %'"
                    else
                        sSearchCriteria += box.getChildren().get(i).getId().concat(" like '%")
                                .concat(sTextField).concat("%'");
                }
            }
            //See if object is combobox
            else if(box.getChildren().get(i) instanceof ComboBox){
                if (((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem() != null) {
                    String sSelectedItem = ((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem().toString();

                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";
                    //exact search and surrounded by ''
                    sSearchCriteria += box.getChildren().get(i).getId().concat(" = '")
                            .concat(sSelectedItem).concat("'");
                }
            }
            //Hur ska denna del utformas så den ser "bra" ut?
            //See if object is checkbox
            else if(box.getChildren().get(i) instanceof CheckBox){
                if (((CheckBox) box.getChildren().get(i)).isSelected()) {
                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";
                    //Special search for inStore
                    if(box.getChildren().get(i).getId().equals("InStore"))
                        sSearchCriteria += "(r.return_date IS NOT NULL OR r.rental_date IS NULL)";
                    else
                    //exact search and surrounded by ''. ID example: ("film.special_features,behind the scenes")
                    sSearchCriteria += box.getChildren().get(i).getId().split(",")[0].concat(" like '%")
                            .concat(box.getChildren().get(i).getId().split(",")[1]).concat("%'");
                }
            }
            System.out.println(sSearchCriteria); //Debug
        }
        return sSearchCriteria;
    }

    /** New createSearchCriteria for movies. Gets the Where query for MySQL
     * @param movieSearch Object containig all searchdata
     * @return Returns a WHERE Query for MySQL
     */
    public String createSearchCriteriaMovie(MovieSearch movieSearch) {

        String sSearchCriteria;
        Boolean isFirst = true;
        sSearchCriteria = exactSearchInt(movieSearch.getsFilmId(),"film.film_id");
        sSearchCriteria += exactSearchInt(movieSearch.getsReleaseYear(),"film.release_year");
        sSearchCriteria += containsSearch(movieSearch.getsTitle(),"title");
        sSearchCriteria += containsSearch(movieSearch.getsDescription(), "film.description");
        //sSearchCriteria += containsSearch(movieSearch.getsALastName(),"a.last_name");
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

        System.out.println(sSearchCriteria);

        sSearchCriteria = changeFirstAnd(sSearchCriteria);

        return sSearchCriteria;
    }

    /** for int
     * @param sSearchCriteria Search data
     * @param column what column to search from
     * @return String for MySQL/ERROR
     */
    private String exactSearchInt(String sSearchCriteria, String column) {
        if (!sSearchCriteria.equals("")) {
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
        return " AND (r.return_date IS NOT NULL OR r.rental_date IS NULL)";

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
        //creates new string where first AND is replaced with WHERE
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

}
