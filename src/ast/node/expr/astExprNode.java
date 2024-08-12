package ast.node.expr;
import ast.node.astNode;
import util.typeinfo;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public abstract class astExprNode extends astNode{
  private typeinfo type;
  private boolean isLValue = false;
  // add info below
}
