package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.utils.json.HomeJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.INSERT;
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

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        productDAO = mysqlFactory.getProductDAO();
        cartDAO = mysqlFactory.getShoppingCartDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User authorizedUser = (User) session.getAttribute("User");
        String userLogin = authorizedUser == null ? null : authorizedUser.getName();
        boolean showAdminMenu = authorizedUser != null && authorizedUser.getGroup() == ADMINISTRATOR;
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();

        String respJson = handleRequest(json, authorizedUser, userLogin, showAdminMenu);

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
     * @param showAdminMenu  whether to display admin menu
     *
     * @return the server's response to the page
     */
    private String handleRequest(String json, User authorizedUser, String userLogin, boolean showAdminMenu) {
        List<Product> products = productDAO.findAll();
        List<ShoppingCart> carts = cartDAO.findAll();
        Gson gson = new Gson();
        String messageSuccess = "";
        String messageFailed = "";

        if (json != null) {
            HomeJsonDataPackage requestJson = gson.fromJson(json, HomeJsonDataPackage.class);
            if (requestJson.getTypeOperation() == INSERT) {
                if (authorizedUser == null) {
                    messageFailed = "You need to login to purchase products in the store!";
                } else {
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
                        messageSuccess = "New product " + getProductNameById(products, shoppingCart.getProductId()) + " added to you shopping cart successfully!";
                    } else {
                        messageFailed = "New product " + getProductNameById(products, shoppingCart.getProductId()) + " is not added to you shopping cart!";
                    }
                }
            }
        }

        HomeJsonDataPackage responseJson = new HomeJsonDataPackage(userLogin, showAdminMenu, products, messageSuccess, messageFailed);

        return gson.toJson(responseJson);
    }

    /**
     * Returns the name of the product by its ID
     *
     * @param products all products of the online store
     * @param id       the product ID
     *
     * @return name of the product
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
