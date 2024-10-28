package juhuh.compiler.ast.node.expr;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class astArrayExprNode extends astExprNode implements repExpr{
    private astExprNode array, sub;

    @Override
    public String toString() {
        if(array == null || sub == null) return "";
        return array.toString() + "[" + sub.toString() + "]";
      }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    @Override
    public void replaceExpr(astExprNode oldNode, astExprNode newNode) {
        if (array == oldNode) array = newNode;
        else if (sub == oldNode) sub = newNode;
        else {
            throw new error("replace expression error");
        }
    }
    @Override
    public boolean hasCall() {
        return array.hasCall() || sub.hasCall();
    }
}
