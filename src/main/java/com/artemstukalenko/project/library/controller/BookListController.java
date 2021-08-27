package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.BookDAO;
import com.artemstukalenko.project.library.dao.implementators.BookDAOImpl;
import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.utility.Searcher;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/BookListController")
public class BookListController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\bookListControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("BookListController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private BookDAO bookDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource bookDataSource;

    private Sorter sorter;
    private Searcher searcher;

    private String sortMethod;
    private String searchCriteria;
    private String userInputForSearch;

    private List<Book> allBooksList;
    private List<Book> filteredBookList;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAOImpl(bookDataSource);
            sorter = new Sorter();
            searcher = new Searcher();
            updateBookList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
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

        initializeUserFilters(request);

        if (request.getParameter("removeFilters") != null) {
            removeFilters();
        }

        switch (command) {
            case "DELETE BOOK":
                try {
                    bookDAO.deleteBook(currentBookId);
                    updateBookList();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Failed to delete book: " + e.getStackTrace());
                }
            default:
                showAllBooks(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String newBookTitle = request.getParameter("bookTitle");
        String newBookAuthor = request.getParameter("bookAuthor");
        String newBookYear = request.getParameter("bookYearOfPublishing");

        if (newBookTitle.trim().equals("") || newBookAuthor.trim().equals("")
                || newBookYear.trim().equals(""))  {
            request.setAttribute("inputIsNotFull", true);
            getEnterInfoForNewBookPage(request, response);
            return;
        }

        Book newBook = new Book(newBookTitle, newBookAuthor, newBookYear);

        try {
            bookDAO.addNewBook(newBook);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add book: " + e.getStackTrace());
            throw new ServletException(e);
        }

        updateBookList();

        showAllBooks(request, response);
    }

    private void showAllBooks(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {

        int desiredPage = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));

        if (sortMethod != null || searchCriteria != null
                || userInputForSearch != null) {
            showFilteredBookList(request, response);
            return;
        }

            Map<Integer, List<Book>> allPages = getBookPages(allBooksList);
            filteredBookList = allBooksList;
            request.setAttribute("allBooks", allPages.get(desiredPage));
            request.setAttribute("pagesCount", allPages.keySet());

        User currentTempUser = (User) request.getSession().getAttribute("currentUser");
        request.setAttribute("isUser", currentTempUser.getAuthorityString().equals("USER"));
        request.setAttribute("isAdmin", currentTempUser.getAuthorityString().equals("ADMIN"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list-page.jsp");
        dispatcher.forward(request, response);
    }

    private Map<Integer, List<Book>> getBookPages(List<Book> allBooks) {
        Map<Integer, List<Book>> pages = new HashMap<>();
        int pageSizeLimit = 5;
        int pageCounter = 1;
        int currentBookIndex = 0;

        while (pageCounter <= allBooks.size()/pageSizeLimit + 1) {
            int countOfBooksOnThePage = 0;
            List<Book> page = new ArrayList<>();
            while (countOfBooksOnThePage < pageSizeLimit && currentBookIndex < allBooks.size()) {
                page.add(allBooks.get(currentBookIndex));
                currentBookIndex++;
                countOfBooksOnThePage++;
            }

            pages.put(pageCounter, page);
            pageCounter++;
        }

        return pages;
    }

    private void showFilteredBookList(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        int desiredPage = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));

        if (sortMethod != null) {
            sorter.setSortMethod(sortMethod);
            filteredBookList = sorter.sortList(allBooksList);
            allBooksList = filteredBookList;
        }
        if (searchCriteria != null && userInputForSearch != null) {
            searcher.setSearchCriteria(searchCriteria);
            searcher.setUserInput(userInputForSearch);
            try {
                filteredBookList = searcher.getResultOfTheBookSearch(bookDAO.getAllBooks());
                allBooksList = filteredBookList;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to obtain all books list: " + e.getStackTrace());
            }
        }
        Map<Integer, List<Book>> allPages = getBookPages(filteredBookList);

        request.setAttribute("allBooks", allPages.get(desiredPage));
        request.setAttribute("pagesCount", allPages.keySet());

        User currentTempUser = (User) request.getSession().getAttribute("currentUser");
        request.setAttribute("isUser", currentTempUser.getAuthorityString().equals("USER"));
        request.setAttribute("isAdmin", currentTempUser.getAuthorityString().equals("ADMIN"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list-page.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        for (Handler h : LOGGER.getHandlers()) {
            h.close();
        }
    }

    private void getEnterInfoForNewBookPage(HttpServletRequest request,
                                            HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("enter-info-for-new-book.jsp");
        dispatcher.forward(request, response);
    }

    private void initializeUserFilters(HttpServletRequest request) {
        sortMethod = request.getParameter("sortMethod");
        searchCriteria = request.getParameter("searchCriteria");
        userInputForSearch = request.getParameter("userInputForSearch");
    }

    private void removeFilters() {
        sortMethod = null;
        searchCriteria = null;
        userInputForSearch = null;
        updateBookList();
    }

    private void updateBookList() {
        try {
            allBooksList = bookDAO.getAllBooks();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain all books list: " + e.getStackTrace());
        }
    }
}
