package ast.node.expr;

import frontend.astVisitor;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astFStrExpr extends astExprNode {
  private vector<String> vecStr;
  private vector<astExprNode> vecExpr;

  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }

  @Override
    public String toString() {
      String s = "";
      for(int i = 0; i < vecExpr.size(); ++i)
          s += vecStr.get(i).toString() + vecExpr.get(i).toString();
      s += vecStr.getlst().toString();
      return s;
    }
}
