package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIf extends irStmt{

  irBlock cond, tbran, fbran;

  @Override
  public String toString(){
    String s = cond.toString() + tbran.toString() + fbran.toString();
    return s;
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
