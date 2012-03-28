/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

/**
 *
 * @author janjong
 */
public class OrderLine extends Item {
    private int orderId;
    private int itemId;
    private Item a = new Item();;
    private int quantity;
    private double total;

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @return the itemId
     */
    public int getitemId() {
        return a.getItemId();
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        a.setItemId(itemId);
    }
    
    
    
    
}
