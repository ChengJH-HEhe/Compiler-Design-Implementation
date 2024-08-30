package juhuh.compiler.ir.entity;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class intConst extends constant {
  int value;
  @Override
  public String toString() {
    return Integer.toString(value);
  }
  @Override
  public void accept(irVisitor visitor)
      throws error {
    visitor.visit(this);
  }
}