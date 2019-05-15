package warehouse.interfaces;

import warehouse.models.Author;
import warehouse.models.Genre;

import java.util.List;

/**
 * Interface that declares Notification Methods that fires when book list changed
 */
public interface BookListener {
    void bookListChanged();

    void bookListChanged(String searchValue, List<Genre> genres, List<Author> authors);
}
