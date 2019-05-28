package com.epam.training.onlineshop.entity.catalog;

import com.epam.training.onlineshop.entity.Entity;

import java.util.Objects;

/**
 * It is an additional characteristic of the product and is used for
 * division of products into subgroups according to their manufacturer.
 * It serves as an additional filter when sorting products.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Manufacturer implements Entity {

    /** Manufacturer ID */
    private int id;

    /** Manufacturer name */
    private String name;

    /** Manufacturer sort order */
    private int sortOrder;

    /** Is the manufacturer shown to customers of the store */
    private boolean isEnabled;

    // Description of constructors for the class
    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
        this.isEnabled = true;
    }

    public Manufacturer(int id, String name, int sortOrder, boolean isEnabled) {
        this(name);
        this.id = id;
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
     * Compares this manufacturer to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this manufacturer.
     *
     * @param obj The object to compare this {@code Manufacturer} against
     *
     * @return {@code true} if the given object represents a {@code Manufacturer}
     * equivalent to this manufacturer, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Manufacturer that = (Manufacturer) obj;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    /**
     * Calculates the hash code of this manufacturer.
     *
     * @return a hash code value for this manufacturer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sortOrder=" + sortOrder +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
