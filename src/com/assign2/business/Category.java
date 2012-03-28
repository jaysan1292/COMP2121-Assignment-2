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
    private int parentCategoryId;

    public Category() {
        categoryId = 0;
        categoryName = "";
        parentCategoryId = 0;

    }

    public Category(int cc, String cn, int pc) {
        categoryId = cc;
        categoryName = cn;
        parentCategoryId = pc;

    }

    public void setCategoryName(String CategoryName) {
        categoryName = CategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
    
    
}
