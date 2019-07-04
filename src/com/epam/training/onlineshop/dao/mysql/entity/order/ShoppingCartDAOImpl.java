package com.epam.training.onlineshop.dao.mysql.entity.order;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.order.ShoppingCart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with carts in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class ShoppingCartDAOImpl extends AbstractDAO<ShoppingCart> {

    /*  List of carts found on request in the database */
    private List<ShoppingCart> carts;

    /**
     * Adding a new cart to the database
     *
     * @param cart the cart added to database
     *
     * @return {@code true} if the cart is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(ShoppingCart cart) {
        return getInstance().executeDatabaseQuery(INSERT, this, cart, MYSQL_INSERT_NEW_CART);
    }

    /**
     * Search for a cart with specified parameters in the database
     *
     * @param cart the cart to find
     *
     * @return list of found carts
     */
    @Override
    public List<ShoppingCart> find(ShoppingCart cart) {
        return findShoppingCart(MYSQL_SELECT_CART + cart.getId());
    }

    /**
     * Returns all carts in the database
     *
     * @return list of found carts
     */
    @Override
    public List<ShoppingCart> findAll() {
        return findShoppingCart(MYSQL_SELECT_ALL_CARTS);
    }


    /**
     * The method produces a search of carts according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all carts found
     */
    private List<ShoppingCart> findShoppingCart(String sql) {
        carts = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return carts;
    }

    /**
     * Edit cart settings in database
     *
     * @param cart the cart with the edited parameters
     *
     * @return {@code true} if cart successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(ShoppingCart cart) {
        return getInstance().executeDatabaseQuery(UPDATE, this, cart, MYSQL_UPDATE_CART);
    }

    /**
     * Removes a cart from the database
     *
     * @param cart cart whose you want to delete
     *
     * @return {@code true} if the cart is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(ShoppingCart cart) {
        return getInstance().executeDatabaseQuery(DELETE, this, cart, MYSQL_DELETE_CART);
    }

    /**
     * Adds carts to the list of carts from the set
     *
     * @param resultSet set of carts found in the database
     *
     * @return {@code true} if the carts is successfully added
     * to the list of carts, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the carts to
     *                      the list of carts
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setId(resultSet.getInt("cart_id"));
            newShoppingCart.setUserId(resultSet.getInt("user_id"));
            newShoppingCart.setUserName(resultSet.getString("user_name"));
            newShoppingCart.setProductId(resultSet.getInt("product_id"));
            newShoppingCart.setProductName(resultSet.getString("product_name"));
            newShoppingCart.setProductPrice(resultSet.getBigDecimal("price"));
            newShoppingCart.setProductQuantity(resultSet.getInt("quantity"));
            carts.add(newShoppingCart);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new cart
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param cart              the cart you want to add to the database
     *
     * @return {@code true} if the cart is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a cart to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, ShoppingCart cart) throws SQLException {
        preparedStatement.setInt(1, cart.getUserId());
        preparedStatement.setInt(2, cart.getProductId());
        preparedStatement.setBigDecimal(3, cart.getProductPrice());
        preparedStatement.setInt(4, cart.getProductQuantity());
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the cart
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param cart              the cart you want to update in the database
     *
     * @return {@code true} if the cart is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the cart in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, ShoppingCart cart) throws SQLException {
        preparedStatement.setInt(5, cart.getId());
        return executeInsertRequest(preparedStatement, cart);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the cart
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param cart              the cart you want to remove from the database
     *
     * @return {@code true} if the cart is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the cart from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, ShoppingCart cart) throws SQLException {
        preparedStatement.setInt(1, cart.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
