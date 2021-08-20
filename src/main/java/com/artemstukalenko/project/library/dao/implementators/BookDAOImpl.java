package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.entity.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private DataSource bookDataSource;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public BookDAOImpl(DataSource bookDataSource) {
        this.bookDataSource = bookDataSource;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> allBooks = new ArrayList<>();

        try {
            connection = bookDataSource.getConnection();
            String sqlStatement = "select * from books";
            statement = connection.prepareStatement(sqlStatement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String yearOfPublishing = resultSet.getString("year_of_publishing");
                boolean taken = resultSet.getBoolean("taken");

                Book tempBook = new Book(id, title, author, yearOfPublishing, taken);

                allBooks.add(tempBook);
            }

            return allBooks;
        } finally {
            close(connection, statement, resultSet);
        }

    }

    @Override
    public boolean deleteBook(int bookId) throws SQLException {

        try {
            connection = bookDataSource.getConnection();
            String deletionStatement = "delete from books where id = ?";
            statement = connection.prepareStatement(deletionStatement);
            statement.setInt(1, bookId);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean addNewBook(Book bookToAdd) throws SQLException {

        try {
            connection = bookDataSource.getConnection();
            String addNewBookStatement = "insert into books (title, author, year_of_publishing, taken) "
                    + "values (?, ?, ?, ?)";
            statement = connection.prepareStatement(addNewBookStatement);
            statement.setString(1, bookToAdd.getBookTitle());
            statement.setString(2, bookToAdd.getBookAuthor());
            statement.setString(3, bookToAdd.getBookYearOfPublishing());
            statement.setBoolean(4, false);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public Book findBookById(int bookId) throws SQLException {
        Book soughtBook;

        try {

            connection = bookDataSource.getConnection();
            String sqlStatement = "select * from books where id=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, bookId);

            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String yearOfPublishing = resultSet.getString("year_of_publishing");
                boolean taken = resultSet.getBoolean("taken");

                soughtBook = new Book(id, title, author, yearOfPublishing, taken);
            } else {
                throw new SQLException();
            }

            return soughtBook;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean setTaken(int id, boolean taken) throws SQLException {

        try {
            connection = bookDataSource.getConnection();
            String updateStatement = "update books set taken = ? where id = ?";
            statement = connection.prepareStatement(updateStatement);
            statement.setBoolean(1, taken);
            statement.setInt(2, id);

            statement.executeUpdate();
            return taken;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    private void close(Connection findUserConnection,
                       PreparedStatement findUserStatement, ResultSet findUserResultSet) {
        try {
            if (findUserConnection != null) {
                findUserConnection.close();
            }
            if (findUserStatement != null) {
                findUserStatement.close();
            }
            if (findUserResultSet != null) {
                findUserResultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
