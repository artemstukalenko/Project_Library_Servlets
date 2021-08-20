package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.CustomRequest;

import java.sql.SQLException;
import java.util.List;

public interface CustomRequestDAO {

    public boolean addCustomRequestToDB(CustomRequest request) throws SQLException;

    public boolean deleteCustomSubscriptionRequestFromDB(int id) throws SQLException;

    public boolean deleteUsersCustomRequests(String username) throws SQLException;

    public List<CustomRequest> getAllRequests() throws SQLException;

    public CustomRequest findRequestById(int id) throws SQLException;

}
