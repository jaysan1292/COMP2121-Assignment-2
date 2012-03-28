/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

/**
 *
 * @author Jason Recillo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        Utils.log_info("Connecting to database.");
        java.sql.Connection conn = com.assign2.data.CommonAccess.dbConnect();
        Utils.log_debug( conn.toString() );
    }
}
