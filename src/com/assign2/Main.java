/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.business.Customer;
import com.assign2.business.CustomerHandler;

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
            Customer c1 = new Customer();
            c1.setFirstName("asdsdsa");
            c1.setLastName("asd");
        } catch (Exception ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
