package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class FilterDialogController {
    public GridPane pane;
    public Button applyBtn;
    public Button resetBtn;
    public Label genresLabel;
    public Label authorsLabel;
    private SQLiteClient client;
    private int maxRow;

    public List<CheckBox> genresCheckBoxesList;
    public List<CheckBox> authorsCheckBoxesList;
    public ObservableList<Genre> genres;
    public ObservableList<Author> authors;
    public MenuButton authorsButton;

    @FXML
    public void initialize() {
        client = new SQLiteClient();

        genres = FXCollections.observableArrayList(client.getGenres());
        authors = FXCollections.observableArrayList(client.getAuthors());

        maxRow = genres.size() > authors.size() ? genres.size() : authors.size();

        genresCheckBoxesList = new ArrayList<>();
        authorsCheckBoxesList = new ArrayList<>();
        genres.forEach(g -> genresCheckBoxesList.add(new CheckBox(g.toString())));
        authors.forEach(a -> authorsCheckBoxesList.add(new CheckBox(a.toString())));

        positionGenres();
        authorsMenuItemsInit();

        pane.getChildren().addAll(genresCheckBoxesList);
        pane.getChildren().addAll(authorsButton);
        pane.getChildren().addAll(authorsLabel, genresLabel);

        GridPane.setConstraints(applyBtn, 0, maxRow + 2);
        GridPane.setConstraints(resetBtn, 1, maxRow + 2);
    }

    private void positionGenres() {
        this.pane.setPadding(new Insets(5, 5, 5, 5));
        this.pane.setVgap(10);
        this.pane.setHgap(5);
        this.pane.setAlignment(Pos.CENTER);

        genresLabel = new Label("Genres: ");
        authorsLabel = new Label("Authors: ");

        GridPane.setConstraints(genresLabel, 0, 0);
        GridPane.setConstraints(authorsLabel, 0, genres.size()/3 + 2);

        for (int i = 0; i < genresCheckBoxesList.size(); i++) {
            GridPane.setConstraints(genresCheckBoxesList.get(i), i % 3, i/3 + 1);
        }
    }

    private void authorsMenuItemsInit() {
        authorsButton = new MenuButton("Select authors");
        authors.stream().map(a -> a.toString()).map(CheckMenuItem::new).forEach(authorsButton.getItems()::add);
        authorsButton.setPrefWidth(150);

        authorsButton.getItems().forEach(i -> i.setOnAction(e -> {
            List<String> selectedAuthors = new ArrayList<>();
            String selectedAuthorsText = new String();

            for (MenuItem menu : authorsButton.getItems()) {
                if (((CheckMenuItem) menu).isSelected()) {
                    selectedAuthors.add(menu.getText());
                }
            }

            if (selectedAuthors.isEmpty()) {
                selectedAuthorsText = "Select authors";
            } else {
                if (selectedAuthors.size() == 1) {
                    selectedAuthorsText = selectedAuthors.get(0);
                } else {
                    for (String sa : selectedAuthors) {
                        selectedAuthorsText += sa;
                        selectedAuthorsText += ", ";
                    }
                }
            }
            authorsButton.setText(selectedAuthorsText);
        }));

        GridPane.setConstraints(authorsButton, 0, genres.size()/3 + 3);
    }
}
