package ast.node.expr;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public abstract class astExprNode {
  private String type;
  private boolean isLValue;
}
