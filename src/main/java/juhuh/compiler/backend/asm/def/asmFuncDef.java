package juhuh.compiler.backend.asm.def;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmFuncDef extends asmNode{
  private String name;
  private vector<asmBlock> nodes;
  @Override
  public String toString() {
    String s = "";
    s += "  .globl " + name + "\n";
    // entry changes name to funcname
    for (asmNode node : nodes) {
      s += node.toString() + "\n";
    }
    s += "\n";
    
    return s;
  }
}
