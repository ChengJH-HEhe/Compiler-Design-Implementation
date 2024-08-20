package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irCall extends irIns{
  String res, retType, func;
  vector<String> type, val;
  @Override
  public String toString(){
    String s = "";
    for(int i = 0; i < type.size(); i++){
      s += type.get(i) + " " + val.get(i) + ", ";
    }
    if(res.equals(""))
      return "call void " + func + "(" + s + ")";
    return res + " = call " + retType + " " + func + "(" + s + ")";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
