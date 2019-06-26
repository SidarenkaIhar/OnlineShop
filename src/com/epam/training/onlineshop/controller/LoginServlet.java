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
        User guest = new User("", userPassword, userEmail);

        for (User user : users) {
            if (user.equals(guest)) {
                session.setAttribute("User", user);
                response.sendRedirect("/");
                return;
            }
        }
        request.getRequestDispatcher("/html/login.html").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("User", null);
        response.sendRedirect("/html/login.html");
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
}
