package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

public class irFuncDef extends irDefNode{
  String retType, fName;
  vector<String> paralist;
  vector<String> body;
  @Override
  public String toString(){
    return "define " + retType + " @" + fName + "(" + paralist + ") {\n" + body + "\n}";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
