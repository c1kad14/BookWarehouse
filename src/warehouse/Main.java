package warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import warehouse.controllers.ListController;
import warehouse.controllers.MenuController;
import warehouse.controllers.SearchController;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("ui/menu.fxml"));
        Parent menu = menuLoader.load();
        MenuController menuController = menuLoader.getController();

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("ui/search.fxml"));
        Parent search = searchLoader.load();
        SearchController searchController = searchLoader.getController();

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("ui/list.fxml"));
        Parent list = listLoader.load();
        ListController listController = listLoader.getController();

        searchController.setListController(listController);
        menuController.setListController(listController);

        root.add(menu, 0, 0);
        root.add(search, 0, 1, 5, 2);
        root.add(list, 0, 4);

        primaryStage.setTitle("Book Warehouse");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
