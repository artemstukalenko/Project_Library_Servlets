package com.artemstukalenko.project.library.utility;

import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.dao.implementators.SubscriptionDAOImpl;
import com.artemstukalenko.project.library.entity.Subscription;
import com.artemstukalenko.project.library.entity.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenaltyCalculator {

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    private SubscriptionDAO subscriptionDAO;

    public PenaltyCalculator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int calculateUsersPenalty(String username) {
        System.out.println("GONNA CALCULATE PENALTY " + username);
        List<Subscription> userSubscriptions = new ArrayList<>();

        System.out.println("DATA SOURCE: " + dataSource);

        try {
            subscriptionDAO = new SubscriptionDAOImpl(dataSource);
            System.out.println("DAO: " + subscriptionDAO);
            userSubscriptions = subscriptionDAO.getUserSubscriptions(username);
            System.out.println("USER SUBSCRIPTION LIST: " + userSubscriptions);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (int)(userSubscriptions.stream()
                .filter(subscription -> {return subscription.getExpired() && !subscription.getFined();})
                .peek(subscription -> {subscription.setFined(true);})
                .count() * 10);
    }

}
