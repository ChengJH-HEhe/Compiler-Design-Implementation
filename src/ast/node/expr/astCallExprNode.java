package ast.node.expr;

import frontend.astVisitor;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astCallExprNode extends astExprNode implements repExpr {
  private astExprNode func;
  private vector<astExprNode> args;

  @Override
  public String toString() {
    return func + "(" + args.toString("", ", ", "") + ")";
  }

  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
  @Override
  public void replaceExpr(astExprNode expr, astExprNode replacement) {
    if (func == expr) {
      func = replacement;
    } else {
      for (int i = 0; i < args.size(); i++) {
        if (args.get(i) == expr) {
          args.set(i, replacement);
          return;
        }
      }
      throw new error("replace expression not exist");
    }
  }
}
