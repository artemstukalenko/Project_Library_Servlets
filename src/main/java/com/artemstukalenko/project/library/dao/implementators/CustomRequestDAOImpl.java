package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.CustomRequestDAO;
import com.artemstukalenko.project.library.entity.CustomRequest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomRequestDAOImpl implements CustomRequestDAO {

    private DataSource dataSource;

    public CustomRequestDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean addCustomRequestToDB(CustomRequest request) {
        return false;
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
    public CustomRequest findRequestById(int id) {
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
