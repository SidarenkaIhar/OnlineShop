package com.epam.training.onlineshop.entity.order;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Customer payment card details
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class CreditCard {

    /** The type of card that the customer uses for payment */
    private String cardType;

    /** Card number that the customer uses for payment */
    private int cardNumber;

    /** Customer card expiry date */
    private Date cardExpiryDate;

    /** CVV2 code of the customer card */
    private char[] cardSecurityCode;

    // Description of constructor for the class
    public CreditCard(String cardType, int cardNumber, Date cardExpiryDate, char[] cardSecurityCode) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardSecurityCode = cardSecurityCode;
    }

    // Getters and setters for class fields
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public char[] getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(char[] cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    /**
     * Compares this credit card to the specified object.  Returns {@code
     * true} if the object being compared is not {@code null} and if its class
     * and the fields are identical to the class and fields of this credit card.
     *
     * @param obj The object to compare this {@code CreditCard} against
     *
     * @return {@code true} if the given object represents a {@code CreditCard}
     * equivalent to this credit card, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CreditCard that = (CreditCard) obj;
        return cardNumber == that.cardNumber &&
                Objects.equals(cardType, that.cardType) &&
                Objects.equals(cardExpiryDate, that.cardExpiryDate) &&
                Arrays.equals(cardSecurityCode, that.cardSecurityCode);
    }

    /**
     * Calculates the hash code of this credit card.
     *
     * @return a hash code value for this credit card.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(cardType, cardNumber, cardExpiryDate);
        result = 31 * result + Arrays.hashCode(cardSecurityCode);
        return result;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "CreditCard{" +
                "cardType='" + cardType + '\'' +
                ", cardNumber=" + cardNumber +
                ", cardExpiryDate=" + cardExpiryDate +
                ", cardSecurityCode=" + Arrays.toString(cardSecurityCode) +
                '}';
    }
}
