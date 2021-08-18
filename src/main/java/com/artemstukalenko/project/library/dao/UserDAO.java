package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.User;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();

    public boolean blockUser(String username);

    public boolean unblockUser(String username);

    public String getUserRole(String username);

    public boolean registerUser(User user) throws SQLIntegrityConstraintViolationException;

    public User findUserByUsername(String username) throws SQLException;

    public boolean updateUser(User user);

    public boolean deleteUser(String username);

    public boolean makeUserLibrarian(String username);

    public boolean depriveLibrarianPrivileges(String username);

    public void updatePenaltyInfo(String username, int updateSum);
}
