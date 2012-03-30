/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Category;
import com.assign2.business.Item;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Jason Recillo
 */
public class ItemAccess extends CommonAccess {
    public static final String ITEM_ID = "item_id";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "price";
    public static final String IMAGE = "image";

    public static Item findItem(String column, String value) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM item WHERE %s='%s';", column, value);
        Utils.log_info("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Item item =  new Item();
        item.setItemId(resultSet.getInt(ITEM_ID));
        item.setName(resultSet.getString(NAME));
        item.setCategory(CategoryAccess.findCategory(CategoryAccess.CATEGORY_ID, resultSet.getString(CATEGORY)));
        item.setPrice(resultSet.getDouble(PRICE));
        item.setDescription(resultSet.getString(DESCRIPTION));
        // TODO: retrieve image from database and set it in code

        return item;
    }
    
    public void addNewItem(int itemId, String name, Category category,double price,String description,Image image, int qtyInStock){
        throw new NotImplementedException();
    }
    
    public void deleteItem(int itemId){
        throw new NotImplementedException();
    }
    
    public void updateItem(int itemId, String column, String value){
        throw new NotImplementedException();
    }
}
