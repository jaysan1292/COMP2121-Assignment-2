package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason Recillo
 */
public class CustomerAccess extends CommonAccess {
    public static final String CUSTOMER_ID = "customer_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";

    private CustomerAccess() {
        super();
    }

    /**
     * Searches for the specified value in the database using the specified column.
     * @param column The column to search in the database.<br/>Use the string constants
     * to specify the column (i.e., <code>CustomerAccess.CUSTOMER_ID</code> or <code>CustomerAccess.FIRST_NAME</code>).
     * @param value The string to search the database for.
     * @return A <code>Customer</code> object representing the customer found in the database.
     * @throws SQLException if the specified customer was not found in the database. 
     */
    public static Customer findCustomer(String column, String value) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM customer WHERE %s='%s';", column, value);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt(CUSTOMER_ID));
        customer.setFirstName(resultSet.getString(FIRST_NAME));
        customer.setLastName(resultSet.getString(LAST_NAME));
        customer.setAddress(resultSet.getString(ADDRESS));
        customer.setPhoneNumber(resultSet.getString(PHONE_NUMBER));

        Utils.log_debug("Returned customer information:\n\t\tFirst name: %s\n\t\tLast name: %s", customer.getFirstName(), customer.getLastName());

        return customer;
    }

    public static void addNewCustomer(Customer customer) throws SQLException {
        addNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getPhoneNumber());
    }

    public static void addNewCustomer(String firstName, String lastName, String address, String phoneNumber) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO customer ";
        query += "(first_name, last_name, address, phone_number) ";
        query += String.format("VALUES('%s', '%s', '%s', '%s');", firstName, lastName, address, phoneNumber);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        deleteCustomer(customer.getCustomerId());
    }

    public static void deleteCustomer(int customerId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM customer WHERE customer_id='%s';", customerId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void updateCustomer(Customer customer, String column, String newValue) throws SQLException {
        updateCustomer(customer.getCustomerId(), column, newValue);
    }

    public static void updateCustomer(int customerId, String column, String newValue) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("UPDATE customer SET %s='%s' WHERE customer_id=%s", column, newValue, customerId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }
}