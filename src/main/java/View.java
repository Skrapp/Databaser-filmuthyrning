import attributes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.*;

import javafx.scene.text.FontWeight;

import java.util.List;

public class View {
        private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

        //Classes
        FXBuilder fxBuilder = new FXBuilder();
        Controller controller = new Controller(fxBuilder);
        Fetch fetch = new Fetch(ENTITY_MANAGER_FACTORY,controller);

        //Operating Language
        MovieOperatingLanguage movieSwedish = new MovieOperatingLanguage("Titel", "Beskrivning", "Betyg", "Originalspråk","Språk", "Kategori", "Extramaterial", "Skådespelare", "Ersättningskostnad", "Hyreskostnad", "Filmlängd", "Film ID", "Hyreslängd", "Släpptes år", "Senast uppdaterad", "Lager", "Registrerad i butik");
        CustomerOperatingLanguage customerSwedish = new CustomerOperatingLanguage("Namn", "Kund ID", "Butik ID", "Aktiv", "Email", "Telefon", "Stad", "Adress", "Registrerades", "Uppdaterades");

        //Observable list
        final ObservableList olCategory = FXCollections.observableArrayList();
        final ObservableList olLanguages = FXCollections.observableArrayList();
        final ObservableList olStaff = FXCollections.observableArrayList();
        final ObservableList olRating = FXCollections.observableArrayList();
        final ObservableList olStores = FXCollections.observableArrayList();
        final ObservableList<CustomerSearchResults> olCustomerSearchResults = FXCollections.observableArrayList();
        final ObservableList<FilmSearchResults> olMovieSearchResults = FXCollections.observableArrayList();

        // Combobox
        //Movie search
        ComboBox cbMovieSearchCategory = new ComboBox(olCategory);
        ComboBox cbMovieSearchLanguages = new ComboBox(olLanguages);
        ComboBox cbMovieSearchRating = new ComboBox(olRating);
        ComboBox cbMovieSearchOriginalLanguage = new ComboBox(olLanguages);
        //Customer Search
        ComboBox cbCustomerSearchStoreId = new ComboBox(olStores);
        //Movie add
        ComboBox cbMovieAddCategory = new ComboBox(olCategory);
        ComboBox cbMovieAddLanguages = new ComboBox(olLanguages);


        //Table view
        TableView<CustomerSearchResults> tvSearchResultsCustomer = new TableView<>();
        TableView<FilmSearchResults> tvSearchResultsMovie = new TableView<FilmSearchResults>();

        //Text fields
        // add customer
        TextField tfAddCustomerFirstName = new TextField();
        TextField tfAddCustomerLastName = new TextField();
        TextField tfAddCustomerEmail = new TextField();
        TextField tfAddCustomerStoreId = new TextField();
        TextField tfAddCustomerRegistered = new TextField();
        TextField tfAddCustomerActive = new TextField();
        TextField tfAddCustomerAddress = new TextField();
        TextField tfAddCustomerAddress2 = new TextField();
        TextField tfAddCustomerPostalCode = new TextField();
        TextField tfAddCustomerDistrict = new TextField();
        TextField tfAddCustomerPhone = new TextField();
        TextField tfAddCustomerCity = new TextField();
        TextField tfAddCustomerCountrys = new TextField();

        //Search Customer
        TextField tfCustomerSearchFirstName = new TextField();
        TextField tfCustomerSearchId = new TextField();
        TextField tfCustomerSearchEmail = new TextField();
        TextField tfCustomerSearchCity = new TextField();
        TextField tfCustomerSearchAddress = new TextField();
        TextField tfCustomerSearchPhone = new TextField();
        TextField tfCustomerSearchRegistered = new TextField();
        TextField tfCustomerSearchUpdate = new TextField();

        //Search Movie
        TextField tfMovieSearchTitle = new TextField();
        TextField tfMovieSearchReleaseYear = new TextField();
        TextField tfMovieSearchId = new TextField();
        TextField tfMovieSearchDescription = new TextField();
        TextField tfMovieSearchActors = new TextField();
        TextField tfMovieSearchLengthMin = new TextField();
        TextField tfMovieSearchLengthMax = new TextField();
        TextField tfMovieSearchRentalCostMin = new TextField();
        TextField tfMovieSearchRentalCostMax = new TextField();
        TextField tfMovieSearchReplacementCostMin = new TextField();
        TextField tfMovieSearchReplacementCostMax = new TextField();
        TextField tfMovieSearchRentalDurationMin = new TextField();
        TextField tfMovieSearchRentalDurationMax = new TextField();
        TextField tfMovieSearchLastUpdate = new TextField();

        //Checkboxes
        //Movie Search
        CheckBox chbMovieSearchSFTrailers = new CheckBox("Trailer");
        CheckBox chbMovieSearchSFCommentaries = new CheckBox("Kommentarer");
        CheckBox chbMovieSearchSFBTS = new CheckBox("Bakom kulisserna");
        CheckBox chbMovieSearchSFDeletedScenes = new CheckBox("Borttaget material");
        CheckBox chbMovieSearchInStore = new CheckBox("Bara tillgängliga");
        //Customer Search
        CheckBox chbCustomerSearchActive = new CheckBox("Bara aktiva");

        /**
         * Search in table for written search criteria
         * @param list List containing data
         * @param ol The observable list to add data to
         */
        public void addResultToOL(List<Object> list, ObservableList ol){
                ol.clear();
                for(Object o : list){
                        ol.add(o);
                }
        }

        public void executeCustomerSearch(){
                CustomerSearch customerSearch = new CustomerSearch();

                customerSearch.setsFirstName(getDataTextField(tfCustomerSearchFirstName));
                customerSearch.setsId(getDataTextField(tfCustomerSearchId));
                customerSearch.setsEmail(getDataTextField(tfCustomerSearchEmail));
                customerSearch.setsCity(getDataTextField(tfCustomerSearchCity));
                customerSearch.setsAddress(getDataTextField(tfCustomerSearchAddress));
                customerSearch.setsPhone(getDataTextField(tfCustomerSearchPhone));
                customerSearch.setsRegistered(getDataTextField(tfCustomerSearchRegistered));
                customerSearch.setsUpdate(getDataTextField(tfCustomerSearchUpdate));

                customerSearch.setsStoreId(getDataComboBox(cbCustomerSearchStoreId));

                customerSearch.setIsActive(getDataCheckBox(chbCustomerSearchActive));

                List result = fetch.searchCustomers(customerSearch);
                addResultToOL(result,olCustomerSearchResults);
        }

        public void executeMovieSearch(){
                MovieSearch movieSearch = new MovieSearch();

                movieSearch.setHasSFBTS(getDataCheckBox(chbMovieSearchSFBTS));
                movieSearch.setHasSFTrailer(getDataCheckBox(chbMovieSearchSFTrailers));
                movieSearch.setHasSFDeletedScenes(getDataCheckBox(chbMovieSearchSFDeletedScenes));
                movieSearch.setHasSFCommentaries(getDataCheckBox(chbMovieSearchSFCommentaries));
                movieSearch.setInStore(getDataCheckBox(chbMovieSearchInStore));

                movieSearch.setsCategory(getDataComboBox(cbMovieSearchCategory));
                movieSearch.setsLanguage(getDataComboBox(cbMovieSearchLanguages));
                movieSearch.setsOriginalLanguage(getDataComboBox(cbMovieSearchOriginalLanguage));
                movieSearch.setsRating(getDataComboBox(cbMovieSearchRating));

                movieSearch.setsDescription(getDataTextField(tfMovieSearchDescription));
                movieSearch.setsTitle(getDataTextField(tfMovieSearchTitle));
                movieSearch.setsFilmId(getDataTextField(tfMovieSearchId));
                movieSearch.setsReleaseYear(getDataTextField(tfMovieSearchReleaseYear));
                movieSearch.setsRentalDurationMax(getDataTextField(tfMovieSearchRentalDurationMax));
                movieSearch.setsRentalDurationMin(getDataTextField(tfMovieSearchRentalDurationMax));
                movieSearch.setsReplacementCostMin(getDataTextField(tfMovieSearchReplacementCostMin));
                movieSearch.setsReplacementCostMax(getDataTextField(tfMovieSearchReplacementCostMax));
                movieSearch.setsLengthMin(getDataTextField(tfMovieSearchLengthMin));
                movieSearch.setsLengthMax(getDataTextField(tfMovieSearchLengthMax));
                movieSearch.setsRentalRateMin(getDataTextField(tfMovieSearchRentalCostMin));
                movieSearch.setsRentalRateMax(getDataTextField(tfMovieSearchRentalCostMax));
                movieSearch.setsLastUpdate(getDataTextField(tfMovieSearchLastUpdate));
                movieSearch.setsAFirstName(getDataTextField(tfMovieSearchActors));
                //movieSearch.setsALastName(getDataTextField(tfMovieSearchActors));

                List result = fetch.searchMovies(movieSearch);
                addResultToOL(result,olMovieSearchResults);
        }

        public void executeMovieInfo(){
                FilmSearchResults filmSearchResults = tvSearchResultsMovie.getSelectionModel().getSelectedItem();
                if (filmSearchResults != null){
                        int movieId = filmSearchResults.getId();
                        MovieInfo movieInfo = fetch.setMovieInfo(movieId);

                        fxBuilder.createInfoPopUp(movieInfo, movieSwedish, "Film - Info");

                }
                else
                        fxBuilder.createErrorPopup("Välj en film för att kunna visa information om den.");
        }

        public void executeCustomerInfo(){
                CustomerSearchResults customerSearchResults = tvSearchResultsCustomer.getSelectionModel().getSelectedItem();
                if (customerSearchResults != null){
                        int customerId = customerSearchResults.getId();
                        CustomerInfo customerInfo = fetch.setCustomerInfo(customerId);

                        fxBuilder.createInfoPopUp(customerInfo, customerSwedish, "Kund - Info");

                }
                else
                        fxBuilder.createErrorPopup("Välj en kund för att kunna visa information om den.");
        }

        public Boolean getDataCheckBox(CheckBox checkBox){
                return checkBox.isSelected();
        }

        public String getDataComboBox(ComboBox comboBox){
                if (comboBox.getSelectionModel().getSelectedItem() != null)
                        return comboBox.getSelectionModel().getSelectedItem().toString();

                return "";
        }

        public String getDataTextField(TextField textField){
                return textField.getText().trim();
        }

        public void buildInterface(Stage primaryStage) throws Exception {

                Stage loginStage = new Stage(); //To login

                primaryStage.setTitle("Uthyrning");
                BorderPane borderPane = new BorderPane();
                AddToDatabase addToDatabase = new AddToDatabase(ENTITY_MANAGER_FACTORY);

                //Combobox
                cbMovieSearchOriginalLanguage.setPromptText("Originalspråk");
                cbMovieSearchRating.setPromptText("Betyg");
                cbMovieSearchCategory.setPromptText("Kategori");
                cbMovieSearchLanguages.setPromptText("Språk");

                //Customer Search
                cbCustomerSearchStoreId.setPromptText("Butik ID");

                //Movie add
                cbMovieAddCategory.setPromptText("Kategori");
                cbMovieAddLanguages.setPromptText("Språk");

                //Add data to observable lists
                addResultToOL(fetch.getSimpleList("name","category"),olCategory);
                addResultToOL(fetch.getSimpleList("name","language"),olLanguages);
                addResultToOL(fetch.getSimpleList("rating","film"),olRating);
                addResultToOL(fetch.getSimpleList("store_id","store"),olStores);

                //Add default selection, user can manually deselect
                olCategory.add(0,null);
                olLanguages.add(0,null);
                olRating.add(0,null);
                olStores.add(0,null);

                //Table View
                //Customer
                TableColumn colCustomerId = new TableColumn("Id");
                colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colCustomerId.setPrefWidth(35);
                TableColumn colCustomerName = new TableColumn("Namn");
                colCustomerName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                colCustomerName.setPrefWidth(120);
                TableColumn colCustomerEmail = new TableColumn("Email");
                colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                colCustomerEmail.setPrefWidth(200);

                TableColumn<CustomerSearchResults, Hyperlink> colCustomerEdit = new TableColumn("Redigera");
                colCustomerEdit.setCellValueFactory(new PropertyValueFactory<>("linkEdit"));
                colCustomerEdit.setCellFactory(new EditCell());

                TableColumn<CustomerSearchResults, Hyperlink> colCustomerDelete = new TableColumn("Ta bort");
                colCustomerDelete.setCellValueFactory(new PropertyValueFactory<>("linkDelete"));
                colCustomerDelete.setCellFactory(new EditCell());

                tvSearchResultsCustomer.setItems(olCustomerSearchResults);
                tvSearchResultsCustomer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                tvSearchResultsCustomer.getColumns().addAll(colCustomerId, colCustomerName, colCustomerEmail, colCustomerEdit, colCustomerDelete);

                //Movie
                TableColumn colMovieId = new TableColumn("Id");
                colMovieId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colMovieId.setPrefWidth(50);
                TableColumn colMovieTitle = new TableColumn("Title");
                colMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                colMovieTitle.setPrefWidth(150);
                TableColumn colMovieDescription = new TableColumn("Description");
                colMovieDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                colMovieDescription.setPrefWidth(250);
                tvSearchResultsMovie.setItems(olMovieSearchResults);
                tvSearchResultsMovie.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                tvSearchResultsMovie.getColumns().addAll(colMovieId, colMovieTitle, colMovieDescription);

                //Boxes
                //Center
                VBox vBoxCenter = new VBox();
                vBoxCenter.setPadding(new Insets(10));
                VBox vBoxCenterButtons = new VBox();
                HBox hBoxButtonRent = new HBox();
                hBoxButtonRent.setSpacing(5);
                hBoxButtonRent.setAlignment(Pos.CENTER);
                HBox hBoxButtonInfo = new HBox();
                hBoxButtonInfo.setSpacing(5);
                hBoxButtonInfo.setAlignment(Pos.CENTER);

                //Left/Right
                VBox vBoxRight = new VBox();
                vBoxRight.setPadding(new Insets(10));
                VBox vBoxLeft = new VBox();

                //Search
                VBox vBoxMovieSearch = new VBox();
                vBoxMovieSearch.setPadding(new Insets(10));
                VBox vBoxCustomerSearch = new VBox();
                vBoxCustomerSearch.setPadding(new Insets(10));

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

                //Staff
                VBox vBoxStaffAdd = new VBox();
                vBoxStaffAdd.setPadding(new Insets(10));
                VBox vBoxDeleteStaff = new VBox();

                //Movie
                VBox vBoxMovieAdd = new VBox();

                //Customer
                VBox vBoxAddCustomer = new VBox();
                VBox vBoxDeleteCustomer = new VBox();

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


                //Meny
                MenuBar menuBar = new MenuBar();
                Menu mbFile = new Menu("Arkiv");
                MenuItem miFileLogOut = new MenuItem("Logga ut");
                MenuItem miFileExit = new MenuItem("Avsluta");
                mbFile.getItems().addAll(miFileLogOut, miFileExit);
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

                menuBar.getMenus().addAll(mbFile, mbCustomer, mbStaff, mbFilm);

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
                Label lAddCustomerHeader = new Label("Lägg till kund");
                lAddCustomerHeader.setFont(new Font(40));
                Label lAddCustomerFirstName = new Label("Förnamn");
                Label lAddCustomerLastName = new Label("Efternamn");
                Label lAddCustomerEmail = new Label("Kundmail");
                Label lAddCustomerStoreId = new Label("ButikID");
                Label lAddCustomerActive = new Label("Aktiv");

                Label lAddCustomerAddress = new Label("Adress");
                Label lAddCustomerPostalCode = new Label("Postkod");
                Label lAddCustomerDistrict = new Label("Distrikt");
                Label lAddCustomerPhone = new Label("Telefonnummer");
                Label lAddCustomerCity = new Label("Stad");
                Label lAddCustomerCountry = new Label("Land");
                Label lAddCustomerAdress2 = new Label("Adress");

                Label lDeleteCustomer = new Label("Ange kundID");

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

                //Text fields
                // add customer
                tfAddCustomerActive.setPromptText("Aktiv?");
                tfAddCustomerRegistered.setPromptText("Registreringsdatum?");
                tfAddCustomerStoreId.setPromptText("Butiksid");
                tfAddCustomerEmail.setPromptText("Mailadress");
                tfAddCustomerLastName.setPromptText("Efternamn");
                tfAddCustomerFirstName.setPromptText("Förnamn");
                tfAddCustomerCountrys.setPromptText("Land");
                tfAddCustomerCity.setPromptText("Stad");
                tfAddCustomerPhone.setPromptText("Telefonnummer");
                tfAddCustomerDistrict.setPromptText("Distrikt");
                tfAddCustomerPostalCode.setPromptText("Postkod");
                tfAddCustomerAddress2.setPromptText("Adress2");
                tfAddCustomerAddress.setPromptText("Adress");

                TextField tfDeleteCustomer = new TextField();
                tfDeleteCustomer.setPromptText("Ange IDnummer");

                //Movie search
                tfMovieSearchTitle.setPromptText("Sök");
                tfMovieSearchReleaseYear.setPromptText("yyyy");
                tfMovieSearchId.setPromptText("Film ID");
                tfMovieSearchDescription.setPromptText("Beskrivning");
                tfMovieSearchActors.setPromptText("Skådespelare förnamn");
                tfMovieSearchLengthMin.setPromptText("Kortaste spellängd");
                tfMovieSearchLengthMax.setPromptText("Längsta spellängd");
                tfMovieSearchRentalCostMin.setPromptText("Minsta hyreskostnaden");
                tfMovieSearchRentalCostMax.setPromptText("Största hyreskostnaden");
                tfMovieSearchReplacementCostMin.setPromptText("Minsta ersättningskostnaden");
                tfMovieSearchReplacementCostMax.setPromptText("Största ersättningskostnaden");
                tfMovieSearchRentalDurationMin.setPromptText("Kortaste hyrestiden");
                tfMovieSearchRentalDurationMax.setPromptText("Längsta hyrestiden");
                tfMovieSearchLastUpdate.setPromptText("yyyy-mm-dd");

                //Movie add
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

                //Customer Search
                tfCustomerSearchFirstName.setPromptText("Kundnamn");
                tfCustomerSearchId.setPromptText("KundID");
                tfCustomerSearchEmail.setPromptText("Kundmail");
                tfCustomerSearchCity.setPromptText("Stad");
                tfCustomerSearchAddress.setPromptText("Adress");
                tfCustomerSearchPhone.setPromptText("Telefonnummer");
                tfCustomerSearchRegistered.setPromptText("Registrerad");
                tfCustomerSearchUpdate.setPromptText("Uppdaterad");

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

                //Customer
                tfCustomerSearchFirstName.setId("customer.first_name");
                tfCustomerSearchId.setId("customer.customer_id");
                tfCustomerSearchEmail.setId("customer.email");
                tfCustomerSearchCity.setId("ci.city");
                tfCustomerSearchAddress.setId("a.address");
                tfCustomerSearchPhone.setId("a.phone");
                tfCustomerSearchRegistered.setId("customer.create_date");
                tfCustomerSearchUpdate.setId("customer.last_update");

                cbCustomerSearchStoreId.setId("customer.store_id");

                chbCustomerSearchActive.setId("customer.active,1");

                //Join text for mySQL for search
                String sMovieJoin =
                        " LEFT JOIN language l ON film.language_id = l.language_id" +
                                " LEFT JOIN film_actor fa ON film.film_id = fa.film_id" +
                                " LEFT JOIN actor a ON fa.actor_id = a.actor_id" +
                                " LEFT JOIN film_category fc ON film.film_id = fc.film_id" +
                                " LEFT JOIN category c ON fc.category_id = c.category_id" +
                                " LEFT JOIN inventory i ON film.film_id = i.film_id" +
                                " LEFT JOIN rental r ON i.inventory_id = r.inventory_id";

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

                //Button function
                bAdvancedSearchCustomer.setOnAction(event -> {
                        hBoxAdvancedSearchCustomer.setVisible(!hBoxAdvancedSearchCustomer.isVisible());
                });

                bAdvancedSearchMovies.setOnAction(event -> {
                        hBoxAdvancedSearchMovies.setVisible(!hBoxAdvancedSearchMovies.isVisible());
                });

                bCreateCustomer.setOnAction(event -> {
                        int countryID = addToDatabase.addCountryID(tfAddCustomerCountrys);
                        int cityID = addToDatabase.addCityID(tfAddCustomerCity, countryID);
                        int adressID = addToDatabase.addAdressId( tfAddCustomerAddress, tfAddCustomerAddress2,
                                tfAddCustomerPostalCode, tfAddCustomerDistrict, tfAddCustomerPhone,cityID);

                        addToDatabase.addCustomer( tfAddCustomerFirstName,
                                tfAddCustomerLastName, tfAddCustomerEmail, tfAddCustomerStoreId,
                                tfAddCustomerActive, adressID);
                        fxBuilder.clearFields(vBoxAddCustomer);

                });

                bStaffAdd.setOnAction(event -> {
                        int countryID = addToDatabase.addCountryID(tfStaffAddCountry);
                        int cityID = addToDatabase.addCityID(tfStaffAddCity, countryID);
                        int addressID = addToDatabase.addAdressId(tfStaffAddAdress,tfStaffAddAdress2,tfStaffAddPostalCode
                                , tfStaffAddDistrict,tfStaffAddPhone, cityID);

                        addToDatabase.AddStaff(tfStaffAddFirstName,tfStaffAddLastName,
                                tfStaffAddEmail,tfStaffAddUserName,tfStaffAddPassword,
                                tfStaffAddStoreID, tfStaffAddActive, addressID);
                        fxBuilder.clearFields(vBoxStaffAdd);
                });

                bDeleteCustomer.setOnAction(event -> {
                        addToDatabase.deleteCustomer(tfDeleteCustomer, "customer", "customer_id");
                        fxBuilder.clearFields(vBoxDeleteCustomer);
                });

                bDeleteStaff.setOnAction(event -> {
                        addToDatabase.deleteCustomer(tfDeleteStaff, "staff", "staff_id");
                        fxBuilder.clearFields(vBoxDeleteStaff);
                });

                bSearchMovie.setOnAction(event -> {
                        executeMovieSearch();
                });

                bSearchCustomer.setOnAction(event -> {
                        executeCustomerSearch();
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
                        FilmSearchResults filmSearchResults = tvSearchResultsMovie.getSelectionModel().getSelectedItem();
                        CustomerSearchResults customerSearchResults = tvSearchResultsCustomer.getSelectionModel().getSelectedItem();
                        if(filmSearchResults != null && customerSearchResults != null){
                                int movieId = filmSearchResults.getId();
                                String customerName = customerSearchResults.getFullName();
                                String movieTitle = filmSearchResults.getTitle();

                                if(fetch.isInStore(movieId,sMovieJoin)) {
                                        //Popup for renting movie
                                        tRentMovie.setText("Vill " + customerName + " hyra " + movieTitle);
                                        fxBuilder.createPopUp(vBoxRentMovie);
                                }
                                else
                                        //Create popup for informing it is not in stock
                                        fxBuilder.createErrorPopup(movieTitle + " är redan uthyrd.");
                        }
                        else
                                //Create popup for error
                                fxBuilder.createErrorPopup("Markera både kund och film \nför att kunna hyra.");
                });

                bRentMovieAccept.setOnAction(e -> {
                        FilmSearchResults filmSearchResults = tvSearchResultsMovie.getSelectionModel().getSelectedItem();
                        int movieId = filmSearchResults.getId();
                        CustomerSearchResults customerSearchResults = tvSearchResultsCustomer.getSelectionModel().getSelectedItem();
                        int customerId = customerSearchResults.getId();
                        addToDatabase.rentMovie(movieId, customerId);
                        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
                });

                bRentMovieDecline.setOnAction(e -> {
                        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
                });

                bInfoMovie.setOnAction(event -> {
                        executeMovieInfo();
                });

                bInfoCustomer.setOnAction(event -> {
                        executeCustomerInfo();
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

                vBoxCenter.getChildren().addAll(lMovieResult,tvSearchResultsMovie,vBoxCenterButtons,lCustomerResult,tvSearchResultsCustomer);

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
                        chbMovieSearchSFBTS, chbMovieSearchSFCommentaries, chbMovieSearchSFDeletedScenes,
                        lMovieSearchRentalDuration,tfMovieSearchRentalDurationMin,tfMovieSearchRentalDurationMax, lMovieSearchReplacementCost,
                        tfMovieSearchReplacementCostMin,tfMovieSearchReplacementCostMax,lMovieSearchLastUpdate,tfMovieSearchLastUpdate);

                //Customer Search
                vBoxCustomerSearch.getChildren().addAll(lCustomerHeader, lCustomerSearchName, tfCustomerSearchFirstName,lCustomerSearchId,
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
                        fetch.login(tfUsername,tfPassword,primaryStage,loginStage);
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
