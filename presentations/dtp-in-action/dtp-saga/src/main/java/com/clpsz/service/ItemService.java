package com.clpsz.service;


import com.clpsz.JDBCConnection;
import com.clpsz.dao.ItemDao;
import com.clpsz.domain.Item;

import java.sql.Connection;


/**
 * @author clpsz
 */
public class ItemService {

    private final ItemDao itemDao = new ItemDao();

    public Item getItemById(Long itemId) {
        try (Connection conn = JDBCConnection.getItemConn()) {
            return itemDao.selectById(conn, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
