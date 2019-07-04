package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.entity.order.Payment;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;
import com.epam.training.onlineshop.utils.json.PaymentJsonDataPackage;
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
 * The servlet is responsible for displaying and removing payments store
 *
 * @author Ihar Sidarenka
 * @version 0.1 25-May-19
 */
@WebServlet(name = "ShowPaymentsServlet", urlPatterns = "/showPaymentsServlet")
public class ShowPaymentsServlet extends ShowServlet<Payment> {

    /* Working with payments in database */
    private AbstractDAO<Payment> paymentDAO;

    public void init() {
        DAOFactory mysqlFactory = getDAOFactory(MYSQL);
        paymentDAO = mysqlFactory.getPaymentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Locale locale = LoginServlet.getUserLocale(request);
        Gson gson = new Gson();
        PaymentJsonDataPackage requestJson = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String json = br.readLine();
        if (json != null) {
            requestJson = gson.fromJson(json, PaymentJsonDataPackage.class);
        }

        JsonDataPackage<Payment> responseJson = getResponse(paymentDAO, requestJson, locale);
        String respJson = gson.toJson(responseJson);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/html/admin/payments.html");
    }
}
