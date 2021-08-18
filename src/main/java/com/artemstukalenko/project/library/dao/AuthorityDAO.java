package com.artemstukalenko.project.library.dao;

import java.sql.SQLException;

public interface AuthorityDAO {
    public String getUsersAuthority(String username) throws SQLException;

    public boolean makeUserLibrarian(String username) throws SQLException;

    public boolean depriveLibrarianPrivileges(String username) throws SQLException;
}
