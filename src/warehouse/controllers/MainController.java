package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import warehouse.data.SQLiteClient;
import warehouse.models.Book;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    public ChoiceBox genresComboBox;
    public ComboBox authorsComboBox;
    public TableView booksView;
    public ObservableList<String> genres;
    public ObservableList<String> authors;
    private SQLiteClient client;

    public MainController() {

    }

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
        genresComboBox.setItems(genres);
        authorsComboBox.setItems(authors);


        TableColumn<String, Book> column1 = new TableColumn<>("Author");
        column1.setCellValueFactory(new PropertyValueFactory<>("Author"));

        TableColumn<String, Book> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("Title"));

        TableColumn<String, Book> column3 = new TableColumn<>("Genre");
        column3.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn<String, Book> column4 = new TableColumn<>("Description");
        column4.setCellValueFactory(new PropertyValueFactory<>("Description"));

        TableColumn<Book, Void> column5 = new TableColumn<>("Open");

        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
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

        column5.setCellFactory(cellFactory);
        booksView.getColumns().addAll(column1, column2, column3, column4, column5);
        booksView.setItems(FXCollections.observableArrayList(bl));
    }
}
