package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
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
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.*;

/**
 * Servlet responsible for adding a new cart to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditCartServlet", urlPatterns = "/editCartServlet")
public class EditCartServlet extends HttpServlet {

    /* Working with carts in database */
    private AbstractDAO<ShoppingCart> cartDAO;

    /* Checks data for validity */
    private Validator validator;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        cartDAO = mysqlFactory.getShoppingCartDAO();
        validator = new Validator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Locale locale = LoginServlet.getUserLocale(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();

        String respJson = handleRequest(json, locale);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/editcart.html");
    }

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param json   request from the page to the server
     * @param locale language for displaying messages to the user
     *
     * @return the server's response to the page
     */
    private String handleRequest(String json, Locale locale) {
        Gson gson = new Gson();
        List<ShoppingCart> carts = cartDAO.findAll();
        ShoppingCart editableCart = null;
        String messageSuccess = "";
        String messageFailed = "";
        StatementType typeOperation = INSERT;

        if (json != null) {
            CartJsonDataPackage requestJson = gson.fromJson(json, CartJsonDataPackage.class);
            if (requestJson.getTypeOperation() == INSERT) {
                editableCart = requestJson.getEditableEntity();
                boolean isExist = false;
                for (ShoppingCart cart : carts) {
                    if (cart.equals(editableCart)) {
                        isExist = true;
                        cart.setProductQuantity(cart.getProductQuantity() + editableCart.getProductQuantity());
                        boolean isSuccessfully = cartDAO.update(cart);
                        if (isSuccessfully) {
                            messageSuccess = cart.getProductName() + MessagesManager.getMessage(ADDED_TO_CART, locale);
                        } else {
                            messageFailed = cart.getProductName() + MessagesManager.getMessage(NOT_ADDED_TO_CART, locale);
                        }
                        break;
                    }
                }
                if (!isExist) {
                    boolean isSuccessfully = cartDAO.addNew(editableCart);
                    if (isSuccessfully) {
                        messageSuccess = MessagesManager.getMessage(ENTITY_ADDED_SUCCESSFULLY, locale);
                        editableCart = new ShoppingCart(new BigDecimal(0));
                    } else {
                        messageFailed = MessagesManager.getMessage(ENTITY_NOT_ADDED, locale);
                    }
                }
            } else if (requestJson.getTypeOperation() == SELECT) {
                if (requestJson.getEntitiesToEdit().size() > 0) {
                    int cartId = validator.getNumber(requestJson.getEntitiesToEdit().get(0), -1);
                    for (ShoppingCart cart : carts) {
                        if (cart.getId() == cartId) {
                            editableCart = cart;
                            typeOperation = UPDATE;
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == UPDATE) {
                editableCart = requestJson.getEditableEntity();
                typeOperation = UPDATE;
                boolean isSuccessfully = cartDAO.update(editableCart);
                if (isSuccessfully) {
                    messageSuccess = "#" + editableCart.getId() + MessagesManager.getMessage(ENTITY_SUCCESSFULLY_UPDATED, locale);
                } else {
                    messageFailed = "#" + editableCart.getId() + MessagesManager.getMessage(ENTITY_NOT_UPDATED, locale);
                }
            }
        }

        CartJsonDataPackage responseJson = new CartJsonDataPackage(editableCart, messageSuccess, messageFailed, typeOperation);

        return gson.toJson(responseJson);
    }
}
