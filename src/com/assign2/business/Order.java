/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author janjong
 */
public class Order {
    private int orderId;
    private Customer customer;
    private Date date;
    private ArrayList<OrderLine> orderItems;

    public Order() {
        orderItems=new ArrayList<OrderLine>();
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public Order setOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * @return the customerId
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customerId to set
     */
    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderLine[] getOrderItems() {
        return orderItems.toArray(new OrderLine[1]);
    }

    public Order setOrderItems(OrderLine[] orderItems) {
        ArrayList<OrderLine> items = new ArrayList<OrderLine>();
        for (OrderLine item : items) {
            items.add(item);
        }
        this.orderItems = items;
        return this;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public Order setDate(Date date) {
        this.date = date;
        return this;
    }

    public void addItem(Item item, int qty) {
        orderItems.add(new OrderLine(this, item, qty));
    }

    public void removeItem(Item item) {
        for (OrderLine line : orderItems) {
            if (line.getItem().equals(item)){
                orderItems.remove(item);
            }
        }
    }
}