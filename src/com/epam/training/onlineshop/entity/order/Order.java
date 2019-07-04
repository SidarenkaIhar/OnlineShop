package com.epam.training.onlineshop.entity.order;

import com.epam.training.onlineshop.entity.Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static com.epam.training.onlineshop.entity.order.OrderStatus.NEW;
import static com.epam.training.onlineshop.entity.order.OrderStatus.getOrderStatusById;

/**
 * The order which is formed when buying products by the client.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Order implements Entity {

    /** Order ID */
    private int id;

    /** ID number of the customer who created the order */
    private int userId;

    /** Customer name who created the order */
    private String userName;

    /** Cost of products in the order */
    private BigDecimal amount;

    /** Payment ID */
    private int paymentId;

    /** Customer order creation date */
    private Timestamp creationDate;

    /** Order status */
    private OrderStatus orderStatus;

    /** Date of the status change of the order */
    private Timestamp dateStatusChange;

    /** The tracking number of the order */
    private String trackingNumber;

    // Description of constructors for the class
    public Order() {
        this.creationDate = new Timestamp(new Date().getTime());
        this.orderStatus = NEW;
    }

    public Order(int userId, BigDecimal amount, int paymentId) {
        this();
        this.userId = userId;
        this.amount = amount;
        this.paymentId = paymentId;
        this.dateStatusChange = new Timestamp(new Date().getTime());
        this.trackingNumber = "";
    }

    public Order(int id, int userId, BigDecimal amount, int paymentId, Timestamp creationDate, OrderStatus orderStatus, Timestamp dateStatusChange, String trackingNumber) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.paymentId = paymentId;
        this.creationDate = creationDate;
        this.orderStatus = orderStatus;
        this.dateStatusChange = dateStatusChange;
        this.trackingNumber = trackingNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = new Timestamp(creationDate.getTime());
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderStatus(int orderStatusId) {
        this.orderStatus = getOrderStatusById(orderStatusId);
    }

    public Timestamp getDateStatusChange() {
        return dateStatusChange;
    }

    public void setDateStatusChange(Date dateStatusChange) {
        this.dateStatusChange = new Timestamp(dateStatusChange.getTime());
    }

    public void setDateStatusChange(Timestamp dateStatusChange) {
        this.dateStatusChange = dateStatusChange;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * Compares this order to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this order.
     *
     * @param obj The object to compare this {@code Order} against
     *
     * @return {@code true} if the given object represents a {@code Order}
     * equivalent to this order, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return userId == order.userId &&
                paymentId == order.paymentId &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(creationDate, order.creationDate);
    }

    /**
     * Calculates the hash code of this order.
     *
     * @return a hash code value for this order.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, amount, paymentId, creationDate);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", paymentId=" + paymentId +
                ", creationDate=" + creationDate +
                ", orderStatus=" + orderStatus +
                ", dateStatusChange=" + dateStatusChange +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
