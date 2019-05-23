package warehouse.interfaces;

import warehouse.models.Author;
import warehouse.models.Type;

import java.util.List;

/**
 * Interface that declares Method that fires when filter selections changed
 */
public interface FilterListener {
    void filterSelectionChanged(List<Type> selectedTypes, List<Author> selectedAuthors);
}
