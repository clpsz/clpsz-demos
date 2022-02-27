package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum ReserveStatus {
    /**
     *
     */
    INIT("INIT", "INIT"),
    CONFIRMED("CONFIRMED", "CONFIRMED"),
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
