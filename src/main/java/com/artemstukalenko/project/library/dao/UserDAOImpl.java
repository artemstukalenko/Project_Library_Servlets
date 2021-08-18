package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.User;
import com.mysql.cj.conf.ConnectionPropertiesTransform;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private DataSource userDataSource;

    public UserDAOImpl(DataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean blockUser(String username) {
        return false;
    }

    @Override
    public boolean unblockUser(String username) {
        return false;
    }

    @Override
    public String getUserRole(String username) {
        return null;
    }

    @Override
    public boolean registerUser(User user) throws SQLIntegrityConstraintViolationException {
        return false;
    }

    @Override
    public User findUserByUsername(String username) throws SQLException {
        User soughtUser = null;

        Connection findUserConnection = null;
        PreparedStatement findUserStatement = null;
        ResultSet findUserResultSet = null;

        try {
            findUserConnection = userDataSource.getConnection();
            System.out.println("CONNECTION DONE");
            String sqlStatement = "select * from users where username=?";
            findUserStatement = findUserConnection.prepareStatement(sqlStatement);
            findUserStatement.setString(1, username);
            System.out.println("EXECUTING QUERY");
            findUserResultSet = findUserStatement.executeQuery();
            System.out.println("QUERY EXECUTED: " + findUserResultSet);

            if(findUserResultSet.next()) {
                String foundUsername = findUserResultSet.getString("username");
                String foundPassword = findUserResultSet.getString("password");
                int foundEnabledStatus = findUserResultSet.getInt("enabled");

                soughtUser = new User(foundUsername, foundPassword, foundEnabledStatus);
            } else {
                throw new SQLException();
            }

            return soughtUser;
        } finally {
            close(findUserConnection, findUserStatement, findUserResultSet);
        }
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean makeUserLibrarian(String username) {
        return false;
    }

    @Override
    public boolean depriveLibrarianPrivileges(String username) {
        return false;
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
