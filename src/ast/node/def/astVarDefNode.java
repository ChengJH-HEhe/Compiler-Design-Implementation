package ast.node.def;

import ast.node.expr.astExprNode;
import ast.node.expr.repExpr;
import ast.node.type.astTypeNode;
import util.error.error;

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
}
