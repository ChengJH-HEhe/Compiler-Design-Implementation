package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.util.error.error;

public class irIns extends irNode{
  @Override
  public String toString(){
    throw new UnsupportedOperationException("irIns toString");
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
