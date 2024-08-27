package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irStructDef extends irDefNode{
  String name;
  vector<String> member;
  public String toString(){
    String member = "";
    if(this.member.size() > 0) {
      member = this.member.get(0);
      for(int i = 1; i < this.member.size(); i++){
        member += "," + this.member.get(i);
      }
    }
    return name + " = type { " + member + " }";
  }
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
