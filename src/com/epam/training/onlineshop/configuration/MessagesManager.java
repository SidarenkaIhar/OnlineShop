package com.epam.training.onlineshop.configuration;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.epam.training.onlineshop.configuration.ConfigurationManager.*;


/**
 * Displays messages for the user according to their language
 *
 * @author Ihar Sidarenka
 * @version 0.1 30-Jun-19
 */
public class MessagesManager {

    /* Russian locale */
    private static final Locale RU_LOCALE;

    /* The path to the configuration file messages */
    private static final String MESSAGES_PROPERTIES_PATH;

    private static ResourceBundle EN_RESOURCE_BUNDLE;
    private static ResourceBundle RU_RESOURCE_BUNDLE;

    static {
        RU_LOCALE = new Locale("ru", "RU");
        MESSAGES_PROPERTIES_PATH = "resources.MessagesBundle";
        EN_RESOURCE_BUNDLE = getInstance().getResourceBundle(MESSAGES_PROPERTIES_PATH, Locale.US);
        RU_RESOURCE_BUNDLE = getInstance().getResourceBundle(MESSAGES_PROPERTIES_PATH, RU_LOCALE);
    }

    public static Locale getEnLocale() {
        return Locale.US;
    }

    public static Locale getRuLocale() {
        return RU_LOCALE;
    }

    /**
     * Forms a message to the user according to the localization
     *
     * @param message a message to the user
     * @param locale  user locale
     *
     * @return message to user according to localization
     */
    public static String getMessage(Messages message, Locale locale) {
        if (RU_LOCALE.equals(locale)) {
            return RU_RESOURCE_BUNDLE.getString(message.name());
        }
        return EN_RESOURCE_BUNDLE.getString(message.name());
    }
}
