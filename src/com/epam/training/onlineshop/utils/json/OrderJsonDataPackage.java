package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.order.Order;
import com.epam.training.onlineshop.entity.order.OrderStatus;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the orders
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class OrderJsonDataPackage extends JsonDataPackage<Order> {

    /*  An array of all types of order statuses */
    private OrderStatus[] orderStatuses;

    public OrderJsonDataPackage() {
        this.orderStatuses = OrderStatus.values();
    }

    public OrderJsonDataPackage(List<Order> entitiesToShow, List<String> entitiesToEdit, Order editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.orderStatuses = OrderStatus.values();
    }

    public OrderStatus[] getOrderStatuses() {
        return orderStatuses;
    }
}
