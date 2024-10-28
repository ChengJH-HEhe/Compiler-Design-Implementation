package juhuh.compiler.ast.node.stmt;
import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astReturnStmtNode extends astStmtNode {
    private astExprNode expr;
    @Override
    public String toString() {
        return super.toString() + (expr == null ? "return;" : "return " + expr.toString() + ";");
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    @Override
    public boolean hasCall() {
        return expr == null ? false : expr.hasCall();
    }
}
