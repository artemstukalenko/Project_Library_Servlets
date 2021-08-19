package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.dao.implementators.BookDAOImpl;
import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.entity.User;

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

@WebServlet("/BookListController")
public class BookListController extends HttpServlet {

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource bookDataSource;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(bookDataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        List<Book> allBooks;

        try {
            allBooks = bookDAO.getAllBooks();
            request.setAttribute("allBooks", allBooks);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        User currentTempUser = (User) request.getSession().getAttribute("currentUser");
        request.setAttribute("isUser", currentTempUser.getAuthorityString().equals("USER"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list-page.jsp");
        dispatcher.forward(request, response);
    }
}
