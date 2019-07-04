package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;
import com.epam.training.onlineshop.utils.json.ProductJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * The servlet is responsible for displaying and removing products store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowProductsServlet", urlPatterns = "/showProductsServlet")
public class ShowProductsServlet extends ShowServlet<Product> {

    /* Working with products in database */
    private AbstractDAO<Product> productDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        productDAO = mysqlFactory.getProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Locale locale = LoginServlet.getUserLocale(request);
        Gson gson = new Gson();
        ProductJsonDataPackage requestJson = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();
        if (json != null) {
            requestJson = gson.fromJson(json, ProductJsonDataPackage.class);
        }

        JsonDataPackage<Product> responseJson = getResponse(productDAO, requestJson, locale);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/products.html");
    }
}
