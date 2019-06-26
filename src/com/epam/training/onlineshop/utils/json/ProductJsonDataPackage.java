package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.catalog.Category;
import com.epam.training.onlineshop.entity.catalog.Product;

import java.util.List;

import static com.epam.training.onlineshop.dao.DAOFactory.MYSQL;
import static com.epam.training.onlineshop.dao.DAOFactory.getDAOFactory;

/**
 * Parameterization of data transferred between the page and the server for the products
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class ProductJsonDataPackage extends JsonDataPackage<Product> {

    /* All categories of online store */
    private List<Category> categories;

    public ProductJsonDataPackage() {
        this.categories = getAllCategories();
    }

    public ProductJsonDataPackage(List<Product> entitiesToShow, List<String> entitiesToEdit, Product editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.categories = getAllCategories();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    private List<Category> getAllCategories() {
        return getDAOFactory(MYSQL).getCategoryDAO().findAll();
    }
}
