/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.business.*;
import com.assign2.data.*;

/**
 *
 * @author Jason Recillo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Order order = OrderAccess.findOrder(1);
            Item item = ItemAccess.findItem(2);
            OrderLineAccess.updateOrderLine(order, item, 1, OrderLineAccess.Mode.REPLACE);
        } catch (Exception ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
