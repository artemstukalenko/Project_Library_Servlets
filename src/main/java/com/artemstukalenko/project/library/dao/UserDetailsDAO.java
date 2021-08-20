package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.UserDetails;

import java.sql.SQLException;
import java.util.List;

public interface UserDetailsDAO {
    public UserDetails getDetailsByUsername(String username) throws SQLException;

    public List<UserDetails> getAllDetails() throws SQLException;

    public boolean updateAuthorityInfo(String username, String authorityString) throws SQLException;

    public boolean deleteUserDetails(String username) throws SQLException;

    public boolean registerUserDetails(UserDetails newUserDetails) throws SQLException;
}

