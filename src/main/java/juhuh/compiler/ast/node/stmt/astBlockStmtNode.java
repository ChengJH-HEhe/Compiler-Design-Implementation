package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = true)
public class astBlockStmtNode extends astStmtNode implements scopeStmt{
    private Scope scope;
    private final vector<astStmtNode> stmts;
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{\n");
        indent++;
        for (astStmtNode stmt : stmts) {
            string.append(stmt.toString()).append("\n");
        }
        indent--;
        string.append("\n" + super.toString() + "}");
        return string.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    
}
