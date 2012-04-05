/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import java.io.FileInputStream;
import java.util.ArrayList;
import com.assign2.Utils;
import com.assign2.business.Category;
import com.assign2.business.Item;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.*;

/**
 *
 * @author Jason Recillo
 */
public class ItemAccess extends AccessCommon {
    public static final String ITEM_ID = "item_id";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String QUANTITY_IN_STOCK = "quantity_in_stock";

    private ItemAccess() {
        super();
    }

    public static Item findItem(int value) throws SQLException {
        return findItem(ITEM_ID, String.valueOf(value));
    }

    public static Item findItem(String column, String value) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM item WHERE %s='%s';", column, value);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Item item = new Item();
        item.setItemId(resultSet.getInt(ITEM_ID));
        item.setName(resultSet.getString(NAME));
        item.setCategory(CategoryAccess.findCategory(CategoryAccess.CATEGORY_ID, resultSet.getString(CATEGORY)));
        item.setPrice(resultSet.getDouble(PRICE));
        item.setDescription(resultSet.getString(DESCRIPTION));
        try {
            item.setImage(getItemImage(item.getItemId(), false));
        } catch (SQLException sQLException) {
            item.setImage(null);
        }
        item.setQtyInStock(resultSet.getInt(QUANTITY_IN_STOCK));

        return item;
    }

    public static void addNewItem(Item item) throws SQLException, FileNotFoundException {
        addNewItem(item.getName(), item.getCategory(), item.getPrice(), item.getDescription(), item.getQtyInStock(), item.getImage());
    }

    public static void addNewItem(String name, Category category, double price, String description, int qtyInStock, String image) throws SQLException, FileNotFoundException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO item ";
        query += "(item_id, name, category, price, description, quantity_in_stock) ";
        query += String.format("VALUES(NULL, '%s', %s, %s, '%s', %s);",
                               name, category.getCategoryId(), price, description, qtyInStock);

        Utils.log_debug("Executing SQL query: %s", query);
        sqlStatement.executeUpdate(query);

        // Save image
        if ("".equals(image)) {
            return;
        }

        // get the item id of the newly inserted row
        query = String.format("SELECT * FROM item WHERE name='%s' AND price=%s AND quantity_in_stock=%s AND category=%s",
                              name, price, qtyInStock, category.getCategoryId());

        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet newItemId = sqlStatement.executeQuery(query);

        if (!newItemId.first()) {
            throw new SQLException("Failed to upload image :c");
        }

        int itemId = newItemId.getInt(ITEM_ID);

        saveItemImage(itemId, image);
    }

    public static void deleteItem(Item item) throws SQLException {
        deleteItem(item.getItemId());
    }

    public static void deleteItem(int itemId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM item WHERE item_id='%s';", itemId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static void updateItem(Item item, String column, String newValue) throws SQLException {
        updateItem(item.getItemId(), column, newValue);
    }

    public static void updateItem(int itemId, String column, String newValue) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("UPDATE item SET %s='%s' WHERE item_id=%s", column, newValue, itemId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    /**
     * Downloads the image for the specified item from the database and saves it to the user's temporary directory.
     * @param itemId the itemId to search the database for
     * @param thumb true for thumbnail size image (100x100) or false for full-size image
     * @return
     * @throws SQLException 
     */
    public static String getItemImage(int itemId, boolean thumb) throws SQLException {
        Utils.log_info("Retrieving image for item %s from database...", itemId);
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("Select image from item WHERE item_id=%s", itemId);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet result = sqlStatement.executeQuery(query);

        String imgData = "";
        if (result.next()) {
            try {
                // Get the binary data of the image and set it to a string
                // to create a byte array to hold the image data
                imgData = result.getString(IMAGE);
                byte[] img = new byte[imgData.length()];

                img = result.getBytes(IMAGE);

                // Create a temporary file to hold the image
                File imageFile = File.createTempFile("item_" + itemId, ".jpg");

                InputStream in = new ByteArrayInputStream(img);
                BufferedImage bufferedImage = ImageIO.read(in);

                // if thumbnail was requested, resize it to 100x100 pixels,
                // while keeping the aspect ratio
                if (thumb) {
                    bufferedImage = Scalr.resize(bufferedImage, Method.QUALITY, 100);
                }

                ImageIO.write(bufferedImage, "jpg", imageFile);

                Utils.log_debug("Saved image in %s", imageFile.getCanonicalFile());

                return imageFile.getAbsolutePath();
            } catch (Exception ex) {
                if (ex.getClass() == SQLException.class || ex.getClass() == NullPointerException.class) {
                    throw new SQLException(String.format("Image was not found for item with item id=%s.", itemId));
                } else {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void saveItem(Item i) {
        try {
            updateItem(i, NAME, i.getName());
            updateItem(i, CATEGORY, String.valueOf(i.getCategory()));
            updateItem(i, PRICE, String.valueOf(i.getPrice()));
            updateItem(i, DESCRIPTION, String.valueOf(i.getDescription()));
            updateItem(i, QUANTITY_IN_STOCK, String.valueOf(i.getQtyInStock()));
        } catch (SQLException ex) {
            Utils.log_error("Error updating item.");
        }
    }

    public static void saveItemImage(Item i, String filename) throws SQLException, FileNotFoundException {
        saveItemImage(i.getItemId(), filename);
    }

    public static void saveItemImage(int itemId, String filename) throws SQLException, FileNotFoundException {
        Connection conn = dbConnect();
        File image = new File(filename);

        String query = "UPDATE item SET image=? WHERE item_id=" + itemId;
        PreparedStatement sqlStatement = conn.prepareStatement(query);
        FileInputStream input = new FileInputStream(image);
        sqlStatement.setBinaryStream(1, input);
        Utils.log_debug("Executing SQL query: %s", query);
        int result = sqlStatement.executeUpdate();
        if (result > 0) {
            Utils.log_info("Uploaded image successfully!");
        } else {
            Utils.log_error("Image failed to upload :c");
            throw new SQLException("Image failed to upload :c");
        }
    }

    public static Item[] getItems() throws SQLException {
        return getItems(true);
    }

    public static Item[] getItems(boolean getImages) throws SQLException {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "SELECT item_id, name, category, price, description, quantity_in_stock FROM item;";
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet results = sqlStatement.executeQuery(query);

        while (results.next()) {
            Item item = new Item();
            item.setItemId(results.getInt(ITEM_ID));
            item.setName(results.getString(NAME));
            item.setCategory(CategoryAccess.findCategory(CategoryAccess.CATEGORY_ID, results.getString(CATEGORY)));
            item.setPrice(results.getDouble(PRICE));
            item.setDescription(results.getString(DESCRIPTION));
            if (getImages) {
                try {
                    item.setImage(getItemImage(item.getItemId(), false));
                } catch (SQLException sQLException) {
                    item.setImage(null);
                }
            }
            item.setQtyInStock(results.getInt(QUANTITY_IN_STOCK));
            itemList.add(item);
            Utils.log_debug("Retriving information for item %s.", item.getItemId());
        }
        return itemList.toArray(new Item[1]);
    }

    public static Item[] getItems(int lowerBound, int upperBound) throws SQLException {
        return getItems(lowerBound, upperBound, true);
    }

    public static Item[] getItems(int lowerBound, int upperBound, boolean getImages) throws SQLException {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT item_id, name, category, price, description, quantity_in_stock FROM item LIMIT %d, %d;", lowerBound, upperBound);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet results = sqlStatement.executeQuery(query);

        while (results.next()) {
            Item item = new Item();
            item.setItemId(results.getInt(ITEM_ID));
            item.setName(results.getString(NAME));
            item.setCategory(CategoryAccess.findCategory(CategoryAccess.CATEGORY_ID, results.getString(CATEGORY)));
            item.setPrice(results.getDouble(PRICE));
            item.setDescription(results.getString(DESCRIPTION));
            if (getImages) {
                try {
                    item.setImage(getItemImage(item.getItemId(), false));
                } catch (SQLException sQLException) {
                    item.setImage(null);
                }
            }
            item.setQtyInStock(results.getInt(QUANTITY_IN_STOCK));
            itemList.add(item);
            Utils.log_debug("Retriving information for item %s.", item.getItemId());
        }
        return itemList.toArray(new Item[1]);
    }
}
