package com.epam.training.onlineshop.dao.mysql.entity.user;

import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.onlineshop.configuration.MysqlQueries.*;
import static com.epam.training.onlineshop.dao.StatementType.*;
import static com.epam.training.onlineshop.dao.mysql.MysqlConnectionPool.getInstance;

/**
 * DAO class for working with users in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class UserDAOImpl extends UserDAO {

    /*  List of users found on request in the database */
    private List<User> users;

    /**
     * Adding a new user to the database
     *
     * @param user the user added to database
     *
     * @return {@code true} if the user is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(User user) {
        return getInstance().executeDatabaseQuery(INSERT, this, user, MYSQL_INSERT_NEW_USER);
    }

    /**
     * Search for a user with specified parameters in the database
     *
     * @param user the user to find
     *
     * @return list of found users
     */
    @Override
    public List<User> find(User user) {
        return findUser(MYSQL_SELECT_USER + user.getId());
    }

    /**
     * Returns all users in the database
     *
     * @return list of found users
     */
    @Override
    public List<User> findAll() {
        return findUser(MYSQL_SELECT_ALL_USERS);
    }


    /**
     * The method produces a search of users according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all users found
     */
    private List<User> findUser(String sql) {
        users = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return users;
    }

    /**
     * Edit user settings in database
     *
     * @param user the user with the edited parameters
     *
     * @return {@code true} if user successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(User user) {
        return getInstance().executeDatabaseQuery(UPDATE, this, user, MYSQL_UPDATE_USER);
    }

    /**
     * Removes a user from the database
     *
     * @param user user whose you want to delete
     *
     * @return {@code true} if the user is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(User user) {
        return getInstance().executeDatabaseQuery(DELETE, this, user, MYSQL_DELETE_USER);
    }

    /**
     * Adds a user to the blacklist
     *
     * @param user user to add to blacklist
     *
     * @return {@code true} if the user is successfully added
     * in the black list, otherwise returns {@code false}
     */
    @Override
    public boolean addUserToBlacklist(User user) {
        user.setEnabled(false);
        return update(user);
    }

    /**
     * Removes a user from the blacklist
     *
     * @param user user to remove from blacklist
     *
     * @return {@code true} if the user is successfully removed
     * from blacklist, otherwise returns {@code false}
     */
    @Override
    public boolean deleteUserFromBlacklist(User user) {
        user.setEnabled(true);
        return update(user);
    }

    /**
     * Adds users to the list of users from the set
     *
     * @param resultSet set of users found in the database
     *
     * @return {@code true} if the users is successfully added
     * to the list of users, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the users to
     *                      the list of users
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            User newUser = new User();
            newUser.setId(resultSet.getInt("user_id"));
            newUser.setGroup(resultSet.getInt("user_group_id"));
            newUser.setName(resultSet.getString("username"));
            newUser.setPassword(resultSet.getString("password"));
            newUser.setEmail(resultSet.getString("email"));
            newUser.setEnabled(resultSet.getInt("status"));
            newUser.setCreationDate(resultSet.getTimestamp("date_added"));
            users.add(newUser);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new user
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param user              the user you want to add to the database
     *
     * @return {@code true} if the user is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a user to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setInt(1, user.getGroupId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, new String(user.getPassword()));
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setInt(5, user.isEnabled() ? 1 : 0);
        // convert java.util.Date to java.sql.Date
        preparedStatement.setTimestamp(6, new Timestamp(user.getCreationDate().getTime()));
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the user
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param user              the user you want to update in the database
     *
     * @return {@code true} if the user is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the user in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setInt(7, user.getId());
        return executeInsertRequest(preparedStatement, user);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the user
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param user              the user you want to remove from the database
     *
     * @return {@code true} if the user is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the user from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setInt(1, user.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
