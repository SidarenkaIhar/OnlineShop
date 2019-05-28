package com.epam.training.onlineshop.servlets;

import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {
    private UserDAO userDAO;
    private List<User> users;

    public void init() {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        userDAO = mysqlFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteSelectedUsers(request);

        findAllUsers(request, response);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        findAllUsers(request, response);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void deleteSelectedUsers(HttpServletRequest request) {
        String[] selected = request.getParameterValues("selected[]");
        StringBuilder failedExecution = new StringBuilder();
        StringBuilder successfulExecution = new StringBuilder();
        if (selected != null) {
            for (String userId : selected) {
                for (User user : users) {
                    if (Integer.valueOf(userId) == user.getId()) {
                        boolean isSuccessfully = userDAO.delete(user);
                        if (isSuccessfully) {
                            successfulExecution.append("User ").append(user.getName()).append(", № ").append(user.getId()).append(" is successfully deleted. ");
                        } else {
                            failedExecution.append("User ").append(user.getName()).append(", № ").append(user.getId()).append(" was not deleted. ");
                        }
                    }
                }
            }
        } else {
            failedExecution.append("You must select at least one user!");
        }
        request.setAttribute("failed", failedExecution.length() > 0 ? failedExecution : null);
        request.setAttribute("success", successfulExecution.length() > 0 ? successfulExecution : null);
    }

    private void findAllUsers(HttpServletRequest request, HttpServletResponse response) {
        users = userDAO.findAll();
        request.setAttribute("allUsers", users);
    }
}
