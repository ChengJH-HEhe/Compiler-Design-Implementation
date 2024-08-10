package ast.node.stmt;
import ast.node.expr.astExprNode;
import frontend.astVisitor;
import util.Scope;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astWhileStmtNode extends astStmtNode implements scopeStmt{
    private Scope scope;
    private astExprNode cond;
    private astStmtNode stmt;
    @Override
    public String toString() {
        return super.toString() + "while(" + cond.toString() + ")" + stmt.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    
}
