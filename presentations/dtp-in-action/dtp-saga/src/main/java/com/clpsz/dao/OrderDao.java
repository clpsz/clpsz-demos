package com.clpsz.dao;

import com.clpsz.domain.Order;
import com.clpsz.util.TimeUtil;

import java.sql.*;


/**
 * @author clpsz
 */
public class OrderDao {

    public Order selectBySagaIdForUpdate(Connection conn, Long tccId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from order_table where saga_id=%d for update", tccId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Order(
                rs.getLong("order_id"),
                rs.getLong("saga_id"),
                rs.getLong("item_id"),
                rs.getLong("item_count"),
                rs.getLong("payed_amount"),
                rs.getString("order_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
                );
    }

    public Order selectByIdForUpdate(Connection conn, Long orderId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from order_table where order_id=%d for update", orderId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Order(
                rs.getLong("order_id"),
                rs.getLong("saga_id"),
                rs.getLong("item_id"),
                rs.getLong("item_count"),
                rs.getLong("payed_amount"),
                rs.getString("order_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
                );
    }

    public Long insertOrder(Connection conn, Long tccId, Long itemId, Long itemCount, Long payedAmount, String status) throws SQLException {
        String query = "insert into order_table (saga_id, item_id, item_count, payed_amount, order_status, create_time, update_time) values" +
                " (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepStat.setLong(1, tccId);
        prepStat.setLong(2, itemId);
        prepStat.setLong(3, itemCount);
        prepStat.setLong(4, payedAmount);
        prepStat.setString(5, status);

        long ts = TimeUtil.getMillTimeStamp();
        prepStat.setLong(6, ts);
        prepStat.setLong(7, ts);

        prepStat.executeUpdate();

        ResultSet rs = prepStat.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getLong(1);
        } else {
            return null;
        }
    }

    public Integer updateOrderStatus(Connection conn, Long orderId, String status) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String query = String.format("update order_table set order_status='%s', update_time=%d where order_id=%d", status, ts, orderId);
        PreparedStatement prepStat = conn.prepareStatement(query);
        return prepStat.executeUpdate();
    }
}
