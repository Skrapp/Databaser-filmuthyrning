import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class test2 {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    public static void main(String[] args) {


        AddToDatabase addToDatabase = new AddToDatabase();
        //addToDatabase.addCustomer(ENTITY_MANAGER_FACTORY);
    }
}
