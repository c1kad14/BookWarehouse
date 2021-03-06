package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;
import warehouse.data.SQLiteClient;
import warehouse.models.Book;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static warehouse.constants.StringConstants.*;

/**
 * Controller class for Edit Books Dialog
 */
public class EditBooksListDialogController {
    public TableView booksView;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn authorColumn;
    public TableColumn typeColumn;
    public TableColumn editColumn;
    public TableColumn deleteColumn;
    public Button closeBtn;
    public TableColumn notesColumn;
    private SQLiteClient client;
    private ObservableList<Book> booksList;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        booksList = FXCollections.observableArrayList(client.getBooks("", new ArrayList<>(), new ArrayList<>()));

        idColumn.setCellValueFactory(new PropertyValueFactory<>(ID_FIELD));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>(TITLE_FIELD));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>(AUTHOR_FIELD));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(TYPE_FIELD));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>(NOTES_FIELD));
        editColumn.setCellFactory(getEditButton());
        deleteColumn.setCellFactory(getDeleteButton());

        booksView.setItems(booksList);
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private Callback<TableColumn<Book, Void>, TableCell<Book, Void>> getDeleteButton() {
        return new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button(DELETE_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                Book book = getTableView().getItems().get(getIndex());

                                //Call edit dialog open
                                File file = new File(book.getPath());
                                FileUtils.deleteDirectory(file.getParentFile());
                                client.deleteBook(book);

                                booksList = FXCollections.observableArrayList(client.getBooks("", new ArrayList<>(), new ArrayList<>()));
                                booksView.setItems(booksList);
                            } catch (IOException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }


                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private Callback<TableColumn<Book, Void>, TableCell<Book, Void>> getEditButton() {
        return new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button(EDIT_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book book = getTableView().getItems().get(getIndex());

                            FXMLLoader bookDialogLoader = new FXMLLoader(getClass().getResource("/ui/editBookDialog.fxml"));
                            Parent editBookDialog = null;
                            try {
                                editBookDialog = bookDialogLoader.load();
                            } catch (IOException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            EditBookDialogController editBookDialogController = bookDialogLoader.getController();
                            editBookDialogController.setBook(book);

                            Scene scene = new Scene(editBookDialog, 372, 600);
                            Stage stage = new Stage();

                            stage.setTitle("Edit book");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            try {
                                stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            stage.setScene(scene);
                            stage.showAndWait();

                            booksList = FXCollections.observableArrayList(client.getBooks("", new ArrayList<>(), new ArrayList<>()));
                            booksView.setItems(booksList);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }

}
