package com.clpsz.test.sharding;

import com.clpsz.test.sharding.sqlparser.SqlParser;
import com.google.common.collect.Maps;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class})})
public class ShardPlugin implements Interceptor {
    private Map<String, String> table2DB = Maps.newHashMap();
    private UidShardingStrategy strategy = new UidShardingStrategy();
    private final Field boundSqlField;


    public ShardPlugin() {
        try {
            this.boundSqlField = BoundSql.class.getDeclaredField("sql");
            this.boundSqlField.setAccessible(true);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public Object intercept(Invocation invocation) throws Throwable {
        BoundSql boundSql = getBoundSql(invocation);
        SqlParser sqlParser = SqlParserFactory.getInstance().createParser(boundSql.getSql());
        List<Table> tables = sqlParser.getTables();
        for (Table table : tables) {
            String logicTblName = table.getName();
            String logicDBName = table2DB.get(logicTblName);

            Map<String, Object> params = paresParams(boundSql);
            List<String> shardingCondition = strategy.parse(params);

            String realDBName = logicDBName + "_" + shardingCondition.get(0) + "_" + shardingCondition.get(1) + "_db";
            String realTblName = logicTblName + "_" + shardingCondition.get(2);

            table.setName(realTblName);
            table.setSchemaName(realDBName);
        }


        String targetSql = sqlParser.toSQL();
        boundSqlField.set(boundSql, targetSql);
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    public void setTable2DB(Map<String, String> table2DBMap) {
        this.table2DB = table2DBMap;
    }

    private void doSharding() {

    }

    private BoundSql getBoundSql(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        return boundSql;
    }


    private Map<String, Object> paresParams(BoundSql boundSql) throws Throwable {
        Object parameterObject = boundSql.getParameterObject();
        Object params = null;
        if(PluginCommon.SINGLE_PARAM_CLASSES.contains(parameterObject.getClass())) {
            List beanInfo = boundSql.getParameterMappings();
            if(beanInfo != null && !beanInfo.isEmpty()) {
                ParameterMapping proDescrtptors = (ParameterMapping)beanInfo.get(0);
                params = new HashMap();
                ((Map)params).put(proDescrtptors.getProperty(), parameterObject);
            } else {
                params = Collections.emptyMap();
            }
        } else if(parameterObject instanceof Map) {
            params = (Map)parameterObject;
        } else {
            params = new HashMap();
            BeanInfo var10 = Introspector.getBeanInfo(parameterObject.getClass());
            PropertyDescriptor[] var11 = var10.getPropertyDescriptors();
            if(var11 != null && var11.length > 0) {
                PropertyDescriptor[] arr$ = var11;
                int len$ = var11.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    PropertyDescriptor propDesc = arr$[i$];
                    ((Map)params).put(propDesc.getName(), propDesc.getReadMethod().invoke(parameterObject, new Object[0]));
                }
            }
        }

        return (Map)params;
    }
}
