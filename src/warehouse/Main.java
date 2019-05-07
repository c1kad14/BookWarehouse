package warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        Parent grid = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        Parent menu = FXMLLoader.load(getClass().getResource("ui/menu.fxml"));

        root.setTop(menu);
        root.setCenter(grid);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
