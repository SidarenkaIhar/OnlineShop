package com.epam.training.onlineshop.utils.json;

import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.entity.user.UserGroup;

import java.util.List;

/**
 * Parameterization of data transferred between the page and the server for the users
 *
 * @author Ihar Sidarenka
 * @version 0.1 19-Jun-19
 */
public class UserJsonDataPackage extends JsonDataPackage<User> {

    /*  An array of all types of store users */
    private UserGroup[] userGroups;

    public UserJsonDataPackage(List<User> entitiesToShow, List<String> entitiesToEdit, User editableEntity, String messageSuccess, String messageFailed, StatementType typeOperation) {
        super(entitiesToShow, entitiesToEdit, editableEntity, messageSuccess, messageFailed, typeOperation);
        this.userGroups = UserGroup.values();
    }

    public UserGroup[] getUserGroups() {
        return userGroups;
    }
}
