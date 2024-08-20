package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

public class irGlobalDef extends irDefNode{
  String name, type, initConst;
  public String toString(){
    return name + " = global " + type + " " + initConst;
  }
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
