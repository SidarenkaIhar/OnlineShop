package com.epam.training.onlineshop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ihar Sidarenka
 * @version 0.1 26-May-19
 */
public class Validator {
    private static final String NAME_PATTERN = "^[A-Za-z0-9_]{4,20}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z@#$%]).{6,20})";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private static final Pattern namePattern;
    private static final Pattern passwordPattern;
    private static final Pattern emailPattern;

    static {
        namePattern = Pattern.compile(NAME_PATTERN);
        passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        emailPattern = Pattern.compile(EMAIL_PATTERN);
    }

    private static Validator instance = new Validator();

    public synchronized static Validator getInstance() {
        return instance;
    }

    private Validator() {
    }

    public boolean validateUserData(String name, String password, String email) {
        return validateName(name) && validatePassword(password) && validateEmail(email);
    }

    public boolean validateName(String name) {
        return isCorrect(name, namePattern);
    }

    public boolean validatePassword(String password) {
        return isCorrect(password, passwordPattern);
    }

    public boolean validateEmail(String email) {
        return isCorrect(email, emailPattern);
    }

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