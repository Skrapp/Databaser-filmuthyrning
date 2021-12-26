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

        final ObservableList olCategory = FXCollections.observableArrayList(
                "Här",
                "och här" //Om vi ska lägga in dom manuellt
        );
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

                Stage loginStage = new Stage(); //To login

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

                //Add default options to observable lists
                olCategory.add(0,null);
                olLanguages.add(0,null);
                olRating.add(0,null);
                olStores.add(0,null);

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

                Button bCustomerSearchClear= new Button("Töm sökning");
                Button bMovieSearchClear= new Button("Töm sökning");

                Button bStaffAdd = new Button("Lägg till");

                //Rent movie
                Button bRentMovie = new Button("Hyra");
                Button bReturnMovie = new Button("Lämna tillbaka");
                Button bInfoMovie = new Button("Info om filmen");
                Button bInfoCustomer = new Button("Info om kunden");
                Button bRentMovieAccept = new Button("Ja");
                Button bRentMovieDecline = new Button("Nej");

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

                //Movie add
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
                Label lCustomerSearchUpdate = new Label("Uppdaterad");

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

                //Staff
                Label lStaffAddHeader = new Label("Lägg till arbetare");
                lStaffAddHeader.setFont(new Font(40));
                Label lStaffAddFirstName = new Label("Förnamn");
                Label lStaffAddLastName = new Label("Efternamn");
                Label lStaffAddCountry = new Label("Land");
                Label lStaffAddCity = new Label("Stad");
                Label lStaffAddAdress = new Label("Adress");
                Label lStaffAddEmail = new Label("E-mail");
                Label lStaffUserName = new Label("Användarnamn");
                Label lStaffPassword = new Label("Lösenord");
                Label lStaffDisctrict = new Label("Distrikt");

                //File
                Label lConfirmLogout = new Label("Är du säker?");
                Label lConfirmExit = new Label("Är du säker?");

                //Text
                Text tRentMovie = new Text();

                //Checkboxes
                //SpecialFeatures and InStore
                CheckBox chbMovieSearchSFTrailers = new CheckBox("Trailer");
                CheckBox chbMovieSearchSFBTS = new CheckBox("Bakom kulisserna");
                CheckBox chbMovieSearchSFCommentaries = new CheckBox("Kommentarer");
                CheckBox chbMovieSearchSFDeletedScenes = new CheckBox("Borttaget material");
                CheckBox chbMovieSearchInStore = new CheckBox("Bara tillgängliga");
                CheckBox chbCustomerSearchActive = new CheckBox("Bara aktiva");

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

                //Textfields
                TextField tfStaffAddHeader = new TextField();
                tfStaffAddHeader.setPromptText("Sök");
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
                TextField tfStaffAddEmail = new TextField();
                tfStaffAddEmail.setPromptText("Email");
                TextField tfStaffUserName = new TextField();
                tfStaffUserName.setPromptText("Username");
                TextField tfStaffPassword = new TextField();
                tfStaffPassword.setPromptText("Password");
                TextField tfStaffDistrict = new TextField();
                tfStaffDistrict.setPromptText("Distrikt");

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
                chbMovieSearchSFBTS.setId("film.special_features,Behind The Scenes");
                chbMovieSearchSFDeletedScenes.setId("film.special_features,Deleted Scenes");
                chbMovieSearchSFCommentaries.setId("film.special_features,Commentaries");
                chbMovieSearchInStore.setId("InStore");

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

                HBox hBoxTest = new HBox();
                hBoxTest.getChildren().addAll(tfTest);

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

                //Customer
                miCustomerAdd.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxAddCustomer);
                });

                //Staff
                miStaffAdd.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxStaffAdd);
                });

                //Menu - File
                miFileLogOut.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxLogout);
                });
                miFileExit.setOnAction(event -> {
                        fxBuilder.createPopUp(vBoxExit);
                });

                //Button function
                bAdvancedSearchCustomer.setOnAction(event -> {
                        hBoxAdvancedSearchCustomer.setVisible(!hBoxAdvancedSearchCustomer.isVisible());
                });

                bAdvancedSearchMovies.setOnAction(event -> {
                        hBoxAdvancedSearchMovies.setVisible(!hBoxAdvancedSearchMovies.isVisible());
                });

                bSearchMovie.setOnAction(event -> {
                        fetch.searchFromDatabase(vBoxLeft,olSearchResultsMovie,ENTITY_MANAGER_FACTORY,"title", "film", sMovieJoin);
                });

                bSearchCustomer.setOnAction(event -> {
                        fetch.searchFromDatabase(vBoxRight,olSearchResultsCustomer,ENTITY_MANAGER_FACTORY,"first_name","customer",sCustomerJoin);
                });

                //Empty text fields
                bMovieSearchClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxLeft);
                });

                bCustomerSearchClear.setOnAction(event -> {
                        fxBuilder.clearFields(vBoxRight);
                });

                //Rent movie
                bRentMovie.setOnAction(event -> {
                        String sMovie = (String)lvSearchResultsMovie.getSelectionModel().getSelectedItem();
                        String sCustomer = (String)lvSearchResultsCustomer.getSelectionModel().getSelectedItem();
                        if(sMovie != null && sCustomer != null){
                                if(fetch.isInStore(ENTITY_MANAGER_FACTORY,sMovie,sMovieJoin)) {
                                        tRentMovie.setText("Vill " + sCustomer + " hyra " + sMovie);
                                        fxBuilder.createPopUp(vBoxRentMovie);
                                }
                                else
                                        //Create popup for informing it is not in stock
                                        fxBuilder.createPopUp(hBoxTest);
                        }
                        else
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

                //Movie Add
                vBoxMovieAdd.getChildren().addAll(lMovieHeader, lMovieAddTitle,lMovieAddRentalCost,
                        cbMovieAddCategory,lMovieAddDescription, lMovieAddLength, lMovieAddRating,lMovieAddOriginalLanguage,
                        cbMovieAddLanguages,lMovieAddActors,lMovieAddSpecialFeatures, lMovieAddRentalDuration,
                        lMovieAddReplacementCost, lMovieAddInStore, lMovieAddLastUpdate, bCreateMovie);

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
                        chbMovieSearchSFBTS,chbMovieSearchSFCommentaries,chbMovieSearchSFDeletedScenes,
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
                vBoxAddCustomer.getChildren().addAll(lAddCustomerFirstName, tfAddCustomerFirstName, lAddCustomerLastName,
                        tfAddCustomerLastName, lAddCustomerEmail, tfAddCustomerEmail, lAddCustomerStoreId,
                        tfAddCustomerStoreId, lAddCustomerRegistered, tfAddCustomerRegistered, lAddCustomerActive,
                        tfAddCustomerActive, lAddCustomerAddress, tfAddCustomerAddress, lAddCustomerPostalCode,
                        tfAddCustomerPostalCode, lAddCustomerDistrict, tfAddCustomerDistrict, lAddCustomerPhone,
                        tfAddCustomerPhone, lAddCustomerCity, tfAddCustomerCity, lAddCustomerCountry,
                        tfAddCustomerCountrys, bCreateCustomer);

                //Add staff
                vBoxStaffAdd.getChildren().addAll(lStaffAddHeader, lStaffAddFirstName, tfStaffAddFirstName,
                        lStaffAddLastName, tfStaffAddLastName, lStaffAddEmail, tfStaffAddEmail,lStaffAddCity,
                        tfStaffAddCity, lStaffAddCountry, tfStaffAddCountry, lStaffUserName, tfStaffUserName,
                        lStaffPassword, tfStaffPassword,lStaffAddAdress, tfStaffAddAdress,lStaffDisctrict,
                        tfStaffDistrict, bStaffAdd);

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
