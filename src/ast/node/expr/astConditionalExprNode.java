package ast.node.expr;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astConditionalExprNode extends astExprNode implements repExpr{
    private astExprNode cond, lhs, rhs;
    @Override
    public String toString() {
        return cond.toString() + "? " + lhs.toString() + " : " + rhs.toString();
    }

    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    @Override
    public void replaceExpr(astExprNode expr, astExprNode replacement) {
        if (cond == expr) {
            cond = replacement;
        } else if (lhs == expr) {
            lhs = replacement;
        } else if (rhs == expr) {
            rhs = replacement;
        } else {
            throw new error("replace expression not exist");
        }
    }
}
