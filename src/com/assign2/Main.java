/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.business.Customer;
import com.assign2.data.CustomerAccess;
import java.sql.SQLException;

/**
 *
 * @author Jason Recillo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        try {
            CustomerAccess.addNewCustomer( "asdf", "ghjk", "fdxgfhghd", "123456890");
            CustomerAccess.deleteCustomer( "4");
            CustomerAccess.updateCustomer( CustomerAccess.FIRST_NAME, "5", "JD");
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
    }
}
