package juhuh.compiler.ast.node.def;

import juhuh.compiler.ast.node.expr.astExprNode;
import juhuh.compiler.ast.node.expr.repExpr;
import juhuh.compiler.ast.node.type.astTypeNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astVarDefNode extends astDefNode implements repExpr{
    private astExprNode unit;
    private astTypeNode type;
    @Override
    public String toString() {
        return super.toString() + (unit != null ? unit.toString() : "") + ";";
    }
    @Override 
    public void replaceExpr(astExprNode oldExpr, astExprNode newExpr) {
        if (unit == oldExpr) {
            unit = newExpr;
        } else throw new error("VarDefNode replaceExpr error");
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
      return visitor.visit(this);
    }
    @Override
    public boolean hasCall() {
        return unit != null && unit.hasCall();
    }
}
