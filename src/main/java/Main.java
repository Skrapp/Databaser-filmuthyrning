import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    final ObservableList olSearchResults = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Uthyrning");
        BorderPane borderPane = new BorderPane();

        // Combobox
        ComboBox cbCategory = new ComboBox(olCategory);
        cbCategory.setPromptText("Kategori");
        ComboBox cbLanguages = new ComboBox(olLanguages);
        cbLanguages.setPromptText("Språk");
        ComboBox cbStaff = new ComboBox(olStaff);
        cbStaff.setPromptText("Anställda");


        // Lists
        ListView lvSearchResults = new ListView(olSearchResults);


        //Boxes
        VBox vBoxLeft = new VBox();
        vBoxLeft.setPadding(new Insets(10));
        VBox vBoxUpRight = new VBox();
        vBoxUpRight.setPadding(new Insets(10));
        VBox vBoxCenter = new VBox();
        vBoxCenter.setPadding(new Insets(10));
        VBox vBoxDownRight = new VBox();
        vBoxDownRight.setPadding(new Insets(10));
        VBox vBoxRight = new VBox();
        vBoxRight.setPadding(new Insets(10));

        HBox hBoxTop= new HBox(); // jag menade hbox.
        HBox hBoxRight = new HBox(); //Ja, det kan bli jättefel.

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
        Label lCustomerCity = new Label("Stad");
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(40));
        Label lCustomerHeader = new Label("Kundsektion");
        lCustomerHeader.setFont(new Font(40));



        //Textfields
        TextField tfSearch = new TextField();
        tfSearch.setPromptText("Sök");
        TextField tfActor = new TextField();
        tfActor.setPromptText("Skådespelare");
        TextField tfReleaseDate = new TextField();
        tfReleaseDate.setPromptText("yyyy-mm-dd");
        TextField tfCustomerName = new TextField();
        tfCustomerName.setPromptText("Kundnamn");
        TextField tfCustomerId = new TextField();
        tfCustomerId.setPromptText("KundId");
        TextField tfCustomerEmail = new TextField();
        tfCustomerEmail.setPromptText("KundMail");
        TextField tfCustomerCity = new TextField();
        tfCustomerCity.setPromptText("Stad");

        //Textfields kund
        TextField tfCustomerInfoId = new TextField();
        TextField tfCustomerInfoName = new TextField();
        TextField tfCustomerInfoEmail = new TextField();
        TextField tfCustomerInfoCity = new TextField();
        TextField tfCustomerInfoStoreId = new TextField();
        TextField tfCustomerInfoAddress = new TextField();
        TextField tfCustomerInfoPhone = new TextField();
        TextField tfCustomerInfoRegistered = new TextField();
        TextField tfCustomerInfoActive = new TextField();
        TextField tfCustomerInfoUpdate = new TextField();


        //Add to Hbox
        vBoxLeft.getChildren().addAll(lMovieHeader, lSearch, tfSearch, lActor, tfActor, cbCategory, lReleaseDate, tfReleaseDate, cbLanguages, bCreateMovie,bSearchMovie);
        vBoxUpRight.getChildren().addAll(cbStaff,lCustomerHeader, lCustomerName, tfCustomerName, lCustomerCity, tfCustomerCity, lCustomerId, tfCustomerId, lCustomerEmail, tfCustomerEmail, bCreateCustomer,bSearchCustomer);
        vBoxCenter.getChildren().addAll(lvSearchResults);
        vBoxDownRight.getChildren().addAll(tfCustomerInfoId, tfCustomerInfoName, tfCustomerInfoAddress, tfCustomerInfoCity, tfCustomerInfoPhone, tfCustomerInfoEmail, tfCustomerInfoRegistered, tfCustomerInfoActive, tfCustomerInfoUpdate, tfCustomerInfoStoreId);
        vBoxRight.getChildren().addAll(vBoxUpRight, vBoxDownRight);
        //Add to VBox
        //hBoxTop.getChildren().addAll(vBoxLeft, vBoxUpRight);
        vBoxUpRight.setAlignment(Pos.TOP_RIGHT);
        vBoxLeft.setAlignment(Pos.TOP_LEFT);

        //Add to borderPane
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(vBoxCenter);

        Scene scene = new Scene(borderPane,1200,900);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
