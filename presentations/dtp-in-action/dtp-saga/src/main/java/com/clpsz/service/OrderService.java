package com.clpsz.service;


import com.clpsz.CreateOrderEvent;
import com.clpsz.EventCenter;
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

    public boolean createOrder(Long sagaId, Long itemId, Long itemCount, Long payedAmount) throws SQLException {
        logger.info("SAGA: createOrder for sagaId={}", sagaId);

        Connection conn = JDBCConnection.getOrderConn();

        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectBySagaIdForUpdate(conn, sagaId);
            if (order != null) {
                logger.error("order already created");
                return true;
            }

            orderDao.insertOrder(conn, sagaId, itemId, itemCount, payedAmount, OrderStatus.NORMAL.getVal());
            conn.commit();

            EventCenter.INSTANCE.putEvent(new CreateOrderEvent(sagaId, CreateOrderEvent.CREATE_ORDER, itemId, itemCount, true));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();

            EventCenter.INSTANCE.putEvent(new CreateOrderEvent(sagaId, CreateOrderEvent.CREATE_ORDER, itemId, itemCount, false));
            return false;
        } finally {
            conn.close();
        }
    }

    public boolean cancelOrder(Long sagaId) throws SQLException {
        logger.info("SAGA: cancelOrder for sagaId={}", sagaId);

        Connection conn = JDBCConnection.getOrderConn();

        conn.setAutoCommit(false);

        try {
            Order order = orderDao.selectBySagaIdForUpdate(conn, sagaId);
            if (order == null) {
                orderDao.insertOrder(conn, sagaId, 0L, 0L, 0L, OrderStatus.CANCELED.getVal());
                return true;
            }
            if (!order.getOrderStatus().equals(OrderStatus.NORMAL.getVal())) {
                logger.error("SAGA: expected status {}, get {}", OrderStatus.NORMAL.getVal(), order.getOrderStatus());
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
