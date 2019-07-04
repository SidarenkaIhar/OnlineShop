package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.order.Order;
import com.epam.training.onlineshop.entity.order.OrderedProduct;
import com.epam.training.onlineshop.entity.order.Payment;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.entity.user.UserGroup;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.PaymentJsonDataPackage;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;
import static com.epam.training.onlineshop.dao.StatementType.*;

/**
 * Servlet responsible for adding a new payment to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditPaymentServlet", urlPatterns = "/editPaymentServlet")
public class EditPaymentServlet extends HttpServlet {

    /* Working with payments in database */
    private AbstractDAO<Payment> paymentDAO;

    /* Working with carts in database */
    private AbstractDAO<ShoppingCart> cartDAO;

    /* Working with orders in database */
    private AbstractDAO<Order> orderDAO;

    /* Working with ordered products in database */
    private AbstractDAO<OrderedProduct> orderedProductDAO;

    /* Checks data for validity */
    private Validator validator;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        paymentDAO = mysqlFactory.getPaymentDAO();
        cartDAO = mysqlFactory.getShoppingCartDAO();
        orderDAO = mysqlFactory.getOrderDAO();
        orderedProductDAO = mysqlFactory.getOrderedProductDAO();
        validator = new Validator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User authorizedUser = LoginServlet.getAuthorizedUser(request);
        Locale locale = LoginServlet.getUserLocale(request);

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();

        String respJson = handleRequest(authorizedUser, json, locale);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/editpayment.html");
    }

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param authorizedUser authorized store user
     * @param json           request from the page to the server
     * @param locale         language for displaying messages to the user
     *
     * @return the server's response to the page
     */
    private String handleRequest(User authorizedUser, String json, Locale locale) {
        Gson gson = new Gson();
        List<Payment> payments = paymentDAO.findAll();
        Payment editablePayment = null;
        String messageSuccess = "";
        String messageFailed = "";
        StatementType typeOperation = INSERT;

        if (json != null) {
            PaymentJsonDataPackage requestJson = gson.fromJson(json, PaymentJsonDataPackage.class);
            if (requestJson.getTypeOperation() == INSERT) {
                editablePayment = requestJson.getEditableEntity();
                if (authorizedUser == null || authorizedUser.getGroup() != UserGroup.ADMINISTRATOR) {
                    int userId = authorizedUser == null ? -1 : authorizedUser.getId();
                    if (userId >= 0) {
                        if (authorizedUser.isEnabled()) {
                            boolean areProductsPurchased;
                            editablePayment.setUserId(userId);
                            BigDecimal totalAmount = getTotalAmount(userId);
                            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                                int paymentId = getPaymentId(editablePayment);
                                if (paymentId < 0) {
                                    paymentDAO.addNew(editablePayment);
                                    paymentId = getPaymentId(editablePayment);
                                }
                                Order order = new Order(userId, totalAmount, paymentId);
                                areProductsPurchased = orderDAO.addNew(order);
                                int orderId = getOrderId(order);

                                areProductsPurchased &= paymentProducts(userId, orderId);
                                if (areProductsPurchased) {
                                    messageSuccess = MessagesManager.getMessage(ORDERED_SUCCESSFULLY, locale);
                                } else {
                                    messageFailed = MessagesManager.getMessage(ORDER_NOT_PROCESSED, locale);
                                }
                            } else {
                                messageFailed = MessagesManager.getMessage(NO_ITEMS_IN_CART, locale);
                            }
                        } else {
                            messageFailed = MessagesManager.getMessage(BLOCKED, locale);
                        }
                    } else {
                        messageFailed = MessagesManager.getMessage(NEED_TO_LOGIN, locale);
                    }
                } else {
                    if (getPaymentId(editablePayment) >= 0) {
                        messageFailed = MessagesManager.getMessage(ALREADY_EXIST, locale);
                    } else {
                        editablePayment.setCreationDate(new Date());
                        boolean isSuccessfully = paymentDAO.addNew(editablePayment);
                        if (isSuccessfully) {
                            messageSuccess = MessagesManager.getMessage(ENTITY_ADDED_SUCCESSFULLY, locale);
                            editablePayment = new Payment("", "", "", "", "", "");
                        } else {
                            messageFailed = MessagesManager.getMessage(ENTITY_NOT_ADDED, locale);
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == SELECT) {
                if (requestJson.getEntitiesToEdit().size() > 0) {
                    int paymentId = validator.getNumber(requestJson.getEntitiesToEdit().get(0), -1);
                    for (Payment payment : payments) {
                        if (payment.getId() == paymentId) {
                            editablePayment = payment;
                            typeOperation = UPDATE;
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == UPDATE) {
                editablePayment = requestJson.getEditableEntity();
                typeOperation = UPDATE;
                boolean isSuccessfully = paymentDAO.update(editablePayment);
                if (isSuccessfully) {
                    messageSuccess = MessagesManager.getMessage(ENTITY_SUCCESSFULLY_UPDATED, locale);
                } else {
                    messageFailed = MessagesManager.getMessage(ENTITY_NOT_UPDATED, locale);
                }
            }
        } else if (authorizedUser == null || authorizedUser.getGroup() != UserGroup.ADMINISTRATOR) {
            int userId = authorizedUser == null ? -1 : authorizedUser.getId();
            BigDecimal totalAmount = getTotalAmount(userId);
            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                messageSuccess = MessagesManager.getMessage(TOTAL_AMOUNT, locale) + totalAmount;
            } else {
                messageFailed = MessagesManager.getMessage(NO_ITEMS_IN_CART, locale);
            }
        }

        PaymentJsonDataPackage responseJson = new PaymentJsonDataPackage(editablePayment, messageSuccess, messageFailed, typeOperation);

        return gson.toJson(responseJson);
    }

    /**
     * Counts the total cost of products in the shopping cart
     *
     * @param userId the owner ID of the basket
     *
     * @return the total cost of products in the shopping cart
     */
    private BigDecimal getTotalAmount(int userId) {
        List<ShoppingCart> carts = cartDAO.findAll();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ShoppingCart cart : carts) {
            if (cart.getUserId() == userId) {
                totalAmount = totalAmount.add(cart.getProductPrice().multiply(new BigDecimal(cart.getProductQuantity())));
            }
        }
        return totalAmount;
    }

    /**
     * Checks whether the payment exists in the database
     *
     * @param payment payment to be checked
     *
     * @return returns the payment ID if it exists
     */
    private int getPaymentId(Payment payment) {
        List<Payment> payments = paymentDAO.findAll();
        for (Payment paym : payments) {
            if (paym.equals(payment)) {
                return paym.getId();
            }
        }
        return -1;
    }

    /**
     * Checks whether the order exists in the database
     *
     * @param order order to be checked
     *
     * @return returns the order ID if it exists
     */
    private int getOrderId(Order order) {
        List<Order> orders = orderDAO.findAll();
        for (Order ord : orders) {
            if (ord.equals(order)) {
                return ord.getId();
            }
        }
        return -1;
    }

    /**
     * Ordered items are removed from the cart and added to the order
     *
     * @param userId  the owner ID of the basket
     * @param orderId order ID
     *
     * @return {@code true}  if items are successfully ordered, {@code false} otherwise
     */
    private boolean paymentProducts(int userId, int orderId) {
        List<ShoppingCart> carts = cartDAO.findAll();
        boolean areProductsPurchased = true;
        for (ShoppingCart cart : carts) {
            if (cart.getUserId() == userId) {
                OrderedProduct product = new OrderedProduct(orderId, cart.getProductId(), cart.getProductPrice(), cart.getProductQuantity());
                areProductsPurchased &= orderedProductDAO.addNew(product);
                areProductsPurchased &= cartDAO.delete(cart);
            }
        }
        return areProductsPurchased;
    }
}
