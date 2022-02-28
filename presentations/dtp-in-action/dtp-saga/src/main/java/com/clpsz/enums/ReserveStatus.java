package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum ReserveStatus {
    /**
     *
     */
    NORMAL("NORMAL", "NORMAL"),
    CANCELED("CANCELED", "CANCELED");


    private final String val;

    private final String comment;


    ReserveStatus(String val, String comment) {
        this.val = val;
        this.comment = comment;
    }

    public String getVal() {
        return this.val;
    }
}
