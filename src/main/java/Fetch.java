import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public void searchFromMovies (TextField tf, ObservableList ol, EntityManagerFactory em, String column, String table){
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan gör de en regnig dag.
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT " + column + " FROM " + table + " WHERE title LIKE '%" +  tf.getText().toString() + "%';" );
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
                    System.out.println("Fel lösenord");
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
