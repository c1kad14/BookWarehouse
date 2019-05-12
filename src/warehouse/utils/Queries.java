package warehouse.utils;

/**
 * Class that contains string queries to the SQLite Database
 */
public class Queries {
    public static final String SELECT_BOOKS = "SELECT Books.Id as IdBook, Books.Title, Books.Description, Books.Path, " +
            "Authors.Id as IdAuthor, Authors.FirstName, Authors.LastName, Genres.Id as IdGenre, " +
            "Genres.Name AS Genre FROM Books INNER JOIN Authors ON Books.AuthorId = Authors.Id "
            + "INNER JOIN Genres ON Books.GenreId = Genres.Id";

    public static final String SELECT_BOOKS_FOR_SEARCH = "SELECT Books.Id as IdBook, Books.Title, Books.Description, Books.Path, " +
            "Authors.Id as IdAuthor, Authors.FirstName, Authors.LastName, Genres.Id as IdGenre, " +
            "Genres.Name AS Genre FROM Books INNER JOIN Authors ON Books.AuthorId = Authors.Id "
            + "INNER JOIN Genres ON Books.GenreId = Genres.Id WHERE Books.Title LIKE '%%%s%%' OR Authors.FirstName LIKE '%%%s%%' OR Authors.LastName LIKE '%%%s%%'";

    public static final String SEARCH_CLAUSE = "Books.Title LIKE '%%%s%%' OR Authors.FirstName LIKE '%%%s%%' OR Authors.LastName LIKE '%%%s%%'";
    public static final String GENRES_ID_CLAUSE = "Genres.Id = %s";
    public static final String AUTHOR_ID_CLAUSE = "Authors.Id = %s";
    public static final String WHERE_CLAUSE = " WHERE ";
    public static final String OR_CLAUSE = " OR ";
    public static final String AND_CLAUSE = " AND ";

    public static final String SELECT_AUTHORS = "SELECT * FROM Authors;";
    public static final String SELECT_GENRES = "SELECT * FROM Genres;";

    public static final String INSERT_AUTHOR = "INSERT INTO Authors (FirstName, LastName) VALUES ('%s', '%s');";
    public static final String INSERT_GENRE = "INSERT INTO Genres (Name) VALUES ('%s');";
    public static final String INSERT_BOOK = "INSERT INTO Books (Title, Description, Path, AuthorId, GenreId) VALUES ('%s', '%s', '%s', %s, %s);";
}
