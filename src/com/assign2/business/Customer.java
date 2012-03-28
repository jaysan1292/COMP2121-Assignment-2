/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

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
    
    public Customer( String FirstN, String LastN, String add, String phoneNum)
     {
         
         firstName = FirstN;
         lastName = LastN;
         address = add;
         phoneNumber = phoneNum;
           
     }
    public void setFirstName(String FirstN)
    {
        firstName = FirstN;
    }
     public void setLastName(String LastN)
    {
        lastName = LastN;
    }
      public void setAddress(String _Add)
    {
        address = _Add;
    }
       public void setPhoneNumber(String _phoneNum)
    {
        phoneNumber = _phoneNum;
    }
    public String getFirstName()
    {
        return firstName;
    }
     public String getLastName()
    {
        return lastName;
    }
      public String getAddress()
    {
       return address;
    }
       public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
     
    
}


