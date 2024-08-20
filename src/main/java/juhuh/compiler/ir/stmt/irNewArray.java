package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irNewArray extends irStmt{
  @Override
  public String toString(){
    //TODO
    return "";
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
