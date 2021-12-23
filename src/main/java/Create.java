import db.Address;
import db.Film;
import db.Language;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.locationtech.jts.geom.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
// Work in progress så att säga.
public class Create {
    public void createMovie (VBox box, EntityManagerFactory entityManager) {
        EntityManager emCreateMovie = entityManager.createEntityManager();
        EntityTransaction transaction = null;
        String[] sTextField = new String[box.getChildren().size()];
        try{
            transaction = emCreateMovie.getTransaction();
            transaction.begin();
            for(int i = 0; i < box.getChildren().size() - 1; i++) {
                if (box.getChildren().get(i) instanceof TextField) {
                    sTextField[i] = ((TextField) box.getChildren().get(i)).getText().trim();
                    System.out.println(i + "tf     " + sTextField[i]);
                }
                if(box.getChildren().get(i) instanceof ComboBox) {
                    String cbString = (String) ((ComboBox) box.getChildren().get(i)).getSelectionModel().getSelectedItem();
                    sTextField[i] = getCorrespondingId(cbString, entityManager); // Gets the corresponding ID for whatever is chosen in combobox
                    System.out.println(i + "cb     " + sTextField[i]);
                }
            }
            Film newFilm = new Film();
            //Objects for entity Language (new and original) created where corresponding language_id for whatever
            // language chosen is set to the object, object is then set to newFilm Film object.
            Language language = new Language();
            language.setId(Integer.valueOf(sTextField[13])); //sTextField[12] is language_id value of language combobox
            newFilm.setTitle(sTextField[1]);
            newFilm.setDescription(sTextField[6]);
            newFilm.setLanguage(language);
            newFilm.setLastUpdate(Instant.now());
            newFilm.setRentalDuration(3);
            newFilm.setRentalRate(BigDecimal.valueOf(4.99));
            newFilm.setReplacementCost(BigDecimal.valueOf(19.99));
            newFilm.setRating("G");
            newFilm.setOriginalLanguage(language);
            newFilm.setReleaseYear(2002);
            newFilm.setSpecialFeatures("Trailers,Commentaries,Behind the Scenes");
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(1, 2);
            Geometry geometry = null;
            geometry = geometryFactory.createPoint(coord);
            Address address = new Address();
            address.setLocation(geometry);
            emCreateMovie.persist(newFilm);
            emCreateMovie.flush();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            emCreateMovie.close();
        }
    }

    private String getCorrespondingId(String cbString, EntityManagerFactory em) {
        EntityManager entityManager = em.createEntityManager();
        EntityTransaction transaction = null;
        String idAsString = "";
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("SELECT language_id from language WHERE name = '" + cbString + "';");
            Query query2 = entityManager.createNativeQuery("SELECT category_id from category WHERE name = '" + cbString + "';");
            try {
                if (query.getSingleResult() != null) idAsString = query.getResultList().get(0).toString();
                if (query2.getSingleResult() != null) idAsString = query2.getResultList().get(0).toString();
                transaction.commit();
            }catch (NoResultException ee) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return idAsString;
    }
}
