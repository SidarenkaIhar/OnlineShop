package com.epam.training.onlineshop.users;

/**
 * The client of the online store. Have access to pages and
 * products store, with the ability to purchase goods.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public interface Customer {

    /** Get the requested page */
    void getPage();

    /** Product search in the product catalog */
    void getProduct();

    /** Add a product to the customer shopping cart */
    void addProductToCart();

    /** Removing a product from the shopping cart */
    void removeProductFromCart();

    /** Create a purchase order for items in the shopping cart */
    void createOrder();
}
