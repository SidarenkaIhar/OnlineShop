package com.epam.training.onlineshop.entity.user;

/**
 * All customers belong to a certain group
 * which has the appropriate rights.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public enum UserGroup {

    /**
     * The client of the online store. Have access to pages and
     * products store, with the ability to purchase goods.
     */
    CUSTOMER(0),

    /**
     * The moderator of the online store. Has limited rights
     * on the management of the online store. Exercises control over
     * clients, with the possibility of putting them in the "black list".
     */
    MODERATOR(1),

    /**
     * The administrator of the online store. Has full access to all
     * aspects of the store. Can control the operation clients,
     * moderators and other administrators.
     */
    ADMINISTRATOR(2);

    /** User group ID */
    private final Integer groupId;

    /**
     * Each user group is assigned a corresponding ID number.
     *
     * @param groupId User group ID
     */
    UserGroup(int groupId) {
        this.groupId = groupId;
    }

    /** Returns the ID number of the user group */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Returns user group by ID number
     *
     * @param id ID number
     */
    public static UserGroup getGroupById(int id) {
        for (UserGroup group : values()) {
            if (group.groupId.equals(id)) return group;
        }
        return CUSTOMER;
    }
}
