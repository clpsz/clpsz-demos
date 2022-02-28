package com.clpsz.dao;

import com.clpsz.domain.Saga;
import com.clpsz.enums.SagaStatus;
import com.clpsz.util.TimeUtil;

import java.sql.*;


/**
 * @author clpsz
 */
public class SagaDao {

    public Saga selectBySagaIdForUpdate(Connection conn, Long sagaId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from saga_table where saga_id=%d for update", sagaId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Saga(
                rs.getLong("saga_id"),
                rs.getString("saga_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
        );
    }

    public Long insertSaga(Connection conn) throws SQLException {
        String query = "insert into saga_table (saga_status, create_time, update_time) values" +
                " (?, ?, ?)";
        PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepStat.setString(1, SagaStatus.INIT.getVal());

        long ts = TimeUtil.getMillTimeStamp();
        prepStat.setLong(2, ts);
        prepStat.setLong(3, ts);

        prepStat.executeUpdate();

        ResultSet rs = prepStat.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getLong(1);
        } else {
            return null;
        }
    }

    public Integer updateSagaStatus(Connection conn, Long sagaId, String status) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String sql = String.format("update saga_table set saga_status='%s', update_time=%d where saga_id=%d", status, ts, sagaId);
        PreparedStatement prepStat = conn.prepareStatement(sql);
        return prepStat.executeUpdate();
    }
}
