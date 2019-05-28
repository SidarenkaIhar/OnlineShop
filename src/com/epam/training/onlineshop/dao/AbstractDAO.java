package com.epam.training.onlineshop.dao;

import com.epam.training.onlineshop.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO class for working with entities in database
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-Apr-19
 */
public abstract class AbstractDAO<T extends Entity> {

    /**
     * Adding a new entity to the database
     *
     * @param entity the entity added to database
     *
     * @return {@code true} if the entity is successfully added,
     * otherwise returns {@code false}
     */
    public abstract boolean addNew(T entity);

    /**
     * Search for a entity with specified parameters in the database
     *
     * @param entity the entity to find
     *
     * @return list of found entities
     */
    public abstract List<T> find(T entity);

    /**
     * Returns all entities in the database
     *
     * @return list of found entities
     */
    public abstract List<T> findAll();

    /**
     * Edit entity settings in database
     *
     * @param entity the entity with the edited parameters
     *
     * @return {@code true} if entity successfully edited,
     * otherwise returns {@code false}
     */
    public abstract boolean update(T entity);

    /**
     * Removes a entity from the database
     *
     * @param entity the entity whose you want to delete
     *
     * @return {@code true} if the entity is successfully removed,
     * otherwise returns {@code false}
     */
    public abstract boolean delete(T entity);

    /**
     * Adds entities to the list of entities from the set
     *
     * @param resultSet set of entities found in the database
     *
     * @return {@code true} if the entities is successfully added
     * to the list of entities, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the entities to
     *                      the list of entities
     */
    public abstract boolean addFoundEntities(ResultSet resultSet) throws SQLException;

    /**
     * Sets and executes parameters for the prepared statement to add a new entity
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param entity            the entity you want to add to the database
     *
     * @return {@code true} if the entity is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a entity to
     *                      the database
     */
    public abstract boolean executeInsertRequest(PreparedStatement preparedStatement, T entity) throws SQLException;

    /**
     * Sets and executes parameters for the prepared statement to update the entity
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param entity            the entity you want to update in the database
     *
     * @return {@code true} if the entity is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the entity in
     *                      the database
     */
    public abstract boolean executeUpdateRequest(PreparedStatement preparedStatement, T entity) throws SQLException;

    /**
     * Sets and executes parameters for the prepared statement to remove the entity
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param entity            the entity you want to remove from the database
     *
     * @return {@code true} if the entity is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the entity from
     *                      the database
     */
    public abstract boolean executeDeleteRequest(PreparedStatement preparedStatement, T entity) throws SQLException;
}
