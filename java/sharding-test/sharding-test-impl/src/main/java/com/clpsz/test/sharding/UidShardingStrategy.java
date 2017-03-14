package com.clpsz.test.sharding;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by clpsz on 2017/3/14.
 */
public class UidShardingStrategy {

    private final Long UID_DIV_NUM = 1000L;// 表名分片基数

    private final Long DIV_NUM = 10000000L;// 数据库set分片基数



    public List<String> parse(Map<String, Object> params) {
        String uidString = getString(params, "uid");
        if (!StringUtils.isEmpty(uidString)) {
            return shardByUid(Long.parseLong(uidString));
        }



        return null;
    }

    private List<String> shardByUid(Long uid) {
        Long setNum = uid / DIV_NUM;
        if (setNum < 0 || setNum > 99) {
            throw new IllegalArgumentException("set超时预设范围" + String.valueOf(setNum));
        }
        String setNumStr = StringUtils.leftPad(setNum.toString(), 2, "0");


        Long table = uid % UID_DIV_NUM;
        String tableStr = table.toString();
        if (tableStr.length() < 3) {
            tableStr = StringUtils.leftPad(tableStr, 3, "0");
        }
        String dbNumStr = StringUtils.substring(tableStr, 0, 2);
        String tableNumStr = StringUtils.substring(tableStr, 2, 3);

        return Lists.newArrayList(setNumStr, dbNumStr, tableNumStr);
    }

    private String getString(Map<String, Object> data, String key) {
        String st = null;
        Object obj = data.get(key);
        if (null != obj) {
            st = obj.toString();
        }
        return st;
    }
}
