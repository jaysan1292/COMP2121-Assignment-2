/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import java.util.Date;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Jason Recillo
 */
public class OrderAccess extends CommonAccess {
    public static final String ORDER_ID = "order_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String ORDER_DATE = "order_date";

    private OrderAccess() {
        super();
    }

    public void addNewOrder(int orderId, int customerId, Date date) {
        throw new NotImplementedException();
    }

    public void deleteOrder(int orderId) {
        throw new NotImplementedException();
    }

    public void findOrder(String column, String value) {
        throw new NotImplementedException();
    }

    public void updateOrder(int orderId, String column, String newValue) {
        throw new NotImplementedException();
    }
}
