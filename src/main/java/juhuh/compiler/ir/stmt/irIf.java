package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIf extends irBlock{

  irBlock tbran, fbran, end;   
  @Override
  public String toString(){
    String s = tbran.toString() + "\n" + fbran.toString() + "\n" + end.toString() + ":\n";
    return s;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  
}
