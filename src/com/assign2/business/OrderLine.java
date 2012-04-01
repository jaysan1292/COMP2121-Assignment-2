/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

/**
 *
 * @author janjong
 */
public class OrderLine {
    private Order order;
    private Item item;
    private int quantity;
    private double total;

    public OrderLine() {
    }

    public Order getOrder() {
        return order;
    }

    /**
     * @param order the orderId to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return the itemId
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the itemId to set
     */
    public void setItem(Item item) {
        this.item = item;
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
}