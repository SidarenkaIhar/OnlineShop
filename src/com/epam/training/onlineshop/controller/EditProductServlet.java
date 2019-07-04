package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.utils.json.ProductJsonDataPackage;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * Servlet responsible for adding a new product to the store or editing an existing one
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
@WebServlet(name = "EditProductServlet", urlPatterns = "/editProductServlet")
public class EditProductServlet extends EditServlet<Product> {

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
        Product emptyProduct = new Product("", "", "", new BigDecimal(0), "");

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();
        if (json != null) {
            requestJson = gson.fromJson(json, ProductJsonDataPackage.class);
        }
        ProductJsonDataPackage responseJson = getResponse(productDAO, requestJson, emptyProduct, new ProductJsonDataPackage(), locale);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/editproduct.html");
    }
}
