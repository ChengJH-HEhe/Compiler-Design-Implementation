package ast.node.stmt;

import ast.node.expr.astExprNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class astPureExprNode extends astStmtNode {
  astExprNode expr;

}
