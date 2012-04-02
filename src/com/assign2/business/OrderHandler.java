/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import com.assign2.data.ItemAccess;
import java.sql.SQLException;

/**
 *
 * @author Jason Recillo
 */
public class OrderHandler {
    private OrderHandler() {
    }

    public void processNewOrder() {
    }

    public void addItemsToOrder(Order order, int itemId, int qty) throws SQLException {
        addItemsToOrder(order, ItemAccess.findItem(ItemAccess.ITEM_ID, String.valueOf(itemId)), qty);
    }

    public void addItemsToOrder(Order order, Item item, int qty) {
        // checkIfOrderLineExists(order, item, qty)
        // check database for current order and item
        // if SQLException is thrown in function, return false
        // if query executes without error, return true
        // it's 12:31AM, no time to implement >:
    }
}
