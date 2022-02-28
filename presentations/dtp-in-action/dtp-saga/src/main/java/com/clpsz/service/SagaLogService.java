package com.clpsz.service;


import com.clpsz.JDBCConnection;
import com.clpsz.dao.SagaLogDao;
import com.clpsz.domain.Item;
import com.clpsz.domain.ItemReserveStock;
import com.clpsz.domain.SagaLog;
import com.clpsz.enums.ReserveStatus;
import com.clpsz.enums.SagaLogEventStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @author clpsz
 */
public class SagaLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final SagaLogDao sagaLogDao = new SagaLogDao();

    public List<SagaLog> selectSagaLog(Long sagaId) throws SQLException {
        Connection conn = JDBCConnection.getItemConn();
        return sagaLogDao.selectBySagaId(conn, sagaId);
    }

    public Long insertSagaLog(Long sagaId, String event) throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Long sagaLogId = sagaLogDao.insertSagaLog(conn, sagaId, event, SagaLogEventStatus.INIT.getVal());

            conn.commit();
            return sagaLogId;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return null;
        } finally {
            conn.close();
        }
    }

    public boolean updateSagaLogEventStatus(Long sagaId, String event, String status) throws SQLException {
        logger.info("SAGA: updateSagaLogEventStatus for sagaId={}, event={}", sagaId, event);

        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            SagaLog sagaLog = sagaLogDao.selectBySagaIdAndEventForUpdate(conn, sagaId, event);
            if (sagaLog == null) {
                throw new RuntimeException("sagaLog not exist");
            }

            sagaLogDao.updateSagaLogEventStatus(conn, sagaLog.getSagaLogId(), status);
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
}
