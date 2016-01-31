package com.peter.util.db.antlr;

import main.antlr4.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * Created by Peter on 1/31/2016.
 */
public class SQLiteWalkerTest {

    @Test
    public void testEnterParser() throws Exception {
        String string = "select _id,date FROM historicalUsernMetrics WHERE (_id='dbetancourt' OR _id='dcastaneda') AND (date='2015-06-23T18:28:30.859Z' OR date='2015-06-23T03:52:07.796Z')".toLowerCase();
        //     string = "CREATE DATABASE dbname;".toLowerCase();
        CharStream charStream = new ANTLRInputStream(string);
        // Get our lexer
        SQLiteLexer lexer = new SQLiteLexer(charStream);
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Pass the tokens to the parser
        SQLiteParser parser = new SQLiteParser(tokens);
        ParseTree tree = parser.parse();
        ParseTreeWalker walker = new ParseTreeWalker();
        //Set Listener
        walker.walk( new SQLiteWalker(), tree );
    }
}