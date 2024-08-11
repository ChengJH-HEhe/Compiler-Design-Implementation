package util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class position {
    private int row, column;

    public position(int row, int col) {
        this.row = row;
        this.column = col;
    }
    public position(position rhs) {
        this.row = rhs.row;
        this.column = rhs.column;
    }
    public position(Token token) {
        this.row = token.getLine();
        this.column = token.getCharPositionInLine();
    }

    public position(TerminalNode terminal) {
        this(terminal.getSymbol());
    }

    public position(ParserRuleContext ctx) {
        this(ctx.getStart());
    }

    public int row() { return row; }

    public int col() {
        return column;
    }

    public String toString() { return row + "," + column; }

    // public Object position(position position) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'position'");
    // }
}