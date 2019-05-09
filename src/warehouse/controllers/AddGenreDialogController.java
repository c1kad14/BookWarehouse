package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Genre;

public class AddGenreDialogController {
    public TextField genreTextBox;
    public Button saveBtn;
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

    public void saveBtnClick(ActionEvent actionEvent) {
        client.addGenre(new Genre(genreTextBox.getText()));
        close();
    }

    private void addListeners() {
        genreTextBox.textProperty().addListener((observable, oldValue, newValue) -> saveBtn.setDisable(genreTextBox.getText().isEmpty()));
    }

    private void close() {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }
}
