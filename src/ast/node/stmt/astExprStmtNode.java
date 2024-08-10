package ast.node.stmt;
import ast.node.expr.astExprNode;
import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.EqualsAndHashCode(callSuper = true)

public class astExprStmtNode extends astStmtNode{
    private astExprNode expr;
    @Override
    public String toString() {
        return super.toString() + expr.toString() + ";";
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    } 
}
