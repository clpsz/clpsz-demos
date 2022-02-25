package com.clpsz.dao;

import com.clpsz.domain.ItemReserveStock;
import com.clpsz.domain.Tcc;
import com.clpsz.enums.ReserveStatus;
import com.clpsz.enums.TccStatus;
import com.clpsz.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;


/**
 * @author clpsz
 */
@Repository
public class TccDao {

    public Tcc selectByTccIdForUpdate(Connection conn, Long tccId) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = String.format("select * from tcc_table where tcc_id=%d for update", tccId);
        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return new Tcc(
                rs.getLong("tcc_id"),
                rs.getString("tcc_status"),
                rs.getLong("create_time"),
                rs.getLong("update_time")
        );
    }

    public Long insertTcc(Connection conn) throws SQLException {
        String query = "insert into tcc_table (tcc_status, create_time, update_time) values" +
                " (?, ?, ?)";
        PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        prepStat.setString(1, TccStatus.INIT.getDesc());

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

    public Integer updateTccStatus(Connection conn, Long tccId, String status) throws SQLException {
        Long ts = TimeUtil.getMillTimeStamp();
        String sql = String.format("update tcc_table set tcc_status='%s', update_time=%d where tcc_id=%d", status, ts, tccId);
        PreparedStatement prepStat = conn.prepareStatement(sql);
        return prepStat.executeUpdate();
    }
}
