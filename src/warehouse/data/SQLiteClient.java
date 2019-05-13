package warehouse.data;

import com.sun.org.apache.xpath.internal.operations.And;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.sqlite.SQLiteConfig;
import warehouse.controllers.EditGenreDialogController;
import warehouse.controllers.NotificationDialogController;
import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Genre;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static warehouse.utils.Queries.*;
import static warehouse.utils.StringConstants.*;

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

    public List<Genre> getGenres() {
        ResultSet rs;
        List<Genre> genres = new ArrayList<>();
        try {

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_GENRES);

            while (rs.next()) {
                genres.add(new Genre(rs.getInt(ID_FIELD), rs.getString(NAME_FIELD)));
            }

            //release resources
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return genres;
    }

    public List<Book> getBooks(String searchValue, List<Genre> genres, List<Author> authors) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SELECT_BOOKS);

        if (genres.size() > 0 || authors.size() > 0 || !searchValue.isEmpty()) {
            stringBuilder.append(WHERE_CLAUSE);

            if (!searchValue.isEmpty()) {
                stringBuilder.append("(");
                stringBuilder.append(String.format(SEARCH_CLAUSE, searchValue, searchValue, searchValue));
                stringBuilder.append(")");
            }

            if (genres.size() > 0 || authors.size() > 0) {
                if (!searchValue.isEmpty()) {
                    stringBuilder.append(AND_CLAUSE);
                }
                stringBuilder.append("(");

                for (int i = 0; i < genres.size(); i++) {
                    stringBuilder.append(String.format(GENRES_ID_CLAUSE, genres.get(i).getId()));

                    if (i != genres.size() - 1) {
                        stringBuilder.append(OR_CLAUSE);
                    }
                }

                if (genres.size() > 0 && authors.size() > 0) {
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
                book.setDescription(rs.getString(DESCRIPTION_FIELD));
                book.setPath(rs.getString(PATH_FIELD));
                book.setAuthor(new Author(rs.getInt(ID_AUTHOR_FIELD), rs.getString(FNAME_FIELD), rs.getString(LNAME_FIELD)));
                book.setGenre(new Genre(rs.getInt(ID_GENRE_FIELD), rs.getString(GENRE_FIELD)));
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

    public Genre addGenre(Genre genre) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_GENRE, genre.getName()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return genre;
    }

    public Book addBook(Book book) {
        try {
            //connect and create statement
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_BOOK,
                    book.getTitle(), book.getDescription(), book.getPath(), book.getAuthor().getId(), book.getGenre().getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return book;
    }

    public boolean deleteGenre(Genre genre) {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(DELETE_GENRE,
                    genre.getId()));

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

    public Genre updateGenre(Genre genre) {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(UPDATE_GENRE,
                    genre.getName(), genre.getId()));

            //release resources
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return genre;
    }

    public boolean deleteAuthor(Author author) {
        try {
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

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(SQLITE_JDBC_DB, config.toProperties());

            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(UPDATE_BOOK, book.getTitle(), book.getDescription(), book.getPath(),
                    book.getAuthor().getId(), book.getGenre().getId(), book.getId()));

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

    private void openNotification() {
        FXMLLoader notificationDialogLoader = new FXMLLoader(getClass().getResource("../ui/notificationDialog.fxml"));
        Parent notificationDialog = null;
        try {
            notificationDialog = notificationDialogLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(notificationDialog, 375, 121);
        Stage stage = new Stage();

        stage.setTitle("Attention");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
