package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason Recillo
 */
public class CategoryAccess extends AccessCommon {
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String PARENT_CATEGORY_ID = "parent_category_id";

    private CategoryAccess() {
        super();
    }

    public void addNewCategory(Category category) throws SQLException {
        addNewCategory(category.getCategoryName(), category.getParentCategory());
    }

    public void addNewCategory(String categoryName, Category parentCategory) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO category ";
        query += "(category_name, parent_category_id) ";
        query += String.format("VALUES('%s', '%s');", categoryName, parentCategory);

        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public void deleteCategory(Category category) throws SQLException {
        deleteCategory(category.getCategoryId());
    }

    public void deleteCategory(int categoryId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM category WHERE category_id='%s';", categoryId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static Category findCategory(String column, String value) throws SQLException {

        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM category WHERE %s='%s';", column, value);
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet resultSet = sqlStatement.executeQuery(query);

        if (!(resultSet.first())) {
            throw new SQLException("Result set returned no data.");
        }

        Category category = new Category();
        category.setCategoryId(resultSet.getInt(CATEGORY_ID));
        category.setCategoryName(resultSet.getString(CATEGORY_NAME));
        try {
            category.setParentCategory(findCategory(CATEGORY_ID, resultSet.getString(PARENT_CATEGORY_ID)));
        } catch (SQLException ex) {
            category.setParentCategory(null);
        }

        return category;
    }

    public void updateCategory(Category category, String column, String newValue) throws SQLException {
        updateCategory(category.getCategoryId(), column, newValue);
    }

    public void updateCategory(int categoryId, String column, String newValue) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("UPDATE category SET %s='%s' WHERE categoryId=%s", column, newValue, categoryId);
        Utils.log_debug("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public static Category[] getCategories() throws SQLException {
        ArrayList<Category> categoryList = new ArrayList<Category>();
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "SELECT * FROM category";
        Utils.log_debug("Executing SQL query: %s", query);
        ResultSet results = sqlStatement.executeQuery(query);

        while (results.next()) {
            Category category = new Category();
            category.setCategoryId(results.getInt(CATEGORY_ID));
            category.setCategoryName(results.getString(CATEGORY_NAME));
            try {
                category.setParentCategory(findCategory(CATEGORY_ID, results.getString(PARENT_CATEGORY_ID)));
            } catch (SQLException ex) {
                category.setParentCategory(null);
            }
            categoryList.add(category);
            Utils.log_debug("Retriving information for %s.", category.getCategoryName());
        }
        return categoryList.toArray(new Category[1]);
    }
}
