package warehouse.utils;

/**
 * Class that contains string queries to the SQLite Database
 */
public class Queries {
    public static String SELECT_BOOKS = "SELECT Books.Id as IdBook, Books.Title, Books.Description, Books.Path, Authors.Id as IdAuthor, Authors.FirstName, Authors.LastName, Genres.Id as IdGenre, Genres.Name AS Genre FROM Books INNER JOIN Authors ON Books.AuthorId = Authors.Id "
            + "INNER JOIN Genres ON Books.GenreId = Genres.Id";
    public static final String SELECT_AUTHORS = "SELECT * FROM Authors;";
    public static final String SELECT_GENRES = "SELECT * FROM Genres;";

    public static final String INSERT_AUTHOR = "INSERT INTO Authors (FirstName, LastName) VALUES ('%s', '%s');";
    public static final String INSERT_GENRE = "INSERT INTO Genres (Name) VALUES ('%s');";
    public static final String INSERT_BOOK = "INSERT INTO Books (Title, Description, Path, AuthorId, GenreId) VALUES ('%s', '%s', '%s', %s, %s);";
}
