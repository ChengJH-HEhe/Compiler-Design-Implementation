package ast.node;

import frontend.astVisitor;
import util.position;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class astNode {
  protected astNode parent;
  protected position pos;
  public astNode() {
    this.pos = new position(0, 0);
  }
  public astNode(position pos) {
    this.pos = pos;
  }

  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
