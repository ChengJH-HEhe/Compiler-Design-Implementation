package juhuh.compiler.ir.ins;

import java.util.HashMap;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irPhi extends irIns{
  String res, labl, tp;
  private HashMap<String, String> label2val;
  public String getReg() {
    return res + "." + labl;
  }
  @Override
  public String toString(){
    String s = getReg() + " = phi " + tp ;

    for(var entry : label2val.entrySet()){ 
      s += " [ " + entry.getValue() + " , %" + entry.getKey() + "],";  
    }
    return s.substring(0, s.length() - 1);
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
