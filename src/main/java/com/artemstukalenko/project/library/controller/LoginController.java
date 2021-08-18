package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.UserDAOImpl;
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

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1l;

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
        System.out.println("LANG " + request.getParameter("lang"));
        String desiredLanguage = request.getParameter("lang");

        if (desiredLanguage == null) {
            desiredLanguage = "en";
        }

        switch (desiredLanguage) {
                case "en":
                    textInfo.changeLanguageToEn();
                    break;
                case "ua":
                    textInfo.changeLanguageToUa();
                    break;
                default:
                    textInfo.changeLanguageToEn();
                    break;
            }

        request.setAttribute("textInfo", textInfo);
        showLoginPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        try {
            User currentUser = userDAO.findUserByUsername(request.getParameter("username"));
            request.setAttribute("currentUser", currentUser);
            getHomepage(request, response);
        } catch (SQLException e) {
            System.out.println("SQL EXCEPTION CAUGHT");
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
