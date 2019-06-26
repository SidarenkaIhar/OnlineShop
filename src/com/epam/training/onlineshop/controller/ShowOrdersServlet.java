package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.order.Order;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;
import com.epam.training.onlineshop.utils.json.OrderJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * The servlet is responsible for displaying and removing orders store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowOrdersServlet", urlPatterns = "/showOrdersServlet")
public class ShowOrdersServlet extends ShowServlet<Order> {

    /* Working with orders in database */
    private AbstractDAO<Order> orderDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        orderDAO = mysqlFactory.getOrderDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        OrderJsonDataPackage requestJson = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();

        if (json != null) {
            requestJson = gson.fromJson(json, OrderJsonDataPackage.class);
        }

        JsonDataPackage<Order> responseJson = getResponse(orderDAO, requestJson);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/orders.html");
    }
}
