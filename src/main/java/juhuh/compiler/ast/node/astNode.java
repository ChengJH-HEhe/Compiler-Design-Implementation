package juhuh.compiler.ast.node;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.position;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public abstract class astNode {
  protected astNode parent;
  protected position pos;
  public astNode() {
    this.pos = new position(0, 0);
  }
  public astNode(position pos) {
    this.pos = pos;
  }

  public abstract boolean hasCall();
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
