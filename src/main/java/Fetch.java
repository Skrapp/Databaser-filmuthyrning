import javafx.scene.control.ComboBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Fetch {
    public void addToComboList (ComboBox cbox, EntityManagerFactory em) {
        EntityManager entityManager = em.createEntityManager(); // Så här bör man nog egentligen inte göra, men vafan.

    }
}
