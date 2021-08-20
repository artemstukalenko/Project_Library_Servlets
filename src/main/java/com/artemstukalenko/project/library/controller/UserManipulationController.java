package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.*;
import com.artemstukalenko.project.library.dao.implementators.*;
import com.artemstukalenko.project.library.entity.Subscription;

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
import java.util.List;

@WebServlet("/UserManipulationController")
public class UserManipulationController extends HttpServlet {

    private UserDAOImpl userDAO;

    private AuthorityDAO authorityDAO;

    private UserDetailsDAO userDetailsDAO;

    private SubscriptionDAO subscriptionDAO;

    private CustomRequestDAO customRequestDAO;

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
            authorityDAO = new AuthorityDAOImpl(userDataSource);
            userDetailsDAO = new UserDetailsDAOImpl(userDataSource);
            bookDAO = new BookDAOImpl(userDataSource);
            subscriptionDAO = new SubscriptionDAOImpl(userDataSource);
            customRequestDAO = new CustomRequestDAOImpl(userDataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String processedUserUsername = request.getParameter("userName");

        switch (request.getParameter("command")) {
            case "BLOCK":
                try {
                    userDAO.blockUser(processedUserUsername);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "UNBLOCK":
                try {
                    userDAO.unblockUser(processedUserUsername);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                try {
                    processUserDeletion(processedUserUsername);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "MAKE LIBRARIAN":
                try {
                    authorityDAO.makeUserLibrarian(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "LIBRARIAN");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "MAKE NOT LIBRARIAN":
                try {
                    authorityDAO.depriveLibrarianPrivileges(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "USER");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("UserListController");
        dispatcher.forward(request, response);
    }

    private void processUserDeletion(String processedUserUsername) throws SQLException {
        customRequestDAO.deleteUsersCustomRequests(processedUserUsername);

        List<Subscription> usersSubscriptions = subscriptionDAO.getUserSubscriptions(processedUserUsername);

        for (Subscription currentSubscription : usersSubscriptions) {
            bookDAO.setTaken(currentSubscription.getBookId(), false);
        }

        subscriptionDAO.deleteUsersSubscriptions(processedUserUsername);
        authorityDAO.deleteAuthority(processedUserUsername);
        userDetailsDAO.deleteUserDetails(processedUserUsername);
        userDAO.deleteUser(processedUserUsername);
    }
}
