package ast.node.expr;

import frontend.astVisitor;
import util.typeinfo;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
// 0维array即是普通变量
public class astNewArrayExprNode extends astExprNode implements repExpr{
    private vector<astExprNode> lengths;
    private astArrayConstExpr init;
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("new" + getType().toString());
        for (astExprNode length : lengths) {
            s.append("[" + length.toString() + "]");
        }
        for(int i = lengths.size(); i < ((typeinfo)getType()).getDim(); i++) {
            s.append("[]");
        }
        return s.toString();
    }
    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
      return visitor.visit(this);
    }
    @Override
    public void replaceExpr(astExprNode expr, astExprNode replacement) {
        for (int i = 0; i < lengths.size(); i++) {
            if (lengths.get(i) == expr) {
                lengths.set(i, replacement);
                return;
            }
        }
        throw new error("replace expression not exist");
    }
}
