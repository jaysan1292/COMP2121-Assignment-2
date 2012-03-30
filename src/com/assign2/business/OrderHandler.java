/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import com.assign2.data.CustomerAccess;
import com.assign2.data.ItemAccess;
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

    public void addItemsToOrder(int itemId, int qty) {
        Item item= null;
        try {
            item = ItemAccess.findItem( ItemAccess.ITEM_ID, String.valueOf( itemId ) );
        } catch (SQLException ex) {
            Logger.getLogger(OrderHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        addItemsToOrder( item, qty );
    }

    public void addItemsToOrder(Item item, int qty) {
        //
    }
}
