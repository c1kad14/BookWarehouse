package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Type;

/**
 * Controller class for Edit Type Dialog
 */
public class EditGenreDialogController {
    public TextField idTextBox;
    public TextField genreTextBox;
    public Button closeBtn;
    public Button saveBtn;
    private SQLiteClient client;
    private Type type;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
    }

    public void setType(Type type) {
        this.type = type;
        this.idTextBox.setText(String.valueOf(this.type.getId()));
        this.genreTextBox.setText(this.type.getName());
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        type.setName(genreTextBox.getText());
        Type result = client.updateGenre(type);

        System.out.println(result);

        close();
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private void close() { ((Stage) closeBtn.getScene().getWindow()).close(); }
}
