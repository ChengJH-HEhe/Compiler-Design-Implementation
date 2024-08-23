package juhuh.compiler.ast.node.stmt;
import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = true)
public class astWhileStmtNode extends astStmtNode implements scopeStmt{
    private Scope scope;
    private astExprNode cond;
    private astStmtNode stmt;
    @Override
    public String toString() {
        if(stmt == null || cond == null) return "";
        return super.toString() + "while(" + cond.toString() + ")" + stmt.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    
}
