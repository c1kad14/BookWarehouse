package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import warehouse.data.SQLiteClient;
import warehouse.models.Author;
import warehouse.models.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Filter Dialog
 */
public class FilterDialogController {
    public GridPane pane;
    public Button applyBtn;
    public Button resetBtn;
    public Label genresLabel;
    public Label authorsLabel;
    private SQLiteClient client;
    private int maxRow;

    public List<CheckBox> genresCheckBoxesList;
    public ObservableList<Type> types;
    public ObservableList<Author> authors;

    private List<Type> selectedTypes;
    private List<Author> selectedAuthors;
    public MenuButton authorsButton;
    private SearchController searchController;

    @FXML
    public void initialize() {
        client = new SQLiteClient();

        types = FXCollections.observableArrayList(client.getGenres());
        authors = FXCollections.observableArrayList(client.getAuthors());

        selectedTypes = new ArrayList<>();
        selectedAuthors = new ArrayList<>();

        maxRow = types.size() > authors.size() ? types.size() : authors.size();

        genresCheckBoxesList = new ArrayList<>();

        types.forEach(g -> genresCheckBoxesList.add(new CheckBox(g.toString())));

        positionGenres();
        authorsMenuItemsInit();

        pane.getChildren().addAll(genresCheckBoxesList);
        pane.getChildren().addAll(authorsButton);
        pane.getChildren().addAll(authorsLabel, genresLabel);

        GridPane.setConstraints(applyBtn, 0, maxRow + 1);
        GridPane.setConstraints(resetBtn, 1, maxRow + 1);
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        genresCheckBoxesList.forEach(g -> g.setSelected(false));
        authorsButton.getItems().forEach(i -> ((CheckMenuItem) i).setSelected(false));

        selectedTypes = new ArrayList<>();
        selectedAuthors = new ArrayList<>();

        setAuthorsBtnText();
    }

    public void applyBtnClick(ActionEvent actionEvent) {
        searchController.filterSelectionChanged(selectedTypes, selectedAuthors);
        close();
    }

    /**
     * Method that inits controller with existing selections from search controller
     *
     * @param searchController search controller instance
     */
    public void init(SearchController searchController) {
        this.searchController = searchController;
        this.selectedAuthors = searchController.getSelectedAuthors();
        this.selectedTypes = searchController.getSelectedTypes();
        initSelections();
    }

    private void positionGenres() {
        this.pane.setPadding(new Insets(5, 5, 5, 5));
        this.pane.setVgap(10);
        this.pane.setHgap(5);
        this.pane.setAlignment(Pos.CENTER);

        genresLabel = new Label("Genres: ");
        authorsLabel = new Label("Authors: ");

        GridPane.setConstraints(genresLabel, 0, 0);
        GridPane.setConstraints(authorsLabel, 0, types.size() / 3 + 2);

        for (int i = 0; i < genresCheckBoxesList.size(); i++) {
            CheckBox genreCheckBox = genresCheckBoxesList.get(i);
            genreCheckBox.setOnAction(event -> {
                if (genreCheckBox.isSelected()) {
                    selectedTypes.add(types.stream().filter(g -> g.toString().equals(genreCheckBox.getText())).findFirst().get());
                } else {
                    selectedTypes.removeIf(sg -> sg.getId() == types.stream().filter(g -> g.toString().equals(genreCheckBox.getText())).findFirst().get().getId());
                }
            });
            GridPane.setConstraints(genresCheckBoxesList.get(i), i % 3, i / 3 + 1);
        }
    }

    private void authorsMenuItemsInit() {
        authorsButton = new MenuButton("Select authors");
        authors.stream().map(a -> a.toString()).map(CheckMenuItem::new).forEach(authorsButton.getItems()::add);
        authorsButton.setPrefWidth(150);

        authorsButton.getItems().forEach(i -> i.setOnAction(e -> {
            if (((CheckMenuItem) i).isSelected()) {
                selectedAuthors.add(authors.stream().filter(a -> a.toString().equals(i.getText())).findFirst().get());
            } else {
                selectedAuthors.removeIf(sa -> sa.getId() == authors.stream().filter(a -> a.toString().equals(i.getText())).findFirst().get().getId());
            }

            setAuthorsBtnText();
        }));

        GridPane.setConstraints(authorsButton, 0, types.size() / 3 + 3);
    }

    /**
     * Method that inits controls with existing selections
     */
    private void initSelections() {
        selectedTypes.forEach(sg -> genresCheckBoxesList.stream().filter(gcb ->
                gcb.getText().equals(sg.getName())).findFirst().get().setSelected(true));

        selectedAuthors.forEach(sa -> ((CheckMenuItem) authorsButton.getItems().stream().filter(item ->
                item.getText().equals(sa.toString())).findFirst().get()).setSelected(true));

        setAuthorsBtnText();
    }

    /**
     * Method that sets text for selected authors
     */
    private void setAuthorsBtnText() {
        String selectedAuthorsText = new String();

        if (selectedAuthors.isEmpty()) {
            selectedAuthorsText = "Select authors";
        } else {
            if (selectedAuthors.size() == 1) {
                selectedAuthorsText = selectedAuthors.get(0).toString();
            } else {
                for (Author sa : selectedAuthors) {
                    selectedAuthorsText += sa.toString();
                    selectedAuthorsText += ", ";
                }
            }
        }
        authorsButton.setText(selectedAuthorsText);
    }

    private void close() { ((Stage) applyBtn.getScene().getWindow()).close(); }
}
