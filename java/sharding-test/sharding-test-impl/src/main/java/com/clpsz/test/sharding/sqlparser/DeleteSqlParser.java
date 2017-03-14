package com.clpsz.test.sharding.sqlparser;

import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public class DeleteSqlParser implements SqlParser {
    private boolean inited = false;
    private Delete statement;
    private List<Table> tables = new ArrayList();

    public DeleteSqlParser(Delete statement) {
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
