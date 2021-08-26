package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.UserDAO;
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
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/UserListController")
public class UserListController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\userListControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("UserListController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private UserDAO userDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        changeLanguage(request.getParameter("lang"));
        List<User> allUsers;

        try {
            allUsers = userDAO.getAllUsers();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain full user list: " + e.getStackTrace());
            throw new ServletException(e);
        }
        request.setAttribute("allUsers", allUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-list-page.jsp");
        dispatcher.forward(request, response);
    }
}
