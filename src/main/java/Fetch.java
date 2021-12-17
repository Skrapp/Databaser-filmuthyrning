import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
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

    public void createSearchCriteria (VBox vBox) {
        String searchCriteria = "";
        for (int i = 0; i < vBox.getChildren().size(); i++) {
            searchCriteria = searchCriteria + "AND first_name = '" + vBox.getChildren().get(i) + "'";
        }
    }
}
