package com.clpsz.test.sharding.sqlparser;

/**
 * Created by clpsz on 2017/3/14.
 */
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public class UpdateSqlParser implements SqlParser {
    private boolean inited = false;
    private Update statement;
    private List<Table> tables = new ArrayList();

    public UpdateSqlParser(Update statement) {
        this.statement = statement;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public void init() {
        if(!this.inited) {
            this.inited = true;
            this.tables.addAll(this.statement.getTables());
        }
    }

    public String toSQL() {
        StatementDeParser deParser = new StatementDeParser(new StringBuilder());
        this.statement.accept(deParser);
        return deParser.getBuffer().toString();
    }
}
