import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.persistence.*;
import java.util.List;

public class Fetch {
    public void addToComboList (ObservableList ol, EntityManagerFactory em, String column, String table) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT "+ column +" FROM "+ table +" GROUP BY " + column + ";");
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
                    " JOIN film_category fc ON film.film_id = fc.film_id" +
                    " JOIN category c ON fc.category_id = c.category_id" +
                    " JOIN inventory i ON film.film_id = i.film_id" +
                    " JOIN rental r ON i.inventory_id = r.inventory_id" +
                    createSearchCriteria(vBox, "") +
                    " GROUP BY film.film_id" +
                    " ORDER BY " + table + "." + column +";");
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
     * Searches all data in children to a Pane and returns a string with searchCriteria formatted for sql
     * @param box Pane (parent to VBox and HBox) containing the searchable information
     * @param sSearchCriteria string to add search criteria
     * @return search criteria
     */
    //
    public String createSearchCriteria (Pane box, String sSearchCriteria) {
        for (int i = 0; i < box.getChildren().size(); i++) {
            if (box.getChildren().get(i) instanceof Pane)
            {
                sSearchCriteria = createSearchCriteria((Pane) box.getChildren().get(i), sSearchCriteria);
            }
            //See if object is a textField
            if(box.getChildren().get(i) instanceof TextField){
                String sTextField = ((TextField) box.getChildren().get(i)).getText().trim();

                if(!sTextField.equals("")) {
                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";

                    //exact search and not surrounded by '' (int and date)
                    if (box.getChildren().get(i).getId().contains("id") || box.getChildren().get(i).getId().contains("update"))
                        sSearchCriteria += box.getChildren().get(i).getId().concat(" = ")
                                .concat(sTextField);
                    //Search with "like '% %'"
                    else
                        sSearchCriteria += box.getChildren().get(i).getId().concat(" like '%")
                                .concat(sTextField).concat("%'");
                }
            }
            //See if object is combobox
            if(box.getChildren().get(i) instanceof ComboBox){
                String sSelectedItem = (String) ((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem();
                System.out.println(sSelectedItem);
                if (sSelectedItem != null) {
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
            if(box.getChildren().get(i) instanceof CheckBox){
                if (((CheckBox) box.getChildren().get(i)).isSelected()) {
                    if (sSearchCriteria.equals(""))
                        sSearchCriteria += " WHERE ";
                    else
                        sSearchCriteria += " AND ";
                    //Special search inStore
                    if(box.getChildren().get(i).getId().equals("InStore"))
                        sSearchCriteria += "(r.return_date IS NOT NULL OR r.rental_date IS NULL)";
                    else
                    //exact search and surrounded by ''
                    sSearchCriteria += box.getChildren().get(i).getId().split(",")[0].concat(" = '")
                            .concat(box.getChildren().get(i).getId().split(",")[1]).concat("'");



                }
            }
            System.out.println(sSearchCriteria); //Debug
        }
        return sSearchCriteria;
    }
}
