package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;
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
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.SELECT;

/**
 * The servlet is responsible for displaying and removing users store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowUsersServlet", urlPatterns = "/showUsersServlet")
public class ShowUsersServlet extends HttpServlet {

    /* Working with users in database */
    private UserDAO userDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        userDAO = mysqlFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User authorizedUser = LoginServlet.getAuthorizedUser(request);
        Locale locale = LoginServlet.getUserLocale(request);

        List<User> users = userDAO.findAll();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";

        String json = br.readLine();
        if (json != null) {
            UserJsonDataPackage requestJson = gson.fromJson(json, UserJsonDataPackage.class);
            Validator validator = new Validator();

            if (requestJson != null && requestJson.getEntitiesToEdit().size() > 0) {
                StringBuilder success = new StringBuilder();
                StringBuilder failed = new StringBuilder();
                for (String userId : requestJson.getEntitiesToEdit()) {
                    for (User user : users) {
                        if (validator.getNumber(userId, -1) == user.getId()) {
                            if (authorizedUser != null && authorizedUser.getId() == user.getId()) {
                                failed.append(MessagesManager.getMessage(CANNOT_DELETE_YOURSELF, locale));
                            } else {
                                boolean isSuccessfully = userDAO.delete(user);
                                if (isSuccessfully) {
                                    success.append(user.getName()).append(MessagesManager.getMessage(ENTITY_SUCCESSFULLY_DELETED, locale));
                                } else {
                                    failed.append(user.getName()).append(MessagesManager.getMessage(ENTITY_NOT_DELETED, locale));
                                }
                            }
                        }
                    }
                }
                messageSuccess = success.length() > 0 ? success.toString() : "";
                messageFailed = failed.length() > 0 ? failed.toString() : "";
            } else {
                messageFailed = MessagesManager.getMessage(NOTHING_SELECTED_TO_DELETE, locale);
            }
        }
        users = userDAO.findAll();

        JsonDataPackage<User> responseJson = new JsonDataPackage<>(users, messageSuccess, messageFailed, SELECT);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/users.html");
    }
}
