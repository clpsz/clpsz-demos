package com.clpsz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author clpsz
 */
public class JDBCConnection {

    public static Connection getOrderConn() throws SQLException {
        String db = "jdbc:mysql://127.0.0.1:3306/order_db?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true";
        String user = "root";
        String pass = "root";

        return DriverManager.getConnection(db, user, pass);
    }

    public static Connection getItemConn() throws SQLException {
        String dbUrl = "jdbc:mysql://127.0.0.1:3307/item_db?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true";
        String user = "root";
        String pass = "root";

        return DriverManager.getConnection(dbUrl, user, pass);
    }
}
