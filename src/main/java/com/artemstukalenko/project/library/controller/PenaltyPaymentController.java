package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.dao.implementators.AuthorityDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDetailsDAOImpl;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.entity.UserDetails;
import com.artemstukalenko.project.library.utility.PenaltyCalculator;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet("/PenaltyPaymentController")
public class PenaltyPaymentController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\penaltyPaymentControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("PenaltyPaymentController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private UserDetailsDAO userDetailsDAO;
    private UserDAOImpl userDAO;

    private User currentUser;

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(dataSource);
            userDetailsDAO = new UserDetailsDAOImpl(dataSource);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("penalty-payment-form.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        int userInputSum = Integer.parseInt(request.getParameter("userSum"));
        String payerUsername = (String) request.getSession().getAttribute("currentUserUsername");
        UserDetails payersDetails = new UserDetails();
        currentUser = (User) request.getSession().getAttribute("currentUser");

        try {
            payersDetails = userDAO.findUserByUsername(payerUsername).getUserDetails();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain payers details: " + e.getStackTrace());
            throw new ServletException(e);
        }

        try {
            userDetailsDAO.updatePenaltyInfo(payerUsername, payersDetails.getUserPenalty() - userInputSum);
            currentUser.getUserDetails().setPenalty(-userInputSum);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update user info after payment: " + e.getStackTrace());
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("HomepageController");
        dispatcher.forward(request, response);
    }
}
