package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.implementators.UserDAOImpl;

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

@WebServlet("/UserManipulationController")
public class UserManipulationController extends HttpServlet {

    private UserDAOImpl userDAO;

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
        String processedUserUsername = request.getParameter("userName");
        System.out.println("IN MANIPULTOR: " + processedUserUsername);
        switch (request.getParameter("command")) {
            case "BLOCK":
                try {
                    userDAO.blockUser(processedUserUsername);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "UNBLOCK":
                try {
                    userDAO.unblockUser(processedUserUsername);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                break;
            case "MAKE LIBRARIAN":
                break;
            case "MAKE NOT LIBRARIAN":
                break;
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("UserListController");
        dispatcher.forward(request, response);
    }
}
