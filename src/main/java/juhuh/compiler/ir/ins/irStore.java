package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irStore extends irIns{
  String tp, res, ptr;
  @Override
  public String toString(){
    return "store " + tp + " " + res + ", ptr " + ptr;  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public java.util.HashSet<String> getUse() {
    return new java.util.HashSet<String>(java.util.Arrays.asList(res, ptr));
  }
  @Override
  public String getDef() {
    return null;
  }
}
