import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.*;
import java.util.List;

    public class Login extends Application {
        javax.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("hibernate");

        @Override
        public void start(Stage primaryStage) {

            Stage loginStage = new Stage();
            loginStage.setTitle("Logga in");

            BorderPane borderPaneLogin = new BorderPane();
            Scene loginScen = new Scene(borderPaneLogin,400,400);
            borderPaneLogin.setPadding(new Insets(20));


            //TextFeilds
            TextField tfUsername = new TextField();
            tfUsername.setPromptText("Användarnamn");
            TextField tfPassword = new PasswordField();
            tfPassword.setPromptText("Lösenord");

            //Text
            Label tWelcome = new Label("Välkommen");
            tWelcome.setFont(Font.font("Tahoma", FontWeight.LIGHT,30));
            tWelcome.setPadding(new Insets(0, 0, 30, 0));
            //Labels
            Label lUsername = new Label("Användarnamn");
            lUsername.setFont(Font.font(18));
            Label lPassword = new Label("Lösenord");
            lPassword.setFont(Font.font(18));

            //Button
            Button bLogin = new Button("Logga in");
            Button bCancel = new Button("Avbryt");

            //Boxjävlar
            VBox vBoxtfLogin = new VBox(tfUsername,tfPassword);
            VBox vBoxLLogin = new VBox(lUsername,lPassword);
            vBoxLLogin.setPadding(new Insets(10));
            vBoxtfLogin.setPadding(new Insets(10));

            HBox hBoxButtons = new HBox(bLogin,bCancel);
            hBoxButtons.setSpacing(10);
            hBoxButtons.setPadding(new Insets(10, 0, 0, 95));

            HBox hBoxLogin = new HBox(vBoxLLogin,vBoxtfLogin);

            VBox vBoxAllOfLogin = new VBox(tWelcome,hBoxLogin,hBoxButtons);

            //Buttons action
            bCancel.setOnAction(event -> {
                tfUsername.clear();
                tfPassword.clear();
            });
            bLogin.setOnAction(event -> {
                login(tfUsername,tfPassword,primaryStage,loginStage);
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




        }

        public void login(TextField tfUsername, TextField tfPassword, Stage primaryStage, Stage loginStage) {
            EntityManager entityManager = EntityManagerFactory.createEntityManager();
            EntityTransaction transaction = null;

            String username = tfUsername.getText();
            String password = tfPassword.getText();

            try{
                Query queryPassword = entityManager.createNativeQuery(
                        "SELECT password FROM staff WHERE username='"+username+"'");


                if (queryPassword.getResultList().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Fel användarnamn eller lösenord");
                    alert.show();
                } else {

                    List<String> listPass = queryPassword.getResultList();
                    String p = listPass.get(0);

                    if (p == null) {
                        p = "";
                    }

                    if (password.equals(p)) {
                        tfPassword.clear();
                        tfUsername.clear();
                        primaryStage.show();
                        loginStage.close();


                    } else {
                        System.out.println("Fel lösenord");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Fel användarnamn eller lösenord");
                        alert.show();
                    }
                }

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }finally {
                entityManager.close();
            }
        }


    }


