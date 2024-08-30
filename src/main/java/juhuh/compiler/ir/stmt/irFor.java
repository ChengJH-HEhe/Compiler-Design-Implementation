package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFor extends irBlock{
  irBlock cond, inc, body;
  irBlock end;
  @Override
  public String toString(){
    String s = "";
    if(cond != null){
      s += cond.toString();
    }
    if(body != null) {
      s += body.toString();
    }
    if(inc != null){
      s += inc.toString();
    } 
    s += end.toString();
    return s;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
