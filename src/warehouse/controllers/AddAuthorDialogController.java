package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;

public class AddAuthorDialogController {
    public TextField firstNameTextBox;
    public TextField lastNameTextBox;
    public Button saveBtn;
    public Button cancelBtn;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        addListeners();
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        client.addAuthor(new Author(firstNameTextBox.getText(), lastNameTextBox.getText()));
        close();
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        close();
    }

    private void shouldSaveButtonBeEnabled() {
        if (this.firstNameTextBox.getText().isEmpty() && this.lastNameTextBox.getText().isEmpty()) {
            saveBtn.setDisable(true);
        } else {
            saveBtn.setDisable(false);
        }
    }

    private void addListeners() {
        firstNameTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldSaveButtonBeEnabled());
        lastNameTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldSaveButtonBeEnabled());
    }

    private void close() {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }
}
