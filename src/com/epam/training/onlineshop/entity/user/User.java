package com.epam.training.onlineshop.entity.user;

import com.epam.training.onlineshop.entity.Entity;
import com.epam.training.onlineshop.users.UserGroup;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static com.epam.training.onlineshop.users.UserGroup.CUSTOMER;
import static com.epam.training.onlineshop.users.UserGroup.getGroupById;

/**
 * The basis of the user of the online store to build
 * specialized store users.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class User implements Entity {

    /** User Id */
    private int id;

    /** User group type */
    private UserGroup group;

    /** User name */
    private String name;

    /** User password */
    private char[] password;

    /** User Email */
    private String email;

    /** The status of user, active or blocked (placed in the "black list") */
    private boolean isEnabled;

    /** The creation date of the user account */
    private Date creationDate;

    // Description of constructors for the class
    public User() {
        this.group = CUSTOMER;
        this.creationDate = new Date();
    }

    public User(String name, String password, String email) {
        this();
        this.name = name;
        this.password = password.toCharArray();
        this.email = email;
        this.isEnabled = true;
    }

    public User(UserGroup group, String name, String password, String email, boolean isEnabled) {
        this(name, password, email);
        this.group = group;
        this.isEnabled = isEnabled;
    }

    public User(int id, UserGroup group, String name, String password, String email, boolean isEnabled) {
        this(group, name, password, email, isEnabled);
        this.id = id;
    }

    // Getters and setters for class fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserGroup getGroup() {
        return group;
    }

    public int getGroupId() {
        return group.getGroupId();
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

    public void setGroup(int groupId) {
        this.group = getGroupById(groupId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public String getStringPassword() {
        return new String(password);
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Compares this user to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the user's class and fields.
     *
     * @param obj The object to compare this {@code User} against
     *
     * @return {@code true} if the given object represents a {@code User}
     * equivalent to this user, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Arrays.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    /**
     * Calculates the hash code of this user.
     *
     * @return a hash code value for this user.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, email);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", password=" + Arrays.toString(password) +
                ", email='" + email + '\'' +
                ", isEnabled=" + isEnabled +
                ", creationDate=" + creationDate +
                '}';
    }
}