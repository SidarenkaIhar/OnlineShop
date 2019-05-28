package com.epam.training.onlineshop.users;

/**
 * The administrator of the online store. Has full access to all
 * aspects of the store. Can control the operation clients,
 * moderators and other administrators.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public interface Administrator extends Moderator {

    /** Adding a new page to the online store */
    void addNewPage();

    /** Editing a page in the online store */
    void editPage();

    /** Deleting a page from the online store */
    void deletePage();

    /** Adding a new user to the user database. */
    void addNewUser();

    /** Search for a user in the user database */
    void getUser();

    /** Editing user settings */
    void editUser();

    /** To remove a user from the user database */
    void deleteUser();
}
