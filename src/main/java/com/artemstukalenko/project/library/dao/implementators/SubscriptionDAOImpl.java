package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.entity.Subscription;
import com.artemstukalenko.project.library.utility.ConnectionCloser;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl implements SubscriptionDAO, ConnectionCloser {

    private DataSource subscriptionDataSource;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public SubscriptionDAOImpl(DataSource subscriptionDataSource) {
        this.subscriptionDataSource = subscriptionDataSource;
    }

    @Override
    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister) throws SQLException {

        try {
            connection = subscriptionDataSource.getConnection();
            String insertStatement = "insert into subscriptions " +
                    "(username, book_id, book_title, book_author, start_of_the_period, end_of_the_period, expired, fined) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(insertStatement);
            statement.setString(1, subscriptionToRegister.getUsername());
            statement.setInt(2, subscriptionToRegister.getBookId());
            statement.setString(3, subscriptionToRegister.getTitle());
            statement.setString(4, subscriptionToRegister.getAuthor());
            statement.setObject(5, subscriptionToRegister.getStartOfThePeriod());
            statement.setObject(6, subscriptionToRegister.getEndOfThePeriod());
            statement.setBoolean(7, subscriptionToRegister.getExpired());
            statement.setBoolean(8, subscriptionToRegister.getFined());

            statement.execute();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws SQLException {
        List<Subscription> allSubscriptions = new ArrayList<>();

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "select * from subscriptions";
            statement = connection.prepareStatement(sqlStatement);
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");
                LocalDate startOfThePeriod = resultSet.getDate("start_of_the_period").toLocalDate();
                LocalDate endOfThePeriod = resultSet.getDate("end_of_the_period").toLocalDate();
                boolean expired = resultSet.getBoolean("expired");
                boolean fined = resultSet.getBoolean("fined");

                Subscription tempSubscription = new Subscription(id, username, bookId, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod, expired, fined);

                allSubscriptions.add(tempSubscription);
            }

            return allSubscriptions;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public List<Subscription> getUserSubscriptions(String username) throws SQLException {
        List<Subscription> userSubscriptions = new ArrayList<>();

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "select * from subscriptions where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");
                LocalDate startOfThePeriod = resultSet.getDate("start_of_the_period").toLocalDate();
                LocalDate endOfThePeriod = resultSet.getDate("end_of_the_period").toLocalDate();
                boolean expired = resultSet.getBoolean("expired");
                boolean fined = resultSet.getBoolean("fined");

                Subscription tempSubscription = new Subscription(id, username, bookId, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod, expired, fined);

                userSubscriptions.add(tempSubscription);
            }

            return userSubscriptions;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public Subscription findSubscriptionById(int id) throws SQLException {
        Subscription soughtSubscription;

        try {

            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "select * from subscriptions where id=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String username = resultSet.getString("username");
                int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");
                LocalDate startOfThePeriod = resultSet.getDate("start_of_the_period").toLocalDate();
                LocalDate endOfThePeriod = resultSet.getDate("end_of_the_period").toLocalDate();
                boolean expired = resultSet.getBoolean("expired");
                boolean fined = resultSet.getBoolean("fined");

                soughtSubscription = new Subscription(id, username, bookId, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod, expired, fined);
            } else {
                throw new SQLException();
            }

            return soughtSubscription;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean deleteSubscriptionFromDB(int id) throws SQLException {

        try {
            connection = subscriptionDataSource.getConnection();
            String deletionStatement = "delete from subscriptions where id=?";
            statement = connection.prepareStatement(deletionStatement);
            statement.setInt(1, id);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean deleteUsersSubscriptions(String username) throws SQLException {

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "delete from subscriptions where username = ?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public Subscription findSubscriptionByBookId(int bookId) throws SQLException {
        Subscription soughtSubscription;

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "select * from subscriptions where book_id = ?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, bookId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int subscriptionId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                int bookIdSubscription = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");
                LocalDate startOfThePeriod = resultSet.getDate("start_of_the_period").toLocalDate();
                LocalDate endOfThePeriod = resultSet.getDate("end_of_the_period").toLocalDate();
                boolean expired = resultSet.getBoolean("expired");
                boolean fined = resultSet.getBoolean("fined");

                soughtSubscription = new Subscription(subscriptionId, username, bookIdSubscription, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod, expired, fined);
            } else {
                throw new SQLException();
            }

            return soughtSubscription;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean updateFinedInfo(int subscriptionId, boolean fined) throws SQLException {

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "update subscriptions set fined = ? where id = ?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setBoolean(1, fined);
            statement.setInt(2, subscriptionId);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }
}
