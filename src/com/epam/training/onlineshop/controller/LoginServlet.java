package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * Responsible for the authorization of users in the online store
 *
 * @author Ihar Sidarenka
 * @version 0.1 22-Jun-19
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    /* Working with users in database */
    private UserDAO userDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        userDAO = mysqlFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<User> users = userDAO.findAll();

        String userEmail = getParameter(request, "user_email");
        String userPassword = getParameter(request, "user_password");
        String userLanguage = getParameter(request, "userLanguage");
        User guest = new User("", userPassword, userEmail);
        guest.setLocale(userLanguage);

        for (User user : users) {
            if (user.equals(guest)) {
                if (!user.getLocale().equals(guest.getLocale())) {
                    user.setLocale(userLanguage);
                    userDAO.update(user);
                }
                session.setAttribute("User", user);
                if (Locale.US.equals(user.getLocale())) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/html/ru/index.html");
                }
                return;
            }
        }
        if (Locale.US.equals(guest.getLocale())) {
            request.getRequestDispatcher("/html/login.html").forward(request, response);
        } else {
            request.getRequestDispatcher("/html/ru/login.html").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user =  (User) session.getAttribute("User");
        session.setAttribute("User", null);
        if (Locale.US.equals(user.getLocale())) {
            response.sendRedirect("/html/login.html");
        } else {
            response.sendRedirect("/html/ru/login.html");
        }
    }

    /**
     * Checks the obtained parameters
     *
     * @param request   request to server with parameters
     * @param parameter parameter to check
     *
     * @return parameter value
     */
    private String getParameter(HttpServletRequest request, String parameter) {
        String parameterValue = request.getParameter(parameter);
        boolean isEmpty = parameterValue == null || parameterValue.isEmpty();
        return isEmpty ? "" : parameterValue;
    }

    /**
     * Returns an authorized store user
     *
     * @param request   request to server with parameters
     *
     * @return authorized user
     */
    static User getAuthorizedUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("User");
    }

    /**
     * Get the language of the authorized user
     *
     * @param request   request to server with parameters
     *
     * @return user locale
     */
    static Locale getUserLocale(HttpServletRequest request) {
        User authorizedUser = getAuthorizedUser(request);
        return authorizedUser == null ? Locale.US : authorizedUser.getLocale();
    }
}
