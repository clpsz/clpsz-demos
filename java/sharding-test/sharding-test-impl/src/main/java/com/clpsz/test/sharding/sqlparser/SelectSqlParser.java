package com.clpsz.test.sharding.sqlparser;

/**
 * Created by clpsz on 2017/3/14.
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

public class SelectSqlParser implements SqlParser, SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor {
    private boolean inited = false;
    private Select statement;
    private List<Table> tables = new ArrayList();

    public SelectSqlParser(Select statement) {
        this.statement = statement;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public String toSQL() {
        return this.statement.toString();
    }

    public void init() {
        if(!this.inited) {
            this.inited = true;
            this.statement.getSelectBody().accept(this);
        }
    }

    public void visit(PlainSelect plainSelect) {
        plainSelect.getFromItem().accept(this);
        if(plainSelect.getJoins() != null) {
            Iterator joinsIt = plainSelect.getJoins().iterator();

            while(joinsIt.hasNext()) {
                Join join = (Join)joinsIt.next();
                join.getRightItem().accept(this);
            }
        }

        if(plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(this);
        }

    }

    public void visit(Table table) {
        this.tables.add(table);
    }

    public void visit(SubSelect subSelect) {
        subSelect.getSelectBody().accept(this);
    }

    public void visit(Addition addition) {
        this.visitBinaryExpression(addition);
    }

    public void visit(AndExpression andExpression) {
        this.visitBinaryExpression(andExpression);
    }

    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        between.getBetweenExpressionStart().accept(this);
        between.getBetweenExpressionEnd().accept(this);
    }

    public void visit(Column tableColumn) {
    }

    public void visit(Division division) {
        this.visitBinaryExpression(division);
    }

    public void visit(HexValue hexValue) {
    }

    public void visit(MySQLGroupConcat groupConcat) {
    }

    public void visit(RowConstructor rowConstructor) {
    }

    public void visit(DoubleValue doubleValue) {
    }

    public void visit(EqualsTo equalsTo) {
        this.visitBinaryExpression(equalsTo);
    }

    public void visit(Function function) {
    }

    public void visit(GreaterThan greaterThan) {
        this.visitBinaryExpression(greaterThan);
    }

    public void visit(GreaterThanEquals greaterThanEquals) {
        this.visitBinaryExpression(greaterThanEquals);
    }

    public void visit(InExpression inExpression) {
        if(inExpression.getLeftExpression() != null) {
            inExpression.getLeftExpression().accept(this);
        }

        if(inExpression.getLeftItemsList() != null) {
            inExpression.getLeftItemsList().accept(this);
        }

        if(inExpression.getRightItemsList() != null) {
            inExpression.getRightItemsList().accept(this);
        }

    }

    public void visit(IsNullExpression isNullExpression) {
    }

    public void visit(JdbcParameter jdbcParameter) {
    }

    public void visit(LikeExpression likeExpression) {
        this.visitBinaryExpression(likeExpression);
    }

    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept(this);
    }

    public void visit(LongValue longValue) {
    }

    public void visit(MinorThan minorThan) {
        this.visitBinaryExpression(minorThan);
    }

    public void visit(MinorThanEquals minorThanEquals) {
        this.visitBinaryExpression(minorThanEquals);
    }

    public void visit(Multiplication multiplication) {
        this.visitBinaryExpression(multiplication);
    }

    public void visit(NotEqualsTo notEqualsTo) {
        this.visitBinaryExpression(notEqualsTo);
    }

    public void visit(NullValue nullValue) {
    }

    public void visit(OrExpression orExpression) {
        this.visitBinaryExpression(orExpression);
    }

    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }

    public void visit(Subtraction subtraction) {
        this.visitBinaryExpression(subtraction);
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }

    public void visit(ExpressionList expressionList) {
        Iterator iter = expressionList.getExpressions().iterator();

        while(iter.hasNext()) {
            Expression expression = (Expression)iter.next();
            expression.accept(this);
        }

    }

    public void visit(DateValue dateValue) {
    }

    public void visit(TimestampValue timestampValue) {
    }

    public void visit(TimeValue timeValue) {
    }

    public void visit(CaseExpression caseExpression) {
    }

    public void visit(WhenClause whenClause) {
    }

    public void visit(AllComparisonExpression allComparisonExpression) {
        allComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }

    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(this);
        subjoin.getJoin().getRightItem().accept(this);
    }

    public void visit(Concat concat) {
        this.visitBinaryExpression(concat);
    }

    public void visit(Matches matches) {
        this.visitBinaryExpression(matches);
    }

    public void visit(BitwiseAnd bitwiseAnd) {
        this.visitBinaryExpression(bitwiseAnd);
    }

    public void visit(BitwiseOr bitwiseOr) {
        this.visitBinaryExpression(bitwiseOr);
    }

    public void visit(BitwiseXor bitwiseXor) {
        this.visitBinaryExpression(bitwiseXor);
    }

    public void visit(StringValue stringValue) {
    }

    public void visit(MultiExpressionList expressionList) {
    }

    public void visit(SignedExpression arg0) {
    }

    public void visit(JdbcNamedParameter arg0) {
    }

    public void visit(CastExpression arg0) {
    }

    public void visit(Modulo arg0) {
    }

    public void visit(AnalyticExpression arg0) {
    }

    public void visit(WithinGroupExpression arg0) {
    }

    public void visit(ExtractExpression arg0) {
    }

    public void visit(IntervalExpression arg0) {
    }

    public void visit(OracleHierarchicalExpression arg0) {
    }

    public void visit(RegExpMatchOperator arg0) {
    }

    public void visit(JsonExpression arg0) {
    }

    public void visit(RegExpMySQLOperator arg0) {
    }

    public void visit(UserVariable arg0) {
    }

    public void visit(NumericBind arg0) {
    }

    public void visit(KeepExpression arg0) {
    }

    public void visit(LateralSubSelect arg0) {
    }

    public void visit(ValuesList arg0) {
    }

    public void visit(SetOperationList arg0) {
    }

    public void visit(WithItem withItem) {
    }

    public void visit(OracleHint oracleHint) {

    }

    public void visit(TimeKeyExpression timeKeyExpression) {

    }

    public void visit(DateTimeLiteralExpression dateTimeLiteralExpression) {

    }

    public void visit(TableFunction tableFunction) {

    }
}
