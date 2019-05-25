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
import warehouse.models.Type;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static warehouse.constants.StringConstants.*;

/**
 * Controller class for Edit Types Dialog
 */
public class EditTypesListDialogController {
    public TableView typesView;
    public TableColumn idColumn;
    public TableColumn typeColumn;
    public TableColumn editColumn;
    public TableColumn deleteColumn;
    private ObservableList<Type> typesList;
    public Button closeBtn;
    private SQLiteClient client;

    @FXML
    public void initialize() {
        client = new SQLiteClient();
        typesList = FXCollections.observableArrayList(client.getTypes());

        idColumn.setCellValueFactory(new PropertyValueFactory<>(ID_FIELD));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(NAME_FIELD));
        editColumn.setCellFactory(getEditButton());
        deleteColumn.setCellFactory(getDeleteButton());

        typesView.setItems(typesList);
    }

    public void closeBtnClick(ActionEvent actionEvent) {
        close();
    }

    private Callback<TableColumn<Type, Void>, TableCell<Type, Void>> getDeleteButton() {
        return new Callback<TableColumn<Type, Void>, TableCell<Type, Void>>() {
            @Override
            public TableCell<Type, Void> call(final TableColumn<Type, Void> param) {
                final TableCell<Type, Void> cell = new TableCell<Type, Void>() {

                    private final Button btn = new Button(DELETE_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Type type = getTableView().getItems().get(getIndex());

                            //Call edit dialog open
                            try {
                                client.deleteType(type);
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }

                            typesList = FXCollections.observableArrayList(client.getTypes());
                            typesView.setItems(typesList);
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

    private Callback<TableColumn<Type, Void>, TableCell<Type, Void>> getEditButton() {
        return new Callback<TableColumn<Type, Void>, TableCell<Type, Void>>() {
            @Override
            public TableCell<Type, Void> call(final TableColumn<Type, Void> param) {
                final TableCell<Type, Void> cell = new TableCell<Type, Void>() {

                    private final Button btn = new Button(EDIT_FIELD);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Type type = getTableView().getItems().get(getIndex());

                            FXMLLoader typeDialogLoader = new FXMLLoader(getClass().getResource("../ui/editTypeDialog.fxml"));
                            Parent editTypeDialog = null;
                            try {
                                editTypeDialog = typeDialogLoader.load();
                            } catch (IOException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            EditTypeDialogController editTypeDialogController = typeDialogLoader.getController();
                            editTypeDialogController.setType(type);

                            Scene scene = new Scene(editTypeDialog, 331, 247);
                            Stage stage = new Stage();

                            stage.setTitle("Edit type");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            try {
                                stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
                            } catch (MalformedURLException e) {
                                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            }
                            stage.setScene(scene);
                            stage.showAndWait();

                            typesList = FXCollections.observableArrayList(client.getTypes());
                            typesView.setItems(typesList);
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
