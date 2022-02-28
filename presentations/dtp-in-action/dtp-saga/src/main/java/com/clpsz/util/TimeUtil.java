package com.clpsz.util;

import java.util.Calendar;

/**
 * @author clpsz
 */
public class TimeUtil {
    public static Long getMillTimeStamp() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
