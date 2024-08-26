package juhuh.compiler.ast.node.expr;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Value
@lombok.EqualsAndHashCode(callSuper = true)

public class astArrayConstExpr extends astAtomExprNode{
    private vector<astExprNode> vec;
    @Override
    public String toString() {
        return "{" + (vec != null?vec.toString():"") + "}";     
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
