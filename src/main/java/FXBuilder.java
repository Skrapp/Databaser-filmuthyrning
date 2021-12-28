import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    public void createErrorPopup(String errorMessage){
        Text tError = new Text(errorMessage);

        Button bClose = new Button("Stäng");
        bClose.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        VBox vBoxError = new VBox(tError,bClose);
        vBoxError.setAlignment(Pos.CENTER);
        vBoxError.setPrefWidth(300);
        vBoxError.setPrefHeight(50);
        vBoxError.setSpacing(10);

        createPopUp(vBoxError);
    }

    public void createInformationPopup(TableView tableView){
        VBox vBoxInfo = new VBox(tableView);
        vBoxInfo.setAlignment(Pos.CENTER);
        vBoxInfo.setSpacing(10);

        createPopUp(vBoxInfo);
    }
}
