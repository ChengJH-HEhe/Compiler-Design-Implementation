package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irAlloca extends irIns{
  String res, tp;
  @Override
  public String toString(){
    return res + "= alloca " + tp;  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
