package com.clpsz.domain;


/**
 * @author clpsz
 */
public class SagaLog {
    private Long sagaLogId;
    private Long sagaId;
    private String event;
    private String eventStatus;
    private Long createTime;
    private Long updateTime;

    public SagaLog(Long sagaId, String event, String eventStatus, Long createTime, Long updateTime) {
        this.sagaLogId = sagaId;
        this.event = event;
        this.eventStatus = eventStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getSagaLogId() {
        return sagaLogId;
    }

    public void setSagaLogId(Long sagaLogId) {
        this.sagaLogId = sagaLogId;
    }

    public Long getSagaId() {
        return sagaId;
    }

    public void setSagaId(Long sagaId) {
        this.sagaId = sagaId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
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
