package juhuh.compiler.ast.node.stmt;
import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = true)
public class astIfStmtNode extends astStmtNode implements scopeStmt{
    private Scope thenscope,elsescope;
    private astExprNode cond;
    private astStmtNode thenStmt, elseStmt;
    @Override
    public String toString() {
        if(cond == null || thenStmt == null || elseStmt == null) return "";
        return super.toString() + "if(" + cond.toString() + ")" + thenStmt.toString() + (elseStmt == null ? "" : "else" + elseStmt.toString());
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
