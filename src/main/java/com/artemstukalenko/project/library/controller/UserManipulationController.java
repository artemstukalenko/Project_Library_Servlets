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
import java.util.logging.*;

@WebServlet("/UserManipulationController")
public class UserManipulationController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\userManipulationControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("UserManipulationController");
        LOGGER.addHandler(FILE_HANDLER);
    }

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
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
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
                    LOGGER.log(Level.SEVERE, "Failed to block user's account: " + e.getStackTrace());
                    throw new ServletException(e);
                }
                break;
            case "UNBLOCK":
                try {
                    userDAO.unblockUser(processedUserUsername);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Failed to unblock user's account: " + e.getStackTrace());
                    throw new ServletException(e);
                }
                break;
            case "DELETE":
                try {
                    processUserDeletion(processedUserUsername);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Failed to delete user's account: " + e.getStackTrace());
                    throw new ServletException(e);
                }
                break;
            case "MAKE LIBRARIAN":
                try {
                    authorityDAO.makeUserLibrarian(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "LIBRARIAN");
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Failed to make user librarian: " + e.getStackTrace());
                    throw new ServletException(e);
                }
                break;
            case "MAKE NOT LIBRARIAN":
                try {
                    authorityDAO.depriveLibrarianPrivileges(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "USER");
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Failed to deprive librarian privileges: " + e.getStackTrace());
                    throw new ServletException(e);
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

    @Override
    public void destroy() {
        for (Handler h : LOGGER.getHandlers()) {
            h.close();
        }
    }
}
