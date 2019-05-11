package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import warehouse.models.Genre;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListController implements BookListener {
    public TableView booksView;
    public ObservableList<Genre> genres;
    public ObservableList<Author> authors;

    public TableColumn titleColumn;
    public TableColumn authorColumn;
    public TableColumn genreColumn;
    public TableColumn descColumn;
    public TableColumn actionColumn;

    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        List<Book> bl = client.getBooks();

        genres = FXCollections.observableArrayList(client.getGenres());
        authors = FXCollections.observableArrayList(client.getAuthors());

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        actionColumn.setCellFactory(initActionButton());
        booksView.setItems(FXCollections.observableArrayList(bl));
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

    @Override
    public void bookListChanged(List<Book> books) {
        booksView.setItems(FXCollections.observableArrayList(books));
    }

    @Override
    public void bookListChanged() {
        booksView.setItems(FXCollections.observableArrayList(client.getBooks()));
    }
}
