package com.epam.training.onlineshop.dao.mysql.entity.catalog;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.catalog.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with products in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class ProductDAOImpl extends AbstractDAO<Product> {

    /*  List of products found on request in the database */
    private List<Product> products;

    /**
     * Adding a new product to the database
     *
     * @param product the product added to database
     *
     * @return {@code true} if the product is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Product product) {
        return getInstance().executeDatabaseQuery(INSERT, this, product, MYSQL_INSERT_NEW_PRODUCT);
    }

    /**
     * Search for a product with specified parameters in the database
     *
     * @param product the product to find
     *
     * @return list of found products
     */
    @Override
    public List<Product> find(Product product) {
        return findProduct(MYSQL_SELECT_PRODUCT + product.getId());
    }

    /**
     * Returns all products in the database
     *
     * @return list of found products
     */
    @Override
    public List<Product> findAll() {
        return findProduct(MYSQL_SELECT_ALL_PRODUCTS);
    }


    /**
     * The method produces a search of products according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all products found
     */
    private List<Product> findProduct(String sql) {
        products = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return products;
    }

    /**
     * Edit product settings in database
     *
     * @param product the product with the edited parameters
     *
     * @return {@code true} if product successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Product product) {
        return getInstance().executeDatabaseQuery(UPDATE, this, product, MYSQL_UPDATE_PRODUCT);
    }

    /**
     * Removes a product from the database
     *
     * @param product product whose you want to delete
     *
     * @return {@code true} if the product is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Product product) {
        return getInstance().executeDatabaseQuery(DELETE, this, product, MYSQL_DELETE_PRODUCT);
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
            Product newProduct = new Product();
            newProduct.setId(resultSet.getInt("product_id"));
            newProduct.setName(resultSet.getString("name"));
            newProduct.setDescription(resultSet.getString("description"));
            newProduct.setManufacturer(resultSet.getString("manufacturer"));
            newProduct.setCategoryId(resultSet.getInt("category_id"));
            newProduct.setPrice(resultSet.getBigDecimal("price"));
            newProduct.setQuantity(resultSet.getInt("quantity"));
            newProduct.setImage(resultSet.getString("image"));
            newProduct.setCreationDate(resultSet.getTimestamp("date_added"));
            newProduct.setSortOrder(resultSet.getInt("sort_order"));
            newProduct.setEnabled(resultSet.getInt("status"));
            products.add(newProduct);
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
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setString(3, product.getManufacturer());
        preparedStatement.setInt(4, product.getCategoryId());
        preparedStatement.setBigDecimal(5, product.getPrice());
        preparedStatement.setInt(6, product.getQuantity());
        preparedStatement.setString(7, product.getImage());
        preparedStatement.setTimestamp(8, product.getCreationDate());
        preparedStatement.setInt(9, product.getSortOrder());
        preparedStatement.setInt(10, product.isEnabled() ? 1 : 0);
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
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setInt(11, product.getId());
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
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setInt(1, product.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
