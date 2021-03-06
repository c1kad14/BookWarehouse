package warehouse.constants;

/**
 * Class that contains string constants
 */
public class StringConstants {
    public static final String SQLITE_JDBC = "org.sqlite.JDBC";
    public static final String SQLITE_JDBC_DB = "jdbc:sqlite:BookLibrary.db";

    public static final String ID_FIELD = "Id";
    public static final String ID_BOOK_FIELD = "IdBook";
    public static final String ID_AUTHOR_FIELD = "IdAuthor";
    public static final String ID_TYPE_FIELD = "IdType";
    public static final String FNAME_FIELD = "FirstName";
    public static final String LNAME_FIELD = "LastName";
    public static final String NAME_FIELD = "Name";
    public static final String EDIT_FIELD = "Edit";
    public static final String DELETE_FIELD = "Delete";
    public static final String TITLE_FIELD = "Title";
    public static final String YEAR_FIELD = "Year";
    public static final String PUBLISHER_FIELD = "Publisher";
    public static final String AUTHOR_FIELD = "Author";
    public static final String TYPE_FIELD = "Type";
    public static final String NOTES_FIELD = "Notes";
    public static final String PATH_FIELD = "Path";
    public static final String EMPTY_STRING = "";

    public static final String SELECT_FILE_LABEL_TEXT = "No file selected";
    public static final String BOOK_WAREHOUSE_FOLDER = System.getProperty("user.dir") + "//books";
}
