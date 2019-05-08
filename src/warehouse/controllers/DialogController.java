package warehouse.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Genre;

import java.util.List;

import static warehouse.utils.StringConstants.SELECT_FILE_LABEL_TEXT;

public class DialogController {

    public Button cancelBtn;
    public TextField titleTextBox;
    public ComboBox genresComboBox;
    public ComboBox authorsComboBox;
    public Button selectFileBtn;
    public Button addBtn;
    public Label selectedFileLabel;
    private SQLiteClient client;


    @FXML
    public void initialize() {
        client = new SQLiteClient();
        selectedFileLabel.setText(SELECT_FILE_LABEL_TEXT);

        List<Genre> genres = client.getGenres();
        List<Author> authors = client.getAuthors();

        genresComboBox.setItems(FXCollections.observableArrayList(genres));
        authorsComboBox.setItems(FXCollections.observableArrayList(authors));

        genresComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                System.out.println("GENRES CHANGED");
                isAddBtnEnabled();
            }
        });

        authorsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                System.out.println("GENRES CHANGED");
                isAddBtnEnabled();
            }
        });

        titleTextBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                System.out.println("TITLE CHANGED");
                isAddBtnEnabled();

            }
        });


        isAddBtnEnabled();
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
    }

    public void selectFileBtnClick(ActionEvent actionEvent) {
    }

    public void addBtnClick(ActionEvent actionEvent) {
    }

    private void isAddBtnEnabled() {
        if (this.titleTextBox.getText().isEmpty() || this.authorsComboBox.getSelectionModel().isEmpty()
               || this.genresComboBox.getSelectionModel().isEmpty() /** || selectedFileLabel.equals(SELECT_FILE_LABEL_TEXT)*/) {
            addBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
        }
    }

}
