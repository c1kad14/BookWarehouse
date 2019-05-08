package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Genre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    public Button searchBtn;
    public Button filterBtn;
    public Button addBtn;

    @FXML
    public void initialize() {
    }

    @FXML
    public void addBtnClick(ActionEvent event) throws IOException {
        Parent dialog = FXMLLoader.load(getClass().getResource("../ui/dialog.fxml"));


        Scene scene = new Scene(dialog, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Add new book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void searchBtnClick(ActionEvent actionEvent) {


    }

    public void filterBtnClick(ActionEvent actionEvent) {

    }
}
