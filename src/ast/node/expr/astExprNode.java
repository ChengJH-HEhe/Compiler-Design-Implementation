package ast.node.expr;
import ast.node.astNode;
import util.typeinfo;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public abstract class astExprNode extends astNode{
  public static enum AtomType {
    INT, BOOL, STRING, VOID, NULL, CUSTOM, THIS
  }
  private typeinfo type;
  private boolean isLValue;
  // add info below
}
