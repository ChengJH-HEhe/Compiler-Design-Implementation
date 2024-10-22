package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class irStmt extends irNode{
  String indent;
  public boolean useless;
  @Override
  public String toString() {
    return indent;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  
}
