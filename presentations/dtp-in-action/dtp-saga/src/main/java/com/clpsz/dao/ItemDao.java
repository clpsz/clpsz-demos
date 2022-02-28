package com.clpsz.dao;

import com.clpsz.domain.Item;
import com.clpsz.util.TimeUtil;

import java.sql.*;


/**
 * @author clpsz
 */
public class ItemDao {

    public Item selectById(Connection conn, Long itemId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from item_table where item_id=%d", itemId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Item(
                rs.getLong("item_id"),
                rs.getString("title"),
                rs.getLong("price"),
                rs.getLong("stock"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
                );
    }

    public Item selectByIdForUpdate(Connection conn, Long itemId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from item_table where item_id=%d for update", itemId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Item(
                rs.getLong("item_id"),
                rs.getString("title"),
                rs.getLong("price"),
                rs.getLong("stock"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
                );
    }

    public Integer updateItemStock(Connection conn, Long itemId, Long stock) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String query = String.format("update item_table set stock=%d, update_time=%d where item_id=%d", stock, ts, itemId);
        PreparedStatement prepStat = conn.prepareStatement(query);
        return prepStat.executeUpdate();
    }
}
