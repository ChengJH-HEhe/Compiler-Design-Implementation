package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.util.error.error;

public class irStmt extends irNode{
  int indent;
  @Override
  public String toString() {
    throw new UnsupportedOperationException("Unimplemented irstmt 'toString'");
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
