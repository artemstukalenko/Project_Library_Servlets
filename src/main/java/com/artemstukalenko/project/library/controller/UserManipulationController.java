package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.dao.implementators.AuthorityDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDetailsDAOImpl;

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

    private AuthorityDAO authorityDAO;

    private UserDetailsDAO userDetailsDAO;

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
            authorityDAO = new AuthorityDAOImpl(userDataSource);
            userDetailsDAO = new UserDetailsDAOImpl(userDataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String processedUserUsername = request.getParameter("userName");

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
                try {
                    authorityDAO.makeUserLibrarian(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "LIBRARIAN");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "MAKE NOT LIBRARIAN":
                try {
                    authorityDAO.depriveLibrarianPrivileges(processedUserUsername);
                    userDetailsDAO.updateAuthorityInfo(processedUserUsername, "USER");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("UserListController");
        dispatcher.forward(request, response);
    }
}
