package juhuh.compiler.ir.ins;

import java.util.HashSet;

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
  vector<String> reg;
  public void addReg(int num) {
    if(reg == null)
      reg = new vector<String>();
    reg.add("s" + num);
  }
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
  @Override
  public HashSet<String> getUse() {
    return new HashSet<String>(val);
  }
  @Override
  public String getDef() {
    return res.equals("")? null : res;
  }
}
