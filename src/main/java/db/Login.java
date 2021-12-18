package db;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.persistence.*;
import java.util.List;

    public class Login extends Application {
        javax.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("hibernate");

        @Override
        public void start(Stage primaryStage) throws Exception {

            Stage loginStage = new Stage();
            BorderPane borderPaneLogin = new BorderPane();
            Scene loginScen = new Scene(borderPaneLogin,400,200);


            //TextFeilds
            TextField tfUsername = new TextField();
            TextField tfPassword = new PasswordField();

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

            HBox hBoxButtons = new HBox(bLogin,bCancel);
            hBoxButtons.setSpacing(10);

            HBox hBoxLogin = new HBox(vBoxLLogin,vBoxtfLogin);

            //Buttons action
            bCancel.setOnAction(event -> {
                tfUsername.clear();
                tfPassword.clear();
            });
            bLogin.setOnAction(event -> {
                login(tfUsername,tfPassword);
                tfPassword.clear();
                tfUsername.clear();
            });


            borderPaneLogin.setCenter(hBoxLogin);
            hBoxButtons.setAlignment(Pos.CENTER_LEFT);

            borderPaneLogin.setBottom(hBoxButtons);
            hBoxButtons.setAlignment(Pos.BOTTOM_CENTER);

            loginStage.setScene(loginScen);
            loginStage.show();




        }

        private void login(TextField tfUsername, TextField tfPassword) {
            EntityManager entityManager = EntityManagerFactory.createEntityManager();
            EntityTransaction transaction = null;

            String username = tfUsername.getText();
            String password = tfPassword.getText();

            try{
                Query query = entityManager.createNativeQuery(
                        "SELECT password FROM staff WHERE username='"+username+"'");

                List<String> list = query.getResultList();
                String p = list.get(0);

                System.out.println(p);

                if (password.equals(p)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("YAAAAAAY");
                    tfPassword.clear();
                    tfUsername.clear();
                    alert.show();

                } else  {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Fel användarnamn eller lösenord");
                    alert.show();
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


