package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.entity.UserDetails;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDetailsDAOImpl implements UserDetailsDAO {

    private DataSource userDetailsDataSource;

    public UserDetailsDAOImpl(DataSource userDetailsDataSource) {
        this.userDetailsDataSource = userDetailsDataSource;
    }

    @Override
    public List<UserDetails> getAllDetails() throws SQLException {
        List<UserDetails> allUserInfo = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = userDetailsDataSource.getConnection();
            String sqlStatement = "select * from user_details order by username";
            statement = connection.prepareStatement(sqlStatement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                int penalty = resultSet.getInt("penalty");
                String authorityString = resultSet.getString("authority_string");

                allUserInfo.add(new UserDetails(foundUsername, firstName, lastName, email, phoneNumber,
                        address, penalty, authorityString));
            }
            return allUserInfo;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public UserDetails getDetailsByUsername(String username) throws SQLException {

        UserDetails soughtDetails = null;

        Connection connectionForDetailsSearch = null;
        PreparedStatement statementForDetailsSearch = null;
        ResultSet resultSet = null;

        try {
            connectionForDetailsSearch = userDetailsDataSource.getConnection();
            String sqlStatement = "select * from user_details where username=?";
            statementForDetailsSearch = connectionForDetailsSearch.prepareStatement(sqlStatement);
            statementForDetailsSearch.setString(1, username);
            resultSet = statementForDetailsSearch.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                int penalty = resultSet.getInt("penalty");
                String authorityString = resultSet.getString("authority_string");

                soughtDetails = new UserDetails(foundUsername, firstName, lastName, email, phoneNumber,
                        address, penalty, authorityString);

            } else {
                throw new SQLException();
            }

            return soughtDetails;
        } finally {
            close(connectionForDetailsSearch, statementForDetailsSearch, resultSet);
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
