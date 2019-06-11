package com.epam.training.onlineshop.utils;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.users.UserGroup;

import java.util.List;

/**
 * Responsible for the format of data transfer between the servlet and the page
 *
 * @author Ihar Sidarenka
 * @version 0.1 08-Jun-19
 */
public class JsonDataPackage {

    /*  List of all store users to display on the page */
    private List<User> usersToShow;

    /*  List of store users to edit or delete */
    private List<String> usersToEdit;

    /*  An array of all types of store users */
    private UserGroup[] userGroups;

    /*  The user being edited at the moment */
    private User editableUser;

    /*  The message on successful completion of the operation */
    private String messageSuccess;

    /*  The message on unsuccessful completion of the operation */
    private String messageFailed;

    /*  Type of operation currently in progress */
    private StatementType typeOperation;

    public JsonDataPackage() {
        this.userGroups = UserGroup.values();
    }

    public JsonDataPackage(List<User> usersToShow, List<String> usersToEdit, User editableUser, String messageSuccess, String messageFailed, StatementType typeOperation) {
        this();
        this.usersToShow = usersToShow;
        this.usersToEdit = usersToEdit;
        this.editableUser = editableUser;
        this.messageSuccess = messageSuccess;
        this.messageFailed = messageFailed;
        this.typeOperation = typeOperation;
    }

    public List<User> getUsersToShow() {
        return usersToShow;
    }

    public void setUsersToShow(List<User> usersToShow) {
        this.usersToShow = usersToShow;
    }

    public List<String> getUsersToEdit() {
        return usersToEdit;
    }

    public void setUsersToEdit(List<String> usersToEdit) {
        this.usersToEdit = usersToEdit;
    }

    public UserGroup[] getUserGroups() {
        return userGroups;
    }

    public User getEditableUser() {
        return editableUser;
    }

    public void setEditableUser(User editableUser) {
        this.editableUser = editableUser;
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
