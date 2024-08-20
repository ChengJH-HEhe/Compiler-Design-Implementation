package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irStore extends irIns{
  String tp, res, ptr;
  @Override
  public String toString(){
    return "store " + tp + " " + res + ", ptr " + ptr;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
