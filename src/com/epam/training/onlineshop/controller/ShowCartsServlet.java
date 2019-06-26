package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.catalog.Product;
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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    /* Working with products in database */
    private AbstractDAO<Product> productDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        cartDAO = mysqlFactory.getShoppingCartDAO();
        productDAO = mysqlFactory.getProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User authorizedUser = (User) session.getAttribute("User");

        String respJson = handleRequest(request, authorizedUser);

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
    private String handleRequest(HttpServletRequest request, User authorizedUser) throws IOException {
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";
        List<ShoppingCart> userCart = null;
        List<ShoppingCart> carts = cartDAO.findAll();
        List<Product> products = productDAO.findAll();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
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
                            if (isSuccessfully) {
                                success.append("The product ").append(getProductNameById(products, cart.getProductId()))
                                        .append(" is successfully deleted. <br>");
                            } else {
                                failed.append("The product ").append(getProductNameById(products, cart.getProductId()))
                                        .append(" was not deleted. <br>");
                            }
                        }
                    }
                }
                messageSuccess = success.length() > 0 ? success.toString() : "";
                messageFailed = failed.length() > 0 ? failed.toString() : "";
            } else {
                messageFailed = "You must select at least one cart!";
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

        CartJsonDataPackage responseJson = new CartJsonDataPackage(carts, userCart, products, messageSuccess,
                                                                   messageFailed, SELECT);
        return gson.toJson(responseJson);
    }

    /**
     * Returns the name of the product by its ID
     *
     * @param products all products of the online store
     * @param id       id of the product whose name you need to find
     *
     * @return product name
     */
    private String getProductNameById(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product.getName();
            }
        }
        return "";
    }
}
