package warehouse.constants;

/**
 * Class that contains string queries to the SQLite Database
 */
public class Queries {
    public static final String SELECT_BOOKS = "SELECT Books.Id as IdBook, Books.Title, Books.Notes, Books.Path, Books.Year, Books.Publisher, " +
            "Authors.Id as IdAuthor, Authors.FirstName, Authors.LastName, Types.Id as IdType, " +
            "Types.Name AS Type FROM Books INNER JOIN Authors ON Books.AuthorId = Authors.Id "
            + "INNER JOIN Types ON Books.TypeId = Types.Id";

    public static final String SEARCH_CLAUSE = "Books.Title LIKE '%%%s%%' OR Books.Notes LIKE '%%%s%%' OR Authors.FirstName LIKE '%%%s%%' OR Authors.LastName LIKE '%%%s%%'";
    public static final String TYPES_ID_CLAUSE = "Types.Id = %s";
    public static final String AUTHOR_ID_CLAUSE = "Authors.Id = %s";
    public static final String WHERE_CLAUSE = " WHERE ";
    public static final String OR_CLAUSE = " OR ";
    public static final String AND_CLAUSE = " AND ";

    public static final String SELECT_AUTHORS = "SELECT * FROM Authors;";
    public static final String SELECT_TYPES = "SELECT * FROM Types;";

    public static final String INSERT_AUTHOR = "INSERT INTO Authors (FirstName, LastName) VALUES ('%s', '%s');";
    public static final String INSERT_TYPE = "INSERT INTO Types (Name) VALUES ('%s');";
    public static final String INSERT_BOOK = "INSERT INTO Books (Title, Notes, Path, Year, Publisher, AuthorId, TypeId) VALUES ('%s', '%s', '%s', '%s', '%s', %s, %s);";

    public static final String UPDATE_AUTHOR = "UPDATE Authors SET FirstName = '%s', LastName = '%s' WHERE Id = %s;";
    public static final String UPDATE_TYPE = "UPDATE Types SET Name = '%s' WHERE Id = %s;";
    public static final String UPDATE_BOOK = "UPDATE Books SET Title = '%s', Notes = '%s', Path = '%s', Year = '%s', Publisher = '%s', AuthorId = %s, TypeId = %s WHERE Id = %s;";


    public static final String DELETE_AUTHOR = "DELETE FROM Authors WHERE Id = %s;";
    public static final String DELETE_TYPE = "DELETE FROM Types WHERE Id = %s;";
    public static final String DELETE_BOOK = "DELETE FROM Books WHERE Id = %s;";


}
