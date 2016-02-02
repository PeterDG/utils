package com.peter.util.db.antlr;

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

    public String tableName;
    public Bson rowsFilter;
    public ArrayList<String> columnsFilter;
    public HashMap<String,Bson> filtersMap;

    public SQLtoMongoWalker() {
        filtersMap = new HashMap<String,Bson>();
    }

    public enum logicOperators {
        AND, OR, EQ, EMPTY,GTE,LTE,LT,GT,LIKE
    }

    @Override
    public void enterSelect_core(SQLiteParser.Select_coreContext ctx) {
        columnsFilter = new ArrayList<>();
        tableName = ctx.table_or_subquery(0).getText();
        for (SQLiteParser.Result_columnContext column : ctx.result_column()) {
            columnsFilter.add(column.getText());
        }
        if (ctx.K_WHERE() != null)
            setFilter(ctx.expr(0));
        if(ctx.expr().size()>0) rowsFilter = filtersMap.get(ctx.expr(0).toString());
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
