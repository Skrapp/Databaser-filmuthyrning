import javafx.collections.ObservableList;

import javafx.scene.control.CheckBox;

import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.persistence.*;
import java.util.List;

public class Fetch {
    ErrorCheck ec = new ErrorCheck("yyyy-mm-dd");

    /**
     * Adds specified data to observable list from the database
     * @param ol where data is to be stored
     * @param em Entity Manager Factory
     * @param column What data to show
     * @param table From what table to take data from
     */
    public void addToComboList (ObservableList ol, EntityManagerFactory em, String column, String table) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT "+ column +" FROM "+ table +" GROUP BY " + column + ";");

            List<Object>list = query.getResultList();
            for (Object p : list)
                ol.add(p);

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

    public void searchFromDatabase(TextField tf, ObservableList ol, EntityManagerFactory em, String column, String table){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT " + column + " FROM " + table + " WHERE " + column + " LIKE '%" +  tf.getText().toString() + "%';" );
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

    /**
     * Search in table for written search criteria
     * @param box Pane (VBox or HBox) containing searchable data
     * @param ol where data is written out
     * @param em entity manager factory
     * @param column what column in table to show in search results
     * @param table what table in database to use
     * @param join String of what other tables should be joined
     */
    public void searchFromDatabase(Pane box, ObservableList ol, EntityManagerFactory em, String column, String table, String join){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag mitt i november.
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

    /**Checks if a movie is in stock
     * @param em Entity Manager Factory
     * @param movieTitle What title to search for
     * @param join String of what other tables should be joined
     * @return returns if the movie is in stock or not
     */
    public boolean isInStore(EntityManagerFactory em, String movieTitle, String join){
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

            List<String>list = query.getResultList();

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


    /** Searches all data in children to a Pane and returns a string with searchCriteria formatted for mysql
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

                    //exact search and not surrounded by '' (int and date)
                    if (box.getChildren().get(i).getId().contains("id") ||
                        box.getChildren().get(i).getId().contains("year") ){

                        //Checks if it's an integer
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
                        //Checks if it's the correct date format
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
                        //Checks if it's a double
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
                        //Checks if it's a double
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
            //See if object is checkbox
            else if(box.getChildren().get(i) instanceof CheckBox){
                if (((CheckBox) box.getChildren().get(i)).isSelected()) {
                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";
                    //Special search inStore
                    if(box.getChildren().get(i).getId().equals("InStore"))
                        sSearchCriteria += "(r.return_date IS NOT NULL OR r.rental_date IS NULL)";
                    else
                    //contains searched content
                    sSearchCriteria += box.getChildren().get(i).getId().split(",")[0].concat(" like '%")
                            .concat(box.getChildren().get(i).getId().split(",")[1]).concat("%'");
                }
            }
            System.out.println(sSearchCriteria); //Debug
        }
        return sSearchCriteria;
    }

    public void login(EntityManagerFactory em, TextField tfUsername, TextField tfPassword, Stage primaryStage, Stage loginStage) {
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
