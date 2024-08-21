package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class irClassDef extends irDefNode{
  irStructDef struct;
  vector<irFuncDef> funcDefs;
  @Override
  public String toString(){
    throw new UnsupportedOperationException("irClassDef toString");
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
