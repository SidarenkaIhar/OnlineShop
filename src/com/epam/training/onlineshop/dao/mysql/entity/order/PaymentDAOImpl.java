package com.epam.training.onlineshop.dao.mysql.entity.order;

import com.epam.training.onlineshop.dao.AbstractDAO;
import com.epam.training.onlineshop.entity.order.Payment;

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
 * DAO class for working with payments in MYSQL
 *
 * @author Ihar Sidarenka
 * @version 0.1 23-Apr-19
 */
public class PaymentDAOImpl extends AbstractDAO<Payment> {

    /*  List of payments found on request in the database */
    private List<Payment> payments;

    /**
     * Adding a new payment to the database
     *
     * @param payment the payment added to database
     *
     * @return {@code true} if the payment is successfully added,
     * otherwise returns {@code false}
     */
    @Override
    public boolean addNew(Payment payment) {
        return getInstance().executeDatabaseQuery(INSERT, this, payment, MYSQL_INSERT_NEW_PAYMENT);
    }

    /**
     * Search for a payment with specified parameters in the database
     *
     * @param payment the payment to find
     *
     * @return list of found payments
     */
    @Override
    public List<Payment> find(Payment payment) {
        return findPayment(MYSQL_SELECT_PAYMENT + payment.getId());
    }

    /**
     * Returns all payments in the database
     *
     * @return list of found payments
     */
    @Override
    public List<Payment> findAll() {
        return findPayment(MYSQL_SELECT_ALL_PAYMENTS);
    }


    /**
     * The method produces a search of payments according to the specified parameters
     *
     * @param sql generated query for database search
     *
     * @return returns a list of all payments found
     */
    private List<Payment> findPayment(String sql) {
        payments = new ArrayList<>();
        getInstance().executeDatabaseQuery(SELECT, this, null, sql);
        return payments;
    }

    /**
     * Edit payment settings in database
     *
     * @param payment the payment with the edited parameters
     *
     * @return {@code true} if payment successfully edited,
     * otherwise returns {@code false}
     */
    @Override
    public boolean update(Payment payment) {
        return getInstance().executeDatabaseQuery(UPDATE, this, payment, MYSQL_UPDATE_PAYMENT);
    }

    /**
     * Removes a payment from the database
     *
     * @param payment payment whose you want to delete
     *
     * @return {@code true} if the payment is successfully removed,
     * otherwise returns {@code false}
     */
    @Override
    public boolean delete(Payment payment) {
        return getInstance().executeDatabaseQuery(DELETE, this, payment, MYSQL_DELETE_PAYMENT);
    }

    /**
     * Adds payments to the list of payments from the set
     *
     * @param resultSet set of payments found in the database
     *
     * @return {@code true} if the payments is successfully added
     * to the list of payments, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding the payments to
     *                      the list of payments
     */
    public boolean addFoundEntities(ResultSet resultSet) throws SQLException {
        boolean successful = false;
        while (resultSet.next()) {
            Payment newPayment = new Payment();
            newPayment.setId(resultSet.getInt("payment_id"));
            newPayment.setUserId(resultSet.getInt("user_id"));
            newPayment.setFirstName(resultSet.getString("firstname"));
            newPayment.setLastName(resultSet.getString("lastname"));
            newPayment.setPostcode(resultSet.getInt("postcode"));
            newPayment.setAddress(resultSet.getString("address"));
            newPayment.setCity(resultSet.getString("city"));
            newPayment.setCountry(resultSet.getString("country"));
            newPayment.setRegion(resultSet.getString("region"));
            newPayment.setCreationDate(resultSet.getTimestamp("date_added"));
            payments.add(newPayment);
            successful = true;
        }
        return successful;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to add a new payment
     * to the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param payment           the payment you want to add to the database
     *
     * @return {@code true} if the payment is successfully added
     * to the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when adding a payment to
     *                      the database
     */
    @Override
    public boolean executeInsertRequest(PreparedStatement preparedStatement, Payment payment) throws SQLException {
        preparedStatement.setInt(1, payment.getUserId());
        preparedStatement.setString(2, payment.getFirstName());
        preparedStatement.setString(3, payment.getLastName());
        preparedStatement.setInt(4, payment.getPostcode());
        preparedStatement.setString(5, payment.getAddress());
        preparedStatement.setString(6, payment.getCity());
        preparedStatement.setString(7, payment.getCountry());
        preparedStatement.setString(8, payment.getRegion());
        // convert java.util.Date to java.sql.Date
        preparedStatement.setTimestamp(9, new Timestamp(payment.getCreationDate().getTime()));
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     * Sets parameters for the prepared statement and executes a request to update the payment
     * in the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param payment           the payment you want to update in the database
     *
     * @return {@code true} if the payment is successfully updated
     * in the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when updating the payment in
     *                      the database
     */
    @Override
    public boolean executeUpdateRequest(PreparedStatement preparedStatement, Payment payment) throws SQLException {
        preparedStatement.setInt(10, payment.getId());
        return executeInsertRequest(preparedStatement, payment);
    }

    /**
     * Sets parameters for the prepared statement and executes a request to remove the payment
     * from the database
     *
     * @param preparedStatement prepared Statement query in the database
     * @param payment           the payment you want to remove from the database
     *
     * @return {@code true} if the payment is successfully removed
     * from the database, otherwise returns {@code false}
     *
     * @throws SQLException handles errors encountered when removing the payment from
     *                      the database
     */
    @Override
    public boolean executeDeleteRequest(PreparedStatement preparedStatement, Payment payment) throws SQLException {
        preparedStatement.setInt(1, payment.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
