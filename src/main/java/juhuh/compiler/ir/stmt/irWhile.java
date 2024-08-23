package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irWhile extends irStmt{
  irBlock cond, body;
  String label;
  @Override
  public String toString(){
    String s = "";
    if(cond != null){
      s += cond.toString();
    }
    if(body != null) {
      s += body.toString();
    }
    s += label + ":\n";
    return s;
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
