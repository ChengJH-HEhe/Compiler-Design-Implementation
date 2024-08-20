package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irClassDef extends irDefNode{
  @Override
  public String toString(){
    //TODO
    return "";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
