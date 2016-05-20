package com.peter.util.db.antlr;

import com.mongodb.BasicDBObject;
import com.peter.util.exceptions.TechnicalException;
import com.peter.util.time.Time;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;


import src.main.antlr4.SQLiteBaseListener;
import src.main.antlr4.SQLiteParser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Peter on 1/31/2016.
 */
public class SQLtoMongoWalker extends SQLiteBaseListener {
    public command cmd;
    public String tableName;
    public Bson rowsFilter;
    public Bson order;
    public ArrayList<String> columnNames;
    public ArrayList<String> columnValues;
    public HashMap<String,Bson> filtersMap;
    public int limit=0;
    public int offset=0;

    public SQLtoMongoWalker() {
        filtersMap = new HashMap<String,Bson>();
        order=new BasicDBObject();
    }

    public enum logicOperators {
        AND, OR, EQ, EMPTY,GTE,LTE,LT,GT,LIKE
    }

    public enum command {
        SELECT, TRUNCATE, INSERT, COUNT
    }

    @Override
    public void enterSelect_core(SQLiteParser.Select_coreContext ctx) {
        cmd= command.SELECT;
        columnNames = new ArrayList<>();
        tableName = ctx.table_or_subquery(0).getText();
        for (SQLiteParser.Result_columnContext column : ctx.result_column()) {
            columnNames.add(column.getText());
        }
        if (ctx.K_WHERE() != null)
            setFilter(ctx.expr(0));
        if(ctx.expr().size()>0) rowsFilter = filtersMap.get(ctx.expr(0).toString());
    }

    @Override
    public void enterFactored_select_stmt(SQLiteParser.Factored_select_stmtContext ctx) {
        if (ctx.K_LIMIT() != null)
            limit=Integer.parseInt(ctx.expr(0).getText());
        if (ctx.K_OFFSET() != null)
            offset=Integer.parseInt(ctx.expr(1).getText());
        if (ctx.K_ORDER()!=null){
            for(SQLiteParser.Ordering_termContext term:ctx.ordering_term()){
                int asc_order=-1;
                if(term.K_ASC()!=null)
                    asc_order=1;
                ((BasicDBObject)order).append(term.expr().getText(),asc_order);
            }
        }
    }

    @Override
    public void enterInsert_stmt(SQLiteParser.Insert_stmtContext ctx){
        cmd=command.INSERT;
        columnNames = new ArrayList<>();
        columnValues = new ArrayList<>();
        tableName = ctx.table_name().getText();
        for (SQLiteParser.Column_nameContext column : ctx.column_name()) {
            columnNames.add(column.getText());
        }
        for (SQLiteParser.ExprContext exp : ctx.expr()) {
            columnValues.add(exp.getText().replace("'","\""));
        }
    }
    @Override
    public void enterTruncate_table_stmt(SQLiteParser.Truncate_table_stmtContext ctx){
        cmd=command.TRUNCATE;
        tableName = ctx.table_name().getText();
    }

    public logicOperators getOperator(SQLiteParser.ExprContext exp) {
        logicOperators operator = logicOperators.EMPTY;
        if (exp.K_AND() != null) operator = logicOperators.AND;
        if (exp.K_OR() != null) operator = logicOperators.OR;
        if (exp.getChild(1).getText().equals("=")) operator = logicOperators.EQ;
        if (exp.getChild(1).getText().equals(">=")) operator = logicOperators.GTE;
        if (exp.getChild(1).getText().equals(">")) operator = logicOperators.GT;
        if (exp.getChild(1).getText().equals("<=")) operator = logicOperators.LTE;
        if (exp.getChild(1).getText().equals("<")) operator = logicOperators.LT;
        if (exp.getChild(1).getText().equals("~")) operator = logicOperators.LIKE;
        return operator;
    }

    public void updateFilter(SQLiteParser.ExprContext exp) {
        logicOperators operator = getOperator(exp);
        Bson newFilter = null;
        String fieldName =exp.getChild(0).getText();
        String fieldValue =exp.getChild(2).getText().replaceAll("'|'","");
        String keyFilterA =exp.getChild(0).toString();
        String keyFilterB =exp.getChild(2).toString();
        String keyFilterC =exp.getChild(1).toString();
        switch (operator) {
            case EMPTY:
                newFilter = filtersMap.get(keyFilterC);
                break;
            case AND:
                newFilter = and(filtersMap.get(keyFilterA), filtersMap.get(keyFilterB));
                break;
            case OR:
                newFilter = or(filtersMap.get(keyFilterA), filtersMap.get(keyFilterB));
                break;
            case EQ:
                newFilter = eq(fieldName, fieldValue);
                break;
            case GT:
                newFilter = gt(fieldName, getValue(fieldValue));
                break;
            case LT:
                newFilter = lt(fieldName, getValue(fieldValue));
                break;
            case GTE:
                newFilter = gte(fieldName, getValue(fieldValue));
                break;
            case LTE:
                newFilter = lte(fieldName, getValue(fieldValue));
                break;
            case LIKE:
                newFilter = regex(fieldName, fieldValue);
                break;
        }
        filtersMap.put(exp.getRuleContext().toString(),newFilter);
    }

    public Object getValue(String value){
        Object object = null;
        if (Time.isISODate(value)){
            try {
                object= Time.string2Date(value,Time.DEFAULT_ISO_DATE_FORMAT,"GTM+00:00");
            } catch (TechnicalException e) {
                e.printStackTrace();
            }
        } else{
            object= value;
        }
     return object;
    }

    public void setFilter(SQLiteParser.ExprContext n) {
        if (n == null || n.getChildCount() < 3) {
            return;
        } else {
            setFilter(n.expr(0));
            setFilter(n.expr(1));
            updateFilter(n);
        }
    }
}
