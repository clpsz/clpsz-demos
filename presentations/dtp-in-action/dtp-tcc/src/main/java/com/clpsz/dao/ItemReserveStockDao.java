package com.clpsz.dao;

import com.clpsz.domain.ItemReserveStock;
import com.clpsz.enums.ReserveStatus;
import com.clpsz.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;


/**
 * @author clpsz
 */
@Repository
public class ItemReserveStockDao {
    public Integer updateReserveStatus(Connection conn, Long irsId, String status) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String sql = String.format("update item_reserve_stock_table set reserve_status='%s', update_time=%d where irs_id=%d", status, ts, irsId);
        PreparedStatement prepStat = conn.prepareStatement(sql);
        return prepStat.executeUpdate();
    }

    public ItemReserveStock selectByTccIdForUpdate(Connection conn, Long tccId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from item_reserve_stock_table where tcc_id=%d for update", tccId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new ItemReserveStock(
                rs.getLong("irs_id"),
                rs.getLong("tcc_id"),
                rs.getLong("item_id"),
                rs.getLong("reserve_stock"),
                rs.getString("reserve_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
                );
    }

    public Long insertItemReserveStock(Connection conn, Long tccId, Long itemId, Long reserveStock) throws SQLException {
        String query = "insert into item_reserve_stock_table (tcc_id, item_id, reserve_stock, reserve_status, create_time, update_time) values" +
                " (?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepStat.setLong(1, tccId);
        prepStat.setLong(2, itemId);
        prepStat.setLong(3, reserveStock);
        prepStat.setString(4, ReserveStatus.INIT.getDesc());

        long ts = TimeUtil.getMillTimeStamp();
        prepStat.setLong(5, ts);
        prepStat.setLong(6, ts);

        prepStat.executeUpdate();

        ResultSet rs = prepStat.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getLong(1);
        } else {
            return null;
        }
    }
}
