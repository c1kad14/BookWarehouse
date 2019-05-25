package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Type;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static warehouse.constants.StringConstants.BOOK_WAREHOUSE_FOLDER;
import static warehouse.constants.StringConstants.SELECT_FILE_LABEL_TEXT;

/**
 * Controller class for Add Book Dialog
 */
public class AddBookDialogController {

    @FXML
    public AnchorPane pane;
    public Button cancelBtn;
    public TextField titleTextBox;
    public ComboBox typesComboBox;
    public ComboBox authorsComboBox;
    public Button selectFileBtn;
    public Button addBtn;
    public Label selectedFileLabel;
    public TextArea notesTextBox;
    public TextField yearTextBox;
    public TextField publisherTextBox;
    private FileChooser fileChooser;
    private SQLiteClient client;
    private List<Type> types;
    private List<Author> authors;
    private String selectedPath;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        selectedFileLabel.setText(SELECT_FILE_LABEL_TEXT);

        types = client.getTypes();
        authors = client.getAuthors();

        fileChooser = new FileChooser();

        typesComboBox.setItems(FXCollections.observableArrayList(types));
        authorsComboBox.setItems(FXCollections.observableArrayList(authors));

        addListeners();
        shouldAddButtonBeEnabled();
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
        try {
            addBtn.setDisable(true);

            File file = new File(selectedPath);
            File bookDirectory = new File(BOOK_WAREHOUSE_FOLDER + "//" + System.currentTimeMillis());
            bookDirectory.mkdirs();

            FileUtils.copyFileToDirectory(file, bookDirectory);

            Book book = new Book();
            book.setTitle(titleTextBox.getText());
            book.setNotes(notesTextBox.getText());
            book.setPublisher(publisherTextBox.getText());
            book.setYear(yearTextBox.getText());
            book.setType(types.stream().filter(g -> g.getName().equals(typesComboBox.getSelectionModel().getSelectedItem().toString())).findFirst().get());
            book.setAuthor(authors.stream().filter(a -> a.toString().equals(authorsComboBox.getSelectionModel().getSelectedItem().toString())).findFirst().get());
            book.setPath(bookDirectory.getPath() + "//" + file.getName());

            client.addBook(book);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        close();
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        close();
    }

    /**
     * Method that enable or disable Add Button
     */
    private void shouldAddButtonBeEnabled() {
        if (this.titleTextBox.getText().isEmpty() || this.authorsComboBox.getSelectionModel().isEmpty()
                || this.typesComboBox.getSelectionModel().isEmpty() || selectedFileLabel.getText().equals(SELECT_FILE_LABEL_TEXT)) {
            addBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
        }
    }

    /**
     * Method that combo boxes and text fields listeners
     */
    private void addListeners() {
        typesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());
        authorsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());
        titleTextBox.textProperty().addListener((observable, oldValue, newValue) -> shouldAddButtonBeEnabled());
        yearTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearTextBox.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void close() {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }
}
