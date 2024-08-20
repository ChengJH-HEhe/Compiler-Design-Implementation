package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.ir.stmt.irStmt;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFuncDef extends irDefNode{
  String retType, fName;
  vector<String> paratypelist, paravaluelist;
  irBlock entry, body;
  @Override
  public String toString(){
    String paralist = "";
    for(int i = 0; i < this.paratypelist.size(); i++){
      paralist += this.paratypelist.get(i) + " " + this.paravaluelist.get(i) + ", ";
    }
    return "define " + retType + " @" + fName + "(" + paralist + ") {\n" + body.toString() + "\n}";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
