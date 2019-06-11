package com.epam.training.onlineshop.servlets;

import com.epam.training.onlineshop.dao.DAOFactory;
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
import java.util.List;

import static com.epam.training.onlineshop.dao.StatementType.SELECT;

/**
 * The servlet is responsible for displaying and removing users store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "HomeServlet", urlPatterns = "/homeServlet")
public class HomeServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        userDAO = mysqlFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/");
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userDAO.findAll();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";

        String json = br.readLine();
        if (json != null) {
            JsonDataPackage requestJson = gson.fromJson(json, JsonDataPackage.class);
            Validator validator = new Validator();

            if (requestJson != null && requestJson.getUsersToEdit().size() > 0) {
                for (String userId : requestJson.getUsersToEdit()) {
                    for (User user : users) {
                        if (validator.getNumber(userId, -1) == user.getId()) {
                            boolean isSuccessfully = userDAO.delete(user);
                            if (isSuccessfully) {
                                messageSuccess += "User " + user.getName() + ", № " + user.getId() + " is successfully deleted. <br>";
                            } else {
                                messageFailed += "User " + user.getName() + ", № " + user.getId() + " was not deleted. <br>";
                            }
                        }
                    }
                }
            } else {
                messageFailed = "You must select at least one user!";
            }
        }
        users = userDAO.findAll();

        JsonDataPackage responseJson = new JsonDataPackage(users, null, null, messageSuccess, messageFailed, SELECT);

        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }
}
