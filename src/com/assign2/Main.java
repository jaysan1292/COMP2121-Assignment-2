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
            OrderAccess.findOrder(OrderAccess.ORDER_ID, "5");
        } catch (Exception ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
