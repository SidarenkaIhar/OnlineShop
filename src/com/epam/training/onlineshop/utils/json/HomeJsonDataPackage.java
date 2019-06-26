package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.ShoppingCart;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the products
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class HomeJsonDataPackage extends JsonDataPackage<Product> {

    /* Authorized user login */
    private String userLogin;

    /* Whether to display admin menu */
    private boolean showAdminMenu;

    /* All products online store */
    private List<Product> productCatalog;

    /* User's shopping cart */
    private ShoppingCart shoppingCart;

    public HomeJsonDataPackage(String userLogin, boolean showAdminMenu, List<Product> productCatalog, String messageSuccess, String messageFailed) {
        super(null, null, null, messageSuccess, messageFailed, null);
        this.userLogin = userLogin;
        this.showAdminMenu = showAdminMenu;
        this.productCatalog = productCatalog;
    }

    public HomeJsonDataPackage(String userLogin, boolean showAdminMenu, List<Product> entitiesToShow, List<String> entitiesToEdit, Product editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation, List<Product> productCatalog) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.userLogin = userLogin;
        this.showAdminMenu = showAdminMenu;
        this.productCatalog = productCatalog;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public boolean isShowAdminMenu() {
        return showAdminMenu;
    }

    public void setShowAdminMenu(boolean showAdminMenu) {
        this.showAdminMenu = showAdminMenu;
    }

    public List<Product> getProductCatalog() {
        return productCatalog;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
