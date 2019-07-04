package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.Entity;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.StatementType.SELECT;

/**
 * The servlet is responsible for displaying and removing of entities on the Internet store
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-Jun-19
 */
@WebServlet(name = "ShowServlet")
public abstract class ShowServlet<T extends Entity> extends HttpServlet {

    /**
     * Displaying and removing of entities on the Internet store
     *
     * @param entityDAO   responsible for working with the entity in the database
     * @param requestJson response from the page to the online store server
     * @param locale      language for displaying messages to the user
     *
     * @return the server's response to the page
     */
    JsonDataPackage<T> getResponse(AbstractDAO<T> entityDAO, JsonDataPackage<T> requestJson, Locale locale) {
        List<T> entities = entityDAO.findAll();
        Validator validator = new Validator();
        String messageSuccess = "";
        String messageFailed = "";

        if (requestJson != null) {
            if (requestJson.getEntitiesToEdit().size() > 0) {
                StringBuilder success = new StringBuilder();
                StringBuilder failed = new StringBuilder();
                for (String entityId : requestJson.getEntitiesToEdit()) {
                    for (T entity : entities) {
                        if (validator.getNumber(entityId, -1) == entity.getId()) {
                            boolean isSuccessfully = entityDAO.delete(entity);
                            String id = "#" + entity.getId();
                            if (isSuccessfully) {
                                success.append(id).append(MessagesManager.getMessage(ENTITY_SUCCESSFULLY_DELETED, locale));
                            } else {
                                failed.append(id).append(MessagesManager.getMessage(ENTITY_NOT_DELETED, locale));
                            }
                        }
                    }
                }
                messageSuccess = success.length() > 0 ? success.toString() : "";
                messageFailed = failed.length() > 0 ? failed.toString() : "";
            } else {
                messageFailed = MessagesManager.getMessage(NOTHING_SELECTED_TO_DELETE, locale);
            }
        }
        entities = entityDAO.findAll();

        return new JsonDataPackage<>(entities, messageSuccess, messageFailed, SELECT);
    }
}
