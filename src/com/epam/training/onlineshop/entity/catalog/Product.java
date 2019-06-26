package com.epam.training.onlineshop.entity.catalog;

import com.epam.training.onlineshop.entity.Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Represents the basic product of the online store.
 * The product can be excluded from the display to customers of the store.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Product implements Entity {

    /** Product ID */
    private int id;

    /** Product name */
    private String name;

    /** Product description */
    private String description;

    /** Product manufacturer */
    private String manufacturer;

    /** Product category ID */
    private int categoryId;

    /** Product price */
    private BigDecimal price;

    /** Quantity of products in stock */
    private int quantity;

    /** Link to product image */
    private String image;

    /** Date the product was added to the product catalog */
    private Timestamp creationDate;

    /** Product sort order */
    private int sortOrder;

    /** Is the product shown to the customers of the store */
    private boolean isEnabled;

    // Description of constructors for the class
    public Product() {
        this.creationDate = new Timestamp(new Date().getTime());
    }

    public Product(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public Product(String name, String description, String manufacturer, BigDecimal price, String image) {
        this(name, description);
        this.manufacturer = manufacturer;
        this.price = price;
        this.image = image;
    }

    public Product(int id, String name, String description, String manufacturer, int categoryId, int quantity, BigDecimal price, String image, int sortOrder, boolean isEnabled) {
        this(name, description, manufacturer, price, image);
        this.id = id;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.sortOrder = sortOrder;
        this.isEnabled = isEnabled;
    }

    // Getters and setters for class fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(int enabled) {
        isEnabled = (enabled == 1);
    }

    /**
     * Compares this product to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this product.
     *
     * @param obj The object to compare this {@code Product} against
     *
     * @return {@code true} if the given object represents a {@code Product}
     * equivalent to this product, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return categoryId == product.categoryId &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(manufacturer, product.manufacturer);
    }

    /**
     * Calculates the hash code of this product.
     *
     * @return a hash code value for this product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, manufacturer, categoryId);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer=" + manufacturer +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", creationDate=" + creationDate +
                ", sortOrder=" + sortOrder +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
