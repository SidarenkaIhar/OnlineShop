package com.epam.training.onlineshop.users;

/**
 * The moderator of the online store. Has limited rights
 * on the management of the online store. Exercises control over
 * clients, with the possibility of putting them in the "black list".
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public interface Moderator extends Customer {

    /** Adding a new product to the product catalog */
    void addNewProduct();

    /** Edit the parameters of the product */
    void editProduct();

    /** Removing an item from the product catalog */
    void deleteProduct();

    /** A customer lookup in the customer base */
    void getCustomer();

    /** Adding a customer to the "blacklist" */
    void addCustomerToBlacklist();

    /** Removing a client from the "blacklist" */
    void removeCustomerFromBlacklist();
}
