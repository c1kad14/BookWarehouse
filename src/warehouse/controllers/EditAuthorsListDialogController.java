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
import warehouse.models.Author;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static warehouse.constants.StringConstants.*;

/**
 * Controller class for Edit Authors Dialog
 */
public class EditAuthorsListDialogController {
    public TableView authorsView;
    public TableColumn idColumn;
    public TableColumn firstNameColumn;
    public TableColumn lastNameColumn;
    public TableColumn editColumn;
    public TableColumn deleteColumn;
    public Button closeBtn;
    private SQLiteClient client;
    private ObservableList<Author> authorList;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        authorList = FXCollections.observableArrayList(client.getAuthors());

        idColumn.setCellValueFactory(new PropertyValueFactory<>(ID_FIELD));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>(FNAME_FIELD));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>(LNAME_FIELD));
        editColumn.setCellFactory(getEditButton());
        deleteColumn.setCellFactory(getDeleteButton());

        authorsView.setItems(authorList);
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private Callback<TableColumn<Author, Void>, TableCell<Author, Void>> getDeleteButton() {
        return new Callback<TableColumn<Author, Void>, TableCell<Author, Void>>() {
            @Override
            public TableCell<Author, Void> call(final TableColumn<Author, Void> param) {
                final TableCell<Author, Void> cell = new TableCell<Author, Void>() {

                    private final Button btn = new Button(DELETE_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Author author = getTableView().getItems().get(getIndex());

                            //Call edit dialog open
                            try {
                                client.deleteAuthor(author);
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }

                            authorList = FXCollections.observableArrayList(client.getAuthors());
                            authorsView.setItems(authorList);
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

    private Callback<TableColumn<Author, Void>, TableCell<Author, Void>> getEditButton() {
        return new Callback<TableColumn<Author, Void>, TableCell<Author, Void>>() {
            @Override
            public TableCell<Author, Void> call(final TableColumn<Author, Void> param) {
                final TableCell<Author, Void> cell = new TableCell<Author, Void>() {

                    private final Button btn = new Button(EDIT_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Author author = getTableView().getItems().get(getIndex());

                            FXMLLoader authorDialogLoader = new FXMLLoader(getClass().getResource("../ui/editAuthorDialog.fxml"));
                            Parent editAuthorDialog = null;
                            try {
                                editAuthorDialog = authorDialogLoader.load();
                            } catch (IOException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            EditAuthorDialogController filterDialogController = authorDialogLoader.getController();

                            filterDialogController.setAuthor(author);

                            Scene scene = new Scene(editAuthorDialog, 383, 282);
                            Stage stage = new Stage();

                            stage.setTitle("Edit author");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            try {
                                stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            stage.setScene(scene);
                            stage.showAndWait();

                            authorList = FXCollections.observableArrayList(client.getAuthors());
                            authorsView.setItems(authorList);
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
