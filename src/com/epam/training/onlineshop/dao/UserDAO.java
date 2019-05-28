package com.epam.training.onlineshop.dao;

import com.epam.training.onlineshop.entity.user.User;

/**
 * DAO class for working with users in database
 *
 * @author Ihar Sidarenka
 * @version 0.1 30-Apr-19
 */
public abstract class UserDAO extends AbstractDAO<User> {

    /**
     * Adds a user to the blacklist
     *
     * @param user user to add to blacklist
     *
     * @return {@code true} if the user is successfully added
     * in the black list, otherwise returns {@code false}
     */
    public abstract boolean addUserToBlacklist(User user);

    /**
     * Removes a user from the blacklist
     *
     * @param user user to remove from blacklist
     *
     * @return {@code true} if the user is successfully removed
     * from blacklist, otherwise returns {@code false}
     */
    public abstract boolean deleteUserFromBlacklist(User user);
}
