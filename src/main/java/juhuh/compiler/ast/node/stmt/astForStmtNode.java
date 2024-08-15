package juhuh.compiler.ast.node.stmt;
import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astForStmtNode extends astStmtNode implements scopeStmt{
    private Scope scope;
    private astStmtNode init;
    private astExprNode cond, update;
    private astStmtNode stmt;
    @Override
    public String toString() {
        if(stmt == null || init == null || cond == null || update == null) return "";
        return super.toString() + "for(" + init.toString() + ";" + cond.toString() + ";" + update.toString() + ")" + stmt.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
