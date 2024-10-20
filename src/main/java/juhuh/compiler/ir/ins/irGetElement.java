package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irGetElement extends irIns{
  String res, tp, ptrval, tp1, tp2;
  String id1, id2;
  @Override
  public String toString(){
    String s =  res + " = getelementptr " + tp + ", ptr " + ptrval + ", " + 
    (tp1 + " " + id1) ;
    if(tp2 != null) s += ", " + (tp2 + " " + id2);  
    return s;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
