package com.epam.training.onlineshop.controller;

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
import java.util.List;

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
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();

        String respJson = handleRequest(json);

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
     * @param json request from the page to the server
     *
     * @return the server's response to the page
     */
    private String handleRequest(String json) {
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
                            messageSuccess = "The product worth $" + cart.getProductPrice() + " successfully added to the shopping cart #" + cart.getId();
                        } else {
                            messageFailed = "The product worth $" + cart.getProductPrice() + " is not added to the shopping cart #" + cart.getId();
                        }
                        break;
                    }
                }
                if (!isExist) {
                    boolean isSuccessfully = cartDAO.addNew(editableCart);
                    if (isSuccessfully) {
                        messageSuccess = "New cart added successfully!";
                        editableCart = new ShoppingCart(new BigDecimal(0));
                    } else {
                        messageFailed = "New cart is not added to the database!";
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
                    messageSuccess = "The Cart #" + editableCart.getId() + " is successfully updated!";
                } else {
                    messageFailed = "The Cart #" + editableCart.getId() + " is not updated in the database!";
                }
            }
        }

        CartJsonDataPackage responseJson = new CartJsonDataPackage(null, null, null, null, editableCart, messageSuccess, messageFailed, typeOperation);

        return gson.toJson(responseJson);
    }
}
