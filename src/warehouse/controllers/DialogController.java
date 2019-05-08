package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Genre;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static warehouse.utils.StringConstants.SELECT_FILE_LABEL_TEXT;

public class DialogController {

    public Button cancelBtn;
    public TextField titleTextBox;
    public ComboBox genresComboBox;
    public ComboBox authorsComboBox;
    public Button selectFileBtn;
    public Button addBtn;
    public Label selectedFileLabel;
    public TextArea descTextBox;
    private FileChooser fileChooser;
    private SQLiteClient client;
    private List<Genre> genres;
    private List<Author> authors;
    private String selectedPath;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        selectedFileLabel.setText(SELECT_FILE_LABEL_TEXT);

        genres = client.getGenres();
        authors = client.getAuthors();

        fileChooser = new FileChooser();
        genresComboBox.setItems(FXCollections.observableArrayList(genres));
        authorsComboBox.setItems(FXCollections.observableArrayList(authors));

        addListeners();
        shouldAddButtonBeEnabled();
    }


    public void cancelBtnClick(ActionEvent actionEvent) {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    public void selectFileBtnClick(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFileLabel.setText(file.getName());
            selectedPath = file.getAbsolutePath();
        }
        shouldAddButtonBeEnabled();
    }

    public void addBtnClick(ActionEvent actionEvent) {
        Book book = new Book();
        book.setTitle(titleTextBox.getText());
        book.setDescription(descTextBox.getText());

        Optional<Genre> genre = genres.stream().filter(g -> g.getName().equals(genresComboBox.getSelectionModel().getSelectedItem().toString())).findFirst();
        book.setGenre(genre.get());

        Optional<Author> author = authors.stream().filter(a -> a.toString().equals(authorsComboBox.getSelectionModel().getSelectedItem().toString())).findFirst();
        book.setAuthor(author.get());

        book.setPath(selectedPath);

        client.addBook(book);

        ((Stage) addBtn.getScene().getWindow()).close();
    }

    private void shouldAddButtonBeEnabled() {
        if (this.titleTextBox.getText().isEmpty() || this.authorsComboBox.getSelectionModel().isEmpty()
                || this.genresComboBox.getSelectionModel().isEmpty() || selectedFileLabel.getText().equals(SELECT_FILE_LABEL_TEXT)) {
            addBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
        }
    }

    private void addListeners() {
        genresComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());

        authorsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());

        titleTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());
    }
}
