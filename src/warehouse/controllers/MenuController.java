package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    public MenuItem addAuthorMenuItem;
    public MenuItem addBookMenuItem;
    public MenuItem addGenreMenuItem;
    public MenuItem editAuthorsMenuItem;
    public MenuItem editBooksMenuItem;
    public MenuItem editGenresMenuItem;
    public MenuItem helpMenuItem;
    public MenuItem aboutMenuItem;

    @FXML
    public void initialize() {

    }

    public void addAuthorMenuItemClick(ActionEvent actionEvent) {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addAuthorDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(dialog, 400, 200);
        Stage stage = new Stage();
        stage.setTitle("Add new author");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void addBookMenuItemClick(ActionEvent actionEvent) {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addBookDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(dialog, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Add new book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void addGenreMenuItemClick(ActionEvent actionEvent) {
        Parent dialog = null;
        try {
            dialog = FXMLLoader.load(getClass().getResource("../ui/addGenreDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(dialog, 350, 170);
        Stage stage = new Stage();
        stage.setTitle("Add new genre");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
