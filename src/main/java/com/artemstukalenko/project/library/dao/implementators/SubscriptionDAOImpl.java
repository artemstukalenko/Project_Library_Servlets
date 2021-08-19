package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.entity.Subscription;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private DataSource subscriptionDataSource;

    public SubscriptionDAOImpl(DataSource subscriptionDataSource) {
        this.subscriptionDataSource = subscriptionDataSource;
    }

    @Override
    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

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
            close(connection, statement, null);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws SQLException {
        List<Subscription> allSubscriptions = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = subscriptionDataSource.getConnection();
            String sqlStatement = "select * from subscriptions";
            statement = connection.createStatement();
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
    public Subscription findSubscriptionById(int id) {
        return null;
    }

    @Override
    public boolean deleteSubscriptionFromDB(int id) {
        return false;
    }

    @Override
    public Subscription findSubscriptionByBookId(int bookId) {
        return null;
    }

    private void close(Connection findUserConnection,
                       Statement findUserStatement, ResultSet findUserResultSet) {
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
