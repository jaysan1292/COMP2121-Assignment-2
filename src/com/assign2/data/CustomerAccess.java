/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason Recillo
 */
public class CustomerAccess {
    public static final String CUSTOMER_ID = "customer_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";

    public static Customer findCustomer( String column, String value ) {
        try {
            Utils.log_info( "Connecting to database..." );
            Connection conn = CommonAccess.dbConnect();
            Statement sqlStatement = conn.createStatement();
            
            String query = String.format( "select * from customer where %s='%s'", column, value );
            Utils.log_info( "Executing SQL query: %s", query );
            ResultSet resultSet = sqlStatement.executeQuery( query );

            if ( !( resultSet.first() ) ) {
                throw new SQLException( "Result set returned no data." );
            }

            Customer customer = new Customer();
            customer.setCustomerId( resultSet.getInt( "customer_id" ) );
            customer.setFirstName( resultSet.getString( "first_name" ) );
            customer.setLastName( resultSet.getString( "last_name" ) );
            customer.setAddress( resultSet.getString( "address" ) );
            customer.setPhoneNumber( resultSet.getString( "phone_number" ) );

            Utils.log_info( "Returned customer information:\n\t\tFirst name: %s\n\t\tLast name: %s", customer.getFirstName(), customer.getLastName() );

            return customer;
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
        return null;
    }
}
