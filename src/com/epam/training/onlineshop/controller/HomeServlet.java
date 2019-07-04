package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.json.HomeJsonDataPackage;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;


import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.INSERT;
import static com.epam.training.onlineshop.dao.StatementType.UPDATE;
import static com.epam.training.onlineshop.entity.user.UserGroup.ADMINISTRATOR;

/**
 * The servlet is responsible for displaying and removing products store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "HomeServlet", urlPatterns = "/homeServlet")
public class HomeServlet extends HttpServlet {

    /* Working with products in database */
    private AbstractDAO<Product> productDAO;

    /* Working with shopping carts in database */
    private AbstractDAO<ShoppingCart> cartDAO;

    /* Working with users in database */
    private UserDAO userDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        productDAO = mysqlFactory.getProductDAO();
        cartDAO = mysqlFactory.getShoppingCartDAO();
        userDAO = mysqlFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User authorizedUser = LoginServlet.getAuthorizedUser(request);
        Locale locale = LoginServlet.getUserLocale(request);
        String userLogin = authorizedUser == null ? null : authorizedUser.getName();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();

        String respJson = handleRequest(json, authorizedUser, userLogin, locale);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param json           request from the page to the server
     * @param authorizedUser authorized store user
     * @param userLogin      user login
     *
     * @return the server's response to the page
     */
    private String handleRequest(String json, User authorizedUser, String userLogin, Locale locale) {
        boolean showAdminMenu = authorizedUser != null && authorizedUser.getGroup() == ADMINISTRATOR;
        List<Product> products = productDAO.findAll();
        List<ShoppingCart> carts = cartDAO.findAll();
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";

        if (json != null) {
            HomeJsonDataPackage requestJson = gson.fromJson(json, HomeJsonDataPackage.class);
            if (authorizedUser == null) {
                messageFailed = MessagesManager.getMessage(NEED_TO_LOGIN, locale);
            } else {
                if (requestJson.getTypeOperation() == INSERT) {
                    ShoppingCart shoppingCart = requestJson.getShoppingCart();
                    shoppingCart.setUserId(authorizedUser.getId());
                    boolean isExist = false;
                    boolean isSuccessfully = false;
                    for (ShoppingCart cart : carts) {
                        if (cart.equals(shoppingCart)) {
                            isExist = true;
                            cart.setProductQuantity(cart.getProductQuantity() + shoppingCart.getProductQuantity());
                            isSuccessfully = cartDAO.update(cart);
                            break;
                        }
                    }
                    if (!isExist) {
                        isSuccessfully = cartDAO.addNew(shoppingCart);
                    }
                    if (isSuccessfully) {
                        messageSuccess = shoppingCart.getProductName() + MessagesManager.getMessage(ADDED_TO_CART, locale);
                    } else {
                        messageFailed = shoppingCart.getProductName() + MessagesManager.getMessage(NOT_ADDED_TO_CART, locale);
                    }
                } else if (requestJson.getTypeOperation() == UPDATE) {
                    locale = requestJson.getUserLanguage();
                    if (!authorizedUser.getLocale().equals(locale)) {
                        authorizedUser.setLocale(locale);
                        userDAO.update(authorizedUser);
                    }
                }
            }
        }

        HomeJsonDataPackage responseJson = new HomeJsonDataPackage(userLogin, showAdminMenu, products, messageSuccess, messageFailed, locale);

        return gson.toJson(responseJson);
    }
}
