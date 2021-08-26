package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.dao.implementators.AuthorityDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDetailsDAOImpl;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.utility.PenaltyCalculator;

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
import java.util.logging.*;

import static com.artemstukalenko.project.library.utility.LanguageChanger.changeLanguage;

@WebServlet("/HomepageController")
public class HomepageController extends HttpServlet {

    private final static Logger LOGGER;
    private static FileHandler FILE_HANDLER;

    static {
        try {
            FILE_HANDLER = new FileHandler("D:\\project_library_servlets\\src\\main\\resources\\logs\\homepageControllerLog.log",
                    true);
            FILE_HANDLER.setFormatter(new SimpleFormatter());
            FILE_HANDLER.setLevel(Level.ALL);
            FILE_HANDLER.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger("HomepageController");
        LOGGER.addHandler(FILE_HANDLER);
    }

    private AuthorityDAO authorityDAO;

    private UserDetailsDAO userDetailsDAO;

    private String currentUserAuthority;

    private User currentUser;

    private PenaltyCalculator penaltyCalculator;

    @Resource(name = "jdbc/library_db")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            authorityDAO = new AuthorityDAOImpl(dataSource);
            userDetailsDAO = new UserDetailsDAOImpl(dataSource);
            penaltyCalculator = new PenaltyCalculator(dataSource);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize: " + e.getStackTrace());
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
        changeLanguage(request.getParameter("lang"));
        request.setAttribute("currentURL", request.getServletPath());
        currentUser = (User) request.getSession().getAttribute("currentUser");

        String currentUserUsername = currentUser.getUsername();

        try {
            currentUserAuthority = authorityDAO.getUsersAuthority(currentUserUsername);

            userDetailsDAO.updatePenaltyInfo(currentUserUsername,
                    (userDetailsDAO.getDetailsByUsername(currentUserUsername).getUserPenalty()
                            + penaltyCalculator.calculateUsersPenalty(currentUserUsername)));
            currentUser.setUserDetails(userDetailsDAO.getDetailsByUsername(currentUserUsername));


            request.setAttribute("currentUserAuthority", currentUserAuthority);
            request.getSession().setAttribute("currentUserUsername", currentUserUsername);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed obtain current user information: " + e.getStackTrace());
            throw new ServletException(e);
        }

        getHomePage(request, response);
    }

    private void getHomePage(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher;

        switch (currentUserAuthority) {
            case "ROLE_ADMIN":
                dispatcher = request.getRequestDispatcher("admin-homepage.jsp");
                break;
            case "ROLE_LIBRARIAN":
                dispatcher = request.getRequestDispatcher("librarian-home-page.jsp");
                break;
            default:
                dispatcher = request.getRequestDispatcher("homepage.jsp");
        }

        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        for (Handler h : LOGGER.getHandlers()) {
            h.close();
        }
    }
}
