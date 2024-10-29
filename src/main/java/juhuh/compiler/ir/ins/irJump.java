package juhuh.compiler.ir.ins;

import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irJump extends irIns{
  String dest;
  @Override
  public String toString(){
    return "br label %" + dest;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public String getDef(){
    return null;
  }
  @Override
  public HashSet<String> getUse(){
    return null;
  }
}
