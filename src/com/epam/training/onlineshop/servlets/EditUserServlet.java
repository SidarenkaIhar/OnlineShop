package com.epam.training.onlineshop.servlets;

import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.JsonDataPackage;
import com.epam.training.onlineshop.utils.Validator;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import static com.epam.training.onlineshop.dao.StatementType.*;

/**
 * Servlet responsible for adding a new user to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditUserServlet", urlPatterns = "/editUserServlet")
public class EditUserServlet extends HttpServlet {
    private UserDAO userDAO;
    private Validator validator;

    public void init() {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        userDAO = mysqlFactory.getUserDAO();
        validator = new Validator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/edituser.html");
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        Gson gson = new Gson();
        List<User> users = userDAO.findAll();
        User editableUser = null;
        String messageSuccess = "";
        String messageFailed = "";
        StatementType typeOperation = INSERT;

        String json = br.readLine();
        if (json != null) {
            JsonDataPackage requestJson = gson.fromJson(json, JsonDataPackage.class);
            if (requestJson.getTypeOperation() == INSERT) {
                editableUser = requestJson.getEditableUser();
                editableUser.setCreationDate(new Date());
                boolean isCorrect = validator.validateUserData(editableUser.getName(), editableUser.getStringPassword(), editableUser.getEmail());
                if (isCorrect) {
                    boolean isSuccessfully = userDAO.addNew(editableUser);
                    if (isSuccessfully) {
                        messageSuccess = "New user " + editableUser.getName() + " added successfully!";
                        editableUser = new User("", "", "");
                    } else {
                        messageFailed = "New user " + editableUser.getName() + " is not added to the database!";
                    }
                } else {
                    messageFailed = "New user " + editableUser.getName() + " is not added, the entered data does not meet the conditions!";
                }
            } else if (requestJson.getTypeOperation() == SELECT) {
                if (requestJson.getUsersToEdit().size() > 0) {
                    int userId = validator.getNumber(requestJson.getUsersToEdit().get(0), -1);
                    for (User us : users) {
                        if (us.getId() == userId) {
                            editableUser = us;
                            typeOperation = UPDATE;
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == UPDATE) {
                editableUser = requestJson.getEditableUser();
                typeOperation = UPDATE;
                boolean isCorrect = validator.validateUserData(editableUser.getName(), editableUser.getStringPassword(), editableUser.getEmail());
                if (isCorrect) {
                    boolean isSuccessfully = userDAO.update(editableUser);
                    if (isSuccessfully) {
                        messageSuccess = "User " + editableUser.getName() + " is successfully updated!";
                    } else {
                        messageFailed = "User " + editableUser.getName() + " is not updated in the database!";
                    }
                } else {
                    messageFailed = "User " + editableUser.getName() + " is not updated, the entered data does not meet the conditions!";
                }
            }
        }

        JsonDataPackage responseJson = new JsonDataPackage(null, null, editableUser, messageSuccess, messageFailed, typeOperation);

        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }
}
