package juhuh.compiler.ir.entity;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class boolConst extends constant {
  public boolean value;
  @Override
  public String toString() {
    return value == true ? "1" : "0";
  }
  @Override
  public void accept(irVisitor visitor)
      throws error {
    visitor.visit(this);
  }
}
