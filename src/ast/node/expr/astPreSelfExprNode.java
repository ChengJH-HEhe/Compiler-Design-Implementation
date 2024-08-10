package ast.node.expr;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astPreSelfExprNode extends astExprNode implements repExpr{
    private String op;
    private astExprNode expr;
    @Override
    public String toString() {
        return op + op + expr.toString();
    }
    @Override
    public void replaceExpr(astExprNode expr, astExprNode replacement) {
        if (this.expr == expr) {
            this.expr = replacement;
        } else {
            throw new error("replace expression not exist");
        }
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
