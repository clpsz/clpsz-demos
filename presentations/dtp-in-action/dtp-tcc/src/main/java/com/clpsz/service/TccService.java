package com.clpsz.service;


import com.clpsz.JDBCConnection;
import com.clpsz.dao.TccDao;
import com.clpsz.domain.Tcc;
import com.clpsz.enums.TccStatus;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author clpsz
 */
public class TccService {

    private final TccDao tccDao = new TccDao();

    public Long insertTcc() throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);

        try {
            Long tccId = tccDao.insertTcc(conn);

            conn.commit();
            return tccId;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return null;
        } finally {
            conn.close();
        }
    }

    public Integer updateTccFromInit(Long tccId, String tccStatus) throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);
        try {
            Tcc tcc = tccDao.selectByTccIdForUpdate(conn, tccId);
            if (!tcc.getTccStatus().equals(TccStatus.INIT.getVal())) {
                throw new RuntimeException("expected tcc status is INIT, get %d " + tcc.getTccStatus());
            }
            Integer updated = tccDao.updateTccStatus(conn, tccId, tccStatus);
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

    public Integer updateTccToFinished(Long tccId) throws SQLException {
        Connection conn = JDBCConnection.getItemConn();

        conn.setAutoCommit(false);
        try {
            Tcc tcc = tccDao.selectByTccIdForUpdate(conn, tccId);
            boolean statusIsToConfirmOrToCancel = tcc.getTccStatus().equals(TccStatus.TO_CONFIRM.getVal()) ||
                    tcc.getTccStatus().equals(TccStatus.TO_CANCEL.getVal());
            if (!statusIsToConfirmOrToCancel) {
                throw new RuntimeException("expected tcc status is TO_CONFIRM or TO_CANCEL, get %d " + tcc.getTccStatus());
            }
            Integer updated = tccDao.updateTccStatus(conn, tccId, TccStatus.FINISHED.getVal());
            conn.commit();

            return updated;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
            conn.close();
        }
    }}
