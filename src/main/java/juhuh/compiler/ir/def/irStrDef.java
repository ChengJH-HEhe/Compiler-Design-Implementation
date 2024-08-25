package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class irStrDef extends irGlobalDef{
  String res;
  String init;
  public static int strNum = 0;
  @Override
  public String toString(){
    return res + " = private unnamed_addr constant [" + init.length() + " x i8] c\"" + init + "\\00\"";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
