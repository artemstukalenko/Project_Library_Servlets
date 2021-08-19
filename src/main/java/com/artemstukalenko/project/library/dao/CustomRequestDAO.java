package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.CustomRequest;

import java.sql.SQLException;
import java.util.List;

public interface CustomRequestDAO {

    public boolean addCustomRequestToDB(CustomRequest request) throws SQLException;

    public boolean deleteCustomSubscriptionRequestFromDB(int id);

    public List<CustomRequest> getAllRequests() throws SQLException;

    public CustomRequest findRequestById(int id);

}
