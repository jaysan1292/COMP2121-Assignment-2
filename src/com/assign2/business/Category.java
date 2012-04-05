/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.business;

/**
 *
 * @author janjong
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private Category parentCategory;

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public Category setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Category setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        return this;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

    public boolean equals(Category otherCategory) {
        return categoryId == otherCategory.categoryId;
    }
}