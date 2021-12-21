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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import javax.persistence.*;


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

        Stage loginStage = new Stage(); //To login

        primaryStage.setTitle("Uthyrning");
        BorderPane borderPane = new BorderPane();
        Fetch fetch = new Fetch();
        FXBuilder fxBuilder = new FXBuilder();

        // Combobox
        ComboBox cbCategory = new ComboBox(olCategory);
        cbCategory.setPromptText("Kategori");
        ComboBox cbLanguages = new ComboBox(olLanguages);
        cbLanguages.setPromptText("Språk");

        ComboBox cbAddCategory = new ComboBox(olCategory);
        cbAddCategory.setPromptText("Kategori");
        ComboBox cbAddLanguages = new ComboBox(olLanguages);
        cbAddLanguages.setPromptText("Språk");
        fetch.addToComboList(olCategory, ENTITY_MANAGER_FACTORY, "name", "category");
        fetch.addToComboList(olLanguages, ENTITY_MANAGER_FACTORY, "name", "language");
        // Lists
        ListView lvSearchResults = new ListView(olSearchResults);

        //Boxes
        VBox vBoxMovieAdd = new VBox();
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
        vBoxCustomerInfoLeft.setPadding(new Insets(0, 5, 0, 0));
        VBox vBoxCustomerInfoRight = new VBox();
        VBox vBoxMovieInfoLeft = new VBox();
        vBoxMovieInfoLeft.setPadding(new Insets(0, 5, 0, 0));
        VBox vBoxMovieInfoRight = new VBox();

        VBox vBoxAddCustomer = new VBox();

        VBox vBoxLeft = new VBox();

        //Logout och EXIT
        VBox vBoxExit = new VBox();
        vBoxExit.setPadding(new Insets(10));
        VBox vBoxLogout = new VBox();
        vBoxLogout.setPadding(new Insets(10));
        HBox hBoxLogout = new HBox();
        hBoxLogout.setSpacing(10);
        hBoxLogout.setPadding(new Insets(10, 0, 0, 0));
        HBox hBoxExit = new HBox();
        hBoxExit.setSpacing(10);
        hBoxExit.setPadding(new Insets(10, 0, 0, 0));


        //Buttons
        Button bSearchMovie = new Button("Sök");
        Button bSearchCustomer = new Button("Sök");
        Button bAdvancedSearchMovies = new Button("Avancerad sökning");
        Button bAdvancedSearchCustomer = new Button("Avancerad sökning");
        Button bCreateMovie = new Button("Lägg till");
        Button bCreateCustomer = new Button("Lägg till");
        Button bUpdateCustomer = new Button("Redigera");
        Button bUpdateMovie = new Button("Redigera");
        Button bRentMovie = new Button("Hyra");
        Button bReturnMovie = new Button("Lämna tillbaka");

        //Logout buttons
        Button bConfirmLogout = new Button("Logga ut");
        Button bCancelLogout = new Button("Avbryt");
        Button bConfirmExit = new Button("Avsluta");
        Button bCancelExit = new Button("Avbryt");

        //Meny
        MenuBar menuBar = new MenuBar();
        Menu mbFile = new Menu("Arkiv");
        MenuItem miFileLogOut = new MenuItem("Logga ut");
        MenuItem miFileOptions = new MenuItem("Alternativ");
        MenuItem miFileOptionsFontsize = new MenuItem("Ställ in fontstorlek");
        MenuItem miFileOptionsShortcut = new MenuItem("Genvägar");
        MenuItem miFilePrint = new MenuItem("Skriv ut");
        MenuItem miFileExit = new MenuItem("Avsluta");
        mbFile.getItems().addAll(miFileLogOut, miFileOptions, miFileOptionsFontsize, miFileOptionsShortcut, miFilePrint, miFileExit);
        Menu mbStaff = new Menu("Anställda");
        MenuItem miStaffAdd = new MenuItem("Lägg till");
        MenuItem miStaffEdit = new MenuItem("Redigera");
        MenuItem miStaffDelete = new MenuItem("Ta bort");
        mbStaff.getItems().addAll(miStaffAdd, miStaffEdit, miStaffDelete);
        Menu mbCustomer = new Menu("Kund");
        MenuItem miCustomerAdd = new MenuItem("Lägg till");
        MenuItem miCustomerEdit = new MenuItem("Redigera");
        MenuItem miCustomerRentedBy = new MenuItem("Vem hyr?");
        MenuItem miCustomerLockedOut = new MenuItem("Portad");
        mbCustomer.getItems().addAll(miCustomerAdd, miCustomerEdit, miCustomerRentedBy, miCustomerLockedOut);
        Menu mbFilm = new Menu("Film");
        MenuItem miFilmAdd = new MenuItem("Lägg till");
        MenuItem miFilmEdit = new MenuItem("Redigera");
        MenuItem miFilmDelete = new MenuItem("Ta bort");
        mbFilm.getItems().addAll(miFilmAdd, miFilmEdit, miFilmDelete);
        Menu mbHelp = new Menu("Hjälp");
        MenuItem miHelpInstructions = new MenuItem("Instruktioner");
        MenuItem miHelpMovieOfTheDay = new MenuItem("Dagens filmtips");
        MenuItem miHelpContactSupport = new MenuItem("Kontakta supporten");
        MenuItem miHelpAboutTheService = new MenuItem("Om tjänsten");
        mbHelp.getItems().addAll(miHelpInstructions, miHelpMovieOfTheDay, miHelpContactSupport, miHelpAboutTheService);

        menuBar.getMenus().addAll(mbFile, mbCustomer, mbStaff, mbFilm, mbHelp);

        //Labels
        //Movie
        Label lMovieHeader = new Label("Filmsektion");
        lMovieHeader.setFont(new Font(40));
        Label lSearchTitle = new Label("Sök titel");
        Label lMovieInfoId = new Label("FilmID");
        Label lMovieInfoDescription = new Label("Beskrivning");
        Label lMovieInfoActors = new Label("Skådespelare");
        Label lMovieInfoOriginalLanguage = new Label("Originalspråk");
        Label lMovieInfoLength = new Label("Längd");
        Label lMovieInfoRating = new Label("Betyg");
        Label lMovieInfoRentalCost = new Label("Hyreskostnad");
        Label lMovieInfoReplacementCost = new Label("Ersättningskostnad");
        Label lMovieInfoInStore = new Label("Tillgänglighet");
        Label lMovieInfoSpecialFeatures = new Label("Extramaterial");
        Label lMovieInfoRentalDuration = new Label("Hyrestid");
        Label lMovieInfoLastUpdate = new Label("Senast uppdaterad");

        Label lAddTitle = new Label("Titel");
        Label lMovieAddId = new Label("FilmID");
        Label lMovieAddDescription = new Label("Beskrivning");
        Label lMovieAddActors = new Label("Skådespelare");
        Label lMovieAddOriginalLanguage = new Label("Originalspråk");
        Label lMovieAddLength = new Label("Längd");
        Label lMovieAddRating = new Label("Betyg");
        Label lMovieAddRentalCost = new Label("Hyreskostnad");
        Label lMovieAddReplacementCost = new Label("Ersättningskostnad");
        Label lMovieAddInStore = new Label("Tillgänglighet");
        Label lMovieAddSpecialFeatures = new Label("Extramaterial");
        Label lMovieAddRentalDuration = new Label("Hyrestid");
        Label lMovieAddLastUpdate = new Label("Senast uppdaterad");

        //Customer
        Label lCustomerHeader = new Label("Kundsektion");
        lCustomerHeader.setFont(new Font(40));
        Label lCustomerName = new Label("Kundnamn");
        Label lCustomerId = new Label("KundID");
        Label lCustomerEmail = new Label("Kundmail");
        Label lCustomerInfoCity = new Label("Stad");
        Label lCustomerInfoStoreId = new Label("ButikID");
        Label lCustomerInfoAddress = new Label("Adress");
        Label lCustomerInfoPhone = new Label("Telefonnummer");
        Label lCustomerInfoRegistered = new Label("Registrerad");
        Label lCustomerInfoActive = new Label("Aktiv");
        Label lCustomerInfoUpdate = new Label("Uppdaterad");
        Label lTest = new Label("Funka då!");

        //Customer add
        Label lAddCustomerFirstName = new Label("Förnamn");
        Label lAddCustomerLastName = new Label("Efternamn");
        Label lAddCustomerEmail = new Label("Kundmail");
        Label lAddCustomerStoreId = new Label("ButikID");
        Label lAddCustomerRegistered = new Label("Registrerad");
        Label lAddCustomerActive = new Label("Aktiv");

        Label lAddCustomerAddress = new Label("Adress");
        Label lAddCustomerPostalCode = new Label("Postkod");
        Label lAddCustomerDistrict = new Label("Distrikt");
        Label lAddCustomerPhone = new Label("Telefonnummer");
        Label lAddCustomerCity = new Label("Stad");
        Label lAddCustomerCountry = new Label("Land");

        Label lAddCustomerInfoUpdate = new Label("Uppdaterad");

        //File
        Label lConfirmLogout = new Label("Är du säker?");
        Label lConfirmExit = new Label("Är du säker?");


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
        TextField tfCustomerInfoCity = new TextField();
        TextField tfCustomerInfoStoreId = new TextField();
        TextField tfCustomerInfoAddress = new TextField();
        TextField tfCustomerInfoPhone = new TextField();
        TextField tfCustomerInfoRegistered = new TextField();
        TextField tfCustomerInfoActive = new TextField();
        TextField tfCustomerInfoUpdate = new TextField();

        //Text fields add customer
        TextField tfAddCustomerFirstName = new TextField();
        tfAddCustomerFirstName.setPromptText("Förnamn");
        TextField tfAddCustomerLastName = new TextField();
        tfAddCustomerLastName.setPromptText("Efternamn");
        TextField tfAddCustomerEmail = new TextField();
        tfAddCustomerEmail.setPromptText("Mailadress");
        TextField tfAddCustomerStoreId = new TextField();
        tfAddCustomerStoreId.setPromptText("Butiksid");
        TextField tfAddCustomerRegistered = new TextField();
        tfAddCustomerRegistered.setPromptText("Registreringsdatum?");
        TextField tfAddCustomerActive = new TextField();
        tfAddCustomerActive.setPromptText("Aktiv?");

        TextField tfAddCustomerAddress = new TextField();
        tfAddCustomerAddress.setPromptText("Adress");
        TextField tfAddCustomerPostalCode = new TextField();
        tfAddCustomerPostalCode.setPromptText("Postkod");
        TextField tfAddCustomerDistrict = new TextField();
        tfAddCustomerDistrict.setPromptText("Distrikt");
        TextField tfAddCustomerPhone = new TextField();
        tfAddCustomerPhone.setPromptText("Telefonnummer");
        TextField tfAddCustomerCity = new TextField();
        tfAddCustomerCity.setPromptText("Stad");
        TextField tfAddCustomerCountrys = new TextField();
        tfAddCustomerCountrys.setPromptText("Land");


        //Text fields movies
        TextField tfMovieInfoId = new TextField();
        TextField tfMovieInfoDescription = new TextField();
        TextField tfMovieInfoActors = new TextField();
        TextField tfMovieInfoOriginalLanguage = new TextField();
        TextField tfMovieInfoLength = new TextField();
        TextField tfMovieInfoRating = new TextField();
        TextField tfMovieInfoRentalCost = new TextField();
        TextField tfMovieInfoReplacementCost = new TextField();
        TextField tfMovieInfoInStore = new TextField();
        TextField tfMovieInfoSpecialFeatures = new TextField();
        TextField tfMovieInfoRentalDuration = new TextField();
        TextField tfMovieInfoLastUpdate = new TextField();
        TextField tfMovieInfoTest = new TextField();

        TextField tfAddTitle = new TextField();
        TextField tfMovieAddId = new TextField();
        TextField tfMovieAddDescription = new TextField();
        TextField tfMovieAddActors = new TextField();
        TextField tfMovieAddOriginalLanguage = new TextField();
        TextField tfMovieAddLength = new TextField();
        TextField tfMovieAddRating = new TextField();
        TextField tfMovieAddRentalCost = new TextField();
        TextField tfMovieAddReplacementCost = new TextField();
        TextField tfMovieAddInStore = new TextField();
        TextField tfMovieAddSpecialFeatures = new TextField();
        TextField tfMovieAddRentalDuration = new TextField();
        TextField tfMovieAddLastUpdate = new TextField();
        TextField tfMovieAddTest = new TextField();


        VBox vboxTest = new VBox();
        vboxTest.getChildren().addAll(lTest);
        HBox hboxTest = new HBox();
        hboxTest.getChildren().addAll(tfMovieInfoTest);

        //Popup
        //Movie - Add
        miFilmAdd.setOnAction(event -> {
            fxBuilder.createPopUp(vBoxMovieAdd);
        });

        //customer
        miCustomerAdd.setOnAction(event -> {
            fxBuilder.createPopUp(vBoxAddCustomer);
        });

        //Menu - File
        miFileLogOut.setOnAction(event -> {
            fxBuilder.createPopUp(vBoxLogout);
        });
        miFileExit.setOnAction(event -> {
            fxBuilder.createPopUp(vBoxExit);
        });


        //Add button function
        bAdvancedSearchCustomer.setOnAction(event -> {
            if (hBoxAdvancedSearchCustomer.isVisible()) {
                hBoxAdvancedSearchCustomer.setVisible(false);
            } else {
                hBoxAdvancedSearchCustomer.setVisible(true);
            }
        });
        bAdvancedSearchMovies.setOnAction(event -> {
            if (hBoxAdvancedSearchMovies.isVisible()) {
                hBoxAdvancedSearchMovies.setVisible(false);
            } else {
                hBoxAdvancedSearchMovies.setVisible(true);
            }
        });

        bSearchMovie.setOnAction(event -> {
            fetch.searchFromMovies(tfSearch, olSearchResults, ENTITY_MANAGER_FACTORY, "title", "film");
        });

        bConfirmLogout.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            primaryStage.close();
            loginStage.show();
        });

        bCancelLogout.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        bConfirmExit.setOnAction(event -> {
            primaryStage.close();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        bCancelExit.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });


        //Add to boxes
        vBoxRight.getChildren().addAll(vBoxCustomerSearch, hBoxAdvancedSearchCustomer);

        vBoxLeft.getChildren().addAll(vBoxMovieSearch, hBoxAdvancedSearchMovies);

        vBoxCenter.getChildren().addAll(lvSearchResults);

        vBoxMovieAdd.getChildren().addAll(lMovieHeader, lAddTitle, tfAddTitle, lMovieAddRentalCost, tfMovieAddRentalCost,
                cbAddCategory, lMovieAddDescription, tfMovieAddDescription,
                lMovieAddLength, tfMovieAddLength, lMovieAddRating, tfMovieAddRating, lMovieAddOriginalLanguage,
                tfMovieAddOriginalLanguage, cbAddLanguages, lMovieAddActors, tfMovieAddActors, lMovieAddSpecialFeatures, tfMovieAddSpecialFeatures,
                lMovieAddRentalDuration, tfMovieAddRentalDuration, lMovieAddReplacementCost, tfMovieAddReplacementCost,
                lMovieAddInStore, tfMovieAddInStore, lMovieAddLastUpdate, tfMovieAddLastUpdate, bCreateMovie);

        //Logout
        hBoxLogout.getChildren().addAll(bConfirmLogout,bCancelLogout);
        vBoxLogout.getChildren().addAll(lConfirmLogout,hBoxLogout);

        //Exit
        hBoxExit.getChildren().addAll(bConfirmExit,bCancelExit);
        vBoxExit.getChildren().addAll(lConfirmExit,hBoxExit);

        //Movie
        vBoxMovieSearch.getChildren().addAll(lMovieHeader, lSearchTitle, tfSearch, lMovieInfoRentalCost, tfMovieInfoRentalCost,
                cbCategory, bAdvancedSearchMovies, bSearchMovie);

        hBoxAdvancedSearchMovies.getChildren().addAll(vBoxMovieInfoLeft, vBoxMovieInfoRight);

        vBoxMovieInfoLeft.getChildren().addAll(lMovieInfoId, tfMovieInfoId, lMovieInfoDescription, tfMovieInfoDescription,
                lMovieInfoLength, tfMovieInfoLength, lMovieInfoRating, tfMovieInfoRating, lMovieInfoOriginalLanguage,
                tfMovieInfoOriginalLanguage, cbLanguages);

        vBoxMovieInfoRight.getChildren().addAll(lMovieInfoActors, tfMovieInfoActors, lMovieInfoSpecialFeatures, tfMovieInfoSpecialFeatures,
                lMovieInfoRentalDuration, tfMovieInfoRentalDuration, lMovieInfoReplacementCost, tfMovieInfoReplacementCost,
                lMovieInfoInStore, tfMovieInfoInStore, lMovieInfoLastUpdate, tfMovieInfoLastUpdate);

        //Customer
        vBoxCustomerSearch.getChildren().addAll(lCustomerHeader, lCustomerName, tfCustomerName, lCustomerId,
                tfCustomerId, lCustomerEmail, tfCustomerEmail, bAdvancedSearchCustomer, bSearchCustomer);

        hBoxAdvancedSearchCustomer.getChildren().addAll(vBoxCustomerInfoLeft, vBoxCustomerInfoRight);

        vBoxCustomerInfoLeft.getChildren().addAll(lCustomerInfoAddress, tfCustomerInfoAddress, lCustomerInfoCity, tfCustomerInfoCity,
                lCustomerInfoPhone, tfCustomerInfoPhone);

        vBoxCustomerInfoRight.getChildren().addAll(lCustomerInfoRegistered,
                tfCustomerInfoRegistered, lCustomerInfoActive, tfCustomerInfoActive, lCustomerInfoUpdate, tfCustomerInfoUpdate,
                lCustomerInfoStoreId, tfCustomerInfoStoreId);

        //Add cutomer
        vBoxAddCustomer.getChildren().addAll(lAddCustomerFirstName, tfAddCustomerFirstName, lAddCustomerLastName, tfAddCustomerLastName, lAddCustomerEmail, tfAddCustomerEmail,
                lAddCustomerStoreId, tfAddCustomerStoreId, lAddCustomerRegistered, tfAddCustomerRegistered, lAddCustomerActive, tfAddCustomerActive, lAddCustomerAddress, tfAddCustomerAddress,
                lAddCustomerPostalCode,tfAddCustomerPostalCode, lAddCustomerDistrict, tfAddCustomerDistrict, lAddCustomerPhone, tfAddCustomerPhone, lAddCustomerCity, tfAddCustomerCity,
                lAddCustomerCountry, tfAddCustomerCountrys, bCreateCustomer);

        //Add to borderPane
        borderPane.setTop(menuBar);
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(vBoxCenter);

        //Test Login--------------------------------------------------------------------

        loginStage.setTitle("Logga in");
        BorderPane borderPaneLogin = new BorderPane();
        Scene loginScen = new Scene(borderPaneLogin,400,400);
        borderPaneLogin.setPadding(new Insets(20));

        //TextFields
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Användarnamn");
        TextField tfPassword = new PasswordField();
        tfPassword.setPromptText("Lösenord");

        //Labels
        Label lWelcome = new Label("Välkommen");
        lWelcome.setFont(Font.font("Tahoma", FontWeight.LIGHT,30));
        lWelcome.setPadding(new Insets(0, 0, 30, 0));
        Label lUsername = new Label("Användarnamn");
        lUsername.setFont(Font.font(18));
        Label lPassword = new Label("Lösenord");
        lPassword.setFont(Font.font(18));

        //Button
        Button bLogin = new Button("Logga in");
        Button bCancel = new Button("Avbryt");

        //Boxes
        VBox vBoxtfLogin = new VBox(tfUsername,tfPassword);
        VBox vBoxLLogin = new VBox(lUsername,lPassword);
        vBoxLLogin.setPadding(new Insets(10));
        vBoxtfLogin.setPadding(new Insets(10));
        HBox hBoxButtons = new HBox(bLogin,bCancel);
        hBoxButtons.setSpacing(10);
        hBoxButtons.setPadding(new Insets(10, 0, 0, 95));
        HBox hBoxLogin = new HBox(vBoxLLogin,vBoxtfLogin);
        VBox vBoxAllOfLogin = new VBox(lWelcome,hBoxLogin,hBoxButtons);

        //Buttons action
        bCancel.setOnAction(event -> {
            tfUsername.clear();
            tfPassword.clear();
        });
        bLogin.setOnAction(event -> {
            fetch.login(ENTITY_MANAGER_FACTORY,tfUsername,tfPassword,primaryStage,loginStage);
            tfPassword.clear();
            tfUsername.clear();
        });

        //Position of boxes
        hBoxButtons.setAlignment(Pos.CENTER_LEFT);
        hBoxButtons.setAlignment(Pos.BOTTOM_CENTER);
        borderPaneLogin.setCenter(vBoxAllOfLogin);
        vBoxAllOfLogin.setAlignment(Pos.CENTER);

        loginStage.setScene(loginScen);
        loginStage.show();


        ///---------------------------------------------------------------------------------------
        Scene scene = new Scene(borderPane, 1200, 900);
        primaryStage.setScene(scene);
        //primaryStage.show();

    }
}
