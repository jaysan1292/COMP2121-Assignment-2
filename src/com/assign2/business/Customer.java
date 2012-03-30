/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import com.assign2.data.CustomerAccess;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

/**
 *
 * @author janjong
 */
public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number was not valid.");
        }



    }

   public void CreateCustomer(String firstName, String lastName, String address, String phoneNumber) {
        Customer newCustomer = new Customer(firstName, lastName, address, phoneNumber);
        CustomerHandler _Handler = new CustomerHandler();
      _Handler.saveNewCustomer(newCustomer);
        
    }

    
    

    public Customer findCustomer(String _column, String _value) {

     
        CustomerHandler _handler = new CustomerHandler();

        String column = "";

        switch (_column) {

            case "first":

                column = CustomerAccess.FIRST_NAME;

                break;

            case "last":

                column = CustomerAccess.LAST_NAME;

                break;

            case "cNum":

                column = CustomerAccess.CUSTOMER_ID;

                break;
            case "address":
                column = CustomerAccess.ADDRESS;

                break;

        }
        
       return (_handler.findCusomter(column,  _value));

        

    }
    
    public void removeCustomer(Customer customer)
    {   CustomerHandler _handler = new CustomerHandler();
    _handler.removeCustomer(customer.getCustomerId());        
    }
    
    public void updateCustomer(Customer customer, String _column, String newValue)
    { CustomerHandler _handler = new CustomerHandler();
        String column = "";

        switch (_column) {

            case "first":

                column = CustomerAccess.FIRST_NAME;

                break;

            case "last":

                column = CustomerAccess.LAST_NAME;

                break;

            case "cNum":

                column = CustomerAccess.CUSTOMER_ID;

                break;
            case "address":
                column = CustomerAccess.ADDRESS;

                break;

        }
    _handler.UpdateExistingCustomer(customer, column, newValue);
        
    }
}
