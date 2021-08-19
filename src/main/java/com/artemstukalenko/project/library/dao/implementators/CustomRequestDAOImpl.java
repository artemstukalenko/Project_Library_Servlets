package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.CustomRequestDAO;
import com.artemstukalenko.project.library.entity.CustomRequest;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomRequestDAOImpl implements CustomRequestDAO {

    private DataSource dataSource;

    public CustomRequestDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean addCustomRequestToDB(CustomRequest request) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            String sqlStatement = "insert into custom_subscription_requests (username, book_id, book_title, " +
                    "book_author, start_of_the_period, end_of_the_period) "
                    + "values (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, request.getUsername());
            statement.setInt(2, request.getBookId());
            statement.setString(3, request.getTitle());
            statement.setString(4, request.getAuthor());
            statement.setObject(5, request.getStartOfThePeriod());
            statement.setObject(6, request.getEndOfThePeriod());

            statement.execute();
            return true;
        } finally {
            close(connection, statement, null);
        }
    }

    @Override
    public boolean deleteCustomSubscriptionRequestFromDB(int id) {
        return false;
    }

    @Override
    public List<CustomRequest> getAllRequests() throws SQLException {
        List<CustomRequest> allRequests = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sqlStatement = "select * from custom_subscription_requests";
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

                CustomRequest tempRequest = new CustomRequest(id, username, bookId, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod);

                allRequests.add(tempRequest);
            }

            return allRequests;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public CustomRequest findRequestById(int id) throws SQLException {
        CustomRequest soughtRequest;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sqlStatement = "select * from custom_subscription_requests where id = ?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");
                LocalDate startOfThePeriod = resultSet.getDate("start_of_the_period").toLocalDate();
                LocalDate endOfThePeriod = resultSet.getDate("end_of_the_period").toLocalDate();

                soughtRequest = new CustomRequest(username, bookId, bookTitle, bookAuthor,
                        startOfThePeriod, endOfThePeriod);
            } else {
                throw new SQLException();
            }
            return soughtRequest;
        } finally {
            close(connection, statement, resultSet);
        }
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
