package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irSelect extends irIns{
  String res, cond, tp1, val1, tp2, val2;
  @Override
  public String toString(){
    return res + " = select i1 " + cond + ", " + tp1 + " " + val1 + ", " + tp2 + " " + val2;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
