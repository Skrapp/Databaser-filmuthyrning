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
        VBox vBoxUpLeft = new VBox();
        vBoxUpLeft.setPadding(new Insets(10));
        VBox vBoxUpRight = new VBox();
        vBoxUpRight.setPadding(new Insets(10));
        VBox vBoxCenter = new VBox();
        vBoxCenter.setPadding(new Insets(10));
        VBox vBoxDownRight = new VBox();
        vBoxDownRight.setPadding(new Insets(10));
        VBox vBoxRight = new VBox();
        vBoxRight.setPadding(new Insets(10));
        VBox vBoxDownLeft = new VBox();
        vBoxDownLeft.setPadding(new Insets(10));
        VBox vBoxLeft = new VBox();

        HBox hBoxTop= new HBox();
        HBox hBoxRight = new HBox();

        //Buttons
        Button bSearchMovie = new Button("Sök");
        Button bSearchCustomer = new Button("Sök");
        Button bCreateMovie = new Button("Lägg till");
        Button bCreateCustomer = new Button("Lägg till");
        Button bUpdateCustomer = new Button("Redigera");
        Button bUpdateMovie = new Button("Redigera");
        Button bRentMovie = new Button("Hyra");
        Button bReturnMovie = new Button("Lämna tillbaka");

        //Labels
        //Movie
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(40));
        Label lSearch = new Label("Sök");
        Label lActor = new Label("Skådespelare");
        Label lReleaseDate = new Label("Utgivningsdatum");
        Label lMovieInfoId = new Label("FilmId");
        Label lMovieInfoTitle = new Label("Titel");
        Label lMovieInfoDescription = new Label("Beskrivning");
        Label lMovieInfoActors = new Label("Skådespelare");
        Label lMovieInfoCategory = new Label("Kategori");
        Label lMovieInfoLanguage = new Label("Språk");
        Label lMovieInfoOriginalLanguage = new Label("OrginalSpråk");
        Label lMovieInfoLength = new Label("Längd");
        Label lMovieInfoRating = new Label("Betyg");
        Label lMovieInfoRentalCost = new Label("HyrKostnad");
        Label lMovieInfoReplacementCost = new Label("ErsättningsKostnad");
        Label lMovieInfoInStore = new Label("Tillgänglighet");
        Label lMovieInfoSpecialFeatures = new Label("ExtraMaterial");
        Label lMovieInfoRentalDuration = new Label("Hyrtid");
        Label lMovieInfoLastUpdate = new Label("SenastUppdaterad");

        //Customer
        Label lCustomerHeader = new Label("Kundsektion");
        lCustomerHeader.setFont(new Font(40));
        Label lCustomerName = new Label("Kundnamn");
        Label lCustomerId = new Label("KundId");
        Label lCustomerEmail = new Label("Kundmail");
        Label lCustomerCity = new Label("Stad");
        Label lCustomerInfoId = new Label("KundId");
        Label lCustomerInfoName = new Label("Namn");
        Label lCustomerInfoEmail = new Label("Email");
        Label lCustomerInfoCity = new Label("Stad");
        Label lCustomerInfoStoreId = new Label("ButikId");
        Label lCustomerInfoAddress = new Label("Adress");
        Label lCustomerInfoPhone = new Label("Telefonnummer");
        Label lCustomerInfoRegistered = new Label("Registrerad");
        Label lCustomerInfoActive = new Label("Aktiv");
        Label lCustomerInfoUpdate = new Label("Uppdaterad");

        //Textfields
        //Movie
        TextField tfSearch = new TextField();
        tfSearch.setPromptText("Sök");
        TextField tfActor = new TextField();
        tfActor.setPromptText("Skådespelare");
        TextField tfReleaseDate = new TextField();
        tfReleaseDate.setPromptText("yyyy-mm-dd");

        //Customer
        TextField tfCustomerName = new TextField();
        tfCustomerName.setPromptText("Kundnamn");
        TextField tfCustomerId = new TextField();
        tfCustomerId.setPromptText("KundId");
        TextField tfCustomerEmail = new TextField();
        tfCustomerEmail.setPromptText("KundMail");
        TextField tfCustomerCity = new TextField();
        tfCustomerCity.setPromptText("Stad");

        //Text fields customer
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

        //Text fields movies
        TextField tfMovieInfoId = new TextField();
        TextField tfMovieInfoTitle = new TextField();
        TextField tfMovieInfoDescription = new TextField();
        TextField tfMovieInfoActors = new TextField();
        TextField tfMovieInfoCategory = new TextField();
        TextField tfMovieInfoLanguage = new TextField();
        TextField tfMovieInfoOriginalLanguage = new TextField();
        TextField tfMovieInfoLength = new TextField();
        TextField tfMovieInfoRating = new TextField();
        TextField tfMovieInfoRentalCost = new TextField();
        TextField tfMovieInfoReplacementCost = new TextField();
        TextField tfMovieInfoInStore = new TextField();
        TextField tfMovieInfoSpecialFeatures = new TextField();
        TextField tfMovieInfoRentalDuration = new TextField();
        TextField tfMovieInfoLastUpdate = new TextField();

        //Add to HBox

        //Add to Vbox
        vBoxUpLeft.getChildren().addAll(lMovieHeader, lSearch, tfSearch, lActor, tfActor, cbCategory, lReleaseDate,
                tfReleaseDate, cbLanguages,bSearchMovie);
        vBoxUpRight.getChildren().addAll(cbStaff,lCustomerHeader, lCustomerName, tfCustomerName, lCustomerCity,
                tfCustomerCity, lCustomerId, tfCustomerId, lCustomerEmail, tfCustomerEmail, bSearchCustomer);
                vBoxCenter.getChildren().addAll(lvSearchResults);
        vBoxDownLeft.getChildren().addAll(lMovieInfoId,tfMovieInfoId, lMovieInfoTitle,tfMovieInfoTitle,lMovieInfoCategory,
                tfMovieInfoCategory, lMovieInfoDescription,tfMovieInfoDescription,
                lMovieInfoLength,tfMovieInfoLength, lMovieInfoRating,tfMovieInfoRating, lMovieInfoOriginalLanguage,
                tfMovieInfoOriginalLanguage, lMovieInfoLanguage,tfMovieInfoLanguage, lMovieInfoActors,tfMovieInfoActors,
                lMovieInfoSpecialFeatures,tfMovieInfoSpecialFeatures, lMovieInfoRentalCost,tfMovieInfoRentalCost,
                lMovieInfoRentalDuration,tfMovieInfoRentalDuration, lMovieInfoReplacementCost,tfMovieInfoReplacementCost,
                lMovieInfoInStore,tfMovieInfoInStore, lMovieInfoLastUpdate,tfMovieInfoLastUpdate, bCreateMovie, bUpdateMovie );
        vBoxDownRight.getChildren().addAll(lCustomerInfoId,tfCustomerInfoId, lCustomerInfoName,tfCustomerInfoName,
                lCustomerInfoAddress,tfCustomerInfoAddress, lCustomerInfoCity,tfCustomerInfoCity,
                lCustomerInfoPhone,tfCustomerInfoPhone, lCustomerInfoEmail,tfCustomerInfoEmail, lCustomerInfoRegistered,
                tfCustomerInfoRegistered, lCustomerInfoActive,tfCustomerInfoActive, lCustomerInfoUpdate,tfCustomerInfoUpdate,
                lCustomerInfoStoreId,tfCustomerInfoStoreId,bCreateCustomer, bUpdateCustomer);
        vBoxRight.getChildren().addAll(vBoxUpRight, vBoxDownRight);
        vBoxLeft.getChildren().addAll(vBoxUpLeft, vBoxDownLeft);

        //Add to borderPane
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(vBoxCenter);

        Scene scene = new Scene(borderPane,1200,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
