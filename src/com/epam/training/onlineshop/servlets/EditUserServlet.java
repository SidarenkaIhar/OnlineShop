package com.epam.training.onlineshop.servlets;

import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.users.UserGroup.getGroupById;

/**
 * Servlet responsible for adding a new user to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditUserServlet", urlPatterns = "/edituser")
public class EditUserServlet extends HttpServlet {
    private UserDAO userDAO;
    private Validator validator;

    public void init() {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        userDAO = mysqlFactory.getUserDAO();
        validator = new Validator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String statementType = getParameter(request, "statement_type", INSERT.name());
        handleRequest(request, statementType);

        request.getRequestDispatcher("jsp/edituser.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("editedUser", "Add new user");
        clearUserFields(request);
        request.getRequestDispatcher("jsp/edituser.jsp").forward(request, response);
    }

    private void setUserAttributes(HttpServletRequest request, User user) {
        request.setAttribute("val_user_id", user.getId());
        request.setAttribute("user_group_id", user.getGroupId());
        request.setAttribute("val_username", user.getName());
        request.setAttribute("val_password", user.getStringPassword());
        request.setAttribute("val_confirm", user.getStringPassword());
        request.setAttribute("val_email", user.getEmail());
        request.setAttribute("status", user.isEnabled());
    }

    private void handleRequest(HttpServletRequest request, String statementType) {
        int userId = getParameter(request, "user_id", -1);
        int userGroup = getParameter(request, "user_group", 0);
        String userName = getParameter(request, "username", "");
        String password = getParameter(request, "password", "");
        String confirm = getParameter(request, "confirm", "");
        String email = getParameter(request, "email", "");
        boolean status = getParameter(request, "status", 0) == 1;

        if (statementType.equalsIgnoreCase(SELECT.name())) {
            if (userId >= 0) {
                for (User user : userDAO.findAll()) {
                    if (user.getId() == userId) {
                        request.setAttribute("editedUser", "Edit user №" + user.getId());
                        request.setAttribute("val_statement_type", UPDATE.name());
                        setUserAttributes(request, user);
                    }
                }
            } else {
                request.setAttribute("failed", "Invalid user ID!");
                clearUserFields(request);
            }
        } else if (statementType.equalsIgnoreCase(UPDATE.name())) {
            request.setAttribute("editedUser", "Edit user №" + userId);
            request.setAttribute("val_statement_type", UPDATE.toString());
            boolean isCorrect = validator.validateUserData(userName, password, email) && password.equals(confirm);
            if (isCorrect) {
                User user = new User(userId, getGroupById(userGroup), userName, password, email, status);
                boolean isSuccessfully = userDAO.update(user);
                if (isSuccessfully) {
                    request.setAttribute("success", "User " + userName + " is successfully updated!");
                    setUserAttributes(request, user);
                } else {
                    request.setAttribute("failed", "User " + userName + " is not updated in the database!");
                }
            } else {
                request.setAttribute("failed", "User " + userName + " is not updated, the entered data does not meet the conditions!");
            }
        } else {
            request.setAttribute("editedUser", "Add new user");
            boolean isCorrect = validator.validateUserData(userName, password, email) && password.equals(confirm);
            if (isCorrect) {
                User user = new User(getGroupById(userGroup), userName, password, email, status);
                boolean isSuccessfully = userDAO.addNew(user);
                if (isSuccessfully) {
                    request.setAttribute("success", "New user " + userName + " added successfully!");
                } else {
                    request.setAttribute("failed", "New user " + userName + " is not added to the database!");
                }
            } else {
                request.setAttribute("failed", "New user " + userName + " is not added, the entered data does not meet the conditions!");
            }
            clearUserFields(request);
        }
    }

    private void clearUserFields(HttpServletRequest request) {
        User user = new User("", "", "");
        setUserAttributes(request, user);
    }

    private String getParameter(HttpServletRequest request, String parameter, String defaultValue) {
        String parameterValue = request.getParameter(parameter);
        return parameterValue == null || parameterValue.isEmpty() ? defaultValue : parameterValue;
    }

    private int getParameter(HttpServletRequest request, String parameter, int defaultValue) {
        String parameterValue = request.getParameter(parameter);
        if (parameterValue == null || parameterValue.isEmpty()) {
            return defaultValue;
        } else {
            return getNumber(parameterValue, defaultValue);
        }
    }

    private int getNumber(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
