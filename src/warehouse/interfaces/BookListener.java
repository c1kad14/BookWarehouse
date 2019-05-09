package warehouse.interfaces;

import warehouse.models.Book;

import java.util.List;

public interface BookListener {
    void bookListChanged();

    void bookListChanged(List<Book> books);
}
