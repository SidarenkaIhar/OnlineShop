package com.epam.training.onlineshop.dao;

/**
 * Data Manipulation Statements
 *
 * @author Ihar Sidarenka
 * @version 0.1 26-Apr-19
 */
public enum StatementType {

    /*  Inserts new rows into an existing table */
    INSERT,

    /*  Used to retrieve rows selected from one or more tables */
    SELECT,

    /*  DML statement that modifies rows in a table */
    UPDATE,

    /*  DML statement that removes rows from a table */
    DELETE
}
