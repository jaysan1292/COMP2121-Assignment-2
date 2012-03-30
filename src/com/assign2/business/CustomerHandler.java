/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import com.assign2.Utils;
import com.assign2.data.CustomerAccess;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janjong
 */
public class CustomerHandler {

    public void saveNewCustomer(Customer customer) {

        Utils.log_info("Sending new customer information to database");
        
        try {
            CustomerAccess.addNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getPhoneNumber());
        } catch (SQLException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void UpdateExistingCustomer() {
        Utils.log_info("Updating existing customer information");
        

    }

    
}
