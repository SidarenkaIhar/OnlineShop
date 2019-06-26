package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.order.OrderedProduct;
import com.epam.training.onlineshop.utils.json.OrderedProductJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * Servlet responsible for adding a new ordered Product to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditOrderedProductServlet", urlPatterns = "/editOrderedProductServlet")
public class EditOrderedProductServlet extends EditServlet<OrderedProduct> {
    private AbstractDAO<OrderedProduct> orderedProductDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        orderedProductDAO = mysqlFactory.getOrderedProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        OrderedProductJsonDataPackage requestJson = null;
        OrderedProduct emptyOrderedProduct = new OrderedProduct(new BigDecimal(0));

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();
        if (json != null) {
            requestJson = gson.fromJson(json, OrderedProductJsonDataPackage.class);
        }
        OrderedProductJsonDataPackage responseJson = getResponse(orderedProductDAO, requestJson, emptyOrderedProduct, new OrderedProductJsonDataPackage());
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/editorderedproduct.html");
    }
}
