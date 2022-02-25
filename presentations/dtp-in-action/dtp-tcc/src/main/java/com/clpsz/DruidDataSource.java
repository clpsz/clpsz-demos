package com.clpsz;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author clpsz
 */
public class DruidDataSource {

    public final static DruidDataSource druidDataSource = new DruidDataSource();

    public DataSource getOrderDataSource() {
        Properties properties = new Properties();
        properties.setProperty(DruidDataSourceFactory.PROP_URL, "jdbc:mysql://127.0.0.1:3306/order_db?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true");

        properties.setProperty(DruidDataSourceFactory.PROP_USERNAME, "root");
        properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD, "root");

        properties.setProperty(DruidDataSourceFactory.PROP_NAME, "test-druid-ds");
        properties.setProperty(DruidDataSourceFactory.PROP_INITIALSIZE, "10");
        properties.setProperty(DruidDataSourceFactory.PROP_MAXACTIVE, "20");

        DataSource ds3306 = null;
        try {
            ds3306 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds3306;
    }

    public DataSource getItemDataSource() {
        Properties properties = new Properties();
        properties.setProperty(DruidDataSourceFactory.PROP_URL, "jdbc:mysql://127.0.0.1:3307/item_db?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true");
//        properties.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver");
        properties.setProperty(DruidDataSourceFactory.PROP_USERNAME, "root");
        properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD, "root");

        properties.setProperty(DruidDataSourceFactory.PROP_NAME, "test-druid-ds");
        properties.setProperty(DruidDataSourceFactory.PROP_INITIALSIZE, "10");
        properties.setProperty(DruidDataSourceFactory.PROP_MAXACTIVE, "20");

        DataSource ds3307 = null;
        try {
            ds3307 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds3307;
    }
}
