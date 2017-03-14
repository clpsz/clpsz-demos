package com.clpsz.test.sharding.sqlparser;

import net.sf.jsqlparser.schema.Table;

import java.util.List;

/**
 * Created by clpsz on 2017/3/14.
 */
public interface SqlParser {
    List<Table> getTables();

    String toSQL();
}
