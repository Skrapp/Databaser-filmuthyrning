import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    View view = new View();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        view.buildInterface(primaryStage);
    }
}
