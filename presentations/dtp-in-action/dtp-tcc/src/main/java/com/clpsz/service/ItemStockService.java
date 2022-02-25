package com.clpsz.service;


import com.clpsz.DruidDataSource;
import com.clpsz.dao.ItemDao;
import com.clpsz.dao.ItemReserveStockDao;
import com.clpsz.domain.Item;
import com.clpsz.domain.ItemReserveStock;
import com.clpsz.enums.ReserveStatus;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author clpsz
 */
@Service
public class ItemStockService {

    private final ItemDao itemDao = new ItemDao();

    private final ItemReserveStockDao itemReserveStockDao = new ItemReserveStockDao();

    public boolean tryReserveStock(Long tccId, Long itemId, Long stock) throws SQLException {
        DataSource ds = DruidDataSource.druidDataSource.getItemDataSource();
        Connection conn;

        conn = ds.getConnection();
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
                System.out.println("already reserved");
                return true;
            } else {
                itemReserveStockDao.insertItemReserveStock(conn, tccId, itemId, stock);
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
        DataSource ds = DruidDataSource.druidDataSource.getItemDataSource();
        Connection conn;

        conn = ds.getConnection();
        conn.setAutoCommit(false);

        try {
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectByTccIdForUpdate(conn, tccId);
            if (itemReserveStock.getReserveStatus().equals(ReserveStatus.INIT.getDesc())) {
                itemReserveStockDao.updateReserveStatus(conn, itemReserveStock.getIrsId(), ReserveStatus.CONFIRMED.getDesc());
                conn.commit();
                return true;
            } else if (itemReserveStock.getReserveStatus().equals(ReserveStatus.CONFIRMED.getDesc())) {
                System.out.printf("already confirmed%n");
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
        DataSource ds = DruidDataSource.druidDataSource.getItemDataSource();
        Connection conn;

        conn = ds.getConnection();
        conn.setAutoCommit(false);

        try {
            Item item = itemDao.selectByIdForUpdate(conn, itemId);
            if (item == null) {
                throw new RuntimeException("item not exist");
            }
            ItemReserveStock itemReserveStock = itemReserveStockDao.selectByTccIdForUpdate(conn, tccId);
            if (itemReserveStock != null) {
                if (itemReserveStock.getReserveStatus().equals(ReserveStatus.INIT.getDesc())) {
                    itemReserveStockDao.updateReserveStatus(conn, itemReserveStock.getIrsId(), ReserveStatus.CANCELED.getDesc());
                    itemDao.updateItemStock(conn, itemId, item.getStock()+itemReserveStock.getReserveStock());
                    conn.commit();
                    return true;
                } else if (itemReserveStock.getReserveStatus().equals(ReserveStatus.CANCELED.getDesc())) {
                    System.out.printf("already canceled%n");
                    return true;
                } else { // confirmed, can not cancel
                    throw new RuntimeException("confirmed, can not cancel");
                }
            } else { // cancel reaches before try
                itemReserveStockDao.insertItemReserveStock(conn, tccId, itemId, 0L);
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
