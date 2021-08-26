package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.dao.CustomRequestDAO;
import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.dao.implementators.BookDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.CustomRequestDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.SubscriptionDAOImpl;
import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.entity.CustomRequest;
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

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/SubscriptionController")
public class SubscriptionController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\subscriptionControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("SubscriptionController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private SubscriptionDAO subscriptionDAO;

    private CustomRequestDAO customRequestDAO;

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(dataSource);
            subscriptionDAO = new SubscriptionDAOImpl(dataSource);
            customRequestDAO = new CustomRequestDAOImpl(dataSource);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        changeLanguage(request.getParameter("lang"));
        String command = request.getParameter("command") == null ? "" : request.getParameter("command");

        switch (command) {
            case "ARRANGE SUBSCRIPTION":
                registerSubscription(request, response);
                break;
            case "SHOW USER SUBSCRIPTIONS":
                showUserSubscriptions(request, response);
                break;
            case "RETURN BOOK":
                processBookReturn(request, response);
                break;
            default:
                showSubscriptionList(request, response);
                break;
        }



    }

    private void processBookReturn(HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException {

        int subscriptionIdForDeletion = Integer.parseInt(request.getParameter("subscriptionId"));
        Subscription subscriptionForDeletion;

        try {
            subscriptionForDeletion = subscriptionDAO.findSubscriptionById(subscriptionIdForDeletion);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find subscription for deletion: " + e.getStackTrace());
            throw new ServletException(e);
        }

        try {
            subscriptionDAO.deleteSubscriptionFromDB(subscriptionIdForDeletion);
            bookDAO.setTaken(subscriptionForDeletion.getBookId(), false);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete subscription/change book status: " + e.getStackTrace());
            throw new ServletException(e);
        }

        showUserSubscriptions(request, response);
    }

    private void showUserSubscriptions(HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {

        List<Subscription> currentUserSubscriptions;

        try {
            currentUserSubscriptions = subscriptionDAO.getUserSubscriptions((String) request.getSession().getAttribute("currentUserUsername"));
            request.setAttribute("currentUserSubscriptions", currentUserSubscriptions);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain user's subscriptions: " + e.getStackTrace());
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-subscriptions-page.jsp");
        dispatcher.forward(request, response);
    }

    private void showSubscriptionList(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        List<Subscription> allSubscriptions;
        List<CustomRequest> allRequests;

        try {
            allSubscriptions = subscriptionDAO.getAllSubscriptions();
            allRequests = customRequestDAO.getAllRequests();
            request.setAttribute("allRequests", allRequests);
            request.setAttribute("allSubscriptions", allSubscriptions);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain full subscription/request list: " + e.getStackTrace());
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("subscription-list-page.jsp");
        dispatcher.forward(request, response);
    }

    private void registerSubscription(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String currentUserUsername = (String) request.getSession().getAttribute("currentUserUsername");
        Book desiredBook = null;

        try {
            desiredBook = bookDAO.findBookById(bookId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find book for new subscription: " + e.getStackTrace());
            throw new ServletException(e);
        }


        Subscription newSubscription = new Subscription(currentUserUsername, bookId, desiredBook.getBookTitle(),
                desiredBook.getBookAuthor());

        try {
            subscriptionDAO.registerSubscriptionInDB(newSubscription);
            bookDAO.setTaken(desiredBook.getBookId(), true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to register subscription/change book status: " + e.getStackTrace());
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/HomepageController");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        for (Handler h : LOGGER.getHandlers()) {
            h.close();
        }
    }
}
