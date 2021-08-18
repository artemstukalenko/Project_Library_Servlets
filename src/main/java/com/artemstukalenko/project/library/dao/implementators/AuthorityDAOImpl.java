package com.artemstukalenko.project.library.dao.implementators;

import com.artemstukalenko.project.library.dao.AuthorityDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorityDAOImpl implements AuthorityDAO {

    private DataSource authorityDataSource;

    public AuthorityDAOImpl(DataSource authorityDataSource) {
        this.authorityDataSource = authorityDataSource;
    }


    @Override
    public String getUsersAuthority(String username) throws SQLException {
        String realUsersAuthority = null;

        Connection findAuthorityConnection = null;
        PreparedStatement findAuthorityStatement = null;
        ResultSet findAuthorityResultSet = null;

        try {
            findAuthorityConnection = authorityDataSource.getConnection();
            String sqlStatement = "select * from authorities where username=?";
            findAuthorityStatement = findAuthorityConnection.prepareStatement(sqlStatement);
            findAuthorityStatement.setString(1, username);
            findAuthorityResultSet = findAuthorityStatement.executeQuery();

            if (findAuthorityResultSet != null) {
                findAuthorityResultSet.next();
                realUsersAuthority = findAuthorityResultSet.getString("authority");
            } else {
                throw new SQLException();
            }

            return realUsersAuthority;
        } finally {
            close(findAuthorityConnection, findAuthorityStatement, findAuthorityResultSet);
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
