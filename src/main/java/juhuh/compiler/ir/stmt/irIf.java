package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIf extends irBlock{

  irBlock tbran, fbran;
  String label;   
  @Override
  public String toString(){
    String s = tbran.toString() + "\n" + fbran.toString() + "\n" + label + ":\n";
    return s;
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
