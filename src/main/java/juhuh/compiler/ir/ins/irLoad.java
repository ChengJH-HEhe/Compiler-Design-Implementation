package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irLoad extends irIns{
  String res, tp, ptr;
  @Override
  public String toString(){
    return res + " = load " + tp + ", ptr " + ptr;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
