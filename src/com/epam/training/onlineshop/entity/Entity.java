package com.epam.training.onlineshop.entity;

import java.io.Serializable;

/**
 * The interface that should implement all the entities of the online store
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public interface Entity extends Serializable {

    /**
     * The unique identifier of the entity number is also the primary key of
     * each entity in the database.
     *
     * @return the id of the entity
     */
    int getId();

    /**
     * Sets the unique identifier of the entity number, id is also the primary
     * key of each entity in the database.
     *
     * @param id the id of the entity
     */
    void setId(int id);
}