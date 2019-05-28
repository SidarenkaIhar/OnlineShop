package com.epam.training.onlineshop.dao.mysql.entity.order;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.order.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with orders in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class OrderDAOImpl extends AbstractDAO<Order> {

    /*  List of orders found on request in the database */
    private List<Order> orders;

    /**
     * Adding a new order to the database
     *
     * @param order the order added to database
     *
     * @return {@code true} if the order is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Order order) {
        return getInstance().executeDatabaseQuery(INSERT, this, order, MYSQL_INSERT_NEW_ORDER);
    }

    /**
     * Search for a order with specified parameters in the database
     *
     * @param order the order to find
     *
     * @return list of found orders
     */
    @Override
    public List<Order> find(Order order) {
        return findOrder(MYSQL_SELECT_ORDER + order.getId());
    }

    /**
     * Returns all orders in the database
     *
     * @return list of found orders
     */
    @Override
    public List<Order> findAll() {
        return findOrder(MYSQL_SELECT_ALL_ORDERS);
    }


    /**
     * The method produces a search of orders according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all orders found
     */
    private List<Order> findOrder(String sql) {
        orders = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return orders;
    }

    /**
     * Edit order settings in database
     *
     * @param order the order with the edited parameters
     *
     * @return {@code true} if order successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Order order) {
        return getInstance().executeDatabaseQuery(UPDATE, this, order, MYSQL_UPDATE_ORDER);
    }

    /**
     * Removes a order from the database
     *
     * @param order order whose you want to delete
     *
     * @return {@code true} if the order is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Order order) {
        return getInstance().executeDatabaseQuery(DELETE, this, order, MYSQL_DELETE_ORDER);
    }

    /**
     * Adds orders to the list of orders from the set
     *
     * @param resultSet set of orders found in the database
     *
     * @return {@code true} if the orders is successfully added
     * to the list of orders, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the orders to
     *                      the list of orders
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            Order newOrder = new Order();
            newOrder.setId(resultSet.getInt("order_id"));
            newOrder.setUserId(resultSet.getInt("user_id"));
            newOrder.setAmount(resultSet.getBigDecimal("amount"));
            newOrder.setPaymentId(resultSet.getInt("payment_id"));
            newOrder.setCreationDate(resultSet.getTimestamp("date_added"));
            newOrder.setOrderStatus(resultSet.getInt("order_status_id"));
            newOrder.setDateStatusChange(resultSet.getTimestamp("date_modified"));
            newOrder.setTrackingNumber(resultSet.getString("tracking_number"));
            orders.add(newOrder);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new order
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param order             the order you want to add to the database
     *
     * @return {@code true} if the order is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a order to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(1, order.getUserId());
        preparedStatement.setBigDecimal(2, order.getAmount());
        preparedStatement.setInt(3, order.getPaymentId());
        // convert java.util.Date to java.sql.Date
        preparedStatement.setTimestamp(4, new Timestamp(order.getCreationDate().getTime()));
        preparedStatement.setInt(5, order.getOrderStatus().getOrderStatusId());
        // convert java.util.Date to java.sql.Date
        preparedStatement.setTimestamp(6, new Timestamp(order.getDateStatusChange().getTime()));
        preparedStatement.setString(7, order.getTrackingNumber());
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the order
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param order             the order you want to update in the database
     *
     * @return {@code true} if the order is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the order in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(8, order.getId());
        return executeInsertRequest(preparedStatement, order);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the order
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param order             the order you want to remove from the database
     *
     * @return {@code true} if the order is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the order from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(1, order.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
