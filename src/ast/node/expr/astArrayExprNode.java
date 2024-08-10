package ast.node.expr;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class astArrayExprNode extends astExprNode implements repExpr{
    private astExprNode array, sub;

    @Override
    public String toString() {
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
    
}
