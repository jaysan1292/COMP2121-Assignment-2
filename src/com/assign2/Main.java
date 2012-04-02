/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

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
            OrderLineAccess.addNewOrderLine(OrderAccess.findOrder(OrderAccess.ORDER_ID, "1"), ItemAccess.findItem(ItemAccess.ITEM_ID, "25"), 3);
            System.out.println("-------------------------------------------------------------------------------------");
            OrderLineAccess.updateOrderLine(1, 25, 1);
            System.out.println("-------------------------------------------------------------------------------------");
            OrderLineAccess.deleteOrderLine(1, 25);
        } catch (Exception ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
