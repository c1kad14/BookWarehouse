package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Genre;

public class EditGenreDialogController {
    public TextField idTextBox;
    public TextField genreTextBox;
    public Button closeBtn;
    public Button saveBtn;
    private SQLiteClient client;
    private Genre genre;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        this.idTextBox.setText(String.valueOf(this.genre.getId()));
        this.genreTextBox.setText(this.genre.getName());
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        genre.setName(genreTextBox.getText());
        Genre result = client.updateGenre(genre);

        System.out.println(result);

        close();
    }

    private void close() { ((Stage) closeBtn.getScene().getWindow()).close(); }
}
