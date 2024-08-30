package juhuh.compiler.ir.entity;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class constant extends entity {
  String name;
  @Override
  public String toString() {
    return name == "this"? "%this.copy" : name;
  }
  @Override
  public void accept(irVisitor visitor)
      throws error {
    visitor.visit(this);
  }
}
