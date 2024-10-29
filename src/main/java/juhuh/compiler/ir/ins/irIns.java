package juhuh.compiler.ir.ins;

import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.stmt.irStmt;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIns extends irStmt{
  public boolean live;
  @Override
  public String toString(){
    throw new UnsupportedOperationException("irIns toString");
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public HashSet<String> getUse(){
    throw new UnsupportedOperationException("irIns getUse");
  }
  @Override
  public String getDef(){
    throw new UnsupportedOperationException("irIns getDef");
  }
}
