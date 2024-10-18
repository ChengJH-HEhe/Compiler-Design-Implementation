package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class pseudo extends asmNode{
  private vector<String> strs;
  @Override
  public String toString() {
    String s = "";
    boolean first = false;
    
    for(var str : strs)
      if(first)
        s += str + ", ";
      else {
        first = true;
        s += str + "  ";
      }
    return s.substring(0, s.length()-2);
  }
}
