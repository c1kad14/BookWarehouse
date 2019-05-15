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
import warehouse.data.SQLiteClient;
import warehouse.models.Genre;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static warehouse.constants.StringConstants.*;

/**
 * Controller class for Edit Genres Dialog
 */
public class EditGenresListDialogController {
    public TableView genresView;
    public TableColumn idColumn;
    public TableColumn genreColumn;
    public TableColumn editColumn;
    public TableColumn deleteColumn;
    private ObservableList<Genre> genresList;
    public Button closeBtn;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        genresList = FXCollections.observableArrayList(client.getGenres());

        idColumn.setCellValueFactory(new PropertyValueFactory<>(ID_FIELD));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>(NAME_FIELD));
        editColumn.setCellFactory(getEditButton());
        deleteColumn.setCellFactory(getDeleteButton());

        genresView.setItems(genresList);
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private Callback<TableColumn<Genre, Void>, TableCell<Genre, Void>> getDeleteButton() {
        return new Callback<TableColumn<Genre, Void>, TableCell<Genre, Void>>() {
            @Override
            public TableCell<Genre, Void> call(final TableColumn<Genre, Void> param) {
                final TableCell<Genre, Void> cell = new TableCell<Genre, Void>() {

                    private final Button btn = new Button(DELETE_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Genre genre = getTableView().getItems().get(getIndex());

                            //Call edit dialog open
                            boolean result = false;
                            try {
                                result = client.deleteGenre(genre);
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            System.out.println(result);

                            genresList = FXCollections.observableArrayList(client.getGenres());
                            genresView.setItems(genresList);
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

    private Callback<TableColumn<Genre, Void>, TableCell<Genre, Void>> getEditButton() {
        return new Callback<TableColumn<Genre, Void>, TableCell<Genre, Void>>() {
            @Override
            public TableCell<Genre, Void> call(final TableColumn<Genre, Void> param) {
                final TableCell<Genre, Void> cell = new TableCell<Genre, Void>() {

                    private final Button btn = new Button(EDIT_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Genre genre = getTableView().getItems().get(getIndex());

                            FXMLLoader genreDialogLoader = new FXMLLoader(getClass().getResource("../ui/editGenreDialog.fxml"));
                            Parent editGenreDialog = null;
                            try {
                                editGenreDialog = genreDialogLoader.load();
                            } catch (IOException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            EditGenreDialogController editGenreDialogController = genreDialogLoader.getController();
                            editGenreDialogController.setGenre(genre);

                            Scene scene = new Scene(editGenreDialog, 331, 247);
                            Stage stage = new Stage();

                            stage.setTitle("Edit genre");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            try {
                                stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            stage.setScene(scene);
                            stage.showAndWait();

                            genresList = FXCollections.observableArrayList(client.getGenres());
                            genresView.setItems(genresList);
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

    private void close() { ((Stage) closeBtn.getScene().getWindow()).close(); }

}
