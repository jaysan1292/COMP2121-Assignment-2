/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Item;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public Item findItem(String column, String value) throws SQLException {
        Utils.log_info("Connecting to database...");
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM item WHERE %s='%s';", column, value);
        Utils.log_info("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Item item =  new Item();

        return item;
    }
    
    public void addNewItem() {
    }
}
