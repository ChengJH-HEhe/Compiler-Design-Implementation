package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irGlobalDef extends irDefNode{
  String name, type, init;
  public String toString(){
    return name + " = global " + type + " " + init;
  }
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
