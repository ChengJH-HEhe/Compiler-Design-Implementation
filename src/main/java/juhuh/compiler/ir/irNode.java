package juhuh.compiler.ir;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public abstract class irNode {
  private int block, stmt;
  private juhuh.compiler.opt.regAlloc.live live;
  public abstract String toString();

  public abstract void accept(irVisitor visitor) throws error;
}
