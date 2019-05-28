package com.epam.training.onlineshop.dao.mysql.entity.catalog;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.catalog.Manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with manufacturers in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class ManufacturerDAOImpl extends AbstractDAO<Manufacturer> {

    /*  List of manufacturers found on request in the database */
    private List<Manufacturer> manufacturers;

    /**
     * Adding a new manufacturer to the database
     *
     * @param manufacturer the manufacturer added to database
     *
     * @return {@code true} if the manufacturer is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Manufacturer manufacturer) {
        return getInstance().executeDatabaseQuery(INSERT, this, manufacturer, MYSQL_INSERT_NEW_MANUFACTURER);
    }

    /**
     * Search for a manufacturer with specified parameters in the database
     *
     * @param manufacturer the manufacturer to find
     *
     * @return list of found manufacturers
     */
    @Override
    public List<Manufacturer> find(Manufacturer manufacturer) {
        return findManufacturer(MYSQL_SELECT_MANUFACTURER + manufacturer.getId());
    }

    /**
     * Returns all manufacturers in the database
     *
     * @return list of found manufacturers
     */
    @Override
    public List<Manufacturer> findAll() {
        return findManufacturer(MYSQL_SELECT_ALL_MANUFACTURERS);
    }


    /**
     * The method produces a search of manufacturers according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all manufacturers found
     */
    private List<Manufacturer> findManufacturer(String sql) {
        manufacturers = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return manufacturers;
    }

    /**
     * Edit manufacturer settings in database
     *
     * @param manufacturer the manufacturer with the edited parameters
     *
     * @return {@code true} if manufacturer successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Manufacturer manufacturer) {
        return getInstance().executeDatabaseQuery(UPDATE, this, manufacturer, MYSQL_UPDATE_MANUFACTURER);
    }

    /**
     * Removes a manufacturer from the database
     *
     * @param manufacturer manufacturer whose you want to delete
     *
     * @return {@code true} if the manufacturer is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return getInstance().executeDatabaseQuery(DELETE, this, manufacturer, MYSQL_DELETE_MANUFACTURER);
    }

    /**
     * Adds manufacturers to the list of manufacturers from the set
     *
     * @param resultSet set of manufacturers found in the database
     *
     * @return {@code true} if the manufacturers is successfully added
     * to the list of manufacturers, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the manufacturers to
     *                      the list of manufacturers
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            Manufacturer newManufacturer = new Manufacturer();
            newManufacturer.setId(resultSet.getInt("manufacturer_id"));
            newManufacturer.setName(resultSet.getString("name"));
            newManufacturer.setSortOrder(resultSet.getInt("sort_order"));
            newManufacturer.setEnabled(resultSet.getInt("status"));
            manufacturers.add(newManufacturer);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new manufacturer
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param manufacturer      the manufacturer you want to add to the database
     *
     * @return {@code true} if the manufacturer is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a manufacturer to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Manufacturer manufacturer) throws SQLException {
        preparedStatement.setString(1, manufacturer.getName());
        preparedStatement.setInt(2, manufacturer.getSortOrder());
        preparedStatement.setInt(3, manufacturer.isEnabled() ? 1 : 0);
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the manufacturer
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param manufacturer      the manufacturer you want to update in the database
     *
     * @return {@code true} if the manufacturer is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the manufacturer in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Manufacturer manufacturer) throws SQLException {
        preparedStatement.setInt(4, manufacturer.getId());
        return executeInsertRequest(preparedStatement, manufacturer);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the manufacturer
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param manufacturer      the manufacturer you want to remove from the database
     *
     * @return {@code true} if the manufacturer is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the manufacturer from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Manufacturer manufacturer) throws SQLException {
        preparedStatement.setInt(1, manufacturer.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
