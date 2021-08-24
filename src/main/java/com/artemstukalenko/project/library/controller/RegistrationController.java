package com.artemstukalenko.project.library.controller;

import com.artemstukalenko.project.library.dao.AuthorityDAO;
import com.artemstukalenko.project.library.dao.UserDetailsDAO;
import com.artemstukalenko.project.library.dao.implementators.AuthorityDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDAOImpl;
import com.artemstukalenko.project.library.dao.implementators.UserDetailsDAOImpl;
import com.artemstukalenko.project.library.entity.Authority;
import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.entity.UserDetails;
import com.artemstukalenko.project.library.utility.RegistrationDataValidator;

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

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {

    private UserDAOImpl userDAO;

    private UserDetailsDAO userDetailsDAO;

    private AuthorityDAO authorityDAO;

    private RegistrationDataValidator validator;

    @Resource(name = "jdbc/library_db")
    private DataSource userDataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAOImpl(userDataSource);
            userDetailsDAO = new UserDetailsDAOImpl(userDataSource);
            authorityDAO = new AuthorityDAOImpl(userDataSource);
            validator = new RegistrationDataValidator();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("registration-form.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        if (!dataIsValid(request, username, firstName, lastName, email, phoneNumber, address)) {

            request.setAttribute("enteredUsername", username);
            request.setAttribute("enteredFirstName", firstName);
            request.setAttribute("enteredLastName", lastName);
            request.setAttribute("enteredEmail", email);
            request.setAttribute("enteredPhoneNumber", phoneNumber);
            request.setAttribute("enteredAddress", address);

            doGet(request, response);
            return;
        }

        User potentialUser = new User(username, password);
        Authority potentialUsersAuthority = new Authority(username, "ROLE_USER");
        UserDetails potentialUsersDetails = new UserDetails(username, firstName, lastName,
                email, phoneNumber, address, "USER");

        processUserRegistration(potentialUser);
        processAuthorityRegistration(potentialUsersAuthority);
        processDetailsRegistration(potentialUsersDetails);

        redirectToLoginPage(request, response);
    }

    public void redirectToLoginPage(HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController");
        request.getSession().invalidate();
        dispatcher.forward(request, response);
    }

    private boolean dataIsValid(HttpServletRequest request, String username,
                                String firstName, String lastName, String email,
                                String phoneNumber, String address) {
        List<String> mistakeList = validator.getFieldsWithMistakes(username, firstName,
                lastName, email, phoneNumber, address);

        if (mistakeList.isEmpty()) {
            return true;
        } else {
            mistakeList.stream().forEach(
                    mistake -> {
                        request.setAttribute((mistake + "IsInvalid"), true);
                    }
            );



            return false;
        }
    }

    private void processUserRegistration(User potentialUser) {
        try {
            userDAO.registerUser(potentialUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processAuthorityRegistration(Authority authorityToRegister) {
        try {
            authorityDAO.addAuthorityToDB(authorityToRegister);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void processDetailsRegistration(UserDetails potentialUsersDetails) {
        try {
            userDetailsDAO.registerUserDetails(potentialUsersDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
