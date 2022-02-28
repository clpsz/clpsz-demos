package com.clpsz.service;


import com.clpsz.CreateOrderEvent;
import com.clpsz.EventCenter;
import com.clpsz.JDBCConnection;
import com.clpsz.dao.ItemDao;
import com.clpsz.dao.ItemReserveStockDao;
import com.clpsz.domain.Item;
import com.clpsz.domain.ItemReserveStock;
import com.clpsz.enums.ReserveStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;



/**
 * @author clpsz
 */
public class ItemStockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final ItemDao itemDao = new ItemDao();

    private final ItemReserveStockDao itemReserveStockDao = new ItemReserveStockDao();

    public boolean deductStock(Long sagaId, Long itemId, Long stock) throws SQLException {
        logger.info("SAGA: deductStock for sagaId={}", sagaId);

        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Item item = itemDao.selectByIdForUpdate(conn, itemId);
            if (item == null) {
                throw new RuntimeException("item not exist");
            }
            if (item.getStock() < stock) {
                throw new RuntimeException(String.format("stock not enough, need=%d, remain=%d", item.getStock(), stock));
            }
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectBySagaIdForUpdate(conn, sagaId);
            if (itemReserveStock != null) {
                logger.error("SAGA: item_reserve_stock already reserved");

                EventCenter.INSTANCE.putEvent(new CreateOrderEvent(sagaId, CreateOrderEvent.DEDUCT_STOCK, itemId, stock, true));
                return true;
            } else {
                itemReserveStockDao.insertItemReserveStock(conn, sagaId, itemId, stock, ReserveStatus.NORMAL.getVal());
                itemDao.updateItemStock(conn, itemId, item.getStock()-stock);
            }

            EventCenter.INSTANCE.putEvent(new CreateOrderEvent(sagaId, CreateOrderEvent.DEDUCT_STOCK, itemId, stock, true));
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();

            EventCenter.INSTANCE.putEvent(new CreateOrderEvent(sagaId, CreateOrderEvent.DEDUCT_STOCK, itemId, stock, false));
            return false;
        } finally {
            conn.close();
        }
    }

    public boolean cancelDeductStock(Long sagaId, Long itemId) throws SQLException {
        logger.info("SAGA: cancelDeductStock for sagaId={}", sagaId);

        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Item item = itemDao.selectByIdForUpdate(conn, itemId);
            if (item == null) {
                throw new RuntimeException("item not exist");
            }
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectBySagaIdForUpdate(conn, sagaId);
            if (itemReserveStock != null) {
                if (itemReserveStock.getReserveStatus().equals(ReserveStatus.NORMAL.getVal())) {
                    itemReserveStockDao.updateReserveStatus(conn, itemReserveStock.getIrsId(), ReserveStatus.CANCELED.getVal());
                    itemDao.updateItemStock(conn, itemId, item.getStock()+itemReserveStock.getReserveStock());
                    conn.commit();
                    return true;
                } else if (itemReserveStock.getReserveStatus().equals(ReserveStatus.CANCELED.getVal())) {
                    logger.error("SAGA: item_reserve_stock already canceled%n");
                    return true;
                } else { // confirmed, can not cancel
                    throw new RuntimeException("confirmed, can not cancel");
                }
            } else { // cancel reaches before try
                itemReserveStockDao.insertItemReserveStock(conn, sagaId, itemId, 0L, ReserveStatus.CANCELED.getVal());
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
            conn.close();
        }
    }
}
