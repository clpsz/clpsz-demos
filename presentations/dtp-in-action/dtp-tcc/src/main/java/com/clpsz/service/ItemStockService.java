package com.clpsz.service;


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

    public boolean tryReserveStock(Long tccId, Long itemId, Long stock) throws SQLException {
        logger.info("TCC: tryReserveStock for tccId={}", tccId);

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
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectByTccIdForUpdate(conn, tccId);
            if (itemReserveStock != null) {
                logger.error("TCC: item_reserve_stock already reserved");
                return true;
            } else {
                itemReserveStockDao.insertItemReserveStock(conn, tccId, itemId, stock, ReserveStatus.INIT.getVal());
                itemDao.updateItemStock(conn, itemId, item.getStock()-stock);
            }

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

    public boolean confirmReserveStock(Long tccId) throws SQLException {
        logger.info("TCC: confirmReserveStock for tccId={}", tccId);

        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectByTccIdForUpdate(conn, tccId);
            if (itemReserveStock.getReserveStatus().equals(ReserveStatus.INIT.getVal())) {
                itemReserveStockDao.updateReserveStatus(conn, itemReserveStock.getIrsId(), ReserveStatus.CONFIRMED.getVal());
                conn.commit();
                return true;
            } else if (itemReserveStock.getReserveStatus().equals(ReserveStatus.CONFIRMED.getVal())) {
                logger.error("TCC: item_reserver_stock already confirmed");
                return true;
            } else { // canceled, can not confirm
                throw new RuntimeException("canceled, can not confirm");
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
            conn.close();
        }
    }

    public boolean cancelReserveStock(Long tccId, Long itemId) throws SQLException {
        logger.info("TCC: cancelReserveStock for tccId={}", tccId);

        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Item item = itemDao.selectByIdForUpdate(conn, itemId);
            if (item == null) {
                throw new RuntimeException("item not exist");
            }
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectByTccIdForUpdate(conn, tccId);
            if (itemReserveStock != null) {
                if (itemReserveStock.getReserveStatus().equals(ReserveStatus.INIT.getVal())) {
                    itemReserveStockDao.updateReserveStatus(conn, itemReserveStock.getIrsId(), ReserveStatus.CANCELED.getVal());
                    itemDao.updateItemStock(conn, itemId, item.getStock()+itemReserveStock.getReserveStock());
                    conn.commit();
                    return true;
                } else if (itemReserveStock.getReserveStatus().equals(ReserveStatus.CANCELED.getVal())) {
                    logger.error("TCC: item_reserve_stock already canceled%n");
                    return true;
                } else { // confirmed, can not cancel
                    throw new RuntimeException("confirmed, can not cancel");
                }
            } else { // cancel reaches before try
                itemReserveStockDao.insertItemReserveStock(conn, tccId, itemId, 0L, ReserveStatus.CANCELED.getVal());
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
