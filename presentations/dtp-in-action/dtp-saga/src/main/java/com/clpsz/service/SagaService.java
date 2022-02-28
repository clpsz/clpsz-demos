package com.clpsz.service;


import com.clpsz.JDBCConnection;
import com.clpsz.dao.SagaDao;
import com.clpsz.domain.Saga;
import com.clpsz.enums.SagaStatus;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author clpsz
 */
public class SagaService {

    private final SagaDao sagaDao = new SagaDao();

    public Long insertSaga() throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Long sagaId = sagaDao.insertSaga(conn);

            conn.commit();
            return sagaId;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return null;
        } finally {
            conn.close();
        }
    }

    public Integer updateSagaStatus(Long sagaId, String sagaStatus) throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);
        try {
            Saga tcc = sagaDao.selectBySagaIdForUpdate(conn, sagaId);
            if (!tcc.getSagaStatus().equals(SagaStatus.INIT.getVal())) {
                throw new RuntimeException("expected tcc status is INIT, get %d " + tcc.getSagaStatus());
            }
            Integer updated = sagaDao.updateSagaStatus(conn, sagaId, sagaStatus);
            conn.commit();

            return updated;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
            conn.close();
        }
    }
}