/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.business.OrderLine;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public static void addNewOrderLine(int orderId, int itemId, int quantity, double total) {
        throw new NotImplementedException();
    }

    public static void deleteOrderLine(int orderLineId) {
        throw new NotImplementedException();
    }

    public static OrderLine findOrderLine(String column, String value) {
        throw new NotImplementedException();
    }

    public static void updateOrderLine(int orderLineId, String column, String newValue) {
        throw new NotImplementedException();
    }
}
