package com.epam.training.onlineshop.configuration;

import com.epam.training.onlineshop.utils.UTF8Control;

import java.util.ResourceBundle;

/**
 * Loads the resources from the configuration files.
 *
 * @author Ihar Sidarenka
 * @version 0.1 22-Apr-19
 */
public class ConfigurationManager {
    private static ConfigurationManager instance;

    private ConfigurationManager() {
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    /**
     * Loads the resource from the configuration file
     *
     * @param propertyPath path to configuration file
     *
     * @return returns the ResourceBundle corresponding to the
     * configuration file
     */
    public ResourceBundle getResourceBundle(String propertyPath) {
        return ResourceBundle.getBundle(propertyPath, new UTF8Control());
    }
}