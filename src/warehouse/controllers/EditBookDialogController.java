package warehouse.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static warehouse.constants.StringConstants.BOOK_WAREHOUSE_FOLDER;
import static warehouse.constants.StringConstants.SELECT_FILE_LABEL_TEXT;

/**
 * Controller class for Edit Books Dialog
 */
public class EditBookDialogController {
    public ComboBox authorsComboBox;
    public ComboBox typesComboBox;
    public TextField idTextBox;
    public TextField titleTextBox;
    public TextArea notesTextBox;
    public Button saveBtn;
    public Button closeBtn;
    public Label selectedFileLabel;
    public TextField publisherTextBox;
    public TextField yearTextBox;
    private SQLiteClient client;
    private Book book;
    private ObservableList<Author> authors;
    private ObservableList<Type> types;
    private FileChooser fileChooser;
    private String selectedPath;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        authors = FXCollections.observableArrayList(client.getAuthors());
        types = FXCollections.observableArrayList(client.getTypes());

        selectedFileLabel.setText(SELECT_FILE_LABEL_TEXT);

        fileChooser = new FileChooser();
        titleTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldSaveButtonBeEnabled());
        yearTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearTextBox.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        typesComboBox.setItems(FXCollections.observableArrayList(types));
        authorsComboBox.setItems(FXCollections.observableArrayList(authors));

        shouldSaveButtonBeEnabled();
    }

    public void setBook(Book book) {
        this.book = book;
        this.idTextBox.setText(String.valueOf(this.book.getId()));
        this.titleTextBox.setText(this.book.getTitle());
        this.selectedPath = this.book.getPath();
        this.selectedFileLabel.setText(new File(this.book.getPath()).getName());
        this.notesTextBox.setText(this.book.getNotes());
        this.publisherTextBox.setText(this.book.getPublisher());
        this.yearTextBox.setText(this.book.getYear());
        this.authorsComboBox.getSelectionModel().select(authors.stream().filter(a -> a.getId() == this.book.getAuthor().getId()).findFirst().get());
        this.typesComboBox.getSelectionModel().select(types.stream().filter(g -> g.getId() == this.book.getType().getId()).findFirst().get());
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        saveBtn.setDisable(true);
        try {
            if(!this.book.getPath().equals(selectedPath)) {
                //delete old directory
                FileUtils.deleteDirectory(new File(book.getPath()).getParentFile());

                //copy new file
                File file = new File(selectedPath);
                File bookDirectory = new File(BOOK_WAREHOUSE_FOLDER + "//" + System.currentTimeMillis());
                bookDirectory.mkdirs();
                FileUtils.copyFileToDirectory(file, bookDirectory);
                this.book.setPath(new File(new File(BOOK_WAREHOUSE_FOLDER).getParent()).toPath().relativize(bookDirectory.toPath()) + "\\" + file.getName());
            }

            this.book.setTitle(this.titleTextBox.getText());
            this.book.setNotes(this.notesTextBox.getText());
            this.book.setPublisher(this.publisherTextBox.getText());
            this.book.setYear(this.yearTextBox.getText());
            this.book.setType(this.types.stream().filter(g ->
                    g.getName().equals(typesComboBox.getSelectionModel().getSelectedItem().toString())).findFirst().get());
            this.book.setAuthor(this.authors.stream().filter(a ->
                    a.toString().equals(authorsComboBox.getSelectionModel().getSelectedItem().toString())).findFirst().get());

            client.updateBook(this.book);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        close();
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    /**
     * Method that enable or disable Save Button
     */
    private void shouldSaveButtonBeEnabled() {
        if (this.titleTextBox.getText().isEmpty()) {
            saveBtn.setDisable(true);
        } else {
            saveBtn.setDisable(false);
        }
    }

    public void selectFileBtnClick(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFileLabel.setText(file.getName());
            try {
                selectedPath = file.getCanonicalPath();
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        shouldSaveButtonBeEnabled();
    }

    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }
}
