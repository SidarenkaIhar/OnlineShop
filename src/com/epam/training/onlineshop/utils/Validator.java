package com.epam.training.onlineshop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the data against the specified pattern
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
public class Validator {

    /*  Correct name pattern */
    private static final String NAME_PATTERN;

    /*  Correct password pattern */
    private static final String PASSWORD_PATTERN;

    /*  Correct email pattern */
    private static final String EMAIL_PATTERN;

    private static final Pattern namePattern;
    private static final Pattern passwordPattern;
    private static final Pattern emailPattern;

    static {
        NAME_PATTERN = "^[A-Za-z0-9_]{4,20}$";
        PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z@#$%]).{6,20})";
        EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,50})$";
        namePattern = Pattern.compile(NAME_PATTERN);
        passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        emailPattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Checks user data for correctness.
     *
     * @param name     the name of the user you want to check
     * @param password the password of the user you want to check
     * @param email    the email of the user you want to check
     *
     * @return returns {@code true} if the entered data matches the
     * specified patterns
     */
    public boolean validateUserData(String name, String password, String email) {
        return validateName(name) && validatePassword(password) && validateEmail(email);
    }

    /**
     * Checks user name for correctness.
     *
     * @param name the name of the user you want to check
     *
     * @return returns {@code true} if the entered name matches the
     * specified pattern
     */
    public boolean validateName(String name) {
        return isCorrect(name, namePattern);
    }

    /**
     * Checks user password for correctness.
     *
     * @param password the password of the user you want to check
     *
     * @return returns {@code true} if the entered password matches the
     * specified pattern
     */
    public boolean validatePassword(String password) {
        return isCorrect(password, passwordPattern);
    }

    /**
     * Checks user email for correctness.
     *
     * @param email the email of the user you want to check
     *
     * @return returns {@code true} if the entered email matches the
     * specified pattern
     */
    public boolean validateEmail(String email) {
        return isCorrect(email, emailPattern);
    }

    /**
     * Checks the given string for conformance to the pattern
     *
     * @param string  the string to check
     * @param pattern the pattern that the string must match
     *
     * @return returns {@code true} if the string matches the pattern
     */
    private boolean isCorrect(String string, Pattern pattern) {
        boolean isCorrect;
        try {
            Matcher matcher = pattern.matcher(string);
            isCorrect = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            isCorrect = false;
        }
        return isCorrect;
    }
}