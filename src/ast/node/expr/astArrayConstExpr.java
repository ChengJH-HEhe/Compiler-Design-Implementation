package ast.node.expr;

import frontend.astVisitor;
import util.typeinfo;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Value
@lombok.EqualsAndHashCode(callSuper = true)
public class astArrayConstExpr extends astExprNode{
    private typeinfo constType;
    private vector<astExprNode> vec;
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    @Override
    public String toString() {
        return "{" + (vec.toString()) + "}";     
    }
    
}
