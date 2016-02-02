package com.peter.util.db.antlr;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import src.main.antlr4.SQLiteLexer;
import src.main.antlr4.SQLiteParser;

/**
 * Created by Peter on 1/31/2016.
 */
public class SQLiteInterpreter {
    public String query;
    public CharStream charStream;
    public SQLiteLexer lexer;
    public CommonTokenStream tokens;
    public SQLtoMongoWalker sqLtoMongoWalker;

    public SQLiteInterpreter(String query) {
        this.query = query;
        charStream = new ANTLRInputStream(query);
        lexer = new SQLiteLexer(charStream);
        tokens = new CommonTokenStream(lexer);
        sqLtoMongoWalker =new SQLtoMongoWalker();
    }

    public void setQueryParameters(){
        SQLiteParser parser = new SQLiteParser(tokens);
        ParseTree tree = parser.parse();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( sqLtoMongoWalker, tree );

    }
}
