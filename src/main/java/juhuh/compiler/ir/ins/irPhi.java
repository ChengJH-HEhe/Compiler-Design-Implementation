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

  @Override
  public String toString(){
    String s = res + "." + labl + " = phi " + tp ;
    for(var entry : label2val.entrySet()){ 
      s += " [ " + entry.getValue() + " , %" + entry.getKey() + "],";  
    }
    return s;
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
