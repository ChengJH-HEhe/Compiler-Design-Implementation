package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)

public class astEmptyStmtNode extends astStmtNode{
    public astEmptyStmtNode(){
        super();
    }
    @Override
    public String toString() {
        return super.toString() + ";";
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}