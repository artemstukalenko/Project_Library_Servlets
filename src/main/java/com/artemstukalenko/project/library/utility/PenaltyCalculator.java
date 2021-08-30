package com.artemstukalenko.project.library.utility;

import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.dao.implementators.SubscriptionDAOImpl;
import com.artemstukalenko.project.library.entity.Subscription;
import com.artemstukalenko.project.library.entity.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PenaltyCalculator {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\penaltyLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("PenaltyCalculator");
        LOGGER.addHandler(FILE_HANDLER);
    }

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    private SubscriptionDAO subscriptionDAO;

    public PenaltyCalculator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int calculateUsersPenalty(String username) throws SQLException {
        List<Subscription> userSubscriptions = new ArrayList<>();

        subscriptionDAO = new SubscriptionDAOImpl(dataSource);
        userSubscriptions = subscriptionDAO.getUserSubscriptions(username);


        int penalty = (int)(userSubscriptions.stream()
                .filter(subscription -> {return subscription.getExpired() && !subscription.getFined();})
                .peek(subscription -> {
                    try {
                        subscriptionDAO.updateFinedInfo(subscription.getSubscriptionId(), true);
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Failed to calculate user's penalty by subscription: " + subscription);
                    }
                })
                .count() * 10);

        FILE_HANDLER.close();

        return penalty;
    }

}
