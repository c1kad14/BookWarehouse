package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;

/**
 * Controller class for Add Author Dialog
 */
public class AddAuthorDialogController {
    public TextField firstNameTextBox;
    public TextField lastNameTextBox;
    public Button addBtn;
    public Button cancelBtn;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        addListeners();
    }

    public void addBtnClick(ActionEvent actionEvent) {
        client.addAuthor(new Author(firstNameTextBox.getText(), lastNameTextBox.getText()));
        close();
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        close();
    }

    /**
     * Method that enable or disable Save Button
     */
    private void shouldSaveButtonBeEnabled() {
        if (this.firstNameTextBox.getText().isEmpty() && this.lastNameTextBox.getText().isEmpty()) {
            addBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
        }
    }

    /**
     * Method that text fields listeners
     */
    private void addListeners() {
        firstNameTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldSaveButtonBeEnabled());
        lastNameTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldSaveButtonBeEnabled());
    }

    private void close() {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }
}
