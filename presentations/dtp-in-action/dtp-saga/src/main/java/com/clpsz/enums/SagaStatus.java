package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum SagaStatus {
    /**
     *
     */
    INIT("INIT", "INIT"),
    FINISHED("FINISHED", "FINISHED"),
    CANCELED("CANCELED", "CANCELED");


    private final String val;

    private final String comment;


    SagaStatus(String val, String comment) {
        this.val = val;
        this.comment = comment;
    }

    public String getVal() {
        return this.val;
    }
}
