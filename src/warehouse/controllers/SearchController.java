package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchController {
    public Button searchBtn;
    public Button filterBtn;
    public Button addBtn;
    private ListController listController;

    @FXML
    public void initialize() {
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

    public void searchBtnClick(ActionEvent actionEvent) {


    }

    public void filterBtnClick(ActionEvent actionEvent) {

    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }
}
