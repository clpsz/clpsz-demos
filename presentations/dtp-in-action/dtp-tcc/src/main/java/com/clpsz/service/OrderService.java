package com.clpsz.service;


import com.clpsz.DruidDataSource;
import com.clpsz.dao.OrderDao;
import com.clpsz.domain.Order;
import com.clpsz.enums.OrderStatus;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author clpsz
 */
@Service
public class OrderService {

    private final OrderDao orderDao = new OrderDao();

    public boolean tryCreateOrder(Long tccId, Long itemId, Long itemCount, Long payedAmount) throws SQLException {
        DataSource ds = DruidDataSource.druidDataSource.getOrderDataSource();
        Connection conn;

        conn = ds.getConnection();
        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (order != null) {
                System.out.println("already created");
                return true;
            }

            orderDao.insertOrder(conn, tccId, itemId, itemCount, payedAmount);
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
            conn.close();
        }
    }

    public boolean confirmCreateOrder(Long tccId) throws SQLException {
        DataSource ds = DruidDataSource.druidDataSource.getOrderDataSource();
        Connection conn;

        conn = ds.getConnection();
        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (!order.getOrderStatus().equals(OrderStatus.INIT.getDesc())) {
                System.out.printf("expected status %s, get %s%n", OrderStatus.INIT.getDesc(), order.getOrderStatus());
                return false;
            }

            orderDao.updateOrderStatus(conn, order.getOrderId(), OrderStatus.NORMAL.getDesc());
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
            conn.close();
        }
    }

    public boolean cancelCreateOrder(Long tccId) throws SQLException {
        DataSource ds = DruidDataSource.druidDataSource.getOrderDataSource();
        Connection conn;

        conn = ds.getConnection();
        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (!order.getOrderStatus().equals(OrderStatus.INIT.getDesc())) {
                System.out.printf("expected status %s, get %s%n", OrderStatus.INIT.getDesc(), order.getOrderStatus());
                return false;
            }

            orderDao.updateOrderStatus(conn, order.getOrderId(), OrderStatus.CANCELED.getDesc());
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
            conn.close();
        }
    }
}
