package com.peter.util.db.antlr;

import main.antlr4.SQLiteBaseListener;
import main.antlr4.SQLiteParser;

/**
 * Created by Peter on 1/31/2016.
 */
public class SQLiteWalker extends SQLiteBaseListener {

    @Override public void enterParse(SQLiteParser.ParseContext ctx) {
        System.out.println( "Entering Parse : "+ctx.toStringTree() + ctx.sql_stmt_list());
    }

    @Override public void enterSelect_core(SQLiteParser.Select_coreContext ctx) {
        System.out.println( "Entering Select_core Clause : " + ctx.K_WHERE().getSymbol());
    }
}
