/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.data;

import com.assign2.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jason Recillo
 */
public class CommonAccess {
    private CommonAccess() {
    }

    public static Connection dbConnect() {
        try {
            String host = "jdbc:mysql://jaysan1292.com:3306/jrecillo_school";
            String user = "jrecillo";
            String pass = "parallelline";
            Connection conn = DriverManager.getConnection( host, user, pass );
            return conn;
        } catch ( SQLException ex ) {
            Utils.log_error( ex.getMessage() );
            return null;
        }
    }
}
