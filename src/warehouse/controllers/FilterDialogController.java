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
    public Label typesLabel;
    public Label authorsLabel;
    private SQLiteClient client;
    private int maxRow;

    public List<CheckBox> typesCheckBoxesList;
    public ObservableList<Type> types;
    public ObservableList<Author> authors;

    private List<Type> selectedTypes;
    private List<Author> selectedAuthors;
    public MenuButton authorsButton;
    private SearchController searchController;

    @FXML
    public void initialize() {
        client = new SQLiteClient();

        types = FXCollections.observableArrayList(client.getTypes());
        authors = FXCollections.observableArrayList(client.getAuthors());

        selectedTypes = new ArrayList<>();
        selectedAuthors = new ArrayList<>();

        maxRow = types.size() > authors.size() ? types.size() : authors.size();

        typesCheckBoxesList = new ArrayList<>();

        types.forEach(g -> typesCheckBoxesList.add(new CheckBox(g.toString())));

        positionTypes();
        authorsMenuItemsInit();

        pane.getChildren().addAll(typesCheckBoxesList);
        pane.getChildren().add(authorsButton);
        pane.getChildren().addAll(authorsLabel, typesLabel);

        GridPane.setConstraints(applyBtn, 0, maxRow);
        GridPane.setConstraints(resetBtn, 1, maxRow);
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        typesCheckBoxesList.forEach(g -> g.setSelected(false));
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

    private void positionTypes() {
        this.pane.setPadding(new Insets(5, 5, 5, 5));
        this.pane.setVgap(10);
        this.pane.setHgap(5);
        this.pane.setAlignment(Pos.CENTER);

        typesLabel = new Label("Types: ");
        authorsLabel = new Label("Authors: ");

        GridPane.setConstraints(typesLabel, 0, 0);
        GridPane.setConstraints(authorsLabel, 0, types.size() / 3 + 2);

        for (int i = 0; i < typesCheckBoxesList.size(); i++) {
            CheckBox typeCheckBox = typesCheckBoxesList.get(i);
            typeCheckBox.setOnAction(event -> {
                if (typeCheckBox.isSelected()) {
                    selectedTypes.add(types.stream().filter(g -> g.toString().equals(typeCheckBox.getText())).findFirst().get());
                } else {
                    selectedTypes.removeIf(sg -> sg.getId() == types.stream().filter(g -> g.toString().equals(typeCheckBox.getText())).findFirst().get().getId());
                }
            });
            GridPane.setConstraints(typesCheckBoxesList.get(i), i % 3, i / 3 + 1);
        }
    }

    private void authorsMenuItemsInit() {
        authorsButton = new MenuButton("Select authors");
        authors.stream().map(a -> a.toString()).map(CheckMenuItem::new).forEach(authorsButton.getItems()::add);
        authorsButton.setPrefWidth(250);

        authorsButton.getItems().forEach(i -> i.setOnAction(e -> {
            if (((CheckMenuItem) i).isSelected()) {
                selectedAuthors.add(authors.stream().filter(a -> a.toString().equals(i.getText())).findFirst().get());
            } else {
                selectedAuthors.removeIf(sa -> sa.getId() == authors.stream().filter(a -> a.toString().equals(i.getText())).findFirst().get().getId());
            }

            setAuthorsBtnText();
        }));

        GridPane.setConstraints(authorsButton, 0, types.size() / 3 + 3, 2, 1);
    }

    /**
     * Method that inits controls with existing selections
     */
    private void initSelections() {
        selectedTypes.forEach(sg -> typesCheckBoxesList.stream().filter(gcb ->
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
