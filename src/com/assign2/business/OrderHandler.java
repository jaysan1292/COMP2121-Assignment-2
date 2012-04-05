/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import com.assign2.Utils;
import com.assign2.data.ItemAccess;
import com.assign2.data.OrderAccess;
import com.assign2.data.OrderLineAccess;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason Recillo
 */
public class OrderHandler {
    private OrderHandler() {
    }

    public void processNewOrder() {
    }

    public void addItemsToOrder(int orderId, int itemId, int qty) throws SQLException {
        addItemsToOrder(OrderAccess.findOrder(OrderAccess.ORDER_ID, String.valueOf(orderId)),
                        ItemAccess.findItem(ItemAccess.ITEM_ID, String.valueOf(itemId)), qty);
    }

    public void addItemsToOrder(Order order, Item item, int qty) {
        try {
            OrderLine newLine = new OrderLine(order, item, qty);
            OrderLine[] customerLines = OrderLineAccess.getOrderLines(order);
            boolean lineExists = orderLineExists(order, item, qty);

            if (lineExists) {
                OrderLineAccess.updateOrderLine(order, item, qty, OrderLineAccess.Mode.ADD);
            } else {
                OrderLineAccess.addNewOrderLine(order, item, qty);
            }
            // checkIfOrderLineExists(order, item, qty)
            // check database for current order and item
            // if SQLException is thrown in function, return false
            // if query executes without error, return true
            // it's 12:31AM, no time to implement >:
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
    }

    private boolean orderLineExists(Order order, Item item, int qty) {
        try {
            OrderLine[] line = OrderLineAccess.findOrderLine(OrderLineAccess.ORDER_ID, String.valueOf(order.getOrderId()));
            OrderLine theOrderLine = new OrderLine(order, item, qty);
            for (OrderLine ol : line) {
                if (ol.equals(theOrderLine)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            if (ex.getClass() == SQLException.class) {
                Utils.log_error(ex.getMessage());
            } else if (ex.getClass() == IllegalArgumentException.class) {
                Utils.log_error(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
