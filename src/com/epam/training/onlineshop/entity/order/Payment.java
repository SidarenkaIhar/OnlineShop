package com.epam.training.onlineshop.entity.order;


import com.epam.training.onlineshop.entity.Entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Payment for the order by the user.
 * Contains payment and shipping information.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class Payment implements Entity {

    /** Payment ID */
    private int id;

    /** Payer ID number */
    private int userId;

    /** Customer first name */
    private String firstName;

    /** Customer last name */
    private String lastName;

    /** Customer's postal code */
    private int postcode;

    /** Customer's address */
    private String address;

    /** Customer's city */
    private String city;

    /** Customer's country */
    private String country;

    /** Customer's region */
    private String region;

    /** Date of payment by the customer */
    private Timestamp creationDate;

    // Description of constructors for the class
    public Payment() {
        this.creationDate = new Timestamp(new Date().getTime());
    }

    public Payment(String firstName, String lastName, String address, String city, String country, String region) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.region = region;
    }

    public Payment(int id, int userId, String firstName, String lastName, int postcode, String address, String city, String country, String region) {
        this(firstName, lastName, address, city, country, region);
        this.id = id;
        this.userId = userId;
        this.postcode = postcode;
    }

    // Getters and setters for class fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    /**
     * Compares this payment to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this payment.
     *
     * @param obj The object to compare this {@code Payment} against
     *
     * @return {@code true} if the given object represents a {@code Payment}
     * equivalent to this payment, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Payment payment = (Payment) obj;
        return userId == payment.userId &&
                postcode == payment.postcode &&
                Objects.equals(firstName, payment.firstName) &&
                Objects.equals(lastName, payment.lastName) &&
                Objects.equals(address, payment.address) &&
                Objects.equals(city, payment.city) &&
                Objects.equals(country, payment.country) &&
                Objects.equals(region, payment.region);
    }

    /**
     * Calculates the hash code of this payment.
     *
     * @return a hash code value for this payment.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, postcode, address, city, country, region);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postcode=" + postcode +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
