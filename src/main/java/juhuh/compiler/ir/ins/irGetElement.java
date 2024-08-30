package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irGetElement extends irIns{
  String res, tp, ptrval, tp1;
  String id1;
  @Override
  public String toString(){
    return res + " = getelementptr " + tp + ", ptr " + ptrval + ", " + 
    (tp1 + " " + id1);  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
