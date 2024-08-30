package juhuh.compiler.ir;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public abstract class irNode {
  public abstract String toString();

  public abstract void accept(irVisitor visitor) throws error;
}
