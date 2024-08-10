package ast.node.stmt;

import ast.node.def.astVarDefNode;
import frontend.astVisitor;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astVarDefStmtNode extends astStmtNode{
    private vector<astVarDefNode> array;
    @Override
    public String toString() {
        return super.toString() + array.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
