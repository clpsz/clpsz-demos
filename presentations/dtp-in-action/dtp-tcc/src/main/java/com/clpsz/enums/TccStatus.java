package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum TccStatus {
    /**
     *
     */
    INIT("INIT", "INIT"),
    TO_CONFIRM("TO_CONFIRM", "TO_CONFIRM"),
    TO_CANCEL("TO_CANCEL", "TO_CANCEL"),
    FINISHED("FINISHED", "FINISHED");


    private final String val;

    private final String comment;


    TccStatus(String val, String comment) {
        this.val = val;
        this.comment = comment;
    }

    public String getVal() {
        return this.val;
    }
}
