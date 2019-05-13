package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;

public class EditAuthorDialogController {
    public TextField idTextBox;
    public TextField firstNameTextBox;
    public TextField lastNameTextBox;
    public Button closeBtn;
    public Button saveBtn;
    private Author author;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
    }

    public void setAuthor(Author author) {
        this.author = author;
        idTextBox.setText(String.valueOf(this.author.getId()));
        firstNameTextBox.setText(this.author.getFirstName());
        lastNameTextBox.setText(this.author.getLastName());
    }


    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        author.setFirstName(firstNameTextBox.getText());
        author.setLastName(lastNameTextBox.getText());
        Author result = client.updateAuthor(author);

        System.out.println(result);

        close();
    }

    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }
}
