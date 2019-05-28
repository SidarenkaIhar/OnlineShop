package com.epam.training.onlineshop.dao.mysql.entity.catalog;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.catalog.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with categories in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class CategoryDAOImpl extends AbstractDAO<Category> {

    /*  List of categories found on request in the database */
    private List<Category> categories;

    /**
     * Adding a new category to the database
     *
     * @param category the category added to database
     *
     * @return {@code true} if the category is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Category category) {
        return getInstance().executeDatabaseQuery(INSERT, this, category, MYSQL_INSERT_NEW_CATEGORY);
    }

    /**
     * Search for a category with specified parameters in the database
     *
     * @param category the category to find
     *
     * @return list of found categories
     */
    @Override
    public List<Category> find(Category category) {
        return findCategory(MYSQL_SELECT_CATEGORY + category.getId());
    }

    /**
     * Returns all categories in the database
     *
     * @return list of found categories
     */
    @Override
    public List<Category> findAll() {
        return findCategory(MYSQL_SELECT_ALL_CATEGORIES);
    }


    /**
     * The method produces a search of categories according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all categories found
     */
    private List<Category> findCategory(String sql) {
        categories = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return categories;
    }

    /**
     * Edit category settings in database
     *
     * @param category the category with the edited parameters
     *
     * @return {@code true} if category successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Category category) {
        return getInstance().executeDatabaseQuery(UPDATE, this, category, MYSQL_UPDATE_CATEGORY);
    }

    /**
     * Removes a category from the database
     *
     * @param category category whose you want to delete
     *
     * @return {@code true} if the category is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Category category) {
        return getInstance().executeDatabaseQuery(DELETE, this, category, MYSQL_DELETE_CATEGORY);
    }

    /**
     * Adds categories to the list of categories from the set
     *
     * @param resultSet set of categories found in the database
     *
     * @return {@code true} if the categories is successfully added
     * to the list of categories, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the
     *                      categories to the list of categories
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            Category newCategory = new Category();
            newCategory.setId(resultSet.getInt("category_id"));
            newCategory.setParentId(resultSet.getInt("parent_id"));
            newCategory.setName(resultSet.getString("name"));
            newCategory.setSortOrder(resultSet.getInt("sort_order"));
            newCategory.setEnabled(resultSet.getInt("status"));
            categories.add(newCategory);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new category
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param category          the category you want to add to the database
     *
     * @return {@code true} if the category is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a category to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setInt(1, category.getParentId());
        preparedStatement.setString(2, category.getName());
        preparedStatement.setInt(3, category.getSortOrder());
        preparedStatement.setInt(4, category.isEnabled() ? 1 : 0);
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the category
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param category          the category you want to update in the database
     *
     * @return {@code true} if the category is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the category in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setInt(5, category.getId());
        return executeInsertRequest(preparedStatement, category);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the category
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param category          the category you want to remove from the database
     *
     * @return {@code true} if the category is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the category from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setInt(1, category.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
