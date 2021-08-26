package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.implementators.UserDAOImpl;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.view.FirstView;

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

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\applicationLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("LoginController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private UserDAOImpl userDAO;

    private FirstView textInfo = new FirstView();

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    public LoginController() {}

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentURL", request.getServletPath());
        changeLanguage(request.getParameter("lang"));

        request.getSession().setAttribute("textInfo", textInfo);
        showLoginPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().isNew()) {
            doGet(request, response);
        }

        try {
            User currentUser = userDAO.findUserByUsername(request.getParameter("username"));
            request.getSession().setAttribute("currentUser", currentUser);
            getHomepage(request, response);
        } catch (SQLException e) {
            showLoginPage(request, response);
        }

    }

    private void getHomepage(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/HomepageController");
        dispatcher.forward(request, response);
    }

    private void showLoginPage(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
