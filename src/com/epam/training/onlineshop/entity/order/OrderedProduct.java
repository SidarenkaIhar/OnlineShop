package com.epam.training.onlineshop.entity.order;

import com.epam.training.onlineshop.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Products ordered by the user. Contains products that the user has
 * purchased and is awaiting delivery or has received.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class OrderedProduct implements Entity {

    /** Ordered product ID */
    private int id;

    /** Order ID */
    private int orderId;

    /** Product ID */
    private int productId;

    /** Product Price */
    private BigDecimal productPrice;

    /** The number of ordered product */
    private int productQuantity;

    // Description of constructors for the class
    public OrderedProduct() {
    }

    public OrderedProduct(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public OrderedProduct(int orderId, int productId, BigDecimal productPrice, int productQuantity) {
        this(productPrice);
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public OrderedProduct(int id, int orderId, int productId, BigDecimal productPrice, int productQuantity) {
        this(orderId, productId, productPrice, productQuantity);
        this.id = id;
    }

    // Getters and setters for class fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
     * Compares this ordered product to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this ordered product.
     *
     * @param obj The object to compare this {@code OrderProduct} against
     *
     * @return {@code true} if the given object represents a {@code OrderProduct}
     * equivalent to this ordered product, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderedProduct that = (OrderedProduct) obj;
        return orderId == that.orderId &&
                productId == that.productId &&
                productQuantity == that.productQuantity &&
                Objects.equals(productPrice, that.productPrice);
    }

    /**
     * Calculates the hash code of this ordered product.
     *
     * @return a hash code value for this ordered product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productPrice, productQuantity);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
