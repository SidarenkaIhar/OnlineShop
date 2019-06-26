package com.epam.training.onlineshop.dao;

import com.epam.training.onlineshop.dao.mysql.MysqlDAOFactory;
import com.epam.training.onlineshop.entity.catalog.Category;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.Order;
import com.epam.training.onlineshop.entity.order.OrderedProduct;
import com.epam.training.onlineshop.entity.order.Payment;
import com.epam.training.onlineshop.entity.order.ShoppingCart;

/**
 * Abstract dao factory
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public abstract class DAOFactory {

    // List of DAO types supported by the generator
    public static final int MYSQL = 1;
    public static final int POSTGRE_SQL = 2;
    public static final int MICROSOFT_SQL = 3;

    /**
     * Returns the factory corresponding to the dao index
     *
     * @param whichFactory index of a dao factory
     *
     * @return the corresponding index of the dao factory
     */
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return new MysqlDAOFactory();
            case POSTGRE_SQL:
            case MICROSOFT_SQL:
            default:
                return null;
        }
    }

    /*  Returns the corresponding dao category of the database */
    public abstract AbstractDAO<Category> getCategoryDAO();

    /*  Returns the corresponding dao product of the database */
    public abstract AbstractDAO<Product> getProductDAO();

    /*  Returns the corresponding dao order of the database */
    public abstract AbstractDAO<Order> getOrderDAO();

    /*  Returns the corresponding dao ordered product of the database */
    public abstract AbstractDAO<OrderedProduct> getOrderedProductDAO();

    /*  Returns the corresponding dao payment of the database */
    public abstract AbstractDAO<Payment> getPaymentDAO();

    /*  Returns the corresponding dao category of the database */
    public abstract AbstractDAO<ShoppingCart> getShoppingCartDAO();

    /*  Returns the corresponding dao user of the database */
    public abstract UserDAO getUserDAO();
}
