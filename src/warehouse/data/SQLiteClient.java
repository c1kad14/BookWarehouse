package warehouse.data;

import warehouse.models.Author;
import warehouse.models.Book;
import warehouse.models.Genre;

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
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_AUTHORS);
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt(ID_FIELD));
                author.setFirstName(rs.getString(FNAME_FIELD));
                author.setFirstName(rs.getString(LNAME_FIELD));
                authors.add(author);
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
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_GENRES);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt(ID_FIELD));
                genre.setName(rs.getString(NAME_FIELD));
                genres.add(genre);
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

    public List<Book> getBooks() {
        ResultSet rs;
        List<Book> books = new ArrayList<>();
        try {
            //connect and create statement
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_BOOKS);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(ID_FIELD));
                book.setTitle(rs.getString(TITLE_FIELD));
                book.setDescription(rs.getString(DESCRIPTION_FIELD));
                book.setPath(rs.getString(PATH_FIELD));

                Author author = new Author();
                author.setId(rs.getInt(ID_FIELD));
                author.setFirstName(rs.getString(FNAME_FIELD));
                author.setLastName(rs.getString(LNAME_FIELD));

                Genre genre = new Genre();
                genre.setId(rs.getInt(ID_FIELD));
                genre.setName(rs.getString(GENRE_FIELD));

                book.setAuthor(author);
                book.setGenre(genre);
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
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_AUTHOR,
                    author.getFirstName(), author.getLastName()));

            //release resources
            stmt.close();
            connection.commit();
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
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_GENRE, genre.getName()));

            //release resources
            stmt.close();
            connection.commit();
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
            connection = DriverManager.getConnection(SQLITE_JDBC_DB);
            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(INSERT_BOOK,
                    book.getTitle(), book.getDescription(), book.getPath(), book.getAuthor().getId(), book.getGenre().getId()));

            //release resources
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return book;
    }
}
