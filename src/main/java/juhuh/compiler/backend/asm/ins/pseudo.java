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
    for(var str : strs)
      s += str + " ";
    return s + "\n";
  }
}
