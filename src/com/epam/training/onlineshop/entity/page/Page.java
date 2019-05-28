package com.epam.training.onlineshop.entity.page;

import com.epam.training.onlineshop.entity.Entity;

import java.util.Objects;

/**
 * The standard page of the online store, which is
 * the basis for building more specialized pages.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Page implements Entity {

    /** Page ID */
    private int id;

    /** Page title */
    private String title;

    /** Page body */
    private String description;

    /** Page sort order */
    private int sortOrder;

    /** Page status, whether the page will be visible to customers */
    private boolean isEnabled;

    // Description of constructors for the class
    public Page() {
    }

    public Page(String title, String description) {
        this.title = title;
        this.description = description;
        this.isEnabled = true;
    }

    public Page(int id, String title, String description, int sortOrder, boolean isEnabled) {
        this(title, description);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     * Compares this page to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this page.
     *
     * @param obj The object to compare this {@code Page} against
     *
     * @return {@code true} if the given object represents a {@code Page}
     * equivalent to this page, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Page that = (Page) obj;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    /**
     * Calculates the hash code of this page.
     *
     * @return a hash code value for this page.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sortOrder=" + sortOrder +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
