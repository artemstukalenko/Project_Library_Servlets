package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.entity.Authority;
import com.artemstukalenko.project.library.utility.ConnectionCloser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorityDAOImpl implements AuthorityDAO, ConnectionCloser {

    private DataSource authorityDataSource;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public AuthorityDAOImpl(DataSource authorityDataSource) {
        this.authorityDataSource = authorityDataSource;
    }


    @Override
    public String getUsersAuthority(String username) throws SQLException {
        String realUsersAuthority;

        try {
            connection = authorityDataSource.getConnection();
            String sqlStatement = "select * from authorities where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet != null) {
                resultSet.next();
                realUsersAuthority = resultSet.getString("authority");
            } else {
                throw new SQLException();
            }

            return realUsersAuthority;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean makeUserLibrarian(String username) throws SQLException {

        try {
            connection = authorityDataSource.getConnection();
            String sqlStatement = "update authorities set authority='ROLE_LIBRARIAN' where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean depriveLibrarianPrivileges(String username) throws SQLException {

        try {
            connection = authorityDataSource.getConnection();
            String sqlStatement = "update authorities set authority='ROLE_USER' where username=?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean deleteAuthority(String username) throws SQLException {

        try {
            connection = authorityDataSource.getConnection();
            String sqlStatement = "delete from authorities where username = ?";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, username);

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public boolean addAuthorityToDB(Authority authority) throws SQLException {

        try {
            connection = authorityDataSource.getConnection();
            String sqlStatement = "insert into authorities (username, authority) " +
                    "values (?, ?)";
            statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, authority.getUsername());
            statement.setString(2, authority.getAuthority());

            statement.executeUpdate();
            return true;
        } finally {
            close(connection, statement, resultSet);
        }
    }
}
