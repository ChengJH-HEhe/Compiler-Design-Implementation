package ast.node.stmt;
import ast.node.expr.astExprNode;
import frontend.astVisitor;
import util.Scope;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astIfStmtNode extends astStmtNode implements scopeStmt{
    private Scope scope;
    private astExprNode cond;
    private astStmtNode thenStmt, elseStmt;
    @Override
    public String toString() {
        return super.toString() + "if(" + cond.toString() + ")" + thenStmt.toString() + (elseStmt == null ? "" : "else" + elseStmt.toString());
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
