/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.data.CustomerAccess;
import com.assign2.business.Customer;
/**
 *
 * @author Jason Recillo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

      Customer a = new Customer();
      a= a.findCustomer("last", "james");
      a.removeCustomer(a);

    }
}
