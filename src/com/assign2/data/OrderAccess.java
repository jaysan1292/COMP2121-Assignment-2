/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Customer;
import com.assign2.business.Order;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jason Recillo
 */
public class OrderAccess extends AccessCommon {
    public static final String ORDER_ID = "order_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String ORDER_DATE = "order_date";

    private OrderAccess() {
        super();
    }

    public static Order[] getOrders() throws SQLException {
        ArrayList<Order> orderList = new ArrayList<Order>();
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "SELECT * FROM `order`;";
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet results = sqlStatement.executeQuery(query);

        while (results.next()) {
            Order order = new Order();
            order.setOrderId(results.getInt(ORDER_ID));
            order.setCustomer(CustomerAccess.findCustomer(CustomerAccess.CUSTOMER_ID, results.getString(CUSTOMER_ID)));
            order.setDate(results.getDate(ORDER_DATE));
            orderList.add(order);
            Utils.log_debug("Retriving information for order %s.", order.getOrderId());
        }
        return orderList.toArray(new Order[1]);
    }

    public static void addNewOrder(Order order, Customer customer, Date date) throws SQLException {
        addNewOrder(order.getOrderId(), customer.getCustomerId(), date);
    }

    public static void addNewOrder(int orderId, int customerId, Date date) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO customer ";
        query += "(first_name, last_name, address, phone_number) ";
        query += String.format("VALUES(%s, %s, '%s');", orderId, customerId, date);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void deleteOrder(Order order) throws SQLException {
        deleteOrder(order.getOrderId());
    }

    public static void deleteOrder(int orderId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM order WHERE order_id='%s';", orderId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static Order findOrder(int orderId) throws SQLException {
        return findOrder(ORDER_ID, String.valueOf(orderId));
    }

    public static Order findOrder(String column, String value) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM `order` WHERE %s='%s';", column, value);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Order order = new Order();
        order.setOrderId(resultSet.getInt(ORDER_ID));
        order.setCustomer(CustomerAccess.findCustomer(CustomerAccess.CUSTOMER_ID, resultSet.getString(CUSTOMER_ID)));
        order.setDate(resultSet.getDate(ORDER_DATE));

        Utils.log_debug("Returned order information:\n\t\tOrder ID: %s\n\t\tCustomer Name: %s %s", order.getOrderId(), order.getCustomer().getFirstName(), order.getCustomer().getLastName());

        return order;
    }

    public static void updateOrder(Order order, String column, String newValue) throws SQLException {
        updateOrder(order.getOrderId(), column, newValue);
    }

    public static void updateOrder(int orderId, String column, String newValue) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("UPDATE order SET %s='%s' WHERE order_id=%s", column, newValue, orderId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }
}
