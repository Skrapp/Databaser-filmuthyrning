import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class Main extends Application {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    final ObservableList olCategory = FXCollections.observableArrayList(
            "Här",
            "och här" //Om vi ska lägga in dom manuellt
    );
    final ObservableList olLanguages = FXCollections.observableArrayList();
    final ObservableList olStaff = FXCollections.observableArrayList();
    final ObservableList olSearchResults = FXCollections.observableArrayList();
    final ObservableList olRating = FXCollections.observableArrayList();
    final ObservableList olStores = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        olCategory.add(0,null);
        olLanguages.add(0,null);
        olRating.add(0,null);
        olStores.add(0,null);

        primaryStage.setTitle("Uthyrning");
        BorderPane borderPane = new BorderPane();
        Fetch fetch = new Fetch();
        FXBuilder fxBuilder = new FXBuilder();

        // Combobox
        //Movie search
        ComboBox cbMovieSearchCategory = new ComboBox(olCategory);
        cbMovieSearchCategory.setPromptText("Kategori");
        ComboBox cbMovieSearchLanguages = new ComboBox(olLanguages);
        cbMovieSearchLanguages.setPromptText("Språk");
        ComboBox cbMovieSearchRating = new ComboBox(olRating);
        cbMovieSearchRating.setPromptText("Betyg");
        ComboBox cbMovieSearchOriginalLanguage = new ComboBox(olLanguages);
        cbMovieSearchOriginalLanguage.setPromptText("Originalspråk");

        //Customer Search
        ComboBox cbCustomerSearchStoreId = new ComboBox(olStores);
        cbCustomerSearchStoreId.setPromptText("Butik ID");

        //Movie add
        ComboBox cbMovieAddCategory = new ComboBox(olCategory);
        cbMovieAddCategory.setPromptText("Kategori");
        ComboBox cbMovieAddLanguages = new ComboBox(olLanguages);
        cbMovieAddLanguages.setPromptText("Språk");

        //Add data to observable lists
        fetch.addToComboList(olCategory, ENTITY_MANAGER_FACTORY,"name","category");
        fetch.addToComboList(olLanguages, ENTITY_MANAGER_FACTORY,"name","language");
        fetch.addToComboList(olRating, ENTITY_MANAGER_FACTORY,"rating","film");
        fetch.addToComboList(olStores, ENTITY_MANAGER_FACTORY,"store_id","store");

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
        VBox vBoxCustomerSearchLeft = new VBox();
        vBoxCustomerSearchLeft.setPadding(new Insets(0,5,0,0));
        VBox vBoxCustomerSearchRight = new VBox();
        VBox vBoxMovieSearchLeft = new VBox();
        vBoxMovieSearchLeft.setPadding(new Insets(0,5,0,0));
        VBox vBoxMovieSearchRight = new VBox();

        VBox vBoxLeft = new VBox();

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
        Button bCustomerSearchClear= new Button("Töm sökning");
        Button bMovieSearchClear= new Button("Töm sökning");

        //Meny
        MenuBar menuBar = new MenuBar();
        Menu mbFile = new Menu("Arkiv");
            MenuItem miFileLogOut = new MenuItem("Byt användare");
            MenuItem miFileOptions = new MenuItem("Alternativ");
            MenuItem miFileOptionsFontsize = new MenuItem("Ställ in fontstorlek");
            MenuItem miFileOptionsShortcut = new MenuItem("Genvägar");
            MenuItem miFilePrint = new MenuItem("Skriv ut");
            MenuItem miFileExit = new MenuItem("Avsluta");
        mbFile.getItems().addAll(miFileLogOut, miFileOptions,miFileOptionsFontsize,miFileOptionsShortcut, miFilePrint, miFileExit);
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
        Label lMovieSearchTitle = new Label("Sök titel");
        Label lMovieSearchId = new Label("FilmID");
        Label lMovieSearchDescription = new Label("Beskrivning");
        Label lMovieSearchActors = new Label("Skådespelare");
        Label lMovieSearchOriginalLanguage = new Label("Originalspråk");
        Label lMovieSearchLength = new Label("Längd");
        Label lMovieSearchRating = new Label("Betyg");
        Label lMovieSearchRentalCost = new Label("Hyreskostnad");
        Label lMovieSearchReplacementCost = new Label("Ersättningskostnad");
        Label lMovieSearchInStore = new Label("Tillgänglighet");
        Label lMovieSearchSpecialFeatures = new Label("Extramaterial");
        Label lMovieSearchRentalDuration = new Label("Hyrestid");
        Label lMovieSearchLastUpdate = new Label("Senast uppdaterad");
        Label lMovieSearchReleaseDate = new Label("Utgivningsdatum");

        Label lMovieAddTitle = new Label("Titel");
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
        Label lCustomerSearchName = new Label("Kundnamn");
        Label lCustomerSearchId = new Label("KundID");
        Label lCustomerSearchEmail = new Label("Kundmail");
        Label lCustomerSearchCity = new Label("Stad");
        Label lCustomerSearchStoreId = new Label("ButikID");
        Label lCustomerSearchAddress = new Label("Adress");
        Label lCustomerSearchPhone = new Label("Telefonnummer");
        Label lCustomerSearchRegistered = new Label("Registrerad");
        Label lCustomerSearchActive = new Label("Aktiv");
        Label lCustomerSearchUpdate = new Label("Uppdaterad");

        //-----------------------------
        //Checkboxes
        //SpecialFeatures and InStore
        CheckBox chbMovieSearchSFTrailers = new CheckBox("Trailer");
        CheckBox chbMovieSearchInStore = new CheckBox("Bara tillgängliga");
        CheckBox chbCustomerSearchActive = new CheckBox("Bara aktiva");
        //-----------------------------



        //Textfields
        //Movie search
        TextField tfMovieSearchTitle = new TextField();
        tfMovieSearchTitle.setPromptText("Sök");
        TextField tfMovieSearchActor = new TextField();
        tfMovieSearchActor.setPromptText("Skådespelare");
        TextField tfMovieSearchReleaseYear = new TextField();
        tfMovieSearchReleaseYear.setPromptText("yyyy");
        TextField tfMovieSearchId = new TextField();
        tfMovieSearchId.setPromptText("Film ID");
        TextField tfMovieSearchDescription = new TextField();
        tfMovieSearchDescription.setPromptText("Beskrivning");
        TextField tfMovieSearchActors = new TextField();
        tfMovieSearchActors.setPromptText("Skådespelare förnamn");
        TextField tfMovieSearchLengthMin = new TextField();
        tfMovieSearchLengthMin.setPromptText("Kortaste spellängd");
        TextField tfMovieSearchLengthMax = new TextField();
        tfMovieSearchLengthMax.setPromptText("Längsta spellängd");
        TextField tfMovieSearchRentalCostMin = new TextField();
        tfMovieSearchRentalCostMin.setPromptText("Minsta hyreskostnaden");
        TextField tfMovieSearchRentalCostMax = new TextField();
        tfMovieSearchRentalCostMax.setPromptText("Största hyreskostnaden");
        TextField tfMovieSearchReplacementCostMin = new TextField();
        tfMovieSearchReplacementCostMin.setPromptText("Minsta ersättningskostnaden");
        TextField tfMovieSearchReplacementCostMax = new TextField();
        tfMovieSearchReplacementCostMax.setPromptText("Största ersättningskostnaden");
        TextField tfMovieSearchRentalDurationMin = new TextField();
        tfMovieSearchRentalDurationMin.setPromptText("Kortaste hyrestiden");
        TextField tfMovieSearchRentalDurationMax = new TextField();
        tfMovieSearchRentalDurationMax.setPromptText("Längsta hyrestiden");
        TextField tfMovieSearchLastUpdate = new TextField();
        tfMovieSearchLastUpdate.setPromptText("yyyy-mm-dd");

        TextField tfTest = new TextField();


        //Movie add
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

        //Customer
        TextField tfCustomerSearchName = new TextField();
        tfCustomerSearchName.setPromptText("Kundnamn");
        TextField tfCustomerSearchId = new TextField();
        tfCustomerSearchId.setPromptText("KundID");
        TextField tfCustomerSearchEmail = new TextField();
        tfCustomerSearchEmail.setPromptText("Kundmail");
        TextField tfCustomerSearchCity = new TextField();
        tfCustomerSearchCity.setPromptText("Stad");
        TextField tfCustomerSearchAddress = new TextField();
        TextField tfCustomerSearchPhone = new TextField();
        TextField tfCustomerSearchRegistered = new TextField();
        TextField tfCustomerSearchUpdate = new TextField();

        //Add ID to text fields
        //Movie
        tfMovieSearchTitle.setId("title");
        tfMovieSearchId.setId("film.film_id");
        tfMovieSearchDescription.setId("film.description");
        tfMovieSearchActors.setId("a.first_name"); //Hur ska man få in både för- och efternamn på en och samma?
        tfMovieSearchLengthMin.setId("film.length,min_value");
        tfMovieSearchLengthMax.setId("film.length,max_value");
        tfMovieSearchReplacementCostMin.setId("film.replacement_cost,min_value");
        tfMovieSearchReplacementCostMax.setId("film.replacement_cost,max_value");
        tfMovieSearchRentalDurationMin.setId("film.rental_duration,min_value");
        tfMovieSearchRentalDurationMax.setId("film.rental_duration,max_value");
        tfMovieSearchRentalCostMax.setId("film.rental_rate,max_value");
        tfMovieSearchRentalCostMin.setId("film.rental_rate,min_value");
        tfMovieSearchLastUpdate.setId("film.last_update"); //
        tfMovieSearchReleaseYear.setId("film.release_year"); //

        cbMovieSearchRating.setId("film.rating");
        cbMovieSearchOriginalLanguage.setId("l.name");
        cbMovieSearchLanguages.setId("l.name");
        cbMovieSearchCategory.setId("c.name");

        chbMovieSearchSFTrailers.setId("film.special_features,trailers");
        chbMovieSearchInStore.setId("InStore"); //Hur ska denna utformas

        tfTest.setId("test");

        //Customer
        tfCustomerSearchName.setId("customer.first_name");
        tfCustomerSearchId.setId("customer.customer_id");
        tfCustomerSearchEmail.setId("customer.email");
        tfCustomerSearchCity.setId("ci.city");
        tfCustomerSearchAddress.setId("a.address");
        tfCustomerSearchPhone.setId("a.phone");
        tfCustomerSearchRegistered.setId("customer.create_date");
        tfCustomerSearchUpdate.setId("customer.last_update");

        cbCustomerSearchStoreId.setId("customer.store_id");

        chbCustomerSearchActive.setId("customer.active,1");

        HBox hboxTest = new HBox();
        hboxTest.getChildren().addAll(tfTest);

        //Join text for mySQL for search
        String sMovieJoin =
                " JOIN language l ON film.language_id = l.language_id" +
                " JOIN film_actor fa ON film.film_id = fa.film_id" +
                " JOIN actor a ON fa.actor_id = a.actor_id" +
                " JOIN film_category fc ON film.film_id = fc.film_id" +
                " JOIN category c ON fc.category_id = c.category_id" +
                " JOIN inventory i ON film.film_id = i.film_id" +
                " JOIN rental r ON i.inventory_id = r.inventory_id";

        String sCustomerJoin =
                " JOIN address a ON customer.address_id = a.address_id"+
                " JOIN city ci ON ci.city_id = a.city_id"
                ;

        //Popup
        //Movie - Add
        miFilmAdd.setOnAction(event -> {
            fxBuilder.createPopUp(vBoxMovieAdd);
        });

        miCustomerAdd.setOnAction(event -> {
            fxBuilder.createPopUp(hboxTest);
        });

        //Add button function
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


        bSearchMovie.setOnAction(event -> {
            fetch.searchFromDatabase(vBoxLeft,olSearchResults, ENTITY_MANAGER_FACTORY, "title", "film", sMovieJoin);
        });

        bSearchCustomer.setOnAction(event -> {
            fetch.searchFromDatabase(vBoxRight,olSearchResults,ENTITY_MANAGER_FACTORY,"first_name","customer",sCustomerJoin);
        });

        bMovieSearchClear.setOnAction(event -> {
            fxBuilder.clearFields(vBoxLeft);
        });

        bCustomerSearchClear.setOnAction(event -> {
            fxBuilder.clearFields(vBoxRight);
        });

        //Add to boxes
        vBoxRight.getChildren().addAll(vBoxCustomerSearch, hBoxAdvancedSearchCustomer,bCustomerSearchClear);

        vBoxLeft.getChildren().addAll(vBoxMovieSearch, hBoxAdvancedSearchMovies,bMovieSearchClear);

        vBoxCenter.getChildren().addAll(lvSearchResults);

        vBoxMovieAdd.getChildren().addAll(lMovieHeader, lMovieAddTitle,tfAddTitle,lMovieAddRentalCost,tfMovieAddRentalCost,
                cbMovieAddCategory,lMovieAddDescription,tfMovieAddDescription,
                lMovieAddLength,tfMovieAddLength, lMovieAddRating,tfMovieAddRating, lMovieAddOriginalLanguage,
                tfMovieAddOriginalLanguage, cbMovieAddLanguages,lMovieAddActors,tfMovieAddActors,lMovieAddSpecialFeatures,tfMovieAddSpecialFeatures,
                lMovieAddRentalDuration,tfMovieAddRentalDuration, lMovieAddReplacementCost,tfMovieAddReplacementCost,
                lMovieAddInStore,tfMovieAddInStore, lMovieAddLastUpdate,tfMovieAddLastUpdate, bCreateMovie);


        //Movie Search
        vBoxMovieSearch.getChildren().addAll(lMovieHeader, lMovieSearchTitle, tfMovieSearchTitle,lMovieSearchRentalCost,
                tfMovieSearchRentalCostMin,tfMovieSearchRentalCostMax,cbMovieSearchCategory, chbMovieSearchInStore,
                  bAdvancedSearchMovies,bSearchMovie);

        hBoxAdvancedSearchMovies.getChildren().addAll(vBoxMovieSearchLeft, vBoxMovieSearchRight);

        vBoxMovieSearchLeft.getChildren().addAll(lMovieSearchId,tfMovieSearchId,lMovieSearchDescription,tfMovieSearchDescription,
                lMovieSearchLength,tfMovieSearchLengthMin,tfMovieSearchLengthMax,cbMovieSearchRating,cbMovieSearchLanguages,cbMovieSearchOriginalLanguage,
                lMovieSearchReleaseDate,tfMovieSearchReleaseYear);

        vBoxMovieSearchRight.getChildren().addAll(lMovieSearchActors,tfMovieSearchActors,lMovieSearchSpecialFeatures,chbMovieSearchSFTrailers,
                lMovieSearchRentalDuration,tfMovieSearchRentalDurationMin,tfMovieSearchRentalDurationMax, lMovieSearchReplacementCost,
                tfMovieSearchReplacementCostMin,tfMovieSearchReplacementCostMax,lMovieSearchLastUpdate,tfMovieSearchLastUpdate);

        //Customer Search
        vBoxCustomerSearch.getChildren().addAll(lCustomerHeader, lCustomerSearchName, tfCustomerSearchName,lCustomerSearchId,
                tfCustomerSearchId, lCustomerSearchEmail, tfCustomerSearchEmail,bAdvancedSearchCustomer, bSearchCustomer);

        hBoxAdvancedSearchCustomer.getChildren().addAll(vBoxCustomerSearchLeft,vBoxCustomerSearchRight);

        vBoxCustomerSearchLeft.getChildren().addAll(lCustomerSearchAddress,tfCustomerSearchAddress, lCustomerSearchCity,
                tfCustomerSearchCity, lCustomerSearchPhone,tfCustomerSearchPhone, chbCustomerSearchActive);

        vBoxCustomerSearchRight.getChildren().addAll(lCustomerSearchRegistered,tfCustomerSearchRegistered, lCustomerSearchUpdate,
                tfCustomerSearchUpdate,lCustomerSearchStoreId,cbCustomerSearchStoreId);


        //Add to borderPane
        borderPane.setTop(menuBar);
        borderPane.setLeft(vBoxLeft);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(vBoxCenter);

        Scene scene = new Scene(borderPane,1200,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
