package juhuh.compiler.ast.node.stmt;
import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)

public class astExprStmtNode extends astStmtNode{
    private astExprNode expr;
    @Override
    public String toString() {
        if(expr == null) return "";
        return super.toString() + expr.toString() + ";";
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    } 
    @Override
    public boolean hasCall() {
        return expr.hasCall();
    }
}
