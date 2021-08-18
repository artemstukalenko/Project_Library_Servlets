package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.UserDetails;

import java.sql.SQLException;

public interface UserDetailsDAO {
    public UserDetails getDetailsByUsername(String username) throws SQLException;
}

