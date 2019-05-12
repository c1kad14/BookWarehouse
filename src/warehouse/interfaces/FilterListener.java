package warehouse.interfaces;

import warehouse.models.Author;
import warehouse.models.Genre;

import java.util.List;

public interface FilterListener {
    void filterSelectionChanged(List<Genre> selectedGenres, List<Author> selectedAuthors);
}