package com.epam.training.onlineshop.dao.mysql;

import com.epam.training.onlineshop.controller.HomeServlet;
import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.dao.StatementType;
import com.epam.training.onlineshop.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Connection pool that works with MYSQL,
 * manages and optimizes database connections.
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class MysqlConnectionPool {
    private final static Logger logger = LogManager.getLogger(MysqlConnectionPool.class);

    private static MysqlConnectionPool instance = new MysqlConnectionPool();

    public static MysqlConnectionPool getInstance() {
        return instance;
    }

    private MysqlConnectionPool() {
    }

    /**
     * Performing CRUD operations for MYSQL
     *
     * @param type      the type of operation to perform
     * @param entityDAO entity over which operations are performed in the database
     * @param entity    the entity with whom the operations are performed
     * @param sql       query in dB
     *
     * @return {@code true} if the operation is successful,
     * otherwise returns {@code false}
     *
     * @see StatementType
     */
    public <T extends Entity> boolean executeDatabaseQuery(StatementType type, AbstractDAO<T> entityDAO, T entity, String sql) {
        boolean successful = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //  Getting connection from the pool
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            switch (type) {
                case INSERT:
                    successful = entityDAO.executeInsertRequest(preparedStatement, entity);
                    break;
                case SELECT:
                    ResultSet resultSet = preparedStatement.executeQuery();
                    successful = entityDAO.addFoundEntities(resultSet);
                    break;
                case UPDATE:
                    successful = entityDAO.executeUpdateRequest(preparedStatement, entity);
                    break;
                case DELETE:
                    successful = entityDAO.executeDeleteRequest(preparedStatement, entity);
                    break;
            }
        } catch (SQLException e) {
            // Handling all SQLException
            logger.error("Database connection error: " + e);
        } finally {
            // Closing statement and connection
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return successful;
    }

    /* Gets a connection to the database. */
    private Connection getConnection() {
        Connection connection = null;
        try {
            Context envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource ds = (DataSource) envCtx.lookup("jdbc/mysqlConPool");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Error loading database connection settings" + e);
        }
        return connection;
    }

    /*  Closes the statement if it was opened */
    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing statement: " + e);
        }

    }

    /*  Returns the connection to the connection pool */
    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing connection: " + e);
        }
    }
}