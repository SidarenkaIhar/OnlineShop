package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.order.Payment;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the payments
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class PaymentJsonDataPackage extends JsonDataPackage<Payment> {
    public PaymentJsonDataPackage(Payment editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(editableEntity, messageSuccess, messageFailed, typeOperation);
    }

    public PaymentJsonDataPackage(List<Payment> entitiesToShow, List<String> entitiesToEdit, Payment editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
    }
}
