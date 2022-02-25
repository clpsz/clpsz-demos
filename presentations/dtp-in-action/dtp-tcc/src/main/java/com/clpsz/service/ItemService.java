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
public class ItemService {

    private final ItemDao itemDao = new ItemDao();

    public Item getItemById(Long itemId) throws SQLException {
        DataSource ds = DruidDataSource.druidDataSource.getItemDataSource();

        try (Connection conn = ds.getConnection()) {
            return itemDao.selectById(conn, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
