package com.epam.training.onlineshop.configuration;

import java.util.ResourceBundle;

import static com.epam.training.onlineshop.configuration.ConfigurationManager.getInstance;

/**
 * Loads queries to the database for all online store entities.
 *
 * @author Ihar Sidarenka
 * @version 0.1 22-Apr-19
 */
public class MysqlQueries {

    /** Path to the configuration file with database queries */
    private static final String MYSQL_QUERY_PROPERTIES_PATH;

    private static final ResourceBundle RESOURCE_BUNDLE;

    // MySQL queries for categories
    public static final String MYSQL_INSERT_NEW_CATEGORY;
    public static final String MYSQL_SELECT_ALL_CATEGORIES;
    public static final String MYSQL_SELECT_CATEGORY;
    public static final String MYSQL_UPDATE_CATEGORY;
    public static final String MYSQL_DELETE_CATEGORY;

    // MySQL queries for manufacturers
    public static final String MYSQL_INSERT_NEW_MANUFACTURER;
    public static final String MYSQL_SELECT_ALL_MANUFACTURERS;
    public static final String MYSQL_SELECT_MANUFACTURER;
    public static final String MYSQL_UPDATE_MANUFACTURER;
    public static final String MYSQL_DELETE_MANUFACTURER;

    // MySQL queries for products
    public static final String MYSQL_INSERT_NEW_PRODUCT;
    public static final String MYSQL_SELECT_ALL_PRODUCTS;
    public static final String MYSQL_SELECT_PRODUCT;
    public static final String MYSQL_UPDATE_PRODUCT;
    public static final String MYSQL_DELETE_PRODUCT;

    // MySQL queries for orders
    public static final String MYSQL_INSERT_NEW_ORDER;
    public static final String MYSQL_SELECT_ALL_ORDERS;
    public static final String MYSQL_SELECT_ORDER;
    public static final String MYSQL_UPDATE_ORDER;
    public static final String MYSQL_DELETE_ORDER;

    // MySQL queries for ordered products
    public static final String MYSQL_INSERT_NEW_ORDER_PRODUCT;
    public static final String MYSQL_SELECT_ALL_ORDER_PRODUCTS;
    public static final String MYSQL_SELECT_ORDER_PRODUCT;
    public static final String MYSQL_UPDATE_ORDER_PRODUCT;
    public static final String MYSQL_DELETE_ORDER_PRODUCT;

    // MySQL queries for payments
    public static final String MYSQL_INSERT_NEW_PAYMENT;
    public static final String MYSQL_SELECT_ALL_PAYMENTS;
    public static final String MYSQL_SELECT_PAYMENT;
    public static final String MYSQL_UPDATE_PAYMENT;
    public static final String MYSQL_DELETE_PAYMENT;

    // MySQL queries for carts
    public static final String MYSQL_INSERT_NEW_CART;
    public static final String MYSQL_SELECT_ALL_CARTS;
    public static final String MYSQL_SELECT_CART;
    public static final String MYSQL_UPDATE_CART;
    public static final String MYSQL_DELETE_CART;

    // MySQL queries for pages
    public static final String MYSQL_INSERT_NEW_PAGE;
    public static final String MYSQL_SELECT_ALL_PAGES;
    public static final String MYSQL_SELECT_PAGE;
    public static final String MYSQL_UPDATE_PAGE;
    public static final String MYSQL_DELETE_PAGE;

    // MySQL queries for users
    public static final String MYSQL_INSERT_NEW_USER;
    public static final String MYSQL_SELECT_ALL_USERS;
    public static final String MYSQL_SELECT_USER;
    public static final String MYSQL_UPDATE_USER;
    public static final String MYSQL_DELETE_USER;

    static {
        MYSQL_QUERY_PROPERTIES_PATH = "resources.mysqlQuery";
        RESOURCE_BUNDLE = getInstance().getResourceBundle(MYSQL_QUERY_PROPERTIES_PATH);
        // MySQL queries for categories
        MYSQL_INSERT_NEW_CATEGORY = getProperty("mysql.insert.new.category");
        MYSQL_SELECT_ALL_CATEGORIES = getProperty("mysql.select.all.categories");
        MYSQL_SELECT_CATEGORY = getProperty("mysql.select.category");
        MYSQL_UPDATE_CATEGORY = getProperty("mysql.update.category");
        MYSQL_DELETE_CATEGORY = getProperty("mysql.delete.category");
        // MySQL queries for manufacturers
        MYSQL_INSERT_NEW_MANUFACTURER = getProperty("mysql.insert.new.manufacturer");
        MYSQL_SELECT_ALL_MANUFACTURERS = getProperty("mysql.select.all.manufacturers");
        MYSQL_SELECT_MANUFACTURER = getProperty("mysql.select.manufacturer");
        MYSQL_UPDATE_MANUFACTURER = getProperty("mysql.update.manufacturer");
        MYSQL_DELETE_MANUFACTURER = getProperty("mysql.delete.manufacturer");
        // MySQL queries for products
        MYSQL_INSERT_NEW_PRODUCT = getProperty("mysql.insert.new.product");
        MYSQL_SELECT_ALL_PRODUCTS = getProperty("mysql.select.all.products");
        MYSQL_SELECT_PRODUCT = getProperty("mysql.select.product");
        MYSQL_UPDATE_PRODUCT = getProperty("mysql.update.product");
        MYSQL_DELETE_PRODUCT = getProperty("mysql.delete.product");
        // MySQL queries for orders
        MYSQL_INSERT_NEW_ORDER = getProperty("mysql.insert.new.order");
        MYSQL_SELECT_ALL_ORDERS = getProperty("mysql.select.all.orders");
        MYSQL_SELECT_ORDER = getProperty("mysql.select.order");
        MYSQL_UPDATE_ORDER = getProperty("mysql.update.order");
        MYSQL_DELETE_ORDER = getProperty("mysql.delete.order");
        // MySQL queries for ordered products
        MYSQL_INSERT_NEW_ORDER_PRODUCT = getProperty("mysql.insert.new.order.product");
        MYSQL_SELECT_ALL_ORDER_PRODUCTS = getProperty("mysql.select.all.order.products");
        MYSQL_SELECT_ORDER_PRODUCT = getProperty("mysql.select.order.product");
        MYSQL_UPDATE_ORDER_PRODUCT = getProperty("mysql.update.order.product");
        MYSQL_DELETE_ORDER_PRODUCT = getProperty("mysql.delete.order.product");
        // MySQL queries for payments
        MYSQL_INSERT_NEW_PAYMENT = getProperty("mysql.insert.new.payment");
        MYSQL_SELECT_ALL_PAYMENTS = getProperty("mysql.select.all.payments");
        MYSQL_SELECT_PAYMENT = getProperty("mysql.select.payment");
        MYSQL_UPDATE_PAYMENT = getProperty("mysql.update.payment");
        MYSQL_DELETE_PAYMENT = getProperty("mysql.delete.payment");
        // MySQL queries for carts
        MYSQL_INSERT_NEW_CART = getProperty("mysql.insert.new.cart");
        MYSQL_SELECT_ALL_CARTS = getProperty("mysql.select.all.carts");
        MYSQL_SELECT_CART = getProperty("mysql.select.cart");
        MYSQL_UPDATE_CART = getProperty("mysql.update.cart");
        MYSQL_DELETE_CART = getProperty("mysql.delete.cart");
        // MySQL queries for pages
        MYSQL_INSERT_NEW_PAGE = getProperty("mysql.insert.new.page");
        MYSQL_SELECT_ALL_PAGES = getProperty("mysql.select.all.pages");
        MYSQL_SELECT_PAGE = getProperty("mysql.select.page");
        MYSQL_UPDATE_PAGE = getProperty("mysql.update.page");
        MYSQL_DELETE_PAGE = getProperty("mysql.delete.page");
        // MySQL queries for users
        MYSQL_INSERT_NEW_USER = getProperty("mysql.insert.new.user");
        MYSQL_SELECT_ALL_USERS = getProperty("mysql.select.all.users");
        MYSQL_SELECT_USER = getProperty("mysql.select.user");
        MYSQL_UPDATE_USER = getProperty("mysql.update.user");
        MYSQL_DELETE_USER = getProperty("mysql.delete.user");
    }

    /**
     * Returns the property corresponding to the key from the ResourceBundle
     *
     * @param key name corresponding to the required property
     *
     * @return the property corresponding to the key
     */
    private static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
