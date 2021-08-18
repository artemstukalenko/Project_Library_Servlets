package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.User;

import javax.sql.DataSource;
import java.sql.SQLIntegrityConstraintViolationException;
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
    public User findUserByUsername(String username) {
        return null;
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
}
