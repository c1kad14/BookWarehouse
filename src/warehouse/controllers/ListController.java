package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import warehouse.data.SQLiteClient;
import warehouse.interfaces.BookListener;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Type;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static warehouse.constants.StringConstants.*;

/**
 * Controller class for List Component
 */
public class ListController implements BookListener {
    public TableView booksView;
    public List<Type> types;
    public List<Author> authors;

    public TableColumn titleColumn;
    public TableColumn authorColumn;
    public TableColumn typeColumn;
    public TableColumn notesColumn;
    public TableColumn actionColumn;
    public TableColumn yearColumn;
    public TableColumn publisherColumn;

    private SQLiteClient client;

    private String searchValue;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        List<Book> bl = client.getBooks("", new ArrayList<>(), new ArrayList<>());
        types = new ArrayList<>();
        authors = new ArrayList<>();
        searchValue = EMPTY_STRING;

        authorColumn.setCellValueFactory(new PropertyValueFactory<>(AUTHOR_FIELD));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>(TITLE_FIELD));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(TYPE_FIELD));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>(NOTES_FIELD));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>(YEAR_FIELD));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>(PUBLISHER_FIELD));
        actionColumn.setCellFactory(initActionButton());
        booksView.setItems(FXCollections.observableArrayList(bl));
    }

    @Override
    public void bookListChanged() {
        booksView.setItems(FXCollections.observableArrayList(
                client.getBooks(this.searchValue, this.types, this.authors)));
    }

    @Override
    public void bookListChanged(String searchValue, List<Type> types, List<Author> authors) {
        this.searchValue = searchValue;
        this.types = types;
        this.authors = authors;

        booksView.setItems(FXCollections.observableArrayList(client.getBooks(searchValue, types, authors)));
    }

    private Callback<TableColumn<Book, Void>, TableCell<Book, Void>> initActionButton() {
        return new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button("Open");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book data = getTableView().getItems().get(getIndex());

                            try {
                                Desktop.getDesktop().open(new File(data.getPath()));
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
}
