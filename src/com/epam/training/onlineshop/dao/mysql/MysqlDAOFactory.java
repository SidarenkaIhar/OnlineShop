package com.epam.training.onlineshop.dao.mysql;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.dao.mysql.entity.catalog.CategoryDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.catalog.ManufacturerDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.catalog.ProductDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.order.OrderDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.order.OrderProductDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.order.PaymentDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.order.ShoppingCartDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.page.PageDAOImpl;
import com.epam.training.onlineshop.dao.mysql.entity.user.UserDAOImpl;
import com.epam.training.onlineshop.entity.catalog.Category;
import com.epam.training.onlineshop.entity.catalog.Manufacturer;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.Order;
import com.epam.training.onlineshop.entity.order.OrderProduct;
import com.epam.training.onlineshop.entity.order.Payment;
import com.epam.training.onlineshop.entity.order.ShoppingCart;
import com.epam.training.onlineshop.entity.page.Page;

/**
 * DAO factory working with MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class MysqlDAOFactory extends DAOFactory {

    /*  Returns the corresponding dao category of the MYSQL database */
    @Override
    public AbstractDAO<Category> getCategoryDAO() {
        return new CategoryDAOImpl();
    }

    /*  Returns the corresponding dao category of the MYSQL database */
    @Override
    public AbstractDAO<Manufacturer> getManufacturerDAO() {
        return new ManufacturerDAOImpl();
    }

    /*  Returns the corresponding dao product of the MYSQL database */
    @Override
    public AbstractDAO<Product> getProductDAO() {
        return new ProductDAOImpl();
    }

    /*  Returns the corresponding dao order of the MYSQL database */
    @Override
    public AbstractDAO<Order> getOrderDAO() {
        return new OrderDAOImpl();
    }

    /*  Returns the corresponding dao ordered product of the MYSQL database */
    @Override
    public AbstractDAO<OrderProduct> getOrderProductDAO() {
        return new OrderProductDAOImpl();
    }

    /*  Returns the corresponding dao payment of the MYSQL database */
    @Override
    public AbstractDAO<Payment> getPaymentDAO() {
        return new PaymentDAOImpl();
    }

    /*  Returns the corresponding dao category of the MYSQL database */
    @Override
    public AbstractDAO<ShoppingCart> getShoppingCartDAO() {
        return new ShoppingCartDAOImpl();
    }

    /*  Returns the corresponding dao page of the MYSQL database */
    @Override
    public AbstractDAO<Page> getPageDAO() {
        return new PageDAOImpl();
    }

    /*  Returns the corresponding dao user of the MYSQL database */
    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
