package ast.node.stmt;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astConstrNode extends astStmtNode {
  private String className;
  private astBlockStmtNode block;

  @Override
  public String toString() {
    return super.toString() + className + "(" + ")" + block.toString();
  }

  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
