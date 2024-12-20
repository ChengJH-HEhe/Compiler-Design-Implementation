package juhuh.compiler.ir.ins;

import java.util.Arrays;
import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irBranch extends irIns{
  public String cond, iftrue, iffalse;
  public irIcmp cmp;
  @Override 
  public String toString() {
    String s = (cmp == null? "" : cmp.toString());
    return s + "\n  br i1 " + cond +", label %" + iftrue + ", label %" + iffalse;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public HashSet<String> getUse() {
    return new HashSet<String>(Arrays.asList(cond));
  }
} 
