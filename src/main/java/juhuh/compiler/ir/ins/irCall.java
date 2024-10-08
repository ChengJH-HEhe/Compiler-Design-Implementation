package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irBuilder;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.FuncInfo;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irCall extends irIns{
  String res;
  FuncInfo func;
  vector<String> type, val;
  @Override
  public String toString(){
    String s = "";
    if(type.size() > 0) {
      s = type.get(0) + " " + val.get(0);
      assert(type.size() == val.size());
      for(int i = 1; i < type.size(); i++){
        s +=  ", " + type.get(i) + " " ;
        s += val.get(i) ;
      }
    }
    if(res.equals(""))
      return "call void @" + func.getName() + "(" + s + ")";
    return res + " = call " + irBuilder.tp(func.getRetType()) + " @" + func.getName() + "(" + s + ")";
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  
}
