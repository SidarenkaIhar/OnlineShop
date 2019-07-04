package com.epam.training.onlineshop.controller;

import com.epam.training.onlineshop.configuration.MessagesManager;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.Entity;
import com.epam.training.onlineshop.utils.Validator;
import com.epam.training.onlineshop.utils.json.JsonDataPackage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Locale;

import static com.epam.training.onlineshop.configuration.Messages.*;
import static com.epam.training.onlineshop.dao.StatementType.*;

/**
 * The servlet is responsible for adding and editing entities in the internet shop
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-Jun-19
 */
@WebServlet(name = "EditServlet")
public abstract class EditServlet<T extends Entity> extends HttpServlet {

    /**
     * Processes the request from the page to the server and generates the server's response to the page
     *
     * @param entityDAO    the entity that processes queries in the database
     * @param requestJson  request from the page to the server
     * @param emptyEntity  empty entity to clear fields on the page
     * @param responseJson the server's response to the page
     * @param locale       language for displaying messages to the user
     * @param <Q>          response type from the server
     *
     * @return the server's response to the page
     */
    <Q extends JsonDataPackage<T>> Q getResponse(AbstractDAO<T> entityDAO, Q requestJson, T emptyEntity, Q responseJson, Locale locale) {
        Validator validator = new Validator();
        List<T> entities = entityDAO.findAll();
        T editableEntity = null;
        String messageSuccess = "";
        String messageFailed = "";
        StatementType typeOperation = INSERT;

        if (requestJson != null) {
            if (requestJson.getTypeOperation() == INSERT) {
                editableEntity = requestJson.getEditableEntity();
                boolean isExist = false;
                for (T entity : entities) {
                    if (entity.equals(editableEntity)) {
                        messageFailed = MessagesManager.getMessage(ALREADY_EXIST, locale);
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    boolean isSuccessfully = entityDAO.addNew(editableEntity);
                    if (isSuccessfully) {
                        messageSuccess = MessagesManager.getMessage(ENTITY_ADDED_SUCCESSFULLY, locale);
                        editableEntity = emptyEntity;
                    } else {
                        messageFailed = MessagesManager.getMessage(ENTITY_NOT_ADDED, locale);
                    }
                }
            } else if (requestJson.getTypeOperation() == SELECT) {
                if (requestJson.getEntitiesToEdit().size() > 0) {
                    int entityId = validator.getNumber(requestJson.getEntitiesToEdit().get(0), -1);
                    for (T entity : entities) {
                        if (entity.getId() == entityId) {
                            editableEntity = entity;
                            typeOperation = UPDATE;
                        }
                    }
                }
            } else if (requestJson.getTypeOperation() == UPDATE) {
                editableEntity = requestJson.getEditableEntity();
                typeOperation = UPDATE;
                boolean isSuccessfully = entityDAO.update(editableEntity);
                String id = "#" + editableEntity.getId();
                if (isSuccessfully) {
                    messageSuccess = id + MessagesManager.getMessage(ENTITY_SUCCESSFULLY_UPDATED, locale);
                } else {
                    messageFailed = id + MessagesManager.getMessage(ENTITY_NOT_UPDATED, locale);
                }
            }
        }

        responseJson.setEditableEntity(editableEntity);
        responseJson.setMessageSuccess(messageSuccess);
        responseJson.setMessageFailed(messageFailed);
        responseJson.setTypeOperation(typeOperation);
        return responseJson;
    }
}
