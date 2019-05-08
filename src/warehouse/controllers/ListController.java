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
import warehouse.models.Book;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListController {
    public TableView booksView;
    public ObservableList<String> genres;
    public ObservableList<String> authors;

    public TableColumn titleColumn;
    public TableColumn authorColumn;
    public TableColumn genreColumn;
    public TableColumn descColumn;
    public TableColumn actionColumn;

    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        ArrayList<String> gl = new ArrayList<>();
        ArrayList<String> al = new ArrayList<>();
        List<Book> bl = client.getBooks();
        client.getGenres().forEach(g -> gl.add(g.getName()));
        client.getAuthors().forEach(a -> al.add(a.getFirstName()));
        genres = FXCollections.observableArrayList(gl);
        authors = FXCollections.observableArrayList(al);


        authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button("Action");

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

        actionColumn.setCellFactory(cellFactory);
        booksView.setItems(FXCollections.observableArrayList(bl));
    }
}
