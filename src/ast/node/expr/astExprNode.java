package ast.node.expr;
import ast.node.astNode;
import util.Info;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public abstract class astExprNode extends astNode{
  private Info type;
  private boolean isLValue = false;
  // add info below
}
