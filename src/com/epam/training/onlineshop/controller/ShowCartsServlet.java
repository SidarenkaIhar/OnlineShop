package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.entity.user.UserGroup;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.CartJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.SELECT;

/**
 * The servlet is responsible for displaying and removing carts store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowCartsServlet", urlPatterns = "/showCartsServlet")
public class ShowCartsServlet extends HttpServlet {

    /* Working with carts in database */
    private AbstractDAO<ShoppingCart> cartDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        cartDAO = mysqlFactory.getShoppingCartDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User authorizedUser = LoginServlet.getAuthorizedUser(request);
        Locale locale = LoginServlet.getUserLocale(request);

        String respJson = handleRequest(request, authorizedUser, locale);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/carts.html");
    }

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param request        request from the page to the server
     * @param authorizedUser authorized store user
     *
     * @return the server's response to the page
     *
     * @throws IOException I/O exception of some sort has occurred
     */
    private String handleRequest(HttpServletRequest request, User authorizedUser, Locale locale) throws IOException {
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";
        List<ShoppingCart> userCart = null;
        List<ShoppingCart> carts = cartDAO.findAll();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();
        if (json != null) {
            CartJsonDataPackage requestJson = gson.fromJson(json, CartJsonDataPackage.class);
            Validator validator = new Validator();

            if (requestJson != null && requestJson.getEntitiesToEdit().size() > 0) {
                StringBuilder success = new StringBuilder();
                StringBuilder failed = new StringBuilder();
                for (String cartId : requestJson.getEntitiesToEdit()) {
                    for (ShoppingCart cart : carts) {
                        if (validator.getNumber(cartId, -1) == cart.getId()) {
                            boolean isSuccessfully = cartDAO.delete(cart);
                            String name = cart.getProductName();
                            if (isSuccessfully) {
                                success.append(name).append(MessagesManager.getMessage(ENTITY_SUCCESSFULLY_DELETED, locale));
                            } else {
                                failed.append(name).append(MessagesManager.getMessage(ENTITY_NOT_DELETED, locale));
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
        carts = cartDAO.findAll();

        if (authorizedUser == null || authorizedUser.getGroup() != UserGroup.ADMINISTRATOR) {
            userCart = new ArrayList<>();
            int userId = authorizedUser == null ? -1 : authorizedUser.getId();
            for (ShoppingCart cart : carts) {
                if (cart.getUserId() == userId) {
                    userCart.add(cart);
                }
            }
            carts = null;
        }

        CartJsonDataPackage responseJson = new CartJsonDataPackage(carts, userCart, messageSuccess, messageFailed, SELECT);
        return gson.toJson(responseJson);
    }
}
