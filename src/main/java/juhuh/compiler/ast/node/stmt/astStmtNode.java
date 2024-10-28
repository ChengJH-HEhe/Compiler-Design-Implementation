package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.util.error.error;

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
  @Override 
  public boolean hasCall() {
    throw new error("shouldn't visited");
  }
}
