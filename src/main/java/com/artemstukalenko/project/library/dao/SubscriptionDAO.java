package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.Subscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionDAO {

    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister) throws SQLException;

    public List<Subscription> getAllSubscriptions() throws SQLException;

    public Subscription findSubscriptionById(int id);

    public boolean deleteSubscriptionFromDB(int id);

    public Subscription findSubscriptionByBookId(int bookId);

}
