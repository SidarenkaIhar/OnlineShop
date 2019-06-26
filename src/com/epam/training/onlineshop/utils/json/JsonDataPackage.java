package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.Entity;

import java.util.List;

/**
 * Responsible for the format of data transfer between the servlet and the page
 *
 * @author Ihar Sidarenka
 * @version 0.1 08-Jun-19
 */
public class JsonDataPackage<T extends Entity> {

    /*  List of all store entities to display on the page */
    private List<T> entitiesToShow;

    /*  List of store entities to edit or delete */
    private List<String> entitiesToEdit;

    /*  The entity being edited at the moment */
    private T editableEntity;

    /*  The message on successful completion of the operation */
    private String messageSuccess;

    /*  The message on unsuccessful completion of the operation */
    private String messageFailed;

    /*  Type of operation currently in progress */
    private StatementType typeOperation;

    public JsonDataPackage() {
    }

    public JsonDataPackage(T editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        this.editableEntity = editableEntity;
        this.messageSuccess = messageSuccess;
        this.messageFailed = messageFailed;
        this.typeOperation = typeOperation;
    }

    public JsonDataPackage(List<T> entitiesToShow, String messageSuccess, String messageFailed, StatementType typeOperation) {
        this.entitiesToShow = entitiesToShow;
        this.messageSuccess = messageSuccess;
        this.messageFailed = messageFailed;
        this.typeOperation = typeOperation;
    }

    public JsonDataPackage(List<T> entitiesToShow, List<String> entitiesToEdit, T editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        this(entitiesToShow, messageSuccess, messageFailed, typeOperation);
        this.entitiesToEdit = entitiesToEdit;
        this.editableEntity = editableEntity;
    }

    public List<T> getEntitiesToShow() {
        return entitiesToShow;
    }

    public void setEntitiesToShow(List<T> entitiesToShow) {
        this.entitiesToShow = entitiesToShow;
    }

    public List<String> getEntitiesToEdit() {
        return entitiesToEdit;
    }

    public void setEntitiesToEdit(List<String> entitiesToEdit) {
        this.entitiesToEdit = entitiesToEdit;
    }

    public T getEditableEntity() {
        return editableEntity;
    }

    public void setEditableEntity(T editableEntity) {
        this.editableEntity = editableEntity;
    }

    public String getMessageSuccess() {
        return messageSuccess;
    }

    public void setMessageSuccess(String messageSuccess) {
        this.messageSuccess = messageSuccess;
    }

    public String getMessageFailed() {
        return messageFailed;
    }

    public void setMessageFailed(String messageFailed) {
        this.messageFailed = messageFailed;
    }

    public StatementType getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(StatementType typeOperation) {
        this.typeOperation = typeOperation;
    }
}