package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.ast.node.astNode;

@lombok.experimental.SuperBuilder
@lombok.EqualsAndHashCode(callSuper = false)
public class astStmtNode extends astNode{
  public static int indent = 0;
  public astStmtNode(){
    super();
  }
  @Override
  public String toString() {
    return "  ".repeat(indent);
  }
}
