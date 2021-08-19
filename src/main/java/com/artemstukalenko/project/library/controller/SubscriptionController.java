package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.dao.SubscriptionDAO;
import com.artemstukalenko.project.library.dao.implementators.BookDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.SubscriptionDAOImpl;
import com.artemstukalenko.project.library.entity.Book;
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
import java.time.Period;
import java.util.List;

@WebServlet("/SubscriptionController")
public class SubscriptionController extends HttpServlet {

    private SubscriptionDAO subscriptionDAO;

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(dataSource);
            subscriptionDAO = new SubscriptionDAOImpl(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
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
            throw new ServletException(e);
        }

        try {
            subscriptionDAO.deleteSubscriptionFromDB(subscriptionIdForDeletion);
            bookDAO.setTaken(subscriptionForDeletion.getBookId(), false);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        showUserSubscriptions(request, response);
    }

    private void showUserSubscriptions(HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {

        List<Subscription> currentUserSubscriptions;

        try {
            System.out.println("USERNAME: " + request.getSession().getAttribute("currentUserUsername"));
            currentUserSubscriptions = subscriptionDAO.getUserSubscriptions((String) request.getSession().getAttribute("currentUserUsername"));
            request.setAttribute("currentUserSubscriptions", currentUserSubscriptions);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-subscriptions-page.jsp");
        dispatcher.forward(request, response);
    }

    private void showSubscriptionList(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        List<Subscription> allSubscriptions;

        try {
            allSubscriptions = subscriptionDAO.getAllSubscriptions();
            request.setAttribute("allSubscriptions", allSubscriptions);
        } catch (SQLException e) {
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
            e.printStackTrace();
        }


        Subscription newSubscription = new Subscription(currentUserUsername, bookId, desiredBook.getBookTitle(),
                desiredBook.getBookAuthor());

        try {
            subscriptionDAO.registerSubscriptionInDB(newSubscription);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/HomepageController");
        dispatcher.forward(request, response);
    }
}
