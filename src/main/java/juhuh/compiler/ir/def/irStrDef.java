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
    int size = 1;
    boolean match = false;
    for(int i = 0; i < init.length(); ++i) {
      if(init.getBytes()[i] == '\\'){
        if(!match) {
          match = true;
        } else {
          match = false;
          ++size;
        }
      } else {
        ++size;
      }
    }
    
    return res + " = private unnamed_addr constant [" + (size) + " x i8] c\""
     + init.replace("\\\n", "\\0A").replace("\\\"", "\\22").replace("\t","\\t") + "\\00\"";
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
