package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Type;

/**
 * Controller class responsible for Add Type Dialog
 */
public class AddTypeDialogController {
    public TextField typeTextBox;
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
        client.addType(new Type(typeTextBox.getText()));
        close();
    }

    /**
     * Method that text fields listeners
     */
    private void addListeners() {
        typeTextBox.textProperty().addListener((observable, oldValue, newValue) -> addBtn.setDisable(typeTextBox.getText().isEmpty()));
    }

    private void close() { ((Stage) cancelBtn.getScene().getWindow()).close(); }
}
