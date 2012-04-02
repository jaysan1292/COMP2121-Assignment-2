/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

import java.awt.Image;

/**
 *
 * @author janjong
 */
public class Item {
    private int itemId;
    private String name;
    private Category category;
    private double price;
    private String description;
    private Image image;
    private int qtyInStock;

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public Item setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public Item setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public Item setCategory(Category category) {
        this.category = category;
        return this;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public Item setPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return the Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the Image to set
     */
    public Item setImage(Image image) {
        this.image = image;
        return this;
    }

    /**
     * @return the qtyInStock
     */
    public int getQtyInStock() {
        return qtyInStock;
    }

    /**
     * @param qtyInStock the qtyInStock to set
     */
    public Item setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
        return this;
    }
}
