package juhuh.compiler.ir.ins;

import java.util.Arrays;
import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irLoad extends irIns{
  String res, tp, ptr;
  @Override
  public String toString(){
    return res + " = load " + tp + ", ptr " + ptr;  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public HashSet<String> getUse() {
    return new HashSet<String>(Arrays.asList(ptr));
  }
  @Override
  public String getDef() {
    return res;
  }
}
