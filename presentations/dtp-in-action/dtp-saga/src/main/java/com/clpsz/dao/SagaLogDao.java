package com.clpsz.dao;

import com.clpsz.domain.Item;
import com.clpsz.domain.SagaLog;
import com.clpsz.util.TimeUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author clpsz
 */
public class SagaLogDao {

    public SagaLog selectBySagaIdAndEventForUpdate(Connection conn, Long sagaId, String event) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from saga_log_table where saga_id=%d and event='%s' for update", sagaId, event);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new SagaLog(
                rs.getLong("saga_log_id"),
                rs.getString("event"),
                rs.getString("event_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
        );
    }

    public List<SagaLog> selectBySagaId(Connection conn, Long sagaId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from saga_log_table where saga_id=%d", sagaId);
        ResultSet rs = stmt.executeQuery(sql);

        List<SagaLog> res = new ArrayList<>();
        while(rs.next()) {
            res.add(new SagaLog(
                    rs.getLong("saga_log_id"),
                    rs.getString("event"),
                    rs.getString("event_status"),
                    rs.getLong("create_time"),
                    rs.getLong("update_time")
            ));
        }

        return res;
    }

    public Long insertSagaLog(Connection conn, Long sagaId, String event, String eventStatus) throws SQLException {
        String query = "insert into saga_log_table (saga_id, event, event_status, create_time, update_time) values" +
                " (?, ?, ?, ?, ?)";
        PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepStat.setLong(1, sagaId);
        prepStat.setString(2, event);
        prepStat.setString(3, eventStatus);

        long ts = TimeUtil.getMillTimeStamp();
        prepStat.setLong(4, ts);
        prepStat.setLong(5, ts);

        prepStat.executeUpdate();

        ResultSet rs = prepStat.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getLong(1);
        } else {
            return null;
        }
    }

    public Integer updateSagaLogEventStatus(Connection conn, Long sagaLogId, String status) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String sql = String.format("update saga_log_table set event_status='%s', update_time=%d where saga_log_id=%d", status, ts, sagaLogId);
        PreparedStatement prepStat = conn.prepareStatement(sql);
        return prepStat.executeUpdate();
    }
}
