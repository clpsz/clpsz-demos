package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum TccStatus {
    /**
     *
     */
    INIT("INIT", 0),
    TO_CONFIRM("TO_CONFIRM", 1),
    TO_CANCEL("TO_CANCEL", 2),
    FINISHED("FINISHED", 10);


    private final Integer val;

    private final String desc;


    TccStatus(String desc, Integer val) {
        this.val = val;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public Integer getVal() {
        return this.val;
    }
}
