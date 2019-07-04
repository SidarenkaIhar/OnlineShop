package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.UserJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.entity.user.UserGroup.ADMINISTRATOR;
import static com.epam.training.onlineshop.entity.user.UserGroup.CUSTOMER;

/**
 * Servlet responsible for adding a new user to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditUserServlet", urlPatterns = "/editUserServlet")
public class EditUserServlet extends HttpServlet {

    /* Working with users in database */
    private UserDAO userDAO;

    /* Checks data for validity */
    private Validator validator;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        userDAO = mysqlFactory.getUserDAO();
        validator = new Validator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User authorizedUser = LoginServlet.getAuthorizedUser(request);
        Locale locale = LoginServlet.getUserLocale(request);
        boolean isNotAdmin = authorizedUser == null || authorizedUser.getGroup() != ADMINISTRATOR;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();

        String respJson = handleRequest(json, isNotAdmin, locale);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/edituser.html");
    }

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param json       request from the page to the server
     * @param isNotAdmin is the user an admin
     * @param locale     language for displaying messages to the user
     *
     * @return the server's response to the page
     */
    private String handleRequest(String json, boolean isNotAdmin, Locale locale) {
        Gson gson = new Gson();
        List<User> users = userDAO.findAll();
        User editableUser = null;
        String messageSuccess = "";
        String messageFailed = "";
        StatementType typeOperation = INSERT;

        if (json != null) {
            UserJsonDataPackage requestJson = gson.fromJson(json, UserJsonDataPackage.class);
            if (requestJson.getTypeOperation() == INSERT) {
                editableUser = requestJson.getEditableEntity();
                locale = editableUser.getLocale();
                boolean isExist = false;
                for (User user : users) {
                    if (user.equals(editableUser)) {
                        messageFailed = editableUser.getEmail() + MessagesManager.getMessage(ALREADY_EXIST, locale);
                        isExist = true;
                    }
                }
                if (!isExist) {
                    editableUser.setCreationDate(new Date());
                    boolean isCorrect = validator.validateUserData(editableUser.getName(), editableUser.getStringPassword(), editableUser.getEmail());
                    if (isCorrect) {
                        if (isNotAdmin) {
                            editableUser.setGroup(CUSTOMER);
                        }
                        boolean isSuccessfully = userDAO.addNew(editableUser);
                        if (isSuccessfully) {
                            messageSuccess = editableUser.getName() + MessagesManager.getMessage(ENTITY_ADDED_SUCCESSFULLY, locale);
                            editableUser = new User("", "", "");
                        } else {
                            messageFailed = editableUser.getName() + MessagesManager.getMessage(ENTITY_NOT_ADDED, locale);
                        }
                    } else {
                        messageFailed = editableUser.getName() + MessagesManager.getMessage(NOT_MEET_CONDITIONS, locale);
                    }
                }
            } else if (requestJson.getTypeOperation() == SELECT) {
                if (requestJson.getEntitiesToEdit().size() > 0) {
                    int userId = validator.getNumber(requestJson.getEntitiesToEdit().get(0), -1);
                    for (User us : users) {
                        if (us.getId() == userId) {
                            editableUser = us;
                            typeOperation = UPDATE;
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == UPDATE) {
                editableUser = requestJson.getEditableEntity();
                typeOperation = UPDATE;
                boolean isCorrect = validator.validateUserData(editableUser.getName(), editableUser.getStringPassword(), editableUser.getEmail());
                if (isCorrect) {
                    boolean isSuccessfully = userDAO.update(editableUser);
                    if (isSuccessfully) {
                        messageSuccess = editableUser.getName() + MessagesManager.getMessage(ENTITY_SUCCESSFULLY_UPDATED, locale);
                    } else {
                        messageFailed = editableUser.getName() + MessagesManager.getMessage(ENTITY_NOT_UPDATED, locale);
                    }
                } else {
                    messageFailed = editableUser.getName() + MessagesManager.getMessage(NOT_MEET_CONDITIONS, locale);
                }
            }
        }
        UserJsonDataPackage responseJson = new UserJsonDataPackage(null, null, editableUser, messageSuccess, messageFailed, typeOperation);
        return gson.toJson(responseJson);
    }
}
