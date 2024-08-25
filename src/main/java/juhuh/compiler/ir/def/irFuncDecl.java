package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irBuilder;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.FuncInfo;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFuncDecl extends irDefNode{
  FuncInfo info;
  vector<String> paratypelist, paravaluelist;
  public String toString(){
    String paralist = "";
    for(int i = 0; i < this.paratypelist.size(); i++){
      paralist += this.paratypelist.get(i) + " " + this.paravaluelist.get(i) + ", ";
    }
    return "declare " + irBuilder.tp(info.getRetType()) + " @" + info.getName() + "(" + paralist + ")";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
