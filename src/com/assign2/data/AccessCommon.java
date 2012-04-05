package com.assign2.data;

import com.assign2.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Observable;

/**
 *
 * @author Jason Recillo
 */
public abstract class AccessCommon {
    static Connection connection = null;

    public AccessCommon() {
    }

    private static void _dbConnect() {
        try {
            Utils.log_info("Connecting to database...");
            String host = "jdbc:mysql://jaysan1292.com:3306/jrecillo_school";
            String user = "jrecillo";
            String pass = "parallelline";
            Connection conn = DriverManager.getConnection(host, user, pass);
            connection = conn;
        } catch (SQLException ex) {
                Utils.log_error("Could not connect to database.");
        }
    }

    public static Connection dbConnect() {
        if (connection == null) {
            _dbConnect();
        }
        return connection;
    }
}
