package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.dao.AuthorityDAOImpl;
import com.artemstukalenko.project.library.dao.UserDAOImpl;
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

@WebServlet("/HomepageController")
public class HomepageController extends HttpServlet {

    private AuthorityDAO authorityDAO;

    private String currentUserAuthority;

    private User currentUser;

    @Resource(name = "jdbc/library_db")
    private DataSource authorityDataSource;

    @Override
    public void init() throws ServletException {
        try {
            authorityDAO = new AuthorityDAOImpl(authorityDataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        currentUser = (User) request.getSession().getAttribute("currentUser");
        System.out.println("USERNAME: " + currentUser.getUsername());
        try {
            currentUserAuthority = authorityDAO.getUsersAuthority(currentUser.getUsername());
            System.out.println("AUTHORITY: " + currentUserAuthority);
            request.setAttribute("currentUserAuthority", currentUserAuthority);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getHomePage(request, response);
    }

    private void getHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");
        dispatcher.forward(request, response);
    }
}
