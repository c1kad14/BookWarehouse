package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;

import java.io.IOException;

public class SearchController {
    public Button filterBtn;
    public Button addBtn;
    public TextField searchTextBox;
    private ListController listController;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        addListeners();
    }

    @FXML
    public void addBtnClick(ActionEvent event) throws IOException {
        Parent dialog = FXMLLoader.load(getClass().getResource("../ui/addBookDialog.fxml"));
        Scene scene = new Scene(dialog, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Add new book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        listController.bookListChanged();
    }

    public void filterBtnClick(ActionEvent actionEvent) throws IOException {
        Parent dialog = FXMLLoader.load(getClass().getResource("../ui/filterDialog.fxml"));
        Scene scene = new Scene(dialog, 450, 400);
        Stage stage = new Stage();
        stage.setTitle("Apply filters");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    private void addListeners() {
        searchTextBox.textProperty().addListener((observable, oldValue, newValue) ->
                listController.bookListChanged(client.searchBooks(searchTextBox.getText())));
    }

}
