package warehouse.data;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.sqlite.SQLiteConfig;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Type;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static warehouse.constants.Queries.*;
import static warehouse.constants.StringConstants.*;

/**
 * Class responsible for database connection and retrieving records
 */
public class SQLiteClient {
    Connection connection = null;
    Statement stmt = null;

    public SQLiteClient() {
        try {
            Class.forName(SQLITE_JDBC);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public List<Author> getAuthors() {
        ResultSet rs;
        List<Author> authors = new ArrayList<>();
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_AUTHORS);
            while (rs.next()) {
                authors.add(new Author(rs.getInt(ID_FIELD), rs.getString(FNAME_FIELD), rs.getString(LNAME_FIELD)));
            }

            //release resources
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return authors;
    }

    public List<Type> getTypes() {
        ResultSet rs;
        List<Type> types = new ArrayList<>();
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_TYPES);

            while (rs.next()) {
                types.add(new Type(rs.getInt(ID_FIELD), rs.getString(NAME_FIELD)));
            }

            //release resources
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return types;
    }

    public List<Book> getBooks(String searchValue, List<Type> types, List<Author> authors) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_BOOKS);

        //generate query
        if (types.size() > 0 || authors.size() > 0 || !searchValue.isEmpty()) {
            stringBuilder.append(WHERE_CLAUSE);

            if (!searchValue.isEmpty()) {
                stringBuilder.append("(");
                stringBuilder.append(String.format(SEARCH_CLAUSE, searchValue, searchValue, searchValue, searchValue));
                stringBuilder.append(")");
            }

            if (types.size() > 0 || authors.size() > 0) {
                if (!searchValue.isEmpty()) {
                    stringBuilder.append(AND_CLAUSE);
                }
                stringBuilder.append("(");

                for (int i = 0; i < types.size(); i++) {
                    stringBuilder.append(String.format(TYPES_ID_CLAUSE, types.get(i).getId()));

                    if (i != types.size() - 1) {
                        stringBuilder.append(OR_CLAUSE);
                    }
                }

                if (types.size() > 0 && authors.size() > 0) {
                    stringBuilder.append(")");
                    stringBuilder.append(AND_CLAUSE);
                    stringBuilder.append("(");
                }

                for (int i = 0; i < authors.size(); i++) {
                    stringBuilder.append(String.format(AUTHOR_ID_CLAUSE, authors.get(i).getId()));

                    if (i != authors.size() - 1) {
                        stringBuilder.append(OR_CLAUSE);
                    }
                }
                stringBuilder.append(")");
            }

        }

        ResultSet rs;
        List<Book> books = new ArrayList<>();
        try {
            //connect and create statement
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(stringBuilder.toString());
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(ID_BOOK_FIELD));
                book.setTitle(rs.getString(TITLE_FIELD));
                book.setNotes(rs.getString(NOTES_FIELD));
                book.setPath(rs.getString(PATH_FIELD));
                book.setYear(rs.getString(YEAR_FIELD));
                book.setPublisher(rs.getString(PUBLISHER_FIELD));
                book.setAuthor(new Author(rs.getInt(ID_AUTHOR_FIELD), rs.getString(FNAME_FIELD), rs.getString(LNAME_FIELD)));
                book.setType(new Type(rs.getInt(ID_TYPE_FIELD), rs.getString(TYPE_FIELD)));
                books.add(book);
            }

            //release resources
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return books;

    }

    public Author addAuthor(Author author) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_AUTHOR,
                    author.getFirstName(), author.getLastName()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return author;
    }

    public Type addType(Type type) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_TYPE, type.getName()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return type;
    }

    public Book addBook(Book book) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_BOOK,
                    book.getTitle(), book.getNotes(), book.getPath(), book.getYear(), book.getPublisher(),
                    book.getAuthor().getId(), book.getType().getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return book;
    }

    public boolean deleteType(Type type) throws MalformedURLException {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(DELETE_TYPE,
                    type.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            openNotification();
            return false;
        }

        return true;
    }

    public Type updateType(Type type) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(UPDATE_TYPE,
                    type.getName(), type.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return type;
    }

    public boolean deleteAuthor(Author author) throws MalformedURLException {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(DELETE_AUTHOR,
                    author.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            openNotification();
            return false;
        }

        return true;
    }

    public Author updateAuthor(Author author) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(UPDATE_AUTHOR,
                    author.getFirstName(), author.getLastName(), author.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return author;
    }

    public Book updateBook(Book book) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(UPDATE_BOOK, book.getTitle(), book.getNotes(), book.getPath(),
                    book.getYear(), book.getPublisher(), book.getAuthor().getId(), book.getType().getId(), book.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return book;
    }

    public boolean deleteBook(Book book) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(DELETE_BOOK,
                    book.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    private void openNotification() throws MalformedURLException {
        FXMLLoader notificationDialogLoader = new FXMLLoader(getClass().getResource("../ui/notificationDialog.fxml"));
        Parent notificationDialog = null;
        try {
            notificationDialog = notificationDialogLoader.load();
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        Scene scene = new Scene(notificationDialog, 375, 121);
        Stage stage = new Stage();

        stage.setTitle("Attention");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "//logo.png").toURI().toURL().toString(), false));
        stage.setScene(scene);
        stage.showAndWait();

    }
}
