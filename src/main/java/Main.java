import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    final ObservableList olCategory = FXCollections.observableArrayList();
    final ObservableList olLanguages = FXCollections.observableArrayList();
    final ObservableList olStaff = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Uthyrning");
        BorderPane borderPane = new BorderPane();

        // Combobox
        ComboBox cbCategory = new ComboBox(olCategory);
        ComboBox cbLanguages = new ComboBox(olLanguages);
        ComboBox cbStaff = new ComboBox(olStaff);

        //Boxes
        VBox vBoxLeft = new VBox();
        VBox vBoxRight = new VBox();

        HBox hBoxTop= new HBox();

        //Buttons
        Button bSearchMovie = new Button("Sök");
        Button bSearchCustomer = new Button("Sök");
        Button bCreateMovie = new Button("Lägg till");
        Button bCreateCustomer = new Button("Lägg till");


        //Labels
        Label lSearch = new Label("Sök");
        Label lActor = new Label("Skådespelare");
        Label lReleaseDate = new Label("Utgivningsdatum");
        Label lCustomerName = new Label("Kundnamn");
        Label lCustomerId = new Label("KundId");
        Label lCustomerEmail = new Label("Kundmail");
        Label lCustomerCity = new Label("Kundstad");
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(50));
        Label lCustomerHeader = new Label("Kundsektion");
        lCustomerHeader.setFont(new Font(50));



        //Textfields
        TextField tfSearch = new TextField();
        TextField tfActor = new TextField();
        TextField tfReleaseDate = new TextField();
        TextField tfCustomerName = new TextField();
        TextField tfCustomerId = new TextField();
        TextField tfCustomerEmail = new TextField();
        TextField tfCustomerCity = new TextField();




        //Add to Hbox
        vBoxLeft.getChildren().addAll(lMovieHeader, lSearch, tfSearch, lActor, tfActor, cbCategory, lReleaseDate, tfReleaseDate, cbLanguages, bCreateMovie,bSearchMovie);
        vBoxRight.getChildren().addAll(cbStaff,lCustomerHeader, lCustomerName, tfCustomerName, lCustomerCity, tfCustomerCity, lCustomerId, tfCustomerId, lCustomerEmail, tfCustomerEmail, bCreateCustomer,bSearchCustomer);


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
