package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.ast.node.def.astVarDefNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class astVarDefStmtNode extends astStmtNode{
    private vector<astVarDefNode> array;
    @Override
    public String toString() {
        if(array == null) return "";
        return super.toString() + array.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
    @Override
    public boolean hasCall() {
        for(var array : array){
            if(array.hasCall()) return true;
        }
        return false;
    }
}
