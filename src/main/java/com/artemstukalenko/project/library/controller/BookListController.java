package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.dao.implementators.BookDAOImpl;
import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.utility.Sorter;

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

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/BookListController")
public class BookListController extends HttpServlet {

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource bookDataSource;

    private Sorter sorter;

    private String sortMethod;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(bookDataSource);
            sorter = new Sorter();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        changeLanguage(request.getParameter("lang"));
        String command = request.getParameter("command") == null ? "" : request.getParameter("command");
        int currentBookId = request.getParameter("bookId") == null ? -1 :
                Integer.parseInt(request.getParameter("bookId"));

        sortMethod = request.getParameter("sortMethod");

        switch (command) {
            case "DELETE BOOK":
                try {
                    bookDAO.deleteBook(currentBookId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            default:
                showAllBooks(request, response, sortMethod);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String newBookTitle = request.getParameter("bookTitle");
        String newBookAuthor = request.getParameter("bookAuthor");
        String newBookYear = request.getParameter("bookYearOfPublishing");

        Book newBook = new Book(newBookTitle, newBookAuthor, newBookYear);

        try {
            bookDAO.addNewBook(newBook);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        showAllBooks(request, response, sortMethod);
    }

    private void showAllBooks(HttpServletRequest request,
                              HttpServletResponse response, String sortMethod) throws ServletException, IOException {
        List<Book> allBooks;

        try {
            allBooks = bookDAO.getAllBooks();
            if (sortMethod != null) {
                sorter.setSortMethod(sortMethod);
                allBooks = sorter.sortList(allBooks);
            }
            request.setAttribute("allBooks", allBooks);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        User currentTempUser = (User) request.getSession().getAttribute("currentUser");
        request.setAttribute("isUser", currentTempUser.getAuthorityString().equals("USER"));
        request.setAttribute("isAdmin", currentTempUser.getAuthorityString().equals("ADMIN"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list-page.jsp");
        dispatcher.forward(request, response);
    }
}
