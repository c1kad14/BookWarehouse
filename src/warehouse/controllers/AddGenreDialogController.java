package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Genre;

/**
 * Controller class responsible for Add Genre Dialog
 */
public class AddGenreDialogController {
    public TextField genreTextBox;
    public Button addBtn;
    public Button cancelBtn;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        addListeners();
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        close();
    }

    public void addBtnClick(ActionEvent actionEvent) {
        client.addGenre(new Genre(genreTextBox.getText()));
        close();
    }

    /**
     * Method that text fields listeners
     */
    private void addListeners() {
        genreTextBox.textProperty().addListener((observable, oldValue, newValue) -> addBtn.setDisable(genreTextBox.getText().isEmpty()));
    }

    private void close() { ((Stage) cancelBtn.getScene().getWindow()).close(); }
}
