package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.interfaces.FilterListener;
import warehouse.models.Author;
import warehouse.models.Genre;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchController implements FilterListener {
    public Button filterBtn;
    public Button addBtn;
    public TextField searchTextBox;
    private ListController listController;
    private SQLiteClient client;
    private List<Genre> selectedGenres;
    private List<Author> selectedAuthors;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        selectedGenres = new ArrayList<>();
        selectedAuthors = new ArrayList<>();
        addListeners();
    }

    @FXML
    public void addBtnClick(ActionEvent event) throws IOException {
        Parent dialog = FXMLLoader.load(getClass().getResource("../ui/addBookDialog.fxml"));
        Scene scene = new Scene(dialog, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Add new book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();
        listController.bookListChanged();
    }

    public void filterBtnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader filterLoader = new FXMLLoader(getClass().getResource("../ui/filterDialog.fxml"));
        Parent filterDialog = filterLoader.load();
        FilterDialogController filterDialogController = filterLoader.getController();

        filterDialogController.init(this);

        Scene scene = new Scene(filterDialog, 450, 350);
        Stage stage = new Stage();

        stage.setTitle("Apply filters");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    @Override
    public void filterSelectionChanged(List<Genre> selectedGenres, List<Author> selectedAuthors) {
        this.selectedGenres = selectedGenres;
        this.selectedAuthors = selectedAuthors;
        listController.bookListChanged(searchTextBox.getText(), this.selectedGenres, this.selectedAuthors);
    }

    public List<Genre> getSelectedGenres() {
        return this.selectedGenres;
    }

    public List<Author> getSelectedAuthors() {
        return this.selectedAuthors;
    }

    private void addListeners() {
        searchTextBox.textProperty().addListener((observable, oldValue, newValue) ->
                listController.bookListChanged(searchTextBox.getText(), this.selectedGenres, this.selectedAuthors));
    }
}
