package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astContinueStmtNode extends astStmtNode {
    @Override
    public String toString() {
        return super.toString() + " continue;";
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}