import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Uthyrning");
        BorderPane borderPane = new BorderPane();

        //Boxes
        VBox vBoxLeft = new VBox();
        VBox vBoxRight = new VBox();

        HBox hBoxTop= new HBox();
        /*VBox vBoxSearch = new VBox();
        VBox vBoxSearch = new VBox();
        VBox vBoxSearch = new VBox();
        VBox vBoxSearch = new VBox();
        VBox vBoxSearch = new VBox();
        VBox vBoxSearch = new VBox();*/

        //Labels
        Label lSearch = new Label("Sök");
        Label lActor = new Label("Skådespelare");
        Label lReleaseDate = new Label("Utgivningsdatum");
        Label lCustomerName = new Label("Kundnamn");
        Label lCustomerId = new Label("KundId");
        Label lCustomerEmail = new Label("Kundmail");
        Label lCustomerCity = new Label("Kundstad");


        //Textfields
        TextField tfSearch = new TextField();
        TextField tfActor = new TextField();
        TextField tfReleaseDate = new TextField();
        TextField tfCustomerName = new TextField();
        TextField tfCustomerId = new TextField();
        TextField tfCustomerEmail = new TextField();
        TextField tfCustomerCity = new TextField();


        //Add to Hbox
        vBoxLeft.getChildren().addAll(lSearch, tfSearch, lActor, tfActor, lReleaseDate, tfReleaseDate);
        vBoxRight.getChildren().addAll(lCustomerName, tfCustomerName, lCustomerCity, tfCustomerCity, lCustomerId, tfCustomerId, lCustomerEmail, tfCustomerEmail);

        //Add to VBox
        //hBoxTop.getChildren().addAll(vBoxLeft, vBoxRight);

        vBoxRight.setAlignment(Pos.TOP_RIGHT);
        vBoxLeft.setAlignment(Pos.TOP_LEFT);

        //Add to borderPane
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);

        Scene scene = new Scene(borderPane,1200,900);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
