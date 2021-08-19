package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.entity.Book;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private DataSource bookDataSource;

    public BookDAOImpl(DataSource bookDataSource) {
        this.bookDataSource = bookDataSource;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> allBooks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

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
    public boolean deleteBook(int bookId) {
        return false;
    }

    @Override
    public boolean addNewBook(Book bookToAdd) {
        return false;
    }

    @Override
    public Book findBookById(int bookId) throws SQLException {
        System.out.println("TRYING TO FIND A BOOK WITH ID = " + bookId);
        Book soughtBook = null;

        Connection myConnection = null;
        PreparedStatement myStatement = null;
        ResultSet resultSet = null;

        try {

            myConnection = bookDataSource.getConnection();
            String sqlStatement = "select * from books where id=?";
            myStatement = myConnection.prepareStatement(sqlStatement);
            myStatement.setInt(1, bookId);
            System.out.println("ABOUT TO EXECUTE QUERY");
            resultSet = myStatement.executeQuery();
            System.out.println("RESULT SET: " + resultSet);
            if(resultSet.next()) {
                System.out.println("RESULT SET IS READY");
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String yearOfPublishing = resultSet.getString("year_of_publishing");
                boolean taken = resultSet.getBoolean("taken");

                soughtBook = new Book(id, title, author, yearOfPublishing, taken);
                System.out.println("BOOK OBJECT MADE: " + soughtBook);
            } else {
                throw new SQLException();
            }

            return soughtBook;
        } finally {
            close(myConnection, myStatement, resultSet);
        }
    }

    @Override
    public boolean setTaken(int id, boolean taken) {
        return false;
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
