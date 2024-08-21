package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irStructDef extends irDefNode{
  String name;
  vector<String> member;
  public String toString(){
    return name + " = type { " + member.toString() + " }";
  }
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
