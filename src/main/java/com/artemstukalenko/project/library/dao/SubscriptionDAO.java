package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.Subscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionDAO {

    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister) throws SQLException;

    public List<Subscription> getAllSubscriptions() throws SQLException;

    public Subscription findSubscriptionById(int id) throws SQLException;

    public boolean deleteSubscriptionFromDB(int id) throws SQLException;

    boolean deleteUsersSubscriptions(String username) throws SQLException;

    public Subscription findSubscriptionByBookId(int bookId) throws SQLException;

    public List<Subscription> getUserSubscriptions(String username) throws SQLException;

    public boolean updateFinedInfo(int subscriptionId, boolean fined) throws SQLException;

}
