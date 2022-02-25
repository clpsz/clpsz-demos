package com.clpsz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clpsz
 */

public enum ReserveStatus {
    /**
     *
     */
    INIT("INIT", 0),
    CONFIRMED("CONFIRMED", 1),
    CANCELED("CANCELED", 2);


    private final Integer val;

    private final String desc;


    ReserveStatus(String desc, Integer val) {
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
