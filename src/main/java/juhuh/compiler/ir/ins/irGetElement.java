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
    return res + " = getelementptr " + tp + ", ptr " + ptrval + ", " +
    (tp2 != null ? (tp1 + " " + id1 + " " + tp2 + " " + id2):(tp1 + " " + id1) );  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
