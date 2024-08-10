package ast.node.stmt;

import ast.node.astNode;

@lombok.experimental.SuperBuilder
@lombok.EqualsAndHashCode(callSuper = false)
public class astStmtNode extends astNode{
  public static int indent = 0;
  @Override
  public String toString() {
    return "  ".repeat(indent);
  }
}
