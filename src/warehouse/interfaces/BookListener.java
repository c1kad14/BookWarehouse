package warehouse.interfaces;

import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Genre;

import java.util.List;

public interface BookListener {
    void bookListChanged();

    void bookListChanged(List<Book> books);

    void bookListChanged(List<Genre> genres, List<Author> authors);
}
