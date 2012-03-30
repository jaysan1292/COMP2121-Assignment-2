/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import com.assign2.business.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Jason Recillo
 */
public class CategoryAccess extends CommonAccess {
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String PARENT_CATEGORY_ID = "parent_category_id";

    private CategoryAccess() {
        super();
    }

    public void addNewCategory(int categoryId, String categoryName, Category parentCategory) {
        throw new NotImplementedException();
    }

    public void deleteCategory(int categoryId) {
        throw new NotImplementedException();
    }

    public static Category findCategory(String column, String value) throws SQLException {

        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("SELECT * FROM category WHERE %s='%s';", column, value);
        Utils.log_info("Executing SQL query: %s", query);
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

    public void updateCategory(int categoryId, String column, String newValue) {
        throw new NotImplementedException();
    }
}
