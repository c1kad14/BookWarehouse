package warehouse.interfaces;

import warehouse.models.Author;
import warehouse.models.Genre;

import java.util.List;

/**
 * Interface that declares Method that fires when filter selections changed
 */
public interface FilterListener {
    void filterSelectionChanged(List<Genre> selectedGenres, List<Author> selectedAuthors);
}
