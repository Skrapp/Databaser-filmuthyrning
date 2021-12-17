import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class FXBuilder {
    public void createPopUp(VBox vBox){
        Stage stage = new Stage();
        Popup p = new Popup();
        Scene scene2 = new Scene(vBox);
        p.show(stage);
        stage.setScene(scene2);
        stage.show();
    }

    public void createPopUp(HBox hBox){
        Stage stage = new Stage();
        Popup p = new Popup();
        Scene scene2 = new Scene(hBox);
        p.show(stage);
        stage.setScene(scene2);
        stage.show();
    }

}
