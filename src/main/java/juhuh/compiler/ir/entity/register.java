package juhuh.compiler.ir.entity;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class register extends entity {
  String name; 
  String ptr, tp;
  @Override
  public String toString() {
    return name;
  }
  @Override
  public <T> T accept(irVisitor<T> visitor)
      throws error {
    return visitor.visit(this);
  }
}
