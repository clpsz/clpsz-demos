package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum OrderStatus {
    /**
     *
     */
    INIT("INIT", "INIT"),
    NORMAL("NORMAL", "NORMAL"),
    CANCELED("CANCELED", "CANCELED"),
    FINISHED("FINISHED", "FINISHED");


    private final String val;

    private final String comment;


    OrderStatus(String val, String comment) {
        this.val = val;
        this.comment = comment;
    }

    public String getVal() {
        return this.val;
    }
}
