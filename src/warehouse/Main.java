package warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = new GridPane();
        Parent menu = FXMLLoader.load(getClass().getResource("ui/menu.fxml"));
        Parent search = FXMLLoader.load(getClass().getResource("ui/search.fxml"));
        Parent list = FXMLLoader.load(getClass().getResource("ui/list.fxml"));

        root.add(menu, 0, 0);
        root.add(search, 0, 1, 5, 2);
        root.add(list, 0, 4);

        primaryStage.setTitle("Book Warehouse");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
