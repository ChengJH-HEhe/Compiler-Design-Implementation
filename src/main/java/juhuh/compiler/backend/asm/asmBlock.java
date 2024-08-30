package juhuh.compiler.backend.asm;

import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmBlock extends asmNode {
  private String label;
  private vector<asmNode> nodes;
  public void add(asmNode ins) {
    nodes.add(ins);
  }
  @Override
  public String toString() {
    String s = ".globl " + label + "\n .type @function\n" + label + ":\n";
    for (asmNode node : nodes) {
      s += node.toString() + "\n";
    }
    return s;
  }
}
