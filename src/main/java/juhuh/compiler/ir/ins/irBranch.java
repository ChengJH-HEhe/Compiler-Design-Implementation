package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irBranch extends irIns{
  public String cond, iftrue, iffalse;
  @Override 
  public String toString() {
    return "br i1 " + cond +", label %" + iftrue + ", label %" + iffalse;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
} 
