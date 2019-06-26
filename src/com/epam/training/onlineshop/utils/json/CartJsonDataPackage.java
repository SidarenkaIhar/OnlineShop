package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.catalog.Product;
import com.epam.training.onlineshop.entity.order.ShoppingCart;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the user cart
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class CartJsonDataPackage extends JsonDataPackage<ShoppingCart> {

    /* User's shopping cart */
    private List<ShoppingCart> userCart;

    /* Names of items in user's shopping cart */
    private List<Product> namesProducts;

    public CartJsonDataPackage(List<ShoppingCart> entitiesToShow, List<ShoppingCart> userCart, List<Product> namesProducts, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, messageSuccess, messageFailed, typeOperation);
        this.userCart = userCart;
        this.namesProducts = namesProducts;
    }

    public CartJsonDataPackage(List<ShoppingCart> entitiesToShow, List<ShoppingCart> userCart, List<Product> namesProducts, List<String> entitiesToEdit, ShoppingCart editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.userCart = userCart;
        this.namesProducts = namesProducts;
    }

    public List<ShoppingCart> getUserCart() {
        return userCart;
    }

    public void setUserCart(List<ShoppingCart> userCart) {
        this.userCart = userCart;
    }

    public List<Product> getNamesProducts() {
        return namesProducts;
    }

    public void setNamesProducts(List<Product> namesProducts) {
        this.namesProducts = namesProducts;
    }
}
