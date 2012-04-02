/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Item;
import com.assign2.business.Order;
import com.assign2.business.OrderLine;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason Recillo
 */
public class OrderLineAccess extends CommonAccess {
    public static final String ORDER_ID = "order_id";
    public static final String ITEM_ID = "item_id";
    public static final String QUANTITY = "quantity";
    public static final String TOTAL = "total";

    private OrderLineAccess() {
        super();
    }

    public static OrderLine findOrderLine(String column, String value) throws SQLException,IllegalArgumentException {
        if (!column.equals(ORDER_ID) || !column.equals(ITEM_ID)) throw new IllegalArgumentException(String.format("Cannot search using \"%s\"", column));

        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM order_line WHERE %s='%s';", column, value);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        OrderLine ol = new OrderLine();
        ol.setOrder(OrderAccess.findOrder(OrderAccess.ORDER_ID, resultSet.getString(ORDER_ID)));
        ol.setItem(ItemAccess.findItem(ItemAccess.ITEM_ID, resultSet.getString(ITEM_ID)));
        ol.setQuantity(resultSet.getInt(QUANTITY));
        ol.setTotal(resultSet.getDouble(TOTAL));

        return ol;
    }

    public static void addNewOrderLine(int orderId, int itemId, int quantity) throws SQLException {
        double price = ItemAccess.findItem(ItemAccess.ITEM_ID, String.valueOf(quantity)).getPrice();
        addNewOrderLine(orderId, itemId, quantity, price);
    }

    private static void addNewOrderLine(int orderId, int itemId, int quantity, double price) throws SQLException {
        // This method is private to prevent from specifying price manually
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO order_line ";
        query += "(order_id, item_id, quantity, total) ";
        query += String.format("VALUES('%s', '%s', %d, %.2f);", orderId, itemId, quantity, price);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void addNewOrderLine(Order order, Item item, int quantity) throws SQLException {
        double price = item.getPrice() * quantity;
    }

    public static void deleteOrderLine(Order order, Item item) throws SQLException {
        deleteOrderLine(order.getOrderId(), item.getItemId());
    }

    public static void deleteOrderLine(int orderId, int itemId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM order_line WHERE order_id=%d AND item_id=%d;", orderId, itemId);

        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void updateOrderLine(Order order, Item item, int newQuantity) throws SQLException {
        updateOrderLine(order.getOrderId(), item.getItemId(), newQuantity);
    }

    public static void updateOrderLine(int orderId, int itemId, int newQuantity) throws SQLException {
        deleteOrderLine(orderId, itemId);
        addNewOrderLine(orderId, itemId, newQuantity);
    }
}
