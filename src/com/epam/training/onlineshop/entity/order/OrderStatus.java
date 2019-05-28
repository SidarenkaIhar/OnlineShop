package com.epam.training.onlineshop.entity.order;

/**
 * Displays the status of the order.
 *
 * @author Ihar Sidarenka
 * @version 0.1 10-Apr-19
 */
public enum OrderStatus {

    /** Receiving a new order */
    NEW(0),

    /** Waiting for payment on order */
    PENDING(1),

    /** Payment on order not received */
    PAYMENT_FAILED(2),

    /** Payment for the order has been received */
    PAYMENT_RECEIVED(3),

    /** Order processing */
    PROCESSING(4),

    /** The order has been sent to the buyer */
    SHIPPED(5),

    /** The order was received by the buyer */
    DELIVERED(6),

    /** The transaction is successfully completed */
    COMPLETED(7),

    /** Order was cancelled */
    CANCELED(8);

    /** Order status ID */
    private final Integer orderStatusId;

    /**
     * Each order status is assigned a corresponding ID number
     *
     * @param orderStatusId Order status ID
     */
    OrderStatus(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    /** Returns the ID number of the order status */
    public int getOrderStatusId() {
        return orderStatusId;
    }

    /**
     * Returns user group by ID number
     *
     * @param id ID number
     */
    public static OrderStatus getOrderStatusById(int id) {
        for (OrderStatus status : values()) {
            if (status.orderStatusId.equals(id)) return status;
        }
        return NEW;
    }
}