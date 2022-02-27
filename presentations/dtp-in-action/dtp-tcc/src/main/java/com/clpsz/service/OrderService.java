package com.clpsz.service;


import com.clpsz.JDBCConnection;
import com.clpsz.dao.OrderDao;
import com.clpsz.domain.Order;
import com.clpsz.enums.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author clpsz
 */
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final OrderDao orderDao = new OrderDao();

    public boolean tryCreateOrder(Long tccId, Long itemId, Long itemCount, Long payedAmount) throws SQLException {
        logger.info("TCC: tryCreateOrder for tccId={}", tccId);

        Connection conn = JDBCConnection.getOrderConn();

        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (order != null) {
                logger.error("order already created");
                return true;
            }

            orderDao.insertOrder(conn, tccId, itemId, itemCount, payedAmount, OrderStatus.INIT.getVal());
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
        logger.info("TCC: confirmCreateOrder for tccId={}", tccId);

        Connection conn = JDBCConnection.getOrderConn();

        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (!order.getOrderStatus().equals(OrderStatus.INIT.getVal())) {
                logger.error("TCC: expected status {}, get {}", OrderStatus.INIT.getVal(), order.getOrderStatus());
                return false;
            }

            orderDao.updateOrderStatus(conn, order.getOrderId(), OrderStatus.NORMAL.getVal());
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
        logger.info("TCC: cancelCreateOrder for tccId={}", tccId);

        Connection conn = JDBCConnection.getOrderConn();

        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectByTccIdForUpdate(conn, tccId);
            if (order == null) {
                orderDao.insertOrder(conn, tccId, 0L, 0L, 0L, OrderStatus.CANCELED.getVal());
                return true;
            }
            if (!order.getOrderStatus().equals(OrderStatus.INIT.getVal())) {
                logger.error("TCC: expected status {}, get {}", OrderStatus.INIT.getVal(), order.getOrderStatus());
                return false;
            }

            orderDao.updateOrderStatus(conn, order.getOrderId(), OrderStatus.CANCELED.getVal());
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
