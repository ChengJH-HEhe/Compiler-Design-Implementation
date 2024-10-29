package juhuh.compiler.ir.stmt;

import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public abstract class irStmt extends irNode{
  String indent;
  public boolean useful;
  public int bid, sid;
  public void setB2S(int b, int s) {
    bid = b;
    sid = s;
  }
  @Override
  public String toString() {
    return indent;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  abstract public HashSet<String> getUse();
  abstract public String getDef();

}
