/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.data.ItemAccess;

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
            ItemAccess.getItemImage(1,true);
//            Customer c1 = new Customer();
//            c1.setFirstName("asdsdsa");
//            c1.setLastName("asd");
//            OrderLine[] ol = OrderLineAccess.findOrderLine(OrderLineAccess.ORDER_ID, "1");
//            for (int i = 0; i < ol.length; i++) {
//                Utils.log_debug("OrderLine for order %s: item_id: %s | item_price: %.2f | quantity: %s",
//                                ol[i].getOrder().getOrderId(), ol[i].getItem().getItemId(), ol[i].getItem().getPrice(), ol[i].getQuantity());
//            }
        } catch (Exception ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
