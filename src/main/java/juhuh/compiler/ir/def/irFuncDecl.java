package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFuncDecl extends irDefNode{
  String retType, fName;
  vector<String> paratypelist, paravaluelist;
  public String toString(){
    String paralist = "";
    for(int i = 0; i < this.paratypelist.size(); i++){
      paralist += this.paratypelist.get(i) + " " + this.paravaluelist.get(i) + ", ";
    }
    return "declare " + retType + " @" + fName + "(" + paralist + ")";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
  
}
