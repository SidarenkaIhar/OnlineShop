package com.epam.training.onlineshop.entity.order;

import com.epam.training.onlineshop.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * User's shopping cart. Contains products which the user intends to buy.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class ShoppingCart implements Entity {

    /** Shopping cart ID */
    private int id;

    /** User ID */
    private int userId;

    /** Product ID */
    private int productId;

    /** Product Price */
    private BigDecimal productPrice;

    /** The number of products in the shopping cart */
    private int productQuantity;

    // Description of constructors for the class
    public ShoppingCart() {
    }

    public ShoppingCart(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public ShoppingCart(int userId, int productId, BigDecimal productPrice, int productQuantity) {
        this(productPrice);
        this.userId = userId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public ShoppingCart(int id, int userId, int productId, BigDecimal productPrice, int productQuantity) {
        this(userId, productId, productPrice, productQuantity);
        this.id = id;
    }

    // Getters and setters for class fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * Compares this shopping cart to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this shopping cart.
     *
     * @param obj The object to compare this {@code ShoppingCart} against
     *
     * @return {@code true} if the given object represents a {@code ShoppingCart}
     * equivalent to this shopping cart, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShoppingCart that = (ShoppingCart) obj;
        return userId == that.userId &&
                productId == that.productId;
    }

    /**
     * Calculates the hash code of this shopping cart.
     *
     * @return a hash code value for this shopping cart.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
