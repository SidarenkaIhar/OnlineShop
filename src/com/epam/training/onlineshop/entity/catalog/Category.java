package com.epam.training.onlineshop.entity.catalog;

import com.epam.training.onlineshop.entity.Entity;

import java.util.Objects;

/**
 * It is an additional characteristic of the product and is used for
 * division of products into categories and subcategories.
 * It serves as an additional filter when sorting products.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Category implements Entity {

    /** Category ID */
    private int id;

    /** ID number of the parent category */
    private int parentId;

    /** Category name */
    private String name;

    /** Category sort order */
    private int sortOrder;

    /** Is the category shown to customers of the store */
    private boolean isEnabled;

    // Description of constructors for the class
    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.isEnabled = true;
    }

    public Category(int id, int parentId, String name, int sortOrder, boolean isEnabled) {
        this(name);
        this.id = id;
        this.parentId = parentId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setEnabled(int enabled) {
        isEnabled = (enabled == 1);
    }

    /**
     * Compares this category to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this category.
     *
     * @param obj The object to compare this {@code Category} against
     *
     * @return {@code true} if the given object represents a {@code Category}
     * equivalent to this category, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category category = (Category) obj;
        return id == category.id &&
                parentId == category.parentId &&
                Objects.equals(name, category.name);
    }

    /**
     * Calculates the hash code of this category.
     *
     * @return a hash code value for this category.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", sortOrder=" + sortOrder +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
