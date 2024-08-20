package juhuh.compiler.ir;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public abstract class irNode {
  public abstract String toString();

  public abstract <T> T accept(irVisitor<T> visitor) throws error;
}
