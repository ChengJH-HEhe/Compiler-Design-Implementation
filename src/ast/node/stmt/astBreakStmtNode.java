package ast.node.stmt;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astBreakStmtNode extends astStmtNode {
    @Override
    public String toString() {
        return super.toString() + " break;";
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
