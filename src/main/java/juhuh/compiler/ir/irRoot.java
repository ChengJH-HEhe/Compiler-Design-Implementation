package juhuh.compiler.ir;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class irRoot extends irNode{
  public String toString(){
    //TODO
    return "";
  }
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
