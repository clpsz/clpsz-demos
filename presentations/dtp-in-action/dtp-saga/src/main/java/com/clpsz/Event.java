package com.clpsz;


public class Event {
    private String name;
    private Long sagaId;
    private Boolean succeed;

    public Event(Long sagaId, String name, Boolean succeed) {
        this.sagaId = sagaId;
        this.name = name;
        this.succeed = succeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSagaId() {
        return sagaId;
    }

    public void setSagaId(Long sagaId) {
        this.sagaId = sagaId;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }
}