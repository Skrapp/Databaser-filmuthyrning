import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class FXBuilder {
    public void createPopUp(Pane box){
        Stage stage = new Stage();
        Popup p = new Popup();
            box.setPadding(new Insets(10));
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(box);
        Scene scene2 = new Scene(borderPane);
            p.show(stage);
            stage.setScene(scene2);
            stage.show();
            p.setOnCloseRequest(e -> stage.close());
    }

    public void clearFields(Pane box) {
        for (int i = 0; i < box.getChildren().size(); i++) {
        if (box.getChildren().get(i) instanceof Pane)
        {
            clearFields((Pane) box.getChildren().get(i));
        }
        //See if object is a textField
        if(box.getChildren().get(i) instanceof TextField){
            ((TextField) box.getChildren().get(i)).clear();
        }
        //See if object is combobox
        if(box.getChildren().get(i) instanceof ComboBox){
            ((ComboBox) box.getChildren().get(i)).getSelectionModel().clearSelection();
        }
        //See if object is checkbox
        if(box.getChildren().get(i) instanceof CheckBox){
            ((CheckBox) box.getChildren().get(i)).setSelected(false);

            }
        }
    }

    //Errorpopup
}
