package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.stmt.irStmt;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIns extends irStmt{
  @Override
  public String toString(){
    throw new UnsupportedOperationException("irIns toString");
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  
}
