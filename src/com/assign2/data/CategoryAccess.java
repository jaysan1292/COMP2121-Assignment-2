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

    public void addNewCategory(String categoryName, Category parentCategory) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = "INSERT INTO category ";
        query += "(category_name, parent_category_id) ";
        query += String.format("VALUES('%s', '%s');", categoryName, parentCategory);

        Utils.log_info("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }

    public void deleteCategory(int categoryId) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("DELETE FROM category WHERE category_id='%s';", categoryId);
        Utils.log_info("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
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

    public void updateCategory(int categoryId, String column, String newValue) throws SQLException {
        Connection conn = dbConnect();
        Statement sqlStatement = conn.createStatement();

        String query = String.format("UPDATE category SET %s='%s' WHERE categoryId=%s", column, newValue, categoryId);
        Utils.log_info("Executing SQL query: %s", query);

        sqlStatement.executeUpdate(query);
    }
}
