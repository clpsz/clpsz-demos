package com.clpsz.domain;


/**
 * @author clpsz
 */
public class Tcc {
    private Long tccId;
    private String tccStatus;
    private Long createTime;
    private Long updateTime;

    public Tcc(Long tccId, String tccStatus, Long createTime, Long updateTime) {
        this.tccId = tccId;
        this.tccStatus = tccStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getTccId() {
        return tccId;
    }

    public void setTccId(Long tccId) {
        this.tccId = tccId;
    }

    public String getTccStatus() {
        return tccStatus;
    }

    public void setTccStatus(String tccStatus) {
        this.tccStatus = tccStatus;
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
