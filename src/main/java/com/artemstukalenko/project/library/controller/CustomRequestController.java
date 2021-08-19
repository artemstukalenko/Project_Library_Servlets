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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/CustomRequestController")
public class CustomRequestController extends HttpServlet {

    private BookDAO bookDAO;

    private SubscriptionDAO subscriptionDAO;

    private CustomRequestDAO customRequestDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(dataSource);
            customRequestDAO = new CustomRequestDAOImpl(dataSource);
            subscriptionDAO = new SubscriptionDAOImpl(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command") == null ? "" : request.getParameter("command");

        switch (command) {
            case "ARRANGE CUSTOM REQUEST":
                getArrangeCustomRequestForm(request, response);
                break;
            default:
                showAllRequests(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        registerCustomRequest(request, response);

    }

    private void registerCustomRequest(HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {
        String customersUsername = (String) request.getSession().getAttribute("currentUserUsername");

        Book currentBook = (Book) request.getSession().getAttribute("currentBook");

        int desiredBookId = currentBook.getBookId();
        String desiredBookTitle = currentBook.getBookTitle();
        String desiredBookAuthor = currentBook.getBookAuthor();
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        CustomRequest newRequest = new CustomRequest(customersUsername, desiredBookId, desiredBookTitle,
                desiredBookAuthor, startDate, endDate);

        try {
            customRequestDAO.addCustomRequestToDB(newRequest);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list-page.jsp");
        dispatcher.forward(request, response);

    }

    private void getArrangeCustomRequestForm(HttpServletRequest request,
                                             HttpServletResponse response) throws ServletException, IOException {

        Book currentBook;

        try {
            currentBook = bookDAO.findBookById(Integer.parseInt(request.getParameter("bookId")));
            request.getSession().setAttribute("currentBook", currentBook);

            if (currentBook.getTaken()) {
                Subscription currentSubscription = subscriptionDAO.findSubscriptionByBookId(currentBook.getBookId());
                request.getSession().setAttribute("currentSubscription", currentSubscription);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.setAttribute("today", LocalDate.now());

        RequestDispatcher dispatcher = request.getRequestDispatcher("arrange-custom-request-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showAllRequests(HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException, IOException {

        List<CustomRequest> requestList;

        try {
            requestList = customRequestDAO.getAllRequests();
            request.setAttribute("allRequests", requestList);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("subscription-list-page.jsp");
        dispatcher.forward(request, response);
    }
}
