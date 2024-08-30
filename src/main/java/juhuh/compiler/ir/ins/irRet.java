package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irRet extends irIns{
  String tp, val;
  @Override
  public String toString(){
    if(tp == "void")
      return "ret void";
    else  return "ret " + tp + " " + val;  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
