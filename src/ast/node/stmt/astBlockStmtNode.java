package ast.node.stmt;

import frontend.astVisitor;
import util.Scope;
import util.vector;
import util.error.error;

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
