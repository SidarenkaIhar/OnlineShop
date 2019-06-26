package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.order.OrderedProduct;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;
import com.epam.training.onlineshop.utils.json.OrderedProductJsonDataPackage;
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
 * The servlet is responsible for displaying and removing orderedProducts store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowOrderedProductsServlet", urlPatterns = "/showOrderedProductsServlet")
public class ShowOrderedProductsServlet extends ShowServlet<OrderedProduct> {

    /* Working with ordered products in database */
    private AbstractDAO<OrderedProduct> orderedProductDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        orderedProductDAO = mysqlFactory.getOrderedProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        OrderedProductJsonDataPackage requestJson = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();

        if (json != null) {
            requestJson = gson.fromJson(json, OrderedProductJsonDataPackage.class);
        }

        JsonDataPackage<OrderedProduct> responseJson = getResponse(orderedProductDAO, requestJson);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/orderedproducts.html");
    }
}
