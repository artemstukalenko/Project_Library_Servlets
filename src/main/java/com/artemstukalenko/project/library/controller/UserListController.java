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

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/UserListController")
public class UserListController extends HttpServlet {

    private UserDAO userDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
        } catch (Exception e) {
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
            throw new ServletException(e);
        }
        request.setAttribute("allUsers", allUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-list-page.jsp");
        dispatcher.forward(request, response);
    }
}
