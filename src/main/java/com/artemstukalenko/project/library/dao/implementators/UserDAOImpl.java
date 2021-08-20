package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.UserDAO;
import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.entity.User;
import com.mysql.cj.conf.ConnectionPropertiesTransform;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private DataSource userDataSource;

    private UserDetailsDAO userDetailsDAO;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public UserDAOImpl(DataSource userDataSource) {
        this.userDataSource = userDataSource;
        this.userDetailsDAO = new UserDetailsDAOImpl(userDataSource);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "select * from users";
            statement = connection.prepareStatement(sqlStatement);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int enabled = resultSet.getInt("enabled");

                User tempUser = new User(username, enabled);
                tempUser.setUserDetails(userDetailsDAO.getDetailsByUsername(username));
                allUsers.add(tempUser);
            }

            return allUsers;
        } finally {
            close(connection, statement, resultSet);
        }

    }

    @Override
    public boolean blockUser(String username) throws SQLException {

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "update users set enabled=0 where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);
            statement.executeUpdate();

            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean unblockUser(String username) throws SQLException {

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "update users set enabled=1 where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);
            statement.executeUpdate();

            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public String getUserRole(String username) {
        return null;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "insert into users (username, password, enabled) " +
                    "values (?, ?, ?)";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, ("{noop}" + user.getPassword()));
            statement.setInt(3, user.getEnabled());

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public User findUserByUsername(String username) throws SQLException {
        User soughtUser;

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "select * from users where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String foundPassword = resultSet.getString("password");
                int foundEnabledStatus = resultSet.getInt("enabled");

                soughtUser = new User(foundUsername, foundPassword, foundEnabledStatus);
                soughtUser.setUserDetails(userDetailsDAO.getDetailsByUsername(username));
            } else {
                throw new SQLException();
            }

            return soughtUser;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) throws SQLException {

        try {
            connection = userDataSource.getConnection();
            String sqlStatement = "delete from users where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public void updatePenaltyInfo(String username, int updateSum) {

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
