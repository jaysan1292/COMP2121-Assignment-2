package com.assign2.business;

import com.assign2.Utils;
import com.assign2.data.CustomerAccess;
import java.sql.SQLException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author janjong
 */
public class CustomerHandler {
    public static void saveNewCustomer(Customer customer) {
        Utils.log_info("Saving %s's information to database...", customer.getFirstName());

        try {
            CustomerAccess.addNewCustomer(customer);
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
    }

    public static void updateExistingCustomer() {
        Utils.log_info("Updating existing customer information");
        throw new NotImplementedException();
    }

    public Customer findCustomer(String column, String value) {
        try {
            Utils.log_info("Searching for customer information");
            Customer customer = CustomerAccess.findCustomer(column, value);
            return customer;
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
            return null;
        }
    }

    public static void removeCustomer(int customerId) {
        try {
            CustomerAccess.deleteCustomer(customerId);
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
    }
}