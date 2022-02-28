package com.clpsz.domain;


/**
 * @author clpsz
 */
public class Saga {
    private Long sagaId;
    private String sagaStatus;
    private Long createTime;
    private Long updateTime;

    public Saga(Long sagaId, String tccStatus, Long createTime, Long updateTime) {
        this.sagaId = sagaId;
        this.sagaStatus = tccStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getSagaId() {
        return sagaId;
    }

    public void setSagaId(Long sagaId) {
        this.sagaId = sagaId;
    }

    public String getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(String sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
