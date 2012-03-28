/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

/**
 *
 * @author janjong
 */
public class Item {
    private int itemId;
    private String category;
    private double price;
    private String description;
    private String Image;
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
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
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
    public void setPrice(double price) {
        this.price = price;
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
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the Image
     */
    public String getImage() {
        return Image;
    }

    /**
     * @param Image the Image to set
     */
    public void setImage(String Image) {
        this.Image = Image;
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
    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }
    
    
}
