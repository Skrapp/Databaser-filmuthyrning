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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.scene.text.FontWeight;

public class Main extends Application {
        private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

        final ObservableList olCategory = FXCollections.observableArrayList();
        final ObservableList olLanguages = FXCollections.observableArrayList();
        final ObservableList olStaff = FXCollections.observableArrayList();
        final ObservableList olSearchResultsMovie = FXCollections.observableArrayList();
        final ObservableList olSearchResultsCustomer = FXCollections.observableArrayList();
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

                Stage loginStage = new Stage(); //To login

                primaryStage.setTitle("Uthyrning");
                BorderPane borderPane = new BorderPane();
                Fetch fetch = new Fetch();
                FXBuilder fxBuilder = new FXBuilder();
                AddToDatabase addToDatabase = new AddToDatabase();

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

                fetch.addToComboList(olCategory, ENTITY_MANAGER_FACTORY, "name", "category");
                fetch.addToComboList(olLanguages, ENTITY_MANAGER_FACTORY, "name", "language");

                // Lists
                ListView lvSearchResultsMovie = new ListView(olSearchResultsMovie);
                ListView lvSearchResultsCustomer = new ListView(olSearchResultsCustomer);

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
                VBox vBoxCenterButtons = new VBox();
                HBox hBoxButtonRent = new HBox();
                hBoxButtonRent.setSpacing(5);
                hBoxButtonRent.setAlignment(Pos.CENTER);
                HBox hBoxButtonInfo = new HBox();
                hBoxButtonInfo.setSpacing(5);
                hBoxButtonInfo.setAlignment(Pos.CENTER);

                //Advanced search
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

                VBox vBoxStaffAdd = new VBox();
                vBoxStaffAdd.setPadding(new Insets(10));
                VBox vBoxDeleteStaff = new VBox();

                VBox vBoxAddCustomer = new VBox();
                VBox vBoxDeleteCustomer = new VBox();
                VBox vBoxJeppeSjuk = new VBox();

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

                //Renting popup
                VBox vBoxRentMovie = new VBox();
                vBoxRentMovie.setPadding(new Insets(10));
                HBox hBoxRentMovieButtons = new HBox();
                hBoxRentMovieButtons.setAlignment(Pos.CENTER);
                hBoxRentMovieButtons.setSpacing(20);

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
                Button bInfoMovie = new Button("Info om filmen");
                Button bInfoCustomer = new Button("Info om kunden");

                Button bCustomerSearchClear= new Button("Töm sökning");
                Button bMovieSearchClear= new Button("Töm sökning");
                Button bCustomerAddClear = new Button("Rensa");
                Button bStaffAddClear = new Button("Rensa");

                Button bStaffAdd = new Button("Lägg till");

                Button bRentMovieAccept = new Button("Ja");
                Button bRentMovieDecline = new Button("Nej");

                //Logout buttons
                Button bConfirmLogout = new Button("Logga ut");
                Button bCancelLogout = new Button("Avbryt");
                Button bConfirmExit = new Button("Avsluta");
                Button bCancelExit = new Button("Avbryt");

                //Delete buttons
                Button bDeleteCustomer = new Button("Radera");
                Button bDeleteStaff = new Button("Radera");

                //Update buttons
                Button bCustomerSearch = new Button("Sök");


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
                MenuItem miCustomerDelete = new MenuItem("Ta bort");
                MenuItem miCustomerRentedBy = new MenuItem("Vem hyr?");
                mbCustomer.getItems().addAll(miCustomerAdd, miCustomerEdit,miCustomerDelete, miCustomerRentedBy);
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
                //Center
                Label lMovieResult = new Label("Filmresultat");
                Label lCustomerResult = new Label("Kundresultat");
                //Movie
                Label lMovieHeader = new Label("Filmsektion");
                lMovieHeader.setFont(new Font(40));
                Label lMovieSearchTitle = new Label("Sök titel");
                Label lMovieSearchId = new Label("FilmID");
                Label lMovieSearchDescription = new Label("Beskrivning");
                Label lMovieSearchActors = new Label("Skådespelare");
                Label lMovieSearchLength = new Label("Längd");
                Label lMovieSearchRentalCost = new Label("Hyreskostnad");
                Label lMovieSearchReplacementCost = new Label("Ersättningskostnad");
                Label lMovieSearchSpecialFeatures = new Label("Extramaterial");
                Label lMovieSearchRentalDuration = new Label("Hyrestid");
                Label lMovieSearchLastUpdate = new Label("Senast uppdaterad");
                Label lMovieSearchReleaseDate = new Label("Utgivningsdatum");

                Label lMovieAddTitle = new Label("Titel");
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

                //Customer add
                Label lAddCustomerHeader = new Label("Lägg till kund");
                lAddCustomerHeader.setFont(new Font(40));
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
                Label lAddCustomerAdress2 = new Label("Adress");

                Label lDeleteCustomer = new Label("Ange kundID");
                Label lUpdateCustomer = new Label("Ange kundID");

                //Staff
                Label lStaffAddHeader = new Label("Lägg till arbetare");
                lStaffAddHeader.setFont(new Font(40));
                Label lStaffAddFirstName = new Label("Förnamn");
                Label lStaffAddLastName = new Label("Efternamn");
                Label lStaffAddCountry = new Label("Land");
                Label lStaffAddCity = new Label("Stad");
                Label lStaffAddAdress = new Label("Adress");
                Label lStaffAddEmail = new Label("E-mail");
                Label lStaffAddUserName = new Label("Användarnamn");
                Label lStaffAddPassword = new Label("Lösenord");
                Label lStaffAddDisctrict = new Label("Distrikt");
                Label lStaffAddAdress2 = new Label("Adress2");
                Label lStaffAddStoreID = new Label("ButikID");
                Label lStaffAddActive = new Label("Aktiv");
                Label lStaffAddPhone = new Label("Telefonnummer");
                Label lStaffAddPostalCode = new Label("Postkod");
                Label lDeleteStaff = new Label("Ange personalID");

                //File
                Label lConfirmLogout = new Label("Är du säker?");
                Label lConfirmExit = new Label("Är du säker?");

                //Text
                Text tRentMovie = new Text("");

                //-----------------------------
                //Checkboxes
                //SpecialFeatures and InStore
                CheckBox chbMovieSearchSFTrailers = new CheckBox("Trailer");
                CheckBox chbMovieSearchSFCommentaries = new CheckBox("Kommentarer");
                CheckBox chbMovieSearchSFBTS = new CheckBox("Bakom kulisserna");
                CheckBox chbMovieSearchSFDeletedScenes = new CheckBox("Borttaget material");
                CheckBox chbMovieSearchInStore = new CheckBox("Bara tillgängliga");
                CheckBox chbCustomerSearchActive = new CheckBox("Bara aktiva");
                //-----------------------------

                //Text fields
                // add customer
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
                TextField tfAddCustomerAddress2 = new TextField();
                tfAddCustomerAddress2.setPromptText("Adress2");
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

                TextField tfDeleteCustomer = new TextField();
                tfDeleteCustomer.setPromptText("Ange IDnummer");


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
                TextField tfMovieAddTitle = new TextField();
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
                tfCustomerSearchAddress.setPromptText("Adress");
                TextField tfCustomerSearchPhone = new TextField();
                tfCustomerSearchPhone.setPromptText("Telefonnummer");
                TextField tfCustomerSearchRegistered = new TextField();
                tfCustomerSearchRegistered.setPromptText("Registrerad");
                TextField tfCustomerSearchUpdate = new TextField();
                tfCustomerSearchUpdate.setPromptText("Uppdaterad");
                TextField tfCustomerUpdate = new TextField();
                tfCustomerUpdate.setPromptText("KundID");

                // Staff Add Textfields
                TextField tfStaffAddFirstName = new TextField();
                tfStaffAddFirstName.setPromptText("Förnamn");
                TextField tfStaffAddLastName = new TextField();
                tfStaffAddLastName.setPromptText("Efternamn");
                TextField tfStaffAddCountry = new TextField();
                tfStaffAddCountry.setPromptText("Land");
                TextField tfStaffAddCity = new TextField();
                tfStaffAddCity.setPromptText("Stad");
                TextField tfStaffAddAdress = new TextField();
                tfStaffAddAdress.setPromptText("Adress");
                TextField tfStaffAddAdress2 = new TextField();
                tfAddCustomerAddress2.setPromptText("Adress2");
                TextField tfStaffAddEmail = new TextField();
                tfStaffAddEmail.setPromptText("Email");
                TextField tfStaffAddUserName = new TextField();
                tfStaffAddUserName.setPromptText("Username");
                TextField tfStaffAddPassword = new TextField();
                tfStaffAddPassword.setPromptText("Password");
                TextField tfStaffAddDistrict = new TextField();
                tfStaffAddDistrict.setPromptText("Distrikt");
                TextField tfStaffAddStoreID = new TextField();
                tfStaffAddStoreID.setPromptText("StoreID");
                TextField tfStaffAddActive = new TextField();
                tfStaffAddActive.setPromptText("Aktiv");
                TextField tfStaffAddPhone = new TextField();
                tfStaffAddPhone.setPromptText("Nummer");
                TextField tfStaffAddPostalCode = new TextField();
                tfStaffAddPostalCode.setPromptText("Postkod");
                TextField tfDeleteStaff = new TextField();
                tfDeleteStaff.setPromptText("Ange PersonalID");

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
                chbMovieSearchSFCommentaries.setId("film.special_features,commentaries");
                chbMovieSearchSFBTS.setId("film.special_features,behind the scenes");
                chbMovieSearchSFDeletedScenes.setId("film.special_features,deleted scenes");
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
                                " JOIN city ci ON ci.city_id = a.city_id";

                //Popup
                //Movie - Add
                miFilmAdd.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxMovieAdd);
                });

                //Customer Add
                miCustomerAdd.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxAddCustomer);
                });
                miCustomerDelete.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxDeleteCustomer);
                });


                //Staff Add
                miStaffAdd.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxStaffAdd);
                });

                miStaffDelete.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxDeleteStaff);
                });

                //Menu - File
                miFileLogOut.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxLogout);
                });
                miFileExit.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxExit);
                });

                miCustomerEdit.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxJeppeSjuk);
                });




                //Button function
                bAdvancedSearchCustomer.setOnAction(event -> {
                        hBoxAdvancedSearchCustomer.setVisible(!hBoxAdvancedSearchCustomer.isVisible());
                });

                bAdvancedSearchMovies.setOnAction(event -> {
                        hBoxAdvancedSearchMovies.setVisible(!hBoxAdvancedSearchMovies.isVisible());
                });
                bCreateCustomer.setOnAction(event -> {
                        //Change test -> addToDatabase
                        int countryID = addToDatabase.addCountryID(ENTITY_MANAGER_FACTORY,tfAddCustomerCountrys);
                        int cityID = addToDatabase.addCityID(ENTITY_MANAGER_FACTORY,tfAddCustomerCity, countryID);
                        int adressID = addToDatabase.addAdressId(ENTITY_MANAGER_FACTORY, tfAddCustomerAddress, tfAddCustomerAddress2,
                                tfAddCustomerPostalCode, tfAddCustomerDistrict, tfAddCustomerPhone,cityID);

                        addToDatabase.addCustomer(ENTITY_MANAGER_FACTORY, tfAddCustomerFirstName,
                                tfAddCustomerLastName, tfAddCustomerEmail, tfAddCustomerStoreId,
                                tfAddCustomerActive, adressID);
                        fxBuilder.clearFields(vBoxAddCustomer);

                });

                bStaffAdd.setOnAction(event -> {
                        int countryID = addToDatabase.addCountryID(ENTITY_MANAGER_FACTORY,tfStaffAddCountry);
                        int cityID = addToDatabase.addCityID(ENTITY_MANAGER_FACTORY,tfStaffAddCity, countryID);
                        int addressID = addToDatabase.addAdressId(ENTITY_MANAGER_FACTORY,tfStaffAddAdress,tfStaffAddAdress2,tfStaffAddPostalCode
                                , tfStaffAddDistrict,tfStaffAddPhone, cityID);

                        addToDatabase.AddStaff(ENTITY_MANAGER_FACTORY,tfStaffAddFirstName,tfStaffAddLastName,
                                tfStaffAddEmail,tfStaffAddUserName,tfStaffAddPassword,
                                tfStaffAddStoreID, tfStaffAddActive, addressID);
                        fxBuilder.clearFields(vBoxStaffAdd);

                });

                bDeleteCustomer.setOnAction(event -> {
                        addToDatabase.deleteCustomer(ENTITY_MANAGER_FACTORY,tfDeleteCustomer, "customer", "customer_id");
                        fxBuilder.clearFields(vBoxDeleteCustomer);
                });

                bDeleteStaff.setOnAction(event -> {
                        addToDatabase.deleteCustomer(ENTITY_MANAGER_FACTORY,tfDeleteStaff, "staff", "staff_id");
                        fxBuilder.clearFields(vBoxDeleteStaff);
                });

                bSearchMovie.setOnAction(event -> {
                        fetch.searchFromDatabase(vBoxLeft,olSearchResultsMovie,ENTITY_MANAGER_FACTORY,"title", "film", sMovieJoin);
                });

                bSearchCustomer.setOnAction(event -> {
                        fetch.searchFromDatabase(vBoxRight,olSearchResultsCustomer,ENTITY_MANAGER_FACTORY,"first_name","customer",sCustomerJoin);
                });

                bMovieSearchClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxLeft);
                });

                bCustomerSearchClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxRight);
                });
                bCustomerAddClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxAddCustomer);
                });
                bStaffAddClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxStaffAdd);
                });

                //Rent movie
                bRentMovie.setOnAction(event -> {
                        //Funktion?
                        String sMovie = (String)lvSearchResultsMovie.getSelectionModel().getSelectedItem();
                        String sCustomer = (String)lvSearchResultsCustomer.getSelectionModel().getSelectedItem();
                        if(sMovie != null && sCustomer != null){
                                if(fetch.isInStore(ENTITY_MANAGER_FACTORY,sMovie,sMovieJoin)) {
                                        tRentMovie.setText("Vill " + sCustomer + " hyra " + sMovie);
                                        fxBuilder.createPopUp(vBoxRentMovie);
                                }
                                else
                                        //Create popup for informing it is not in stock
                                        fxBuilder.createPopUp(hboxTest);
                        }
                        else
                                //Create popup for error
                                System.out.println("Välj kund och film");
                });

                //Log out
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
                vBoxRight.getChildren().addAll(vBoxCustomerSearch, hBoxAdvancedSearchCustomer,bCustomerSearchClear);

                vBoxLeft.getChildren().addAll(vBoxMovieSearch, hBoxAdvancedSearchMovies,bMovieSearchClear);

                vBoxCenter.getChildren().addAll(lMovieResult,lvSearchResultsMovie,vBoxCenterButtons,lCustomerResult,lvSearchResultsCustomer);

                vBoxCenterButtons.getChildren().addAll(hBoxButtonRent,hBoxButtonInfo);
                hBoxButtonInfo.getChildren().addAll(bInfoMovie,bInfoCustomer);
                hBoxButtonRent.getChildren().addAll(bRentMovie,bReturnMovie);

                //Renting popup
                vBoxRentMovie.getChildren().addAll(tRentMovie,hBoxRentMovieButtons);
                hBoxRentMovieButtons.getChildren().addAll(bRentMovieAccept,bRentMovieDecline);

                vBoxMovieAdd.getChildren().addAll(lMovieHeader, lMovieAddTitle, tfMovieAddTitle, lMovieAddRentalCost,
                        tfMovieAddRentalCost, cbMovieAddCategory,lMovieAddDescription, tfMovieAddDescription,
                        lMovieAddLength, tfMovieAddLength, lMovieAddRating,tfMovieAddRating, lMovieAddOriginalLanguage,
                        tfMovieAddOriginalLanguage, cbMovieAddLanguages,lMovieAddActors, tfMovieAddActors,
                        lMovieAddSpecialFeatures, tfMovieAddSpecialFeatures, lMovieAddRentalDuration, tfMovieAddRentalDuration,
                        lMovieAddReplacementCost, tfMovieAddReplacementCost, lMovieAddInStore, tfMovieAddInStore,
                        lMovieAddLastUpdate, tfMovieAddLastUpdate, bCreateMovie);

                //Logout
                hBoxLogout.getChildren().addAll(bConfirmLogout,bCancelLogout);
                vBoxLogout.getChildren().addAll(lConfirmLogout,hBoxLogout);

                //Exit
                hBoxExit.getChildren().addAll(bConfirmExit,bCancelExit);
                vBoxExit.getChildren().addAll(lConfirmExit,hBoxExit);


                //Movie Search
                vBoxMovieSearch.getChildren().addAll(lMovieHeader, lMovieSearchTitle, tfMovieSearchTitle,lMovieSearchRentalCost,
                        tfMovieSearchRentalCostMin,tfMovieSearchRentalCostMax,cbMovieSearchCategory, chbMovieSearchInStore,
                        bAdvancedSearchMovies,bSearchMovie);

                hBoxAdvancedSearchMovies.getChildren().addAll(vBoxMovieSearchLeft, vBoxMovieSearchRight);

                vBoxMovieSearchLeft.getChildren().addAll(lMovieSearchId,tfMovieSearchId,lMovieSearchDescription,
                        tfMovieSearchDescription, lMovieSearchLength,tfMovieSearchLengthMin,tfMovieSearchLengthMax,
                        cbMovieSearchRating, cbMovieSearchLanguages,cbMovieSearchOriginalLanguage,
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

                //Add customer
                vBoxAddCustomer.getChildren().addAll(lAddCustomerHeader,lAddCustomerFirstName, tfAddCustomerFirstName, lAddCustomerLastName,
                        tfAddCustomerLastName, lAddCustomerEmail, tfAddCustomerEmail, lAddCustomerStoreId,
                        tfAddCustomerStoreId, lAddCustomerActive,
                        tfAddCustomerActive, lAddCustomerAddress, tfAddCustomerAddress,lAddCustomerAdress2, tfAddCustomerAddress2, lAddCustomerPostalCode,
                        tfAddCustomerPostalCode, lAddCustomerDistrict, tfAddCustomerDistrict, lAddCustomerPhone,
                        tfAddCustomerPhone, lAddCustomerCity, tfAddCustomerCity, lAddCustomerCountry,
                        tfAddCustomerCountrys, bCreateCustomer, bCustomerAddClear);

                //Delete customer
                vBoxDeleteCustomer.getChildren().addAll(lDeleteCustomer,tfDeleteCustomer, bDeleteCustomer);

                //Update customer
                vBoxJeppeSjuk.getChildren().addAll(lUpdateCustomer,tfCustomerUpdate,bCustomerSearch);

                //Add staff
                vBoxStaffAdd.getChildren().addAll(lStaffAddHeader, lStaffAddFirstName, tfStaffAddFirstName,
                        lStaffAddLastName, tfStaffAddLastName, lStaffAddEmail, tfStaffAddEmail,lStaffAddCity,
                        tfStaffAddCity, lStaffAddCountry, tfStaffAddCountry,lStaffAddStoreID,tfStaffAddStoreID, lStaffAddUserName, tfStaffAddUserName,
                        lStaffAddPassword, tfStaffAddPassword,lStaffAddActive,tfStaffAddActive,lStaffAddAdress, tfStaffAddAdress,lStaffAddAdress2,
                        tfStaffAddAdress2,lStaffAddDisctrict,
                        tfStaffAddDistrict,lStaffAddPhone,tfStaffAddPhone,lStaffAddPostalCode,tfStaffAddPostalCode, bStaffAdd, bStaffAddClear);

                //Delete staff
                vBoxDeleteStaff.getChildren().addAll(lDeleteStaff,tfDeleteStaff,bDeleteStaff);

                //Add to borderPane
                borderPane.setTop(menuBar);
                borderPane.setLeft(vBoxLeft);
                borderPane.setRight(vBoxRight);
                borderPane.setCenter(vBoxCenter);

                //Test Login--------------------------------------------------------------------
                //Flyttas om
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
                bCreateMovie.setOnAction(event -> {
                        addToDatabase.addMovie(vBoxMovieAdd, ENTITY_MANAGER_FACTORY);
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
        }
}
