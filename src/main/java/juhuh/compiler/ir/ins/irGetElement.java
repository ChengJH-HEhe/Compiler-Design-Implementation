package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irGetElement extends irIns{
  String res, tp, ptrval;
  @Override
  public String toString(){
    return res + "= alloca " + tp;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
