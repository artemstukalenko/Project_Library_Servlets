package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.dao.implementators.AuthorityDAOImpl;
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
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        currentUser = (User) request.getSession().getAttribute("currentUser");
        try {
            currentUserAuthority = authorityDAO.getUsersAuthority(currentUser.getUsername());
            request.setAttribute("currentUserAuthority", currentUserAuthority);
            request.getSession().setAttribute("currentUserUsername", currentUser.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getHomePage(request, response);
    }

    private void getHomePage(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");

        if (currentUserAuthority.equals("ROLE_ADMIN")) {
            dispatcher = request.getRequestDispatcher("admin-homepage.jsp");
        }


        dispatcher.forward(request, response);
    }
}
