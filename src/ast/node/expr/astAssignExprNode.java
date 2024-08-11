package ast.node.expr;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astAssignExprNode extends astExprNode implements repExpr{
    private astExprNode lhs, rhs;
    private final String op="=";

    @Override
    public String toString() {
        return lhs.toString() + " " + op + " " + rhs.toString();
    }

    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }

    @Override
    public void replaceExpr(astExprNode expr, astExprNode replacement) {
        if (lhs == expr) {
            lhs = replacement;
        } else if (rhs == expr) {
            rhs = replacement;
        } else {
            throw new error("Cannot replace expression that does not exist in this node");
        }
    }
}
