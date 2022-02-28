package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum SagaLogEventStatus {
    /**
     *
     */
    INIT("INIT", "INIT"),
    SUCCEED("SUCCEED", "SUCCEED"),
    CANCELED("CANCELED", "CANCELED");


    private final String val;

    private final String comment;


    SagaLogEventStatus(String val, String comment) {
        this.val = val;
        this.comment = comment;
    }

    public String getVal() {
        return this.val;
    }
}
