package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irBinary extends irIns{
  String res, op, tp, op1, op2;
  @Override
  public String toString(){
    return res + " = " + op + " " + tp + " " + op1 + " " + op2;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
