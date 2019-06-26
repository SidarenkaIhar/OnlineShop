package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.order.OrderedProduct;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the ordered products
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class OrderedProductJsonDataPackage extends JsonDataPackage<OrderedProduct> {

    public OrderedProductJsonDataPackage() {
    }

    public OrderedProductJsonDataPackage(List<OrderedProduct> entitiesToShow, List<String> entitiesToEdit, OrderedProduct editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
    }
}
