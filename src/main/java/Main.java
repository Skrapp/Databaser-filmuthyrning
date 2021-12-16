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

    final ObservableList olCategory = FXCollections.observableArrayList(
            "Här",
            "och här" //Om vi ska lägga in dom manuellt alltså.
    );
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
        Fetch fetch = new Fetch();

        // Combobox
        ComboBox cbCategory = new ComboBox(olCategory);
        cbCategory.setPromptText("Kategori");
        ComboBox cbLanguages = new ComboBox(olLanguages);
        cbLanguages.setPromptText("Språk");
        ComboBox cbStaff = new ComboBox(olStaff);
        cbStaff.setPromptText("Anställda");
        fetch.addToComboList(olCategory, ENTITY_MANAGER_FACTORY,"name","category");
        fetch.addToComboList(olLanguages, ENTITY_MANAGER_FACTORY,"name","language");
        // Lists
        ListView lvSearchResults = new ListView(olSearchResults);

        //Boxes
        VBox vBoxMovieSearch = new VBox();
        vBoxMovieSearch.setPadding(new Insets(10));
        VBox vBoxCustomerSearch = new VBox();
        vBoxCustomerSearch.setPadding(new Insets(10));
        VBox vBoxCenter = new VBox();
        vBoxCenter.setPadding(new Insets(10));
        VBox vBoxRight = new VBox();
        vBoxRight.setPadding(new Insets(10));

        HBox hBoxAdvancedSearchMovies = new HBox();
        hBoxAdvancedSearchMovies.setPadding(new Insets(10));
        hBoxAdvancedSearchMovies.setVisible(false);
        HBox hBoxAdvancedSearchCustomer = new HBox();
        hBoxAdvancedSearchCustomer.setPadding(new Insets(10));
        hBoxAdvancedSearchCustomer.setVisible(false);
        VBox vBoxCustomerInfoLeft = new VBox();
        vBoxCustomerInfoLeft.setPadding(new Insets(0,5,0,0));
        VBox vBoxCustomerInfoRight = new VBox();
        VBox vBoxMovieInfoLeft = new VBox();
        vBoxMovieInfoLeft.setPadding(new Insets(0,5,0,0));
        VBox vBoxMovieInfoRight = new VBox();

        VBox vBoxAdvancedSearchMovies = new VBox();
        VBox vBoxAdvancedSearchCustomer = new VBox();


        VBox vBoxLeft = new VBox();


        //Buttons
        Button bSearchMovie = new Button("Sök");
        Button bSearchCustomer = new Button("Sök");
        Button bCreateMovie = new Button("Lägg till");
        Button bCreateCustomer = new Button("Lägg till");
        Button bUpdateCustomer = new Button("Redigera");
        Button bUpdateMovie = new Button("Redigera");
        Button bRentMovie = new Button("Hyra");
        Button bReturnMovie = new Button("Lämna tillbaka");
        Button bAdvancedSearchMovies = new Button("Avancerad sökning");
        Button bAdvancedSearchCustomer = new Button("Avancerad sökning");

        bAdvancedSearchCustomer.setOnAction(event -> {
            if (hBoxAdvancedSearchCustomer.isVisible()) {
                hBoxAdvancedSearchCustomer.setVisible(false);
            }
            else {
                hBoxAdvancedSearchCustomer.setVisible(true);
            }
        });
        bAdvancedSearchMovies.setOnAction(event -> {
            if (hBoxAdvancedSearchMovies.isVisible()) {
                hBoxAdvancedSearchMovies.setVisible(false);
            }
            else{
                hBoxAdvancedSearchMovies.setVisible(true);
            }
        });


        //Labels
        //Movie
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(40));
        Label lSearchTitle = new Label("Sök titel");
        Label lActor = new Label("Skådespelare");
        Label lReleaseDate = new Label("Utgivningsdatum");
        Label lMovieInfoId = new Label("FilmID");
        Label lMovieInfoTitle = new Label("Titel");
        Label lMovieInfoDescription = new Label("Beskrivning");
        Label lMovieInfoActors = new Label("Skådespelare");
        Label lMovieInfoCategory = new Label("Kategori");
        Label lMovieInfoLanguage = new Label("Språk");
        Label lMovieInfoOriginalLanguage = new Label("Originalspråk");
        Label lMovieInfoLength = new Label("Längd");
        Label lMovieInfoRating = new Label("Betyg");
        Label lMovieInfoRentalCost = new Label("Hyreskostnad");
        Label lMovieInfoReplacementCost = new Label("Ersättningskostnad");
        Label lMovieInfoInStore = new Label("Tillgänglighet");
        Label lMovieInfoSpecialFeatures = new Label("Extramaterial");
        Label lMovieInfoRentalDuration = new Label("Hyrestid");
        Label lMovieInfoLastUpdate = new Label("Senast uppdaterad");

        //Customer
        Label lCustomerHeader = new Label("Kundsektion");
        lCustomerHeader.setFont(new Font(40));
        Label lCustomerName = new Label("Kundnamn");
        Label lCustomerId = new Label("KundID");
        Label lCustomerEmail = new Label("Kundmail");
        Label lCustomerCity = new Label("Stad");
        Label lCustomerInfoId = new Label("KundID");
        Label lCustomerInfoName = new Label("Namn");
        Label lCustomerInfoEmail = new Label("Email");
        Label lCustomerInfoCity = new Label("Stad");
        Label lCustomerInfoStoreId = new Label("ButikID");
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
        tfCustomerId.setPromptText("KundID");
        TextField tfCustomerEmail = new TextField();
        tfCustomerEmail.setPromptText("Kundmail");
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
        vBoxMovieSearch.getChildren().addAll(lMovieHeader, lSearchTitle, tfSearch,lMovieInfoRentalCost,tfMovieInfoRentalCost, cbCategory, bAdvancedSearchMovies,bSearchMovie);

        vBoxCustomerSearch.getChildren().addAll(cbStaff,lCustomerHeader, lCustomerName, tfCustomerName,lCustomerId, tfCustomerId, lCustomerEmail, tfCustomerEmail,bAdvancedSearchCustomer, bSearchCustomer);

        vBoxCenter.getChildren().addAll(lvSearchResults);

        hBoxAdvancedSearchMovies.getChildren().addAll(vBoxMovieInfoLeft, vBoxMovieInfoRight);

        vBoxCustomerInfoLeft.getChildren().addAll(lCustomerInfoId,tfCustomerInfoId, lCustomerInfoName,tfCustomerInfoName,
                lCustomerInfoAddress,tfCustomerInfoAddress, lCustomerInfoCity,tfCustomerInfoCity,
                lCustomerInfoPhone,tfCustomerInfoPhone, lCustomerInfoEmail,tfCustomerInfoEmail);
        vBoxRight.getChildren().addAll(vBoxCustomerSearch, hBoxAdvancedSearchCustomer);
        vBoxLeft.getChildren().addAll(vBoxMovieSearch, hBoxAdvancedSearchMovies);
        vBoxMovieInfoLeft.getChildren().addAll(lMovieInfoId,tfMovieInfoId, lMovieInfoTitle,tfMovieInfoTitle,lMovieInfoCategory,
                tfMovieInfoCategory, lMovieInfoDescription,tfMovieInfoDescription,
                lMovieInfoLength,tfMovieInfoLength, lMovieInfoRating,tfMovieInfoRating, lMovieInfoOriginalLanguage, tfMovieInfoOriginalLanguage, bCreateMovie);
        vBoxMovieInfoRight.getChildren().addAll( cbLanguages, lMovieInfoActors,tfMovieInfoActors,
                lMovieInfoSpecialFeatures,tfMovieInfoSpecialFeatures,
                lMovieInfoRentalDuration,tfMovieInfoRentalDuration, lMovieInfoReplacementCost,tfMovieInfoReplacementCost,
                lMovieInfoInStore,tfMovieInfoInStore, lMovieInfoLastUpdate,tfMovieInfoLastUpdate,bUpdateMovie);
        vBoxCustomerInfoRight.getChildren().addAll(lCustomerInfoRegistered,
                tfCustomerInfoRegistered, lCustomerInfoActive,tfCustomerInfoActive, lCustomerInfoUpdate,tfCustomerInfoUpdate,
                lCustomerInfoStoreId,tfCustomerInfoStoreId,bCreateCustomer, bUpdateCustomer);
        hBoxAdvancedSearchCustomer.getChildren().addAll(vBoxCustomerInfoLeft,vBoxCustomerInfoRight);





        //Add to borderPane
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(vBoxCenter);

        Scene scene = new Scene(borderPane,1200,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
