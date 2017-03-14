package com.clpsz.test.sharding.sqlparser;

import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public class InsertSqlParser implements SqlParser {
    private boolean inited = false;
    private Insert statement;
    private List<Table> tables = new ArrayList();

    public InsertSqlParser(Insert statement) {
        this.statement = statement;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public void init() {
        if(!this.inited) {
            this.inited = true;
            this.tables.add(this.statement.getTable());
        }
    }

    public String toSQL() {
        StatementDeParser deParser = new StatementDeParser(new StringBuilder());
        this.statement.accept(deParser);
        return deParser.getBuffer().toString();
    }
}
