package com.epam.training.onlineshop.dao.mysql.entity.order;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.order.OrderProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with ordered products in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class OrderProductDAOImpl extends AbstractDAO<OrderProduct> {

    /*  List of ordered products found on request in the database */
    private List<OrderProduct> products;

    /**
     * Adding a new ordered product to the database
     *
     * @param product the ordered product added to database
     *
     * @return {@code true} if the ordered product is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(OrderProduct product) {
        return getInstance().executeDatabaseQuery(INSERT, this, product, MYSQL_INSERT_NEW_ORDER_PRODUCT);
    }

    /**
     * Search for a ordered product with specified parameters in the database
     *
     * @param product the ordered product to find
     *
     * @return list of found ordered products
     */
    @Override
    public List<OrderProduct> find(OrderProduct product) {
        return findOrderProduct(MYSQL_SELECT_ORDER_PRODUCT + product.getId());
    }

    /**
     * Returns all ordered products in the database
     *
     * @return list of found ordered products
     */
    @Override
    public List<OrderProduct> findAll() {
        return findOrderProduct(MYSQL_SELECT_ALL_ORDER_PRODUCTS);
    }


    /**
     * The method produces a search of ordered products according to the specified
     * parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all ordered products found
     */
    private List<OrderProduct> findOrderProduct(String sql) {
        products = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return products;
    }

    /**
     * Edit ordered product settings in database
     *
     * @param product the ordered product with the edited parameters
     *
     * @return {@code true} if ordered product successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(OrderProduct product) {
        return getInstance().executeDatabaseQuery(UPDATE, this, product, MYSQL_UPDATE_ORDER_PRODUCT);
    }

    /**
     * Removes a ordered product from the database
     *
     * @param product ordered product whose you want to delete
     *
     * @return {@code true} if the ordered product is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(OrderProduct product) {
        return getInstance().executeDatabaseQuery(DELETE, this, product, MYSQL_DELETE_ORDER_PRODUCT);
    }

    /**
     * Adds products to the list of products from the set
     *
     * @param resultSet set of products found in the database
     *
     * @return {@code true} if the products is successfully added
     * to the list of products, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the products to
     *                      the list of products
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            OrderProduct newOrderProduct = new OrderProduct();
            newOrderProduct.setId(resultSet.getInt("product_id"));
            newOrderProduct.setOrderId(resultSet.getInt("order_id"));
            newOrderProduct.setProductId(resultSet.getInt("product_id"));
            newOrderProduct.setProductPrice(resultSet.getBigDecimal("price"));
            newOrderProduct.setProductQuantity(resultSet.getInt("quantity"));
            products.add(newOrderProduct);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new product
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param product           the product you want to add to the database
     *
     * @return {@code true} if the product is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a product to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, OrderProduct product) throws SQLException {
        preparedStatement.setInt(1, product.getOrderId());
        preparedStatement.setInt(2, product.getProductId());
        preparedStatement.setBigDecimal(3, product.getProductPrice());
        preparedStatement.setInt(4, product.getProductQuantity());
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the product
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param product           the product you want to update in the database
     *
     * @return {@code true} if the product is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the product in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, OrderProduct product) throws SQLException {
        preparedStatement.setInt(5, product.getId());
        return executeInsertRequest(preparedStatement, product);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the product
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param product           the product you want to remove from the database
     *
     * @return {@code true} if the product is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the product from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, OrderProduct product) throws SQLException {
        preparedStatement.setInt(1, product.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
