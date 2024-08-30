package juhuh.compiler.backend.asm.def;

import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmFuncDef extends asmNode{
  private String name;
  private vector<asmNode> nodes;
  @Override
  public String toString() {
    String s = "";
    s += "  .globl " + name + "\n" + name + ":\n";
    // TODO entry changes name to funcname
    for (asmNode node : nodes) {
      s += node.toString() + "\n";
    }
    s += "\n";
    
    return s;
  }
}
