package juhuh.compiler.ir.entity;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class entity extends juhuh.compiler.ir.irNode {
  @Override
  public String toString() {
    return "entity";
  }
  @Override
  public void accept(irVisitor visitor)
      throws error {
    visitor.visit(this);
  }
}
