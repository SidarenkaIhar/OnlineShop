package com.epam.training.onlineshop.dao.mysql.entity.page;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.page.Page;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with pages in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class PageDAOImpl extends AbstractDAO<Page> {

    /*  List of pages found on request in the database */
    private List<Page> pages;

    /**
     * Adding a new page to the database
     *
     * @param page the page added to database
     *
     * @return {@code true} if the page is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Page page) {
        return getInstance().executeDatabaseQuery(INSERT, this, page, MYSQL_INSERT_NEW_PAGE);
    }

    /**
     * Search for a page with specified parameters in the database
     *
     * @param page the page to find
     *
     * @return list of found pages
     */
    @Override
    public List<Page> find(Page page) {
        return findPage(MYSQL_SELECT_PAGE + page.getId());
    }

    /**
     * Returns all pages in the database
     *
     * @return list of found pages
     */
    @Override
    public List<Page> findAll() {
        return findPage(MYSQL_SELECT_ALL_PAGES);
    }


    /**
     * The method produces a search of pages according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all pages found
     */
    private List<Page> findPage(String sql) {
        pages = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return pages;
    }

    /**
     * Edit page settings in database
     *
     * @param page the page with the edited parameters
     *
     * @return {@code true} if page successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Page page) {
        return getInstance().executeDatabaseQuery(UPDATE, this, page, MYSQL_UPDATE_PAGE);
    }

    /**
     * Removes a page from the database
     *
     * @param page page whose you want to delete
     *
     * @return {@code true} if the page is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Page page) {
        return getInstance().executeDatabaseQuery(DELETE, this, page, MYSQL_DELETE_PAGE);
    }

    /**
     * Adds pages to the list of pages from the set
     *
     * @param resultSet set of pages found in the database
     *
     * @return {@code true} if the pages is successfully added
     * to the list of pages, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the pages to
     *                      the list of pages
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            Page newPage = new Page();
            newPage.setId(resultSet.getInt("page_id"));
            newPage.setTitle(resultSet.getString("title"));
            newPage.setDescription(resultSet.getString("description"));
            newPage.setSortOrder(resultSet.getInt("sort_order"));
            newPage.setEnabled(resultSet.getInt("status"));
            pages.add(newPage);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new page
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param page              the page you want to add to the database
     *
     * @return {@code true} if the page is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a page to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Page page) throws SQLException {
        preparedStatement.setString(1, page.getTitle());
        preparedStatement.setString(2, page.getDescription());
        preparedStatement.setInt(3, page.getSortOrder());
        preparedStatement.setInt(4, page.isEnabled() ? 1 : 0);
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the page
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param page              the page you want to update in the database
     *
     * @return {@code true} if the page is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the page in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Page page) throws SQLException {
        preparedStatement.setInt(5, page.getId());
        return executeInsertRequest(preparedStatement, page);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the page
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param page              the page you want to remove from the database
     *
     * @return {@code true} if the page is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the page from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Page page) throws SQLException {
        preparedStatement.setInt(1, page.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
