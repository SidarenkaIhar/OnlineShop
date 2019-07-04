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

    public CartJsonDataPackage(ShoppingCart editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(editableEntity, messageSuccess, messageFailed, typeOperation);
    }

    public CartJsonDataPackage(List<ShoppingCart> entitiesToShow, List<ShoppingCart> userCart, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, messageSuccess, messageFailed, typeOperation);
        this.userCart = userCart;
    }

    public CartJsonDataPackage(List<ShoppingCart> entitiesToShow, List<ShoppingCart> userCart, List<String> entitiesToEdit, ShoppingCart editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.userCart = userCart;
    }

    public List<ShoppingCart> getUserCart() {
        return userCart;
    }

    public void setUserCart(List<ShoppingCart> userCart) {
        this.userCart = userCart;
    }
}
