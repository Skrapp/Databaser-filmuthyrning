import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Fetch {
    public void addToComboList (ObservableList ol, EntityManagerFactory em, String column, String table) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT "+ column +" FROM "+ table +";");
            List<String>list = query.getResultList();
            for (String p : list){
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
     * @param vBox vBox containing searchable data
     * @param ol where data is saved
     * @param em entity manager factory
     * @param column what column in table to show in search results
     * @param table what table in database to use
     */
    public void searchFromDatabase(VBox vBox, ObservableList ol, EntityManagerFactory em, String column, String table){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Only for film right now
            Query query = entityManager.createNativeQuery(
                    "SELECT " + column +
                    " FROM " + table +
                    " JOIN language l ON film.language_id = l.language_id" +
                    " JOIN film_actor fa ON film.film_id = fa.film_id" +
                    " JOIN actor a ON fa.actor_id = a.actor_id" +
                    " WHERE " + createSearchCriteria(vBox) +
                    " GROUP BY film.film_id;" );
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
     * Searches all data in children to a VBox and returns a string with searchCriteria for sql
     * @param box Vbox containing information
     * @return search criteria
     */
    public String createSearchCriteria (VBox box) {
        String searchCriteriaS ="";

        for (int i = 0; i < box.getChildren().size(); i++) {
            if (box.getChildren().get(i) instanceof VBox || box.getChildren().get(i) instanceof HBox)
            {
                //Enter box to find children there
                //searchCriteria += createSearchCriteria(box.getChildren().get(i)); //-ish
            }
            //Se if object is a textfield
            if(box.getChildren().get(i) instanceof TextField){
                if (searchCriteriaS != "")
                    searchCriteriaS += " AND ";
                //exact search and not surrounded by '' (int and date)
                if ( box.getChildren().get(i).getId().equals("film.film_id") || box.getChildren().get(i).getId().equals("film.last_update"))
                    searchCriteriaS += box.getChildren().get(i).getId().concat(" = ")
                            .concat(((TextField) box.getChildren().get(i)).getText().trim());

                else
                searchCriteriaS += box.getChildren().get(i).getId().concat(" like '%")
                        .concat(((TextField) box.getChildren().get(i)).getText().trim()).concat("%'");
            }
            System.out.println(searchCriteriaS); //Debug
        }
        return searchCriteriaS;
    }
}
