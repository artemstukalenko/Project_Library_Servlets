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

@WebServlet("/PenaltyPaymentController")
public class PenaltyPaymentController extends HttpServlet {

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
            e.printStackTrace();
        }

        try {
            userDetailsDAO.updatePenaltyInfo(payerUsername, payersDetails.getUserPenalty() - userInputSum);
            currentUser.getUserDetails().setPenalty(-userInputSum);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("HomepageController");
        dispatcher.forward(request, response);
    }
}
