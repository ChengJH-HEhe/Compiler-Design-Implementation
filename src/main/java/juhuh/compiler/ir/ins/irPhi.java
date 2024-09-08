package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irPhi extends irIns{
  String res,labl, tp;
  private vector<String> label, val;
  @Override
  public String toString(){

    String s = res + "." + labl + " = phi " + tp ;
    for(int i = 0; i < label.size(); ++i) {
      s += " [ " + val.get(i) + " , %" + label.get(i) + "],";  
    }
    return s;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
