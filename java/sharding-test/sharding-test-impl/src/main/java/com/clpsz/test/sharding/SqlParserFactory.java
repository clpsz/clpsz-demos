package com.clpsz.test.sharding;

import com.clpsz.test.sharding.sqlparser.*;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;
import java.sql.SQLException;

/**
 * Created by clpsz on 2017/3/14.
 */
public class SqlParserFactory {
    private static SqlParserFactory instance = new SqlParserFactory();
    private final CCJSqlParserManager manager = new CCJSqlParserManager();

    public static SqlParserFactory getInstance() {
        return instance;
    }

    public SqlParserFactory() {
    }

    public SqlParser createParser(String originalSql) throws SQLException {
        try {
            Statement e = this.manager.parse(new StringReader(originalSql));
            if(e instanceof Select) {
                SelectSqlParser delete3 = new SelectSqlParser((Select)e);
                delete3.init();
                return delete3;
            } else if(e instanceof Update) {
                UpdateSqlParser delete2 = new UpdateSqlParser((Update)e);
                delete2.init();
                return delete2;
            } else if(e instanceof Insert) {
                InsertSqlParser delete1 = new InsertSqlParser((Insert)e);
                delete1.init();
                return delete1;
            } else if(e instanceof Delete) {
                DeleteSqlParser delete = new DeleteSqlParser((Delete)e);
                delete.init();
                return delete;
            } else {
                throw new SQLException("Unsupported Parser[" + e.getClass().getName() + "]");
            }
        } catch (JSQLParserException var4) {
            throw new SQLException("SQL Parse Failed", var4);
        }
    }
}
