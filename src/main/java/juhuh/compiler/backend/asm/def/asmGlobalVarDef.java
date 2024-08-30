package juhuh.compiler.backend.asm.def;

import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmGlobalVarDef extends asmVarDef {
  private String name;
  private vector<asmNode> nodes;
  private int size;
  @Override
  public String toString() {
    return "  .globl " + name + "\n" + name + ":\n  .word " + size + "\n";
  }
}
