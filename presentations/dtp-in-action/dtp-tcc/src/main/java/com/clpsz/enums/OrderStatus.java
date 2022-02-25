package com.clpsz.enums;

/**
 * @author clpsz
 */

public enum OrderStatus {
    /**
     *
     */
    INIT("INIT", 0),
    NORMAL("NORMAL", 1),
    CANCELED("CANCELED", 2),
    FINISHED("FINISHED", 10);


    private final Integer val;

    private final String desc;


    OrderStatus(String desc, Integer val) {
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
