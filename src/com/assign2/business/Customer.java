/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

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
    
    public String getFullName(){
        return String.format("%s %s", firstName,lastName);
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

    public Customer setCustomerId(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public Customer setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            this.phoneNumber = phoneNumber.replaceAll("[^\\d]", phoneNumber);
        } else {
            throw new IllegalArgumentException("Phone number was not valid.");
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s", customerId, firstName, lastName);
    }
}
