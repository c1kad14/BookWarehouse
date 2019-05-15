package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Controller class for Menu Component
 */
public class MenuController {
    public MenuItem addAuthorMenuItem;
    public MenuItem addBookMenuItem;
    public MenuItem addGenreMenuItem;
    public MenuItem editAuthorsMenuItem;
    public MenuItem editBooksMenuItem;
    public MenuItem editGenresMenuItem;
    public MenuItem helpMenuItem;
    public MenuItem aboutMenuItem;
    private ListController listController;

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    public void addAuthorMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addAuthorDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 400, 200);
        Stage stage = new Stage();
        stage.setTitle("Add new author");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void addBookMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addBookDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Add new book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();

        listController.bookListChanged();
    }

    public void addGenreMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addGenreDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 350, 170);
        Stage stage = new Stage();
        stage.setTitle("Add new genre");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void editAuthorsMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/editAuthorsListDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 568, 400);
        Stage stage = new Stage();
        stage.setTitle("Edit Authors");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();


        listController.bookListChanged();
    }

    public void editGenresMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/editGenresListDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 568, 383);
        Stage stage = new Stage();
        stage.setTitle("Edit Genres");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();

        listController.bookListChanged();
    }

    public void editBooksMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/editBooksListDialog.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 662, 326);
        Stage stage = new Stage();
        stage.setTitle("Edit Books");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();

        listController.bookListChanged();
    }

    public void aboutMenuItemClick(ActionEvent actionEvent) throws MalformedURLException {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/about.fxml"));
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Scene scene = new Scene(dialog, 298, 310);
        Stage stage = new Stage();
        stage.setTitle("About Book Warehouse");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();
    }
}
