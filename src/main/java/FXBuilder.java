import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class FXBuilder {
    public void createPopUp(VBox vBox){
        Stage stage = new Stage();
        Popup p = new Popup();
            vBox.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        Scene scene2 = new Scene(borderPane);
            p.show(stage);
            stage.setScene(scene2);
            stage.show();
            p.setOnCloseRequest(e -> stage.close());


    }

    public void createPopUp(HBox hBox){

        Stage stage = new Stage();
        Popup p = new Popup();
        hBox.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(hBox);
        Scene scene2 = new Scene(borderPane);
        p.show(stage);
        stage.setScene(scene2);
        stage.show();

    }

}
