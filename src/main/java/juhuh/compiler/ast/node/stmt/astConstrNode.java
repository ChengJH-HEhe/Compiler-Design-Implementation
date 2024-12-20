package juhuh.compiler.ast.node.stmt;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astConstrNode extends astStmtNode {
  private String className;
  private astBlockStmtNode block;
  private Scope scope, Origin;

  @Override
  public String toString() {
    if(block == null) return "";
    return super.toString() + className + "(" + ")" + block.toString();
  }

  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
  @Override
  public boolean hasCall() {
    return block != null && block.hasCall();
  }
}
